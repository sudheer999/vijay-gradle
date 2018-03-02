package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.JobRequirement;
import com.dikshatech.portal.dto.JobRequirementPk;
import com.dikshatech.portal.exceptions.JobRequirementDaoException;;

public interface JobRequirementDao {

	/** 
	 * Inserts a new row in the FIXED_BONUS table.
	 */
	public JobRequirementPk insert(JobRequirement dto) throws JobRequirementDaoException;

	/** 
	 * Updates a single row in the FIXED_BONUS table.
	 */
	public void update(JobRequirementPk pk, JobRequirement dto) throws JobRequirementDaoException;

	/** 
	 * Deletes a single row in the FIXED_BONUS table.
	 */
	public void delete(JobRequirementPk pk) throws JobRequirementDaoException;

	/** 
	 * Returns the rows from the FIXED_BONUS table that matches the specified primary-key value.
	 */
	public JobRequirement findByPrimaryKey(JobRequirementPk pk) throws JobRequirementDaoException;

	/** 
	 * Returns all rows from the FIXED_BONUS table that match the criteria 'ID = :id'.
	 */
	public JobRequirement findByPrimaryKey(Integer id) throws JobRequirementDaoException;

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
	public JobRequirement[] findByDynamicSelect(String sql, Object[] sqlParams) throws JobRequirementDaoException;

	/** 
	 * Returns all rows from the FIXED_BONUS table that match the specified arbitrary SQL statement
	 */
	public JobRequirement[] findByDynamicWhere(String sql, Object[] sqlParams) throws JobRequirementDaoException;
	
}
