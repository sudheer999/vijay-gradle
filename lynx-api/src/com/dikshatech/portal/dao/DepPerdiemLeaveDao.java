package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.DepPerdiemLeave;
import com.dikshatech.portal.dto.DepPerdiemLeavePk;
import com.dikshatech.portal.exceptions.DepPerdiemLeaveDaoException;

public interface DepPerdiemLeaveDao {

	/** 
	 * Inserts a new row in the DEP_PERDIEM_LEAVE table.
	 */
	public DepPerdiemLeavePk insert(DepPerdiemLeave dto) throws DepPerdiemLeaveDaoException;

	/** 
	 * Updates a single row in the DEP_PERDIEM_LEAVE table.
	 */
	public void update(DepPerdiemLeavePk pk, DepPerdiemLeave dto) throws DepPerdiemLeaveDaoException;

	/** 
	 * Deletes a single row in the DEP_PERDIEM_LEAVE table.
	 */
	public void delete(DepPerdiemLeavePk pk) throws DepPerdiemLeaveDaoException;

	/** 
	 * Returns the rows from the DEP_PERDIEM_LEAVE table that matches the specified primary-key value.
	 */
	public DepPerdiemLeave findByPrimaryKey(DepPerdiemLeavePk pk) throws DepPerdiemLeaveDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_LEAVE table that match the criteria 'ID = :id'.
	 */
	public DepPerdiemLeave findByPrimaryKey(Integer id) throws DepPerdiemLeaveDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_LEAVE table that match the criteria 'REP_ID = :repId'.
	 */
	public DepPerdiemLeave[] findWhereRepIdEquals(Integer repId) throws DepPerdiemLeaveDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the DEP_PERDIEM_LEAVE table that match the specified arbitrary SQL statement
	 */
	public DepPerdiemLeave[] findByDynamicSelect(String sql, Object[] sqlParams) throws DepPerdiemLeaveDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_LEAVE table that match the specified arbitrary SQL statement
	 */
	public DepPerdiemLeave[] findByDynamicWhere(String sql, Object[] sqlParams) throws DepPerdiemLeaveDaoException;
}
