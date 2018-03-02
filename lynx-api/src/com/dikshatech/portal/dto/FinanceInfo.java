

 
package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.beans.Salary;
import com.dikshatech.portal.forms.PortalForm;

public class FinanceInfo extends PortalForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * This attribute maps to the column ID in the FINANCE_INFO table.
	 */
	protected int		id;
	protected String	plb;

	public String getPlb() {
		return plb;
	}

	public void setPlb(String plb) {
		this.plb = plb;
	}

	/**
	 * This attribute maps to the column PF_ACC_NO in the FINANCE_INFO table.
	 */
	protected String	pfAccNo;
	/**
	 * This attribute maps to the column PAN_NO in the FINANCE_INFO table.
	 */
	protected String	panNo;
	/**
	 * This attribute maps to the column CTC in the FINANCE_INFO table.
	 */
	protected String	ctc;
	/**
	 * This attribute maps to the column PRIM_BANK_ACC_NO in the FINANCE_INFO table.
	 */
	protected String	primBankAccNo;
	/**
	 * This attribute maps to the column PRIM_BANK_NAME in the FINANCE_INFO table.
	 */
	protected String	primBankName;
	/**
	 * This attribute maps to the column SEC_BANK_ACC_NO in the FINANCE_INFO table.
	 */
	protected String	secBankAccNo;
	/**
	 * This attribute maps to the column SEC_BANK_NAME in the FINANCE_INFO table.
	 */
	protected String	secBankName;
	/**
	 * This attribute maps to the column SALARY_DETAIL in the FINANCE_INFO table.
	 */
	protected String	uan;
	/**
	 * This attribute maps to the column SALARY_DETAIL in the FINANCE_INFO table.
	 */
	protected String	aadhaarNo;
	/**
	 * This attribute maps to the column SALARY_DETAIL in the FINANCE_INFO table.
	 */
	
	//primaryifsc:"546456"
	
	protected String	salaryDetail;
	protected String[]	fields;
	protected String	files;
	protected int		docid;
	private Documents[]	documentArr;
	boolean				salaryEdit;
	/**
	 * Method 'FinanceInfo'
	 */
	private String		ctcAmount;
	private String		perdiemAmount;
	private int			perdiemId;
	
	protected String esic;
	
	protected String active;
	
	protected  Date inActiveFromDate;
	
	protected  Date inActiveToDate;



	public Date getInActiveFromDate() {
		return inActiveFromDate;
	}

	public void setInActiveFromDate(Date inActiveFromDate) {
		this.inActiveFromDate = inActiveFromDate;
	}

	public Date getInActiveToDate() {
		return inActiveToDate;
	}

	public void setInActiveToDate(Date inActiveToDate) {
		this.inActiveToDate = inActiveToDate;
	}

	public String getActive() {
		return active;
	//	return "YES";
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getEsic() {
		return esic;
	}

	public void setEsic(String esic) {
		this.esic = esic;
	}

	public int getCPLBperformance() {
		return CPLBperformance;
	}

	public void setCPLBperformance(int cPLBperformance) {
		CPLBperformance = cPLBperformance;
	}

	private int			perdiemUserId;
	private Date		perdiemFrom;
	
	private int QPLBperformance;
	
	
	private int CPLBperformance;
	
	
	public int getQPLBperformance() {
		return QPLBperformance;
	}

	public void setQPLBperformance(int qPLBperformance) {
		QPLBperformance = qPLBperformance;
	}
	
	
	private int salaryStack;
	
	public int getSalaryStack() {
		return salaryStack;
	}

	public void setSalaryStack(int salaryStack) {
		this.salaryStack = salaryStack;
	}

	



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSalaryInAdvBalance() {
		return salaryInAdvBalance;
	}

	public void setSalaryInAdvBalance(String salaryInAdvBalance) {
		this.salaryInAdvBalance = salaryInAdvBalance;
	}

	public String getSalaryInAdvPaid() {
		return salaryInAdvPaid;
	}

	public void setSalaryInAdvPaid(String salaryInAdvPaid) {
		this.salaryInAdvPaid = salaryInAdvPaid;
	}

	
	

	private Date		perdiemTo;
	private String		perdiemCurrencyType;
	private String		salaryInAdvTot;
	private String		salaryInAdvMon;

	private String		salaryInAdvBalance;
	private String		salaryInAdvPaid;
	private String      salaryAdvToBeDeducted;

	


	private String		salaryInNoOfMonths;
	private String		fixedPerdiemAmount;
	
	public String getSalaryInNoOfMonths() {
		return salaryInNoOfMonths;
	}

	public void setSalaryInNoOfMonths(String salaryInNoOfMonths) {
		this.salaryInNoOfMonths = salaryInNoOfMonths;
	}

	public String getPerdiemInNoOfMonths() {
		return perdiemInNoOfMonths;
	}

	public void setPerdiemInNoOfMonths(String perdiemInNoOfMonths) {
		this.perdiemInNoOfMonths = perdiemInNoOfMonths;
	}

	private Date		fixedPerdiemFrom;
	private Date		fixedPerdiemTo;
	private String		fixedPerdiemCurrencyType;
	private String		perdiemInAdvTot;
	private String		perdiemInAdvMon;
	private int		    modifiedBy;
	private String		retentionBonus;
	private String		joiningBonus;
	private String		perdiemOffered;
	private String		perdiemInNoOfMonths;
	private String 		perdiemTerms;
	private String     retentionInstallments;
	private Date     retentionStartDate;

	public String getRetentionInstallments() {
		return retentionInstallments;
	}

	public void setRetentionInstallments(String retentionInstallments) {
		this.retentionInstallments = retentionInstallments;
	}

	public Date getRetentionStartDate() {
		return retentionStartDate;
	}

	public void setRetentionStartDate(Date retentionStartDate) {
		this.retentionStartDate = retentionStartDate;
	}

	public String getPerdiemInAdvPaid() {
		return perdiemInAdvPaid;
	}

	public void setPerdiemInAdvPaid(String perdiemInAdvPaid) {
		this.perdiemInAdvPaid = perdiemInAdvPaid;
	}

	private String		perdiemInBalTot;
	private String		perdiemInAdvPaid;
	private String		paidMonth;
	private String		salaryPaidMonth;
	
	private String 		salaryCycle;
	private String		primaryifsc;;
	private String 		secondaryifsccode;
	
	
	

	

	public String getPrimaryifsc() {
		return primaryifsc;
	}

	public void setPrimaryifsc(String primaryifsc) {
		this.primaryifsc = primaryifsc;
	}

	public String getSecondaryifsccode() {
		return secondaryifsccode;
	}

	public void setSecondaryifsccode(String secondaryifsccode) {
		this.secondaryifsccode = secondaryifsccode;
	}

	

	public String getSalaryCycle() {
		return salaryCycle;
	}

	public void setSalaryCycle(String salaryCycle) {
		this.salaryCycle = salaryCycle;
	}

	public String getPerdiemTerms() {
		return perdiemTerms;
	}

	public void setPerdiemTerms(String perdiemTerms) {
		this.perdiemTerms = perdiemTerms;
	}

	public String getPerdiemOffered() {
		return perdiemOffered;
	}

	public void setPerdiemOffered(String perdiemOffered) {
		this.perdiemOffered = perdiemOffered;
	}

	public String getPerdiemAmount() {
		return perdiemAmount;
	}

	public void setPerdiemAmount(String perdiemAmount) {
		this.perdiemAmount = perdiemAmount;
	}

	private Salary[]	salary;

	public Salary[] getSalary() {
		return salary;
	}

	public void setSalary(Salary[] salary) {
		this.salary = salary;
	}

	public String getCtcAmount() {
		return ctcAmount;
	}

	public void setCtcAmount(String ctcAmount) {
		this.ctcAmount = ctcAmount;
	}

	public FinanceInfo() {}

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
	 * Method 'getPfAccNo'
	 * 
	 * @return String
	 */
	public String getPfAccNo() {
		return pfAccNo;
	}

	/**
	 * Method 'setPfAccNo'
	 * 
	 * @param pfAccNo
	 */
	public void setPfAccNo(String pfAccNo) {
		this.pfAccNo = pfAccNo;
	}

	/**
	 * Method 'getPanNo'
	 * 
	 * @return String
	 */
	public String getPanNo() {
		return panNo;
	}

	/**
	 * Method 'setPanNo'
	 * 
	 * @param panNo
	 */
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	/**
	 * Method 'getCtc'
	 * 
	 * @return String
	 */
	public String getCtc() {
		return ctc;
	}

	/**
	 * Method 'setCtc'
	 * 
	 * @param ctc
	 */
	public void setCtc(String ctc) {
		this.ctc = ctc;
	}

	/**
	 * Method 'getPrimBankAccNo'
	 * 
	 * @return String
	 */
	public String getPrimBankAccNo() {
		return primBankAccNo;
	}

	/**
	 * Method 'setPrimBankAccNo'
	 * 
	 * @param primBankAccNo
	 */
	public void setPrimBankAccNo(String primBankAccNo) {
		this.primBankAccNo = primBankAccNo;
	}

	/**
	 * Method 'getPrimBankName'
	 * 
	 * @return String
	 */
	public String getPrimBankName() {
		return primBankName;
	}

	/**
	 * Method 'setPrimBankName'
	 * 
	 * @param primBankName
	 */
	public void setPrimBankName(String primBankName) {
		this.primBankName = primBankName;
	}

	/**
	 * Method 'getSecBankAccNo'
	 * 
	 * @return String
	 */
	public String getSecBankAccNo() {
		return secBankAccNo;
	}

	/**
	 * Method 'setSecBankAccNo'
	 * 
	 * @param secBankAccNo
	 */
	public void setSecBankAccNo(String secBankAccNo) {
		this.secBankAccNo = secBankAccNo;
	}

	/**
	 * Method 'getSecBankName'
	 * 
	 * @return String
	 */
	public String getSecBankName() {
		return secBankName;
	}

	/**
	 * Method 'setSecBankName'
	 * 
	 * @param secBankName
	 */
	public void setSecBankName(String secBankName) {
		this.secBankName = secBankName;
	}

	/**
	 * Method 'getSalaryDetail'
	 * 
	 * @return String
	 */
	public String getSalaryDetail() {
		return salaryDetail;
	}

	/**
	 * Method 'setSalaryDetail'
	 * 
	 * @param salaryDetail
	 */
	public void setSalaryDetail(String salaryDetail) {
		this.salaryDetail = salaryDetail;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public int getDocid() {
		return docid;
	}

	public void setDocid(int docid) {
		this.docid = docid;
	}

	public Documents[] getDocumentArr() {
		return documentArr;
	}

	public void setDocumentArr(Documents[] documentArr) {
		this.documentArr = documentArr;
	}

	public boolean isSalaryEdit() {
		return salaryEdit;
	}

	public void setSalaryEdit(boolean salaryEdit) {
		this.salaryEdit = salaryEdit;
	}
	

	public String getUan() {
		return uan;
	}

	public void setUan(String uan) {
		this.uan = uan;
	}

	public String getAadhaarNo() {
		return aadhaarNo;
	}

	public void setAadhaarNo(String aadhaarNo) {
		this.aadhaarNo = aadhaarNo;
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
		if (!(_other instanceof FinanceInfo)){ return false; }
		final FinanceInfo _cast = (FinanceInfo) _other;
		if (id != _cast.id){ return false; }
		if (pfAccNo == null ? _cast.pfAccNo != pfAccNo : !pfAccNo.equals(_cast.pfAccNo)){ return false; }
		if (panNo == null ? _cast.panNo != panNo : !panNo.equals(_cast.panNo)){ return false; }
		if (uan == null ? _cast.uan != uan : !uan.equals(_cast.uan)){ return false; }
		if (aadhaarNo == null ? _cast.aadhaarNo != aadhaarNo : !aadhaarNo.equals(_cast.aadhaarNo)){ return false; }
		if (ctc == null ? _cast.ctc != ctc : !ctc.equals(_cast.ctc)){ return false; }
		if (primBankAccNo == null ? _cast.primBankAccNo != primBankAccNo : !primBankAccNo.equals(_cast.primBankAccNo)){ return false; }
		if (primBankName == null ? _cast.primBankName != primBankName : !primBankName.equals(_cast.primBankName)){ return false; }
		if (secBankAccNo == null ? _cast.secBankAccNo != secBankAccNo : !secBankAccNo.equals(_cast.secBankAccNo)){ return false; }
		if (secBankName == null ? _cast.secBankName != secBankName : !secBankName.equals(_cast.secBankName)){ return false; }
		if (salaryDetail == null ? _cast.salaryDetail != salaryDetail : !salaryDetail.equals(_cast.salaryDetail)){ return false; }
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
		if (pfAccNo != null){
			_hashCode = 29 * _hashCode + pfAccNo.hashCode();
		}
		if (panNo != null){
			_hashCode = 29 * _hashCode + panNo.hashCode();
		}
		if (uan != null){
			_hashCode = 29 * _hashCode + uan.hashCode();
		}
		if (aadhaarNo != null){
			_hashCode = 29 * _hashCode + aadhaarNo.hashCode();
		}
		if (ctc != null){
			_hashCode = 29 * _hashCode + ctc.hashCode();
		}
		if (primBankAccNo != null){
			_hashCode = 29 * _hashCode + primBankAccNo.hashCode();
		}
		if (primBankName != null){
			_hashCode = 29 * _hashCode + primBankName.hashCode();
		}
		if (secBankAccNo != null){
			_hashCode = 29 * _hashCode + secBankAccNo.hashCode();
		}
		if (secBankName != null){
			_hashCode = 29 * _hashCode + secBankName.hashCode();
		}
		if (salaryDetail != null){
			_hashCode = 29 * _hashCode + salaryDetail.hashCode();
		}
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return FinanceInfoPk
	 */
	public FinanceInfoPk createPk() {
		return new FinanceInfoPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.FinanceInfo: ");
		ret.append("id=" + id);
		ret.append(", pfAccNo=" + pfAccNo);
		ret.append(", panNo=" + panNo);
		ret.append(", ctc=" + ctc);
		ret.append(", primBankAccNo=" + primBankAccNo);
		ret.append(", primBankName=" + primBankName);
		ret.append(", secBankAccNo=" + secBankAccNo);
		ret.append(", secBankName=" + secBankName);
		ret.append(", salaryDetail=" + salaryDetail);
		ret.append(", uan=" + uan);
		ret.append(", aadhaarNo=" + aadhaarNo);
		ret.append(", totalCtc=" + totalCtc);
		ret.append(", salaryStack=" + salaryStack);
		ret.append(", QPLBperformance=" + QPLBperformance);
		return ret.toString();
	}

	
	// ------------------------------------------------------ PERDIEM_MASTER_DATA ----------------------------------------
	public String getPerdiemCurrencyType() {
		return perdiemCurrencyType;
	}

	

	

	public void setPerdiemCurrencyType(String perdiemCurrencyType) {
		this.perdiemCurrencyType = perdiemCurrencyType;
	}

	private PerdiemMasterData	perdiemMasterData;

	public int getPerdiemUserId() {
		return perdiemUserId;
	}

	public void setPerdiemUserId(int perdiemUserId) {
		this.perdiemUserId = perdiemUserId;
	}

	public Date getPerdiemFrom() {
		return perdiemFrom;
	}

	public void setPerdiemFrom(Date perdiemFrom) {
		this.perdiemFrom = perdiemFrom;
	}

	public Date getPerdiemTo() {
		return perdiemTo;
	}

	public void setPerdiemTo(Date perdiemTo) {
		this.perdiemTo = perdiemTo;
	}

	public void setPerdiemId(int perdiemId) {
		this.perdiemId = perdiemId;
	}

	public int getPerdiemId() {
		return perdiemId;
	}

	public void setPerdiemMasterData(PerdiemMasterData perdiemMasterData) {
		this.perdiemMasterData = perdiemMasterData;
	}

	public PerdiemMasterData getPerdiemMasterData() {
		return perdiemMasterData;
	}

	public String getSalaryInAdvTot() {
		return salaryInAdvTot;
	}

	public void setSalaryInAdvTot(String salaryInAdvTot) {
		this.salaryInAdvTot = salaryInAdvTot;
	}

	public String getSalaryInAdvMon() {
		return salaryInAdvMon;
	}

	public void setSalaryInAdvMon(String salaryInAdvMon) {
		this.salaryInAdvMon = salaryInAdvMon;
	}

	public String getFixedPerdiemAmount() {
		return fixedPerdiemAmount;
	}

	public void setFixedPerdiemAmount(String fixedPerdiemAmount) {
		this.fixedPerdiemAmount = fixedPerdiemAmount;
	}

	public Date getFixedPerdiemFrom() {
		return fixedPerdiemFrom;
	}

	public void setFixedPerdiemFrom(Date fixedPerdiemFrom) {
		this.fixedPerdiemFrom = fixedPerdiemFrom;
	}

	public Date getFixedPerdiemTo() {
		return fixedPerdiemTo;
	}

	public void setFixedPerdiemTo(Date fixedPerdiemTo) {
		this.fixedPerdiemTo = fixedPerdiemTo;
	}

	public String getFixedPerdiemCurrencyType() {
		return fixedPerdiemCurrencyType;
	}

	public void setFixedPerdiemCurrencyType(String fixedPerdiemCurrencyType) {
		this.fixedPerdiemCurrencyType = fixedPerdiemCurrencyType;
	}

	public String getPerdiemInAdvTot() {
		return perdiemInAdvTot;
	}

	public void setPerdiemInAdvTot(String perdiemInAdvTot) {
		this.perdiemInAdvTot = perdiemInAdvTot;
	}

	public String getPerdiemInAdvMon() {
		return perdiemInAdvMon;
	}

	public void setPerdiemInAdvMon(String perdiemInAdvMon) {
		this.perdiemInAdvMon = perdiemInAdvMon;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getRetentionBonus() {
		return retentionBonus;
	}

	public void setRetentionBonus(String retentionBonus) {
		this.retentionBonus = retentionBonus;
	}

	public String getJoiningBonus() {
		return joiningBonus;
	}

	public void setJoiningBonus(String joiningBonus) {
		this.joiningBonus = joiningBonus;
	}

	public String getPerdiemInBalTot() {
		return perdiemInBalTot;
	}

	public void setPerdiemInBalTot(String perdiemInBalTot) {
		this.perdiemInBalTot = perdiemInBalTot;
	}

	public String getPaidMonth() {
		return paidMonth;
	}

	public void setPaidMonth(String paidMonth) {
		this.paidMonth = paidMonth;
	}

	public String getSalaryPaidMonth() {
		return salaryPaidMonth;
	}

	public void setSalaryPaidMonth(String salaryPaidMonth) {
		this.salaryPaidMonth = salaryPaidMonth;
	}

	
	private float		totalCtc;

	public float getTotalCtc() {
		return totalCtc;
	}

	public void setTotalCtc(float totalCtc) {
		this.totalCtc = totalCtc;
	}
	
	// new Field added here for deduction status by venkat
	
	public String getSalaryAdvToBeDeducted() {
		return salaryAdvToBeDeducted;
	}

	public void setSalaryAdvToBeDeducted(String salaryAdvToBeDeducted) {
		this.salaryAdvToBeDeducted = salaryAdvToBeDeducted;
	}
	//----------------------------//

//Travel Advance Code start here written by venkat


private String		travelInAdvTot;
private String		travelInAdvMon;

private String		travelInAdvBalance;
private String		travelInAdvPaid;
private String		travelPaidMonth;

private String		travelInNoOfMonths;
private String      travelDeductionStatus;

public String getTravelInAdvTot() {
	return travelInAdvTot;
}

public void setTravelInAdvTot(String travelInAdvTot) {
	this.travelInAdvTot = travelInAdvTot;
}

public String getTravelInAdvMon() {
	return travelInAdvMon;
}

public void setTravelInAdvMon(String travelInAdvMon) {
	this.travelInAdvMon = travelInAdvMon;
}

public String getTravelInAdvBalance() {
	return travelInAdvBalance;
}

public void setTravelInAdvBalance(String travelInAdvBalance) {
	this.travelInAdvBalance = travelInAdvBalance;
}

public String getTravelInAdvPaid() {
	return travelInAdvPaid;
}

public void setTravelInAdvPaid(String travelInAdvPaid) {
	this.travelInAdvPaid = travelInAdvPaid;
}

public String getTravelInNoOfMonths() {
	return travelInNoOfMonths;
}

public void setTravelInNoOfMonths(String travelInNoOfMonths) {
	this.travelInNoOfMonths = travelInNoOfMonths;
}

public String getTravelDeductionStatus() {
	return travelDeductionStatus;
}

public void setTravelDeductionStatus(String travelDeductionStatus) {
	this.travelDeductionStatus = travelDeductionStatus;
}

public String getTravelPaidMonth() {
	return travelPaidMonth;
}

public void setTravelPaidMonth(String travelPaidMonth) {
	this.travelPaidMonth = travelPaidMonth;
}





}


