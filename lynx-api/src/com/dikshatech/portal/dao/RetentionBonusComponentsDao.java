package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.RetentionBonusComponents;

import com.dikshatech.portal.dto.RetentionBonusComponentsPk;
import com.dikshatech.portal.exceptions.RetentionBonusComponentsDaoException;

public interface RetentionBonusComponentsDao {

	
	
	/** 
	 * Inserts a new row in the RETENTION_DEP_PERDIEM_COMPONENTS  table.
	 */
	public RetentionBonusComponentsPk insert(RetentionBonusComponents dto) throws RetentionBonusComponentsDaoException;

	/** 
	 * Updates a single row in the RETENTION_DEP_PERDIEM_COMPONENTS  table.
	 */
	public void update(RetentionBonusComponentsPk pk, RetentionBonusComponents dto) throws RetentionBonusComponentsDaoException;

	/** 
	 * Deletes a single row in the RETENTION_DEP_PERDIEM_COMPONENTS  table.
	 */
	public void delete(RetentionBonusComponentsPk pk) throws RetentionBonusComponentsDaoException;

	/** 
	 * Returns the rows from the RETENTION_DEP_PERDIEM_COMPONENTS  table that matches the specified primary-key value.
	 */
	public RetentionBonusComponents Retention(RetentionBonusComponentsPk pk) throws RetentionBonusComponentsDaoException;

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_COMPONENTS  table that match the criteria 'ID = :id'.
	 */
	public RetentionBonusComponents findByPrimaryKey(Integer id) throws RetentionBonusComponentsDaoException;

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_COMPONENTS  table that match the criteria 'REP_ID = :repId'.
	 */
	public RetentionBonusComponents[] findWhereRepIdEquals(Integer repId) throws RetentionBonusComponentsDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_COMPONENTS  table that match the specified arbitrary SQL statement
	 */
	public RetentionBonusComponents[] findByDynamicSelect(String sql, Object[] sqlParams) throws RetentionBonusComponentsDaoException;

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_COMPONENTS  table that match the specified arbitrary SQL statement
	 */
	public RetentionBonusComponents[] findByDynamicWhere(String sql, Object[] sqlParams) throws RetentionBonusComponentsDaoException;

}
