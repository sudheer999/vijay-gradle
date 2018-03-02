package com.dikshatech.beans;


public class RequestIssueBean
{
	 
		protected int id;		
		protected String startDate;		
		protected String submissionDate;	
		protected String status;		
		protected String summary;		
		protected String description;		
		protected String estimationDate;		
		protected String autoReqId;		
		protected String dependentId;		
		protected int issueDepId;		
		protected int esrMapId;		
		protected int userId;
	    protected IssueBean[] issueBeanArr;		
		protected String  issueName;
		private boolean toHandle = false;
		protected String dateOfCompletion;
		protected String requestedBy;
		
		
		public int getId()
        {
        	return id;
        }
		public void setId(int id)
        {
        	this.id = id;
        }
		
		public String getStatus()
        {
        	return status;
        }
		public void setStatus(String status)
        {
        	this.status = status;
        }
		public String getSummary()
        {
        	return summary;
        }
		public void setSummary(String summary)
        {
        	this.summary = summary;
        }
		public String getDescription()
        {
        	return description;
        }
		public void setDescription(String description)
        {
        	this.description = description;
        }
		
		public String getAutoReqId()
        {
        	return autoReqId;
        }
		public void setAutoReqId(String autoReqId)
        {
        	this.autoReqId = autoReqId;
        }
		public String getDependentId()
        {
        	return dependentId;
        }
		public void setDependentId(String dependentId)
        {
        	this.dependentId = dependentId;
        }
		public int getIssueDepId()
        {
        	return issueDepId;
        }
		public void setIssueDepId(int issueDepId)
        {
        	this.issueDepId = issueDepId;
        }
		public int getEsrMapId()
        {
        	return esrMapId;
        }
		public void setEsrMapId(int esrMapId)
        {
        	this.esrMapId = esrMapId;
        }
		public int getUserId()
        {
        	return userId;
        }
		public void setUserId(int userId)
        {
        	this.userId = userId;
        }
		public IssueBean[] getIssueBeanArr()
        {
        	return issueBeanArr;
        }
		public void setIssueBeanArr(IssueBean[] issueBeanArr)
        {
        	this.issueBeanArr = issueBeanArr;
        }
		public String getIssueName()
        {
        	return issueName;
        }
		public void setIssueName(String issueName)
        {
        	this.issueName = issueName;
        }
		public String getStartDate()
        {
        	return startDate;
        }
		public void setStartDate(String startDate)
        {
        	this.startDate = startDate;
        }
		public String getSubmissionDate()
        {
        	return submissionDate;
        }
		public void setSubmissionDate(String submissionDate)
        {
        	this.submissionDate = submissionDate;
        }
		public String getEstimationDate()
        {
        	return estimationDate;
        }
		public void setEstimationDate(String estimationDate)
        {
        	this.estimationDate = estimationDate;
        }
		public boolean isToHandle()
        {
        	return toHandle;
        }
		public void setToHandle(boolean toHandle)
        {
        	this.toHandle = toHandle;
        }
		public String getDateOfCompletion()
        {
        	return dateOfCompletion;
        }
		public void setDateOfCompletion(String dateOfCompletion)
        {
        	this.dateOfCompletion = dateOfCompletion;
        }
		public String getRequestedBy()
        {
        	return requestedBy;
        }
		public void setRequestedBy(String requestedBy)
        {
        	this.requestedBy = requestedBy;
        }
				
}
