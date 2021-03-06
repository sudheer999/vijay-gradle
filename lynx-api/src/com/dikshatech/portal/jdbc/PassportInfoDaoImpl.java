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

import com.dikshatech.portal.dao.PassportInfoDao;
import com.dikshatech.portal.dto.PassportInfo;
import com.dikshatech.portal.dto.PassportInfoPk;
import com.dikshatech.portal.exceptions.PassportInfoDaoException;

public class PassportInfoDaoImpl extends AbstractDAO implements PassportInfoDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( PassportInfoDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, GIVENNAME, SURNAME, PASSPORT_NO, DATE_OF_ISSUE, DATE_OF_EXPIRE, PLACE_OF_ISSUE, PLACE_OF_BIRTH, MODIFIED_BY FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, GIVENNAME, SURNAME, PASSPORT_NO, DATE_OF_ISSUE, DATE_OF_EXPIRE, PLACE_OF_ISSUE, PLACE_OF_BIRTH, MODIFIED_BY ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, GIVENNAME = ?, SURNAME = ?, PASSPORT_NO = ?, DATE_OF_ISSUE = ?, DATE_OF_EXPIRE = ?, PLACE_OF_ISSUE = ?, PLACE_OF_BIRTH = ? , MODIFIED_BY= ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column GIVENNAME
	 */
	protected static final int COLUMN_GIVENNAME = 2;

	/** 
	 * Index of column SURNAME
	 */
	protected static final int COLUMN_SURNAME = 3;

	/** 
	 * Index of column PASSPORT_NO
	 */
	protected static final int COLUMN_PASSPORT_NO = 4;

	/** 
	 * Index of column DATE_OF_ISSUE
	 */
	protected static final int COLUMN_DATE_OF_ISSUE = 5;

	/** 
	 * Index of column DATE_OF_EXPIRE
	 */
	protected static final int COLUMN_DATE_OF_EXPIRE = 6;

	/** 
	 * Index of column PLACE_OF_ISSUE
	 */
	protected static final int COLUMN_PLACE_OF_ISSUE = 7;

	/** 
	 * Index of column PLACE_OF_BIRTH
	 */
	protected static final int COLUMN_PLACE_OF_BIRTH = 8;

	/** 
	 * Index of column PLACE_OF_BIRTH
	 */
	protected static final int COLUMN_MODIFIED_BY= 9;
	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 9;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the PASSPORT_INFO table.
	 */
	public PassportInfoPk insert(PassportInfo dto) throws PassportInfoDaoException
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
			stmt.setString( index++, dto.getGivenname() );
			stmt.setString( index++, dto.getSurname() );
			stmt.setString( index++, dto.getPassportNo() );
			stmt.setDate(index++, dto.getDateOfIssue()==null ? null : new java.sql.Date( dto.getDateOfIssue().getTime() ) );
			stmt.setDate(index++, dto.getDateOfExpire()==null ? null : new java.sql.Date( dto.getDateOfExpire().getTime() ) );
			stmt.setString( index++, dto.getPlaceOfIssue() );
			stmt.setString( index++, dto.getPlaceOfBirth() );
			stmt.setInt( index++, dto.getModifiedBy() );
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
			throw new PassportInfoDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the PASSPORT_INFO table.
	 */
	public void update(PassportInfoPk pk, PassportInfo dto) throws PassportInfoDaoException
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
			stmt.setString( index++, dto.getGivenname() );
			stmt.setString( index++, dto.getSurname() );
			stmt.setString( index++, dto.getPassportNo() );
			stmt.setDate(index++, dto.getDateOfIssue()==null ? null : new java.sql.Date( dto.getDateOfIssue().getTime() ) );
			stmt.setDate(index++, dto.getDateOfExpire()==null ? null : new java.sql.Date( dto.getDateOfExpire().getTime() ) );
			stmt.setString( index++, dto.getPlaceOfIssue() );
			stmt.setString( index++, dto.getPlaceOfBirth() );
			stmt.setInt( index++, dto.getModifiedBy() );
			stmt.setInt( 10, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new PassportInfoDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the PASSPORT_INFO table.
	 */
	public void delete(PassportInfoPk pk) throws PassportInfoDaoException
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
			throw new PassportInfoDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the PASSPORT_INFO table that matches the specified primary-key value.
	 */
	public PassportInfo findByPrimaryKey(PassportInfoPk pk) throws PassportInfoDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the PASSPORT_INFO table that match the criteria 'ID = :id'.
	 */
	public PassportInfo findByPrimaryKey(int id) throws PassportInfoDaoException
	{
		PassportInfo ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the PASSPORT_INFO table that match the criteria ''.
	 */
	public PassportInfo[] findAll() throws PassportInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the PASSPORT_INFO table that match the criteria 'ID = :id'.
	 */
	public PassportInfo[] findWhereIdEquals(int id) throws PassportInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the PASSPORT_INFO table that match the criteria 'GIVENNAME = :givenname'.
	 */
	public PassportInfo[] findWhereGivennameEquals(String givenname) throws PassportInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE GIVENNAME = ? ORDER BY GIVENNAME", new Object[] { givenname } );
	}

	/** 
	 * Returns all rows from the PASSPORT_INFO table that match the criteria 'SURNAME = :surname'.
	 */
	public PassportInfo[] findWhereSurnameEquals(String surname) throws PassportInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE SURNAME = ? ORDER BY SURNAME", new Object[] { surname } );
	}

	/** 
	 * Returns all rows from the PASSPORT_INFO table that match the criteria 'PASSPORT_NO = :passportNo'.
	 */
	public PassportInfo[] findWherePassportNoEquals(String passportNo) throws PassportInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PASSPORT_NO = ? ORDER BY PASSPORT_NO", new Object[] { passportNo } );
	}

	/** 
	 * Returns all rows from the PASSPORT_INFO table that match the criteria 'DATE_OF_ISSUE = :dateOfIssue'.
	 */
	public PassportInfo[] findWhereDateOfIssueEquals(Date dateOfIssue) throws PassportInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE DATE_OF_ISSUE = ? ORDER BY DATE_OF_ISSUE", new Object[] { dateOfIssue==null ? null : new java.sql.Date( dateOfIssue.getTime() ) } );
	}

	/** 
	 * Returns all rows from the PASSPORT_INFO table that match the criteria 'DATE_OF_EXPIRE = :dateOfExpire'.
	 */
	public PassportInfo[] findWhereDateOfExpireEquals(Date dateOfExpire) throws PassportInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE DATE_OF_EXPIRE = ? ORDER BY DATE_OF_EXPIRE", new Object[] { dateOfExpire==null ? null : new java.sql.Date( dateOfExpire.getTime() ) } );
	}

	/** 
	 * Returns all rows from the PASSPORT_INFO table that match the criteria 'PLACE_OF_ISSUE = :placeOfIssue'.
	 */
	public PassportInfo[] findWherePlaceOfIssueEquals(String placeOfIssue) throws PassportInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PLACE_OF_ISSUE = ? ORDER BY PLACE_OF_ISSUE", new Object[] { placeOfIssue } );
	}

	/** 
	 * Returns all rows from the PASSPORT_INFO table that match the criteria 'PLACE_OF_BIRTH = :placeOfBirth'.
	 */
	public PassportInfo[] findWherePlaceOfBirthEquals(String placeOfBirth) throws PassportInfoDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PLACE_OF_BIRTH = ? ORDER BY PLACE_OF_BIRTH", new Object[] { placeOfBirth } );
	}

	/**
	 * Method 'PassportInfoDaoImpl'
	 * 
	 */
	public PassportInfoDaoImpl()
	{
	}

	/**
	 * Method 'PassportInfoDaoImpl'
	 * 
	 * @param userConn
	 */
	public PassportInfoDaoImpl(final java.sql.Connection userConn)
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
		return "PASSPORT_INFO";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected PassportInfo fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			PassportInfo dto = new PassportInfo();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected PassportInfo[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<PassportInfo> resultList = new ArrayList<PassportInfo>();
		while (rs.next()) {
			PassportInfo dto = new PassportInfo();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		PassportInfo ret[] = new PassportInfo[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(PassportInfo dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setGivenname( rs.getString( COLUMN_GIVENNAME ) );
		dto.setSurname( rs.getString( COLUMN_SURNAME ) );
		dto.setPassportNo( rs.getString( COLUMN_PASSPORT_NO ) );
		dto.setDateOfIssue( rs.getDate(COLUMN_DATE_OF_ISSUE ) );
		dto.setDateOfExpire( rs.getDate(COLUMN_DATE_OF_EXPIRE ) );
		dto.setPlaceOfIssue( rs.getString( COLUMN_PLACE_OF_ISSUE ) );
		dto.setPlaceOfBirth( rs.getString( COLUMN_PLACE_OF_BIRTH ) );
		dto.setModifiedBy(rs.getInt( COLUMN_MODIFIED_BY ));
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(PassportInfo dto)
	{
	}

	/** 
	 * Returns all rows from the PASSPORT_INFO table that match the specified arbitrary SQL statement
	 */
	public PassportInfo[] findByDynamicSelect(String sql, Object[] sqlParams) throws PassportInfoDaoException
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
			throw new PassportInfoDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the PASSPORT_INFO table that match the specified arbitrary SQL statement
	 */
	public PassportInfo[] findByDynamicWhere(String sql, Object[] sqlParams) throws PassportInfoDaoException
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
			throw new PassportInfoDaoException( "Exception: " + _e.getMessage(), _e );
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
