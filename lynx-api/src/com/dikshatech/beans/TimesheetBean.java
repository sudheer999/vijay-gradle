package com.dikshatech.beans;

import java.io.Serializable;

public class TimesheetBean implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private int					id;
	private String				startDate;
	private String				endDate;
	private String				dueDate;
	private String				status;
	private String				statusId;
	private String				submissionDate;
	private String				userId;
	private String				userName;
	private String				timeSheetPeriod;
	private String				comments;
	private UserTaskListBean[]	userTasklistbean;
	private String				raisedBy;
	private String				approve;
	private String				reject;
	private String				assign;
	private String				onHold;
	private String				toCancell;
	private String				esrMapId;
	private float				comp_off;

	public String getRaisedBy() {
		return raisedBy;
	}

	public void setRaisedBy(String raisedBy) {
		this.raisedBy = raisedBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTimeSheetPeriod() {
		return timeSheetPeriod;
	}

	public void setTimeSheetPeriod(String timeSheetPeriod) {
		this.timeSheetPeriod = timeSheetPeriod;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public UserTaskListBean[] getUserTasklistbean() {
		return userTasklistbean;
	}

	public void setUserTasklistbean(UserTaskListBean[] userTasklistbean) {
		this.userTasklistbean = userTasklistbean;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setEsrMapId(String esrMapId) {
		this.esrMapId = esrMapId;
	}

	public String getEsrMapId() {
		return esrMapId;
	}

	public void setComp_off(float comp_off) {
		this.comp_off = comp_off;
	}

	public float getComp_off() {
		return comp_off;
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

	public String getAssign() {
		return assign;
	}

	public void setAssign(String assign) {
		this.assign = assign;
	}

	public String getOnHold() {
		return onHold;
	}

	public void setOnHold(String onHold) {
		this.onHold = onHold;
	}

	public String getToCancell() {
		return toCancell;
	}

	public void setToCancell(String toCancell) {
		this.toCancell = toCancell;
	}
}
