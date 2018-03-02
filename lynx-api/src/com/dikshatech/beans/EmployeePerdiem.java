package com.dikshatech.beans;

public class EmployeePerdiem {
	private int userId;
	private int empId;
	private String empFullName;
	private String projectName;
	private String managerName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public void setEmpFullName(String empFullName) {
		this.empFullName = empFullName;
	}

	public String getEmpFullName() {
		return empFullName;
	}

	@Override
	public String toString() {
		return "userId : " + this.userId + "\nempId : " + this.empId
				+ "\nempName : " + this.empFullName + "\nproject name : "
				+ this.projectName + "\nmanager name : " + this.managerName;
	}

}
