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

public class DepPerdiemHistoryDaoImpl extends AbstractDAO implements DepPerdiemHistoryDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( DepPerdiemHistoryDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, DEP_ID, USER_ID, ADDED_BY, MODIFIED_BY, MANAGER_ID, MANAGER_NAME, PROJECT_NAME, PERDIEM, PERDIEM_HISTORY, PAYABLE_DAYS, PAYABLE_DAYS_HISTORY, CURRENCY, CURRENCY_HISTORY, PERDIEM_COMPUTED, COMMENTS FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, DEP_ID, USER_ID, ADDED_BY, MODIFIED_BY, MANAGER_ID, MANAGER_NAME, PROJECT_NAME, PERDIEM, PERDIEM_HISTORY, PAYABLE_DAYS, PAYABLE_DAYS_HISTORY, CURRENCY, CURRENCY_HISTORY, PERDIEM_COMPUTED, COMMENTS ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, DEP_ID = ?, USER_ID = ?, ADDED_BY = ?, MODIFIED_BY = ?, MANAGER_ID = ?, MANAGER_NAME = ?, PROJECT_NAME = ?, PERDIEM = ?, PERDIEM_HISTORY = ?, PAYABLE_DAYS = ?, PAYABLE_DAYS_HISTORY = ?, CURRENCY = ?, CURRENCY_HISTORY = ?, PERDIEM_COMPUTED = ?, COMMENTS = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column DEP_ID
	 */
	protected static final int COLUMN_DEP_ID = 2;

	/** 
	 * Index of column USER_ID
	 */
	protected static final int COLUMN_USER_ID = 3;

	/** 
	 * Index of column ADDED_BY
	 */
	protected static final int COLUMN_ADDED_BY = 4;

	/** 
	 * Index of column MODIFIED_BY
	 */
	protected static final int COLUMN_MODIFIED_BY = 5;

	/** 
	 * Index of column MANAGER_ID
	 */
	protected static final int COLUMN_MANAGER_ID = 6;

	/** 
	 * Index of column MANAGER_NAME
	 */
	protected static final int COLUMN_MANAGER_NAME = 7;

	/** 
	 * Index of column PROJECT_NAME
	 */
	protected static final int COLUMN_PROJECT_NAME = 8;

	/** 
	 * Index of column PERDIEM
	 */
	protected static final int COLUMN_PERDIEM = 9;

	/** 
	 * Index of column PERDIEM_HISTORY
	 */
	protected static final int COLUMN_PERDIEM_HISTORY = 10;

	/** 
	 * Index of column PAYABLE_DAYS
	 */
	protected static final int COLUMN_PAYABLE_DAYS = 11;

	/** 
	 * Index of column PAYABLE_DAYS_HISTORY
	 */
	protected static final int COLUMN_PAYABLE_DAYS_HISTORY = 12;

	/** 
	 * Index of column CURRENCY
	 */
	protected static final int COLUMN_CURRENCY = 13;

	/** 
	 * Index of column CURRENCY_HISTORY
	 */
	protected static final int COLUMN_CURRENCY_HISTORY = 14;

	/** 
	 * Index of column PERDIEM_COMPUTED
	 */
	protected static final int COLUMN_PERDIEM_COMPUTED = 15;

	/** 
	 * Index of column COMMENTS
	 */
	protected static final int COLUMN_COMMENTS = 16;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 16;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the DEP_PERDIEM_HISTORY table.
	 */
	public DepPerdiemHistoryPk insert(DepPerdiemHistory dto) throws DepPerdiemHistoryDaoException
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
			stmt.setInt( index++, dto.getDepId() );
			stmt.setInt( index++, dto.getUserId() );
			stmt.setInt( index++, dto.getAddedBy() );
			stmt.setInt( index++, dto.getModifiedBy() );
			stmt.setInt( index++, dto.getManagerId() );
			stmt.setString( index++, dto.getManagerName() );
			stmt.setString( index++, dto.getProjectName() );
			stmt.setString( index++, dto.getPerdiem() );
			stmt.setString( index++, dto.getPerdiemHistory() );
			stmt.setInt( index++, dto.getPayableDays() );
			stmt.setString( index++, dto.getPayableDaysHistory() );
			stmt.setString( index++, dto.getCurrency() );
			stmt.setString( index++, dto.getCurrencyHistory() );
			stmt.setString( index++, dto.getPerdiemComputed() );
			stmt.setString( index++, dto.getComments() );
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
			throw new DepPerdiemHistoryDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the DEP_PERDIEM_HISTORY table.
	 */
	public void update(DepPerdiemHistoryPk pk, DepPerdiemHistory dto) throws DepPerdiemHistoryDaoException
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
			stmt.setInt( index++, dto.getDepId() );
			stmt.setInt( index++, dto.getUserId() );
			stmt.setInt( index++, dto.getAddedBy() );
			stmt.setInt( index++, dto.getModifiedBy() );
			stmt.setInt( index++, dto.getManagerId() );
			stmt.setString( index++, dto.getManagerName() );
			stmt.setString( index++, dto.getProjectName() );
			stmt.setString( index++, dto.getPerdiem() );
			stmt.setString( index++, dto.getPerdiemHistory() );
			stmt.setInt( index++, dto.getPayableDays() );
			stmt.setString( index++, dto.getPayableDaysHistory() );
			stmt.setString( index++, dto.getCurrency() );
			stmt.setString( index++, dto.getCurrencyHistory() );
			stmt.setString( index++, dto.getPerdiemComputed() );
			stmt.setString( index++, dto.getComments() );
			stmt.setInt( 17, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new DepPerdiemHistoryDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the DEP_PERDIEM_HISTORY table.
	 */
	public void delete(DepPerdiemHistoryPk pk) throws DepPerdiemHistoryDaoException
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
			throw new DepPerdiemHistoryDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the DEP_PERDIEM_HISTORY table that matches the specified primary-key value.
	 */
	public DepPerdiemHistory findByPrimaryKey(DepPerdiemHistoryPk pk) throws DepPerdiemHistoryDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'ID = :id'.
	 */
	public DepPerdiemHistory findByPrimaryKey(int id) throws DepPerdiemHistoryDaoException
	{
		DepPerdiemHistory ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria ''.
	 */
	public DepPerdiemHistory[] findAll() throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'ID = :id'.
	 */
	public DepPerdiemHistory[] findWhereIdEquals(int id) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'DEP_ID = :depId'.
	 */
	public DepPerdiemHistory[] findWhereDepIdEquals(int depId) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE DEP_ID = ? ORDER BY DEP_ID", new Object[] {  new Integer(depId) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'USER_ID = :userId'.
	 */
	public DepPerdiemHistory[] findWhereUserIdEquals(int userId) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE USER_ID = ? ORDER BY USER_ID", new Object[] {  new Integer(userId) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'ADDED_BY = :addedBy'.
	 */
	public DepPerdiemHistory[] findWhereAddedByEquals(int addedBy) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ADDED_BY = ? ORDER BY ADDED_BY", new Object[] {  new Integer(addedBy) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'MODIFIED_BY = :modifiedBy'.
	 */
	public DepPerdiemHistory[] findWhereModifiedByEquals(int modifiedBy) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE MODIFIED_BY = ? ORDER BY MODIFIED_BY", new Object[] {  new Integer(modifiedBy) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'MANAGER_ID = :managerId'.
	 */
	public DepPerdiemHistory[] findWhereManagerIdEquals(int managerId) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE MANAGER_ID = ? ORDER BY MANAGER_ID", new Object[] {  new Integer(managerId) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'MANAGER_NAME = :managerName'.
	 */
	public DepPerdiemHistory[] findWhereManagerNameEquals(String managerName) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE MANAGER_NAME = ? ORDER BY MANAGER_NAME", new Object[] { managerName } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'PROJECT_NAME = :projectName'.
	 */
	public DepPerdiemHistory[] findWhereProjectNameEquals(String projectName) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PROJECT_NAME = ? ORDER BY PROJECT_NAME", new Object[] { projectName } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'PERDIEM = :perdiem'.
	 */
	public DepPerdiemHistory[] findWherePerdiemEquals(String perdiem) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PERDIEM = ? ORDER BY PERDIEM", new Object[] { perdiem } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'PERDIEM_HISTORY = :perdiemHistory'.
	 */
	public DepPerdiemHistory[] findWherePerdiemHistoryEquals(String perdiemHistory) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PERDIEM_HISTORY = ? ORDER BY PERDIEM_HISTORY", new Object[] { perdiemHistory } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'PAYABLE_DAYS = :payableDays'.
	 */
	public DepPerdiemHistory[] findWherePayableDaysEquals(int payableDays) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PAYABLE_DAYS = ? ORDER BY PAYABLE_DAYS", new Object[] {  new Integer(payableDays) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'PAYABLE_DAYS_HISTORY = :payableDaysHistory'.
	 */
	public DepPerdiemHistory[] findWherePayableDaysHistoryEquals(String payableDaysHistory) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PAYABLE_DAYS_HISTORY = ? ORDER BY PAYABLE_DAYS_HISTORY", new Object[] { payableDaysHistory } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'CURRENCY = :currency'.
	 */
	public DepPerdiemHistory[] findWhereCurrencyEquals(String currency) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE CURRENCY = ? ORDER BY CURRENCY", new Object[] { currency } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'CURRENCY_HISTORY = :currencyHistory'.
	 */
	public DepPerdiemHistory[] findWhereCurrencyHistoryEquals(String currencyHistory) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE CURRENCY_HISTORY = ? ORDER BY CURRENCY_HISTORY", new Object[] { currencyHistory } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'PERDIEM_COMPUTED = :perdiemComputed'.
	 */
	public DepPerdiemHistory[] findWherePerdiemComputedEquals(String perdiemComputed) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PERDIEM_COMPUTED = ? ORDER BY PERDIEM_COMPUTED", new Object[] { perdiemComputed } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'COMMENTS = :comments'.
	 */
	public DepPerdiemHistory[] findWhereCommentsEquals(String comments) throws DepPerdiemHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE COMMENTS = ? ORDER BY COMMENTS", new Object[] { comments } );
	}

	/**
	 * Method 'DepPerdiemHistoryDaoImpl'
	 * 
	 */
	public DepPerdiemHistoryDaoImpl()
	{
	}

	/**
	 * Method 'DepPerdiemHistoryDaoImpl'
	 * 
	 * @param userConn
	 */
	public DepPerdiemHistoryDaoImpl(final java.sql.Connection userConn)
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
		return "DEP_PERDIEM_HISTORY";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected DepPerdiemHistory fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			DepPerdiemHistory dto = new DepPerdiemHistory();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected DepPerdiemHistory[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<DepPerdiemHistory> resultList = new ArrayList<DepPerdiemHistory>();
		while (rs.next()) {
			DepPerdiemHistory dto = new DepPerdiemHistory();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		DepPerdiemHistory ret[] = new DepPerdiemHistory[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(DepPerdiemHistory dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setDepId( rs.getInt( COLUMN_DEP_ID ) );
		dto.setUserId( rs.getInt( COLUMN_USER_ID ) );
		dto.setAddedBy( rs.getInt( COLUMN_ADDED_BY ) );
		dto.setModifiedBy( rs.getInt( COLUMN_MODIFIED_BY ) );
		dto.setManagerId( rs.getInt( COLUMN_MANAGER_ID ) );
		dto.setManagerName( rs.getString( COLUMN_MANAGER_NAME ) );
		dto.setProjectName( rs.getString( COLUMN_PROJECT_NAME ) );
		dto.setPerdiem( rs.getString( COLUMN_PERDIEM ) );
		dto.setPerdiemHistory( rs.getString( COLUMN_PERDIEM_HISTORY ) );
		dto.setPayableDays( rs.getInt( COLUMN_PAYABLE_DAYS ) );
		dto.setPayableDaysHistory( rs.getString( COLUMN_PAYABLE_DAYS_HISTORY ) );
		dto.setCurrency( rs.getString( COLUMN_CURRENCY ) );
		dto.setCurrencyHistory( rs.getString( COLUMN_CURRENCY_HISTORY ) );
		dto.setPerdiemComputed( rs.getString( COLUMN_PERDIEM_COMPUTED ) );
		dto.setComments( rs.getString( COLUMN_COMMENTS ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(DepPerdiemHistory dto)
	{
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the specified arbitrary SQL statement
	 */
	public DepPerdiemHistory[] findByDynamicSelect(String sql, Object[] sqlParams) throws DepPerdiemHistoryDaoException
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
			throw new DepPerdiemHistoryDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the specified arbitrary SQL statement
	 */
	public DepPerdiemHistory[] findByDynamicWhere(String sql, Object[] sqlParams) throws DepPerdiemHistoryDaoException
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
			throw new DepPerdiemHistoryDaoException( "Exception: " + _e.getMessage(), _e );
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
