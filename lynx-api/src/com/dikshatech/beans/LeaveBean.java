package com.dikshatech.beans;

import java.util.Date;
import java.util.HashMap;

import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.dto.ServiceReqInfo;

public class LeaveBean {

	private String					id;
	private Date					fromDate;
	private Date					toDate;
	private String					appliedDate;
	private String					comment;
	private String					attachment;
	private String					appliedBy;
	private String					descriptions;
	private String					Status;
	private String					statusId;
	private String					reqId;
	private String					leaveType;
	private String					reason;
	protected ServiceReqInfo[]		serviceReqInfoarr;
	private Float					leaveAccumalated;
	private Float					leaveBalance;
	private Float					leavesTaken;
	private String					leaveTypeName;
	private String					contactNo;
	private String					fileName;
	private String					projectName;
	private String					projectID;
	private String					chargeCode;
	private String					chargeCodeid;
	private String					esr_map_id;
	private String					reportingMgr;
	private String					department;
	private String					division;
	private String					divisionid;
	private String					departmentid;
	private String					duration;
	private String					leaveHandler;
	private String					toCancell;
	private String					assign;
	private String					approve;
	private String					reject;
	private String					complete;
	private String					userId;
	private HashMap<String, String>	leaveCounts;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getChargeCodeid() {
		return chargeCodeid;
	}

	public void setChargeCodeid(String chargeCodeid) {
		this.chargeCodeid = chargeCodeid;
	}

	public String getEsr_map_id() {
		return esr_map_id;
	}

	public void setEsr_map_id(String esr_map_id) {
		this.esr_map_id = esr_map_id;
	}

	public String getDivisionid() {
		return divisionid;
	}

	public void setDivisionid(String divisionid) {
		this.divisionid = divisionid;
	}

	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getToCancell() {
		return toCancell;
	}

	public void setToCancell(String toCancell) {
		this.toCancell = toCancell;
	}

	public String getAssign() {
		return assign;
	}

	public void setAssign(String assign) {
		this.assign = assign;
	}

	public String getApprove() {
		return approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public String getReject() {
		return reject;
	}

	public void setReject(String reject) {
		this.reject = reject;
	}

	public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String	title;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = PortalUtility.getdd_MM_yyyy_hh_mm_a(appliedDate);
	}

	public void setAppliedDate(String appliedDateTime) {
		this.appliedDate = appliedDateTime;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAppliedBy() {
		return appliedBy;
	}

	public void setAppliedBy(String appliedBy) {
		this.appliedBy = appliedBy;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getLeaveTypeName() {
		return leaveTypeName;
	}

	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}

	public Float getLeaveAccumalated() {
		return leaveAccumalated;
	}

	public void setLeaveAccumalated(Float leaveAccumalated) {
		this.leaveAccumalated = leaveAccumalated;
	}

	public Float getLeaveBalance() {
		return leaveBalance;
	}

	public void setLeaveBalance(Float leaveBalance) {
		this.leaveBalance = leaveBalance;
	}

	public Float getLeavesTaken() {
		return leavesTaken;
	}

	public void setLeavesTaken(Float leavesTaken) {
		this.leavesTaken = leavesTaken;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLeaveHandler() {
		return leaveHandler;
	}

	public void setLeaveHandler(String leaveHandler) {
		this.leaveHandler = leaveHandler;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getReportingMgr() {
		return reportingMgr;
	}

	public void setReportingMgr(String reportingMgr) {
		this.reportingMgr = reportingMgr;
	}

	public ServiceReqInfo[] getServiceReqInfoarr() {
		return serviceReqInfoarr;
	}

	public void setServiceReqInfoarr(ServiceReqInfo[] serviceReqInfoarr) {
		this.serviceReqInfoarr = serviceReqInfoarr;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public HashMap<String, String> getLeaveCounts() {
		return leaveCounts;
	}

	public void setLeaveCounts(HashMap<String, String> leaveCounts) {
		this.leaveCounts = leaveCounts;
	}
}
