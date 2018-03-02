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

public interface ClientDivMapDao
{
	/** 
	 * Inserts a new row in the CLIENT_DIV_MAP table.
	 */
	public ClientDivMapPk insert(ClientDivMap dto) throws ClientDivMapDaoException;

	/** 
	 * Updates a single row in the CLIENT_DIV_MAP table.
	 */
	public void update(ClientDivMapPk pk, ClientDivMap dto) throws ClientDivMapDaoException;

	/** 
	 * Deletes a single row in the CLIENT_DIV_MAP table.
	 */
	public void delete(ClientDivMapPk pk) throws ClientDivMapDaoException;

	/** 
	 * Returns the rows from the CLIENT_DIV_MAP table that matches the specified primary-key value.
	 */
	public ClientDivMap findByPrimaryKey(ClientDivMapPk pk) throws ClientDivMapDaoException;

	/** 
	 * Returns all rows from the CLIENT_DIV_MAP table that match the criteria 'ID = :id'.
	 */
	public ClientDivMap findByPrimaryKey(int id) throws ClientDivMapDaoException;

	/** 
	 * Returns all rows from the CLIENT_DIV_MAP table that match the criteria ''.
	 */
	public ClientDivMap[] findAll() throws ClientDivMapDaoException;

	/** 
	 * Returns all rows from the CLIENT_DIV_MAP table that match the criteria 'CLIENT_ID = :clientId'.
	 */
	public ClientDivMap[] findByClient(int clientId) throws ClientDivMapDaoException;

	/** 
	 * Returns all rows from the CLIENT_DIV_MAP table that match the criteria 'ID = :id'.
	 */
	public ClientDivMap[] findWhereIdEquals(int id) throws ClientDivMapDaoException;

	/** 
	 * Returns all rows from the CLIENT_DIV_MAP table that match the criteria 'CLIENT_ID = :clientId'.
	 */
	public ClientDivMap[] findWhereClientIdEquals(int clientId) throws ClientDivMapDaoException;

	/** 
	 * Returns all rows from the CLIENT_DIV_MAP table that match the criteria 'DIV_ID = :divId'.
	 */
	public ClientDivMap[] findWhereDivIdEquals(int divId) throws ClientDivMapDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the CLIENT_DIV_MAP table that match the specified arbitrary SQL statement
	 */
	public ClientDivMap[] findByDynamicSelect(String sql, Object[] sqlParams) throws ClientDivMapDaoException;

	/** 
	 * Returns all rows from the CLIENT_DIV_MAP table that match the specified arbitrary SQL statement
	 */
	public ClientDivMap[] findByDynamicWhere(String sql, Object[] sqlParams) throws ClientDivMapDaoException;
	
	public boolean deleteAllByClient(int clientId);

}
