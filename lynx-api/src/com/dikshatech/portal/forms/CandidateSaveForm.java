package com.dikshatech.portal.forms;

import java.util.Date;

import com.dikshatech.beans.Salary;
import com.dikshatech.beans.SalaryInfoBean;

public class CandidateSaveForm extends PortalForm {

	/**
	 * 
	 */
	private static final long					serialVersionUID		= 1L;
	private String								firstName;
	private String								middleName;
	private String								lastName;
	private String								maidenName;
	private String								nationality;
	private String								gender;
	private Date								dob;
	private String								officalEmailId;
	private int									hrSpoc;
	protected int								reportingMgr;
	private Date								dateOfJoining;
	private Date								dateOfConfirmation;
	private Date								dateOfSeperation;
	private int									noticePeriod;
	private String								empType;
	protected Date								createDate;
	int											levelId;
	private String								department;
	private int									permenantAddress;
	private int									currentAddress;
	private String								reportingMgrName;
	private String								hrSpocName;
	private String								division;
	private String								designation;
	private String								region;
	private String								level;
	private String								role;
	private String								Company;
	private String								mainregion;
	private int									departmentId;
	private int									divisionId;
	private int									designationId;
	private int									regionId;
	private int									mainregionId;
	private int									companyId;
	private String								permAddress;
	protected String							city;
	protected int								zipCode;
	protected String							country;
	protected String							state;
	private String								primaryPhoneNo;
	private boolean								primaryPhoneNoNull;
	private String								secondaryPhoneNo;
	private boolean								secondaryPhoneNoNull;
	private String								personalEmailId;
	private String								alternateEmailId;
	private String								motherName;
	private String								fatherName;
	private String								maritalStatus;
	private String								spouseName;
	private String								emerContactName;
	private String								emerCpRelationship;
	private Long								emerPhoneNo;
	private String								curAddress;
	protected String							currcity;
	protected int								currzipCode;
	protected String							currcountry;
	protected String							currstate;
	private int									id;
	/*private int candidateId;*/
	private int									userId;
	private String								fieldLabel;
	private String								fields[];
	/*private ProfileInfo profileInfo;
	private PersonalInfo personalInfo ;
	private SalaryDetails salaryDetails ;*/
	private int									roleId;
	private int									approveOffer;
	private int									rejectOffer;
	private String								comments;
	private int									projectId;
	//fields of commitment table
	protected String							commitment;
	protected Date								datePicker;
	protected String[]							commitments;
	private com.dikshatech.beans.CommitmentBean	commitmentbean[];
	protected String							reportingTime;
	protected String							reportingAddress;
	protected String							reportingAddressNormal;
	boolean										sendforapprovalPreview	= false;
	boolean										preview					= false;
	private int									statusId;
	private String								relocationBonus;
	private String								joiningBonusString;
	private String								retentionBonus;
	private String								perdiemString;
	private String								joiningBonusAmount;
	private String								paymentTerms;
	private SalaryInfoBean						salaryInfoBean[];
	/**
	 * New fields added for salary restructure
	 */
	private String								ctcAmount;
	private String								perdiemAmount;
	private Salary[]							salary;
	private String								candidatePerdiemDetails;			//CANDIDATE_PERDIEM_DETAILS format followed   locationName1~=~perdiemDetails~;~locationNAme2~=~perdiemDetails2
	private String								plb;
	private String                              fixedPerdiemAmount;
	private String 								retentionInstallments;
	private String								perdiemOffered;
	private String								relocationCity;
	private int	 perdiemType;

	public String getPerdiemOffered() {
		return perdiemOffered;
	}

	public void setPerdiemOffered(String perdiemOffered) {
		this.perdiemOffered = perdiemOffered;
	}

	public String getRetentionInstallments() {
		return retentionInstallments;
	}

	public void setRetentionInstallments(String retentionInstallments) {
		this.retentionInstallments = retentionInstallments;
	}

	public String getPlb() {
		return plb;
	}

	public void setPlb(String plb) {
		this.plb = plb;
	}

	public Salary[] getSalary() {
		return salary;
	}

	public void setSalary(Salary[] salary) {
		this.salary = salary;
	}

	public String getCtcAmount() {
		return ctcAmount;
	}

	public void setCtcAmount(String ctcAmount) {
		this.ctcAmount = ctcAmount;
	}


	public com.dikshatech.beans.CommitmentBean[] getCommitmentbean() {
		return commitmentbean;
	}

	public void setCommitmentbean(com.dikshatech.beans.CommitmentBean[] commitmentbean) {
		this.commitmentbean = commitmentbean;
	}

	public String getCommitment() {
		return commitment;
	}

	public void setCommitment(String commitment) {
		this.commitment = commitment;
	}

	public Date getDatePicker() {
		return datePicker;
	}

	public void setDatePicker(Date datePicker) {
		this.datePicker = datePicker;
	}

	public String[] getCommitments() {
		return commitments;
	}

	public void setCommitments(String[] commitments) {
		this.commitments = commitments;
	}

	public int getApproveOffer() {
		return approveOffer;
	}

	public int getRejectOffer() {
		return rejectOffer;
	}

	public String getComments() {
		return comments;
	}

	public void setApproveOffer(int approveOffer) {
		this.approveOffer = approveOffer;
	}

	public void setRejectOffer(int rejectOffer) {
		this.rejectOffer = rejectOffer;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public CandidateSaveForm() {
		/*setProfileInfo();
		setPersonalInfo();
		setSalaryDetails();*/
	}

	/*public ProfileInfo getProfileInfo()
	{
		return profileInfo;
	}
	public void setProfileInfo()
	{
		profileInfo=  new ProfileInfo();
		profileInfo.setFirstName(firstName);
		profileInfo.setMiddleName(middleName);
		profileInfo.setLastName(lastName);
		profileInfo.setMaidenName(maidenName);
		profileInfo.setNationality(nationality);
		profileInfo.setGender(gender);
		profileInfo.setDob(dob);
		
		profileInfo.setOfficalEmailId(officalEmailId);
		profileInfo.setHrSpoc(hrSpoc);
		profileInfo.setReportingMgr(reportingMgr);
		
		profileInfo.setDateOfConfirmation(dateOfConfirmation);
		profileInfo.setDateOfJoining(dateOfJoining);
		profileInfo.setNoticePeriod(noticePeriod);
		profileInfo.setEmpType(empType);
		profileInfo.setCreateDate(createDate);
		profileInfo.setLevelId(levelId);
		this.profileInfo = profileInfo;
		
	}
	public PersonalInfo getPersonalInfo()
	{
		return personalInfo;
	}
	public void setPersonalInfo()
	{
		personalInfo= new PersonalInfo();
		personalInfo.setCurrentAddress(currentAddress);
		personalInfo.setPermanentAddress(permanentAddress);
		personalInfo.setCurrentAddress(currentAddress  );
		personalInfo.setPrimaryPhoneNo(primaryPhoneNo);
		personalInfo.setSecondaryPhoneNo(secondaryPhoneNo);
		personalInfo.setPersonalEmailId(personalEmailId);
		personalInfo.setMotherName(motherName);
		personalInfo.setFatherName(fatherName);
		personalInfo.setMaritalStatus(maritalStatus);
		personalInfo.setSpouseName(spouseName);
		personalInfo.setEmerContactName(emerContactName);
		personalInfo.setEmerCpRelationship(emerCpRelationship);
		personalInfo.setEmerPhoneNo(emerPhoneNo);
		personalInfo.setCity(city);candidateSaveForm
		personalInfo.setZipCode(zipCode);
		personalInfo.setCountry(country);
		personalInfo.setState(state);
		
		
		
		this.personalInfo = personalInfo;
	}
	public SalaryDetails getSalaryDetails()
	{
		return salaryDetails;
	}
	public void setSalaryDetails()
	{
		 salaryDetails= new SalaryDetails();
		 salaryDetails.setCandidateId(candidateId);
		 salaryDetails.setUserId(userId);
		 salaryDetails.setFields(fields);
		 salaryDetails.setFieldLabel(fieldLabel);
		 this.salaryDetails = salaryDetails;
	}*/
	public int getReportingMgr() {
		return reportingMgr;
	}

	public void setReportingMgr(int reportingMgr) {
		this.reportingMgr = reportingMgr;
	}

	public String getAlternateEmailId() {
		return alternateEmailId;
	}

	public void setAlternateEmailId(String alternateEmailId) {
		this.alternateEmailId = alternateEmailId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getReportingTime() {
		return reportingTime;
	}

	public void setReportingTime(String reportingTime) {
		this.reportingTime = reportingTime;
	}

	public String getReportingAddress() {
		return reportingAddress;
	}

	public void setReportingAddress(String reportingAddress) {
		this.reportingAddress = reportingAddress;
	}

	public String getReportingAddressNormal() {
		return reportingAddressNormal;
	}

	public void setReportingAddressNormal(String reportingAddressNormal) {
		this.reportingAddressNormal = reportingAddressNormal;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}

	public int getDesignationId() {
		return designationId;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public String getReportingMgrName() {
		return reportingMgrName;
	}

	public void setReportingMgrName(String reportingMgrName) {
		this.reportingMgrName = reportingMgrName;
	}

	public int getMainregionId() {
		return mainregionId;
	}

	public void setMainregionId(int mainregionId) {
		this.mainregionId = mainregionId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getPermenantAddress() {
		return permenantAddress;
	}

	public void setPermenantAddress(int permenantAddrress) {
		this.permenantAddress = permenantAddrress;
	}

	public String getHrSpocName() {
		return hrSpocName;
	}

	public void setHrSpocName(String hrSpocName) {
		this.hrSpocName = hrSpocName;
	}

	public int getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(int currentAddress) {
		this.currentAddress = currentAddress;
	}

	public void setMainregion(String mainregion) {
		this.mainregion = mainregion;
	}

	public String getMainregion() {
		return mainregion;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPerdiemString() {
		return perdiemString;
	}

	public void setPerdiemString(String perdiemString) {
		this.perdiemString = perdiemString;
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

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Date getDateOfConfirmation() {
		return dateOfConfirmation;
	}

	public void setDateOfConfirmation(Date dateOfConfirmation) {
		this.dateOfConfirmation = dateOfConfirmation;
	}

	public Date getDateOfSeperation() {
		return dateOfSeperation;
	}

	public void setDateOfSeperation(Date dateOfSeperation) {
		this.dateOfSeperation = dateOfSeperation;
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

	public String getPermAddress() {
		return permAddress;
	}

	public void setPermAddress(String permAddress) {
		this.permAddress = permAddress;
	}

	public String getCurAddress() {
		return curAddress;
	}

	public void setCurAddress(String curAddress) {
		this.curAddress = curAddress;
	}

	public String getCurrcity() {
		return currcity;
	}

	public void setCurrcity(String currcity) {
		this.currcity = currcity;
	}

	public int getCurrzipCode() {
		return currzipCode;
	}

	public void setCurrzipCode(int currzipCode) {
		this.currzipCode = currzipCode;
	}

	public String getCurrcountry() {
		return currcountry;
	}

	public void setCurrcountry(String currcountry) {
		this.currcountry = currcountry;
	}

	public String getCurrstate() {
		return currstate;
	}

	public void setCurrstate(String currstate) {
		this.currstate = currstate;
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

	public Long getEmerPhoneNo() {
		return emerPhoneNo;
	}

	public void setEmerPhoneNo(Long emerPhoneNo) {
		this.emerPhoneNo = emerPhoneNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/*public void setProfileInfo(ProfileInfo profileInfo)
	{
		this.profileInfo = profileInfo;
	}

	public void setPersonalInfo(PersonalInfo personalInfo)
	{
		this.personalInfo = personalInfo;
	}

	public void setSalaryDetails(SalaryDetails salaryDetails)
	{
		this.salaryDetails = salaryDetails;
	}*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getProjectId() {
		return projectId;
	}

	public boolean isSendforapprovalPreview() {
		return sendforapprovalPreview;
	}

	public void setSendforapprovalPreview(boolean sendforapprovalPreview) {
		this.sendforapprovalPreview = sendforapprovalPreview;
	}

	public boolean isPreview() {
		return preview;
	}

	public void setPreview(boolean preview) {
		this.preview = preview;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getStatusId() {
		return statusId;
	}

	public String getRelocationBonus() {
		return relocationBonus;
	}

	public void setRelocationBonus(String relocationBonus) {
		this.relocationBonus = relocationBonus;
	}

	public String getJoiningBonusString() {
		return joiningBonusString;
	}

	public void setJoiningBonusString(String joiningBonusString) {
		this.joiningBonusString = joiningBonusString;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public SalaryInfoBean[] getSalaryInfoBean() {
		return salaryInfoBean;
	}

	public void setSalaryInfoBean(SalaryInfoBean[] salaryInfoBean) {
		this.salaryInfoBean = salaryInfoBean;
	}

	public void setCandidatePerdiemDetails(String candidatePerdiemDetails) {
		this.candidatePerdiemDetails = candidatePerdiemDetails;
	}

	public String getCandidatePerdiemDetails() {
		return candidatePerdiemDetails;
	}

	public String getJoiningBonusAmount() {
		return joiningBonusAmount;
	}

	public void setJoiningBonusAmount(String joiningBonusAmount) {
		this.joiningBonusAmount = joiningBonusAmount;
	}

	public String getPerdiemAmount() {
		try{
			return perdiemAmount;
		} catch (Exception e){}
		return perdiemAmount;
	}

	public void setPerdiemAmount(String perdiemAmount) {
		this.perdiemAmount = perdiemAmount;
	}

	public String getRetentionBonus() {
		return retentionBonus;
	}

	public void setRetentionBonus(String retentionBonus) {
		this.retentionBonus = retentionBonus;
	}

	public String getFixedPerdiemAmount() {
		return fixedPerdiemAmount;
	}

	public void setFixedPerdiemAmount(String fixedPerdiemAmount) {
		this.fixedPerdiemAmount = fixedPerdiemAmount;
	}

	public String getRelocationCity() {
		return relocationCity;
	}

	public void setRelocationCity(String relocationCity) {
		this.relocationCity = relocationCity;
	}

	public int getPerdiemType() {
		return perdiemType;
	}

	public void setPerdiemType(int perdiemType) {
		this.perdiemType = perdiemType;
	}
}
