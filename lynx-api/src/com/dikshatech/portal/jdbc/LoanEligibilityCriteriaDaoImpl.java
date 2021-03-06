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

import com.dikshatech.portal.dao.LoanEligibilityCriteriaDao;
import com.dikshatech.portal.dto.LoanEligibilityCriteria;
import com.dikshatech.portal.dto.LoanEligibilityCriteriaPk;
import com.dikshatech.portal.exceptions.LoanEligibilityCriteriaDaoException;

public class LoanEligibilityCriteriaDaoImpl extends AbstractDAO implements LoanEligibilityCriteriaDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( LoanEligibilityCriteriaDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, LABEL, ELIGIBILITY_AMOUNT, EMI_ELIGIBILITY, MAX_AMOUNT_LIMIT, TYPE_ID FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, LABEL, ELIGIBILITY_AMOUNT, EMI_ELIGIBILITY, MAX_AMOUNT_LIMIT, TYPE_ID ) VALUES ( ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, LABEL = ?, ELIGIBILITY_AMOUNT = ?, EMI_ELIGIBILITY = ?, MAX_AMOUNT_LIMIT = ?, TYPE_ID = ? WHERE ID = ?";

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
	 * Index of column ELIGIBILITY_AMOUNT
	 */
	protected static final int COLUMN_ELIGIBILITY_AMOUNT = 3;

	/** 
	 * Index of column EMI_ELIGIBILITY
	 */
	protected static final int COLUMN_EMI_ELIGIBILITY = 4;

	/** 
	 * Index of column MAX_AMOUNT_LIMIT
	 */
	protected static final int COLUMN_MAX_AMOUNT_LIMIT = 5;

	/** 
	 * Index of column TYPE_ID
	 */
	protected static final int COLUMN_TYPE_ID = 6;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 6;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the LOAN_ELIGIBILITY_CRITERIA table.
	 */
	public LoanEligibilityCriteriaPk insert(LoanEligibilityCriteria dto) throws LoanEligibilityCriteriaDaoException
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
			stmt.setString( index++, dto.getEligibilityAmount() );
			if (dto.isEmiEligibilityNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getEmiEligibility() );
			}
		
			if (dto.isMaxAmountLimitNull()) {
				stmt.setNull( index++, java.sql.Types.DOUBLE );
			} else {
				stmt.setDouble( index++, dto.getMaxAmountLimit() );
			}
		
			if (dto.isTypeIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getTypeId() );
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
			throw new LoanEligibilityCriteriaDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the LOAN_ELIGIBILITY_CRITERIA table.
	 */
	public void update(LoanEligibilityCriteriaPk pk, LoanEligibilityCriteria dto) throws LoanEligibilityCriteriaDaoException
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
			stmt.setString( index++, dto.getEligibilityAmount() );
			if (dto.isEmiEligibilityNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getEmiEligibility() );
			}
		
			if (dto.isMaxAmountLimitNull()) {
				stmt.setNull( index++, java.sql.Types.DOUBLE );
			} else {
				stmt.setDouble( index++, dto.getMaxAmountLimit() );
			}
		
			if (dto.isTypeIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getTypeId() );
			}
		
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
			throw new LoanEligibilityCriteriaDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the LOAN_ELIGIBILITY_CRITERIA table.
	 */
	public void delete(LoanEligibilityCriteriaPk pk) throws LoanEligibilityCriteriaDaoException
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
			throw new LoanEligibilityCriteriaDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the LOAN_ELIGIBILITY_CRITERIA table that matches the specified primary-key value.
	 */
	public LoanEligibilityCriteria findByPrimaryKey(LoanEligibilityCriteriaPk pk) throws LoanEligibilityCriteriaDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'ID = :id'.
	 */
	public LoanEligibilityCriteria findByPrimaryKey(int id) throws LoanEligibilityCriteriaDaoException
	{
		LoanEligibilityCriteria ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria ''.
	 */
	public LoanEligibilityCriteria[] findAll() throws LoanEligibilityCriteriaDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'TYPE_ID = :typeId'.
	 */
	public LoanEligibilityCriteria[] findByLoanType(int typeId) throws LoanEligibilityCriteriaDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE TYPE_ID = ?", new Object[] {  new Integer(typeId) } );
	}

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'ID = :id'.
	 */
	public LoanEligibilityCriteria[] findWhereIdEquals(int id) throws LoanEligibilityCriteriaDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'LABEL = :label'.
	 */
	public LoanEligibilityCriteria[] findWhereLabelEquals(String label) throws LoanEligibilityCriteriaDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE LABEL = ? ORDER BY LABEL", new Object[] { label } );
	}

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'ELIGIBILITY_AMOUNT = :eligibilityAmount'.
	 */
	public LoanEligibilityCriteria[] findWhereEligibilityAmountEquals(String eligibilityAmount) throws LoanEligibilityCriteriaDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ELIGIBILITY_AMOUNT = ? ORDER BY ELIGIBILITY_AMOUNT", new Object[] { eligibilityAmount } );
	}

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'EMI_ELIGIBILITY = :emiEligibility'.
	 */
	public LoanEligibilityCriteria[] findWhereEmiEligibilityEquals(int emiEligibility) throws LoanEligibilityCriteriaDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE EMI_ELIGIBILITY = ? ORDER BY EMI_ELIGIBILITY", new Object[] {  new Integer(emiEligibility) } );
	}

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'MAX_AMOUNT_LIMIT = :maxAmountLimit'.
	 */
	public LoanEligibilityCriteria[] findWhereMaxAmountLimitEquals(double maxAmountLimit) throws LoanEligibilityCriteriaDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE MAX_AMOUNT_LIMIT = ? ORDER BY MAX_AMOUNT_LIMIT", new Object[] {  new Double(maxAmountLimit) } );
	}

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'TYPE_ID = :typeId'.
	 */
	public LoanEligibilityCriteria[] findWhereTypeIdEquals(int typeId) throws LoanEligibilityCriteriaDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE TYPE_ID = ? ORDER BY TYPE_ID", new Object[] {  new Integer(typeId) } );
	}

	/**
	 * Method 'LoanEligibilityCriteriaDaoImpl'
	 * 
	 */
	public LoanEligibilityCriteriaDaoImpl()
	{
	}

	/**
	 * Method 'LoanEligibilityCriteriaDaoImpl'
	 * 
	 * @param userConn
	 */
	public LoanEligibilityCriteriaDaoImpl(final java.sql.Connection userConn)
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
		return "LOAN_ELIGIBILITY_CRITERIA";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected LoanEligibilityCriteria fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			LoanEligibilityCriteria dto = new LoanEligibilityCriteria();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected LoanEligibilityCriteria[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<LoanEligibilityCriteria> resultList = new ArrayList<LoanEligibilityCriteria>();
		while (rs.next()) {
			LoanEligibilityCriteria dto = new LoanEligibilityCriteria();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		LoanEligibilityCriteria ret[] = new LoanEligibilityCriteria[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(LoanEligibilityCriteria dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setLabel( rs.getString( COLUMN_LABEL ) );
		dto.setEligibilityAmount( rs.getString( COLUMN_ELIGIBILITY_AMOUNT ) );
		dto.setEmiEligibility( rs.getInt( COLUMN_EMI_ELIGIBILITY ) );
		if (rs.wasNull()) {
			dto.setEmiEligibilityNull( true );
		}
		
		dto.setMaxAmountLimit( rs.getDouble( COLUMN_MAX_AMOUNT_LIMIT ) );
		if (rs.wasNull()) {
			dto.setMaxAmountLimitNull( true );
		}
		
		dto.setTypeId( rs.getInt( COLUMN_TYPE_ID ) );
		if (rs.wasNull()) {
			dto.setTypeIdNull( true );
		}
		
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(LoanEligibilityCriteria dto)
	{
	}

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the specified arbitrary SQL statement
	 */
	public LoanEligibilityCriteria[] findByDynamicSelect(String sql, Object[] sqlParams) throws LoanEligibilityCriteriaDaoException
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
			throw new LoanEligibilityCriteriaDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the specified arbitrary SQL statement
	 */
	public LoanEligibilityCriteria[] findByDynamicWhere(String sql, Object[] sqlParams) throws LoanEligibilityCriteriaDaoException
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
			throw new LoanEligibilityCriteriaDaoException( "Exception: " + _e.getMessage(), _e );
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
