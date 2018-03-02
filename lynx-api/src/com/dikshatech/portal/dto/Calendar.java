/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dto;

import com.dikshatech.portal.dao.*;
import com.dikshatech.portal.factory.*;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class Calendar extends PortalForm implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the CALENDAR table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column NAME in the CALENDAR table.
	 */
	protected String name;

	/** 
	 * This attribute maps to the column REGION in the CALENDAR table.
	 */
	protected int region;

	/** 
	 * This attribute represents whether the primitive attribute region is null.
	 */
	protected boolean regionNull = true;

	/** 
	 * This attribute maps to the column YEAR in the CALENDAR table.
	 */
	protected int year;

	/** 
	 * This attribute represents whether the primitive attribute year is null.
	 */
	protected boolean yearNull = true;

	/**
	 * Method 'Calendar'
	 * 
	 */
	
	String HolidayList;
	
	int calids[];
	
	public Calendar()
	{
	}

	/**
	 * Method 'getId'
	 * 
	 * @return int
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Method 'getName'
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Method 'setName'
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Method 'getRegion'
	 * 
	 * @return int
	 */
	public int getRegion()
	{
		return region;
	}

	/**
	 * Method 'setRegion'
	 * 
	 * @param region
	 */
	public void setRegion(int region)
	{
		this.region = region;
		this.regionNull = false;
	}

	/**
	 * Method 'setRegionNull'
	 * 
	 * @param value
	 */
	public void setRegionNull(boolean value)
	{
		this.regionNull = value;
	}

	/**
	 * Method 'isRegionNull'
	 * 
	 * @return boolean
	 */
	public boolean isRegionNull()
	{
		return regionNull;
	}

	/**
	 * Method 'getYear'
	 * 
	 * @return int
	 */
	public int getYear()
	{
		return year;
	}

	/**
	 * Method 'setYear'
	 * 
	 * @param year
	 */
	public void setYear(int year)
	{
		this.year = year;
		this.yearNull = false;
	}

	/**
	 * Method 'setYearNull'
	 * 
	 * @param value
	 */
	public void setYearNull(boolean value)
	{
		this.yearNull = value;
	}

	/**
	 * Method 'isYearNull'
	 * 
	 * @return boolean
	 */
	public boolean isYearNull()
	{
		return yearNull;
	}

	public String getHolidayList() {
		return HolidayList;
	}

	public void setHolidayList(String holidayList) {
		HolidayList = holidayList;
	}
	
	public int[] getCalids() {
		return calids;
	}

	public void setCalids(int[] calids) {
		this.calids = calids;
	}


	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof Calendar)) {
			return false;
		}
		
		final Calendar _cast = (Calendar) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (name == null ? _cast.name != name : !name.equals( _cast.name )) {
			return false;
		}
		
		if (region != _cast.region) {
			return false;
		}
		
		if (regionNull != _cast.regionNull) {
			return false;
		}
		
		if (year != _cast.year) {
			return false;
		}
		
		if (yearNull != _cast.yearNull) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		if (name != null) {
			_hashCode = 29 * _hashCode + name.hashCode();
		}
		
		_hashCode = 29 * _hashCode + region;
		_hashCode = 29 * _hashCode + (regionNull ? 1 : 0);
		_hashCode = 29 * _hashCode + year;
		_hashCode = 29 * _hashCode + (yearNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return CalendarPk
	 */
	public CalendarPk createPk()
	{
		return new CalendarPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.Calendar: " );
		ret.append( "id=" + id );
		ret.append( ", name=" + name );
		ret.append( ", region=" + region );
		ret.append( ", year=" + year );
		return ret.toString();
	}

}
