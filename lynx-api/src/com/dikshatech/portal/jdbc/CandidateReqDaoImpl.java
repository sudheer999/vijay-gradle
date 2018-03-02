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

import com.dikshatech.portal.dao.CandidateReqDao;
import com.dikshatech.portal.dto.CandidateReq;
import com.dikshatech.portal.dto.CandidateReqPk;
import com.dikshatech.portal.exceptions.CandidateReqDaoException;

public class CandidateReqDaoImpl extends AbstractDAO implements CandidateReqDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( CandidateReqDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, ESRQM_ID, COMMENTS, STATUS, ASSIGNED_TO, RAISED_BY, SUMMARY, CANDIDATE_ID, RE_SERVE, OFFER_LETTER, CREATEDATETIME, CYCLE, MESSAGE_BODY, SERVED, ACTION_TAKEN FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, ESRQM_ID, COMMENTS, STATUS, ASSIGNED_TO, RAISED_BY, SUMMARY, CANDIDATE_ID, RE_SERVE, OFFER_LETTER, CREATEDATETIME, CYCLE, MESSAGE_BODY, SERVED, ACTION_TAKEN ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, ESRQM_ID = ?, COMMENTS = ?, STATUS = ?, ASSIGNED_TO = ?, RAISED_BY = ?, SUMMARY = ?, CANDIDATE_ID = ?, RE_SERVE = ?, OFFER_LETTER = ?, CREATEDATETIME = ?, CYCLE = ?, MESSAGE_BODY = ?, SERVED = ?, ACTION_TAKEN = ? WHERE ID = ?";

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
	 * Index of column CANDIDATE_ID
	 */
	protected static final int COLUMN_CANDIDATE_ID = 8;

	/** 
	 * Index of column RE_SERVE
	 */
	protected static final int COLUMN_RE_SERVE = 9;

	/** 
	 * Index of column OFFER_LETTER
	 */
	protected static final int COLUMN_OFFER_LETTER = 10;

	/** 
	 * Index of column CREATEDATETIME
	 */
	protected static final int COLUMN_CREATEDATETIME = 11;

	/** 
	 * Index of column CYCLE
	 */
	protected static final int COLUMN_CYCLE = 12;

	/** 
	 * Index of column MESSAGE_BODY
	 */
	protected static final int COLUMN_MESSAGE_BODY = 13;

	/** 
	 * Index of column SERVED
	 */
	protected static final int COLUMN_SERVED = 14;

	/** 
	 * Index of column ACTION_TAKEN
	 */
	protected static final int COLUMN_ACTION_TAKEN = 15;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 15;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;


	/** 
	 * Inserts a new row in the CANDIDATE_REQ table.
	 */
	public CandidateReqPk insert(CandidateReq dto) throws CandidateReqDaoException
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
			stmt.setInt( index++, dto.getCandidateId() );
			stmt.setShort( index++, dto.getReServe() );
			stmt.setString( index++, dto.getOfferLetter() );
			stmt.setTimestamp(index++, dto.getCreatedatetime()==null ? null : new java.sql.Timestamp( dto.getCreatedatetime().getTime() ) );
			if (dto.isCycleNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getCycle() );
			}
			stmt.setString( index++, dto.getMessageBody() );
			if (dto.isServedNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getServed() );
			}
			
			if (dto.isActionTakenNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getActionTaken() );
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
			throw new CandidateReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the CANDIDATE_REQ table.
	 */
	public void update(CandidateReqPk pk, CandidateReq dto) throws CandidateReqDaoException
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
			stmt.setInt( index++, dto.getCandidateId() );
			stmt.setShort( index++, dto.getReServe() );
			stmt.setString( index++, dto.getOfferLetter() );
			
			stmt.setTimestamp(index++, dto.getCreatedatetime()==null ? null : new java.sql.Timestamp( dto.getCreatedatetime().getTime() ) );
			if (dto.isCycleNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getCycle() );
			}
			stmt.setString( index++, dto.getMessageBody() );
			if (dto.isServedNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getServed() );
			}
			
			if (dto.isActionTakenNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getActionTaken() );
			}
			
			stmt.setInt( 16, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new CandidateReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the CANDIDATE_REQ table.
	 */
	public void delete(CandidateReqPk pk) throws CandidateReqDaoException
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
			throw new CandidateReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the CANDIDATE_REQ table that matches the specified primary-key value.
	 */
	public CandidateReq findByPrimaryKey(CandidateReqPk pk) throws CandidateReqDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the CANDIDATE_REQ table that match the criteria 'ID = :id'.
	 */
	public CandidateReq findByPrimaryKey(int id) throws CandidateReqDaoException
	{
		CandidateReq ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the CANDIDATE_REQ table that match the criteria ''.
	 */
	public CandidateReq[] findAll() throws CandidateReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the CANDIDATE_REQ table that match the criteria 'ESRQM_ID = :esrqmId'.
	 */
	public CandidateReq[] findWhereEsrqmIdEquals(int esrqmId) throws CandidateReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ESRQM_ID = ? ORDER BY ESRQM_ID", new Object[] {  new Integer(esrqmId) } );
	}

	/** 
	 * Returns all rows from the CANDIDATE_REQ table that match the criteria 'STATUS = :status'.
	 */
	public CandidateReq[] findWhereStatusEquals(String status) throws CandidateReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE STATUS = ? ORDER BY STATUS", new Object[] { status } );
	}

	/** 
	 * Returns all rows from the CANDIDATE_REQ table that match the criteria 'ASSIGNED_TO = :assignedTo'.
	 */
	public CandidateReq[] findWhereAssignedToEquals(int assignedTo) throws CandidateReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ASSIGNED_TO = ? ORDER BY ASSIGNED_TO", new Object[] {  new Integer(assignedTo) } );
	}

	/** 
	 * Returns all rows from the CANDIDATE_REQ table that match the criteria 'RAISED_BY = :raisedBy'.
	 */
	public CandidateReq[] findWhereRaisedByEquals(int raisedBy) throws CandidateReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE RAISED_BY = ? ORDER BY RAISED_BY", new Object[] {  new Integer(raisedBy) } );
	}

	/** 
	 * Returns all rows from the CANDIDATE_REQ table that match the criteria 'CANDIDATE_ID = :candidateId'.
	 */
	public CandidateReq[] findWhereCandidateIdEquals(int candidateId) throws CandidateReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE CANDIDATE_ID = ? ORDER BY CANDIDATE_ID", new Object[] {  new Integer(candidateId) } );
	}

	/** 
	 * Returns all rows from the CANDIDATE_REQ table that match the criteria 'RE_SERVE = :reServe'.
	 */
	public CandidateReq[] findWhereReServeEquals(short reServe) throws CandidateReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE RE_SERVE = ? ORDER BY RE_SERVE", new Object[] {  new Short(reServe) } );
	}

	/** 
	 * Returns all rows from the CANDIDATE_REQ table that match the criteria 'CREATEDATETIME = :createdatetime'.
	 */
	public CandidateReq[] findWhereCreatedatetimeEquals(Date createdatetime) throws CandidateReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE CREATEDATETIME = ? ORDER BY CREATEDATETIME", new Object[] { createdatetime==null ? null : new java.sql.Timestamp( createdatetime.getTime() ) } );
	}

	/** 
	 * Returns all rows from the CANDIDATE_REQ table that match the criteria 'CYCLE = :cycle'.
	 */
	public CandidateReq[] findWhereCycleEquals(int cycle) throws CandidateReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE CYCLE = ? ORDER BY CYCLE", new Object[] {  new Integer(cycle) } );
	}
	/** 
	 * Returns all rows from the CANDIDATE_REQ table that match the criteria 'MESSAGE_BODY = :messageBody'.
	 */
	public CandidateReq[] findWhereMessageBodyEquals(String messageBody) throws CandidateReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE MESSAGE_BODY = ? ORDER BY MESSAGE_BODY", new Object[] { messageBody } );
	}
	/**
	 * Method 'CandidateReqDaoImpl'
	 * 
	 */
	public CandidateReqDaoImpl()
	{
	}

	/**
	 * Method 'CandidateReqDaoImpl'
	 * 
	 * @param userConn
	 */
	public CandidateReqDaoImpl(final java.sql.Connection userConn)
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
		return "CANDIDATE_REQ";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected CandidateReq fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			CandidateReq dto = new CandidateReq();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected CandidateReq[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<CandidateReq> resultList = new ArrayList<CandidateReq>();
		while (rs.next()) {
			CandidateReq dto = new CandidateReq();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		CandidateReq ret[] = new CandidateReq[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(CandidateReq dto, ResultSet rs) throws SQLException
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
		dto.setCandidateId( rs.getInt( COLUMN_CANDIDATE_ID ) );
		dto.setReServe( rs.getShort( COLUMN_RE_SERVE ) );
		dto.setOfferLetter( rs.getString( COLUMN_OFFER_LETTER ) );
		dto.setCreatedatetime( rs.getTimestamp(COLUMN_CREATEDATETIME ) );
		dto.setCycle( rs.getInt( COLUMN_CYCLE ) );
		if (rs.wasNull()) {
			dto.setCycleNull( true );
		}
		
		dto.setMessageBody( rs.getString( COLUMN_MESSAGE_BODY ) );
		dto.setServed( rs.getInt( COLUMN_SERVED ) );
		if (rs.wasNull()) {
			dto.setServedNull( true );
		}
		dto.setActionTaken( rs.getInt( COLUMN_ACTION_TAKEN ) );
		
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(CandidateReq dto)
	{
	}

	/** 
	 * Returns all rows from the CANDIDATE_REQ table that match the specified arbitrary SQL statement
	 */
	public CandidateReq[] findByDynamicSelect(String sql, Object[] sqlParams) throws CandidateReqDaoException
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
			throw new CandidateReqDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the CANDIDATE_REQ table that match the specified arbitrary SQL statement
	 */
	public CandidateReq[] findByDynamicWhere(String sql, Object[] sqlParams) throws CandidateReqDaoException
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
			throw new CandidateReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}
	
	public boolean updateServe(int esrMapId) throws CandidateReqDaoException
	{
		boolean flag = false;
		
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = "UPDATE " + getTableName() + " SET SERVED = 0" + " WHERE ESRQM_ID = " + esrMapId;
		
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL);
			}
		
			// prepare statement
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
		
			flag = stmt.execute();
			
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new CandidateReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
		return flag;
	}

}