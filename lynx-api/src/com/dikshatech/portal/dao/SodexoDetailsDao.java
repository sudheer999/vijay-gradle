/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dao;

import java.util.Date;
import com.dikshatech.portal.dto.*;
import com.dikshatech.portal.exceptions.*;

public interface SodexoDetailsDao
{
	/** 
	 * Inserts a new row in the SODEXO_DETAILS table.
	 */
	public SodexoDetailsPk insert(SodexoDetails dto) throws SodexoDetailsDaoException;

	/** 
	 * Updates a single row in the SODEXO_DETAILS table.
	 */
	public void update(SodexoDetailsPk pk, SodexoDetails dto) throws SodexoDetailsDaoException;

	/** 
	 * Deletes a single row in the SODEXO_DETAILS table.
	 */
	public void delete(SodexoDetailsPk pk) throws SodexoDetailsDaoException;

	/** 
	 * Returns the rows from the SODEXO_DETAILS table that matches the specified primary-key value.
	 */
	public SodexoDetails findByPrimaryKey(SodexoDetailsPk pk) throws SodexoDetailsDaoException;

	/** 
	 * Returns all rows from the SODEXO_DETAILS table that match the criteria 'ID = :id'.
	 */
	public SodexoDetails findByPrimaryKey(int id) throws SodexoDetailsDaoException;

	/** 
	 * Returns all rows from the SODEXO_DETAILS table that match the criteria ''.
	 */
	public SodexoDetails[] findAll() throws SodexoDetailsDaoException;

	/** 
	 * Returns all rows from the SODEXO_DETAILS table that match the criteria 'ID = :id'.
	 */
	public SodexoDetails[] findWhereIdEquals(int id) throws SodexoDetailsDaoException;

	/** 
	 * Returns all rows from the SODEXO_DETAILS table that match the criteria 'REQUESTOR_ID = :requestorId'.
	 */
	public SodexoDetails[] findWhereRequestorIdEquals(int requestorId) throws SodexoDetailsDaoException;

	/** 
	 * Returns all rows from the SODEXO_DETAILS table that match the criteria 'AMOUNT_ELIGIBLE = :amountEligible'.
	 */
	public SodexoDetails[] findWhereAmountEligibleEquals(int amountEligible) throws SodexoDetailsDaoException;

	/** 
	 * Returns all rows from the SODEXO_DETAILS table that match the criteria 'AMOUNT_AVAILED = :amountAvailed'.
	 */
	public SodexoDetails[] findWhereAmountAvailedEquals(int amountAvailed) throws SodexoDetailsDaoException;

	/** 
	 * Returns all rows from the SODEXO_DETAILS table that match the criteria 'REQUESTED_ON = :requestedOn'.
	 */
	public SodexoDetails[] findWhereRequestedOnEquals(Date requestedOn) throws SodexoDetailsDaoException;

	/** 
	 * Returns all rows from the SODEXO_DETAILS table that match the criteria 'STATUS = :status'.
	 */
	public SodexoDetails[] findWhereStatusEquals(int status) throws SodexoDetailsDaoException;

	/** 
	 * Returns all rows from the SODEXO_DETAILS table that match the criteria 'SR_TYPE = :srType'.
	 */
	public SodexoDetails[] findWhereSrTypeEquals(String srType) throws SodexoDetailsDaoException;

	/** 
	 * Returns all rows from the SODEXO_DETAILS table that match the criteria 'DELIVERY_ADDRESS = :deliveryAddress'.
	 */
	public SodexoDetails[] findWhereDeliveryAddressEquals(String deliveryAddress) throws SodexoDetailsDaoException;

	/** 
	 * Returns all rows from the SODEXO_DETAILS table that match the criteria 'ADDRESS_HTML = :addressHtml'.
	 */
	public SodexoDetails[] findWhereAddressHtmlEquals(String addressHtml) throws SodexoDetailsDaoException;

	/** 
	 * Returns all rows from the SODEXO_DETAILS table that match the criteria 'ADDRESS_FLAG = :addressFlag'.
	 */
	public SodexoDetails[] findWhereAddressFlagEquals(int addressFlag) throws SodexoDetailsDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the SODEXO_DETAILS table that match the specified arbitrary SQL statement
	 */
	public SodexoDetails[] findByDynamicSelect(String sql, Object[] sqlParams) throws SodexoDetailsDaoException;

	/** 
	 * Returns all rows from the SODEXO_DETAILS table that match the specified arbitrary SQL statement
	 */
	public SodexoDetails[] findByDynamicWhere(String sql, Object[] sqlParams) throws SodexoDetailsDaoException;

	public SodexoDetails[] findByDynamicWhereIn(String sql) throws SodexoDetailsDaoException;
}