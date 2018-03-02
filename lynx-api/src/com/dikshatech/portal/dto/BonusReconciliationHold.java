package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;


public class BonusReconciliationHold implements Serializable{
	/** 
	 * This attribute maps to the column ID in the BONUS_RECONCILIATION_HOLD table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column REP_ID in the BONUS_RECONCILIATION_HOLD table.
	 */
	protected int repId;

	/** 
	 * This attribute maps to the column USER_ID in the BONUS_RECONCILIATION_HOLD table.
	 */
	protected int userId;

	/** 
	 * This attribute maps to the column STATUS in the BONUS_RECONCILIATION_HOLD table.
	 */
	protected int status;

	/** 
	 * This attribute maps to the column COMMENTS in the BONUS_RECONCILIATION_HOLD table.
	 */
	protected String comments;

	/** 
	 * This attribute maps to the column ACTION_ON in the BONUS_RECONCILIATION_HOLD table.
	 */
	protected Date actionOn;

	/** 
	 * This attribute maps to the column ESC_FROM in the BONUS_RECONCILIATION_HOLD table.
	 */
	protected int escFrom;
	/**
	 * Method 'DepPerdiemHold'
	 * 
	 */
	public BonusReconciliationHold()
	{
	}

	public BonusReconciliationHold(int repId, int userId, int status, String comments,Date actionOn) {
		this.repId = repId;
		this.userId = userId;
		this.status = status;
		this.comments = comments;
		this.actionOn = actionOn;
	}

	public BonusReconciliationHold(int repId, int userId, int status, String comments, int escFrom) {
		this.repId = repId;
		this.userId = userId;
		this.status = status;
		this.comments = comments;
		this.escFrom = escFrom;
	}

	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	
	public int getRepId() {
		return repId;
	}

	
	public void setRepId(int repId) {
		this.repId = repId;
	}

	
	public int getUserId() {
		return userId;
	}

	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	public int getStatus() {
		return status;
	}

	
	public void setStatus(int status) {
		this.status = status;
	}

	
	public String getComments() {
		return comments;
	}

	
	public void setComments(String comments) {
		this.comments = comments;
	}

	
	public Date getActionOn() {
		return actionOn;
	}

	
	public void setActionOn(Date actionOn) {
		this.actionOn = actionOn;
	}

	
	public int getEscFrom() {
		return escFrom;
	}

	
	public void setEscFrom(int escFrom) {
		this.escFrom = escFrom;
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
		
		if (!(_other instanceof BonusReconciliationHold)) {
			return false;
		}
		
		final BonusReconciliationHold _cast = (BonusReconciliationHold) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (repId != _cast.repId) {
			return false;
		}
		
		if (userId != _cast.userId) {
			return false;
		}
		
		if (status != _cast.status) {
			return false;
		}
		
		if (comments == null ? _cast.comments != comments : !comments.equals( _cast.comments )) {
			return false;
		}
		
		if (actionOn == null ? _cast.actionOn != actionOn : !actionOn.equals( _cast.actionOn )) {
			return false;
		}
		
		if (escFrom != _cast.escFrom) {
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
		_hashCode = 29 * _hashCode + repId;
		_hashCode = 29 * _hashCode + userId;
		_hashCode = 29 * _hashCode + status;
		if (comments != null) {
			_hashCode = 29 * _hashCode + comments.hashCode();
		}
		
		if (actionOn != null) {
			_hashCode = 29 * _hashCode + actionOn.hashCode();
		}
		
		_hashCode = 29 * _hashCode + escFrom;
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DepPerdiemHoldPk
	 */
	public BonusReconciliationHoldPk createPk()
	{
		return new BonusReconciliationHoldPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.BonusReconciliationHold: " );
		ret.append( "id=" + id );
		ret.append( ", repId=" + repId );
		ret.append( ", userId=" + userId );
		ret.append( ", status=" + status );
		ret.append( ", comments=" + comments );
		ret.append( ", actionOn=" + actionOn );
		ret.append( ", escFrom=" + escFrom );
		return ret.toString();
	}

}
