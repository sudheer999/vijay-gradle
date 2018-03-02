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
import java.util.Date;
import org.apache.log4j.Logger;
import com.dikshatech.portal.dao.VisaInfoDao;
import com.dikshatech.portal.dto.VisaInfo;
import com.dikshatech.portal.dto.VisaInfoPk;
import com.dikshatech.portal.exceptions.VisaInfoDaoException;

public class VisaInfoDaoImpl extends AbstractDAO implements VisaInfoDao {

	/**
	 * The factory class for this DAO has two versions of the create() method - one that
	 * takes no arguments and one that takes a Connection argument. If the Connection version
	 * is chosen then the connection will be stored in this attribute and will be used by all
	 * calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;
	protected static final Logger	logger				= Logger.getLogger(VisaInfoDaoImpl.class);
	/**
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String			SQL_SELECT			= "SELECT ID, PASSPORT_ID, VISA_FROM, VISA_TO, VISA_TYPE, COUNTRY FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT			= "INSERT INTO " + getTableName() + " ( ID, PASSPORT_ID, VISA_FROM, VISA_TO, VISA_TYPE, COUNTRY ) VALUES ( ?, ?, ?, ?, ?, ? )";
	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String			SQL_UPDATE			= "UPDATE " + getTableName() + " SET ID = ?, PASSPORT_ID = ?, VISA_FROM = ?, VISA_TO = ?, VISA_TYPE = ?, COUNTRY = ? WHERE ID = ?";
	/**
	 * SQL DELETE statement for this table
	 */
	protected final String			SQL_DELETE			= "DELETE FROM " + getTableName() + " WHERE ID = ?";
	/**
	 * Index of column ID
	 */
	protected static final int		COLUMN_ID			= 1;
	/**
	 * Index of column PASSPORT_ID
	 */
	protected static final int		COLUMN_PASSPORT_ID	= 2;
	/**
	 * Index of column VISA_FROM
	 */
	protected static final int		COLUMN_VISA_FROM	= 3;
	/**
	 * Index of column VISA_TO
	 */
	protected static final int		COLUMN_VISA_TO		= 4;
	/**
	 * Index of column VISA_TYPE
	 */
	protected static final int		COLUMN_VISA_TYPE	= 5;
	/**
	 * Index of column COUNTRY
	 */
	protected static final int		COLUMN_COUNTRY		= 6;
	/**
	 * Number of columns
	 */
	protected static final int		NUMBER_OF_COLUMNS	= 6;
	/**
	 * Index of primary-key column ID
	 */
	protected static final int		PK_COLUMN_ID		= 1;

	/**
	 * Inserts a new row in the visa_info table.
	 */
	public VisaInfoPk insert(VisaInfo dto) throws VisaInfoDaoException {
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
			if (dto.getId() != null){
				stmt.setInt(index++, dto.getId().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			if (dto.getPassportId() != null){
				stmt.setInt(index++, dto.getPassportId().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			stmt.setDate(index++, dto.getVisaFrom() == null ? null : new java.sql.Date(dto.getVisaFrom().getTime()));
			stmt.setDate(index++, dto.getVisaTo() == null ? null : new java.sql.Date(dto.getVisaTo().getTime()));
			stmt.setString(index++, dto.getVisaType());
			stmt.setString(index++, dto.getCountry());
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
				dto.setId(new Integer(rs.getInt(1)));
			}
			reset(dto);
			return dto.createPk();
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new VisaInfoDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Updates a single row in the visa_info table.
	 */
	public void update(VisaInfoPk pk, VisaInfo dto) throws VisaInfoDaoException {
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
			if (dto.getPassportId() != null){
				stmt.setInt(index++, dto.getPassportId().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			stmt.setDate(index++, dto.getVisaFrom() == null ? null : new java.sql.Date(dto.getVisaFrom().getTime()));
			stmt.setDate(index++, dto.getVisaTo() == null ? null : new java.sql.Date(dto.getVisaTo().getTime()));
			stmt.setString(index++, dto.getVisaType());
			stmt.setString(index++, dto.getCountry());
			if (pk.getId() != null){
				stmt.setInt(7, pk.getId().intValue());
			} else{
				stmt.setNull(7, java.sql.Types.INTEGER);
			}
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new VisaInfoDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Deletes a single row in the visa_info table.
	 */
	public void delete(VisaInfoPk pk) throws VisaInfoDaoException {
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
			throw new VisaInfoDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Returns the rows from the visa_info table that matches the specified primary-key value.
	 */
	public VisaInfo findByPrimaryKey(VisaInfoPk pk) throws VisaInfoDaoException {
		return findByPrimaryKey(pk.getId());
	}

	/**
	 * Returns all rows from the visa_info table that match the criteria 'ID = :id'.
	 */
	public VisaInfo findByPrimaryKey(Integer id) throws VisaInfoDaoException {
		VisaInfo ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { id });
		return ret.length == 0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the visa_info table that match the criteria ''.
	 */
	public VisaInfo[] findAll() throws VisaInfoDaoException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY ID", null);
	}

	/**
	 * Returns all rows from the visa_info table that match the criteria 'ID = :id'.
	 */
	public VisaInfo[] findWhereIdEquals(Integer id) throws VisaInfoDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] { id });
	}

	/**
	 * Returns all rows from the visa_info table that match the criteria 'PASSPORT_ID = :passportId'.
	 */
	public VisaInfo[] findWherePassportIdEquals(Integer passportId) throws VisaInfoDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE PASSPORT_ID = ? ORDER BY PASSPORT_ID", new Object[] { passportId });
	}

	/**
	 * Returns all rows from the visa_info table that match the criteria 'VISA_FROM = :visaFrom'.
	 */
	public VisaInfo[] findWhereVisaFromEquals(Date visaFrom) throws VisaInfoDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE VISA_FROM = ? ORDER BY VISA_FROM", new Object[] { visaFrom == null ? null : new java.sql.Date(visaFrom.getTime()) });
	}

	/**
	 * Returns all rows from the visa_info table that match the criteria 'VISA_TO = :visaTo'.
	 */
	public VisaInfo[] findWhereVisaToEquals(Date visaTo) throws VisaInfoDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE VISA_TO = ? ORDER BY VISA_TO", new Object[] { visaTo == null ? null : new java.sql.Date(visaTo.getTime()) });
	}

	/**
	 * Returns all rows from the visa_info table that match the criteria 'VISA_TYPE = :visaType'.
	 */
	public VisaInfo[] findWhereVisaTypeEquals(String visaType) throws VisaInfoDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE VISA_TYPE = ? ORDER BY VISA_TYPE", new Object[] { visaType });
	}

	/**
	 * Returns all rows from the visa_info table that match the criteria 'COUNTRY = :country'.
	 */
	public VisaInfo[] findWhereCountryEquals(String country) throws VisaInfoDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE COUNTRY = ? ORDER BY COUNTRY", new Object[] { country });
	}

	/**
	 * Method 'VisaInfoDaoImpl'
	 */
	public VisaInfoDaoImpl() {}

	/**
	 * Method 'VisaInfoDaoImpl'
	 * 
	 * @param userConn
	 */
	public VisaInfoDaoImpl(final java.sql.Connection userConn) {
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
		return "VISA_INFO";
	}

	/**
	 * Fetches a single row from the result set
	 */
	protected VisaInfo fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			VisaInfo dto = new VisaInfo();
			populateDto(dto, rs);
			return dto;
		} else{
			return null;
		}
	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected VisaInfo[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<VisaInfo> resultList = new ArrayList<VisaInfo>();
		while (rs.next()){
			VisaInfo dto = new VisaInfo();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		VisaInfo ret[] = new VisaInfo[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(VisaInfo dto, ResultSet rs) throws SQLException {
		dto.setId(new Integer(rs.getInt(COLUMN_ID)));
		dto.setPassportId(new Integer(rs.getInt(COLUMN_PASSPORT_ID)));
		dto.setVisaFrom(rs.getDate(COLUMN_VISA_FROM));
		dto.setVisaTo(rs.getDate(COLUMN_VISA_TO));
		dto.setVisaType(rs.getString(COLUMN_VISA_TYPE));
		dto.setCountry(rs.getString(COLUMN_COUNTRY));
	}

	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(VisaInfo dto) {}

	/**
	 * Returns all rows from the visa_info table that match the specified arbitrary SQL statement
	 */
	public VisaInfo[] findByDynamicSelect(String sql, Object[] sqlParams) throws VisaInfoDaoException {
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
			throw new VisaInfoDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/**
	 * Returns all rows from the visa_info table that match the specified arbitrary SQL statement
	 */
	public VisaInfo[] findByDynamicWhere(String sql, Object[] sqlParams) throws VisaInfoDaoException {
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
			throw new VisaInfoDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
}
