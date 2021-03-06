/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.*;
import com.dikshatech.portal.exceptions.*;

public interface LoanEligibilityCriteriaDao
{
	/** 
	 * Inserts a new row in the LOAN_ELIGIBILITY_CRITERIA table.
	 */
	public LoanEligibilityCriteriaPk insert(LoanEligibilityCriteria dto) throws LoanEligibilityCriteriaDaoException;

	/** 
	 * Updates a single row in the LOAN_ELIGIBILITY_CRITERIA table.
	 */
	public void update(LoanEligibilityCriteriaPk pk, LoanEligibilityCriteria dto) throws LoanEligibilityCriteriaDaoException;

	/** 
	 * Deletes a single row in the LOAN_ELIGIBILITY_CRITERIA table.
	 */
	public void delete(LoanEligibilityCriteriaPk pk) throws LoanEligibilityCriteriaDaoException;

	/** 
	 * Returns the rows from the LOAN_ELIGIBILITY_CRITERIA table that matches the specified primary-key value.
	 */
	public LoanEligibilityCriteria findByPrimaryKey(LoanEligibilityCriteriaPk pk) throws LoanEligibilityCriteriaDaoException;

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'ID = :id'.
	 */
	public LoanEligibilityCriteria findByPrimaryKey(int id) throws LoanEligibilityCriteriaDaoException;

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria ''.
	 */
	public LoanEligibilityCriteria[] findAll() throws LoanEligibilityCriteriaDaoException;

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'TYPE_ID = :typeId'.
	 */
	public LoanEligibilityCriteria[] findByLoanType(int typeId) throws LoanEligibilityCriteriaDaoException;

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'ID = :id'.
	 */
	public LoanEligibilityCriteria[] findWhereIdEquals(int id) throws LoanEligibilityCriteriaDaoException;

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'LABEL = :label'.
	 */
	public LoanEligibilityCriteria[] findWhereLabelEquals(String label) throws LoanEligibilityCriteriaDaoException;

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'ELIGIBILITY_AMOUNT = :eligibilityAmount'.
	 */
	public LoanEligibilityCriteria[] findWhereEligibilityAmountEquals(String eligibilityAmount) throws LoanEligibilityCriteriaDaoException;

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'EMI_ELIGIBILITY = :emiEligibility'.
	 */
	public LoanEligibilityCriteria[] findWhereEmiEligibilityEquals(int emiEligibility) throws LoanEligibilityCriteriaDaoException;

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'MAX_AMOUNT_LIMIT = :maxAmountLimit'.
	 */
	public LoanEligibilityCriteria[] findWhereMaxAmountLimitEquals(double maxAmountLimit) throws LoanEligibilityCriteriaDaoException;

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the criteria 'TYPE_ID = :typeId'.
	 */
	public LoanEligibilityCriteria[] findWhereTypeIdEquals(int typeId) throws LoanEligibilityCriteriaDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the specified arbitrary SQL statement
	 */
	public LoanEligibilityCriteria[] findByDynamicSelect(String sql, Object[] sqlParams) throws LoanEligibilityCriteriaDaoException;

	/** 
	 * Returns all rows from the LOAN_ELIGIBILITY_CRITERIA table that match the specified arbitrary SQL statement
	 */
	public LoanEligibilityCriteria[] findByDynamicWhere(String sql, Object[] sqlParams) throws LoanEligibilityCriteriaDaoException;

}
