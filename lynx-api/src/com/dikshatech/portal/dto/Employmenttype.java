/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dto;

import java.io.Serializable;

import com.dikshatech.portal.forms.PortalForm;

public class Employmenttype  extends PortalForm implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the EMPLOYMENTTYPE table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column EMPLOYMENT_TYPE in the EMPLOYMENTTYPE table.
	 */
	protected String employmentType;

	/**
	 * Method 'Employmenttype'
	 * 
	 */
	public Employmenttype()
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
	 * Method 'getEmploymentType'
	 * 
	 * @return String
	 */
	public String getEmploymentType()
	{
		return employmentType;
	}

	/**
	 * Method 'setEmploymentType'
	 * 
	 * @param employmentType
	 */
	public void setEmploymentType(String employmentType)
	{
		this.employmentType = employmentType;
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
		
		if (!(_other instanceof Employmenttype)) {
			return false;
		}
		
		final Employmenttype _cast = (Employmenttype) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (employmentType == null ? _cast.employmentType != employmentType : !employmentType.equals( _cast.employmentType )) {
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
		if (employmentType != null) {
			_hashCode = 29 * _hashCode + employmentType.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return EmploymenttypePk
	 */
	public EmploymenttypePk createPk()
	{
		return new EmploymenttypePk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.Employmenttype: " );
		ret.append( "id=" + id );
		ret.append( ", employmentType=" + employmentType );
		return ret.toString();
	}

}