package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.portal.forms.PortalForm;

public class ResourceReqMapping  extends PortalForm implements Serializable{
  protected int id;
  protected int reqId;
  protected int positionNo;
  protected String resourceName;
  protected int statusId;
  protected String status;
  protected int assignedTo;
  protected String assignedToName;
  protected String emailId;
  protected String contactNo;
  protected int lastUpdatedBy;
  protected String lastUpdatedByName;
  protected String reqTypeId;
  protected String closedReqTypeId;
  protected int attachmentId;
  protected String  fileDescription;
  
 

public String getFileDescription() {
	return fileDescription;
}
public void setFileDescription(String fileDescription) {
	this.fileDescription = fileDescription;
}
public int getAttachmentId() {
	return attachmentId;
}
public void setAttachmentId(int attachmentId) {
	this.attachmentId = attachmentId;
}
public String getClosedReqTypeId() {
	return closedReqTypeId;
}
public void setClosedReqTypeId(String closedReqTypeId) {
	this.closedReqTypeId = closedReqTypeId;
}
protected int totalExp;
protected	int relevantExp;
protected	int currentCompExp;	  
protected String skillSet;
protected String currentEmployer;
protected String currentRole;
protected String leavingReason;
protected double ctc;
protected double ectc;
protected String offerInHand;
protected String noticePeriod;
protected String optionForEarlyJoining;
protected String conditionForEarlyJoining;
protected String currentLocation; 
protected String locConstraint;
protected String comments;
protected int isSelected;  
protected int closed;
public int getClosed() {
	return closed;
}
public void setClosed(int closed) {
	this.closed = closed;
}
protected ResourceReqMapHistory[] mapHistory;




public ResourceReqMapHistory[] getMapHistory() {
	return mapHistory;
}
public void setMapHistory(ResourceReqMapHistory[] mapHistory) {
	this.mapHistory = mapHistory;
}
public int getIsSelected() {
	return isSelected;
}
public void setIsSelected(int isSelected) {
	this.isSelected = isSelected;
}
public String getReqTypeId() {
	return reqTypeId;
}
public void setReqTypeId(String reqTypeId) {
	this.reqTypeId = reqTypeId;
}
public String getEmailId() {
	return emailId;
}
public void setEmailId(String emailId) {
	this.emailId = emailId;
}
public String getContactNo() {
	return contactNo;
}
public void setContactNo(String contactNo) {
	this.contactNo = contactNo;
}
public int getLastUpdatedBy() {
	return lastUpdatedBy;
}
public void setLastUpdatedBy(int lastUpdatedBy) {
	this.lastUpdatedBy = lastUpdatedBy;
}
public String getLastUpdatedByName() {
	return lastUpdatedByName;
}
public void setLastUpdatedByName(String lastUpdatedByName) {
	this.lastUpdatedByName = lastUpdatedByName;
}
/*  protected String reqTitle;
  protected String reqDetails;
  protected int noOfPosition;
  protected String profitability;
  protected String closure;
  protected int assignedTo;
  protected String clientName;*/
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getReqId() {
	return reqId;
}
public void setReqId(int reqId) {
	this.reqId = reqId;
}
public int getPositionNo() {
	return positionNo;
}
public void setPositionNo(int positionNo) {
	this.positionNo = positionNo;
}
public String getResourceName() {
	return resourceName;
}
public void setResourceName(String resourceName) {
	this.resourceName = resourceName;
}
public int getStatusId() {
	return statusId;
}
public void setStatusId(int statusId) {
	this.statusId = statusId;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
  

public int getAssignedTo() {
	return assignedTo;
}
public void setAssignedTo(int assignedTo) {
	this.assignedTo = assignedTo;
}
/**
 * Method 'createPk'
 * 
 * @return BonusReconciliationPk
 */
public ResourceReqMappingPk createPk() {
	return new ResourceReqMappingPk(id);
}
public String getAssignedToName() {
	return assignedToName;
}
public void setAssignedToName(String assignedToName) {
	this.assignedToName = assignedToName;
}
public int getTotalExp() {
	return totalExp;
}
public void setTotalExp(int totalExp) {
	this.totalExp = totalExp;
}
public int getRelevantExp() {
	return relevantExp;
}
public void setRelevantExp(int relevantExp) {
	this.relevantExp = relevantExp;
}
public int getCurrentCompExp() {
	return currentCompExp;
}
public void setCurrentCompExp(int currentCompExp) {
	this.currentCompExp = currentCompExp;
}
public String getSkillSet() {
	return skillSet;
}
public void setSkillSet(String skillSet) {
	this.skillSet = skillSet;
}
public String getCurrentEmployer() {
	return currentEmployer;
}
public void setCurrentEmployer(String currentEmployer) {
	this.currentEmployer = currentEmployer;
}
public String getCurrentRole() {
	return currentRole;
}
public void setCurrentRole(String currentRole) {
	this.currentRole = currentRole;
}
public String getLeavingReason() {
	return leavingReason;
}
public void setLeavingReason(String leavingReason) {
	this.leavingReason = leavingReason;
}
public double getCtc() {
	return ctc;
}
public void setCtc(double ctc) {
	this.ctc = ctc;
}
public double getEctc() {
	return ectc;
}
public void setEctc(double ectc) {
	this.ectc = ectc;
}
public String getOfferInHand() {
	return offerInHand;
}
public void setOfferInHand(String offerInHand) {
	this.offerInHand = offerInHand;
}
public String getNoticePeriod() {
	return noticePeriod;
}
public void setNoticePeriod(String noticePeriod) {
	this.noticePeriod = noticePeriod;
}
public String getOptionForEarlyJoining() {
	return optionForEarlyJoining;
}
public void setOptionForEarlyJoining(String optionForEarlyJoining) {
	this.optionForEarlyJoining = optionForEarlyJoining;
}
public String getConditionForEarlyJoining() {
	return conditionForEarlyJoining;
}
public void setConditionForEarlyJoining(String conditionForEarlyJoining) {
	this.conditionForEarlyJoining = conditionForEarlyJoining;
}
public String getCurrentLocation() {
	return currentLocation;
}
public void setCurrentLocation(String currentLocation) {
	this.currentLocation = currentLocation;
}
public String getLocConstraint() {
	return locConstraint;
}
public void setLocConstraint(String locConstraint) {
	this.locConstraint = locConstraint;
}
public String getComments() {
	return comments;
}
public void setComments(String comments) {
	this.comments = comments;
} 







}
