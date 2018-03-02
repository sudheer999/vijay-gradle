package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.portal.dto.Handlers;
import com.dikshatech.portal.forms.PortalForm;

public class SodexoBean extends PortalForm implements Serializable {

	//SODEXO_DETAILS
	private Integer id;
	private Integer amountEligible;
	private Integer amountAvailed;
	private Integer balanceAmount;
	private Integer requestorId;
	private String requestedOn;//was Date before...changed to String
	private String status;				//NEW IN-PROGRESS COMPLETED  ON-HOLD   REJECTED
	private String sodexoRequestType;	//AVAIL  CHANGE    WITHDRAW
	private Integer statusId;
	
	private Integer previousAvailedAmount;
	
	
	//SODEXO_REQ
	private Integer sodexoReqId;
	private Integer esrMapId;
	private Integer assignedTo;
	private String assignedToPerson;
	private Integer actionBy;	
	private Date actionDate;
	private String messageBody;
	private String actionTakenBy;
	
	
	//EMP_SER_REQ_MAP
	private String requestId;
	private Integer requestTypeId;
	
	
	//employee details
	private Integer empId;
	private String department;
	private String divison;
	private String requestorName;
	private String Designation;
	
	
	
	private Handlers[] handlersArray;
	private HandlerAction handlerAction;
	
	private String deliveryAddress;
	private Integer addressFlag;
	private String addressHtml;
		
	
	private boolean assignButtonflag;
	
	private com.dikshatech.portal.dto.Status[] statusArray;
	
	/**
	 * @return the sodexoDetailsID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param sodexoDetailsID the sodexoDetailsID to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the amountEligible
	 */
	public Integer getAmountEligible() {
		return amountEligible;
	}
	/**
	 * @param amountEligible the amountEligible to set
	 */
	public void setAmountEligible(Integer amountEligible) {
		this.amountEligible = amountEligible;
	}
	/**
	 * @return the amountAvailed
	 */
	public Integer getAmountAvailed() {
		return amountAvailed;
	}
	/**
	 * @param amountAvailed the amountAvailed to set
	 */
	public void setAmountAvailed(Integer amountAvailed) {
		this.amountAvailed = amountAvailed;
	}
	/**
	 * @return the balanceAmount
	 */
	public Integer getBalanceAmount() {
		return balanceAmount;
	}
	/**
	 * @param balanceAmount the balanceAmount to set
	 */
	public void setBalanceAmount(Integer balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	/**
	 * @return the requestorId
	 */
	public Integer getRequestorId() {
		return requestorId;
	}
	/**
	 * @param requestorId the requestorId to set
	 */
	public void setRequestorId(Integer requestorId) {
		this.requestorId = requestorId;
	}
	
	
	/**
	 * @return the sodexoReqId
	 */
	public Integer getSodexoReqId() {
		return sodexoReqId;
	}
	/**
	 * @param sodexoReqId the sodexoReqId to set
	 */
	public void setSodexoReqId(Integer sodexoReqId) {
		this.sodexoReqId = sodexoReqId;
	}
	/**
	 * @return the esrMapId
	 */
	public Integer getEsrMapId() {
		return esrMapId;
	}
	/**
	 * @param esrMapId the esrMapId to set
	 */
	public void setEsrMapId(Integer esrMapId) {
		this.esrMapId = esrMapId;
	}
	/**
	 * @return the assignedTo
	 */
	public Integer getAssignedTo() {
		return assignedTo;
	}
	/**
	 * @param assignedTo the assignedTo to set
	 */
	public void setAssignedTo(Integer assignedTo) {
		this.assignedTo = assignedTo;
	}
	/**
	 * @return the actionBy
	 */
	public Integer getActionBy() {
		return actionBy;
	}
	/**
	 * @param actionBy the actionBy to set
	 */
	public void setActionBy(Integer actionBy) {
		this.actionBy = actionBy;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the actionDate
	 */
	public Date getActionDate() {
		return actionDate;
	}
	/**
	 * @param actionDate the actionDate to set
	 */
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}
	/**
	 * @param requestTypeId the requestTypeId to set
	 */
	public void setRequestTypeId(Integer requestTypeId) {
		this.requestTypeId = requestTypeId;
	}
	/**
	 * @return the requestTypeId
	 */
	public Integer getRequestTypeId() {
		return requestTypeId;
	}
	/**
	 * @param messageBody the messageBody to set
	 */
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	/**
	 * @return the messageBody
	 */
	public String getMessageBody() {
		return messageBody;
	}
	/**
	 * @param sodexoRequestType the sodexoRequestType to set
	 */
	public void setSodexoRequestType(String sodexoRequestType) {
		this.sodexoRequestType = sodexoRequestType;
	}
	/**
	 * @return the sodexoRequestType
	 */
	public String getSodexoRequestType() {
		return sodexoRequestType;
	}
	/**
	 * @param assignedToPerson the assignedToPerson to set
	 */
	public void setAssignedToPerson(String assignedToPerson) {
		this.assignedToPerson = assignedToPerson;
	}
	/**
	 * @return the assignedToPerson
	 */
	public String getAssignedToPerson() {
		return assignedToPerson;
	}
	/**
	 * @param requestorName the requestorName to set
	 */
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	/**
	 * @return the requestorName
	 */
	public String getRequestorName() {
		return requestorName;
	}
	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	/**
	 * @return the statusId
	 */
	public Integer getStatusId() {
		return statusId;
	}
	/**
	 * @param handlersArray the handlersArray to set
	 */
	public void setHandlersArray(Handlers[] handlersArray) {
		this.handlersArray = handlersArray;
	}
	/**
	 * @return the handlersArray
	 */
	public Handlers[] getHandlersArray() {
		return handlersArray;
	}
	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	/**
	 * @return the empId
	 */
	public Integer getEmpId() {
		return empId;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param divison the divison to set
	 */
	public void setDivison(String divison) {
		this.divison = divison;
	}
	/**
	 * @return the divison
	 */
	public String getDivison() {
		return divison;
	}
	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		Designation = designation;
	}
	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return Designation;
	}
	/**
	 * @param actionTakenBy the actionTakenBy to set
	 */
	public void setActionTakenBy(String actionTakenBy) {
		this.actionTakenBy = actionTakenBy;
	}
	/**
	 * @return the actionTakenBy
	 */
	public String getActionTakenBy() {
		return actionTakenBy;
	}
	
	/**
	 * @param handlerAction the handlerAction to set
	 */
	public void setHandlerAction(HandlerAction handlerAction) {
		this.handlerAction = handlerAction;
	}
	/**
	 * @return the handlerAction
	 */
	public HandlerAction getHandlerAction() {
		return handlerAction;
	}
	/**
	 * @param previousAvailedAmount the previousAvailedAmount to set
	 */
	public void setPreviousAvailedAmount(Integer previousAvailedAmount) {
		this.previousAvailedAmount = previousAvailedAmount;
	}
	/**
	 * @return the previousAvailedAmount
	 */
	public Integer getPreviousAvailedAmount() {
		return previousAvailedAmount;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setAddressFlag(Integer addressFlag) {
		this.addressFlag = addressFlag;
	}
	public Integer getAddressFlag() {
		return addressFlag;
	}
	/**
	 * @param addressHtml the addressHtml to set
	 */
	public void setAddressHtml(String addressHtml) {
		this.addressHtml = addressHtml;
	}
	/**
	 * @return the addressHtml
	 */
	public String getAddressHtml() {
		return addressHtml;
	}
	/**
	 * @param requestedOn the requestedOn to set
	 */
	public void setRequestedOn(String requestedOn) {
		this.requestedOn = requestedOn;
	}
	/**
	 * @return the requestedOn
	 */
	public String getRequestedOn() {
		return requestedOn;
	}
	/**
	 * @param assignButtonflag the assignButtonflag to set
	 */
	public void setAssignButtonflag(boolean assignButtonflag) {
		this.assignButtonflag = assignButtonflag;
	}
	/**
	 * @return the assignButtonflag
	 */
	public boolean getAssignButtonflag() {
		return assignButtonflag;
	}
	/**
	 * @param statusArray the statusArray to set
	 */
	public void setStatusArray(com.dikshatech.portal.dto.Status[] statusArray) {
		this.statusArray = statusArray;
	}
	/**
	 * @return the statusArray
	 */
	public com.dikshatech.portal.dto.Status[] getStatusArray() {
		return statusArray;
	}
	
}
