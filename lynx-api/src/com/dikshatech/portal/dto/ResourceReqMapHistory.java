package com.dikshatech.portal.dto;

import java.sql.Date;

public class ResourceReqMapHistory {
	protected int id;
	protected int resMapId;
	protected String resourceName;
	protected int totalExp;
	protected	int relevantExp;
	protected String currentEmployer;
	protected String currentRole;
	protected double ctc;
	protected double ectc;
	protected String noticePeriod;
	protected String comments;
	protected int modifiedBy;  
	protected Date modifiedOn;
	protected String modifiedByName;
	public String getModifiedByName() {
		return modifiedByName;
	}
	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getResMapId() {
		return resMapId;
	}
	public void setResMapId(int resMapId) {
		this.resMapId = resMapId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public int getTotalExp() {
		return totalExp;
	}
	public void setTotalExp(int totalExp) {
		this.totalExp = totalExp;
	}
	public int getRelevantExp() {
		return relevantExp;
	}
	public void setRelevantExp(int relevantExp) {
		this.relevantExp = relevantExp;
	}
	public String getCurrentEmployer() {
		return currentEmployer;
	}
	public void setCurrentEmployer(String currentEmployer) {
		this.currentEmployer = currentEmployer;
	}
	public String getCurrentRole() {
		return currentRole;
	}
	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
	}
	public double getCtc() {
		return ctc;
	}
	public void setCtc(double ctc) {
		this.ctc = ctc;
	}
	public double getEctc() {
		return ectc;
	}
	public void setEctc(double ectc) {
		this.ectc = ectc;
	}
	public String getNoticePeriod() {
		return noticePeriod;
	}
	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	
	public ResourceReqMapHistoryPk createPk() {
		return new ResourceReqMapHistoryPk(id);
	}

}
