package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.portal.forms.PortalForm;

public class InvoiceReconciliation extends PortalForm implements Serializable {

	/**
	 * This attribute maps to the column ID in the DEP_PERDIEM table.
	 */
	protected int		id;
	/**
	 * This attribute maps to the column ESR_MAP_ID in the DEP_PERDIEM table.
	 */
	protected int		esrMapId;
	/**
	 * This attribute maps to the column TERM in the DEP_PERDIEM table.
	 */
	protected String	term;
	/**
	 * This attribute maps to the column MONTH in the DEP_PERDIEM table.
	 */
	protected String	month;
	/**
	 * This attribute maps to the column YEAR in the DEP_PERDIEM table.
	 */
	protected int		year;
	/**
	 * This attribute maps to the column SUBMITTED_ON in the DEP_PERDIEM table.
	 */
	protected Date		submittedOn;
	/**
	 * This attribute maps to the column COMPLETED_ON in the DEP_PERDIEM table.
	 */
	protected Date		completedOn;
	/**
	 * This attribute maps to the column STATUS in the DEP_PERDIEM table.
	 */
	protected String	status;
	/**
	 * This attribute maps to the column HTML_STATUS in the DEP_PERDIEM table.
	 * 
	 */
	
	
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
	protected String	html_status;
	protected String	invoice_status;
	 public String getHtml_status() {
		return html_status;
	}

	public void setHtml_status(String html_status) {
		this.html_status = html_status;
	}

	public String getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
	}
	private Object[]	invoiceList;

	 protected 	String[] 		invoiceDetails;
	 private   	String        invoiceDownloadId;

	

		public Object[] getInvoiceList() {
			return invoiceList;
		}

		public void setInvoiceList(Object[] invoiceList) {
			this.invoiceList = invoiceList;
		}
	
	public int getId() {
		return id;
		//return 1;
	}
	public void setId(int id) {
		this.id = id;
		//this.id = 1;
	}
	public int getEsrMapId() {
		return esrMapId;
	}
	public void setEsrMapId(int esrMapId) {
		this.esrMapId = esrMapId;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Date getSubmittedOn() {
		return submittedOn;
	}
	public void setSubmittedOn(Date submittedOn) {
		this.submittedOn = submittedOn;
	}
	public Date getCompletedOn() {
		return completedOn;
	}
	public void setCompletedOn(Date completedOn) {
		this.completedOn = completedOn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	//charge code tabel field
	
	public String[] getInvoiceDetails() {
		return invoiceDetails;
	}
	public void setInvoiceDetails(String[] invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}
	protected int user_Id;
	
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


	
	
	
	public int getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(int user_Id) {
		this.user_Id = user_Id;
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
	public String getInvoiceDownloadId() {
		return invoiceDownloadId;
	}
	public void setInvoiceDownloadId(String invoiceDownloadId) {
		this.invoiceDownloadId = invoiceDownloadId;
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
	
	
	
}
