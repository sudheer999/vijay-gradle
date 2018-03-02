package com.dikshatech.portal.forms;

import java.text.ParseException;

import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.dto.Travel;
import com.dikshatech.portal.dto.TravelReq;

public class TravelForm extends PortalForm
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2442573553203196302L;

	private int travelId;

	private int esrqmId;

	private int travelStatus;

	private int isContactPersonReq;

	private String tlType;

	private int trlUserId;

	private String purposeOfTl;

	private int isRollOn;

	private int chargeCode;

	private String modeOfTl;

	private String tlFrom;

	private String tlTo;

	private String prfDateToTl;

	private String prfTimeToTl;

	private int isRoundTrip;

	private String returnDate;

	private String returnTime;

	private int accomodationReq;

	private String typeOfAccomodation;

	private int cabReq;

	private int onwardInward;

	private int travelReqId;

	//private int tlReqId;

	private int status;

	private int raisedBy;

	private int assignedTo;

	private int appLevel;

	private int actionType;

	private String comment;

	private String currency;

	private double totalCost;

	private String messageBody;
	
	private int travelIds[];
	
	private int documentsId;
	
	private int fileId;
	private String itemCostInfo;
	private int itemCostInfoId;
	private int actionBy;
	private String travellerDetails;
	private int empId;
	private int projectId;
	
	//traveler detail
	private String travelerName;
	private String contactPerson;
	private String phoneNo;
	protected String emailId;
	protected String address;
	
	private int companyId;
	private String companyName;
	private int regionId;
	private String regName;
	private int departmentId;
	private String departmentName;
	private int divisionId;
	private String divisionName;
	private String designation;
	private String projectName;
	private int chargeCodeId;
	private String chargeCodeName;
	private String chargeCodeTittle;
	private int isBussinessType;
	private int previousStatus;
	private int statusesForCancelRequest;
	private String travelDockingStatus;

	private String travellerComments;
	private String travellerSpouseName;
	
	public String getChargeCodeName()
	{
		return chargeCodeName;
	}

	public void setChargeCodeName(String chargeCodeName)
	{
		this.chargeCodeName = chargeCodeName;
	}

	public String getChargeCodeTittle()
	{
		return chargeCodeTittle;
	}

	public void setChargeCodeTittle(String chargeCodeTittle)
	{
		this.chargeCodeTittle = chargeCodeTittle;
	}

	public int getChargeCodeId()
	{
		return chargeCodeId;
	}

	public void setChargeCodeId(int chargeCodeId)
	{
		this.chargeCodeId = chargeCodeId;
	}

	private String title;
	
	
	public int getTravelId()
	{
		return travelId;
	}

	public int getEsrqmId()
	{
		return esrqmId;
	}

	

	public int getIsContactPersonReq()
	{
		return isContactPersonReq;
	}

	public void setIsContactPersonReq(int isContactPersonReq)
	{
		this.isContactPersonReq = isContactPersonReq;
	}

	public String getTlType()
	{
		return tlType;
	}

	public int getTrlUserId()
	{
		return trlUserId;
	}

	public String getPurposeOfTl()
	{
		return purposeOfTl;
	}

	public int getIsRollOn()
	{
		return isRollOn;
	}

	public int getChargeCode()
	{
		return chargeCode;
	}
	
	public String getModeOfTl()
	{
		return modeOfTl;
	}

	public String getTlFrom()
	{
		return tlFrom;
	}

	public String getTlTo()
	{
		return tlTo;
	}

	public String getPrfDateToTl()
	{
		return prfDateToTl;
	}

	public String getPrfTimeToTl()
	{
		return prfTimeToTl;
	}

	public int getIsRoundTrip()
	{
		return isRoundTrip;
	}

	public String getReturnDate()
	{
		return returnDate;
	}

	public String getReturnTime()
	{
		return returnTime;
	}

	public int getAccomodationReq()
	{
		return accomodationReq;
	}

	public String getTypeOfAccomodation()
	{
		return typeOfAccomodation;
	}

	public int getCabReq()
	{
		return cabReq;
	}

	public int getOnwardInward()
	{
		return onwardInward;
	}

	public int getTravelReqId()
	{
		return travelReqId;
	}

	public int getStatus()
	{
		return status;
	}

	public int getRaisedBy()
	{
		return raisedBy;
	}

	public int getAssignedTo()
	{
		return assignedTo;
	}

	public int getActionType()
	{
		return actionType;
	}

	public String getComment()
	{
		return comment;
	}


	public String getCurrency()
	{
		return currency;
	}

	public double getTotalCost()
	{
		return totalCost;
	}

	public String getMessageBody()
	{
		return messageBody;
	}

	public void setTravelId(int travelId)
	{
		this.travelId = travelId;
	}

	public void setEsrqmId(int esrqmId)
	{
		this.esrqmId = esrqmId;
	}

	
	public void setTlType(String tlType)
	{
		this.tlType = tlType;
	}

	public void setTrlUserId(int trlUserId)
	{
		this.trlUserId = trlUserId;
	}

	public void setPurposeOfTl(String purposeOfTl)
	{
		this.purposeOfTl = purposeOfTl;
	}

	public void setIsRollOn(int isRollOn)
	{
		this.isRollOn = isRollOn;
	}
	public void setChargeCode(int chargeCode)
	{
		this.chargeCode = chargeCode;
	}

	public void setModeOfTl(String modeOfTl)
	{
		this.modeOfTl = modeOfTl;
	}

	public void setTlFrom(String tlFrom)
	{
		this.tlFrom = tlFrom;
	}
	
	public void setTlTo(String tlTo)
	{
		this.tlTo = tlTo;
	}

	public void setPrfDateToTl(String prfDateToTl)
	{
		this.prfDateToTl = prfDateToTl;
	}

	public void setPrfTimeToTl(String prfTimeToTl)
	{
		this.prfTimeToTl = prfTimeToTl;
	}

	public void setIsRoundTrip(int isRoundTrip)
	{
		this.isRoundTrip = isRoundTrip;
	}

	public void setReturnDate(String returnDate)
	{
		this.returnDate = returnDate;
	}

	public void setReturnTime(String returnTime)
	{
		this.returnTime = returnTime;
	}

	public void setAccomodationReq(int accomodationReq)
	{
		this.accomodationReq = accomodationReq;
	}

	public void setTypeOfAccomodation(String typeOfAccomodation)
	{
		this.typeOfAccomodation = typeOfAccomodation;
	}

	public void setCabReq(int cabReq)
	{
		this.cabReq = cabReq;
	}

	public void setOnwardInward(int onwardInward)
	{
		this.onwardInward = onwardInward;
	}

	public void setTravelReqId(int travelReqId)
	{
		this.travelReqId = travelReqId;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public void setRaisedBy(int raisedBy)
	{
		this.raisedBy = raisedBy;
	}

	public void setAssignedTo(int assignedTo)
	{
		this.assignedTo = assignedTo;
	}

	public void setActionType(int actionType)
	{
		this.actionType = actionType;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public void setTotalCost(double totalCost)
	{
		this.totalCost = totalCost;
	}

	public void setMessageBody(String messageBody)
	{
		this.messageBody = messageBody;
	}

	/**
	 * set all travel dto values from TravelForm
	 * 
	 * @param tForm
	 * @return
	 */
	public Travel getTravel(TravelForm tForm)
	{
		Travel travel = new Travel();

		travel.setId(tForm.getTravelId());
		travel.setEsrqmId(tForm.getEsrqmId());
		travel.setRaisedBy(tForm.getRaisedBy());
		travel.setIsContactPersonReq(tForm.getIsContactPersonReq());
		travel.setIsBussinessType(tForm.getIsBussinessType());
		
		if(tForm.getIsContactPersonReq()==1)
		{
			travel.setContactPerson(tForm.getContactPerson());
			travel.setPhoneNo(tForm.getPhoneNo());
			travel.setEmailId(tForm.getEmailId());
			travel.setAddress(tForm.getAddress());
		}
		
		travel.setTlType(tForm.getTlType());
		travel.setTrlUserId(tForm.getTrlUserId());
		travel.setPurposeOfTl(tForm.getPurposeOfTl());
		if (tForm.getIsRollOn() == 1)
		{
			travel.setIsRollOn(tForm.getIsRollOn());
			travel.setChargeCode(tForm.getChargeCode());
		}
		travel.setModeOfTl(tForm.getModeOfTl());
		travel.setTlFrom(tForm.getTlFrom());
		travel.setTlTo(tForm.getTlTo());
		if (tForm.getPrfDateToTl() != null)
		{
			try
			{
				travel.setPrfDateToTl(PortalUtility.fromStringToDate(tForm.getPrfDateToTl()));
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
		}

		if (tForm.getPrfTimeToTl() != null)
		{
			try
			{
				travel.setPrfTimeToTl(PortalUtility.fromStringToTime(tForm.getPrfTimeToTl()));
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
		}

		if (tForm.getIsRoundTrip() == 1)
		{
			travel.setIsRoundTrip(tForm.getIsRoundTrip());
			if (tForm.getReturnDate() != null)
			{
				try
				{
					travel.setReturnDate(PortalUtility.fromStringToDate(tForm.getReturnDate()));
				}
				catch (ParseException e)
				{
					e.printStackTrace();
				}
			}

			if (tForm.getReturnTime() != null)
			{
				try
				{
					travel.setReturnTime(PortalUtility.fromStringToTime(tForm.getReturnTime()));
				}
				catch (ParseException e)
				{
					e.printStackTrace();
				}
			}
		}

		if (tForm.getAccomodationReq() == 1)
		{
			travel.setAccomodationReq(tForm.getAccomodationReq());
			travel.setTypeOfAccomodation(tForm.getTypeOfAccomodation());
		}

		if (tForm.getCabReq() == 1)
		{
			travel.setCabReq(tForm.getCabReq());
			travel.setOnwardInward(tForm.getOnwardInward());
		}
		
		travel.setTravellerComments(tForm.getTravellerComments());
		travel.setTravellerSpouseName(tForm.getTravellerSpouseName());

		return travel;
	}

	/**
	 * set all travelReq dto values from TravelForm
	 * 
	 * @param tForm
	 * @return
	 */
	public TravelReq getTravelReq(TravelForm tForm)
	{
		TravelReq travelReq = new TravelReq();

		travelReq.setId(tForm.getTravelReqId());
		travelReq.setTlReqId(tForm.getTravelId());
		travelReq.setStatus(tForm.getStatus());
		travelReq.setAssignedTo(tForm.getAssignedTo());
		travelReq.setAppLevel(tForm.getAppLevel());
		travelReq.setActionType(tForm.getActionType());
		travelReq.setComment(tForm.getComment());
		travelReq.setTotalCost(tForm.getTotalCost());
		travelReq.setMessageBody(tForm.getMessageBody());

		return travelReq;
	}

	public void setAppLevel(int appLevel)
	{
		this.appLevel = appLevel;
	}

	public int getAppLevel()
	{
		return appLevel;
	}

	public void setTravelStatus(int travelStatus)
	{
		this.travelStatus = travelStatus;
	}

	public int getTravelStatus()
	{
		return travelStatus;
	}

	public int[ ] getTravelIds()
	{
		return travelIds;
	}

	public void setTravelIds(int[ ] travelIds)
	{
		this.travelIds = travelIds;
	}

	public int getDocumentsId()
	{
		return documentsId;
	}

	public void setDocumentsId(int documentsId)
	{
		this.documentsId = documentsId;
	}

	public int getFileId()
	{
		return fileId;
	}

	public void setFileId(int fileId)
	{
		this.fileId = fileId;
	}

	public String getItemCostInfo()
	{
		return itemCostInfo;
	}

	public void setItemCostInfo(String itemCostInfo)
	{
		this.itemCostInfo = itemCostInfo;
	}

	public int getItemCostInfoId()
	{
		return itemCostInfoId;
	}

	public void setItemCostInfoId(int itemCostInfoId)
	{
		this.itemCostInfoId = itemCostInfoId;
	}

	public int getActionBy()
	{
		return actionBy;
	}

	public void setActionBy(int actionBy)
	{
		this.actionBy = actionBy;
	}
	public String getTravellerDetails()
	{
		return travellerDetails;
	}

	public void setTravellerDetails(String travellerDetails)
	{
		this.travellerDetails = travellerDetails;
	}

	public int getEmpId()
	{
		return empId;
	}

	public void setEmpId(int empId)
	{
		this.empId = empId;
	}

	public int getProjectId()
	{
		return projectId;
	}

	public void setProjectId(int projectId)
	{
		this.projectId = projectId;
	}

	public String getTravelerName()
	{
		return travelerName;
	}

	public void setTravelerName(String travelerName)
	{
		this.travelerName = travelerName;
	}

	public int getCompanyId()
	{
		return companyId;
	}

	public void setCompanyId(int companyId)
	{
		this.companyId = companyId;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	public String getRegName()
	{
		return regName;
	}

	public void setRegName(String regName)
	{
		this.regName = regName;
	}

	public String getDepartmentName()
	{
		return departmentName;
	}

	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}

	public int getDivisionId()
	{
		return divisionId;
	}

	public void setDivisionId(int divisionId)
	{
		this.divisionId = divisionId;
	}

	public String getDivisionName()
	{
		return divisionName;
	}

	public void setDivisionName(String divisionName)
	{
		this.divisionName = divisionName;
	}

	public String getDesignation()
	{
		return designation;
	}

	public void setDesignation(String designation)
	{
		this.designation = designation;
	}

	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getContactPerson()
	{
		return contactPerson;
	}

	public void setContactPerson(String contactPerson)
	{
		this.contactPerson = contactPerson;
	}

	public String getPhoneNo()
	{
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo)
	{
		this.phoneNo = phoneNo;
	}

	public String getEmailId()
	{
		return emailId;
	}

	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public int getIsBussinessType()
	{
		return isBussinessType;
	}

	public void setIsBussinessType(int isBussinessType)
	{
		this.isBussinessType = isBussinessType;
	}

	
	/**
	 * @param previousStatusId the previousStatusId to set
	 */
	public void setPreviousStatus(int previousStatusId) {
		this.previousStatus = previousStatusId;
	}

	/**
	 * @return the previousStatusId
	 */
	public int getPreviousStatus() {
		return previousStatus;
	}

	/**
	 * @param statusesForCancelRequest the statusesForCancelRequest to set
	 */
	public void setStatusesForCancelRequest(int statusesForCancelRequest) {
		this.statusesForCancelRequest = statusesForCancelRequest;
	}

	/**
	 * @return the statusesForCancelRequest
	 */
	public int getStatusesForCancelRequest() {
		return statusesForCancelRequest;
	}

	/**
	 * @param travelDockingStatus the travelDockingStatus to set
	 */
	public void setTravelDockingStatus(String travelDockingStatus) {
		this.travelDockingStatus = travelDockingStatus;
	}

	/**
	 * @return the travelDockingStatus
	 */
	public String getTravelDockingStatus() {
		return travelDockingStatus;
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
	
	
}
