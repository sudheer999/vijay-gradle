package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.portal.dto.PersonalInfo;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.SalaryDetails;

public class CandidateSaveBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -986956714290459580L;
	private String firstName;
	private String middleName;
	private String lastName;
	private String maidenName;
	private String nationality;
	private String gender;
	private Date dob;
	private String officalEmailId;
	private int hrSpoc;
	private Date dateOfJoining;
	private Date dateOfConfirmation;
	private int noticePeriod;
	private String empType;
	private String permanentAddress;
	private int currentAddress;
	private int primaryPhoneNo;
	private boolean primaryPhoneNoNull;
	private int secondaryPhoneNo;
	private boolean secondaryPhoneNoNull;
	private String personalEmailId;
	private String motherName;
	private String fatherName;
	private String maritalStatus;
	private String spouseName;
	private String emerContactName;
	private String emerCpRelationship;
	private int emerPhoneNo;
	private int id;
	private int candidateId;
	private int userId;
	private String fieldLabel;
	private String fields[];

	private ProfileInfo profileInfo;
	private PersonalInfo personalInfo;
	private SalaryDetails salaryDetails;

	public CandidateSaveBean()
	{
		setProfileInfo();
		setPersonalInfo();
		setSalaryDetails();
	}

	public ProfileInfo getProfileInfo()
	{
		return profileInfo;
	}

	public void setProfileInfo()
	{
		ProfileInfo profileInfo = new ProfileInfo();
		profileInfo.setFirstName(firstName);
		profileInfo.setLastName(lastName);
		this.profileInfo = profileInfo;
	}

	public PersonalInfo getPersonalInfo()
	{
		return personalInfo;
	}

	public void setPersonalInfo()
	{
		personalInfo = new PersonalInfo();
		personalInfo.setCurrentAddress(currentAddress);
		// this.personalInfo = personalInfo;
	}

	public SalaryDetails getSalaryDetails()
	{
		return salaryDetails;
	}

	public void setSalaryDetails()
	{
		salaryDetails = new SalaryDetails();
		salaryDetails.setFields(fields);
		// this.salaryDetails = salaryDetails;
	}

	public String getFieldLabel()
	{
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel)
	{
		this.fieldLabel = fieldLabel;
	}

	private int annual;
	private int monthly;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getAnnual()
	{
		return annual;
	}

	public void setAnnual(int annual)
	{
		this.annual = annual;
	}

	public int getMonthly()
	{
		return monthly;
	}

	public void setMonthly(int monthly)
	{
		this.monthly = monthly;
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

	public String[] getFields()
	{
		return fields;
	}

	public void setFields(String[] fields)
	{
		this.fields = fields;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getMaidenName()
	{
		return maidenName;
	}

	public void setMaidenName(String maidenName)
	{
		this.maidenName = maidenName;
	}

	public String getNationality()
	{
		return nationality;
	}

	public void setNationality(String nationality)
	{
		this.nationality = nationality;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public Date getDob()
	{
		return dob;
	}

	public void setDob(Date dob)
	{
		this.dob = dob;
	}

	public String getOfficalEmailId()
	{
		return officalEmailId;
	}

	public void setOfficalEmailId(String officalEmailId)
	{
		this.officalEmailId = officalEmailId;
	}

	public int getHrSpoc()
	{
		return hrSpoc;
	}

	public void setHrSpoc(int hrSpoc)
	{
		this.hrSpoc = hrSpoc;
	}

	public Date getDateOfJoining()
	{
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining)
	{
		this.dateOfJoining = dateOfJoining;
	}

	public Date getDateOfConfirmation()
	{
		return dateOfConfirmation;
	}

	public void setDateOfConfirmation(Date dateOfConfirmation)
	{
		this.dateOfConfirmation = dateOfConfirmation;
	}

	public int getNoticePeriod()
	{
		return noticePeriod;
	}

	public void setNoticePeriod(int noticePeriod)
	{
		this.noticePeriod = noticePeriod;
	}

	public String getEmpType()
	{
		return empType;
	}

	public void setEmpType(String empType)
	{
		this.empType = empType;
	}

	public String getPermanentAddress()
	{
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress)
	{
		this.permanentAddress = permanentAddress;
	}

	public int getCurrentAddress()
	{
		return currentAddress;
	}

	public void setCurrentAddress(int currentAddress)
	{
		this.currentAddress = currentAddress;
	}

	public int getPrimaryPhoneNo()
	{
		return primaryPhoneNo;
	}

	public void setPrimaryPhoneNo(int primaryPhoneNo)
	{
		this.primaryPhoneNo = primaryPhoneNo;
	}

	public boolean isPrimaryPhoneNoNull()
	{
		return primaryPhoneNoNull;
	}

	public void setPrimaryPhoneNoNull(boolean primaryPhoneNoNull)
	{
		this.primaryPhoneNoNull = primaryPhoneNoNull;
	}

	public int getSecondaryPhoneNo()
	{
		return secondaryPhoneNo;
	}

	public void setSecondaryPhoneNo(int secondaryPhoneNo)
	{
		this.secondaryPhoneNo = secondaryPhoneNo;
	}

	public boolean isSecondaryPhoneNoNull()
	{
		return secondaryPhoneNoNull;
	}

	public void setSecondaryPhoneNoNull(boolean secondaryPhoneNoNull)
	{
		this.secondaryPhoneNoNull = secondaryPhoneNoNull;
	}

	public String getPersonalEmailId()
	{
		return personalEmailId;
	}

	public void setPersonalEmailId(String personalEmailId)
	{
		this.personalEmailId = personalEmailId;
	}

	public String getMotherName()
	{
		return motherName;
	}

	public void setMotherName(String motherName)
	{
		this.motherName = motherName;
	}

	public String getFatherName()
	{
		return fatherName;
	}

	public void setFatherName(String fatherName)
	{
		this.fatherName = fatherName;
	}

	public String getMaritalStatus()
	{
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus)
	{
		this.maritalStatus = maritalStatus;
	}

	public String getSpouseName()
	{
		return spouseName;
	}

	public void setSpouseName(String spouseName)
	{
		this.spouseName = spouseName;
	}

	public String getEmerContactName()
	{
		return emerContactName;
	}

	public void setEmerContactName(String emerContactName)
	{
		this.emerContactName = emerContactName;
	}

	public String getEmerCpRelationship()
	{
		return emerCpRelationship;
	}

	public void setEmerCpRelationship(String emerCpRelationship)
	{
		this.emerCpRelationship = emerCpRelationship;
	}

	public int getEmerPhoneNo()
	{
		return emerPhoneNo;
	}

	public void setEmerPhoneNo(int emerPhoneNo)
	{
		this.emerPhoneNo = emerPhoneNo;
	}

}
