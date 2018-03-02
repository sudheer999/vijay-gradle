package com.dikshatech.beans;

import java.io.Serializable;

public class Approval implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5566076314414266185L;

	private String approvedOn;
	private String approvedBy;

	public String getApprovedOn()
	{
		return approvedOn;
	}

	public String getApprovedBy()
	{
		return approvedBy;
	}

	public void setApprovedOn(String approvedOn)
	{
		this.approvedOn = approvedOn;
	}

	public void setApprovedBy(String approvedBy)
	{
		this.approvedBy = approvedBy;
	}

}
