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

import com.dikshatech.portal.dao.ResourceReqMapHistoryDao;
import com.dikshatech.portal.dto.ResourceReqMapHistory;
import com.dikshatech.portal.dto.ResourceReqMapHistoryPk;
import com.dikshatech.portal.exceptions.ResourceReqMapHistoryDaoException;

public class ResourceReqMapHistoryDaoImpl implements ResourceReqMapHistoryDao {
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( ResourceReqMappingDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT ID, RES_MAP_ID, RESOURCE_NAME,TOTAL_EXP, RELEVANT_EXP, CURRENT_EMPLOYER, CURRENT_ROLE, CTC,ECTC,NOTICE_PERIOD,COMMENTS, MODIFIED_BY, MODIFIED_ON FROM " + getTableName() + "";
	
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " (ID, RES_MAP_ID, RESOURCE_NAME,TOTAL_EXP, RELEVANT_EXP, CURRENT_EMPLOYER, CURRENT_ROLE, CTC,ECTC,NOTICE_PERIOD,COMMENTS, MODIFIED_BY, MODIFIED_ON) VALUES ( ?, ?, ?, ?, ?, ?, ?,?, ?, ?,?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, RES_MAP_ID = ?, RESOURCE_NAME = ?, TOTAL_EXP=?, RELEVANT_EXP=?, CURRENT_EMPLOYER=?, CURRENT_ROLE=?, CTC=?, ECTC=?, COMMENTS=?, MODIFIED_BY=?, MODIFIED_ON=? WHERE ID = ?";
	
	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column CAL_ID
	 */
	protected static final int COLUMN_RES_MAP_ID = 2;

	/** 
	 * Index of column DATE_PICKER
	 */
	protected static final int COLUMN_RESOURCE_NAME = 3;
    
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_TOTAL_EXP = 4;

	/** 
	 * Index of column CAL_ID
	 */
	protected static final int COLUMN_RELEVANT_EXP = 5;
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_CURRENT_EMPLOYER = 6;
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_CURRENT_ROLE = 7;
	
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_CTC = 8;

	/** 
	 * Index of column CAL_ID
	 */
	protected static final int COLUMN_ECTC = 9;

	/** 
	 * Index of column DATE_PICKER
	 */
	protected static final int COLUMN_NOTICE_PERIOD= 10;
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_COMMENTS = 11;
	
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_MODIFIED_BY = 12;
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_MODIFIED_ON = 13;
	
	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 23;

	/** 
	 * Index of primary-key column ID
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the HOLIDAYS table.
	 */
	public ResourceReqMapHistoryPk insert(ResourceReqMapHistory dto) throws ResourceReqMapHistoryDaoException
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
			stmt.setInt( index++, dto.getResMapId() );
			stmt.setString( index++, dto.getResourceName() );
			stmt.setInt( index++, dto.getTotalExp() );
			stmt.setInt( index++, dto.getRelevantExp() );
			stmt.setString( index++, dto.getCurrentEmployer() );
			stmt.setString( index++, dto.getCurrentRole() );
			stmt.setString( index++, dto.getCtc()+"");
			stmt.setString( index++, dto.getEctc()+"" );
			stmt.setString( index++, dto.getNoticePeriod() );
			stmt.setString( index++, dto.getComments());
			stmt.setInt( index++, dto.getModifiedBy() );
		    stmt.setDate(index++,  new java.sql.Date(new Date().getTime()));
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
			throw new ResourceReqMapHistoryDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the HOLIDAYS table.
	 */
	public void update(ResourceReqMapHistoryPk pk, ResourceReqMapHistory dto) throws ResourceReqMapHistoryDaoException
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
			stmt.setInt( index++, dto.getResMapId() );
			stmt.setString( index++, dto.getResourceName() );
			stmt.setInt( index++, dto.getTotalExp() );
			stmt.setInt( index++, dto.getRelevantExp() );
			stmt.setString( index++, dto.getCurrentEmployer() );
			stmt.setString( index++, dto.getCurrentRole() );
			stmt.setString( index++, dto.getCtc()+"");
			stmt.setString( index++, dto.getEctc()+"" );
			stmt.setString( index++, dto.getNoticePeriod() );
			stmt.setString( index++, dto.getComments());
			stmt.setInt( index++, dto.getModifiedBy() );
			stmt.setDate(index++,  new java.sql.Date(new Date().getTime()));
			stmt.setInt( 14, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new ResourceReqMapHistoryDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the HOLIDAYS table.
	 */
	public void delete(ResourceReqMapHistoryPk pk) throws ResourceReqMapHistoryDaoException
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
			throw new ResourceReqMapHistoryDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the HOLIDAYS table that matches the specified primary-key value.
	 */
	public ResourceReqMapHistory findByPrimaryKey(ResourceReqMapHistoryPk pk) throws ResourceReqMapHistoryDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria 'ID = :id'.
	 */
	public ResourceReqMapHistory findByPrimaryKey(int id) throws ResourceReqMapHistoryDaoException
	{
		ResourceReqMapHistory ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria ''.
	 */
	public ResourceReqMapHistory[] findAll() throws ResourceReqMapHistoryDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	
	/**
	 * Method 'HolidaysDaoImpl'
	 * 
	 */
	public  ResourceReqMapHistoryDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * Method 'HolidaysDaoImpl'
	 * 
	 * @param userConn
	 */
	public ResourceReqMapHistoryDaoImpl(final java.sql.Connection userConn)
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
		return "RESOURCES_MAPPING_HISTORY";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected ResourceReqMapHistory fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			ResourceReqMapHistory dto = new ResourceReqMapHistory();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected ResourceReqMapHistory[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<ResourceReqMapHistory> resultList = new ArrayList<ResourceReqMapHistory>();
		while (rs.next()) {
			ResourceReqMapHistory dto = new ResourceReqMapHistory();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		ResourceReqMapHistory ret[] = new ResourceReqMapHistory[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(ResourceReqMapHistory dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setResMapId(rs.getInt( COLUMN_RES_MAP_ID));
		dto.setResourceName(rs.getString(COLUMN_RESOURCE_NAME));
		dto.setTotalExp(rs.getInt( COLUMN_TOTAL_EXP ) );
		dto.setRelevantExp(rs.getInt( COLUMN_RELEVANT_EXP ) );
	    dto.setCurrentEmployer(rs.getString(COLUMN_CURRENT_EMPLOYER));
		dto.setCurrentRole(rs.getString(COLUMN_CURRENT_ROLE));
		if(rs.getString(COLUMN_CTC)!=null){
		dto.setCtc(Double.parseDouble(rs.getString(COLUMN_CTC)));
		} else dto.setCtc(0);
		if(rs.getString(COLUMN_ECTC)!=null){
		dto.setEctc(Double.parseDouble(rs.getString(COLUMN_ECTC)));
		}else dto.setEctc(0);
		dto.setNoticePeriod(rs.getString(COLUMN_NOTICE_PERIOD)) ;
		dto.setComments(rs.getString(COLUMN_COMMENTS));
        dto.setModifiedBy(rs.getInt(COLUMN_MODIFIED_BY)); 
        dto.setModifiedOn(rs.getDate(COLUMN_MODIFIED_ON));
	}
	

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(ResourceReqMapHistory dto)
	{
	}

	/** 
	 * Returns all rows from the HOLIDAYS table that match the specified arbitrary SQL statement
	 */
	public ResourceReqMapHistory[] findByDynamicSelect(String sql, Object[] sqlParams) throws ResourceReqMapHistoryDaoException
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
			throw new ResourceReqMapHistoryDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the HOLIDAYS table that match the specified arbitrary SQL statement
	 */
	public ResourceReqMapHistory[] findByDynamicWhere(String sql, Object[] sqlParams) throws ResourceReqMapHistoryDaoException
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
			throw new ResourceReqMapHistoryDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}
	
	@Override
	public ResourceReqMapHistory[] findByMapId(int reqId)
			throws ResourceReqMapHistoryDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE RES_MAP_ID = ? ORDER BY ID", new Object[] { new Integer(reqId) });
	}

}
