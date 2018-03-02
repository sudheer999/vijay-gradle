package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.RetentionEmpSerReqMapDao;
import com.dikshatech.portal.dto.RetentionEmpSerReqMap;
import com.dikshatech.portal.dto.RetentionEmpSerReqMapPk;
import com.dikshatech.portal.exceptions.RetentionEmpSerReqMapDaoException;

public class RetentionEmpSerReqMapDaoImpl extends AbstractDAO implements RetentionEmpSerReqMapDao{

	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( RetentionEmpSerReqMapDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, SUB_DATE, REQ_ID, REQ_TYPE_ID, REGION_ID, REQUESTOR_ID, PROCESSCHAIN_ID, NOTIFY, SIBLINGS FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, SUB_DATE, REQ_ID, REQ_TYPE_ID, REGION_ID, REQUESTOR_ID, PROCESSCHAIN_ID, NOTIFY, SIBLINGS ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, SUB_DATE = ?, REQ_ID = ?, REQ_TYPE_ID = ?, REGION_ID = ?, REQUESTOR_ID = ?, PROCESSCHAIN_ID = ?, NOTIFY = ? , SIBLINGS = ? WHERE ID = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column SUB_DATE
	 */
	protected static final int COLUMN_SUB_DATE = 2;

	/** 
	 * Index of column REQ_ID
	 */
	protected static final int COLUMN_REQ_ID = 3;

	/** 
	 * Index of column REQ_TYPE_ID
	 */
	protected static final int COLUMN_REQ_TYPE_ID = 4;

	/** 
	 * Index of column REGION_ID
	 */
	protected static final int COLUMN_REGION_ID = 5;

	/** 
	 * Index of column REQUESTOR_ID
	 */
	protected static final int COLUMN_REQUESTOR_ID = 6;

	/** 
	 * Index of column APPROVAL_CHAIN
	 */
	protected static final int COLUMN_PROCESSCHAIN_ID = 7;

	/** 
	 * Index of column NOTIFY
	 */
	protected static final int COLUMN_NOTIFY = 8;
	protected static final int COLUMN_SIBLINGS = 9;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 9;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the RETENTION_EMP_SER_REQ_MAP table.
	 */
	public RetentionEmpSerReqMapPk insert(RetentionEmpSerReqMap dto) throws RetentionEmpSerReqMapDaoException
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
			stmt.setDate(index++, dto.getSubDate()==null ? null : new java.sql.Date( dto.getSubDate().getTime() ) );
			stmt.setString( index++, dto.getReqId() );
			if (dto.isReqTypeIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getReqTypeId() );
			}
		
			if (dto.isRegionIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getRegionId() );
			}
		
			if (dto.isRequestorIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getRequestorId() );
			}
		
			if(dto.isProcessChainIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getProcessChainId() );
			}
			
			stmt.setString( index++, dto.getNotify() );
			stmt.setString( index++, dto.getSiblings() );
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
			throw new RetentionEmpSerReqMapDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the RETENTION_EMP_SER_REQ_MAP table.
	 */
	public void update(RetentionEmpSerReqMapPk pk, RetentionEmpSerReqMap dto) throws RetentionEmpSerReqMapDaoException
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
			stmt.setDate(index++, dto.getSubDate()==null ? null : new java.sql.Date( dto.getSubDate().getTime() ) );
			stmt.setString( index++, dto.getReqId() );
			if (dto.isReqTypeIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getReqTypeId() );
			}
		
			if (dto.isRegionIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getRegionId() );
			}
		
			if (dto.isRequestorIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getRequestorId() );
			}
		
			if(dto.isProcessChainIdNull()) {
				stmt.setNull( index++, java.sql.Types.INTEGER );
			} else {
				stmt.setInt( index++, dto.getProcessChainId() );
			}
			
			stmt.setString( index++, dto.getNotify() );
			stmt.setString( index++, dto.getSiblings() );
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
			throw new RetentionEmpSerReqMapDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the RETENTION_EMP_SER_REQ_MAP table.
	 */
	public void delete(RetentionEmpSerReqMapPk pk) throws RetentionEmpSerReqMapDaoException
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
			throw new RetentionEmpSerReqMapDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the RETENTION_EMP_SER_REQ_MAP table that matches the specified primary-key value.
	 */
	public RetentionEmpSerReqMap findByPrimaryKey(RetentionEmpSerReqMapPk pk) throws RetentionEmpSerReqMapDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'ID = :id'.
	 */
	public RetentionEmpSerReqMap findByPrimaryKey(int id) throws RetentionEmpSerReqMapDaoException
	{
		RetentionEmpSerReqMap ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria ''.
	 */
	public RetentionEmpSerReqMap[] findAll() throws RetentionEmpSerReqMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'ID = :id'.
	 */
	public RetentionEmpSerReqMap[] findWhereIdEquals(int id) throws RetentionEmpSerReqMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'SUB_DATE = :subDate'.
	 */
	public RetentionEmpSerReqMap[] findWhereSubDateEquals(Date subDate) throws RetentionEmpSerReqMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE SUB_DATE = ? ORDER BY SUB_DATE", new Object[] { subDate==null ? null : new java.sql.Date( subDate.getTime() ) } );
	}

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'REQ_ID = :reqId'.
	 */
	public RetentionEmpSerReqMap[] findWhereReqIdEquals(String reqId) throws RetentionEmpSerReqMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE REQ_ID = ? ORDER BY REQ_ID", new Object[] { reqId } );
	}

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'REQ_TYPE_ID = :reqTypeId'.
	 */
	public RetentionEmpSerReqMap[] findWhereReqTypeIdEquals(int reqTypeId) throws RetentionEmpSerReqMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE REQ_TYPE_ID = ? ORDER BY REQ_TYPE_ID", new Object[] {  new Integer(reqTypeId) } );
	}

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'REGION_ID = :regionId'.
	 */
	public RetentionEmpSerReqMap[] findWhereRegionIdEquals(int regionId) throws RetentionEmpSerReqMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE REGION_ID = ? ORDER BY REGION_ID", new Object[] {  new Integer(regionId) } );
	}

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'REQUESTOR_ID = :requestorId'.
	 */
	public RetentionEmpSerReqMap[] findWhereRequestorIdEquals(int requestorId) throws RetentionEmpSerReqMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE REQUESTOR_ID = ? ORDER BY REQUESTOR_ID", new Object[] {  new Integer(requestorId) } );
	}

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'APPROVAL_CHAIN = :approvalChain'.
	 */
	public RetentionEmpSerReqMap[] findWhereApprovalChainEquals(String approvalChain) throws RetentionEmpSerReqMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE APPROVAL_CHAIN = ? ORDER BY APPROVAL_CHAIN", new Object[] { approvalChain } );
	}

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'NOTIFY = :notify'.
	 */
	public RetentionEmpSerReqMap[] findWhereNotifyEquals(String notify) throws RetentionEmpSerReqMapDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE NOTIFY = ? ORDER BY NOTIFY", new Object[] { notify } );
	}

	/**
	 * Method 'RetentionEmpSerReqMapDaoImpl'
	 * 
	 */
	public RetentionEmpSerReqMapDaoImpl()
	{
	}

	/**
	 * Method 'RetentionEmpSerReqMapDaoImpl'
	 * 
	 * @param userConn
	 */
	public RetentionEmpSerReqMapDaoImpl(final java.sql.Connection userConn)
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
		return "RETENTION_EMP_SER_REQ_MAP";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected RetentionEmpSerReqMap fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			RetentionEmpSerReqMap dto = new RetentionEmpSerReqMap();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected RetentionEmpSerReqMap[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<RetentionEmpSerReqMap> resultList = new ArrayList<RetentionEmpSerReqMap>();
		while (rs.next()) {
			RetentionEmpSerReqMap dto = new RetentionEmpSerReqMap();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		RetentionEmpSerReqMap ret[] = new RetentionEmpSerReqMap[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(RetentionEmpSerReqMap dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setSubDate( rs.getDate(COLUMN_SUB_DATE ) );
		dto.setReqId( rs.getString( COLUMN_REQ_ID ) );
		dto.setReqTypeId( rs.getInt( COLUMN_REQ_TYPE_ID ) );
		if (rs.wasNull()) {
			dto.setReqTypeIdNull( true );
		}
		
		dto.setRegionId( rs.getInt( COLUMN_REGION_ID ) );
		if (rs.wasNull()) {
			dto.setRegionIdNull( true );
		}
		
		dto.setRequestorId( rs.getInt( COLUMN_REQUESTOR_ID ) );
		if (rs.wasNull()) {
			dto.setRequestorIdNull( true );
		}
		
		
		dto.setProcessChainId( rs.getInt( COLUMN_PROCESSCHAIN_ID ) );
		if (rs.wasNull()) {
			dto.setProcessChainIdNull( true );
		}

		dto.setNotify( rs.getString( COLUMN_NOTIFY ) );
		dto.setSiblings( rs.getString( COLUMN_SIBLINGS ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(RetentionEmpSerReqMap dto)
	{
	}

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the specified arbitrary SQL statement
	 */
	public RetentionEmpSerReqMap[] findByDynamicSelect(String sql, Object[] sqlParams) throws RetentionEmpSerReqMapDaoException
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
			throw new RetentionEmpSerReqMapDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the specified arbitrary SQL statement
	 */
	public RetentionEmpSerReqMap[] findByDynamicWhere(String sql, Object[] sqlParams) throws RetentionEmpSerReqMapDaoException
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
			throw new RetentionEmpSerReqMapDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}
	
	
	public HashMap<Integer,String> getIdReqIdMap(int reqTypeId) throws RetentionEmpSerReqMapDaoException{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap<Integer,String> idReqIdMap = new HashMap<Integer, String>();
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = "SELECT ID, REQ_ID FROM RETENTION_EMP_SER_REQ_MAP WHERE REQ_TYPE_ID="+reqTypeId;
		
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL);
			}
		
			// prepare statement
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
		
			rs = stmt.executeQuery();
		
			while(rs.next()){
				idReqIdMap.put(rs.getInt("ID"),rs.getString("REQ_ID"));
			}
			
			
			return idReqIdMap;
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new RetentionEmpSerReqMapDaoException( "Exception: " + _e.getMessage(), _e );
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
