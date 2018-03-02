package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dikshatech.beans.RetentionBonusReportBean;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.portal.dao.RetentionBonusReconciliationReportDao;
import com.dikshatech.portal.dto.RetentionBonusReconciliationReport;
import com.dikshatech.portal.dto.RetentionBonusReconciliationReportPk;
import com.dikshatech.portal.exceptions.RetentionBonusReconciliationReportDaoException;
import com.dikshatech.portal.factory.CurrencyDaoFactory;
import com.dikshatech.portal.models.RetentionBonusReconciliationModel;

public class RetentionBonusReconciliationReportDaoImpl extends AbstractDAO implements RetentionBonusReconciliationReportDao {

	/**
	 * The factory class for this DAO has two versions of the create() method - one that
	 * takes no arguments and one that takes a Connection argument. If the Connection version
	 * is chosen then the connection will be stored in this attribute and will be used by all
	 * calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;
	protected static final Logger	logger		= Logger.getLogger(RetentionBonusReconciliationReportDaoImpl.class);
	/**
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	//protected final String			SQL_SELECT	= "SELECT ID, BR_ID, USER_ID, QUATERELY_BONUS, COMPANY_BONUS,QUA_AMOUNT,QUA_AMOUNT_INR,COM_AMOUNT, COM_AMOUNT_INR, MANAGER_ID, MANAGER_NAME, CLIENT_NAME, MODIFIED_BY, MODIFIED_ON, TYPE,ACCOUNT_TYPE ,TOTAL,CURRENCY_TYPE, MONTH, COMMENTS, PROJECT_DAYS, GLOBAL_BENCH_DAYS, TOTAL_DAYS  FROM " + getTableName() + "";
	protected final String			SQL_SELECT	= "SELECT ID, BR_ID, USER_ID,QUA_AMOUNT_INR, MANAGER_ID, MANAGER_NAME, CLIENT_NAME, MODIFIED_BY, MODIFIED_ON, TYPE,ACCOUNT_TYPE ,TOTAL,CURRENCY_TYPE, MONTH, COMMENTS, PROJECT_DAYS, GLOBAL_BENCH_DAYS, TOTAL_DAYS, RETENTION_BONUS  FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	//protected final String			SQL_INSERT	= "INSERT INTO " + getTableName() + " (ID, BR_ID, USER_ID, QUATERELY_BONUS, COMPANY_BONUS, QUA_AMOUNT, QUA_AMOUNT_INR, COM_AMOUNT, COM_AMOUNT_INR, MANAGER_ID, MANAGER_NAME, CLIENT_NAME, MODIFIED_BY, MODIFIED_ON, TYPE,ACCOUNT_TYPE ,TOTAL,CURRENCY_TYPE, MONTH, COMMENTS, PROJECT_DAYS, GLOBAL_BENCH_DAYS, TOTAL_DAYS) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	protected final String			SQL_INSERT	= "INSERT INTO " + getTableName() + " (ID, BR_ID, USER_ID,  QUA_AMOUNT_INR,  MANAGER_ID, MANAGER_NAME, CLIENT_NAME, MODIFIED_BY, MODIFIED_ON, TYPE,ACCOUNT_TYPE ,TOTAL,CURRENCY_TYPE, MONTH, COMMENTS, PROJECT_DAYS, GLOBAL_BENCH_DAYS, TOTAL_DAYS, RETENTION_BONUS) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	/**
	 * SQL UPDATE statement for this table
	 */
	//protected final String			SQL_UPDATE	= "UPDATE " + getTableName() + " SET ID = ?, BR_ID = ?, USER_ID = ?, QUATERELY_BONUS = ?, COMPANY_BONUS = ?, QUA_AMOUNT = ?, QUA_AMOUNT_INR = ?, COM_AMOUNT = ?, COM_AMOUNT_INR = ?,  MANAGER_ID = ?, MANAGER_NAME = ?, CLIENT_NAME = ?, MODIFIED_BY = ?, MODIFIED_ON = ?, TYPE = ?, ACCOUNT_TYPE = ?, TOTAL = ?, CURRENCY_TYPE=?, MONTH=? , COMMENTS = ? , PROJECT_DAYS=?, GLOBAL_BENCH_DAYS=?, TOTAL_DAYS=? WHERE ID = ?";
	protected final String			SQL_UPDATE	= "UPDATE " + getTableName() + " SET ID = ?, BR_ID = ?, USER_ID = ?, QUA_AMOUNT_INR = ?, MANAGER_ID = ?, MANAGER_NAME = ?, CLIENT_NAME = ?, MODIFIED_BY = ?, MODIFIED_ON = ?, TYPE = ?, ACCOUNT_TYPE = ?, TOTAL = ?, CURRENCY_TYPE=?, MONTH=? , COMMENTS = ? , PROJECT_DAYS=?, GLOBAL_BENCH_DAYS=?, TOTAL_DAYS=?, RETENTION_BONUS=? WHERE ID = ?";
	/**
	 * SQL DELETE statement for this table
	 */
	protected final String			SQL_DELETE	= "DELETE FROM " + getTableName() + " WHERE ID = ?";

	
	
	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(RetentionBonusReconciliationReport dto) {}

	
	/**
	 * Inserts a new row in the retention_bonus_rec_report table.
	 */
	
	@Override
	public RetentionBonusReconciliationReportPk insert(RetentionBonusReconciliationReport dto) throws RetentionBonusReconciliationReportDaoException {
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
			stmt.setInt(index++, dto.getBonusId());
			stmt.setInt(index++, dto.getUserId());
			//stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getqBonus()));
			//stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getcBonus()));
			//stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getqAmount()));
			stmt.setString(index++, dto.getqAmountInr() == null ? null : DesEncrypterDecrypter.getInstance().encrypt(dto.getqAmountInr()));
			//stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getcAmount()));
			//stmt.setString(index++, dto.getcAmountInr() == null ? null : DesEncrypterDecrypter.getInstance().encrypt(dto.getcAmountInr()));
			
			stmt.setInt(index++, dto.getManagerId());
			stmt.setString(index++, dto.getManagerName());
			stmt.setString(index++, dto.getClientName());
			stmt.setString(index++, dto.getModifiedBy());
			stmt.setTimestamp(index++, dto.getModifiedOn() == null ? null : new java.sql.Timestamp(dto.getModifiedOn().getTime()));
			stmt.setShort(index++, dto.getType());
			stmt.setShort(index++, dto.getAccountType());
			stmt.setString(index++, dto.getTotal() == null ? null : DesEncrypterDecrypter.getInstance().encrypt(dto.getTotal()));
			stmt.setInt(index++, dto.getCurrencyType());
			stmt.setString(index++, dto.getMonth());
			stmt.setString(index++, dto.getComments());
			stmt.setInt(index++, dto.getProjectDays());
			stmt.setInt(index++, dto.getGlobalBenchDays());
			stmt.setInt(index++, dto.getTotalDays());
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getrBonus()));
			
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
			throw new RetentionBonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
	/**
	 * Updates a single row in the retention_bonus_rec_report table.
	 */
	@Override
	public void update(RetentionBonusReconciliationReportPk pk, RetentionBonusReconciliationReport dto) throws RetentionBonusReconciliationReportDaoException {
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
			stmt.setInt(index++, dto.getBonusId());
			stmt.setInt(index++, dto.getUserId());
			//stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getqBonus()));
			//stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getcBonus()));
			//stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getqAmount()));
			stmt.setString(index++, dto.getqAmountInr() == null ? null : DesEncrypterDecrypter.getInstance().encrypt(dto.getqAmountInr()));
			//stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getcAmount()));
			//stmt.setString(index++, dto.getcAmountInr() == null ? null : DesEncrypterDecrypter.getInstance().encrypt(dto.getcAmountInr()));
			
			stmt.setInt(index++, dto.getManagerId());
			stmt.setString(index++, dto.getManagerName());
			stmt.setString(index++, dto.getClientName());
			stmt.setString(index++, dto.getModifiedBy());
			stmt.setTimestamp(index++, dto.getModifiedOn() == null ? null : new java.sql.Timestamp(dto.getModifiedOn().getTime()));
			stmt.setShort(index++, dto.getType());
			stmt.setShort(index++, dto.getAccountType());
			stmt.setString(index++, dto.getTotal() == null ? null : DesEncrypterDecrypter.getInstance().encrypt(dto.getTotal()));
			stmt.setInt(index++, dto.getCurrencyType());
			stmt.setString(index++, dto.getMonth());
			stmt.setString(index++, dto.getComments());
			stmt.setInt(index++, dto.getProjectDays());
			stmt.setInt(index++, dto.getGlobalBenchDays());
			stmt.setInt(index++, dto.getTotalDays());
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getrBonus()));
			stmt.setInt(index++, pk.getId());
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new RetentionBonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
		
	}
	/**
	 * Deletes a single row in the retention_bonus_rec_report table.
	 */
	@Override
	public void delete(RetentionBonusReconciliationReportPk pk) throws RetentionBonusReconciliationReportDaoException {
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
			throw new RetentionBonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
		
	}
	/**
	 * Returns the rows from the retention_bonus_rec_report table that matches the specified primary-key value.
	 */
	@Override
	public RetentionBonusReconciliationReport findByPrimaryKey(RetentionBonusReconciliationReportPk pk) throws RetentionBonusReconciliationReportDaoException {
		return findByPrimaryKey(pk.getId());
	}
	/**
	 * Returns all rows from the retention_bonus_rec_report table that match the criteria 'ID = :id'.
	 */
	@Override
	public RetentionBonusReconciliationReport findByPrimaryKey(int id) throws RetentionBonusReconciliationReportDaoException {
		RetentionBonusReconciliationReport ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { new Integer(id) });
		return ret.length == 0 ? null : ret[0];
	}
	/**
	 * Returns all rows from the retention_bonus_rec_report table that match the criteria 'DEP_ID = :depId'.
	 */
	@Override
	public RetentionBonusReconciliationReport[] findWhereBonusIdEquals(int bonusId) throws RetentionBonusReconciliationReportDaoException {
		return findByDynamicSelect("SELECT BRR.*,U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) FROM RETENTION_BONUS_REC_REPORT BRR JOIN USERS U ON BRR.USER_ID=U.ID JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID WHERE BR_ID = ? ORDER BY CLIENT_NAME, EMP_ID", new Object[] { new Integer(bonusId) });

	}
	/**
	 * Returns all rows from the retention_bonus_rec_report table that match the criteria 'MANAGER_ID = :managerId'.
	 */
	@Override
	public RetentionBonusReconciliationReport[] findWhereBonusIdEquals(int bonusId, int managerId) throws RetentionBonusReconciliationReportDaoException {
		return findByDynamicSelect("SELECT BRR.*,U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) FROM RETENTION_BONUS_REC_REPORT BRR JOIN USERS U ON BRR.USER_ID=U.ID JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID WHERE BR_ID = ? AND MANAGER_ID = ? ORDER BY CLIENT_NAME, EMP_ID", new Object[] { new Integer(bonusId) , new Integer(managerId)});

	}
	/**
	 * Returns all rows from the retention_bonus_rec_report table that match the criteria 'USER_ID = :userId'.
	 */
	@Override
	public RetentionBonusReconciliationReport[] findWhereUserIdEquals(int userId) throws RetentionBonusReconciliationReportDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE USER_ID = ? ORDER BY USER_ID", new Object[] { new Integer(userId) });
	}
	@Override
	public RetentionBonusReconciliationReport[] findWherebonusIdEquals(int bonusId,int userId) throws RetentionBonusReconciliationReportDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE BR_ID = ? AND USER_ID = ? ORDER BY BR_ID", new Object[] { new Integer(bonusId) , new Integer(userId) });
	}
	/**
	 * Returns all rows from the retention_bonus_rec_report table that match the criteria 'MANAGER_ID = :managerId'.
	 */
	@Override
	public RetentionBonusReconciliationReport[] findWhereManagerIdEquals(int managerId) throws RetentionBonusReconciliationReportDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE MANAGER_ID = ? ORDER BY MANAGER_ID", new Object[] { new Integer(managerId) });
	}
	/**
	 * Returns all rows from the retention_bonus_rec_report table that match the criteria 'IS_DELETED = :isDeleted'.
	 */
	@Override
	public RetentionBonusReconciliationReport[] findWhereIsDeletedEquals(short isDeleted) throws RetentionBonusReconciliationReportDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE IS_DELETED = ? ORDER BY IS_DELETED", new Object[] { new Short(isDeleted) });
	}

	/**
	 * Method 'RetentionBonusReconciliationReportDaoImpl'
	 */
	public RetentionBonusReconciliationReportDaoImpl() {}

	/**
	 * Method 'RetentionBonusReconciliationReportDaoImpl'
	 * 
	 * @param userConn
	 */
	public RetentionBonusReconciliationReportDaoImpl(final java.sql.Connection userConn) {
		this.userConn = userConn;
	}

	/**
	 * Returns all rows from the retention_bonus_rec_report table that match the specified arbitrary SQL statement
	 */
	@Override
	public RetentionBonusReconciliationReport[] findByDynamicSelect(String sql, Object[] sqlParams) throws RetentionBonusReconciliationReportDaoException {
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
					_e.printStackTrace();
					logger.error("Exception: " + _e.getMessage(), _e);
					throw new RetentionBonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
	}

	/**
	 * Returns all rows from the retention_dep_perdiem_report table that match the specified arbitrary SQL statement
	 */
	@Override
	public RetentionBonusReconciliationReport[] findByDynamicWhere(String sql, Object[] sqlParams) throws RetentionBonusReconciliationReportDaoException {
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
					throw new RetentionBonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
	}

	@Override
	public List<String[]> findInternalReportData(int id) throws RetentionBonusReconciliationReportDaoException {
		// declare variables
				final boolean isConnSupplied = (userConn != null);
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				try{
					conn = isConnSupplied ? userConn : ResourceManager.getConnection();
					/*final String SQL = "SELECT U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, QUA_AMOUNT,COM_AMOUNT, CURRENCY_TYPE, MONTH, TOTAL,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO END) AS ACC_NO,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME ELSE F.PRIM_BANK_NAME END) AS BANK_NAME FROM RETENTION_BONUS_REC_REPORT DR JOIN USERS U ON DR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE BR_ID=? AND TYPE NOT IN ("
							+ RetentionBonusReconciliationModel.DELETED + "," + RetentionBonusReconciliationModel.FIXED_DELETED + ") ORDER BY CLIENT_NAME,EMP_ID";*/
					final String SQL = "SELECT U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, RETENTION_BONUS, CURRENCY_TYPE, MONTH, TOTAL,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO END) AS ACC_NO,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME ELSE F.PRIM_BANK_NAME END) AS BANK_NAME FROM RETENTION_BONUS_REC_REPORT DR JOIN USERS U ON DR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE BR_ID=? AND TYPE NOT IN ("
							+ RetentionBonusReconciliationModel.ADDED_DELETED + "," + RetentionBonusReconciliationModel.FIXED_REJECTED + "," + RetentionBonusReconciliationModel.DELETED + "," + RetentionBonusReconciliationModel.FIXED_DELETED +"," + RetentionBonusReconciliationModel.HOLD +"," + RetentionBonusReconciliationModel.FIXED_HOLD + ") ORDER BY CLIENT_NAME,EMP_ID";
					stmt = conn.prepareStatement(SQL);
					stmt.setMaxRows(maxRows);
					stmt.setObject(1, id);
					rs = stmt.executeQuery();
					List<String[]> resultList = new ArrayList<String[]>();
					Map<String, String> currencyTypes = CurrencyDaoFactory.create(conn).getAllCurrencyTypes();
					while (rs.next()){
						/*String[] row = new String[10];
						row[0] = rs.getInt(1) + "";
						row[1] = rs.getString(2);
						row[2] = rs.getString(3);
						row[3] = new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(4))));
						row[4] = new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(5))));
						String currencyName = currencyTypes.get(rs.getInt(6) + "");
						row[5] = currencyName == null ? rs.getInt(6) + "" : currencyName;
						row[6] = rs.getString(7) + ""; //month
						row[7] = new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(8))));
						
						row[8] = rs.getString(9);
						row[9] = rs.getString(10);
						resultList.add(row);*/
						String[] row = new String[9];
						row[0] = rs.getInt(1) + "";
						row[1] = rs.getString(2);
						row[2] = rs.getString(3);
						row[3] = new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(4))));
						String currencyName = currencyTypes.get(rs.getInt(5) + "");
						row[4] = currencyName == null ? rs.getInt(5) + "" : currencyName;
						row[5] = rs.getString(6) + ""; //month
						row[6] = new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(7))));
						
						row[7] = rs.getString(8);
						row[8] = rs.getString(9);
						resultList.add(row);
					}
					return resultList;
				} catch (Exception _e){
					_e.printStackTrace();
					logger.error("Exception: " + _e.getMessage(), _e);
					throw new RetentionBonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
	}

	@Override
	public List<RetentionBonusReportBean> findBankReport(int id, int flag) throws RetentionBonusReconciliationReportDaoException {
		// declare variables
				final boolean isConnSupplied = (userConn != null);
				
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				try{
					conn = isConnSupplied ? userConn : ResourceManager.getConnection();
					final String ACC_NO_SQL = ", (CASE ACCOUNT_TYPE WHEN 0 THEN (CASE WHEN F.SEC_BANK_ACC_NO IS NULL OR F.SEC_BANK_ACC_NO ='' THEN F.PRIM_BANK_ACC_NO ELSE F.SEC_BANK_ACC_NO END) WHEN 1 THEN F.PRIM_BANK_ACC_NO WHEN 2 THEN F.SEC_BANK_ACC_NO END) AS ACC_NO";
					final String BANK_NAME_SQL = ", (CASE ACCOUNT_TYPE WHEN 0 THEN (CASE WHEN F.SEC_BANK_ACC_NO IS NULL OR F.SEC_BANK_ACC_NO ='' THEN F.PRIM_BANK_NAME ELSE F.SEC_BANK_NAME END) WHEN 1 THEN F.PRIM_BANK_NAME WHEN 2 THEN F.SEC_BANK_NAME END) AS BANK_NAME";
					final String SQL = "SELECT EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME,QUA_AMOUNT, COM_AMOUNT ,TOTAL" + ACC_NO_SQL + BANK_NAME_SQL + " FROM RETENTION_BONUS_REC_REPORT BR JOIN USERS U ON BR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE BR_ID=? AND TYPE NOT IN ("
							+ RetentionBonusReconciliationModel.DELETED + "," + RetentionBonusReconciliationModel.FIXED_DELETED + "," + RetentionBonusReconciliationModel.FIXED_HOLD + "," + RetentionBonusReconciliationModel.FIXED_REJECTED + "," + RetentionBonusReconciliationModel.HOLD + "," + RetentionBonusReconciliationModel.REJECTED + "," + RetentionBonusReconciliationModel.ADDED_DELETED + ") ORDER BY EMP_ID";
					System.out.println(SQL);
					stmt = conn.prepareStatement(SQL);
					stmt.setMaxRows(maxRows);
					stmt.setObject(1, id);
					rs = stmt.executeQuery();
					List<RetentionBonusReportBean> resultList = new ArrayList<RetentionBonusReportBean>();
					Map<String, RetentionBonusReportBean> map = new HashMap<String, RetentionBonusReportBean>();
					while (rs.next()){
						String empId = rs.getInt(1) + "";
						String bankName = rs.getString(7);
						if ((flag == 1 && bankName != null && bankName.toUpperCase().contains("ICICI")) || (flag != 1 && (bankName == null || !bankName.toUpperCase().contains("ICICI")))){
							if (map.containsKey(empId)){
								RetentionBonusReportBean record = map.get(empId);
								record.setTotal((record.getTotal()) + (int) Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(3)))+ (int) Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(4)))) ;
								map.put(empId, record);
							} else{
								map.put(empId, new RetentionBonusReportBean(empId, rs.getString(6), Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(5))), rs.getString(7), rs.getString(2)));
							}
						}
					}
					for (Map.Entry<String, RetentionBonusReportBean> entry : map.entrySet()){
						RetentionBonusReportBean p = entry.getValue();
						if (p.getTotal() > 0) resultList.add(p);
					}
					Collections.sort(resultList);
					return resultList;
				} catch (Exception _e){
					logger.error("Exception: " + _e.getMessage(), _e);
					throw new RetentionBonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
			
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
	return "RETENTION_BONUS_REC_REPORT";
	}
	
	/**
	 * Fetches a single row from the result set
	 */
	protected RetentionBonusReconciliationReport fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			RetentionBonusReconciliationReport dto = new RetentionBonusReconciliationReport();
			populateDto(dto, rs);
			return dto;
		} else{
			return null;
		}
	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected RetentionBonusReconciliationReport[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<RetentionBonusReconciliationReport> resultList = new ArrayList<RetentionBonusReconciliationReport>();
		while (rs.next()){
			RetentionBonusReconciliationReport dto = new RetentionBonusReconciliationReport();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		RetentionBonusReconciliationReport ret[] = new RetentionBonusReconciliationReport[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(RetentionBonusReconciliationReport dto, ResultSet rs) throws SQLException {
		int index = 1;
		dto.setId(rs.getInt(index++));//id
		dto.setBonusId(rs.getInt(index++));//bonus id
		dto.setUserId(rs.getInt(index++));// user id
		/*try{
			int columnid = index++;
			dto.setqBonus(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e1){}
		try{
			int columnid = index++;
			dto.setcBonus(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e1){}
		try{
			int columnid = index++;
			dto.setqAmount(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e1){}*/
		try{
			int columnid = index++;
			dto.setqAmountInr(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e1){
			e1.printStackTrace();
		}//qAmountInr
		
		/*try{
			int columnid = index++;
			dto.setcAmount(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e1){}
		try{
			int columnid = index++;
			dto.setcAmountInr(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e1){}*/
		dto.setManagerId(rs.getInt(index++));
		dto.setManagerName(rs.getString(index++));
		dto.setClientName(rs.getString(index++));
		dto.setModifiedBy(rs.getString(index++));
		dto.setModifiedOn(rs.getTimestamp(index++));
		dto.setType(rs.getShort(index++));
		dto.setAccountType(rs.getShort(index++));
		try{
			int columnid = index++;
			dto.setTotal(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e1){}
		dto.setCurrencyType(rs.getInt(index++));
		dto.setMonth(rs.getString(index++));
		dto.setComments(rs.getString(index++));
		dto.setProjectDays(rs.getInt(index++));
		dto.setGlobalBenchDays(rs.getInt(index++));
		dto.setTotalDays(rs.getInt(index++));
		try{
			int columnid = index++;
			dto.setrBonus(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e1){
			e1.printStackTrace();
		}
		
		/*try{
			dto.setEmpId(rs.getObject(index++).toString());
			dto.setEmployeeName(rs.getString(index++));
		} catch (Exception e){}*/
	}

	

}
