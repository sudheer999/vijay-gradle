package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Date;

public class ReferFriendBean implements Serializable
{
	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	protected int id;
	protected int esrMapId;
	protected String summary;
	protected int attachment;
	protected String referredTo;
	protected String department;
	protected String experienceLavel;
	protected int referredBy;
	protected String referredByName;
	protected String submissionDate;
	protected String ReqId;
	
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
	public String getSummary()
    {
    	return summary;
    }
	public void setSummary(String summary)
    {
    	this.summary = summary;
    }
	public int getAttachment()
    {
    	return attachment;
    }
	public void setAttachment(int attachment)
    {
    	this.attachment = attachment;
    }
	public String getReferredTo()
    {
    	return referredTo;
    }
	public void setReferredTo(String referredTo)
    {
    	this.referredTo = referredTo;
    }
	public String getDepartment()
    {
    	return department;
    }
	public void setDepartment(String department)
    {
    	this.department = department;
    }
	public String getExperienceLavel()
    {
    	return experienceLavel;
    }
	public void setExperienceLavel(String experienceLavel)
    {
    	this.experienceLavel = experienceLavel;
    }
	public int getReferredBy()
    {
    	return referredBy;
    }
	public void setReferredBy(int referredBy)
    {
    	this.referredBy = referredBy;
    }
	public String getReferredByName()
    {
    	return referredByName;
    }
	public void setReferredByName(String referredByName)
    {
    	this.referredByName = referredByName;
    }
	public String getSubmissionDate()
    {
    	return submissionDate;
    }
	public void setSubmissionDate(String submissionDate)
    {
    	this.submissionDate = submissionDate;
    }
	public String getReqId()
    {
    	return ReqId;
    }
	public void setReqId(String reqId)
    {
    	ReqId = reqId;
    }
	
}
