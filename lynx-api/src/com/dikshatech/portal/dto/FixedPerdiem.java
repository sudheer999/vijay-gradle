/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

public class FixedPerdiem implements Serializable {

	/**
	 * This attribute maps to the column ID in the FIXED_PERDIEM table.
	 */
	protected Integer	id;
	/**
	 * This attribute maps to the column PERDIEM in the FIXED_PERDIEM table.
	 */
	protected String	perdiem;
	/**
	 * This attribute maps to the column PERDIEM_FROM in the FIXED_PERDIEM table.
	 */
	protected Date		perdiemFrom;
	/**
	 * This attribute maps to the column PERDIEM_TO in the FIXED_PERDIEM table.
	 */
	protected Date		perdiemTo;
	/**
	 * This attribute maps to the column CURRENCY_TYPE in the FIXED_PERDIEM table.
	 */
	protected String	currencyType;
	/**
	 * This attribute maps to the column MODIFIED_ON in the FIXED_PERDIEM table.
	 */
	protected Date		modifiedOn;
	/**
	 * This attribute maps to the column MODIFIED_BY in the FIXED_PERDIEM table.
	 */
	protected Integer	modifiedBy;

	/**
	 * Method 'FixedPerdiem'
	 */
	public FixedPerdiem() {}

	/**
	 * Method 'getId'
	 * 
	 * @return Integer
	 */
	public Integer getId() {
		return id;
	}

	public FixedPerdiem(Integer id, String perdiem, Date perdiemFrom, Date perdiemTo, String currencyType) {
		this.id = id;
		this.perdiem = perdiem;
		this.perdiemFrom = perdiemFrom;
		this.perdiemTo = perdiemTo;
		this.currencyType = currencyType;
	}

	public void setValues(String perdiem, Date perdiemFrom, Date perdiemTo, String currencyType) {
		this.perdiem = perdiem;
		this.perdiemFrom = perdiemFrom;
		this.perdiemTo = perdiemTo;
		this.currencyType = currencyType;
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
	 * Method 'getPerdiem'
	 * 
	 * @return String
	 */
	public String getPerdiem() {
		return perdiem;
	}

	/**
	 * Method 'setPerdiem'
	 * 
	 * @param perdiem
	 */
	public void setPerdiem(String perdiem) {
		this.perdiem = perdiem;
	}

	/**
	 * Method 'getPerdiemFrom'
	 * 
	 * @return Date
	 */
	public Date getPerdiemFrom() {
		return perdiemFrom;
	}

	/**
	 * Method 'setPerdiemFrom'
	 * 
	 * @param perdiemFrom
	 */
	public void setPerdiemFrom(Date perdiemFrom) {
		this.perdiemFrom = perdiemFrom;
	}

	/**
	 * Method 'getPerdiemTo'
	 * 
	 * @return Date
	 */
	public Date getPerdiemTo() {
		return perdiemTo;
	}

	/**
	 * Method 'setPerdiemTo'
	 * 
	 * @param perdiemTo
	 */
	public void setPerdiemTo(Date perdiemTo) {
		this.perdiemTo = perdiemTo;
	}

	/**
	 * Method 'getCurrencyType'
	 * 
	 * @return String
	 */
	public String getCurrencyType() {
		return currencyType;
	}

	/**
	 * Method 'setCurrencyType'
	 * 
	 * @param currencyType
	 */
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	/**
	 * Method 'getModifiedOn'
	 * 
	 * @return Date
	 */
	public Date getModifiedOn() {
		return modifiedOn;
	}

	/**
	 * Method 'setModifiedOn'
	 * 
	 * @param modifiedOn
	 */
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	/**
	 * Method 'getModifiedBy'
	 * 
	 * @return Integer
	 */
	public Integer getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * Method 'setModifiedBy'
	 * 
	 * @param modifiedBy
	 */
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
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
		if (!(_other instanceof FixedPerdiem)){ return false; }
		final FixedPerdiem _cast = (FixedPerdiem) _other;
		if (id == null ? _cast.id != id : !id.equals(_cast.id)){ return false; }
		if (perdiem == null ? _cast.perdiem != perdiem : !perdiem.equals(_cast.perdiem)){ return false; }
		if (perdiemFrom == null ? _cast.perdiemFrom != perdiemFrom : !perdiemFrom.equals(_cast.perdiemFrom)){ return false; }
		if (perdiemTo == null ? _cast.perdiemTo != perdiemTo : !perdiemTo.equals(_cast.perdiemTo)){ return false; }
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
		if (perdiem != null){
			_hashCode = 29 * _hashCode + perdiem.hashCode();
		}
		if (perdiemFrom != null){
			_hashCode = 29 * _hashCode + perdiemFrom.hashCode();
		}
		if (perdiemTo != null){
			_hashCode = 29 * _hashCode + perdiemTo.hashCode();
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
	public FixedPerdiemPk createPk() {
		return new FixedPerdiemPk(id);
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
		ret.append(", perdiem=" + perdiem);
		ret.append(", perdiemFrom=" + perdiemFrom);
		ret.append(", perdiemTo=" + perdiemTo);
		ret.append(", currencyType=" + currencyType);
		ret.append(", modifiedOn=" + modifiedOn);
		ret.append(", modifiedBy=" + modifiedBy);
		return ret.toString();
	}
}
