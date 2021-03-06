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

public interface PayslipDao
{
	/** 
	 * Inserts a new row in the PAYSLIP table.
	 */
	public PayslipPk insert(Payslip dto) throws PayslipDaoException;

	/** 
	 * Updates a single row in the PAYSLIP table.
	 */
	public void update(PayslipPk pk, Payslip dto) throws PayslipDaoException;

	/** 
	 * Deletes a single row in the PAYSLIP table.
	 */
	public void delete(PayslipPk pk) throws PayslipDaoException;

	/** 
	 * Returns the rows from the PAYSLIP table that matches the specified primary-key value.
	 */
	public Payslip findByPrimaryKey(PayslipPk pk) throws PayslipDaoException;

	/** 
	 * Returns all rows from the PAYSLIP table that match the criteria 'ID = :id'.
	 */
	public Payslip findByPrimaryKey(int id) throws PayslipDaoException;

	/** 
	 * Returns all rows from the PAYSLIP table that match the criteria ''.
	 */
	public Payslip[] findAll() throws PayslipDaoException;

	/** 
	 * Returns all rows from the PAYSLIP table that match the criteria 'ID = :id'.
	 */
	public Payslip[] findWhereIdEquals(int id) throws PayslipDaoException;

	/** 
	 * Returns all rows from the PAYSLIP table that match the criteria 'FileID = :fileID'.
	 */
	public Payslip[] findWhereFileIDEquals(int fileID) throws PayslipDaoException;

	/** 
	 * Returns all rows from the PAYSLIP table that match the criteria 'UserID = :userID'.
	 */
	public Payslip[] findWhereUserIDEquals(int userID) throws PayslipDaoException;

	/** 
	 * Returns all rows from the PAYSLIP table that match the criteria 'Year_ = :year'.
	 */
	public Payslip[] findWhereYearEquals(int year) throws PayslipDaoException;

	/** 
	 * Returns all rows from the PAYSLIP table that match the criteria 'Month_ = :month'.
	 */
	public Payslip[] findWhereMonthEquals(String month) throws PayslipDaoException;

	/** 
	 * Returns all rows from the PAYSLIP table that match the criteria 'Insert_Date = :insertDate'.
	 */
	public Payslip[] findWhereInsertDateEquals(Date insertDate) throws PayslipDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the PAYSLIP table that match the specified arbitrary SQL statement
	 */
	public Payslip[] findByDynamicSelect(String sql, Object[] sqlParams) throws PayslipDaoException;

	/** 
	 * Returns all rows from the PAYSLIP table that match the specified arbitrary SQL statement
	 */
	public Payslip[] findByDynamicWhere(String sql, Object[] sqlParams) throws PayslipDaoException;

	public Payslip[ ] findWhereUserIDEqualsDESC(int userid) throws PayslipDaoException;

}
