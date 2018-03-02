package com.dikshatech.beans;

import java.util.Date;

public class XlsRecordsBean {

	private int		userId;
	private int		loginUserid;
	private String	name;
	private String	gender;
	private Date	dob;
	private int		age;
	private String	relationship;
	private String	cardId;
	private String	endorsementNo;
	private String	policyNumber;
	private String	policyType;
	private int		coverage;
	private Date	coverageFrom;
	private Date	coverageUpto;
	private String	insuranceCompanyName;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getLoginUserid() {
		return loginUserid;
	}

	public void setLoginUserid(int loginUserid) {
		this.loginUserid = loginUserid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getEndorsementNo() {
		return endorsementNo;
	}

	public void setEndorsementNo(String endorsementNo) {
		this.endorsementNo = endorsementNo;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public int getCoverage() {
		return coverage;
	}

	public void setCoverage(int coverage) {
		this.coverage = coverage;
	}

	public Date getCoverageFrom() {
		return coverageFrom;
	}

	public void setCoverageFrom(Date coverageFrom) {
		this.coverageFrom = coverageFrom;
	}

	public Date getCoverageUpto() {
		return coverageUpto;
	}

	public void setCoverageUpto(Date coverageUpto) {
		this.coverageUpto = coverageUpto;
	}

	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}

	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}

	@Override
	public String toString() {
		return "XlsRecordsBean [userId=" + userId + ", loginUserid=" + loginUserid + ", name=" + name + ", gender=" + gender + ", dob=" + dob + ", age=" + age + ", relationship=" + relationship + ", cardId=" + cardId + ", endorsementNo=" + endorsementNo + ", policyNumber=" + policyNumber + ", policyType=" + policyType + ", coverage=" + coverage + ", coverageFrom=" + coverageFrom + ", coverageUpto=" + coverageUpto + ", insuranceCompanyName=" + insuranceCompanyName + "]";
	}

	
}
