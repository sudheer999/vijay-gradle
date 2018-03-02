package com.dikshatech.beans;

import java.io.Serializable;

public class UserTaskListBean implements Serializable,ParsableObject
{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String workingDate; 
   
  //  private int taskId;
    private String taskName;
    private String projectId;
    private String projectName;
    private String etc;
    private String totalEtc;
    private String allTask;
    private TimeSheetHoursBean[] tmeSheetHourCommentarr; 
	
	public String getWorkingDate()
    {
    	return workingDate;
    }
	public void setWorkingDate(String workingDate)
    {
    	this.workingDate = workingDate;
    }
	
	public int getId()
    {
    	return id;
    }
	public void setId(int id)
    {
    	this.id = id;
    }
	
	public String getTaskName()
    {
    	return taskName;
    }
	public void setTaskName(String taskName)
    {
    	this.taskName = taskName;
    }
	
	public String getProjectId()
    {
    	return projectId;
    }
	public void setProjectId(String projectId)
    {
    	this.projectId = projectId;
    }
	public String getProjectName()
    {
    	return projectName;
    }
	public void setProjectName(String projectName)
    {
    	this.projectName = projectName;
    }
	public String getEtc()
    {
    	return etc;
    }
	public void setEtc(String etc)
    {
    	this.etc = etc;
    }
	public String getTotalEtc()
    {
    	return totalEtc;
    }
	public void setTotalEtc(String totalEtc)
    {
    	this.totalEtc = totalEtc;
    }
	public TimeSheetHoursBean[] getTmeSheetHourCommentarr()
    {
    	return tmeSheetHourCommentarr;
    }
	public void setTmeSheetHourCommentarr(
            TimeSheetHoursBean[] tmeSheetHourCommentarr)
    {
    	this.tmeSheetHourCommentarr = tmeSheetHourCommentarr;
    }
	public String getAllTask()
    {
    	return allTask;
    }
	public void setAllTask(String allTask)
    {
    	this.allTask = allTask;
    }
	
	
}
