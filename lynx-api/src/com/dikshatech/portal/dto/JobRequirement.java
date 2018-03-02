package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class JobRequirement implements Serializable {

	/** 
	 * This attribute maps to the column ID in the LEAVE_BALANCE table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column JOB_TITLE in the LEAVE_BALANCE table.
	 */
	protected String jobTitle;

	/** 
	 * This attribute maps to the column JOB_DESCRIPTION in the LEAVE_BALANCE table.
	 */
	protected String jobDescription;

	/** 
	 *  This attribute maps to the column POSTED_BY in the LEAVE_BALANCE table..
	 */
	protected int postedBy;

	/** 
	 * This attribute maps to the column POSTED_ON in the LEAVE_BALANCE table.
	 */
	protected Date postedOn;
	
	protected String reqTagId;
	protected String experience;
	protected String location;
	protected String position;
	protected String skills;
	
	
	
	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getReqTagId() {
		return reqTagId;
	}

	public void setReqTagId(String reqTagId) {
		this.reqTagId = reqTagId;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	protected int isActive;

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public int getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(int postedBy) {
		this.postedBy = postedBy;
	}

	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}
	
	protected List<JobRequirement> jobReqList;
	
	public List<JobRequirement> getJobReqList() {
		return jobReqList;
	}

	public void setJobReqList(List<JobRequirement> jobReqList) {
		this.jobReqList = jobReqList;
	}

	/**
	 * Method 'JobRequirement'
	 * 
	 */
	public JobRequirement()
	{
	}
	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof JobRequirement)) {
			return false;
		}
		
		final JobRequirement _cast = (JobRequirement) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (jobTitle != _cast.jobTitle) {
			return false;
		}
		
		if (jobDescription != _cast.jobDescription) {
			return false;
		}
		
		if (postedBy != _cast.postedBy) {
			return false;
		}
		
		if (postedOn != _cast.postedOn) {
			return false;
		}
	
		return true;
	}

	

	/**
	 * Method 'createPk'
	 * 
	 * @return LeaveBalancePk
	 */
	public JobRequirementPk createPk()
	{
		return new JobRequirementPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.JobRequirement: " );
		ret.append( "id=" + id );
		ret.append( ", jobTitle=" + jobTitle );
		ret.append( ", jobDescription=" + jobDescription );
		ret.append( ", postedBy=" + postedBy );
		ret.append( ", postedOn=" + postedOn );
	
		return ret.toString();
	}

}

