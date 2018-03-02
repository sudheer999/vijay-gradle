package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.BonusReconciliationHold;
import com.dikshatech.portal.dto.BonusReconciliationHoldPk;
import com.dikshatech.portal.exceptions.BonusReconciliationHoldDaoException;


public interface BonusReconciliationHoldDao {
	
	
		/** 
		 * Inserts a new row in the dep_perdiem_hold table.
		 */
		public BonusReconciliationHoldPk insert(BonusReconciliationHold dto) throws BonusReconciliationHoldDaoException;

		/** 
		 * Updates a single row in the dep_perdiem_hold table.
		 */
		public void update(BonusReconciliationHoldPk pk, BonusReconciliationHold dto) throws BonusReconciliationHoldDaoException;

		/** 
		 * Deletes a single row in the dep_perdiem_hold table.
		 */
		public void delete(BonusReconciliationHoldPk pk) throws BonusReconciliationHoldDaoException;

		/** 
		 * Returns the rows from the dep_perdiem_hold table that matches the specified primary-key value.
		 */
		public BonusReconciliationHold findByPrimaryKey(BonusReconciliationHoldPk pk) throws BonusReconciliationHoldDaoException;

		/** 
		 * Returns all rows from the dep_perdiem_hold table that match the criteria 'ID = :id'.
		 */
		public BonusReconciliationHold findByPrimaryKey(int id) throws BonusReconciliationHoldDaoException;

		/** 
		 * Returns all rows from the dep_perdiem_hold table that match the criteria ''.
		 */
		public BonusReconciliationHold[] findAll() throws BonusReconciliationHoldDaoException;

		/** 
		 * Returns all rows from the dep_perdiem_hold table that match the criteria 'REP_ID = :repId'.
		 */
		public BonusReconciliationHold[] findWhereRepIdEquals(Integer repId) throws BonusReconciliationHoldDaoException;

		/** 
		 * Returns all rows from the dep_perdiem_hold table that match the criteria 'USER_ID = :userId'.
		 */
		public BonusReconciliationHold[] findWhereUserIdEquals(Integer userId) throws BonusReconciliationHoldDaoException;

		/** 
		 * Returns all rows from the dep_perdiem_hold table that match the criteria 'ESC_FROM = :escFrom'.
		 */
		public BonusReconciliationHold[] findWhereEscFromEquals(Integer escFrom) throws BonusReconciliationHoldDaoException;

		/** 
		 * Sets the value of maxRows
		 */
		public void setMaxRows(int maxRows);

		/** 
		 * Gets the value of maxRows
		 */
		public int getMaxRows();

		/** 
		 * Returns all rows from the dep_perdiem_hold table that match the specified arbitrary SQL statement
		 */
		public BonusReconciliationHold[] findByDynamicSelect(String sql, Object[] sqlParams) throws BonusReconciliationHoldDaoException;

		/** 
		 * Returns all rows from the dep_perdiem_hold table that match the specified arbitrary SQL statement
		 */
		public BonusReconciliationHold[] findByDynamicWhere(String sql, Object[] sqlParams) throws BonusReconciliationHoldDaoException;

}
