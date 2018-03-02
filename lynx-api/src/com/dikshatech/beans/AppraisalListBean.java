package com.dikshatech.beans;

import com.dikshatech.portal.dto.Appraisal;

public class AppraisalListBean {

	private Appraisal[]	appraisal;
	private String		toApprove;
	private String		toHandle;
	private String		toApproveCount;
	private String		toHandleCount;
	private String		toManage;
	private String		year;
	private String		period;
	private String		enableReports;
	private Object		object;

	public AppraisalListBean() {}

	public AppraisalListBean(String year, String period, boolean enableReports) {
		this.year = year;
		this.period = period;
		this.enableReports = enableReports ? "1" : "0";
	}

	public Appraisal[] getAppraisal() {
		return appraisal;
	}

	public String getToApprove() {
		return toApprove;
	}

	public String getToHandle() {
		return toHandle;
	}

	public String getYear() {
		return year;
	}

	public String getPeriod() {
		return period;
	}

	public void setAppraisal(Appraisal[] appraisal) {
		this.appraisal = appraisal;
	}

	public void setToApprove(String toApprove) {
		this.toApprove = toApprove;
	}

	public void setToHandle(String toHandle) {
		this.toHandle = toHandle;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public void setToManage(String toManage) {
		this.toManage = toManage;
	}

	public String getToManage() {
		return toManage;
	}

	public void setToApproveCount(String toApproveCount) {
		this.toApproveCount = toApproveCount;
	}

	public String getToApproveCount() {
		return toApproveCount;
	}

	public void setToHandleCount(String toHandleCount) {
		this.toHandleCount = toHandleCount;
	}

	public String getToHandleCount() {
		return toHandleCount;
	}

	public void setEnableReports(String enableReports) {
		this.enableReports = enableReports;
	}

	public String getEnableReports() {
		return enableReports;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getObject() {
		return object;
	}
}
