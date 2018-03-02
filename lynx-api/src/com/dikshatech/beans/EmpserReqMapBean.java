package com.dikshatech.beans;

import java.util.Date;

public class EmpserReqMapBean
{

	protected int id;
	protected Date subDate;
	protected String reqId;
	protected int reqTypeId;
	protected boolean reqTypeIdNull = true;
	protected int regionId;
	protected boolean regionIdNull = true;
	protected int requestorId;
	protected boolean requestorIdNull = true;
	protected int processChainId;
	protected boolean processChainIdNull = true;
	protected String notify;
	protected int statusId;
	protected String StatusName;
	protected String RequestTypeName;
	protected Boolean isLoan;
	protected String LoanType;
	protected int loanId;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Date getSubDate()
	{
		return subDate;
	}

	public void setSubDate(Date subDate)
	{
		this.subDate = subDate;
	}

	public String getReqId()
	{
		return reqId;
	}

	public void setReqId(String reqId)
	{
		this.reqId = reqId;
	}

	public int getReqTypeId()
	{
		return reqTypeId;
	}

	public void setReqTypeId(int reqTypeId)
	{
		this.reqTypeId = reqTypeId;
		this.reqTypeIdNull = false;
	}

	public void setReqTypeIdNull(boolean value)
	{
		this.reqTypeIdNull = value;
	}

	public boolean isReqTypeIdNull()
	{
		return reqTypeIdNull;
	}

	public int getRegionId()
	{
		return regionId;
	}

	public void setRegionId(int regionId)
	{
		this.regionId = regionId;
		this.regionIdNull = false;
	}

	public void setRegionIdNull(boolean value)
	{
		this.regionIdNull = value;
	}

	public boolean isRegionIdNull()
	{
		return regionIdNull;
	}

	public int getRequestorId()
	{
		return requestorId;
	}

	public void setRequestorId(int requestorId)
	{
		this.requestorId = requestorId;
		this.requestorIdNull = false;
	}

	public void setRequestorIdNull(boolean value)
	{
		this.requestorIdNull = value;
	}

	public boolean isRequestorIdNull()
	{
		return requestorIdNull;
	}

	public int getProcessChainId()
	{
		return processChainId;
	}

	public boolean isProcessChainIdNull()
	{
		return processChainIdNull;
	}

	public void setProcessChainIdNull(boolean value)
	{
		this.processChainIdNull = value;
	}

	public void setProcessChainId(int processChainId)
	{
		this.processChainId = processChainId;
		processChainIdNull = false;
	}

	public int getLoanId()
	{
		return loanId;
	}

	public void setLoanId(int loanId)
	{
		this.loanId = loanId;
	}

	public Boolean getIsLoan()
	{
		return isLoan;
	}

	public void setIsLoan(Boolean isLoan)
	{
		this.isLoan = isLoan;
	}

	public String getLoanType()
	{
		return LoanType;
	}

	public void setLoanType(String loanType)
	{
		LoanType = loanType;
	}

	public String getRequestTypeName()
	{
		return RequestTypeName;
	}

	public void setRequestTypeName(String requestTypeName)
	{
		RequestTypeName = requestTypeName;
	}

	public int getStatusId()
	{
		return statusId;
	}

	public void setStatusId(int statusId)
	{
		this.statusId = statusId;
	}

	public String getStatusName()
	{
		return StatusName;
	}

	public void setStatusName(String statusName)
	{
		StatusName = statusName;
	}

	public String getNotify()
	{
		return notify;
	}

	public void setNotify(String notify)
	{
		this.notify = notify;
	}

}
