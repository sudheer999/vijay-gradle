package com.dikshatech.beans;

import java.io.Serializable;

import com.dikshatech.portal.dto.Documents;

public class EducationBean implements Serializable
{
	private static final long serialVersionUID = 3569651435007277349L;
	private int id;
    private Integer candidateId;
    private Integer userId;
	private String degreecourse;
	private String type;

	private String subjectMajor;

	private String startDate;

	private int yearPassing;

	private boolean yearPassingNull = true;

	private String studIdNoenrollNo;

	private String collegeuniversity;

	private String gradepercentage;

	private String gradutionDate;
	
	private int sequence;
	
	private Documents[] documentArr;

	public Documents[] getDocumentArr()
	{
		return documentArr;
	}

	public void setDocumentArr(Documents[] documentArr)
	{
		this.documentArr = documentArr;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getDegreecourse()
	{
		return degreecourse;
	}

	public void setDegreecourse(String degreecourse)
	{
		this.degreecourse = degreecourse;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getSubjectMajor()
	{
		return subjectMajor;
	}

	public void setSubjectMajor(String subjectMajor)
	{
		this.subjectMajor = subjectMajor;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public int getYearPassing()
	{
		return yearPassing;
	}

	public void setYearPassing(int yearPassing)
	{
		this.yearPassing = yearPassing;
	}

	public boolean isYearPassingNull()
	{
		return yearPassingNull;
	}

	public void setYearPassingNull(boolean yearPassingNull)
	{
		this.yearPassingNull = yearPassingNull;
	}

	public String getStudIdNoenrollNo()
	{
		return studIdNoenrollNo;
	}

	public void setStudIdNoenrollNo(String studIdNoenrollNo)
	{
		this.studIdNoenrollNo = studIdNoenrollNo;
	}

	public String getCollegeuniversity()
	{
		return collegeuniversity;
	}

	public void setCollegeuniversity(String collegeuniversity)
	{
		this.collegeuniversity = collegeuniversity;
	}

	public String getGradepercentage()
	{
		return gradepercentage;
	}

	public void setGradepercentage(String gradepercentage)
	{
		this.gradepercentage = gradepercentage;
	}

	public String getGradutionDate()
	{
		return gradutionDate;
	}

	public void setGradutionDate(String gradutionDate)
	{
		this.gradutionDate = gradutionDate;
	}

	public void setSequence(int sequence)
	{
		this.sequence = sequence;
	}

	public int getSequence()
	{
		return sequence;
	}

	public Integer getCandidateId()
	{
		return candidateId;
	}

	public void setCandidateId(Integer candidateId)
	{
		this.candidateId = candidateId;
	}

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

}
