package com.dikshatech.portal.dto;

import java.io.Serializable;

public class LeaveReceiveBean implements Serializable {

	private Object[]	LeaveBeanArray;
	private Object[]	leaveBean;
	private String		toApprove;
	private String		toHandle;
	private Object		leaveDetails;
	private String		leaveHandler;
	private String		count;

	public String getToApprove() {
		return toApprove;
	}

	public void setToApprove(String toApprove) {
		this.toApprove = toApprove;
	}

	public String getToHandle() {
		return toHandle;
	}

	public void setToHandle(String toHandle) {
		this.toHandle = toHandle;
	}

	public Object[] getLeaveBeanArray() {
		return LeaveBeanArray;
	}

	public void setLeaveBeanArray(Object[] leaveBeanArray) {
		LeaveBeanArray = leaveBeanArray;
	}

	public Object getLeaveDetails() {
		return leaveDetails;
	}

	public void setLeaveDetails(Object leaveDetails) {
		this.leaveDetails = leaveDetails;
	}

	public Object[] getLeaveBean() {
		return leaveBean;
	}

	public void setLeaveBean(Object[] leaveBean) {
		this.leaveBean = leaveBean;
	}

	public String getLeaveHandler() {
		return leaveHandler;
	}

	public void setLeaveHandler(String leaveHandler) {
		this.leaveHandler = leaveHandler;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
}
