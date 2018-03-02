package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

public class RetentionBonusReconciliationReq implements Serializable{

	
	/** 
	 * This attribute maps to the column ID in the RETENTION_BONUS_RECONCILIATION_REQ table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column BR_ID in the RETENTION_BONUS_RECONCILIATION_REQ table.
	 */
	protected int bonusId;

	/** 
	 * This attribute maps to the column SEQ_ID in the RETENTION_BONUS_RECONCILIATION_REQ table.
	 */
	protected int seqId;

	/** 
	 * This attribute maps to the column ASSIGNED_TO in the RETENTION_BONUS_RECONCILIATION_REQ table.
	 */
	protected int assignedTo;

	/** 
	 * This attribute maps to the column RECEIVED_ON in the RETENTION_BONUS_RECONCILIATION_REQ table.
	 */
	protected Date receivedOn;

	/** 
	 * This attribute maps to the column SUBMITTED_ON in the RETENTION_BONUS_RECONCILIATION_REQ table.
	 */
	protected Date submittedOn;
	
	/** 
	 * This attribute maps to the column IS_ESCALATED in the RETENTION_BONUS_RECONCILIATION_REQ table.
	 */
	protected int isEscalated;

	/** 
	 * This attribute represents whether the primitive attribute isEscalated is null.
	 */
	protected boolean isEscalatedNull = true;


	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	
	public int getBonusId() {
		return bonusId;
	}

	
	public void setBonusId(int bonusId) {
		this.bonusId = bonusId;
	}

	
	public int getSeqId() {
		return seqId;
	}

	
	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}

	
	public int getAssignedTo() {
		return assignedTo;
	}

	
	public void setAssignedTo(int assignedTo) {
		this.assignedTo = assignedTo;
	}

	
	public Date getReceivedOn() {
		return receivedOn;
	}

	
	public void setReceivedOn(Date receivedOn) {
		this.receivedOn = receivedOn;
	}

	
	public Date getSubmittedOn() {
		return submittedOn;
	}

	
	public void setSubmittedOn(Date submittedOn) {
		this.submittedOn = submittedOn;
	}

	
	public int getIsEscalated() {
		return isEscalated;
	}

	
	public void setIsEscalated(int isEscalated) {
		this.isEscalated = isEscalated;
	}

	
	public boolean isEscalatedNull() {
		return isEscalatedNull;
	}

	
	public void setEscalatedNull(boolean isEscalatedNull) {
		this.isEscalatedNull = isEscalatedNull;
	}

	
	/**
	 * Method 'RetentionBonusReconciliationReq'
	 * 
	 */
	
	public RetentionBonusReconciliationReq()
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
		
		if (!(_other instanceof RetentionBonusReconciliationReq)) {
			return false;
		}
		
		final RetentionBonusReconciliationReq _cast = (RetentionBonusReconciliationReq) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (bonusId != _cast.bonusId) {
			return false;
		}
		
		if (seqId != _cast.seqId) {
			return false;
		}
		
		if (assignedTo != _cast.assignedTo) {
			return false;
		}
		
		if (receivedOn == null ? _cast.receivedOn != receivedOn : !receivedOn.equals( _cast.receivedOn )) {
			return false;
		}
		
		if (submittedOn == null ? _cast.submittedOn != submittedOn : !submittedOn.equals( _cast.submittedOn )) {
			return false;
		}
		
		if (isEscalated != _cast.isEscalated) {
			return false;
		}
		
		if (isEscalatedNull != _cast.isEscalatedNull) {
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
		_hashCode = 29 * _hashCode + bonusId;
		_hashCode = 29 * _hashCode + seqId;
		_hashCode = 29 * _hashCode + assignedTo;
		if (receivedOn != null) {
			_hashCode = 29 * _hashCode + receivedOn.hashCode();
		}
		
		if (submittedOn != null) {
			_hashCode = 29 * _hashCode + submittedOn.hashCode();
		}
		
		_hashCode = 29 * _hashCode + isEscalated;
		_hashCode = 29 * _hashCode + (isEscalatedNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DepPerdiemReqPk
	 */
	public RetentionBonusReconciliationReqPk createPk()
	{
		return new RetentionBonusReconciliationReqPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.RetentionBonusReconciliationReq: " );
		ret.append( "id=" + id );
		ret.append( ", depId=" + bonusId );
		ret.append( ", seqId=" + seqId );
		ret.append( ", assignedTo=" + assignedTo );
		ret.append( ", receivedOn=" + receivedOn );
		ret.append( ", submittedOn=" + submittedOn );
		ret.append( ", isEscalated=" + isEscalated );
		return ret.toString();
	}


}
