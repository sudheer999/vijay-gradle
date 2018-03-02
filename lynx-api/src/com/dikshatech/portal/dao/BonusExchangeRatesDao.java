package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.BonusExchangeRates;
import com.dikshatech.portal.dto.BonusExchangeRatesPk;
import com.dikshatech.portal.exceptions.BonusExchangeRatesDaoException;



public interface BonusExchangeRatesDao {
	
	/** 
	 * Inserts a new row in the DEP_PERDIEM_EXCHANGE_RATES table.
	 */
	public BonusExchangeRatesPk insert(BonusExchangeRates dto) throws BonusExchangeRatesDaoException;

	/** 
	 * Updates a single row in the DEP_PERDIEM_EXCHANGE_RATES table.
	 */
	public void update(BonusExchangeRatesPk pk, BonusExchangeRates dto) throws BonusExchangeRatesDaoException;

	/** 
	 * Deletes a single row in the DEP_PERDIEM_EXCHANGE_RATES table.
	 */
	public void delete(BonusExchangeRatesPk pk) throws BonusExchangeRatesDaoException;

	/** 
	 * Returns the rows from the DEP_PERDIEM_EXCHANGE_RATES table that matches the specified primary-key value.
	 */
	public BonusExchangeRates findByPrimaryKey(BonusExchangeRatesPk pk) throws BonusExchangeRatesDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_EXCHANGE_RATES table that match the criteria 'ID = :id'.
	 */
	public BonusExchangeRates findByPrimaryKey(int id) throws BonusExchangeRatesDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_EXCHANGE_RATES table that match the criteria 'REP_ID = :repId'.
	 */
	public BonusExchangeRates[] findWhereRepIdEquals(Integer repId) throws BonusExchangeRatesDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the DEP_PERDIEM_EXCHANGE_RATES table that match the specified arbitrary SQL statement
	 */
	public BonusExchangeRates[] findByDynamicSelect(String sql, Object[] sqlParams) throws BonusExchangeRatesDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_EXCHANGE_RATES table that match the specified arbitrary SQL statement
	 */
	public BonusExchangeRates[] findByDynamicWhere(String sql, Object[] sqlParams) throws BonusExchangeRatesDaoException;

}
