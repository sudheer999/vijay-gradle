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

import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProcessChainPk;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;

public class ProcessChainDaoImpl extends AbstractDAO implements ProcessChainDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( ProcessChainDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, PROC_NAME, APPROVAL_CHAIN, NOTIFICATION, HANDLER, FEATURE_ID FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, PROC_NAME, APPROVAL_CHAIN, NOTIFICATION, HANDLER, FEATURE_ID ) VALUES ( ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, PROC_NAME = ?, APPROVAL_CHAIN = ?, NOTIFICATION = ?, HANDLER = ?, FEATURE_ID = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column PROC_NAME
	 */
	protected static final int COLUMN_PROC_NAME = 2;

	/** 
	 * Index of column APPROVAL_CHAIN
	 */
	protected static final int COLUMN_APPROVAL_CHAIN = 3;

	/** 
	 * Index of column NOTIFICATION
	 */
	protected static final int COLUMN_NOTIFICATION = 4;

	/** 
	 * Index of column HANDLER
	 */
	protected static final int COLUMN_HANDLER = 5;

	/** 
	 * Index of column FEATURE_ID
	 */
	protected static final int COLUMN_FEATURE_ID = 6;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 6;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the PROCESS_CHAIN table.
	 */
	public ProcessChainPk insert(ProcessChain dto) throws ProcessChainDaoException
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
			stmt.setString( index++, dto.getProcName() );
			stmt.setString( index++, dto.getApprovalChain() );
			stmt.setString( index++, dto.getNotification() );
			stmt.setString( index++, dto.getHandler() );
			stmt.setInt( index++, dto.getFeatureId() );
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
			throw new ProcessChainDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the PROCESS_CHAIN table.
	 */
	public void update(ProcessChainPk pk, ProcessChain dto) throws ProcessChainDaoException
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
			stmt.setString( index++, dto.getProcName() );
			stmt.setString( index++, dto.getApprovalChain() );
			stmt.setString( index++, dto.getNotification() );
			stmt.setString( index++, dto.getHandler() );
			stmt.setInt( index++, dto.getFeatureId() );
			stmt.setInt( 7, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new ProcessChainDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the PROCESS_CHAIN table.
	 */
	public void delete(ProcessChainPk pk) throws ProcessChainDaoException
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
			throw new ProcessChainDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the PROCESS_CHAIN table that matches the specified primary-key value.
	 */
	public ProcessChain findByPrimaryKey(ProcessChainPk pk) throws ProcessChainDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the PROCESS_CHAIN table that match the criteria 'ID = :id'.
	 */
	public ProcessChain findByPrimaryKey(int id) throws ProcessChainDaoException
	{
		ProcessChain ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the PROCESS_CHAIN table that match the criteria ''.
	 */
	public ProcessChain[] findAll() throws ProcessChainDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the PROCESS_CHAIN table that match the criteria 'ID = :id'.
	 */
	public ProcessChain[] findWhereIdEquals(int id) throws ProcessChainDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the PROCESS_CHAIN table that match the criteria 'PROC_NAME = :procName'.
	 */
	public ProcessChain[] findWhereProcNameEquals(String procName) throws ProcessChainDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PROC_NAME = ? ORDER BY PROC_NAME", new Object[] { procName } );
	}

	/** 
	 * Returns all rows from the PROCESS_CHAIN table that match the criteria 'APPROVAL_CHAIN = :approvalChain'.
	 */
	public ProcessChain[] findWhereApprovalChainEquals(String approvalChain) throws ProcessChainDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE APPROVAL_CHAIN = ? ORDER BY APPROVAL_CHAIN", new Object[] { approvalChain } );
	}

	/** 
	 * Returns all rows from the PROCESS_CHAIN table that match the criteria 'NOTIFICATION = :notification'.
	 */
	public ProcessChain[] findWhereNotificationEquals(String notification) throws ProcessChainDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE NOTIFICATION = ? ORDER BY NOTIFICATION", new Object[] { notification } );
	}

	/** 
	 * Returns all rows from the PROCESS_CHAIN table that match the criteria 'HANDLER = :handler'.
	 */
	public ProcessChain[] findWhereHandlerEquals(String handler) throws ProcessChainDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE HANDLER = ? ORDER BY HANDLER", new Object[] { handler } );
	}

	/** 
	 * Returns all rows from the PROCESS_CHAIN table that match the criteria 'FEATURE_ID = :featureId'.
	 */
	public ProcessChain[] findWhereFeatureIdEquals(int featureId) throws ProcessChainDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE FEATURE_ID = ? ORDER BY FEATURE_ID", new Object[] {  new Integer(featureId) } );
	}

	/**
	 * Method 'ProcessChainDaoImpl'
	 * 
	 */
	public ProcessChainDaoImpl()
	{
	}

	/**
	 * Method 'ProcessChainDaoImpl'
	 * 
	 * @param userConn
	 */
	public ProcessChainDaoImpl(final java.sql.Connection userConn)
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
		return "PROCESS_CHAIN";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected ProcessChain fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			ProcessChain dto = new ProcessChain();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected ProcessChain[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection resultList = new ArrayList();
		while (rs.next()) {
			ProcessChain dto = new ProcessChain();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		ProcessChain ret[] = new ProcessChain[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(ProcessChain dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setProcName( rs.getString( COLUMN_PROC_NAME ) );
		dto.setApprovalChain( rs.getString( COLUMN_APPROVAL_CHAIN ) );
		dto.setNotification( rs.getString( COLUMN_NOTIFICATION ) );
		dto.setHandler( rs.getString( COLUMN_HANDLER ) );
		dto.setFeatureId( rs.getInt( COLUMN_FEATURE_ID ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(ProcessChain dto)
	{
	}

	/** 
	 * Returns all rows from the PROCESS_CHAIN table that match the specified arbitrary SQL statement
	 */
	public ProcessChain[] findByDynamicSelect(String sql, Object[] sqlParams) throws ProcessChainDaoException
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
			throw new ProcessChainDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the PROCESS_CHAIN table that match the specified arbitrary SQL statement
	 */
	public ProcessChain[] findByDynamicWhere(String sql, Object[] sqlParams) throws ProcessChainDaoException
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
			throw new ProcessChainDaoException( "Exception: " + _e.getMessage(), _e );
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
