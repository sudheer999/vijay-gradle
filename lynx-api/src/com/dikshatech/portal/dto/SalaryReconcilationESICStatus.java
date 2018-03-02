package com.dikshatech.portal.dto;

import java.io.Serializable;

public class SalaryReconcilationESICStatus implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Integer userId=null;

private Integer employeeId=null;
private String firstName=null;
private String lastName=null;
private String esicStatus=null;
private Double grossSalary=null;

public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
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
public String getEsicStatus() {
	return esicStatus;
}
public void setEsicStatus(String esicStatus) {
	this.esicStatus = esicStatus;
}
public Double getGrossSalary() {
	return grossSalary;
}
public void setGrossSalary(Double grossSalary) {
	this.grossSalary = grossSalary;
}

}
