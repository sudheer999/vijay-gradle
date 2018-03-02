package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.apache.log4j.Logger;
import com.dikshatech.portal.dao.RetentionBonusDao;
import com.dikshatech.portal.dto.RetentionBonus;
import com.dikshatech.portal.dto.RetentionBonusPk;
import com.dikshatech.portal.exceptions.RetentionBonusDaoException;


public class RetentionBonusDaoImpl extends AbstractDAO implements RetentionBonusDao {


	protected java.sql.Connection	userConn;
	protected static final Logger	logger					= Logger.getLogger(RetentionBonusDaoImpl.class);
	/**
	 * All finder methods in this class use this SELECT constant to build their
	 * queries
	 */
	protected final String			SQL_SELECT				= "SELECT ID, USER_ID, AMOUNT, FROM_DATE, RETENTION_INSTALLMENTS, LAST_MODIFIED_DATE FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT				= "INSERT INTO " + getTableName() + " ( ID, USER_ID, AMOUNT, FROM_DATE, RETENTION_INSTALLMENTS, LAST_MODIFIED_DATE) VALUES ( ?, ?, ?, ?, ?, ? )";
	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String			SQL_UPDATE				= "UPDATE " + getTableName() + " SET ID = ?, USER_ID = ?, AMOUNT = ?, FROM_DATE = ?, RETENTION_INSTALLMENTS = ?, LAST_MODIFIED_DATE = ? WHERE ID = ?";
	/**
	 * SQL DELETE statement for this table
	 */
	protected final String			SQL_DELETE				= "DELETE FROM " + getTableName() + " WHERE ID = ?";
	/**
	 * Index of column ID
	 */
	protected static final int		COLUMN_ID				= 1;
	/**
	 * Index of column USER_ID
	 */
	protected static final int		COLUMN_USER_ID			= 2;
	/**
	 * Index of column PERDIEM
	 */
	protected static final int		COLUMN_AMOUNT			= 3;
	/**
	 * Index of column PERDIEM_FROM
	 */
	protected static final int		COLUMN_FROM_DATE		= 4;
	/**
	 * Index of column PERDIEM_TO
	 */
	protected static final int		COLUMN_RETENTION_INSTALLMENTS		= 5;
	/**
	 * Index of column CURRENCY_TYPE
	 */
	protected static final int		COLUMN_LAST_MODIFIED_DATE	= 6;
	/**
	 * Number of columns
	 */
	protected static final int		NUMBER_OF_COLUMNS		= 6;
	/**
	 * Index of primary-key column ID
	 */
	protected static final int		PK_COLUMN_ID			= 1;

	/**
	 * Inserts a new row in the PERDIEM_MASTER_DATA table.
	 */
	public RetentionBonusPk insert(RetentionBonus dto) throws RetentionBonusDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			stmt.setInt(index++, dto.getId());
			stmt.setInt(index++, dto.getUserId());
			stmt.setString(index++, dto.getAmount());
			stmt.setDate(index++, dto.getRetentionStartDate() == null ? null : new java.sql.Date( dto.getRetentionStartDate().getTime()));
			stmt.setInt(index++, dto.getRetentionInstallments());
			stmt.setDate(index++, dto.getLastModifiedDate() == null ? new java.sql.Date(new Date().getTime()) : new java.sql.Date(dto.getLastModifiedDate().getTime()));
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
			throw new RetentionBonusDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Updates a single row in the PERDIEM_MASTER_DATA table.
	 */
	public void update(RetentionBonusPk pk, RetentionBonus dto) throws RetentionBonusDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_UPDATE + " with DTO: " + dto);
			}
			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			stmt.setInt(index++, dto.getId());
			stmt.setInt(index++, dto.getUserId());
			stmt.setString(index++, dto.getAmount());
			stmt.setDate(index++, dto.getRetentionStartDate() == null ? null : new java.sql.Date( dto.getRetentionStartDate().getTime()));
			stmt.setInt(index++, dto.getRetentionInstallments());
			stmt.setDate(index++, dto.getLastModifiedDate() == null ? new java.sql.Date(new Date().getTime()) : new java.sql.Date(dto.getLastModifiedDate().getTime()));
			stmt.setInt(7, pk.getId());
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new RetentionBonusDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Deletes a single row in the PERDIEM_MASTER_DATA table.
	 */
	public void delete(RetentionBonusPk pk) throws RetentionBonusDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_DELETE + " with PK: " + pk);
			}
			stmt = conn.prepareStatement(SQL_DELETE);
			stmt.setInt(1, pk.getId());
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new RetentionBonusDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Returns the rows from the PERDIEM_MASTER_DATA table that matches the
	 * specified primary-key value.
	 */
	public RetentionBonus findByPrimaryKey(RetentionBonusPk pk) throws RetentionBonusDaoException {
		return findByPrimaryKey(pk.getId());
	}

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria 'ID = :id'.
	 */
	public RetentionBonus findByPrimaryKey(int id) throws RetentionBonusDaoException {
		RetentionBonus ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { new Integer(id) });
		return ret.length == 0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria ''.
	 */
	public RetentionBonus[] findAll() throws RetentionBonusDaoException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY ID", null);
	}

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria 'ID = :id'.
	 */
	public RetentionBonus[] findWhereIdEquals(int id) throws RetentionBonusDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] { new Integer(id) });
	}

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria 'USER_ID = :userId'.
	 */
	public RetentionBonus[] findWhereUserIdEquals(int userId) throws RetentionBonusDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE USER_ID = ? ORDER BY USER_ID", new Object[] { new Integer(userId) });
	}

	
	/**
	 * Method 'RetentionBonusDaoImpl'
	 * 
	 */
	public RetentionBonusDaoImpl() {}

	/**
	 * Method 'RetentionBonusDaoImpl'
	 * 
	 * @param userConn
	 */
	public RetentionBonusDaoImpl(final java.sql.Connection userConn) {
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
		return "RETENTION_BONUS";
	}

	/**
	 * Fetches a single row from the result set
	 */
	protected RetentionBonus fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			RetentionBonus dto = new RetentionBonus();
			populateDto(dto, rs);
			return dto;
		} else{
			return null;
		}
	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected RetentionBonus[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<RetentionBonus> resultList = new ArrayList<RetentionBonus>();
		while (rs.next()){
			RetentionBonus dto = new RetentionBonus();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		RetentionBonus ret[] = new RetentionBonus[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(RetentionBonus dto, ResultSet rs) throws SQLException {
		dto.setId(rs.getInt(COLUMN_ID));
		dto.setUserId(rs.getInt(COLUMN_USER_ID));
		dto.setAmount(rs.getString(COLUMN_AMOUNT));
		dto.setRetentionStartDate(rs.getDate(COLUMN_FROM_DATE));
		dto.setRetentionInstallments(rs.getInt(COLUMN_RETENTION_INSTALLMENTS));
		dto.setLastModifiedDate(rs.getDate(COLUMN_LAST_MODIFIED_DATE));
	}

	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(RetentionBonus dto) {}

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * specified arbitrary SQL statement
	 */
	public RetentionBonus[] findByDynamicSelect(String sql, Object[] sqlParams) throws RetentionBonusDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the
			// ResourceManager
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
			throw new RetentionBonusDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * specified arbitrary SQL statement
	 */
	public RetentionBonus[] findByDynamicWhere(String sql, Object[] sqlParams) throws RetentionBonusDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the
			// ResourceManager
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
			throw new RetentionBonusDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	


}
