/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

public class VisaInfo implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the visa_info table.
	 */
	protected Integer id;

	/** 
	 * This attribute maps to the column PASSPORT_ID in the visa_info table.
	 */
	protected Integer passportId;

	/** 
	 * This attribute maps to the column VISA_FROM in the visa_info table.
	 */
	protected Date visaFrom;

	/** 
	 * This attribute maps to the column VISA_TO in the visa_info table.
	 */
	protected Date visaTo;

	/** 
	 * This attribute maps to the column VISA_TYPE in the visa_info table.
	 */
	protected String visaType;

	/** 
	 * This attribute maps to the column COUNTRY in the visa_info table.
	 */
	protected String country;

	/**
	 * Method 'VisaInfo'
	 * 
	 */
	public VisaInfo()
	{
	}

	/**
	 * Method 'getId'
	 * 
	 * @return Integer
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * Method 'getPassportId'
	 * 
	 * @return Integer
	 */
	public Integer getPassportId()
	{
		return passportId;
	}

	/**
	 * Method 'setPassportId'
	 * 
	 * @param passportId
	 */
	public void setPassportId(Integer passportId)
	{
		this.passportId = passportId;
	}

	/**
	 * Method 'getVisaFrom'
	 * 
	 * @return Date
	 */
	public Date getVisaFrom()
	{
		return visaFrom;
	}

	/**
	 * Method 'setVisaFrom'
	 * 
	 * @param visaFrom
	 */
	public void setVisaFrom(Date visaFrom)
	{
		this.visaFrom = visaFrom;
	}

	/**
	 * Method 'getVisaTo'
	 * 
	 * @return Date
	 */
	public Date getVisaTo()
	{
		return visaTo;
	}

	/**
	 * Method 'setVisaTo'
	 * 
	 * @param visaTo
	 */
	public void setVisaTo(Date visaTo)
	{
		this.visaTo = visaTo;
	}

	/**
	 * Method 'getVisaType'
	 * 
	 * @return String
	 */
	public String getVisaType()
	{
		return visaType;
	}

	/**
	 * Method 'setVisaType'
	 * 
	 * @param visaType
	 */
	public void setVisaType(String visaType)
	{
		this.visaType = visaType;
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
		
		if (!(_other instanceof VisaInfo)) {
			return false;
		}
		
		final VisaInfo _cast = (VisaInfo) _other;
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
			return false;
		}
		
		if (passportId == null ? _cast.passportId != passportId : !passportId.equals( _cast.passportId )) {
			return false;
		}
		
		if (visaFrom == null ? _cast.visaFrom != visaFrom : !visaFrom.equals( _cast.visaFrom )) {
			return false;
		}
		
		if (visaTo == null ? _cast.visaTo != visaTo : !visaTo.equals( _cast.visaTo )) {
			return false;
		}
		
		if (visaType == null ? _cast.visaType != visaType : !visaType.equals( _cast.visaType )) {
			return false;
		}
		
		if (country == null ? _cast.country != country : !country.equals( _cast.country )) {
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
		if (id != null) {
			_hashCode = 29 * _hashCode + id.hashCode();
		}
		
		if (passportId != null) {
			_hashCode = 29 * _hashCode + passportId.hashCode();
		}
		
		if (visaFrom != null) {
			_hashCode = 29 * _hashCode + visaFrom.hashCode();
		}
		
		if (visaTo != null) {
			_hashCode = 29 * _hashCode + visaTo.hashCode();
		}
		
		if (visaType != null) {
			_hashCode = 29 * _hashCode + visaType.hashCode();
		}
		
		if (country != null) {
			_hashCode = 29 * _hashCode + country.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return VisaInfoPk
	 */
	public VisaInfoPk createPk()
	{
		return new VisaInfoPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.VisaInfo: " );
		ret.append( "id=" + id );
		ret.append( ", passportId=" + passportId );
		ret.append( ", visaFrom=" + visaFrom );
		ret.append( ", visaTo=" + visaTo );
		ret.append( ", visaType=" + visaType );
		ret.append( ", country=" + country );
		return ret.toString();
	}

}
