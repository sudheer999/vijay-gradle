/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.dikshatech.beans.RollOnOffBean;
import com.dikshatech.portal.forms.PortalForm;

public class RollOn extends PortalForm implements Serializable {

	/**
	 * This attribute maps to the column ID in the ROLL_ON table.
	 */
	protected int					id;
	/**
	 * This attribute maps to the column EMP_ID in the ROLL_ON table.
	 */
	protected int					empId;
	/**
	 * This attribute maps to the column START_DATE in the ROLL_ON table.
	 */
	protected Date					startDate;
	/**
	 * This attribute maps to the column END_DATE in the ROLL_ON table.
	 */
	protected Date					endDate;
	/**
	 * This attribute maps to the column REPORT_DT in the ROLL_ON table.
	 */
	protected Date					reportDt;
	/**
	 * This attribute maps to the column REPORT_TM in the ROLL_ON table.
	 */
	protected Date					reportTm;
	/**
	 * This attribute maps to the column PERDIEM in the ROLL_ON table.
	 */
	protected String				perdiem;
	/**
	 * This attribute maps to the column CH_CODE_ID in the ROLL_ON table.
	 */
	protected Integer				chCodeId;
	/**
	 * This attribute represents whether the primitive attribute chCodeId is null.
	 */
	protected boolean				chCodeIdNull		= true;
	/**
	 * This attribute maps to the column PAYMENT_TERM in the ROLL_ON table.
	 */
	protected String				paymentTerm;
	/**
	 * This attribute maps to the column CURRENCY in the ROLL_ON table.
	 */
	protected String				currency;
	/**
	 * This attribute maps to the column CURRENT in the ROLL_ON table.
	 */
	protected short					current;
	/**
	 * This attribute maps to the column RAISEDBY in the ROLL_ON table.
	 */
	protected int					raisedBy;
	/**
	 * This attribute maps to the column MESSAGEBODY in the ROLL_ON table.
	 */
	protected String				messageBody;
	/**
	 * This attribute maps to the column ESR_MAP_ID in the REIMBURSEMENT table.
	 */
	protected int					esrqmId;
	/**
	 * This attribute maps to the column CREATE_DATE in the ROLL_ON table.
	 */
	protected Date					createDate;
	/**
	 * This attribute maps to the column TRAVEL_REQ_FLAG in the ROLL_ON table.
	 */
	protected int					travelReqFlag;
	/**
	 * This attribute represents whether the primitive attribute travelReqFlag is null.
	 */
	protected boolean				travelReqFlagNull	= true;
	private String					comments;
	private String					mgrPhoneNo;
	/**
	 * This attribute maps to the column NOTIFIERS in the ROLL_ON table.
	 */
	protected String				notifiers;
	protected int					managerId;
	protected int					projId;
	protected String				empName;
	protected String				deptName;
	protected String				designation;
	protected Set<RollOnOffBean>	rollOnDetails;
	protected String				projName;
	protected ProjLocations[]		projLocs;
	protected ProjContInfo[]		projContacts;
	protected int					deptId;
	private int						regionId;
	private String					regName;
	private String					managerName;
	private String					receiverEmailId;
	private String					mgrEmailId;
	private String					mgrDetails;
	private String					onSiteMgr;
	private String					onSiteMgrDetail;
	private Set<ProjLocations>		projLoc;
	private String					reportingTime;
	private String					clientName;
	private String					chargeCode;
	private String					chargeCodeTitle;
	private String					lastName;
	private String					empDiv;
	private boolean					isRollOn;
	private Object[]				profileinfobeans;
	private String					notifierName;
	private String[]				CCnotifierMailIds;
	private String					clientLocation;
	private String					clientOfficeAddress;
	private String[]				multiRollOn;											//for MULTIPLEROLLON
	private Integer					projectLocationId;										//used in MULTIPLEROLLON
	private short					empInactiveInfo;										//indicates if the employee's chargeChode was made incative.... this can happen only during multiple rollon
	private String					dikshaReportingManager;
	private String					dikshaRmMailId;
	private Map<String, Object>		map					= new HashMap<String, Object>();
	private String 					perdiemOffered;
	private String					candidatePerdiemDetails;			//CANDIDATE_PERDIEM_DETAILS format followed   locationName1~=~perdiemDetails~;~locationNAme2~=~perdiemDetails2
	private String	                projectType;
	private Date 					rollOffDate;
	
	
	public Date getRollOffDate() {
		return rollOffDate;
	}

	public void setRollOffDate(Date rollOffDate) {
		this.rollOffDate = rollOffDate;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getCandidatePerdiemDetails() {
		return candidatePerdiemDetails;
	}

	public void setCandidatePerdiemDetails(String candidatePerdiemDetails) {
		this.candidatePerdiemDetails = candidatePerdiemDetails;
	}

	public String getPerdiemOffered() {
		return perdiemOffered;
	}

	public void setPerdiemOffered(String perdiemOffered) {
		this.perdiemOffered = perdiemOffered;
	}

	public String[] getCCnotifierMailIds() {
		return CCnotifierMailIds;
	}

	public void setCCnotifierMailIds(String[] cCnotifierMailIds) {
		CCnotifierMailIds = cCnotifierMailIds;
	}

	public String getNotifierName() {
		return notifierName;
	}

	public void setNotifierName(String notifierName) {
		this.notifierName = notifierName;
	}

	public String getClientLocation() {
		return clientLocation;
	}

	public void setClientLocation(String clientLocation) {
		this.clientLocation = clientLocation;
	}

	public String getClientOfficeAddress() {
		return clientOfficeAddress;
	}

	public void setClientOfficeAddress(String clientOfficeAddress) {
		this.clientOfficeAddress = clientOfficeAddress;
	}

	/**
	 * Method 'RollOn'
	 */
	public RollOn() {}

	public String getMgrEmailId() {
		return mgrEmailId;
	}

	public void setMgrEmailId(String mgrEmailId) {
		this.mgrEmailId = mgrEmailId;
	}

	public String getEmpDiv() {
		return empDiv;
	}

	public void setEmpDiv(String empDiv) {
		this.empDiv = empDiv;
	}

	public int getRaisedBy() {
		return raisedBy;
	}

	public void setRaisedBy(int raisedBy) {
		this.raisedBy = raisedBy;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

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
	 * Method 'getEmpId'
	 * 
	 * @return int
	 */
	public int getEmpId() {
		return empId;
	}

	/**
	 * Method 'setEmpId'
	 * 
	 * @param empId
	 */
	public void setEmpId(int empId) {
		this.empId = empId;
	}

	/**
	 * Method 'getStartDate'
	 * 
	 * @return Date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Method 'setStartDate'
	 * 
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Method 'getEndDate'
	 * 
	 * @return Date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Method 'setEndDate'
	 * 
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Method 'getReportDt'
	 * 
	 * @return Date
	 */
	public Date getReportDt() {
		return reportDt;
	}

	/**
	 * Method 'setReportDt'
	 * 
	 * @param reportDt
	 */
	public void setReportDt(Date reportDt) {
		this.reportDt = reportDt;
	}

	/**
	 * Method 'getReportTm'
	 * 
	 * @return Date
	 */
	public Date getReportTm() {
		return reportTm;
	}

	/**
	 * Method 'setReportTm'
	 * 
	 * @param reportTm
	 */
	public void setReportTm(Time reportTm) {
		this.reportTm = reportTm;
	}

	/**
	 * Method 'getPerdiem'
	 * 
	 * @return String
	 */
	public String getPerdiem() {
		return perdiem;
	}

	/**
	 * Method 'setPerdiem'
	 * 
	 * @param perdiem
	 */
	public void setPerdiem(String perdiem) {
		this.perdiem = perdiem;
	}

	/**
	 * Method 'getChCodeId'
	 * 
	 * @return int
	 */
	public Integer getChCodeId() {
		return chCodeId;
	}

	/**
	 * Method 'setChCodeId'
	 * 
	 * @param chCodeId
	 */
	public void setChCodeId(Integer chCodeId) {
		this.chCodeId = chCodeId;
		this.chCodeIdNull = false;
	}

	/**
	 * Method 'setChCodeIdNull'
	 * 
	 * @param value
	 */
	public void setChCodeIdNull(boolean value) {
		this.chCodeIdNull = value;
	}

	/**
	 * Method 'isChCodeIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isChCodeIdNull() {
		return chCodeIdNull;
	}

	/**
	 * Method 'getPaymentTerm'
	 * 
	 * @return String
	 */
	public String getPaymentTerm() {
		return paymentTerm;
	}

	/**
	 * Method 'setPaymentTerm'
	 * 
	 * @param paymentTerm
	 */
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	/**
	 * Method 'getCurrency'
	 * 
	 * @return String
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Method 'setCurrency'
	 * 
	 * @param currency
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * Method 'getCurrent'
	 * 
	 * @return short
	 */
	public short getCurrent() {
		return current;
	}

	/**
	 * Method 'setCurrent'
	 * 
	 * @param current
	 */
	public void setCurrent(short current) {
		this.current = current;
	}

	/**
	 * Method 'getEsrqmId'
	 * 
	 * @return int
	 */
	public int getEsrqmId() {
		return esrqmId;
	}

	/**
	 * Method 'setEsrqmId'
	 * 
	 * @param esrqmId
	 */
	public void setEsrqmId(int esrqmId) {
		this.esrqmId = esrqmId;
	}

	/**
	 * Method 'getCreateDate'
	 * 
	 * @return Date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Method 'setCreateDate'
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public int getProjId() {
		return projId;
	}

	public void setProjId(int projId) {
		this.projId = projId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Set<RollOnOffBean> getRollOnDetails() {
		return rollOnDetails;
	}

	public void setRollOnDetails(Set<RollOnOffBean> rollOnDetails) {
		this.rollOnDetails = rollOnDetails;
	}

	public String getProjName() {
		return projName;
	}

	public String getOnSiteMgr() {
		return onSiteMgr;
	}

	public void setOnSiteMgr(String onSiteMgr) {
		this.onSiteMgr = onSiteMgr;
	}

	public String getOnSiteMgrDetail() {
		return onSiteMgrDetail;
	}

	public void setOnSiteMgrDetail(String onSiteMgrDetail) {
		this.onSiteMgrDetail = onSiteMgrDetail;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public ProjLocations[] getProjLocs() {
		return projLocs;
	}

	public void setProjLocs(ProjLocations[] projLocs) {
		this.projLocs = projLocs;
	}

	public ProjContInfo[] getProjContacts() {
		return projContacts;
	}

	public void setProjContacts(ProjContInfo[] projContacts) {
		this.projContacts = projContacts;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
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

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getMgrDetails() {
		return mgrDetails;
	}

	public void setMgrDetails(String mgrDetails) {
		this.mgrDetails = mgrDetails;
	}

	public Set<ProjLocations> getProjLoc() {
		return projLoc;
	}

	public void setProjLoc(Set<ProjLocations> projLoc) {
		this.projLoc = projLoc;
	}

	public String getReportingTime() {
		return reportingTime;
	}

	public void setReportingTime(String reportingTime) {
		this.reportingTime = reportingTime;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setReportTm(Date reportTm) {
		this.reportTm = reportTm;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public String getChargeCodeTitle() {
		return chargeCodeTitle;
	}

	public void setChargeCodeTitle(String chargeCodeTitle) {
		this.chargeCodeTitle = chargeCodeTitle;
	}

	public String getReceiverEmailId() {
		return receiverEmailId;
	}

	public void setReceiverEmailId(String receiverEmailId) {
		this.receiverEmailId = receiverEmailId;
	}

	public String getLastName() {
		return lastName;
	}

	public boolean isRollOn() {
		return isRollOn;
	}

	public void setRollOn(boolean isRollOn) {
		this.isRollOn = isRollOn;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Object[] getProfileinfobeans() {
		return profileinfobeans;
	}

	public void setProfileinfobeans(Object[] profileinfobeans) {
		this.profileinfobeans = profileinfobeans;
	}

	/**
	 * Method 'getTravelReqFlag'
	 * 
	 * @return int
	 */
	public int getTravelReqFlag() {
		return travelReqFlag;
	}

	/**
	 * Method 'setTravelReqFlag'
	 * 
	 * @param travelReqFlag
	 */
	public void setTravelReqFlag(int travelReqFlag) {
		this.travelReqFlag = travelReqFlag;
		this.travelReqFlagNull = false;
	}

	/**
	 * Method 'setTravelReqFlagNull'
	 * 
	 * @param value
	 */
	public void setTravelReqFlagNull(boolean value) {
		this.travelReqFlagNull = value;
	}

	/**
	 * Method 'isTravelReqFlagNull'
	 * 
	 * @return boolean
	 */
	public boolean isTravelReqFlagNull() {
		return travelReqFlagNull;
	}

	/**
	 * Method 'getNotifiers'
	 * 
	 * @return String
	 */
	public String getNotifiers() {
		return notifiers;
	}

	/**
	 * Method 'setNotifiers'
	 * 
	 * @param notifiers
	 */
	public void setNotifiers(String notifiers) {
		this.notifiers = notifiers;
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
		if (!(_other instanceof RollOn)){ return false; }
		final RollOn _cast = (RollOn) _other;
		if (id != _cast.id){ return false; }
		if (empId != _cast.empId){ return false; }
		if (startDate == null ? _cast.startDate != startDate : !startDate.equals(_cast.startDate)){ return false; }
		if (endDate == null ? _cast.endDate != endDate : !endDate.equals(_cast.endDate)){ return false; }
		if (reportDt == null ? _cast.reportDt != reportDt : !reportDt.equals(_cast.reportDt)){ return false; }
		if (reportTm == null ? _cast.reportTm != reportTm : !reportTm.equals(_cast.reportTm)){ return false; }
		if (perdiem == null ? _cast.perdiem != perdiem : !perdiem.equals(_cast.perdiem)){ return false; }
		if (chCodeId != _cast.chCodeId){ return false; }
		if (chCodeIdNull != _cast.chCodeIdNull){ return false; }
		if (paymentTerm == null ? _cast.paymentTerm != paymentTerm : !paymentTerm.equals(_cast.paymentTerm)){ return false; }
		if (currency == null ? _cast.currency != currency : !currency.equals(_cast.currency)){ return false; }
		if (current != _cast.current){ return false; }
		/*if (raisedBy == null ? _cast.raisedBy != raisedBy : !raisedBy.equals( _cast.raisedBy )) {
			return false;
		}*/
		if (raisedBy != _cast.raisedBy){ return false; }
		if (messageBody == null ? _cast.messageBody != messageBody : !messageBody.equals(_cast.messageBody)){ return false; }
		if (esrqmId != _cast.esrqmId){ return false; }
		if (createDate == null ? _cast.createDate != createDate : !createDate.equals(_cast.createDate)){ return false; }
		if (travelReqFlag != _cast.travelReqFlag){ return false; }
		if (travelReqFlagNull != _cast.travelReqFlagNull){ return false; }
		if (notifiers == null ? _cast.notifiers != notifiers : !notifiers.equals(_cast.notifiers)){ return false; }
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
		_hashCode = 29 * _hashCode + empId;
		if (startDate != null){
			_hashCode = 29 * _hashCode + startDate.hashCode();
		}
		if (endDate != null){
			_hashCode = 29 * _hashCode + endDate.hashCode();
		}
		if (reportDt != null){
			_hashCode = 29 * _hashCode + reportDt.hashCode();
		}
		if (reportTm != null){
			_hashCode = 29 * _hashCode + reportTm.hashCode();
		}
		if (perdiem != null){
			_hashCode = 29 * _hashCode + perdiem.hashCode();
		}
		_hashCode = 29 * _hashCode + chCodeId;
		_hashCode = 29 * _hashCode + (chCodeIdNull ? 1 : 0);
		if (paymentTerm != null){
			_hashCode = 29 * _hashCode + paymentTerm.hashCode();
		}
		if (currency != null){
			_hashCode = 29 * _hashCode + currency.hashCode();
		}
		_hashCode = 29 * _hashCode + (int) current;
		/*if (raisedBy != null) {
			_hashCode = 29 * _hashCode + raisedBy.hashCode();
		}*/
		if (messageBody != null){
			_hashCode = 29 * _hashCode + messageBody.hashCode();
		}
		_hashCode = 29 * _hashCode + esrqmId;
		if (createDate != null){
			_hashCode = 29 * _hashCode + createDate.hashCode();
		}
		_hashCode = 29 * _hashCode + travelReqFlag;
		_hashCode = 29 * _hashCode + (travelReqFlagNull ? 1 : 0);
		if (notifiers != null){
			_hashCode = 29 * _hashCode + notifiers.hashCode();
		}
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return RollOnPk
	 */
	public RollOnPk createPk() {
		return new RollOnPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.RollOn: ");
		ret.append("id=" + id);
		ret.append(", empId=" + empId);
		ret.append(", startDate=" + startDate);
		ret.append(", endDate=" + endDate);
		ret.append(", reportDt=" + reportDt);
		ret.append(", reportTm=" + reportTm);
		ret.append(", perdiem=" + perdiem);
		ret.append(", chCodeId=" + chCodeId);
		ret.append(", paymentTerm=" + paymentTerm);
		ret.append(", currency=" + currency);
		ret.append(", current=" + current);
		ret.append(", raisedBy=" + raisedBy);
		ret.append(", messageBody=" + messageBody);
		ret.append(", esrqmId=" + esrqmId);
		ret.append(", createDate=" + createDate);
		ret.append(", travelReqFlag=" + travelReqFlag);
		ret.append(", notifiers=" + notifiers);
		return ret.toString();
	}

	/**
	 * @param multiRollOn
	 *            the multiRollOn to set
	 */
	public void setMultiRollOn(String[] multiRollOn) {
		this.multiRollOn = multiRollOn;
	}

	/**
	 * @return the multiRollOn
	 */
	public String[] getMultiRollOn() {
		return multiRollOn;
	}

	/**
	 * @param projectLocationId
	 *            the projectLocationId to set
	 */
	public void setProjectLocationId(Integer projectLocationId) {
		this.projectLocationId = projectLocationId;
	}

	/**
	 * @return the projectLocationId
	 */
	public Integer getProjectLocationId() {
		return projectLocationId;
	}

	/**
	 * @param empInactiveInfo
	 *            the empInactiveInfo to set
	 */
	public void setEmpInactiveInfo(short empInactiveInfo) {
		this.empInactiveInfo = empInactiveInfo;
	}

	/**
	 * @return the empInactiveInfo
	 */
	public short getEmpInactiveInfo() {
		return empInactiveInfo;
	}

	/**
	 * @param dikshaReportingManager
	 *            the dikshaReportingManager to set
	 */
	public void setDikshaReportingManager(String dikshaReportingManager) {
		this.dikshaReportingManager = dikshaReportingManager;
	}

	/**
	 * @return the dikshaReportingManager
	 */
	public String getDikshaReportingManager() {
		return dikshaReportingManager;
	}

	/**
	 * @param dikshaRmMailId
	 *            the dikshaRmMailId to set
	 */
	public void setDikshaRmMailId(String dikshaRmMailId) {
		this.dikshaRmMailId = dikshaRmMailId;
	}

	/**
	 * @return the dikshaRmMailId
	 */
	public String getDikshaRmMailId() {
		return dikshaRmMailId;
	}

	/**
	 * @param mgrPhoneNo
	 *            the mgrPhoneNo to set
	 */
	public void setMgrPhoneNo(String mgrPhoneNo) {
		this.mgrPhoneNo = mgrPhoneNo;
	}

	/**
	 * @return the mgrPhoneNo
	 */
	public String getMgrPhoneNo() {
		return mgrPhoneNo;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
