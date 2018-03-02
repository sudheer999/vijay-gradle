package com.dikshatech.common.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * @author gurunath.rokkam
 */
public class JDBCUtiility {

	private static Logger		logger	= LoggerUtil.getLogger(JDBCUtiility.class);
	private static JDBCUtiility	ju		= null;

	private JDBCUtiility() {}

	public static JDBCUtiility getInstance() {
		if (ju == null) ju = new JDBCUtiility();
		return ju;
		//return(new JDBCUtiility());
	}

	public List<Object> getSingleColumn(String sql, Object[] sqlParams) {
		return getSingleColumn(sql, sqlParams, null);
	}

	public List<Object> getSingleColumn(String sql, Object[] sqlParams, Connection conn) {
		boolean isConnPassed = (conn != null);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Object> resultList = new ArrayList<Object>();
		try{
			if (!isConnPassed)
			conn = ResourceManager.getConnection();
			// construct the SQL statement
			final String SQL = sql;
			if (logger.isDebugEnabled()) logger.debug("Executing " + SQL);
			// prepare statement
			stmt = conn.prepareStatement(SQL);
			// bind parameters
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++){
				stmt.setObject(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();
			// fetch the results
			while (rs.next())
				resultList.add(rs.getObject(1));
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			_e.printStackTrace();
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnPassed) ResourceManager.close(conn);
		}
		return resultList;
	}
	public List<Object[]> getData(String sql, Object[] sqlParams) {
		return getData(sql, sqlParams, null);
	}

	public List<Object[]> getData(String sql, Object[] sqlParams, Connection conn) {
		boolean isConnPassed = (conn != null);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Object[]> rows = new ArrayList<Object[]>();
		try{
			if (!isConnPassed) conn = ResourceManager.getConnection();
			// construct the SQL statement
			final String SQL = sql;
			if (logger.isDebugEnabled()) logger.debug("Executing " + SQL);
			// prepare statement
			stmt = conn.prepareStatement(SQL);
			// bind parameters
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++){
				stmt.setObject(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();
			// fetch the results
			int count = rs.getMetaData().getColumnCount();
			while (rs.next()){
				Object[] rowdata = new Object[count];
				for (int i = 0; i < count; i++){
					rowdata[i] = rs.getObject(i + 1);
				}
				rows.add(rowdata);
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			_e.printStackTrace();
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnPassed) ResourceManager.close(conn);
		}
		return rows;
	}

	public long getRowCount(String sql, Object[] sqlParams) {
		return getRowCount(sql, sqlParams, null);
	}

	public long getRowCount(String sql, Object[] sqlParams, Connection conn) {
		final boolean isConnSupplied = (conn != null);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		long count = 0;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? conn : ResourceManager.getConnection();
			final String SQL = "SELECT COUNT(*) " + sql;
			// construct the SQL statement
			if (logger.isDebugEnabled()) logger.debug("Executing " + SQL);
			// prepare statement
			stmt = conn.prepareStatement(SQL);
			// bind parameters
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++){
				stmt.setObject(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();
			// fetch the results
			if (rs.next()) count = rs.getLong(1);
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			_e.printStackTrace();
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) ResourceManager.close(conn);
		}
		return count;
	}

	public int update(String updateQuery, Object[] sqlParams) {
		return update(updateQuery, sqlParams, null);
	}

	public int update(String updateQuery, Object[] sqlParams, Connection conn) {
		// declare variables
		final boolean isConnSupplied = (conn != null);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int noOfRowsUpdated = 0;
		try{
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? conn : ResourceManager.getConnection();
			if (logger.isDebugEnabled()) logger.debug("Executing " + updateQuery);
			// prepare statement
			stmt = conn.prepareStatement(updateQuery);
			// bind parameters not required
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++){
				stmt.setObject(i + 1, sqlParams[i]);
			}
			noOfRowsUpdated = stmt.executeUpdate();
		} catch (MySQLIntegrityConstraintViolationException _e){
			noOfRowsUpdated = -1;
			logger.error(_e.getMessage());
		} catch (Exception _e){
			noOfRowsUpdated = -1;
			logger.error("Exception: " + _e.getMessage(), _e);
			_e.printStackTrace();
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
		return noOfRowsUpdated;
	}

	/**
	 * For updating records based no non-key attribute. MySQL 5.0 safe mode update do not allow 
	 * mass updates and delete ie. queries with non-key column in where clause.
	 * @param WHERE
	 */
	public void updateInbox(final String SET, final String WHERE) {
		String selectInbox = "SELECT * FROM INBOX WHERE " + WHERE;
		StringBuilder updateSet = new StringBuilder("UPDATE INBOX SET ");
		updateSet.append(SET);
		updateSet.append(" ");
		try{
			InboxDao inboxProvider = InboxDaoFactory.create();
			Inbox[] notificationInbox = inboxProvider.findByDynamicSelect(selectInbox, null);
			if (notificationInbox.length > 0){
				String ids = "";
				for (Inbox element : notificationInbox){
					ids += element.getId() + ",";
				}
				if (ids.length() > 0){
					ids = ids.substring(0, ids.lastIndexOf(","));
					updateSet.append(" WHERE ID IN(");
					updateSet.append(ids);
					updateSet.append(")");
					String updateInbox = updateSet.toString();
					logger.debug("Updating INBOX: " + updateInbox);
					inboxProvider.executeUpdate(updateInbox);
				}
			} else{
				logger.debug("No notification to set IS_READ=0");
			}
		} catch (InboxDaoException ex){
			logger.error("Unble to set IS_READ for all notification in INBOX." + ex.getMessage());
		}
	}

	/**
	 * For deleting records based no non-key attribute. MySQL 5.0 safe mode update do not allow 
	 * mass updates and delete ie. queries with non-key column in where clause.
	 * @param WHERE
	 */
	public void deleteInbox(final String WHERE) {
		String selectInbox = "SELECT * FROM INBOX WHERE " + WHERE;
		StringBuilder updateSet = new StringBuilder("DELETE FROM INBOX WHERE ID IN(");
		try{
			InboxDao inboxProvider = InboxDaoFactory.create();
			Inbox[] notificationInbox = inboxProvider.findByDynamicSelect(selectInbox, null);
			if (notificationInbox.length > 0){
				String ids = "";
				for (Inbox element : notificationInbox){
					ids += element.getId() + ",";
				}
				if (ids.length() > 0){
					ids = ids.substring(0, ids.lastIndexOf(","));
					updateSet.append(ids);
					updateSet.append(")");
					String deleteInbox = updateSet.toString();
					logger.debug("Updating INBOX: " + deleteInbox);
					inboxProvider.executeUpdate(deleteInbox);
				}
			} else{
				logger.debug("No candidate request notification to delete in INBOX.");
			}
		} catch (InboxDaoException ex){
			logger.error("Unble to delete candidate request notification in INBOX." + ex.getMessage());
		}
	}
}
