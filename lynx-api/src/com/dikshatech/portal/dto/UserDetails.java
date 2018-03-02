package com.dikshatech.portal.dto;

import java.io.Serializable;

/**
 * The objects of this class will contain emp-id --- emp-name this is used as
 * DTO to send employee information when a new Project is created
 */
@SuppressWarnings("serial")
public class UserDetails implements Serializable {

	private String	empName;
	private Integer	userId;
	private String	firstName;
	private String	lastName;
	private String	employeeId;
	private String	 maritalStatus;
	public UserDetails() {
	// TODO Auto-generated constructor stub
	}
	public UserDetails(Integer userId, String firstName, String empName) {
		this.setUserId(userId);
		this.setFirstName(firstName);
		this.setEmpName(empName);
	}
	
	public UserDetails(Integer userId, String firstName, String empName, String lastName, String employeeId, String maritalStatus) {
		this.setUserId(userId);
		this.setFirstName(firstName);
		this.setEmpName(empName);
		this.setLastName(lastName);
		this.setEmployeeId(employeeId);
		this.setMaritalStatus(maritalStatus);
	}

	/**
	 * @param empName
	 *            the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeId() {
		return employeeId;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
}
