package com.dikshatech.beans;

public class TimesheetDelayedBean {

	private int empId;
	private String empName;
	private String projectName;
	private String rmName;

	/**
	 * @param empId
	 *            the empId to set
	 */
	public void setEmpId(int empId) {
		this.empId = empId;
	}

	/**
	 * @return the empId
	 */
	public int getEmpId() {
		return empId;
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
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @return the rmName
	 */
	public String getRmName() {
		return rmName;
	}

	/**
	 * @param rmName
	 *            the rmName to set
	 */
	public void setRmName(String rmName) {
		this.rmName = rmName;
	}
}
