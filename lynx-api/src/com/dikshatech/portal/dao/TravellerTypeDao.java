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

public interface TravellerTypeDao
{
	/** 
	 * Inserts a new row in the TRAVELLER_TYPE table.
	 */
	public TravellerTypePk insert(TravellerType dto) throws TravellerTypeDaoException;

	/** 
	 * Updates a single row in the TRAVELLER_TYPE table.
	 */
	public void update(TravellerTypePk pk, TravellerType dto) throws TravellerTypeDaoException;

	/** 
	 * Deletes a single row in the TRAVELLER_TYPE table.
	 */
	public void delete(TravellerTypePk pk) throws TravellerTypeDaoException;

	/** 
	 * Returns the rows from the TRAVELLER_TYPE table that matches the specified primary-key value.
	 */
	public TravellerType findByPrimaryKey(TravellerTypePk pk) throws TravellerTypeDaoException;

	/** 
	 * Returns all rows from the TRAVELLER_TYPE table that match the criteria 'ID = :id'.
	 */
	public TravellerType findByPrimaryKey(int id) throws TravellerTypeDaoException;

	/** 
	 * Returns all rows from the TRAVELLER_TYPE table that match the criteria ''.
	 */
	public TravellerType[] findAll() throws TravellerTypeDaoException;

	/** 
	 * Returns all rows from the TRAVELLER_TYPE table that match the criteria 'ID = :id'.
	 */
	public TravellerType[] findWhereIdEquals(int id) throws TravellerTypeDaoException;

	/** 
	 * Returns all rows from the TRAVELLER_TYPE table that match the criteria 'TL_ID = :tlId'.
	 */
	public TravellerType[] findWhereTlIdEquals(int tlId) throws TravellerTypeDaoException;

	/** 
	 * Returns all rows from the TRAVELLER_TYPE table that match the criteria 'IS_BUSSINESS_TYPE = :isBussinessType'.
	 */
	public TravellerType[] findWhereIsBussinessTypeEquals(int isBussinessType) throws TravellerTypeDaoException;

	/** 
	 * Returns all rows from the TRAVELLER_TYPE table that match the criteria 'NEXT_SET_OF_APPROVERS = :nextSetOfApprovers'.
	 */
	public TravellerType[] findWhereNextSetOfApproversEquals(String nextSetOfApprovers) throws TravellerTypeDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the TRAVELLER_TYPE table that match the specified arbitrary SQL statement
	 */
	public TravellerType[] findByDynamicSelect(String sql, Object[] sqlParams) throws TravellerTypeDaoException;

	/** 
	 * Returns all rows from the TRAVELLER_TYPE table that match the specified arbitrary SQL statement
	 */
	public TravellerType[] findByDynamicWhere(String sql, Object[] sqlParams) throws TravellerTypeDaoException;

}
