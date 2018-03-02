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
import com.dikshatech.portal.dao.ExitDeclarationDao;
import com.dikshatech.portal.dto.ExitDeclaration;
import com.dikshatech.portal.dto.ExitDeclarationPk;
import com.dikshatech.portal.exceptions.ExitDeclarationDaoException;

public class ExitDeclarationDaoImpl extends AbstractDAO implements ExitDeclarationDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( ExitDeclarationDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, EXIT_ID, INVENTIONS, SUBMITTED_ON FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, EXIT_ID, INVENTIONS, SUBMITTED_ON ) VALUES ( ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, EXIT_ID = ?, INVENTIONS = ?, SUBMITTED_ON = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column EXIT_ID
	 */
	protected static final int COLUMN_EXIT_ID = 2;

	/** 
	 * Index of column INVENTIONS
	 */
	protected static final int COLUMN_INVENTIONS = 3;

	/** 
	 * Index of column SUBMITTED_ON
	 */
	protected static final int COLUMN_SUBMITTED_ON = 4;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 4;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the exit_declaration table.
	 */
	public ExitDeclarationPk insert(ExitDeclaration dto) throws ExitDeclarationDaoException
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
			stmt.setInt( index++, dto.getExitId() );
			stmt.setString( index++, dto.getInventions() );
			stmt.setTimestamp(index++, dto.getSubmittedOn()==null ? null : new java.sql.Timestamp( dto.getSubmittedOn().getTime() ) );
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
			throw new ExitDeclarationDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the exit_declaration table.
	 */
	public void update(ExitDeclarationPk pk, ExitDeclaration dto) throws ExitDeclarationDaoException
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
			stmt.setInt( index++, dto.getExitId() );
			stmt.setString( index++, dto.getInventions() );
			stmt.setTimestamp(index++, dto.getSubmittedOn()==null ? null : new java.sql.Timestamp( dto.getSubmittedOn().getTime() ) );
			stmt.setInt( 5, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new ExitDeclarationDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the exit_declaration table.
	 */
	public void delete(ExitDeclarationPk pk) throws ExitDeclarationDaoException
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
			throw new ExitDeclarationDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the exit_declaration table that matches the specified primary-key value.
	 */
	public ExitDeclaration findByPrimaryKey(ExitDeclarationPk pk) throws ExitDeclarationDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the exit_declaration table that match the criteria 'ID = :id'.
	 */
	public ExitDeclaration findByPrimaryKey(int id) throws ExitDeclarationDaoException
	{
		ExitDeclaration ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the exit_declaration table that match the criteria 'EXIT_ID = :exitId'.
	 */
	public ExitDeclaration findWhereExitIdEquals(Integer exitId) throws ExitDeclarationDaoException
	{
		ExitDeclaration ret[] = findByDynamicSelect( SQL_SELECT + " WHERE EXIT_ID = ?", new Object[] { exitId } );
		return ret.length==0 ? null : ret[0];
	}

	/**
	 * Method 'ExitDeclarationDaoImpl'
	 * 
	 */
	public ExitDeclarationDaoImpl()
	{
	}

	/**
	 * Method 'ExitDeclarationDaoImpl'
	 * 
	 * @param userConn
	 */
	public ExitDeclarationDaoImpl(final java.sql.Connection userConn)
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
		return "EXIT_DECLARATION";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected ExitDeclaration fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			ExitDeclaration dto = new ExitDeclaration();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected ExitDeclaration[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<ExitDeclaration> resultList = new ArrayList<ExitDeclaration>();
		while (rs.next()) {
			ExitDeclaration dto = new ExitDeclaration();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		ExitDeclaration ret[] = new ExitDeclaration[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(ExitDeclaration dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setExitId( rs.getInt( COLUMN_EXIT_ID ) );
		dto.setInventions( rs.getString( COLUMN_INVENTIONS ) );
		dto.setSubmittedOn( rs.getTimestamp(COLUMN_SUBMITTED_ON ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(ExitDeclaration dto)
	{
	}

	/** 
	 * Returns all rows from the exit_declaration table that match the specified arbitrary SQL statement
	 */
	public ExitDeclaration[] findByDynamicSelect(String sql, Object[] sqlParams) throws ExitDeclarationDaoException
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
			throw new ExitDeclarationDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the exit_declaration table that match the specified arbitrary SQL statement
	 */
	public ExitDeclaration[] findByDynamicWhere(String sql, Object[] sqlParams) throws ExitDeclarationDaoException
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
			throw new ExitDeclarationDaoException( "Exception: " + _e.getMessage(), _e );
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
