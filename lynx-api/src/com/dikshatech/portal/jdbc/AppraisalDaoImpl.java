/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
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
import java.util.Date;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.AppraisalDao;
import com.dikshatech.portal.dto.Appraisal;
import com.dikshatech.portal.dto.AppraisalPk;
import com.dikshatech.portal.exceptions.AppraisalDaoException;

public class AppraisalDaoImpl extends AbstractDAO implements AppraisalDao {

	/**
	 * The factory class for this DAO has two versions of the create() method - one that
	 * takes no arguments and one that takes a Connection argument. If the Connection version
	 * is chosen then the connection will be stored in this attribute and will be used by all
	 * calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;
	protected static final Logger	logger					= Logger.getLogger(AppraisalDaoImpl.class);
	/**
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String			SQL_SELECT				= "SELECT ID, USER_ID, APPRAISER, HANDLER, TYPE, PERIOD, STATUS, DATETIME, YEAR, ESR_MAP_ID, APPRAISEEDUEDATE, APPRAISERDUEDATE, START_DATE, VERSION FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT				= "INSERT INTO " + getTableName() + " ( ID, USER_ID, APPRAISER, HANDLER, TYPE, PERIOD, STATUS, DATETIME, YEAR, ESR_MAP_ID, APPRAISEEDUEDATE, APPRAISERDUEDATE, START_DATE, VERSION ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String			SQL_UPDATE				= "UPDATE " + getTableName() + " SET ID = ?, USER_ID = ?, APPRAISER = ?, HANDLER = ?, TYPE = ?, PERIOD = ?, STATUS = ?, DATETIME = ?, YEAR = ?, ESR_MAP_ID = ?, APPRAISEEDUEDATE = ?, APPRAISERDUEDATE = ?, START_DATE = ?, VERSION=? WHERE ID = ?";
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
	 * Index of column APPRAISER
	 */
	protected static final int		COLUMN_APPRAISER		= 3;
	/**
	 * Index of column HANDLER
	 */
	protected static final int		COLUMN_HANDLER			= 4;
	/**
	 * Index of column TYPE
	 */
	protected static final int		COLUMN_TYPE				= 5;
	/**
	 * Index of column PERIOD
	 */
	protected static final int		COLUMN_PERIOD			= 6;
	/**
	 * Index of column STATUS
	 */
	protected static final int		COLUMN_STATUS			= 7;
	/**
	 * Index of column DATETIME
	 */
	protected static final int		COLUMN_DATETIME			= 8;
	/**
	 * Index of column YEAR
	 */
	protected static final int		COLUMN_YEAR				= 9;
	protected static final int		COLUMN_ESR_MAP_ID		= 10;
	protected static final int		COLUMN_APPRAISEEDUEDATE	= 11;
	protected static final int		COLUMN_APPRAISERDUEDATE	= 12;
	protected static final int		COLUMN_START_DATE		= 13;
	protected static final int		COLUMN_VERSION			= 14;
	/**
	 * Number of columns
	 */
	protected static final int		NUMBER_OF_COLUMNS		= 14;
	/**
	 * Index of primary-key column ID
	 */
	protected static final int		PK_COLUMN_ID			= 1;

	/**
	 * Inserts a new row in the APPRAISAL table.
	 */
	public AppraisalPk insert(Appraisal dto) throws AppraisalDaoException {
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
			stmt.setInt(index++, dto.getUserId());
			stmt.setInt(index++, dto.getAppraiser());
			stmt.setInt(index++, dto.getHandler());
			stmt.setString(index++, dto.getType());
			stmt.setString(index++, dto.getPeriod());
			stmt.setString(index++, dto.getStatus());
			stmt.setTimestamp(index++, dto.getDatetime() == null ? null : new java.sql.Timestamp(dto.getDatetime().getTime()));
			stmt.setString(index++, dto.getYear());
			stmt.setInt(index++, dto.getEsr_map_id());
			stmt.setDate(index++, dto.getAppraiseeDueDate() == null ? null : new java.sql.Date(dto.getAppraiseeDueDate().getTime()));
			stmt.setDate(index++, dto.getAppraiserDueDate() == null ? null : new java.sql.Date(dto.getAppraiserDueDate().getTime()));
			stmt.setDate(index++, dto.getStartDate() == null ? null : new java.sql.Date(dto.getStartDate().getTime()));
			stmt.setInt(index++, dto.getVersion());
			if (logger.isDebugEnabled()) logger.debug("Executing " + SQL_INSERT + " with DTO: " + dto);
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			// retrieve values from auto-increment columns
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()){
				dto.setId(rs.getInt(1));
			}
			reset(dto);
			return dto.createPk();
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new AppraisalDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Updates a single row in the APPRAISAL table.
	 */
	public void update(AppraisalPk pk, Appraisal dto) throws AppraisalDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			if (logger.isDebugEnabled()) logger.debug("Executing " + SQL_UPDATE + " with DTO: " + dto);
			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			stmt.setInt(index++, dto.getId());
			stmt.setInt(index++, dto.getUserId());
			stmt.setInt(index++, dto.getAppraiser());
			stmt.setInt(index++, dto.getHandler());
			stmt.setString(index++, dto.getType());
			stmt.setString(index++, dto.getPeriod());
			stmt.setString(index++, dto.getStatus());
			stmt.setTimestamp(index++, dto.getDatetime() == null ? null : new java.sql.Timestamp(dto.getDatetime().getTime()));
			stmt.setString(index++, dto.getYear());
			stmt.setInt(index++, dto.getEsr_map_id());
			stmt.setDate(index++, dto.getAppraiseeDueDate() == null ? null : new java.sql.Date(dto.getAppraiseeDueDate().getTime()));
			stmt.setDate(index++, dto.getAppraiserDueDate() == null ? null : new java.sql.Date(dto.getAppraiserDueDate().getTime()));
			stmt.setDate(index++, dto.getStartDate() == null ? null : new java.sql.Date(dto.getStartDate().getTime()));
			stmt.setInt(index++, dto.getVersion());
			stmt.setInt(index++, pk.getId());
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new AppraisalDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Deletes a single row in the APPRAISAL table.
	 */
	public void delete(AppraisalPk pk) throws AppraisalDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			if (logger.isDebugEnabled()) logger.debug("Executing " + SQL_DELETE + " with PK: " + pk);
			stmt = conn.prepareStatement(SQL_DELETE);
			stmt.setInt(1, pk.getId());
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new AppraisalDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Returns the rows from the APPRAISAL table that matches the specified primary-key value.
	 */
	public Appraisal findByPrimaryKey(AppraisalPk pk) throws AppraisalDaoException {
		return findByPrimaryKey(pk.getId());
	}

	/**
	 * Returns all rows from the APPRAISAL table that match the criteria 'ID = :id'.
	 */
	public Appraisal findByPrimaryKey(int id) throws AppraisalDaoException {
		Appraisal ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { new Integer(id) });
		return ret.length == 0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the APPRAISAL table that match the criteria ''.
	 */
	public Appraisal[] findAll() throws AppraisalDaoException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY ID", null);
	}

	/**
	 * Returns all rows from the APPRAISAL table that match the criteria 'ID = :id'.
	 */
	public Appraisal[] findWhereIdEquals(int id) throws AppraisalDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] { new Integer(id) });
	}

	/**
	 * Returns all rows from the APPRAISAL table that match the criteria 'USER_ID = :userId'.
	 */
	public Appraisal[] findWhereUserIdEquals(int userId) throws AppraisalDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE USER_ID = ? ORDER BY USER_ID", new Object[] { new Integer(userId) });
	}

	/**
	 * Returns all rows from the APPRAISAL table that match the criteria 'APPRAISER = :appraiser'.
	 */
	public Appraisal[] findWhereAppraiserEquals(int appraiser) throws AppraisalDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE APPRAISER = ? ORDER BY APPRAISER", new Object[] { new Integer(appraiser) });
	}

	/**
	 * Returns all rows from the APPRAISAL table that match the criteria 'HANDLER = :handler'.
	 */
	public Appraisal[] findWhereHandlerEquals(int handler) throws AppraisalDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE HANDLER = ? ORDER BY HANDLER", new Object[] { new Integer(handler) });
	}

	/**
	 * Returns all rows from the APPRAISAL table that match the criteria 'TYPE = :type'.
	 */
	public Appraisal[] findWhereTypeEquals(String type) throws AppraisalDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE TYPE = ? ORDER BY TYPE", new Object[] { type });
	}

	/**
	 * Returns all rows from the APPRAISAL table that match the criteria 'PERIOD = :period'.
	 */
	public Appraisal[] findWherePeriodEquals(String period) throws AppraisalDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE PERIOD = ? ORDER BY PERIOD", new Object[] { period });
	}

	/**
	 * Returns all rows from the APPRAISAL table that match the criteria 'STATUS = :status'.
	 */
	public Appraisal[] findWhereStatusEquals(String status) throws AppraisalDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE STATUS = ? ORDER BY STATUS", new Object[] { status });
	}

	/**
	 * Returns all rows from the APPRAISAL table that match the criteria 'DATETIME = :datetime'.
	 */
	public Appraisal[] findWhereDatetimeEquals(Date datetime) throws AppraisalDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE DATETIME = ? ORDER BY DATETIME", new Object[] { datetime == null ? null : new java.sql.Timestamp(datetime.getTime()) });
	}

	/**
	 * Method 'AppraisalDaoImpl'
	 */
	public AppraisalDaoImpl() {}

	/**
	 * Method 'AppraisalDaoImpl'
	 * 
	 * @param userConn
	 */
	public AppraisalDaoImpl(final java.sql.Connection userConn) {
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
		return "APPRAISAL";
	}

	/**
	 * Fetches a single row from the result set
	 */
	protected Appraisal fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			Appraisal dto = new Appraisal();
			populateDto(dto, rs);
			return dto;
		}
		return null;
	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected Appraisal[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<Appraisal> resultList = new ArrayList<Appraisal>();
		while (rs.next()){
			Appraisal dto = new Appraisal();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		Appraisal ret[] = new Appraisal[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(Appraisal dto, ResultSet rs) throws SQLException {
		dto.setId(rs.getInt(COLUMN_ID));
		dto.setUserId(rs.getInt(COLUMN_USER_ID));
		dto.setAppraiser(rs.getInt(COLUMN_APPRAISER));
		dto.setHandler(rs.getInt(COLUMN_HANDLER));
		dto.setType(rs.getString(COLUMN_TYPE));
		dto.setPeriod(rs.getString(COLUMN_PERIOD));
		dto.setStatus(rs.getString(COLUMN_STATUS));
		dto.setDatetime(rs.getTimestamp(COLUMN_DATETIME));
		dto.setYear(rs.getString(COLUMN_YEAR));
		dto.setEsr_map_id(rs.getInt(COLUMN_ESR_MAP_ID));
		dto.setAppraiseeDueDate(rs.getDate(COLUMN_APPRAISEEDUEDATE));
		dto.setAppraiserDueDate(rs.getDate(COLUMN_APPRAISERDUEDATE));
		dto.setStartDate(rs.getDate(COLUMN_START_DATE));
		dto.setVersion(rs.getInt(COLUMN_VERSION));
	}

	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(Appraisal dto) {}

	/**
	 * Returns all rows from the APPRAISAL table that match the specified arbitrary SQL statement
	 */
	public Appraisal[] findByDynamicSelect(String sql, Object[] sqlParams) throws AppraisalDaoException {
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
			if (logger.isDebugEnabled()) logger.debug("Executing " + SQL);
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
			throw new AppraisalDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Returns all rows from the APPRAISAL table that match the specified arbitrary SQL statement
	 */
	public Appraisal[] findByDynamicWhere(String sql, Object[] sqlParams) throws AppraisalDaoException {
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
			if (logger.isDebugEnabled()) logger.debug("Executing " + SQL);
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
			throw new AppraisalDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
}
