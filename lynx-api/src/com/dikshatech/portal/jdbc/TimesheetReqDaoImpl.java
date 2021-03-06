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
import java.util.Date;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.TimesheetReqDao;
import com.dikshatech.portal.dto.TimesheetReq;
import com.dikshatech.portal.dto.TimesheetReqPk;
import com.dikshatech.portal.exceptions.TimesheetReqDaoException;

public class TimesheetReqDaoImpl extends AbstractDAO implements TimesheetReqDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( TimesheetReqDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, ESRQM_ID, COMMENTS, STATUS, ASSIGNED_TO, RAISED_BY, SUMMARY, TSHEET_ID, CREATEDATETIME, SEQUENCE, MESSAGE_BODY, ACTION_BY FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, ESRQM_ID, COMMENTS, STATUS, ASSIGNED_TO, RAISED_BY, SUMMARY, TSHEET_ID, CREATEDATETIME, SEQUENCE, MESSAGE_BODY, ACTION_BY ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, ESRQM_ID = ?, COMMENTS = ?, STATUS = ?, ASSIGNED_TO = ?, RAISED_BY = ?, SUMMARY = ?, TSHEET_ID = ?, CREATEDATETIME = ?, SEQUENCE = ?, MESSAGE_BODY = ?, ACTION_BY = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column ESRQM_ID
	 */
	protected static final int COLUMN_ESRQM_ID = 2;

	/** 
	 * Index of column COMMENTS
	 */
	protected static final int COLUMN_COMMENTS = 3;

	/** 
	 * Index of column STATUS
	 */
	protected static final int COLUMN_STATUS = 4;

	/** 
	 * Index of column ASSIGNED_TO
	 */
	protected static final int COLUMN_ASSIGNED_TO = 5;

	/** 
	 * Index of column RAISED_BY
	 */
	protected static final int COLUMN_RAISED_BY = 6;

	/** 
	 * Index of column SUMMARY
	 */
	protected static final int COLUMN_SUMMARY = 7;

	/** 
	 * Index of column TSHEET_ID
	 */
	protected static final int COLUMN_TSHEET_ID = 8;

	/** 
	 * Index of column CREATEDATETIME
	 */
	protected static final int COLUMN_CREATEDATETIME = 9;

	/** 
	 * Index of column SEQUENCE
	 */
	protected static final int COLUMN_SEQUENCE = 10;

	/** 
	 * Index of column MESSAGE_BODY
	 */
	protected static final int COLUMN_MESSAGE_BODY = 11;

	/** 
	 * Index of column ACTION_BY
	 */
	protected static final int COLUMN_ACTION_BY = 12;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 12;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the TIMESHEET_REQ table.
	 */
	public TimesheetReqPk insert(TimesheetReq dto) throws TimesheetReqDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			stmt = conn.prepareStatement( SQL_INSERT, Statement.RETURN_GENERATED_KEYS );
			int index = 1;
			stmt.setInt( index++, dto.getId() );
			stmt.setInt( index++, dto.getEsrqmId() );
			stmt.setString( index++, dto.getComments() );
			stmt.setString( index++, dto.getStatus() );
			if (dto.isAssignedToNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getAssignedTo() );
			}
		
			if (dto.isRaisedByNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getRaisedBy() );
			}
		
			stmt.setString( index++, dto.getSummary() );
			stmt.setInt( index++, dto.getTsheetId() );
			stmt.setTimestamp(index++, dto.getCreatedatetime()==null ? null : new java.sql.Timestamp( dto.getCreatedatetime().getTime() ) );
			stmt.setInt( index++, dto.getSequence() );
			stmt.setString( index++, dto.getMessageBody() );
			if (dto.isActionByNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getActionBy() );
			}
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL_INSERT + " with DTO: " + dto);
			}
		
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		
			// retrieve values from auto-increment columns
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setId( rs.getInt( 1 ) );
			}
		
			reset(dto);
			return dto.createPk();
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new TimesheetReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the TIMESHEET_REQ table.
	 */
	public void update(TimesheetReqPk pk, TimesheetReq dto) throws TimesheetReqDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL_UPDATE + " with DTO: " + dto);
			}
		
			stmt = conn.prepareStatement( SQL_UPDATE );
			int index=1;
			stmt.setInt( index++, dto.getId() );
			stmt.setInt( index++, dto.getEsrqmId() );
			stmt.setString( index++, dto.getComments() );
			stmt.setString( index++, dto.getStatus() );
			if (dto.isAssignedToNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getAssignedTo() );
			}
		
			if (dto.isRaisedByNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getRaisedBy() );
			}
		
			stmt.setString( index++, dto.getSummary() );
			stmt.setInt( index++, dto.getTsheetId() );
			stmt.setTimestamp(index++, dto.getCreatedatetime()==null ? null : new java.sql.Timestamp( dto.getCreatedatetime().getTime() ) );
			stmt.setInt( index++, dto.getSequence() );
			stmt.setString( index++, dto.getMessageBody() );
			if (dto.isActionByNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getActionBy() );
			}
		
			stmt.setInt( 13, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new TimesheetReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the TIMESHEET_REQ table.
	 */
	public void delete(TimesheetReqPk pk) throws TimesheetReqDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL_DELETE + " with PK: " + pk);
			}
		
			stmt = conn.prepareStatement( SQL_DELETE );
			stmt.setInt( 1, pk.getId() );
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new TimesheetReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the TIMESHEET_REQ table that matches the specified primary-key value.
	 */
	public TimesheetReq findByPrimaryKey(TimesheetReqPk pk) throws TimesheetReqDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'ID = :id'.
	 */
	public TimesheetReq findByPrimaryKey(int id) throws TimesheetReqDaoException
	{
		TimesheetReq ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria ''.
	 */
	public TimesheetReq[] findAll() throws TimesheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'ID = :id'.
	 */
	public TimesheetReq[] findWhereIdEquals(int id) throws TimesheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'ESRQM_ID = :esrqmId'.
	 */
	public TimesheetReq[] findWhereEsrqmIdEquals(int esrqmId) throws TimesheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ESRQM_ID = ? ORDER BY ESRQM_ID", new Object[] {  new Integer(esrqmId) } );
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'COMMENTS = :comments'.
	 */
	public TimesheetReq[] findWhereCommentsEquals(String comments) throws TimesheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE COMMENTS = ? ORDER BY COMMENTS", new Object[] { comments } );
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'STATUS = :status'.
	 */
	public TimesheetReq[] findWhereStatusEquals(String status) throws TimesheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE STATUS = ? ORDER BY STATUS", new Object[] { status } );
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'ASSIGNED_TO = :assignedTo'.
	 */
	public TimesheetReq[] findWhereAssignedToEquals(int assignedTo) throws TimesheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ASSIGNED_TO = ? ORDER BY ASSIGNED_TO", new Object[] {  new Integer(assignedTo) } );
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'RAISED_BY = :raisedBy'.
	 */
	public TimesheetReq[] findWhereRaisedByEquals(int raisedBy) throws TimesheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE RAISED_BY = ? ORDER BY RAISED_BY", new Object[] {  new Integer(raisedBy) } );
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'SUMMARY = :summary'.
	 */
	public TimesheetReq[] findWhereSummaryEquals(String summary) throws TimesheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE SUMMARY = ? ORDER BY SUMMARY", new Object[] { summary } );
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'TSHEET_ID = :tsheetId'.
	 */
	public TimesheetReq[] findWhereTsheetIdEquals(int tsheetId) throws TimesheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE TSHEET_ID = ? ORDER BY TSHEET_ID", new Object[] {  new Integer(tsheetId) } );
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'CREATEDATETIME = :createdatetime'.
	 */
	public TimesheetReq[] findWhereCreatedatetimeEquals(Date createdatetime) throws TimesheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE CREATEDATETIME = ? ORDER BY CREATEDATETIME", new Object[] { createdatetime==null ? null : new java.sql.Timestamp( createdatetime.getTime() ) } );
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'SEQUENCE = :sequence'.
	 */
	public TimesheetReq[] findWhereSequenceEquals(int sequence) throws TimesheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE SEQUENCE = ? ORDER BY SEQUENCE", new Object[] {  new Integer(sequence) } );
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'MESSAGE_BODY = :messageBody'.
	 */
	public TimesheetReq[] findWhereMessageBodyEquals(String messageBody) throws TimesheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE MESSAGE_BODY = ? ORDER BY MESSAGE_BODY", new Object[] { messageBody } );
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'ACTION_BY = :actionBy'.
	 */
	public TimesheetReq[] findWhereActionByEquals(int actionBy) throws TimesheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ACTION_BY = ? ORDER BY ACTION_BY", new Object[] {  new Integer(actionBy) } );
	}

	/**
	 * Method 'TimesheetReqDaoImpl'
	 * 
	 */
	public TimesheetReqDaoImpl()
	{
	}

	/**
	 * Method 'TimesheetReqDaoImpl'
	 * 
	 * @param userConn
	 */
	public TimesheetReqDaoImpl(final java.sql.Connection userConn)
	{
		this.userConn = userConn;
	}

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows)
	{
		this.maxRows = maxRows;
	}

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows()
	{
		return maxRows;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "TIMESHEET_REQ";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected TimesheetReq fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			TimesheetReq dto = new TimesheetReq();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected TimesheetReq[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<TimesheetReq> resultList = new ArrayList<TimesheetReq>();
		while (rs.next()) {
			TimesheetReq dto = new TimesheetReq();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		TimesheetReq ret[] = new TimesheetReq[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(TimesheetReq dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setEsrqmId( rs.getInt( COLUMN_ESRQM_ID ) );
		dto.setComments( rs.getString( COLUMN_COMMENTS ) );
		dto.setStatus( rs.getString( COLUMN_STATUS ) );
		dto.setAssignedTo( rs.getInt( COLUMN_ASSIGNED_TO ) );
		if (rs.wasNull()) {
			dto.setAssignedToNull( true );
		}
		
		dto.setRaisedBy( rs.getInt( COLUMN_RAISED_BY ) );
		if (rs.wasNull()) {
			dto.setRaisedByNull( true );
		}
		
		dto.setSummary( rs.getString( COLUMN_SUMMARY ) );
		dto.setTsheetId( rs.getInt( COLUMN_TSHEET_ID ) );
		dto.setCreatedatetime( rs.getTimestamp(COLUMN_CREATEDATETIME ) );
		dto.setSequence( rs.getInt( COLUMN_SEQUENCE ) );
		dto.setMessageBody( rs.getString( COLUMN_MESSAGE_BODY ) );
		dto.setActionBy( rs.getInt( COLUMN_ACTION_BY ) );
		if (rs.wasNull()) {
			dto.setActionByNull( true );
		}
		
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(TimesheetReq dto)
	{
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the specified arbitrary SQL statement
	 */
	public TimesheetReq[] findByDynamicSelect(String sql, Object[] sqlParams) throws TimesheetReqDaoException
	{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = sql;
		
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL);
			}
		
			// prepare statement
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
		
			// bind parameters
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setObject( i+1, sqlParams[i] );
			}
		
		
			rs = stmt.executeQuery();
		
			// fetch the results
			return fetchMultiResults(rs);
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new TimesheetReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the specified arbitrary SQL statement
	 */
	public TimesheetReq[] findByDynamicWhere(String sql, Object[] sqlParams) throws TimesheetReqDaoException
	{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = SQL_SELECT + " WHERE " + sql;
		
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL);
			}
		
			// prepare statement
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
		
			// bind parameters
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setObject( i+1, sqlParams[i] );
			}
		
		
			rs = stmt.executeQuery();
		
			// fetch the results
			return fetchMultiResults(rs);
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new TimesheetReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	@Override
	public int update(String query, Object[] sqlParams) throws TimesheetReqDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		int rs = 0;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = query;
		
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL);
			}
		
			// prepare statement
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
		
			// bind parameters
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setObject( i+1, sqlParams[i] );
			}
		
		
			rs = stmt.executeUpdate();
		
			// fetch the results
			return rs;
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new TimesheetReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {			
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
		
	}

}
