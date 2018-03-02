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

import com.dikshatech.beans.DropDownBean;
import com.dikshatech.portal.dao.ResourceRequirementDao;
import com.dikshatech.portal.dto.ResourceRequirement;
import com.dikshatech.portal.dto.ResourceRequirementPk;
import com.dikshatech.portal.exceptions.ResourceRequirementDaoException;

public class ResourceRequirementDaoImpl extends AbstractDAO implements ResourceRequirementDao {
	
		/** 
		 * The factory class for this DAO has two versions of the create() method - one that
	takes no arguments and one that takes a Connection argument. If the Connection version
	is chosen then the connection will be stored in this attribute and will be used by all
	calls to this DAO, otherwise a new Connection will be allocated for each operation.
		 */
		protected java.sql.Connection userConn;

		protected static final Logger logger = Logger.getLogger( ResourceRequirementDaoImpl.class );

		/** 
		 * All finder methods in this class use this SELECT constant to build their queries
		 */
		protected final String SQL_SELECT = "SELECT ID,  ESR_MAP_ID, REQ_TITLE, REQ_DETAILS, NO_OF_POSITIONS, PROFITABILITY, CLOSURE,  CLIENT_NAME, CREATE_DATE, MAIN_STATUS_ID"
				+ ",REQUIREMENT_DATE, MANDATORY_SKILLS, ADDITIONAL_SKILLS, YEARS_OF_EXPERIENCE, RELEVANT_EXPERIENCE, LOCATION,POSITION_NAME, "
				+ "REQUIRED_FOR,INTERVIEWER,COMMENTS,PROJECTNAME,EMPLOYMENT_TYPE_ID,ASSIGN_TO,CLOSURE_ID,PROFITABILITY_ID,REQUIRED_FOR_ID FROM " + getTableName() + " ";

		/** 
		 * Finder methods will pass this value to the JDBC setMaxRows method
		 */
		protected int maxRows;

		/** 
		 * SQL INSERT statement for this table
		 */
		
		protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, ESR_MAP_ID, REQ_TITLE, REQ_DETAILS, NO_OF_POSITIONS, PROFITABILITY, CLOSURE,"
				+ "  CLIENT_NAME, CREATE_DATE, MAIN_STATUS_ID,REQUIREMENT_DATE, MANDATORY_SKILLS, ADDITIONAL_SKILLS, YEARS_OF_EXPERIENCE, RELEVANT_EXPERIENCE, LOCATION,POSITION_NAME, "
				+ "REQUIRED_FOR,INTERVIEWER,COMMENTS,PROJECTNAME,EMPLOYMENT_TYPE_ID,ASSIGN_TO,CLOSURE_ID,PROFITABILITY_ID,REQUIRED_FOR_ID ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,? ,?,?,?,?,?,?,?,?,?,?,?,?,? )";

		/** 
		 * SQL UPDATE statement for this table
		 */
		
	//	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, ESR_MAP_ID = ?, REQ_TITLE = ?, REQ_DETAILS = ?, NO_OF_POSITIONS=?, PROFITABILITY=?, CLOSURE=?,  CLIENT_NAME=?, CREATE_DATE= ?, MAIN_STATUS_ID=?,ASSIGN_TO=? WHERE ID = ?";

		protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID=?, ESR_MAP_ID=?, REQ_TITLE=?, REQ_DETAILS=?, NO_OF_POSITIONS=?, PROFITABILITY=?, CLOSURE=?,"
				 +"CLIENT_NAME=?, CREATE_DATE=?, MAIN_STATUS_ID=?,REQUIREMENT_DATE=?, MANDATORY_SKILLS=?, ADDITIONAL_SKILLS=?, YEARS_OF_EXPERIENCE=?, RELEVANT_EXPERIENCE=?, LOCATION=?,POSITION_NAME=?, "
				+ "REQUIRED_FOR=?,INTERVIEWER=?,COMMENTS=?,PROJECTNAME=?,EMPLOYMENT_TYPE_ID=?,ASSIGN_TO =?,CLOSURE_ID=?,PROFITABILITY_ID=?,REQUIRED_FOR_ID=? WHERE ID = ?";
		/** 
		 * SQL DELETE statement for this table
		 */
		protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";
		
		/** 
		 * Get Employment type static data
		 */
		protected final String SQL_SELECT_RESOURCE_EMPLOYMENT_TYPE = "SELECT ID, EMPLOYMENT_TYPE FROM RESOURCE_REQ_EMPLOYMENT_TYPE";
		/** 
		 * Get Employment type static data
		 */
		protected final String SQL_SELECT_RESOURCE_EMPLOYMENT_TYPE_BY_ID = "SELECT ID, EMPLOYMENT_TYPE FROM RESOURCE_REQ_EMPLOYMENT_TYPE WHERE ID=?";
		

		/** 
		 * Index of column ID
		 */
		protected static final int COLUMN_ID = 1;

		/** 
		 * Index of column CAL_ID
		 */
		protected static final int COLUMN_ESR_MAP_ID = 2;

		/** 
		 * Index of column HOLIDAY_NAME
		 */
		protected static final int COLUMN_REQ_TITLE = 3;

		/** 
		 * Index of column DATE_PICKER
		 */
		protected static final int COLUMN_REQ_DETAILS = 4;
        
		/** 
		 * Index of column ID
		 */
		protected static final int COLUMN_NO_OF_POSITIONS = 5;

		/** 
		 * Index of column CAL_ID
		 */
		protected static final int COLUMN_PROFITABILITY = 6;

		/** 
		 * Index of column HOLIDAY_NAME
		 */
		protected static final int COLUMN_CLOSURE = 7;
		/** 
		 * Index of column HOLIDAY_NAME
		 */
	//	protected static final int COLUMN_ASSIGN_TO = 8;

		/** 
		 * Index of column DATE_PICKER
		 */
		protected static final int COLUMN_CLIENT_NAME = 8;
        
		/** 
		 * Index of column DATE_PICKER
		 */
		protected static final int COLUMN_CREATE_DATE = 9;

		/** 
		 * Index of column DATE_PICKER
		 */
		protected static final int COLUMN_MAIN_STATUS_ID = 10;
		
		
		protected static final int REQUIREMENT_DATE =11; 
		
		protected static final int MANDATORY_SKILLS =12;
		
		protected static final int ADDITIONAL_SKILLS =13;
		
		protected static final int YEARS_OF_EXPERIENCE=14;
		
		protected static final int RELEVANT_EXPERIENCE =15;
		
		protected static final int LOCATION =16;
		
		protected static final int POSITION_NAME =17;
		
		protected static final int REQUIRED_FOR =18;
		
		protected static final int INTERVIEWER =19;
		
		protected static final int COMMENTS =20;
		
		protected static final int PROJECTNAME =21;
		
		protected static final int EMPLOYMENT_TYPE_ID =22;
		
		protected static final int ASSIGN_TO =23;

		protected static final int CLOSURE_ID =24;
		
		protected static final int PROFITABILITY_ID =25;
		
		protected static final int REQUIRED_FOR_ID =26;
		/** 
		 * Number of columns
		 */
		protected static final int NUMBER_OF_COLUMNS = 26;

		/** 
		 * Index of primary-key column ID
		 */
		protected static final int PK_COLUMN_ID = 1;
		/** 
		 * Inserts a new row in the HOLIDAYS table.
		 */
		public ResourceRequirementPk insert(ResourceRequirement dto) throws ResourceRequirementDaoException
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
				stmt.setString( index++, dto.getReqTitle() );
				stmt.setString( index++, dto.getReqDetails() );
				stmt.setInt( index++, dto.getNoOfPosition() );
				stmt.setString( index++, dto.getProfitability() );
				stmt.setString( index++, dto.getClosure() );
				//stmt.setInt( index++, dto.getAssignedTo() );
				stmt.setString( index++, dto.getClientName() );
				stmt.setTimestamp(index++, dto.getCreateDate()==null ? new java.sql.Timestamp( new Date().getTime() ) : new java.sql.Timestamp( dto.getCreateDate().getTime() ) );
				stmt.setInt( index++, dto.getMainStatusId() );
				stmt.setTimestamp( index++, new java.sql.Timestamp( dto.getReqDate().getTime() ) );
				stmt.setString( index++, dto.getMandatorySkills() );
				stmt.setString( index++, dto.getAdditionalSkills() );
				stmt.setString( index++, dto.getYearsOfExperience() );
				stmt.setString( index++, dto.getRelevantExperience() );
				stmt.setString( index++, dto.getLocation() );
				stmt.setString( index++, dto.getPositionName() );
				stmt.setString( index++, dto.getRequiredFor() );
				stmt.setString( index++, dto.getInterviewer() );
				stmt.setString( index++, dto.getComments() );
				stmt.setString( index++, dto.getProjectName() );
				stmt.setInt( index++, dto.getEmploymentTypeId() );
				stmt.setInt( index++, dto.getAssignedTo());
				stmt.setInt( index++, dto.getClosureId());
				stmt.setInt( index++, dto.getProfitabilityId());
				stmt.setInt( index++, dto.getRequiredForId());
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
				throw new ResourceRequirementDaoException( "Exception: " + _e.getMessage(), _e );
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
		public void update(ResourceRequirementPk pk, ResourceRequirement dto) throws ResourceRequirementDaoException
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
				stmt.setString( index++, dto.getReqTitle() );
				stmt.setString( index++, dto.getReqDetails() );
				stmt.setInt( index++, dto.getNoOfPosition() );
				stmt.setString( index++, dto.getProfitability() );
				stmt.setString( index++, dto.getClosure() );
				stmt.setString( index++, dto.getClientName() );
				stmt.setTimestamp(index++, dto.getCreateDate()==null ? null : new java.sql.Timestamp( dto.getCreateDate().getTime() ) );
				stmt.setInt( index++, dto.getMainStatusId() );
				stmt.setTimestamp( index++, new java.sql.Timestamp( dto.getReqDate().getTime() ) );
				stmt.setString( index++, dto.getMandatorySkills() );
				stmt.setString( index++, dto.getAdditionalSkills() );
				stmt.setString( index++, dto.getYearsOfExperience() );
				stmt.setString( index++, dto.getRelevantExperience() );
				stmt.setString( index++, dto.getLocation() );
				stmt.setString( index++, dto.getPositionName() );
				stmt.setString( index++, dto.getRequiredFor() );
				stmt.setString( index++, dto.getInterviewer() );
				stmt.setString( index++, dto.getComments() );
				stmt.setString( index++, dto.getProjectName() );
				stmt.setInt( index++, dto.getEmploymentTypeId() );
				stmt.setInt( index++, dto.getAssignedTo());
				stmt.setInt( index++, dto.getClosureId());
				stmt.setInt( index++, dto.getProfitabilityId());
				stmt.setInt( index++, dto.getRequiredForId());
				stmt.setInt( 27, pk.getId() );
				int rows = stmt.executeUpdate();
				reset(dto);
				long t2 = System.currentTimeMillis();
				if (logger.isDebugEnabled()) {
					logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
				}
			
			}
			catch (Exception _e) {
				logger.error( "Exception: " + _e.getMessage(), _e );
				throw new ResourceRequirementDaoException( "Exception: " + _e.getMessage(), _e );
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
		public void delete(ResourceRequirementPk pk) throws ResourceRequirementDaoException
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
				throw new ResourceRequirementDaoException( "Exception: " + _e.getMessage(), _e );
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
		public ResourceRequirement findByPrimaryKey(ResourceRequirementPk pk) throws ResourceRequirementDaoException
		{
			return findByPrimaryKey( pk.getId() );
		}

		/** 
		 * Returns all rows from the HOLIDAYS table that match the criteria 'ID = :id'.
		 */
		public ResourceRequirement findByPrimaryKey(int id) throws ResourceRequirementDaoException
		{
			ResourceRequirement ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] {  new Integer(id) } );
			return ret.length==0 ? null : ret[0];
		}

		/** 
		 * Returns all rows from the HOLIDAYS table that match the criteria ''.
		 */
		public ResourceRequirement[] findAll() throws ResourceRequirementDaoException
		{
			return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", null );
		}

		
		/**
		 * Method 'HolidaysDaoImpl'
		 * 
		 */
		public ResourceRequirementDaoImpl()
		{
		}

		/**
		 * Method 'HolidaysDaoImpl'
		 * 
		 * @param userConn
		 */
		public ResourceRequirementDaoImpl(final java.sql.Connection userConn)
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
			return "RESOURCE_REQUIREMENT";
		}

		/** 
		 * Fetches a single row from the result set
		 */
		protected ResourceRequirement fetchSingleResult(ResultSet rs) throws SQLException
		{
			if (rs.next()) {
				ResourceRequirement dto = new ResourceRequirement();
				populateDto( dto, rs);
				return dto;
			} else {
				return null;
			}
			
		}

		/** 
		 * Fetches multiple rows from the result set
		 */
		protected ResourceRequirement[] fetchMultiResults(ResultSet rs) throws SQLException
		{
			Collection<ResourceRequirement> resultList = new ArrayList<ResourceRequirement>();
			while (rs.next()) {
				ResourceRequirement dto = new ResourceRequirement();
				populateDto( dto, rs);
				resultList.add( dto );
			}
			
			ResourceRequirement ret[] = new ResourceRequirement[ resultList.size() ];
			resultList.toArray( ret );
			return ret;
		}

		/** 
		 * Populates a DTO with data from a ResultSet
		 */
		protected void populateDto(ResourceRequirement dto, ResultSet rs) throws SQLException
		{
			dto.setId( rs.getInt( COLUMN_ID ) );
			dto.setEsrMapId(rs.getInt( COLUMN_ESR_MAP_ID ));
			dto.setReqTitle(rs.getString(COLUMN_REQ_TITLE));
			dto.setReqDetails(rs.getString(COLUMN_REQ_DETAILS));
			dto.setNoOfPosition(rs.getInt(COLUMN_NO_OF_POSITIONS));
			dto.setProfitability(rs.getString(COLUMN_PROFITABILITY));
			dto.setClosure(rs.getString(COLUMN_CLOSURE));
		//	dto.setAssignedTo(rs.getInt(COLUMN_ASSIGN_TO));
			dto.setClientName(rs.getString(COLUMN_CLIENT_NAME));
			dto.setCreateDate( rs.getDate(COLUMN_CREATE_DATE ) );
			dto.setMainStatusId(rs.getInt(COLUMN_MAIN_STATUS_ID));
			dto.setReqDate(rs.getDate(REQUIREMENT_DATE));
			dto.setMandatorySkills(rs.getString(MANDATORY_SKILLS));
			dto.setAdditionalSkills(rs.getString(ADDITIONAL_SKILLS));
			dto.setYearsOfExperience(rs.getString(YEARS_OF_EXPERIENCE));
			dto.setRelevantExperience(rs.getString(RELEVANT_EXPERIENCE));
			dto.setLocation(rs.getString(LOCATION));
			dto.setPositionName(rs.getString(POSITION_NAME));
			dto.setRequiredFor(rs.getString(REQUIRED_FOR));
			dto.setInterviewer(rs.getString(INTERVIEWER));
			dto.setComments(rs.getString(COMMENTS));
			dto.setProjectName(rs.getString(PROJECTNAME));
			dto.setEmploymentTypeId(rs.getInt(EMPLOYMENT_TYPE_ID));
			dto.setAssignedTo(rs.getInt(ASSIGN_TO));
            dto.setClosureId(rs.getInt(CLOSURE_ID));
            dto.setProfitabilityId(rs.getInt(PROFITABILITY_ID));
            dto.setRequiredForId(rs.getInt(REQUIRED_FOR_ID));
		}

	
		/** 
		 * Resets the modified attributes in the DTO
		 */
		protected void reset(ResourceRequirement dto)
		{
		}

		/** 
		 * Returns all rows from the HOLIDAYS table that match the specified arbitrary SQL statement
		 */
		public ResourceRequirement[] findByDynamicSelect(String sql, Object[] sqlParams) throws ResourceRequirementDaoException
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
				throw new ResourceRequirementDaoException( "Exception: " + _e.getMessage(), _e );
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
		public ResourceRequirement[] findByDynamicWhere(String sql, Object[] sqlParams) throws ResourceRequirementDaoException
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
				throw new ResourceRequirementDaoException( "Exception: " + _e.getMessage(), _e );
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
		public DropDownBean[] getEmploymentTypes() throws ResourceRequirementDaoException
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
				final String SQL =SQL_SELECT_RESOURCE_EMPLOYMENT_TYPE;
			
			
				if (logger.isDebugEnabled()) {
					logger.debug( "Executing " + SQL);
				}
			
				// prepare statement
				stmt = conn.prepareStatement( SQL );
				rs = stmt.executeQuery();
			
				// fetch the results
				return populateDropDownDto(rs);
			}
			catch (Exception _e) {
				logger.error( "Exception: " + _e.getMessage(), _e );
				throw new ResourceRequirementDaoException( "Exception: " + _e.getMessage(), _e );
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
		public String getEmploymentTypeByID(int employmentTypeID) throws ResourceRequirementDaoException
		{
			// declare variables
			final boolean isConnSupplied = (userConn != null);
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String employmentType=null;
			try {
				// get the user-specified connection or get a connection from the ResourceManager
				conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			
				// construct the SQL statement
				final String SQL =SQL_SELECT_RESOURCE_EMPLOYMENT_TYPE_BY_ID;
			
			
				if (logger.isDebugEnabled()) {
					logger.debug( "Executing " + SQL);
				}
			
				// prepare statement
				stmt = conn.prepareStatement( SQL );
				stmt.setObject( 1, employmentTypeID);
				rs = stmt.executeQuery();
				if(rs.next()){
					employmentType= rs.getString(2);
				}
				// fetch the results
				return employmentType;
			}
			catch (Exception _e) {
				logger.error( "Exception: " + _e.getMessage(), _e );
				throw new ResourceRequirementDaoException( "Exception: " + _e.getMessage(), _e );
			}
			finally {
				ResourceManager.close(rs);
				ResourceManager.close(stmt);
				if (!isConnSupplied) {
					ResourceManager.close(conn);
				}
			
			}
			
		}
		
		protected DropDownBean[] populateDropDownDto(ResultSet rs) throws SQLException
		{
			Collection<DropDownBean> resultList = new ArrayList<DropDownBean>();
			while (rs.next()) {
				DropDownBean dropDownBean = new DropDownBean();
				dropDownBean.setKey(rs.getInt( COLUMN_ID));;
				dropDownBean.setValue(rs.getString(2));
				resultList.add(dropDownBean);
			}
			DropDownBean[] downBeans=new DropDownBean[resultList.size()];
			resultList.toArray(downBeans);
			return downBeans;
		}

	}
