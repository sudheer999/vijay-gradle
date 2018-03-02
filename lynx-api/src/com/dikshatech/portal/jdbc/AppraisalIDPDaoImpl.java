/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
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

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.AppraisalIDPDao;
import com.dikshatech.portal.dto.AppraisalIDP;
import com.dikshatech.portal.dto.AppraisalIDPPk;
import com.dikshatech.portal.exceptions.AppraisalIDPDaoException;

public class AppraisalIDPDaoImpl extends AbstractDAO implements AppraisalIDPDao {

	/**
	 * The factory class for this DAO has two versions of the create() method - one that
	 * takes no arguments and one that takes a Connection argument. If the Connection version
	 * is chosen then the connection will be stored in this attribute and will be used by all
	 * calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;
	protected static final Logger	logger						= Logger.getLogger(AppraisalIDPDaoImpl.class);
	/**
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String			SQL_SELECT					= "SELECT ID, APPRAISAL_ID, CAREER_PLAN, OBJECTIVES, SMART_GOALS, FINAL_OBJECTIVES, COMMENTS, PROJECT_ACHIEVEMENTS FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT					= "INSERT INTO " + getTableName() + " ( ID, APPRAISAL_ID, CAREER_PLAN, OBJECTIVES, SMART_GOALS, FINAL_OBJECTIVES, COMMENTS, PROJECT_ACHIEVEMENTS ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";
	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String			SQL_UPDATE					= "UPDATE " + getTableName() + " SET ID = ?, APPRAISAL_ID = ?, CAREER_PLAN = ?, OBJECTIVES = ?, SMART_GOALS = ?, FINAL_OBJECTIVES = ?, COMMENTS = ?, PROJECT_ACHIEVEMENTS = ? WHERE ID = ?";
	/**
	 * SQL DELETE statement for this table
	 */
	protected final String			SQL_DELETE					= "DELETE FROM " + getTableName() + " WHERE ID = ?";
	/**
	 * Index of column ID
	 */
	protected static final int		COLUMN_ID					= 1;
	/**
	 * Index of column APPRAISAL_ID
	 */
	protected static final int		COLUMN_APPRAISAL_ID			= 2;
	/**
	 * Index of column CAREER_PLAN
	 */
	protected static final int		COLUMN_CAREER_PLAN			= 3;
	/**
	 * Index of column OBJECTIVES
	 */
	protected static final int		COLUMN_OBJECTIVES			= 4;
	/**
	 * Index of column SMART_GOALS
	 */
	protected static final int		COLUMN_SMART_GOALS			= 5;
	/**
	 * Index of column FINAL_OBJECTIVES
	 */
	protected static final int		COLUMN_FINAL_OBJECTIVES		= 6;
	/**
	 * Index of column COMMENTS
	 */
	protected static final int		COLUMN_COMMENTS				= 7;
	/**
	 * Index of column PROJECT_ACHIEVEMENTS
	 */
	protected static final int		COLUMN_PROJECT_ACHIEVEMENTS	= 8;
	/**
	 * Number of columns
	 */
	protected static final int		NUMBER_OF_COLUMNS			= 8;
	/**
	 * Index of primary-key column ID
	 */
	protected static final int		PK_COLUMN_ID				= 1;

	/**
	 * Inserts a new row in the APPRAISAL_IDP table.
	 */
	public AppraisalIDPPk insert(AppraisalIDP dto) throws AppraisalIDPDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			stmt.setInt(index++, dto.getId());
			stmt.setInt(index++, dto.getAppraisalId());
			stmt.setString(index++, dto.getCareerPlan());
			stmt.setString(index++, dto.getObjectives());
			stmt.setString(index++, dto.getSmartGoals());
			stmt.setString(index++, dto.getFinalObjectives());
			stmt.setString(index++, dto.getComments());
			stmt.setString(index++, dto.getProjectAchievements());
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_INSERT + " with DTO: " + dto);
			}
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
			// retrieve values from auto-increment columns
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()){
				dto.setId(rs.getInt(1));
			}
			reset(dto);
			return dto.createPk();
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new AppraisalIDPDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Updates a single row in the APPRAISAL_IDP table.
	 */
	public void update(AppraisalIDPPk pk, AppraisalIDP dto) throws AppraisalIDPDaoException {
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
			stmt.setInt(index++, dto.getId());
			stmt.setInt(index++, dto.getAppraisalId());
			stmt.setString(index++, dto.getCareerPlan());
			stmt.setString(index++, dto.getObjectives());
			stmt.setString(index++, dto.getSmartGoals());
			stmt.setString(index++, dto.getFinalObjectives());
			stmt.setString(index++, dto.getComments());
			stmt.setString(index++, dto.getProjectAchievements());
			stmt.setInt(9, pk.getId());
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new AppraisalIDPDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Deletes a single row in the APPRAISAL_IDP table.
	 */
	public void delete(AppraisalIDPPk pk) throws AppraisalIDPDaoException {
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
			stmt.setInt(1, pk.getId());
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new AppraisalIDPDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Returns the rows from the APPRAISAL_IDP table that matches the specified primary-key value.
	 */
	public AppraisalIDP findByPrimaryKey(AppraisalIDPPk pk) throws AppraisalIDPDaoException {
		return findByPrimaryKey(pk.getId());
	}

	/**
	 * Returns all rows from the APPRAISAL_IDP table that match the criteria 'ID = :id'.
	 */
	public AppraisalIDP findByPrimaryKey(int id) throws AppraisalIDPDaoException {
		AppraisalIDP ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { new Integer(id) });
		return ret.length == 0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the APPRAISAL_IDP table that match the criteria ''.
	 */
	public AppraisalIDP[] findAll() throws AppraisalIDPDaoException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY ID", null);
	}

	/**
	 * Returns all rows from the APPRAISAL_IDP table that match the criteria 'APPRAISAL_ID = :appraisalId'.
	 */
	public AppraisalIDP findWhereAppraisalIdEquals(Integer appraisalId) throws AppraisalIDPDaoException {
		AppraisalIDP ret[] = findByDynamicSelect(SQL_SELECT + " WHERE APPRAISAL_ID = ?", new Object[] { appraisalId });
		return ret.length == 0 ? null : ret[0];
	}

	/**
	 * Method 'AppraisalIDPDaoImpl'
	 */
	public AppraisalIDPDaoImpl() {}

	/**
	 * Method 'AppraisalIDPDaoImpl'
	 * 
	 * @param userConn
	 */
	public AppraisalIDPDaoImpl(final java.sql.Connection userConn) {
		this.userConn = userConn;
	}

	/**
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	/**
	 * Gets the value of maxRows
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "APPRAISAL_IDP";
	}

	/**
	 * Fetches a single row from the result set
	 */
	protected AppraisalIDP fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			AppraisalIDP dto = new AppraisalIDP();
			populateDto(dto, rs);
			return dto;
		} else{
			return null;
		}
	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected AppraisalIDP[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<AppraisalIDP> resultList = new ArrayList<AppraisalIDP>();
		while (rs.next()){
			AppraisalIDP dto = new AppraisalIDP();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		AppraisalIDP ret[] = new AppraisalIDP[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(AppraisalIDP dto, ResultSet rs) throws SQLException {
		dto.setId(rs.getInt(COLUMN_ID));
		dto.setAppraisalId(rs.getInt(COLUMN_APPRAISAL_ID));
		dto.setCareerPlan(rs.getString(COLUMN_CAREER_PLAN));
		dto.setObjectives(rs.getString(COLUMN_OBJECTIVES));
		dto.setSmartGoals(rs.getString(COLUMN_SMART_GOALS));
		dto.setFinalObjectives(rs.getString(COLUMN_FINAL_OBJECTIVES));
		dto.setComments(rs.getString(COLUMN_COMMENTS));
		dto.setProjectAchievements(rs.getString(COLUMN_PROJECT_ACHIEVEMENTS));
	}

	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(AppraisalIDP dto) {}

	/**
	 * Returns all rows from the APPRAISAL_IDP table that match the specified arbitrary SQL statement
	 */
	public AppraisalIDP[] findByDynamicSelect(String sql, Object[] sqlParams) throws AppraisalIDPDaoException {
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
			throw new AppraisalIDPDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Returns all rows from the APPRAISAL_IDP table that match the specified arbitrary SQL statement
	 */
	public AppraisalIDP[] findByDynamicWhere(String sql, Object[] sqlParams) throws AppraisalIDPDaoException {
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
			throw new AppraisalIDPDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
}
