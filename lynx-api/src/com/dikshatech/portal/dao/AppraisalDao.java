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

public interface AppraisalDao
{
	/** 
	 * Inserts a new row in the APPRAISAL table.
	 */
	public AppraisalPk insert(Appraisal dto) throws AppraisalDaoException;

	/** 
	 * Updates a single row in the APPRAISAL table.
	 */
	public void update(AppraisalPk pk, Appraisal dto) throws AppraisalDaoException;

	/** 
	 * Deletes a single row in the APPRAISAL table.
	 */
	public void delete(AppraisalPk pk) throws AppraisalDaoException;

	/** 
	 * Returns the rows from the APPRAISAL table that matches the specified primary-key value.
	 */
	public Appraisal findByPrimaryKey(AppraisalPk pk) throws AppraisalDaoException;

	/** 
	 * Returns all rows from the APPRAISAL table that match the criteria 'ID = :id'.
	 */
	public Appraisal findByPrimaryKey(int id) throws AppraisalDaoException;

	/** 
	 * Returns all rows from the APPRAISAL table that match the criteria ''.
	 */
	public Appraisal[] findAll() throws AppraisalDaoException;

	/** 
	 * Returns all rows from the APPRAISAL table that match the criteria 'ID = :id'.
	 */
	public Appraisal[] findWhereIdEquals(int id) throws AppraisalDaoException;

	/** 
	 * Returns all rows from the APPRAISAL table that match the criteria 'USER_ID = :userId'.
	 */
	public Appraisal[] findWhereUserIdEquals(int userId) throws AppraisalDaoException;

	/** 
	 * Returns all rows from the APPRAISAL table that match the criteria 'APPRAISER = :appraiser'.
	 */
	public Appraisal[] findWhereAppraiserEquals(int appraiser) throws AppraisalDaoException;

	/** 
	 * Returns all rows from the APPRAISAL table that match the criteria 'HANDLER = :handler'.
	 */
	public Appraisal[] findWhereHandlerEquals(int handler) throws AppraisalDaoException;

	/** 
	 * Returns all rows from the APPRAISAL table that match the criteria 'TYPE = :type'.
	 */
	public Appraisal[] findWhereTypeEquals(String type) throws AppraisalDaoException;

	/** 
	 * Returns all rows from the APPRAISAL table that match the criteria 'PERIOD = :period'.
	 */
	public Appraisal[] findWherePeriodEquals(String period) throws AppraisalDaoException;

	/** 
	 * Returns all rows from the APPRAISAL table that match the criteria 'STATUS = :status'.
	 */
	public Appraisal[] findWhereStatusEquals(String status) throws AppraisalDaoException;

	/** 
	 * Returns all rows from the APPRAISAL table that match the criteria 'DATETIME = :datetime'.
	 */
	public Appraisal[] findWhereDatetimeEquals(Date datetime) throws AppraisalDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the APPRAISAL table that match the specified arbitrary SQL statement
	 */
	public Appraisal[] findByDynamicSelect(String sql, Object[] sqlParams) throws AppraisalDaoException;

	/** 
	 * Returns all rows from the APPRAISAL table that match the specified arbitrary SQL statement
	 */
	public Appraisal[] findByDynamicWhere(String sql, Object[] sqlParams) throws AppraisalDaoException;

}
