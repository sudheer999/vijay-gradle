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

public interface EducationInfoDao
{
	/** 
	 * Inserts a new row in the EDUCATION_INFO table.
	 */
	public EducationInfoPk insert(EducationInfo dto) throws EducationInfoDaoException;

	/** 
	 * Updates a single row in the EDUCATION_INFO table.
	 */
	public void update(EducationInfoPk pk, EducationInfo dto) throws EducationInfoDaoException;

	/** 
	 * Deletes a single row in the EDUCATION_INFO table.
	 */
	public void delete(EducationInfoPk pk) throws EducationInfoDaoException;

	/** 
	 * Returns the rows from the EDUCATION_INFO table that matches the specified primary-key value.
	 */
	public EducationInfo findByPrimaryKey(EducationInfoPk pk) throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the criteria 'ID = :id'.
	 */
	public EducationInfo findByPrimaryKey(int id) throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the criteria ''.
	 */
	public EducationInfo[] findAll() throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the criteria 'ID = :id'.
	 */
	public EducationInfo[] findWhereIdEquals(int id) throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the criteria 'DEGREE_COURSE = :degreeCourse'.
	 */
	public EducationInfo[] findWhereDegreeCourseEquals(String degreeCourse) throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the criteria 'TYPE = :type'.
	 */
	public EducationInfo[] findWhereTypeEquals(String type) throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the criteria 'SUBJECT_MAJOR = :subjectMajor'.
	 */
	public EducationInfo[] findWhereSubjectMajorEquals(String subjectMajor) throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the criteria 'START_DATE = :startDate'.
	 */
	public EducationInfo[] findWhereStartDateEquals(String startDate) throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the criteria 'YEAR_PASSING = :yearPassing'.
	 */
	public EducationInfo[] findWhereYearPassingEquals(int yearPassing) throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the criteria 'STUD_ID_NO_ENROLL_NO = :studIdNoEnrollNo'.
	 */
	public EducationInfo[] findWhereStudIdNoEnrollNoEquals(String studIdNoEnrollNo) throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the criteria 'COLLEGE_UNIVERSITY = :collegeUniversity'.
	 */
	public EducationInfo[] findWhereCollegeUniversityEquals(String collegeUniversity) throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the criteria 'GRADE_PERCENTAGE = :gradePercentage'.
	 */
	public EducationInfo[] findWhereGradePercentageEquals(String gradePercentage) throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the criteria 'GRADUTION_DATE = :gradutionDate'.
	 */
	public EducationInfo[] findWhereGradutionDateEquals(String gradutionDate) throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the criteria 'CANDIDATE_ID = :candidateId'.
	 */
	public EducationInfo[] findWhereCandidateIdEquals(int candidateId) throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the criteria 'USER_ID = :userId'.
	 */
	public EducationInfo[] findWhereUserIdEquals(int userId) throws EducationInfoDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the specified arbitrary SQL statement
	 */
	public EducationInfo[] findByDynamicSelect(String sql, Object[] sqlParams) throws EducationInfoDaoException;

	/** 
	 * Returns all rows from the EDUCATION_INFO table that match the specified arbitrary SQL statement
	 */
	public EducationInfo[] findByDynamicWhere(String sql, Object[] sqlParams) throws EducationInfoDaoException;

}
