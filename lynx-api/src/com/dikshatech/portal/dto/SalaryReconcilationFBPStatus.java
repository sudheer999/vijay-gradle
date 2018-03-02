package com.dikshatech.portal.dto;

public class SalaryReconcilationFBPStatus {
	private Integer userId=null;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	private Integer employeeId=null;
	private String firstName=null;
	private String lastName=null;
	private String fbpStatus=null;
	private Double grossSalary=null;
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFbpStatus() {
		return fbpStatus;
	}
	public void setFbpStatus(String fbpStatus) {
		this.fbpStatus = fbpStatus;
	}
	public Double getGrossSalary() {
		return grossSalary;
	}
	public void setGrossSalary(Double grossSalary) {
		this.grossSalary = grossSalary;
	}
	

}
