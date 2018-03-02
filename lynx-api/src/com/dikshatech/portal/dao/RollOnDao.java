/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dao;

import java.util.Date;
import com.dikshatech.portal.dto.*;
import com.dikshatech.portal.exceptions.*;

public interface RollOnDao
{
	/** 
	 * Inserts a new row in the ROLL_ON table.
	 */
	public RollOnPk insert(RollOn dto) throws RollOnDaoException;

	/** 
	 * Updates a single row in the ROLL_ON table.
	 */
	public void update(RollOnPk pk, RollOn dto) throws RollOnDaoException;

	/** 
	 * Deletes a single row in the ROLL_ON table.
	 */
	public void delete(RollOnPk pk) throws RollOnDaoException;

	/** 
	 * Returns the rows from the ROLL_ON table that matches the specified primary-key value.
	 */
	public RollOn findByPrimaryKey(RollOnPk pk) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'ID = :id'.
	 */
	public RollOn findByPrimaryKey(int id) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria ''.
	 */
	public RollOn[] findAll() throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'ID = :id'.
	 */
	public RollOn[] findWhereIdEquals(int id) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'EMP_ID = :empId'.
	 */
	public RollOn[] findWhereEmpIdEquals(int empId) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'START_DATE = :startDate'.
	 */
	public RollOn[] findWhereStartDateEquals(Date startDate) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'END_DATE = :endDate'.
	 */
	public RollOn[] findWhereEndDateEquals(Date endDate) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'REPORT_DT = :reportDt'.
	 */
	public RollOn[] findWhereReportDtEquals(Date reportDt) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'REPORT_TM = :reportTm'.
	 */
	public RollOn[] findWhereReportTmEquals(Date reportTm) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'PERDIEM = :perdiem'.
	 */
	public RollOn[] findWherePerdiemEquals(String perdiem) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'CH_CODE_ID = :chCodeId'.
	 */
	public RollOn[] findWhereChCodeIdEquals(int chCodeId) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'PAYMENT_TERM = :paymentTerm'.
	 */
	public RollOn[] findWherePaymentTermEquals(String paymentTerm) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'CURRENCY = :currency'.
	 */
	public RollOn[] findWhereCurrencyEquals(String currency) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'CURRENT = :current'.
	 */
	public RollOn[] findWhereCurrentEquals(short current) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'RAISED_BY = :raisedBy'.
	 */
	public RollOn[] findWhereRaisedByEquals(String raisedBy) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'MESSAGE_BODY = :messageBody'.
	 */
	public RollOn[] findWhereMessageBodyEquals(String messageBody) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'ESRQM_ID = :esrqmId'.
	 */
	public RollOn[] findWhereEsrqmIdEquals(int esrqmId) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'CREATE_DATE = :createDate'.
	 */
	public RollOn[] findWhereCreateDateEquals(Date createDate) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'TRAVEL_REQ_FLAG = :travelReqFlag'.
	 */
	public RollOn[] findWhereTravelReqFlagEquals(int travelReqFlag) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the criteria 'NOTIFIERS = :notifiers'.
	 */
	public RollOn[] findWhereNotifiersEquals(String notifiers) throws RollOnDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the ROLL_ON table that match the specified arbitrary SQL statement
	 */
	public RollOn[] findByDynamicSelect(String sql, Object[] sqlParams) throws RollOnDaoException;

	/** 
	 * Returns all rows from the ROLL_ON table that match the specified arbitrary SQL statement
	 */
	public RollOn[] findByDynamicWhere(String sql, Object[] sqlParams) throws RollOnDaoException;

	boolean inactiveRollOnData(int userId);

}
