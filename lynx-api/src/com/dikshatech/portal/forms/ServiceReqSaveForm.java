package com.dikshatech.portal.forms;

import java.util.Date;

import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.ServiceReqInfo;

public class ServiceReqSaveForm extends PortalForm 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/* this is for service req info  fields to be filled by Requestor  */

	
	protected String status;
	protected String summary;
	protected String description;
	protected String comment;

/* this is for EMP_REQ_  fields to be filled by Requestor  */


	protected String reqId;
	protected int reqTypeId;
	protected int regionId;
	protected int requestorId;
	protected Date subDate;

/*Getter and setter  methods for service req info*/
	

public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getSummary() {
	return summary;
}
public void setSummary(String summary) {
	this.summary = summary;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

public String getComment() {
	return comment;
}
public void setComment(String comment) {
	this.comment = comment;
}

/* getter and setter method for EMP_SER_REQ request*/

public String getReqId() {
	return reqId;
}
public void setReqId(String reqId) {
	this.reqId = reqId;
}
public int getReqTypeId() {
	return reqTypeId;
}
public void setReqTypeId(int reqTypeId) {
	this.reqTypeId = reqTypeId;
}
public int getRegionId() {
	return regionId;
}
public void setRegionId(int regionId) {
	this.regionId = regionId;
}
public int getRequestorId() {
	return requestorId;
}
public void setRequestorId(int requestorId) {
	this.requestorId = requestorId;
}
public Date getSubDate() {
	return subDate;
}
public void setSubDate(Date subDate) {
	this.subDate = subDate;
}

private ServiceReqInfo serviceReqInfo;
private EmpSerReqMap empSerReqMap;


public ServiceReqSaveForm(){
	setServiceReqInfo();
	setEmpSerReqMap();
	
}


public ServiceReqInfo getServiceReqInfo() {
	return serviceReqInfo;
}

public void setServiceReqInfo() 
{
	serviceReqInfo=new ServiceReqInfo();
	
	serviceReqInfo.setStatus(status);
	serviceReqInfo.setSummary(summary);
	serviceReqInfo.setDescription(description);
	serviceReqInfo.setComment(comment);
	
	this.serviceReqInfo=serviceReqInfo;
}
public void setServiceReqInfo(ServiceReqInfo serviceReqInfo)
{
	this.serviceReqInfo = serviceReqInfo;
}



public EmpSerReqMap getEmpSerReqMap() {
	return empSerReqMap;
}

public void setEmpSerReqMap(EmpSerReqMap empSerReqMap)
{
	
this.empSerReqMap=empSerReqMap;
}
public void setEmpSerReqMap() 
{
	empSerReqMap=new  EmpSerReqMap();
	
	/* fields to be filled here*/
	
	empSerReqMap.setRegionId(regionId);
	empSerReqMap.setReqId(reqId);
	
	empSerReqMap.setRequestorId(requestorId);
	empSerReqMap.setSubDate(subDate);
	
	
	
	
	this.empSerReqMap=empSerReqMap;
}
}


