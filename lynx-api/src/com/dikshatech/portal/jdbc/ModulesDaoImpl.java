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

public class ModulesDaoImpl extends AbstractDAO implements ModulesDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( ModulesDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, NAME, PARENT_ID, IS_PROC_CHAIN, PERMISSION_TYPES FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, NAME, PARENT_ID, IS_PROC_CHAIN, PERMISSION_TYPES ) VALUES ( ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, NAME = ?, PARENT_ID = ?, IS_PROC_CHAIN = ?, PERMISSION_TYPES = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column NAME
	 */
	protected static final int COLUMN_NAME = 2;

	/** 
	 * Index of column PARENT_ID
	 */
	protected static final int COLUMN_PARENT_ID = 3;

	/** 
	 * Index of column IS_PROC_CHAIN
	 */
	protected static final int COLUMN_IS_PROC_CHAIN = 4;

	/** 
	 * Index of column PERMISSION_TYPES
	 */
	protected static final int COLUMN_PERMISSION_TYPES = 5;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 5;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the MODULES table.
	 */
	public ModulesPk insert(Modules dto) throws ModulesDaoException
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
			stmt.setString( index++, dto.getName() );
			if (dto.isParentIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getParentId() );
			}
		
			stmt.setInt( index++, dto.getIsProcChain() );
			stmt.setString( index++, dto.getPermissionTypes() );
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
			throw new ModulesDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the MODULES table.
	 */
	public void update(ModulesPk pk, Modules dto) throws ModulesDaoException
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
			stmt.setString( index++, dto.getName() );
			if (dto.isParentIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getParentId() );
			}
		
			stmt.setInt( index++, dto.getIsProcChain() );
			stmt.setString( index++, dto.getPermissionTypes() );
			stmt.setInt( 6, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new ModulesDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the MODULES table.
	 */
	public void delete(ModulesPk pk) throws ModulesDaoException
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
			throw new ModulesDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the MODULES table that matches the specified primary-key value.
	 */
	public Modules findByPrimaryKey(ModulesPk pk) throws ModulesDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the MODULES table that match the criteria 'ID = :id'.
	 */
	public Modules findByPrimaryKey(int id) throws ModulesDaoException
	{
		Modules ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the MODULES table that match the criteria ''.
	 */
	public Modules[] findAll() throws ModulesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the MODULES table that match the criteria 'ID = :id'.
	 */
	public Modules[] findWhereIdEquals(int id) throws ModulesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the MODULES table that match the criteria 'NAME = :name'.
	 */
	public Modules[] findWhereNameEquals(String name) throws ModulesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE NAME = ? ORDER BY NAME", new Object[] { name } );
	}

	/** 
	 * Returns all rows from the MODULES table that match the criteria 'PARENT_ID = :parentId'.
	 */
	public Modules[] findWhereParentIdEquals(int parentId) throws ModulesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PARENT_ID = ? ORDER BY PARENT_ID", new Object[] {  new Integer(parentId) } );
	}

	/** 
	 * Returns all rows from the MODULES table that match the criteria 'IS_PROC_CHAIN = :isProcChain'.
	 */
	public Modules[] findWhereIsProcChainEquals(int isProcChain) throws ModulesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE IS_PROC_CHAIN = ? ORDER BY IS_PROC_CHAIN", new Object[] {  new Integer(isProcChain) } );
	}

	/** 
	 * Returns all rows from the MODULES table that match the criteria 'PERMISSION_TYPES = :permissionTypes'.
	 */
	public Modules[] findWherePermissionTypesEquals(String permissionTypes) throws ModulesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PERMISSION_TYPES = ? ORDER BY PERMISSION_TYPES", new Object[] { permissionTypes } );
	}

	/**
	 * Method 'ModulesDaoImpl'
	 * 
	 */
	public ModulesDaoImpl()
	{
	}

	/**
	 * Method 'ModulesDaoImpl'
	 * 
	 * @param userConn
	 */
	public ModulesDaoImpl(final java.sql.Connection userConn)
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
		return "MODULES";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected Modules fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			Modules dto = new Modules();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected Modules[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<Modules> resultList = new ArrayList<Modules>();
		while (rs.next()) {
			Modules dto = new Modules();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		Modules ret[] = new Modules[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(Modules dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setName( rs.getString( COLUMN_NAME ) );
		dto.setParentId( rs.getInt( COLUMN_PARENT_ID ) );
		if (rs.wasNull()) {
			dto.setParentIdNull( true );
		}
		
		dto.setIsProcChain( rs.getInt( COLUMN_IS_PROC_CHAIN ) );
		dto.setPermissionTypes( rs.getString( COLUMN_PERMISSION_TYPES ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(Modules dto)
	{
	}

	/** 
	 * Returns all rows from the MODULES table that match the specified arbitrary SQL statement
	 */
	public Modules[] findByDynamicSelect(String sql, Object[] sqlParams) throws ModulesDaoException
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
			throw new ModulesDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the MODULES table that match the specified arbitrary SQL statement
	 */
	public Modules[] findByDynamicWhere(String sql, Object[] sqlParams) throws ModulesDaoException
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
			throw new ModulesDaoException( "Exception: " + _e.getMessage(), _e );
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
