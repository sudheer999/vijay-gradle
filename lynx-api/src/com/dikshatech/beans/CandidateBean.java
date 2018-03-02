package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Date;

public class CandidateBean implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2312316861056437983L;
	private int id;
	private int profileId;
	private boolean profileIdNull = true;
	private int educationId;
	private boolean educationIdNull = true;
	private int experienceId;
	private boolean experienceIdNull = true;
	private int personalId;
	private boolean personalIdNull = true;
	private int financialId;
	private boolean financialIdNull = true;
	private int passportId;
	private boolean passportIdNull = true;
	private short isEmployee;
	private short isActive;
	private Date createDate;
	private int status;
	
	
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getProfileId()
	{
		return profileId;
	}
	public void setProfileId(int profileId)
	{
		this.profileId = profileId;
	}
	public boolean isProfileIdNull()
	{
		return profileIdNull;
	}
	public void setProfileIdNull(boolean profileIdNull)
	{
		this.profileIdNull = profileIdNull;
	}
	public int getEducationId()
	{
		return educationId;
	}
	public void setEducationId(int educationId)
	{
		this.educationId = educationId;
	}
	public boolean isEducationIdNull()
	{
		return educationIdNull;
	}
	public void setEducationIdNull(boolean educationIdNull)
	{
		this.educationIdNull = educationIdNull;
	}
	public int getExperienceId()
	{
		return experienceId;
	}
	public void setExperienceId(int experienceId)
	{
		this.experienceId = experienceId;
	}
	public boolean isExperienceIdNull()
	{
		return experienceIdNull;
	}
	public void setExperienceIdNull(boolean experienceIdNull)
	{
		this.experienceIdNull = experienceIdNull;
	}
	public int getPersonalId()
	{
		return personalId;
	}
	public void setPersonalId(int personalId)
	{
		this.personalId = personalId;
	}
	public boolean isPersonalIdNull()
	{
		return personalIdNull;
	}
	public void setPersonalIdNull(boolean personalIdNull)
	{
		this.personalIdNull = personalIdNull;
	}
	public int getFinancialId()
	{
		return financialId;
	}
	public void setFinancialId(int financialId)
	{
		this.financialId = financialId;
	}
	public boolean isFinancialIdNull()
	{
		return financialIdNull;
	}
	public void setFinancialIdNull(boolean financialIdNull)
	{
		this.financialIdNull = financialIdNull;
	}
	public int getPassportId()
	{
		return passportId;
	}
	public void setPassportId(int passportId)
	{
		this.passportId = passportId;
	}
	public boolean isPassportIdNull()
	{
		return passportIdNull;
	}
	public void setPassportIdNull(boolean passportIdNull)
	{
		this.passportIdNull = passportIdNull;
	}
	public short getIsEmployee()
	{
		return isEmployee;
	}
	public void setIsEmployee(short isEmployee)
	{
		this.isEmployee = isEmployee;
	}
	public short getIsActive()
	{
		return isActive;
	}
	public void setIsActive(short isActive)
	{
		this.isActive = isActive;
	}
	public Date getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
}
