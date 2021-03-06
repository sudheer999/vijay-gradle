/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.portal.forms.PortalForm;

public class TimeSheetDetails extends PortalForm implements Serializable, Cloneable {

	/**
	 * This attribute maps to the column ID in the TIME_SHEET_DETAILS table.
	 */
	protected int									id;
	/**
	 * This attribute maps to the column START_DATE in the TIME_SHEET_DETAILS table.
	 */
	protected Date									startDate;
	/**
	 * This attribute maps to the column END_DATE in the TIME_SHEET_DETAILS table.
	 */
	protected Date									endDate;
	/**
	 * This attribute maps to the column STATUS in the TIME_SHEET_DETAILS table.
	 */
	protected String								status;
	/**
	 * This attribute maps to the column SUBMISSION_DATE in the TIME_SHEET_DETAILS table.
	 */
	protected Date									submissionDate;
	/**
	 * This attribute maps to the column USER_ID in the TIME_SHEET_DETAILS table.
	 */
	protected int									userId;
	/**
	 * Method 'TimeSheetDetails'
	 */
	private boolean									toApprove	= false;
	/**
	 * This attribute maps to the column DURATION in the LEAVE_MASTER table.
	 */
	private float									comp_off;
	private String[]								timeSheetDataArr;
	private Integer[]								timeSheetIds;
	private String									comments;
	private com.dikshatech.beans.TimesheetBean[]	timeSheetBeanArray;
	private com.dikshatech.beans.HandlerAction		handlerActionBean;
	private int										noOfTsheettoApprove;
	
	/** 
	 * This attribute maps to the column IS_DELAYED in the TIME_SHEET_DETAILS table.
	 */
	protected short isDelayed;


	//Method to get a clone of TimeSheetDetails dto
	public Object clone() {
		try{
			return super.clone();
		} catch (CloneNotSupportedException e){
			return null;
		}
	}

	public TimeSheetDetails() {}

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
	 * Method 'getStartDate'
	 * 
	 * @return Date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Method 'setStartDate'
	 * 
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Method 'getEndDate'
	 * 
	 * @return Date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Method 'setEndDate'
	 * 
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Method 'getStatus'
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Method 'setStatus'
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Method 'getSubmissionDate'
	 * 
	 * @return Date
	 */
	public Date getSubmissionDate() {
		return submissionDate;
	}

	/**
	 * Method 'setSubmissionDate'
	 * 
	 * @param submissionDate
	 */
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	/**
	 * Method 'getUserId'
	 * 
	 * @return int
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * Method 'setUserId'
	 * 
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Method 'getIsDelayed'
	 * 
	 * @return short
	 */
	public short getIsDelayed()
	{
		return isDelayed;
	}

	/**
	 * Method 'setIsDelayed'
	 * 
	 * @param isDelayed
	 */
	public void setIsDelayed(short isDelayed)
	{
		this.isDelayed = isDelayed;
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
		
		if (!(_other instanceof TimeSheetDetails)) {
			return false;
		}
		
		final TimeSheetDetails _cast = (TimeSheetDetails) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (startDate == null ? _cast.startDate != startDate : !startDate.equals( _cast.startDate )) {
			return false;
		}
		
		if (endDate == null ? _cast.endDate != endDate : !endDate.equals( _cast.endDate )) {
			return false;
		}
		
		if (status == null ? _cast.status != status : !status.equals( _cast.status )) {
			return false;
		}
		
		if (submissionDate == null ? _cast.submissionDate != submissionDate : !submissionDate.equals( _cast.submissionDate )) {
			return false;
		}
		
		if (userId != _cast.userId) {
			return false;
		}
		
		if (comp_off != _cast.comp_off) {
			return false;
		}
		
		if (isDelayed != _cast.isDelayed) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		if (startDate != null) {
			_hashCode = 29 * _hashCode + startDate.hashCode();
		}
		
		if (endDate != null) {
			_hashCode = 29 * _hashCode + endDate.hashCode();
		}
		
		if (status != null) {
			_hashCode = 29 * _hashCode + status.hashCode();
		}
		
		if (submissionDate != null) {
			_hashCode = 29 * _hashCode + submissionDate.hashCode();
		}
		
		_hashCode = 29 * _hashCode + userId;
		_hashCode = 29 * _hashCode + Float.floatToIntBits(comp_off);
		_hashCode = 29 * _hashCode + (int) isDelayed;
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TimeSheetDetailsPk
	 */
	public TimeSheetDetailsPk createPk() {
		return new TimeSheetDetailsPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.TimeSheetDetails: ");
		ret.append("id=" + id);
		ret.append(", startDate=" + startDate);
		ret.append(", endDate=" + endDate);
		ret.append(", status=" + status);
		ret.append(", submissionDate=" + submissionDate);
		ret.append(", userId=" + userId);
		return ret.toString();
	}

	public String[] getTimeSheetDataArr() {
		return timeSheetDataArr;
	}

	public void setTimeSheetDataArr(String[] timeSheetDataArr) {
		this.timeSheetDataArr = timeSheetDataArr;
	}

	public Integer[] getTimeSheetIds() {
		return timeSheetIds;
	}

	public void setTimeSheetIds(Integer[] timeSheetIds) {
		this.timeSheetIds = timeSheetIds;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public com.dikshatech.beans.TimesheetBean[] getTimeSheetBeanArray() {
		return timeSheetBeanArray;
	}

	public void setTimeSheetBeanArray(com.dikshatech.beans.TimesheetBean[] timeSheetBeanArray) {
		this.timeSheetBeanArray = timeSheetBeanArray;
	}

	public com.dikshatech.beans.HandlerAction getHandlerActionBean() {
		return handlerActionBean;
	}

	public void setHandlerActionBean(com.dikshatech.beans.HandlerAction handlerActionBean) {
		this.handlerActionBean = handlerActionBean;
	}

	public int getNoOfTsheettoApprove() {
		return noOfTsheettoApprove;
	}

	public void setNoOfTsheettoApprove(int noOfTsheettoApprove) {
		this.noOfTsheettoApprove = noOfTsheettoApprove;
	}

	public void setToApprove(boolean toApprove) {
		this.toApprove = toApprove;
	}

	public boolean isToApprove() {
		return toApprove;
	}

	public void setComp_off(float comp_off) {
		this.comp_off = comp_off;
	}

	public float getComp_off() {
		return comp_off;
	}
}
