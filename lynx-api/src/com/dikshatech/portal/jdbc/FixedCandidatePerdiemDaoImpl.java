package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;

import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.portal.dao.FixedCandidatePerdiemDao;
import com.dikshatech.portal.dto.FixedCandidatePerdiem;
import com.dikshatech.portal.dto.FixedCandidatePerdiemPk;
import com.dikshatech.portal.exceptions.FixedCandidatePerdiemDaoException;




public class FixedCandidatePerdiemDaoImpl extends AbstractDAO implements FixedCandidatePerdiemDao {
	/**
	 * The factory class for this DAO has two versions of the create() method - one that
	 * takes no arguments and one that takes a Connection argument. If the Connection version
	 * is chosen then the connection will be stored in this attribute and will be used by all
	 * calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;
	protected static final Logger	logger					= Logger.getLogger(FixedCandidatePerdiemDaoImpl.class);
	/**
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String			SQL_SELECT				= "SELECT ID, FIXED_PERDIEM, CURRENCY_TYPE  FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT				= "INSERT INTO " + getTableName() + " ( ID, FIXED_PERDIEM, CURRENCY_TYPE ) VALUES (  ?, ?, ? )";
	/**
	 * SQL UPDATE statement for this table
	 */
	
	protected final String    SQL_UPDATE= "UPDATE " + getTableName() + " SET ID = ?, FIXED_PERDIEM = ?, CURRENCY_TYPE = ?  WHERE ID = ?";
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
	protected static final int		COLUMN_FIXED_PERDIEM			= 2;
	/**
	 * Index of column CURRENCY_TYPE
	 */
	protected static final int		COLUMN_CURRENCY_TYPE	= 3;
	
	/**
	 * Number of columns
	 */
	protected static final int		NUMBER_OF_COLUMNS		= 3;
	/**
	 * Index of primary-key column ID
	 */
	protected static final int		PK_COLUMN_ID			= 1;

	/**
	 * Inserts a new row in the FIXED_PERDIEM table.
	 */
	
	
	public FixedCandidatePerdiemPk insert(FixedCandidatePerdiem dto) throws FixedCandidatePerdiemDaoException {
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
			if (dto.getId() != null){
				stmt.setInt(index++, dto.getId().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getPerdiem()));
			stmt.setString(index++, dto.getCurrencyType());
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
			throw new FixedCandidatePerdiemDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Updates a single row in the FIXED_PERDIEM table.
	 */
	public void update(FixedCandidatePerdiemPk pk, FixedCandidatePerdiem dto) throws FixedCandidatePerdiemDaoException {
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
			if (dto.getId() != null){
				stmt.setInt(index++, dto.getId().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getPerdiem()));
			stmt.setString(index++, dto.getCurrencyType());
			if (pk.getId() != null){
				stmt.setInt(4, pk.getId().intValue());
			} else{
				stmt.setNull(4, java.sql.Types.INTEGER);
			}
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new FixedCandidatePerdiemDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Deletes a single row in the FIXED_PERDIEM table.
	 */
	public void delete(FixedCandidatePerdiemPk pk) throws FixedCandidatePerdiemDaoException {
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
			if (pk.getId() != null){
				stmt.setInt(1, pk.getId().intValue());
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
			throw new FixedCandidatePerdiemDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Returns the rows from the FIXED_PERDIEM table that matches the specified primary-key value.
	 */
	public FixedCandidatePerdiem findByPrimaryKey(FixedCandidatePerdiemPk pk) throws FixedCandidatePerdiemDaoException {
		return findByPrimaryKey(pk.getId());
	}

	/**
	 * Returns all rows from the FIXED_PERDIEM table that match the criteria 'ID = :id'.
	 */
	public FixedCandidatePerdiem findByPrimaryKey(Integer id) throws FixedCandidatePerdiemDaoException {
		FixedCandidatePerdiem ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { id });
		return ret.length == 0 ? null : ret[0];
	}

	/**
	 * Method 'FixedPerdiemDaoImpl'
	 */
	public FixedCandidatePerdiemDaoImpl() {}

	/**
	 * Method 'FixedPerdiemDaoImpl'
	 * 
	 * @param userConn
	 */
	public FixedCandidatePerdiemDaoImpl(final java.sql.Connection userConn) {
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
		return "FIXED_PERDIEM_CANDIDATE";
	}

	/**
	 * Fetches a single row from the result set
	 */
	protected FixedCandidatePerdiem fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			FixedCandidatePerdiem dto = new FixedCandidatePerdiem();
			populateDto(dto, rs);
			return dto;
		} else{
			return null;
		}
	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected FixedCandidatePerdiem[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<FixedCandidatePerdiem> resultList = new ArrayList<FixedCandidatePerdiem>();
		while (rs.next()){
			FixedCandidatePerdiem dto = new FixedCandidatePerdiem();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		FixedCandidatePerdiem ret[] = new FixedCandidatePerdiem[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(FixedCandidatePerdiem dto, ResultSet rs) throws SQLException {
		dto.setId(new Integer(rs.getInt(COLUMN_ID)));
		dto.setPerdiem(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(COLUMN_FIXED_PERDIEM)));
		dto.setCurrencyType(rs.getString(COLUMN_CURRENCY_TYPE));
		if (rs.wasNull()){
			dto.setModifiedBy(null);
		}
	}

	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(FixedCandidatePerdiem dto) {}

	/**
	 * Returns all rows from the FIXED_PERDIEM table that match the specified arbitrary SQL statement
	 */
	public FixedCandidatePerdiem[] findByDynamicSelect(String sql, Object[] sqlParams) throws FixedCandidatePerdiemDaoException {
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
			throw new FixedCandidatePerdiemDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Returns all rows from the FIXED_PERDIEM table that match the specified arbitrary SQL statement
	 */
	public FixedCandidatePerdiem[] findByDynamicWhere(String sql, Object[] sqlParams) throws FixedCandidatePerdiemDaoException {
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
			throw new FixedCandidatePerdiemDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
}
