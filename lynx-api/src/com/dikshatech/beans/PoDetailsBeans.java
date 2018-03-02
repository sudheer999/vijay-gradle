package com.dikshatech.beans;

import java.io.Serializable;

public class PoDetailsBeans implements Serializable
{
	private 	String 		chCodeTitle;
	
	private 	String 		poNumber;
	
	private 	String 		poDate;
	
	private 	String 		poTerms;
	
	private 	String 		startDate;
	
	private 	String 		endDate;
	
	private 	String 		poDuration;
	
	private 	int 		projectId;
	
	private 	int 		userId;  

	public int getUserId()
    {
    	return userId;
    }

	public void setUserId(int userId)
    {
    	this.userId = userId;
    }

	public int getProjectId()
    {
    	return projectId;
    }

	public void setProjectId(int projectId)
    {
    	this.projectId = projectId;
    }

	public String getChCodeTitle()
    {
    	return chCodeTitle;
    }

	public void setChCodeTitle(String chCodeTitle)
    {
    	this.chCodeTitle = chCodeTitle;
    }

	public String getPoNumber()
    {
    	return poNumber;
    }

	public void setPoNumber(String poNumber)
    {
    	this.poNumber = poNumber;
    }

	public String getPoDate()
    {
    	return poDate;
    }

	public void setPoDate(String poDate)
    {
    	this.poDate = poDate;
    }

	public String getPoTerms()
    {
    	return poTerms;
    }

	public void setPoTerms(String poTerms)
    {
    	this.poTerms = poTerms;
    }

	public String getStartDate()
    {
    	return startDate;
    }

	public void setStartDate(String startDate)
    {
    	this.startDate = startDate;
    }

	public String getEndDate()
    {
    	return endDate;
    }

	public void setEndDate(String endDate)
    {
    	this.endDate = endDate;
    }

	public String getPoDuration()
    {
    	return poDuration;
    }

	public void setPoDuration(String poDuration)
    {
    	this.poDuration = poDuration;
    }

}
