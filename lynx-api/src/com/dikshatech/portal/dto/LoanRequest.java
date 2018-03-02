/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dto;

import com.dikshatech.beans.LoanDetailsBean;
import com.dikshatech.portal.dao.*;
import com.dikshatech.portal.factory.*;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class LoanRequest extends PortalForm implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the LOAN_REQUEST table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column ESR_MAP_ID in the LOAN_REQUEST table.
	 */
	protected int esrMapId;

	/** 
	 * This attribute represents whether the primitive attribute esrMapId is null.
	 */
	protected boolean esrMapIdNull = true;

	/** 
	 * This attribute maps to the column CREATED_DATETIME in the LOAN_REQUEST table.
	 */
	protected Date createdDatetime;

	/** 
	 * This attribute maps to the column REQUESTED_LOAN_AMT in the LOAN_REQUEST table.
	 */
	protected double requestedLoanAmt;

	/** 
	 * This attribute represents whether the primitive attribute requestedLoanAmt is null.
	 */
	protected boolean requestedLoanAmtNull = true;

	/** 
	 * This attribute maps to the column EMI_PERIOD in the LOAN_REQUEST table.
	 */
	protected int emiPeriod;

	/** 
	 * This attribute represents whether the primitive attribute emiPeriod is null.
	 */
	protected boolean emiPeriodNull = true;

	/** 
	 * This attribute maps to the column STATUS_ID in the LOAN_REQUEST table.
	 */
	protected int statusId;

	/** 
	 * This attribute represents whether the primitive attribute statusId is null.
	 */
	protected boolean statusIdNull = true;

	/** 
	 * This attribute maps to the column ASSIGN_TO in the LOAN_REQUEST table.
	 */
	protected int assignTo;

	/** 
	 * This attribute represents whether the primitive attribute assignTo is null.
	 */
	protected boolean assignToNull = true;

	/** 
	 * This attribute maps to the column LOAN_ID in the LOAN_REQUEST table.
	 */
	protected int loanId;

	/** 
	 * This attribute represents whether the primitive attribute loanId is null.
	 */
	protected boolean loanIdNull = true;

	/** 
	 * This attribute maps to the column LOAN_TYPE_ID in the LOAN_REQUEST table.
	 */
	protected int loanTypeId;

	/** 
	 * This attribute represents whether the primitive attribute loanTypeId is null.
	 */
	protected boolean loanTypeIdNull = true;

	/** 
	 * This attribute maps to the column LOAN_USER_ID in the LOAN_REQUEST table.
	 */
	protected int loanUserId;

	/** 
	 * This attribute represents whether the primitive attribute loanUserId is null.
	 */
	protected boolean loanUserIdNull = true;

	/** 
	 * This attribute maps to the column EMAIL_DATA in the LOAN_REQUEST table.
	 */
	protected String emailData;

	/** 
	 * This attribute maps to the column ACTION_TAKEN_BY in the LOAN_REQUEST table.
	 */
	protected int actionTakenBy;

	/** 
	 * This attribute maps to the column ACTION_TAKEN_DATE in the LOAN_REQUEST table.
	 */
	protected Date actionTakenDate;

	/** 
	 * This attribute maps to the column SEQUENCE in the LOAN_REQUEST table.
	 */
	protected int sequence;

	/** 
	 * This attribute represents whether the primitive attribute sequence is null.
	 */
	protected boolean sequenceNull = true;

	/** 
	 * This attribute maps to the column COMMENTS in the LOAN_REQUEST table.
	 */
	protected String comments;

	/**
	 * Method 'LoanRequest'
	 * 
	 */
	public LoanRequest()
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
	 * Method 'getEsrMapId'
	 * 
	 * @return int
	 */
	public int getEsrMapId()
	{
		return esrMapId;
	}

	/**
	 * Method 'setEsrMapId'
	 * 
	 * @param esrMapId
	 */
	public void setEsrMapId(int esrMapId)
	{
		this.esrMapId = esrMapId;
		this.esrMapIdNull = false;
	}

	/**
	 * Method 'setEsrMapIdNull'
	 * 
	 * @param value
	 */
	public void setEsrMapIdNull(boolean value)
	{
		this.esrMapIdNull = value;
	}

	/**
	 * Method 'isEsrMapIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isEsrMapIdNull()
	{
		return esrMapIdNull;
	}

	/**
	 * Method 'getCreatedDatetime'
	 * 
	 * @return Date
	 */
	public Date getCreatedDatetime()
	{
		return createdDatetime;
	}

	/**
	 * Method 'setCreatedDatetime'
	 * 
	 * @param createdDatetime
	 */
	public void setCreatedDatetime(Date createdDatetime)
	{
		this.createdDatetime = createdDatetime;
	}

	/**
	 * Method 'getRequestedLoanAmt'
	 * 
	 * @return double
	 */
	public double getRequestedLoanAmt()
	{
		return requestedLoanAmt;
	}

	/**
	 * Method 'setRequestedLoanAmt'
	 * 
	 * @param requestedLoanAmt
	 */
	public void setRequestedLoanAmt(double requestedLoanAmt)
	{
		this.requestedLoanAmt = requestedLoanAmt;
		this.requestedLoanAmtNull = false;
	}

	/**
	 * Method 'setRequestedLoanAmtNull'
	 * 
	 * @param value
	 */
	public void setRequestedLoanAmtNull(boolean value)
	{
		this.requestedLoanAmtNull = value;
	}

	/**
	 * Method 'isRequestedLoanAmtNull'
	 * 
	 * @return boolean
	 */
	public boolean isRequestedLoanAmtNull()
	{
		return requestedLoanAmtNull;
	}

	/**
	 * Method 'getEmiPeriod'
	 * 
	 * @return int
	 */
	public int getEmiPeriod()
	{
		return emiPeriod;
	}

	/**
	 * Method 'setEmiPeriod'
	 * 
	 * @param emiPeriod
	 */
	public void setEmiPeriod(int emiPeriod)
	{
		this.emiPeriod = emiPeriod;
		this.emiPeriodNull = false;
	}

	/**
	 * Method 'setEmiPeriodNull'
	 * 
	 * @param value
	 */
	public void setEmiPeriodNull(boolean value)
	{
		this.emiPeriodNull = value;
	}

	/**
	 * Method 'isEmiPeriodNull'
	 * 
	 * @return boolean
	 */
	public boolean isEmiPeriodNull()
	{
		return emiPeriodNull;
	}

	/**
	 * Method 'getStatusId'
	 * 
	 * @return int
	 */
	public int getStatusId()
	{
		return statusId;
	}

	/**
	 * Method 'setStatusId'
	 * 
	 * @param statusId
	 */
	public void setStatusId(int statusId)
	{
		this.statusId = statusId;
		this.statusIdNull = false;
	}

	/**
	 * Method 'setStatusIdNull'
	 * 
	 * @param value
	 */
	public void setStatusIdNull(boolean value)
	{
		this.statusIdNull = value;
	}

	/**
	 * Method 'isStatusIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isStatusIdNull()
	{
		return statusIdNull;
	}

	/**
	 * Method 'getAssignTo'
	 * 
	 * @return int
	 */
	public int getAssignTo()
	{
		return assignTo;
	}

	/**
	 * Method 'setAssignTo'
	 * 
	 * @param assignTo
	 */
	public void setAssignTo(int assignTo)
	{
		this.assignTo = assignTo;
		this.assignToNull = false;
	}

	/**
	 * Method 'setAssignToNull'
	 * 
	 * @param value
	 */
	public void setAssignToNull(boolean value)
	{
		this.assignToNull = value;
	}

	/**
	 * Method 'isAssignToNull'
	 * 
	 * @return boolean
	 */
	public boolean isAssignToNull()
	{
		return assignToNull;
	}

	/**
	 * Method 'getLoanId'
	 * 
	 * @return int
	 */
	public int getLoanId()
	{
		return loanId;
	}

	/**
	 * Method 'setLoanId'
	 * 
	 * @param loanId
	 */
	public void setLoanId(int loanId)
	{
		this.loanId = loanId;
		this.loanIdNull = false;
	}

	/**
	 * Method 'setLoanIdNull'
	 * 
	 * @param value
	 */
	public void setLoanIdNull(boolean value)
	{
		this.loanIdNull = value;
	}

	/**
	 * Method 'isLoanIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isLoanIdNull()
	{
		return loanIdNull;
	}

	/**
	 * Method 'getLoanTypeId'
	 * 
	 * @return int
	 */
	public int getLoanTypeId()
	{
		return loanTypeId;
	}

	/**
	 * Method 'setLoanTypeId'
	 * 
	 * @param loanTypeId
	 */
	public void setLoanTypeId(int loanTypeId)
	{
		this.loanTypeId = loanTypeId;
		this.loanTypeIdNull = false;
	}

	/**
	 * Method 'setLoanTypeIdNull'
	 * 
	 * @param value
	 */
	public void setLoanTypeIdNull(boolean value)
	{
		this.loanTypeIdNull = value;
	}

	/**
	 * Method 'isLoanTypeIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isLoanTypeIdNull()
	{
		return loanTypeIdNull;
	}

	/**
	 * Method 'getLoanUserId'
	 * 
	 * @return int
	 */
	public int getLoanUserId()
	{
		return loanUserId;
	}

	/**
	 * Method 'setLoanUserId'
	 * 
	 * @param loanUserId
	 */
	public void setLoanUserId(int loanUserId)
	{
		this.loanUserId = loanUserId;
		this.loanUserIdNull = false;
	}

	/**
	 * Method 'setLoanUserIdNull'
	 * 
	 * @param value
	 */
	public void setLoanUserIdNull(boolean value)
	{
		this.loanUserIdNull = value;
	}

	/**
	 * Method 'isLoanUserIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isLoanUserIdNull()
	{
		return loanUserIdNull;
	}

	/**
	 * Method 'getEmailData'
	 * 
	 * @return String
	 */
	public String getEmailData()
	{
		return emailData;
	}

	/**
	 * Method 'setEmailData'
	 * 
	 * @param emailData
	 */
	public void setEmailData(String emailData)
	{
		this.emailData = emailData;
	}

	/**
	 * Method 'getActionTakenBy'
	 * 
	 * @return int
	 */
	public int getActionTakenBy()
	{
		return actionTakenBy;
	}

	/**
	 * Method 'setActionTakenBy'
	 * 
	 * @param actionTakenBy
	 */
	public void setActionTakenBy(int actionTakenBy)
	{
		this.actionTakenBy = actionTakenBy;
	}

	/**
	 * Method 'getActionTakenDate'
	 * 
	 * @return Date
	 */
	public Date getActionTakenDate()
	{
		return actionTakenDate;
	}

	/**
	 * Method 'setActionTakenDate'
	 * 
	 * @param actionTakenDate
	 */
	public void setActionTakenDate(Date actionTakenDate)
	{
		this.actionTakenDate = actionTakenDate;
	}

	/**
	 * Method 'getSequence'
	 * 
	 * @return int
	 */
	public int getSequence()
	{
		return sequence;
	}

	/**
	 * Method 'setSequence'
	 * 
	 * @param sequence
	 */
	public void setSequence(int sequence)
	{
		this.sequence = sequence;
		this.sequenceNull = false;
	}

	/**
	 * Method 'setSequenceNull'
	 * 
	 * @param value
	 */
	public void setSequenceNull(boolean value)
	{
		this.sequenceNull = value;
	}

	/**
	 * Method 'isSequenceNull'
	 * 
	 * @return boolean
	 */
	public boolean isSequenceNull()
	{
		return sequenceNull;
	}

	/**
	 * Method 'getComments'
	 * 
	 * @return String
	 */
	public String getComments()
	{
		return comments;
	}

	/**
	 * Method 'setComments'
	 * 
	 * @param comments
	 */
	public void setComments(String comments)
	{
		this.comments = comments;
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
		
		if (!(_other instanceof LoanRequest)) {
			return false;
		}
		
		final LoanRequest _cast = (LoanRequest) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (esrMapId != _cast.esrMapId) {
			return false;
		}
		
		if (esrMapIdNull != _cast.esrMapIdNull) {
			return false;
		}
		
		if (createdDatetime == null ? _cast.createdDatetime != createdDatetime : !createdDatetime.equals( _cast.createdDatetime )) {
			return false;
		}
		
		if (requestedLoanAmt != _cast.requestedLoanAmt) {
			return false;
		}
		
		if (requestedLoanAmtNull != _cast.requestedLoanAmtNull) {
			return false;
		}
		
		if (emiPeriod != _cast.emiPeriod) {
			return false;
		}
		
		if (emiPeriodNull != _cast.emiPeriodNull) {
			return false;
		}
		
		if (statusId != _cast.statusId) {
			return false;
		}
		
		if (statusIdNull != _cast.statusIdNull) {
			return false;
		}
		
		if (assignTo != _cast.assignTo) {
			return false;
		}
		
		if (assignToNull != _cast.assignToNull) {
			return false;
		}
		
		if (loanId != _cast.loanId) {
			return false;
		}
		
		if (loanIdNull != _cast.loanIdNull) {
			return false;
		}
		
		if (loanTypeId != _cast.loanTypeId) {
			return false;
		}
		
		if (loanTypeIdNull != _cast.loanTypeIdNull) {
			return false;
		}
		
		if (loanUserId != _cast.loanUserId) {
			return false;
		}
		
		if (loanUserIdNull != _cast.loanUserIdNull) {
			return false;
		}
		
		if (emailData == null ? _cast.emailData != emailData : !emailData.equals( _cast.emailData )) {
			return false;
		}
		
		if (actionTakenBy != _cast.actionTakenBy) {
			return false;
		}
		
		if (actionTakenDate == null ? _cast.actionTakenDate != actionTakenDate : !actionTakenDate.equals( _cast.actionTakenDate )) {
			return false;
		}
		
		if (sequence != _cast.sequence) {
			return false;
		}
		
		if (sequenceNull != _cast.sequenceNull) {
			return false;
		}
		
		if (comments == null ? _cast.comments != comments : !comments.equals( _cast.comments )) {
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
		_hashCode = 29 * _hashCode + esrMapId;
		_hashCode = 29 * _hashCode + (esrMapIdNull ? 1 : 0);
		if (createdDatetime != null) {
			_hashCode = 29 * _hashCode + createdDatetime.hashCode();
		}
		
		long temp_requestedLoanAmt = Double.doubleToLongBits(requestedLoanAmt);
		_hashCode = 29 * _hashCode + (int) (temp_requestedLoanAmt ^ (temp_requestedLoanAmt >>> 32));
		_hashCode = 29 * _hashCode + (requestedLoanAmtNull ? 1 : 0);
		_hashCode = 29 * _hashCode + emiPeriod;
		_hashCode = 29 * _hashCode + (emiPeriodNull ? 1 : 0);
		_hashCode = 29 * _hashCode + statusId;
		_hashCode = 29 * _hashCode + (statusIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + assignTo;
		_hashCode = 29 * _hashCode + (assignToNull ? 1 : 0);
		_hashCode = 29 * _hashCode + loanId;
		_hashCode = 29 * _hashCode + (loanIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + loanTypeId;
		_hashCode = 29 * _hashCode + (loanTypeIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + loanUserId;
		_hashCode = 29 * _hashCode + (loanUserIdNull ? 1 : 0);
		if (emailData != null) {
			_hashCode = 29 * _hashCode + emailData.hashCode();
		}
		
		_hashCode = 29 * _hashCode + actionTakenBy;
		if (actionTakenDate != null) {
			_hashCode = 29 * _hashCode + actionTakenDate.hashCode();
		}
		
		_hashCode = 29 * _hashCode + sequence;
		_hashCode = 29 * _hashCode + (sequenceNull ? 1 : 0);
		if (comments != null) {
			_hashCode = 29 * _hashCode + comments.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return LoanRequestPk
	 */
	public LoanRequestPk createPk()
	{
		return new LoanRequestPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.LoanRequest: " );
		ret.append( "id=" + id );
		ret.append( ", esrMapId=" + esrMapId );
		ret.append( ", createdDatetime=" + createdDatetime );
		ret.append( ", requestedLoanAmt=" + requestedLoanAmt );
		ret.append( ", emiPeriod=" + emiPeriod );
		ret.append( ", statusId=" + statusId );
		ret.append( ", assignTo=" + assignTo );
		ret.append( ", loanId=" + loanId );
		ret.append( ", loanTypeId=" + loanTypeId );
		ret.append( ", loanUserId=" + loanUserId );
		ret.append( ", emailData=" + emailData );
		ret.append( ", actionTakenBy=" + actionTakenBy );
		ret.append( ", actionTakenDate=" + actionTakenDate );
		ret.append( ", sequence=" + sequence );
		ret.append( ", comments=" + comments );
		return ret.toString();
	}


	private LoanDetailsBean[] loanBeanArr;
	
	public LoanDetailsBean[] getLoanBeanArr()
	{
		return loanBeanArr;
	}

	public void setLoanBeanArr(LoanDetailsBean[] loanBeanArr)
	{
		this.loanBeanArr = loanBeanArr;
	}

}
