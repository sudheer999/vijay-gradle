package com.dikshatech.portal.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.RetentionBonusMasterDataDao;
import com.dikshatech.portal.dto.RetentionBonusMasterData;
import com.dikshatech.portal.dto.RetentionBonusMasterDataPk;
import com.dikshatech.portal.dto.RetentionBonusReconciliationReport;
import com.dikshatech.portal.exceptions.RetentionBonusMasterDataDaoException;
import com.dikshatech.portal.models.RetentionBonusReconciliationModel;
public class RetentionBonusMasterDataDaoImpl  implements RetentionBonusMasterDataDao{

	
	/**
	 * The factory class for this DAO has two versions of the create() method -
	 * one that takes no arguments and one that takes a Connection argument. If
	 * the Connection version is chosen then the connection will be stored in
	 * this attribute and will be used by all calls to this DAO, otherwise a new
	 * Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;
	protected static final Logger	logger					= Logger.getLogger(PerdiemMasterDataDaoImpl.class);
	/**
	 * All finder methods in this class use this SELECT constant to build their
	 * queries
	 */
	//protected final String			SQL_SELECT				= "SELECT ID, USER_ID, QUATERELY_BONUS, COMPANY_BONUS, MONTH, CURRENCY_TYPE,YEAR FROM " + getTableName() + "";
	protected final String			SQL_SELECT				= "SELECT ID, USER_ID, MONTH, CURRENCY_TYPE,YEAR,RETENTION_BONUS FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	//protected final String			SQL_INSERT				= "INSERT INTO " + getTableName() + " ( ID, USER_ID, QUATERELY_BONUS, COMPANY_BONUS, MONTH, CURRENCY_TYPE, YEAR ) VALUES ( ?, ?, ?, ?, ?, ?,? )";
	protected final String			SQL_INSERT				= "INSERT INTO " + getTableName() + " ( ID, USER_ID, MONTH, CURRENCY_TYPE, YEAR, RETENTION_BONUS ) VALUES ( ?, ?, ?, ?, ?, ? )";
	/**
	 * SQL UPDATE statement for this table
	 */
	//protected final String			SQL_UPDATE				= "UPDATE " + getTableName() + " SET ID = ?, USER_ID = ?, QUATERELY_BONUS = ?, COMPANY_BONUS = ?, MONTH = ?, CURRENCY_TYPE = ? YEAR= ? WHERE ID = ?";
	protected final String			SQL_UPDATE				= "UPDATE " + getTableName() + " SET ID = ?, USER_ID = ?, MONTH = ?,RETENTION_BONUS=?, CURRENCY_TYPE = ? YEAR= ? WHERE ID = ?";
	/**
	 * SQL DELETE statement for this table
	 */
	protected final String			SQL_DELETE				= "DELETE FROM " + getTableName() + " WHERE ID = ?";
	/**
	 * Index of column ID
	 */
	protected static final int		COLUMN_ID				= 1;
	/**
	 * Index of column USER_ID
	 */
	protected static final int		COLUMN_USER_ID			= 2;
	/**
	 * Index of column PERDIEM
	 */
	//protected static final int		COLUMN_QUATERELY_BONUS	= 3;
	/**
	 * Index of column PERDIEM_FROM
	 */
	//protected static final int		COLUMN_COMPANY_BONUS		= 4;
	/**
	 * Index of column PERDIEM_TO
	 */
	//protected static final int		COLUMN_MONTH		= 5;
	protected static final int		COLUMN_MONTH		= 3;
	/**
	 * Index of column CURRENCY_TYPE
	 */
	//protected static final int		COLUMN_CURRENCY_TYPE	= 6;
	protected static final int		COLUMN_CURRENCY_TYPE	= 4;
	/**
	 * Index of column CURRENCY_TYPE
	 */
	//protected static final int		COLUMN_YEAR	= 5;
	protected static final int		COLUMN_YEAR	= 5;
	protected static final int		COLUMN_RETENTION_BONUS=6;
	/**
	 * Number of columns
	 */
	//protected static final int		NUMBER_OF_COLUMNS		= 7;
	protected static final int		NUMBER_OF_COLUMNS		= 6;
	/**
	 * Index of primary-key column ID
	 */
	protected static final int		PK_COLUMN_ID			= 1;

	public RetentionBonusMasterDataDaoImpl(Connection conn) {
		// TODO Auto-generated constructor stub
	}
	
	public RetentionBonusMasterDataDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Inserts a new row in the RETENTION_BONUS_MASTER_DATA table.
	 */
	public RetentionBonusMasterDataPk insert(RetentionBonusMasterData dto) throws RetentionBonusMasterDataDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			stmt.setInt(index++, dto.getId());
			stmt.setInt(index++, dto.getUserId());
			//stmt.setString(index++, dto.getqBonus());
			//stmt.setString(index++, dto.getcBonus());
			stmt.setString(index++, dto.getMonth());
			stmt.setInt(index++, dto.getCurrencyType());
			stmt.setInt(index++, dto.getYear());
			stmt.setString(index++, dto.getrBonus());
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
			throw new RetentionBonusMasterDataDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}


	public void update(RetentionBonusMasterDataPk pk, RetentionBonusMasterData dto) throws RetentionBonusMasterDataDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL_UPDATE + " with DTO: " + dto);
			}
			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			stmt.setInt(index++, dto.getId());
			stmt.setInt(index++, dto.getUserId());
			//stmt.setString(index++, dto.getqBonus());
			//stmt.setString(index++, dto.getcBonus());
			stmt.setString(index++, dto.getMonth());
			stmt.setInt(index++, dto.getCurrencyType());
			stmt.setInt(index++, dto.getYear());
			stmt.setString(index++, dto.getrBonus());
			stmt.setInt(7, pk.getId());
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new RetentionBonusMasterDataDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	public void delete(RetentionBonusMasterDataPk pk) throws RetentionBonusMasterDataDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// get the user-specified connection or get a connection from the
			// ResourceManager
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
			throw new RetentionBonusMasterDataDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}


	public RetentionBonusMasterData findByPrimaryKey(RetentionBonusMasterDataPk pk) throws RetentionBonusMasterDataDaoException {
		return findByPrimaryKey(pk.getId());
	}

	public RetentionBonusMasterData findByPrimaryKey(int id) throws RetentionBonusMasterDataDaoException {
		RetentionBonusMasterData ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { new Integer(id) });
		return ret.length == 0 ? null : ret[0];
	}

	
	public RetentionBonusMasterData[] findAll() throws RetentionBonusMasterDataDaoException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY ID", null);
	}

	
	public RetentionBonusMasterData[] findWhereIdEquals(int id) throws RetentionBonusMasterDataDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] { new Integer(id) });
	}

	
	public RetentionBonusMasterData[] findWhereUserIdEquals(int userId) throws RetentionBonusMasterDataDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE USER_ID = ? ORDER BY USER_ID", new Object[] { new Integer(userId) });

	}

	
	public RetentionBonusMasterData[] findWhereQbonusEquals(String qBonus) throws RetentionBonusMasterDataDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE QUATERELY_BONUS = ? ORDER BY QUATERELY_BONUS", new Object[] { qBonus });
	}

	
	public RetentionBonusMasterData[] findWhereCbonusEquals(String cBonus) throws RetentionBonusMasterDataDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE QUATERELY_BONUS = ? ORDER BY QUATERELY_BONUS", new Object[] { cBonus });
	}

	
	public RetentionBonusMasterData[] findWhereMonthEquals(String month) throws RetentionBonusMasterDataDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE MONTH = ? ORDER BY MONTH", new Object[] {month});
	}

	
	public RetentionBonusMasterData[] findWhereCurrencyTypeEquals(String currencyType) throws RetentionBonusMasterDataDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE CURRENCY_TYPE = ? ORDER BY CURRENCY_TYPE", new Object[] { currencyType });
	}

	
	public void setMaxRows(int maxRows) {
		// TODO Auto-generated method stub
		
	}

	
	public int getMaxRows() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public RetentionBonusMasterData[] findByDynamicSelect(String sql, Object[] sqlParams) throws RetentionBonusMasterDataDaoException {
		// declare variables
				final boolean isConnSupplied = (userConn != null);
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				try{
					// get the user-specified connection or get a connection from the
					// ResourceManager
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
					throw new RetentionBonusMasterDataDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
	}

	
	public RetentionBonusMasterData[] findByDynamicWhere(String sql, Object[] sqlParams) throws RetentionBonusMasterDataDaoException {
		// declare variables
				final boolean isConnSupplied = (userConn != null);
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				try{
					// get the user-specified connection or get a connection from the
					// ResourceManager
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
					throw new RetentionBonusMasterDataDaoException("Exception: " + _e.getMessage(), _e);
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
		return "RETENTION_BONUS_MASTER_DATA";
	}
	
	/**
	 * Fetches a single row from the result set
	 */
	protected RetentionBonusMasterData fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			RetentionBonusMasterData dto = new RetentionBonusMasterData();
			populateDto(dto, rs);
			return dto;
		} else{
			return null;
		}
	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected RetentionBonusMasterData[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<RetentionBonusMasterData> resultList = new ArrayList<RetentionBonusMasterData>();
		while (rs.next()){
			RetentionBonusMasterData dto = new RetentionBonusMasterData();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		RetentionBonusMasterData ret[] = new RetentionBonusMasterData[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(RetentionBonusMasterData dto, ResultSet rs) throws SQLException {
		dto.setId(rs.getInt(COLUMN_ID));
		dto.setUserId(rs.getInt(COLUMN_USER_ID));
		//dto.setqBonus(rs.getString(COLUMN_QUATERELY_BONUS));
		//dto.setcBonus(rs.getString(COLUMN_COMPANY_BONUS));
		dto.setMonth(rs.getString(COLUMN_MONTH));
		dto.setCurrencyType(rs.getInt(COLUMN_CURRENCY_TYPE));
		dto.setYear(rs.getInt(COLUMN_YEAR));
		dto.setqBonus(rs.getString(COLUMN_RETENTION_BONUS));
	}

	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(RetentionBonusMasterData dto) {}

	
	public List<RetentionBonusReconciliationReport> getBonusCurrencyMaps(String month,int year) throws RetentionBonusMasterDataDaoException{
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<RetentionBonusReconciliationReport> list = new ArrayList<RetentionBonusReconciliationReport>();
		try{
			
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			/*String SQL = "SELECT USER_ID, QUATERELY_BONUS, COMPANY_BONUS, MONTH, CURRENCY_TYPE ," + RetentionBonusReconciliationModel.AUTO
					+ " AS TYPE FROM RETENTION_BONUS_MASTER_DATA B JOIN USERS U ON U.ID=B.USER_ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID"
					+ " where MONTH= ? and YEAR=?";*/
			String SQL = "SELECT USER_ID, MONTH, CURRENCY_TYPE,RETENTION_BONUS ," + RetentionBonusReconciliationModel.AUTO
					+ " AS TYPE FROM RETENTION_BONUS_MASTER_DATA B JOIN USERS U ON U.ID=B.USER_ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID"
					+ " where MONTH= ? and YEAR=?";
			if (logger.isDebugEnabled()){
				logger.debug("Executing " + SQL);
			}
			// prepare statement
			
			stmt = conn.prepareStatement(SQL);
			stmt.setMaxRows(maxRows);
			stmt.setObject(1, month);
			stmt.setObject(2, year);
			rs = stmt.executeQuery();
			list = populateBonusDetails(rs);
			return list;
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new RetentionBonusMasterDataDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	private List<RetentionBonusReconciliationReport> populateBonusDetails(ResultSet rs) throws SQLException {
			List<RetentionBonusReconciliationReport> list = new ArrayList<RetentionBonusReconciliationReport>();
			while (rs.next()){
				try{
					/*int userId=rs.getInt(1);
					String qBonus=rs.getString(2);
					String cBonus=rs.getString(3);
					String month=rs.getString(4);
					int currencyType=rs.getShort(5);
					short type=rs.getShort(6);*/
					int userId=rs.getInt(1);
					String month=rs.getString(2);
					int currencyType=rs.getShort(3);
					String rBonus = rs.getString(4);
					short type=rs.getShort(5);
					
					//list.add(new RetentionBonusReconciliationReport(userId,qBonus,cBonus,currencyType,type,month));
							list.add(new RetentionBonusReconciliationReport(userId,currencyType,type,month,rBonus));
				} catch (Exception e){
					logger.error("unable to get perdiem data from db", e);
				}
			}
			return list;
		}
	


}
