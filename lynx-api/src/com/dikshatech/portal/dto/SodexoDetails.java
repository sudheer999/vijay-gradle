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

public class SodexoDetails extends PortalForm implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the SODEXO_DETAILS table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column REQUESTOR_ID in the SODEXO_DETAILS table.
	 */
	protected int requestorId;

	/** 
	 * This attribute represents whether the primitive attribute requestorId is null.
	 */
	protected boolean requestorIdNull = true;

	/** 
	 * This attribute maps to the column AMOUNT_ELIGIBLE in the SODEXO_DETAILS table.
	 */
	protected int amountEligible;

	/** 
	 * This attribute represents whether the primitive attribute amountEligible is null.
	 */
	protected boolean amountEligibleNull = true;

	/** 
	 * This attribute maps to the column AMOUNT_AVAILED in the SODEXO_DETAILS table.
	 */
	protected int amountAvailed;

	/** 
	 * This attribute represents whether the primitive attribute amountAvailed is null.
	 */
	protected boolean amountAvailedNull = true;

	/** 
	 * This attribute maps to the column REQUESTED_ON in the SODEXO_DETAILS table.
	 */
	protected Date requestedOn;

	/** 
	 * This attribute maps to the column STATUS in the SODEXO_DETAILS table.
	 */
	protected int status;

	/** 
	 * This attribute represents whether the primitive attribute status is null.
	 */
	protected boolean statusNull = true;

	/** 
	 * This attribute maps to the column SR_TYPE in the SODEXO_DETAILS table.
	 */
	protected String srType;

	/** 
	 * This attribute maps to the column DELIVERY_ADDRESS in the SODEXO_DETAILS table.
	 */
	protected String deliveryAddress;

	/** 
	 * This attribute maps to the column ADDRESS_HTML in the SODEXO_DETAILS table.
	 */
	protected String addressHtml;

	/** 
	 * This attribute maps to the column ADDRESS_FLAG in the SODEXO_DETAILS table.
	 */
	protected int addressFlag;

	/** 
	 * This attribute represents whether the primitive attribute addressFlag is null.
	 */
	protected boolean addressFlagNull = true;
	
	private String previousStatus;

	/**
	 * Method 'SodexoDetails'
	 * 
	 */
	public SodexoDetails()
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
	 * Method 'getAmountEligible'
	 * 
	 * @return int
	 */
	public int getAmountEligible()
	{
		return amountEligible;
	}

	/**
	 * Method 'setAmountEligible'
	 * 
	 * @param amountEligible
	 */
	public void setAmountEligible(int amountEligible)
	{
		this.amountEligible = amountEligible;
		this.amountEligibleNull = false;
	}

	/**
	 * Method 'setAmountEligibleNull'
	 * 
	 * @param value
	 */
	public void setAmountEligibleNull(boolean value)
	{
		this.amountEligibleNull = value;
	}

	/**
	 * Method 'isAmountEligibleNull'
	 * 
	 * @return boolean
	 */
	public boolean isAmountEligibleNull()
	{
		return amountEligibleNull;
	}

	/**
	 * Method 'getAmountAvailed'
	 * 
	 * @return int
	 */
	public int getAmountAvailed()
	{
		return amountAvailed;
	}

	/**
	 * Method 'setAmountAvailed'
	 * 
	 * @param amountAvailed
	 */
	public void setAmountAvailed(int amountAvailed)
	{
		this.amountAvailed = amountAvailed;
		this.amountAvailedNull = false;
	}

	/**
	 * Method 'setAmountAvailedNull'
	 * 
	 * @param value
	 */
	public void setAmountAvailedNull(boolean value)
	{
		this.amountAvailedNull = value;
	}

	/**
	 * Method 'isAmountAvailedNull'
	 * 
	 * @return boolean
	 */
	public boolean isAmountAvailedNull()
	{
		return amountAvailedNull;
	}

	/**
	 * Method 'getRequestedOn'
	 * 
	 * @return Date
	 */
	public Date getRequestedOn()
	{
		return requestedOn;
	}

	/**
	 * Method 'setRequestedOn'
	 * 
	 * @param requestedOn
	 */
	public void setRequestedOn(Date requestedOn)
	{
		this.requestedOn = requestedOn;
	}

	/**
	 * Method 'getStatus'
	 * 
	 * @return int
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * Method 'setStatus'
	 * 
	 * @param status
	 */
	public void setStatus(int status)
	{
		this.status = status;
		this.statusNull = false;
	}

	/**
	 * Method 'setStatusNull'
	 * 
	 * @param value
	 */
	public void setStatusNull(boolean value)
	{
		this.statusNull = value;
	}

	/**
	 * Method 'isStatusNull'
	 * 
	 * @return boolean
	 */
	public boolean isStatusNull()
	{
		return statusNull;
	}

	/**
	 * Method 'getSrType'
	 * 
	 * @return String
	 */
	public String getSrType()
	{
		return srType;
	}

	/**
	 * Method 'setSrType'
	 * 
	 * @param srType
	 */
	public void setSrType(String srType)
	{
		this.srType = srType;
	}

	/**
	 * Method 'getDeliveryAddress'
	 * 
	 * @return String
	 */
	public String getDeliveryAddress()
	{
		return deliveryAddress;
	}

	/**
	 * Method 'setDeliveryAddress'
	 * 
	 * @param deliveryAddress
	 */
	public void setDeliveryAddress(String deliveryAddress)
	{
		this.deliveryAddress = deliveryAddress;
	}

	/**
	 * Method 'getAddressHtml'
	 * 
	 * @return String
	 */
	public String getAddressHtml()
	{
		return addressHtml;
	}

	/**
	 * Method 'setAddressHtml'
	 * 
	 * @param addressHtml
	 */
	public void setAddressHtml(String addressHtml)
	{
		this.addressHtml = addressHtml;
	}

	/**
	 * Method 'getAddressFlag'
	 * 
	 * @return int
	 */
	public int getAddressFlag()
	{
		return addressFlag;
	}

	/**
	 * Method 'setAddressFlag'
	 * 
	 * @param addressFlag
	 */
	public void setAddressFlag(int addressFlag)
	{
		this.addressFlag = addressFlag;
		this.addressFlagNull = false;
	}

	/**
	 * Method 'setAddressFlagNull'
	 * 
	 * @param value
	 */
	public void setAddressFlagNull(boolean value)
	{
		this.addressFlagNull = value;
	}

	/**
	 * Method 'isAddressFlagNull'
	 * 
	 * @return boolean
	 */
	public boolean isAddressFlagNull()
	{
		return addressFlagNull;
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
		
		if (!(_other instanceof SodexoDetails)) {
			return false;
		}
		
		final SodexoDetails _cast = (SodexoDetails) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (requestorId != _cast.requestorId) {
			return false;
		}
		
		if (requestorIdNull != _cast.requestorIdNull) {
			return false;
		}
		
		if (amountEligible != _cast.amountEligible) {
			return false;
		}
		
		if (amountEligibleNull != _cast.amountEligibleNull) {
			return false;
		}
		
		if (amountAvailed != _cast.amountAvailed) {
			return false;
		}
		
		if (amountAvailedNull != _cast.amountAvailedNull) {
			return false;
		}
		
		if (requestedOn == null ? _cast.requestedOn != requestedOn : !requestedOn.equals( _cast.requestedOn )) {
			return false;
		}
		
		if (status != _cast.status) {
			return false;
		}
		
		if (statusNull != _cast.statusNull) {
			return false;
		}
		
		if (srType == null ? _cast.srType != srType : !srType.equals( _cast.srType )) {
			return false;
		}
		
		if (deliveryAddress == null ? _cast.deliveryAddress != deliveryAddress : !deliveryAddress.equals( _cast.deliveryAddress )) {
			return false;
		}
		
		if (addressHtml == null ? _cast.addressHtml != addressHtml : !addressHtml.equals( _cast.addressHtml )) {
			return false;
		}
		
		if (addressFlag != _cast.addressFlag) {
			return false;
		}
		
		if (addressFlagNull != _cast.addressFlagNull) {
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
		_hashCode = 29 * _hashCode + requestorId;
		_hashCode = 29 * _hashCode + (requestorIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + amountEligible;
		_hashCode = 29 * _hashCode + (amountEligibleNull ? 1 : 0);
		_hashCode = 29 * _hashCode + amountAvailed;
		_hashCode = 29 * _hashCode + (amountAvailedNull ? 1 : 0);
		if (requestedOn != null) {
			_hashCode = 29 * _hashCode + requestedOn.hashCode();
		}
		
		_hashCode = 29 * _hashCode + status;
		_hashCode = 29 * _hashCode + (statusNull ? 1 : 0);
		if (srType != null) {
			_hashCode = 29 * _hashCode + srType.hashCode();
		}
		
		if (deliveryAddress != null) {
			_hashCode = 29 * _hashCode + deliveryAddress.hashCode();
		}
		
		if (addressHtml != null) {
			_hashCode = 29 * _hashCode + addressHtml.hashCode();
		}
		
		_hashCode = 29 * _hashCode + addressFlag;
		_hashCode = 29 * _hashCode + (addressFlagNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return SodexoDetailsPk
	 */
	public SodexoDetailsPk createPk()
	{
		return new SodexoDetailsPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.SodexoDetails: " );
		ret.append( "id=" + id );
		ret.append( ", requestorId=" + requestorId );
		ret.append( ", amountEligible=" + amountEligible );
		ret.append( ", amountAvailed=" + amountAvailed );
		ret.append( ", requestedOn=" + requestedOn );
		ret.append( ", status=" + status );
		ret.append( ", srType=" + srType );
		ret.append( ", deliveryAddress=" + deliveryAddress );
		ret.append( ", addressHtml=" + addressHtml );
		ret.append( ", addressFlag=" + addressFlag );
		return ret.toString();
	}

	private String reqId = "";
	
	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public Integer getAssignedToHandler() {
		return assignedToHandler;
	}

	public void setAssignedToHandler(Integer assignedToHandler) {
		this.assignedToHandler = assignedToHandler;
	}

	public int getEsrMapId() {
		return esrMapId;
	}

	public void setEsrMapId(int esrMapId) {
		this.esrMapId = esrMapId;
	}

	/**
	 * @param previousStatus the previousStatus to set
	 */
	public void setPreviousStatus(String previousStatus) {
		this.previousStatus = previousStatus;
	}

	/**
	 * @return the previousStatus
	 */
	public String getPreviousStatus() {
		return previousStatus;
	}

	private Integer assignedToHandler;
	
	private int esrMapId;
}