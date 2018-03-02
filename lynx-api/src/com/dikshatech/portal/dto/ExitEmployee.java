package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.Status;

public class ExitEmployee implements Serializable {

	/**
	 * This attribute maps to the column ID in the EXIT_EMPLOYEE table.
	 */
	protected int		id;
	/**
	 * This attribute maps to the column ESR_MAP_ID in the EXIT_EMPLOYEE table.
	 */
	protected int		esrMapId;
	/**
	 * This attribute maps to the column USER_ID in the EXIT_EMPLOYEE table.
	 */
	protected int		userId;
	/**
	 * This attribute maps to the column REASON in the EXIT_EMPLOYEE table.
	 */
	protected String	reason;
	/**
	 * This attribute maps to the column STATUS_ID in the EXIT_EMPLOYEE table.
	 */
	protected int		statusId;
	/**
	 * This attribute maps to the column SUBMITTEDON in the EXIT_EMPLOYEE table.
	 */
	protected Date		submittedon;
	/**
	 * This attribute maps to the column LAST_WORKING_DAY in the EXIT_EMPLOYEE table.
	 */
	protected Date		lastWorkingDay;
	/**
	 * This attribute maps to the column BUY_BACK in the EXIT_EMPLOYEE table.
	 */
	protected int		buyBack;
	/**
	 * This attribute maps to the column COMMENTS in the EXIT_EMPLOYEE table.
	 */
	protected String	comments;
	/**
	 * This attribute maps to the column EMPLOYEE_NOTE in the EXIT_EMPLOYEE table.
	 */
	protected String	employeeNote;
	private int			noticePeriod;
	private String		status;
	private String		submittedOn;
	private String		enableNoc;
	private String		requestId;
	private String		canRelieve;

	/**
	 * Method 'ExitEmployee'
	 */
	public ExitEmployee() {}

	public ExitEmployee(int userId, int esrMapId, String reason, int statusId, Date submittedon) {
		this.esrMapId = esrMapId;
		this.userId = userId;
		this.reason = reason;
		this.statusId = statusId;
		this.submittedon = submittedon;
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
	 * Method 'getEsrMapId'
	 * 
	 * @return int
	 */
	public int getEsrMapId() {
		return esrMapId;
	}

	/**
	 * Method 'setEsrMapId'
	 * 
	 * @param esrMapId
	 */
	public void setEsrMapId(int esrMapId) {
		this.esrMapId = esrMapId;
	}

	/**
	 * Method 'getUserId'
	 * 
	 * @return int
	 */
	public int getUserId() {
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
	 * Method 'getReason'
	 * 
	 * @return String
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Method 'setReason'
	 * 
	 * @param reason
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * Method 'getStatusId'
	 * 
	 * @return int
	 */
	public int getStatusId() {
		return statusId;
	}

	/**
	 * Method 'setStatusId'
	 * 
	 * @param statusId
	 */
	public void setStatusId(int statusId) {
		this.statusId = statusId;
		setStatus(Status.getStatus(statusId));
	}

	/**
	 * Method 'getSubmittedon'
	 * 
	 * @return Date
	 */
	public Date getSubmittedon() {
		return submittedon;
	}

	/**
	 * Method 'setSubmittedon'
	 * 
	 * @param submittedon
	 */
	public void setSubmittedon(Date submittedon) {
		this.submittedon = submittedon;
		setSubmittedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(submittedon));
	}

	/**
	 * Method 'getLastWorkingDay'
	 * 
	 * @return Date
	 */
	public Date getLastWorkingDay() {
		return lastWorkingDay;
	}

	/**
	 * Method 'setLastWorkingDay'
	 * 
	 * @param lastWorkingDay
	 */
	public void setLastWorkingDay(Date lastWorkingDay) {
		this.lastWorkingDay = lastWorkingDay;
	}

	/**
	 * Method 'getBuyBack'
	 * 
	 * @return int
	 */
	public int getBuyBack() {
		return buyBack;
	}

	/**
	 * Method 'setBuyBack'
	 * 
	 * @param buyBack
	 */
	public void setBuyBack(int buyBack) {
		this.buyBack = buyBack;
	}

	/**
	 * Method 'getComments'
	 * 
	 * @return String
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Method 'setComments'
	 * 
	 * @param comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * Method 'getEmployeeNote'
	 * 
	 * @return String
	 */
	public String getEmployeeNote() {
		return employeeNote;
	}

	/**
	 * Method 'setEmployeeNote'
	 * 
	 * @param employeeNote
	 */
	public void setEmployeeNote(String employeeNote) {
		this.employeeNote = employeeNote;
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
		if (!(_other instanceof ExitEmployee)){
			return false;
		}
		final ExitEmployee _cast = (ExitEmployee) _other;
		if (id != _cast.id){
			return false;
		}
		if (esrMapId != _cast.esrMapId){
			return false;
		}
		if (userId != _cast.userId){
			return false;
		}
		if (reason == null ? _cast.reason != reason : !reason.equals(_cast.reason)){
			return false;
		}
		if (statusId != _cast.statusId){
			return false;
		}
		if (submittedon == null ? _cast.submittedon != submittedon : !submittedon.equals(_cast.submittedon)){
			return false;
		}
		if (lastWorkingDay == null ? _cast.lastWorkingDay != lastWorkingDay : !lastWorkingDay.equals(_cast.lastWorkingDay)){
			return false;
		}
		if (buyBack != _cast.buyBack){
			return false;
		}
		if (comments == null ? _cast.comments != comments : !comments.equals(_cast.comments)){
			return false;
		}
		if (employeeNote == null ? _cast.employeeNote != employeeNote : !employeeNote.equals(_cast.employeeNote)){
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
		_hashCode = 29 * _hashCode + esrMapId;
		_hashCode = 29 * _hashCode + userId;
		if (reason != null){
			_hashCode = 29 * _hashCode + reason.hashCode();
		}
		_hashCode = 29 * _hashCode + statusId;
		if (submittedon != null){
			_hashCode = 29 * _hashCode + submittedon.hashCode();
		}
		if (lastWorkingDay != null){
			_hashCode = 29 * _hashCode + lastWorkingDay.hashCode();
		}
		_hashCode = 29 * _hashCode + buyBack;
		if (comments != null){
			_hashCode = 29 * _hashCode + comments.hashCode();
		}
		if (employeeNote != null){
			_hashCode = 29 * _hashCode + employeeNote.hashCode();
		}
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ExitEmployeePk
	 */
	public ExitEmployeePk createPk() {
		return new ExitEmployeePk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.ExitEmployee: ");
		ret.append("id=" + id);
		ret.append(", esrMapId=" + esrMapId);
		ret.append(", userId=" + userId);
		ret.append(", reason=" + reason);
		ret.append(", statusId=" + statusId);
		ret.append(", submittedon=" + submittedon);
		ret.append(", lastWorkingDay=" + lastWorkingDay);
		ret.append(", buyBack=" + buyBack);
		ret.append(", comments=" + comments);
		ret.append(", employeeNote=" + employeeNote);
		return ret.toString();
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setSubmittedOn(String submittedOn) {
		this.submittedOn = submittedOn;
	}

	public String getSubmittedOn() {
		return submittedOn;
	}

	public void setNoticePeriod(int noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public int getNoticePeriod() {
		return noticePeriod;
	}

	public void setEnableNoc(String enableNoc) {
		this.enableNoc = enableNoc;
	}

	public String getEnableNoc() {
		return enableNoc;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestId() {
		return requestId;
	}

	public String getCanRelieve() {
		return canRelieve;
	}

	public void setCanRelieve(String canRelieve) {
		this.canRelieve = canRelieve;
	}
}