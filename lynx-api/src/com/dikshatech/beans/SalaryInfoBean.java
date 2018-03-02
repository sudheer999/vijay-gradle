package com.dikshatech.beans;

public class SalaryInfoBean {

	private int		id;
	private int		basic;
	private int		hra;
	private String	relocationBonus;
	private String	joiningBonusString;
	private String	joiningBonusAmount;
	private String	retentionBonus;
	private String	paymentTerms;
	private String	perdiemString;
	private String 	retentionInstallments;
	private String perdiemOffered;
	private String relocationCity;
	private int perdiemType;
	public int getPerdiemType() {
		return perdiemType;
	}

	public void setPerdiemType(int perdiemType) {
		this.perdiemType = perdiemType;
	}

	public String getPerdiemOffered() {
		return perdiemOffered;
	}

	public void setPerdiemOffered(String perdiemOffered) {
		this.perdiemOffered = perdiemOffered;
	}

	public String getRetentionInstallments() {
		return retentionInstallments;
	}

	public void setRetentionInstallments(String retentionInstallments) {
		this.retentionInstallments = retentionInstallments;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBasic() {
		return basic;
	}

	public void setBasic(int basic) {
		this.basic = basic;
	}

	public int getHra() {
		return hra;
	}

	public void setHra(int hra) {
		this.hra = hra;
	}

	public String getRelocationBonus() {
		return relocationBonus;
	}

	public void setRelocationBonus(String relocationBonus) {
		this.relocationBonus = relocationBonus;
	}

	public String getJoiningBonusString() {
		return joiningBonusString;
	}

	public void setJoiningBonusString(String joiningBonusString) {
		this.joiningBonusString = joiningBonusString;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getPerdiemString() {
		return perdiemString;
	}

	public void setPerdiemString(String perdiemString) {
		this.perdiemString = perdiemString;
	}

	public String getJoiningBonusAmount() {
		return joiningBonusAmount;
	}

	public void setJoiningBonusAmount(String joiningBonusAmount) {
		this.joiningBonusAmount = joiningBonusAmount;
	}

	public String getRetentionBonus() {
		return retentionBonus;
	}

	public void setRetentionBonus(String retentionBonus) {
		this.retentionBonus = retentionBonus;
	}

	public String getRelocationCity() {
		return relocationCity;
	}

	public void setRelocationCity(String relocationCity) {
		this.relocationCity = relocationCity;
	}

	
}
