package com.dikshatech.beans;

import java.io.Serializable;

public class ProfileListBean implements Serializable {

	/**
     * 
     */
	private static final long	serialVersionUID	= 1L;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1103769921186606620L;
	private int					id;
	private String				firstName;
	private String				lastName;
	private String				officalEmailId;
	private int					userId;
	private String				department;
	private int					levelid;
	private String				designation;
	private String				division;
	private String				phonenumber;
	private int					empid;
	private String				hrSpocName;
	private String				projectName;
	private String				incomplete;

	public ProfileListBean() {
		// TODO Auto-generated constructor stub
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getOfficalEmailId() {
		return officalEmailId;
	}

	public void setOfficalEmailId(String officalEmailId) {
		this.officalEmailId = officalEmailId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String getHrSpocName() {
		return hrSpocName;
	}

	public void setHrSpocName(String hrSpocName) {
		this.hrSpocName = hrSpocName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLevelid(int levelid) {
		this.levelid = levelid;
	}

	public int getLevelid() {
		return levelid;
	}

	public ProfileListBean(int id, int userId, int empid, int levelid, String firstName, String lastName, String officalEmailId, String hrSpocName, String designation, String phonenumber, short incomplete) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.officalEmailId = officalEmailId;
		this.userId = userId;
		this.levelid = levelid;
		this.designation = designation;
		this.phonenumber = phonenumber;
		this.empid = empid;
		this.hrSpocName = hrSpocName;
		this.incomplete = incomplete == 0 ? null : "false";
	}

	public String getIncomplete() {
		return incomplete;
	}

	public void setIncomplete(String incomplete) {
		this.incomplete = incomplete;
	}
}
