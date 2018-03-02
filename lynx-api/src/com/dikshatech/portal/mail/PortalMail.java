package com.dikshatech.portal.mail;

import java.io.Serializable;
import java.util.Date;

public class PortalMail implements Serializable, Cloneable {

	private String			candidateName;
	private String			recipientMailId;
	private String			candidateDOJ;
	private String			offeredDesignation;
	private String			offeredDepartment;
	private String			offreredDivision;
	private String			mailSubject;
	private String			mailBody;
	private String			senderName;
	private Attachements[]	fileSources;
	private String			templateName;
	private String			autoGenUserName;
	private String			autoGenPassword;
	private String			hrSPOC;
	private String			serverId;
	private String			clientAddress;
	private String			repoMngrAtClient;
	private String			repoMngrContNo;
	private String			repoMngrEmailId;
	private String			empId;
	// SERVICE REQUEST
	private String			autoIssueReqId;
	private String			portalLink;
	private String			issueReqStatus;
	private String			issueSubmissionDate;
	private String			issueApprover;
	private String			issueDescription;
	private String			empLName;
	private String			empDesignation;
	private String			empDivision;
	private String			empContNo;
	private String			empEmailId;
	private String			rollOnDate;
	private String			rollOffdate;
	private String			clientLocation;
	private String			dikshaRepoDate;
	private String[]		allReceipientMailId;
	private String[]		allReceipientcCMailId;
	private String[]		allReceipientBCCMailId;
	private String			regionName;
	private String			reportingTm;
	private String			reportingDate;
	private String			chargecode;
	private String			chargeCodeTitle;
	private String			dateOfSeperation;
	private String			dateOfAction;
	// It request fields
	private String			summary;
	private String			type;
	private String			fieldChanged;
	private int				port;
	private String			requestorLastName;
	// ts
	private String			timePeriod;
	private String			tsheetDueDate;
	private String			tsheetSubmissionDate;
	private String			tsheetApprover;
	private String			tsheetCurrentStatus;
	private String			comments;
	private String			employeeName;
	private int				random	= 1;
	//
	private String			SerReqID;
	private String			EmpFname;
	private String			reqID;
	private String			handlerName;
	private Date			date_Time;
	//leaves
	private String			acceptOfferLink;
	private String			rejectOfferLink;
	private String			actionType;
	private String			leaveType;
	private String			leaveStartDate;
	private String			leaveEndDate;
	private float			Totaldays;
	private float			balance;
	private Date			approvedDate;
	private Date			rejectDate;
	private Date			cancellDate;
	private float			leaveBalance;
	private String			onDate;
	private String			accumulatedLeaves;
	private String			leavesTaken;
	// Reimbursement
	private String			amount;
	private String			date;
	private String			approverName;
	private String			requesterId;
	private String			reason;
	private String			siblingName;
	private String			reimbursementType;
	private String			empDepartment;
	// Project details
	private String			region;
	private int				projectId;
	private String			projectName;
	private String			fieldName;
	private int				clientId;
	private String			clientName;
	// LOAN--Send Mail Attribute
	private double			avlblAmt;
	private double			apldAmt;
	private int				emiDuration;
	private String			loanType;
	private String			reqId;
	private String			requester;
	private String			submitDate;
	private String			comment;
	private String			messageBody;
	// travel req
	private String			trvlFrom;
	private String			trvlTo;
	private String			purposeOfTrvl;
	private String			trvlReqId;
	private String			trvlRequestorName;
	private int				requestorEmpId;
	private String			dateOfSubmission;
	private int				handlerEmpId;
	private int				travellerEmpId;
	private String			travellerName;
	private String			travellerDepartment;
	private String			travellerDesignation;
	private String			typeOfAccomodation;
	// Sodexo Details
	private Integer			amountAvailed;
	private Integer			previousAvailedAmount;
	private Integer			amountEligible;
	private Integer			requestorId;
	private String			requestorFirstName;
	private String			requestorDivision;
	private String			deliveryAddress;
	//
	private int				employeeId;
	// roll on
	private String			rollOnTravelInformation;
	//client  new...changes to template
	private String			clientCreatorName;
	//roll-on
	private String			dikshaReportingManager;
	private String			dikshaRmMailId;
	private String			fromMailId;
	//roll_on mgr phone name
	private String			mgrPhoneNo;
	// exit employee...
	private String			buyBack;
	//insurance
	private String			relationship;
	private String			period;
	private String			coverage;
	private String			requestedOn;
	private String			hndName;
	private String			resolvedOn;
	private String			insuranceRequestInfo;
	//TravelDetails
	private String			travelMode;
	private String			travellingFrom;
	private String			travellingTo;
	private String			travelPrefDate;
	private String			travelPrefTime;
	private String			travelOneWayRoundTrip;
	private String			travelRetDate;
	private String			travelRetTime;
	private String			travelAccommodationReq;
	private String			travelAccommodationType;
	private String			travelCabReq;
	private String			travelCabOnwardInward;
	private String			travellerSpouseName;
	private String			travellerWithFamily;
	private String			travellerRemarks;
	//perdiem reconciliation
	private String			perdiemTerm;
	private String			perdiemAcceptedOrApproved;
	private String			daysCrossed;
	//Bonus reconciliation
	private String			bonusMonth;
	private String			bonusAcceptedOrApproved;
	private String			joiningBonusAmount;
	
	//Resource requirement 
	private String reqTitle;
	private String reqDetails;
	private int noOfPosition;
    private String profitability;
	private int assignedTo;
	private String mandatorySkills;
	private String additionalSkills;
	private String yearsOfExperience;
	private String relevantExperience;
	private String location;
	private String positionName;
	private String requiredFor;
	private String interviewer;
	private Date reqDate;
	private String receiverName;
	private Date proposedDate;
	private String contactNo;
	private int totalExp;
	private	 int relevantExp;
	private	 int currentCompExp;	  
	private  String skillSet;
	private String currentEmployer;
	private String currentRole;
	private String leavingReason;
	private double ctc;
	private double ectc;
	private String offerInHand;
	private String noticePeriod;
	private String optionForEarlyJoining;
	private String conditionForEarlyJoining;
	private String currentLocation; 
	private String locConstraint;
	private String employementPeriod;
	private String currentDate;
	//reimursement for other 
	private String reimbuEmployeename;
	private String reimbuEmployeeId;
	private String totalAmount;

	// SalaryReconciliation
	private String noOfEmployees;
	private String noOfModifications;
	private String percentage;

	private String finalSalary;
	
	// for Second job anniversary template
	


	public String getFinalSalary() {
		return finalSalary;
	}

	public void setFinalSalary(String finalSalary) {
		this.finalSalary = finalSalary;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	private int empPeriod;
	
	protected String uuid;
	
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getEmpPeriod() {
		return empPeriod;
	}

	public void setEmpPeriod(int empPeriod) {
		this.empPeriod = empPeriod;
	}

	public String getEmployementPeriod() {
		return employementPeriod;
	}

	public void setEmployementPeriod(String employementPeriod) {
		this.employementPeriod = employementPeriod;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
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

	private String refereeName;

	private String refereeDept;
	
	private String refereeExpLevel;
	

	public String getPerdiemAcceptedOrApproved() {
		return perdiemAcceptedOrApproved;
	}

	public void setPerdiemAcceptedOrApproved(String perdiemAcceptedOrApproved) {
		this.perdiemAcceptedOrApproved = perdiemAcceptedOrApproved;
	}

	public String getInsuranceRequestInfo() {
		return insuranceRequestInfo;
	}

	public void setInsuranceRequestInfo(String insuranceRequestInfo) {
		this.insuranceRequestInfo = insuranceRequestInfo;
	}

	public String getResolvedOn() {
		return resolvedOn;
	}

	public void setResolvedOn(String resolvedOn) {
		this.resolvedOn = resolvedOn;
	}

	public String getRequestedOn() {
		return requestedOn;
	}

	public void setRequestedOn(String requestedOn) {
		this.requestedOn = requestedOn;
	}

	public String getHndName() {
		return hndName;
	}

	public void setHndName(String hndName) {
		this.hndName = hndName;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getRecipientMailId() {
		return recipientMailId;
	}

	public void setRecipientMailId(String recipientMailId) {
		this.recipientMailId = recipientMailId;
	}

	public String getCandidateDOJ() {
		return candidateDOJ;
	}

	public void setCandidateDOJ(String candidateDOJ) {
		this.candidateDOJ = candidateDOJ;
	}

	public String getOfferedDesignation() {
		return offeredDesignation;
	}

	public void setOfferedDesignation(String offeredDesignation) {
		this.offeredDesignation = offeredDesignation;
	}

	public String getOfferedDepartment() {
		return offeredDepartment;
	}

	public void setOfferedDepartment(String offeredDepartment) {
		this.offeredDepartment = offeredDepartment;
	}

	public String getOffreredDivision() {
		return offreredDivision;
	}

	public void setOffreredDivision(String offreredDivision) {
		this.offreredDivision = offreredDivision;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Attachements[] getFileSources() {
		return fileSources;
	}

	public void setFileSources(Attachements[] fileSources) {
		this.fileSources = fileSources;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getAutoGenUserName() {
		return autoGenUserName;
	}

	public void setAutoGenUserName(String autoGenUserName) {
		this.autoGenUserName = autoGenUserName;
	}

	public String getAutoGenPassword() {
		return autoGenPassword;
	}

	public void setAutoGenPassword(String autoGenPassword) {
		this.autoGenPassword = autoGenPassword;
	}

	public String getHrSPOC() {
		return hrSPOC;
	}

	public void setHrSPOC(String hrSPOC) {
		this.hrSPOC = hrSPOC;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getRepoMngrAtClient() {
		return repoMngrAtClient;
	}

	public void setRepoMngrAtClient(String repoMngrAtClient) {
		this.repoMngrAtClient = repoMngrAtClient;
	}

	public String getRepoMngrContNo() {
		return repoMngrContNo;
	}

	public void setRepoMngrContNo(String repoMngrContNo) {
		this.repoMngrContNo = repoMngrContNo;
	}

	public String getRepoMngrEmailId() {
		return repoMngrEmailId;
	}

	public void setRepoMngrEmailId(String repoMngrEmailId) {
		this.repoMngrEmailId = repoMngrEmailId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getAutoIssueReqId() {
		return autoIssueReqId;
	}

	public void setAutoIssueReqId(String autoIssueReqId) {
		this.autoIssueReqId = autoIssueReqId;
	}

	public String getPortalLink() {
		return portalLink;
	}

	public void setPortalLink(String portalLink) {
		this.portalLink = portalLink;
	}

	public String getIssueReqStatus() {
		return issueReqStatus;
	}

	public void setIssueReqStatus(String issueReqStatus) {
		this.issueReqStatus = issueReqStatus;
	}

	public String getIssueSubmissionDate() {
		return issueSubmissionDate;
	}

	public void setIssueSubmissionDate(String issueSubmissionDate) {
		this.issueSubmissionDate = issueSubmissionDate;
	}

	public String getIssueApprover() {
		return issueApprover;
	}

	public void setIssueApprover(String issueApprover) {
		this.issueApprover = issueApprover;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public String getEmpLName() {
		return empLName;
	}

	public void setEmpLName(String empLName) {
		this.empLName = empLName;
	}

	public String getEmpDesignation() {
		return empDesignation;
	}

	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}

	public String getEmpDivision() {
		return empDivision;
	}

	public void setEmpDivision(String empDivision) {
		this.empDivision = empDivision;
	}

	public String getEmpContNo() {
		return empContNo;
	}

	public void setEmpContNo(String empContNo) {
		this.empContNo = empContNo;
	}

	public String getEmpEmailId() {
		return empEmailId;
	}

	public void setEmpEmailId(String empEmailId) {
		this.empEmailId = empEmailId;
	}

	public String getRollOnDate() {
		return rollOnDate;
	}

	public void setRollOnDate(String rollOnDate) {
		this.rollOnDate = rollOnDate;
	}

	public String getRollOffdate() {
		return rollOffdate;
	}

	public void setRollOffdate(String rollOffdate) {
		this.rollOffdate = rollOffdate;
	}

	public String getClientLocation() {
		return clientLocation;
	}

	public void setClientLocation(String clientLocation) {
		this.clientLocation = clientLocation;
	}

	public String getDikshaRepoDate() {
		return dikshaRepoDate;
	}

	public void setDikshaRepoDate(String dikshaRepoDate) {
		this.dikshaRepoDate = dikshaRepoDate;
	}

	public String[] getAllReceipientMailId() {
		return allReceipientMailId;
	}

	public void setAllReceipientMailId(String[] allReceipientMailId) {
		this.allReceipientMailId = allReceipientMailId;
	}

	public String[] getAllReceipientcCMailId() {
		return allReceipientcCMailId;
	}

	public void setAllReceipientcCMailId(String[] allReceipientcCMailId) {
		this.allReceipientcCMailId = allReceipientcCMailId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getReportingTm() {
		return reportingTm;
	}

	public void setReportingTm(String reportingTm) {
		this.reportingTm = reportingTm;
	}

	public String getReportingDate() {
		return reportingDate;
	}

	public void setReportingDate(String reportingDate) {
		this.reportingDate = reportingDate;
	}

	public String getChargecode() {
		return chargecode;
	}

	public void setChargecode(String chargecode) {
		this.chargecode = chargecode;
	}

	public String getChargeCodeTitle() {
		return chargeCodeTitle;
	}

	public void setChargeCodeTitle(String chargeCodeTitle) {
		this.chargeCodeTitle = chargeCodeTitle;
	}

	public String getDateOfSeperation() {
		return dateOfSeperation;
	}

	public void setDateOfSeperation(String dateOfSeperation) {
		this.dateOfSeperation = dateOfSeperation;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFieldChanged() {
		return fieldChanged;
	}

	public void setFieldChanged(String fieldChanged) {
		this.fieldChanged = fieldChanged;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRequestorLastName() {
		return requestorLastName;
	}

	public void setRequestorLastName(String requestorLastName) {
		this.requestorLastName = requestorLastName;
	}

	public String getAcceptOfferLink() {
		return acceptOfferLink;
	}

	public void setAcceptOfferLink(String acceptOfferLink) {
		this.acceptOfferLink = acceptOfferLink;
	}

	public String getRejectOfferLink() {
		return rejectOfferLink;
	}

	public void setRejectOfferLink(String rejectOfferLink) {
		this.rejectOfferLink = rejectOfferLink;
	}

	public String getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public String getTsheetDueDate() {
		return tsheetDueDate;
	}

	public void setTsheetDueDate(String tsheetDueDate) {
		this.tsheetDueDate = tsheetDueDate;
	}

	public String getTsheetSubmissionDate() {
		return tsheetSubmissionDate;
	}

	public void setTsheetSubmissionDate(String tsheetSubmissionDate) {
		this.tsheetSubmissionDate = tsheetSubmissionDate;
	}

	public String getTsheetApprover() {
		return tsheetApprover;
	}

	public void setTsheetApprover(String tsheetApprover) {
		this.tsheetApprover = tsheetApprover;
	}

	public String getTsheetCurrentStatus() {
		return tsheetCurrentStatus;
	}

	public void setTsheetCurrentStatus(String tsheetCurrentStatus) {
		this.tsheetCurrentStatus = tsheetCurrentStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getRandom() {
		return random;
	}

	public void setRandom(int random) {
		this.random = random;
	}

	public String getSerReqID() {
		return SerReqID;
	}

	public void setSerReqID(String serReqID) {
		SerReqID = serReqID;
	}

	public String getEmpFname() {
		return EmpFname;
	}

	public void setEmpFname(String empFname) {
		EmpFname = empFname;
	}

	public String getReqID() {
		return reqID;
	}

	public void setReqID(String reqID) {
		this.reqID = reqID;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public Date getDate_Time() {
		return date_Time;
	}

	public void setDate_Time(Date dateTime) {
		date_Time = dateTime;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getLeaveStartDate() {
		return leaveStartDate;
	}

	public void setLeaveStartDate(String leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}

	public String getLeaveEndDate() {
		return leaveEndDate;
	}

	public void setLeaveEndDate(String leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}

	public float getTotaldays() {
		return Totaldays;
	}

	public void setTotaldays(float totaldays) {
		Totaldays = totaldays;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Date getRejectDate() {
		return rejectDate;
	}

	public void setRejectDate(Date rejectDate) {
		this.rejectDate = rejectDate;
	}

	public Date getCancellDate() {
		return cancellDate;
	}

	public void setCancellDate(Date cancellDate) {
		this.cancellDate = cancellDate;
	}

	public float getLeavebalance() {
		return leaveBalance;
	}

	public String getRefereeName() {
		return refereeName;
	}

	public void setRefereeName(String refereeName) {
		this.refereeName = refereeName;
	}

	public String getRefereeDept() {
		return refereeDept;
	}

	public void setRefereeDept(String refereeDept) {
		this.refereeDept = refereeDept;
	}

	public String getRefereeExpLevel() {
		return refereeExpLevel;
	}

	public void setRefereeExpLevel(String refereeExpLevel) {
		this.refereeExpLevel = refereeExpLevel;
	}

	public void setLeavebalance(float leaveBalance) {
		this.leaveBalance = leaveBalance;
	}

	public String getOnDate() {
		return onDate;
	}

	public void setOnDate(String onDate) {
		this.onDate = onDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSiblingName() {
		return siblingName;
	}

	public void setSiblingName(String siblingName) {
		this.siblingName = siblingName;
	}

	public String getReimbursementType() {
		return reimbursementType;
	}

	public void setReimbursementType(String reimbursementType) {
		this.reimbursementType = reimbursementType;
	}

	public String getEmpDepartment() {
		return empDepartment;
	}

	public void setEmpDepartment(String empDepartment) {
		this.empDepartment = empDepartment;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public double getAvlblAmt() {
		return avlblAmt;
	}

	public void setAvlblAmt(double avlblAmt) {
		this.avlblAmt = avlblAmt;
	}

	public double getApldAmt() {
		return apldAmt;
	}

	public void setApldAmt(double apldAmt) {
		this.apldAmt = apldAmt;
	}

	public int getEmiDuration() {
		return emiDuration;
	}

	public void setEmiDuration(int emiDuration) {
		this.emiDuration = emiDuration;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getTrvlFrom() {
		return trvlFrom;
	}

	public void setTrvlFrom(String trvlFrom) {
		this.trvlFrom = trvlFrom;
	}

	public String getTrvlTo() {
		return trvlTo;
	}

	public void setTrvlTo(String trvlTo) {
		this.trvlTo = trvlTo;
	}

	public String getPurposeOfTrvl() {
		return purposeOfTrvl;
	}

	public void setPurposeOfTrvl(String purposeOfTrvl) {
		this.purposeOfTrvl = purposeOfTrvl;
	}

	public String getTrvlReqId() {
		return trvlReqId;
	}

	public void setTrvlReqId(String trvlReqId) {
		this.trvlReqId = trvlReqId;
	}

	public String getTrvlRequestorName() {
		return trvlRequestorName;
	}

	public void setTrvlRequestorName(String trvlRequestorName) {
		this.trvlRequestorName = trvlRequestorName;
	}

	public int getRequestorEmpId() {
		return requestorEmpId;
	}

	public void setRequestorEmpId(int requestorEmpId) {
		this.requestorEmpId = requestorEmpId;
	}

	public String getDateOfSubmission() {
		return dateOfSubmission;
	}

	public void setDateOfSubmission(String dateOfSubmission) {
		this.dateOfSubmission = dateOfSubmission;
	}

	public int getHandlerEmpId() {
		return handlerEmpId;
	}

	public void setHandlerEmpId(int handlerEmpId) {
		this.handlerEmpId = handlerEmpId;
	}

	public int getTravellerEmpId() {
		return travellerEmpId;
	}

	public void setTravellerEmpId(int travellerEmpId) {
		this.travellerEmpId = travellerEmpId;
	}

	public String getTravellerName() {
		return travellerName;
	}

	public void setTravellerName(String travellerName) {
		this.travellerName = travellerName;
	}

	public String getTravellerDepartment() {
		return travellerDepartment;
	}

	public void setTravellerDepartment(String travellerDepartment) {
		this.travellerDepartment = travellerDepartment;
	}

	public String getTravellerDesignation() {
		return travellerDesignation;
	}

	public void setTravellerDesignation(String travellerDesignation) {
		this.travellerDesignation = travellerDesignation;
	}

	public Integer getAmountAvailed() {
		return amountAvailed;
	}

	public void setAmountAvailed(Integer amountAvailed) {
		this.amountAvailed = amountAvailed;
	}

	public Integer getPreviousAvailedAmount() {
		return previousAvailedAmount;
	}

	public void setPreviousAvailedAmount(Integer previousAvailedAmount) {
		this.previousAvailedAmount = previousAvailedAmount;
	}

	public Integer getAmountEligible() {
		return amountEligible;
	}

	public void setAmountEligible(Integer amountEligible) {
		this.amountEligible = amountEligible;
	}

	public Integer getRequestorId() {
		return requestorId;
	}

	public void setRequestorId(Integer requestorId) {
		this.requestorId = requestorId;
	}

	public String getRequestorFirstName() {
		return requestorFirstName;
	}

	public void setRequestorFirstName(String requestorFirstName) {
		this.requestorFirstName = requestorFirstName;
	}

	public String getRequestorDivision() {
		return requestorDivision;
	}

	public void setRequestorDivision(String requestorDivision) {
		this.requestorDivision = requestorDivision;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getRollOnTravelInformation() {
		return rollOnTravelInformation;
	}

	public void setRollOnTravelInformation(String rollOnTravelInformation) {
		this.rollOnTravelInformation = rollOnTravelInformation;
	}

	public PortalMail getcopy() {
		try{
			PortalMail pmail = (PortalMail) this.clone();
			if (allReceipientcCMailId != null) pmail.setAllReceipientcCMailId(allReceipientcCMailId.clone());
			if (allReceipientMailId != null) pmail.setAllReceipientMailId(allReceipientMailId.clone());
			if (fileSources != null) pmail.setFileSources(fileSources.clone());
			return pmail;
		} catch (CloneNotSupportedException e){
			e.printStackTrace();
		}
		return null;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getAccumulatedLeaves() {
		return accumulatedLeaves;
	}

	public void setAccumulatedLeaves(String accumulatedLeaves) {
		this.accumulatedLeaves = accumulatedLeaves;
	}

	public String getLeavesTaken() {
		return leavesTaken;
	}

	public void setLeavesTaken(String leavesTaken) {
		this.leavesTaken = leavesTaken;
	}

	public void setClientCreatorName(String clientCreatorName) {
		this.clientCreatorName = clientCreatorName;
	}

	public String getClientCreatorName() {
		return clientCreatorName;
	}

	public void setDateOfAction(String dateOfAction) {
		this.dateOfAction = dateOfAction;
	}

	public String getDateOfAction() {
		return dateOfAction;
	}

	/**
	 * @param dikshaReportingManager
	 *            the dikshaReportingManager to set
	 */
	public void setDikshaReportingManager(String dikshaReportingManager) {
		this.dikshaReportingManager = dikshaReportingManager;
	}

	/**
	 * @return the dikshaReportingManager
	 */
	public String getDikshaReportingManager() {
		return dikshaReportingManager;
	}

	/**
	 * @param dikshaRmMailId
	 *            the dikshaRmMailId to set
	 */
	public void setDikshaRmMailId(String dikshaRmMailId) {
		this.dikshaRmMailId = dikshaRmMailId;
	}

	/**
	 * @return the dikshaRmMailId
	 */
	public String getDikshaRmMailId() {
		return dikshaRmMailId;
	}

	public void setAllReceipientBCCMailId(String[] allReceipientBCCMailId) {
		this.allReceipientBCCMailId = allReceipientBCCMailId;
	}

	public String[] getAllReceipientBCCMailId() {
		return allReceipientBCCMailId;
	}

	/**
	 * @param typeOfAccomodation
	 *            the typeOfAccomodation to set
	 */
	public void setTypeOfAccomodation(String typeOfAccomodation) {
		this.typeOfAccomodation = typeOfAccomodation;
	}

	/**
	 * @return the typeOfAccomodation
	 */
	public String getTypeOfAccomodation() {
		return typeOfAccomodation;
	}

	public void setFromMailId(String fromMailId) {
		this.fromMailId = fromMailId;
	}

	public String getFromMailId() {
		return fromMailId;
	}

	/**
	 * @param mgrPhoneNo
	 *            the mgrPhoneNo to set
	 */
	public void setMgrPhoneNo(String mgrPhoneNo) {
		this.mgrPhoneNo = mgrPhoneNo;
	}

	/**
	 * @return the mgrPhoneNo
	 */
	public String getMgrPhoneNo() {
		return mgrPhoneNo;
	}

	public String getRelationship() {
		return relationship;
	}

	public String getPeriod() {
		return period;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public void setBuyBack(String buyBack) {
		this.buyBack = buyBack;
	}

	public String getBuyBack() {
		return buyBack;
	}

	/**
	 * @param travelMode
	 *            the travelMode to set
	 */
	public void setTravelMode(String travelMode) {
		this.travelMode = travelMode;
	}

	/**
	 * @return the travelMode
	 */
	public String getTravelMode() {
		return travelMode;
	}

	/**
	 * @param travellingFrom
	 *            the travellingFrom to set
	 */
	public void setTravellingFrom(String travellingFrom) {
		this.travellingFrom = travellingFrom;
	}

	/**
	 * @return the travellingFrom
	 */
	public String getTravellingFrom() {
		return travellingFrom;
	}

	/**
	 * @param travelPrefDate
	 *            the travelPrefDate to set
	 */
	public void setTravelPrefDate(String travelPrefDate) {
		this.travelPrefDate = travelPrefDate;
	}

	/**
	 * @return the travelPrefDate
	 */
	public String getTravelPrefDate() {
		return travelPrefDate;
	}

	/**
	 * @param travellingTo
	 *            the travellingTo to set
	 */
	public void setTravellingTo(String travellingTo) {
		this.travellingTo = travellingTo;
	}

	/**
	 * @return the travellingTo
	 */
	public String getTravellingTo() {
		return travellingTo;
	}

	/**
	 * @param travelOneWayRoundTrip
	 *            the travelOneWayRoundTrip to set
	 */
	public void setTravelOneWayRoundTrip(String travelOneWayRoundTrip) {
		this.travelOneWayRoundTrip = travelOneWayRoundTrip;
	}

	/**
	 * @return the travelOneWayRoundTrip
	 */
	public String getTravelOneWayRoundTrip() {
		return travelOneWayRoundTrip;
	}

	/**
	 * @param travelRetDate
	 *            the travelRetDate to set
	 */
	public void setTravelRetDate(String travelRetDate) {
		this.travelRetDate = travelRetDate;
	}

	/**
	 * @return the travelRetDate
	 */
	public String getTravelRetDate() {
		return travelRetDate;
	}

	/**
	 * @param travelPrefTime
	 *            the travelPrefTime to set
	 */
	public void setTravelPrefTime(String travelPrefTime) {
		this.travelPrefTime = travelPrefTime;
	}

	/**
	 * @return the travelPrefTime
	 */
	public String getTravelPrefTime() {
		return travelPrefTime;
	}

	/**
	 * @param travelAccommodationReq
	 *            the travelAccommodationReq to set
	 */
	public void setTravelAccommodationReq(String travelAccommodationReq) {
		this.travelAccommodationReq = travelAccommodationReq;
	}

	/**
	 * @return the travelAccommodationReq
	 */
	public String getTravelAccommodationReq() {
		return travelAccommodationReq;
	}

	/**
	 * @param travelCabOnwardInward
	 *            the travelCabOnwardInward to set
	 */
	public void setTravelCabOnwardInward(String travelCabOnwardInward) {
		this.travelCabOnwardInward = travelCabOnwardInward;
	}

	/**
	 * @return the travelCabOnwardInward
	 */
	public String getTravelCabOnwardInward() {
		return travelCabOnwardInward;
	}

	/**
	 * @param travelRetTime
	 *            the travelRetTime to set
	 */
	public void setTravelRetTime(String travelRetTime) {
		this.travelRetTime = travelRetTime;
	}

	/**
	 * @return the travelRetTime
	 */
	public String getTravelRetTime() {
		return travelRetTime;
	}

	/**
	 * @param travelCabReq
	 *            the travelCabReq to set
	 */
	public void setTravelCabReq(String travelCabReq) {
		this.travelCabReq = travelCabReq;
	}

	/**
	 * @return the travelCabReq
	 */
	public String getTravelCabReq() {
		return travelCabReq;
	}

	/**
	 * @param travelAccommodationType
	 *            the travelAccommodationType to set
	 */
	public void setTravelAccommodationType(String travelAccommodationType) {
		this.travelAccommodationType = travelAccommodationType;
	}

	/**
	 * @return the travelAccommodationType
	 */
	public String getTravelAccommodationType() {
		return travelAccommodationType;
	}

	/**
	 * @param travellerSpouseName
	 *            the travellerSpouseName to set
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
	 * @param travellerWithFamily
	 *            the travellerWithFamily to set
	 */
	public void setTravellerWithFamily(String travellerWithFamily) {
		this.travellerWithFamily = travellerWithFamily;
	}

	/**
	 * @return the travellerWithFamily
	 */
	public String getTravellerWithFamily() {
		return travellerWithFamily;
	}

	/**
	 * @param travellerRemarks
	 *            the travellerRemarks to set
	 */
	public void setTravellerRemarks(String travellerRemarks) {
		this.travellerRemarks = travellerRemarks;
	}

	/**
	 * @return the travellerRemarks
	 */
	public String getTravellerRemarks() {
		return travellerRemarks;
	}

	public void setPerdiemTerm(String perdiemTerm) {
		this.perdiemTerm = perdiemTerm;
	}

	public String getPerdiemTerm() {
		return perdiemTerm;
	}

	private String	commentsByFinanceTeam;

	public String getCommentsByFinanceTeam() {
		return commentsByFinanceTeam;
	}

	public void setCommentsByFinanceTeam(String commentsByFinanceTeam) {
		this.commentsByFinanceTeam = commentsByFinanceTeam;
	}

	public String getDaysCrossed() {
		return daysCrossed;
	}

	public void setDaysCrossed(String daysCrossed) {
		this.daysCrossed = daysCrossed;
	}

	public String getBonusMonth() {
		return bonusMonth;
	}

	public void setBonusMonth(String bonusMonth) {
		this.bonusMonth = bonusMonth;
	}

	public String getBonusAcceptedOrApproved() {
		return bonusAcceptedOrApproved;
	}

	public void setBonusAcceptedOrApproved(String bonusAcceptedOrApproved) {
		this.bonusAcceptedOrApproved = bonusAcceptedOrApproved;
	}

	public String getJoiningBonusAmount() {
		return joiningBonusAmount;
	}

	public void setJoiningBonusAmount(String joiningBonusAmount) {
		this.joiningBonusAmount = joiningBonusAmount;
	}

	public float getLeaveBalance() {
		return leaveBalance;
	}

	public void setLeaveBalance(float leaveBalance) {
		this.leaveBalance = leaveBalance;
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

	public String getProfitability() {
		return profitability;
	}

	public void setProfitability(String profitability) {
		this.profitability = profitability;
	}

	
	public int getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(int assignedTo) {
		this.assignedTo = assignedTo;
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

	public Date getReqDate() {
		return reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public Date getProposedDate() {
		return proposedDate;
	}

	public void setProposedDate(Date proposedDate) {
		this.proposedDate = proposedDate;
	}

	public String getReimbuEmployeename() {
		return reimbuEmployeename;
	}

	public void setReimbuEmployeename(String reimbuEmployeename) {
		this.reimbuEmployeename = reimbuEmployeename;
	}

	public String getReimbuEmployeeId() {
		return reimbuEmployeeId;
	}

	public void setReimbuEmployeeId(String reimbuEmployeeId) {
		this.reimbuEmployeeId = reimbuEmployeeId;
	}

	public String getNoOfModifications() {
		return noOfModifications;
	}

	public void setNoOfModifications(String noOfModifications) {
		this.noOfModifications = noOfModifications;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(String noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}

}