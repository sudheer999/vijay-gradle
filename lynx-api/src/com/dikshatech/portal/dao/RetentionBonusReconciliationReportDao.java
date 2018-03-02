package com.dikshatech.portal.dao;

import java.util.List;

import com.dikshatech.beans.RetentionBonusReportBean;
import com.dikshatech.portal.dto.RetentionBonusReconciliationReport;
import com.dikshatech.portal.dto.RetentionBonusReconciliationReportPk;
import com.dikshatech.portal.exceptions.RetentionBonusReconciliationReportDaoException;

public interface RetentionBonusReconciliationReportDao {

	
	/** 
	 * Inserts a new row in the dep_perdiem_report table.
	 */
	public RetentionBonusReconciliationReportPk insert(RetentionBonusReconciliationReport dto) throws RetentionBonusReconciliationReportDaoException;

	/** 
	 * Updates a single row in the dep_perdiem_report table.
	 */
	public void update(RetentionBonusReconciliationReportPk pk, RetentionBonusReconciliationReport dto) throws RetentionBonusReconciliationReportDaoException;

	/** 
	 * Deletes a single row in the dep_perdiem_report table.
	 */
	public void delete(RetentionBonusReconciliationReportPk pk) throws RetentionBonusReconciliationReportDaoException;

	/** 
	 * Returns the rows from the dep_perdiem_report table that matches the specified primary-key value.
	 */
	public RetentionBonusReconciliationReport findByPrimaryKey(RetentionBonusReconciliationReportPk pk) throws RetentionBonusReconciliationReportDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report table that match the criteria 'ID = :id'.
	 */
	public RetentionBonusReconciliationReport findByPrimaryKey(int id) throws RetentionBonusReconciliationReportDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report table that match the criteria 'DEP_ID = :depId'.
	 */
	public RetentionBonusReconciliationReport[] findWhereBonusIdEquals(int bonusId) throws RetentionBonusReconciliationReportDaoException;

	public RetentionBonusReconciliationReport[] findWhereBonusIdEquals(int bonusId, int managerId) throws RetentionBonusReconciliationReportDaoException;
	/** 
	 * Returns all rows from the dep_perdiem_report table that match the criteria 'USER_ID = :userId'.
	 */
	public RetentionBonusReconciliationReport[] findWhereUserIdEquals(int userId) throws RetentionBonusReconciliationReportDaoException;
	public RetentionBonusReconciliationReport[] findWherebonusIdEquals(int bonusId,int userId) throws RetentionBonusReconciliationReportDaoException;
	/** 
	 * Returns all rows from the dep_perdiem_report table that match the criteria 'MANAGER_ID = :managerId'.
	 */
	public RetentionBonusReconciliationReport[] findWhereManagerIdEquals(int managerId) throws RetentionBonusReconciliationReportDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report table that match the criteria 'IS_DELETED = :isDeleted'.
	 */
	public RetentionBonusReconciliationReport[] findWhereIsDeletedEquals(short isDeleted) throws RetentionBonusReconciliationReportDaoException;

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
	public RetentionBonusReconciliationReport[] findByDynamicSelect(String sql, Object[] sqlParams) throws RetentionBonusReconciliationReportDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report table that match the specified arbitrary SQL statement
	 */
	public RetentionBonusReconciliationReport[] findByDynamicWhere(String sql, Object[] sqlParams) throws RetentionBonusReconciliationReportDaoException;

	public List<String[]> findInternalReportData(int id) throws RetentionBonusReconciliationReportDaoException;

	public List<RetentionBonusReportBean> findBankReport(int id, int flag) throws RetentionBonusReconciliationReportDaoException;

}
