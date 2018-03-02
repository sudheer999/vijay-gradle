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
import com.dikshatech.portal.dao.ProjContInfoDao;
import com.dikshatech.portal.dto.ProjContInfo;
import com.dikshatech.portal.dto.ProjContInfoPk;
import com.dikshatech.portal.exceptions.ProjContInfoDaoException;

public class ProjContInfoDaoImpl extends AbstractDAO implements ProjContInfoDao
{
	protected static final Logger	logger				= Logger.getLogger(ProjContInfoDaoImpl.class);
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
	protected final String SQL_SELECT = "SELECT ID, PROJ_ID, NAME, CONT_NUMBER, E_MAIL, DESIGNATION, CONT_TYPE, COMMENTS FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, PROJ_ID, NAME, CONT_NUMBER, E_MAIL, DESIGNATION, CONT_TYPE, COMMENTS ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, PROJ_ID = ?, NAME = ?, CONT_NUMBER = ?, E_MAIL = ?, DESIGNATION = ?, CONT_TYPE = ?, COMMENTS = ? WHERE ID = ?";

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
	 * Index of column NAME
	 */
	protected static final int COLUMN_NAME = 3;

	/** 
	 * Index of column CONT_NUMBER
	 */
	protected static final int COLUMN_CONT_NUMBER = 4;

	/** 
	 * Index of column E_MAIL
	 */
	protected static final int COLUMN_E_MAIL = 5;

	/** 
	 * Index of column DESIGNATION
	 */
	protected static final int COLUMN_DESIGNATION = 6;

	/** 
	 * Index of column CONT_TYPE
	 */
	protected static final int COLUMN_CONT_TYPE = 7;

	/** 
	 * Index of column COMMENTS
	 */
	protected static final int COLUMN_COMMENTS = 8;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 8;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the PROJ_CONT_INFO table.
	 */
	public ProjContInfoPk insert(ProjContInfo dto) throws ProjContInfoDaoException
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
			stmt.setString( index++, dto.getName() );
			stmt.setString( index++, dto.getContNumber() );
			stmt.setString( index++, dto.getEMail() );
			stmt.setString( index++, dto.getDesignation() );
			stmt.setString( index++, dto.getContType() );
			stmt.setString( index++, dto.getComments() );
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
			throw new ProjContInfoDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the PROJ_CONT_INFO table.
	 */
	public void update(ProjContInfoPk pk, ProjContInfo dto) throws ProjContInfoDaoException
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
			stmt.setString( index++, dto.getName() );
			stmt.setString( index++, dto.getContNumber() );
			stmt.setString( index++, dto.getEMail() );
			stmt.setString( index++, dto.getDesignation() );
			stmt.setString( index++, dto.getContType() );
			stmt.setString( index++, dto.getComments() );
			stmt.setInt( 9, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			logger.debug( rows + " rows affected (" + (t2-t1) + " ms)" );
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new ProjContInfoDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the PROJ_CONT_INFO table.
	 */
	public void delete(ProjContInfoPk pk) throws ProjContInfoDaoException
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
			throw new ProjContInfoDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the PROJ_CONT_INFO table that matches the specified primary-key value.
	 */
	public ProjContInfo findByPrimaryKey(ProjContInfoPk pk) throws ProjContInfoDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the PROJ_CONT_INFO table that match the criteria 'ID = :id'.
	 */
	public ProjContInfo findByPrimaryKey(int id) throws ProjContInfoDaoException
	{
		ProjContInfo ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the PROJ_CONT_INFO table that match the criteria ''.
	 */
	public ProjContInfo[] findAll() throws ProjContInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the PROJ_CONT_INFO table that match the criteria 'ID = :id'.
	 */
	public ProjContInfo[] findWhereIdEquals(int id) throws ProjContInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the PROJ_CONT_INFO table that match the criteria 'PROJ_ID = :projId'.
	 */
	public ProjContInfo[] findWhereProjIdEquals(int projId) throws ProjContInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PROJ_ID = ? ORDER BY PROJ_ID", new Object[] {  new Integer(projId) } );
	}

	/** 
	 * Returns all rows from the PROJ_CONT_INFO table that match the criteria 'NAME = :name'.
	 */
	public ProjContInfo[] findWhereNameEquals(String name) throws ProjContInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE NAME = ? ORDER BY NAME", new Object[] { name } );
	}

	/** 
	 * Returns all rows from the PROJ_CONT_INFO table that match the criteria 'CONT_NUMBER = :contNumber'.
	 */
	public ProjContInfo[] findWhereContNumberEquals(String contNumber) throws ProjContInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE CONT_NUMBER = ? ORDER BY CONT_NUMBER", new Object[] { contNumber } );
	}

	/** 
	 * Returns all rows from the PROJ_CONT_INFO table that match the criteria 'E_MAIL = :eMail'.
	 */
	public ProjContInfo[] findWhereEMailEquals(String eMail) throws ProjContInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE E_MAIL = ? ORDER BY E_MAIL", new Object[] { eMail } );
	}

	/** 
	 * Returns all rows from the PROJ_CONT_INFO table that match the criteria 'DESIGNATION = :designation'.
	 */
	public ProjContInfo[] findWhereDesignationEquals(String designation) throws ProjContInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE DESIGNATION = ? ORDER BY DESIGNATION", new Object[] { designation } );
	}

	/** 
	 * Returns all rows from the PROJ_CONT_INFO table that match the criteria 'CONT_TYPE = :contType'.
	 */
	public ProjContInfo[] findWhereContTypeEquals(String contType) throws ProjContInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE CONT_TYPE = ? ORDER BY CONT_TYPE", new Object[] { contType } );
	}

	/** 
	 * Returns all rows from the PROJ_CONT_INFO table that match the criteria 'COMMENTS = :comments'.
	 */
	public ProjContInfo[] findWhereCommentsEquals(String comments) throws ProjContInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE COMMENTS = ? ORDER BY COMMENTS", new Object[] { comments } );
	}

	/**
	 * Method 'ProjContInfoDaoImpl'
	 * 
	 */
	public ProjContInfoDaoImpl()
	{
	}

	/**
	 * Method 'ProjContInfoDaoImpl'
	 * 
	 * @param userConn
	 */
	public ProjContInfoDaoImpl(final java.sql.Connection userConn)
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
		return "PROJ_CONT_INFO";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected ProjContInfo fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			ProjContInfo dto = new ProjContInfo();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected ProjContInfo[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<ProjContInfo> resultList = new ArrayList<ProjContInfo>();
		while (rs.next()) {
			ProjContInfo dto = new ProjContInfo();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		ProjContInfo ret[] = new ProjContInfo[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(ProjContInfo dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setProjId( rs.getInt( COLUMN_PROJ_ID ) );
		dto.setName( rs.getString( COLUMN_NAME ) );
		dto.setContNumber( rs.getString( COLUMN_CONT_NUMBER ) );
		dto.setEMail( rs.getString( COLUMN_E_MAIL ) );
		dto.setDesignation( rs.getString( COLUMN_DESIGNATION ) );
		dto.setContType( rs.getString( COLUMN_CONT_TYPE ) );
		dto.setComments( rs.getString( COLUMN_COMMENTS ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(ProjContInfo dto)
	{
	}

	/** 
	 * Returns all rows from the PROJ_CONT_INFO table that match the specified arbitrary SQL statement
	 */
	public ProjContInfo[] findByDynamicSelect(String sql, Object[] sqlParams) throws ProjContInfoDaoException
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
			throw new ProjContInfoDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the PROJ_CONT_INFO table that match the specified arbitrary SQL statement
	 */
	public ProjContInfo[] findByDynamicWhere(String sql, Object[] sqlParams) throws ProjContInfoDaoException
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
			throw new ProjContInfoDaoException( "Exception: " + _e.getMessage(), _e );
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