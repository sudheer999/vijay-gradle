package com.dikshatech.beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Salary implements Serializable {

	static final long			serialVersionUID	= 1L;
	private List<SalaryHead>	salaryHead			= new ArrayList<SalaryHead>();
	private final float			FIXEDRATE			= .75f;
	private final float			FBPRATE				= .25f;
	private final float			TPLBRATE			= 1 / (12f * 2f);
	private float				annualCTC;
	private float				monthlyCTC;
	private float				fixed;
	private float				FBP;
	private float				perDiem;
	private float				tblb;
	private float				totCTC;
	private float				ctc;
	private String				perdiemFrom;
	private String				perdiemTo;
	private String				perdiemCurrencyType;
	private float				fixedPerdiemAmount;
	private float				retentionBonus;
	private String 				esic;
    protected String active;
	
	protected  Date inActiveFromDate;
	
	protected  Date inActiveToDate;

	public float getAnnualCTC() {
		return annualCTC;
	}

	public String getEsic() {
		return esic;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}


	public void setEsic(String esic) {
		this.esic = esic;
	}

	public Date getInActiveFromDate() {
		return inActiveFromDate;
	}

	public void setInActiveFromDate(Date inActiveFromDate) {
		this.inActiveFromDate = inActiveFromDate;
	}

	public Date getInActiveToDate() {
		return inActiveToDate;
	}

	public void setInActiveToDate(Date inActiveToDate) {
		this.inActiveToDate = inActiveToDate;
	}

	public List<SalaryHead> getSalaryHead() {
		return salaryHead;
	}

	public void setSalaryHead(List<SalaryHead> salaryHead) {
		this.salaryHead = salaryHead;
	}

	public void setAnnualCTC(float annualCTC) {
		this.annualCTC = annualCTC;
	}

	public float getMonthlyCTC() {
		return monthlyCTC;
	}

	public void setMonthlyCTC(float monthlyCTC) {
		this.monthlyCTC = monthlyCTC;
	}

	public float getFixed() {
		return fixed;
	}

	public void setFixed(float fixed) {
		this.fixed = fixed * FIXEDRATE;
	}



	public float getFBP() {
		return FBP;
	}

	public void setFBP(float fBP) {
		FBP = fBP;
	}

	public float getPerDiem() {
		return perDiem;
	}

	public void setPerDiem(float perDiem) {
		this.perDiem = perDiem;
	}

	public float getTblb() {
		return tblb;
	}

	public void setTblb(float tblb) {
		this.tblb = tblb * TPLBRATE;
	}

	public void setTblbValue(float tblb) {
		this.tblb = tblb;
	}

	public float getTotCTC() {
		return totCTC;
	}

	public void setTotCTC(float totCTC) {
		this.totCTC = totCTC;
	}

	public float getCtc() {
		return ctc;
	}

	public void setCtc(float ctc) {
		this.ctc = ctc;
	}

	public Salary() {
		super();
	}

	public String getPerdiemFrom() {
		return perdiemFrom;
	}

	public void setPerdiemFrom(String perdiemFrom) {
		this.perdiemFrom = perdiemFrom;
	}

	public String getPerdiemTo() {
		return perdiemTo;
	}

	public void setPerdiemTo(String perdiemTo) {
		this.perdiemTo = perdiemTo;
	}

	public String getPerdiemCurrencyType() {
		return perdiemCurrencyType;
	}

	public void setPerdiemCurrencyType(String perdiemCurrencyType) {
		this.perdiemCurrencyType = perdiemCurrencyType;
	}

	public float getFixedPerdiemAmount() {
		return fixedPerdiemAmount;
	}

	public void setFixedPerdiemAmount(float fixedPerdiemAmount) {
		this.fixedPerdiemAmount = fixedPerdiemAmount;
	}

	public float getRetentionBonus() {
		return retentionBonus;
	}

	public void setRetentionBonus(float retentionBonus) {
		this.retentionBonus = retentionBonus;
	}

	
	private float	totalCtc;

	public float getTotalCtc() {
		return totalCtc;
	}

	public void setTotalCtc(float totalCtc) {
		this.totalCtc = totalCtc;
	}
}
