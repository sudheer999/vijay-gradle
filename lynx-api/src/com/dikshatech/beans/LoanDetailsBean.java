package com.dikshatech.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dikshatech.portal.dto.LoanEligibilityCriteria;
import com.dikshatech.portal.forms.DropDown;

public class LoanDetailsBean
{
	//loan details field
	private String loanReqId;
	private String loanType;
	private int loneId;
	
	private Double loanAmt;
	private String requestedOn;
	private String loanStatus;
	private int emiPeriod;
	private String comment;
	public String getComment()
	{
		return comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	private String purpose;
	private String createdDate;
	
	//Eligibility Details
	private Double availableAmt;
	private Double eligibilityAmt;
	private int emiEligibility;
	private Double grossSalary;
	private Double netSalary;
	DropDown dropDown;
	List<Object> loanReqList  = new ArrayList<Object>();
	public List<Object> getLoanReqList()
	{
		return loanReqList;
	}
	public void setLoanReqList(List<Object> loanReqList)
	{
		this.loanReqList = loanReqList;
	}
	//Employee Details
	private int empId;
	private String requestedBy;
	private String designation;
	private String department;
	private String responseBy;
	private String responseDate;
	private int loanReceiveId=0;
	
	//handlers
	//private String[] handlers;
	//private String[] status;
	//private Set<Integer>lonId = new HashSet<Integer>();
	//private String[] LoanTypeArr;
	
	HandlerAction handlerAction;
	public HandlerAction getHandlerAction()
	{
		return handlerAction;
	}
	public void setHandlerAction(HandlerAction handlerAction)
	{
		this.handlerAction = handlerAction;
	}
	public int getLoanReceiveId()
	{
		return loanReceiveId;
	}
	public void setLoanReceiveId(int loanReceiveId)
	{
		this.loanReceiveId = loanReceiveId;
	}
	
	/*private boolean toHandle = false;
	
	public boolean isToHandle()
	{
		return toHandle;
	}
	public void setToHandle(boolean toHandle)
	{
		this.toHandle = toHandle;
	}
	private boolean toApprove = false;
	
	public boolean isToApprove()
	{
		return toApprove;
	}
	public void setToApprove(boolean toApprove)
	{
		this.toApprove = toApprove;
	}*/
	private LoanEligibilityCriteria LoanELC;
	
	/*public void setHandlers(String[] handlers)
	{
		this.handlers = handlers;
	}
	public void setStatus(String[] status)
	{
		this.status = status;
	}
	public void setLoanTypeArr(String[] loanTypeArr)
	{
		LoanTypeArr = loanTypeArr;
	}*/
	public String getResponseBy()
	{
		return responseBy;
	}
	public String getResponseDate()
	{
		return responseDate;
	}
	public void setResponseBy(String responseBy)
	{
		this.responseBy = responseBy;
	}
	public void setResponseDate(String responseDate)
	{
		this.responseDate = responseDate;
	}
	
	public String getLoanType()
	{
		return loanType;
	}
	
	public void setLoanType(String loanType)
	{
		this.loanType = loanType;
	}
	
	
	
	public String getLoanReqId()
	{
		return loanReqId;
	}
	public String getCreatedDate()
	{
		return createdDate;
	}
	
	public Double getLoanAmt()
	{
		return loanAmt;
	}
	public String getRequestedOn()
	{
		return requestedOn;
	}
	public String getLoanStatus()
	{
		return loanStatus;
	}
	public int getEmiPeriod()
	{
		return emiPeriod;
	}
	public String getPurpose()
	{
		return purpose;
	}
	public Double getEligibilityAmt()
	{
		return eligibilityAmt;
	}
	public int getEmiEligibility()
	{
		return emiEligibility;
	}
	public Double getGrossSalary()
	{
		return grossSalary;
	}
	public Double getNetSalary()
	{
		return netSalary;
	}
	public int getEmpId()
	{
		return empId;
	}
	public String getRequestedBy()
	{
		return requestedBy;
	}
	public String getDesignation()
	{
		return designation;
	}
	public String getDepartment()
	{
		return department;
	}
	public void setLoanReqId(String loanReqId)
	{
		this.loanReqId = loanReqId;
	}
	public void setCreatedDate(String createdDate)
	{
		this.createdDate = createdDate;
	}
	
	public void setLoanAmt(Double loanAmt)
	{
		this.loanAmt = loanAmt;
	}
	public LoanEligibilityCriteria getLoanELC()
	{
		return LoanELC;
	}
	public void setLoanELC(LoanEligibilityCriteria loanELC)
	{
		LoanELC = loanELC;
	}
	public void setRequestedOn(String requestedOn)
	{
		this.requestedOn = requestedOn;
	}
	public void setLoanStatus(String loanStatus)
	{
		this.loanStatus = loanStatus;
	}
	public void setEmiPeriod(int emiPeriod)
	{
		this.emiPeriod = emiPeriod;
	}
	public void setPurpose(String purpose)
	{
		this.purpose = purpose;
	}
	public void setEligibilityAmt(Double eligibilityAmt)
	{
		this.eligibilityAmt = eligibilityAmt;
	}
	public void setEmiEligibility(int emiEligibility)
	{
		this.emiEligibility = emiEligibility;
	}
	public void setGrossSalary(Double grossSalary)
	{
		this.grossSalary = grossSalary;
	}
	public void setNetSalary(Double netSalary)
	{
		this.netSalary = netSalary;
	}
	public void setEmpId(int empId)
	{
		this.empId = empId;
	}
	public void setRequestedBy(String requestedBy)
	{
		this.requestedBy = requestedBy;
	}
	public void setDesignation(String designation)
	{
		this.designation = designation;
	}
	public void setDepartment(String department)
	{
		this.department = department;
	}
	/*public ArrayList<String> getHandlers()
	{
		return handlers;
	}
	public void setHandlers(ArrayList<String> handlers)
	{
		this.handlers = handlers;
	}
	public ArrayList<String> getStatus()
	{
		return status;
	}
	public void setStatus(ArrayList<String> status)
	{
		this.status = status;
	}
	public ArrayList<String> getLoanTypeArr()
	{
		return LoanTypeArr;
	}
	public void setLoanTypeArr(ArrayList<String> loanTypeArr)
	{
		LoanTypeArr = loanTypeArr;
	}*/
	
	public int getLoneId()
	{
		return loneId;
	}
	public void setLoneId(int loneId)
	{
		this.loneId = loneId;
	}
	public Double getAvailableAmt()
	{
		return availableAmt;
	}
	public void setAvailableAmt(Double availableAmt)
	{
		this.availableAmt = availableAmt;
	}


}
