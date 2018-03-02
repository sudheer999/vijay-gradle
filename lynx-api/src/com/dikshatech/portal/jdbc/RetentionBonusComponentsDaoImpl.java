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
import com.dikshatech.portal.dao.RetentionBonusComponentsDao;
import com.dikshatech.portal.dto.RetentionBonusComponents;
import com.dikshatech.portal.dto.RetentionBonusComponentsPk;
import com.dikshatech.portal.exceptions.RetentionBonusComponentsDaoException;

public class RetentionBonusComponentsDaoImpl extends AbstractDAO implements RetentionBonusComponentsDao{


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
	protected final String			SQL_SELECT			= "SELECT ID, REP_ID, TYPE, REASON, CURRENCY_TYPE, AMOUNT, ADDED_BY, ADDED_ON, COMMENTS FROM " + getTableName() + "";
	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT			= "INSERT INTO " + getTableName() + " ( ID, REP_ID, TYPE, REASON, CURRENCY_TYPE, AMOUNT, ADDED_BY, ADDED_ON, COMMENTS) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?  )";
	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String			SQL_UPDATE			= "UPDATE " + getTableName() + " SET ID=?, REP_ID = ?, TYPE = ?, REASON = ?, CURRENCY_TYPE = ?, AMOUNT = ?, ADDED_BY = ?, ADDED_ON = ?, COMMENTS = ? WHERE ID = ?";
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
	protected static final int		COLUMN_CURRENCY_TYPE	= 5;
	/** 
	 * Index of column AMOUNT
	 */
	protected static final int		COLUMN_AMOUNT		= 6;
	/** 
	 * Index of column AMOUNT_INR
	 */
	protected static final int		COLUMN_ADDED_BY		= 7;
	/** 
	 * Index of column ADDED_ON
	 */
	protected static final int		COLUMN_ADDED_ON		= 8;
	/** 
	 * Index of column COMMENTS
	 */
	protected static final int		COLUMN_COMMENTS		= 9;
	/** 
	 * Number of columns
	 */
	protected static final int		NUMBER_OF_COLUMNS	= 9;
	/** 
	 * Index of primary-key column ID
	 */
	protected static final int		PK_COLUMN_ID		= 1;

	
	@Override
	public RetentionBonusComponentsPk insert(RetentionBonusComponents dto) throws RetentionBonusComponentsDaoException {
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
			stmt.setString(index++, dto.getAddedBy());
			stmt.setTimestamp(index++, dto.getAddedOn() == null ? null : new java.sql.Timestamp(dto.getAddedOn().getTime()));
			stmt.setString(index++, dto.getComments());
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_INSERT + " with DTO: " + dto);
			}
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug( rows + " rows affected (" + (t2 - t1) + " ms)");
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
			throw new RetentionBonusComponentsDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
	@Override
	public void update(RetentionBonusComponentsPk pk, RetentionBonusComponents dto) throws RetentionBonusComponentsDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		//ResultSet rs = null;
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
			stmt.setString(index++, dto.getAddedBy());
			stmt.setTimestamp(index++, dto.getAddedOn() == null ? null : new java.sql.Timestamp(dto.getAddedOn().getTime()));
			stmt.setString(index++, dto.getComments());
			if (pk.getId() != null){
				stmt.setInt(10, pk.getId().intValue());
			} else{
				stmt.setNull(10, java.sql.Types.INTEGER);
			}
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
			/*rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()){
				dto.setId(new Integer(rs.getInt(1)));
			}*/
			
			//return pk.createPk();
			
			/*if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}*/
		} catch (Exception _e){
			_e.printStackTrace();
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new RetentionBonusComponentsDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
		
	}
	@Override
	public void delete(RetentionBonusComponentsPk pk) throws RetentionBonusComponentsDaoException {
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
			throw new RetentionBonusComponentsDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
		
	}

	public RetentionBonusComponents findByPrimaryKey(RetentionBonusComponentsPk pk) throws RetentionBonusComponentsDaoException {
		return findByPrimaryKey(pk.getId());
	}
	@Override
	public RetentionBonusComponents findByPrimaryKey(Integer id) throws RetentionBonusComponentsDaoException {
		RetentionBonusComponents ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { id });
		return ret.length == 0 ? null : ret[0];
	}

	@Override
	public RetentionBonusComponents[] findWhereRepIdEquals(Integer repId) throws RetentionBonusComponentsDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE REP_ID = ? ORDER BY REP_ID", new Object[] { repId });
	}

	/**
	 * Method 'RetentionDepPerdiemComponentsDaoImpl'
	 * 
	 */
	public RetentionBonusComponentsDaoImpl() {}

	/**
	 * Method 'RetentionDepPerdiemComponentsDaoImpl'
	 * 
	 * @param userConn
	 */
	public RetentionBonusComponentsDaoImpl(final java.sql.Connection userConn) {
		this.userConn = userConn;
	}
	
	public void setMaxRows(int maxRows) {
		// TODO Auto-generated method stub
		
	}

	public int getMaxRows() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/** 
	 * Fetches a single row from the result set
	 */
	protected RetentionBonusComponents fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			RetentionBonusComponents dto = new RetentionBonusComponents();
			populateDto(dto, rs);
			return dto;
		} else{
			return null;
		}
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected RetentionBonusComponents[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<RetentionBonusComponents> resultList = new ArrayList<RetentionBonusComponents>();
		while (rs.next()){
			RetentionBonusComponents dto = new RetentionBonusComponents();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		RetentionBonusComponents ret[] = new RetentionBonusComponents[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(RetentionBonusComponents dto, ResultSet rs) throws SQLException {
		dto.setId(new Integer(rs.getInt(COLUMN_ID)));
		dto.setRepId(new Integer(rs.getInt(COLUMN_REP_ID)));
		dto.setType(new Short(rs.getShort(COLUMN_TYPE)));
		dto.setReason(rs.getString(COLUMN_REASON));
		dto.setCurrency(new Integer(rs.getInt(COLUMN_CURRENCY_TYPE)));
		dto.setAmount(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(COLUMN_AMOUNT)));
		dto.setAddedBy(rs.getString(COLUMN_ADDED_BY));
		dto.setAddedOn(rs.getTimestamp(COLUMN_ADDED_ON));
		dto.setComments(rs.getString(COLUMN_COMMENTS));
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(RetentionBonusComponents dto) {}

	
	
	
	@Override
	public RetentionBonusComponents[] findByDynamicSelect(String sql, Object[] sqlParams) throws RetentionBonusComponentsDaoException {
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
			throw new RetentionBonusComponentsDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	@Override
	public RetentionBonusComponents[] findByDynamicWhere(String sql, Object[] sqlParams) throws RetentionBonusComponentsDaoException {
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
					throw new RetentionBonusComponentsDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
	}
	
	
	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "RETENTION_BONUS_REC_COMPONENTS";
	}
	@Override
	public RetentionBonusComponents Retention(RetentionBonusComponentsPk pk)
			throws RetentionBonusComponentsDaoException {
		// TODO Auto-generated method stub
		return null;
	}

}
