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

public class LoanDetails extends PortalForm implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the LOAN_DETAILS table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column REQUESTER_ID in the LOAN_DETAILS table.
	 */
	protected int requesterId;

	/** 
	 * This attribute represents whether the primitive attribute requesterId is null.
	 */
	protected boolean requesterIdNull = true;

	/** 
	 * This attribute maps to the column ELIGIBILITY_AMOUNT in the LOAN_DETAILS table.
	 */
	protected double eligibilityAmount;

	/** 
	 * This attribute represents whether the primitive attribute eligibilityAmount is null.
	 */
	protected boolean eligibilityAmountNull = true;

	/** 
	 * This attribute maps to the column EMI_PERIOD in the LOAN_DETAILS table.
	 */
	protected int emiPeriod;

	/** 
	 * This attribute represents whether the primitive attribute emiPeriod is null.
	 */
	protected boolean emiPeriodNull = true;

	/** 
	 * This attribute maps to the column EMI_ELIGIBILITY in the LOAN_DETAILS table.
	 */
	protected int emiEligibility;

	/** 
	 * This attribute represents whether the primitive attribute emiEligibility is null.
	 */
	protected boolean emiEligibilityNull = true;

	/** 
	 * This attribute maps to the column GROSS_SALARY in the LOAN_DETAILS table.
	 */
	protected double grossSalary;

	/** 
	 * This attribute represents whether the primitive attribute grossSalary is null.
	 */
	protected boolean grossSalaryNull = true;

	/** 
	 * This attribute maps to the column NET_SALARY in the LOAN_DETAILS table.
	 */
	protected double netSalary;

	/** 
	 * This attribute represents whether the primitive attribute netSalary is null.
	 */
	protected boolean netSalaryNull = true;

	/** 
	 * This attribute maps to the column APPROVED_AMOUNT in the LOAN_DETAILS table.
	 */
	protected double approvedAmount;

	/** 
	 * This attribute represents whether the primitive attribute approvedAmount is null.
	 */
	protected boolean approvedAmountNull = true;

	/** 
	 * This attribute maps to the column EMI_PAID in the LOAN_DETAILS table.
	 */
	protected int emiPaid;

	/** 
	 * This attribute represents whether the primitive attribute emiPaid is null.
	 */
	protected boolean emiPaidNull = true;

	/** 
	 * This attribute maps to the column REMAINING_AMOUNT in the LOAN_DETAILS table.
	 */
	protected double remainingAmount;

	/** 
	 * This attribute represents whether the primitive attribute remainingAmount is null.
	 */
	protected boolean remainingAmountNull = true;

	/** 
	 * This attribute maps to the column PURPOSE in the LOAN_DETAILS table.
	 */
	protected String purpose;

	/** 
	 * This attribute maps to the column STATUS_ID in the LOAN_DETAILS table.
	 */
	protected int statusId;

	/** 
	 * This attribute represents whether the primitive attribute statusId is null.
	 */
	protected boolean statusIdNull = true;

	/** 
	 * This attribute maps to the column APPLY_DATE in the LOAN_DETAILS table.
	 */
	protected Date applyDate;

	/** 
	 * This attribute maps to the column CREATE_DATE_TIME in the LOAN_DETAILS table.
	 */
	protected Date createDateTime;

	/** 
	 * This attribute maps to the column RESPONSE_DATE in the LOAN_DETAILS table.
	 */
	protected Date responseDate;

	/** 
	 * This attribute maps to the column LOAN_TYPE_ID in the LOAN_DETAILS table.
	 */
	protected int loanTypeId;

	/** 
	 * This attribute represents whether the primitive attribute loanTypeId is null.
	 */
	protected boolean loanTypeIdNull = true;

	/**
	 * Method 'LoanDetails'
	 * 
	 */
	public LoanDetails()
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
	 * Method 'getRequesterId'
	 * 
	 * @return int
	 */
	public int getRequesterId()
	{
		return requesterId;
	}

	/**
	 * Method 'setRequesterId'
	 * 
	 * @param requesterId
	 */
	public void setRequesterId(int requesterId)
	{
		this.requesterId = requesterId;
		this.requesterIdNull = false;
	}

	/**
	 * Method 'setRequesterIdNull'
	 * 
	 * @param value
	 */
	public void setRequesterIdNull(boolean value)
	{
		this.requesterIdNull = value;
	}

	/**
	 * Method 'isRequesterIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isRequesterIdNull()
	{
		return requesterIdNull;
	}

	/**
	 * Method 'getEligibilityAmount'
	 * 
	 * @return double
	 */
	public double getEligibilityAmount()
	{
		return eligibilityAmount;
	}

	/**
	 * Method 'setEligibilityAmount'
	 * 
	 * @param eligibilityAmount
	 */
	public void setEligibilityAmount(double eligibilityAmount)
	{
		this.eligibilityAmount = eligibilityAmount;
		this.eligibilityAmountNull = false;
	}

	/**
	 * Method 'setEligibilityAmountNull'
	 * 
	 * @param value
	 */
	public void setEligibilityAmountNull(boolean value)
	{
		this.eligibilityAmountNull = value;
	}

	/**
	 * Method 'isEligibilityAmountNull'
	 * 
	 * @return boolean
	 */
	public boolean isEligibilityAmountNull()
	{
		return eligibilityAmountNull;
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
	 * Method 'getEmiEligibility'
	 * 
	 * @return int
	 */
	public int getEmiEligibility()
	{
		return emiEligibility;
	}

	/**
	 * Method 'setEmiEligibility'
	 * 
	 * @param emiEligibility
	 */
	public void setEmiEligibility(int emiEligibility)
	{
		this.emiEligibility = emiEligibility;
		this.emiEligibilityNull = false;
	}

	/**
	 * Method 'setEmiEligibilityNull'
	 * 
	 * @param value
	 */
	public void setEmiEligibilityNull(boolean value)
	{
		this.emiEligibilityNull = value;
	}

	/**
	 * Method 'isEmiEligibilityNull'
	 * 
	 * @return boolean
	 */
	public boolean isEmiEligibilityNull()
	{
		return emiEligibilityNull;
	}

	/**
	 * Method 'getGrossSalary'
	 * 
	 * @return double
	 */
	public double getGrossSalary()
	{
		return grossSalary;
	}

	/**
	 * Method 'setGrossSalary'
	 * 
	 * @param grossSalary
	 */
	public void setGrossSalary(double grossSalary)
	{
		this.grossSalary = grossSalary;
		this.grossSalaryNull = false;
	}

	/**
	 * Method 'setGrossSalaryNull'
	 * 
	 * @param value
	 */
	public void setGrossSalaryNull(boolean value)
	{
		this.grossSalaryNull = value;
	}

	/**
	 * Method 'isGrossSalaryNull'
	 * 
	 * @return boolean
	 */
	public boolean isGrossSalaryNull()
	{
		return grossSalaryNull;
	}

	/**
	 * Method 'getNetSalary'
	 * 
	 * @return double
	 */
	public double getNetSalary()
	{
		return netSalary;
	}

	/**
	 * Method 'setNetSalary'
	 * 
	 * @param netSalary
	 */
	public void setNetSalary(double netSalary)
	{
		this.netSalary = netSalary;
		this.netSalaryNull = false;
	}

	/**
	 * Method 'setNetSalaryNull'
	 * 
	 * @param value
	 */
	public void setNetSalaryNull(boolean value)
	{
		this.netSalaryNull = value;
	}

	/**
	 * Method 'isNetSalaryNull'
	 * 
	 * @return boolean
	 */
	public boolean isNetSalaryNull()
	{
		return netSalaryNull;
	}

	/**
	 * Method 'getApprovedAmount'
	 * 
	 * @return double
	 */
	public double getApprovedAmount()
	{
		return approvedAmount;
	}

	/**
	 * Method 'setApprovedAmount'
	 * 
	 * @param approvedAmount
	 */
	public void setApprovedAmount(double approvedAmount)
	{
		this.approvedAmount = approvedAmount;
		this.approvedAmountNull = false;
	}

	/**
	 * Method 'setApprovedAmountNull'
	 * 
	 * @param value
	 */
	public void setApprovedAmountNull(boolean value)
	{
		this.approvedAmountNull = value;
	}

	/**
	 * Method 'isApprovedAmountNull'
	 * 
	 * @return boolean
	 */
	public boolean isApprovedAmountNull()
	{
		return approvedAmountNull;
	}

	/**
	 * Method 'getEmiPaid'
	 * 
	 * @return int
	 */
	public int getEmiPaid()
	{
		return emiPaid;
	}

	/**
	 * Method 'setEmiPaid'
	 * 
	 * @param emiPaid
	 */
	public void setEmiPaid(int emiPaid)
	{
		this.emiPaid = emiPaid;
		this.emiPaidNull = false;
	}

	/**
	 * Method 'setEmiPaidNull'
	 * 
	 * @param value
	 */
	public void setEmiPaidNull(boolean value)
	{
		this.emiPaidNull = value;
	}

	/**
	 * Method 'isEmiPaidNull'
	 * 
	 * @return boolean
	 */
	public boolean isEmiPaidNull()
	{
		return emiPaidNull;
	}

	/**
	 * Method 'getRemainingAmount'
	 * 
	 * @return double
	 */
	public double getRemainingAmount()
	{
		return remainingAmount;
	}

	/**
	 * Method 'setRemainingAmount'
	 * 
	 * @param remainingAmount
	 */
	public void setRemainingAmount(double remainingAmount)
	{
		this.remainingAmount = remainingAmount;
		this.remainingAmountNull = false;
	}

	/**
	 * Method 'setRemainingAmountNull'
	 * 
	 * @param value
	 */
	public void setRemainingAmountNull(boolean value)
	{
		this.remainingAmountNull = value;
	}

	/**
	 * Method 'isRemainingAmountNull'
	 * 
	 * @return boolean
	 */
	public boolean isRemainingAmountNull()
	{
		return remainingAmountNull;
	}

	/**
	 * Method 'getPurpose'
	 * 
	 * @return String
	 */
	public String getPurpose()
	{
		return purpose;
	}

	/**
	 * Method 'setPurpose'
	 * 
	 * @param purpose
	 */
	public void setPurpose(String purpose)
	{
		this.purpose = purpose;
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
	 * Method 'getApplyDate'
	 * 
	 * @return Date
	 */
	public Date getApplyDate()
	{
		return applyDate;
	}

	/**
	 * Method 'setApplyDate'
	 * 
	 * @param applyDate
	 */
	public void setApplyDate(Date applyDate)
	{
		this.applyDate = applyDate;
	}

	/**
	 * Method 'getCreateDateTime'
	 * 
	 * @return Date
	 */
	public Date getCreateDateTime()
	{
		return createDateTime;
	}

	/**
	 * Method 'setCreateDateTime'
	 * 
	 * @param createDateTime
	 */
	public void setCreateDateTime(Date createDateTime)
	{
		this.createDateTime = createDateTime;
	}

	/**
	 * Method 'getResponseDate'
	 * 
	 * @return Date
	 */
	public Date getResponseDate()
	{
		return responseDate;
	}

	/**
	 * Method 'setResponseDate'
	 * 
	 * @param responseDate
	 */
	public void setResponseDate(Date responseDate)
	{
		this.responseDate = responseDate;
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
		
		if (!(_other instanceof LoanDetails)) {
			return false;
		}
		
		final LoanDetails _cast = (LoanDetails) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (requesterId != _cast.requesterId) {
			return false;
		}
		
		if (requesterIdNull != _cast.requesterIdNull) {
			return false;
		}
		
		if (eligibilityAmount != _cast.eligibilityAmount) {
			return false;
		}
		
		if (eligibilityAmountNull != _cast.eligibilityAmountNull) {
			return false;
		}
		
		if (emiPeriod != _cast.emiPeriod) {
			return false;
		}
		
		if (emiPeriodNull != _cast.emiPeriodNull) {
			return false;
		}
		
		if (emiEligibility != _cast.emiEligibility) {
			return false;
		}
		
		if (emiEligibilityNull != _cast.emiEligibilityNull) {
			return false;
		}
		
		if (grossSalary != _cast.grossSalary) {
			return false;
		}
		
		if (grossSalaryNull != _cast.grossSalaryNull) {
			return false;
		}
		
		if (netSalary != _cast.netSalary) {
			return false;
		}
		
		if (netSalaryNull != _cast.netSalaryNull) {
			return false;
		}
		
		if (approvedAmount != _cast.approvedAmount) {
			return false;
		}
		
		if (approvedAmountNull != _cast.approvedAmountNull) {
			return false;
		}
		
		if (emiPaid != _cast.emiPaid) {
			return false;
		}
		
		if (emiPaidNull != _cast.emiPaidNull) {
			return false;
		}
		
		if (remainingAmount != _cast.remainingAmount) {
			return false;
		}
		
		if (remainingAmountNull != _cast.remainingAmountNull) {
			return false;
		}
		
		if (purpose == null ? _cast.purpose != purpose : !purpose.equals( _cast.purpose )) {
			return false;
		}
		
		if (statusId != _cast.statusId) {
			return false;
		}
		
		if (statusIdNull != _cast.statusIdNull) {
			return false;
		}
		
		if (applyDate == null ? _cast.applyDate != applyDate : !applyDate.equals( _cast.applyDate )) {
			return false;
		}
		
		if (createDateTime == null ? _cast.createDateTime != createDateTime : !createDateTime.equals( _cast.createDateTime )) {
			return false;
		}
		
		if (responseDate == null ? _cast.responseDate != responseDate : !responseDate.equals( _cast.responseDate )) {
			return false;
		}
		
		if (loanTypeId != _cast.loanTypeId) {
			return false;
		}
		
		if (loanTypeIdNull != _cast.loanTypeIdNull) {
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
		_hashCode = 29 * _hashCode + requesterId;
		_hashCode = 29 * _hashCode + (requesterIdNull ? 1 : 0);
		long temp_eligibilityAmount = Double.doubleToLongBits(eligibilityAmount);
		_hashCode = 29 * _hashCode + (int) (temp_eligibilityAmount ^ (temp_eligibilityAmount >>> 32));
		_hashCode = 29 * _hashCode + (eligibilityAmountNull ? 1 : 0);
		_hashCode = 29 * _hashCode + emiPeriod;
		_hashCode = 29 * _hashCode + (emiPeriodNull ? 1 : 0);
		_hashCode = 29 * _hashCode + emiEligibility;
		_hashCode = 29 * _hashCode + (emiEligibilityNull ? 1 : 0);
		long temp_grossSalary = Double.doubleToLongBits(grossSalary);
		_hashCode = 29 * _hashCode + (int) (temp_grossSalary ^ (temp_grossSalary >>> 32));
		_hashCode = 29 * _hashCode + (grossSalaryNull ? 1 : 0);
		long temp_netSalary = Double.doubleToLongBits(netSalary);
		_hashCode = 29 * _hashCode + (int) (temp_netSalary ^ (temp_netSalary >>> 32));
		_hashCode = 29 * _hashCode + (netSalaryNull ? 1 : 0);
		long temp_approvedAmount = Double.doubleToLongBits(approvedAmount);
		_hashCode = 29 * _hashCode + (int) (temp_approvedAmount ^ (temp_approvedAmount >>> 32));
		_hashCode = 29 * _hashCode + (approvedAmountNull ? 1 : 0);
		_hashCode = 29 * _hashCode + emiPaid;
		_hashCode = 29 * _hashCode + (emiPaidNull ? 1 : 0);
		long temp_remainingAmount = Double.doubleToLongBits(remainingAmount);
		_hashCode = 29 * _hashCode + (int) (temp_remainingAmount ^ (temp_remainingAmount >>> 32));
		_hashCode = 29 * _hashCode + (remainingAmountNull ? 1 : 0);
		if (purpose != null) {
			_hashCode = 29 * _hashCode + purpose.hashCode();
		}
		
		_hashCode = 29 * _hashCode + statusId;
		_hashCode = 29 * _hashCode + (statusIdNull ? 1 : 0);
		if (applyDate != null) {
			_hashCode = 29 * _hashCode + applyDate.hashCode();
		}
		
		if (createDateTime != null) {
			_hashCode = 29 * _hashCode + createDateTime.hashCode();
		}
		
		if (responseDate != null) {
			_hashCode = 29 * _hashCode + responseDate.hashCode();
		}
		
		_hashCode = 29 * _hashCode + loanTypeId;
		_hashCode = 29 * _hashCode + (loanTypeIdNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return LoanDetailsPk
	 */
	public LoanDetailsPk createPk()
	{
		return new LoanDetailsPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.LoanDetails: " );
		ret.append( "id=" + id );
		ret.append( ", requesterId=" + requesterId );
		ret.append( ", eligibilityAmount=" + eligibilityAmount );
		ret.append( ", emiPeriod=" + emiPeriod );
		ret.append( ", emiEligibility=" + emiEligibility );
		ret.append( ", grossSalary=" + grossSalary );
		ret.append( ", netSalary=" + netSalary );
		ret.append( ", approvedAmount=" + approvedAmount );
		ret.append( ", emiPaid=" + emiPaid );
		ret.append( ", remainingAmount=" + remainingAmount );
		ret.append( ", purpose=" + purpose );
		ret.append( ", statusId=" + statusId );
		ret.append( ", applyDate=" + applyDate );
		ret.append( ", createDateTime=" + createDateTime );
		ret.append( ", responseDate=" + responseDate );
		ret.append( ", loanTypeId=" + loanTypeId );
		return ret.toString();
	}
	

	protected int[] loanIds;
		
		private int esrqmId;

		

		public int getEsrqmId()
		{
			return esrqmId;
		}

		public void setEsrqmId(int esrqmId)
		{
			this.esrqmId = esrqmId;
		}

		public int[] getLoanIds()
		{
			return loanIds;
		}
		protected int loanReceiveId;
		public int getLoanReceiveId()
		{
			return loanReceiveId;
		}
		private int assignTo;
		
	public int getAssignTo()
		{
			return assignTo;
		}

		public void setAssignTo(int assignTo)
		{
			this.assignTo = assignTo;
		}
	private String actionType;
		
	private String comment;

		public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

		public String getActionType()
		{
			return actionType;
		}

		public void setActionType(String actionType)
		{
			this.actionType = actionType;
		}
		public void setLoanReceiveId(int loanReceiveId)
		{
			this.loanReceiveId = loanReceiveId;
		}

		public void setLoanIds(int[] loanIds)
		{
			this.loanIds = loanIds;
		}
	 private String status;
	 
		public String getStatus()
	{
		return status;
	}



	public void setStatus(String status)
	{
		this.status = status;
	}
	private boolean toApprove = false;
	private boolean toHandle = false;
	public boolean isToHandle()
	{
		return toHandle;
	}

	public void setToHandle(boolean toHandle)
	{
		this.toHandle = toHandle;
	}

	public boolean isToApprove()
	{
		return toApprove;
	}

	public void setToApprove(boolean toApprove)
	{
		this.toApprove = toApprove;
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
