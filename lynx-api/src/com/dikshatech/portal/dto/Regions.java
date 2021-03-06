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

import com.dikshatech.portal.forms.PortalForm;

public class Regions extends PortalForm implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * This attribute maps to the column ID in the REGIONS table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column COMPANY_ID in the REGIONS table.
	 */
	protected int companyId;

	/** 
	 * This attribute maps to the column REG_NAME in the REGIONS table.
	 */
	protected String regName;

	protected com.dikshatech.beans.Region RegionBeans;
	

	public com.dikshatech.beans.Region getRegionBeans() {
		return RegionBeans;
	}

	public void setRegionBeans(com.dikshatech.beans.Region regionBeans) {
		RegionBeans = regionBeans;
	}

	/** 
	 * This attribute maps to the column REF_ABBREVIATION in the REGIONS table.
	 */
	protected String refAbbreviation;

	/** 
	 * This attribute maps to the column PARENT_ID in the REGIONS table.
	 */
	protected int parentId;

	/** 
	 * This attribute represents whether the primitive attribute parentId is null.
	 */
	protected boolean parentIdNull = true;

	/** 
	 * This attribute maps to the column CREATE_DATE in the REGIONS table.
	 */
	protected Date createDate;

	/**
	 * Method 'Regions'
	 * 
	 */
	public Regions()
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
	 * Method 'getCompanyId'
	 * 
	 * @return int
	 */
	public int getCompanyId()
	{
		return companyId;
	}

	/**
	 * Method 'setCompanyId'
	 * 
	 * @param companyId
	 */
	public void setCompanyId(int companyId)
	{
		this.companyId = companyId;
	}

	/**
	 * Method 'getRegName'
	 * 
	 * @return String
	 */
	public String getRegName()
	{
		return regName;
	}

	/**
	 * Method 'setRegName'
	 * 
	 * @param regName
	 */
	public void setRegName(String regName)
	{
		this.regName = regName;
	}

	/**
	 * Method 'getRefAbbreviation'
	 * 
	 * @return String
	 */
	public String getRefAbbreviation()
	{
		return refAbbreviation;
	}

	/**
	 * Method 'setRefAbbreviation'
	 * 
	 * @param refAbbreviation
	 */
	public void setRefAbbreviation(String refAbbreviation)
	{
		this.refAbbreviation = refAbbreviation;
	}

	/**
	 * Method 'getParentId'
	 * 
	 * @return int
	 */
	public int getParentId()
	{
		return parentId;
	}

	/**
	 * Method 'setParentId'
	 * 
	 * @param parentId
	 */
	public void setParentId(int parentId)
	{
		this.parentId = parentId;
		this.parentIdNull = false;
	}

	/**
	 * Method 'setParentIdNull'
	 * 
	 * @param value
	 */
	public void setParentIdNull(boolean value)
	{
		this.parentIdNull = value;
	}

	/**
	 * Method 'isParentIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isParentIdNull()
	{
		return parentIdNull;
	}

	/**
	 * Method 'getCreateDate'
	 * 
	 * @return Date
	 */
	public Date getCreateDate()
	{
		return createDate;
	}

	/**
	 * Method 'setCreateDate'
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
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
		
		if (!(_other instanceof Regions)) {
			return false;
		}
		
		final Regions _cast = (Regions) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (companyId != _cast.companyId) {
			return false;
		}
		
		if (regName == null ? _cast.regName != regName : !regName.equals( _cast.regName )) {
			return false;
		}
		
		if (refAbbreviation == null ? _cast.refAbbreviation != refAbbreviation : !refAbbreviation.equals( _cast.refAbbreviation )) {
			return false;
		}
		
		if (parentId != _cast.parentId) {
			return false;
		}
		
		if (parentIdNull != _cast.parentIdNull) {
			return false;
		}
		
		if (createDate == null ? _cast.createDate != createDate : !createDate.equals( _cast.createDate )) {
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
		_hashCode = 29 * _hashCode + companyId;
		if (regName != null) {
			_hashCode = 29 * _hashCode + regName.hashCode();
		}
		
		if (refAbbreviation != null) {
			_hashCode = 29 * _hashCode + refAbbreviation.hashCode();
		}
		
		_hashCode = 29 * _hashCode + parentId;
		_hashCode = 29 * _hashCode + (parentIdNull ? 1 : 0);
		if (createDate != null) {
			_hashCode = 29 * _hashCode + createDate.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return RegionsPk
	 */
	public RegionsPk createPk()
	{
		return new RegionsPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.Regions: " );
		ret.append( "id=" + id );
		ret.append( ", companyId=" + companyId );
		ret.append( ", regName=" + regName );
		ret.append( ", refAbbreviation=" + refAbbreviation );
		ret.append( ", parentId=" + parentId );
		ret.append( ", createDate=" + createDate );
		return ret.toString();
	}

}
