package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.portal.forms.PortalForm;

public class RetentionBonusReconciliation extends PortalForm implements Serializable {


	/**
	 * This attribute maps to the column ID in the RETENTION_BONUS_RECONCILIATION table.
	 */
	protected int		id;
	/**
	 * This attribute maps to the column ESR_MAP_ID in the RETENTION_BONUS_RECONCILIATION table.
	 */
	protected int		esrMapId;
	/**
	 * This attribute maps to the column TERM in the RETENTION_BONUS_RECONCILIATION table.
	 */
	protected String	term;
	/**
	 * This attribute maps to the column MONTH in the RETENTION_BONUS_RECONCILIATION table.
	 */
	protected String	month;
	/**
	 * This attribute maps to the column YEAR in the RETENTION_BONUS_RECONCILIATION table.
	 */
	protected int		year;
	/**
	 * This attribute maps to the column SUBMITTED_ON in the RETENTION_BONUS_RECONCILIATION table.
	 */
	protected Date		submittedOn;
	/**
	 * This attribute maps to the column COMPLETED_ON in the RETENTION_BONUS_RECONCILIATION table.
	 */
	protected Date		completedOn;
	/**
	 * This attribute maps to the column STATUS in the RETENTION_BONUS_RECONCILIATION table.
	 */
	protected String	status;
	/**
	 * This attribute maps to the column HTML_STATUS in the RETENTION_BONUS_RECONCILIATION table.
	 */
	protected String	htmlStatus;
	
	/**
	 * This attribute maps to the column ID,CURRENCY_TYPE,QUA_AMOUNT,COM_AMOUNT in the RETENTION_DEP_PERDIEM_EXCHANGE_RATES table.
	 */
	private String[]	currencyArray;
	
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

	
	public String[] getCurrencyArray() {
		return currencyArray;
	}

	
	public void setCurrencyArray(String[] currencyArray) {
		this.currencyArray = currencyArray;
	}
	private int			bonusId;
	private String	    reqId;
	private String		actionButtonVisibility;
	private int			gotoSeqId;
	private String		escalatedFrom;
	private int			escalatedFromId;
	private int			companyId;
	private String		currency;
	private String		qAmount;
	private String		cAmount;
	private String		qBonus;
	private String		cBonus;
	private String 		comments;
	private String[]	components;
	private String[]	addedEmpData;
	private String		commentsByFinanceTeam;
	private String		isRejected;
	private String	    reason;
	private String clientName;
	private int totalDays;
	private int projectDays;
	private int globalBenchDays;
	private int leaves;
	private int deductionPercent;
	private String managerName;
	private int managerId;
	
	
	public String getManagerName() {
		return managerName;
	}


	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}


	public int getManagerId() {
		return managerId;
	}


	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}


	/**
	 * Method 'RetentionBonusReconciliation'
	 */
	public RetentionBonusReconciliation() {}
	
	
	public String getqBonus() {
		return qBonus;
	}


	
	public void setqBonus(String qBonus) {
		this.qBonus = qBonus;
	}


	
	public String getcBonus() {
		return cBonus;
	}


	
	public void setcBonus(String cBonus) {
		this.cBonus = cBonus;
	}


	
	public String getComments() {
		return comments;
	}


	
	public void setComments(String comments) {
		this.comments = comments;
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
		if (!(_other instanceof RetentionBonusReconciliation)){ return false; }
		final RetentionBonusReconciliation _cast = (RetentionBonusReconciliation) _other;
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
	 * @return BonusReconciliationPk
	 */
	public RetentionBonusReconciliationPk createPk() {
		return new RetentionBonusReconciliationPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.RetentionBonusReconciliation: ");
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


	public String getReqId() {
		return reqId;
	}


	public void setReqId(String reqId) {
		this.reqId = reqId;
	}


	public String getActionButtonVisibility() {
		return actionButtonVisibility;
	}


	public void setActionButtonVisibility(String actionButtonVisibility) {
		this.actionButtonVisibility = actionButtonVisibility;
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


	public int getBonusId() {
		return bonusId;
	}


	public void setBonusId(int bonusId) {
		this.bonusId = bonusId;
	}


	public int getCompanyId() {
		return companyId;
	}


	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getqAmount() {
		return qAmount;
	}


	public void setqAmount(String qAmount) {
		this.qAmount = qAmount;
	}


	public String getcAmount() {
		return cAmount;
	}


	public void setcAmount(String cAmount) {
		this.cAmount = cAmount;
	}


	public String[] getComponents() {
		return components;
	}


	public void setComponents(String[] components) {
		this.components = components;
	}


	public String[] getAddedEmpData() {
		return addedEmpData;
	}


	public void setAddedEmpData(String[] addedEmpData) {
		this.addedEmpData = addedEmpData;
	}


	public String getCommentsByFinanceTeam() {
		return commentsByFinanceTeam;
	}


	public void setCommentsByFinanceTeam(String commentsByFinanceTeam) {
		this.commentsByFinanceTeam = commentsByFinanceTeam;
	}


	public String getIsRejected() {
		return isRejected;
	}


	public void setIsRejected(String isRejected) {
		this.isRejected = isRejected;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getClientName() {
		return clientName;
	}


	public void setClientName(String clientName) {
		this.clientName = clientName;
	}


	public int getTotalDays() {
		return totalDays;
	}


	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}


	public int getProjectDays() {
		return projectDays;
	}


	public void setProjectDays(int projectDays) {
		this.projectDays = projectDays;
	}


	public int getGlobalBenchDays() {
		return globalBenchDays;
	}


	public void setGlobalBenchDays(int globalBenchDays) {
		this.globalBenchDays = globalBenchDays;
	}


	public int getLeaves() {
		return leaves;
	}


	public void setLeaves(int leaves) {
		this.leaves = leaves;
	}


	public int getDeductionPercent() {
		return deductionPercent;
	}


	public void setDeductionPercent(int deductionPercent) {
		this.deductionPercent = deductionPercent;
	}
	
	



}
