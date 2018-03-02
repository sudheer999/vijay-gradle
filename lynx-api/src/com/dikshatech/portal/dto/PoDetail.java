package com.dikshatech.portal.dto;

import java.io.Serializable;



import java.util.Date;

import com.dikshatech.portal.forms.PortalForm;

public class PoDetail extends PortalForm implements Serializable {
	
	protected int empPoId;

	protected int empProjId;
	
	public int getEmpProjId() {
		return empProjId;
	}

	public void setEmpProjId(int empProjId) {
		this.empProjId = empProjId;
	}

	protected String empPoNumber;
	
	protected Date empPoDate;
	
	protected Date empPoStDate;
	
	protected Date empPoEndDate;
	
	protected String empPoDuration;
	
	protected String empPaymentTerms;
	
	protected String rate;
	
	protected short isDisable;
	
	protected boolean isDisableNull = true;
	
	
	
	
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public boolean isDisableNull() {
		return isDisableNull;
	}

	public void setDisableNull(boolean isDisableNull) {
		this.isDisableNull = isDisableNull;
	}

	public short getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(short isDisable) {
		this.isDisable = isDisable;
	}

	public PoDetail(){
		
	}

	public int getEmpPoId() {
		return empPoId;
	}

	public void setEmpPoId(int empPoId) {
		this.empPoId = empPoId;
	}

	public String getEmpPoNumber() {
		return empPoNumber;
	}

	public void setEmpPoNumber(String empPoNumber) {
		this.empPoNumber = empPoNumber;
	}

	public Date getEmpPoDate() {
		return empPoDate;
	}

	public void setEmpPoDate(Date empPoDate) {
		this.empPoDate = empPoDate;
	}

	public Date getEmpPoStDate() {
		return empPoStDate;
	}

	public void setEmpPoStDate(Date empPoStDate) {
		this.empPoStDate = empPoStDate;
	}

	public Date getEmpPoEndDate() {
		return empPoEndDate;
	}

	public void setEmpPoEndDate(Date empPoEndDate) {
		this.empPoEndDate = empPoEndDate;
	}

	public String getEmpPoDuration() {
		return empPoDuration;
	}

	public void setEmpPoDuration(String empPoDuration) {
		this.empPoDuration = empPoDuration;
	}

	public String getEmpPaymentTerms() {
		return empPaymentTerms;
	}

	public void setEmpPaymentTerms(String empPaymentTerms) {
		this.empPaymentTerms = empPaymentTerms;
	}

	public PoDetailsPk createPk()
	{
		return new PoDetailsPk(empPoId);
	}

	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.PoDetail: " );
		ret.append( "empPoId=" + empPoId );
		ret.append( ", empProjId=" + empProjId );
		ret.append( ", empPoNumber=" + empPoNumber );
		ret.append( ", empPoDate=" + empPoDate );
		ret.append( ", empPoStDate=" + empPoStDate );
		ret.append( ", empPoEndDate=" + empPoEndDate );
		ret.append( ", empPoDuration=" + empPoDuration );

		ret.append( ", isDisable=" + isDisable );
		return ret.toString();
	}
	
	
	
}
