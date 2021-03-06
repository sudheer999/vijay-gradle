/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.portal.forms.PortalForm;

public class DepPerdiem extends PortalForm implements Serializable {

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
	 */
	
	private String      salaryCycle;
	
	
	private String totalAmount;
	
	private Float lwpDays;

	private String salaryAdvanceDeduction;

	private String travelAdvanceDeduction;
	
	
	
	


	

	public Float getLwpDays() {
		return lwpDays;
	}

	public void setLwpDays(Float lwpDays) {
		this.lwpDays = lwpDays;
	}

	public String getSalaryAdvanceDeduction() {
		return salaryAdvanceDeduction;
	}

	public void setSalaryAdvanceDeduction(String salaryAdvanceDeduction) {
		this.salaryAdvanceDeduction = salaryAdvanceDeduction;
	}

	public String getTravelAdvanceDeduction() {
		return travelAdvanceDeduction;
	}

	public void setTravelAdvanceDeduction(String travelAdvanceDeduction) {
		this.travelAdvanceDeduction = travelAdvanceDeduction;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getSalaryCycle() {
		return salaryCycle;
	}

	public void setSalaryCycle(String salaryCycle) {
		this.salaryCycle = salaryCycle;
	}

	protected String	htmlStatus;
	private String[]	currencyArray;
 private String isnotiBusiness;
 
 private Object[]		hdfcReport;
 private Object[]		listHdfc;
 private Object[]		listNonHdfc;
 
 
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

private String 		deppid;


private String 		bankFlag ;


	public Object[] getHdfcReport() {
	return hdfcReport;
}

public void setHdfcReport(Object[] hdfcReport) {
	this.hdfcReport = hdfcReport;
}

public String getDeppid() {
 
	
	return deppid;
}

public void setDeppid(String deppid) {
	this.deppid = deppid;
}


	public String getBankFlag() {
	return bankFlag;
}

public void setBankFlag(String bankFlag) {
	this.bankFlag = bankFlag;
}

	/**
	 * Method 'DepPerdiem'
	 */
	public DepPerdiem() {}

	/**
	 * Method 'getId'
	 * 
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Method 'getEsrMapId'
	 * 
	 * @return int
	 */
	public int getEsrMapId() {
		return esrMapId;
	}

	/**
	 * Method 'setEsrMapId'
	 * 
	 * @param esrMapId
	 */
	public void setEsrMapId(int esrMapId) {
		this.esrMapId = esrMapId;
	}

	/**
	 * Method 'getTerm'
	 * 
	 * @return String
	 */
	public String getTerm() {
		return term;
	}

	/**
	 * Method 'setTerm'
	 * 
	 * @param term
	 */
	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * Method 'getMonth'
	 * 
	 * @return String
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * Method 'setMonth'
	 * 
	 * @param month
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * Method 'getYear'
	 * 
	 * @return int
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Method 'setYear'
	 * 
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Method 'getSubmittedOn'
	 * 
	 * @return Date
	 */
	public Date getSubmittedOn() {
		return submittedOn;
	}

	/**
	 * Method 'setSubmittedOn'
	 * 
	 * @param submittedOn
	 */
	public void setSubmittedOn(Date submittedOn) {
		this.submittedOn = submittedOn;
	}

	/**
	 * Method 'getCompletedOn'
	 * 
	 * @return Date
	 */
	public Date getCompletedOn() {
		return completedOn;
	}

	/**
	 * Method 'setCompletedOn'
	 * 
	 * @param completedOn
	 */
	public void setCompletedOn(Date completedOn) {
		this.completedOn = completedOn;
	}

	/**
	 * Method 'getStatus'
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Method 'setStatus'
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Method 'getHtmlStatus'
	 * 
	 * @return String
	 */
	public String getHtmlStatus() {
		return htmlStatus;
	}

	/**
	 * Method 'setHtmlStatus'
	 * 
	 * @param htmlStatus
	 */
	public void setHtmlStatus(String htmlStatus) {
		this.htmlStatus = htmlStatus;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other) {
		if (_other == null){ return false; }
		if (_other == this){ return true; }
		if (!(_other instanceof DepPerdiem)){ return false; }
		final DepPerdiem _cast = (DepPerdiem) _other;
		if (id != _cast.id){ return false; }
		if (esrMapId != _cast.esrMapId){ return false; }
		if (term == null ? _cast.term != term : !term.equals(_cast.term)){ return false; }
		if (month == null ? _cast.month != month : !month.equals(_cast.month)){ return false; }
		if (year != _cast.year){ return false; }
		if (submittedOn == null ? _cast.submittedOn != submittedOn : !submittedOn.equals(_cast.submittedOn)){ return false; }
		if (completedOn == null ? _cast.completedOn != completedOn : !completedOn.equals(_cast.completedOn)){ return false; }
		if (status == null ? _cast.status != status : !status.equals(_cast.status)){ return false; }
		if (htmlStatus == null ? _cast.htmlStatus != htmlStatus : !htmlStatus.equals(_cast.htmlStatus)){ return false; }
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode() {
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		_hashCode = 29 * _hashCode + esrMapId;
		if (term != null){
			_hashCode = 29 * _hashCode + term.hashCode();
		}
		if (month != null){
			_hashCode = 29 * _hashCode + month.hashCode();
		}
		_hashCode = 29 * _hashCode + year;
		if (submittedOn != null){
			_hashCode = 29 * _hashCode + submittedOn.hashCode();
		}
		if (completedOn != null){
			_hashCode = 29 * _hashCode + completedOn.hashCode();
		}
		if (status != null){
			_hashCode = 29 * _hashCode + status.hashCode();
		}
		if (htmlStatus != null){
			_hashCode = 29 * _hashCode + htmlStatus.hashCode();
		}
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DepPerdiemPk
	 */
	public DepPerdiemPk createPk() {
		return new DepPerdiemPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.DepPerdiem: ");
		ret.append("id=" + id);
		ret.append(", esrMapId=" + esrMapId);
		ret.append(", term=" + term);
		ret.append(", month=" + month);
		ret.append(", year=" + year);
		ret.append(", submittedOn=" + submittedOn);
		ret.append(", completedOn=" + completedOn);
		ret.append(", status=" + status);
		ret.append(", htmlStatus=" + htmlStatus);
		return ret.toString();
	}

	/**
	 * @param reqId
	 *            the reqId to set
	 */
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	/**
	 * @return the reqId
	 */
	public String getReqId() {
		return reqId;
	}

	/**
	 * @param payableDays
	 *            the payableDays to set
	 */
	public void setPayableDays(float payableDays) {
		this.payableDays = payableDays;
	}

	/**
	 * @return the payableDays
	 */
	public float getPayableDays() {
		return payableDays;
	}

	/**
	 * @param perdiem
	 *            the perdiem to set
	 */
	public void setPerdiem(String perdiem) {
		this.perdiem = perdiem;
	}

	/**
	 * @return the perdiem
	 */
	public String getPerdiem() {
		return perdiem;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param payableDaysHistory
	 *            the payableDaysHistory to set
	 */
	public void setPayableDaysHistory(String payableDaysHistory) {
		this.payableDaysHistory = payableDaysHistory;
	}

	/**
	 * @return the payableDaysHistory
	 */
	public String getPayableDaysHistory() {
		return payableDaysHistory;
	}

	private String	reqId;

	public String getCurrencyHistory() {
		return currencyHistory;
	}

	public void setCurrencyHistory(String currencyHistory) {
		this.currencyHistory = currencyHistory;
	}

	public String getPerdiemHistory() {
		return perdiemHistory;
	}

	public void setPerdiemHistory(String perdiemHistory) {
		this.perdiemHistory = perdiemHistory;
	}

	private Object[]	reportList;
	private boolean		editable;
	private float		payableDays;
	private String		perdiem;
	private String		amount;
	private String		currency;
	private String		comments;
	private String		comment;
	private String		payableDaysHistory;
	private String		currencyHistory;
	private String		perdiemHistory;
	private int			userId;
	private int			empId;
	private String		empName;
	private String		perdiemcomputed;
	private int			depId;
	private String[]	leaves;
	private String[]	addedEmpData;
	private String[]	components;
	private String		isRejected;
	private int			gotoSeqId;
	private int			companyId;
	private String		managerName;
	private String		projectName;
	private String		actionButtonVisibility;
	private String		commentsByFinanceTeam;
	private String		changedHistory;
	private String		escalatedFrom;
	private int			escalatedFromId;

	public int getEscalatedFromId() {
		return escalatedFromId;
	}

	public void setEscalatedFromId(int escalatedFromId) {
		this.escalatedFromId = escalatedFromId;
	}

	public String getEscalatedFrom() {
		return escalatedFrom;
	}

	public void setEscalatedFrom(String escalatedFrom) {
		this.escalatedFrom = escalatedFrom;
	}

	public String getChangedHistory() {
		return changedHistory;
	}

	public void setChangedHistory(String changedHistory) {
		this.changedHistory = changedHistory;
	}

	public String getCommentsByFinanceTeam() {
		return commentsByFinanceTeam;
	}

	public void setCommentsByFinanceTeam(String commentsByFinanceTeam) {
		this.commentsByFinanceTeam = commentsByFinanceTeam;
	}

	public String getActionButtonVisibility() {
		return actionButtonVisibility;
	}

	public void setActionButtonVisibility(String actionButtonVisibility) {
		this.actionButtonVisibility = actionButtonVisibility;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	/**
	 * @param perdiemcomputed
	 *            the perdiemcomputed to set
	 */
	public void setPerdiemcomputed(String perdiemcomputed) {
		this.perdiemcomputed = perdiemcomputed;
	}

	/**
	 * @return the perdiemcomputed
	 */
	public String getPerdiemcomputed() {
		return perdiemcomputed;
	}

	/**
	 * @param depId
	 *            the depId to set
	 */
	public void setDepId(int depId) {
		this.depId = depId;
	}

	/**
	 * @return the depId
	 */
	public int getDepId() {
		
		return depId;
	}

	/**
	 * @param objects
	 *            the reportList to set
	 */
	public void setReportList(Object[] objects) {
		this.reportList = objects;
	}

	/**
	 * @return the reportList
	 */
	public Object[] getReportList() {
		return reportList;
	}

	/**
	 * @param editable
	 *            the editable to set
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * @return the editable
	 */
	public boolean getEditable() {
		return editable;
	}

	/**
	 * @param addedEmpData
	 *            the addedEmpData to set
	 */
	public void setAddedEmpData(String[] addedEmpData) {
		this.addedEmpData = addedEmpData;
	}

	/**
	 * @return the addedEmpData
	 */
	public String[] getAddedEmpData() {
		return addedEmpData;
	}

	/**
	 * @param gotoSeqId
	 *            the gotoSeqId to set
	 */
	public void setGotoSeqId(int gotoSeqId) {
		this.gotoSeqId = gotoSeqId;
	}

	/**
	 * @return the gotoSeqId
	 */
	public int getGotoSeqId() {
		return gotoSeqId;
	}

	/**
	 * @param isRejected
	 *            the isRejected to set
	 */
	public void setIsRejected(String isRejected) {
		this.isRejected = isRejected;
	}

	/**
	 * @return the isRejected
	 */
	public String getIsRejected() {
		return isRejected;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String[] getCurrencyArray() {
		return currencyArray;
	}

	public void setCurrencyArray(String currencyArray[]) {
		this.currencyArray = currencyArray;
	}

	public String[] getLeaves() {
		return leaves;
	}

	public void setLeaves(String[] leaves) {
		this.leaves = leaves;
	}

	public String[] getComponents() {
		return components;
	}

	public void setComponents(String[] components) {
		this.components = components;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getIsnotiBusiness() {
		return isnotiBusiness;
	}

	public void setIsnotiBusiness(String isnotiBusiness) {
		this.isnotiBusiness = isnotiBusiness;
	}
}
