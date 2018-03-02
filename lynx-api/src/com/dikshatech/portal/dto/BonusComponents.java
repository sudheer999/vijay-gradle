package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;


public class BonusComponents implements Serializable {
	
	
	/** 
	 * This attribute maps to the column ID in the DEP_PERDIEM_COMPONENTS table.
	 */
	protected Integer	id;
	/** 
	 * This attribute maps to the column REP_ID in the DEP_PERDIEM_COMPONENTS table.
	 */
	protected Integer	repId;
	/** 
	 * This attribute maps to the column TYPE in the DEP_PERDIEM_COMPONENTS table.
	 */
	protected Short		type;
	/** 
	 * This attribute maps to the column REASON in the DEP_PERDIEM_COMPONENTS table.
	 */
	protected String	reason;
	/** 
	 * This attribute maps to the column CURRENCY in the DEP_PERDIEM_COMPONENTS table.
	 */
	protected Integer	currency;
	/** 
	 * This attribute maps to the column AMOUNT in the DEP_PERDIEM_COMPONENTS table.
	 */
	protected String	amount;
	
	protected int noOfLeaves;
	protected int deductionPercent;

	public int getDeductionPercent() {
		return deductionPercent;
	}


	public void setDeductionPercent(int deductionPercent) {
		this.deductionPercent = deductionPercent;
	}


	public void setValues(Integer repId, Short type, String reason, Integer currency, String amount, String addedBy, Date addedOn, String comments) {
		this.repId = repId;
		this.type = type;
		this.reason = reason;
		this.currency = currency;
		this.amount = amount;
		this.addedBy = addedBy;
		this.addedOn = addedOn;
		this.comments = comments;
	}

	/** 
	 * This attribute maps to the column ADDED_BY in the DEP_PERDIEM_COMPONENTS table.
	 */
	protected String	addedBy;
	/** 
	 * This attribute maps to the column ADDED_ON in the DEP_PERDIEM_COMPONENTS table.
	 */
	protected Date		addedOn;
	/** 
	 * This attribute maps to the column COMMENTS in the DEP_PERDIEM_COMPONENTS table.
	 */
	protected String	comments;

	/**
	 * Method 'BonusComponents'
	 * 
	 */

	public BonusComponents() {}

	
	public Integer getId() {
		return id;
	}

	
	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getRepId() {
		return repId;
	}

	
	public void setRepId(Integer repId) {
		this.repId = repId;
	}

	
	public Short getType() {
		return type;
	}

	
	public void setType(Short type) {
		this.type = type;
	}

	
	public String getReason() {
		return reason;
	}

	
	public void setReason(String reason) {
		this.reason = reason;
	}

	
	public Integer getCurrency() {
		return currency;
	}

	
	public void setCurrency(Integer currency) {
		this.currency = currency;
	}

	
	

	
	
	public String getAmount() {
		return amount;
	}


	
	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getAddedBy() {
		return addedBy;
	}

	
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	
	public Date getAddedOn() {
		return addedOn;
	}

	
	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}

	
	public String getComments() {
		return comments;
	}

	
	public void setComments(String comments) {
		this.comments = comments;
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
		if (!(_other instanceof BonusComponents)){ return false; }
		final BonusComponents _cast = (BonusComponents) _other;
		if (id == null ? _cast.id != id : !id.equals(_cast.id)){ return false; }
		if (repId == null ? _cast.repId != repId : !repId.equals(_cast.repId)){ return false; }
		if (type == null ? _cast.type != type : !type.equals(_cast.type)){ return false; }
		if (reason == null ? _cast.reason != reason : !reason.equals(_cast.reason)){ return false; }
		if (currency == null ? _cast.currency != currency : !currency.equals(_cast.currency)){ return false; }
		if (amount == null ? _cast.amount != amount : !amount.equals(_cast.amount)){ return false; }
		if (addedBy == null ? _cast.addedBy != addedBy : !addedBy.equals(_cast.addedBy)){ return false; }
		if (addedOn == null ? _cast.addedOn != addedOn : !addedOn.equals(_cast.addedOn)){ return false; }
		if (comments == null ? _cast.comments != comments : !comments.equals(_cast.comments)){ return false; }
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
		if (repId != null){
			_hashCode = 29 * _hashCode + repId.hashCode();
		}
		if (type != null){
			_hashCode = 29 * _hashCode + type.hashCode();
		}
		if (reason != null){
			_hashCode = 29 * _hashCode + reason.hashCode();
		}
		if (currency != null){
			_hashCode = 29 * _hashCode + currency.hashCode();
		}
		if (amount != null){
			_hashCode = 29 * _hashCode + amount.hashCode();
		}
		if (addedBy != null){
			_hashCode = 29 * _hashCode + addedBy.hashCode();
		}
		if (addedOn != null){
			_hashCode = 29 * _hashCode + addedOn.hashCode();
		}
		if (comments != null){
			_hashCode = 29 * _hashCode + comments.hashCode();
		}
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DepPerdiemComponentsPk
	 */
	public BonusComponentsPk createPk() {
		return new BonusComponentsPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.BonusComponents: ");
		ret.append("id=" + id);
		ret.append(", repId=" + repId);
		ret.append(", type=" + type);
		ret.append(", reason=" + reason);
		ret.append(", currency=" + currency);
		ret.append(", amount=" + amount);
		ret.append(", addedBy=" + addedBy);
		ret.append(", addedOn=" + addedOn);
		ret.append(", comments=" + comments);
		return ret.toString();
	}


	public int getNoOfLeaves() {
		return noOfLeaves;
	}


	public void setNoOfLeaves(int noOfLeaves) {
		this.noOfLeaves = noOfLeaves;
	}
	
	
	
}
