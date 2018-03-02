package com.dikshatech.beans;

import java.io.Serializable;

public class FinanceBean implements Serializable
{
	private static final long serialVersionUID = -998803160627317002L;
	private int id;
    private String pfAccNo;

	
	private String panNo;

	private int ctc;

	
	private boolean ctcNull = true;

	
	private String primBankAccNo;

	private String primBankName;

	
	private String secBankAccNo;

	
	private String secBankName;

	
	private String salaryDetail;
	
	
	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
	}


	public String getPfAccNo()
	{
		return pfAccNo;
	}


	public void setPfAccNo(String pfAccNo)
	{
		this.pfAccNo = pfAccNo;
	}


	public String getPanNo()
	{
		return panNo;
	}


	public void setPanNo(String panNo)
	{
		this.panNo = panNo;
	}


	public int getCtc()
	{
		return ctc;
	}


	public void setCtc(int ctc)
	{
		this.ctc = ctc;
	}


	public boolean isCtcNull()
	{
		return ctcNull;
	}


	public void setCtcNull(boolean ctcNull)
	{
		this.ctcNull = ctcNull;
	}


	public String getPrimBankAccNo()
	{
		return primBankAccNo;
	}


	public void setPrimBankAccNo(String primBankAccNo)
	{
		this.primBankAccNo = primBankAccNo;
	}


	public String getPrimBankName()
	{
		return primBankName;
	}


	public void setPrimBankName(String primBankName)
	{
		this.primBankName = primBankName;
	}


	public String getSecBankAccNo()
	{
		return secBankAccNo;
	}


	public void setSecBankAccNo(String secBankAccNo)
	{
		this.secBankAccNo = secBankAccNo;
	}


	public String getSecBankName()
	{
		return secBankName;
	}


	public void setSecBankName(String secBankName)
	{
		this.secBankName = secBankName;
	}


	public String getSalaryDetail()
	{
		return salaryDetail;
	}


	public void setSalaryDetail(String salaryDetail)
	{
		this.salaryDetail = salaryDetail;
	}


	
}
