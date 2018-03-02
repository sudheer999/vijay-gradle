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
import com.dikshatech.portal.dao.ProjClientMapDao;
import com.dikshatech.portal.dto.ProjClientMap;
import com.dikshatech.portal.dto.ProjClientMapPk;
import com.dikshatech.portal.exceptions.ProjClientMapDaoException;

public class ProjClientMapDaoImpl extends AbstractDAO implements ProjClientMapDao
{
	protected static final Logger	logger				= Logger.getLogger(ProjClientMapDaoImpl.class);
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
	protected final String SQL_SELECT = "SELECT ID, PROJ_ID, CLIENT_ID FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, PROJ_ID, CLIENT_ID ) VALUES ( ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, PROJ_ID = ?, CLIENT_ID = ? WHERE ID = ?";

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
	 * Index of column CLIENT_ID
	 */
	protected static final int COLUMN_CLIENT_ID = 3;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 3;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the PROJ_CLIENT_MAP table.
	 */
	public ProjClientMapPk insert(ProjClientMap dto) throws ProjClientMapDaoException
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
			stmt.setInt( index++, dto.getClientId() );
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
			throw new ProjClientMapDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the PROJ_CLIENT_MAP table.
	 */
	public void update(ProjClientMapPk pk, ProjClientMap dto) throws ProjClientMapDaoException
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
			stmt.setInt( index++, dto.getClientId() );
			stmt.setInt( 4, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			logger.debug( rows + " rows affected (" + (t2-t1) + " ms)" );
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new ProjClientMapDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the PROJ_CLIENT_MAP table.
	 */
	public void delete(ProjClientMapPk pk) throws ProjClientMapDaoException
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
			throw new ProjClientMapDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the PROJ_CLIENT_MAP table that matches the specified primary-key value.
	 */
	public ProjClientMap findByPrimaryKey(ProjClientMapPk pk) throws ProjClientMapDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the PROJ_CLIENT_MAP table that match the criteria 'ID = :id'.
	 */
	public ProjClientMap findByPrimaryKey(int id) throws ProjClientMapDaoException
	{
		ProjClientMap ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the PROJ_CLIENT_MAP table that match the criteria ''.
	 */
	public ProjClientMap[] findAll() throws ProjClientMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the PROJ_CLIENT_MAP table that match the criteria 'ID = :id'.
	 */
	public ProjClientMap[] findWhereIdEquals(int id) throws ProjClientMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the PROJ_CLIENT_MAP table that match the criteria 'PROJ_ID = :projId'.
	 */
	public ProjClientMap[] findWhereProjIdEquals(int projId) throws ProjClientMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PROJ_ID = ? ORDER BY PROJ_ID", new Object[] {  new Integer(projId) } );
	}

	/** 
	 * Returns all rows from the PROJ_CLIENT_MAP table that match the criteria 'CLIENT_ID = :clientId'.
	 */
	public ProjClientMap[] findWhereClientIdEquals(int clientId) throws ProjClientMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE CLIENT_ID = ? ORDER BY CLIENT_ID", new Object[] {  new Integer(clientId) } );
	}

	/**
	 * Method 'ProjClientMapDaoImpl'
	 * 
	 */
	public ProjClientMapDaoImpl()
	{
	}

	/**
	 * Method 'ProjClientMapDaoImpl'
	 * 
	 * @param userConn
	 */
	public ProjClientMapDaoImpl(final java.sql.Connection userConn)
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
		return "PROJ_CLIENT_MAP";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected ProjClientMap fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			ProjClientMap dto = new ProjClientMap();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected ProjClientMap[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<ProjClientMap> resultList = new ArrayList<ProjClientMap>();
		while (rs.next()) {
			ProjClientMap dto = new ProjClientMap();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		ProjClientMap ret[] = new ProjClientMap[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(ProjClientMap dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setProjId( rs.getInt( COLUMN_PROJ_ID ) );
		dto.setClientId( rs.getInt( COLUMN_CLIENT_ID ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(ProjClientMap dto)
	{
	}

	/** 
	 * Returns all rows from the PROJ_CLIENT_MAP table that match the specified arbitrary SQL statement
	 */
	public ProjClientMap[] findByDynamicSelect(String sql, Object[] sqlParams) throws ProjClientMapDaoException
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
		
		
		//	logger.debug("Executing " + SQL );
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
			throw new ProjClientMapDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the PROJ_CLIENT_MAP table that match the specified arbitrary SQL statement
	 */
	public ProjClientMap[] findByDynamicWhere(String sql, Object[] sqlParams) throws ProjClientMapDaoException
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
		
		
		//	logger.debug("Executing " + SQL );
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
			throw new ProjClientMapDaoException( "Exception: " + _e.getMessage(), _e );
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
