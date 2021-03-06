/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.common.utils.Status;

public class SalaryReconciliation implements Serializable {

	/**
	 * This attribute maps to the column ID in the salary_reconciliation table.
	 */
	protected Integer	id;
	/**
	 * This attribute maps to the column ESR_MAP_ID in the salary_reconciliation table.
	 */
	protected Integer	esrMapId;
	/**
	 * This attribute maps to the column MONTH in the salary_reconciliation table.
	 */
	protected Integer	month;
	/**
	 * This attribute maps to the column YEAR in the salary_reconciliation table.
	 */
	protected Integer	year;
	/**
	 * This attribute maps to the column STATUS in the salary_reconciliation table.
	 */
	protected Integer	status;
	/**
	 * This attribute maps to the column CREATED_ON in the salary_reconciliation table.
	 */
	protected Date		createdOn;
	/**
	 * This attribute maps to the column COMPLETED_ON in the salary_reconciliation table.
	 */
	protected Date		completedOn;
	private String		statusName;
	private String		canEdit;
	private String		reqId;
	/**
	 * Method 'SalaryReconciliation'
	 */
	public SalaryReconciliation() {}

	public SalaryReconciliation(Integer esrMapId, Integer month, Integer year, Integer status, Date createdOn, Date completedOn) {
		this.esrMapId = esrMapId;
		this.month = month;
		this.year = year;
		setStatus(status);
		this.createdOn = createdOn;
		this.completedOn = completedOn;
	}

	/**
	 * Method 'getId'
	 * 
	 * @return Integer
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Method 'getEsrMapId'
	 * 
	 * @return Integer
	 */
	public Integer getEsrMapId() {
		return esrMapId;
	}

	/**
	 * Method 'setEsrMapId'
	 * 
	 * @param esrMapId
	 */
	public void setEsrMapId(Integer esrMapId) {
		this.esrMapId = esrMapId;
	}

	/**
	 * Method 'getMonth'
	 * 
	 * @return Integer
	 */
	public Integer getMonth() {
		return month;
	}

	/**
	 * Method 'setMonth'
	 * 
	 * @param month
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}

	/**
	 * Method 'getYear'
	 * 
	 * @return Integer
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * Method 'setYear'
	 * 
	 * @param year
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * Method 'getStatus'
	 * 
	 * @return Integer
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Method 'setStatus'
	 * 
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
		setStatusName(Status.getStatus(status));
	}

	/**
	 * Method 'getCreatedOn'
	 * 
	 * @return Date
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * Method 'setCreatedOn'
	 * 
	 * @param createdOn
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Method 'getCompletedOn'
	 * 
	 * @return Date
	 */
	public Date getCompletedOn() {
		return completedOn;
	}

	/**
	 * Method 'setCompletedOn'
	 * 
	 * @param completedOn
	 */
	public void setCompletedOn(Date completedOn) {
		this.completedOn = completedOn;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other) {
		if (_other == null){ return false; }
		if (_other == this){ return true; }
		if (!(_other instanceof SalaryReconciliation)){ return false; }
		final SalaryReconciliation _cast = (SalaryReconciliation) _other;
		if (id == null ? _cast.id != id : !id.equals(_cast.id)){ return false; }
		if (esrMapId == null ? _cast.esrMapId != esrMapId : !esrMapId.equals(_cast.esrMapId)){ return false; }
		if (month == null ? _cast.month != month : !month.equals(_cast.month)){ return false; }
		if (year == null ? _cast.year != year : !year.equals(_cast.year)){ return false; }
		if (status == null ? _cast.status != status : !status.equals(_cast.status)){ return false; }
		if (createdOn == null ? _cast.createdOn != createdOn : !createdOn.equals(_cast.createdOn)){ return false; }
		if (completedOn == null ? _cast.completedOn != completedOn : !completedOn.equals(_cast.completedOn)){ return false; }
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode() {
		int _hashCode = 0;
		if (id != null){
			_hashCode = 29 * _hashCode + id.hashCode();
		}
		if (esrMapId != null){
			_hashCode = 29 * _hashCode + esrMapId.hashCode();
		}
		if (month != null){
			_hashCode = 29 * _hashCode + month.hashCode();
		}
		if (year != null){
			_hashCode = 29 * _hashCode + year.hashCode();
		}
		if (status != null){
			_hashCode = 29 * _hashCode + status.hashCode();
		}
		if (createdOn != null){
			_hashCode = 29 * _hashCode + createdOn.hashCode();
		}
		if (completedOn != null){
			_hashCode = 29 * _hashCode + completedOn.hashCode();
		}
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return SalaryReconciliationPk
	 */
	public SalaryReconciliationPk createPk() {
		return new SalaryReconciliationPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.SalaryReconciliation: ");
		ret.append("id=" + id);
		ret.append(", esrMapId=" + esrMapId);
		ret.append(", month=" + month);
		ret.append(", year=" + year);
		ret.append(", status=" + status);
		ret.append(", createdOn=" + createdOn);
		ret.append(", completedOn=" + completedOn);
		return ret.toString();
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(String canEdit) {
		this.canEdit = canEdit;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

}
