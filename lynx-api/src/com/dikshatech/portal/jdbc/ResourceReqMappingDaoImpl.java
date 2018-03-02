package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;


import com.dikshatech.portal.dao.ResourceReqMappingDao;
import com.dikshatech.portal.dto.ResourceReqMapping;
import com.dikshatech.portal.dto.ResourceReqMappingPk;
import com.dikshatech.portal.exceptions.ResourceReqMappingDaoException;;

public class ResourceReqMappingDaoImpl implements ResourceReqMappingDao {


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
	protected final String SQL_SELECT = "SELECT ID,RESOURCE_NAME, EMAIL_ID, CONTACT_NO, LAST_MODIFIED_BY ,TOTAL_EXP, RELEVANT_EXP, CURRENT_COMP_EXP,CURRENT_EMPLOYER, CURRENT_ROLE,SKILL_SET,LEAVING_REASON,CTC,ECTC,NOTICE_PERIOD,OFFER_IN_HAND,OFFER_FOR_EARLY_JOINING,CONDITION_FOR_EARLY_JOINING,CURRENT_LOCATION,LOCATION_CONSTRAINT,COMMENTS, IS_SELECTED ,ATTACHMENT_ID FROM " + getTableName() + "";
	/** 
	 * Get Employment type static data
	 */
	protected final String SQL_SELECT_RESOURCE_EMPLOYMENT_TYPE = "SELECT ID, EMPLOYMENT_TYPE FROM RESOURCE_REQ_EMPLOYMENT_TYPE";
	/** 
	 * Get Employment type static data
	 */
	protected final String SQL_SELECT_RESOURCE_EMPLOYMENT_TYPE_BY_ID = "SELECT ID, EMPLOYMENT_TYPE FROM RESOURCE_REQ_EMPLOYMENT_TYPE WHERE ID=?";
	
	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " (ID, RESOURCE_NAME, EMAIL_ID, CONTACT_NO, LAST_MODIFIED_BY ,TOTAL_EXP, RELEVANT_EXP, CURRENT_COMP_EXP,CURRENT_EMPLOYER, CURRENT_ROLE,SKILL_SET,LEAVING_REASON,CTC,ECTC,NOTICE_PERIOD,OFFER_IN_HAND,OFFER_FOR_EARLY_JOINING,CONDITION_FOR_EARLY_JOINING,CURRENT_LOCATION,LOCATION_CONSTRAINT,COMMENTS,IS_SELECTED,ATTACHMENT_ID) VALUES ( ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?,? ,?)";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?,  RESOURCE_NAME = ?,  EMAIL_ID=?, CONTACT_NO=?, LAST_MODIFIED_BY=?, TOTAL_EXP=?, RELEVANT_EXP=?, CURRENT_COMP_EXP=?,CURRENT_EMPLOYER=?, CURRENT_ROLE=?, SKILL_SET=?,LEAVING_REASON=?, CTC=?, ECTC=?, NOTICE_PERIOD=?, OFFER_IN_HAND=?, OFFER_FOR_EARLY_JOINING=?, CONDITION_FOR_EARLY_JOINING=?, CURRENT_LOCATION=?, LOCATION_CONSTRAINT=?, COMMENTS=?,IS_SELECTED=?,ATTACHMENT_ID=? WHERE ID = ?";
	
	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ID = 1;


	/** 
	 * Index of column DATE_PICKER
	 */
	protected static final int COLUMN_RESOURCE_NAME = 2;
    
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_EMAIL_ID = 3;
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_CONTACT_NO = 4;
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_LAST_MODIFIED_BY = 5;
	
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_TOTAL_EXP = 6;

	/** 
	 * Index of column CAL_ID
	 */
	protected static final int COLUMN_RELEVANT_EXP = 7;

	/** 
	 * Index of column DATE_PICKER
	 */
	protected static final int COLUMN_CURRENT_COMP_EXP = 8;
    
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_CURRENT_EMPLOYER = 9;
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_CURRENT_ROLE = 10;
	
	

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_SKILL_SET = 11;
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_LEAVING_REASON = 12;
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_CTC = 13;

	/** 
	 * Index of column CAL_ID
	 */
	protected static final int COLUMN_ECTC = 14;

	/** 
	 * Index of column DATE_PICKER
	 */
	protected static final int COLUMN_NOTICE_PERIOD= 15;
    
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_OFFER_IN_HAND = 16;
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_OPTION_FOR_EARLY_JOINING = 17;
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_CONDITION_FOR_EARLY_JOINING = 18;
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_CURRENT_LOCATION = 19;

	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_LOCATION_CONSTRAINT = 20;
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_COMMENTS = 21;
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_IS_SELECTED = 22;
	
	/** 
	 * Index of column ID
	 */
	protected static final int COLUMN_ATTACHMENT_ID = 23;
	
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
	public ResourceReqMappingPk insert(ResourceReqMapping dto) throws ResourceReqMappingDaoException
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
			stmt.setString( index++, dto.getResourceName() );
			stmt.setString( index++, dto.getEmailId() );
			stmt.setString( index++, dto.getContactNo() );
			stmt.setInt( index++, dto.getLastUpdatedBy() );
			stmt.setInt( index++, dto.getTotalExp() );
			stmt.setInt( index++, dto.getRelevantExp() );
			stmt.setInt( index++, dto.getCurrentCompExp() );
			stmt.setString( index++, dto.getCurrentEmployer() );
			stmt.setString( index++, dto.getCurrentRole() );
			stmt.setString( index++, dto.getSkillSet() );
			stmt.setString( index++, dto.getLeavingReason() );
			stmt.setString( index++, dto.getCtc()+"");
			stmt.setString( index++, dto.getEctc()+"" );
			
			stmt.setString( index++, dto.getNoticePeriod() );
			stmt.setString( index++, dto.getOfferInHand() );
			stmt.setString( index++, dto.getOptionForEarlyJoining() );
			stmt.setString( index++, dto.getConditionForEarlyJoining() );
			stmt.setString( index++, dto.getCurrentLocation() );
			stmt.setString( index++, dto.getLocConstraint() );
			stmt.setString( index++, dto.getComments());
			stmt.setInt( index++, dto.getIsSelected() );
			stmt.setInt( index++, dto.getAttachmentId() );
			
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
			throw new ResourceReqMappingDaoException( "Exception: " + _e.getMessage(), _e );
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
	public void update(ResourceReqMappingPk pk, ResourceReqMapping dto) throws ResourceReqMappingDaoException
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
			stmt.setString( index++, dto.getResourceName() );
			stmt.setString( index++, dto.getEmailId() );
			stmt.setString( index++, dto.getContactNo() );
			stmt.setInt( index++, dto.getLastUpdatedBy() );
			stmt.setInt( index++, dto.getTotalExp() );
			stmt.setInt( index++, dto.getRelevantExp() );
			stmt.setInt( index++, dto.getCurrentCompExp() );
			stmt.setString( index++, dto.getCurrentEmployer() );
			stmt.setString( index++, dto.getCurrentRole() );
			stmt.setString( index++, dto.getSkillSet() );
			stmt.setString( index++, dto.getLeavingReason() );
			stmt.setString( index++, dto.getCtc()+"");
			stmt.setString( index++, dto.getEctc()+"" );
			
			stmt.setString( index++, dto.getNoticePeriod() );
			stmt.setString( index++, dto.getOfferInHand() );
			stmt.setString( index++, dto.getOptionForEarlyJoining() );
			stmt.setString( index++, dto.getConditionForEarlyJoining() );
			stmt.setString( index++, dto.getCurrentLocation() );
			stmt.setString( index++, dto.getLocConstraint() );
			stmt.setString( index++, dto.getComments());
			stmt.setInt( index++, dto.getIsSelected() );
			stmt.setInt( index++, dto.getAttachmentId() );
			stmt.setInt( 24, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new ResourceReqMappingDaoException( "Exception: " + _e.getMessage(), _e );
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
	public void delete(ResourceReqMappingPk pk) throws ResourceReqMappingDaoException
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
			throw new ResourceReqMappingDaoException( "Exception: " + _e.getMessage(), _e );
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
	public ResourceReqMapping findByPrimaryKey(ResourceReqMappingPk pk) throws ResourceReqMappingDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria 'ID = :id'.
	 */
	public ResourceReqMapping findByPrimaryKey(int id) throws ResourceReqMappingDaoException
	{
		ResourceReqMapping ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria ''.
	 */
	public ResourceReqMapping[] findAll() throws ResourceReqMappingDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
	}

	
	/**
	 * Method 'HolidaysDaoImpl'
	 * 
	 */
	public ResourceReqMappingDaoImpl()
	{
	}

	/**
	 * Method 'HolidaysDaoImpl'
	 * 
	 * @param userConn
	 */
	public ResourceReqMappingDaoImpl(final java.sql.Connection userConn)
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
		return "RESOURCES";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected ResourceReqMapping fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			ResourceReqMapping dto = new ResourceReqMapping();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected ResourceReqMapping[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection<ResourceReqMapping> resultList = new ArrayList<ResourceReqMapping>();
		while (rs.next()) {
			ResourceReqMapping dto = new ResourceReqMapping();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		ResourceReqMapping ret[] = new ResourceReqMapping[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(ResourceReqMapping dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setResourceName(rs.getString(COLUMN_RESOURCE_NAME));
		dto.setEmailId(rs.getString(COLUMN_EMAIL_ID));
		dto.setContactNo(rs.getString(COLUMN_CONTACT_NO));
		dto.setLastUpdatedBy(rs.getInt(COLUMN_LAST_MODIFIED_BY));
		dto.setTotalExp(rs.getInt( COLUMN_TOTAL_EXP ) );
		dto.setRelevantExp(rs.getInt( COLUMN_RELEVANT_EXP ) );
		dto.setCurrentCompExp(rs.getInt( COLUMN_CURRENT_COMP_EXP ) );
	    dto.setCurrentEmployer(rs.getString(COLUMN_CURRENT_EMPLOYER));
		dto.setCurrentRole(rs.getString(COLUMN_CURRENT_ROLE));
		dto.setSkillSet(rs.getString(COLUMN_SKILL_SET));
		dto.setLeavingReason(rs.getString(COLUMN_LEAVING_REASON));
		if(rs.getString(COLUMN_CTC)!=null){
		dto.setCtc(Double.parseDouble(rs.getString(COLUMN_CTC)));
		} else dto.setCtc(0);
		if(rs.getString(COLUMN_ECTC)!=null){
		dto.setEctc(Double.parseDouble(rs.getString(COLUMN_ECTC)));
		}else dto.setEctc(0);
		dto.setNoticePeriod(rs.getString(COLUMN_NOTICE_PERIOD)) ;
		dto.setOfferInHand(rs.getString(COLUMN_OFFER_IN_HAND)) ;
		dto.setOptionForEarlyJoining(rs.getString(COLUMN_OPTION_FOR_EARLY_JOINING)) ;
		dto.setConditionForEarlyJoining(rs.getString(COLUMN_CONDITION_FOR_EARLY_JOINING)) ;
		dto.setCurrentLocation(rs.getString(COLUMN_CURRENT_LOCATION)) ;
		dto.setLocConstraint(rs.getString(COLUMN_LOCATION_CONSTRAINT)) ;
		dto.setComments(rs.getString(COLUMN_COMMENTS));
		dto.setIsSelected( rs.getInt( COLUMN_IS_SELECTED ) );
		dto.setAttachmentId( rs.getInt( COLUMN_ATTACHMENT_ID) );
	}
	
	
	
	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(ResourceReqMapping dto)
	{
	}
	
	/** 
	 * Populates a DTO with data from a ResultSet
	 *//*
	

	

	/** 
	 * Returns all rows from the HOLIDAYS table that match the specified arbitrary SQL statement
	 */
	public ResourceReqMapping[] findByDynamicSelect(String sql, Object[] sqlParams) throws ResourceReqMappingDaoException
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
			throw new ResourceReqMappingDaoException( "Exception: " + _e.getMessage(), _e );
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
	public ResourceReqMapping[] findByDynamicWhere(String sql, Object[] sqlParams) throws ResourceReqMappingDaoException
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
			throw new ResourceReqMappingDaoException( "Exception: " + _e.getMessage(), _e );
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
	public ResourceReqMapping[] findByReqId(int reqId)
			throws ResourceReqMappingDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE REQ_ID = ? ORDER BY ID", new Object[] { new Integer(reqId) });
	}

}
