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
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class LoanEligibilityCriteria extends PortalForm implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the LOAN_ELIGIBILITY_CRITERIA table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column LABEL in the LOAN_ELIGIBILITY_CRITERIA table.
	 */
	protected String label;

	/** 
	 * This attribute maps to the column ELIGIBILITY_AMOUNT in the LOAN_ELIGIBILITY_CRITERIA table.
	 */
	protected String eligibilityAmount;

	/** 
	 * This attribute maps to the column EMI_ELIGIBILITY in the LOAN_ELIGIBILITY_CRITERIA table.
	 */
	protected int emiEligibility;

	/** 
	 * This attribute represents whether the primitive attribute emiEligibility is null.
	 */
	protected boolean emiEligibilityNull = true;

	/** 
	 * This attribute maps to the column MAX_AMOUNT_LIMIT in the LOAN_ELIGIBILITY_CRITERIA table.
	 */
	protected double maxAmountLimit;

	/** 
	 * This attribute represents whether the primitive attribute maxAmountLimit is null.
	 */
	protected boolean maxAmountLimitNull = true;

	/** 
	 * This attribute maps to the column TYPE_ID in the LOAN_ELIGIBILITY_CRITERIA table.
	 */
	protected int typeId;

	/** 
	 * This attribute represents whether the primitive attribute typeId is null.
	 */
	protected boolean typeIdNull = true;

	/**
	 * Method 'LoanEligibilityCriteria'
	 * 
	 */
	public LoanEligibilityCriteria()
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
	 * Method 'getLabel'
	 * 
	 * @return String
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * Method 'setLabel'
	 * 
	 * @param label
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * Method 'getEligibilityAmount'
	 * 
	 * @return String
	 */
	public String getEligibilityAmount()
	{
		return eligibilityAmount;
	}

	/**
	 * Method 'setEligibilityAmount'
	 * 
	 * @param eligibilityAmount
	 */
	public void setEligibilityAmount(String eligibilityAmount)
	{
		this.eligibilityAmount = eligibilityAmount;
	}

	/**
	 * Method 'getEmiEligibility'
	 * 
	 * @return int
	 */
	public int getEmiEligibility()
	{
		return emiEligibility;
	}

	/**
	 * Method 'setEmiEligibility'
	 * 
	 * @param emiEligibility
	 */
	public void setEmiEligibility(int emiEligibility)
	{
		this.emiEligibility = emiEligibility;
		this.emiEligibilityNull = false;
	}

	/**
	 * Method 'setEmiEligibilityNull'
	 * 
	 * @param value
	 */
	public void setEmiEligibilityNull(boolean value)
	{
		this.emiEligibilityNull = value;
	}

	/**
	 * Method 'isEmiEligibilityNull'
	 * 
	 * @return boolean
	 */
	public boolean isEmiEligibilityNull()
	{
		return emiEligibilityNull;
	}

	/**
	 * Method 'getMaxAmountLimit'
	 * 
	 * @return double
	 */
	public double getMaxAmountLimit()
	{
		return maxAmountLimit;
	}

	/**
	 * Method 'setMaxAmountLimit'
	 * 
	 * @param maxAmountLimit
	 */
	public void setMaxAmountLimit(double maxAmountLimit)
	{
		this.maxAmountLimit = maxAmountLimit;
		this.maxAmountLimitNull = false;
	}

	/**
	 * Method 'setMaxAmountLimitNull'
	 * 
	 * @param value
	 */
	public void setMaxAmountLimitNull(boolean value)
	{
		this.maxAmountLimitNull = value;
	}

	/**
	 * Method 'isMaxAmountLimitNull'
	 * 
	 * @return boolean
	 */
	public boolean isMaxAmountLimitNull()
	{
		return maxAmountLimitNull;
	}

	/**
	 * Method 'getTypeId'
	 * 
	 * @return int
	 */
	public int getTypeId()
	{
		return typeId;
	}

	/**
	 * Method 'setTypeId'
	 * 
	 * @param typeId
	 */
	public void setTypeId(int typeId)
	{
		this.typeId = typeId;
		this.typeIdNull = false;
	}

	/**
	 * Method 'setTypeIdNull'
	 * 
	 * @param value
	 */
	public void setTypeIdNull(boolean value)
	{
		this.typeIdNull = value;
	}

	/**
	 * Method 'isTypeIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isTypeIdNull()
	{
		return typeIdNull;
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
		
		if (!(_other instanceof LoanEligibilityCriteria)) {
			return false;
		}
		
		final LoanEligibilityCriteria _cast = (LoanEligibilityCriteria) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (label == null ? _cast.label != label : !label.equals( _cast.label )) {
			return false;
		}
		
		if (eligibilityAmount == null ? _cast.eligibilityAmount != eligibilityAmount : !eligibilityAmount.equals( _cast.eligibilityAmount )) {
			return false;
		}
		
		if (emiEligibility != _cast.emiEligibility) {
			return false;
		}
		
		if (emiEligibilityNull != _cast.emiEligibilityNull) {
			return false;
		}
		
		if (maxAmountLimit != _cast.maxAmountLimit) {
			return false;
		}
		
		if (maxAmountLimitNull != _cast.maxAmountLimitNull) {
			return false;
		}
		
		if (typeId != _cast.typeId) {
			return false;
		}
		
		if (typeIdNull != _cast.typeIdNull) {
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
		if (label != null) {
			_hashCode = 29 * _hashCode + label.hashCode();
		}
		
		if (eligibilityAmount != null) {
			_hashCode = 29 * _hashCode + eligibilityAmount.hashCode();
		}
		
		_hashCode = 29 * _hashCode + emiEligibility;
		_hashCode = 29 * _hashCode + (emiEligibilityNull ? 1 : 0);
		long temp_maxAmountLimit = Double.doubleToLongBits(maxAmountLimit);
		_hashCode = 29 * _hashCode + (int) (temp_maxAmountLimit ^ (temp_maxAmountLimit >>> 32));
		_hashCode = 29 * _hashCode + (maxAmountLimitNull ? 1 : 0);
		_hashCode = 29 * _hashCode + typeId;
		_hashCode = 29 * _hashCode + (typeIdNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return LoanEligibilityCriteriaPk
	 */
	public LoanEligibilityCriteriaPk createPk()
	{
		return new LoanEligibilityCriteriaPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.LoanEligibilityCriteria: " );
		ret.append( "id=" + id );
		ret.append( ", label=" + label );
		ret.append( ", eligibilityAmount=" + eligibilityAmount );
		ret.append( ", emiEligibility=" + emiEligibility );
		ret.append( ", maxAmountLimit=" + maxAmountLimit );
		ret.append( ", typeId=" + typeId );
		return ret.toString();
	}


	private String lonTyp; 
	public String getLonTyp()
	{
		return lonTyp;
	}

	public void setLonTyp(String lonTyp)
	{
		this.lonTyp = lonTyp;
	}
}
