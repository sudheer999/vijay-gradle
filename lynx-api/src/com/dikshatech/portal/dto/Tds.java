/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dto;

import com.dikshatech.beans.MonthlyPayrollBean;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.portal.dao.*;
import com.dikshatech.portal.factory.*;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.exceptions.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;

public class Tds extends PortalForm
{
	/** 
	 * This attribute maps to the column ID in the TDS table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column MONTH_ID in the TDS table.
	 */
	protected String monthId;

	/** 
	 * This attribute maps to the column USERID in the TDS table.
	 */
	protected int userid;

	/** 
	 * This attribute represents whether the primitive attribute userid is null.
	 */
	protected boolean useridNull = true;

	/** 
	 * This attribute maps to the column AMOUNT in the TDS table.
	 */
	protected String amount;

	/** 
	 * This attribute maps to the column STATUS in the TDS table.
	 */
	protected int status;

	/** 
	 * This attribute represents whether the primitive attribute status is null.
	 */
	protected boolean statusNull = true;
	
	
	protected String idTds;
	
	

	public String getIdTds() {
		return idTds;
	}

	public void setIdTds(String idTds) {
		this.idTds = idTds;
	}
	protected String component;


	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	/**
	 * Method 'Tds'
	 * 
	 */
	public Tds()
	{
	}

	public Tds(String id, String component, String amount) {
			setIdTds(id);
			setComponent(component);
			setAmount(new DecimalFormat("0.00").format(Float.parseFloat(amount)));
		}
		// TODO Auto-generated constructor stub
	

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
	 * Method 'getMonthId'
	 * 
	 * @return String
	 */
	public String getMonthId()
	{
		return monthId;
	}

	/**
	 * Method 'setMonthId'
	 * 
	 * @param monthId
	 */
	public void setMonthId(String monthId)
	{
		this.monthId = monthId;
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
		this.useridNull = false;
	}

	/**
	 * Method 'setUseridNull'
	 * 
	 * @param value
	 */
	public void setUseridNull(boolean value)
	{
		this.useridNull = value;
	}

	/**
	 * Method 'isUseridNull'
	 * 
	 * @return boolean
	 */
	public boolean isUseridNull()
	{
		return useridNull;
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
	 * Method 'getStatus'
	 * 
	 * @return int
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * Method 'setStatus'
	 * 
	 * @param status
	 */
	public void setStatus(int status)
	{
		this.status = status;
		this.statusNull = false;
	}

	/**
	 * Method 'setStatusNull'
	 * 
	 * @param value
	 */
	public void setStatusNull(boolean value)
	{
		this.statusNull = value;
	}

	/**
	 * Method 'isStatusNull'
	 * 
	 * @return boolean
	 */
	public boolean isStatusNull()
	{
		return statusNull;
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
		
		if (!(_other instanceof Tds)) {
			return false;
		}
		
		final Tds _cast = (Tds) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (monthId == null ? _cast.monthId != monthId : !monthId.equals( _cast.monthId )) {
			return false;
		}
		
		if (userid != _cast.userid) {
			return false;
		}
		
		if (useridNull != _cast.useridNull) {
			return false;
		}
		
		if (amount == null ? _cast.amount != amount : !amount.equals( _cast.amount )) {
			return false;
		}
		
		if (status != _cast.status) {
			return false;
		}
		
		if (statusNull != _cast.statusNull) {
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
		if (monthId != null) {
			_hashCode = 29 * _hashCode + monthId.hashCode();
		}
		
		_hashCode = 29 * _hashCode + userid;
		_hashCode = 29 * _hashCode + (useridNull ? 1 : 0);
		if (amount != null) {
			_hashCode = 29 * _hashCode + amount.hashCode();
		}
		
		_hashCode = 29 * _hashCode + status;
		_hashCode = 29 * _hashCode + (statusNull ? 1 : 0);

		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TdsPk
	 */
	public TdsPk createPk()
	{
		return new TdsPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.Tds: " );
		ret.append( "id=" + id );
		ret.append( ", monthId=" + monthId );
		ret.append( ", userid=" + userid );
		ret.append( ", amount=" + amount );
		ret.append( ", status=" + status );
		return ret.toString();
	}
	public Tds getTdsAmount() {
		try{
			return new Tds(this.idTds + "", this.component, DesEncrypterDecrypter.getInstance().decrypt(this.amount));
		} catch (Exception e){
			e.printStackTrace();
		}
		return new Tds();
	}

}