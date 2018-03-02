package com.dikshatech.portal.dao;
import java.util.List;

import com.dikshatech.portal.dto.BonusMasterData;
import com.dikshatech.portal.dto.BonusMasterDataPk;
import com.dikshatech.portal.dto.BonusReconciliationReport;
import com.dikshatech.portal.exceptions.BonusMasterDataDaoException;


public interface BonusMasterDataDao {
	/**
	 * Inserts a new row in the PERDIEM_MASTER_DATA table.
	 */
	public BonusMasterDataPk insert(BonusMasterData dto) throws BonusMasterDataDaoException;

	/**
	 * Updates a single row in the PERDIEM_MASTER_DATA table.
	 */
	public void update(BonusMasterDataPk pk, BonusMasterData dto)
			throws BonusMasterDataDaoException;

	/**
	 * Deletes a single row in the PERDIEM_MASTER_DATA table.
	 */
	public void delete(BonusMasterDataPk pk)
			throws BonusMasterDataDaoException;

	/**
	 * Returns the rows from the PERDIEM_MASTER_DATA table that matches the
	 * specified primary-key value.
	 */
	public BonusMasterData findByPrimaryKey(BonusMasterDataPk pk)
			throws BonusMasterDataDaoException;

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria 'ID = :id'.
	 */
	public BonusMasterData findByPrimaryKey(int id)
			throws BonusMasterDataDaoException;

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria ''.
	 */
	public BonusMasterData[] findAll() throws BonusMasterDataDaoException;

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria 'ID = :id'.
	 */
	public BonusMasterData[] findWhereIdEquals(int id)
			throws BonusMasterDataDaoException;

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria 'USER_ID = :userId'.
	 */
	public BonusMasterData[] findWhereUserIdEquals(int userId)
			throws BonusMasterDataDaoException;

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria 'PERDIEM = :perdiem'.
	 */
	public BonusMasterData[] findWhereQbonusEquals(String qBonus)
			throws BonusMasterDataDaoException;

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria 'PERDIEM_FROM = :perdiemFrom'.
	 */
	public BonusMasterData[] findWhereCbonusEquals(String cBonus)
			throws BonusMasterDataDaoException;

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria 'PERDIEM_TO = :perdiemTo'.
	 */
	public BonusMasterData[] findWhereMonthEquals(String month)
			throws BonusMasterDataDaoException;

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria 'CURRENCY_TYPE = :currencyType'.
	 */
	public BonusMasterData[] findWhereCurrencyTypeEquals(String currencyType)
			throws BonusMasterDataDaoException;

	/**
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/**
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * specified arbitrary SQL statement
	 */
	public BonusMasterData[] findByDynamicSelect(String sql,
			Object[] sqlParams) throws BonusMasterDataDaoException;

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * specified arbitrary SQL statement
	 */
	public BonusMasterData[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws BonusMasterDataDaoException;


public List<BonusReconciliationReport> getBonusCurrencyMaps(String month, int year) throws BonusMasterDataDaoException;

}
