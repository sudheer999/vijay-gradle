/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.beans.CommitmentBean;
import com.dikshatech.beans.EmpserReqMapBean;
import com.dikshatech.portal.forms.PortalForm;

public class Users extends PortalForm implements Serializable {

	/**
	 * This attribute maps to the column ID in the USERS table.
	 */
	protected int					id;
	/**
	 * This attribute maps to the column EMP_ID in the USERS table.
	 */
	protected int					empId;
	/**
	 * This attribute maps to the column LEVEL_ID in the USERS table.
	 */
	protected int					levelId;
	/**
	 * This attribute maps to the column REG_DIV_ID in the USERS table.
	 */
	protected int					regDivId;
	/**
	 * This attribute represents whether the primitive attribute regDivId is null.
	 */
	protected boolean				regDivIdNull		= true;
	/**
	 * This attribute maps to the column PROFILE_ID in the USERS table.
	 */
	protected int					profileId;
	/**
	 * This attribute represents whether the primitive attribute profileId is null.
	 */
	protected boolean				profileIdNull		= true;
	/**
	 * This attribute maps to the column FINANCE_ID in the USERS table.
	 */
	protected int					financeId;
	/**
	 * This attribute represents whether the primitive attribute financeId is null.
	 */
	protected boolean				financeIdNull		= true;
	/**
	 * This attribute maps to the column NOMINEE_ID in the USERS table.
	 */
	protected int					nomineeId;
	/**
	 * This attribute represents whether the primitive attribute nomineeId is null.
	 */
	protected boolean				nomineeIdNull		= true;
	/**
	 * This attribute maps to the column PASSPORT_ID in the USERS table.
	 */
	protected int					passportId;
	/**
	 * This attribute represents whether the primitive attribute passportId is null.
	 */
	protected boolean				passportIdNull		= true;
	/**
	 * This attribute maps to the column PERSONAL_ID in the USERS table.
	 */
	protected int					personalId;
	/**
	 * This attribute represents whether the primitive attribute personalId is null.
	 */
	protected boolean				personalIdNull		= true;
	/**
	 * This attribute maps to the column COMPLETE in the USERS table.
	 */
	private short					complete;
	/**
	 * This attribute maps to the column STATUS in the USERS table.
	 */
	protected short					status;
	/**
	 * This attribute maps to the column CREATE_DATE in the USERS table.
	 */
	protected Date					createDate;
	/**
	 * This attribute maps to the column USER_CREATED_BY in the USERS table.
	 */
	protected int					userCreatedBy;
	/**
	 * This attribute represents whether the primitive attribute userCreatedBy is null.
	 */
	protected boolean				userCreatedByNull	= true;
	/**
	 * This attribute maps to the column EXPERIENCE_ID in the USERS table.
	 */
	protected int					experienceId;
	/**
	 * This attribute represents whether the primitive attribute experienceId is null.
	 */
	protected boolean				experienceIdNull	= true;
	/**
	 * This attribute maps to the column SKILLSET_ID in the USERS table.
	 */
	protected String				skillsetId;
	/**
	 * This attribute maps to the column OTHERS in the USERS table.
	 */
	protected String				others;
	/**
	 * This attribute maps to the column ACTION_BY in the USERS table.
	 */
	protected int					actionBy;
	/**
	 * This attribute represents whether the primitive attribute actionBy is null.
	 */
	protected boolean				actionByNull		= true;
	protected SkillSet				skillsArr[];
	protected boolean				educationIdNull		= true;
	protected boolean				skillsetIdNull		= true;
	protected Date					dateOfSeperation;
	protected int					docid;
	protected CommitmentBean[]		commitmentBean;
	protected ProfileInfo			profileInfo;
	protected ProjectMapping		projectMapping;
	protected String				createdBy;
	protected Date					commitmentDate;
	protected String				seperatedString;
	protected boolean				isLoan;
	protected EmpserReqMapBean[]	empserReqMapBeans;
	private String					payableDays;
	private String[]				userIds;
	protected int					bgc_Seperate;
	



	public int getBgc_Seperate() {
		return bgc_Seperate;
	}

	public void setBgc_Seperate(int bgc_Seperate) {
		this.bgc_Seperate = bgc_Seperate;
	}

	/**
	 * Method 'Users'
	 */
	public Users() {}

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
	 * Method 'getEmpId'
	 * 
	 * @return int
	 */
	public int getEmpId() {
		return empId;
	}

	/**
	 * Method 'setEmpId'
	 * 
	 * @param empId
	 */
	public void setEmpId(int empId) {
		this.empId = empId;
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
	}

	/**
	 * Method 'getRegDivId'
	 * 
	 * @return int
	 */
	public int getRegDivId() {
		return regDivId;
	}

	/**
	 * Method 'setRegDivId'
	 * 
	 * @param regDivId
	 */
	public void setRegDivId(int regDivId) {
		this.regDivId = regDivId;
		this.regDivIdNull = false;
	}

	/**
	 * Method 'setRegDivIdNull'
	 * 
	 * @param value
	 */
	public void setRegDivIdNull(boolean value) {
		this.regDivIdNull = value;
	}

	/**
	 * Method 'isRegDivIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isRegDivIdNull() {
		return regDivIdNull;
	}

	/**
	 * Method 'getProfileId'
	 * 
	 * @return int
	 */
	public int getProfileId() {
		return profileId;
	}

	/**
	 * Method 'setProfileId'
	 * 
	 * @param profileId
	 */
	public void setProfileId(int profileId) {
		this.profileId = profileId;
		this.profileIdNull = false;
	}

	/**
	 * Method 'setProfileIdNull'
	 * 
	 * @param value
	 */
	public void setProfileIdNull(boolean value) {
		this.profileIdNull = value;
	}

	/**
	 * Method 'isProfileIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isProfileIdNull() {
		return profileIdNull;
	}

	/**
	 * Method 'getFinanceId'
	 * 
	 * @return int
	 */
	public int getFinanceId() {
		return financeId;
	}

	/**
	 * Method 'setFinanceId'
	 * 
	 * @param financeId
	 */
	public void setFinanceId(int financeId) {
		this.financeId = financeId;
		this.financeIdNull = false;
	}

	/**
	 * Method 'setFinanceIdNull'
	 * 
	 * @param value
	 */
	public void setFinanceIdNull(boolean value) {
		this.financeIdNull = value;
	}

	/**
	 * Method 'isFinanceIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isFinanceIdNull() {
		return financeIdNull;
	}

	/**
	 * Method 'getNomineeId'
	 * 
	 * @return int
	 */
	public int getNomineeId() {
		return nomineeId;
	}

	/**
	 * Method 'setNomineeId'
	 * 
	 * @param nomineeId
	 */
	public void setNomineeId(int nomineeId) {
		this.nomineeId = nomineeId;
		this.nomineeIdNull = false;
	}

	/**
	 * Method 'setNomineeIdNull'
	 * 
	 * @param value
	 */
	public void setNomineeIdNull(boolean value) {
		this.nomineeIdNull = value;
	}

	/**
	 * Method 'isNomineeIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isNomineeIdNull() {
		return nomineeIdNull;
	}

	/**
	 * Method 'getPassportId'
	 * 
	 * @return int
	 */
	public int getPassportId() {
		return passportId;
	}

	/**
	 * Method 'setPassportId'
	 * 
	 * @param passportId
	 */
	public void setPassportId(int passportId) {
		this.passportId = passportId;
		this.passportIdNull = false;
	}

	/**
	 * Method 'setPassportIdNull'
	 * 
	 * @param value
	 */
	public void setPassportIdNull(boolean value) {
		this.passportIdNull = value;
	}

	/**
	 * Method 'isPassportIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isPassportIdNull() {
		return passportIdNull;
	}

	/**
	 * Method 'getPersonalId'
	 * 
	 * @return int
	 */
	public int getPersonalId() {
		return personalId;
	}

	/**
	 * Method 'setPersonalId'
	 * 
	 * @param personalId
	 */
	public void setPersonalId(int personalId) {
		this.personalId = personalId;
		this.personalIdNull = false;
	}

	/**
	 * Method 'setPersonalIdNull'
	 * 
	 * @param value
	 */
	public void setPersonalIdNull(boolean value) {
		this.personalIdNull = value;
	}

	/**
	 * Method 'isPersonalIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isPersonalIdNull() {
		return personalIdNull;
	}

	/**
	 * Method 'getStatus'
	 * 
	 * @return short
	 */
	public short getStatus() {
		return status;
	}

	/**
	 * Method 'setStatus'
	 * 
	 * @param status
	 */
	public void setStatus(short status) {
		this.status = status;
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
	 * Method 'getUserCreatedBy'
	 * 
	 * @return int
	 */
	public int getUserCreatedBy() {
		return userCreatedBy;
	}

	/**
	 * Method 'setUserCreatedBy'
	 * 
	 * @param userCreatedBy
	 */
	public void setUserCreatedBy(int userCreatedBy) {
		this.userCreatedBy = userCreatedBy;
		this.userCreatedByNull = false;
	}

	/**
	 * Method 'setUserCreatedByNull'
	 * 
	 * @param value
	 */
	public void setUserCreatedByNull(boolean value) {
		this.userCreatedByNull = value;
	}

	/**
	 * Method 'isUserCreatedByNull'
	 * 
	 * @return boolean
	 */
	public boolean isUserCreatedByNull() {
		return userCreatedByNull;
	}

	/**
	 * Method 'getExperienceId'
	 * 
	 * @return int
	 */
	public int getExperienceId() {
		return experienceId;
	}

	/**
	 * Method 'setExperienceId'
	 * 
	 * @param experienceId
	 */
	public void setExperienceId(int experienceId) {
		this.experienceId = experienceId;
		this.experienceIdNull = false;
	}

	/**
	 * Method 'setExperienceIdNull'
	 * 
	 * @param value
	 */
	public void setExperienceIdNull(boolean value) {
		this.experienceIdNull = value;
	}

	/**
	 * Method 'isExperienceIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isExperienceIdNull() {
		return experienceIdNull;
	}

	/**
	 * Method 'getSkillsetId'
	 * 
	 * @return String
	 */
	public String getSkillsetId() {
		return skillsetId;
	}

	/**
	 * Method 'setSkillsetId'
	 * 
	 * @param skillsetId
	 */
	public void setSkillsetId(String skillsetId) {
		this.skillsetId = skillsetId;
	}

	/**
	 * Method 'getOthers'
	 * 
	 * @return String
	 */
	public String getOthers() {
		return others;
	}

	/**
	 * Method 'setOthers'
	 * 
	 * @param others
	 */
	public void setOthers(String others) {
		this.others = others;
	}

	/**
	 * Method 'getActionBy'
	 * 
	 * @return int
	 */
	public int getActionBy() {
		return actionBy;
	}

	/**
	 * Method 'setActionBy'
	 * 
	 * @param actionBy
	 */
	public void setActionBy(int actionBy) {
		this.actionBy = actionBy;
		this.actionByNull = false;
	}

	/**
	 * Method 'setActionByNull'
	 * 
	 * @param value
	 */
	public void setActionByNull(boolean value) {
		this.actionByNull = value;
	}

	/**
	 * Method 'isActionByNull'
	 * 
	 * @return boolean
	 */
	public boolean isActionByNull() {
		return actionByNull;
	}

	public SkillSet[] getSkillsArr() {
		return skillsArr;
	}

	public void setSkillsArr(SkillSet[] skillsArr) {
		this.skillsArr = skillsArr;
	}

	public boolean isEducationIdNull() {
		return educationIdNull;
	}

	public void setEducationIdNull(boolean educationIdNull) {
		this.educationIdNull = educationIdNull;
	}

	public boolean isSkillsetIdNull() {
		return skillsetIdNull;
	}

	public void setSkillsetIdNull(boolean skillsetIdNull) {
		this.skillsetIdNull = skillsetIdNull;
	}

	public Date getDateOfSeperation() {
		return dateOfSeperation;
	}

	public void setDateOfSeperation(Date dateOfSeperation) {
		this.dateOfSeperation = dateOfSeperation;
	}

	public int getDocid() {
		return docid;
	}

	public void setDocid(int docid) {
		this.docid = docid;
	}

	public CommitmentBean[] getCommitmentBean() {
		return commitmentBean;
	}

	public void setCommitmentBean(CommitmentBean[] commitmentBean) {
		this.commitmentBean = commitmentBean;
	}

	public ProfileInfo getProfileInfo() {
		return profileInfo;
	}

	public void setProfileInfo(ProfileInfo profileInfo) {
		this.profileInfo = profileInfo;
	}

	public ProjectMapping getProjectMapping() {
		return projectMapping;
	}

	public void setProjectMapping(ProjectMapping projectMapping) {
		this.projectMapping = projectMapping;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCommitmentDate() {
		return commitmentDate;
	}

	public void setCommitmentDate(Date commitmentDate) {
		this.commitmentDate = commitmentDate;
	}

	public String getSeperatedString() {
		return seperatedString;
	}

	public void setSeperatedString(String seperatedString) {
		this.seperatedString = seperatedString;
	}

	public boolean isLoan() {
		return isLoan;
	}

	public void setLoan(boolean isLoan) {
		this.isLoan = isLoan;
	}

	public EmpserReqMapBean[] getEmpserReqMapBeans() {
		return empserReqMapBeans;
	}

	public void setEmpserReqMapBeans(EmpserReqMapBean[] empserReqMapBeans) {
		this.empserReqMapBeans = empserReqMapBeans;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other) {
		if (_other == null){ return false; }
		if (_other == this){ return true; }
		if (!(_other instanceof Users)){ return false; }
		final Users _cast = (Users) _other;
		if (id != _cast.id){ return false; }
		if (empId != _cast.empId){ return false; }
		if (levelId != _cast.levelId){ return false; }
		if (regDivId != _cast.regDivId){ return false; }
		if (regDivIdNull != _cast.regDivIdNull){ return false; }
		if (profileId != _cast.profileId){ return false; }
		if (profileIdNull != _cast.profileIdNull){ return false; }
		if (financeId != _cast.financeId){ return false; }
		if (financeIdNull != _cast.financeIdNull){ return false; }
		if (nomineeId != _cast.nomineeId){ return false; }
		if (nomineeIdNull != _cast.nomineeIdNull){ return false; }
		if (passportId != _cast.passportId){ return false; }
		if (passportIdNull != _cast.passportIdNull){ return false; }
		if (personalId != _cast.personalId){ return false; }
		if (personalIdNull != _cast.personalIdNull){ return false; }
		if (complete != _cast.complete){ return false; }
		if (status != _cast.status){ return false; }
		if (createDate == null ? _cast.createDate != createDate : !createDate.equals(_cast.createDate)){ return false; }
		if (userCreatedBy != _cast.userCreatedBy){ return false; }
		if (userCreatedByNull != _cast.userCreatedByNull){ return false; }
		if (experienceId != _cast.experienceId){ return false; }
		if (experienceIdNull != _cast.experienceIdNull){ return false; }
		if (skillsetId == null ? _cast.skillsetId != skillsetId : !skillsetId.equals(_cast.skillsetId)){ return false; }
		if (others == null ? _cast.others != others : !others.equals(_cast.others)){ return false; }
		if (actionBy != _cast.actionBy){ return false; }
		if (actionByNull != _cast.actionByNull){ return false; }
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
		_hashCode = 29 * _hashCode + empId;
		_hashCode = 29 * _hashCode + levelId;
		_hashCode = 29 * _hashCode + regDivId;
		_hashCode = 29 * _hashCode + (regDivIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + profileId;
		_hashCode = 29 * _hashCode + (profileIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + financeId;
		_hashCode = 29 * _hashCode + (financeIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + nomineeId;
		_hashCode = 29 * _hashCode + (nomineeIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + passportId;
		_hashCode = 29 * _hashCode + (passportIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + personalId;
		_hashCode = 29 * _hashCode + (personalIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + complete;
		_hashCode = 29 * _hashCode + (int) status;
		if (createDate != null){
			_hashCode = 29 * _hashCode + createDate.hashCode();
		}
		_hashCode = 29 * _hashCode + userCreatedBy;
		_hashCode = 29 * _hashCode + (userCreatedByNull ? 1 : 0);
		_hashCode = 29 * _hashCode + experienceId;
		_hashCode = 29 * _hashCode + (experienceIdNull ? 1 : 0);
		if (skillsetId != null){
			_hashCode = 29 * _hashCode + skillsetId.hashCode();
		}
		if (others != null){
			_hashCode = 29 * _hashCode + others.hashCode();
		}
		_hashCode = 29 * _hashCode + actionBy;
		_hashCode = 29 * _hashCode + (actionByNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return UsersPk
	 */
	public UsersPk createPk() {
		return new UsersPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.Users: ");
		ret.append("id=" + id);
		ret.append(", empId=" + empId);
		ret.append(", levelId=" + levelId);
		ret.append(", regDivId=" + regDivId);
		ret.append(", profileId=" + profileId);
		ret.append(", financeId=" + financeId);
		ret.append(", nomineeId=" + nomineeId);
		ret.append(", passportId=" + passportId);
		ret.append(", personalId=" + personalId);
		ret.append(", complete=" + complete);
		ret.append(", status=" + status);
		ret.append(", createDate=" + createDate);
		ret.append(", userCreatedBy=" + userCreatedBy);
		ret.append(", experienceId=" + experienceId);
		ret.append(", skillsetId=" + skillsetId);
		ret.append(", others=" + others);
		ret.append(", actionBy=" + actionBy);
		return ret.toString();
	}

	public String getPayableDays() {
		return payableDays;
	}

	public void setPayableDays(String payableDays) {
		this.payableDays = payableDays;
	}

	public String[] getUserIds() {
		return userIds;
	}

	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}

	public short getComplete() {
		return complete;
	}

	public void setComplete(short complete) {
		this.complete = complete;
	}
	int  emplid;
	int empName;
	int emailId;
	int contactNo;
	int project;
	int designation;
	int chargeCode;
	int ctc;
	int education;
	int experience;
	int client;
	int annualCtc;
	int location;


	public int getLocation() {
	//	return 1;
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getEmplid() {
		return emplid;
	}

	public void setEmplid(int emplid) {
		this.emplid = emplid;
	}

	public int getEmpName() {
		return empName;
	}

	public void setEmpName(int empName) {
		this.empName = empName;
	}

	public int getEmailId() {
		return emailId;
	}

	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}

	public int getContactNo() {
		return contactNo;
	}

	public void setContactNo(int contactNo) {
		this.contactNo = contactNo;
	}

	public int getProject() {
		return project;
	}

	public void setProject(int project) {
		this.project = project;
	}

	public int getDesignation() {
		return designation;
	}

	public void setDesignation(int designation) {
		this.designation = designation;
	}

	public int getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(int chargeCode) {
		this.chargeCode = chargeCode;
	}

	public int getCtc() {
		return ctc;
	}

	public void setCtc(int ctc) {
		this.ctc = ctc;
	}

	public int getEducation() {
		return education;
	}

	public void setEducation(int education) {
		this.education = education;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getClient() {
		return client;
	}

	public void setClient(int client) {
		this.client = client;
	}

}
