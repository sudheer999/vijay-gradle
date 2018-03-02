/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.*;
import com.dikshatech.portal.exceptions.*;

public interface ProjectMapDao
{
	/** 
	 * Inserts a new row in the PROJECT_MAP table.
	 */
	public ProjectMapPk insert(ProjectMap dto) throws ProjectMapDaoException;

	/** 
	 * Updates a single row in the PROJECT_MAP table.
	 */
	public void update(ProjectMapPk pk, ProjectMap dto) throws ProjectMapDaoException;

	/** 
	 * Deletes a single row in the PROJECT_MAP table.
	 */
	public void delete(ProjectMapPk pk) throws ProjectMapDaoException;

	/** 
	 * Returns the rows from the PROJECT_MAP table that matches the specified primary-key value.
	 */
	public ProjectMap findByPrimaryKey(ProjectMapPk pk) throws ProjectMapDaoException;

	/** 
	 * Returns all rows from the PROJECT_MAP table that match the criteria 'ID = :id'.
	 */
	public ProjectMap findByPrimaryKey(int id) throws ProjectMapDaoException;

	/** 
	 * Returns all rows from the PROJECT_MAP table that match the criteria ''.
	 */
	public ProjectMap[] findAll() throws ProjectMapDaoException;

	/** 
	 * Returns all rows from the PROJECT_MAP table that match the criteria 'ID = :id'.
	 */
	public ProjectMap[] findWhereIdEquals(int id) throws ProjectMapDaoException;

	/** 
	 * Returns all rows from the PROJECT_MAP table that match the criteria 'PROJ_ID = :projId'.
	 */
	public ProjectMap[] findWhereProjIdEquals(int projId) throws ProjectMapDaoException;

	/** 
	 * Returns all rows from the PROJECT_MAP table that match the criteria 'COMP_ID = :compId'.
	 */
	public ProjectMap[] findWhereCompIdEquals(int compId) throws ProjectMapDaoException;

	/** 
	 * Returns all rows from the PROJECT_MAP table that match the criteria 'REGION_ID = :regionId'.
	 */
	public ProjectMap[] findWhereRegionIdEquals(int regionId) throws ProjectMapDaoException;

	/** 
	 * Returns all rows from the PROJECT_MAP table that match the criteria 'DEPT_ID = :deptId'.
	 */
	public ProjectMap[] findWhereDeptIdEquals(int deptId) throws ProjectMapDaoException;

	/** 
	 * Returns all rows from the PROJECT_MAP table that match the criteria 'DIV_ID = :divId'.
	 */
	public ProjectMap[] findWhereDivIdEquals(int divId) throws ProjectMapDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the PROJECT_MAP table that match the specified arbitrary SQL statement
	 */
	public ProjectMap[] findByDynamicSelect(String sql, Object[] sqlParams) throws ProjectMapDaoException;

	/** 
	 * Returns all rows from the PROJECT_MAP table that match the specified arbitrary SQL statement
	 */
	public ProjectMap[] findByDynamicWhere(String sql, Object[] sqlParams) throws ProjectMapDaoException;

}