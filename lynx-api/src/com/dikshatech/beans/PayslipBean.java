package com.dikshatech.beans;

import java.io.Serializable;

public class PayslipBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int ID;
	private int fileID;
	private String  fileName;
	private int year;
	private String month;
	private String userFname;
	private String userMname;
	private String userLname;
	private String descriptions;
	
	public String getUserFname()
	{
		return userFname;
	}
	public void setUserFname(String userFname)
	{
		this.userFname = userFname;
	}
	public String getUserMname()
	{
		return userMname;
	}
	public void setUserMname(String userMname)
	{
		this.userMname = userMname;
	}
	public String getUserLname()
	{
		return userLname;
	}
	public void setUserLname(String userLname)
	{
		this.userLname = userLname;
	}
	private int userID;
	
	public int getID()
	{
		return ID;
	}
	public void setID(int iD)
	{
		ID = iD;
	}
	public int getFileID()
	{
		return fileID;
	}
	public void setFileID(int fileID)
	{
		this.fileID = fileID;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public int getYear()
	{
		return year;
	}
	public void setYear(int year)
	{
		this.year = year;
	}
	public String getMonth()
	{
		return month;
	}
	public void setMonth(String month)
	{
		this.month = month;
	}
	public int getUserID()
	{
		return userID;
	}
	public void setUserID(int userID)
	{
		this.userID = userID;
	}
	public String getDescriptions()
	{
		return descriptions;
	}
	public void setDescriptions(String descriptions)
	{
		this.descriptions = descriptions;
	}
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
}


