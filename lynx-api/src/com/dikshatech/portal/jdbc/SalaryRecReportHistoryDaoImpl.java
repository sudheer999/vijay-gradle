/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.portal.dao.SalaryRecReportHistoryDao;
import com.dikshatech.portal.dto.SalaryRecReportHistory;
import com.dikshatech.portal.dto.SalaryRecReportHistoryPk;
import com.dikshatech.portal.exceptions.SalaryRecReportHistoryDaoException;

public class SalaryRecReportHistoryDaoImpl extends AbstractDAO implements SalaryRecReportHistoryDao {

	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
	takes no arguments and one that takes a Connection argument. If the Connection version
	is chosen then the connection will be stored in this attribute and will be used by all
	calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;
	protected static final Logger	logger				= Logger.getLogger(SalaryRecReportHistoryDaoImpl.class);
	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String			SQL_SELECT			= "SELECT ID, SRR_ID, SALARY, PAYABLE_DAYS, TDS, MODIFIED_BY, MODIFIED_ON, COMMENTS FROM " + getTableName() + "";
	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT			= "INSERT INTO " + getTableName() + " ( ID, SRR_ID, SALARY, PAYABLE_DAYS, TDS, MODIFIED_BY, MODIFIED_ON, COMMENTS ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";
	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String			SQL_UPDATE			= "UPDATE " + getTableName() + " SET ID = ?, SRR_ID = ?, SALARY = ?, PAYABLE_DAYS = ?, TDS = ?, MODIFIED_BY = ?, MODIFIED_ON = ?, COMMENTS = ? WHERE ID = ?";
	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String			SQL_DELETE			= "DELETE FROM " + getTableName() + " WHERE ID = ?";
	/** 
	 * Index of column ID
	 */
	protected static final int		COLUMN_ID			= 1;
	/** 
	 * Index of column SRR_ID
	 */
	protected static final int		COLUMN_SRR_ID		= 2;
	/** 
	 * Index of column SALARY
	 */
	protected static final int		COLUMN_SALARY		= 3;
	/** 
	 * Index of column PAYABLE_DAYS
	 */
	protected static final int		COLUMN_PAYABLE_DAYS	= 4;
	/** 
	 * Index of column TDS
	 */
	protected static final int		COLUMN_TDS			= 5;
	/** 
	 * Index of column MODIFIED_BY
	 */
	protected static final int		COLUMN_MODIFIED_BY	= 6;
	/** 
	 * Index of column MODIFIED_ON
	 */
	protected static final int		COLUMN_MODIFIED_ON	= 7;
	/** 
	 * Index of column COMMENTS
	 */
	protected static final int		COLUMN_COMMENTS		= 8;
	/** 
	 * Number of columns
	 */
	protected static final int		NUMBER_OF_COLUMNS	= 8;
	/** 
	 * Index of primary-key column ID
	 */
	protected static final int		PK_COLUMN_ID		= 1;

	/** 
	 * Inserts a new row in the salary_rec_report_history table.
	 */
	public SalaryRecReportHistoryPk insert(SalaryRecReportHistory dto) throws SalaryRecReportHistoryDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			stmt.setInt(index++, dto.getId());
			stmt.setDouble(index++, dto.getSrrId());
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getSalary()));
			stmt.setDouble(index++, dto.getPayableDays());
			stmt.setString(index++, dto.getTds());
			stmt.setString(index++, dto.getModifiedBy());
			stmt.setTimestamp(index++, dto.getModifiedOn() == null ? null : new java.sql.Timestamp(dto.getModifiedOn().getTime()));
			stmt.setString(index++, dto.getComments());
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_INSERT + " with DTO: " + dto);
			}
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
			// retrieve values from auto-increment columns
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()){
				dto.setId(rs.getInt(1));
			}
			reset(dto);
			return dto.createPk();
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new SalaryRecReportHistoryDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/** 
	 * Updates a single row in the salary_rec_report_history table.
	 */
	public void update(SalaryRecReportHistoryPk pk, SalaryRecReportHistory dto) throws SalaryRecReportHistoryDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_UPDATE + " with DTO: " + dto);
			}
			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			stmt.setDouble(index++, dto.getId());
			stmt.setDouble(index++, dto.getSrrId());
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getSalary()));
			stmt.setDouble(index++, dto.getPayableDays());
			stmt.setString(index++, dto.getTds());
			stmt.setString(index++, dto.getModifiedBy());
			stmt.setTimestamp(index++, dto.getModifiedOn() == null ? null : new java.sql.Timestamp(dto.getModifiedOn().getTime()));
			stmt.setString(index++, dto.getComments());
			stmt.setDouble(9, pk.getId());
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new SalaryRecReportHistoryDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/** 
	 * Deletes a single row in the salary_rec_report_history table.
	 */
	public void delete(SalaryRecReportHistoryPk pk) throws SalaryRecReportHistoryDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_DELETE + " with PK: " + pk);
			}
			stmt = conn.prepareStatement(SQL_DELETE);
			stmt.setDouble(1, pk.getId());
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new SalaryRecReportHistoryDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/** 
	 * Returns the rows from the salary_rec_report_history table that matches the specified primary-key value.
	 */
	public SalaryRecReportHistory findByPrimaryKey(SalaryRecReportHistoryPk pk) throws SalaryRecReportHistoryDaoException {
		return findByPrimaryKey(pk.getId());
	}

	/** 
	 * Returns all rows from the salary_rec_report_history table that match the criteria 'ID = :id'.
	 */
	public SalaryRecReportHistory findByPrimaryKey(long id) throws SalaryRecReportHistoryDaoException {
		SalaryRecReportHistory ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { new Double(id) });
		return ret.length == 0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the salary_rec_report_history table that match the criteria 'SRR_ID = :srrId'.
	 */
	public SalaryRecReportHistory[] findBySalaryReconciliationReport(long srrId) throws SalaryRecReportHistoryDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE SRR_ID = ?", new Object[] { srrId });
	}

	/** 
	 * Returns all rows from the salary_rec_report_history table that match the criteria 'SRR_ID = :srrId'.
	 */
	public SalaryRecReportHistory[] findWhereSrrIdEquals(long srrId) throws SalaryRecReportHistoryDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE SRR_ID = ? ORDER BY SRR_ID", new Object[] { srrId });
	}

	/**
	 * Method 'SalaryRecReportHistoryDaoImpl'
	 * 
	 */
	public SalaryRecReportHistoryDaoImpl() {}

	/**
	 * Method 'SalaryRecReportHistoryDaoImpl'
	 * 
	 * @param userConn
	 */
	public SalaryRecReportHistoryDaoImpl(final java.sql.Connection userConn) {
		this.userConn = userConn;
	}

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "SALARY_REC_REPORT_HISTORY";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected SalaryRecReportHistory fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			SalaryRecReportHistory dto = new SalaryRecReportHistory();
			populateDto(dto, rs);
			return dto;
		} else{
			return null;
		}
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected SalaryRecReportHistory[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<SalaryRecReportHistory> resultList = new ArrayList<SalaryRecReportHistory>();
		while (rs.next()){
			SalaryRecReportHistory dto = new SalaryRecReportHistory();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		SalaryRecReportHistory ret[] = new SalaryRecReportHistory[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(SalaryRecReportHistory dto, ResultSet rs) throws SQLException {
		dto.setId(rs.getInt(COLUMN_ID));
		dto.setSrrId(rs.getInt(COLUMN_SRR_ID));
		try{
			dto.setSalary(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(COLUMN_SALARY)));
		} catch (Exception e){
			logger.error("Unable to de-crypt the salary from report history", e);
		}
		dto.setPayableDays(rs.getDouble(COLUMN_PAYABLE_DAYS));
		dto.setTds(rs.getString(COLUMN_TDS));
		dto.setModifiedBy(rs.getString(COLUMN_MODIFIED_BY));
		dto.setModifiedOn(rs.getTimestamp(COLUMN_MODIFIED_ON));
		dto.setComments(rs.getString(COLUMN_COMMENTS));
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(SalaryRecReportHistory dto) {}

	/** 
	 * Returns all rows from the salary_rec_report_history table that match the specified arbitrary SQL statement
	 */
	public SalaryRecReportHistory[] findByDynamicSelect(String sql, Object[] sqlParams) throws SalaryRecReportHistoryDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			// construct the SQL statement
			final String SQL = sql;
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL);
			}
			// prepare statement
			stmt = conn.prepareStatement(SQL);
			stmt.setMaxRows(maxRows);
			// bind parameters
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++){
				stmt.setObject(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();
			// fetch the results
			return fetchMultiResults(rs);
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new SalaryRecReportHistoryDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/** 
	 * Returns all rows from the salary_rec_report_history table that match the specified arbitrary SQL statement
	 */
	public SalaryRecReportHistory[] findByDynamicWhere(String sql, Object[] sqlParams) throws SalaryRecReportHistoryDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			// construct the SQL statement
			final String SQL = SQL_SELECT + " WHERE " + sql;
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL);
			}
			// prepare statement
			stmt = conn.prepareStatement(SQL);
			stmt.setMaxRows(maxRows);
			// bind parameters
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++){
				stmt.setObject(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();
			// fetch the results
			return fetchMultiResults(rs);
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new SalaryRecReportHistoryDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
}
