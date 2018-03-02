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
import com.dikshatech.portal.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class ProjLocations implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the PROJ_LOCATIONS table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column PROJ_ID in the PROJ_LOCATIONS table.
	 */
	protected int projId;

	/** 
	 * This attribute maps to the column NAME in the PROJ_LOCATIONS table.
	 */
	protected String name;

	/** 
	 * This attribute maps to the column CITY in the PROJ_LOCATIONS table.
	 */
	protected String city;

	/** 
	 * This attribute maps to the column ADDRESS in the PROJ_LOCATIONS table.
	 */
	protected String address;

	/** 
	 * This attribute maps to the column ZIP_CODE in the PROJ_LOCATIONS table.
	 */
	protected int zipCode;

	/** 
	 * This attribute represents whether the primitive attribute zipCode is null.
	 */
	protected boolean zipCodeNull = true;

	/** 
	 * This attribute maps to the column STATE in the PROJ_LOCATIONS table.
	 */
	protected String state;

	/** 
	 * This attribute maps to the column COUNTRY in the PROJ_LOCATIONS table.
	 */
	protected String country;

	/** 
	 * This attribute maps to the column TEL_NUM in the PROJ_LOCATIONS table.
	 */
	protected String telNum;

	/** 
	 * This attribute maps to the column FAX_NUM in the PROJ_LOCATIONS table.
	 */
	protected String faxNum;

	/**
	 * Method 'ProjLocations'
	 * 
	 */
	public ProjLocations()
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
	 * Method 'getProjId'
	 * 
	 * @return int
	 */
	public int getProjId()
	{
		return projId;
	}

	/**
	 * Method 'setProjId'
	 * 
	 * @param projId
	 */
	public void setProjId(int projId)
	{
		this.projId = projId;
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
	 * Method 'getCity'
	 * 
	 * @return String
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * Method 'setCity'
	 * 
	 * @param city
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * Method 'getAddress'
	 * 
	 * @return String
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * Method 'setAddress'
	 * 
	 * @param address
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * Method 'getZipCode'
	 * 
	 * @return int
	 */
	public int getZipCode()
	{
		return zipCode;
	}

	/**
	 * Method 'setZipCode'
	 * 
	 * @param zipCode
	 */
	public void setZipCode(int zipCode)
	{
		this.zipCode = zipCode;
		this.zipCodeNull = false;
	}

	/**
	 * Method 'setZipCodeNull'
	 * 
	 * @param value
	 */
	public void setZipCodeNull(boolean value)
	{
		this.zipCodeNull = value;
	}

	/**
	 * Method 'isZipCodeNull'
	 * 
	 * @return boolean
	 */
	public boolean isZipCodeNull()
	{
		return zipCodeNull;
	}

	/**
	 * Method 'getState'
	 * 
	 * @return String
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * Method 'setState'
	 * 
	 * @param state
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * Method 'getCountry'
	 * 
	 * @return String
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * Method 'setCountry'
	 * 
	 * @param country
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	/**
	 * Method 'getTelNum'
	 * 
	 * @return String
	 */
	public String getTelNum()
	{
		return telNum;
	}

	/**
	 * Method 'setTelNum'
	 * 
	 * @param telNum
	 */
	public void setTelNum(String telNum)
	{
		this.telNum = telNum;
	}

	/**
	 * Method 'getFaxNum'
	 * 
	 * @return String
	 */
	public String getFaxNum()
	{
		return faxNum;
	}

	/**
	 * Method 'setFaxNum'
	 * 
	 * @param faxNum
	 */
	public void setFaxNum(String faxNum)
	{
		this.faxNum = faxNum;
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
		
		if (!(_other instanceof ProjLocations)) {
			return false;
		}
		
		final ProjLocations _cast = (ProjLocations) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (projId != _cast.projId) {
			return false;
		}
		
		if (name == null ? _cast.name != name : !name.equals( _cast.name )) {
			return false;
		}
		
		if (city == null ? _cast.city != city : !city.equals( _cast.city )) {
			return false;
		}
		
		if (address == null ? _cast.address != address : !address.equals( _cast.address )) {
			return false;
		}
		
		if (zipCode != _cast.zipCode) {
			return false;
		}
		
		if (zipCodeNull != _cast.zipCodeNull) {
			return false;
		}
		
		if (state == null ? _cast.state != state : !state.equals( _cast.state )) {
			return false;
		}
		
		if (country == null ? _cast.country != country : !country.equals( _cast.country )) {
			return false;
		}
		
		if (telNum == null ? _cast.telNum != telNum : !telNum.equals( _cast.telNum )) {
			return false;
		}
		
		if (faxNum == null ? _cast.faxNum != faxNum : !faxNum.equals( _cast.faxNum )) {
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
		_hashCode = 29 * _hashCode + projId;
		if (name != null) {
			_hashCode = 29 * _hashCode + name.hashCode();
		}
		
		if (city != null) {
			_hashCode = 29 * _hashCode + city.hashCode();
		}
		
		if (address != null) {
			_hashCode = 29 * _hashCode + address.hashCode();
		}
		
		_hashCode = 29 * _hashCode + zipCode;
		_hashCode = 29 * _hashCode + (zipCodeNull ? 1 : 0);
		if (state != null) {
			_hashCode = 29 * _hashCode + state.hashCode();
		}
		
		if (country != null) {
			_hashCode = 29 * _hashCode + country.hashCode();
		}
		
		if (telNum != null) {
			_hashCode = 29 * _hashCode + telNum.hashCode();
		}
		
		if (faxNum != null) {
			_hashCode = 29 * _hashCode + faxNum.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ProjLocationsPk
	 */
	public ProjLocationsPk createPk()
	{
		return new ProjLocationsPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.ProjLocations: " );
		ret.append( "id=" + id );
		ret.append( ", projId=" + projId );
		ret.append( ", name=" + name );
		ret.append( ", city=" + city );
		ret.append( ", address=" + address );
		ret.append( ", zipCode=" + zipCode );
		ret.append( ", state=" + state );
		ret.append( ", country=" + country );
		ret.append( ", telNum=" + telNum );
		ret.append( ", faxNum=" + faxNum );
		return ret.toString();
	}

}