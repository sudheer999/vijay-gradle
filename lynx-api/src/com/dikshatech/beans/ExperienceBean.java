package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.portal.dto.Documents;

public class ExperienceBean implements Serializable
{
	private static final long serialVersionUID = 2796906620162373037L;

	
	private int id;
	
    private int candidateId;
    private int userId;
    
    private String company;

	
	private short currentEmp;

	private boolean currentEmpNull = true;

	
	private Date dateJoining;

	
	private Date dateRelieving;

	
	private String joiningDesignation;

	private String leavingDesignation;

	
	private String rptMgrName;

	
	private String hrName;

	
	private String permanenttemporary;
	
	private String employment_type;
	
	private String empCode;

	
	private String leavingReason;
	
	private Documents[] documentArr;
	
	private String	 backGroundVeri;
	
	private Documents[] backGroundVerification;
	
	
	
	

	public Documents[] getBackGroundVerification() {
		return backGroundVerification;
	}

	public void setBackGroundVerification(Documents[] backGroundVerification) {
		this.backGroundVerification = backGroundVerification;
	}

	public String getBackGroundVeri() {
		return backGroundVeri;
	}

	public void setBackGroundVeri(String backGroundVeri) {
		this.backGroundVeri = backGroundVeri;
	}

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


	public String getCompany()
	{
		return company;
	}


	public void setCompany(String company)
	{
		this.company = company;
	}


	public short getCurrentEmp()
	{
		return currentEmp;
	}


	public void setCurrentEmp(short currentEmp)
	{
		this.currentEmp = currentEmp;
	}


	public boolean isCurrentEmpNull()
	{
		return currentEmpNull;
	}


	public void setCurrentEmpNull(boolean currentEmpNull)
	{
		this.currentEmpNull = currentEmpNull;
	}


	public Date getDateJoining()
	{
		return dateJoining;
	}


	public void setDateJoining(Date dateJoining)
	{
		this.dateJoining = dateJoining;
	}


	public Date getDateRelieving()
	{
		return dateRelieving;
	}


	public void setDateRelieving(Date dateRelieving)
	{
		this.dateRelieving = dateRelieving;
	}


	public String getJoiningDesignation()
	{
		return joiningDesignation;
	}


	public void setJoiningDesignation(String joiningDesignation)
	{
		this.joiningDesignation = joiningDesignation;
	}


	public String getLeavingDesignation()
	{
		return leavingDesignation;
	}


	public void setLeavingDesignation(String leavingDesignation)
	{
		this.leavingDesignation = leavingDesignation;
	}


	public String getRptMgrName()
	{
		return rptMgrName;
	}


	public void setRptMgrName(String rptMgrName)
	{
		this.rptMgrName = rptMgrName;
	}


	public String getHrName()
	{
		return hrName;
	}


	public void setHrName(String hrName)
	{
		this.hrName = hrName;
	}


	public String getPermanenttemporary()
	{
		return permanenttemporary;
	}


	public void setPermanenttemporary(String permanenttemporary)
	{
		this.permanenttemporary = permanenttemporary;
	}


	public String getEmployment_type()
	{
		return employment_type;
	}


	public void setEmployment_type(String employmentType)
	{
		employment_type = employmentType;
	}


	public String getEmpCode()
	{
		return empCode;
	}


	public void setEmpCode(String empCode)
	{
		this.empCode = empCode;
	}


	public String getLeavingReason()
	{
		return leavingReason;
	}


	public void setLeavingReason(String leavingReason)
	{
		this.leavingReason = leavingReason;
	}

	public int getCandidateId()
	{
		return candidateId;
	}

	public void setCandidateId(int candidateId)
	{
		this.candidateId = candidateId;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}


	
}
