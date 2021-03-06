/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

public class PerdiemMasterData implements Serializable {
	/**
	 * This attribute maps to the column ID in the PERDIEM_MASTER_DATA table.
	 */
	protected int id;

	/**
	 * This attribute maps to the column USER_ID in the PERDIEM_MASTER_DATA
	 * table.
	 */
	protected int userId;

	/**
	 * This attribute maps to the column PERDIEM in the PERDIEM_MASTER_DATA
	 * table.
	 */
	protected String perdiem;

	/**
	 * This attribute maps to the column PERDIEM_FROM in the PERDIEM_MASTER_DATA
	 * table.
	 */
	protected Date perdiemFrom;

	/**
	 * This attribute maps to the column PERDIEM_TO in the PERDIEM_MASTER_DATA
	 * table.
	 */
	protected Date perdiemTo;

	/**
	 * This attribute maps to the column CURRENCY_TYPE in the
	 * PERDIEM_MASTER_DATA table.
	 */
	protected String currencyType;

	/**
	 * Method 'PerdiemMasterData'
	 * 
	 */
	public PerdiemMasterData() {
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
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other) {
		if (_other == null) {
			return false;
		}

		if (_other == this) {
			return true;
		}

		if (!(_other instanceof PerdiemMasterData)) {
			return false;
		}

		final PerdiemMasterData _cast = (PerdiemMasterData) _other;
		if (id != _cast.id) {
			return false;
		}

		if (userId != _cast.userId) {
			return false;
		}

		if (perdiem == null ? _cast.perdiem != perdiem : !perdiem
				.equals(_cast.perdiem)) {
			return false;
		}

		if (perdiemFrom == null ? _cast.perdiemFrom != perdiemFrom
				: !perdiemFrom.equals(_cast.perdiemFrom)) {
			return false;
		}

		if (perdiemTo == null ? _cast.perdiemTo != perdiemTo : !perdiemTo
				.equals(_cast.perdiemTo)) {
			return false;
		}

		if (currencyType == null ? _cast.currencyType != currencyType
				: !currencyType.equals(_cast.currencyType)) {
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
		_hashCode = 29 * _hashCode + userId;
		if (perdiem != null) {
			_hashCode = 29 * _hashCode + perdiem.hashCode();
		}

		if (perdiemFrom != null) {
			_hashCode = 29 * _hashCode + perdiemFrom.hashCode();
		}

		if (perdiemTo != null) {
			_hashCode = 29 * _hashCode + perdiemTo.hashCode();
		}

		if (currencyType != null) {
			_hashCode = 29 * _hashCode + currencyType.hashCode();
		}

		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return PerdiemMasterDataPk
	 */
	public PerdiemMasterDataPk createPk() {
		return new PerdiemMasterDataPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.PerdiemMasterData: ");
		ret.append("id=" + id);
		ret.append(", userId=" + userId);
		ret.append(", perdiem=" + perdiem);
		ret.append(", perdiemFrom=" + perdiemFrom);
		ret.append(", perdiemTo=" + perdiemTo);
		ret.append(", currencyType=" + currencyType);
		return ret.toString();
	}

}
