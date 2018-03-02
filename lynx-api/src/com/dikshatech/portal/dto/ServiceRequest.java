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

public class ServiceRequest implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the SERVICE_REQUEST table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column REQ_NAME in the SERVICE_REQUEST table.
	 */
	protected String reqName;

	/** 
	 * This attribute maps to the column ABBREVIATION in the SERVICE_REQUEST table.
	 */
	protected String abbreviation;

	/** 
	 * This attribute maps to the column FEATURE_ID in the SERVICE_REQUEST table.
	 */
	protected int featureId;

	/**
	 * Method 'ServiceRequest'
	 * 
	 */
	public ServiceRequest()
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
	 * Method 'getReqName'
	 * 
	 * @return String
	 */
	public String getReqName()
	{
		return reqName;
	}

	/**
	 * Method 'setReqName'
	 * 
	 * @param reqName
	 */
	public void setReqName(String reqName)
	{
		this.reqName = reqName;
	}

	/**
	 * Method 'getAbbreviation'
	 * 
	 * @return String
	 */
	public String getAbbreviation()
	{
		return abbreviation;
	}

	/**
	 * Method 'setAbbreviation'
	 * 
	 * @param abbreviation
	 */
	public void setAbbreviation(String abbreviation)
	{
		this.abbreviation = abbreviation;
	}

	/**
	 * Method 'getFeatureId'
	 * 
	 * @return int
	 */
	public int getFeatureId()
	{
		return featureId;
	}

	/**
	 * Method 'setFeatureId'
	 * 
	 * @param featureId
	 */
	public void setFeatureId(int featureId)
	{
		this.featureId = featureId;
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
		
		if (!(_other instanceof ServiceRequest)) {
			return false;
		}
		
		final ServiceRequest _cast = (ServiceRequest) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (reqName == null ? _cast.reqName != reqName : !reqName.equals( _cast.reqName )) {
			return false;
		}
		
		if (abbreviation == null ? _cast.abbreviation != abbreviation : !abbreviation.equals( _cast.abbreviation )) {
			return false;
		}
		
		if (featureId != _cast.featureId) {
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
		if (reqName != null) {
			_hashCode = 29 * _hashCode + reqName.hashCode();
		}
		
		if (abbreviation != null) {
			_hashCode = 29 * _hashCode + abbreviation.hashCode();
		}
		
		_hashCode = 29 * _hashCode + featureId;
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ServiceRequestPk
	 */
	public ServiceRequestPk createPk()
	{
		return new ServiceRequestPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.ServiceRequest: " );
		ret.append( "id=" + id );
		ret.append( ", reqName=" + reqName );
		ret.append( ", abbreviation=" + abbreviation );
		ret.append( ", featureId=" + featureId );
		return ret.toString();
	}

}
