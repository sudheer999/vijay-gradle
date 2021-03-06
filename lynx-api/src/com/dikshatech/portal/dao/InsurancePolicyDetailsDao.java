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

public interface InsurancePolicyDetailsDao
{
	/** 
	 * Inserts a new row in the INSURANCE_POLICY_DETAILS table.
	 */
	public InsurancePolicyDetailsPk insert(InsurancePolicyDetails dto) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Updates a single row in the INSURANCE_POLICY_DETAILS table.
	 */
	public void update(InsurancePolicyDetailsPk pk, InsurancePolicyDetails dto) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Deletes a single row in the INSURANCE_POLICY_DETAILS table.
	 */
	public void delete(InsurancePolicyDetailsPk pk) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Returns the rows from the INSURANCE_POLICY_DETAILS table that matches the specified primary-key value.
	 */
	public InsurancePolicyDetails findByPrimaryKey(InsurancePolicyDetailsPk pk) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'ID = :id'.
	 */
	public InsurancePolicyDetails findByPrimaryKey(int id) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria ''.
	 */
	public InsurancePolicyDetails[] findAll() throws InsurancePolicyDetailsDaoException;

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'ID = :id'.
	 */
	public InsurancePolicyDetails[] findWhereIdEquals(Integer id) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'ESR_MAP_ID = :esrMapId'.
	 */
	public InsurancePolicyDetails[] findWhereEsrMapIdEquals(Integer esrMapId) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'NAME = :name'.
	 */
	public InsurancePolicyDetails[] findWhereNameEquals(String name) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'GENDER = :gender'.
	 */
	public InsurancePolicyDetails[] findWhereGenderEquals(String gender) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'DOB = :dob'.
	 */
	public InsurancePolicyDetails[] findWhereDobEquals(Date dob) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'AGE = :age'.
	 */
	public InsurancePolicyDetails[] findWhereAgeEquals(Integer age) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'RELATIONSHIP = :relationship'.
	 */
	public InsurancePolicyDetails[] findWhereRelationshipEquals(String relationship) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'POLICY_REQ_ID = :policyReqId'.
	 */
	public InsurancePolicyDetails[] findWherePolicyReqIdEquals(Integer policyReqId) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'CARD_ID = :cardId'.
	 */
	public InsurancePolicyDetails[] findWhereCardIdEquals(String cardId) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the criteria 'ENDORSEMENT_NO = :endorsementNo'.
	 */
	public InsurancePolicyDetails[] findWhereEndorsementNoEquals(String endorsementNo) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the specified arbitrary SQL statement
	 */
	public InsurancePolicyDetails[] findByDynamicSelect(String sql, Object[] sqlParams) throws InsurancePolicyDetailsDaoException;

	/** 
	 * Returns all rows from the INSURANCE_POLICY_DETAILS table that match the specified arbitrary SQL statement
	 */
	public InsurancePolicyDetails[] findByDynamicWhere(String sql, Object[] sqlParams) throws InsurancePolicyDetailsDaoException;

}
