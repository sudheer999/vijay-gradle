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

import com.dikshatech.beans.BonusReportBean;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.dao.BonusReconciliationReportDao;
import com.dikshatech.portal.dto.BonusReconciliationReport;
import com.dikshatech.portal.dto.BonusReconciliationReportPk;
import com.dikshatech.portal.exceptions.BonusReconciliationReportDaoException;
import com.dikshatech.portal.factory.CurrencyDaoFactory;
import com.dikshatech.portal.models.ReconciliationModel;


public class BonusReconciliationReportDaoImpl extends AbstractDAO implements BonusReconciliationReportDao {
	/**
	 * The factory class for this DAO has two versions of the create() method - one that
	 * takes no arguments and one that takes a Connection argument. If the Connection version
	 * is chosen then the connection will be stored in this attribute and will be used by all
	 * calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection	userConn;
	protected static final Logger	logger		= Logger.getLogger(BonusReconciliationReportDaoImpl.class);
	/**
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String			SQL_SELECT	= "SELECT ID, BR_ID, USER_ID, QUATERELY_BONUS, COMPANY_BONUS,QUA_AMOUNT,QUA_AMOUNT_INR,COM_AMOUNT, COM_AMOUNT_INR, MANAGER_ID, MANAGER_NAME, CLIENT_NAME, MODIFIED_BY, MODIFIED_ON, TYPE,ACCOUNT_TYPE ,TOTAL,CURRENCY_TYPE, MONTH, COMMENTS, PROJECT_DAYS, GLOBAL_BENCH_DAYS, TOTAL_DAYS, SALARY_CYCLE FROM " + getTableName() + "";
	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int					maxRows;
	/**
	 * SQL INSERT statement for this table
	 */
	protected final String			SQL_INSERT	= "INSERT INTO " + getTableName() + " (ID, BR_ID, USER_ID, QUATERELY_BONUS, COMPANY_BONUS, QUA_AMOUNT, QUA_AMOUNT_INR, COM_AMOUNT, COM_AMOUNT_INR, MANAGER_ID, MANAGER_NAME, CLIENT_NAME, MODIFIED_BY, MODIFIED_ON, TYPE,ACCOUNT_TYPE ,TOTAL,CURRENCY_TYPE, MONTH, COMMENTS, PROJECT_DAYS, GLOBAL_BENCH_DAYS, TOTAL_DAYS,SALARY_CYCLE) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String			SQL_UPDATE	= "UPDATE " + getTableName() + " SET ID = ?, BR_ID = ?, USER_ID = ?, QUATERELY_BONUS = ?, COMPANY_BONUS = ?, QUA_AMOUNT = ?, QUA_AMOUNT_INR = ?, COM_AMOUNT = ?, COM_AMOUNT_INR = ?,  MANAGER_ID = ?, MANAGER_NAME = ?, CLIENT_NAME = ?, MODIFIED_BY = ?, MODIFIED_ON = ?, TYPE = ?, ACCOUNT_TYPE = ?, TOTAL = ?, CURRENCY_TYPE=?, MONTH=? , COMMENTS = ? , PROJECT_DAYS=?, GLOBAL_BENCH_DAYS=?, TOTAL_DAYS=?,SALARY_CYCLE=? WHERE ID = ?";
	/**
	 * SQL DELETE statement for this table
	 */
	protected final String			SQL_DELETE	= "DELETE FROM " + getTableName() + " WHERE ID = ?";

	
	
	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(BonusReconciliationReport dto) {}

	
	/**
	 * Inserts a new row in the bonus_rec_report table.
	 */
	
	@Override
	public BonusReconciliationReportPk insert(BonusReconciliationReport dto) throws BonusReconciliationReportDaoException {
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
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getqBonus()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getcBonus()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getqAmount()));
			stmt.setString(index++, dto.getqAmountInr() == null ? null : DesEncrypterDecrypter.getInstance().encrypt(dto.getqAmountInr()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getcAmount()));
			stmt.setString(index++, dto.getcAmountInr() == null ? null : DesEncrypterDecrypter.getInstance().encrypt(dto.getcAmountInr()));
			
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
		    stmt.setString(index++, dto.getSalaryCycle());
			
			
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
			throw new BonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
	/**
	 * Updates a single row in the bonus_rec_report table.
	 */
	@Override
	public void update(BonusReconciliationReportPk pk, BonusReconciliationReport dto) throws BonusReconciliationReportDaoException {
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
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getqBonus()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getcBonus()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getqAmount()));
			stmt.setString(index++, dto.getqAmountInr() == null ? null : DesEncrypterDecrypter.getInstance().encrypt(dto.getqAmountInr()));
			stmt.setString(index++, DesEncrypterDecrypter.getInstance().encrypt(dto.getcAmount()));
			stmt.setString(index++, dto.getcAmountInr() == null ? null : DesEncrypterDecrypter.getInstance().encrypt(dto.getcAmountInr()));
			
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
			stmt.setString(index++, dto.getSalaryCycle());
			stmt.setInt(index++, pk.getId());
			
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()){
				logger.debug(rows + " rows affected (" + (t2 - t1) + " ms)");
			}
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new BonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
		
	}
	/**
	 * Deletes a single row in the bonus_rec_report table.
	 */
	@Override
	public void delete(BonusReconciliationReportPk pk) throws BonusReconciliationReportDaoException {
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
			throw new BonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
		
	}
	/**
	 * Returns the rows from the bonus_rec_report table that matches the specified primary-key value.
	 */
	@Override
	public BonusReconciliationReport findByPrimaryKey(BonusReconciliationReportPk pk) throws BonusReconciliationReportDaoException {
		return findByPrimaryKey(pk.getId());
	}
	/**
	 * Returns all rows from the bonus_rec_report table that match the criteria 'ID = :id'.
	 */
	@Override
	public BonusReconciliationReport findByPrimaryKey(int id) throws BonusReconciliationReportDaoException {
		BonusReconciliationReport ret[] = findByDynamicSelect(SQL_SELECT + " WHERE ID = ?", new Object[] { new Integer(id) });
		return ret.length == 0 ? null : ret[0];
	}
	/**
	 * Returns all rows from the bonus_rec_report table that match the criteria 'DEP_ID = :depId'.
	 */
	@Override
	public BonusReconciliationReport[] findWhereBonusIdEquals(int bonusId) throws BonusReconciliationReportDaoException {
		return findByDynamicSelect("SELECT BRR.*,U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) FROM BONUS_REC_REPORT BRR JOIN USERS U ON BRR.USER_ID=U.ID JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID WHERE BR_ID = ? ORDER BY U.EMP_ID,CLIENT_NAME", new Object[] { new Integer(bonusId) });

	}
	
	@Override
	public BonusReconciliationReport[] findWhereBonusIdEqualshdfc(int bonusId)
			throws BonusReconciliationReportDaoException {
//		return findByDynamicSelect("SELECT BRR.*,U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME),(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO END) AS ACC_NO,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME ELSE F.PRIM_BANK_NAME END) AS BANK_NAME FROM BONUS_REC_REPORT BRR JOIN USERS U ON BRR.USER_ID=U.ID JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE BR_ID =? AND F.PRIM_BANK_NAME LIKE'%HDFC%' ORDER BY CLIENT_NAME, EMP_ID", new Object[] { new Integer(bonusId) });
		return findByDynamicSelect1("SELECT BRR.*,U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME),(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO END) AS ACC_NO,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME ELSE F.PRIM_BANK_NAME END) AS BANK_NAME FROM BONUS_REC_REPORT BRR JOIN USERS U ON BRR.USER_ID=U.ID JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE BR_ID =? AND F.PRIM_BANK_NAME LIKE'%HDFC%' AND (BRR.PAID IS null || BRR.PAID= '') ORDER BY U.EMP_ID,CLIENT_NAME",new Object[] { new Integer(bonusId) });
		
		// TODO Auto-generated method stub
		
	}


	@Override
	public BonusReconciliationReport[] findWhereBonusIdEqualsnonHdfc(int bonusId)
			throws BonusReconciliationReportDaoException {
		// TODO Auto-generated method stub
	//	return findByDynamicSelect("SELECT BRR.*,U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME),(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO END) AS ACC_NO,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME ELSE F.PRIM_BANK_NAME END) AS BANK_NAME FROM BONUS_REC_REPORT BRR JOIN USERS U ON BRR.USER_ID=U.ID JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE BR_ID =? AND F.PRIM_BANK_NAME NOT LIKE'%HDFC%' ORDER BY CLIENT_NAME, EMP_ID", new Object[] { new Integer(bonusId) });
		return findByDynamicSelect1("SELECT BRR.*,U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME),(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO END) AS ACC_NO,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME ELSE F.PRIM_BANK_NAME END) AS BANK_NAME FROM BONUS_REC_REPORT BRR JOIN USERS U ON BRR.USER_ID=U.ID JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE BR_ID =? AND F.PRIM_BANK_NAME NOT LIKE'%HDFC%' AND (BRR.PAID IS null || BRR.PAID= '') ORDER BY U.EMP_ID,CLIENT_NAME",new Object[] { new Integer(bonusId) });
		
	}

	/**
	 * Returns all rows from the bonus_rec_report table that match the criteria 'MANAGER_ID = :managerId'.
	 */
	@Override
	public BonusReconciliationReport[] findWhereBonusIdEquals(int bonusId, int managerId) throws BonusReconciliationReportDaoException {
		return findByDynamicSelect("SELECT BRR.*,U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) FROM BONUS_REC_REPORT BRR JOIN USERS U ON BRR.USER_ID=U.ID JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID WHERE BR_ID = ? AND MANAGER_ID = ? ORDER BY CLIENT_NAME, EMP_ID", new Object[] { new Integer(bonusId) , new Integer(managerId)});

	}
	/**
	 * Returns all rows from the bonus_rec_report table that match the criteria 'USER_ID = :userId'.
	 */
	@Override
	public BonusReconciliationReport[] findWhereUserIdEquals(int userId) throws BonusReconciliationReportDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE USER_ID = ? ORDER BY USER_ID", new Object[] { new Integer(userId) });
	}
	@Override
	public BonusReconciliationReport[] findWherebonusIdAndUserIdEquals(int bonusId, int userId) throws BonusReconciliationReportDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE USER_ID = ? ORDER BY USER_ID", new Object[] { new Integer(bonusId),new Integer(userId) });
	}
	/**
	 * Returns all rows from the bonus_rec_report table that match the criteria 'MANAGER_ID = :managerId'.
	 */
	@Override
	public BonusReconciliationReport[] findWhereManagerIdEquals(int managerId) throws BonusReconciliationReportDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE MANAGER_ID = ? ORDER BY MANAGER_ID", new Object[] { new Integer(managerId) });
	}
	/**
	 * Returns all rows from the bonus_rec_report table that match the criteria 'IS_DELETED = :isDeleted'.
	 */
	@Override
	public BonusReconciliationReport[] findWhereIsDeletedEquals(short isDeleted) throws BonusReconciliationReportDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE IS_DELETED = ? ORDER BY IS_DELETED", new Object[] { new Short(isDeleted) });
	}

	/**
	 * Method 'DepPerdiemReportDaoImpl'
	 */
	public BonusReconciliationReportDaoImpl() {}

	/**
	 * Method 'DepPerdiemReportDaoImpl'
	 * 
	 * @param userConn
	 */
	public BonusReconciliationReportDaoImpl(final java.sql.Connection userConn) {
		this.userConn = userConn;
	}

	/**
	 * Returns all rows from the bonus_rec_report table that match the specified arbitrary SQL statement
	 */
	
	public BonusReconciliationReport[] findByDynamicSelect1(String sql, Object[] sqlParams) throws BonusReconciliationReportDaoException {
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
					return fetchMultiResults1(rs);
				} catch (Exception _e){
					logger.error("Exception: " + _e.getMessage(), _e);
					throw new BonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
	}
	
	
	
	/**
	 * Returns all rows from the bonus_rec_report table that match the specified arbitrary SQL statement
	 */
	@Override
	public BonusReconciliationReport[] findByDynamicSelect(String sql, Object[] sqlParams) throws BonusReconciliationReportDaoException {
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
						stmt.setObject(i + 1, sqlParams[i++]);
					}
					rs = stmt.executeQuery();
					// fetch the results
					return fetchMultiResults(rs);
				} catch (Exception _e){
					logger.error("Exception: " + _e.getMessage(), _e);
					throw new BonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
	}

	/**
	 * Returns all rows from the dep_perdiem_report table that match the specified arbitrary SQL statement
	 */
	@Override
	public BonusReconciliationReport[] findByDynamicWhere(String sql, Object[] sqlParams) throws BonusReconciliationReportDaoException {
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
					throw new BonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
	}  
	@Override
	public BonusReconciliationReport[] findAllPaidAndUnpaid(int bonusId,String flag) throws BonusReconciliationReportDaoException {
	
	        // declare variables
	        final boolean isConnSupplied = (userConn != null);
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        
	        try{
	            // get the user-specified connection or get a connection from the ResourceManager
	            conn = isConnSupplied ? userConn : ResourceManager.getConnection();
	            

	            // construct the SQL statement
	            String SQL_HDFC="SELECT BRR.*,U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME),(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO END) AS ACC_NO,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME ELSE F.PRIM_BANK_NAME END) AS BANK_NAME FROM BONUS_REC_REPORT BRR JOIN USERS U ON BRR.USER_ID=U.ID JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE BR_ID = ? AND F.PRIM_BANK_NAME LIKE '%HDFC%' ORDER BY U.EMP_ID,CLIENT_NAME";
	            //String SQL_HDFC="SELECT SRR.*, CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, U.EMP_ID " + "FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID WHERE SR_ID=? ORDER BY SRR.MODIFIED_ON DESC,SRR.ID ASC";
	            //String SQL_NON_HDFC="SELECT SRR.*, CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, U.EMP_ID " + "FROM SALARY_RECONCILIATION_REPORT SRR JOIN USERS U ON SRR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID WHERE SR_ID=? ORDER BY SRR.MODIFIED_ON DESC,SRR.ID ASC";
	           String SQL_NON_HDFC="SELECT BRR.*,U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME),(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO END) AS ACC_NO,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME ELSE F.PRIM_BANK_NAME END) AS BANK_NAME FROM BONUS_REC_REPORT BRR JOIN USERS U ON BRR.USER_ID=U.ID JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE BR_ID = ? AND F.PRIM_BANK_NAME NOT LIKE '%HDFC%' ORDER BY U.EMP_ID,CLIENT_NAME";

	            // prepare statement
	            if(flag.equals("HDFC")){
	                stmt = conn.prepareStatement(SQL_HDFC);
	            }else{
	                stmt = conn.prepareStatement(SQL_NON_HDFC);
	            }
	        
	            stmt.setMaxRows(maxRows);
	            stmt.setObject(1, bonusId);
	            // bind parameters
	            
	            rs = stmt.executeQuery();
	            // fetch the results
	            Collection<BonusReconciliationReport> resultList = new ArrayList<BonusReconciliationReport>();
	            while (rs.next()){
	                BonusReconciliationReport dto=new BonusReconciliationReport();
	                populateDto1(dto, rs);
	                resultList.add(dto);
	            }
	            BonusReconciliationReport ret[] = new BonusReconciliationReport[resultList.size()];
	            resultList.toArray(ret);
	            return ret;
	        } catch (Exception _e){
	            logger.error("Exception: " + _e.getMessage(), _e);
	            throw new BonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
	        } finally{
	            ResourceManager.close(rs);
	            ResourceManager.close(stmt);
	            if (!isConnSupplied){
	                ResourceManager.close(conn);
	            }
	        }
	    }
	@Override
	public String updateAllReceivedPay(int id, ArrayList<Integer> bbr_Id,
			String flag1) throws BonusReconciliationReportDaoException {
		 // declare variables
        final boolean isConnSupplied = (userConn != null);
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
  conn = isConnSupplied ? userConn : ResourceManager.getConnection();
              


              int retval = bbr_Id.size();
              int count=1;
              StringBuilder builder = new StringBuilder();
              for( int i = 0 ; i < retval; i++ ) {
                  if(count<retval){
                      builder.append("?,");
                      count++;
                  }else{
                      builder.append("?");
                  }
              
              }
              String sql="UPDATE BONUS_REC_REPORT SET PAID=? WHERE BR_ID=? AND ID IN("+builder+") ";
              int i=3;
             
              stmt = conn.prepareStatement(sql);
              stmt.setObject(1, "paid");
              stmt.setObject(2, id);
              for(i=3;i<bbr_Id.size()+3;i++){
                  stmt.setObject(i,bbr_Id.get(i-3));    
              }
              int affectedrow=stmt.executeUpdate();
              return "Successfully updated";
		
	
        }
        catch (Exception _e){
            logger.error("Exception: " + _e.getMessage(), _e);
            throw new BonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
        } finally{
            ResourceManager.close(rs);
            ResourceManager.close(stmt);
            if (!isConnSupplied){
                ResourceManager.close(conn);
            }
        }
       
	}

	
	
	
	
	
	@Override// to get only HDFC values     
	
	public List<String[]> findInternalReportDataHDFC(int id,ArrayList<Integer> arraylist) throws BonusReconciliationReportDaoException{
	
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
	
		
		try{
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			
			int retval = arraylist.size();
			int count=1;
			StringBuilder builder= new StringBuilder();
			for(int i=0;i<arraylist.size();i++){
				if(count<retval){
					builder.append("?,");
					count++;
				}
				else{
					builder.append("?");
				}
			}
			try{
	            String sql="UPDATE BONUS_REC_REPORT SET PAID=? WHERE BR_ID=? AND ID IN("+builder+") ";
	            int i=3;
	            
	            stmt = conn.prepareStatement(sql);
	            stmt.setObject(1, "paid");
	            stmt.setObject(2, id);
	            for(i=3;i<arraylist.size()+3;i++){
	                stmt.setObject(i,arraylist.get(i-3));    
	            }
	            int affectedrow=stmt.executeUpdate();
	            logger.debug("PAID STATUS UPDATED IN BONUS_REC_REPORT ROW AFFECTED"+affectedrow);
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }
			
			
		//	final String SQL = "SELECT U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, QUA_AMOUNT,COM_AMOUNT, CURRENCY_TYPE, MONTH, TOTAL,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO END) AS ACC_NO,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME ELSE F.PRIM_BANK_NAME END) AS BANK_NAME FROM BONUS_REC_REPORT DR JOIN USERS U ON DR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE BR_ID=? AND DR.ID IN ("+builder+") AND F.PRIM_BANK_NAME LIKE'%HDFC%' AND TYPE NOT IN ("
					//		+ ReconciliationModel.DELETED + "," + ReconciliationModel.FIXED_DELETED +"," + ReconciliationModel.HOLD +"," + ReconciliationModel.FIXED_HOLD + ") ORDER BY CLIENT_NAME,EMP_ID";
			
			final String SQL = "SELECT U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, QUA_AMOUNT,COM_AMOUNT, CURRENCY_TYPE, MONTH, TOTAL,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO END) AS ACC_NO,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME ELSE F.PRIM_BANK_NAME END) AS BANK_NAME,PF.OFFICAL_EMAIL_ID,BD.IFCI_NUMBER FROM BONUS_REC_REPORT DR JOIN USERS U ON DR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID LEFT JOIN BANK_DETAILS BD ON BD.BANK_NAME = F.PRIM_BANK_NAME WHERE BR_ID=? AND DR.ID IN ("+builder+") AND F.PRIM_BANK_NAME LIKE'%HDFC%' AND TYPE NOT IN ("
					+ ReconciliationModel.DELETED + "," + ReconciliationModel.FIXED_DELETED +"," + ReconciliationModel.HOLD +"," + ReconciliationModel.FIXED_HOLD + ") ORDER BY CLIENT_NAME,EMP_ID";
			
					stmt = conn.prepareStatement(SQL);
				
				
					stmt.setMaxRows(maxRows);
					stmt.setObject(1, id);
			
			/*final String SQL = "SELECT U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, QUA_AMOUNT,COM_AMOUNT, CURRENCY_TYPE, MONTH, TOTAL,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO END) AS ACC_NO,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME ELSE F.PRIM_BANK_NAME END) AS BANK_NAME FROM BONUS_REC_REPORT DR JOIN USERS U ON DR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE F.PRIM_BANK_NAME LIKE '%HDFC%' AND BR_ID=? AND TYPE NOT IN ("
					+ ReconciliationModel.DELETED + "," + ReconciliationModel.FIXED_DELETED +"," + ReconciliationModel.HOLD +"," + ReconciliationModel.FIXED_HOLD + ") ORDER BY CLIENT_NAME,EMP_ID";
					
			stmt = conn.prepareStatement(SQL);
			*/
			
			stmt.setMaxRows(maxRows);
			int i=2;
			
			stmt.setObject(1, id);
			
			for(i=2;i<arraylist.size()+2;i++){
				stmt.setObject(i, arraylist.get(i-2));
				
			}
			
			rs = stmt.executeQuery();
			List<String[]> resultList = new ArrayList<String[]>();
			Map<String, String> currencyTypes = CurrencyDaoFactory.create(conn).getAllCurrencyTypes();
			while (rs.next()){
				String[] row = new String[12];
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
	//			row[9] = rs.getString(10);
				row[9] = PortalUtility.getTodayDate();
				row[10] = rs.getString(11);//email
				row[11] = rs.getString(12);// ifci
				resultList.add(row);
			}
			return resultList;
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			throw new BonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
	}
	//to get non HDFC values
	
	public List<String[]> findInternalReportDataNONHDFC(int id, ArrayList<Integer> arraylist) throws BonusReconciliationReportDaoException {
		// declare variables
				final boolean isConnSupplied = (userConn != null);
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				try{
					conn = isConnSupplied ? userConn : ResourceManager.getConnection();
					
					int retval = arraylist.size(); 
					int count=1;
					StringBuilder builder= new StringBuilder();
					for(int i=0;i<arraylist.size();i++){  
						if(count<retval){
							builder.append("?,");
							count++;
						}
						else{
							builder.append("?");
						}
					}
					try{
			            String sql="UPDATE BONUS_REC_REPORT SET PAID=? WHERE BR_ID=? AND ID IN("+builder+") ";
			            int i=3;
			           
			            stmt = conn.prepareStatement(sql);
			            stmt.setObject(1, "paid");
			            stmt.setObject(2, id);
			            for(i=3;i<arraylist.size()+3;i++){
			                stmt.setObject(i,arraylist.get(i-3));    
			            }
			            int affectedrow=stmt.executeUpdate();
			            logger.debug("PAID STATUS UPDATED IN BONUS_REC_REPORT ROW AFFECTED"+affectedrow);
			            
			        }catch(SQLException e){
			            e.printStackTrace();
			        }
					
					/*final String SQLS = "SELECT U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, QUA_AMOUNT,COM_AMOUNT, CURRENCY_TYPE, MONTH, TOTAL,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO END) AS ACC_NO,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME ELSE F.PRIM_BANK_NAME END) AS BANK_NAME FROM BONUS_REC_REPORT DR JOIN USERS U ON DR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE BR_ID=? AND DR.ID IN ("+builder+") AND F.PRIM_BANK_NAME NOT LIKE '%HDFC%' AND TYPE NOT IN ("
							+ ReconciliationModel.DELETED + "," + ReconciliationModel.FIXED_DELETED +"," + ReconciliationModel.HOLD +"," + ReconciliationModel.FIXED_HOLD + ") ORDER BY CLIENT_NAME,EMP_ID";
					*/
					
					final String SQLS = "SELECT U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, QUA_AMOUNT,COM_AMOUNT, CURRENCY_TYPE, MONTH, TOTAL,(CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_ACC_NO WHEN 1 THEN F.PRIM_BANK_ACC_NO WHEN 2 THEN F.SEC_BANK_ACC_NO END) AS BANK_ACC_NO,(CASE ACCOUNT_TYPE WHEN 0 THEN F.PRIM_BANK_NAME WHEN 1 THEN F.PRIM_BANK_NAME WHEN 2 THEN F.SEC_BANK_NAME END) AS BANK_NAME,PF.OFFICAL_EMAIL_ID,F.PRIMARY_IFSC_CODE FROM BONUS_REC_REPORT DR JOIN USERS U ON DR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE BR_ID=? AND DR.ID IN ("+builder+") AND F.PRIM_BANK_NAME NOT LIKE'%HDFC%' AND TYPE NOT IN ("
							+ ReconciliationModel.DELETED + "," + ReconciliationModel.FIXED_DELETED +"," + ReconciliationModel.HOLD +"," + ReconciliationModel.FIXED_HOLD + ") ORDER BY CLIENT_NAME,EMP_ID";
			
					
					
					
					
					stmt = conn.prepareStatement(SQLS);
					
					
					stmt.setMaxRows(maxRows);
						int i=1;
					
					stmt.setObject(1, id);
					
					for(i=2;i<arraylist.size()+2;i++){
						stmt.setObject(i, arraylist.get(i-2));
				
					}
					rs = stmt.executeQuery();
					List<String[]> resultList = new ArrayList<String[]>();
					Map<String, String> currencyTypes = CurrencyDaoFactory.create(conn).getAllCurrencyTypes();
					while (rs.next()){
						String[] row = new String[13];
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
						row[10] = PortalUtility.getTodayDate();
						row[11] = rs.getString(11);//email
						row[12] = rs.getString(12);//ifci
						
						resultList.add(row);
					}
					return resultList;
				} catch (Exception _e){
					logger.error("Exception: " + _e.getMessage(), _e);
					throw new BonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
	}
   
	 
	@Override
	public List<String[]> findInternalReportData(int id) throws BonusReconciliationReportDaoException {
		// declare variables
				final boolean isConnSupplied = (userConn != null);
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				try{
					conn = isConnSupplied ? userConn : ResourceManager.getConnection();
					final String SQLA = "SELECT U.EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME, CLIENT_NAME, QUA_AMOUNT,COM_AMOUNT, CURRENCY_TYPE, MONTH, TOTAL,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO END) AS ACC_NO,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME ELSE F.PRIM_BANK_NAME END) AS BANK_NAME FROM BONUS_REC_REPORT DR JOIN USERS U ON DR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE BR_ID=? AND TYPE NOT IN ("
							+ ReconciliationModel.DELETED + "," + ReconciliationModel.FIXED_DELETED +"," + ReconciliationModel.HOLD +"," + ReconciliationModel.FIXED_HOLD + ") ORDER BY CLIENT_NAME,EMP_ID";
					stmt = conn.prepareStatement(SQLA);
					stmt.setMaxRows(maxRows);
					stmt.setObject(1, id);
					rs = stmt.executeQuery();
					List<String[]> resultList = new ArrayList<String[]>();
					Map<String, String> currencyTypes = CurrencyDaoFactory.create(conn).getAllCurrencyTypes();
					while (rs.next()){
						String[] row = new String[10];
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
						resultList.add(row);
					}
					return resultList;
				} catch (Exception _e){
					logger.error("Exception: " + _e.getMessage(), _e);
					throw new BonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
	}
	

	@Override
	public List<BonusReportBean> findBankReport(int id, int flag) throws BonusReconciliationReportDaoException {
		// declare variables
				final boolean isConnSupplied = (userConn != null);
				
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				try{
					conn = isConnSupplied ? userConn : ResourceManager.getConnection();
					final String ACC_NO_SQL = ", (CASE ACCOUNT_TYPE WHEN 0 THEN (CASE WHEN F.SEC_BANK_ACC_NO IS NULL OR F.SEC_BANK_ACC_NO ='' THEN F.PRIM_BANK_ACC_NO ELSE F.SEC_BANK_ACC_NO END) WHEN 1 THEN F.PRIM_BANK_ACC_NO WHEN 2 THEN F.SEC_BANK_ACC_NO END) AS ACC_NO";
					final String BANK_NAME_SQL = ", (CASE ACCOUNT_TYPE WHEN 0 THEN (CASE WHEN F.SEC_BANK_ACC_NO IS NULL OR F.SEC_BANK_ACC_NO ='' THEN F.PRIM_BANK_NAME ELSE F.SEC_BANK_NAME END) WHEN 1 THEN F.PRIM_BANK_NAME WHEN 2 THEN F.SEC_BANK_NAME END) AS BANK_NAME";
					final String SQL = "SELECT EMP_ID,CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME,QUA_AMOUNT, COM_AMOUNT ,TOTAL" + ACC_NO_SQL + BANK_NAME_SQL + " FROM BONUS_REC_REPORT BR JOIN USERS U ON BR.USER_ID = U.ID JOIN PROFILE_INFO PF ON PF.ID=U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID=U.FINANCE_ID WHERE BR_ID=? AND TYPE NOT IN ("
							+ ReconciliationModel.DELETED + "," + ReconciliationModel.FIXED_DELETED + "," + ReconciliationModel.FIXED_HOLD + "," + ReconciliationModel.FIXED_REJECTED + "," + ReconciliationModel.HOLD + "," + ReconciliationModel.REJECTED + "," + ReconciliationModel.ADDED_DELETED + ") ORDER BY EMP_ID";
					//System.out.println(SQL);
					stmt = conn.prepareStatement(SQL);
					stmt.setMaxRows(maxRows);
					stmt.setObject(1, id);
					rs = stmt.executeQuery();
					List<BonusReportBean> resultList = new ArrayList<BonusReportBean>();
					Map<String, BonusReportBean> map = new HashMap<String, BonusReportBean>();
					while (rs.next()){
						String empId = rs.getInt(1) + "";
						String bankName = rs.getString(7);
						if ((flag == 1 && bankName != null && bankName.toUpperCase().contains("ICICI")) || (flag != 1 && (bankName == null || !bankName.toUpperCase().contains("ICICI")))){
							if (map.containsKey(empId)){
								BonusReportBean record = map.get(empId);
								record.setTotal((record.getTotal()) + (int) Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(3)))+ (int) Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(4)))) ;
								map.put(empId, record);
							} else{
								map.put(empId, new BonusReportBean(empId, rs.getString(6), Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(5))), rs.getString(7), rs.getString(2)));
							}
						}
					}
					for (Map.Entry<String, BonusReportBean> entry : map.entrySet()){
						BonusReportBean p = entry.getValue();
						if (p.getTotal() > 0) resultList.add(p);
					}
					Collections.sort(resultList);
					return resultList;
				} catch (Exception _e){
					logger.error("Exception: " + _e.getMessage(), _e);
					throw new BonusReconciliationReportDaoException("Exception: " + _e.getMessage(), _e);
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
	return "BONUS_REC_REPORT";
	}
	
	/**
	 * Fetches a single row from the result set
	 */
	protected BonusReconciliationReport fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()){
			BonusReconciliationReport dto = new BonusReconciliationReport();
			populateDto(dto, rs);
			return dto;
		} else{
			return null;
		}
	}
	
	/**
	 * Fetches multiple rows from the result set for HDFC or NON_HDFC
	 */
	protected BonusReconciliationReport[] fetchMultiResults1(ResultSet rs) throws SQLException {
		Collection<BonusReconciliationReport> resultList = new ArrayList<BonusReconciliationReport>();
		while (rs.next()){
			BonusReconciliationReport dto = new BonusReconciliationReport();
			populateDto1(dto, rs);
			resultList.add(dto);
		}
		BonusReconciliationReport ret[] = new BonusReconciliationReport[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}


	/**
	 * Fetches multiple rows from the result set
	 */
	protected BonusReconciliationReport[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<BonusReconciliationReport> resultList = new ArrayList<BonusReconciliationReport>();
		while (rs.next()){
			BonusReconciliationReport dto = new BonusReconciliationReport();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		BonusReconciliationReport ret[] = new BonusReconciliationReport[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}
	/**
	 * Populates a DTO with data from a ResultSet FOR HDFC OR NON_HDFC
	 * 
	 */
	protected void populateDto1(BonusReconciliationReport dto, ResultSet rs) throws SQLException {
		int index = 1;
		dto.setId(rs.getInt(index++));
		dto.setBonusId(rs.getInt(index++));
		dto.setUserId(rs.getInt(index++));
		try{
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
		} catch (Exception e1){}
		try{
			int columnid = index++;
			dto.setqAmountInr(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e1){}
		try{
			int columnid = index++;
			dto.setcAmount(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e1){}
		try{
			int columnid = index++;
			dto.setcAmountInr(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e1){}
		dto.setManagerId(rs.getInt(index++));
		dto.setManagerName(rs.getString(index++));
		dto.setClientName(rs.getString(index++));
		dto.setModifiedBy(rs.getString(index++));
		dto.setModifiedOn(rs.getDate((index++)));
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
		dto.setSalaryCycle(rs.getString(index));
		dto.setPaid(rs.getString(1+index++));
		
		try
		{
			int i = ((Integer) rs.getObject(1+index++)).intValue();

			dto.setEmpId(i);
			dto.setEmployeeName(rs.getString(1+index++));
			
		}
		catch (Exception e){
			logger.error("printing  " +e);
			
			
		}
		
	}
	
	

	/**
	 * Populates a DTO with data from a ResultSet
	 * 
	 */
	protected void populateDto(BonusReconciliationReport dto, ResultSet rs) throws SQLException {
		int index = 1;
		dto.setId(rs.getInt(index++));
		dto.setBonusId(rs.getInt(index++));
		dto.setUserId(rs.getInt(index++));
		try{
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
		} catch (Exception e1){}
		try{
			int columnid = index++;
			dto.setqAmountInr(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e1){}
		try{
			int columnid = index++;
			dto.setcAmount(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e1){}
		try{
			int columnid = index++;
			dto.setcAmountInr(new DecimalFormat("0.00").format(Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(rs.getString(columnid)))));
		} catch (Exception e1){}
		dto.setManagerId(rs.getInt(index++));
		dto.setManagerName(rs.getString(index++));
		dto.setClientName(rs.getString(index++));
		dto.setModifiedBy(rs.getString(index++));
		dto.setModifiedOn(rs.getDate((index++)));
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
		//dto.setPaid(rs.getString(index++));
		dto.setSalaryCycle(rs.getString(index));
		
	
		try
		{
			int i = ((Integer) rs.getObject(index++)).intValue();
	
			dto.setEmpId(i);
			dto.setEmployeeName(rs.getString(index++));
			
		}
		catch (Exception e){}
		
	}

	}
		