package com.dikshatech.beans;

import java.util.HashMap;
import java.util.Map;

public class ReimbursementReportBean {
	
	private String	name;
	private String	account_no;
	private int		emp_code;
	private String	cur_code;
	private String	cr_dr;
	private String	tran_amt;
	private String	tran_part;
	private String	bankName;
	private double	payableDays;
	private String    primaryIfsc;
	private String email_id;
	protected String				reqId;
	protected String				totalAmount;
	
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	private double    Amount;
	

	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
		Amount = amount;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount_no() {
		return account_no;
	}
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	public int getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}
	public String getCur_code() {
		return cur_code;
	}
	public void setCur_code(String cur_code) {
		this.cur_code = cur_code;
	}
	public String getCr_dr() {
		return cr_dr;
	}
	public void setCr_dr(String cr_dr) {
		this.cr_dr = cr_dr;
	}
	public String getTran_amt() {
		return tran_amt;
	}
	public void setTran_amt(String tran_amt) {
		this.tran_amt = tran_amt;
	}
	public String getTran_part() {
		return tran_part;
	}
	public void setTran_part(String tran_part) {
		this.tran_part = tran_part;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public double getPayableDays() {
		return payableDays;
	}
	public void setPayableDays(double payableDays) {
		this.payableDays = payableDays;
	}
	public String getPrimaryIfsc() {
		return primaryIfsc;
	}
	public void setPrimaryIfsc(String primaryIfsc) {
		this.primaryIfsc = primaryIfsc;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	
	public Map<String, Object> toMap(int i){
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("name", name);
		map.put("emp_code", emp_code);
		map.put("account_no", account_no);
		map.put("totalAmount", totalAmount);
		map.put("bankName", bankName);
		map.put("primaryIfsc", primaryIfsc);
		map.put("email_id",email_id);
		map.put("reqId", reqId);

		return map;
	}
	
	
	
	
	
	
	

}
