package com.dikshatech.portal.dao;

import java.util.Date;

import com.dikshatech.portal.dto.RmgTimeSheetReco;
import com.dikshatech.portal.dto.RmgTimeSheetRecoPk;
import com.dikshatech.portal.exceptions.RmgTimeSheetReconDaoException;

public interface RmgTimeSheetReconDao {
	

	/** 
	 * Inserts a new row in the DEP_PERDIEM table.
	 */
	public RmgTimeSheetRecoPk insert(RmgTimeSheetReco dto) throws RmgTimeSheetReconDaoException;

	/** 
	 * Updates a single row in the DEP_PERDIEM table.
	 */
	public void update(RmgTimeSheetRecoPk pk, RmgTimeSheetReco dto) throws RmgTimeSheetReconDaoException;

	/** 
	 * Deletes a single row in the DEP_PERDIEM table.
	 */
	public void delete(RmgTimeSheetRecoPk pk) throws RmgTimeSheetReconDaoException;

	/** 
	 * Returns the rows from the DEP_PERDIEM table that matches the specified primary-key value.
	 */
	public RmgTimeSheetReco findByPrimaryKey(RmgTimeSheetRecoPk pk) throws RmgTimeSheetReconDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'ID = :id'.
	 */
	public RmgTimeSheetReco findByPrimaryKey(int id) throws RmgTimeSheetReconDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria ''.
	 */
	public RmgTimeSheetReco[] findAll() throws RmgTimeSheetReconDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'ID = :id'.
	 */
	public RmgTimeSheetReco[] findWhereIdEquals(int id) throws RmgTimeSheetReconDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'ESR_MAP_ID = :esrMapId'.
	 */
	public RmgTimeSheetReco findWhereEsrMapIdEquals(int esrMapId) throws RmgTimeSheetReconDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'TERM = :term'.
	 */
	public RmgTimeSheetReco[] findWhereTermEquals(String term) throws RmgTimeSheetReconDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'MONTH = :month'.
	 */
	public RmgTimeSheetReco[] findWhereMonthEquals(String month) throws RmgTimeSheetReconDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'YEAR = :year'.
	 */
	public RmgTimeSheetReco[] findWhereYearEquals(int year) throws RmgTimeSheetReconDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'SUBMITTED_ON = :submittedOn'.
	 */
	public RmgTimeSheetReco[] findWhereSubmittedOnEquals(Date submittedOn) throws RmgTimeSheetReconDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'COMPLETED_ON = :completedOn'.
	 */
	public RmgTimeSheetReco[] findWhereCompletedOnEquals(Date completedOn) throws RmgTimeSheetReconDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'STATUS = :status'.
	 */
	public RmgTimeSheetReco[] findWhereStatusEquals(String status) throws RmgTimeSheetReconDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'HTML_STATUS = :htmlStatus'.
	 */
	public RmgTimeSheetReco[] findWhereHtmlStatusEquals(String htmlStatus) throws RmgTimeSheetReconDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the specified arbitrary SQL statement
	 */
	public RmgTimeSheetReco[] findByDynamicSelect(String sql, Object[] sqlParams) throws RmgTimeSheetReconDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the specified arbitrary SQL statement
	 */
	public RmgTimeSheetReco[] findByDynamicWhere(String sql, Object[] sqlParams) throws RmgTimeSheetReconDaoException;


	public int findMaxId() throws RmgTimeSheetReconDaoException;
	
	public int update(String sql) throws RmgTimeSheetReconDaoException;


}
