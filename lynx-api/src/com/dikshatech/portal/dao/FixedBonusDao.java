package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.FixedBonus;
import com.dikshatech.portal.dto.FixedBonusPk;
import com.dikshatech.portal.exceptions.FixedBonusDaoException;


public interface FixedBonusDao {

	/** 
	 * Inserts a new row in the FIXED_BONUS table.
	 */
	public FixedBonusPk insert(FixedBonus dto) throws FixedBonusDaoException;

	/** 
	 * Updates a single row in the FIXED_BONUS table.
	 */
	public void update(FixedBonusPk pk, FixedBonus dto) throws FixedBonusDaoException;

	/** 
	 * Deletes a single row in the FIXED_BONUS table.
	 */
	public void delete(FixedBonusPk pk) throws FixedBonusDaoException;

	/** 
	 * Returns the rows from the FIXED_BONUS table that matches the specified primary-key value.
	 */
	public FixedBonus findByPrimaryKey(FixedBonusPk pk) throws FixedBonusDaoException;

	/** 
	 * Returns all rows from the FIXED_BONUS table that match the criteria 'ID = :id'.
	 */
	public FixedBonus findByPrimaryKey(Integer id) throws FixedBonusDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the FIXED_BONUS table that match the specified arbitrary SQL statement
	 */
	public FixedBonus[] findByDynamicSelect(String sql, Object[] sqlParams) throws FixedBonusDaoException;

	/** 
	 * Returns all rows from the FIXED_BONUS table that match the specified arbitrary SQL statement
	 */
	public FixedBonus[] findByDynamicWhere(String sql, Object[] sqlParams) throws FixedBonusDaoException;

}
