package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.dikshatech.portal.forms.PortalForm;

public class ActiveChargeCode extends PortalForm implements Serializable {

	protected int empId;

	protected String name;

	protected String project;

	protected String chargeCode;

	protected Date startDate;

	protected Date endDate;

	protected String poNo;

	protected int rate;

	protected String currency;

	protected String poStatus;

	protected String projectType;

	protected int projType;
	
	protected int poId;
	
	protected int poEmapId;
	
	

	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
	}

	public ActiveChargeCode() {
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

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public int getProjType() {
		return projType;
	}

	public void setProjType(int projType) {
		this.projType = projType;
	}
	
	

	public int getPoEmapId() {
		return poEmapId;
	}

	public void setPoEmapId(int poEmapId) {
		this.poEmapId = poEmapId;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.Project: ");
		ret.append("empId=" + empId);
		ret.append(", current=" + currency);
		ret.append(", projectType=" + projectType);
		ret.append(", poId=" + poId);
		ret.append(", poEmapId=" + poEmapId);

		return ret.toString();
	}

	public Map<String, Object> toMap(int i) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("empId", empId);
		map.put("name", name);
		map.put("project", project);
		map.put("chargeCode", chargeCode);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("poNo", poNo);
		map.put("rate", rate);
		map.put("currency", currency);
		map.put("poStatus", poStatus);
		map.put("projectType", projectType);
		map.put("poId", poId);
		map.put("poEmapId", poEmapId);

		return map;
	}
	
	
	
	

}
