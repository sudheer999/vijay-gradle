package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;


public class FixedBonus implements Serializable{

	/**
	 * This attribute maps to the column ID in the FIXED_BONUS table.
	 */
	protected Integer	id;
	/**
	 * This attribute maps to the column QUATERELY_BONUS in the FIXED_BONUS table.
	 */
	protected String	qBonus;
	/**
	 * This attribute maps to the column COMPANY_BONUS in the FIXED_BONUS table.
	 */
	protected String		cBonus;
	
	/**
	 * This attribute maps to the column CURRENCY_TYPE in the FIXED_PERDIEM table.
	 */
	protected Integer	currencyType;
	/**
	 * This attribute maps to the column MODIFIED_ON in the FIXED_BONUS table.
	 */
	protected Date		modifiedOn;
	/**
	 * This attribute maps to the column MODIFIED_BY in the FIXED_BONUS table.
	 */
	protected Integer	modifiedBy;
	/**
	 * This attribute maps to the column MODIFIED_BY in the FIXED_BONUS table.
	 */
	protected String	month;

	/**
	 * Method 'FixedBonus'
	 */
	public FixedBonus() {}
	
	public FixedBonus(Integer id, String qBonus,String cBonus,String month,int currencyType) {
		this.id = id;
		this.qBonus = qBonus;
		this.cBonus = cBonus;
		this.month = month;
		this.currencyType = currencyType;
	}

	public void setValues(Integer id, String qBonus, String cBonus,String month,int currencyType) {
		this.qBonus = qBonus;
		this.cBonus = cBonus;
		this.month = month;
		this.currencyType = currencyType;
	}

	
	public Integer getId() {
		return id;
	}

	
	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getqBonus() {
		return qBonus;
	}

	
	public void setqBonus(String qBonus) {
		this.qBonus = qBonus;
	}

	
	public String getcBonus() {
		return cBonus;
	}

	
	public void setcBonus(String cBonus) {
		this.cBonus = cBonus;
	}

	
	public Integer getCurrencyType() {
		return currencyType;
	}

	
	public void setCurrencyType(Integer currencyType) {
		this.currencyType = currencyType;
	}

	
	public Date getModifiedOn() {
		return modifiedOn;
	}

	
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	
	public Integer getModifiedBy() {
		return modifiedBy;
	}

	
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	
	public String getMonth() {
		return month;
	}

	
	public void setMonth(String month) {
		this.month = month;
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
		if (!(_other instanceof FixedBonus)){ return false; }
		final FixedBonus _cast = (FixedBonus) _other;
		if (id == null ? _cast.id != id : !id.equals(_cast.id)){ return false; }
		if (cBonus == null ? _cast.cBonus != cBonus : !cBonus.equals(_cast.cBonus)){ return false; }
		if (qBonus == null ? _cast.qBonus != qBonus : !qBonus.equals(_cast.qBonus)){ return false; }
		if (month == null ? _cast.month != month : !month.equals(_cast.month)){ return false; }
	   if (currencyType == null ? _cast.currencyType != currencyType : !currencyType.equals(_cast.currencyType)){ return false; }
		if (modifiedOn == null ? _cast.modifiedOn != modifiedOn : !modifiedOn.equals(_cast.modifiedOn)){ return false; }
		if (modifiedBy == null ? _cast.modifiedBy != modifiedBy : !modifiedBy.equals(_cast.modifiedBy)){ return false; }
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
		if (qBonus != null){
			_hashCode = 29 * _hashCode + qBonus.hashCode();
		}
		if (cBonus != null){
			_hashCode = 29 * _hashCode + cBonus.hashCode();
		}
		if (month != null){
			_hashCode = 29 * _hashCode + month.hashCode();
		}
		
		if (currencyType != null){
			_hashCode = 29 * _hashCode + currencyType.hashCode();
		}
		if (modifiedOn != null){
			_hashCode = 29 * _hashCode + modifiedOn.hashCode();
		}
		if (modifiedBy != null){
			_hashCode = 29 * _hashCode + modifiedBy.hashCode();
		}
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return FixedPerdiemPk
	 */
	public FixedBonusPk createPk() {
		return new FixedBonusPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.FixedPerdiem: ");
		ret.append("id=" + id);
		ret.append(", qBonus=" + qBonus);
		ret.append(", cBonus=" + cBonus);
		ret.append(", month=" + month);
		ret.append(", currencyType=" + currencyType);
		ret.append(", modifiedOn=" + modifiedOn);
		ret.append(", modifiedBy=" + modifiedBy);
		return ret.toString();
	}



}
