package com.dikshatech.beans;

public class RollOnBean implements Comparable<RollOnBean> {

	private String	designation;
	private String	name;
	private String	project;
	private String	chCode;
	private String	userId;
	private String	department;
	private String	empid;
	private String	rollOnId;
	private String mobileNo;
	private String annualCtc;
	private String highDegree;
	private String rmName;
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAnnualCtc() {
		return annualCtc;
	}

	public void setAnnualCtc(String annualCtc) {
		this.annualCtc = annualCtc;
	}

	

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getTotExperience() {
		return totExperience;
	}

	public void setTotExperience(String totExperience) {
		this.totExperience = totExperience;
	}

	private String passportNo;
	private String totExperience;
	
	

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getChCode() {
		return chCode;
	}

	public void setChCode(String chCode) {
		this.chCode = chCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	@Override
	public int compareTo(RollOnBean o) {
		try{
			return Integer.parseInt(empid) - Integer.parseInt(o.getEmpid());
		} catch (Exception e){}
		return -1;
	}

	public String getRollOnId() {
		return rollOnId;
	}

	public void setRollOnId(String rollOnId) {
		this.rollOnId = rollOnId;
	}

	public String getHighDegree() {
		return highDegree;
	}

	public void setHighDegree(String highDegree) {
		this.highDegree = highDegree;
	}

	public String getRmName() {
		return rmName;
	}

	public void setRmName(String rmName) {
		this.rmName = rmName;
	}
}
