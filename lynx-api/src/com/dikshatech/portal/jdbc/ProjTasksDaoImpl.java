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
import com.dikshatech.portal.dao.ProjTasksDao;
import com.dikshatech.portal.dto.ProjTasks;
import com.dikshatech.portal.dto.ProjTasksPk;
import com.dikshatech.portal.exceptions.ProjTasksDaoException;

public class ProjTasksDaoImpl extends AbstractDAO implements ProjTasksDao
{
	protected static final Logger	logger				= Logger.getLogger(ProjTasksDaoImpl.class);
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, PROJ_ID, TASKS FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, PROJ_ID, TASKS ) VALUES ( ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, PROJ_ID = ?, TASKS = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column PROJ_ID
	 */
	protected static final int COLUMN_PROJ_ID = 2;

	/** 
	 * Index of column TASKS
	 */
	protected static final int COLUMN_TASKS = 3;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 3;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the PROJ_TASKS table.
	 */
	public ProjTasksPk insert(ProjTasks dto) throws ProjTasksDaoException
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
			stmt.setInt( index++, dto.getProjId() );
			stmt.setString( index++, dto.getTasks() );
			logger.debug("Executing " + SQL_INSERT + " with DTO: " + dto );
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			logger.debug( rows + " rows affected (" + (t2-t1) + " ms)" );
		
			// retrieve values from auto-increment columns
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setId( rs.getInt( 1 ) );
			}
		
			reset(dto);
			return dto.createPk();
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new ProjTasksDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the PROJ_TASKS table.
	 */
	public void update(ProjTasksPk pk, ProjTasks dto) throws ProjTasksDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			logger.debug("Executing " + SQL_UPDATE + " with DTO: " + dto );
			stmt = conn.prepareStatement( SQL_UPDATE );
			int index=1;
			stmt.setInt( index++, dto.getId() );
			stmt.setInt( index++, dto.getProjId() );
			stmt.setString( index++, dto.getTasks() );
			stmt.setInt( 4, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			logger.debug( rows + " rows affected (" + (t2-t1) + " ms)" );
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new ProjTasksDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the PROJ_TASKS table.
	 */
	public void delete(ProjTasksPk pk) throws ProjTasksDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			logger.debug("Executing " + SQL_DELETE + " with PK: " + pk );
			stmt = conn.prepareStatement( SQL_DELETE );
			stmt.setInt( 1, pk.getId() );
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			logger.debug( rows + " rows affected (" + (t2-t1) + " ms)" );
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new ProjTasksDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the PROJ_TASKS table that matches the specified primary-key value.
	 */
	public ProjTasks findByPrimaryKey(ProjTasksPk pk) throws ProjTasksDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the PROJ_TASKS table that match the criteria 'ID = :id'.
	 */
	public ProjTasks findByPrimaryKey(int id) throws ProjTasksDaoException
	{
		ProjTasks ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the PROJ_TASKS table that match the criteria ''.
	 */
	public ProjTasks[] findAll() throws ProjTasksDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the PROJ_TASKS table that match the criteria 'ID = :id'.
	 */
	public ProjTasks[] findWhereIdEquals(int id) throws ProjTasksDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the PROJ_TASKS table that match the criteria 'PROJ_ID = :projId'.
	 */
	public ProjTasks[] findWhereProjIdEquals(int projId) throws ProjTasksDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PROJ_ID = ? ORDER BY PROJ_ID", new Object[] {  new Integer(projId) } );
	}

	/** 
	 * Returns all rows from the PROJ_TASKS table that match the criteria 'TASKS = :tasks'.
	 */
	public ProjTasks[] findWhereTasksEquals(String tasks) throws ProjTasksDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE TASKS = ? ORDER BY TASKS", new Object[] { tasks } );
	}

	/**
	 * Method 'ProjTasksDaoImpl'
	 * 
	 */
	public ProjTasksDaoImpl()
	{
	}

	/**
	 * Method 'ProjTasksDaoImpl'
	 * 
	 * @param userConn
	 */
	public ProjTasksDaoImpl(final java.sql.Connection userConn)
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
		return "PROJ_TASKS";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected ProjTasks fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			ProjTasks dto = new ProjTasks();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected ProjTasks[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<ProjTasks> resultList = new ArrayList<ProjTasks>();
		while (rs.next()) {
			ProjTasks dto = new ProjTasks();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		ProjTasks ret[] = new ProjTasks[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(ProjTasks dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setProjId( rs.getInt( COLUMN_PROJ_ID ) );
		dto.setTasks( rs.getString( COLUMN_TASKS ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(ProjTasks dto)
	{
	}

	/** 
	 * Returns all rows from the PROJ_TASKS table that match the specified arbitrary SQL statement
	 */
	public ProjTasks[] findByDynamicSelect(String sql, Object[] sqlParams) throws ProjTasksDaoException
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
		
		
			logger.debug("Executing " + SQL );
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
			_e.printStackTrace();
			throw new ProjTasksDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the PROJ_TASKS table that match the specified arbitrary SQL statement
	 */
	public ProjTasks[] findByDynamicWhere(String sql, Object[] sqlParams) throws ProjTasksDaoException
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
		
		
			logger.debug("Executing " + SQL );
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
			_e.printStackTrace();
			throw new ProjTasksDaoException( "Exception: " + _e.getMessage(), _e );
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
    public boolean deleteAllTaskByProject(int projectId)
    {


		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean isDone = false;
		String SQL = null;
	
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			// Delete All Data in Project_div_Map By Project
			SQL = "DELETE FROM PROJ_TASKS WHERE PROJ_ID="+projectId+"";
			stmt = conn.prepareStatement( SQL );
			stmt.executeUpdate();
			
			
			stmt = conn.prepareStatement( SQL );
			stmt.executeUpdate();
			isDone = true;
			
		}catch(Exception e)
		{
			isDone = false;
			e.printStackTrace();
		}
		finally
		{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return isDone;
	
    }
	
}
