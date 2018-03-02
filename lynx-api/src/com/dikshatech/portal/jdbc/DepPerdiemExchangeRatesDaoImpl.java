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

import com.dikshatech.portal.dao.DepPerdiemExchangeRatesDao;
import com.dikshatech.portal.dto.DepPerdiemExchangeRates;
import com.dikshatech.portal.dto.DepPerdiemExchangeRatesPk;
import com.dikshatech.portal.exceptions.DepPerdiemExchangeRatesDaoException;

public class DepPerdiemExchangeRatesDaoImpl extends AbstractDAO implements DepPerdiemExchangeRatesDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( DepPerdiemExchangeRatesDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, REP_ID, CURRENCY_TYPE, AMOUNT FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, REP_ID, CURRENCY_TYPE, AMOUNT ) VALUES ( ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, REP_ID = ?, CURRENCY_TYPE = ?, AMOUNT = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column REP_ID
	 */
	protected static final int COLUMN_REP_ID = 2;

	/** 
	 * Index of column CURRENCY_TYPE
	 */
	protected static final int COLUMN_CURRENCY_TYPE = 3;

	/** 
	 * Index of column AMOUNT
	 */
	protected static final int COLUMN_AMOUNT = 4;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 4;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the DEP_PERDIEM_EXCHANGE_RATES table.
	 */
	public DepPerdiemExchangeRatesPk insert(DepPerdiemExchangeRates dto) throws DepPerdiemExchangeRatesDaoException
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
			stmt.setInt( index++, dto.getRepId() );
			stmt.setShort( index++, dto.getCurrencyType() );
			stmt.setDouble(index++, dto.getAmount());
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
			throw new DepPerdiemExchangeRatesDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the DEP_PERDIEM_EXCHANGE_RATES table.
	 */
	public void update(DepPerdiemExchangeRatesPk pk, DepPerdiemExchangeRates dto) throws DepPerdiemExchangeRatesDaoException
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
			stmt.setInt( index++, dto.getRepId() );
			stmt.setShort( index++, dto.getCurrencyType() );
			stmt.setDouble(index++, dto.getAmount());
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
			throw new DepPerdiemExchangeRatesDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the DEP_PERDIEM_EXCHANGE_RATES table.
	 */
	public void delete(DepPerdiemExchangeRatesPk pk) throws DepPerdiemExchangeRatesDaoException
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
			throw new DepPerdiemExchangeRatesDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the DEP_PERDIEM_EXCHANGE_RATES table that matches the specified primary-key value.
	 */
	public DepPerdiemExchangeRates findByPrimaryKey(DepPerdiemExchangeRatesPk pk) throws DepPerdiemExchangeRatesDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_EXCHANGE_RATES table that match the criteria 'ID = :id'.
	 */
	public DepPerdiemExchangeRates findByPrimaryKey(int id) throws DepPerdiemExchangeRatesDaoException
	{
		DepPerdiemExchangeRates ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_EXCHANGE_RATES table that match the criteria 'REP_ID = :repId'.
	 */
	public DepPerdiemExchangeRates[] findWhereRepIdEquals(Integer repId) throws DepPerdiemExchangeRatesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE REP_ID = ? ORDER BY REP_ID", new Object[] { repId } );
	}

	/**
	 * Method 'DepPerdiemExchangeRatesDaoImpl'
	 * 
	 */
	public DepPerdiemExchangeRatesDaoImpl()
	{
	}

	/**
	 * Method 'DepPerdiemExchangeRatesDaoImpl'
	 * 
	 * @param userConn
	 */
	public DepPerdiemExchangeRatesDaoImpl(final java.sql.Connection userConn)
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
		return "DEP_PERDIEM_EXCHANGE_RATES";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected DepPerdiemExchangeRates fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			DepPerdiemExchangeRates dto = new DepPerdiemExchangeRates();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected DepPerdiemExchangeRates[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<DepPerdiemExchangeRates> resultList = new ArrayList<DepPerdiemExchangeRates>();
		while (rs.next()) {
			DepPerdiemExchangeRates dto = new DepPerdiemExchangeRates();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		DepPerdiemExchangeRates ret[] = new DepPerdiemExchangeRates[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(DepPerdiemExchangeRates dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setRepId( rs.getInt( COLUMN_REP_ID ) );
		dto.setCurrencyType( rs.getShort( COLUMN_CURRENCY_TYPE ) );
		dto.setAmount(rs.getDouble(COLUMN_AMOUNT));
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(DepPerdiemExchangeRates dto)
	{
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_EXCHANGE_RATES table that match the specified arbitrary SQL statement
	 */
	public DepPerdiemExchangeRates[] findByDynamicSelect(String sql, Object[] sqlParams) throws DepPerdiemExchangeRatesDaoException
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
			throw new DepPerdiemExchangeRatesDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the DEP_PERDIEM_EXCHANGE_RATES table that match the specified arbitrary SQL statement
	 */
	public DepPerdiemExchangeRates[] findByDynamicWhere(String sql, Object[] sqlParams) throws DepPerdiemExchangeRatesDaoException
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
			throw new DepPerdiemExchangeRatesDaoException( "Exception: " + _e.getMessage(), _e );
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
