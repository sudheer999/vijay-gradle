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
import com.dikshatech.portal.dao.ProjHistoryDao;
import com.dikshatech.portal.dto.ProjHistory;
import com.dikshatech.portal.dto.ProjHistoryPk;
import com.dikshatech.portal.exceptions.ProjHistoryDaoException;

public class ProjHistoryDaoImpl extends AbstractDAO implements ProjHistoryDao
{
	protected static final Logger	logger				= Logger.getLogger(ProjHistoryDaoImpl.class);
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
	protected final String SQL_SELECT = "SELECT ID, PROJ_ID, LAST_MODIFIED_BY, MODIFIED_TIME FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, PROJ_ID, LAST_MODIFIED_BY, MODIFIED_TIME ) VALUES ( ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, PROJ_ID = ?, LAST_MODIFIED_BY = ?, MODIFIED_TIME = ? WHERE ID = ?";

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
	 * Index of column LAST_MODIFIED_BY
	 */
	protected static final int COLUMN_LAST_MODIFIED_BY = 3;

	/** 
	 * Index of column MODIFIED_TIME
	 */
	protected static final int COLUMN_MODIFIED_TIME = 4;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 4;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the PROJ_HISTORY table.
	 */
	public ProjHistoryPk insert(ProjHistory dto) throws ProjHistoryDaoException
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
			stmt.setInt( index++, dto.getLastModifiedBy() );
			stmt.setTimestamp(index++, dto.getModifiedTime()==null ? null : new java.sql.Timestamp( dto.getModifiedTime().getTime() ) );
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
			throw new ProjHistoryDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the PROJ_HISTORY table.
	 */
	public void update(ProjHistoryPk pk, ProjHistory dto) throws ProjHistoryDaoException
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
			stmt.setInt( index++, dto.getLastModifiedBy() );
			stmt.setTimestamp(index++, dto.getModifiedTime()==null ? null : new java.sql.Timestamp( dto.getModifiedTime().getTime() ) );
			stmt.setInt( 5, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			logger.debug( rows + " rows affected (" + (t2-t1) + " ms)" );
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new ProjHistoryDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the PROJ_HISTORY table.
	 */
	public void delete(ProjHistoryPk pk) throws ProjHistoryDaoException
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
			throw new ProjHistoryDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the PROJ_HISTORY table that matches the specified primary-key value.
	 */
	public ProjHistory findByPrimaryKey(ProjHistoryPk pk) throws ProjHistoryDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the PROJ_HISTORY table that match the criteria 'ID = :id'.
	 */
	public ProjHistory findByPrimaryKey(int id) throws ProjHistoryDaoException
	{
		ProjHistory ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the PROJ_HISTORY table that match the criteria ''.
	 */
	public ProjHistory[] findAll() throws ProjHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the PROJ_HISTORY table that match the criteria 'ID = :id'.
	 */
	public ProjHistory[] findWhereIdEquals(int id) throws ProjHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the PROJ_HISTORY table that match the criteria 'PROJ_ID = :projId'.
	 */
	public ProjHistory[] findWhereProjIdEquals(int projId) throws ProjHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PROJ_ID = ? ORDER BY PROJ_ID", new Object[] {  new Integer(projId) } );
	}

	/** 
	 * Returns all rows from the PROJ_HISTORY table that match the criteria 'LAST_MODIFIED_BY = :lastModifiedBy'.
	 */
	public ProjHistory[] findWhereLastModifiedByEquals(int lastModifiedBy) throws ProjHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE LAST_MODIFIED_BY = ? ORDER BY LAST_MODIFIED_BY", new Object[] {  new Integer(lastModifiedBy) } );
	}

	/** 
	 * Returns all rows from the PROJ_HISTORY table that match the criteria 'MODIFIED_TIME = :modifiedTime'.
	 */
	public ProjHistory[] findWhereModifiedTimeEquals(Date modifiedTime) throws ProjHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE MODIFIED_TIME = ? ORDER BY MODIFIED_TIME", new Object[] { modifiedTime==null ? null : new java.sql.Timestamp( modifiedTime.getTime() ) } );
	}

	/**
	 * Method 'ProjHistoryDaoImpl'
	 * 
	 */
	public ProjHistoryDaoImpl()
	{
	}

	/**
	 * Method 'ProjHistoryDaoImpl'
	 * 
	 * @param userConn
	 */
	public ProjHistoryDaoImpl(final java.sql.Connection userConn)
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
		return "PROJ_HISTORY";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected ProjHistory fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			ProjHistory dto = new ProjHistory();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected ProjHistory[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<ProjHistory> resultList = new ArrayList<ProjHistory>();
		while (rs.next()) {
			ProjHistory dto = new ProjHistory();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		ProjHistory ret[] = new ProjHistory[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(ProjHistory dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setProjId( rs.getInt( COLUMN_PROJ_ID ) );
		dto.setLastModifiedBy( rs.getInt( COLUMN_LAST_MODIFIED_BY ) );
		dto.setModifiedTime( rs.getTimestamp(COLUMN_MODIFIED_TIME ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(ProjHistory dto)
	{
	}

	/** 
	 * Returns all rows from the PROJ_HISTORY table that match the specified arbitrary SQL statement
	 */
	public ProjHistory[] findByDynamicSelect(String sql, Object[] sqlParams) throws ProjHistoryDaoException
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
			throw new ProjHistoryDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the PROJ_HISTORY table that match the specified arbitrary SQL statement
	 */
	public ProjHistory[] findByDynamicWhere(String sql, Object[] sqlParams) throws ProjHistoryDaoException
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
			throw new ProjHistoryDaoException( "Exception: " + _e.getMessage(), _e );
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
