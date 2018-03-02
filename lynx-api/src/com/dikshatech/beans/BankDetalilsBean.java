package com.dikshatech.beans;

import java.util.Date;

import com.dikshatech.portal.dto.BankDetails;
import com.dikshatech.portal.dto.BankDetailsPk;

public class BankDetalilsBean
{

	/** 
	 * This attribute maps to the column ID in the BANK_DETAILS table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column BANK_NAME in the BANK_DETAILS table.
	 */
	protected String bankName;

	/** 
	 * This attribute maps to the column BANK_ADDRESS in the BANK_DETAILS table.
	 */
	protected String bankAddress;

	/** 
	 * This attribute maps to the column ACCOUNT_NUMBER in the BANK_DETAILS table.
	 */
	protected String accountNumber;

	/** 
	 * This attribute maps to the column IFCI NUMBER in the BANK_DETAILS table.
	 */
	protected String ifciNumber;

	/** 
	 * This attribute maps to the column SWIFT_CODE in the BANK_DETAILS table.
	 */
	protected String swiftCode;

	protected String micrCode;

	/** 
	 * This attribute maps to the column BRANCH in the BANK_DETAILS table.
	 */
	protected String branch;

	/** 
	 * This attribute maps to the column CREATED_BY in the BANK_DETAILS table.
	 */
	protected int createdBy;

	protected String createdByName;
	/** 
	 * This attribute represents whether the primitive attribute createdBy is null.
	 */
	protected boolean createdByNull = true;

	/** 
	 * This attribute maps to the column CREATED_ON in the BANK_DETAILS table.
	 */
	protected String createdOn;

	/**
	 * Method 'getId'
	 * 
	 * @return int
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Method 'getBankName'
	 * 
	 * @return String
	 */
	public String getBankName()
	{
		return bankName;
	}

	/**
	 * Method 'setBankName'
	 * 
	 * @param bankName
	 */
	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	/**
	 * Method 'getBankAddress'
	 * 
	 * @return String
	 */
	public String getBankAddress()
	{
		return bankAddress;
	}

	/**
	 * Method 'setBankAddress'
	 * 
	 * @param bankAddress
	 */
	public void setBankAddress(String bankAddress)
	{
		this.bankAddress = bankAddress;
	}

	/**
	 * Method 'getAccountNumber'
	 * 
	 * @return String
	 */
	public String getAccountNumber()
	{
		return accountNumber;
	}

	/**
	 * Method 'setAccountNumber'
	 * 
	 * @param accountNumber
	 */
	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	/**
	 * Method 'getIfciNumber'
	 * 
	 * @return String
	 */
	public String getIfciNumber()
	{
		return ifciNumber;
	}

	/**
	 * Method 'setIfciNumber'
	 * 
	 * @param ifciNumber
	 */
	public void setIfciNumber(String ifciNumber)
	{
		this.ifciNumber = ifciNumber;
	}

	/**
	 * Method 'getSwiftCode'
	 * 
	 * @return String
	 */
	public String getSwiftCode()
	{
		return swiftCode;
	}

	/**
	 * Method 'setSwiftCode'
	 * 
	 * @param swiftCode
	 */
	public void setSwiftCode(String swiftCode)
	{
		this.swiftCode = swiftCode;
	}

	public String getMicrCode()
	{
		return micrCode;
	}

	/**
	 * Method 'setMicrCode'
	 * 
	 * @param micrCode
	 */
	public void setMicrCode(String micrCode)
	{
		this.micrCode = micrCode;
	}

	/**
	 * Method 'getBranch'
	 * 
	 * @return String
	 */
	public String getBranch()
	{
		return branch;
	}

	/**
	 * Method 'setBranch'
	 * 
	 * @param branch
	 */
	public void setBranch(String branch)
	{
		this.branch = branch;
	}

	/**
	 * Method 'getCreatedBy'
	 * 
	 * @return int
	 */
	public int getCreatedBy()
	{
		return createdBy;
	}

	/**
	 * Method 'setCreatedBy'
	 * 
	 * @param createdBy
	 */
	public void setCreatedBy(int createdBy)
	{
		this.createdBy = createdBy;
		this.createdByNull = false;
	}

	/**
	 * Method 'setCreatedByNull'
	 * 
	 * @param value
	 */
	public void setCreatedByNull(boolean value)
	{
		this.createdByNull = value;
	}

	/**
	 * Method 'isCreatedByNull'
	 * 
	 * @return boolean
	 */
	public boolean isCreatedByNull()
	{
		return createdByNull;
	}

	/**
	 * Method 'getCreatedOn'
	 * 
	 * @return Date
	 */
	public String getCreatedOn()
	{
		return createdOn;
	}

	/**
	 * Method 'setCreatedOn'
	 * 
	 * @param createdOn
	 */
	public void setCreatedOn(String createdOn)
	{
		this.createdOn = createdOn;
	}


	public String getCreatedByName()
	{
		return createdByName;
	}

	public void setCreatedByName(String createdByName)
	{
		this.createdByName = createdByName;
	}


	
}
