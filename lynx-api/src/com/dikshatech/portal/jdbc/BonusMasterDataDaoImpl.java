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

import com.dikshatech.portal.dao.BonusMasterDataDao;
import com.dikshatech.portal.dto.BonusMasterData;
import com.dikshatech.portal.dto.BonusMasterDataPk;
import com.dikshatech.portal.dto.BonusReconciliationReport;
import com.dikshatech.portal.exceptions.BonusMasterDataDaoException;
import com.dikshatech.portal.models.BonusReconciliationModel;


public class BonusMasterDataDaoImpl implements BonusMasterDataDao{
	
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
	protected final String			SQL_SELECT				= "SELECT ID, USER_ID, QUATERELY_BONUS, COMPANY_BONUS, MONTH, CURRENCY_TYPE,YEAR FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT				= "INSERT INTO " + getTableName() + " ( ID, USER_ID, QUATERELY_BONUS, COMPANY_BONUS, MONTH, CURRENCY_TYPE, YEAR ) VALUES ( ?, ?, ?, ?, ?, ?,? )";
	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String			SQL_UPDATE				= "UPDATE " + getTableName() + " SET ID = ?, USER_ID = ?, QUATERELY_BONUS = ?, COMPANY_BONUS = ?, MONTH = ?, CURRENCY_TYPE = ? YEAR= ? WHERE ID = ?";
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
	protected static final int		COLUMN_QUATERELY_BONUS	= 3;
	/**
	 * Index of column PERDIEM_FROM
	 */
	protected static final int		COLUMN_COMPANY_BONUS		= 4;
	/**
	 * Index of column PERDIEM_TO
	 */
	protected static final int		COLUMN_MONTH		= 5;
	/**
	 * Index of column CURRENCY_TYPE
	 */
	protected static final int		COLUMN_CURRENCY_TYPE	= 6;
	/**
	 * Index of column CURRENCY_TYPE
	 */
	protected static final int		COLUMN_YEAR	= 7;
	
	/**
	 * Number of columns
	 */
	protected static final int		NUMBER_OF_COLUMNS		= 7;
	/**
	 * Index of primary-key column ID
	 */
	protected static final int		PK_COLUMN_ID			= 1;

	public BonusMasterDataDaoImpl(Connection conn) {
		// TODO Auto-generated constructor stub
	}
	
	public BonusMasterDataDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Inserts a new row in the BONUS_MASTER_DATA table.
	 */
	@Override
	public BonusMasterDataPk insert(BonusMasterData dto) throws BonusMasterDataDaoException {
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
			stmt.setString(index++, dto.getqBonus());
			stmt.setString(index++, dto.getcBonus());
			stmt.setString(index++, dto.getMonth());
			stmt.setInt(index++, dto.getCurrencyType());
			stmt.setInt(index++, dto.getYear());
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
			throw new BonusMasterDataDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	@Override
	public void update(BonusMasterDataPk pk, BonusMasterData dto) throws BonusMasterDataDaoException {
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
			stmt.setString(index++, dto.getqBonus());
			stmt.setString(index++, dto.getcBonus());
			stmt.setString(index++, dto.getMonth());
			stmt.setInt(index++, dto.getCurrencyType());
			stmt.setInt(index++, dto.getYear());
			stmt.setInt(7, pk.getId());
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new BonusMasterDataDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
	@Override
	public void delete(BonusMasterDataPk pk) throws BonusMasterDataDaoException {
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
			throw new BonusMasterDataDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	@Override
	public BonusMasterData findByPrimaryKey(BonusMasterDataPk pk) throws BonusMasterDataDaoException {
		return findByPrimaryKey(pk.getId());
	}

	@Override
	public BonusMasterData findByPrimaryKey(int id) throws BonusMasterDataDaoException {
		BonusMasterData ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { new Integer(id) });
		return ret.length == 0 ? null : ret[0];
	}

	@Override
	public BonusMasterData[] findAll() throws BonusMasterDataDaoException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY ID", null);
	}

	@Override
	public BonusMasterData[] findWhereIdEquals(int id) throws BonusMasterDataDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE ID = ? ORDER BY ID", new Object[] { new Integer(id) });
	}

	@Override
	public BonusMasterData[] findWhereUserIdEquals(int userId) throws BonusMasterDataDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE USER_ID = ? ORDER BY USER_ID", new Object[] { new Integer(userId) });

	}

	@Override
	public BonusMasterData[] findWhereQbonusEquals(String qBonus) throws BonusMasterDataDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE QUATERELY_BONUS = ? ORDER BY QUATERELY_BONUS", new Object[] { qBonus });
	}

	@Override
	public BonusMasterData[] findWhereCbonusEquals(String cBonus) throws BonusMasterDataDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE QUATERELY_BONUS = ? ORDER BY QUATERELY_BONUS", new Object[] { cBonus });
	}

	@Override
	public BonusMasterData[] findWhereMonthEquals(String month) throws BonusMasterDataDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE MONTH = ? ORDER BY MONTH", new Object[] {month});
	}

	@Override
	public BonusMasterData[] findWhereCurrencyTypeEquals(String currencyType) throws BonusMasterDataDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE CURRENCY_TYPE = ? ORDER BY CURRENCY_TYPE", new Object[] { currencyType });
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

	@Override
	public BonusMasterData[] findByDynamicSelect(String sql, Object[] sqlParams) throws BonusMasterDataDaoException {
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
					throw new BonusMasterDataDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
	}

	@Override
	public BonusMasterData[] findByDynamicWhere(String sql, Object[] sqlParams) throws BonusMasterDataDaoException {
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
					throw new BonusMasterDataDaoException("Exception: " + _e.getMessage(), _e);
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
		return "BONUS_MASTER_DATA";
	}
	
	/**
	 * Fetches a single row from the result set
	 */
	protected BonusMasterData fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			BonusMasterData dto = new BonusMasterData();
			populateDto(dto, rs);
			return dto;
		} else{
			return null;
		}
	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected BonusMasterData[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<BonusMasterData> resultList = new ArrayList<BonusMasterData>();
		while (rs.next()){
			BonusMasterData dto = new BonusMasterData();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		BonusMasterData ret[] = new BonusMasterData[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(BonusMasterData dto, ResultSet rs) throws SQLException {
		dto.setId(rs.getInt(COLUMN_ID));
		dto.setUserId(rs.getInt(COLUMN_USER_ID));
		dto.setqBonus(rs.getString(COLUMN_QUATERELY_BONUS));
		dto.setcBonus(rs.getString(COLUMN_COMPANY_BONUS));
		dto.setMonth(rs.getString(COLUMN_MONTH));
		dto.setCurrencyType(rs.getInt(COLUMN_CURRENCY_TYPE));
		dto.setYear(rs.getInt(COLUMN_YEAR));
	}

	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(BonusMasterData dto) {}

	
	public List<BonusReconciliationReport> getBonusCurrencyMaps(String month,int year) throws BonusMasterDataDaoException{
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<BonusReconciliationReport> list = new ArrayList<BonusReconciliationReport>();
		try{
			
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			String SQL = "SELECT USER_ID, QUATERELY_BONUS, COMPANY_BONUS, MONTH, CURRENCY_TYPE ," + BonusReconciliationModel.AUTO
					+ " AS TYPE FROM BONUS_MASTER_DATA B JOIN USERS U ON U.ID=B.USER_ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID"
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
			// fetch the results
			list = populateBonusDetails(rs);
/*			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			SQL = "SELECT F.ID, QUATERELY_BONUS, COMPANY_BONUS, MONTH, CURRENCY_TYPE ," + ReconciliationModel.FIXED
					+ " AS TYPE FROM FIXED_BONUS F JOIN USERS U ON U.ID=F.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID WHERE U.EMP_ID>0 AND MONTH= ? AND YEAR=?";
			stmt = conn.prepareStatement(SQL);
			stmt.setMaxRows(maxRows);
		   stmt.setObject(1, month);
		   stmt.setObject(2, year);
	
			rs = stmt.executeQuery();
			// fetch the results
			list.addAll(populateBonusDetails(rs));*/
			return list;
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new BonusMasterDataDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}

	private List<BonusReconciliationReport> populateBonusDetails(ResultSet rs) throws SQLException {
			List<BonusReconciliationReport> list = new ArrayList<BonusReconciliationReport>();
			while (rs.next()){
				try{
					int userId=rs.getInt(1);
					String qBonus=rs.getString(2);
					String cBonus=rs.getString(3);
					String month=rs.getString(4);
					int currencyType=rs.getShort(5);
					short type=rs.getShort(6);
					
					list.add(new BonusReconciliationReport(userId,qBonus,cBonus,currencyType,type,month));
				} catch (Exception e){
					logger.error("unable to get perdiem data from db", e);
				}
			}
			return list;
		}
	

}
