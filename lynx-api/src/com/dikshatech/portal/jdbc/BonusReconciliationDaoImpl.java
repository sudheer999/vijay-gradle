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

import com.dikshatech.portal.dao.BonusReconciliationDao;
import com.dikshatech.portal.dto.BonusReconciliation;
import com.dikshatech.portal.dto.BonusReconciliationPk;
import com.dikshatech.portal.exceptions.BonusReconciliationDaoException;




public class BonusReconciliationDaoImpl implements BonusReconciliationDao{
	
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( DepPerdiemDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, ESR_MAP_ID, MONTH, YEAR, SUBMITTED_ON, COMPLETED_ON, STATUS, HTML_STATUS FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, ESR_MAP_ID, MONTH, YEAR, SUBMITTED_ON, COMPLETED_ON, STATUS, HTML_STATUS ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, ESR_MAP_ID = ?, MONTH = ?, YEAR = ?, SUBMITTED_ON = ?, COMPLETED_ON = ?, STATUS = ?, HTML_STATUS = ? WHERE ID = ?";

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
	 * Index of column MONTH
	 */
	protected static final int COLUMN_MONTH = 3;

	/** 
	 * Index of column YEAR
	 */
	protected static final int COLUMN_YEAR = 4;

	/** 
	 * Index of column SUBMITTED_ON
	 */
	protected static final int COLUMN_SUBMITTED_ON = 5;

	/** 
	 * Index of column COMPLETED_ON
	 */
	protected static final int COLUMN_COMPLETED_ON = 6;

	/** 
	 * Index of column STATUS
	 */
	protected static final int COLUMN_STATUS = 7;

	/** 
	 * Index of column HTML_STATUS
	 */
	protected static final int COLUMN_HTML_STATUS = 8;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 8;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/**
	 * Method 'BonusReconciliationDaoImpl'
	 * 
	 */
	public BonusReconciliationDaoImpl()
	{
	}

	/**
	 * Method 'BonusReconciliationDaoImpl'
	 * 
	 * @param userConn
	 */
	public BonusReconciliationDaoImpl(final java.sql.Connection userConn)
	{
		this.userConn = userConn;
	}

	

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "BONUS_RECONCILIATION";
	}



	
	@Override
	public BonusReconciliationPk insert(BonusReconciliation dto) throws BonusReconciliationDaoException {
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
			throw new BonusReconciliationDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
		
	}

	@Override
	public void update(BonusReconciliationPk pk, BonusReconciliation dto) throws BonusReconciliationDaoException {
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
			stmt.setString( index++, dto.getMonth() );
			stmt.setInt( index++, dto.getYear() );
			stmt.setDate(index++, dto.getSubmittedOn()==null ? null : new java.sql.Date( dto.getSubmittedOn().getTime() ) );
			stmt.setDate(index++, dto.getCompletedOn()==null ? null : new java.sql.Date( dto.getCompletedOn().getTime() ) );
			stmt.setString( index++, dto.getStatus() );
			stmt.setString( index++, dto.getHtmlStatus() );
			stmt.setInt( 9, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new BonusReconciliationDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
	}

	@Override
	public void delete(BonusReconciliation pk) throws BonusReconciliationDaoException {
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
			throw new BonusReconciliationDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	@Override
	public BonusReconciliation findByPrimaryKey(BonusReconciliationPk pk) throws BonusReconciliationDaoException {
		// TODO Auto-generated method stub
		return findByPrimaryKey( pk.getId() );
	}

	@Override
	public BonusReconciliation findByPrimaryKey(int id) throws BonusReconciliationDaoException {
		BonusReconciliation ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	
	}

	@Override
	public BonusReconciliation[] findAll() throws BonusReconciliationDaoException {
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	@Override
	public BonusReconciliation[] findWhereIdEquals(int id) throws BonusReconciliationDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	@Override
	public BonusReconciliation findWhereEsrMapIdEquals(int esrMapId) throws BonusReconciliationDaoException {
		BonusReconciliation[] d = findByDynamicSelect(SQL_SELECT + " WHERE ESR_MAP_ID = ? ORDER BY ESR_MAP_ID", new Object[] { new Integer(esrMapId) });
		return d == null || d.length <= 0 ? null : d[0];
	}

	@Override
	public BonusReconciliation[] findWhereTermEquals(String term) throws BonusReconciliationDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE TERM = ? ORDER BY TERM", new Object[] { term } );
	}

	@Override
	public BonusReconciliation[] findWhereMonthEquals(String month) throws BonusReconciliationDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE MONTH = ? ORDER BY MONTH", new Object[] { month } );
	}

	@Override
	public BonusReconciliation[] findWhereYearEquals(int year) throws BonusReconciliationDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE YEAR = ? ORDER BY YEAR", new Object[] {  new Integer(year) } );
	}

	@Override
	public BonusReconciliation[] findWhereSubmittedOnEquals(Date submittedOn) throws BonusReconciliationDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE SUBMITTED_ON = ? ORDER BY SUBMITTED_ON", new Object[] { submittedOn==null ? null : new java.sql.Date( submittedOn.getTime() ) } );
	}

	@Override
	public BonusReconciliation[] findWhereCompletedOnEquals(Date completedOn) throws BonusReconciliationDaoException {

		return findByDynamicSelect( SQL_SELECT + " WHERE COMPLETED_ON = ? ORDER BY COMPLETED_ON", new Object[] { completedOn==null ? null : new java.sql.Date( completedOn.getTime() ) } );
	}

	@Override
	public BonusReconciliation[] findWhereStatusEquals(String status) throws BonusReconciliationDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE STATUS = ? ORDER BY STATUS", new Object[] { status } );
	}

	@Override
	public BonusReconciliation[] findWhereHtmlStatusEquals(String htmlStatus) throws BonusReconciliationDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE HTML_STATUS = ? ORDER BY HTML_STATUS", new Object[] { htmlStatus } );
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
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(BonusReconciliation dto)
	{
	}

	
	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the specified arbitrary SQL statement
	 */	
	@Override
	public BonusReconciliation[] findByDynamicSelect(String sql, Object[] sqlParams) throws BonusReconciliationDaoException {
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
					throw new BonusReconciliationDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Fetches multiple rows from the result set
	 */
	protected BonusReconciliation[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<BonusReconciliation> resultList = new ArrayList<BonusReconciliation>();
		while (rs.next()) {
			BonusReconciliation dto = new BonusReconciliation();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		BonusReconciliation ret[] = new BonusReconciliation[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}
	
	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(BonusReconciliation dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setEsrMapId( rs.getInt( COLUMN_ESR_MAP_ID ) );
		dto.setMonth( rs.getString( COLUMN_MONTH ) );
		dto.setYear( rs.getInt( COLUMN_YEAR ) );
		dto.setSubmittedOn( rs.getDate(COLUMN_SUBMITTED_ON ) );
		dto.setCompletedOn( rs.getDate(COLUMN_COMPLETED_ON ) );
		dto.setStatus( rs.getString( COLUMN_STATUS ) );
		dto.setHtmlStatus( rs.getString( COLUMN_HTML_STATUS ) );
	}
	
	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the specified arbitrary SQL statement
	 */
	public BonusReconciliation[] findByDynamicWhere(String sql, Object[] sqlParams) throws BonusReconciliationDaoException {
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
					throw new BonusReconciliationDaoException( "Exception: " + _e.getMessage(), _e );
				}
				finally {
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied) {
						ResourceManager.close(conn);
					}
				
				}
				
	}
	
	public int findMaxId() throws BonusReconciliationDaoException {
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
			final String SQL = "SELECT MAX(ID) AS COUNT FROM BONUS_RECONCILIATION";

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
			throw new BonusReconciliationDaoException("Exception: "	+ _e.getMessage(), _e);
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}
	
}
