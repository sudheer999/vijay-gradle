package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.portal.forms.PortalForm;

public class RetentionEmpSerReqMap extends PortalForm implements Serializable{

	/** 
	 * This attribute maps to the column ID in the EMP_SER_REQ_MAP table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column SUB_DATE in the EMP_SER_REQ_MAP table.
	 */
	protected Date subDate;

	/** 
	 * This attribute maps to the column REQ_ID in the EMP_SER_REQ_MAP table.
	 */
	protected String reqId;

	/** 
	 * This attribute maps to the column REQ_TYPE_ID in the EMP_SER_REQ_MAP table.
	 */
	protected int reqTypeId;

	/** 
	 * This attribute represents whether the primitive attribute reqTypeId is null.
	 */
	protected boolean reqTypeIdNull = true;

	/** 
	 * This attribute maps to the column REGION_ID in the EMP_SER_REQ_MAP table.
	 */
	protected int regionId;

	/** 
	 * This attribute represents whether the primitive attribute regionId is null.
	 */
	protected boolean regionIdNull = true;

	/** 
	 * This attribute maps to the column REQUESTOR_ID in the EMP_SER_REQ_MAP table.
	 */
	protected int requestorId;

	/** 
	 * This attribute represents whether the primitive attribute requestorId is null.
	 */
	protected boolean requestorIdNull = true;

	/** 
	 * This attribute maps to the column APPROVAL_CHAIN in the EMP_SER_REQ_MAP table.
	 */
	protected int processChainId;
	
	protected boolean processChainIdNull = true;

	/** 
	 * This attribute maps to the column NOTIFY in the EMP_SER_REQ_MAP table.
	 */
	protected String notify;
	protected String siblings="";

	/**
	 * Method 'RetentionEmpSerReqMap'
	 * 
	 */
	public RetentionEmpSerReqMap()
	{
	}

	
	public String getSiblings() {
		return siblings;
	}

	
	public void setSiblings(String siblings) {
		this.siblings = siblings;
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
	 * Method 'getSubDate'
	 * 
	 * @return Date
	 */
	public Date getSubDate()
	{
		return subDate;
	}

	/**
	 * Method 'setSubDate'
	 * 
	 * @param subDate
	 */
	public void setSubDate(Date subDate)
	{
		this.subDate = subDate;
	}

	/**
	 * Method 'getReqId'
	 * 
	 * @return String
	 */
	public String getReqId()
	{
		return reqId;
	}

	/**
	 * Method 'setReqId'
	 * 
	 * @param reqId
	 */
	public void setReqId(String reqId)
	{
		this.reqId = reqId;
	}

	/**
	 * Method 'getReqTypeId'
	 * 
	 * @return int
	 */
	public int getReqTypeId()
	{
		return reqTypeId;
	}

	/**
	 * Method 'setReqTypeId'
	 * 
	 * @param reqTypeId
	 */
	public void setReqTypeId(int reqTypeId)
	{
		this.reqTypeId = reqTypeId;
		this.reqTypeIdNull = false;
	}

	/**
	 * Method 'setReqTypeIdNull'
	 * 
	 * @param value
	 */
	public void setReqTypeIdNull(boolean value)
	{
		this.reqTypeIdNull = value;
	}

	/**
	 * Method 'isReqTypeIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isReqTypeIdNull()
	{
		return reqTypeIdNull;
	}

	/**
	 * Method 'getRegionId'
	 * 
	 * @return int
	 */
	public int getRegionId()
	{
		return regionId;
	}

	/**
	 * Method 'setRegionId'
	 * 
	 * @param regionId
	 */
	public void setRegionId(int regionId)
	{
		this.regionId = regionId;
		this.regionIdNull = false;
	}

	/**
	 * Method 'setRegionIdNull'
	 * 
	 * @param value
	 */
	public void setRegionIdNull(boolean value)
	{
		this.regionIdNull = value;
	}

	/**
	 * Method 'isRegionIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isRegionIdNull()
	{
		return regionIdNull;
	}

	/**
	 * Method 'getRequestorId'
	 * 
	 * @return int
	 */
	public int getRequestorId()
	{
		return requestorId;
	}

	/**
	 * Method 'setRequestorId'
	 * 
	 * @param requestorId
	 */
	public void setRequestorId(int requestorId)
	{
		this.requestorId = requestorId;
		this.requestorIdNull = false;
	}

	/**
	 * Method 'setRequestorIdNull'
	 * 
	 * @param value
	 */
	public void setRequestorIdNull(boolean value)
	{
		this.requestorIdNull = value;
	}

	/**
	 * Method 'isRequestorIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isRequestorIdNull()
	{
		return requestorIdNull;
	}

	/**
	 * Method 'getApprovalChain'
	 * 
	 * @return String
	 */
	public int getProcessChainId()
	{
		return processChainId;
	}
	
	/**
	 * Method 'isProcessChainIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isProcessChainIdNull()
	{
		return processChainIdNull;
	}
	
	public void setProcessChainIdNull(boolean value)
	{
		this.processChainIdNull = value;
	}

	/**
	 * Method 'setApprovalChain'
	 * 
	 * @param approvalChain
	 */
	public void setProcessChainId(int processChainId)
	{
		this.processChainId = processChainId;
		processChainIdNull = false;
	}

	/**
	 * Method 'getNotify'
	 * 
	 * @return String
	 */
	public String getNotify()
	{
		return notify;
	}

	/**
	 * Method 'setNotify'
	 * 
	 * @param notify
	 */
	public void setNotify(String notify)
	{
		this.notify = notify;
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
		
		if (!(_other instanceof RetentionEmpSerReqMap)) {
			return false;
		}
		
		final RetentionEmpSerReqMap _cast = (RetentionEmpSerReqMap) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (subDate == null ? _cast.subDate != subDate : !subDate.equals( _cast.subDate )) {
			return false;
		}
		
		if (reqId == null ? _cast.reqId != reqId : !reqId.equals( _cast.reqId )) {
			return false;
		}
		
		if (reqTypeId != _cast.reqTypeId) {
			return false;
		}
		
		if (reqTypeIdNull != _cast.reqTypeIdNull) {
			return false;
		}
		
		if (regionId != _cast.regionId) {
			return false;
		}
		
		if (regionIdNull != _cast.regionIdNull) {
			return false;
		}
		
		if (requestorId != _cast.requestorId) {
			return false;
		}
		
		if (requestorIdNull != _cast.requestorIdNull) {
			return false;
		}
		
		if (_cast.processChainId != processChainId) {
			return false;
		}
		
		if (notify == null ? _cast.notify != notify : !notify.equals( _cast.notify )) {
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
		if (subDate != null) {
			_hashCode = 29 * _hashCode + subDate.hashCode();
		}
		
		if (reqId != null) {
			_hashCode = 29 * _hashCode + reqId.hashCode();
		}
		
		_hashCode = 29 * _hashCode + reqTypeId;
		_hashCode = 29 * _hashCode + (reqTypeIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + regionId;
		_hashCode = 29 * _hashCode + (regionIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + requestorId;
		_hashCode = 29 * _hashCode + (requestorIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + processChainId;
		
		if (notify != null) {
			_hashCode = 29 * _hashCode + notify.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return RetentionEmpSerReqMapPk
	 */
	public RetentionEmpSerReqMapPk createPk()
	{
		return new RetentionEmpSerReqMapPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.RetentionEmpSerReqMap: " );
		ret.append( "id=" + id );
		ret.append( ", subDate=" + subDate );
		ret.append( ", reqId=" + reqId );
		ret.append( ", reqTypeId=" + reqTypeId );
		ret.append( ", regionId=" + regionId );
		ret.append( ", requestorId=" + requestorId );
		ret.append( ", processChainId=" + processChainId );
		ret.append( ", notify=" + notify );
		return ret.toString();
	}


}
