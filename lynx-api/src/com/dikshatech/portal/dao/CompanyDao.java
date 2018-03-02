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

public interface CompanyDao
{
	/** 
	 * Inserts a new row in the COMPANY table.
	 */
	public CompanyPk insert(Company dto) throws CompanyDaoException;

	/** 
	 * Updates a single row in the COMPANY table.
	 */
	public void update(CompanyPk pk, Company dto) throws CompanyDaoException;

	/** 
	 * Deletes a single row in the COMPANY table.
	 */
	public void delete(CompanyPk pk) throws CompanyDaoException;

	/** 
	 * Returns the rows from the COMPANY table that matches the specified primary-key value.
	 */
	public Company findByPrimaryKey(CompanyPk pk) throws CompanyDaoException;

	/** 
	 * Returns all rows from the COMPANY table that match the criteria 'ID = :id'.
	 */
	public Company findByPrimaryKey(int id) throws CompanyDaoException;

	/** 
	 * Returns all rows from the COMPANY table that match the criteria ''.
	 */
	public Company[] findAll() throws CompanyDaoException;

	/** 
	 * Returns all rows from the COMPANY table that match the criteria 'ID = :id'.
	 */
	public Company[] findWhereIdEquals(int id) throws CompanyDaoException;

	/** 
	 * Returns all rows from the COMPANY table that match the criteria 'COMPANY_NAME = :companyName'.
	 */
	public Company findWhereCompanyNameEquals(String companyName) throws CompanyDaoException;

	/** 
	 * Returns all rows from the COMPANY table that match the criteria 'CREATION_DATE = :creationDate'.
	 */
	public Company[] findWhereCreationDateEquals(Date creationDate) throws CompanyDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the COMPANY table that match the specified arbitrary SQL statement
	 */
	public Company[] findByDynamicSelect(String sql, Object[] sqlParams) throws CompanyDaoException;

	/** 
	 * Returns all rows from the COMPANY table that match the specified arbitrary SQL statement
	 */
	public Company[] findByDynamicWhere(String sql, Object[] sqlParams) throws CompanyDaoException;

}