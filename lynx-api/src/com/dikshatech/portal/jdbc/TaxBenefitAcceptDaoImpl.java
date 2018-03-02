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

import com.dikshatech.portal.dao.TaxBenefitAcceptDao;
import com.dikshatech.portal.dto.TaxBenefitAccept;
import com.dikshatech.portal.dto.TaxBenefitAcceptPk;
import com.dikshatech.portal.exceptions.TaxBenefitAcceptDaoException;

public class TaxBenefitAcceptDaoImpl extends AbstractDAO implements TaxBenefitAcceptDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( TaxBenefitAcceptDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, USERID, BENEFIT_ID, AMOUNT, FINANCIAL_YEAR, COMMENTS FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, USERID, BENEFIT_ID, AMOUNT, FINANCIAL_YEAR, COMMENTS ) VALUES ( ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, USERID = ?, BENEFIT_ID = ?, AMOUNT = ?, FINANCIAL_YEAR = ?, COMMENTS = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column USERID
	 */
	protected static final int COLUMN_USERID = 2;

	/** 
	 * Index of column BENEFIT_ID
	 */
	protected static final int COLUMN_BENEFIT_ID = 3;

	/** 
	 * Index of column AMOUNT
	 */
	protected static final int COLUMN_AMOUNT = 4;

	/** 
	 * Index of column FINANCIAL_YEAR
	 */
	protected static final int COLUMN_FINANCIAL_YEAR = 5;

	/** 
	 * Index of column COMMENTS
	 */
	protected static final int COLUMN_COMMENTS = 6;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 6;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the TAX_BENEFIT_ACCEPT table.
	 */
	public TaxBenefitAcceptPk insert(TaxBenefitAccept dto) throws TaxBenefitAcceptDaoException
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
			stmt.setInt( index++, dto.getUserid() );
			if (dto.isBenefitIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getBenefitId() );
			}
		
			stmt.setString( index++, dto.getAmount() );
			stmt.setString( index++, dto.getFinancialYear() );
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
			throw new TaxBenefitAcceptDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the TAX_BENEFIT_ACCEPT table.
	 */
	public void update(TaxBenefitAcceptPk pk, TaxBenefitAccept dto) throws TaxBenefitAcceptDaoException
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
			stmt.setInt( index++, dto.getUserid() );
			if (dto.isBenefitIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getBenefitId() );
			}
		
			stmt.setString( index++, dto.getAmount() );
			stmt.setString( index++, dto.getFinancialYear() );
			stmt.setString( index++, dto.getComments() );
			stmt.setInt( 7, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new TaxBenefitAcceptDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the TAX_BENEFIT_ACCEPT table.
	 */
	public void delete(TaxBenefitAcceptPk pk) throws TaxBenefitAcceptDaoException
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
			throw new TaxBenefitAcceptDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the TAX_BENEFIT_ACCEPT table that matches the specified primary-key value.
	 */
	public TaxBenefitAccept findByPrimaryKey(TaxBenefitAcceptPk pk) throws TaxBenefitAcceptDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the TAX_BENEFIT_ACCEPT table that match the criteria 'ID = :id'.
	 */
	public TaxBenefitAccept findByPrimaryKey(int id) throws TaxBenefitAcceptDaoException
	{
		TaxBenefitAccept ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the TAX_BENEFIT_ACCEPT table that match the criteria ''.
	 */
	public TaxBenefitAccept[] findAll() throws TaxBenefitAcceptDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the TAX_BENEFIT_ACCEPT table that match the criteria 'ID = :id'.
	 */
	public TaxBenefitAccept[] findWhereIdEquals(int id) throws TaxBenefitAcceptDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the TAX_BENEFIT_ACCEPT table that match the criteria 'USERID = :userid'.
	 */
	public TaxBenefitAccept[] findWhereUseridEquals(int userid) throws TaxBenefitAcceptDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE USERID = ? ORDER BY USERID", new Object[] {  new Integer(userid) } );
	}

	/** 
	 * Returns all rows from the TAX_BENEFIT_ACCEPT table that match the criteria 'BENEFIT_ID = :benefitId'.
	 */
	public TaxBenefitAccept[] findWhereBenefitIdEquals(int benefitId) throws TaxBenefitAcceptDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE BENEFIT_ID = ? ORDER BY BENEFIT_ID", new Object[] {  new Integer(benefitId) } );
	}

	/** 
	 * Returns all rows from the TAX_BENEFIT_ACCEPT table that match the criteria 'AMOUNT = :amount'.
	 */
	public TaxBenefitAccept[] findWhereAmountEquals(String amount) throws TaxBenefitAcceptDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE AMOUNT = ? ORDER BY AMOUNT", new Object[] { amount } );
	}

	/** 
	 * Returns all rows from the TAX_BENEFIT_ACCEPT table that match the criteria 'FINANCIAL_YEAR = :financialYear'.
	 */
	public TaxBenefitAccept[] findWhereFinancialYearEquals(String financialYear) throws TaxBenefitAcceptDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE FINANCIAL_YEAR = ? ORDER BY FINANCIAL_YEAR", new Object[] { financialYear } );
	}

	/** 
	 * Returns all rows from the TAX_BENEFIT_ACCEPT table that match the criteria 'COMMENTS = :comments'.
	 */
	public TaxBenefitAccept[] findWhereCommentsEquals(String comments) throws TaxBenefitAcceptDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE COMMENTS = ? ORDER BY COMMENTS", new Object[] { comments } );
	}

	/**
	 * Method 'TaxBenefitAcceptDaoImpl'
	 * 
	 */
	public TaxBenefitAcceptDaoImpl()
	{
	}

	/**
	 * Method 'TaxBenefitAcceptDaoImpl'
	 * 
	 * @param userConn
	 */
	public TaxBenefitAcceptDaoImpl(final java.sql.Connection userConn)
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
		return "TAX_BENEFIT_ACCEPT";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected TaxBenefitAccept fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			TaxBenefitAccept dto = new TaxBenefitAccept();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected TaxBenefitAccept[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<TaxBenefitAccept> resultList = new ArrayList<TaxBenefitAccept>();
		while (rs.next()) {
			TaxBenefitAccept dto = new TaxBenefitAccept();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		TaxBenefitAccept ret[] = new TaxBenefitAccept[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(TaxBenefitAccept dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setUserid( rs.getInt( COLUMN_USERID ) );
		dto.setBenefitId( rs.getInt( COLUMN_BENEFIT_ID ) );
		if (rs.wasNull()) {
			dto.setBenefitIdNull( true );
		}
		
		dto.setAmount( rs.getString( COLUMN_AMOUNT ) );
		dto.setFinancialYear( rs.getString( COLUMN_FINANCIAL_YEAR ) );
		dto.setComments( rs.getString( COLUMN_COMMENTS ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(TaxBenefitAccept dto)
	{
	}

	/** 
	 * Returns all rows from the TAX_BENEFIT_ACCEPT table that match the specified arbitrary SQL statement
	 */
	public TaxBenefitAccept[] findByDynamicSelect(String sql, Object[] sqlParams) throws TaxBenefitAcceptDaoException
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
			throw new TaxBenefitAcceptDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the TAX_BENEFIT_ACCEPT table that match the specified arbitrary SQL statement
	 */
	public TaxBenefitAccept[] findByDynamicWhere(String sql, Object[] sqlParams) throws TaxBenefitAcceptDaoException
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
			throw new TaxBenefitAcceptDaoException( "Exception: " + _e.getMessage(), _e );
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
