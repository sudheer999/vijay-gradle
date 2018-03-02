package com.dikshatech.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LeaveMasterBean {

	protected LeaveBean[]	leaveBean;
	private String			leaveHandler;
	private Date			startDate;
	private Date			endDate;
	private String			duriation;

	public LeaveMasterBean() {
		// TODO Auto-generated constructor stub
	}

	public LeaveMasterBean(Date startDate, Date endDate, String duriation) {
		this.startDate = startDate;// new SimpleDateFormat("dd, MMM").format(startDate);
		this.endDate = endDate;// new SimpleDateFormat("dd, MMM").format(endDate);
		this.duriation = duriation;
	}

	public String getleaveHistoryString() {
		return new SimpleDateFormat("dd MMM").format(startDate) + " - " + new SimpleDateFormat("dd MMM").format(endDate) + ", " + duriation + " Day(s)";
	}
	public LeaveBean[] getLeaveBean() {
		return leaveBean;
	}

	public void setLeaveBean(LeaveBean[] leaveBean) {
		this.leaveBean = leaveBean;
	}

	public String getLeaveHandler() {
		return leaveHandler;
	}

	public void setLeaveHandler(String leaveHandler) {
		this.leaveHandler = leaveHandler;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDuriation() {
		return duriation;
	}

	public void setDuriation(String duriation) {
		this.duriation = duriation;
	}
}
