package com.dikshatech.beans;

import java.io.Serializable;

public class IssueBean implements Serializable
{
	protected int issueId;
	protected int issueDeptId;
	protected int issueFeatureId;
	protected String issueName;
	protected String issueDeptName;
	protected String date;
	
	
	private String employeeId;
	private String empName;
	private String empDesignation;
	private String empDivisionName;
	public int getIssueId()
    {
    	return issueId;
    }
	public void setIssueId(int issueId)
    {
    	this.issueId = issueId;
    }
	public int getIssueDeptId()
    {
    	return issueDeptId;
    }
	public void setIssueDeptId(int issueDeptId)
    {
    	this.issueDeptId = issueDeptId;
    }
	public int getIssueFeatureId()
    {
    	return issueFeatureId;
    }
	public void setIssueFeatureId(int issueFeatureId)
    {
    	this.issueFeatureId = issueFeatureId;
    }
	public String getIssueName()
    {
    	return issueName;
    }
	public void setIssueName(String issueName)
    {
    	this.issueName = issueName;
    }
	public String getIssueDeptName()
    {
    	return issueDeptName;
    }
	public void setIssueDeptName(String issueDeptName)
    {
    	this.issueDeptName = issueDeptName;
    }
	public String getEmployeeId()
    {
    	return employeeId;
    }
	public void setEmployeeId(String employeeId)
    {
    	this.employeeId = employeeId;
    }
	public String getEmpName()
    {
    	return empName;
    }
	public void setEmpName(String empName)
    {
    	this.empName = empName;
    }
	public String getEmpDesignation()
    {
    	return empDesignation;
    }
	public void setEmpDesignation(String empDesignation)
    {
    	this.empDesignation = empDesignation;
    }
	public String getEmpDivisionName()
    {
    	return empDivisionName;
    }
	public void setEmpDivisionName(String empDivisionName)
    {
    	this.empDivisionName = empDivisionName;
    }
	public String getDate()
    {
    	return date;
    }
	public void setDate(String date)
    {
    	this.date = date;
    }
	
}	
