package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.ReqResMap;
import com.dikshatech.portal.dto.ReqResMapPk;
import com.dikshatech.portal.exceptions.ReqResMapDaoException;

public interface ReqResMapDao {

	
	/** 
	 * Inserts a new row in the HOLIDAYS table.
	 */
	public ReqResMapPk insert(ReqResMap dto) throws ReqResMapDaoException;

	/** 
	 * Updates a single row in the HOLIDAYS table.
	 */
	public void update(ReqResMapPk pk, ReqResMap dto) throws ReqResMapDaoException;

	/** 
	 * Deletes a single row in the HOLIDAYS table.
	 */
	public void delete(ReqResMapPk pk) throws ReqResMapDaoException;

	/** 
	 * Returns the rows from the HOLIDAYS table that matches the specified primary-key value.
	 */
	public ReqResMap findByPrimaryKey(ReqResMapPk pk) throws ReqResMapDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria 'ID = :id'.
	 */
	public ReqResMap findByPrimaryKey(int id) throws ReqResMapDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria ''.
	 */
	public ReqResMap[] findAll() throws ReqResMapDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria 'ID = :id'.
	 */
	public ReqResMap[] findByReqId(int reqId) throws ReqResMapDaoException;
	
	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria 'ID = :id'.
	 */
	public ReqResMap[] findByResourceId(int reqId) throws ReqResMapDaoException;
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
	public ReqResMap[] findByDynamicSelect(String sql, Object[] sqlParams) throws ReqResMapDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the specified arbitrary SQL statement
	 */
	public ReqResMap[] findByDynamicWhere(String sql, Object[] sqlParams) throws ReqResMapDaoException;

}
