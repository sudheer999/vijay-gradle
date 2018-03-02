package com.dikshatech.beans;

import java.util.Arrays;

import com.dikshatech.portal.forms.PortalForm;

public class SalaryReconciliationBean extends PortalForm {

	private int			id;
	private int			srId;
	private Object[]	list;
	private Object[]	modified;
	private Object[]	hold;
	private Object[]	released;
	private Object[]	rejected;
	private String		comments;
	private String		salary;
	private String		payableDays;
	private String		term;
	private String[]	deductions;
	private String[]	additions;
	private String		isFinance;
	private String		status;
	private Object[]	listHdfc;
	private Object[]	listNonHdfc;
	private String		flag;
	private String		bankFlag;
	private String	 srrId;
	private String      salaryCycle;
	public String getSalReport() {
		return salReport;
	}

	public void setSalReport(String salReport) {
		this.salReport = salReport;
	}

	private int			permissionFlagId;
	
	
	private String      totalAmount;
	
	private int deptId;
	
	private String salReport;
	
	
	private String reason;
	
	
	private String date;
	




	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getPermissionFlagId() {
		return permissionFlagId;
	}

	public void setPermissionFlagId(int permissionFlagId) {
		this.permissionFlagId = permissionFlagId;
	}

	public String getSalaryCycle() {
		return salaryCycle;
	}

	public void setSalaryCycle(String salaryCycle) {
		this.salaryCycle = salaryCycle;
	}

	public String getSrrId() {
		return srrId;
	}

	public void setSrrId(String srrId) {
		this.srrId = srrId;
	}

	public String getBankFlag() {
		return bankFlag;
	}

	public void setBankFlag(String bankFlag) {
		this.bankFlag = bankFlag;
	}

	public Object[] getListHdfc() {
		return listHdfc;
	}

	public void setListHdfc(Object[] listHdfc) {
		this.listHdfc = listHdfc;
	}

	public Object[] getListNonHdfc() {
		return listNonHdfc;
	}

	public void setListNonHdfc(Object[] listNonHdfc) {
		this.listNonHdfc = listNonHdfc;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object[] getList() {
		return list;
	}

	public void setList(Object[] list) {
		this.list = list;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getSalary() {
		return salary;
	}

	public String[] getDeductions() {
		return deductions;
	}

	public void setDeductions(String[] deductions) {
		this.deductions = deductions;
	}

	public String[] getAdditions() {
		return additions;
	}

	public void setAdditions(String[] additions) {
		this.additions = additions;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getPayableDays() {
		return payableDays;
	}

	public void setPayableDays(String payableDays) {
		this.payableDays = payableDays;
	}

	public int getSrId() {
		return srId;
	}

	public void setSrId(int srId) {
		this.srId = srId;
	}

	@Override
	public String toString() {
		return "SalaryReconciliationBean [id=" + id + ", srId=" + srId + ",bankFlag="+bankFlag+",srrId="+srrId+"  " + (list != null ? "list=" + Arrays.toString(list) + ", " : "") + (comments != null ? "comments=" + comments + ", " : "") + (salary != null ? "salary=" + salary + ", " : "") + (payableDays != null ? "payableDays=" + payableDays + ", " : "") + (term != null ? "term=" + term + ", " : "") + (deductions != null ? "deductions=" + Arrays.toString(deductions) + ", " : "")
				+ (additions != null ? "additions=" + Arrays.toString(additions) : "") + "]";
	}

	public String getIsFinance() {
		return isFinance;
	}

	public void setIsFinance(String isFinance) {
		this.isFinance = isFinance;
	}

	public Object[] getModified() {
		return modified;
	}

	public void setModified(Object[] modified) {
		this.modified = modified;
	}

	public Object[] getHold() {
		return hold;
	}

	public void setHold(Object[] hold) {
		this.hold = hold;
	}

	public Object[] getReleased() {
		return released;
	}

	public void setReleased(Object[] released) {
		this.released = released;
	}

	public Object[] getRejected() {
		return rejected;
	}

	public void setRejected(Object[] rejected) {
		this.rejected = rejected;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
