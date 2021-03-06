/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
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

import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.portal.dao.DepPerdiemComponentsDao;
import com.dikshatech.portal.dto.DepPerdiemComponents;
import com.dikshatech.portal.dto.DepPerdiemComponentsPk;
import com.dikshatech.portal.exceptions.DepPerdiemComponentsDaoException;

public class DepPerdiemComponentsDaoImpl extends AbstractDAO implements DepPerdiemComponentsDao {

	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
	takes no arguments and one that takes a Connection argument. If the Connection version
	is chosen then the connection will be stored in this attribute and will be used by all
	calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;
	protected static final Logger	logger				= Logger.getLogger(DepPerdiemComponentsDaoImpl.class);
	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String			SQL_SELECT			= "SELECT ID, REP_ID, TYPE, REASON, CURRENCY, AMOUNT, AMOUNT_INR, ADDED_BY, ADDED_ON, COMMENTS FROM " + getTableName() + "";
	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT			= "INSERT INTO " + getTableName() + " ( ID, REP_ID, TYPE, REASON, CURRENCY, AMOUNT, AMOUNT_INR, ADDED_BY, ADDED_ON, COMMENTS ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String			SQL_UPDATE			= "UPDATE " + getTableName() + " SET ID = ?, REP_ID = ?, TYPE = ?, REASON = ?, CURRENCY = ?, AMOUNT = ?, AMOUNT_INR = ?, ADDED_BY = ?, ADDED_ON = ?, COMMENTS = ? WHERE ID = ?";
	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String			SQL_DELETE			= "DELETE FROM " + getTableName() + " WHERE ID = ?";
	/** 
	 * Index of column ID
	 */
	protected static final int		COLUMN_ID			= 1;
	/** 
	 * Index of column REP_ID
	 */
	protected static final int		COLUMN_REP_ID		= 2;
	/** 
	 * Index of column TYPE
	 */
	protected static final int		COLUMN_TYPE			= 3;
	/** 
	 * Index of column REASON
	 */
	protected static final int		COLUMN_REASON		= 4;
	/** 
	 * Index of column CURRENCY
	 */
	protected static final int		COLUMN_CURRENCY		= 5;
	/** 
	 * Index of column AMOUNT
	 */
	protected static final int		COLUMN_AMOUNT		= 6;
	/** 
	 * Index of column AMOUNT_INR
	 */
	protected static final int		COLUMN_AMOUNT_INR	= 7;
	/** 
	 * Index of column ADDED_BY
	 */
	protected static final int		COLUMN_ADDED_BY		= 8;
	/** 
	 * Index of column ADDED_ON
	 */
	protected static final int		COLUMN_ADDED_ON		= 9;
	/** 
	 * Index of column COMMENTS
	 */
	protected static final int		COLUMN_COMMENTS		= 10;
	/** 
	 * Number of columns
	 */
	protected static final int		NUMBER_OF_COLUMNS	= 10;
	/** 
	 * Index of primary-key column ID
	 */
	protected static final int		PK_COLUMN_ID		= 1;

	/** 
	 * Inserts a new row in the DEP_PERDIEM_COMPONENTS table.
	 */
	public DepPerdiemComponentsPk insert(DepPerdiemComponents dto) throws DepPerdiemComponentsDaoException {
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
			if (dto.getRepId() != null){
				stmt.setInt(index++, dto.getRepId().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			if (dto.getType() != null){
				stmt.setShort(index++, dto.getType().shortValue());
			} else{
				stmt.setNull(index++, java.sql.Types.SMALLINT);
			}
			stmt.setString(index++, dto.getReason());
			if (dto.getCurrency() != null){
				stmt.setInt(index++, dto.getCurrency().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getAmount()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getAmountInr()));
			stmt.setString(index++, dto.getAddedBy());
			stmt.setTimestamp(index++, dto.getAddedOn() == null ? null : new java.sql.Timestamp(dto.getAddedOn().getTime()));
			stmt.setString(index++, dto.getComments());
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
			throw new DepPerdiemComponentsDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/** 
	 * Updates a single row in the DEP_PERDIEM_COMPONENTS table.
	 */
	public void update(DepPerdiemComponentsPk pk, DepPerdiemComponents dto) throws DepPerdiemComponentsDaoException {
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
			if (dto.getRepId() != null){
				stmt.setInt(index++, dto.getRepId().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			if (dto.getType() != null){
				stmt.setShort(index++, dto.getType().shortValue());
			} else{
				stmt.setNull(index++, java.sql.Types.SMALLINT);
			}
			stmt.setString(index++, dto.getReason());
			if (dto.getCurrency() != null){
				stmt.setInt(index++, dto.getCurrency().intValue());
			} else{
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getAmount()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getAmountInr()));
			stmt.setString(index++, dto.getAddedBy());
			stmt.setTimestamp(index++, dto.getAddedOn() == null ? null : new java.sql.Timestamp(dto.getAddedOn().getTime()));
			stmt.setString(index++, dto.getComments());
			if (pk.getId() != null){
				stmt.setInt(11, pk.getId().intValue());
			} else{
				stmt.setNull(11, java.sql.Types.INTEGER);
			}
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new DepPerdiemComponentsDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/** 
	 * Deletes a single row in the DEP_PERDIEM_COMPONENTS table.
	 */
	public void delete(DepPerdiemComponentsPk pk) throws DepPerdiemComponentsDaoException {
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
			throw new DepPerdiemComponentsDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/** 
	 * Returns the rows from the DEP_PERDIEM_COMPONENTS table that matches the specified primary-key value.
	 */
	public DepPerdiemComponents findByPrimaryKey(DepPerdiemComponentsPk pk) throws DepPerdiemComponentsDaoException {
		return findByPrimaryKey(pk.getId());
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_COMPONENTS table that match the criteria 'ID = :id'.
	 */
	public DepPerdiemComponents findByPrimaryKey(Integer id) throws DepPerdiemComponentsDaoException {
		DepPerdiemComponents ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { id });
		return ret.length == 0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_COMPONENTS table that match the criteria 'REP_ID = :repId'.
	 */
	public DepPerdiemComponents[] findWhereRepIdEquals(Integer repId) throws DepPerdiemComponentsDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE REP_ID = ? ORDER BY REP_ID", new Object[] { repId });
	}

	/**
	 * Method 'DepPerdiemComponentsDaoImpl'
	 * 
	 */
	public DepPerdiemComponentsDaoImpl() {}

	/**
	 * Method 'DepPerdiemComponentsDaoImpl'
	 * 
	 * @param userConn
	 */
	public DepPerdiemComponentsDaoImpl(final java.sql.Connection userConn) {
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
		return "DEP_PERDIEM_COMPONENTS";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected DepPerdiemComponents fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			DepPerdiemComponents dto = new DepPerdiemComponents();
			populateDto(dto, rs);
			return dto;
		} else{
			return null;
		}
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected DepPerdiemComponents[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<DepPerdiemComponents> resultList = new ArrayList<DepPerdiemComponents>();
		while (rs.next()){
			DepPerdiemComponents dto = new DepPerdiemComponents();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		DepPerdiemComponents ret[] = new DepPerdiemComponents[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(DepPerdiemComponents dto, ResultSet rs) throws SQLException {
		dto.setId(new Integer(rs.getInt(COLUMN_ID)));
		dto.setRepId(new Integer(rs.getInt(COLUMN_REP_ID)));
		dto.setType(new Short(rs.getShort(COLUMN_TYPE)));
		dto.setReason(rs.getString(COLUMN_REASON));
		dto.setCurrency(new Integer(rs.getInt(COLUMN_CURRENCY)));
		dto.setAmount(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(COLUMN_AMOUNT)));
		dto.setAmountInr(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(COLUMN_AMOUNT_INR)));
		dto.setAddedBy(rs.getString(COLUMN_ADDED_BY));
		dto.setAddedOn(rs.getTimestamp(COLUMN_ADDED_ON));
		dto.setComments(rs.getString(COLUMN_COMMENTS));
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(DepPerdiemComponents dto) {}

	/** 
	 * Returns all rows from the DEP_PERDIEM_COMPONENTS table that match the specified arbitrary SQL statement
	 */
	public DepPerdiemComponents[] findByDynamicSelect(String sql, Object[] sqlParams) throws DepPerdiemComponentsDaoException {
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
			throw new DepPerdiemComponentsDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	/** 
	 * Returns all rows from the DEP_PERDIEM_COMPONENTS table that match the specified arbitrary SQL statement
	 */
	public DepPerdiemComponents[] findByDynamicWhere(String sql, Object[] sqlParams) throws DepPerdiemComponentsDaoException {
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
			throw new DepPerdiemComponentsDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
}
