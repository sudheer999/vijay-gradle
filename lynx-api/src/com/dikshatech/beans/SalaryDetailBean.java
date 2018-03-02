package com.dikshatech.beans;

import java.io.Serializable;

public class SalaryDetailBean implements Serializable
{
//	protected int basic;
//	protected int hra;
//	protected int conveyance;
//	protected int medicalAllowance;
//	protected int specialAllowance;
//	protected int pfEmpContribution;
//	protected int annualIncentive;
//	protected int perDiem;
//	protected int joiningBonus;
//	protected int lta;
//	protected int insurancePremium;
//	protected int gratuity;
//	protected int totalSalary;
//	protected int mediClaimPolicy;
//	protected int perAccidentPolicy;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2189113483218095666L;
	private int id;
	private int candidateId;
	private int userId;
	private String fieldLabel;
	private String fieldType;
	private String  fields[];
	public String getFieldLabel()
	{
		return fieldLabel;
	}
	public void setFieldLabel(String fieldLabel)
	{
		this.fieldLabel = fieldLabel;
	}
	private String annual;
	private String monthly;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getAnnual()
	{
		return annual;
	}
	public void setAnnual(String annual)
	{
		this.annual = annual;
	}
	public String getMonthly()
	{
		return monthly;
	}
	public void setMonthly(String monthly)
	{
		this.monthly = monthly;
	}
	public int getCandidateId()
	{
		return candidateId;
	}
	public void setCandidateId(int candidateId)
	{
		this.candidateId = candidateId;
	}
	public int getUserId()
	{
		return userId;
	}
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	public String[] getFields()
	{
		return fields;
	}
	public void setFields(String[] fields)
	{
		this.fields = fields;
	}
	public void setFieldType(String i)
	{
		this.fieldType = i;
	}
	public String getFieldType()
	{
		return fieldType;
	}
	
	

}
