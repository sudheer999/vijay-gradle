package com.dikshatech.beans;

import java.util.Date;

public class AppraisalsBean {

	private String	userId;
	private String	userName;
	private String	type;
	private String	year;
	private String	status;
	private Date	appraiseeDueDate;
	private Date	appraiserDueDate;
	private Date	startDate;
	private String	period;
	private String	appraiserName;
	private String	handlerName;
	private String	appraiserId;
	private String	handlerId;
	private Object	appraisers[];
	private Object	appraisees[];

	public AppraisalsBean() {}

	public AppraisalsBean(String year, Date appraiseeDueDate, Date appraiserDueDate, Date startDate, String period, String handlerName, String handlerId) {
		this.year = year;
		this.appraiseeDueDate = appraiseeDueDate;
		this.appraiserDueDate = appraiserDueDate;
		this.period = period;
		this.handlerName = handlerName;
		this.handlerId = handlerId;
		this.startDate = startDate;
		
	}

	public AppraisalsBean(String userId, String userName, String type, String status) {
		this.userId = userId;
		this.userName = userName;
		this.type = type;
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public String getYear() {
		return year;
	}

	public Date getAppraiseeDueDate() {
		return appraiseeDueDate;
	}

	public Date getAppraiserDueDate() {
		return appraiserDueDate;
	}

	public String getPeriod() {
		return period;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setAppraiseeDueDate(Date appraiseeDueDate) {
		this.appraiseeDueDate = appraiseeDueDate;
	}

	public void setAppraiserDueDate(Date appraiserDueDate) {
		this.appraiserDueDate = appraiserDueDate;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getAppraiserName() {
		return appraiserName;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public String getAppraiserId() {
		return appraiserId;
	}

	public String getHandlerId() {
		return handlerId;
	}

	public Object[] getAppraisers() {
		return appraisers;
	}

	public Object[] getAppraisees() {
		return appraisees;
	}

	public void setAppraiserName(String appraiserName) {
		this.appraiserName = appraiserName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public void setAppraiserId(String appraiserId) {
		this.appraiserId = appraiserId;
	}

	public void setHandlerId(String handlerId) {
		this.handlerId = handlerId;
	}

	public void setAppraisers(Object[] appraisers) {
		this.appraisers = appraisers;
	}

	public void setAppraisees(Object[] appraisees) {
		this.appraisees = appraisees;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
