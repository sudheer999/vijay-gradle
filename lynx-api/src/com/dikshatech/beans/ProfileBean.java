package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.portal.dto.ChargeCode;
import com.dikshatech.portal.dto.ContactType;

public class ProfileBean implements Serializable
{
	/**
     * 
     */
    private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	//private static final long serialVersionUID = 1103769921186606620L;


	private int id;

	
	private String firstName;

	
	private String middleName;

	
	private String lastName;

	
	private String maidenName;

	
	private String nationality;

	
	private String gender;

	
	private Date dob;

	
	private String officalEmailId;

	
	private int hrSpoc;

	
	private int reportingMgr;

	private Date dateOfJoining;

	private String months;
	
	private Date doc; 
	
	private String extension;
	
	private Date dateOfConfirmation;

	
	private int noticePeriod;

	private String employee_type;
	private String empType;

	
	private Date createDate;
	
	private int userCreatedBy;
	
	private String userCreatedByName;
	
	private int userId;
	
	private String department;
	private String level;
	private String designation;
	private String division;
	private String region;
	private String location;
	private int locationid;
	private boolean hideCategoryDetails;
	
	private String bloodGroup;
	
	private String categoryid;
	
	private String category;

	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getCategoryid() {
		return categoryid;
	}


	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}


	public String getBloodGroup() {
		return bloodGroup;
	}


	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}


	public boolean isHideCategoryDetails() {
		return hideCategoryDetails;
	}


	public void setHideCategoryDetails(boolean hideCategoryDetails) {
		this.hideCategoryDetails = hideCategoryDetails;
	}


	public int getLocationid() {
		return locationid;
	}


	public void setLocationid(int locationid) {
		this.locationid = locationid;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	private String company;
	
	private int regionid;
	private String phonenumber;
	private int divisionid;
	private int departmentid;
	private int levelid;
	private int empid;
	private int companyid;
	

    private String reportingMngrName;
    private String hrSpocName;
    private int roleid;
	private String roleName;
	private int employmentid;
	protected int projectId;
	private String projectName;
	private String assoChargeCode;
	private String chargeCodeName;
	
	private boolean isRollOn;
	
	private int  rollOnId;
	private ChargeCode[] proChargeCode;
	private String dateOfSeperation;
	private String actionDate;
	boolean isIncomplete = false;
	
	protected String[] contactType;
	
	protected ContactType[] receiveContactType;
	protected String type;
	protected String name;
	protected String contactTypeDesignation;
	protected String phoneNumber;
	protected String emailId;
	protected String relationship;
	private boolean isHideFinanceDetails;
	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
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


	public int getUserId()
	{
		return userId;
	}


	public void setUserId(int userId)
	{
		this.userId = userId;
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


	public int getEmploymentid()
	{
		return employmentid;
	}


	public void setEmploymentid(int employmentid)
	{
		this.employmentid = employmentid;
	}


	public String getActionDate()
	{
		return actionDate;
	}


	public void setActionDate(String actionDate)
	{
		this.actionDate = actionDate;
	}


	public String getOfficalEmailId()
	{
		return officalEmailId;
	}


	public void setOfficalEmailId(String officalEmailId)
	{
		this.officalEmailId = officalEmailId;
	}


	public String getProjectName()
	{
		return projectName;
	}


	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}


	public int getProjectId()
	{
		return projectId;
	}


	public void setProjectId(int projectId)
	{
		this.projectId = projectId;
	}


	public int getHrSpoc()
	{
		return hrSpoc;
	}


	public void setHrSpoc(int hrSpoc)
	{
		this.hrSpoc = hrSpoc;
	}


	public int getReportingMgr()
	{
		return reportingMgr;
	}


	public void setReportingMgr(int reportingMgr)
	{
		this.reportingMgr = reportingMgr;
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


	public String getDateOfSeperation()
	{
		return dateOfSeperation;
	}


	public void setDateOfSeperation(String dateOfSeperation)
	{
		this.dateOfSeperation = dateOfSeperation;
	}


	public int getNoticePeriod()
	{
		return noticePeriod;
	}


	public void setNoticePeriod(int noticePeriod)
	{
		this.noticePeriod = noticePeriod;
	}


	public String getType()
	{
		return type;
	}


	public void setType(String type)
	{
		this.type = type;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public String getContactTypeDesignation()
	{
		return contactTypeDesignation;
	}


	public void setContactTypeDesignation(String contactTypeDesignation)
	{
		this.contactTypeDesignation = contactTypeDesignation;
	}


	public String getPhoneNumber()
	{
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}


	public String getEmailId()
	{
		return emailId;
	}


	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}


	public String getRelationship()
	{
		return relationship;
	}


	public void setRelationship(String relationship)
	{
		this.relationship = relationship;
	}


	public ContactType[] getReceiveContactType()
	{
		return receiveContactType;
	}


	public void setReceiveContactType(ContactType[] receiveContactType)
	{
		this.receiveContactType = receiveContactType;
	}


	public String[] getContactType()
	{
		return contactType;
	}


	public void setContactType(String[] contactType)
	{
		this.contactType = contactType;
	}


	public String getEmpType()
	{
		return empType;
	}


	public void setEmpType(String empType)
	{
		this.empType = empType;
	}


	public int getRoleid()
	{
		return roleid;
	}


	public void setRoleid(int roleif)
	{
		this.roleid = roleif;
	}


	public Date getCreateDate()
	{
		return createDate;
	}


	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}


	public int getUserCreatedBy()
	{
		return userCreatedBy;
	}


	public void setUserCreatedBy(int userCreatedBy)
	{
		this.userCreatedBy = userCreatedBy;
	}


	public String getUserCreatedByName()
	{
		return userCreatedByName;
	}


	public void setUserCreatedByName(String userCreatedByName)
	{
		this.userCreatedByName = userCreatedByName;
	}


	public String getDepartment()
	{
		return department;
	}


	public void setDepartment(String department)
	{
		this.department = department;
	}


	public String getLevel()
	{
		return level;
	}


	public void setLevel(String level)
	{
		this.level = level;
	}


	public String getDesignation()
	{
		return designation;
	}


	public void setDesignation(String designation)
	{
		this.designation = designation;
	}


	public String getDivision()
	{
		return division;
	}


	public void setDivision(String division)
	{
		this.division = division;
	}


	public String getRoleName()
	{
		return roleName;
	}


	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}


	public boolean isIncomplete()
	{
		return isIncomplete;
	}


	public void setIncomplete(boolean isIncomplete)
	{
		this.isIncomplete = isIncomplete;
	}


	public String getPhonenumber()
	{
		return phonenumber;
	}


	public void setPhonenumber(String phonenumber)
	{
		this.phonenumber = phonenumber;
	}


	public int getDivisionid()
	{
		return divisionid;
	}


	public void setDivisionid(int divisionid)
	{
		this.divisionid = divisionid;
	}


	public int getDepartmentid()
	{
		return departmentid;
	}


	public void setDepartmentid(int departmentid)
	{
		this.departmentid = departmentid;
	}


	public int getLevelid()
	{
		return levelid;
	}


	public void setLevelid(int levelid)
	{
		this.levelid = levelid;
	}


	public int getEmpid()
	{
		return empid;
	}


	public void setEmpid(int empid)
	{
		this.empid = empid;
	}


	public String getCompany()
	{
		return company;
	}


	public void setCompany(String company)
	{
		this.company = company;
	}


	public int getCompanyid()
	{
		return companyid;
	}

	public void setCompanyid(int companyid)
	{
		this.companyid = companyid;
	}


	public String getRegion()
	{
		return region;
	}


	public void setRegion(String region)
	{
		this.region = region;
	}


	public int getRegionid()
	{
		return regionid;
	}


	public void setRegionid(int regionid)
	{
		this.regionid = regionid;
	}
	public String getReportingMngrName()
	{
		return reportingMngrName;
	}


	public void setReportingMngrName(String reportingMngrName)
	{
		this.reportingMngrName = reportingMngrName;
	}


	public String getHrSpocName()
	{
		return hrSpocName;
	}


	public void setHrSpocName(String hrSpocName)
	{
		this.hrSpocName = hrSpocName;
	}


	public String getAssoChargeCode()
    {
    	return assoChargeCode;
    }


	public void setAssoChargeCode(String assoChargeCode)
    {
    	this.assoChargeCode = assoChargeCode;
    }


	public String getChargeCodeName()
    {
    	return chargeCodeName;
    }


	public void setChargeCodeName(String chargeCodeName)
    {
    	this.chargeCodeName = chargeCodeName;
    }


	public boolean isRollOn()
    {
    	return isRollOn;
    }


	public void setRollOn(boolean isRollOn)
    {
    	this.isRollOn = isRollOn;
    }


	public int getRollOnId()
    {
    	return rollOnId;
    }


	public void setRollOnId(int rollOnId)
    {
    	this.rollOnId = rollOnId;
    }


	public ChargeCode[] getProChargeCode()
    {
    	return proChargeCode;
    }


	public void setProChargeCode(ChargeCode[] proChargeCode)
    {
    	this.proChargeCode = proChargeCode;
    }


	public String getMonths()
	{
		return months;
	}


	public void setMonths(String months)
	{
		this.months = months;
	}


	public Date getDoc()
	{
		return doc;
	}


	public void setDoc(Date doc)
	{
		this.doc = doc;
	}


	public String getEmployee_type()
	{
		return employee_type;
	}


	public void setEmployee_type(String employeeType)
	{
		employee_type = employeeType;
	}


	public String getExtension()
	{
		return extension;
	}


	public void setExtension(String extension)
	{
		this.extension = extension;
	}


	public boolean isHideFinanceDetails() {
		return isHideFinanceDetails;
	}


	public void setHideFinanceDetails(boolean isHideFinanceDetails) {
		this.isHideFinanceDetails = isHideFinanceDetails;
	}
	
	
}
