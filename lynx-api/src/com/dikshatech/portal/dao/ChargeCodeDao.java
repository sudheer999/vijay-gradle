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

public interface ChargeCodeDao
{
	/** 
	 * Inserts a new row in the CHARGE_CODE table.
	 */
	public ChargeCodePk insert(ChargeCode dto) throws ChargeCodeDaoException;

	/** 
	 * Updates a single row in the CHARGE_CODE table.
	 */
	public void update(ChargeCodePk pk, ChargeCode dto) throws ChargeCodeDaoException;

	/** 
	 * Deletes a single row in the CHARGE_CODE table.
	 */
	public void delete(ChargeCodePk pk) throws ChargeCodeDaoException;

	/** 
	 * Returns the rows from the CHARGE_CODE table that matches the specified primary-key value.
	 */
	public ChargeCode findByPrimaryKey(ChargeCodePk pk) throws ChargeCodeDaoException;

	/** 
	 * Returns all rows from the CHARGE_CODE table that match the criteria 'ID = :id'.
	 */
	public ChargeCode findByPrimaryKey(Integer id) throws ChargeCodeDaoException;

	/** 
	 * Returns all rows from the CHARGE_CODE table that match the criteria ''.
	 */
	public ChargeCode[] findAll() throws ChargeCodeDaoException;

	/** 
	 * Returns all rows from the CHARGE_CODE table that match the criteria 'PO_ID = :poId'.
	 */
	public ChargeCode[] findByPoDetails(Integer poId) throws ChargeCodeDaoException;

	/** 
	 * Returns all rows from the CHARGE_CODE table that match the criteria 'ID = :id'.
	 */
	public ChargeCode[] findWhereIdEquals(Integer id) throws ChargeCodeDaoException;

	/** 
	 * Returns all rows from the CHARGE_CODE table that match the criteria 'PO_ID = :poId'.
	 */
	public ChargeCode[] findWherePoIdEquals(Integer poId) throws ChargeCodeDaoException;

	/** 
	 * Returns all rows from the CHARGE_CODE table that match the criteria 'CH_CODE_NAME = :chCodeName'.
	 */
	public ChargeCode[] findWhereChCodeNameEquals(String chCodeName) throws ChargeCodeDaoException;

	/** 
	 * Returns all rows from the CHARGE_CODE table that match the criteria 'CH_CODE = :chCode'.
	 */
	public ChargeCode[] findWhereChCodeEquals(String chCode) throws ChargeCodeDaoException;

	/** 
	 * Returns all rows from the CHARGE_CODE table that match the criteria 'AUTH_USERS = :authUsers'.
	 */
	public ChargeCode[] findWhereAuthUsersEquals(String authUsers) throws ChargeCodeDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the CHARGE_CODE table that match the specified arbitrary SQL statement
	 */
	public ChargeCode[] findByDynamicSelect(String sql, Object[] sqlParams) throws ChargeCodeDaoException;

	/** 
	 * Returns all rows from the CHARGE_CODE table that match the specified arbitrary SQL statement
	 */
	public ChargeCode[] findByDynamicWhere(String sql, Object[] sqlParams) throws ChargeCodeDaoException;

}