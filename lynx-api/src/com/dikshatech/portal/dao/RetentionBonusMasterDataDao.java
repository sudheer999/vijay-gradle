package com.dikshatech.portal.dao;

import java.util.List;

import com.dikshatech.portal.dto.RetentionBonusMasterData;
import com.dikshatech.portal.dto.RetentionBonusMasterDataPk;
import com.dikshatech.portal.dto.RetentionBonusReconciliationReport;
import com.dikshatech.portal.exceptions.RetentionBonusMasterDataDaoException;

public interface RetentionBonusMasterDataDao {

	/**
	 * Inserts a new row in the RETENTION_PERDIEM_MASTER_DATA table.
	 */
	public RetentionBonusMasterDataPk insert(RetentionBonusMasterData dto) throws RetentionBonusMasterDataDaoException;

	/**
	 * Updates a single row in the RETENTION_PERDIEM_MASTER_DATA table.
	 */
	public void update(RetentionBonusMasterDataPk pk, RetentionBonusMasterData dto)
			throws RetentionBonusMasterDataDaoException;

	/**
	 * Deletes a single row in the RETENTION_PERDIEM_MASTER_DATA table.
	 */
	public void delete(RetentionBonusMasterDataPk pk)
			throws RetentionBonusMasterDataDaoException;

	/**
	 * Returns the rows from the RETENTION_PERDIEM_MASTER_DATA table that matches the
	 * specified primary-key value.
	 */
	public RetentionBonusMasterData findByPrimaryKey(RetentionBonusMasterDataPk pk)
			throws RetentionBonusMasterDataDaoException;

	/**
	 * Returns all rows from the RETENTION_PERDIEM_MASTER_DATA table that match the
	 * criteria 'ID = :id'.
	 */
	public RetentionBonusMasterData findByPrimaryKey(int id)
			throws RetentionBonusMasterDataDaoException;

	/**
	 * Returns all rows from the RETENTION_PERDIEM_MASTER_DATA table that match the
	 * criteria ''.
	 */
	public RetentionBonusMasterData[] findAll() throws RetentionBonusMasterDataDaoException;

	/**
	 * Returns all rows from the RETENTION_PERDIEM_MASTER_DATA table that match the
	 * criteria 'ID = :id'.
	 */
	public RetentionBonusMasterData[] findWhereIdEquals(int id)
			throws RetentionBonusMasterDataDaoException;

	/**
	 * Returns all rows from the RETENTION_PERDIEM_MASTER_DATA table that match the
	 * criteria 'USER_ID = :userId'.
	 */
	public RetentionBonusMasterData[] findWhereUserIdEquals(int userId)
			throws RetentionBonusMasterDataDaoException;

	/**
	 * Returns all rows from the RETENTION_PERDIEM_MASTER_DATA table that match the
	 * criteria 'PERDIEM = :perdiem'.
	 */
	public RetentionBonusMasterData[] findWhereQbonusEquals(String qBonus)
			throws RetentionBonusMasterDataDaoException;

	/**
	 * Returns all rows from the RETENTION_PERDIEM_MASTER_DATA table that match the
	 * criteria 'PERDIEM_FROM = :perdiemFrom'.
	 */
	public RetentionBonusMasterData[] findWhereCbonusEquals(String cBonus)
			throws RetentionBonusMasterDataDaoException;

	/**
	 * Returns all rows from the RETENTION_PERDIEM_MASTER_DATA table that match the
	 * criteria 'PERDIEM_TO = :perdiemTo'.
	 */
	public RetentionBonusMasterData[] findWhereMonthEquals(String month)
			throws RetentionBonusMasterDataDaoException;

	/**
	 * Returns all rows from the RETENTION_PERDIEM_MASTER_DATA table that match the
	 * criteria 'CURRENCY_TYPE = :currencyType'.
	 */
	public RetentionBonusMasterData[] findWhereCurrencyTypeEquals(String currencyType)
			throws RetentionBonusMasterDataDaoException;

	/**
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/**
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/**
	 * Returns all rows from the RETENTION_PERDIEM_MASTER_DATA table that match the
	 * specified arbitrary SQL statement
	 */
	public RetentionBonusMasterData[] findByDynamicSelect(String sql,
			Object[] sqlParams) throws RetentionBonusMasterDataDaoException;

	/**
	 * Returns all rows from the RETENTION_PERDIEM_MASTER_DATA table that match the
	 * specified arbitrary SQL statement
	 */
	public RetentionBonusMasterData[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws RetentionBonusMasterDataDaoException;


public List<RetentionBonusReconciliationReport> getBonusCurrencyMaps(String month, int year) throws RetentionBonusMasterDataDaoException;


}
