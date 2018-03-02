package com.dikshatech.beans;

public class RetentionBonusReportBean  implements Comparable<RetentionBonusReportBean> {

	
	private String	empId;
	private String	accountNo;
	private double	total;
	private String	bankName;
	private String	employeeName;
	
	
	public RetentionBonusReportBean(String empId, String accountNo, double total, String bankName, String employeeName) {
		super();
		this.empId = empId;
		this.accountNo = accountNo;
		this.total = total;
		this.bankName = bankName;
		this.employeeName = employeeName;
	}
	
	@Override
	public String toString() {
		return "RetentionBonusReportBean [" + (empId != null ? empId + ", " : "") + (employeeName != null ? employeeName + ", " : "") + total + ", " + (accountNo != null ? accountNo + ", " : "") + (bankName != null ? bankName : "") + "]";
	}

	
	public String getEmpId() {
		return empId;
	}

	
	public void setEmpId(String empId) {
		this.empId = empId;
	}

	
	public String getAccountNo() {
		return accountNo;
	}

	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	
	public double getTotal() {
		return total;
	}

	
	public void setTotal(double total) {
		this.total = total;
	}

	
	public String getBankName() {
		return bankName;
	}

	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	
	public String getEmployeeName() {
		return employeeName;
	}

	
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@Override
	public int compareTo(RetentionBonusReportBean o) {
		return Integer.parseInt(this.empId) - Integer.parseInt(o.empId);
	}
	
	
	



}
