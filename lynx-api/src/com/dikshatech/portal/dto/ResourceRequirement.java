package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.portal.forms.PortalForm;

public class ResourceRequirement extends PortalForm implements Serializable  {
 protected int id;
 protected String reqTitle;
 protected String reqDetails;
 protected Date reqDate; 
 protected String mandatorySkills;
 protected String additionalSkills;
 protected String yearsOfExperience;
 protected String relevantExperience;
 protected String location;
 protected String positionName;
 protected int employmentTypeId;
 protected String employmentType;
 protected String requiredFor;
 protected String interviewer;
 protected String interviewerName;
 protected String reqTypeId;
 protected int resourceId;
 protected int isTatuser;
 protected int attachmentId;
 
 public int getAttachmentId() {
	return attachmentId;
}
public void setAttachmentId(int attachmentId) {
	this.attachmentId = attachmentId;
}
public int getIsTatuser() {
	return isTatuser;
}
public void setIsTatuser(int isTatuser) {
	this.isTatuser = isTatuser;
}



protected String mailnotifiers;
 protected String mailnotifiers1;
 
 public String getMailnotifiers() {
	return mailnotifiers;
}
public void setMailnotifiers(String mailnotifiers) {
	this.mailnotifiers = mailnotifiers;
}
public int getResourceId() {
	return resourceId;
}
public void setResourceId(int resourceId) {
	this.resourceId = resourceId;
}
public String getReqTypeId() {
	return reqTypeId;
}
public void setReqTypeId(String reqTypeId) {
	this.reqTypeId = reqTypeId;
}
public String getInterviewerName() {
	return interviewerName;
}
public void setInterviewerName(String interviewerName) {
	this.interviewerName = interviewerName;
}



protected String comments;
 protected String projectName;
 protected int noOfPosition;
 protected int profitabilityId;
 protected int closureId;
 protected int requiredForId;
 public int getRequiredForId() {
	return requiredForId;
}
public void setRequiredForId(int requiredForId) {
	this.requiredForId = requiredForId;
}



protected int assignedTo;
 protected String clientName;
 protected int resourceReqStatusId;
 protected int raisedBy;
 protected Date raisedOn;
 protected int esrMapId;
 protected String profitability;
 protected String closure;
 protected Date createDate;
 protected String createDate1;
 public String getCreateDate1() {
	return createDate1;
}
public void setCreateDate1(String createDate1) {
	this.createDate1 = createDate1;
}



protected String esrMapReqId;
 protected String[] resourceMapping;
 protected int mainStatusId;
 protected String mainStatus;
 protected String raisedByName;
 protected String assignedToName;
 protected ResourceReqMapping[] resources;
 protected String reqId;
 protected int nocandiselected;
 protected int noofproposedcandidate;
 protected int candidateStatusId;
 
public int getCandidateStatusId() {
	return candidateStatusId;
}
public void setCandidateStatusId(int candidateStatusId) {
	this.candidateStatusId = candidateStatusId;
}
public int getNocandiselected() {
	return nocandiselected;
}
public void setNocandiselected(int nocandiselected) {
	this.nocandiselected = nocandiselected;
}
public int getNoofproposedcandidate() {
	return noofproposedcandidate;
}
public void setNoofproposedcandidate(int noofproposedcandidate) {
	this.noofproposedcandidate = noofproposedcandidate;
}
public String getEsrMapReqId() {
	return esrMapReqId;
}
public void setEsrMapReqId(String esrMapReqId) {
	this.esrMapReqId = esrMapReqId;
}
public Date getCreateDate() {
	return createDate;
}
public void setCreateDate(Date createDate) {
	this.createDate = createDate;
}
public String getProfitability() {
	return profitability;
}
public void setProfitability(String profitability) {
	this.profitability = profitability;
}
public String getClosure() {
	return closure;
}
public void setClosure(String closure) {
	this.closure = closure;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getReqTitle() {
	return reqTitle;
}
public void setReqTitle(String reqTitle) {
	this.reqTitle = reqTitle;
}
public String getReqDetails() {
	return reqDetails;
}
public void setReqDetails(String reqDetails) {
	this.reqDetails = reqDetails;
}
public int getNoOfPosition() {
	return noOfPosition;
}
public void setNoOfPosition(int noOfPosition) {
	this.noOfPosition = noOfPosition;
}
public int getProfitabilityId() {
	return profitabilityId;
}
public void setProfitabilityId(int profitabilityId) {
	this.profitabilityId = profitabilityId;
}
public int getClosureId() {
	return closureId;
}
public void setClosureId(int closureId) {
	this.closureId = closureId;
}

public String getClientName() {
	return clientName;
}
public void setClientName(String clientName) {
	this.clientName = clientName;
}
public int getResourceReqStatusId() {
	return resourceReqStatusId;
}
public void setResourceReqStatusId(int resourceReqStatusId) {
	this.resourceReqStatusId = resourceReqStatusId;
}
 

public int getAssignedTo() {
	return assignedTo;
}
public void setAssignedTo(int assignedTo) {
	this.assignedTo = assignedTo;
}
public int getRaisedBy() {
	return raisedBy;
}
public void setRaisedBy(int raisedBy) {
	this.raisedBy = raisedBy;
}
public Date getRaisedOn() {
	return raisedOn;
}
public void setRaisedOn(Date raisedOn) {
	this.raisedOn = raisedOn;
}

public int getEsrMapId() {
	return esrMapId;
}
public void setEsrMapId(int esrMapId) {
	this.esrMapId = esrMapId;
}


public String[] getResourceMapping() {
	return resourceMapping;
}
public void setResourceMapping(String[] resourceMapping) {
	this.resourceMapping = resourceMapping;
}


public int getMainStatusId() {
	return mainStatusId;
}
public void setMainStatusId(int mainStatusId) {
	this.mainStatusId = mainStatusId;
}
public String getMainStatus() {
	return mainStatus;
}
public void setMainStatus(String mainStatus) {
	this.mainStatus = mainStatus;
}


public String getRaisedByName() {
	return raisedByName;
}
public void setRaisedByName(String raisedByName) {
	this.raisedByName = raisedByName;
}
public String getAssignedToName() {
	return assignedToName;
}
public void setAssignedToName(String assignedToName) {
	this.assignedToName = assignedToName;
}



public Date getReqDate() {
	return reqDate;
}
public void setReqDate(Date reqDate) {
	this.reqDate = reqDate;
}
public String getMandatorySkills() {
	return mandatorySkills;
}
public void setMandatorySkills(String mandatorySkills) {
	this.mandatorySkills = mandatorySkills;
}
public String getAdditionalSkills() {
	return additionalSkills;
}
public void setAdditionalSkills(String additionalSkills) {
	this.additionalSkills = additionalSkills;
}

public String getYearsOfExperience() {
	return yearsOfExperience;
}
public void setYearsOfExperience(String yearsOfExperience) {
	this.yearsOfExperience = yearsOfExperience;
}
public String getRelevantExperience() {
	return relevantExperience;
}
public void setRelevantExperience(String relevantExperience) {
	this.relevantExperience = relevantExperience;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getPositionName() {
	return positionName;
}
public void setPositionName(String positionName) {
	this.positionName = positionName;
}
public int getEmploymentTypeId() {
	return employmentTypeId;
}
public void setEmploymentTypeId(int employmentTypeId) {
	this.employmentTypeId = employmentTypeId;
}
public String getRequiredFor() {
	return requiredFor;
}
public void setRequiredFor(String requiredFor) {
	this.requiredFor = requiredFor;
}
public String getInterviewer() {
	return interviewer;
}
public void setInterviewer(String interviewer) {
	this.interviewer = interviewer;
}
public String getComments() {
	return comments;
}
public void setComments(String comments) {
	this.comments = comments;
}
public String getProjectName() {
	return projectName;
}
public void setProjectName(String projectName) {
	this.projectName = projectName;
}
public ResourceReqMapping[] getResources() {
	return resources;
}
public void setResources(ResourceReqMapping[] resources) {
	this.resources = resources;
}

public String getReqId() {
	return reqId;
}
public void setReqId(String reqId) {
	this.reqId = reqId;
}

public String getEmploymentType() {
	return employmentType;
}
public void setEmploymentType(String employmentType) {
	this.employmentType = employmentType;
}
/**
 * Method 'createPk'
 * 
 * @return BonusReconciliationPk
 */
public ResourceRequirementPk createPk() {
	return new ResourceRequirementPk(id);
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
protected String emailId;
protected String contactNo;
protected int statusId;
protected String resourceName;



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
public int getStatusId() {
	return statusId;
}
public void setStatusId(int statusId) {
	this.statusId = statusId;
}
public String getResourceName() {
	return resourceName;
}
public void setResourceName(String resourceName) {
	this.resourceName = resourceName;
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



}
