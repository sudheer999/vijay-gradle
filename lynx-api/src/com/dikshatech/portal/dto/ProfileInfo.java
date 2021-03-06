/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;
import com.dikshatech.portal.forms.PortalForm;

public class ProfileInfo extends PortalForm implements Serializable {

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * This attribute maps to the column ID in the PROFILE_INFO table.
	 */
	protected int		id;
	/**
	 * This attribute maps to the column FIRST_NAME in the PROFILE_INFO table.
	 */
	protected String	firstName;
	/**
	 * This attribute maps to the column MIDDLE_NAME in the PROFILE_INFO table.
	 */
	protected String	middleName;
	/**
	 * This attribute maps to the column LAST_NAME in the PROFILE_INFO table.
	 */
	protected String	lastName;
	/**
	 * This attribute maps to the column MAIDEN_NAME in the PROFILE_INFO table.
	 */
	protected String	maidenName;
	/**
	 * This attribute maps to the column NATIONALITY in the PROFILE_INFO table.
	 */
	protected String	nationality;
	/**
	 * This attribute maps to the column GENDER in the PROFILE_INFO table.
	 */
	protected String	gender;
	/**
	 * This attribute maps to the column DOB in the PROFILE_INFO table.
	 */
	protected Date		dob;
	/**
	 * This attribute maps to the column OFFICAL_EMAIL_ID in the PROFILE_INFO table.
	 */
	protected String	officalEmailId;
	/**
	 * This attribute maps to the column HR_SPOC in the PROFILE_INFO table.
	 */
	protected int		hrSpoc;
	/**
	 * This attribute represents whether the primitive attribute hrSpoc is null.
	 */
	protected boolean	hrSpocNull			= true;
	/**
	 * This attribute maps to the column REPORTING_MGR in the PROFILE_INFO table.
	 */
	protected int		reportingMgr;
	/**
	 * This attribute represents whether the primitive attribute reportingMgr is null.
	 */
	protected boolean	reportingMgrNull	= true;
	/**
	 * This attribute maps to the column DATE_OF_JOINING in the PROFILE_INFO table.
	 */
	protected Date		dateOfJoining;
	/**
	 * This attribute maps to the column DATE_OF_CONFIRMATION in the PROFILE_INFO table.
	 */
	protected Date		dateOfConfirmation;
	/**
	 * This attribute maps to the column MONTHS in the PROFILE_INFO table.
	 */
	protected String	months;
	/**
	 * This attribute maps to the column DOC in the PROFILE_INFO table.
	 */
	protected Date		doc;
	/**
	 * This attribute maps to the column EXTENSION in the PROFILE_INFO table.
	 */
	protected String	extension;
	/**
	 * This attribute maps to the column DATE_OF_SEPERATION in the PROFILE_INFO table.
	 */
	protected Date		dateOfSeperation;
	/**
	 * This attribute maps to the column NOTICE_PERIOD in the PROFILE_INFO table.
	 */
	protected int		noticePeriod;
	/**
	 * This attribute represents whether the primitive attribute noticePeriod is null.
	 */
	protected boolean	noticePeriodNull	= true;
	/**
	 * This attribute maps to the column EMPLOYEE_TYPE in the PROFILE_INFO table.
	 */
	protected String	employeeType;
	/**
	 * This attribute maps to the column EMP_TYPE in the PROFILE_INFO table.
	 */
	protected String	empType;
	/**
	 * This attribute maps to the column CREATE_DATE in the PROFILE_INFO table.
	 */
	protected Date		createDate;
	/**
	 * This attribute maps to the column LEVEL_ID in the PROFILE_INFO table.
	 */
	protected int		levelId;
	/**
	 * This attribute represents whether the primitive attribute levelId is null.
	 */
	protected boolean	levelIdNull			= true;
	/**
	 * This attribute maps to the column REPORTING_TIME in the PROFILE_INFO table.
	 */
	protected String	reportingTime;
	/**
	 * This attribute maps to the column REPORTING_ADDRESS in the PROFILE_INFO table.
	 */
	protected String	reportingAddress;
	
	
	protected String location;
		
	protected String locationid;
	
	
	protected String category;
	
	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * This attribute maps to the column REPORTING_ADDRESS_NORMAL in the PROFILE_INFO table.
	 */
	protected String	reportingAddressNormal;
	protected String	UserInfoArr[];
	protected int		roleId;
	protected int		empId;
	protected int		projectId;
	protected String	message;
	protected String[]	primaryKeys;
	private String		userList[];
	/**
	 * 
	 * fields for contact type
	 */
	protected String[]	contactType;
	private boolean isHideFinanceDetails;
	private String		employeeName;
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	private boolean hideCategoryDetails;

	

	public boolean isHideCategoryDetails() {
		return hideCategoryDetails;
	}

	public void setHideCategoryDetails(boolean hideCategoryDetails) {
		this.hideCategoryDetails = hideCategoryDetails;
	}

	/**
	 * This attribute maps to the column MODIFIED_BY in the PROFILE_INFO table.
	 */
	protected int	modifiedBy;

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * Method 'ProfileInfo'
	 */
	public ProfileInfo() {}

	/**
	 * Method 'getId'
	 * 
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Method 'getFirstName'
	 * 
	 * @return String
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Method 'setFirstName'
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Method 'getMiddleName'
	 * 
	 * @return String
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Method 'setMiddleName'
	 * 
	 * @param middleName
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * Method 'getLastName'
	 * 
	 * @return String
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Method 'setLastName'
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Method 'getMaidenName'
	 * 
	 * @return String
	 */
	public String getMaidenName() {
		return maidenName;
	}

	/**
	 * Method 'setMaidenName'
	 * 
	 * @param maidenName
	 */
	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	/**
	 * Method 'getNationality'
	 * 
	 * @return String
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * Method 'setNationality'
	 * 
	 * @param nationality
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * Method 'getGender'
	 * 
	 * @return String
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Method 'setGender'
	 * 
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Method 'getDob'
	 * 
	 * @return Date
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Method 'setDob'
	 * 
	 * @param dob
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Method 'getOfficalEmailId'
	 * 
	 * @return String
	 */
	public String getOfficalEmailId() {
		return officalEmailId;
	}

	/**
	 * Method 'setOfficalEmailId'
	 * 
	 * @param officalEmailId
	 */
	public void setOfficalEmailId(String officalEmailId) {
		this.officalEmailId = officalEmailId;
	}

	/**
	 * Method 'getHrSpoc'
	 * 
	 * @return int
	 */
	public int getHrSpoc() {
		return hrSpoc;
	}

	/**
	 * Method 'setHrSpoc'
	 * 
	 * @param hrSpoc
	 */
	public void setHrSpoc(int hrSpoc) {
		this.hrSpoc = hrSpoc;
		this.hrSpocNull = false;
	}

	/**
	 * Method 'setHrSpocNull'
	 * 
	 * @param value
	 */
	public void setHrSpocNull(boolean value) {
		this.hrSpocNull = value;
	}

	/**
	 * Method 'isHrSpocNull'
	 * 
	 * @return boolean
	 */
	public boolean isHrSpocNull() {
		return hrSpocNull;
	}

	/**
	 * Method 'getReportingMgr'
	 * 
	 * @return int
	 */
	public int getReportingMgr() {
		return reportingMgr;
	}

	/**
	 * Method 'setReportingMgr'
	 * 
	 * @param reportingMgr
	 */
	public void setReportingMgr(int reportingMgr) {
		this.reportingMgr = reportingMgr;
		this.reportingMgrNull = false;
	}

	/**
	 * Method 'setReportingMgrNull'
	 * 
	 * @param value
	 */
	public void setReportingMgrNull(boolean value) {
		this.reportingMgrNull = value;
	}

	/**
	 * Method 'isReportingMgrNull'
	 * 
	 * @return boolean
	 */
	public boolean isReportingMgrNull() {
		return reportingMgrNull;
	}

	/**
	 * Method 'getDateOfJoining'
	 * 
	 * @return Date
	 */
	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	/**
	 * Method 'setDateOfJoining'
	 * 
	 * @param dateOfJoining
	 */
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	/**
	 * Method 'getDateOfConfirmation'
	 * 
	 * @return Date
	 */
	public Date getDateOfConfirmation() {
		return dateOfConfirmation;
	}

	/**
	 * Method 'setDateOfConfirmation'
	 * 
	 * @param dateOfConfirmation
	 */
	public void setDateOfConfirmation(Date dateOfConfirmation) {
		this.dateOfConfirmation = dateOfConfirmation;
	}

	/**
	 * Method 'getMonths'
	 * 
	 * @return String
	 */
	public String getMonths() {
		return months;
	}

	/**
	 * Method 'setMonths'
	 * 
	 * @param months
	 */
	public void setMonths(String months) {
		this.months = months;
	}

	/**
	 * Method 'getDoc'
	 * 
	 * @return Date
	 */
	public Date getDoc() {
		return doc;
	}

	/**
	 * Method 'setDoc'
	 * 
	 * @param doc
	 */
	public void setDoc(Date doc) {
		this.doc = doc;
	}

	/**
	 * Method 'getDateOfSeperation'
	 * 
	 * @return Date
	 */
	public Date getDateOfSeperation() {
		return dateOfSeperation;
	}

	/**
	 * Method 'setDateOfSeperation'
	 * 
	 * @param dateOfSeperation
	 */
	public void setDateOfSeperation(Date dateOfSeperation) {
		this.dateOfSeperation = dateOfSeperation;
	}

	/**
	 * Method 'getNoticePeriod'
	 * 
	 * @return int
	 */
	public int getNoticePeriod() {
		return noticePeriod;
	}

	/**
	 * Method 'setNoticePeriod'
	 * 
	 * @param noticePeriod
	 */
	public void setNoticePeriod(int noticePeriod) {
		this.noticePeriod = noticePeriod;
		this.noticePeriodNull = false;
	}

	/**
	 * Method 'setNoticePeriodNull'
	 * 
	 * @param value
	 */
	public void setNoticePeriodNull(boolean value) {
		this.noticePeriodNull = value;
	}

	/**
	 * Method 'isNoticePeriodNull'
	 * 
	 * @return boolean
	 */
	public boolean isNoticePeriodNull() {
		return noticePeriodNull;
	}

	/**
	 * Method 'getEmployeeType'
	 * 
	 * @return String
	 */
	public String getEmployeeType() {
		return employeeType;
	}

	/**
	 * Method 'setEmployeeType'
	 * 
	 * @param employeeType
	 */
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	/**
	 * Method 'getEmpType'
	 * 
	 * @return String
	 */
	public String getEmpType() {
		return empType;
	}

	/**
	 * Method 'setEmpType'
	 * 
	 * @param empType
	 */
	public void setEmpType(String empType) {
		this.empType = empType;
	}

	/**
	 * Method 'getCreateDate'
	 * 
	 * @return Date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Method 'setCreateDate'
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Method 'getLevelId'
	 * 
	 * @return int
	 */
	public int getLevelId() {
		return levelId;
	}

	/**
	 * Method 'setLevelId'
	 * 
	 * @param levelId
	 */
	public void setLevelId(int levelId) {
		this.levelId = levelId;
		this.levelIdNull = false;
	}

	/**
	 * Method 'setLevelIdNull'
	 * 
	 * @param value
	 */
	public void setLevelIdNull(boolean value) {
		this.levelIdNull = value;
	}

	/**
	 * Method 'isLevelIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isLevelIdNull() {
		return levelIdNull;
	}

	/**
	 * Method 'getReportingTime'
	 * 
	 * @return String
	 */
	public String getReportingTime() {
		return reportingTime;
	}

	/**
	 * Method 'setReportingTime'
	 * 
	 * @param reportingTime
	 */
	public void setReportingTime(String reportingTime) {
		this.reportingTime = reportingTime;
	}

	/**
	 * Method 'getReportingAddress'
	 * 
	 * @return byte[]
	 */
	public String getReportingAddress() {
		return reportingAddress;
	}

	/**
	 * Method 'setReportingAddress'
	 * 
	 * @param reportingAddress
	 */
	public void setReportingAddress(String reportingAddress) {
		this.reportingAddress = reportingAddress;
	}

	/**
	 * Method 'getReportingAddressNormal'
	 * 
	 * @return String
	 */
	public String getReportingAddressNormal() {
		return reportingAddressNormal;
	}

	/**
	 * Method 'setReportingAddressNormal'
	 * 
	 * @param reportingAddressNormal
	 */
	public void setReportingAddressNormal(String reportingAddressNormal) {
		this.reportingAddressNormal = reportingAddressNormal;
	}

	public String[] getUserInfoArr() {
		return UserInfoArr;
	}

	public void setUserInfoArr(String[] userInfoArr) {
		UserInfoArr = userInfoArr;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(String[] primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public String[] getContactType() {
		return contactType;
	}

	public void setContactType(String[] contactType) {
		this.contactType = contactType;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other) {
		if (_other == null){
			return false;
		}
		if (_other == this){
			return true;
		}
		if (!(_other instanceof ProfileInfo)){
			return false;
		}
		final ProfileInfo _cast = (ProfileInfo) _other;
		if (id != _cast.id){
			return false;
		}
		if (firstName == null ? _cast.firstName != firstName : !firstName.equals(_cast.firstName)){
			return false;
		}
		if (middleName == null ? _cast.middleName != middleName : !middleName.equals(_cast.middleName)){
			return false;
		}
		if (lastName == null ? _cast.lastName != lastName : !lastName.equals(_cast.lastName)){
			return false;
		}
		if (maidenName == null ? _cast.maidenName != maidenName : !maidenName.equals(_cast.maidenName)){
			return false;
		}
		if (nationality == null ? _cast.nationality != nationality : !nationality.equals(_cast.nationality)){
			return false;
		}
		if (gender == null ? _cast.gender != gender : !gender.equals(_cast.gender)){
			return false;
		}
		if (dob == null ? _cast.dob != dob : !dob.equals(_cast.dob)){
			return false;
		}
		if (officalEmailId == null ? _cast.officalEmailId != officalEmailId : !officalEmailId.equals(_cast.officalEmailId)){
			return false;
		}
		if (hrSpoc != _cast.hrSpoc){
			return false;
		}
		if (hrSpocNull != _cast.hrSpocNull){
			return false;
		}
		if (reportingMgr != _cast.reportingMgr){
			return false;
		}
		if (reportingMgrNull != _cast.reportingMgrNull){
			return false;
		}
		if (dateOfJoining == null ? _cast.dateOfJoining != dateOfJoining : !dateOfJoining.equals(_cast.dateOfJoining)){
			return false;
		}
		if (dateOfConfirmation == null ? _cast.dateOfConfirmation != dateOfConfirmation : !dateOfConfirmation.equals(_cast.dateOfConfirmation)){
			return false;
		}
		if (months == null ? _cast.months != months : !months.equals(_cast.months)){
			return false;
		}
		if (doc == null ? _cast.doc != doc : !doc.equals(_cast.doc)){
			return false;
		}
		if (dateOfSeperation == null ? _cast.dateOfSeperation != dateOfSeperation : !dateOfSeperation.equals(_cast.dateOfSeperation)){
			return false;
		}
		if (noticePeriod != _cast.noticePeriod){
			return false;
		}
		if (noticePeriodNull != _cast.noticePeriodNull){
			return false;
		}
		if (employeeType == null ? _cast.employeeType != employeeType : !employeeType.equals(_cast.employeeType)){
			return false;
		}
		if (empType == null ? _cast.empType != empType : !empType.equals(_cast.empType)){
			return false;
		}
		if (createDate == null ? _cast.createDate != createDate : !createDate.equals(_cast.createDate)){
			return false;
		}
		if (levelId != _cast.levelId){
			return false;
		}
		if (levelIdNull != _cast.levelIdNull){
			return false;
		}
		if (reportingTime == null ? _cast.reportingTime != reportingTime : !reportingTime.equals(_cast.reportingTime)){
			return false;
		}
		if (reportingAddress == null ? _cast.reportingAddress != reportingAddress : !reportingAddress.equals(_cast.reportingAddress)){
			return false;
		}
		if (reportingAddressNormal == null ? _cast.reportingAddressNormal != reportingAddressNormal : !reportingAddressNormal.equals(_cast.reportingAddressNormal)){
			return false;
		}
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode() {
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		if (firstName != null){
			_hashCode = 29 * _hashCode + firstName.hashCode();
		}
		if (middleName != null){
			_hashCode = 29 * _hashCode + middleName.hashCode();
		}
		if (lastName != null){
			_hashCode = 29 * _hashCode + lastName.hashCode();
		}
		if (maidenName != null){
			_hashCode = 29 * _hashCode + maidenName.hashCode();
		}
		if (nationality != null){
			_hashCode = 29 * _hashCode + nationality.hashCode();
		}
		if (gender != null){
			_hashCode = 29 * _hashCode + gender.hashCode();
		}
		if (dob != null){
			_hashCode = 29 * _hashCode + dob.hashCode();
		}
		if (officalEmailId != null){
			_hashCode = 29 * _hashCode + officalEmailId.hashCode();
		}
		_hashCode = 29 * _hashCode + hrSpoc;
		_hashCode = 29 * _hashCode + (hrSpocNull ? 1 : 0);
		_hashCode = 29 * _hashCode + reportingMgr;
		_hashCode = 29 * _hashCode + (reportingMgrNull ? 1 : 0);
		if (dateOfJoining != null){
			_hashCode = 29 * _hashCode + dateOfJoining.hashCode();
		}
		if (dateOfConfirmation != null){
			_hashCode = 29 * _hashCode + dateOfConfirmation.hashCode();
		}
		if (months != null){
			_hashCode = 29 * _hashCode + months.hashCode();
		}
		if (doc != null){
			_hashCode = 29 * _hashCode + doc.hashCode();
		}
		if (dateOfSeperation != null){
			_hashCode = 29 * _hashCode + dateOfSeperation.hashCode();
		}
		_hashCode = 29 * _hashCode + noticePeriod;
		_hashCode = 29 * _hashCode + (noticePeriodNull ? 1 : 0);
		if (employeeType != null){
			_hashCode = 29 * _hashCode + employeeType.hashCode();
		}
		if (empType != null){
			_hashCode = 29 * _hashCode + empType.hashCode();
		}
		if (createDate != null){
			_hashCode = 29 * _hashCode + createDate.hashCode();
		}
		_hashCode = 29 * _hashCode + levelId;
		_hashCode = 29 * _hashCode + (levelIdNull ? 1 : 0);
		if (reportingTime != null){
			_hashCode = 29 * _hashCode + reportingTime.hashCode();
		}
		if (reportingAddress != null){
			_hashCode = 29 * _hashCode + reportingAddress.hashCode();
		}
		if (reportingAddressNormal != null){
			_hashCode = 29 * _hashCode + reportingAddressNormal.hashCode();
		}
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ProfileInfoPk
	 */
	public ProfileInfoPk createPk() {
		return new ProfileInfoPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.ProfileInfo: ");
		ret.append("id=" + id);
		ret.append(", firstName=" + firstName);
		ret.append(", middleName=" + middleName);
		ret.append(", lastName=" + lastName);
		ret.append(", maidenName=" + maidenName);
		ret.append(", nationality=" + nationality);
		ret.append(", gender=" + gender);
		ret.append(", dob=" + dob);
		ret.append(", officalEmailId=" + officalEmailId);
		ret.append(", hrSpoc=" + hrSpoc);
		ret.append(", reportingMgr=" + reportingMgr);
		ret.append(", dateOfJoining=" + dateOfJoining);
		ret.append(", dateOfConfirmation=" + dateOfConfirmation);
		ret.append(", months=" + months);
		ret.append(", doc=" + doc);
		ret.append(", dateOfSeperation=" + dateOfSeperation);
		ret.append(", noticePeriod=" + noticePeriod);
		ret.append(", employeeType=" + employeeType);
		ret.append(", empType=" + empType);
		ret.append(", createDate=" + createDate);
		ret.append(", levelId=" + levelId);
		ret.append(", reportingTime=" + reportingTime);
		ret.append(", reportingAddress=" + reportingAddress);
		ret.append(", reportingAddressNormal=" + reportingAddressNormal);
		return ret.toString();
	}

	public String[] getUserList() {
		return userList;
	}

	public void setUserList(String userList[]) {
		this.userList = userList;
	}

	public boolean isHideFinanceDetails() {
		return isHideFinanceDetails;
	}

	public void setHideFinanceDetails(boolean isHideFinanceDetails) {
		this.isHideFinanceDetails = isHideFinanceDetails;
	}
}
