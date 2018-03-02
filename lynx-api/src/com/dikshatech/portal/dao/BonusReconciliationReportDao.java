
package com.dikshatech.portal.dao;

import java.util.List;
import java.util.ArrayList;

import com.dikshatech.beans.BonusReportBean;
import com.dikshatech.portal.dto.BonusReconciliationReport;
import com.dikshatech.portal.dto.BonusReconciliationReportPk;
import com.dikshatech.portal.exceptions.BonusReconciliationReportDaoException;
import com.dikshatech.portal.dto.BonusReconciliation;


public interface BonusReconciliationReportDao {
	
	/** 
	 * Inserts a new row in the dep_perdiem_report table.
	 */
	public BonusReconciliationReportPk insert(BonusReconciliationReport dto) throws BonusReconciliationReportDaoException;

	/** 
	 * Updates a single row in the dep_perdiem_report table.
	 */
	public void update(BonusReconciliationReportPk pk, BonusReconciliationReport dto) throws BonusReconciliationReportDaoException;

	/** 
	 * Deletes a single row in the dep_perdiem_report table.
	 */
	public void delete(BonusReconciliationReportPk pk) throws BonusReconciliationReportDaoException;

	/** 
	 * Returns the rows from the dep_perdiem_report table that matches the specified primary-key value.
	 */
	public BonusReconciliationReport findByPrimaryKey(BonusReconciliationReportPk pk) throws BonusReconciliationReportDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report table that match the criteria 'ID = :id'.
	 */
	public BonusReconciliationReport findByPrimaryKey(int id) throws BonusReconciliationReportDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report table that match the criteria 'DEP_ID = :depId'.
	 */
	public BonusReconciliationReport[] findWhereBonusIdEquals(int bonusId) throws BonusReconciliationReportDaoException;
	
	//HDFC
	public BonusReconciliationReport[] findWhereBonusIdEqualshdfc(int bonusId) throws BonusReconciliationReportDaoException;
	
	//NonHdfc
	
	public BonusReconciliationReport[] findWhereBonusIdEqualsnonHdfc(int bonusId) throws BonusReconciliationReportDaoException;
	
	//paidunpaid
	
	
	public BonusReconciliationReport[] findAllPaidAndUnpaid(int bonusId,String flag) throws BonusReconciliationReportDaoException;

	public BonusReconciliationReport[] findWhereBonusIdEquals(int bonusId, int managerId) throws BonusReconciliationReportDaoException;
	/** 
	 * Returns all rows from the dep_perdiem_report table that match the criteria 'USER_ID = :userId'.
	 */
	public BonusReconciliationReport[] findWhereUserIdEquals(int userId) throws BonusReconciliationReportDaoException;
	public BonusReconciliationReport[] findWherebonusIdAndUserIdEquals(int bonusId , int userId) throws BonusReconciliationReportDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report table that match the criteria 'MANAGER_ID = :managerId'.
	 */
	public BonusReconciliationReport[] findWhereManagerIdEquals(int managerId) throws BonusReconciliationReportDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report table that match the criteria 'IS_DELETED = :isDeleted'.
	 */
	public BonusReconciliationReport[] findWhereIsDeletedEquals(short isDeleted) throws BonusReconciliationReportDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the dep_perdiem_report table that match the specified arbitrary SQL statement
	 */
	public BonusReconciliationReport[] findByDynamicSelect(String sql, Object[] sqlParams) throws BonusReconciliationReportDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report table that match the specified arbitrary SQL statement
	 */
	public BonusReconciliationReport[] findByDynamicWhere(String sql, Object[] sqlParams) throws BonusReconciliationReportDaoException;
	
	public List<String[]> findInternalReportData(int id) throws BonusReconciliationReportDaoException;
	
	public List<String[]> findInternalReportDataHDFC(int id, ArrayList<Integer> arraylist) throws BonusReconciliationReportDaoException; // for HDFC
	
	public List<String[]> findInternalReportDataNONHDFC(int id, ArrayList<Integer> arraylist) throws BonusReconciliationReportDaoException;// for NON HDFC

	public List<BonusReportBean> findBankReport(int id, int flag) throws BonusReconciliationReportDaoException;
	public String updateAllReceivedPay(int id, ArrayList<Integer> bbr_Id,String flag1) throws  BonusReconciliationReportDaoException ;
            
	
}