package com.dikshatech.beans;

public class TimeSheetInboxInfo
{
	protected String timeSheetId;
	protected String submittedBy; 
	protected String submittedOn;
	protected String approvedBy;
	protected String approvedOn;
	protected String rejectedBy;
	protected String rejectedOn;
	protected String holdOn;
	protected String holdBy;
	
	public String getSubmittedBy()
    {
    	return submittedBy;
    }
	public void setSubmittedBy(String submittedBy)
    {
    	this.submittedBy = submittedBy;
    }
	public String getSubmittedOn()
    {
    	return submittedOn;
    }
	public void setSubmittedOn(String submittedOn)
    {
    	this.submittedOn = submittedOn;
    }
	public String getApprovedBy()
    {
    	return approvedBy;
    }
	public void setApprovedBy(String approvedBy)
    {
    	this.approvedBy = approvedBy;
    }
	public String getApprovedOn()
    {
    	return approvedOn;
    }
	public void setApprovedOn(String approvedOn)
    {
    	this.approvedOn = approvedOn;
    }
	public String getRejectedBy()
    {
    	return rejectedBy;
    }
	public void setRejectedBy(String rejectedBy)
    {
    	this.rejectedBy = rejectedBy;
    }
	public String getRejectedOn()
    {
    	return rejectedOn;
    }
	public void setRejectedOn(String rejectedOn)
    {
    	this.rejectedOn = rejectedOn;
    }
	@Override
    public int hashCode()
    {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result
	            + ((approvedBy == null) ? 0 : approvedBy.hashCode());
	    result = prime * result
	            + ((approvedOn == null) ? 0 : approvedOn.hashCode());
	    result = prime * result
	            + ((rejectedBy == null) ? 0 : rejectedBy.hashCode());
	    result = prime * result
	            + ((rejectedOn == null) ? 0 : rejectedOn.hashCode());
	    result = prime * result
	            + ((submittedBy == null) ? 0 : submittedBy.hashCode());
	    result = prime * result
	            + ((submittedOn == null) ? 0 : submittedOn.hashCode());
	    return result;
    }
	@Override
    public boolean equals(Object obj)
    {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    TimeSheetInboxInfo other = (TimeSheetInboxInfo) obj;
	    if (approvedBy == null)
	    {
		    if (other.approvedBy != null)
			    return false;
	    }
	    else if (!approvedBy.equals(other.approvedBy))
		    return false;
	    if (approvedOn == null)
	    {
		    if (other.approvedOn != null)
			    return false;
	    }
	    else if (!approvedOn.equals(other.approvedOn))
		    return false;
	    if (rejectedBy == null)
	    {
		    if (other.rejectedBy != null)
			    return false;
	    }
	    else if (!rejectedBy.equals(other.rejectedBy))
		    return false;
	    if (rejectedOn == null)
	    {
		    if (other.rejectedOn != null)
			    return false;
	    }
	    else if (!rejectedOn.equals(other.rejectedOn))
		    return false;
	    if (submittedBy == null)
	    {
		    if (other.submittedBy != null)
			    return false;
	    }
	    else if (!submittedBy.equals(other.submittedBy))
		    return false;
	    if (submittedOn == null)
	    {
		    if (other.submittedOn != null)
			    return false;
	    }
	    else if (!submittedOn.equals(other.submittedOn))
		    return false;
	    return true;
    }
	public String getHoldOn()
    {
    	return holdOn;
    }
	public void setHoldOn(String holdOn)
    {
    	this.holdOn = holdOn;
    }
	public String getHoldBy()
    {
    	return holdBy;
    }
	public void setHoldBy(String holdBy)
    {
    	this.holdBy = holdBy;
    }
	public String getTimeSheetId()
    {
    	return timeSheetId;
    }
	public void setTimeSheetId(String timeSheetId)
    {
    	this.timeSheetId = timeSheetId;
    }
	
}
