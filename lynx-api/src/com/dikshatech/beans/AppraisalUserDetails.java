package com.dikshatech.beans;

public class AppraisalUserDetails {

	private String	name;
	private String	emailId;
	private String	phoneNo;
	private String	dateOfjoining;
	private String	employeeId;
	private String	designation;
	private String	division;
	private String	experiance;
	private String	reviewPeriod;
	private String	totalExp;
	private String	appraiserName;
	private String	appraiserphoneNo;
	private String	appraiserEmailId;
	private String	subject;
	private String	id;

	public AppraisalUserDetails() {}

	public AppraisalUserDetails(String name, String emailId, String dateOfjoining, String appraiserName, String appraiserEmailId) {
		//this.name = name;
		this.emailId = emailId;
		this.dateOfjoining = dateOfjoining;
		this.appraiserEmailId = appraiserEmailId;
		//this.appraiserName = appraiserName;
	}

	public String getName() {
		return name;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public String getDesignation() {
		return designation;
	}

	public String getDivision() {
		return division;
	}

	public String getDateOfjoining() {
		return dateOfjoining;
	}

	public String getExperiance() {
		return experiance;
	}

	public String getReviewPeriod() {
		return reviewPeriod;
	}

	public String getTotalExp() {
		return totalExp;
	}

	public String getAppraiserName() {
		return appraiserName;
	}

	public String getAppraiserphoneNo() {
		return appraiserphoneNo;
	}

	public String getAppraiserEmailId() {
		return appraiserEmailId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public void setDateOfjoining(String dateOfjoining) {
		this.dateOfjoining = dateOfjoining;
	}

	public void setExperiance(String experiance) {
		this.experiance = experiance;
	}

	public void setReviewPeriod(String reviewPeriod) {
		this.reviewPeriod = reviewPeriod;
	}

	public void setTotalExp(String totalExp) {
		this.totalExp = totalExp;
	}

	public void setAppraiserName(String appraiserName) {
		this.appraiserName = appraiserName;
	}

	public void setAppraiserphoneNo(String appraiserphoneNo) {
		this.appraiserphoneNo = appraiserphoneNo;
	}

	public void setAppraiserEmailId(String appraiserEmailId) {
		this.appraiserEmailId = appraiserEmailId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
