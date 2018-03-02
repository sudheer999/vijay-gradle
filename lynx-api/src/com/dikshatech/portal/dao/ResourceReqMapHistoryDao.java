package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.ResourceReqMapHistory;
import com.dikshatech.portal.dto.ResourceReqMapHistoryPk;
import com.dikshatech.portal.exceptions.ResourceReqMapHistoryDaoException;

public interface ResourceReqMapHistoryDao {
	/** 
	 * Inserts a new row in the HOLIDAYS table.
	 */
	public ResourceReqMapHistoryPk insert(ResourceReqMapHistory dto) throws ResourceReqMapHistoryDaoException;

	/** 
	 * Updates a single row in the HOLIDAYS table.
	 */
	public void update(ResourceReqMapHistoryPk pk, ResourceReqMapHistory dto) throws ResourceReqMapHistoryDaoException;

	/** 
	 * Deletes a single row in the HOLIDAYS table.
	 */
	public void delete(ResourceReqMapHistoryPk pk) throws ResourceReqMapHistoryDaoException;

	/** 
	 * Returns the rows from the HOLIDAYS table that matches the specified primary-key value.
	 */
	public ResourceReqMapHistory findByPrimaryKey(ResourceReqMapHistoryPk pk) throws ResourceReqMapHistoryDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria 'ID = :id'.
	 */
	public ResourceReqMapHistory findByPrimaryKey(int id) throws ResourceReqMapHistoryDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria ''.
	 */
	public ResourceReqMapHistory[] findAll() throws ResourceReqMapHistoryDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria 'ID = :id'.
	 */
	public ResourceReqMapHistory[] findByMapId(int reqId) throws ResourceReqMapHistoryDaoException;
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
	public ResourceReqMapHistory[] findByDynamicSelect(String sql, Object[] sqlParams) throws ResourceReqMapHistoryDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the specified arbitrary SQL statement
	 */
	public ResourceReqMapHistory[] findByDynamicWhere(String sql, Object[] sqlParams) throws ResourceReqMapHistoryDaoException;
	
	
}
