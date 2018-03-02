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

public interface ClientDao
{
	/** 
	 * Inserts a new row in the CLIENT table.
	 */
	public ClientPk insert(Client dto) throws ClientDaoException;

	/** 
	 * Updates a single row in the CLIENT table.
	 */
	public void update(ClientPk pk, Client dto) throws ClientDaoException;

	/** 
	 * Deletes a single row in the CLIENT table.
	 */
	public void delete(ClientPk pk) throws ClientDaoException;

	/** 
	 * Returns the rows from the CLIENT table that matches the specified primary-key value.
	 */
	public Client findByPrimaryKey(ClientPk pk) throws ClientDaoException;

	/** 
	 * Returns all rows from the CLIENT table that match the criteria 'ID = :id'.
	 */
	public Client findByPrimaryKey(int id) throws ClientDaoException;

	/** 
	 * Returns all rows from the CLIENT table that match the criteria ''.
	 */
	public Client[] findAll() throws ClientDaoException;

	/** 
	 * Returns all rows from the CLIENT table that match the criteria 'ID = :id'.
	 */
	public Client[] findWhereIdEquals(int id) throws ClientDaoException;

	/** 
	 * Returns all rows from the CLIENT table that match the criteria 'REGION_ID = :regionId'.
	 */
	public Client[] findWhereRegionIdEquals(int regionId) throws ClientDaoException;

	/** 
	 * Returns all rows from the CLIENT table that match the criteria 'DEPT_ID = :deptId'.
	 */
	public Client[] findWhereDeptIdEquals(int deptId) throws ClientDaoException;

	/** 
	 * Returns all rows from the CLIENT table that match the criteria 'NAME = :name'.
	 */
	public Client[] findWhereNameEquals(String name) throws ClientDaoException;

	/** 
	 * Returns all rows from the CLIENT table that match the criteria 'DESCRIPTION = :description'.
	 */
	public Client[] findWhereDescriptionEquals(String description) throws ClientDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the CLIENT table that match the specified arbitrary SQL statement
	 */
	public Client[] findByDynamicSelect(String sql, Object[] sqlParams) throws ClientDaoException;

	/** 
	 * Returns all rows from the CLIENT table that match the specified arbitrary SQL statement
	 */
	public Client[] findByDynamicWhere(String sql, Object[] sqlParams) throws ClientDaoException;

}
