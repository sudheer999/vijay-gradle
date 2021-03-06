/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

public class ExitWithdraw implements Serializable {

	/**
	 * This attribute maps to the column ID in the EXIT_WITHDRAW table.
	 */
	protected int	id;
	/**
	 * This attribute maps to the column EXIT_ID in the EXIT_WITHDRAW table.
	 */
	protected int	exitId;
	/**
	 * This attribute maps to the column STATUS_ID in the EXIT_WITHDRAW table.
	 */
	protected int	statusId;
	/**
	 * This attribute maps to the column RESAON in the EXIT_WITHDRAW table.
	 */
	private String	reason;
	/**
	 * This attribute maps to the column SUBMITTED_ON in the EXIT_WITHDRAW table.
	 */
	protected Date	submittedOn;
	/**
	 * This attribute maps to the column ON_STATUS_ID in the EXIT_WITHDRAW table.
	 */
	protected int	onStatusId;

	/**
	 * Method 'ExitWithdraw'
	 */
	public ExitWithdraw() {}

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
	 * Method 'getExitId'
	 * 
	 * @return int
	 */
	public int getExitId() {
		return exitId;
	}

	/**
	 * Method 'setExitId'
	 * 
	 * @param exitId
	 */
	public void setExitId(int exitId) {
		this.exitId = exitId;
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
	}

	/**
	 * Method 'getSubmittedOn'
	 * 
	 * @return Date
	 */
	public Date getSubmittedOn() {
		return submittedOn;
	}

	/**
	 * Method 'setSubmittedOn'
	 * 
	 * @param submittedOn
	 */
	public void setSubmittedOn(Date submittedOn) {
		this.submittedOn = submittedOn;
	}

	/**
	 * Method 'getOnStatusId'
	 * 
	 * @return int
	 */
	public int getOnStatusId() {
		return onStatusId;
	}

	/**
	 * Method 'setOnStatusId'
	 * 
	 * @param onStatusId
	 */
	public void setOnStatusId(int onStatusId) {
		this.onStatusId = onStatusId;
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
		if (!(_other instanceof ExitWithdraw)){
			return false;
		}
		final ExitWithdraw _cast = (ExitWithdraw) _other;
		if (id != _cast.id){
			return false;
		}
		if (exitId != _cast.exitId){
			return false;
		}
		if (statusId != _cast.statusId){
			return false;
		}
		if (reason == null ? _cast.reason != reason : !reason.equals(_cast.reason)){
			return false;
		}
		if (submittedOn == null ? _cast.submittedOn != submittedOn : !submittedOn.equals(_cast.submittedOn)){
			return false;
		}
		if (onStatusId != _cast.onStatusId){
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
		_hashCode = 29 * _hashCode + exitId;
		_hashCode = 29 * _hashCode + statusId;
		if (reason != null){
			_hashCode = 29 * _hashCode + reason.hashCode();
		}
		if (submittedOn != null){
			_hashCode = 29 * _hashCode + submittedOn.hashCode();
		}
		_hashCode = 29 * _hashCode + onStatusId;
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ExitWithdrawPk
	 */
	public ExitWithdrawPk createPk() {
		return new ExitWithdrawPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.ExitWithdraw: ");
		ret.append("id=" + id);
		ret.append(", exitId=" + exitId);
		ret.append(", statusId=" + statusId);
		ret.append(", reason=" + reason);
		ret.append(", submittedOn=" + submittedOn);
		ret.append(", onStatusId=" + onStatusId);
		return ret.toString();
	}

	public ExitWithdraw(int exitId, int statusId, String reason, Date submittedOn, int onStatusId) {
		this.exitId = exitId;
		this.statusId = statusId;
		this.reason = reason;
		this.submittedOn = submittedOn;
		this.onStatusId = onStatusId;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}
}
