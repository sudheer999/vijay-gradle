package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.apache.log4j.Logger;
import com.dikshatech.portal.dao.JobRequirementDao;
import com.dikshatech.portal.dto.FixedPerdiem;
import com.dikshatech.portal.dto.JobRequirement;
import com.dikshatech.portal.dto.JobRequirementPk;
import com.dikshatech.portal.exceptions.JobRequirementDaoException;

public class JobRequirementDaoImpl implements JobRequirementDao {

	/**
	 * The factory class for this DAO has two versions of the create() method - one that
	 * takes no arguments and one that takes a Connection argument. If the Connection version
	 * is chosen then the connection will be stored in this attribute and will be used by all
	 * calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;
	protected static final Logger	logger					= Logger.getLogger(FixedPerdiemDaoImpl.class);
	/**
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String			SQL_SELECT				= "SELECT ID, JOB_TITLE, JOB_DESCRIPTION, POSTED_BY, POSTED_ON ,IS_ACTIVE, REQ_TAG_ID, EXPERIENCE, LOCATION, POSITION, SKILLS FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT				= "INSERT INTO " + getTableName() + " ( ID, JOB_TITLE, JOB_DESCRIPTION, POSTED_BY, POSTED_ON ,IS_ACTIVE, REQ_TAG_ID, EXPERIENCE, LOCATION, POSITION, SKILLS ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String			SQL_UPDATE				= "UPDATE " + getTableName() + " SET ID = ?, JOB_TITLE = ?, JOB_DESCRIPTION = ?, POSTED_BY = ?, POSTED_ON = ?, IS_ACTIVE=?, REQ_TAG_ID=?, EXPERIENCE=?, LOCATION=?, POSITION=? , SKILLS =? WHERE ID = ?";
	/**
	 * SQL DELETE statement for this table
	 */
	protected final String			SQL_DELETE				= "DELETE FROM " + getTableName() + " WHERE ID = ?";
	/**
	 * Index of column ID
	 */
	protected static final int		COLUMN_ID				= 1;
	/**
	 * Index of column PERDIEM
	 */
	protected static final int		COLUMN_JOB_TITLE		= 2;
	/**
	 * Index of column PERDIEM_FROM
	 */
	protected static final int		COLUMN_JOB_DESCRIPTION	= 3;
	/**
	 * Index of column PERDIEM_TO
	 */
	protected static final int		COLUMN_POSTED_BY		= 4;
	/**
	 * Index of column CURRENCY_TYPE
	 */
	protected static final int		COLUMN_POSTED_ON	= 5;
	/**
	 * Index of column CURRENCY_TYPE
	 */
	protected static final int		COLUMN_IS_ACTIVE	= 6;
	/**
	 * Index of column CURRENCY_TYPE   
	 */
	protected static final int		COLUMN_REQ_TAG_ID	= 7;
	/**
	 * Index of column CURRENCY_TYPE
	 */
	protected static final int		COLUMN_EXPERIENCE	= 8;
	/**
	 * Index of column CURRENCY_TYPE
	 */
	protected static final int		COLUMN_LOCATION	= 9;
	/**
	 * Index of column CURRENCY_TYPE
	 */
	protected static final int		COLUMN_POSITION	= 10;
	
	/**
	 * Index of column CURRENCY_TYPE
	 */
	protected static final int		COLUMN_SKILLS	= 11;
	/**
	 * Number of columns
	 */
	protected static final int		NUMBER_OF_COLUMNS		= 11;
	/**
	 * Index of primary-key column ID
	 */
	protected static final int		PK_COLUMN_ID			= 1;

	@Override
	public JobRequirementPk insert(JobRequirement dto) throws JobRequirementDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT);
			int index = 1;
			if (dto.getId() != 0){
				stmt.setInt(index++, dto.getId());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			stmt.setString(index++,dto.getJobTitle());
			stmt.setString(index++,dto.getJobDescription());
			if (dto.getPostedBy() != 0){
				stmt.setInt(index++, dto.getPostedBy());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			stmt.setTimestamp(index++, new java.sql.Timestamp(new Date().getTime()));
			stmt.setInt(index++, 1);
			stmt.setString(index++,dto.getReqTagId());
			stmt.setString(index++,dto.getExperience());
			stmt.setString(index++,dto.getLocation());
			stmt.setString(index++,dto.getPosition());
			stmt.setString(index++,dto.getSkills());
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_INSERT + " with DTO: " + dto);
			}
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
			reset(dto);
			return dto.createPk();
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new JobRequirementDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
	

	@Override
	public void update(JobRequirementPk pk, JobRequirement dto) throws JobRequirementDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_UPDATE + " with DTO: " + dto);
			}
			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			if (dto.getId() != 0){
				stmt.setInt(index++, dto.getId());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			stmt.setString(index++,dto.getJobTitle());
			stmt.setString(index++,dto.getJobDescription());
			if (dto.getPostedBy() != 0){
				stmt.setInt(index++, dto.getPostedBy());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			stmt.setTimestamp(index++, new java.sql.Timestamp(new Date().getTime()));
			stmt.setInt(index++, 1);
			stmt.setString(index++,dto.getReqTagId());
			stmt.setString(index++,dto.getExperience());
			stmt.setString(index++,dto.getLocation());
			stmt.setString(index++,dto.getPosition());
			stmt.setString(index++,dto.getSkills());
			if (pk.getId() != 0){
				stmt.setInt(12, pk.getId());
			} else{
				stmt.setNull(12, java.sql.Types.INTEGER);
			}
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new JobRequirementDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	@Override
	public void delete(JobRequirementPk pk) throws JobRequirementDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_DELETE + " with PK: " + pk);
			}
			stmt = conn.prepareStatement(SQL_DELETE);
			if (pk.getId() != 0){
				stmt.setInt(1, pk.getId());
			} else{
				stmt.setNull(1, java.sql.Types.INTEGER);
			}
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new JobRequirementDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	@Override
	public JobRequirement findByPrimaryKey(JobRequirementPk pk) throws JobRequirementDaoException {
		return findByPrimaryKey(pk.getId());
	}

	@Override
	public JobRequirement findByPrimaryKey(Integer id) throws JobRequirementDaoException {
		JobRequirement ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { id });
		return ret.length == 0 ? null : ret[0];
	}
	
	/**
	 * Method 'FixedPerdiemDaoImpl'
	 */
	public JobRequirementDaoImpl() {}

	/**
	 * Method 'FixedPerdiemDaoImpl'
	 * 
	 * @param userConn
	 */
	public JobRequirementDaoImpl(final java.sql.Connection userConn) {
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
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(JobRequirement dto) {}


	
	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "JOB_REQUIREMENT";
	}
	
	
	/**
	 * Fetches a single row from the result set
	 */
	protected JobRequirement fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			JobRequirement dto = new JobRequirement();
			populateDto(dto, rs);
			return dto;
		} else{
			return null;
		}
	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected JobRequirement[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<JobRequirement> resultList = new ArrayList<JobRequirement>();
		while (rs.next()){
			JobRequirement dto = new JobRequirement();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		JobRequirement ret[] = new JobRequirement[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(JobRequirement dto, ResultSet rs) throws SQLException {
		dto.setId(new Integer(rs.getInt(COLUMN_ID)));
		dto.setJobTitle(rs.getString(COLUMN_JOB_TITLE));
		dto.setJobDescription(rs.getString(COLUMN_JOB_DESCRIPTION));
		dto.setPostedBy(new Integer(rs.getInt(COLUMN_POSTED_BY)));
		dto.setPostedOn(rs.getTimestamp(COLUMN_POSTED_ON));
		dto.setIsActive(rs.getInt(COLUMN_IS_ACTIVE));
		dto.setReqTagId(rs.getString(COLUMN_REQ_TAG_ID));
		dto.setExperience(rs.getString(COLUMN_EXPERIENCE));
		dto.setLocation(rs.getString(COLUMN_LOCATION));
		dto.setPosition(rs.getString(COLUMN_POSITION));
		dto.setSkills(rs.getString(COLUMN_SKILLS));
	}
	
	
	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(FixedPerdiem dto) {}

	/**
	 * Returns all rows from the FIXED_PERDIEM table that match the specified arbitrary SQL statement
	 */
	public JobRequirement[] findByDynamicSelect(String sql, Object[] sqlParams) throws JobRequirementDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			// construct the SQL statement
			final String SQL = sql;
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL);
			}
			// prepare statement
			stmt = conn.prepareStatement(SQL);
			stmt.setMaxRows(maxRows);
			// bind parameters
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++){
				stmt.setObject(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();
			// fetch the results
			return fetchMultiResults(rs);
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new JobRequirementDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	public JobRequirement[] findByDynamicWhere(String sql, Object[] sqlParams) throws JobRequirementDaoException {
	// declare variables
			final boolean isConnSupplied = (userConn != null);
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try{
				// get the user-specified connection or get a connection from the ResourceManager
				conn = isConnSupplied ? userConn : ResourceManager.getConnection();
				// construct the SQL statement
				final String SQL = SQL_SELECT + " WHERE " + sql;
				if (logger.isDebugEnabled()){
					logger.debug("Executing " + SQL);
				}
				// prepare statement
				stmt = conn.prepareStatement(SQL);
				stmt.setMaxRows(maxRows);
				// bind parameters
				for (int i = 0; sqlParams != null && i < sqlParams.length; i++){
					stmt.setObject(i + 1, sqlParams[i]);
				}
				rs = stmt.executeQuery();
				// fetch the results
				return fetchMultiResults(rs);
			} catch (Exception _e){
				logger.error("Exception: " + _e.getMessage(), _e);
				throw new JobRequirementDaoException("Exception: " + _e.getMessage(), _e);
			} finally{
				ResourceManager.close(rs);
				ResourceManager.close(stmt);
				if (!isConnSupplied){
					ResourceManager.close(conn);
				}
			}
		}

}
