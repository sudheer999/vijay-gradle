package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.portal.forms.PortalForm;

public class RmgTimeSheetReco extends PortalForm implements Serializable{
	
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
	 */
	
	protected String	htmlStatus;
	
	protected String[] timeSheetDetails;
	
	protected int projType;
	
	protected String	rmgTimeSheetFlag;
	
	protected String	invoiceStatus;
	
	protected String	timeSheetRecoFlag;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getHtmlStatus() {
		return htmlStatus;
	}
	public void setHtmlStatus(String htmlStatus) {
		this.htmlStatus = htmlStatus;
	}
	
	public RmgTimeSheetRecoPk createPk() {
		return new RmgTimeSheetRecoPk(id);
	}
	
	
	
	
	
	
	public String[] getTimeSheetDetails() {
		return timeSheetDetails;
	}
	public void setTimeSheetDetails(String[] timeSheetDetails) {
		this.timeSheetDetails = timeSheetDetails;
	}
	
	






	public int getProjType() {
		return projType;
	}
	public void setProjType(int projType) {
		this.projType = projType;
	}








	public String getRmgTimeSheetFlag() {
		return rmgTimeSheetFlag;
	}
	public void setRmgTimeSheetFlag(String rmgTimeSheetFlag) {
		this.rmgTimeSheetFlag = rmgTimeSheetFlag;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}



	public String getTimeSheetRecoFlag() {
		return timeSheetRecoFlag;
	}
	public void setTimeSheetRecoFlag(String timeSheetRecoFlag) {
		this.timeSheetRecoFlag = timeSheetRecoFlag;
	}



	private Object[]	reportList;
	
	//private String		perdiem;
	
	
	private int			userId;
	private int			empId;
	private String		empName;
	
	//private int			depId;
	private String[]	leaves;
	private String[]	addedEmpData;
	private String[]	components;
	private String		isRejected;
	private int			gotoSeqId;
	private String		commentsByFinanceTeam;
	private String		escalatedFrom;
	private int			escalatedFromId;

	public Object[] getReportList() {
		return reportList;
	}
	public void setReportList(Object[] reportList) {
		this.reportList = reportList;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String[] getLeaves() {
		return leaves;
	}
	public void setLeaves(String[] leaves) {
		this.leaves = leaves;
	}
	public String[] getAddedEmpData() {
		return addedEmpData;
	}
	public void setAddedEmpData(String[] addedEmpData) {
		this.addedEmpData = addedEmpData;
	}
	public String[] getComponents() {
		return components;
	}
	public void setComponents(String[] components) {
		this.components = components;
	}
	public String getIsRejected() {
		return isRejected;
	}
	public void setIsRejected(String isRejected) {
		this.isRejected = isRejected;
	}
	public int getGotoSeqId() {
		return gotoSeqId;
	}
	public void setGotoSeqId(int gotoSeqId) {
		this.gotoSeqId = gotoSeqId;
	}
	public String getEscalatedFrom() {
		return escalatedFrom;
	}
	public void setEscalatedFrom(String escalatedFrom) {
		this.escalatedFrom = escalatedFrom;
	}
	public int getEscalatedFromId() {
		return escalatedFromId;
	}
	public void setEscalatedFromId(int escalatedFromId) {
		this.escalatedFromId = escalatedFromId;
	}
	public String getCommentsByFinanceTeam() {
		return commentsByFinanceTeam;
	}
	public void setCommentsByFinanceTeam(String commentsByFinanceTeam) {
		this.commentsByFinanceTeam = commentsByFinanceTeam;
	}
	
	
	
	
	
	
	
	
	

}
