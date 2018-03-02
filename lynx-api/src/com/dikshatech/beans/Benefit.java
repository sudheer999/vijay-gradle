package com.dikshatech.beans;

public class Benefit {
	private int id;
	private String benefitName;
	private String eligibleAmt;
	private String usedAmt;
	private String unUsedAmt;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBenefitName() {
		return benefitName;
	}
	public void setBenefitName(String benefitName) {
		this.benefitName = benefitName;
	}
	public String getEligibleAmt() {
		return eligibleAmt;
	}
	public void setEligibleAmt(String eligibleAmt) {
		this.eligibleAmt = eligibleAmt;
	}
	public String getUsedAmt() {
		return usedAmt;
	}
	public void setUsedAmt(String usedAmt) {
		this.usedAmt = usedAmt;
	}
	public String getUnUsedAmt() {
		return unUsedAmt;
	}
	public void setUnUsedAmt(String unUsedAmt) {
		this.unUsedAmt = unUsedAmt;
	}
	
}
