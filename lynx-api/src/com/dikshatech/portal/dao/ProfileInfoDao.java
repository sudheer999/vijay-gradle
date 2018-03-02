/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dao;

import java.util.Date;

import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ProfileInfoPk;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;

public interface ProfileInfoDao {

	/**
	 * Inserts a new row in the PROFILE_INFO table.
	 */
	public ProfileInfoPk insert(ProfileInfo dto) throws ProfileInfoDaoException;

	/**
	 * Updates a single row in the PROFILE_INFO table.
	 */
	public void update(ProfileInfoPk pk, ProfileInfo dto) throws ProfileInfoDaoException;

	/**
	 * Deletes a single row in the PROFILE_INFO table.
	 */
	public void delete(ProfileInfoPk pk) throws ProfileInfoDaoException;

	/**
	 * Returns the rows from the PROFILE_INFO table that matches the specified primary-key value.
	 */
	public ProfileInfo findByPrimaryKey(ProfileInfoPk pk) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'ID = :id'.
	 */
	public ProfileInfo findByPrimaryKey(int id) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria ''.
	 */
	public ProfileInfo[] findAll() throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'ID = :id'.
	 */
	public ProfileInfo[] findWhereIdEquals(int id) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'FIRST_NAME = :firstName'.
	 */
	public ProfileInfo[] findWhereFirstNameEquals(String firstName) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'MIDDLE_NAME = :middleName'.
	 */
	public ProfileInfo[] findWhereMiddleNameEquals(String middleName) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'LAST_NAME = :lastName'.
	 */
	public ProfileInfo[] findWhereLastNameEquals(String lastName) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'MAIDEN_NAME = :maidenName'.
	 */
	public ProfileInfo[] findWhereMaidenNameEquals(String maidenName) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'NATIONALITY = :nationality'.
	 */
	public ProfileInfo[] findWhereNationalityEquals(String nationality) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'GENDER = :gender'.
	 */
	public ProfileInfo[] findWhereGenderEquals(String gender) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'DOB = :dob'.
	 */
	public ProfileInfo[] findWhereDobEquals(Date dob) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'OFFICAL_EMAIL_ID = :officalEmailId'.
	 */
	public ProfileInfo[] findWhereOfficalEmailIdEquals(String officalEmailId) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'HR_SPOC = :hrSpoc'.
	 */
	public ProfileInfo[] findWhereHrSpocEquals(int hrSpoc) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'REPORTING_MGR = :reportingMgr'.
	 */
	public ProfileInfo[] findWhereReportingMgrEquals(int reportingMgr) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'DATE_OF_JOINING = :dateOfJoining'.
	 */
	public ProfileInfo[] findWhereDateOfJoiningEquals(Date dateOfJoining) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'DATE_OF_CONFIRMATION = :dateOfConfirmation'.
	 */
	public ProfileInfo[] findWhereDateOfConfirmationEquals(Date dateOfConfirmation) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'MONTHS = :months'.
	 */
	public ProfileInfo[] findWhereMonthsEquals(String months) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'DOC = :doc'.
	 */
	public ProfileInfo[] findWhereDocEquals(Date doc) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'EXTENSION = :extension'.
	 */
	public ProfileInfo[] findWhereExtensionEquals(String extension) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'DATE_OF_SEPERATION = :dateOfSeperation'.
	 */
	public ProfileInfo[] findWhereDateOfSeperationEquals(Date dateOfSeperation) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'NOTICE_PERIOD = :noticePeriod'.
	 */
	public ProfileInfo[] findWhereNoticePeriodEquals(int noticePeriod) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'EMPLOYEE_TYPE = :employeeType'.
	 */
	public ProfileInfo[] findWhereEmployeeTypeEquals(String employeeType) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'EMP_TYPE = :empType'.
	 */
	public ProfileInfo[] findWhereEmpTypeEquals(String empType) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'CREATE_DATE = :createDate'.
	 */
	public ProfileInfo[] findWhereCreateDateEquals(Date createDate) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria ' ID IN
	 * (SELECT PROFILE_ID FROM USERS WHERE STATUS= :status AND REG_DIV_ID IN(SELECT ID FROM DIKSHA_PORTAL_2.REG_DIV_MAP
	 * WHERE REGION_ID = :regionId))'.
	 */
	public ProfileInfo[] findByRegionId(int regionId) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'LEVEL_ID = :levelId'.
	 */
	public ProfileInfo[] findWhereLevelIdEquals(int levelId) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'REPORTING_TIME = :reportingTime'.
	 */
	public ProfileInfo[] findWhereReportingTimeEquals(String reportingTime) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'REPORTING_ADDRESS = :reportingAddress'.
	 */
	public ProfileInfo[] findWhereReportingAddressEquals(byte[] reportingAddress) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the criteria 'REPORTING_ADDRESS_NORMAL = :reportingAddressNormal'.
	 */
	public ProfileInfo[] findWhereReportingAddressNormalEquals(String reportingAddressNormal) throws ProfileInfoDaoException;

	/**
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/**
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/**
	 * Returns all rows from the PROFILE_INFO table that match the specified arbitrary SQL statement
	 */
	public ProfileInfo[] findByDynamicSelect(String sql, Object[] sqlParams) throws ProfileInfoDaoException;

	public int findByDynamicUpdate(String sql, Object[] sqlParams) throws ProfileInfoDaoException;

	/**
	 * Returns all rows from the PROFILE_INFO table that match the specified arbitrary SQL statement
	 */
	public ProfileInfo[] findByDynamicWhere(String sql, Object[] sqlParams) throws ProfileInfoDaoException;

	public Integer[] findByDistinct(String columnName) throws ProfileInfoDaoException;

	public String[] findOfficalMailIdsWhere(String query) throws ProfileInfoDaoException;

	public ProfileInfo findWhereUserIdEquals(int userId) throws ProfileInfoDaoException;
	
	public ProfileInfo[] findWhereDateOfJoiningEqualsCurremtDate(String month, String date) throws ProfileInfoDaoException;

	public ProfileInfo[] findByDynamicSelect1(String string, Object[] objects) throws ProfileInfoDaoException;
	


	public ProfileInfo[] findByUserId(String string, Object[] objects) throws ProfileInfoDaoException;

	public ProfileInfo[] findByOfficialEmailId(String string, Object[] objects)throws ProfileInfoDaoException;
}
