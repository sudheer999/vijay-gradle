package com.dikshatech.beans;

import java.io.Serializable;

import com.dikshatech.portal.dto.Levels;

public class ProcessChainBean implements Serializable
{
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	private int processId;
	private String processChainName;	
	private int companyId;
	private int regionId;
	private int departmentId;
	private int divisionId;
	private int featureId;
	private String CompanyName;
	private String RegionName;
	private String divisionName;
	private String deptName;
	
	private Levels[] levels;
	
	/*private String[] usersIds;*/
	
	public int getProcessId()
    {
    	return processId;
    }
	public void setProcessId(int processId)
    {
    	this.processId = processId;
    }
	public String getProcessChainName()
    {
    	return processChainName;
    }
	public void setProcessChainName(String processChainName)
    {
    	this.processChainName = processChainName;
    }
	public int getCompanyId()
    {
    	return companyId;
    }
	public void setCompanyId(int companyId)
    {
    	this.companyId = companyId;
    }
	public int getRegionId()
    {
    	return regionId;
    }
	public void setRegionId(int regionId)
    {
    	this.regionId = regionId;
    }
	public int getDepartmentId()
    {
    	return departmentId;
    }
	public void setDepartmentId(int departmentId)
    {
    	this.departmentId = departmentId;
    }
	public int getDivisionId()
    {
    	return divisionId;
    }
	public void setDivisionId(int divisionId)
    {
    	this.divisionId = divisionId;
    }

	@Override
    public int hashCode()
    {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + processId;
	    return result;
    }
	@Override
    public boolean equals(Object obj)
    {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    ProcessChainBean other = (ProcessChainBean) obj;
	    if (processId != other.processId)
		    return false;
	    return true;
    }
	
	/*public String[] getUsersIds()
    {
    	return usersIds;
    }
	public void setUsersIds(String[] usersIds)
    {
    	this.usersIds = usersIds;
    }*/
	public int getFeatureId()
    {
    	return featureId;
    }
	public void setFeatureId(int featureId)
    {
    	this.featureId = featureId;
    }
	public String getCompanyName()
    {
    	return CompanyName;
    }
	public void setCompanyName(String companyName)
    {
    	CompanyName = companyName;
    }
	public String getRegionName()
    {
    	return RegionName;
    }
	public void setRegionName(String regionName)
    {
    	RegionName = regionName;
    }
	public String getDivisionName()
    {
    	return divisionName;
    }
	public void setDivisionName(String divisionName)
    {
    	this.divisionName = divisionName;
    }
	public String getDeptName()
    {
    	return deptName;
    }
	public void setDeptName(String deptName)
    {
    	this.deptName = deptName;
    }
	public void setLevels(Levels[] levels)
	{
		this.levels = levels;
	}
	public Levels[] getLevels()
	{
		return levels;
	}
	
	
	
	
	
	
	
	
	
}
