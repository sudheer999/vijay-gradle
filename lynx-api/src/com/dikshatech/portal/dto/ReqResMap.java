package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.List;

import com.dikshatech.portal.forms.PortalForm;

public class ReqResMap extends PortalForm implements Serializable{
	  protected int id;
	  protected int reqId;
	  protected int resourceId;
	  protected int statusId;
	  protected String status;
	  protected int closed;
	  protected String reqTypeId;
	 protected ResourceReqMapping resources;
	 protected ResourceReqMapHistory[] mapHistory;
		
	
	  
	
	
	public ResourceReqMapHistory[] getMapHistory() {
		return mapHistory;
	}
	public void setMapHistory(ResourceReqMapHistory[] mapHistory) {
		this.mapHistory = mapHistory;
	}
	public ResourceReqMapping getResources() {
		return resources;
	}
	public void setResources(ResourceReqMapping resources) {
		this.resources = resources;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReqId() {
		return reqId;
	}
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getClosed() {
		return closed;
	}
	public void setClosed(int closed) {
		this.closed = closed;
	}
	  
	  
	public String getReqTypeId() {
		return reqTypeId;
	}
	public void setReqTypeId(String reqTypeId) {
		this.reqTypeId = reqTypeId;
	}
	public ReqResMapPk createPk() {
		return new ReqResMapPk(id);
	}
}
