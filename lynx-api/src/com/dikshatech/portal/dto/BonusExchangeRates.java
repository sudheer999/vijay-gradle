package com.dikshatech.portal.dto;

import java.io.Serializable;


public class BonusExchangeRates implements Serializable{
	
	
	protected int	id;
	/**
	 * This attribute maps to the column REP_ID in the
	 * DEP_PERDIEM_EXCHANGE_RATES table.
	 */
	protected int	repId;
	/**
	 * This attribute maps to the column CURRENCY_TYPE in the
	 * DEP_PERDIEM_EXCHANGE_RATES table.
	 */
	protected short	currencyType;
	/**
	 * This attribute maps to the column AMOUNT in the
	 * DEP_PERDIEM_EXCHANGE_RATES table.
	 */
	protected double	qAmount;
	protected double	cAmount;
	
	private String	currencyName;

	/**
	 * Method 'DepPerdiemExchangeRates'
	 * 
	 */
	public BonusExchangeRates() {}

	public BonusExchangeRates(int repId, short currencyType, double qAmount,double cAmount, String currencyName) {
		super();
		this.repId = repId;
		this.currencyType = currencyType;
		this.qAmount = qAmount;
		this.cAmount = cAmount;
		this.currencyName = currencyName;
	}

	public void setValues(short currencyType, double qAmount,double cAmount) {
		this.currencyType = currencyType;
		this.qAmount = qAmount;
		this.cAmount = cAmount;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DepPerdiemExchangeRatesPk
	 */
	public BonusExchangeRatesPk createPk() {
		return new BonusExchangeRatesPk(id);
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

	
	public short getCurrencyType() {
		return currencyType;
	}

	
	public void setCurrencyType(short currencyType) {
		this.currencyType = currencyType;
	}

	
	public String getCurrencyName() {
		return currencyName;
	}

	
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	
	public double getqAmount() {
		return qAmount;
	}

	
	public void setqAmount(double qAmount) {
		this.qAmount = qAmount;
	}

	
	public double getcAmount() {
		return cAmount;
	}

	
	public void setcAmount(double cAmount) {
		this.cAmount = cAmount;
	}

}
