package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.BonusComponents;
import com.dikshatech.portal.dto.BonusComponentsPk;
import com.dikshatech.portal.exceptions.BonusComponentsDaoException;


public interface BonusComponentsDao {
	
	
	/** 
	 * Inserts a new row in the DEP_PERDIEM_COMPONENTS table.
	 */
	public BonusComponentsPk insert(BonusComponents dto) throws BonusComponentsDaoException;

	/** 
	 * Updates a single row in the DEP_PERDIEM_COMPONENTS table.
	 */
	public void update(BonusComponentsPk pk, BonusComponents dto) throws BonusComponentsDaoException;

	/** 
	 * Deletes a single row in the DEP_PERDIEM_COMPONENTS table.
	 */
	public void delete(BonusComponentsPk pk) throws BonusComponentsDaoException;

	/** 
	 * Returns the rows from the DEP_PERDIEM_COMPONENTS table that matches the specified primary-key value.
	 */
	public BonusComponents findByPrimaryKey(BonusComponentsPk pk) throws BonusComponentsDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_COMPONENTS table that match the criteria 'ID = :id'.
	 */
	public BonusComponents findByPrimaryKey(Integer id) throws BonusComponentsDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_COMPONENTS table that match the criteria 'REP_ID = :repId'.
	 */
	public BonusComponents[] findWhereRepIdEquals(Integer repId) throws BonusComponentsDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the DEP_PERDIEM_COMPONENTS table that match the specified arbitrary SQL statement
	 */
	public BonusComponents[] findByDynamicSelect(String sql, Object[] sqlParams) throws BonusComponentsDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_COMPONENTS table that match the specified arbitrary SQL statement
	 */
	public BonusComponents[] findByDynamicWhere(String sql, Object[] sqlParams) throws BonusComponentsDaoException;
}
