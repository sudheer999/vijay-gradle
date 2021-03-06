/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.jdbc;

import com.dikshatech.portal.dao.*;
import com.dikshatech.portal.factory.*;
import java.util.Date;
import com.dikshatech.portal.dto.*;
import com.dikshatech.portal.exceptions.*;

import java.sql.Connection;
import java.util.Collection;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class IssueResolutionDetailsDaoImpl extends AbstractDAO implements IssueResolutionDetailsDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( IssueResolutionDetailsDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, ESR_MAP_ID, FIRST_STATUS, LAST_STATUS, REQUESTER_COMMENTS, REQUESTED_ON, RESOLVED_ON, CLOSED_ON, RESOLVED_BY FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, ESR_MAP_ID, FIRST_STATUS, LAST_STATUS, REQUESTER_COMMENTS, REQUESTED_ON, RESOLVED_ON, CLOSED_ON, RESOLVED_BY ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, ESR_MAP_ID = ?, FIRST_STATUS = ?, LAST_STATUS = ?, REQUESTER_COMMENTS = ?, REQUESTED_ON = ?, RESOLVED_ON = ?, CLOSED_ON = ?, RESOLVED_BY = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column ESR_MAP_ID
	 */
	protected static final int COLUMN_ESR_MAP_ID = 2;

	/** 
	 * Index of column FIRST_STATUS
	 */
	protected static final int COLUMN_FIRST_STATUS = 3;

	/** 
	 * Index of column LAST_STATUS
	 */
	protected static final int COLUMN_LAST_STATUS = 4;

	/** 
	 * Index of column REQUESTER_COMMENTS
	 */
	protected static final int COLUMN_REQUESTER_COMMENTS = 5;

	/** 
	 * Index of column REQUESTED_ON
	 */
	protected static final int COLUMN_REQUESTED_ON = 6;

	/** 
	 * Index of column RESOLVED_ON
	 */
	protected static final int COLUMN_RESOLVED_ON = 7;

	/** 
	 * Index of column CLOSED_ON
	 */
	protected static final int COLUMN_CLOSED_ON = 8;

	/** 
	 * Index of column RESOLVED_BY
	 */
	protected static final int COLUMN_RESOLVED_BY = 9;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 9;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the ISSUE_RESOLUTION_DETAILS table.
	 */
	public IssueResolutionDetailsPk insert(IssueResolutionDetails dto) throws IssueResolutionDetailsDaoException
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
			if (dto.isEsrMapIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getEsrMapId() );
			}
		
			stmt.setString( index++, dto.getFirstStatus() );
			stmt.setString( index++, dto.getLastStatus() );
			stmt.setString( index++, dto.getRequesterComments() );
			stmt.setTimestamp(index++, dto.getRequestedOn()==null ? null : new java.sql.Timestamp( dto.getRequestedOn().getTime() ) );
			stmt.setTimestamp(index++, dto.getResolvedOn()==null ? null : new java.sql.Timestamp( dto.getResolvedOn().getTime() ) );
			stmt.setTimestamp(index++, dto.getClosedOn()==null ? null : new java.sql.Timestamp( dto.getClosedOn().getTime() ) );
			if (dto.isResolvedByNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getResolvedBy() );
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
			throw new IssueResolutionDetailsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the ISSUE_RESOLUTION_DETAILS table.
	 */
	public void update(IssueResolutionDetailsPk pk, IssueResolutionDetails dto) throws IssueResolutionDetailsDaoException
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
			if (dto.isEsrMapIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getEsrMapId() );
			}
		
			stmt.setString( index++, dto.getFirstStatus() );
			stmt.setString( index++, dto.getLastStatus() );
			stmt.setString( index++, dto.getRequesterComments() );
			stmt.setTimestamp(index++, dto.getRequestedOn()==null ? null : new java.sql.Timestamp( dto.getRequestedOn().getTime() ) );
			stmt.setTimestamp(index++, dto.getResolvedOn()==null ? null : new java.sql.Timestamp( dto.getResolvedOn().getTime() ) );
			stmt.setTimestamp(index++, dto.getClosedOn()==null ? null : new java.sql.Timestamp( dto.getClosedOn().getTime() ) );
			if (dto.isResolvedByNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getResolvedBy() );
			}
		
			stmt.setInt( 10, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new IssueResolutionDetailsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the ISSUE_RESOLUTION_DETAILS table.
	 */
	public void delete(IssueResolutionDetailsPk pk) throws IssueResolutionDetailsDaoException
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
			throw new IssueResolutionDetailsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the ISSUE_RESOLUTION_DETAILS table that matches the specified primary-key value.
	 */
	public IssueResolutionDetails findByPrimaryKey(IssueResolutionDetailsPk pk) throws IssueResolutionDetailsDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the ISSUE_RESOLUTION_DETAILS table that match the criteria 'ID = :id'.
	 */
	public IssueResolutionDetails findByPrimaryKey(int id) throws IssueResolutionDetailsDaoException
	{
		IssueResolutionDetails ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the ISSUE_RESOLUTION_DETAILS table that match the criteria ''.
	 */
	public IssueResolutionDetails[] findAll() throws IssueResolutionDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the ISSUE_RESOLUTION_DETAILS table that match the criteria 'ID = :id'.
	 */
	public IssueResolutionDetails[] findWhereIdEquals(int id) throws IssueResolutionDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the ISSUE_RESOLUTION_DETAILS table that match the criteria 'ESR_MAP_ID = :esrMapId'.
	 */
	public IssueResolutionDetails[] findWhereEsrMapIdEquals(int esrMapId) throws IssueResolutionDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ESR_MAP_ID = ? ORDER BY ESR_MAP_ID", new Object[] {  new Integer(esrMapId) } );
	}

	/** 
	 * Returns all rows from the ISSUE_RESOLUTION_DETAILS table that match the criteria 'FIRST_STATUS = :firstStatus'.
	 */
	public IssueResolutionDetails[] findWhereFirstStatusEquals(String firstStatus) throws IssueResolutionDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE FIRST_STATUS = ? ORDER BY FIRST_STATUS", new Object[] { firstStatus } );
	}

	/** 
	 * Returns all rows from the ISSUE_RESOLUTION_DETAILS table that match the criteria 'LAST_STATUS = :lastStatus'.
	 */
	public IssueResolutionDetails[] findWhereLastStatusEquals(String lastStatus) throws IssueResolutionDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE LAST_STATUS = ? ORDER BY LAST_STATUS", new Object[] { lastStatus } );
	}

	/** 
	 * Returns all rows from the ISSUE_RESOLUTION_DETAILS table that match the criteria 'REQUESTER_COMMENTS = :requesterComments'.
	 */
	public IssueResolutionDetails[] findWhereRequesterCommentsEquals(String requesterComments) throws IssueResolutionDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE REQUESTER_COMMENTS = ? ORDER BY REQUESTER_COMMENTS", new Object[] { requesterComments } );
	}

	/** 
	 * Returns all rows from the ISSUE_RESOLUTION_DETAILS table that match the criteria 'REQUESTED_ON = :requestedOn'.
	 */
	public IssueResolutionDetails[] findWhereRequestedOnEquals(Date requestedOn) throws IssueResolutionDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE REQUESTED_ON = ? ORDER BY REQUESTED_ON", new Object[] { requestedOn==null ? null : new java.sql.Timestamp( requestedOn.getTime() ) } );
	}

	/** 
	 * Returns all rows from the ISSUE_RESOLUTION_DETAILS table that match the criteria 'RESOLVED_ON = :resolvedOn'.
	 */
	public IssueResolutionDetails[] findWhereResolvedOnEquals(Date resolvedOn) throws IssueResolutionDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE RESOLVED_ON = ? ORDER BY RESOLVED_ON", new Object[] { resolvedOn==null ? null : new java.sql.Timestamp( resolvedOn.getTime() ) } );
	}

	/** 
	 * Returns all rows from the ISSUE_RESOLUTION_DETAILS table that match the criteria 'CLOSED_ON = :closedOn'.
	 */
	public IssueResolutionDetails[] findWhereClosedOnEquals(Date closedOn) throws IssueResolutionDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE CLOSED_ON = ? ORDER BY CLOSED_ON", new Object[] { closedOn==null ? null : new java.sql.Timestamp( closedOn.getTime() ) } );
	}

	/** 
	 * Returns all rows from the ISSUE_RESOLUTION_DETAILS table that match the criteria 'RESOLVED_BY = :resolvedBy'.
	 */
	public IssueResolutionDetails[] findWhereResolvedByEquals(int resolvedBy) throws IssueResolutionDetailsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE RESOLVED_BY = ? ORDER BY RESOLVED_BY", new Object[] {  new Integer(resolvedBy) } );
	}

	/**
	 * Method 'IssueResolutionDetailsDaoImpl'
	 * 
	 */
	public IssueResolutionDetailsDaoImpl()
	{
	}

	/**
	 * Method 'IssueResolutionDetailsDaoImpl'
	 * 
	 * @param userConn
	 */
	public IssueResolutionDetailsDaoImpl(final java.sql.Connection userConn)
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
		return "ISSUE_RESOLUTION_DETAILS";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected IssueResolutionDetails fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			IssueResolutionDetails dto = new IssueResolutionDetails();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected IssueResolutionDetails[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<IssueResolutionDetails> resultList = new ArrayList<IssueResolutionDetails>();
		while (rs.next()) {
			IssueResolutionDetails dto = new IssueResolutionDetails();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		IssueResolutionDetails ret[] = new IssueResolutionDetails[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(IssueResolutionDetails dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setEsrMapId( rs.getInt( COLUMN_ESR_MAP_ID ) );
		if (rs.wasNull()) {
			dto.setEsrMapIdNull( true );
		}
		
		dto.setFirstStatus( rs.getString( COLUMN_FIRST_STATUS ) );
		dto.setLastStatus( rs.getString( COLUMN_LAST_STATUS ) );
		dto.setRequesterComments( rs.getString( COLUMN_REQUESTER_COMMENTS ) );
		dto.setRequestedOn( rs.getTimestamp(COLUMN_REQUESTED_ON ) );
		dto.setResolvedOn( rs.getTimestamp(COLUMN_RESOLVED_ON ) );
		dto.setClosedOn( rs.getTimestamp(COLUMN_CLOSED_ON ) );
		dto.setResolvedBy( rs.getInt( COLUMN_RESOLVED_BY ) );
		if (rs.wasNull()) {
			dto.setResolvedByNull( true );
		}
		
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(IssueResolutionDetails dto)
	{
	}

	/** 
	 * Returns all rows from the ISSUE_RESOLUTION_DETAILS table that match the specified arbitrary SQL statement
	 */
	public IssueResolutionDetails[] findByDynamicSelect(String sql, Object[] sqlParams) throws IssueResolutionDetailsDaoException
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
			throw new IssueResolutionDetailsDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the ISSUE_RESOLUTION_DETAILS table that match the specified arbitrary SQL statement
	 */
	public IssueResolutionDetails[] findByDynamicWhere(String sql, Object[] sqlParams) throws IssueResolutionDetailsDaoException
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
			throw new IssueResolutionDetailsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}
	
	
	public ArrayList<String> getRequesterComments(String sql) throws IssueCommentsDaoException
	{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<String> resultList = new ArrayList<String>();
		
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
			
			rs = stmt.executeQuery();
			if(rs!=null)
			{
				while (rs.next()) {
					resultList.add(rs.getString("REQUESTER_COMMENTS"));
				}
			}
			
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new IssueCommentsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return resultList;
	}

}
