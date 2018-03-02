/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dto;

import java.io.Serializable;

public class UserTaskTimesheetMap implements Serializable {

	/**
	 * This attribute maps to the column ID in the USER_TASK_TIMESHEET_MAP table.
	 */
	protected int		id;
	/**
	 * This attribute maps to the column ETC in the USER_TASK_TIMESHEET_MAP table.
	 */
	protected float		etc;
	/**
	 * This attribute maps to the column TOTAL_ETC in the USER_TASK_TIMESHEET_MAP table.
	 */
	protected float		totalEtc;
	/**
	 * This attribute maps to the column TASK_NAME in the USER_TASK_TIMESHEET_MAP table.
	 */
	protected String	taskName;
	/**
	 * This attribute maps to the column PROJECT_ID in the USER_TASK_TIMESHEET_MAP table.
	 */
	protected int		projectId;
	/**
	 * This attribute maps to the column TS_ID in the USER_TASK_TIMESHEET_MAP table.
	 */
	protected int		tsId;
	private String		mon	= "0.00|";
	private String		tue	= "0.00|";
	private String		wed	= "0.00|";
	private String		thu	= "0.00|";
	private String		fri	= "0.00|";
	private String		sat	= "0.00|";
	private String		sun	= "0.00|";

	/**
	 * Method 'UserTaskTimesheetMap'
	 */
	public UserTaskTimesheetMap() {}

	public String getMon() {
		return mon;
	}

	public String[] getMonDetails() {
		return getDetails(mon);
	}

	public String[] getTueDetails() {
		return getDetails(tue);
	}

	public String[] getWedDetails() {
		return getDetails(wed);
	}

	public String[] getThuDetails() {
		return getDetails(thu);
	}

	public String[] getFriDetails() {
		return getDetails(fri);
	}

	public String[] getSatDetails() {
		return getDetails(sat);
	}

	public String[] getSunDetails() {
		return getDetails(sun);
	}

	private String[] getDetails(String dayString) {
		if (dayString != null){
			String[] hoursCommentArr = dayString.split("\\|");
			if (hoursCommentArr != null && hoursCommentArr.length > 0){
				if (hoursCommentArr.length == 2) return hoursCommentArr;
				return new String[] { hoursCommentArr[0], null };
			}
		}
		return new String[] { "0.00", null };
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public String getTue() {
		return tue;
	}

	public void setTue(String tue) {
		this.tue = tue;
	}

	public String getWed() {
		return wed;
	}

	public void setWed(String wed) {
		this.wed = wed;
	}

	public String getThu() {
		return thu;
	}

	public void setThu(String thu) {
		this.thu = thu;
	}

	public String getFri() {
		return fri;
	}

	public void setFri(String fri) {
		this.fri = fri;
	}

	public String getSat() {
		return sat;
	}

	public void setSat(String sat) {
		this.sat = sat;
	}

	public String getSun() {
		return sun;
	}

	public void setSun(String sun) {
		this.sun = sun;
	}

	/**
	 * Method 'getId'
	 * 
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Method 'getEtc'
	 * 
	 * @return int
	 */
	public float getEtc() {
		return etc;
	}

	/**
	 * Method 'setEtc'
	 * 
	 * @param etc
	 */
	public void setEtc(float etc) {
		this.etc = etc;
	}

	/**
	 * Method 'getTotalEtc'
	 * 
	 * @return int
	 */
	public float getTotalEtc() {
		return totalEtc;
	}

	/**
	 * Method 'setTotalEtc'
	 * 
	 * @param totalEtc
	 */
	public void setTotalEtc(float totalEtc) {
		this.totalEtc = totalEtc;
	}

	/**
	 * Method 'getTaskName'
	 * 
	 * @return String
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Method 'setTaskName'
	 * 
	 * @param taskName
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * Method 'getProjectId'
	 * 
	 * @return int
	 */
	public int getProjectId() {
		return projectId;
	}

	/**
	 * Method 'setProjectId'
	 * 
	 * @param projectId
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	/**
	 * Method 'getTsId'
	 * 
	 * @return int
	 */
	public int getTsId() {
		return tsId;
	}

	/**
	 * Method 'setTsId'
	 * 
	 * @param tsId
	 */
	public void setTsId(int tsId) {
		this.tsId = tsId;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other) {
		if (_other == null){
			return false;
		}
		if (_other == this){
			return true;
		}
		if (!(_other instanceof UserTaskTimesheetMap)){
			return false;
		}
		final UserTaskTimesheetMap _cast = (UserTaskTimesheetMap) _other;
		if (id != _cast.id){
			return false;
		}
		if (etc != _cast.etc){
			return false;
		}
		if (totalEtc != _cast.totalEtc){
			return false;
		}
		if (taskName == null ? _cast.taskName != taskName : !taskName.equals(_cast.taskName)){
			return false;
		}
		if (projectId != _cast.projectId){
			return false;
		}
		if (tsId != _cast.tsId){
			return false;
		}
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode() {
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		_hashCode = (int) (29 * _hashCode + etc);
		_hashCode = (int) (29 * _hashCode + totalEtc);
		if (taskName != null){
			_hashCode = 29 * _hashCode + taskName.hashCode();
		}
		_hashCode = 29 * _hashCode + projectId;
		_hashCode = 29 * _hashCode + tsId;
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return UserTaskTimesheetMapPk
	 */
	public UserTaskTimesheetMapPk createPk() {
		return new UserTaskTimesheetMapPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.UserTaskTimesheetMap: ");
		ret.append("id=" + id);
		ret.append(", etc=" + etc);
		ret.append(", totalEtc=" + totalEtc);
		ret.append(", taskName=" + taskName);
		ret.append(", projectId=" + projectId);
		ret.append(", tsId=" + tsId);
		return ret.toString();
	}
}