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

import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.LevelsPk;
import com.dikshatech.portal.exceptions.LevelsDaoException;

public class LevelsDaoImpl extends AbstractDAO implements LevelsDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( LevelsDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT L.ID, LABEL, DESIGNATION, DIVISION_ID, D.NAME FROM " + getTableName() + " L LEFT JOIN DIVISON D ON D.ID = DIVISION_ID";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, LABEL, DESIGNATION, DIVISION_ID ) VALUES ( ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, LABEL = ?, DESIGNATION = ?, DIVISION_ID = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column LABEL
	 */
	protected static final int COLUMN_LABEL = 2;

	/** 
	 * Index of column DESIGNATION
	 */
	protected static final int COLUMN_DESIGNATION = 3;

	/** 
	 * Index of column DIVISION_ID
	 */
	protected static final int COLUMN_DIVISION_ID = 4;
	
	/** 
	 * Index of column DIVISION_ID
	 */
	protected static final int COLUMN_DIVISION_NAME = 5;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 5;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the LEVELS table.
	 */
	public LevelsPk insert(Levels dto) throws LevelsDaoException
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
			stmt.setString( index++, dto.getLabel() );
			stmt.setString( index++, dto.getDesignation() );
			if (dto.isDivisionIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getDivisionId() );
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
			throw new LevelsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the LEVELS table.
	 */
	public void update(LevelsPk pk, Levels dto) throws LevelsDaoException
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
			stmt.setString( index++, dto.getLabel() );
			stmt.setString( index++, dto.getDesignation() );
			if (dto.isDivisionIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getDivisionId() );
			}
		
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
			throw new LevelsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the LEVELS table.
	 */
	public void delete(LevelsPk pk) throws LevelsDaoException
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
			throw new LevelsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the LEVELS table that matches the specified primary-key value.
	 */
	public Levels findByPrimaryKey(LevelsPk pk) throws LevelsDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the LEVELS table that match the criteria 'ID = :id'.
	 */
	public Levels findByPrimaryKey(int id) throws LevelsDaoException
	{
		Levels ret[] = findByDynamicSelect( SQL_SELECT + " WHERE L.ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the LEVELS table that match the criteria ''.
	 */
	public Levels[] findAll() throws LevelsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY L.ID", null );
	}

	/** 
	 * Returns all rows from the LEVELS table that match the criteria 'DIVISION_ID = :divisionId'.
	 */
	public Levels[] findByDivison(int divisionId) throws LevelsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE DIVISION_ID = ?", new Object[] {  new Integer(divisionId) } );
	}

	/** 
	 * Returns all rows from the LEVELS table that match the criteria 'ID = :id'.
	 */
	public Levels[] findWhereIdEquals(int id) throws LevelsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE L.ID = ? ORDER BY L.ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the LEVELS table that match the criteria 'LABEL = :label'.
	 */
	public Levels[] findWhereLabelEquals(String label) throws LevelsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE LABEL = ? ORDER BY LABEL", new Object[] { label } );
	}

	/** 
	 * Returns all rows from the LEVELS table that match the criteria 'DESIGNATION = :designation'.
	 */
	public Levels[] findWhereDesignationEquals(String designation) throws LevelsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE DESIGNATION = ? ORDER BY DESIGNATION", new Object[] { designation } );
	}

	/** 
	 * Returns all rows from the LEVELS table that match the criteria 'DIVISION_ID = :divisionId'.
	 */
	public Levels[] findWhereDivisionIdEquals(int divisionId) throws LevelsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE DIVISION_ID = ? ORDER BY DIVISION_ID", new Object[] {  new Integer(divisionId) } );
	}

	/**
	 * Method 'LevelsDaoImpl'
	 * 
	 */
	public LevelsDaoImpl()
	{
	}

	/**
	 * Method 'LevelsDaoImpl'
	 * 
	 * @param userConn
	 */
	public LevelsDaoImpl(final java.sql.Connection userConn)
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
		return "LEVELS";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected Levels fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			Levels dto = new Levels();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected Levels[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<Levels> resultList = new ArrayList<Levels>();
		while (rs.next()) {
			Levels dto = new Levels();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		Levels ret[] = new Levels[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(Levels dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setLabel( rs.getString( COLUMN_LABEL ) );
		dto.setDesignation( rs.getString( COLUMN_DESIGNATION ) );
		dto.setDivisionId( rs.getInt( COLUMN_DIVISION_ID ) );
		if (rs.wasNull()) {
			dto.setDivisionIdNull( true );
		}
		//dto.setDivisionName( rs.getString( COLUMN_DIVISION_NAME ) );
		
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(Levels dto)
	{
	}

	/** 
	 * Returns all rows from the LEVELS table that match the specified arbitrary SQL statement
	 */
	public Levels[] findByDynamicSelect(String sql, Object[] sqlParams) throws LevelsDaoException
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
			throw new LevelsDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the LEVELS table that match the specified arbitrary SQL statement
	 */
	public Levels[] findByDynamicWhere(String sql, Object[] sqlParams) throws LevelsDaoException
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
			throw new LevelsDaoException( "Exception: " + _e.getMessage(), _e );
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
	public int findWhereUsersIdEquals(int usid) throws LevelsDaoException {
		// TODO Auto-generated method stub
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = "SELECT LEVEL_ID FROM USERS where ID=?";
		
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL);
			}
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
			stmt.setObject(1, usid);
			rs = stmt.executeQuery();
			
			Collection<Levels> resultList = new ArrayList<Levels>();
			int levelid=0;
			while (rs.next()) {
				Levels dto = new Levels();
				
				dto.setId( rs.getInt( COLUMN_ID ) );
				resultList.add( dto );
				
			 levelid = dto.getId();
			}
		
			return levelid;
			

		}
		
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new LevelsDaoException( "Exception: " + _e.getMessage(), _e );
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
