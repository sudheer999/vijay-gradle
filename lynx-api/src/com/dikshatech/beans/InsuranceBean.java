package com.dikshatech.beans;

import java.io.Serializable;

import com.dikshatech.portal.dto.InsurancePolicyDetails;
import com.dikshatech.portal.dto.InsurancePolicyReq;
import com.dikshatech.portal.forms.PortalForm;

public class InsuranceBean extends PortalForm implements Serializable {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= 1L;
	// InsurancePolicyReq fields
	private int							id;
	private int							esrMapId;
	private String						requestId;
	private String						status;
	private int							requeterId;
	private String						policyNumber;
	private String						cardId				= "-";
	private String						policyType;
	private String						coverage;
	private String						coverageFrom;
	private String						coverageUpto;
	private String						comments;
	private String						deliveryAddress;
	private String						basicPremium;
	private String						totalPremium;
	private String						serviceTax;
	private String						requestedOn;
	private String						dateOfCompletion;
	// InsurancePolicyDetails fields
	private String						name;
	private String						gender;
	private String						dob;
	private String						age;
	private String						relationship;
	private int							noOfPolicies;
	private String						saveType;
	private String						requestedBy;
	private int							noOfInsPolicyDtl;
	private InsurancePolicyReq[]		insPolicyReqBean;
	private String						policyHolderName;
	private int							statusId;
	private boolean						isAssigned;
	private InsurancePolicyDetails[]	insPolicydtlBean;
	private HandlersListBean[]			hndSiblingList;
	private String						assignedHndName;
	private String						approvedReqIds;
	private String						rejectedReqIds;

	public void setApprovedReqIds(String approvedReqIds) {
		this.approvedReqIds = approvedReqIds;
	}

	public String getApprovedReqIds() {
		return this.approvedReqIds;
	}

	public void setRejectedReqIds(String rejectedReqIds) {
		this.rejectedReqIds = rejectedReqIds;
	}

	public String getRejectedReqIds() {
		return this.rejectedReqIds;
	}

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

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRequeterId() {
		return requeterId;
	}

	public void setRequeterId(int requeterId) {
		this.requeterId = requeterId;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public String getCoverageFrom() {
		return coverageFrom;
	}

	public void setCoverageFrom(String coverageFrom) {
		this.coverageFrom = coverageFrom;
	}

	public String getCoverageUpto() {
		return coverageUpto;
	}

	public void setCoverageUpto(String coverageUpto) {
		this.coverageUpto = coverageUpto;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getBasicPremium() {
		return basicPremium;
	}

	public void setBasicPremium(String basicPremium) {
		this.basicPremium = basicPremium;
	}

	public String getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(String totalPremium) {
		this.totalPremium = totalPremium;
	}

	public String getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}

	public String getRequestedOn() {
		return requestedOn;
	}

	public void setRequestedOn(String requestedOn) {
		this.requestedOn = requestedOn;
	}

	public String getDateOfCompletion() {
		return dateOfCompletion;
	}

	public void setDateOfCompletion(String dateOfCompletion) {
		this.dateOfCompletion = dateOfCompletion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public int getNoOfPolicies() {
		return noOfPolicies;
	}

	public void setNoOfPolicies(int noOfPolicies) {
		this.noOfPolicies = noOfPolicies;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public int getNoOfInsPolicyDtl() {
		return noOfInsPolicyDtl;
	}

	public void setNoOfInsPolicyDtl(int noOfInsPolicyDtl) {
		this.noOfInsPolicyDtl = noOfInsPolicyDtl;
	}

	public InsurancePolicyReq[] getInsPolicyReqBean() {
		return insPolicyReqBean;
	}

	public void setInsPolicyReqBean(InsurancePolicyReq[] insPolicyReqBean) {
		this.insPolicyReqBean = insPolicyReqBean;
	}

	public String getPolicyHolderName() {
		return policyHolderName;
	}

	public void setPolicyHolderName(String policyHolderName) {
		this.policyHolderName = policyHolderName;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public boolean getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	public InsurancePolicyDetails[] getInsPolicydtlBean() {
		return insPolicydtlBean;
	}

	public void setInsPolicydtlBean(InsurancePolicyDetails[] insPolicydtlBean) {
		this.insPolicydtlBean = insPolicydtlBean;
	}

	public HandlersListBean[] getHndSiblingList() {
		return hndSiblingList;
	}

	public void setHndSiblingList(HandlersListBean[] hndSiblingList) {
		this.hndSiblingList = hndSiblingList;
	}

	public String getAssignedHndName() {
		return assignedHndName;
	}

	public void setAssignedHndName(String assignedHndName) {
		this.assignedHndName = assignedHndName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		if (cardId != null) this.cardId = cardId;
	}
}