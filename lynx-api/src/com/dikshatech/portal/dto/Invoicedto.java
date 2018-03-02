package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dikshatech.portal.forms.PortalForm;

public class Invoicedto extends PortalForm implements Serializable  {

	protected 	int				id;
	protected 	int     		invoice_Id;
	protected 	int				invoiceAmount;
	private 	Date  			invoiceDate ;
    private 	Date			actionDate;
	private 	String			invoiceStatus;
	private 	int				collectionAmount;
	private 	Date			collectionDate;
	private String 				isFinance;
	private String 				iseditinvoice;
	

	public String getIseditinvoice() {
		return iseditinvoice;
	}

	public void setIseditinvoice(String iseditinvoice) {
		this.iseditinvoice = iseditinvoice;
	}

	public String getIsFinance() {
		return isFinance;
	}

	public void setIsFinance(String isFinance) {
		this.isFinance = isFinance;
	}

	//invoice tabel field
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(int invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}



	public int getCollectionAmount() {
		return collectionAmount;
	}

	public void setCollectionAmount(int collectionAmount) {
		this.collectionAmount = collectionAmount;
	}


	
	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	
	
	
	

	
	
	// RMGTIMESHEET DATA FOR invoice

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

    protected int user_Id;
	
	public int getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(int user_Id) {
		this.user_Id = user_Id;
	}







	protected int prj_Id;
	
	protected int chrg_Code_Id;
	
	protected Date poStartDate;

	protected Date poEndDate;
	
	protected int working_Days;
	
	protected int timeSheet_Id;
	
	protected String comments;
	
	protected int leave;
	
	protected String timeSheet_Cato;
	
	protected String ActionBy;
	
	protected int projType;
	
	protected int empId;
	
	protected String name;
	
    protected String prj_Name;
	
	protected String chrg_Code_Name;
	
	protected String[] timeSheetDetails;
	
	protected String projectType;
	
	protected String poNo;

	protected int rate;

	protected String currency;

	protected String poStatus;

	protected Date timesheetStartDate;
	protected Date timesheetEndDate;
	
	protected int timesheetReportId;
	protected String timesheetStatus;
	
	
	public String getTimesheetStatus() {
		return timesheetStatus;
	}

	public void setTimesheetStatus(String timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
	}

	public int getTimesheetReportId() {
		return timesheetReportId;
	}

	public void setTimesheetReportId(int timesheetReportId) {
		this.timesheetReportId = timesheetReportId;
	}

	public int getPrj_Id() {
		return prj_Id;
	}

	public void setPrj_Id(int prj_Id) {
		this.prj_Id = prj_Id;
	}

	public int getChrg_Code_Id() {
		return chrg_Code_Id;
	}

	public void setChrg_Code_Id(int chrg_Code_Id) {
		this.chrg_Code_Id = chrg_Code_Id;
	}

	public Date getPoStartDate() {
		return poStartDate;
	}

	public void setPoStartDate(Date poStartDate) {
		this.poStartDate = poStartDate;
	}

	public Date getPoEndDate() {
		return poEndDate;
	}

	public void setPoEndDate(Date poEndDate) {
		this.poEndDate = poEndDate;
	}

	public int getWorking_Days() {
		return working_Days;
	}

	public void setWorking_Days(int working_Days) {
		this.working_Days = working_Days;
	}

	public int getTimeSheet_Id() {
		return timeSheet_Id;
	}

	public void setTimeSheet_Id(int timeSheet_Id) {
		this.timeSheet_Id = timeSheet_Id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getLeave() {
		return leave;
	}

	public void setLeave(int leave) {
		this.leave = leave;
	}

	public String getTimeSheet_Cato() {
		return timeSheet_Cato;
	}

	public void setTimeSheet_Cato(String timeSheet_Cato) {
		this.timeSheet_Cato = timeSheet_Cato;
	}

	public String getActionBy() {
		return ActionBy;
	}

	public void setActionBy(String actionBy) {
		ActionBy = actionBy;
	}

	public int getProjType() {
		return projType;
	}

	public void setProjType(int projType) {
		this.projType = projType;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrj_Name() {
		return prj_Name;
	}

	public void setPrj_Name(String prj_Name) {
		this.prj_Name = prj_Name;
	}

	public String getChrg_Code_Name() {
		return chrg_Code_Name;
	}

	public void setChrg_Code_Name(String chrg_Code_Name) {
		this.chrg_Code_Name = chrg_Code_Name;
	}

	public String[] getTimeSheetDetails() {
		return timeSheetDetails;
	}

	public void setTimeSheetDetails(String[] timeSheetDetails) {
		this.timeSheetDetails = timeSheetDetails;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}

	public Date getTimesheetStartDate() {
		return timesheetStartDate;
	}

	public void setTimesheetStartDate(Date timesheetStartDate) {
		this.timesheetStartDate = timesheetStartDate;
	}

	public Date getTimesheetEndDate() {
		return timesheetEndDate;
	}

	public void setTimesheetEndDate(Date timesheetEndDate) {
		this.timesheetEndDate = timesheetEndDate;
	}

	public InvoiceReconciliationPk createPk() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getInvoice_Id() {
		return invoice_Id;
	}

	public void setInvoice_Id(int invoice_Id) {
		this.invoice_Id = invoice_Id;
	}
	
	
	
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.invoicedto: ");
		ret.append("id=" + id);
		ret.append(", invoice_Id=" + invoice_Id);
		ret.append(", invoiceAmount=" + invoiceAmount);
		ret.append(", invoiceDate=" + invoiceDate);
		ret.append(", actionDate=" + actionDate);
		ret.append(", invoiceStatus=" + invoiceStatus);
		ret.append(", collectionAmount=" + collectionAmount);
		ret.append(", collectionDate=" + collectionDate);

		return ret.toString();
	}
	
	
	
	
	public Map<String, Object> toMap(int i) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("invoiceDate", invoiceDate);
		map.put("empId", empId);
		map.put("prj_Name", prj_Name);
		map.put("collectionDate", collectionDate);
		map.put("timesheetReportId", timesheetReportId);
		map.put("chrg_Code_Name", chrg_Code_Name);
		map.put("timeSheet_Id", timeSheet_Id);
		map.put("projType", projType);
		map.put("user_Id", user_Id);
		map.put("poStartDate", poStartDate);
		map.put("poEndDate", poEndDate);
		map.put("invoiceStatus", invoiceStatus);
		map.put("invoiceAmount", invoiceAmount);
	    map.put("collectionAmount", collectionAmount);
		map.put("projectType", projectType);
		map.put("currency", currency);
		map.put("id", id);
		map.put("candidateId", candidateId);
		map.put("actionDate", actionDate);
		map.put("rate", rate);
		map.put("leave", leave);
		map.put("timeSheet_Cato", timeSheet_Cato);
		map.put("ActionBy", ActionBy);
		map.put("timesheetStatus", timesheetStatus);
		map.put("name", name);
		map.put("poStatus", poStatus);
		map.put("prj_Id", prj_Id);
		map.put("invoice_Id", invoice_Id);
		map.put("working_Days", working_Days);
		map.put("timesheetStartDate", timesheetStartDate);
		map.put("timesheetEndDate", timesheetEndDate);
		map.put("chrg_Code_Id", chrg_Code_Id);
		map.put("poNo", poNo);
		return map;
	}


	
	
}
