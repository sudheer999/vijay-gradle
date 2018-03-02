package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.RetentionBonusReconciliationHoldDao;
import com.dikshatech.portal.dto.RetentionBonusReconciliationHold;
import com.dikshatech.portal.dto.RetentionBonusReconciliationHoldPk;
import com.dikshatech.portal.exceptions.RetentionBonusReconciliationHoldDaoException;

public class RetentionBonusReconciliationHoldDaoImpl implements RetentionBonusReconciliationHoldDao{


	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( DepPerdiemHoldDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, REP_ID, USER_ID, STATUS, COMMENTS, ACTION_ON, ESCALATED_FROM  FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, REP_ID, USER_ID, STATUS, COMMENTS, ACTION_ON, ESCALATED_FROM  ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, REP_ID = ?, USER_ID = ?, STATUS = ?, COMMENTS = ?, ACTION_ON = ?, ESCALATED_FROM  = ? WHERE ID = ?";

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
	 * Index of column USER_ID
	 */
	protected static final int COLUMN_USER_ID = 3;

	/** 
	 * Index of column STATUS
	 */
	protected static final int COLUMN_STATUS = 4;

	/** 
	 * Index of column COMMENTS
	 */
	protected static final int COLUMN_COMMENTS = 5;

	/** 
	 * Index of column ACTION_ON
	 */
	protected static final int COLUMN_ACTION_ON = 6;

	/** 
	 * Index of column ESCALATED_FROM 
	 */
	protected static final int COLUMN_ESCALATED_FROM  = 7;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 7;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	
	
	
	/** 
	 * Inserts a new row in the retention_bonus_rec_hold table.
	 */
	
	@Override
	public RetentionBonusReconciliationHoldPk insert(RetentionBonusReconciliationHold dto) throws RetentionBonusReconciliationHoldDaoException {
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
			stmt.setInt( index++, dto.getUserId() );
			stmt.setInt( index++, dto.getStatus() );
			stmt.setString( index++, dto.getComments() );
			stmt.setTimestamp(index++, dto.getActionOn()==null ? null : new java.sql.Timestamp( dto.getActionOn().getTime() ) );
			stmt.setInt( index++, dto.getEscFrom() );
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
			throw new RetentionBonusReconciliationHoldDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}
	
	/** 
	 * Updates a single row in the retention_dep_perdiem_hold table.
	 */

	@Override
	public void update(RetentionBonusReconciliationHoldPk pk, RetentionBonusReconciliationHold dto) throws RetentionBonusReconciliationHoldDaoException {
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
			stmt.setInt( index++, dto.getUserId() );
			stmt.setInt( index++, dto.getStatus() );
			stmt.setString( index++, dto.getComments() );
			stmt.setTimestamp(index++, dto.getActionOn()==null ? null : new java.sql.Timestamp( dto.getActionOn().getTime() ) );
			stmt.setInt( index++, dto.getEscFrom() );
			stmt.setInt( 8, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new RetentionBonusReconciliationHoldDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
		
	}

	@Override
	public void delete(RetentionBonusReconciliationHoldPk pk) throws RetentionBonusReconciliationHoldDaoException {
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
			throw new RetentionBonusReconciliationHoldDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}
	/** 
	 * Returns the rows from the retention_dep_perdiem_hold table that matches the specified primary-key value.
	 */
	@Override
	public RetentionBonusReconciliationHold findByPrimaryKey(RetentionBonusReconciliationHoldPk pk) throws RetentionBonusReconciliationHoldDaoException {
		return findByPrimaryKey( pk.getId() );
	}
	/** 
	 * Returns all rows from the retention_dep_perdiem_hold table that match the criteria 'ID = :id'.
	 */
	@Override
	public RetentionBonusReconciliationHold findByPrimaryKey(int id) throws RetentionBonusReconciliationHoldDaoException {
		RetentionBonusReconciliationHold ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}
	/** 
	 * Returns all rows from the retention_dep_perdiem_hold table that match the criteria ''.
	 */
	@Override
	public RetentionBonusReconciliationHold[] findAll() throws RetentionBonusReconciliationHoldDaoException {
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the retention_dep_perdiem_hold table that match the criteria 'REP_ID = :repId'.
	 */
	@Override
	public RetentionBonusReconciliationHold[] findWhereRepIdEquals(Integer repId) throws RetentionBonusReconciliationHoldDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE REP_ID = ? ORDER BY REP_ID", new Object[] { repId } );
	}
	/** 
	 * Returns all rows from the retention_dep_perdiem_hold table that match the criteria 'USER_ID = :userId'.
	 */
	@Override
	public RetentionBonusReconciliationHold[] findWhereUserIdEquals(Integer userId) throws RetentionBonusReconciliationHoldDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE USER_ID = ? ORDER BY USER_ID", new Object[] { userId } );
	}
	/** 
	 * Returns all rows from the retention_dep_perdiem_hold table that match the criteria 'ESCALATED_FROM  = :escFrom'.
	 */
	@Override
	public RetentionBonusReconciliationHold[] findWhereEscFromEquals(Integer escFrom) throws RetentionBonusReconciliationHoldDaoException {

		return findByDynamicSelect( SQL_SELECT + " WHERE ESCALATED_FROM  = ? ORDER BY ESCALATED_FROM ", new Object[] { escFrom } );
	}

	/**
	 * Method 'RetentionBonusReconciliationHoldDaoImpl'
	 * 
	 */
	public RetentionBonusReconciliationHoldDaoImpl()
	{
	}

	/**
	 * Method 'RetentionBonusReconciliationHoldDaoImpl'
	 * 
	 * @param userConn
	 */
	public RetentionBonusReconciliationHoldDaoImpl(final java.sql.Connection userConn)
	{
		this.userConn = userConn;
	}

	@Override
	public void setMaxRows(int maxRows) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMaxRows() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/** 
	 * Fetches a single row from the result set
	 */
	protected RetentionBonusReconciliationHold fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			RetentionBonusReconciliationHold dto = new RetentionBonusReconciliationHold();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected RetentionBonusReconciliationHold[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<RetentionBonusReconciliationHold> resultList = new ArrayList<RetentionBonusReconciliationHold>();
		while (rs.next()) {
			RetentionBonusReconciliationHold dto = new RetentionBonusReconciliationHold();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		RetentionBonusReconciliationHold ret[] = new RetentionBonusReconciliationHold[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(RetentionBonusReconciliationHold dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setRepId( rs.getInt( COLUMN_REP_ID ) );
		dto.setUserId( rs.getInt( COLUMN_USER_ID ) );
		dto.setStatus( rs.getInt( COLUMN_STATUS ) );
		dto.setComments( rs.getString( COLUMN_COMMENTS ) );
		dto.setActionOn( rs.getTimestamp(COLUMN_ACTION_ON ) );
		dto.setEscFrom( rs.getInt( COLUMN_ESCALATED_FROM  ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(RetentionBonusReconciliationHold dto)
	{
	}
	/** 
	 * Returns all rows from the retention_dep_perdiem_hold table that match the specified arbitrary SQL statement
	 */
	
	@Override
	public RetentionBonusReconciliationHold[] findByDynamicSelect(String sql, Object[] sqlParams) throws RetentionBonusReconciliationHoldDaoException {
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
					throw new RetentionBonusReconciliationHoldDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the retention_dep_perdiem_hold table that match the specified arbitrary SQL statement
	 */
	@Override
	public RetentionBonusReconciliationHold[] findByDynamicWhere(String sql, Object[] sqlParams) throws RetentionBonusReconciliationHoldDaoException {
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
					throw new RetentionBonusReconciliationHoldDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "RETENTION_BONUS_REC_HOLD";
	}
	

}