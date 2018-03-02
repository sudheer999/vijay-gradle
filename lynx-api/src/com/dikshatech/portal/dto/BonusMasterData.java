package com.dikshatech.portal.dto;

import java.io.Serializable;



public class BonusMasterData  implements Serializable {
	
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
	protected String qBonus;

	/**
	 * This attribute maps to the column PERDIEM_FROM in the PERDIEM_MASTER_DATA
	 * table.
	 */
	protected String cBonus;

	/**
	 * This attribute maps to the column PERDIEM_TO in the PERDIEM_MASTER_DATA
	 * table.
	 */
	protected String month;

	/**
	 * This attribute maps to the column CURRENCY_TYPE in the
	 * PERDIEM_MASTER_DATA table.
	 */
	protected int currencyType;
	protected int year;

	
	public int getYear() {
		return year;
	}


	
	public void setYear(int year) {
		this.year = year;
	}


	/**
	 * Method 'PerdiemMasterData'
	 * 
	 */
	public BonusMasterData() {
	}

	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	
	public int getUserId() {
		return userId;
	}

	
	public void setUserId(int userId) {
		this.userId = userId;
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

	
	public String getMonth() {
		return month;
	}

	
	public void setMonth(String month) {
		this.month = month;
	}

	
	public int getCurrencyType() {
		return currencyType;
	}

	
	public void setCurrencyType(int currencyType) {
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

		if (!(_other instanceof BonusMasterData)) {
			return false;
		}

		final BonusMasterData _cast = (BonusMasterData) _other;
		if (id != _cast.id) {
			return false;
		}

		if (userId != _cast.userId) {
			return false;
		}

		if (qBonus == null ? _cast.qBonus != qBonus : !qBonus
				.equals(_cast.qBonus)) {
			return false;
		}

		if (cBonus == null ? _cast.cBonus != cBonus
				: !cBonus.equals(_cast.cBonus)) {
			return false;
		}

		if (month == null ? _cast.month != month : !month
				.equals(_cast.month)) {
			return false;
		}

		if (currencyType != _cast.currencyType) {
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
		_hashCode = 29 * _hashCode + currencyType;
		if (qBonus != null) {
			_hashCode = 29 * _hashCode + qBonus.hashCode();
		}

		if (cBonus != null) {
			_hashCode = 29 * _hashCode + cBonus.hashCode();
		}

		if (month != null) {
			_hashCode = 29 * _hashCode + month.hashCode();
		}


		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return PerdiemMasterDataPk
	 */
	public BonusMasterDataPk createPk() {
		return new BonusMasterDataPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.BonusMasterData: ");
		ret.append("id=" + id);
		ret.append(", userId=" + userId);
		ret.append(", qBonus=" + qBonus);
		ret.append(", cBonus=" + cBonus);
		ret.append(", month=" + month);
		ret.append(", currencyType=" + currencyType);
		ret.append(", year=" + year);
		return ret.toString();
	}

}

