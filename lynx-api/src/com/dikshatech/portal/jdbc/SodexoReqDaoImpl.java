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

import com.dikshatech.portal.dao.SodexoReqDao;
import com.dikshatech.portal.dto.SodexoReq;
import com.dikshatech.portal.dto.SodexoReqPk;
import com.dikshatech.portal.exceptions.SodexoReqDaoException;

public class SodexoReqDaoImpl extends AbstractDAO implements SodexoReqDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( SodexoReqDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, SD_ID, ESR_MAP_ID, ASSIGNED_TO, ACTION_BY, MESSAGE_BODY, ACTION_DATE FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, SD_ID, ESR_MAP_ID, ASSIGNED_TO, ACTION_BY, MESSAGE_BODY, ACTION_DATE ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, SD_ID = ?, ESR_MAP_ID = ?, ASSIGNED_TO = ?, ACTION_BY = ?, MESSAGE_BODY = ?, ACTION_DATE = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column SD_ID
	 */
	protected static final int COLUMN_SD_ID = 2;

	/** 
	 * Index of column ESR_MAP_ID
	 */
	protected static final int COLUMN_ESR_MAP_ID = 3;

	/** 
	 * Index of column ASSIGNED_TO
	 */
	protected static final int COLUMN_ASSIGNED_TO = 4;

	/** 
	 * Index of column ACTION_BY
	 */
	protected static final int COLUMN_ACTION_BY = 5;

	/** 
	 * Index of column MESSAGE_BODY
	 */
	protected static final int COLUMN_MESSAGE_BODY = 6;

	/** 
	 * Index of column ACTION_DATE
	 */
	protected static final int COLUMN_ACTION_DATE = 7;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 7;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the SODEXO_REQ table.
	 */
	public SodexoReqPk insert(SodexoReq dto) throws SodexoReqDaoException
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
			if (dto.isSdIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getSdId() );
			}
		
			if (dto.isEsrMapIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getEsrMapId() );
			}
		
			if (dto.isAssignedToNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getAssignedTo() );
			}
		
			if (dto.isActionByNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getActionBy() );
			}
		
			stmt.setString( index++, dto.getMessageBody() );
			stmt.setTimestamp(index++, dto.getActionDate()==null ? null : new java.sql.Timestamp( dto.getActionDate().getTime() ) );
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
			throw new SodexoReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the SODEXO_REQ table.
	 */
	public void update(SodexoReqPk pk, SodexoReq dto) throws SodexoReqDaoException
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
			if (dto.isSdIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getSdId() );
			}
		
			if (dto.isEsrMapIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getEsrMapId() );
			}
		
			if (dto.isAssignedToNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getAssignedTo() );
			}
		
			if (dto.isActionByNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getActionBy() );
			}
		
			stmt.setString( index++, dto.getMessageBody() );
			stmt.setTimestamp(index++, dto.getActionDate()==null ? null : new java.sql.Timestamp( dto.getActionDate().getTime() ) );
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
			throw new SodexoReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the SODEXO_REQ table.
	 */
	public void delete(SodexoReqPk pk) throws SodexoReqDaoException
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
			throw new SodexoReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the SODEXO_REQ table that matches the specified primary-key value.
	 */
	public SodexoReq findByPrimaryKey(SodexoReqPk pk) throws SodexoReqDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the SODEXO_REQ table that match the criteria 'ID = :id'.
	 */
	public SodexoReq findByPrimaryKey(int id) throws SodexoReqDaoException
	{
		SodexoReq ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the SODEXO_REQ table that match the criteria ''.
	 */
	public SodexoReq[] findAll() throws SodexoReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the SODEXO_REQ table that match the criteria 'SD_ID = :sdId'.
	 */
	public SodexoReq[] findBySodexoDetails(int sdId) throws SodexoReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE SD_ID = ?", new Object[] {  new Integer(sdId) } );
	}

	/** 
	 * Returns all rows from the SODEXO_REQ table that match the criteria 'ID = :id'.
	 */
	public SodexoReq[] findWhereIdEquals(int id) throws SodexoReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the SODEXO_REQ table that match the criteria 'SD_ID = :sdId'.
	 */
	public SodexoReq[] findWhereSdIdEquals(int sdId) throws SodexoReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE SD_ID = ? ORDER BY SD_ID", new Object[] {  new Integer(sdId) } );
	}

	/** 
	 * Returns all rows from the SODEXO_REQ table that match the criteria 'ESR_MAP_ID = :esrMapId'.
	 */
	public SodexoReq[] findWhereEsrMapIdEquals(int esrMapId) throws SodexoReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ESR_MAP_ID = ? ORDER BY ESR_MAP_ID", new Object[] {  new Integer(esrMapId) } );
	}

	/** 
	 * Returns all rows from the SODEXO_REQ table that match the criteria 'ASSIGNED_TO = :assignedTo'.
	 */
	public SodexoReq[] findWhereAssignedToEquals(int assignedTo) throws SodexoReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ASSIGNED_TO = ? ORDER BY ASSIGNED_TO", new Object[] {  new Integer(assignedTo) } );
	}

	/** 
	 * Returns all rows from the SODEXO_REQ table that match the criteria 'ACTION_BY = :actionBy'.
	 */
	public SodexoReq[] findWhereActionByEquals(int actionBy) throws SodexoReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ACTION_BY = ? ORDER BY ACTION_BY", new Object[] {  new Integer(actionBy) } );
	}

	/** 
	 * Returns all rows from the SODEXO_REQ table that match the criteria 'MESSAGE_BODY = :messageBody'.
	 */
	public SodexoReq[] findWhereMessageBodyEquals(String messageBody) throws SodexoReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE MESSAGE_BODY = ? ORDER BY MESSAGE_BODY", new Object[] { messageBody } );
	}

	/** 
	 * Returns all rows from the SODEXO_REQ table that match the criteria 'ACTION_DATE = :actionDate'.
	 */
	public SodexoReq[] findWhereActionDateEquals(Date actionDate) throws SodexoReqDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ACTION_DATE = ? ORDER BY ACTION_DATE", new Object[] { actionDate==null ? null : new java.sql.Timestamp( actionDate.getTime() ) } );
	}

	/**
	 * Method 'SodexoReqDaoImpl'
	 * 
	 */
	public SodexoReqDaoImpl()
	{
	}

	/**
	 * Method 'SodexoReqDaoImpl'
	 * 
	 * @param userConn
	 */
	public SodexoReqDaoImpl(final java.sql.Connection userConn)
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
		return "SODEXO_REQ";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected SodexoReq fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			SodexoReq dto = new SodexoReq();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected SodexoReq[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<SodexoReq> resultList = new ArrayList<SodexoReq>();
		while (rs.next()) {
			SodexoReq dto = new SodexoReq();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		SodexoReq ret[] = new SodexoReq[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(SodexoReq dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setSdId( rs.getInt( COLUMN_SD_ID ) );
		if (rs.wasNull()) {
			dto.setSdIdNull( true );
		}
		
		dto.setEsrMapId( rs.getInt( COLUMN_ESR_MAP_ID ) );
		if (rs.wasNull()) {
			dto.setEsrMapIdNull( true );
		}
		
		dto.setAssignedTo( rs.getInt( COLUMN_ASSIGNED_TO ) );
		if (rs.wasNull()) {
			dto.setAssignedToNull( true );
		}
		
		dto.setActionBy( rs.getInt( COLUMN_ACTION_BY ) );
		if (rs.wasNull()) {
			dto.setActionByNull( true );
		}
		
		dto.setMessageBody( rs.getString( COLUMN_MESSAGE_BODY ) );
		dto.setActionDate( rs.getTimestamp(COLUMN_ACTION_DATE ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(SodexoReq dto)
	{
	}

	/** 
	 * Returns all rows from the SODEXO_REQ table that match the specified arbitrary SQL statement
	 */
	public SodexoReq[] findByDynamicSelect(String sql, Object[] sqlParams) throws SodexoReqDaoException
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
			throw new SodexoReqDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the SODEXO_REQ table that match the specified arbitrary SQL statement
	 */
	public SodexoReq[] findByDynamicWhere(String sql, Object[] sqlParams) throws SodexoReqDaoException
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
			throw new SodexoReqDaoException( "Exception: " + _e.getMessage(), _e );
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
	public SodexoReq[] findByDynamicWhereIn(String sql) throws SodexoReqDaoException {
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
		
			// bind parameters not required in this case 
			
			
			rs = stmt.executeQuery();
		
			// fetch the results
			return fetchMultiResults(rs);
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new SodexoReqDaoException( "Exception: " + _e.getMessage(), _e );
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
