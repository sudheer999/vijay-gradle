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

import com.dikshatech.portal.dao.RmgTimeSheetReconDao;
import com.dikshatech.portal.dto.RmgTimeSheetReco;
import com.dikshatech.portal.dto.RmgTimeSheetRecoPk;
import com.dikshatech.portal.exceptions.RmgTimeSheetReconDaoException;

public class RmgTimeSheetReconDaoImpl implements RmgTimeSheetReconDao{
	
	
	
	
	
	
	

	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	//protected static final Logger logger = Logger.getLogger( DepPerdiemDaoImpl.class );
	protected static final Logger logger = Logger.getLogger( RmgTimeSheetReconDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, ESR_MAP_ID, TERM, MONTH, YEAR, SUBMITTED_ON, COMPLETED_ON, STATUS, HTML_STATUS FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, ESR_MAP_ID, TERM, MONTH, YEAR, SUBMITTED_ON, COMPLETED_ON, STATUS, HTML_STATUS ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, ESR_MAP_ID = ?, TERM = ?, MONTH = ?, YEAR = ?, SUBMITTED_ON = ?, COMPLETED_ON = ?, STATUS = ?, HTML_STATUS = ?,INVOICE_STATUS=? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column ESR_MAP_ID
	 */
	protected static final int COLUMN_ESR_MAP_ID = 2;

	/** 
	 * Index of column TERM
	 */
	protected static final int COLUMN_TERM = 3;

	/** 
	 * Index of column MONTH
	 */
	protected static final int COLUMN_MONTH = 4;

	/** 
	 * Index of column YEAR
	 */
	protected static final int COLUMN_YEAR = 5;

	/** 
	 * Index of column SUBMITTED_ON
	 */
	protected static final int COLUMN_SUBMITTED_ON = 6;

	/** 
	 * Index of column COMPLETED_ON
	 */
	protected static final int COLUMN_COMPLETED_ON = 7;

	/** 
	 * Index of column STATUS
	 */
	protected static final int COLUMN_STATUS = 8;

	/** 
	 * Index of column HTML_STATUS
	 */
	protected static final int COLUMN_HTML_STATUS = 9;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 9;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the DEP_PERDIEM table.
	 */
	public RmgTimeSheetRecoPk insert(RmgTimeSheetReco dto) throws RmgTimeSheetReconDaoException
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
			stmt.setInt( index++, dto.getEsrMapId() );
			stmt.setString( index++, dto.getTerm() );
			stmt.setString( index++, dto.getMonth() );
			stmt.setInt( index++, dto.getYear() );
			stmt.setDate(index++, dto.getSubmittedOn()==null ? null : new java.sql.Date( dto.getSubmittedOn().getTime() ) );
			stmt.setDate(index++, dto.getCompletedOn()==null ? null : new java.sql.Date( dto.getCompletedOn().getTime() ) );
			stmt.setString( index++, dto.getStatus() );
			stmt.setString( index++, dto.getHtmlStatus() );
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
			throw new RmgTimeSheetReconDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the DEP_PERDIEM table.
	 */
	public void update(RmgTimeSheetRecoPk pk, RmgTimeSheetReco dto) throws RmgTimeSheetReconDaoException
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
			stmt.setInt( index++, dto.getEsrMapId() );
			stmt.setString( index++, dto.getTerm() );
			stmt.setString( index++, dto.getMonth() );
			stmt.setInt( index++, dto.getYear() );
			stmt.setDate(index++, dto.getSubmittedOn()==null ? null : new java.sql.Date( dto.getSubmittedOn().getTime() ) );
			stmt.setDate(index++, dto.getCompletedOn()==null ? null : new java.sql.Date( dto.getCompletedOn().getTime() ) );
			stmt.setString( index++, dto.getStatus() );
			stmt.setString( index++, dto.getHtmlStatus() );
			
			stmt.setString( index++, dto.getInvoiceStatus());
			
			stmt.setInt( 11, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new RmgTimeSheetReconDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the DEP_PERDIEM table.
	 */
	public void delete(RmgTimeSheetRecoPk pk) throws RmgTimeSheetReconDaoException
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
			throw new RmgTimeSheetReconDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the DEP_PERDIEM table that matches the specified primary-key value.
	 */
	public RmgTimeSheetReco findByPrimaryKey(RmgTimeSheetRecoPk pk) throws RmgTimeSheetReconDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'ID = :id'.
	 */
	public RmgTimeSheetReco findByPrimaryKey(int id) throws RmgTimeSheetReconDaoException
	{
		RmgTimeSheetReco ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria ''.
	 */
	public RmgTimeSheetReco[] findAll() throws RmgTimeSheetReconDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'ID = :id'.
	 */
	public RmgTimeSheetReco[] findWhereIdEquals(int id) throws RmgTimeSheetReconDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'ESR_MAP_ID = :esrMapId'.
	 */
	public RmgTimeSheetReco findWhereEsrMapIdEquals(int esrMapId) throws RmgTimeSheetReconDaoException
	{
		RmgTimeSheetReco[] d = findByDynamicSelect(SQL_SELECT + " WHERE ESR_MAP_ID = ? ORDER BY ESR_MAP_ID", new Object[] { new Integer(esrMapId) });
		return d == null || d.length <= 0 ? null : d[0];
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'TERM = :term'.
	 */
	public RmgTimeSheetReco[] findWhereTermEquals(String term) throws RmgTimeSheetReconDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE TERM = ? ORDER BY TERM", new Object[] { term } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'MONTH = :month'.
	 */
	public RmgTimeSheetReco[] findWhereMonthEquals(String month) throws RmgTimeSheetReconDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE MONTH = ? ORDER BY MONTH", new Object[] { month } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'YEAR = :year'.
	 */
	public RmgTimeSheetReco[] findWhereYearEquals(int year) throws RmgTimeSheetReconDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE YEAR = ? ORDER BY YEAR", new Object[] {  new Integer(year) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'SUBMITTED_ON = :submittedOn'.
	 */
	public RmgTimeSheetReco[] findWhereSubmittedOnEquals(Date submittedOn) throws RmgTimeSheetReconDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE SUBMITTED_ON = ? ORDER BY SUBMITTED_ON", new Object[] { submittedOn==null ? null : new java.sql.Date( submittedOn.getTime() ) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'COMPLETED_ON = :completedOn'.
	 */
	public RmgTimeSheetReco[] findWhereCompletedOnEquals(Date completedOn) throws RmgTimeSheetReconDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE COMPLETED_ON = ? ORDER BY COMPLETED_ON", new Object[] { completedOn==null ? null : new java.sql.Date( completedOn.getTime() ) } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'STATUS = :status'.
	 */
	public RmgTimeSheetReco[] findWhereStatusEquals(String status) throws RmgTimeSheetReconDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE STATUS = ? ORDER BY STATUS", new Object[] { status } );
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'HTML_STATUS = :htmlStatus'.
	 */
	public RmgTimeSheetReco[] findWhereHtmlStatusEquals(String htmlStatus) throws RmgTimeSheetReconDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE HTML_STATUS = ? ORDER BY HTML_STATUS", new Object[] { htmlStatus } );
	}

	/**
	 * Method 'RmgTimeSheetRecoDaoImpl'
	 * 
	 */
/*	public RmgTimeSheetRecoDaoImpl()
	{
	}

	*//**
	 * Method 'RmgTimeSheetRecoDaoImpl'
	 * 
	 * @param userConn
	 *//*
	public RmgTimeSheetRecoDaoImpl(final java.sql.Connection userConn)
	{
		this.userConn = userConn;
	}*/

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
		return "RMG_TIMESHEET";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected RmgTimeSheetReco fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			RmgTimeSheetReco dto = new RmgTimeSheetReco();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected RmgTimeSheetReco[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<RmgTimeSheetReco> resultList = new ArrayList<RmgTimeSheetReco>();
		while (rs.next()) {
			RmgTimeSheetReco dto = new RmgTimeSheetReco();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		RmgTimeSheetReco ret[] = new RmgTimeSheetReco[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(RmgTimeSheetReco dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setEsrMapId( rs.getInt( COLUMN_ESR_MAP_ID ) );
		dto.setTerm( rs.getString( COLUMN_TERM ) );
		dto.setMonth( rs.getString( COLUMN_MONTH ) );
		dto.setYear( rs.getInt( COLUMN_YEAR ) );
		dto.setSubmittedOn( rs.getDate(COLUMN_SUBMITTED_ON ) );
		dto.setCompletedOn( rs.getDate(COLUMN_COMPLETED_ON ) );
		dto.setStatus( rs.getString( COLUMN_STATUS ) );
		dto.setHtmlStatus( rs.getString( COLUMN_HTML_STATUS ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(RmgTimeSheetReco dto)
	{
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the specified arbitrary SQL statement
	 */
	public RmgTimeSheetReco[] findByDynamicSelect(String sql, Object[] sqlParams) throws RmgTimeSheetReconDaoException
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
			throw new RmgTimeSheetReconDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the DEP_PERDIEM table that match the specified arbitrary SQL statement
	 */
	public RmgTimeSheetReco[] findByDynamicWhere(String sql, Object[] sqlParams) throws RmgTimeSheetReconDaoException
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
			throw new RmgTimeSheetReconDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}
	
	
	public int findMaxId() throws RmgTimeSheetReconDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			// construct the SQL statement
			final String SQL = "SELECT MAX(ID) AS COUNT FROM RMG_TIMESHEET";

			if (logger.isDebugEnabled()) {
				logger.debug("Executing " + SQL);
			}

			// prepare statement
			stmt = conn.prepareStatement(SQL);
			stmt.setMaxRows(maxRows);

			rs = stmt.executeQuery();

			// fetch the results
			while (rs.next()) {
				count = rs.getInt("COUNT");
			}
			count = (count == 0) ? 1 : (count+1);
			return count;
		} catch (Exception _e) {
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new RmgTimeSheetReconDaoException("Exception: "	+ _e.getMessage(), _e);
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}
	
	
	public int update(String sql) throws RmgTimeSheetReconDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int rowsUpdated = 0;

		try {
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			// construct the SQL statement
			final String SQL = sql;

			if (logger.isDebugEnabled()) {
				logger.debug("Executing " + SQL);
			}

			// prepare statement
			stmt = conn.prepareStatement(SQL);
			stmt.setMaxRows(maxRows);

			rowsUpdated =  stmt.executeUpdate();
			
			return rowsUpdated;
		} catch (Exception _e) {
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new RmgTimeSheetReconDaoException("Exception: "
					+ _e.getMessage(), _e);
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}


	
	
	
	public RmgTimeSheetReconDaoImpl()
	{
	}

	/**
	 * Method 'RmgTimeSheetRecoReqDaoImpl'
	 * 
	 * @param userConn
	 */
	public RmgTimeSheetReconDaoImpl(final java.sql.Connection userConn)
	{
		this.userConn = userConn;
	}

}
