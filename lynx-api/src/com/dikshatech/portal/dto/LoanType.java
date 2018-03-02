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

public class LoanType extends PortalForm implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the LOAN_TYPE table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column TYPE in the LOAN_TYPE table.
	 */
	protected String type;

	/**
	 * Method 'LoanType'
	 * 
	 */
	public LoanType()
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
	 * Method 'getType'
	 * 
	 * @return String
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Method 'setType'
	 * 
	 * @param type
	 */
	public void setType(String type)
	{
		this.type = type;
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
		
		if (!(_other instanceof LoanType)) {
			return false;
		}
		
		final LoanType _cast = (LoanType) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (type == null ? _cast.type != type : !type.equals( _cast.type )) {
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
		if (type != null) {
			_hashCode = 29 * _hashCode + type.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return LoanTypePk
	 */
	public LoanTypePk createPk()
	{
		return new LoanTypePk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.LoanType: " );
		ret.append( "id=" + id );
		ret.append( ", type=" + type );
		return ret.toString();
	}

}
