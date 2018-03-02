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
import com.dikshatech.portal.dao.ExitEmployeeDao;
import com.dikshatech.portal.dto.ExitEmployee;
import com.dikshatech.portal.dto.ExitEmployeePk;
import com.dikshatech.portal.exceptions.ExitEmployeeDaoException;

public class ExitEmployeeDaoImpl extends AbstractDAO implements ExitEmployeeDao {

	protected static final Logger	logger				= Logger.getLogger(ExitEmployeeDaoImpl.class);
	/**
	 * The factory class for this DAO has two versions of the create() method - one that
	 * takes no arguments and one that takes a Connection argument. If the Connection version
	 * is chosen then the connection will be stored in this attribute and will be used by all
	 * calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;
	/**
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String			SQL_SELECT				= "SELECT ID, ESR_MAP_ID, USER_ID, REASON, STATUS_ID, SUBMITTEDON, LAST_WORKING_DAY, BUY_BACK, COMMENTS, EMPLOYEE_NOTE, NOTICE_PERIOD FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT				= "INSERT INTO " + getTableName() + " ( ID, ESR_MAP_ID, USER_ID, REASON, STATUS_ID, SUBMITTEDON, LAST_WORKING_DAY, BUY_BACK, COMMENTS, EMPLOYEE_NOTE, NOTICE_PERIOD ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String			SQL_UPDATE				= "UPDATE " + getTableName() + " SET ID = ?, ESR_MAP_ID = ?, USER_ID = ?, REASON = ?, STATUS_ID = ?, SUBMITTEDON = ?, LAST_WORKING_DAY = ?, BUY_BACK = ?, COMMENTS = ?, EMPLOYEE_NOTE = ?, NOTICE_PERIOD = ? WHERE ID = ?";
	/**
	 * SQL DELETE statement for this table
	 */
	protected final String			SQL_DELETE				= "DELETE FROM " + getTableName() + " WHERE ID = ?";
	/**
	 * Index of column ID
	 */
	protected static final int		COLUMN_ID				= 1;
	/**
	 * Index of column ESR_MAP_ID
	 */
	protected static final int		COLUMN_ESR_MAP_ID		= 2;
	/**
	 * Index of column USER_ID
	 */
	protected static final int		COLUMN_USER_ID			= 3;
	/**
	 * Index of column REASON
	 */
	protected static final int		COLUMN_REASON			= 4;
	/**
	 * Index of column STATUS_ID
	 */
	protected static final int		COLUMN_STATUS_ID		= 5;
	/**
	 * Index of column SUBMITTEDON
	 */
	protected static final int		COLUMN_SUBMITTEDON		= 6;
	/**
	 * Index of column LAST_WORKING_DAY
	 */
	protected static final int		COLUMN_LAST_WORKING_DAY	= 7;
	/**
	 * Index of column BUY_BACK
	 */
	protected static final int		COLUMN_BUY_BACK			= 8;
	/**
	 * Index of column COMMENTS
	 */
	protected static final int		COLUMN_COMMENTS			= 9;
	/**
	 * Index of column EMPLOYEE_NOTE
	 */
	protected static final int		COLUMN_EMPLOYEE_NOTE	= 10;
	protected static final int		COLUMN_NOTICE_PERIOD	= 11;
	/**
	 * Number of columns
	 */
	protected static final int		NUMBER_OF_COLUMNS		= 11;
	/**
	 * Index of primary-key column ID
	 */
	protected static final int		PK_COLUMN_ID			= 1;

	/**
	 * Inserts a new row in the EXIT_EMPLOYEE table.
	 */
	public ExitEmployeePk insert(ExitEmployee dto) throws ExitEmployeeDaoException {
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
			stmt.setInt(index++, dto.getEsrMapId());
			stmt.setInt(index++, dto.getUserId());
			stmt.setString(index++, dto.getReason());
			stmt.setInt(index++, dto.getStatusId());
			stmt.setTimestamp(index++, dto.getSubmittedon() == null ? null : new java.sql.Timestamp(dto.getSubmittedon().getTime()));
			stmt.setDate(index++, dto.getLastWorkingDay() == null ? null : new java.sql.Date(dto.getLastWorkingDay().getTime()));
			stmt.setInt(index++, dto.getBuyBack());
			stmt.setString(index++, dto.getComments());
			stmt.setString(index++, dto.getEmployeeNote());
			stmt.setInt(index++, dto.getNoticePeriod());
			logger.debug("Executing " + SQL_INSERT + " with DTO: " + dto);
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			// retrieve values from auto-increment columns
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()){
				dto.setId(rs.getInt(1));
			}
			reset(dto);
			return dto.createPk();
		} catch (Exception _e){
			_e.printStackTrace();
			throw new ExitEmployeeDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Updates a single row in the EXIT_EMPLOYEE table.
	 */
	public void update(ExitEmployeePk pk, ExitEmployee dto) throws ExitEmployeeDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			logger.debug("Executing " + SQL_UPDATE + " with DTO: " + dto);
			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			stmt.setInt(index++, dto.getId());
			stmt.setInt(index++, dto.getEsrMapId());
			stmt.setInt(index++, dto.getUserId());
			stmt.setString(index++, dto.getReason());
			stmt.setInt(index++, dto.getStatusId());
			stmt.setTimestamp(index++, dto.getSubmittedon() == null ? null : new java.sql.Timestamp(dto.getSubmittedon().getTime()));
			stmt.setDate(index++, dto.getLastWorkingDay() == null ? null : new java.sql.Date(dto.getLastWorkingDay().getTime()));
			stmt.setInt(index++, dto.getBuyBack());
			stmt.setString(index++, dto.getComments());
			stmt.setString(index++, dto.getEmployeeNote());
			stmt.setInt(index++, dto.getNoticePeriod());
			stmt.setInt(12, pk.getId());
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
		} catch (Exception _e){
			_e.printStackTrace();
			throw new ExitEmployeeDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Deletes a single row in the EXIT_EMPLOYEE table.
	 */
	public void delete(ExitEmployeePk pk) throws ExitEmployeeDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			logger.debug("Executing " + SQL_DELETE + " with PK: " + pk);
			stmt = conn.prepareStatement(SQL_DELETE);
			stmt.setInt(1, pk.getId());
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
		} catch (Exception _e){
			_e.printStackTrace();
			throw new ExitEmployeeDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Returns the rows from the EXIT_EMPLOYEE table that matches the specified primary-key value.
	 */
	public ExitEmployee findByPrimaryKey(ExitEmployeePk pk) throws ExitEmployeeDaoException {
		return findByPrimaryKey(pk.getId());
	}

	/**
	 * Returns all rows from the EXIT_EMPLOYEE table that match the criteria 'ID = :id'.
	 */
	public ExitEmployee findByPrimaryKey(int id) throws ExitEmployeeDaoException {
		ExitEmployee ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { new Integer(id) });
		return ret.length == 0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the EXIT_EMPLOYEE table that match the criteria ''.
	 */
	public ExitEmployee[] findAll() throws ExitEmployeeDaoException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY ID", null);
	}

	/**
	 * Returns all rows from the EXIT_EMPLOYEE table that match the criteria 'ID = :id'.
	 */
	public ExitEmployee[] findWhereIdEquals(Integer id) throws ExitEmployeeDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] { id });
	}

	/**
	 * Returns all rows from the EXIT_EMPLOYEE table that match the criteria 'ESR_MAP_ID = :esrMapId'.
	 */
	public ExitEmployee[] findWhereEsrMapIdEquals(Integer esrMapId) throws ExitEmployeeDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE ESR_MAP_ID = ? ORDER BY ESR_MAP_ID", new Object[] { esrMapId });
	}

	/**
	 * Returns all rows from the EXIT_EMPLOYEE table that match the criteria 'USER_ID = :userId'.
	 */
	public ExitEmployee[] findWhereUserIdEquals(Integer userId) throws ExitEmployeeDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE USER_ID = ? ORDER BY USER_ID", new Object[] { userId });
	}

	/**
	 * Returns all rows from the EXIT_EMPLOYEE table that match the criteria 'REASON = :reason'.
	 */
	public ExitEmployee[] findWhereReasonEquals(String reason) throws ExitEmployeeDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE REASON = ? ORDER BY REASON", new Object[] { reason });
	}

	/**
	 * Returns all rows from the EXIT_EMPLOYEE table that match the criteria 'STATUS_ID = :statusId'.
	 */
	public ExitEmployee[] findWhereStatusIdEquals(Integer statusId) throws ExitEmployeeDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE STATUS_ID = ? ORDER BY STATUS_ID", new Object[] { statusId });
	}

	/**
	 * Returns all rows from the EXIT_EMPLOYEE table that match the criteria 'SUBMITTEDON = :submittedon'.
	 */
	public ExitEmployee[] findWhereSubmittedonEquals(Date submittedon) throws ExitEmployeeDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE SUBMITTEDON = ? ORDER BY SUBMITTEDON", new Object[] { submittedon == null ? null : new java.sql.Timestamp(submittedon.getTime()) });
	}

	/**
	 * Returns all rows from the EXIT_EMPLOYEE table that match the criteria 'LAST_WORKING_DAY = :lastWorkingDay'.
	 */
	public ExitEmployee[] findWhereLastWorkingDayEquals(Date lastWorkingDay) throws ExitEmployeeDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE LAST_WORKING_DAY = ? ORDER BY LAST_WORKING_DAY", new Object[] { lastWorkingDay == null ? null : new java.sql.Date(lastWorkingDay.getTime()) });
	}

	/**
	 * Returns all rows from the EXIT_EMPLOYEE table that match the criteria 'BUY_BACK = :buyBack'.
	 */
	public ExitEmployee[] findWhereBuyBackEquals(Integer buyBack) throws ExitEmployeeDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE BUY_BACK = ? ORDER BY BUY_BACK", new Object[] { buyBack });
	}

	/**
	 * Returns all rows from the EXIT_EMPLOYEE table that match the criteria 'COMMENTS = :comments'.
	 */
	public ExitEmployee[] findWhereCommentsEquals(String comments) throws ExitEmployeeDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE COMMENTS = ? ORDER BY COMMENTS", new Object[] { comments });
	}

	/**
	 * Returns all rows from the EXIT_EMPLOYEE table that match the criteria 'EMPLOYEE_NOTE = :employeeNote'.
	 */
	public ExitEmployee[] findWhereEmployeeNoteEquals(String employeeNote) throws ExitEmployeeDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE EMPLOYEE_NOTE = ? ORDER BY EMPLOYEE_NOTE", new Object[] { employeeNote });
	}

	/**
	 * Method 'ExitEmployeeDaoImpl'
	 */
	public ExitEmployeeDaoImpl() {}

	/**
	 * Method 'ExitEmployeeDaoImpl'
	 * 
	 * @param userConn
	 */
	public ExitEmployeeDaoImpl(final java.sql.Connection userConn) {
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
		return "EXIT_EMPLOYEE";
	}

	/**
	 * Fetches a single row from the result set
	 */
	protected ExitEmployee fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			ExitEmployee dto = new ExitEmployee();
			populateDto(dto, rs);
			return dto;
		}
		return null;
	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected ExitEmployee[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<ExitEmployee> resultList = new ArrayList<ExitEmployee>();
		while (rs.next()){
			ExitEmployee dto = new ExitEmployee();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		ExitEmployee ret[] = new ExitEmployee[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}
	
	protected ExitEmployee[] fetchMultiResultExitEmployee(ResultSet rs) throws SQLException {
		Collection<ExitEmployee> resultList = new ArrayList<ExitEmployee>();
		while (rs.next()){
			ExitEmployee dto = new ExitEmployee();
			populateDtoExitEmployee(dto, rs);
			resultList.add(dto);
		}
		ExitEmployee ret[] = new ExitEmployee[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}
	

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(ExitEmployee dto, ResultSet rs) throws SQLException {
		dto.setId(rs.getInt(COLUMN_ID));
		dto.setEsrMapId(rs.getInt(COLUMN_ESR_MAP_ID));
		dto.setUserId(rs.getInt(COLUMN_USER_ID));
		dto.setReason(rs.getString(COLUMN_REASON));
		dto.setStatusId(rs.getInt(COLUMN_STATUS_ID));
		dto.setSubmittedon(rs.getTimestamp(COLUMN_SUBMITTEDON));
		dto.setLastWorkingDay(rs.getDate(COLUMN_LAST_WORKING_DAY));
		dto.setBuyBack(rs.getInt(COLUMN_BUY_BACK));
		dto.setComments(rs.getString(COLUMN_COMMENTS));
		dto.setEmployeeNote(rs.getString(COLUMN_EMPLOYEE_NOTE));
		dto.setNoticePeriod(rs.getInt(COLUMN_NOTICE_PERIOD));
	}
	protected void populateDtoExitEmployee(ExitEmployee dto, ResultSet rs) throws SQLException {
		dto.setId(rs.getInt(COLUMN_ID));
	//	dto.setEsrMapId(rs.getInt(COLUMN_ESR_MAP_ID));
	//	dto.setUserId(rs.getInt(COLUMN_USER_ID));
		/*	dto.setReason(rs.getString(COLUMN_REASON));
		dto.setStatusId(rs.getInt(COLUMN_STATUS_ID));
		dto.setSubmittedon(rs.getTimestamp(COLUMN_SUBMITTEDON));
		dto.setLastWorkingDay(rs.getDate(COLUMN_LAST_WORKING_DAY));
		dto.setBuyBack(rs.getInt(COLUMN_BUY_BACK));
		dto.setComments(rs.getString(COLUMN_COMMENTS));
		dto.setEmployeeNote(rs.getString(COLUMN_EMPLOYEE_NOTE));
		dto.setNoticePeriod(rs.getInt(COLUMN_NOTICE_PERIOD));*/
	}
	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(ExitEmployee dto) {}

	/**
	 * Returns all rows from the EXIT_EMPLOYEE table that match the specified arbitrary SQL statement
	 */
	public ExitEmployee[] findByDynamicSelect(String sql, Object[] sqlParams) throws ExitEmployeeDaoException {
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
			logger.debug("Executing " + SQL);
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
			_e.printStackTrace();
			throw new ExitEmployeeDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
	
	public ExitEmployee[] findByDynamicSelectExitEmployee(String sql, Object[] sqlParams) throws ExitEmployeeDaoException {
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
			logger.debug("Executing " + SQL);
			// prepare statement
			stmt = conn.prepareStatement(SQL);
			stmt.setMaxRows(maxRows);
			// bind parameters
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++){
				stmt.setObject(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();
			// fetch the results
			return fetchMultiResultExitEmployee(rs);
		} catch (Exception _e){
			_e.printStackTrace();
			throw new ExitEmployeeDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Returns all rows from the EXIT_EMPLOYEE table that match the specified arbitrary SQL statement
	 */
	public ExitEmployee[] findByDynamicWhere(String sql, Object[] sqlParams) throws ExitEmployeeDaoException {
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
			logger.debug("Executing " + SQL);
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
			_e.printStackTrace();
			throw new ExitEmployeeDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
}