package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Date;

public class CandidateCommonBean implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3570452813405405255L;
	private short				isEmployee;
	private short				isActive;
	private Date				createDate;
	private int					status;
	private String				dateOfOffer;
	private int					id;
	private String				firstName;
	private String				middleName;
	private String				lastName;
	private String				maidenName;
	private String				nationality;
	private String				gender;
	private Date				dob;
	private String				officalEmailId;
	private int					hrSpoc;
	private String				dateOfJoining;
	private Date				dateOfConfirmation;
	private int					noticePeriod;
	private String				empType;
	private String				permanentAddress;
	private String				currentAddress;
	private String				primaryPhoneNo;
	private boolean				primaryPhoneNoNull;
	private String				secondaryPhoneNo;
	private boolean				secondaryPhoneNoNull;
	private String				personalEmailId;
	private String				motherName;
	private String				fatherName;
	private String				maritalStatus;
	private String				spouseName;
	private String				emerContactName;
	private String				emerCpRelationship;
	private int					emerPhoneNo;
	private String				department;
	private String				division;
	private String				designation;
	private String				region;
	private String				statusname;
	private String				offerLetter;
	private String				tatId;
	private boolean				noShow;
	private int					levelId;

	public boolean isNoShow() {
		return noShow;
	}

	public void setNoShow(boolean noShow) {
		this.noShow = noShow;
	}

	public short getIsEmployee() {
		return isEmployee;
	}

	public void setIsEmployee(short isEmployee) {
		this.isEmployee = isEmployee;
	}

	public short getIsActive() {
		return isActive;
	}

	public void setIsActive(short isActive) {
		this.isActive = isActive;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setDateOfOffer(String dateOfOffer) {
		this.dateOfOffer = dateOfOffer;
	}

	public String getDateOfOffer() {
		return dateOfOffer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getOfferLetter() {
		return offerLetter;
	}

	public void setOfferLetter(String offerLetter) {
		this.offerLetter = offerLetter;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getOfficalEmailId() {
		return officalEmailId;
	}

	public void setOfficalEmailId(String officalEmailId) {
		this.officalEmailId = officalEmailId;
	}

	public int getHrSpoc() {
		return hrSpoc;
	}

	public void setHrSpoc(int hrSpoc) {
		this.hrSpoc = hrSpoc;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Date getDateOfConfirmation() {
		return dateOfConfirmation;
	}

	public void setDateOfConfirmation(Date dateOfConfirmation) {
		this.dateOfConfirmation = dateOfConfirmation;
	}

	public int getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(int noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getPrimaryPhoneNo() {
		return primaryPhoneNo;
	}

	public void setPrimaryPhoneNo(String primaryPhoneNo) {
		this.primaryPhoneNo = primaryPhoneNo;
	}

	public boolean isPrimaryPhoneNoNull() {
		return primaryPhoneNoNull;
	}

	public void setPrimaryPhoneNoNull(boolean primaryPhoneNoNull) {
		this.primaryPhoneNoNull = primaryPhoneNoNull;
	}

	public String getSecondaryPhoneNo() {
		return secondaryPhoneNo;
	}

	public void setSecondaryPhoneNo(String secondaryPhoneNo) {
		this.secondaryPhoneNo = secondaryPhoneNo;
	}

	public boolean isSecondaryPhoneNoNull() {
		return secondaryPhoneNoNull;
	}

	public void setSecondaryPhoneNoNull(boolean secondaryPhoneNoNull) {
		this.secondaryPhoneNoNull = secondaryPhoneNoNull;
	}

	public String getPersonalEmailId() {
		return personalEmailId;
	}

	public void setPersonalEmailId(String personalEmailId) {
		this.personalEmailId = personalEmailId;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getEmerContactName() {
		return emerContactName;
	}

	public void setEmerContactName(String emerContactName) {
		this.emerContactName = emerContactName;
	}

	public String getEmerCpRelationship() {
		return emerCpRelationship;
	}

	public void setEmerCpRelationship(String emerCpRelationship) {
		this.emerCpRelationship = emerCpRelationship;
	}

	public int getEmerPhoneNo() {
		return emerPhoneNo;
	}

	public void setEmerPhoneNo(int emerPhoneNo) {
		this.emerPhoneNo = emerPhoneNo;
	}

	public boolean isEmerPhoneNoNull() {
		return emerPhoneNoNull;
	}

	public void setEmerPhoneNoNull(boolean emerPhoneNoNull) {
		this.emerPhoneNoNull = emerPhoneNoNull;
	}

	private boolean	emerPhoneNoNull;

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public int getLevelId() {
		return levelId;
	}

	public String getTatId() {
		return tatId;
	}

	public void setTatId(String tatId) {
		this.tatId = tatId;
	}
}
