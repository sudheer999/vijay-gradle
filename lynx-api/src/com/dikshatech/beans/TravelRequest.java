package com.dikshatech.beans;

import com.dikshatech.portal.dto.Handlers;
import com.dikshatech.portal.dto.ItemCostInfo;
import com.dikshatech.portal.forms.TravelForm;

public class TravelRequest extends TravelForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7053656045545373977L;

	private int empId;
	private int companyId;

	private String empServReqMapId;
	private String companyName;

	private int regionId;

	private String regName;

	private int departmentId;

	private String departmentName;

	private int divisionId;

	private String divisionName;

	private String designation;

	private String travelerName;

	private String projectName;

	private int chargeCode;

	private String title;

	private String modeOfTl;

	private String tlFrom;

	private String tlTo;

	private int status;
	private String statusName;

	protected int iscontactPersonReq;
	protected String tlType;
	protected int trlUserId;
	protected String purposeOfTl;
	protected String prfDateToTl;
	protected String prfTimeToTl;
	protected String returnDate;
	protected String returnTime;
	protected int accomodationReq;
	protected String typeOfAccomodation;
	protected int onwardInward;
	private int travelId;
	private String createdDate;
	private int projectId;
	private boolean isToApprove;
	private boolean isTohandle;

	// for travel req:

	private int travelReqId;
	public int getTravelReqId() {
		return travelReqId;
	}

	public void setTravelReqId(int travelReqId) {
		this.travelReqId = travelReqId;
	}

	private int travelreqStatus;
	private String trlreqStatusName;
	private int raisedBy;
	private int assignedTo;
	private int appLevel;
	private int actionType;
	private String comment;
	private String currency;
	private double totalCost;
	private String requestorName;
	private String travelrName;
	private String approvedRejOn;
	private String approvedRejBy;
	// flag for enable or disable
	private HandlerAction handlerAction;
	private Handlers handlers[];
	private ItemCostInfo itemCostInfoArray[];
	private Object[] TravelRequest;
	private String assignedToUser;
	private String descriptions;

	// contact person details

	private String contactPerson;
	private String phoneNo;
	private String emailId;
	private String address;

	private Boolean isRevokedRequestRaised;

	private com.dikshatech.portal.dto.Status[] statusArray;
	
	private Object[] commentsHistory;
	
	private String travellerComments;
	
	private String travellerSpouseName;
	
	private boolean showRadioButtons;
	
	private int loggedInUserId;
	
	private String spouseName;
	
	public int getTravelId() {
		return travelId;
	}

	public void setTravelId(int travelId) {
		this.travelId = travelId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getEmpId() {
		return empId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public int getRegionId() {
		return regionId;
	}

	public String getRegName() {
		return regName;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public int getDivisionId() {
		return divisionId;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public String getDesignation() {
		return designation;
	}

	public String getTravelerName() {
		return travelerName;
	}

	public String getProjectName() {
		return projectName;
	}

	public int getChargeCode() {
		return chargeCode;
	}

	public String getTitle() {
		return title;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setTravelerName(String travelerName) {
		this.travelerName = travelerName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setChargeCode(int chargeCode) {
		this.chargeCode = chargeCode;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getModeOfTl() {
		return modeOfTl;
	}

	public void setModeOfTl(String modeOfTl) {
		this.modeOfTl = modeOfTl;
	}

	public String getTlFrom() {
		return tlFrom;
	}

	public void setTlFrom(String tlFrom) {
		this.tlFrom = tlFrom;
	}

	public String getTlTo() {
		return tlTo;
	}

	public void setTlTo(String tlTo) {
		this.tlTo = tlTo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRaisedBy() {
		return raisedBy;
	}

	public void setRaisedBy(int raisedBy) {
		this.raisedBy = raisedBy;
	}

	public int getIscontactPersonReq() {
		return iscontactPersonReq;
	}

	public void setIscontactPersonReq(int iscontactPersonReq) {
		this.iscontactPersonReq = iscontactPersonReq;
	}

	public String getTlType() {
		return tlType;
	}

	public void setTlType(String tlType) {
		this.tlType = tlType;
	}

	public int getTrlUserId() {
		return trlUserId;
	}

	public void setTrlUserId(int trlUserId) {
		this.trlUserId = trlUserId;
	}

	public String getPurposeOfTl() {
		return purposeOfTl;
	}

	public void setPurposeOfTl(String purposeOfTl) {
		this.purposeOfTl = purposeOfTl;
	}

	public int getAccomodationReq() {
		return accomodationReq;
	}

	public void setAccomodationReq(int accomodationReq) {
		this.accomodationReq = accomodationReq;
	}

	public String getTypeOfAccomodation() {
		return typeOfAccomodation;
	}

	public void setTypeOfAccomodation(String typeOfAccomodation) {
		this.typeOfAccomodation = typeOfAccomodation;
	}

	public int getOnwardInward() {
		return onwardInward;
	}

	public void setOnwardInward(int onwardInward) {
		this.onwardInward = onwardInward;
	}

	public String getPrfDateToTl() {
		return prfDateToTl;
	}

	public void setPrfDateToTl(String prfDateToTl) {
		this.prfDateToTl = prfDateToTl;
	}

	public String getPrfTimeToTl() {
		return prfTimeToTl;
	}

	public void setPrfTimeToTl(String prfTimeToTl) {
		this.prfTimeToTl = prfTimeToTl;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getEmpServReqMapId() {
		return empServReqMapId;
	}

	public void setEmpServReqMapId(String empServReqMapId) {
		this.empServReqMapId = empServReqMapId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public int getTravelreqStatus() {
		return travelreqStatus;
	}

	public void setTravelreqStatus(int travelreqStatus) {
		this.travelreqStatus = travelreqStatus;
	}

	public int getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(int assignedTo) {
		this.assignedTo = assignedTo;
	}

	public int getAppLevel() {
		return appLevel;
	}

	public void setAppLevel(int appLevel) {
		this.appLevel = appLevel;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getTrlreqStatusName() {
		return trlreqStatusName;
	}

	public void setTrlreqStatusName(String trlreqStatusName) {
		this.trlreqStatusName = trlreqStatusName;
	}

	public HandlerAction getHandlerAction() {
		return handlerAction;
	}

	public void setHandlerAction(HandlerAction handlerAction) {
		this.handlerAction = handlerAction;
	}

	public Handlers[] getHandlers() {
		return handlers;
	}

	public void setHandlers(Handlers[] handlers) {
		this.handlers = handlers;
	}

	public String getRequestorName() {
		return requestorName;
	}

	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public String getTravelrName() {
		return travelrName;
	}

	public void setTravelrName(String travelrName) {
		this.travelrName = travelrName;
	}

	public String getApprovedRejBy() {
		return approvedRejBy;
	}

	public void setApprovedRejBy(String approvedRejBy) {
		this.approvedRejBy = approvedRejBy;
	}

	public ItemCostInfo[] getItemCostInfoArray() {
		return itemCostInfoArray;
	}

	public void setItemCostInfoArray(ItemCostInfo[] itemCostInfoArray) {
		this.itemCostInfoArray = itemCostInfoArray;
	}

	public Object[] getTravelRequest() {
		return TravelRequest;
	}

	public void setTravelRequest(Object[] travelRequest) {
		TravelRequest = travelRequest;
	}

	public String getAssignedToUser() {
		return assignedToUser;
	}

	public void setAssignedToUser(String assignedToUser) {
		this.assignedToUser = assignedToUser;
	}

	public boolean isToApprove() {
		return isToApprove;
	}

	public void setToApprove(boolean isToApprove) {
		this.isToApprove = isToApprove;
	}

	public boolean isTohandle() {
		return isTohandle;
	}

	public void setTohandle(boolean isTohandle) {
		this.isTohandle = isTohandle;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public Boolean getIsRevokedRequestRaised() {
		return isRevokedRequestRaised;
	}

	public void setIsRevokedRequestRaised(Boolean isRevokedRequestRaised) {
		this.isRevokedRequestRaised = isRevokedRequestRaised;
	}

	/**
	 * @param statusArray
	 *            the statusArray to set
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

	/**
	 * @param approvedRejOn the approvedRejOn to set
	 */
	public void setApprovedRejOn(String approvedRejOn) {
		this.approvedRejOn = approvedRejOn;
	}

	/**
	 * @return the approvedRejOn
	 */
	public String getApprovedRejOn() {
		return approvedRejOn;
	}

	/**
	 * @param commentsHistory the commentsHistory to set
	 */
	public void setCommentsHistory(Object[] commentsHistory) {
		this.commentsHistory = commentsHistory;
	}

	/**
	 * @return the commentsHistory
	 */
	public Object[] getCommentsHistory() {
		return commentsHistory;
	}

	/**
	 * @param showRadioButtons the showRadioButtons to set
	 */
	public void setShowRadioButtons(boolean showRadioButtons) {
		this.showRadioButtons = showRadioButtons;
	}

	/**
	 * @return the showRadioButtons
	 */
	public boolean getShowRadioButtons() {
		return showRadioButtons;
	}

	/**
	 * @param loggedInUserId the loggedInUserId to set
	 */
	public void setLoggedInUserId(int loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}

	/**
	 * @return the loggedInUserId
	 */
	public int getLoggedInUserId() {
		return loggedInUserId;
	}

	/**
	 * @param travellerComments the travellerComments to set
	 */
	public void setTravellerComments(String travellerComments) {
		this.travellerComments = travellerComments;
	}

	/**
	 * @return the travellerComments
	 */
	public String getTravellerComments() {
		return travellerComments;
	}

	/**
	 * @param travellerSpouseName the travellerSpouseName to set
	 */
	public void setTravellerSpouseName(String travellerSpouseName) {
		this.travellerSpouseName = travellerSpouseName;
	}

	/**
	 * @return the travellerSpouseName
	 */
	public String getTravellerSpouseName() {
		return travellerSpouseName;
	}

	/**
	 * @param spouseName the spouseName to set
	 */
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	/**
	 * @return the spouseName
	 */
	public String getSpouseName() {
		return spouseName;
	}

}
