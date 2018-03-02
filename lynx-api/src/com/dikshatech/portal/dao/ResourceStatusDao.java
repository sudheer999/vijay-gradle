package com.dikshatech.portal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dikshatech.beans.DropDownBean;
import com.dikshatech.portal.dto.ResourceRequirement;
import com.dikshatech.portal.dto.ResourceStatus;
import com.dikshatech.portal.dto.ResourceStatusPk;
import com.dikshatech.portal.exceptions.ResourceReqMappingDaoException;
import com.dikshatech.portal.exceptions.ResourceRequirementDaoException;
import com.dikshatech.portal.exceptions.ResourceStatusDaoException;
import com.dikshatech.portal.jdbc.ResourceManager;

public interface ResourceStatusDao {
	/** 
	 * Inserts a new row in the HOLIDAYS table.
	 */
	public ResourceStatusPk insert(ResourceStatus dto) throws ResourceStatusDaoException;

	/** 
	 * Updates a single row in the HOLIDAYS table.
	 */
	public void update(ResourceStatusPk pk, ResourceStatus dto) throws ResourceStatusDaoException;

	/** 
	 * Deletes a single row in the HOLIDAYS table.
	 */
	public void delete(ResourceStatusPk pk) throws ResourceStatusDaoException;

	/** 
	 * Returns the rows from the HOLIDAYS table that matches the specified primary-key value.
	 */
	public ResourceStatus findByPrimaryKey(ResourceStatusPk pk) throws ResourceStatusDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria 'ID = :id'.
	 */
	public ResourceStatus findByPrimaryKey(int id) throws ResourceStatusDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria ''.
	 */
	public ResourceStatus[] findAll() throws ResourceStatusDaoException;


	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();
	
	/** 
	 * Returns all rows from the HOLIDAYS table that match the specified arbitrary SQL statement
	 */
	public ResourceStatus[] findByDynamicSelect(String sql, Object[] sqlParams) throws ResourceStatusDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the specified arbitrary SQL statement
	 */
	public ResourceStatus[] findByDynamicWhere(String sql, Object[] sqlParams) throws ResourceStatusDaoException;
	
	/** 
	 * Gets the value of Resource main status values
	 * @throws ResourceStatusDaoException 
	 */ 
	public DropDownBean[] getResourcesMainStatus() throws  ResourceStatusDaoException;

	public String getMainStatusByID(int mainStatusID) throws ResourceStatusDaoException;

}
