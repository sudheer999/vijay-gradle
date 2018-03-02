package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Set;

public class Division implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int divisionId;
	private String divisionName;
	private Set<Division> subDivision;
	
	public int getDivisionId()
	{
		return divisionId;
	}
	public String getDivisionName()
	{
		return divisionName;
	}
	public Set<Division> getSubDivision()
	{
		return subDivision;
	}
	public void setDivisionId(int divisionId)
	{
		this.divisionId = divisionId;
	}
	public void setDivisionName(String divisionName)
	{
		this.divisionName = divisionName;
	}
	public void setSubDivision(Set<Division> subDivision)
	{
		this.subDivision = subDivision;
	}
	
}
