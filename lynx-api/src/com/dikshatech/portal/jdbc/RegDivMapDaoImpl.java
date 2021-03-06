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

import com.dikshatech.portal.dao.RegDivMapDao;
import com.dikshatech.portal.dto.RegDivMap;
import com.dikshatech.portal.dto.RegDivMapPk;
import com.dikshatech.portal.exceptions.RegDivMapDaoException;

public class RegDivMapDaoImpl extends AbstractDAO implements RegDivMapDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( RegDivMapDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, REGION_ID, DIVISION_ID FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, REGION_ID, DIVISION_ID ) VALUES ( ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, REGION_ID = ?, DIVISION_ID = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column REGION_ID
	 */
	protected static final int COLUMN_REGION_ID = 2;

	/** 
	 * Index of column DIVISION_ID
	 */
	protected static final int COLUMN_DIVISION_ID = 3;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 3;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the REG_DIV_MAP table.
	 */
	public RegDivMapPk insert(RegDivMap dto) throws RegDivMapDaoException
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
			stmt.setInt( index++, dto.getRegionId() );
			stmt.setInt( index++, dto.getDivisionId() );
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
			throw new RegDivMapDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the REG_DIV_MAP table.
	 */
	public void update(RegDivMapPk pk, RegDivMap dto) throws RegDivMapDaoException
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
			stmt.setInt( index++, dto.getRegionId() );
			stmt.setInt( index++, dto.getDivisionId() );
			stmt.setInt( 4, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new RegDivMapDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the REG_DIV_MAP table.
	 */
	public void delete(RegDivMapPk pk) throws RegDivMapDaoException
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
			throw new RegDivMapDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the REG_DIV_MAP table that matches the specified primary-key value.
	 */
	public RegDivMap findByPrimaryKey(RegDivMapPk pk) throws RegDivMapDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the REG_DIV_MAP table that match the criteria 'ID = :id'.
	 */
	public RegDivMap findByPrimaryKey(int id) throws RegDivMapDaoException
	{
		RegDivMap ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the REG_DIV_MAP table that match the criteria 'DIVISION_ID = :divisionId'.
	 */
	public RegDivMap[] findByDivison(int divisionId) throws RegDivMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE DIVISION_ID = ?", new Object[] {  new Integer(divisionId) } );
	}

	/** 
	 * Returns all rows from the REG_DIV_MAP table that match the criteria 'REGION_ID = :regionId'.
	 */
	public RegDivMap[] findByRegions(int regionId) throws RegDivMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE REGION_ID = ?", new Object[] {  new Integer(regionId) } );
	}

	/** 
	 * Returns all rows from the REG_DIV_MAP table that match the criteria ''.
	 */
	public RegDivMap[] findAll() throws RegDivMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the REG_DIV_MAP table that match the criteria 'ID = :id'.
	 */
	public RegDivMap[] findWhereIdEquals(int id) throws RegDivMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the REG_DIV_MAP table that match the criteria 'REGION_ID = :regionId'.
	 */
	public RegDivMap[] findWhereRegionIdEquals(int regionId) throws RegDivMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE REGION_ID = ? ORDER BY REGION_ID", new Object[] {  new Integer(regionId) } );
	}

	/** 
	 * Returns all rows from the REG_DIV_MAP table that match the criteria 'DIVISION_ID = :divisionId'.
	 */
	public RegDivMap[] findWhereDivisionIdEquals(int divisionId) throws RegDivMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE DIVISION_ID = ? ORDER BY DIVISION_ID", new Object[] {  new Integer(divisionId) } );
	}
	
	/** 
	 * Returns all rows from the REG_DIV_MAP table that match the criteria 'REGION_ID = :regionId AND DIVISION_ID = :divisionId'.
	 */
	public RegDivMap findByRegDiv(int regionId, int divisionId) throws RegDivMapDaoException
	{
		RegDivMap ret[] = findByDynamicSelect( SQL_SELECT + " WHERE REGION_ID = ? AND DIVISION_ID = ?", new Object[] {  new Integer(regionId),  new Integer(divisionId) } );
		return ret.length==0 ? null : ret[0];
	}

	/**
	 * Method 'RegDivMapDaoImpl'
	 * 
	 */
	public RegDivMapDaoImpl()
	{
	}

	/**
	 * Method 'RegDivMapDaoImpl'
	 * 
	 * @param userConn
	 */
	public RegDivMapDaoImpl(final java.sql.Connection userConn)
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
		return "REG_DIV_MAP";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected RegDivMap fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			RegDivMap dto = new RegDivMap();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected RegDivMap[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<RegDivMap> resultList = new ArrayList<RegDivMap>();
		while (rs.next()) {
			RegDivMap dto = new RegDivMap();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		RegDivMap ret[] = new RegDivMap[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(RegDivMap dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setRegionId( rs.getInt( COLUMN_REGION_ID ) );
		dto.setDivisionId( rs.getInt( COLUMN_DIVISION_ID ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(RegDivMap dto)
	{
	}

	/** 
	 * Returns all rows from the REG_DIV_MAP table that match the specified arbitrary SQL statement
	 */
	public RegDivMap[] findByDynamicSelect(String sql, Object[] sqlParams) throws RegDivMapDaoException
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
			throw new RegDivMapDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the REG_DIV_MAP table that match the specified arbitrary SQL statement
	 */
	public RegDivMap[] findByDynamicWhere(String sql, Object[] sqlParams) throws RegDivMapDaoException
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
			throw new RegDivMapDaoException( "Exception: " + _e.getMessage(), _e );
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
