package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Date;
import com.dikshatech.portal.forms.PortalForm;

/**
 * @author gurunath.rokkam
 */
public class ExitEmployeeBean extends PortalForm implements Serializable, Cloneable {

	private int			id;
	private String		reason;
	private String[]	questions;
	private String		submit;
	private Date		lastWorkingDay;
	private int			noticePerid;
	private int			buyBack;
	private String		comments;
	private String		note;
	//for receive noc
	private String		type;
	//for fin noc
	private Date		salaryFrom;
	private Date		salaryTo;
	private Date		perdiemFrom;
	private Date		perdiemTo;
	private Date		lwpFrom;
	private Date		lwpTo;
	private float		pending;
	private float		salaryRecover;
	private float		agreementRecover;
	private float		due;
	private String		dueAccount;
	private float		chargedAmount;
	private String		chargedAccount;
	private Date		chargedOn;
	private String		remarks;
	// for admin noc
	private int			idCard;
	private int			lockerKeys;
	private int			studyMeterials;
	private int			insuranceCard;
	private float		failureAmount;
	private String		companyAssets;
	//for it noc
	private int			system;
	private int			accessories;
	private int			dissabledEmailId;
	private int			dissabledDomainId;
	private int			mobile;
	// for Questionaries
	private String		preventableSteps;
	private String		newEmpSituation;
	private String		responsibilities;
	private String		concerns;
	private String		recommendations;
	private String		rejoining;
	private String		workingWithUs;

	public int getId() {
		return id;
	}

	public String getReason() {
		return reason;
	}

	public String[] getQuestions() {
		return questions;
	}

	public String getSubmit() {
		return submit;
	}

	public Date getLastWorkingDay() {
		return lastWorkingDay;
	}

	public int getNoticePerid() {
		return noticePerid;
	}

	public int getBuyBack() {
		return buyBack;
	}

	public String getComments() {
		return comments;
	}

	public String getNote() {
		return note;
	}

	public Date getSalaryFrom() {
		return salaryFrom;
	}

	public Date getSalaryTo() {
		return salaryTo;
	}

	public Date getPerdiemFrom() {
		return perdiemFrom;
	}

	public Date getPerdiemTo() {
		return perdiemTo;
	}

	public Date getLwpFrom() {
		return lwpFrom;
	}

	public Date getLwpTo() {
		return lwpTo;
	}

	public float getPending() {
		return pending;
	}

	public float getSalaryRecover() {
		return salaryRecover;
	}

	public float getAgreementRecover() {
		return agreementRecover;
	}

	public float getDue() {
		return due;
	}

	public String getDueAccount() {
		return dueAccount;
	}

	public float getChargedAmount() {
		return chargedAmount;
	}

	public String getChargedAccount() {
		return chargedAccount;
	}

	public Date getChargedOn() {
		return chargedOn;
	}

	public String getRemarks() {
		return remarks;
	}

	public int getIdCard() {
		return idCard;
	}

	public int getLockerKeys() {
		return lockerKeys;
	}

	public int getStudyMeterials() {
		return studyMeterials;
	}

	public int getInsuranceCard() {
		return insuranceCard;
	}

	public float getFailureAmount() {
		return failureAmount;
	}

	public String getCompanyAssets() {
		return companyAssets;
	}

	public int getSystem() {
		return system;
	}

	public int getAccessories() {
		return accessories;
	}

	public int getDissabledEmailId() {
		return dissabledEmailId;
	}

	public int getDissabledDomainId() {
		return dissabledDomainId;
	}

	public int getMobile() {
		return mobile;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setQuestions(String[] questions) {
		this.questions = questions;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public void setLastWorkingDay(Date lastWorkingDay) {
		this.lastWorkingDay = lastWorkingDay;
	}

	public void setNoticePerid(int noticePerid) {
		this.noticePerid = noticePerid;
	}

	public void setBuyBack(int buyBack) {
		this.buyBack = buyBack;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setSalaryFrom(Date salaryFrom) {
		this.salaryFrom = salaryFrom;
	}

	public void setSalaryTo(Date salaryTo) {
		this.salaryTo = salaryTo;
	}

	public void setPerdiemFrom(Date perdiemFrom) {
		this.perdiemFrom = perdiemFrom;
	}

	public void setPerdiemTo(Date perdiemTo) {
		this.perdiemTo = perdiemTo;
	}

	public void setLwpFrom(Date lwpFrom) {
		this.lwpFrom = lwpFrom;
	}

	public void setLwpTo(Date lwpTo) {
		this.lwpTo = lwpTo;
	}

	public void setPending(float pending) {
		this.pending = pending;
	}

	public void setSalaryRecover(float salaryRecover) {
		this.salaryRecover = salaryRecover;
	}

	public void setAgreementRecover(float agreementRecover) {
		this.agreementRecover = agreementRecover;
	}

	public void setDue(float due) {
		this.due = due;
	}

	public void setDueAccount(String dueAccount) {
		this.dueAccount = dueAccount;
	}

	public void setChargedAmount(float chargedAmount) {
		this.chargedAmount = chargedAmount;
	}

	public void setChargedAccount(String chargedAccount) {
		this.chargedAccount = chargedAccount;
	}

	public void setChargedOn(Date chargedOn) {
		this.chargedOn = chargedOn;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setIdCard(int idCard) {
		this.idCard = idCard;
	}

	public void setLockerKeys(int lockerKeys) {
		this.lockerKeys = lockerKeys;
	}

	public void setStudyMeterials(int studyMeterials) {
		this.studyMeterials = studyMeterials;
	}

	public void setInsuranceCard(int insuranceCard) {
		this.insuranceCard = insuranceCard;
	}

	public void setFailureAmount(float failureAmount) {
		this.failureAmount = failureAmount;
	}

	public void setCompanyAssets(String companyAssets) {
		this.companyAssets = companyAssets;
	}

	public void setSystem(int system) {
		this.system = system;
	}

	public void setAccessories(int accessories) {
		this.accessories = accessories;
	}

	public void setDissabledEmailId(int dissabledEmailId) {
		this.dissabledEmailId = dissabledEmailId;
	}

	public void setDissabledDomainId(int dissabledDomainId) {
		this.dissabledDomainId = dissabledDomainId;
	}

	public void setMobile(int mobile) {
		this.mobile = mobile;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String getPreventableSteps() {
		return preventableSteps;
	}

	public void setPreventableSteps(String preventableSteps) {
		this.preventableSteps = preventableSteps;
	}

	public String getNewEmpSituation() {
		return newEmpSituation;
	}

	public void setNewEmpSituation(String newEmpSituation) {
		this.newEmpSituation = newEmpSituation;
	}

	public String getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}

	public String getConcerns() {
		return concerns;
	}

	public void setConcerns(String concerns) {
		this.concerns = concerns;
	}

	public String getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(String recommendations) {
		this.recommendations = recommendations;
	}

	public String getRejoining() {
		return rejoining;
	}

	public void setRejoining(String rejoining) {
		this.rejoining = rejoining;
	}

	public String getWorkingWithUs() {
		return workingWithUs;
	}

	public void setWorkingWithUs(String workingWithUs) {
		this.workingWithUs = workingWithUs;
	}
}
