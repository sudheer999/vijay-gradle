package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Date;

public class PersonalBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -849020633030302865L;


	private int id;

	
	private int permanentAddress;

	
	private int currentAddress;

	
	private String primaryPhoneNo;

	
	private boolean primaryPhoneNoNull;

	
	private String secondaryPhoneNo;

	
	private boolean secondaryPhoneNoNull;

	
	private String personalEmailId;

	
	private String motherName;

	
	private String fatherName;

	
	private String maritalStatus;

	
	private String spouseName;

	
	private String emerContactName;

	
	private String emerCpRelationship;

	
	private Long emerPhoneNo;

	
	private boolean emerPhoneNoNull;
	protected Date spouseDob;
	private String permAddress;
	protected String city;
	protected int zipCode;
	protected String country;
	protected String state;
	
	private String curAddress;
	protected String currcity;
	protected int currzipCode;
	protected String currcountry;
	protected String currstate;
	
	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
	}


	public int getPermanentAddress()
	{
		return permanentAddress;
	}


	public void setPermanentAddress(int permanentAddress)
	{
		this.permanentAddress = permanentAddress;
	}


	public int getCurrentAddress()
	{
		return currentAddress;
	}


	public void setCurrentAddress(int currentAddress)
	{
		this.currentAddress = currentAddress;
	}


	public String getPrimaryPhoneNo()
	{
		return primaryPhoneNo;
	}


	public void setPrimaryPhoneNo(String primaryPhoneNo)
	{
		this.primaryPhoneNo = primaryPhoneNo;
	}


	public boolean isPrimaryPhoneNoNull()
	{
		return primaryPhoneNoNull;
	}


	public void setPrimaryPhoneNoNull(boolean primaryPhoneNoNull)
	{
		this.primaryPhoneNoNull = primaryPhoneNoNull;
	}


	public String getSecondaryPhoneNo()
	{
		return secondaryPhoneNo;
	}


	public void setSecondaryPhoneNo(String secondaryPhoneNo)
	{
		this.secondaryPhoneNo = secondaryPhoneNo;
	}


	public boolean isSecondaryPhoneNoNull()
	{
		return secondaryPhoneNoNull;
	}


	public void setSecondaryPhoneNoNull(boolean secondaryPhoneNoNull)
	{
		this.secondaryPhoneNoNull = secondaryPhoneNoNull;
	}


	public String getPersonalEmailId()
	{
		return personalEmailId;
	}


	public void setPersonalEmailId(String personalEmailId)
	{
		this.personalEmailId = personalEmailId;
	}


	public String getMotherName()
	{
		return motherName;
	}


	public void setMotherName(String motherName)
	{
		this.motherName = motherName;
	}


	public String getFatherName()
	{
		return fatherName;
	}


	public void setFatherName(String fatherName)
	{
		this.fatherName = fatherName;
	}


	public String getMaritalStatus()
	{
		return maritalStatus;
	}


	public void setMaritalStatus(String maritalStatus)
	{
		this.maritalStatus = maritalStatus;
	}


	public String getSpouseName()
	{
		return spouseName;
	}


	public void setSpouseName(String spouseName)
	{
		this.spouseName = spouseName;
	}


	public String getEmerContactName()
	{
		return emerContactName;
	}


	public void setEmerContactName(String emerContactName)
	{
		this.emerContactName = emerContactName;
	}


	public String getEmerCpRelationship()
	{
		return emerCpRelationship;
	}


	public void setEmerCpRelationship(String emerCpRelationship)
	{
		this.emerCpRelationship = emerCpRelationship;
	}


	public Long getEmerPhoneNo()
	{
		return emerPhoneNo;
	}


	public void setEmerPhoneNo(Long emerPhoneNo)
	{
		this.emerPhoneNo = emerPhoneNo;
	}


	public boolean isEmerPhoneNoNull()
	{
		return emerPhoneNoNull;
	}


	public void setEmerPhoneNoNull(boolean emerPhoneNoNull)
	{
		this.emerPhoneNoNull = emerPhoneNoNull;
	}


	public Date getSpouseDob()
	{
		return spouseDob;
	}


	public void setSpouseDob(Date spouseDob)
	{
		this.spouseDob = spouseDob;
	}


	public String getPermAddress()
	{
		return permAddress;
	}


	public void setPermAddress(String permAddress)
	{
		this.permAddress = permAddress;
	}


	public String getCity()
	{
		return city;
	}


	public void setCity(String city)
	{
		this.city = city;
	}


	public int getZipCode()
	{
		return zipCode;
	}


	public void setZipCode(int zipCode)
	{
		this.zipCode = zipCode;
	}


	public String getCountry()
	{
		return country;
	}


	public void setCountry(String country)
	{
		this.country = country;
	}


	public String getState()
	{
		return state;
	}


	public void setState(String state)
	{
		this.state = state;
	}


	public String getCurAddress()
	{
		return curAddress;
	}


	public void setCurAddress(String curAddress)
	{
		this.curAddress = curAddress;
	}


	public String getCurrcity()
	{
		return currcity;
	}


	public void setCurrcity(String currcity)
	{
		this.currcity = currcity;
	}


	public int getCurrzipCode()
	{
		return currzipCode;
	}


	public void setCurrzipCode(int currzipCode)
	{
		this.currzipCode = currzipCode;
	}


	public String getCurrcountry()
	{
		return currcountry;
	}


	public void setCurrcountry(String currcountry)
	{
		this.currcountry = currcountry;
	}


	public String getCurrstate()
	{
		return currstate;
	}


	public void setCurrstate(String currstate)
	{
		this.currstate = currstate;
	}
	
	
}
