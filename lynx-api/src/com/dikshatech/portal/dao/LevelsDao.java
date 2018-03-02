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

public interface LevelsDao
{
	/** 
	 * Inserts a new row in the LEVELS table.
	 */
	public LevelsPk insert(Levels dto) throws LevelsDaoException;

	/** 
	 * Updates a single row in the LEVELS table.
	 */
	public void update(LevelsPk pk, Levels dto) throws LevelsDaoException;

	/** 
	 * Deletes a single row in the LEVELS table.
	 */
	public void delete(LevelsPk pk) throws LevelsDaoException;

	/** 
	 * Returns the rows from the LEVELS table that matches the specified primary-key value.
	 */
	public Levels findByPrimaryKey(LevelsPk pk) throws LevelsDaoException;

	/** 
	 * Returns all rows from the LEVELS table that match the criteria 'ID = :id'.
	 */
	public Levels findByPrimaryKey(int id) throws LevelsDaoException;

	/** 
	 * Returns all rows from the LEVELS table that match the criteria ''.
	 */
	public Levels[] findAll() throws LevelsDaoException;

	/** 
	 * Returns all rows from the LEVELS table that match the criteria 'DIVISION_ID = :divisionId'.
	 */
	public Levels[] findByDivison(int divisionId) throws LevelsDaoException;

	/** 
	 * Returns all rows from the LEVELS table that match the criteria 'ID = :id'.
	 */
	public Levels[] findWhereIdEquals(int id) throws LevelsDaoException;

	/** 
	 * Returns all rows from the LEVELS table that match the criteria 'LABEL = :label'.
	 */
	public Levels[] findWhereLabelEquals(String label) throws LevelsDaoException;

	/** 
	 * Returns all rows from the LEVELS table that match the criteria 'DESIGNATION = :designation'.
	 */
	public Levels[] findWhereDesignationEquals(String designation) throws LevelsDaoException;

	/** 
	 * Returns all rows from the LEVELS table that match the criteria 'DIVISION_ID = :divisionId'.
	 */
	public Levels[] findWhereDivisionIdEquals(int divisionId) throws LevelsDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the LEVELS table that match the specified arbitrary SQL statement
	 */
	public Levels[] findByDynamicSelect(String sql, Object[] sqlParams) throws LevelsDaoException;

	/** 
	 * Returns all rows from the LEVELS table that match the specified arbitrary SQL statement
	 */
	public Levels[] findByDynamicWhere(String sql, Object[] sqlParams) throws LevelsDaoException;

	
	public int findWhereUsersIdEquals(int usid) throws LevelsDaoException;

}
