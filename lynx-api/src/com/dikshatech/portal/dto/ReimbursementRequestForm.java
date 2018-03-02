package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;
import com.dikshatech.portal.forms.PortalForm;

public class ReimbursementRequestForm extends PortalForm implements Serializable
{
	private static final long	serialVersionUID	= 1L;

	protected int				id;
	// Fields belongs to Reimbursement_Req
	protected int				esrMapId;
	protected int				projectCode;
	protected String			projectName;
	protected int				chargeCode;
	protected String			ccTitle;
	protected String			description;
	protected int				assignTo;
	protected short			    active;
	protected String			oldStatus;
	protected String			status;
	protected String			remark;
	protected String			messageBody;
	protected int				requesterId;
	protected Date				createDate;
	protected int actionTakenBy;
	protected Date actionTakenOn;

	// Fields belongs to Reimbursement_FinancialData
	protected int reimbursementId;
	protected Date dateOfOccurrence;
	protected String summary;
	protected String type;
	protected int amount;
	protected String currency;
	protected String receiptsAvailable;
	protected String uploadReceiptsName;
	protected String				reqId;
	
	//field added for uploading file
	protected int docId;
	
	
	//Inputs from UI :financialData=dateOfOccurrence:summary:type:amount:currency:receiptsAvailable:receiptId
	protected String financialData[];
	
	
	//field of service req info 
	protected int assignedToDiv;
	protected int actionBy;
	protected String hdEstDateResolve;
	protected String hdComments;
	protected int escalateTo;
	protected String hRemarks;
	protected Date creationDatetime;
	protected String estDateResolve;
	protected String comment;
	protected int depServReq;
	protected int assignedTo;
	protected String severity;
	protected String priority;
	
	protected String reimbuFlag;
	protected String paymentMadeToFlag;
	
	protected int paymentMadeToEmpId;
	protected int OTHER_EMP_NAME;
	
	private String companyName;

	private int regionId;

	private String regName;

	private int departmentId;

	private String departmentName;

	private int divisionId;

	private String divisionName;

	private String paid;
	
private String		employeeName;
	

protected   String				remesrMapId;
	






	public String getRemesrMapId() {
	return remesrMapId;
}

public void setRemesrMapId(String remesrMapId) {
	this.remesrMapId = remesrMapId;
}

	private int		empId;
	
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

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	//for both save and submit
	protected boolean saveSubmit;
	
	
	public ReimbursementRequestForm()
	{
		
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getEsrMapId()
	{
		return esrMapId;
		
	}

	public void setEsrMapId(int esrMapId)
	{
		this.esrMapId = esrMapId;
	}

	public int getProjectCode()
	{
		return projectCode;
	}

	public void setProjectCode(int projectCode)
	{
		this.projectCode = projectCode;
	}

	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	public int getChargeCode()
	{
		return chargeCode;
	}

	public void setChargeCode(int chargeCode)
	{
		this.chargeCode = chargeCode;
	}

	public String getCcTitle()
	{
		return ccTitle;
	}

	public void setCcTitle(String ccTitle)
	{
		this.ccTitle = ccTitle;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getAssignTo()
	{
		return assignTo;
	}

	public void setAssignTo(int assignTo)
	{
		this.assignTo = assignTo;
	}

	public short getActive()
	{
		return active;
	}

	public void setActive(short active)
	{
		this.active = active;
	}

	public String getOldStatus()
	{
		return oldStatus;
	}

	public void setOldStatus(String oldStatus)
	{
		this.oldStatus = oldStatus;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getMessageBody()
	{
		return messageBody;
	}

	public void setMessageBody(String messageBody)
	{
		this.messageBody = messageBody;
	}

	public int getRequesterId()
	{
		return requesterId;
	}

	public void setRequesterId(int requesterId)
	{
		this.requesterId = requesterId;
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public int getReimbursementId()
	{
		return reimbursementId;
	}

	public void setReimbursementId(int reimbursementId)
	{
		this.reimbursementId = reimbursementId;
	}

	public Date getDateOfOccurrence()
	{
		return dateOfOccurrence;
	}

	public void setDateOfOccurrence(Date dateOfOccurrence)
	{
		this.dateOfOccurrence = dateOfOccurrence;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getReceiptsAvailable()
	{
		return receiptsAvailable;
	}

	public void setReceiptsAvailable(String receiptsAvailable)
	{
		this.receiptsAvailable = receiptsAvailable;
	}

	public String getUploadReceiptsName()
	{
		return uploadReceiptsName;
	}

	public void setUploadReceiptsName(String uploadReceiptsName)
	{
		this.uploadReceiptsName = uploadReceiptsName;
	}

	public String[ ] getFinancialData()
	{
		return financialData;
	}

	public void setFinancialData(String[ ] financialData)
	{
		this.financialData = financialData;
	}

	public int getActionTakenBy()
	{
		return actionTakenBy;
	}

	public void setActionTakenBy(int actionTakenBy)
	{
		this.actionTakenBy = actionTakenBy;
	}

	public Date getActionTakenOn()
	{
		return actionTakenOn;
	}

	public void setActionTakenOn(Date actionTakenOn)
	{
		this.actionTakenOn = actionTakenOn;
	}

	public int getDocId()
	{
		return docId;
	}

	public void setDocId(int docId)
	{
		this.docId = docId;
	}

	public boolean isSaveSubmit()
	{
		return saveSubmit;
	}

	public void setSaveSubmit(boolean saveSubmit)
	{
		this.saveSubmit = saveSubmit;
	}
	//check reqid
	

	public int getAssignedToDiv()
	{
		return assignedToDiv;
	}



	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public void setAssignedToDiv(int assignedToDiv)
	{
		this.assignedToDiv = assignedToDiv;
	}

	public int getActionBy()
	{
		return actionBy;
	}

	public void setActionBy(int actionBy)
	{
		this.actionBy = actionBy;
	}

	public String getHdEstDateResolve()
	{
		return hdEstDateResolve;
	}

	public void setHdEstDateResolve(String hdEstDateResolve)
	{
		this.hdEstDateResolve = hdEstDateResolve;
	}

	public String getHdComments()
	{
		return hdComments;
	}

	public void setHdComments(String hdComments)
	{
		this.hdComments = hdComments;
	}

	public int getEscalateTo()
	{
		return escalateTo;
	}

	public void setEscalateTo(int escalateTo)
	{
		this.escalateTo = escalateTo;
	}

	public String gethRemarks()
	{
		return hRemarks;
	}

	public void sethRemarks(String hRemarks)
	{
		this.hRemarks = hRemarks;
	}

	public Date getCreationDatetime()
	{
		return creationDatetime;
	}

	public void setCreationDatetime(Date creationDatetime)
	{
		this.creationDatetime = creationDatetime;
	}

	public String getEstDateResolve()
	{
		return estDateResolve;
	}

	public void setEstDateResolve(String estDateResolve)
	{
		this.estDateResolve = estDateResolve;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public int getDepServReq()
	{
		return depServReq;
	}

	public void setDepServReq(int depServReq)
	{
		this.depServReq = depServReq;
	}

	public int getAssignedTo()
	{
		return assignedTo;
	}

	public void setAssignedTo(int assignedTo)
	{
		this.assignedTo = assignedTo;
	}

	public String getSeverity()
	{
		return severity;
	}

	public void setSeverity(String severity)
	{
		this.severity = severity;
	}

	public String getPriority()
	{
		return priority;
	}

	public void setPriority(String priority)
	{
		this.priority = priority;
	}

	public String getReimbuFlag() {
		return reimbuFlag;
		//return "OTHER";
	}

	public void setReimbuFlag(String reimbuFlag) {
		this.reimbuFlag = reimbuFlag;
		//this.reimbuFlag = "OTHER";
	}

	public String getPaymentMadeToFlag() {
		return paymentMadeToFlag;
	}

	public void setPaymentMadeToFlag(String paymentMadeToFlag) {
		this.paymentMadeToFlag = paymentMadeToFlag;
	}

	public int getPaymentMadeToEmpId() {
		return paymentMadeToEmpId;
	}

	public void setPaymentMadeToEmpId(int paymentMadeToEmpId) {
		this.paymentMadeToEmpId = paymentMadeToEmpId;
	}


	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public int getOTHER_EMP_NAME() {
		return OTHER_EMP_NAME;
	}

	public void setOTHER_EMP_NAME(int oTHER_EMP_NAME) {
		OTHER_EMP_NAME = oTHER_EMP_NAME;
	}
	
	private String bankFlag;

	public String getBankFlag() {
		return bankFlag;
	}

	public void setBankFlag(String bankFlag) {
		this.bankFlag = bankFlag;
	}
	
	private Object ListHdfc;
	
	private Object ListNonHdfc;

	public Object getListHdfc() {
		return ListHdfc;
	}

	public void setListHdfc(Object listHdfc) {
		ListHdfc = listHdfc;
	}

	public Object getListNonHdfc() {
		return ListNonHdfc;
	}

	public void setListNonHdfc(Object listNonHdfc) {
		ListNonHdfc = listNonHdfc;
	}
	protected String First_name;

	public String getFirst_name() {
		return First_name;
	}

	public void setFirst_name(String first_name) {
		First_name = first_name;
	}
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

	
}
