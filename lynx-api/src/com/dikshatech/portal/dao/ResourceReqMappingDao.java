package com.dikshatech.portal.dao;

import java.util.List;

import com.dikshatech.beans.DropDownBean;
import com.dikshatech.portal.dto.ResourceReqMapping;
import com.dikshatech.portal.dto.ResourceReqMappingPk;
import com.dikshatech.portal.exceptions.ResourceReqMappingDaoException;

public interface ResourceReqMappingDao {
	/** 
	 * Inserts a new row in the HOLIDAYS table.
	 */
	public ResourceReqMappingPk insert(ResourceReqMapping dto) throws ResourceReqMappingDaoException;

	/** 
	 * Updates a single row in the HOLIDAYS table.
	 */
	public void update(ResourceReqMappingPk pk, ResourceReqMapping dto) throws ResourceReqMappingDaoException;

	/** 
	 * Deletes a single row in the HOLIDAYS table.
	 */
	public void delete(ResourceReqMappingPk pk) throws ResourceReqMappingDaoException;

	/** 
	 * Returns the rows from the HOLIDAYS table that matches the specified primary-key value.
	 */
	public ResourceReqMapping findByPrimaryKey(ResourceReqMappingPk pk) throws ResourceReqMappingDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria 'ID = :id'.
	 */
	public ResourceReqMapping findByPrimaryKey(int id) throws ResourceReqMappingDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria ''.
	 */
	public ResourceReqMapping[] findAll() throws ResourceReqMappingDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria 'ID = :id'.
	 */
	public ResourceReqMapping[] findByReqId(int reqId) throws ResourceReqMappingDaoException;
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
	public ResourceReqMapping[] findByDynamicSelect(String sql, Object[] sqlParams) throws ResourceReqMappingDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the specified arbitrary SQL statement
	 */
	public ResourceReqMapping[] findByDynamicWhere(String sql, Object[] sqlParams) throws ResourceReqMappingDaoException;

	
}
