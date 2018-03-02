/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dao;

import java.util.Date;

import com.dikshatech.beans.ProjectBean;
import com.dikshatech.portal.dto.Project;
import com.dikshatech.portal.dto.ProjectPk;
import com.dikshatech.portal.exceptions.ProjectDaoException;

public interface ProjectDao
{
	/** 
	 * Inserts a new row in the PROJECT table.
	 */
	public ProjectPk insert(Project dto) throws ProjectDaoException;

	/** 
	 * Updates a single row in the PROJECT table.
	 */
	public void update(ProjectPk pk, Project dto) throws ProjectDaoException;

	/** 
	 * Deletes a single row in the PROJECT table.
	 */
	public void delete(ProjectPk pk) throws ProjectDaoException;

	/** 
	 * Returns the rows from the PROJECT table that matches the specified primary-key value.
	 */
	public Project findByPrimaryKey(ProjectPk pk) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'ID = :id'.
	 */
	public Project findByPrimaryKey(int id) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria ''.
	 */
	public Project[] findAll() throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'ID = :id'.
	 */
	public Project[] findWhereIdEquals(int id) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'OWNER_ID = :ownerId'.
	 */
	public Project[] findWhereOwnerIdEquals(int ownerId) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'CREATOR_ID = :creatorId'.
	 */
	public Project[] findWhereCreatorIdEquals(int creatorId) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'NAME = :name'.
	 */
	public Project[] findWhereNameEquals(String name) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'DESCRIPTION = :description'.
	 */
	public Project[] findWhereDescriptionEquals(String description) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'COMPANY_ID = :companyId'.
	 */
	public Project[] findWhereCompanyIdEquals(int companyId) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'BILL_ADDRESS = :billAddress'.
	 */
	public Project[] findWhereBillAddressEquals(String billAddress) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'BILL_CITY = :billCity'.
	 */
	public Project[] findWhereBillCityEquals(String billCity) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'BILL_ZIP_CODE = :billZipCode'.
	 */
	public Project[] findWhereBillZipCodeEquals(int billZipCode) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'BILL_STATE = :billState'.
	 */
	public Project[] findWhereBillStateEquals(String billState) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'BILL_COUNTRY = :billCountry'.
	 */
	public Project[] findWhereBillCountryEquals(String billCountry) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'BILL_TEL_NUM = :billTelNum'.
	 */
	public Project[] findWhereBillTelNumEquals(String billTelNum) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'BILL_FAX_NUM = :billFaxNum'.
	 */
	public Project[] findWhereBillFaxNumEquals(String billFaxNum) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'IS_ENABLE = :isEnable'.
	 */
	public Project[] findWhereIsEnableEquals(String isEnable) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'MESSAGE_BODY = :messageBody'.
	 */
	public Project[] findWhereMessageBodyEquals(String messageBody) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'ESRQM_ID = :esrqmId'.
	 */
	public Project[] findWhereEsrqmIdEquals(int esrqmId) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'CREATE_DATE = :createDate'.
	 */
	public Project[] findWhereCreateDateEquals(Date createDate) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'LAST_MODIFIED_BY = :lastModifiedBy'.
	 */
	public Project[] findWhereLastModifiedByEquals(int lastModifiedBy) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the criteria 'LAST_MODIFIED_ON = :lastModifiedOn'.
	 */
	public Project[] findWhereLastModifiedOnEquals(Date lastModifiedOn) throws ProjectDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the PROJECT table that match the specified arbitrary SQL statement
	 */
	public Project[] findByDynamicSelect(String sql, Object[] sqlParams) throws ProjectDaoException;

	/** 
	 * Returns all rows from the PROJECT table that match the specified arbitrary SQL statement
	 */
	public Project[] findByDynamicWhere(String sql, Object[] sqlParams) throws ProjectDaoException;

	public boolean deleteAllByProject(int projectId);

	public Project[ ] findProjectFromTasks(String sql, Object[ ] sqlParams);	

	public ProjectBean[] getProjectDetails(String whereCluse, Object[] sqlParams);
}