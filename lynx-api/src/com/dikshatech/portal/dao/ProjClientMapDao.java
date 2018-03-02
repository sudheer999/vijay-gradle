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

public interface ProjClientMapDao
{
	/** 
	 * Inserts a new row in the PROJ_CLIENT_MAP table.
	 */
	public ProjClientMapPk insert(ProjClientMap dto) throws ProjClientMapDaoException;

	/** 
	 * Updates a single row in the PROJ_CLIENT_MAP table.
	 */
	public void update(ProjClientMapPk pk, ProjClientMap dto) throws ProjClientMapDaoException;

	/** 
	 * Deletes a single row in the PROJ_CLIENT_MAP table.
	 */
	public void delete(ProjClientMapPk pk) throws ProjClientMapDaoException;

	/** 
	 * Returns the rows from the PROJ_CLIENT_MAP table that matches the specified primary-key value.
	 */
	public ProjClientMap findByPrimaryKey(ProjClientMapPk pk) throws ProjClientMapDaoException;

	/** 
	 * Returns all rows from the PROJ_CLIENT_MAP table that match the criteria 'ID = :id'.
	 */
	public ProjClientMap findByPrimaryKey(int id) throws ProjClientMapDaoException;

	/** 
	 * Returns all rows from the PROJ_CLIENT_MAP table that match the criteria ''.
	 */
	public ProjClientMap[] findAll() throws ProjClientMapDaoException;

	/** 
	 * Returns all rows from the PROJ_CLIENT_MAP table that match the criteria 'ID = :id'.
	 */
	public ProjClientMap[] findWhereIdEquals(int id) throws ProjClientMapDaoException;

	/** 
	 * Returns all rows from the PROJ_CLIENT_MAP table that match the criteria 'PROJ_ID = :projId'.
	 */
	public ProjClientMap[] findWhereProjIdEquals(int projId) throws ProjClientMapDaoException;

	/** 
	 * Returns all rows from the PROJ_CLIENT_MAP table that match the criteria 'CLIENT_ID = :clientId'.
	 */
	public ProjClientMap[] findWhereClientIdEquals(int clientId) throws ProjClientMapDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the PROJ_CLIENT_MAP table that match the specified arbitrary SQL statement
	 */
	public ProjClientMap[] findByDynamicSelect(String sql, Object[] sqlParams) throws ProjClientMapDaoException;

	/** 
	 * Returns all rows from the PROJ_CLIENT_MAP table that match the specified arbitrary SQL statement
	 */
	public ProjClientMap[] findByDynamicWhere(String sql, Object[] sqlParams) throws ProjClientMapDaoException;

}
