package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.FixedCandidatePerdiem;
import com.dikshatech.portal.dto.FixedCandidatePerdiemPk;
import com.dikshatech.portal.exceptions.FixedCandidatePerdiemDaoException;

public interface FixedCandidatePerdiemDao {
	/** 
	 * Inserts a new row in the FIXED_PERDIEM table.
	 */
	public FixedCandidatePerdiemPk insert(FixedCandidatePerdiem dto) throws FixedCandidatePerdiemDaoException;

	/** 
	 * Updates a single row in the FIXED_PERDIEM table.
	 */
	public void update(FixedCandidatePerdiemPk pk, FixedCandidatePerdiem dto) throws FixedCandidatePerdiemDaoException;

	/** 
	 * Deletes a single row in the FIXED_PERDIEM table.
	 */
	public void delete(FixedCandidatePerdiemPk pk) throws FixedCandidatePerdiemDaoException;

	/** 
	 * Returns the rows from the FIXED_PERDIEM table that matches the specified primary-key value.
	 */
	public FixedCandidatePerdiem findByPrimaryKey(FixedCandidatePerdiemPk pk) throws FixedCandidatePerdiemDaoException;

	/** 
	 * Returns all rows from the FIXED_PERDIEM table that match the criteria 'ID = :id'.
	 */
	public FixedCandidatePerdiem findByPrimaryKey(Integer id) throws FixedCandidatePerdiemDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the FIXED_PERDIEM table that match the specified arbitrary SQL statement
	 */
	public FixedCandidatePerdiem[] findByDynamicSelect(String sql, Object[] sqlParams) throws FixedCandidatePerdiemDaoException;

	/** 
	 * Returns all rows from the FIXED_PERDIEM table that match the specified arbitrary SQL statement
	 */
	public FixedCandidatePerdiem[] findByDynamicWhere(String sql, Object[] sqlParams) throws FixedCandidatePerdiemDaoException;

}
