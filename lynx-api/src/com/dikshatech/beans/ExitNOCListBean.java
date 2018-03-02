package com.dikshatech.beans;

/**
 * @author gurunath.rokkam
 */
public class ExitNOCListBean {

	public String	id;
	private String	empId;
	private String	empName;
	private String	releseOn;
	private String	submittedon;
	private String	type;
	private String	status;
	private String	statusId;
	private String	buyBack;

	public String getId() {
		return id;
	}

	public String getEmpId() {
		return empId;
	}

	public String getEmpName() {
		return empName;
	}

	public String getReleseOn() {
		return releseOn;
	}

	public String getSubmittedon() {
		return submittedon;
	}

	public String getType() {
		return type;
	}

	public String getStatus() {
		return status;
	}

	public String getStatusId() {
		return statusId;
	}

	public String getBuyBack() {
		return buyBack;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public void setReleseOn(String releseOn) {
		this.releseOn = releseOn;
	}

	public void setSubmittedon(String submittedon) {
		this.submittedon = submittedon;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public void setBuyBack(String buyBack) {
		this.buyBack = buyBack;
	}

	public ExitNOCListBean(String id, String empId, String empName, String releseOn, String submittedon, String type, String status, String statusId, String buyBack) {
		this.id = id;
		this.empId = empId;
		this.empName = empName;
		this.releseOn = releseOn;
		this.submittedon = submittedon;
		this.type = type;
		this.status = status;
		this.statusId = statusId;
		this.buyBack = buyBack;
	}

	public ExitNOCListBean() {
	// TODO Auto-generated constructor stub
	}
}
