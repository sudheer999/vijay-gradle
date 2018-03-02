package com.dikshatech.portal.dao;



import com.dikshatech.beans.DropDownBean;
import com.dikshatech.portal.dto.ResourceRequirement;
import com.dikshatech.portal.dto.ResourceRequirementPk;
import com.dikshatech.portal.exceptions.ResourceRequirementDaoException;;

public interface ResourceRequirementDao {

	/** 
	 * Inserts a new row in the HOLIDAYS table.
	 */
	public ResourceRequirementPk insert(ResourceRequirement dto) throws ResourceRequirementDaoException;

	/** 
	 * Updates a single row in the HOLIDAYS table.
	 */
	public void update(ResourceRequirementPk pk, ResourceRequirement dto) throws ResourceRequirementDaoException;

	/** 
	 * Deletes a single row in the HOLIDAYS table.
	 */
	public void delete(ResourceRequirementPk pk) throws ResourceRequirementDaoException;

	/** 
	 * Returns the rows from the HOLIDAYS table that matches the specified primary-key value.
	 */
	public ResourceRequirement findByPrimaryKey(ResourceRequirementPk pk) throws ResourceRequirementDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria 'ID = :id'.
	 */
	public ResourceRequirement findByPrimaryKey(int id) throws ResourceRequirementDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the criteria ''.
	 */
	public ResourceRequirement[] findAll() throws ResourceRequirementDaoException;


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
	public ResourceRequirement[] findByDynamicSelect(String sql, Object[] sqlParams) throws ResourceRequirementDaoException;

	/** 
	 * Returns all rows from the HOLIDAYS table that match the specified arbitrary SQL statement
	 */
	public ResourceRequirement[] findByDynamicWhere(String sql, Object[] sqlParams) throws ResourceRequirementDaoException;

	public String getEmploymentTypeByID(int employmentTypeId)throws ResourceRequirementDaoException;

	public DropDownBean[] getEmploymentTypes()throws ResourceRequirementDaoException;
	

}
