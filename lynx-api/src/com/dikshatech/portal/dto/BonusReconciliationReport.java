package com.dikshatech.portal.dto;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class BonusReconciliationReport implements Serializable,Comparable<BonusReconciliationReport> {
	
	/**
	 * This attribute maps to the column ID in the BONUS_RECONCILIATION_REPORT table.
	 */
	protected int		id;
	/**
	 * This attribute maps to the column BR_ID in the BONUS_RECONCILIATION_REPORT table.
	 */
	protected int		bonusId;
	/**
	 * This attribute maps to the column USER_ID in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	protected int		userId;
	
	/**
	 * This attribute maps to the column QUATERELY_BONUS in the BONUS_RECONCILIATION_REPORT table.
	 */
	private String		qBonus;
	/**
	 * This attribute maps to the column COMPANY_BONUS in the BONUS_RECONCILIATION_REPORT table.
	 */
	private String		cBonus;
	/**
	 * This attribute maps to the column CURRENCY_TYPE in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	protected int		currencyType;
	
	/**
	 * This attribute maps to the column AMOUNT in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	private String		qAmount;
	/**
	 * This attribute maps to the column AMOUNT_INR in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	private String		qAmountInr;
	
	private int projectDays;
	private int globalBenchDays;
	private int totalDays;
	private String paid;
	
	
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




	public int getTotalDays() {
		return totalDays;
	}




	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}




	public String getqAmount() {
		return qAmount;
	}



	
	public void setqAmount(String qAmount) {
		this.qAmount = qAmount;
	}



	
	public String getqAmountInr() {
		return qAmountInr;
	}



	
	public void setqAmountInr(String qAmountInr) {
		this.qAmountInr = qAmountInr;
	}



	
	public String getcAmount() {
		return cAmount;
	}



	
	public void setcAmount(String cAmount) {
		this.cAmount = cAmount;
	}



	
	public String getcAmountInr() {
		return cAmountInr;
	}



	
	public void setcAmountInr(String cAmountInr) {
		this.cAmountInr = cAmountInr;
	}

	/**
	 * This attribute maps to the column MANAGER_ID in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	/**
	 * This attribute maps to the column AMOUNT in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	private String		cAmount;
	/**
	 * This attribute maps to the column AMOUNT_INR in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	private String		cAmountInr;
	/**
	 * This attribute maps to the column MANAGER_ID in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	protected int		managerId;
	/**
	 * This attribute maps to the column MANAGER_NAME in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	protected String	managerName;
	/**
	 * This attribute maps to the column CLIENT_NAME in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	private String	clientName;
	/**
	 * This attribute maps to the column MODIFIED_BY in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	protected String	modifiedBy;
	/**
	 * This attribute maps to the column MODIFIED_ON in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	protected Date		modifiedOn;
	/**
	 * This attribute maps to the column TYPE in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	private short		type;
	/**
	 * This attribute maps to the column ACCOUNT_TYPE in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	private short		accountType;
	/**
	 * This attribute maps to the column TOTAL in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	private String		total;
	/**
	 * This attribute maps to the column COMMENTS in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	protected String	comments;
	/**
	 * This attribute maps to the  in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	private String		employeeName;
	/**
	 * This attribute maps to the column  in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	private int		empId;
	/**
	 * This attribute maps to the BONUS_FROM in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	
	private Date		from;
	/**
	 * This attribute maps to the BONUS_TO  in the BONUS_RECONCILIATION_REPORT
	 * table.
	 */
	private String      month;
	private Date		to;
	private boolean		release;
	private String		bonusHistory;
	private String		commentsHistory;
	private String		currencyHistory;
	private int year;
	private String salaryCycle;



	public String getPaid() {
		return paid;
	}




	public void setPaid(String paid) {
		this.paid = paid;
	}






	public String getSalaryCycle() {
		return salaryCycle;
	}




	public void setSalaryCycle(String salaryCycle) {
		this.salaryCycle = salaryCycle;
	}




	public String getCurrencyName() {
		return currencyName;
	}


	
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	private String		currencyName;
	/**
	 * Method 'DepPerdiemReport'
	 */
	public BonusReconciliationReport() {}

	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	
	public int getBonusId() {
		return bonusId;
	}

	
	public void setBonusId(int bonusId) {
		this.bonusId = bonusId;
	}

	
	public int getUserId() {
		return userId;
	}

	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCurrencyType() {
		return currencyType;
	}

	
	public void setCurrencyType(int currencyType) {
		this.currencyType = currencyType;
	}

	
	public int getManagerId() {
		return managerId;
	}

	
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	
	public String getManagerName() {
		return managerName;
	}

	
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	
	public String getClientName() {
		return clientName;
	}

	
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	
	public String getModifiedBy() {
		return modifiedBy;
	}

	
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	
	public Date getModifiedOn() {
		return modifiedOn;
	}

	
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	
	public short getType() {
		return type;
	}

	
	public void setType(short type) {
		this.type = type;
	}

	
	public short getAccountType() {
		return accountType;
	}

	
	public void setAccountType(short accountType) {
		this.accountType = accountType;
	}

	
	public String getTotal() {
		return total;
	}

	
	public void setTotal(String total) {
		this.total = total;
	}

	
	public String getComments() {
		return comments;
	}

	
	public void setComments(String comments) {
		this.comments = comments;
	}

	
	public String getEmployeeName() {
		return employeeName;
	}

	
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	
	public int getEmpId() {
		return empId;
	}

	
	public void setEmpId(int empId) {
		this.empId = empId;
	}

	
	public Date getFrom() {
		return from;
	}

	
	public void setFrom(Date from) {
		this.from = from;
	}

	
	public Date getTo() {
		return to;
	}

	
	public void setTo(Date to) {
		this.to = to;
	}

	
	public boolean isRelease() {
		return release;
	}

	
	public void setRelease(boolean release) {
		this.release = release;
	}

	public BonusReconciliationReport(int bonusId, int userId, String qBonus,String cBonus, int currencyType, int managerId, String managerName,String clientName) {
		this.bonusId = bonusId;
		this.userId = userId;
		this.qBonus = qBonus;
		this.cBonus = cBonus;
		this.currencyType = currencyType;
		this.managerId = managerId;
		this.managerName = managerName;
		this.clientName = clientName;
	}

	public BonusReconciliationReport(int userId, String qBonus,String cBonus, int currencyType, short type,String month) {
		this.userId = userId;
		this.qBonus = qBonus;
		this.cBonus = cBonus;
		this.currencyType = currencyType;
		this.type = type;
		this.month = month;
	}

	public BonusReconciliationReport(int userId,String qBonus,String cBonus, int currencyType, short type, String month, String clientName) {
		this(userId, qBonus,cBonus, currencyType, type, month);
		this.clientName = clientName;
	}

	public BonusReconciliationReport(int bonusId, int managerId, String managerName, String clientName) {
		this.bonusId = bonusId;
		this.managerId = managerId;
		this.managerName = managerName;
		this.clientName = clientName;
	}

	public void setData(int bonusId, int managerId, String managerName, String clientName) {
		this.bonusId = bonusId;
		this.managerId = managerId;
		this.managerName = managerName;
		this.clientName = clientName;
	}
	
	public BonusReconciliationReport(int bonusId, int userId, String total, int currencyType, int managerId, String managerName,String clientName,String comments,String salaryCycle) {
		this.bonusId = bonusId;
		this.userId = userId;
		this.currencyType = currencyType;
		this.managerId = managerId;
		this.managerName = managerName;
		this.clientName = clientName;
		this.total = total;
		this.comments = comments;
		this.salaryCycle=salaryCycle;
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
		if (!(_other instanceof BonusReconciliationReport)){ return false; }
		final BonusReconciliationReport _cast = (BonusReconciliationReport) _other;
		if (id != _cast.id){ return false; }
		if (bonusId != _cast.bonusId){ return false; }
		if (userId != _cast.userId){ return false; }
		if (qAmount == null ? _cast.qAmount != qAmount : !qAmount.equals(_cast.qAmount)){ return false; }
		if (cAmount == null ? _cast.cAmount != cAmount : !cAmount.equals(_cast.cAmount)){ return false; }
		if (currencyType != _cast.currencyType){ return false; }
		if (managerId != _cast.managerId){ return false; }
		if (managerName == null ? _cast.managerName != managerName : !managerName.equals(_cast.managerName)){ return false; }
		if (clientName == null ? _cast.clientName != clientName : !clientName.equals(_cast.clientName)){ return false; }
		if (modifiedBy == null ? _cast.modifiedBy != modifiedBy : !modifiedBy.equals(_cast.modifiedBy)){ return false; }
		if (modifiedOn == null ? _cast.modifiedOn != modifiedOn : !modifiedOn.equals(_cast.modifiedOn)){ return false; }
		if (comments == null ? _cast.comments != comments : !comments.equals(_cast.comments)){ return false; }
		if (projectDays != _cast.projectDays){ return false; }
		if (globalBenchDays != _cast.globalBenchDays){ return false; }
		if (totalDays != _cast.totalDays){ return false; }
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
		_hashCode = 29 * _hashCode + bonusId;
		_hashCode = 29 * _hashCode + userId;
		
		if (qAmount != null){
			_hashCode = 29 * _hashCode + qAmount.hashCode();
		}
		if (cAmount != null){
			_hashCode = 29 * _hashCode + qAmount.hashCode();
		}
		_hashCode = 29 * _hashCode + currencyType;
		_hashCode = 29 * _hashCode + managerId;
		if (managerName != null){
			_hashCode = 29 * _hashCode + managerName.hashCode();
		}
		if (clientName != null){
			_hashCode = 29 * _hashCode + clientName.hashCode();
		}
		if (modifiedBy != null){
			_hashCode = 29 * _hashCode + modifiedBy.hashCode();
		}
		if (modifiedOn != null){
			_hashCode = 29 * _hashCode + modifiedOn.hashCode();
		}
		if (comments != null){
			_hashCode = 29 * _hashCode + comments.hashCode();
		}
		_hashCode = 29 * _hashCode + projectDays;
		_hashCode = 29 * _hashCode + globalBenchDays;
		_hashCode = 29 * _hashCode + totalDays;
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DepPerdiemReportPk
	 */
	public BonusReconciliationReportPk createPk() {
		return new BonusReconciliationReportPk(id);
	}


	public String getBonusHistory() {
		return bonusHistory;
	}


	public void setBonusHistory(String bonusHistory) {
		this.bonusHistory = bonusHistory;
	}


	public String getCommentsHistory() {
		return commentsHistory;
	}


	public void setCommentsHistory(String commentsHistory) {
		this.commentsHistory = commentsHistory;
	}


	public String getCurrencyHistory() {
		return currencyHistory;
	}


	public void setCurrencyHistory(String currencyHistory) {
		this.currencyHistory = currencyHistory;
	}

	@Override
	public String toString() {
		return "DepPerdiemReport [id=" + id + ", bonusId=" + bonusId + ", userId=" + userId + ", " + (qBonus != null ? "qBonus=" + qBonus + ", " : "") +(cBonus != null ? "cBonus=" + cBonus + ", " : "") + "currencyType=" + currencyType + ", " + (qAmount != null ? "qAmount=" + qAmount + ", " : "") + (qAmountInr != null ? "qAmountInr=" + qAmountInr + ", " : "") +(cAmount != null ? "cAmount=" + cAmount + ", " : "") + (cAmountInr != null ? "cAmountInr=" + cAmountInr + ", " : "") +
				"managerId=" + managerId + ", " + (managerName != null ? "managerName=" + managerName + ", " : "")
				+ (clientName != null ? "clientName=" + clientName + ", " : "") + (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") + (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") + "type=" + type + ", " + (total != null ? "total=" + total + ", " : "") + (comments != null ? "comments=" + comments + ", " : "") + (employeeName != null ? "employeeName=" + employeeName + ", " : "") + (empId > 0 ? "empId=" + empId + ", " : "")
				+ (bonusHistory != null ? "bonusHistory=" + bonusHistory + ", " : "")  + (commentsHistory != null ? "commentsHistory=" + commentsHistory + ", " : "") + (currencyHistory != null ? "currencyHistory=" + currencyHistory + ", " : "") + (currencyName != null ? "currencyName=" + currencyName + ", " : "")+" projectDays=" + projectDays + ", globalBenchDays=" + globalBenchDays + ", totalDays=" + totalDays + ",salaryCycle="+ salaryCycle+ "]";
	}



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




	public String getMonth() {
		return month;
	}




	public void setMonth(String month) {
		this.month = month;
	}
	
	public Map<String, Object> toMap(int i) {
		Map<String, Object> map = new HashMap<String, Object>();
		switch (i) {

			case 1:
				map.put("id", id);
				map.put("empId", empId);
				map.put("employeeName", employeeName);
				map.put("managerName", managerName);
				map.put("clientName", clientName);
				map.put("modifiedBy", modifiedBy);
				map.put("modifiedOn", modifiedOn);
				map.put("currencyName", currencyName);
				if (isRelease()) map.put("release", 1);
				break;
			case 2:
				
				map.put("qAmount", qAmount);
				map.put("qAmountInr", qAmountInr);
				map.put("cAmount", cAmount);
				map.put("cAmountInr", cAmountInr);
				map.put("total", total);
				map.put("comments", comments);
				map.put("currencyType", currencyType);
				map.put("type", type);
				map.put("month", month);
				map.put("projectDays", projectDays);
				map.put("globalBenchDays", globalBenchDays);
				map.put("totalDays", totalDays);
				break;
		}
		map.put("salaryCycle", salaryCycle);
		map.put("qBonus", qBonus);
		map.put("cBonus", cBonus);
        map.put("total", total);
		return map;
	}

	public BonusRecReportHistory getBonusRecReportHistory() {
		return new BonusRecReportHistory(id, qBonus,cBonus,  (short) currencyType,  modifiedBy, modifiedOn, comments, type, qAmount, qAmountInr,cAmount,cAmountInr, total,currencyName);
	}




	@Override
	public int compareTo(BonusReconciliationReport o) {
		// TODO Auto-generated method stub
		return 0;
	}




	public int getYear() {
		return year;
	}




	public void setYear(int year) {
		this.year = year;
	}

}
