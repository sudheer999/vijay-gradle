/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dto;

import java.io.Serializable;

import com.dikshatech.portal.forms.PortalForm;

public class DocumentMapping extends PortalForm implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the DOCUMENT_MAPPING table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column DOCUMENT_ID in the DOCUMENT_MAPPING table.
	 */
	protected int documentId;

	/** 
	 * This attribute represents whether the primitive attribute documentId is null.
	 */
	protected boolean documentIdNull = true;

	/** 
	 * This attribute maps to the column EXPERIENCE_ID in the DOCUMENT_MAPPING table.
	 */
	protected int experienceId;

	/** 
	 * This attribute represents whether the primitive attribute experienceId is null.
	 */
	protected boolean experienceIdNull = true;

	/** 
	 * This attribute maps to the column EDUCATION_ID in the DOCUMENT_MAPPING table.
	 */
	protected int educationId;

	/** 
	 * This attribute represents whether the primitive attribute educationId is null.
	 */
	protected boolean educationIdNull = true;

	/** 
	 * This attribute maps to the column PASSPORT_ID in the DOCUMENT_MAPPING table.
	 */
	protected int passportId;

	/** 
	 * This attribute represents whether the primitive attribute passportId is null.
	 */
	protected boolean passportIdNull = true;

	/** 
	 * This attribute maps to the column FINANCE_ID in the DOCUMENT_MAPPING table.
	 */
	protected int financeId;
	protected int userSeprationId;

	public int getUserSeprationId() {
		return userSeprationId;
	}

	public void setUserSeprationId(int userSeprationId) {
		this.userSeprationId = userSeprationId;
	}

	/** 
	 * This attribute represents whether the primitive attribute financeId is null.
	 */
	protected boolean financeIdNull = true;
	
	
	protected int backGroundVerificationId;

	
	
	
	
	public int getBackGroundVerificationId() {
		return backGroundVerificationId;
	}

	public void setBackGroundVerificationId(int backGroundVerificationId) {
		this.backGroundVerificationId = backGroundVerificationId;
	}

	/**
	 * Method 'DocumentMapping'
	 * 
	 */
	public DocumentMapping()
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
	 * Method 'getDocumentId'
	 * 
	 * @return int
	 */
	public int getDocumentId()
	{
		return documentId;
	}

	/**
	 * Method 'setDocumentId'
	 * 
	 * @param documentId
	 */
	public void setDocumentId(int documentId)
	{
		this.documentId = documentId;
		this.documentIdNull = false;
	}

	/**
	 * Method 'setDocumentIdNull'
	 * 
	 * @param value
	 */
	public void setDocumentIdNull(boolean value)
	{
		this.documentIdNull = value;
	}

	/**
	 * Method 'isDocumentIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isDocumentIdNull()
	{
		return documentIdNull;
	}

	/**
	 * Method 'getExperienceId'
	 * 
	 * @return int
	 */
	public int getExperienceId()
	{
		return experienceId;
	}

	/**
	 * Method 'setExperienceId'
	 * 
	 * @param experienceId
	 */
	public void setExperienceId(int experienceId)
	{
		this.experienceId = experienceId;
		this.experienceIdNull = false;
	}

	/**
	 * Method 'setExperienceIdNull'
	 * 
	 * @param value
	 */
	public void setExperienceIdNull(boolean value)
	{
		this.experienceIdNull = value;
	}

	/**
	 * Method 'isExperienceIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isExperienceIdNull()
	{
		return experienceIdNull;
	}

	/**
	 * Method 'getEducationId'
	 * 
	 * @return int
	 */
	public int getEducationId()
	{
		return educationId;
	}

	/**
	 * Method 'setEducationId'
	 * 
	 * @param educationId
	 */
	public void setEducationId(int educationId)
	{
		this.educationId = educationId;
		this.educationIdNull = false;
	}

	/**
	 * Method 'setEducationIdNull'
	 * 
	 * @param value
	 */
	public void setEducationIdNull(boolean value)
	{
		this.educationIdNull = value;
	}

	/**
	 * Method 'isEducationIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isEducationIdNull()
	{
		return educationIdNull;
	}

	/**
	 * Method 'getPassportId'
	 * 
	 * @return int
	 */
	public int getPassportId()
	{
		return passportId;
	}

	/**
	 * Method 'setPassportId'
	 * 
	 * @param passportId
	 */
	public void setPassportId(int passportId)
	{
		this.passportId = passportId;
		this.passportIdNull = false;
	}

	/**
	 * Method 'setPassportIdNull'
	 * 
	 * @param value
	 */
	public void setPassportIdNull(boolean value)
	{
		this.passportIdNull = value;
	}

	/**
	 * Method 'isPassportIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isPassportIdNull()
	{
		return passportIdNull;
	}

	/**
	 * Method 'getFinanceId'
	 * 
	 * @return int
	 */
	public int getFinanceId()
	{
		return financeId;
	}

	/**
	 * Method 'setFinanceId'
	 * 
	 * @param financeId
	 */
	public void setFinanceId(int financeId)
	{
		this.financeId = financeId;
		this.financeIdNull = false;
	}

	/**
	 * Method 'setFinanceIdNull'
	 * 
	 * @param value
	 */
	public void setFinanceIdNull(boolean value)
	{
		this.financeIdNull = value;
	}

	/**
	 * Method 'isFinanceIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isFinanceIdNull()
	{
		return financeIdNull;
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
		
		if (!(_other instanceof DocumentMapping)) {
			return false;
		}
		
		final DocumentMapping _cast = (DocumentMapping) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (documentId != _cast.documentId) {
			return false;
		}
		
		if (documentIdNull != _cast.documentIdNull) {
			return false;
		}
		
		if (experienceId != _cast.experienceId) {
			return false;
		}
		
		if (experienceIdNull != _cast.experienceIdNull) {
			return false;
		}
		
		if (educationId != _cast.educationId) {
			return false;
		}
		
		if (educationIdNull != _cast.educationIdNull) {
			return false;
		}
		
		if (passportId != _cast.passportId) {
			return false;
		}
		
		if (passportIdNull != _cast.passportIdNull) {
			return false;
		}
		
		if (financeId != _cast.financeId) {
			return false;
		}
		
		if (financeIdNull != _cast.financeIdNull) {
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
		_hashCode = 29 * _hashCode + documentId;
		_hashCode = 29 * _hashCode + (documentIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + experienceId;
		_hashCode = 29 * _hashCode + (experienceIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + educationId;
		_hashCode = 29 * _hashCode + (educationIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + passportId;
		_hashCode = 29 * _hashCode + (passportIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + financeId;
		_hashCode = 29 * _hashCode + (financeIdNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DocumentMappingPk
	 */
	public DocumentMappingPk createPk()
	{
		return new DocumentMappingPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.DocumentMapping: " );
		ret.append( "id=" + id );
		ret.append( ", documentId=" + documentId );
		ret.append( ", experienceId=" + experienceId );
		ret.append( ", educationId=" + educationId );
		ret.append( ", passportId=" + passportId );
		ret.append( ", financeId=" + financeId );
		return ret.toString();
	}

}