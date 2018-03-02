package com.dikshatech.beans;

/**
 * @author gurunath.rokkam
 */
public class ExitEmployeListBean {

	private Object[]	list;
	private String		isResigned;
	private String		toApprove;
	private String		toHandle;
	private String		toview;
	private String		toHandleNoc;
	private String		count;
	private String		itNoc;
	private String		financeNoc;
	private String		adminNoc;

	public void setFalseToAllFields() {
		isResigned = "false";
		toApprove = "false";
		toHandle = "false";
		toview = "false";
		toHandleNoc = "false";
	}

	public String getToApprove() {
		return toApprove;
	}

	public String getToHandle() {
		return toHandle;
	}

	public String getToview() {
		return toview;
	}

	public String getToHandleNoc() {
		return toHandleNoc;
	}

	public void setToApprove(String toApprove) {
		this.toApprove = toApprove;
	}

	public void setToHandle(String toHandle) {
		this.toHandle = toHandle;
	}

	public void setToview(String toview) {
		this.toview = toview;
	}

	public void setToHandleNoc(String toHandleNoc) {
		this.toHandleNoc = toHandleNoc;
	}

	public void setList(Object[] list) {
		this.list = list;
	}

	public Object[] getList() {
		return list;
	}

	public void setIsResigned(String isResigned) {
		this.isResigned = isResigned;
	}

	public String getIsResigned() {
		return isResigned;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getItNoc() {
		return itNoc;
	}

	public void setItNoc(String itNoc) {
		this.itNoc = itNoc;
	}

	public String getFinanceNoc() {
		return financeNoc;
	}

	public void setFinanceNoc(String financeNoc) {
		this.financeNoc = financeNoc;
	}

	public String getAdminNoc() {
		return adminNoc;
	}

	public void setAdminNoc(String adminNoc) {
		this.adminNoc = adminNoc;
	}
}
