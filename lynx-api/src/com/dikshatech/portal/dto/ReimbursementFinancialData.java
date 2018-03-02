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

public class ReimbursementFinancialData implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the REIMBURSEMENT_FINANCIAL_DATA table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column ESRMAP_ID in the REIMBURSEMENT_FINANCIAL_DATA table.
	 */
	protected int esrmapId;

	/** 
	 * This attribute represents whether the primitive attribute esrmapId is null.
	 */
	protected boolean esrmapIdNull = true;

	/** 
	 * This attribute maps to the column DATE_OF_OCCURRENCE in the REIMBURSEMENT_FINANCIAL_DATA table.
	 */
	protected Date dateOfOccurrence;

	/** 
	 * This attribute maps to the column SUMMARY in the REIMBURSEMENT_FINANCIAL_DATA table.
	 */
	protected String summary;

	/** 
	 * This attribute maps to the column TYPE in the REIMBURSEMENT_FINANCIAL_DATA table.
	 */
	protected String type;

	/** 
	 * This attribute maps to the column AMOUNT in the REIMBURSEMENT_FINANCIAL_DATA table.
	 */
	protected String amount;

	/** 
	 * This attribute maps to the column CURRENCY in the REIMBURSEMENT_FINANCIAL_DATA table.
	 */
	protected String currency;

	/** 
	 * This attribute maps to the column RECEIPTS_AVAILABLE in the REIMBURSEMENT_FINANCIAL_DATA table.
	 */
	protected String receiptsAvailable;

	/** 
	 * This attribute maps to the column UPLOAD_RECEIPTS_ID in the REIMBURSEMENT_FINANCIAL_DATA table.
	 */
	protected int uploadReceiptsId;

	/** 
	 * This attribute represents whether the primitive attribute uploadReceiptsId is null.
	 */
	protected boolean uploadReceiptsIdNull = true;
    protected String			famount;
	// fields required for receive all
	String			esrReqId;
	Date				requestedOn;
	String			status;
	int				reqId;
	String			projectName;
	String			chargeCode;
	String			fileDescription;
	
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Method 'ReimbursementFinancialData'
	 * 
	 */
	public ReimbursementFinancialData()
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
	 * Method 'getEsrmapId'
	 * 
	 * @return int
	 */
	public int getEsrmapId()
	{
		return esrmapId;
	}

	/**
	 * Method 'setEsrmapId'
	 * 
	 * @param esrmapId
	 */
	public void setEsrmapId(int esrmapId)
	{
		this.esrmapId = esrmapId;
		this.esrmapIdNull = false;
	}

	/**
	 * Method 'setEsrmapIdNull'
	 * 
	 * @param value
	 */
	public void setEsrmapIdNull(boolean value)
	{
		this.esrmapIdNull = value;
	}

	/**
	 * Method 'isEsrmapIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isEsrmapIdNull()
	{
		return esrmapIdNull;
	}

	/**
	 * Method 'getDateOfOccurrence'
	 * 
	 * @return Date
	 */
	public Date getDateOfOccurrence()
	{
		return dateOfOccurrence;
	}

	/**
	 * Method 'setDateOfOccurrence'
	 * 
	 * @param dateOfOccurrence
	 */
	public void setDateOfOccurrence(Date dateOfOccurrence)
	{
		this.dateOfOccurrence = dateOfOccurrence;
	}

	/**
	 * Method 'getSummary'
	 * 
	 * @return String
	 */
	public String getSummary()
	{
		return summary;
	}

	/**
	 * Method 'setSummary'
	 * 
	 * @param summary
	 */
	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	/**
	 * Method 'getType'
	 * 
	 * @return String
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Method 'setType'
	 * 
	 * @param type
	 */
	public void setType(String type)
	{
		this.type = type;
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
	 * Method 'getCurrency'
	 * 
	 * @return String
	 */
	public String getCurrency()
	{
		return currency;
	}

	/**
	 * Method 'setCurrency'
	 * 
	 * @param currency
	 */
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	/**
	 * Method 'getReceiptsAvailable'
	 * 
	 * @return String
	 */
	public String getReceiptsAvailable()
	{
		return receiptsAvailable;
	}

	/**
	 * Method 'setReceiptsAvailable'
	 * 
	 * @param receiptsAvailable
	 */
	public void setReceiptsAvailable(String receiptsAvailable)
	{
		this.receiptsAvailable = receiptsAvailable;
	}

	/**
	 * Method 'getUploadReceiptsId'
	 * 
	 * @return int
	 */
	public int getUploadReceiptsId()
	{
		return uploadReceiptsId;
	}

	public String getFamount()
	{
		return famount;
	}

	public void setFamount(String famount)
	{
		this.famount = famount;
	}

	public String getEsrReqId()
	{
		return esrReqId;
	}

	public void setEsrReqId(String esrReqId)
	{
		this.esrReqId = esrReqId;
	}

	public Date getRequestedOn()
	{
		return requestedOn;
	}

	public void setRequestedOn(Date requestedOn)
	{
		this.requestedOn = requestedOn;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public int getReqId()
	{
		return reqId;
	}

	public void setReqId(int reqId)
	{
		this.reqId = reqId;
	}

	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	public String getChargeCode()
	{
		return chargeCode;
	}

	public void setChargeCode(String chargeCode)
	{
		this.chargeCode = chargeCode;
	}

	public String getFileDescription()
	{
		return fileDescription;
	}

	public void setFileDescription(String fileDescription)
	{
		this.fileDescription = fileDescription;
	}

	/**
	 * Method 'setUploadReceiptsId'
	 * 
	 * @param uploadReceiptsId
	 */
	public void setUploadReceiptsId(int uploadReceiptsId)
	{
		this.uploadReceiptsId = uploadReceiptsId;
		this.uploadReceiptsIdNull = false;
	}

	/**
	 * Method 'setUploadReceiptsIdNull'
	 * 
	 * @param value
	 */
	public void setUploadReceiptsIdNull(boolean value)
	{
		this.uploadReceiptsIdNull = value;
	}

	/**
	 * Method 'isUploadReceiptsIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isUploadReceiptsIdNull()
	{
		return uploadReceiptsIdNull;
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
		
		if (!(_other instanceof ReimbursementFinancialData)) {
			return false;
		}
		
		final ReimbursementFinancialData _cast = (ReimbursementFinancialData) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (esrmapId != _cast.esrmapId) {
			return false;
		}
		
		if (esrmapIdNull != _cast.esrmapIdNull) {
			return false;
		}
		
		if (dateOfOccurrence == null ? _cast.dateOfOccurrence != dateOfOccurrence : !dateOfOccurrence.equals( _cast.dateOfOccurrence )) {
			return false;
		}
		
		if (summary == null ? _cast.summary != summary : !summary.equals( _cast.summary )) {
			return false;
		}
		
		if (type == null ? _cast.type != type : !type.equals( _cast.type )) {
			return false;
		}
		
		if (amount == null ? _cast.amount != amount : !amount.equals( _cast.amount )) {
			return false;
		}
		
		if (currency == null ? _cast.currency != currency : !currency.equals( _cast.currency )) {
			return false;
		}
		
		if (receiptsAvailable == null ? _cast.receiptsAvailable != receiptsAvailable : !receiptsAvailable.equals( _cast.receiptsAvailable )) {
			return false;
		}
		
		if (uploadReceiptsId != _cast.uploadReceiptsId) {
			return false;
		}
		
		if (uploadReceiptsIdNull != _cast.uploadReceiptsIdNull) {
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
		_hashCode = 29 * _hashCode + esrmapId;
		_hashCode = 29 * _hashCode + (esrmapIdNull ? 1 : 0);
		if (dateOfOccurrence != null) {
			_hashCode = 29 * _hashCode + dateOfOccurrence.hashCode();
		}
		
		if (summary != null) {
			_hashCode = 29 * _hashCode + summary.hashCode();
		}
		
		if (type != null) {
			_hashCode = 29 * _hashCode + type.hashCode();
		}
		
		if (amount != null) {
			_hashCode = 29 * _hashCode + amount.hashCode();
		}
		
		if (currency != null) {
			_hashCode = 29 * _hashCode + currency.hashCode();
		}
		
		if (receiptsAvailable != null) {
			_hashCode = 29 * _hashCode + receiptsAvailable.hashCode();
		}
		
		_hashCode = 29 * _hashCode + uploadReceiptsId;
		_hashCode = 29 * _hashCode + (uploadReceiptsIdNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ReimbursementFinancialDataPk
	 */
	public ReimbursementFinancialDataPk createPk()
	{
		return new ReimbursementFinancialDataPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.ReimbursementFinancialData: " );
		ret.append( "id=" + id );
		ret.append( ", esrmapId=" + esrmapId );
		ret.append( ", dateOfOccurrence=" + dateOfOccurrence );
		ret.append( ", summary=" + summary );
		ret.append( ", type=" + type );
		ret.append( ", amount=" + amount );
		ret.append( ", currency=" + currency );
		ret.append( ", receiptsAvailable=" + receiptsAvailable );
		ret.append( ", uploadReceiptsId=" + uploadReceiptsId );
		return ret.toString();
	}
	public static ReimbursementFinancialData getReimbursementFinancialData(ReimbursementFinancialData data)
	{
		ReimbursementFinancialData dto = new ReimbursementFinancialData();
		dto.setId(data.getId());
		dto.setEsrmapId(data.getEsrmapId());
		dto.setDateOfOccurrence(data.getDateOfOccurrence());
		dto.setSummary(data.getSummary());
		dto.setType(data.getType());
		dto.setFamount((data.getAmount()));
		dto.setCurrency(data.getCurrency());
		dto.setReceiptsAvailable(data.getReceiptsAvailable());
		dto.setUploadReceiptsId(data.getUploadReceiptsId());
		if(data.getUploadReceiptsId()>0){
		DocumentsDao documentsDao = DocumentsDaoFactory.create();
		try
		{
			Documents doc=documentsDao.findByPrimaryKey(data.getUploadReceiptsId());
			dto.setFileDescription(doc.getDescriptions());
			dto.setFileName(doc.getFilename());
		}
		catch (DocumentsDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

		return dto;
	}
}
