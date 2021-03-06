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

import com.dikshatech.portal.dao.ReimbursementFinancialDataDao;
import com.dikshatech.portal.dto.ReimbursementFinancialData;
import com.dikshatech.portal.dto.ReimbursementFinancialDataPk;
import com.dikshatech.portal.exceptions.ReimbursementFinancialDataDaoException;

public class ReimbursementFinancialDataDaoImpl extends AbstractDAO implements ReimbursementFinancialDataDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( ReimbursementFinancialDataDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, ESRMAP_ID, DATE_OF_OCCURRENCE, SUMMARY, TYPE, AMOUNT, CURRENCY, RECEIPTS_AVAILABLE, UPLOAD_RECEIPTS_ID FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, ESRMAP_ID, DATE_OF_OCCURRENCE, SUMMARY, TYPE, AMOUNT, CURRENCY, RECEIPTS_AVAILABLE, UPLOAD_RECEIPTS_ID ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, ESRMAP_ID = ?, DATE_OF_OCCURRENCE = ?, SUMMARY = ?, TYPE = ?, AMOUNT = ?, CURRENCY = ?, RECEIPTS_AVAILABLE = ?, UPLOAD_RECEIPTS_ID = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column ESRMAP_ID
	 */
	protected static final int COLUMN_ESRMAP_ID = 2;

	/** 
	 * Index of column DATE_OF_OCCURRENCE
	 */
	protected static final int COLUMN_DATE_OF_OCCURRENCE = 3;

	/** 
	 * Index of column SUMMARY
	 */
	protected static final int COLUMN_SUMMARY = 4;

	/** 
	 * Index of column TYPE
	 */
	protected static final int COLUMN_TYPE = 5;

	/** 
	 * Index of column AMOUNT
	 */
	protected static final int COLUMN_AMOUNT = 6;

	/** 
	 * Index of column CURRENCY
	 */
	protected static final int COLUMN_CURRENCY = 7;

	/** 
	 * Index of column RECEIPTS_AVAILABLE
	 */
	protected static final int COLUMN_RECEIPTS_AVAILABLE = 8;

	/** 
	 * Index of column UPLOAD_RECEIPTS_ID
	 */
	protected static final int COLUMN_UPLOAD_RECEIPTS_ID = 9;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 9;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the REIMBURSEMENT_FINANCIAL_DATA table.
	 */
	public ReimbursementFinancialDataPk insert(ReimbursementFinancialData dto) throws ReimbursementFinancialDataDaoException
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
			if (dto.isEsrmapIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getEsrmapId() );
			}
		
			stmt.setDate(index++, dto.getDateOfOccurrence()==null ? null : new java.sql.Date( dto.getDateOfOccurrence().getTime() ) );
			stmt.setString( index++, dto.getSummary() );
			stmt.setString( index++, dto.getType() );
			stmt.setString( index++, dto.getAmount() );
			stmt.setString( index++, dto.getCurrency() );
			stmt.setString( index++, dto.getReceiptsAvailable() );
			if (dto.isUploadReceiptsIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getUploadReceiptsId() );
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
			throw new ReimbursementFinancialDataDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the REIMBURSEMENT_FINANCIAL_DATA table.
	 */
	public void update(ReimbursementFinancialDataPk pk, ReimbursementFinancialData dto) throws ReimbursementFinancialDataDaoException
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
			if (dto.isEsrmapIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getEsrmapId() );
			}
		
			stmt.setDate(index++, dto.getDateOfOccurrence()==null ? null : new java.sql.Date( dto.getDateOfOccurrence().getTime() ) );
			stmt.setString( index++, dto.getSummary() );
			stmt.setString( index++, dto.getType() );
			stmt.setString( index++, dto.getAmount() );
			stmt.setString( index++, dto.getCurrency() );
			stmt.setString( index++, dto.getReceiptsAvailable() );
			if (dto.isUploadReceiptsIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getUploadReceiptsId() );
			}
		
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
			throw new ReimbursementFinancialDataDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the REIMBURSEMENT_FINANCIAL_DATA table.
	 */
	public void delete(ReimbursementFinancialDataPk pk) throws ReimbursementFinancialDataDaoException
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
			throw new ReimbursementFinancialDataDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the REIMBURSEMENT_FINANCIAL_DATA table that matches the specified primary-key value.
	 */
	public ReimbursementFinancialData findByPrimaryKey(ReimbursementFinancialDataPk pk) throws ReimbursementFinancialDataDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the REIMBURSEMENT_FINANCIAL_DATA table that match the criteria 'ID = :id'.
	 */
	public ReimbursementFinancialData findByPrimaryKey(int id) throws ReimbursementFinancialDataDaoException
	{
		ReimbursementFinancialData ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the REIMBURSEMENT_FINANCIAL_DATA table that match the criteria ''.
	 */
	public ReimbursementFinancialData[] findAll() throws ReimbursementFinancialDataDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the REIMBURSEMENT_FINANCIAL_DATA table that match the criteria 'ID = :id'.
	 */
	public ReimbursementFinancialData[] findWhereIdEquals(int id) throws ReimbursementFinancialDataDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the REIMBURSEMENT_FINANCIAL_DATA table that match the criteria 'ESRMAP_ID = :esrmapId'.
	 */
	public ReimbursementFinancialData[] findWhereEsrmapIdEquals(int esrmapId) throws ReimbursementFinancialDataDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ESRMAP_ID = ? ORDER BY ESRMAP_ID", new Object[] {  new Integer(esrmapId) } );
	}

	/** 
	 * Returns all rows from the REIMBURSEMENT_FINANCIAL_DATA table that match the criteria 'DATE_OF_OCCURRENCE = :dateOfOccurrence'.
	 */
	public ReimbursementFinancialData[] findWhereDateOfOccurrenceEquals(Date dateOfOccurrence) throws ReimbursementFinancialDataDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE DATE_OF_OCCURRENCE = ? ORDER BY DATE_OF_OCCURRENCE", new Object[] { dateOfOccurrence==null ? null : new java.sql.Date( dateOfOccurrence.getTime() ) } );
	}

	/** 
	 * Returns all rows from the REIMBURSEMENT_FINANCIAL_DATA table that match the criteria 'SUMMARY = :summary'.
	 */
	public ReimbursementFinancialData[] findWhereSummaryEquals(String summary) throws ReimbursementFinancialDataDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE SUMMARY = ? ORDER BY SUMMARY", new Object[] { summary } );
	}

	/** 
	 * Returns all rows from the REIMBURSEMENT_FINANCIAL_DATA table that match the criteria 'TYPE = :type'.
	 */
	public ReimbursementFinancialData[] findWhereTypeEquals(String type) throws ReimbursementFinancialDataDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE TYPE = ? ORDER BY TYPE", new Object[] { type } );
	}

	/** 
	 * Returns all rows from the REIMBURSEMENT_FINANCIAL_DATA table that match the criteria 'AMOUNT = :amount'.
	 */
	public ReimbursementFinancialData[] findWhereAmountEquals(String amount) throws ReimbursementFinancialDataDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE AMOUNT = ? ORDER BY AMOUNT", new Object[] { amount } );
	}

	/** 
	 * Returns all rows from the REIMBURSEMENT_FINANCIAL_DATA table that match the criteria 'CURRENCY = :currency'.
	 */
	public ReimbursementFinancialData[] findWhereCurrencyEquals(String currency) throws ReimbursementFinancialDataDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE CURRENCY = ? ORDER BY CURRENCY", new Object[] { currency } );
	}

	/** 
	 * Returns all rows from the REIMBURSEMENT_FINANCIAL_DATA table that match the criteria 'RECEIPTS_AVAILABLE = :receiptsAvailable'.
	 */
	public ReimbursementFinancialData[] findWhereReceiptsAvailableEquals(String receiptsAvailable) throws ReimbursementFinancialDataDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE RECEIPTS_AVAILABLE = ? ORDER BY RECEIPTS_AVAILABLE", new Object[] { receiptsAvailable } );
	}

	/** 
	 * Returns all rows from the REIMBURSEMENT_FINANCIAL_DATA table that match the criteria 'UPLOAD_RECEIPTS_ID = :uploadReceiptsId'.
	 */
	public ReimbursementFinancialData[] findWhereUploadReceiptsIdEquals(int uploadReceiptsId) throws ReimbursementFinancialDataDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE UPLOAD_RECEIPTS_ID = ? ORDER BY UPLOAD_RECEIPTS_ID", new Object[] {  new Integer(uploadReceiptsId) } );
	}

	/**
	 * Method 'ReimbursementFinancialDataDaoImpl'
	 * 
	 */
	public ReimbursementFinancialDataDaoImpl()
	{
	}

	/**
	 * Method 'ReimbursementFinancialDataDaoImpl'
	 * 
	 * @param userConn
	 */
	public ReimbursementFinancialDataDaoImpl(final java.sql.Connection userConn)
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
		return "REIMBURSEMENT_FINANCIAL_DATA";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected ReimbursementFinancialData fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			ReimbursementFinancialData dto = new ReimbursementFinancialData();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected ReimbursementFinancialData[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<ReimbursementFinancialData> resultList = new ArrayList<ReimbursementFinancialData>();
		while (rs.next()) {
			ReimbursementFinancialData dto = new ReimbursementFinancialData();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		ReimbursementFinancialData ret[] = new ReimbursementFinancialData[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(ReimbursementFinancialData dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setEsrmapId( rs.getInt( COLUMN_ESRMAP_ID ) );
		if (rs.wasNull()) {
			dto.setEsrmapIdNull( true );
		}
		
		dto.setDateOfOccurrence( rs.getDate(COLUMN_DATE_OF_OCCURRENCE ) );
		dto.setSummary( rs.getString( COLUMN_SUMMARY ) );
		dto.setType( rs.getString( COLUMN_TYPE ) );
		dto.setAmount( rs.getString( COLUMN_AMOUNT ) );
		dto.setCurrency( rs.getString( COLUMN_CURRENCY ) );
		dto.setReceiptsAvailable( rs.getString( COLUMN_RECEIPTS_AVAILABLE ) );
		dto.setUploadReceiptsId( rs.getInt( COLUMN_UPLOAD_RECEIPTS_ID ) );
		if (rs.wasNull()) {
			dto.setUploadReceiptsIdNull( true );
		}
		
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(ReimbursementFinancialData dto)
	{
	}

	/** 
	 * Returns all rows from the REIMBURSEMENT_FINANCIAL_DATA table that match the specified arbitrary SQL statement
	 */
	public ReimbursementFinancialData[] findByDynamicSelect(String sql, Object[] sqlParams) throws ReimbursementFinancialDataDaoException
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
			throw new ReimbursementFinancialDataDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the REIMBURSEMENT_FINANCIAL_DATA table that match the specified arbitrary SQL statement
	 */
	public ReimbursementFinancialData[] findByDynamicWhere(String sql, Object[] sqlParams) throws ReimbursementFinancialDataDaoException
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
			throw new ReimbursementFinancialDataDaoException( "Exception: " + _e.getMessage(), _e );
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
