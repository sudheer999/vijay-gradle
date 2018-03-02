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

import com.dikshatech.portal.dao.RmgTimeSheetReqDao;
import com.dikshatech.portal.dto.RmgTimeSheetReq;
import com.dikshatech.portal.dto.RmgTimeSheetReqPk;
import com.dikshatech.portal.exceptions.RmgTimeSheetReqDaoException;

public class RmgTimeSheetReqDaoImpl extends AbstractDAO implements RmgTimeSheetReqDao{
	

	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( RmgTimeSheetReqDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, RMG_TIMESHEET_ID, SEQ_ID, ASSIGNED_TO, RECEIVED_ON, SUBMITTED_ON, IS_ESCALATED FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, RMG_TIMESHEET_ID, SEQ_ID, ASSIGNED_TO, RECEIVED_ON, SUBMITTED_ON, IS_ESCALATED ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, RMG_TIMESHEET_ID = ?, SEQ_ID = ?, ASSIGNED_TO = ?, RECEIVED_ON = ?, SUBMITTED_ON = ?, IS_ESCALATED = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column DEP_ID
	 */
	protected static final int COLUMN_RMG_TIMESHEET_ID = 2;

	/** 
	 * Index of column SEQ_ID
	 */
	protected static final int COLUMN_SEQ_ID = 3;

	/** 
	 * Index of column ASSIGNED_TO
	 */
	protected static final int COLUMN_ASSIGNED_TO = 4;

	/** 
	 * Index of column RECEIVED_ON
	 */
	protected static final int COLUMN_RECEIVED_ON = 5;

	/** 
	 * Index of column SUBMITTED_ON
	 */
	protected static final int COLUMN_SUBMITTED_ON = 6;

	/** 
	 * Index of column IS_ESCALATED
	 */
	protected static final int COLUMN_IS_ESCALATED = 7;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 7;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the DEP_PERDIEM_REQ table.
	 */
	public RmgTimeSheetReqPk insert(RmgTimeSheetReq dto) throws RmgTimeSheetReqDaoException
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
			stmt.setInt( index++, dto.getRMG_TIMESHEET_ID() );
			stmt.setInt( index++, dto.getSeqId() );
			stmt.setInt( index++, dto.getAssignedTo() );
			stmt.setDate(index++, dto.getReceivedOn()==null ? null : new java.sql.Date( dto.getReceivedOn().getTime() ) );
			stmt.setDate(index++, dto.getSubmittedOn()==null ? null : new java.sql.Date( dto.getSubmittedOn().getTime() ) );
			if (dto.isEscalatedNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getIsEscalated() );
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
			throw new RmgTimeSheetReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the DEP_PERDIEM_REQ table.
	 */
	public void update(RmgTimeSheetReqPk pk, RmgTimeSheetReq dto) throws RmgTimeSheetReqDaoException
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
			stmt.setInt( index++, dto.getRMG_TIMESHEET_ID() );
			stmt.setInt( index++, dto.getSeqId() );
			stmt.setInt( index++, dto.getAssignedTo() );
			stmt.setDate(index++, dto.getReceivedOn()==null ? null : new java.sql.Date( dto.getReceivedOn().getTime() ) );
			stmt.setDate(index++, dto.getSubmittedOn()==null ? null : new java.sql.Date( dto.getSubmittedOn().getTime() ) );
			if (dto.isEscalatedNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getIsEscalated() );
			}
		
			stmt.setInt( 8, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new RmgTimeSheetReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the DEP_PERDIEM_REQ table.
	 */
	public void delete(RmgTimeSheetReqPk pk) throws RmgTimeSheetReqDaoException
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
			throw new RmgTimeSheetReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the DEP_PERDIEM_REQ table that matches the specified primary-key value.
	 */
	public RmgTimeSheetReq findByPrimaryKey(RmgTimeSheetReqPk pk) throws RmgTimeSheetReqDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'ID = :id'.
	 */
	public RmgTimeSheetReq findByPrimaryKey(int id) throws RmgTimeSheetReqDaoException
	{
		RmgTimeSheetReq ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria ''.
	 */
	public RmgTimeSheetReq[] findAll() throws RmgTimeSheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'ID = :id'.
	 */
	public RmgTimeSheetReq[] findWhereIdEquals(int id) throws RmgTimeSheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'DEP_ID = :depId'.
	 */
	public RmgTimeSheetReq[] findWhereDepIdEquals(int depId) throws RmgTimeSheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE RMG_TIMESHEET_ID  = ? ORDER BY RMG_TIMESHEET_ID ", new Object[] {  new Integer(depId) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'SEQ_ID = :seqId'.
	 */
	public RmgTimeSheetReq[] findWhereSeqIdEquals(int seqId) throws RmgTimeSheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE SEQ_ID = ? ORDER BY SEQ_ID", new Object[] {  new Integer(seqId) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'ASSIGNED_TO = :assignedTo'.
	 */
	public RmgTimeSheetReq[] findWhereAssignedToEquals(int assignedTo) throws RmgTimeSheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ASSIGNED_TO = ? ORDER BY ASSIGNED_TO", new Object[] {  new Integer(assignedTo) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'RECEIVED_ON = :receivedOn'.
	 */
	public RmgTimeSheetReq[] findWhereReceivedOnEquals(Date receivedOn) throws RmgTimeSheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE RECEIVED_ON = ? ORDER BY RECEIVED_ON", new Object[] { receivedOn==null ? null : new java.sql.Date( receivedOn.getTime() ) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'SUBMITTED_ON = :submittedOn'.
	 */
	public RmgTimeSheetReq[] findWhereSubmittedOnEquals(Date submittedOn) throws RmgTimeSheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE SUBMITTED_ON = ? ORDER BY SUBMITTED_ON", new Object[] { submittedOn==null ? null : new java.sql.Date( submittedOn.getTime() ) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'IS_ESCALATED = :isEscalated'.
	 */
	public RmgTimeSheetReq[] findWhereIsEscalatedEquals(int isEscalated) throws RmgTimeSheetReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE IS_ESCALATED = ? ORDER BY IS_ESCALATED", new Object[] {  new Integer(isEscalated) } );
	}

	/**
	 * Method 'RmgTimeSheetReqDaoImpl'
	 * 
	 */
	public RmgTimeSheetReqDaoImpl()
	{
	}

	/**
	 * Method 'RmgTimeSheetReqDaoImpl'
	 * 
	 * @param userConn
	 */
	public RmgTimeSheetReqDaoImpl(final java.sql.Connection userConn)
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
		return "RMG_TIMESHEET_REQ";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected RmgTimeSheetReq fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			RmgTimeSheetReq dto = new RmgTimeSheetReq();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected RmgTimeSheetReq[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<RmgTimeSheetReq> resultList = new ArrayList<RmgTimeSheetReq>();
		while (rs.next()) {
			RmgTimeSheetReq dto = new RmgTimeSheetReq();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		RmgTimeSheetReq ret[] = new RmgTimeSheetReq[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(RmgTimeSheetReq dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setRMG_TIMESHEET_ID( rs.getInt( COLUMN_RMG_TIMESHEET_ID ) );
		dto.setSeqId( rs.getInt( COLUMN_SEQ_ID ) );
		dto.setAssignedTo( rs.getInt( COLUMN_ASSIGNED_TO ) );
		dto.setReceivedOn( rs.getDate(COLUMN_RECEIVED_ON ) );
		dto.setSubmittedOn( rs.getDate(COLUMN_SUBMITTED_ON ) );
		dto.setIsEscalated( rs.getInt( COLUMN_IS_ESCALATED ) );
		if (rs.wasNull()) {
			dto.setEscalatedNull(true);
		}
		
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(RmgTimeSheetReq dto)
	{
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the specified arbitrary SQL statement
	 */
	public RmgTimeSheetReq[] findByDynamicSelect(String sql, Object[] sqlParams) throws RmgTimeSheetReqDaoException
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
			throw new RmgTimeSheetReqDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the specified arbitrary SQL statement
	 */
	public RmgTimeSheetReq[] findByDynamicWhere(String sql, Object[] sqlParams) throws RmgTimeSheetReqDaoException
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
			throw new RmgTimeSheetReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}



}
