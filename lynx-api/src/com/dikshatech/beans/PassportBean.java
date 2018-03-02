package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Date;

public class PassportBean implements Serializable
{
	
	private static final long serialVersionUID = 3985337637335525392L;
	private int id;
	private String givenname;
	private String surname;
	private String passportNo;
	private Date dateOfIssue;
	private Date dateOfExpire;
	private String placeOfIssue;
	private String placeOfBirth;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getGivenname()
	{
		return givenname;
	}
	public void setGivenname(String givenname)
	{
		this.givenname = givenname;
	}
	public String getSurname()
	{
		return surname;
	}
	public void setSurname(String surname)
	{
		this.surname = surname;
	}
	public String getPassportNo()
	{
		return passportNo;
	}
	public void setPassportNo(String passportNo)
	{
		this.passportNo = passportNo;
	}
	public Date getDateOfIssue()
	{
		return dateOfIssue;
	}
	public void setDateOfIssue(Date dateOfIssue)
	{
		this.dateOfIssue = dateOfIssue;
	}
	public Date getDateOfExpire()
	{
		return dateOfExpire;
	}
	public void setDateOfExpire(Date dateOfExpire)
	{
		this.dateOfExpire = dateOfExpire;
	}
	public String getPlaceOfIssue()
	{
		return placeOfIssue;
	}
	public void setPlaceOfIssue(String placeOfIssue)
	{
		this.placeOfIssue = placeOfIssue;
	}
	public String getPlaceOfBirth()
	{
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth)
	{
		this.placeOfBirth = placeOfBirth;
	}
	
	
}
