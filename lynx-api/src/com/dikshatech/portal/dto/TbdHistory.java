/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dto;

import com.dikshatech.portal.dao.*;
import com.dikshatech.portal.factory.*;
import com.dikshatech.portal.exceptions.*;
import java.io.Serializable;
import java.util.*;
import java.util.Date;

public class TbdHistory implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the TBD_HISTORY table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column USERID in the TBD_HISTORY table.
	 */
	protected int userid;

	/** 
	 * This attribute maps to the column BENEFIT_ID in the TBD_HISTORY table.
	 */
	protected int benefitId;

	/** 
	 * This attribute represents whether the primitive attribute benefitId is null.
	 */
	protected boolean benefitIdNull = true;

	/** 
	 * This attribute maps to the column AMOUNT in the TBD_HISTORY table.
	 */
	protected String amount;

	/** 
	 * This attribute maps to the column FINANCIAL_YEAR in the TBD_HISTORY table.
	 */
	protected String financialYear;

	/** 
	 * This attribute maps to the column COMMENTS in the TBD_HISTORY table.
	 */
	protected String comments;

	/** 
	 * This attribute maps to the column UPDATED_ON in the TBD_HISTORY table.
	 */
	protected Date updatedOn;

	protected String benefitName;
	protected String description;
	
	public String getBenefitName() {
		return benefitName;
	}

	public void setBenefitName(String benefitName) {
		this.benefitName = benefitName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Method 'TbdHistory'
	 * 
	 */
	public TbdHistory()
	{
	}

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
	 * Method 'getUserid'
	 * 
	 * @return int
	 */
	public int getUserid()
	{
		return userid;
	}

	/**
	 * Method 'setUserid'
	 * 
	 * @param userid
	 */
	public void setUserid(int userid)
	{
		this.userid = userid;
	}

	/**
	 * Method 'getBenefitId'
	 * 
	 * @return int
	 */
	public int getBenefitId()
	{
		return benefitId;
	}

	/**
	 * Method 'setBenefitId'
	 * 
	 * @param benefitId
	 */
	public void setBenefitId(int benefitId)
	{
		this.benefitId = benefitId;
		this.benefitIdNull = false;
	}

	/**
	 * Method 'setBenefitIdNull'
	 * 
	 * @param value
	 */
	public void setBenefitIdNull(boolean value)
	{
		this.benefitIdNull = value;
	}

	/**
	 * Method 'isBenefitIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isBenefitIdNull()
	{
		return benefitIdNull;
	}

	/**
	 * Method 'getAmount'
	 * 
	 * @return String
	 */
	public String getAmount()
	{
		return amount;
	}

	/**
	 * Method 'setAmount'
	 * 
	 * @param amount
	 */
	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	/**
	 * Method 'getFinancialYear'
	 * 
	 * @return String
	 */
	public String getFinancialYear()
	{
		return financialYear;
	}

	/**
	 * Method 'setFinancialYear'
	 * 
	 * @param financialYear
	 */
	public void setFinancialYear(String financialYear)
	{
		this.financialYear = financialYear;
	}

	/**
	 * Method 'getComments'
	 * 
	 * @return String
	 */
	public String getComments()
	{
		return comments;
	}

	/**
	 * Method 'setComments'
	 * 
	 * @param comments
	 */
	public void setComments(String comments)
	{
		this.comments = comments;
	}

	/**
	 * Method 'getUpdatedOn'
	 * 
	 * @return Date
	 */
	public Date getUpdatedOn()
	{
		return updatedOn;
	}

	/**
	 * Method 'setUpdatedOn'
	 * 
	 * @param updatedOn
	 */
	public void setUpdatedOn(Date updatedOn)
	{
		this.updatedOn = updatedOn;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof TbdHistory)) {
			return false;
		}
		
		final TbdHistory _cast = (TbdHistory) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (userid != _cast.userid) {
			return false;
		}
		
		if (benefitId != _cast.benefitId) {
			return false;
		}
		
		if (benefitIdNull != _cast.benefitIdNull) {
			return false;
		}
		
		if (amount == null ? _cast.amount != amount : !amount.equals( _cast.amount )) {
			return false;
		}
		
		if (financialYear == null ? _cast.financialYear != financialYear : !financialYear.equals( _cast.financialYear )) {
			return false;
		}
		
		if (comments == null ? _cast.comments != comments : !comments.equals( _cast.comments )) {
			return false;
		}
		
		if (updatedOn == null ? _cast.updatedOn != updatedOn : !updatedOn.equals( _cast.updatedOn )) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		_hashCode = 29 * _hashCode + userid;
		_hashCode = 29 * _hashCode + benefitId;
		_hashCode = 29 * _hashCode + (benefitIdNull ? 1 : 0);
		if (amount != null) {
			_hashCode = 29 * _hashCode + amount.hashCode();
		}
		
		if (financialYear != null) {
			_hashCode = 29 * _hashCode + financialYear.hashCode();
		}
		
		if (comments != null) {
			_hashCode = 29 * _hashCode + comments.hashCode();
		}
		
		if (updatedOn != null) {
			_hashCode = 29 * _hashCode + updatedOn.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TbdHistoryPk
	 */
	public TbdHistoryPk createPk()
	{
		return new TbdHistoryPk(id);
	}

	public TaxBenefitDeclaration getTaxBenefitDeclaration(){
		TaxBenefitDeclaration tbd=new TaxBenefitDeclaration();
		
		tbd.setUserid(this.userid);
		tbd.setFinancialYear(this.financialYear);
		tbd.setBenefitId(this.benefitId);
		tbd.setBenefitIdNull(this.benefitIdNull);
		tbd.setAmount(this.amount);
		tbd.setComments(this.comments);
		tbd.setBenefitName(this.benefitName);
		tbd.setDescription(this.description);
		
		return(tbd);
	}
	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.TbdHistory: " );
		ret.append( "id=" + id );
		ret.append( ", userid=" + userid );
		ret.append( ", benefitId=" + benefitId );
		ret.append( ", amount=" + amount );
		ret.append( ", financialYear=" + financialYear );
		ret.append( ", comments=" + comments );
		ret.append( ", updatedOn=" + updatedOn );
		return ret.toString();
	}

}
