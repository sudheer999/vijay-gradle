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

import com.dikshatech.portal.dao.RetentionBonusReconciliationReqDao;
import com.dikshatech.portal.dto.RetentionBonusReconciliationReq;
import com.dikshatech.portal.dto.RetentionBonusReconciliationReqPk;
import com.dikshatech.portal.dto.DepPerdiemReq;
import com.dikshatech.portal.exceptions.RetentionBonusReconciliationReqDaoException;

public class RetentionBonusReconciliationReqDaoImpl implements RetentionBonusReconciliationReqDao{


	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( RetentionBonusReconciliationReqDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, BR_ID, SEQ_ID, ASSIGNED_TO, RECEIVED_ON, SUBMITTED_ON, IS_ESCALATED FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, BR_ID, SEQ_ID, ASSIGNED_TO, RECEIVED_ON, SUBMITTED_ON, IS_ESCALATED ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, BR_ID = ?, SEQ_ID = ?, ASSIGNED_TO = ?, RECEIVED_ON = ?, SUBMITTED_ON = ?, IS_ESCALATED = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column BR_ID
	 */
	protected static final int COLUMN_BR_ID = 2;

	/** 
	 * Index of column SEQ_ID
	 */
	protected static final int COLUMN_SEQ_ID = 3;

	/** 
	 * Index of column ASSIGNED_TO
	 */
	protected static final int COLUMN_ASSIGNED_TO = 4;

	/** 
	 * Index of column RECEIVED_ON
	 */
	protected static final int COLUMN_RECEIVED_ON = 5;

	/** 
	 * Index of column SUBMITTED_ON
	 */
	protected static final int COLUMN_SUBMITTED_ON = 6;

	/** 
	 * Index of column IS_ESCALATED
	 */
	protected static final int COLUMN_IS_ESCALATED = 7;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 7;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(RetentionBonusReconciliationReq dto)
	{
	}

	/** 
	 * Inserts a new row in the RETENTION_BONUS_RECONCILIATION_REQ table.
	 */
	@Override
	public RetentionBonusReconciliationReqPk insert(RetentionBonusReconciliationReq dto) throws RetentionBonusReconciliationReqDaoException {
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
			stmt.setInt( index++, dto.getBonusId() );
			stmt.setInt( index++, dto.getSeqId() );
			stmt.setInt( index++, dto.getAssignedTo() );
			stmt.setDate(index++, dto.getReceivedOn()==null ? null : new java.sql.Date( dto.getReceivedOn().getTime() ) );
			stmt.setDate(index++, dto.getSubmittedOn()==null ? null : new java.sql.Date( dto.getSubmittedOn().getTime() ) );
			/*if (dto.isEscalatedNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {*/
				stmt.setInt( index++, dto.getIsEscalated() );
		//	}
		
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
			throw new RetentionBonusReconciliationReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
	}
	/** 
	 * Updates a single row in the RETENTION_BONUS_RECONCILIATION_REQ table.
	 */
	@Override
	public void update(RetentionBonusReconciliationReqPk pk, RetentionBonusReconciliationReq dto) throws RetentionBonusReconciliationReqDaoException {
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
			stmt.setInt( index++, dto.getBonusId() );
			stmt.setInt( index++, dto.getSeqId() );
			stmt.setInt( index++, dto.getAssignedTo() );
			stmt.setDate(index++, dto.getReceivedOn()==null ? null : new java.sql.Date( dto.getReceivedOn().getTime() ) );
			stmt.setDate(index++, dto.getSubmittedOn()==null ? null : new java.sql.Date( dto.getSubmittedOn().getTime() ) );
			/*if (dto.isEscalatedNull()) {
				stmt.setNull(index++, java.sql.Types.INTEGER );
			} else {*/
				stmt.setInt( index++, dto.getIsEscalated());
		//	}
		
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
			throw new RetentionBonusReconciliationReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
		
	}
	/** 
	 * Deletes a single row in the RETENTION_BONUS_RECONCILIATION_REQ table.
	 */
	@Override
	public void delete(RetentionBonusReconciliationReqPk pk) throws RetentionBonusReconciliationReqDaoException {
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
			throw new RetentionBonusReconciliationReqDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}
	/** 
	 * Returns the rows from the RETENTION_BONUS_RECONCILIATION_REQ table that matches the specified primary-key value.
	 */
	@Override
	public RetentionBonusReconciliationReq findByPrimaryKey(RetentionBonusReconciliationReqPk pk) throws RetentionBonusReconciliationReqDaoException {
		return findByPrimaryKey( pk.getId() );
	}
	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'ID = :id'.
	 */
	@Override
	public RetentionBonusReconciliationReq findByPrimaryKey(int id) throws RetentionBonusReconciliationReqDaoException {
		RetentionBonusReconciliationReq ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}
	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria ''.
	 */
	@Override
	public RetentionBonusReconciliationReq[] findAll() throws RetentionBonusReconciliationReqDaoException {
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}
	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'ID = :id'.
	 */
	@Override
	public RetentionBonusReconciliationReq[] findWhereIdEquals(int id) throws RetentionBonusReconciliationReqDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'BR_ID = :bonusId'.
	 */
	@Override
	public RetentionBonusReconciliationReq[] findWhereBonusIdEquals(int bonusId) throws RetentionBonusReconciliationReqDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE BR_ID = ? ORDER BY BR_ID", new Object[] {  new Integer(bonusId) } );
	}
	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'SEQ_ID = :seqId'.
	 */
	@Override
	public RetentionBonusReconciliationReq[] findWhereSeqIdEquals(int seqId) throws RetentionBonusReconciliationReqDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE SEQ_ID = ? ORDER BY SEQ_ID", new Object[] {  new Integer(seqId) } );
	}
	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'ASSIGNED_TO = :assignedTo'.
	 */
	@Override
	public RetentionBonusReconciliationReq[] findWhereAssignedToEquals(int assignedTo) throws RetentionBonusReconciliationReqDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE ASSIGNED_TO = ? ORDER BY ASSIGNED_TO", new Object[] {  new Integer(assignedTo) } );
}
	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'RECEIVED_ON = :receivedOn'.
	 */
	@Override
	public RetentionBonusReconciliationReq[] findWhereReceivedOnEquals(Date receivedOn) throws RetentionBonusReconciliationReqDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE RECEIVED_ON = ? ORDER BY RECEIVED_ON", new Object[] { receivedOn==null ? null : new java.sql.Date( receivedOn.getTime() ) } );

	}
	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'SUBMITTED_ON = :submittedOn'.
	 */
	@Override
	public RetentionBonusReconciliationReq[] findWhereSubmittedOnEquals(Date submittedOn) throws RetentionBonusReconciliationReqDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE SUBMITTED_ON = ? ORDER BY SUBMITTED_ON", new Object[] { submittedOn==null ? null : new java.sql.Date( submittedOn.getTime() ) } );

	}
	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'ESCALATED_FROM = :isEscalated'.
	 */
	@Override
	public RetentionBonusReconciliationReq[] findWhereIsEscalatedEquals(int isEscalated) throws RetentionBonusReconciliationReqDaoException {
		return findByDynamicSelect( SQL_SELECT + " WHERE IS_ESCALATED= ? ORDER BY IS_ESCALATED", new Object[] {  new Integer(isEscalated) } );
	}

	
	/**
	 * Method 'RetentionBonusReconciliationReqDaoImpl'
	 * 
	 */
	public RetentionBonusReconciliationReqDaoImpl()
	{
	}

	/**
	 * Method 'RetentionBonusReconciliationReqDaoImpl'
	 * 
	 * @param userConn
	 */
	public RetentionBonusReconciliationReqDaoImpl(final java.sql.Connection userConn)
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
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
	return "RETENTION_BONUS_RECONCILIATION_REQ";
	}
	/** 
	 * Fetches a single row from the result set
	 */
	protected RetentionBonusReconciliationReq fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			RetentionBonusReconciliationReq dto = new RetentionBonusReconciliationReq();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected RetentionBonusReconciliationReq[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<RetentionBonusReconciliationReq> resultList = new ArrayList<RetentionBonusReconciliationReq>();
		while (rs.next()) {
			RetentionBonusReconciliationReq dto = new RetentionBonusReconciliationReq();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		RetentionBonusReconciliationReq ret[] = new RetentionBonusReconciliationReq[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(RetentionBonusReconciliationReq dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setBonusId( rs.getInt( COLUMN_BR_ID ) );
		dto.setSeqId( rs.getInt( COLUMN_SEQ_ID ) );
		dto.setAssignedTo( rs.getInt( COLUMN_ASSIGNED_TO ) );
		dto.setReceivedOn( rs.getDate(COLUMN_RECEIVED_ON ) );
		dto.setSubmittedOn( rs.getDate(COLUMN_SUBMITTED_ON ) );
		dto.setIsEscalated( rs.getInt( COLUMN_IS_ESCALATED ) );
		if (rs.wasNull()) {
			dto.setEscalatedNull( true );
		}
		
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(DepPerdiemReq dto)
	{
	}

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_REQ table that match the specified arbitrary SQL statement
	 */
	public RetentionBonusReconciliationReq[] findByDynamicSelect(String sql, Object[] sqlParams) throws RetentionBonusReconciliationReqDaoException
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
			throw new RetentionBonusReconciliationReqDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the specified arbitrary SQL statement
	 */
	public RetentionBonusReconciliationReq[] findByDynamicWhere(String sql, Object[] sqlParams) throws RetentionBonusReconciliationReqDaoException
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
			throw new RetentionBonusReconciliationReqDaoException( "Exception: " + _e.getMessage(), _e );
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
