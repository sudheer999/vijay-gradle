/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.Date;

import com.dikshatech.portal.forms.PortalForm;

public class PersonalInfo extends PortalForm implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the PERSONAL_INFO table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column PERMANENT_ADDRESS in the PERSONAL_INFO table.
	 */
	protected int permanentAddress;

	/** 
	 * This attribute represents whether the primitive attribute permanentAddress is null.
	 */
	protected boolean permanentAddressNull = true;

	/** 
	 * This attribute maps to the column CURRENT_ADDRESS in the PERSONAL_INFO table.
	 */
	protected int currentAddress;

	/** 
	 * This attribute represents whether the primitive attribute currentAddress is null.
	 */
	protected boolean currentAddressNull = true;

	/** 
	 * This attribute maps to the column PRIMARY_PHONE_NO in the PERSONAL_INFO table.
	 */
	protected String primaryPhoneNo;

   /**
	 * This attribute represents whether the primitive attribute primaryPhoneNo
	 * is null.
	 */
	protected boolean primaryPhoneNoNull = true;
	/** 
	 * This attribute maps to the column SECONDARY_PHONE_NO in the PERSONAL_INFO table.
	 */
	protected String secondaryPhoneNo;
   /**
	 * This attribute represents whether the primitive attribute
	 * secondaryPhoneNo is null.
	 */
	protected boolean secondaryPhoneNoNull = true;
	/** 
	 * This attribute maps to the column PERSONAL_EMAIL_ID in the PERSONAL_INFO table.
	 */
	protected String personalEmailId;

	/** 
	 * This attribute maps to the column ALTERNATE_EMAIL_ID in the PERSONAL_INFO table.
	 */
	protected String alternateEmailId;

	/** 
	 * This attribute maps to the column MOTHER_NAME in the PERSONAL_INFO table.
	 */
	protected String motherName;

	/** 
	 * This attribute maps to the column FATHER_NAME in the PERSONAL_INFO table.
	 */
	protected String fatherName;

	/** 
	 * This attribute maps to the column MARITAL_STATUS in the PERSONAL_INFO table.
	 */
	protected String maritalStatus;

	/** 
	 * This attribute maps to the column SPOUSE_NAME in the PERSONAL_INFO table.
	 */
	protected String spouseName;

	/** 
	 * This attribute maps to the column EMER_CONTACT_NAME in the PERSONAL_INFO table.
	 */
	protected String emerContactName;

	/** 
	 * This attribute maps to the column EMER_CP_RELATIONSHIP in the PERSONAL_INFO table.
	 */
	protected String emerCpRelationship;

	/** 
	 * This attribute maps to the column EMER_PHONE_NO in the PERSONAL_INFO table.
	 */
	protected String emerPhoneNo;

	/** 
	 * This attribute represents whether the primitive attribute emerPhoneNo is null.
	 */
	protected boolean emerPhoneNoNull = true;

	/** 
	 * This attribute maps to the column CITY in the PERSONAL_INFO table.
	 */
	protected String city;

	/** 
	 * This attribute maps to the column ZIP_CODE in the PERSONAL_INFO table.
	 */
	protected int zipCode;

	/** 
	 * This attribute represents whether the primitive attribute zipCode is null.
	 */
	protected boolean zipCodeNull = true;

	/** 
	 * This attribute maps to the column COUNTRY in the PERSONAL_INFO table.
	 */
	protected String country;

	/** 
	 * This attribute maps to the column STATE in the PERSONAL_INFO table.
	 */
	protected String state;

	/** 
	 * This attribute maps to the column SPOUSE_DOB in the PERSONAL_INFO table.
	 */
	protected Date spouseDob;

    protected String permAddress;

	protected String currAddress;
	
	
	/**
	 * This attribute maps to the column MODIFIED_BY in the PROFILE_INFO table.
	 */
	protected int	modifiedBy;
	
	
	protected int	userId;
	
	
	protected String bloodGroup;

	public String getBloodGroup() {
		return bloodGroup;
		//return "O+ve";
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	
	/**
	 * Method 'PersonalInfo'
	 * 
	 */
	public PersonalInfo()
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
	 * Method 'getPermanentAddress'
	 * 
	 * @return int
	 */
	public int getPermanentAddress()
	{
		return permanentAddress;
	}

	/**
	 * Method 'setPermanentAddress'
	 * 
	 * @param permanentAddress
	 */
	public void setPermanentAddress(int permanentAddress)
	{
		this.permanentAddress = permanentAddress;
		this.permanentAddressNull = false;
	}

	/**
	 * Method 'setPermanentAddressNull'
	 * 
	 * @param value
	 */
	public void setPermanentAddressNull(boolean value)
	{
		this.permanentAddressNull = value;
	}

	/**
	 * Method 'isPermanentAddressNull'
	 * 
	 * @return boolean
	 */
	public boolean isPermanentAddressNull()
	{
		return permanentAddressNull;
	}

	/**
	 * Method 'getCurrentAddress'
	 * 
	 * @return int
	 */
	public int getCurrentAddress()
	{
		return currentAddress;
	}

	/**
	 * Method 'setCurrentAddress'
	 * 
	 * @param currentAddress
	 */
	public void setCurrentAddress(int currentAddress)
	{
		this.currentAddress = currentAddress;
		this.currentAddressNull = false;
	}

	/**
	 * Method 'setCurrentAddressNull'
	 * 
	 * @param value
	 */
	public void setCurrentAddressNull(boolean value)
	{
		this.currentAddressNull = value;
	}

	/**
	 * Method 'isCurrentAddressNull'
	 * 
	 * @return boolean
	 */
	public boolean isCurrentAddressNull()
	{
		return currentAddressNull;
	}

	/**
	 * Method 'getPrimaryPhoneNo'
	 * 
	 * @return String
	 */
	public String getPrimaryPhoneNo()
	{
		return primaryPhoneNo;
	}

	/**
	 * Method 'setPrimaryPhoneNo'
	 * 
	 * @param primaryPhoneNo
	 */
	public void setPrimaryPhoneNo(String primaryPhoneNo)
	{
		this.primaryPhoneNo = primaryPhoneNo;
		this.primaryPhoneNoNull = false;
	}
   /**
	 * Method 'setPrimaryPhoneNoNull'
	 * 
	 * @param value
	 */
	public void setPrimaryPhoneNoNull(boolean value)
	{
		this.primaryPhoneNoNull = value;
	}

	/**
	 * Method 'isPrimaryPhoneNoNull'
	 * 
	 * @return boolean
	 */
	public boolean isPrimaryPhoneNoNull()
	{
		return primaryPhoneNoNull;
	}
	/**
	 * Method 'getSecondaryPhoneNo'
	 * 
	 * @return String
	 */
	public String getSecondaryPhoneNo()
	{
		return secondaryPhoneNo;
	}

	/**
	 * Method 'setSecondaryPhoneNo'
	 * 
	 * @param secondaryPhoneNo
	 */
	public void setSecondaryPhoneNo(String secondaryPhoneNo)
	{
		this.secondaryPhoneNo = secondaryPhoneNo;
	}

	/**
	 * Method 'getPersonalEmailId'
	 * 
	 * @return String
	 */
	public String getPersonalEmailId()
	{
		return personalEmailId;
	}
     /**
	 * Method 'setSecondaryPhoneNoNull'
	 * 
	 * @param value
	 */
	public void setSecondaryPhoneNoNull(boolean value)
	{
		this.secondaryPhoneNoNull = value;
	}

	/**
	 * Method 'isSecondaryPhoneNoNull'
	 * 
	 * @return boolean
	 */
	public boolean isSecondaryPhoneNoNull()
	{
		return secondaryPhoneNoNull;
	}
     
	/**
	 * Method 'setPersonalEmailId'
	 * 
	 * @param personalEmailId
	 */
	public void setPersonalEmailId(String personalEmailId)
	{
		this.personalEmailId = personalEmailId;
	}

	/**
	 * Method 'getAlternateEmailId'
	 * 
	 * @return String
	 */
	public String getAlternateEmailId()
	{
		return alternateEmailId;
	}

	/**
	 * Method 'setAlternateEmailId'
	 * 
	 * @param alternateEmailId
	 */
	public void setAlternateEmailId(String alternateEmailId)
	{
		this.alternateEmailId = alternateEmailId;
	}

	/**
	 * Method 'getMotherName'
	 * 
	 * @return String
	 */
	public String getMotherName()
	{
		return motherName;
	}

	/**
	 * Method 'setMotherName'
	 * 
	 * @param motherName
	 */
	public void setMotherName(String motherName)
	{
		this.motherName = motherName;
	}

	/**
	 * Method 'getFatherName'
	 * 
	 * @return String
	 */
	public String getFatherName()
	{
		return fatherName;
	}

	/**
	 * Method 'setFatherName'
	 * 
	 * @param fatherName
	 */
	public void setFatherName(String fatherName)
	{
		this.fatherName = fatherName;
	}

	/**
	 * Method 'getMaritalStatus'
	 * 
	 * @return String
	 */
	public String getMaritalStatus()
	{
		return maritalStatus;
	}

	/**
	 * Method 'setMaritalStatus'
	 * 
	 * @param maritalStatus
	 */
	public void setMaritalStatus(String maritalStatus)
	{
		this.maritalStatus = maritalStatus;
	}

	/**
	 * Method 'getSpouseName'
	 * 
	 * @return String
	 */
	public String getSpouseName()
	{
		return spouseName;
	}

	/**
	 * Method 'setSpouseName'
	 * 
	 * @param spouseName
	 */
	public void setSpouseName(String spouseName)
	{
		this.spouseName = spouseName;
	}

	/**
	 * Method 'getEmerContactName'
	 * 
	 * @return String
	 */
	public String getEmerContactName()
	{
		return emerContactName;
	}

	/**
	 * Method 'setEmerContactName'
	 * 
	 * @param emerContactName
	 */
	public void setEmerContactName(String emerContactName)
	{
		this.emerContactName = emerContactName;
	}

	/**
	 * Method 'getEmerCpRelationship'
	 * 
	 * @return String
	 */
	public String getEmerCpRelationship()
	{
		return emerCpRelationship;
	}

	/**
	 * Method 'setEmerCpRelationship'
	 * 
	 * @param emerCpRelationship
	 */
	public void setEmerCpRelationship(String emerCpRelationship)
	{
		this.emerCpRelationship = emerCpRelationship;
	}

	/**
	 * Method 'getEmerPhoneNo'
	 * 
	 * @return String
	 */
	public String getEmerPhoneNo()
	{
		return emerPhoneNo;
	}

	/**
	 * Method 'setEmerPhoneNo'
	 * 
	 * @param emerPhoneNo
	 */
	public void setEmerPhoneNo(String emerPhoneNo)
	{
		this.emerPhoneNo = emerPhoneNo;
		this.emerPhoneNoNull = false;
	}

	/**
	 * Method 'setEmerPhoneNoNull'
	 * 
	 * @param value
	 */
	public void setEmerPhoneNoNull(boolean value)
	{
		this.emerPhoneNoNull = value;
	}

	/**
	 * Method 'isEmerPhoneNoNull'
	 * 
	 * @return boolean
	 */
	public boolean isEmerPhoneNoNull()
	{
		return emerPhoneNoNull;
	}

	/**
	 * Method 'getCity'
	 * 
	 * @return String
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * Method 'setCity'
	 * 
	 * @param city
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * Method 'getZipCode'
	 * 
	 * @return int
	 */
	public int getZipCode()
	{
		return zipCode;
	}

	/**
	 * Method 'setZipCode'
	 * 
	 * @param zipCode
	 */
	public void setZipCode(int zipCode)
	{
		this.zipCode = zipCode;
		this.zipCodeNull = false;
	}

	/**
	 * Method 'setZipCodeNull'
	 * 
	 * @param value
	 */
	public void setZipCodeNull(boolean value)
	{
		this.zipCodeNull = value;
	}

	/**
	 * Method 'isZipCodeNull'
	 * 
	 * @return boolean
	 */
	public boolean isZipCodeNull()
	{
		return zipCodeNull;
	}

	/**
	 * Method 'getCountry'
	 * 
	 * @return String
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * Method 'setCountry'
	 * 
	 * @param country
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	/**
	 * Method 'getState'
	 * 
	 * @return String
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * Method 'setState'
	 * 
	 * @param state
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * Method 'getSpouseDob'
	 * 
	 * @return Date
	 */
	public Date getSpouseDob()
	{
		return spouseDob;
	}

	/**
	 * Method 'setSpouseDob'
	 * 
	 * @param spouseDob
	 */
	public void setSpouseDob(Date spouseDob)
	{
		this.spouseDob = spouseDob;
	}

	public String getPermAddress()
	{
		return permAddress;
	}

	public void setPermAddress(String permAddress)
	{
		this.permAddress = permAddress;
	}

	public String getCurrAddress()
	{
		return currAddress;
	}

	public void setCurrAddress(String currAddress)
	{
		this.currAddress = currAddress;
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
		
		if (!(_other instanceof PersonalInfo)) {
			return false;
		}
		
		final PersonalInfo _cast = (PersonalInfo) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (permanentAddress != _cast.permanentAddress) {
			return false;
		}
		
		if (permanentAddressNull != _cast.permanentAddressNull) {
			return false;
		}
		
		if (currentAddress != _cast.currentAddress) {
			return false;
		}
		
		if (currentAddressNull != _cast.currentAddressNull) {
			return false;
		}
		if (primaryPhoneNo != _cast.primaryPhoneNo)
		{
			return false;
		}

		if (primaryPhoneNoNull != _cast.primaryPhoneNoNull)
		{
			return false;
		}

		if (secondaryPhoneNo != _cast.secondaryPhoneNo)
		{
			return false;
		}

		if (secondaryPhoneNoNull != _cast.secondaryPhoneNoNull)
		{
			return false;
		}
		
		if (personalEmailId == null ? _cast.personalEmailId != personalEmailId : !personalEmailId.equals( _cast.personalEmailId )) {
			return false;
		}
		
		if (alternateEmailId == null ? _cast.alternateEmailId != alternateEmailId : !alternateEmailId.equals( _cast.alternateEmailId )) {
			return false;
		}
		
		if (motherName == null ? _cast.motherName != motherName : !motherName.equals( _cast.motherName )) {
			return false;
		}
		
		if (fatherName == null ? _cast.fatherName != fatherName : !fatherName.equals( _cast.fatherName )) {
			return false;
		}
		
		if (maritalStatus == null ? _cast.maritalStatus != maritalStatus : !maritalStatus.equals( _cast.maritalStatus )) {
			return false;
		}
		
		if (spouseName == null ? _cast.spouseName != spouseName : !spouseName.equals( _cast.spouseName )) {
			return false;
		}
		
		if (emerContactName == null ? _cast.emerContactName != emerContactName : !emerContactName.equals( _cast.emerContactName )) {
			return false;
		}
		
		if (emerCpRelationship == null ? _cast.emerCpRelationship != emerCpRelationship : !emerCpRelationship.equals( _cast.emerCpRelationship )) {
			return false;
		}
		
		if (emerPhoneNo != _cast.emerPhoneNo) {
			return false;
		}
		
		if (emerPhoneNoNull != _cast.emerPhoneNoNull) {
			return false;
		}
		
		if (city == null ? _cast.city != city : !city.equals( _cast.city )) {
			return false;
		}
		
		if (zipCode != _cast.zipCode) {
			return false;
		}
		
		if (zipCodeNull != _cast.zipCodeNull) {
			return false;
		}
		
		if (country == null ? _cast.country != country : !country.equals( _cast.country )) {
			return false;
		}
		
		if (state == null ? _cast.state != state : !state.equals( _cast.state )) {
			return false;
		}
		
		if (spouseDob == null ? _cast.spouseDob != spouseDob : !spouseDob.equals( _cast.spouseDob )) {
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
		_hashCode = 29 * _hashCode + permanentAddress;
		_hashCode = 29 * _hashCode + (permanentAddressNull ? 1 : 0);
		_hashCode = 29 * _hashCode + currentAddress;
		_hashCode = 29 * _hashCode + (currentAddressNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (primaryPhoneNoNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (secondaryPhoneNoNull ? 1 : 0);
		if (primaryPhoneNo != null) {
			_hashCode = 29 * _hashCode + primaryPhoneNo.hashCode();
		}
		
		if (secondaryPhoneNo != null) {
			_hashCode = 29 * _hashCode + secondaryPhoneNo.hashCode();
		}
		
		if (personalEmailId != null) {
			_hashCode = 29 * _hashCode + personalEmailId.hashCode();
		}
		
		if (alternateEmailId != null) {
			_hashCode = 29 * _hashCode + alternateEmailId.hashCode();
		}
		
		if (motherName != null) {
			_hashCode = 29 * _hashCode + motherName.hashCode();
		}
		
		if (fatherName != null) {
			_hashCode = 29 * _hashCode + fatherName.hashCode();
		}
		
		if (maritalStatus != null) {
			_hashCode = 29 * _hashCode + maritalStatus.hashCode();
		}
		
		if (spouseName != null) {
			_hashCode = 29 * _hashCode + spouseName.hashCode();
		}
		
		if (emerContactName != null) {
			_hashCode = 29 * _hashCode + emerContactName.hashCode();
		}
		
		if (emerCpRelationship != null) {
			_hashCode = 29 * _hashCode + emerCpRelationship.hashCode();
		}
		
		//_hashCode = 29 * _hashCode + emerPhoneNo;
		_hashCode = 29 * _hashCode + (emerPhoneNoNull ? 1 : 0);
		if (city != null) {
			_hashCode = 29 * _hashCode + city.hashCode();
		}
		
		_hashCode = 29 * _hashCode + zipCode;
		_hashCode = 29 * _hashCode + (zipCodeNull ? 1 : 0);
		if (country != null) {
			_hashCode = 29 * _hashCode + country.hashCode();
		}
		
		if (state != null) {
			_hashCode = 29 * _hashCode + state.hashCode();
		}
		
		if (spouseDob != null) {
			_hashCode = 29 * _hashCode + spouseDob.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return PersonalInfoPk
	 */
	public PersonalInfoPk createPk()
	{
		return new PersonalInfoPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.PersonalInfo: " );
		ret.append( "id=" + id );
		ret.append( ", permanentAddress=" + permanentAddress );
		ret.append( ", currentAddress=" + currentAddress );
		ret.append( ", primaryPhoneNo=" + primaryPhoneNo );
		ret.append( ", secondaryPhoneNo=" + secondaryPhoneNo );
		ret.append( ", personalEmailId=" + personalEmailId );
		ret.append( ", alternateEmailId=" + alternateEmailId );
		ret.append( ", motherName=" + motherName );
		ret.append( ", fatherName=" + fatherName );
		ret.append( ", maritalStatus=" + maritalStatus );
		ret.append( ", spouseName=" + spouseName );
		ret.append( ", emerContactName=" + emerContactName );
		ret.append( ", emerCpRelationship=" + emerCpRelationship );
		ret.append( ", emerPhoneNo=" + emerPhoneNo );
		ret.append( ", city=" + city );
		ret.append( ", zipCode=" + zipCode );
		ret.append( ", country=" + country );
		ret.append( ", state=" + state );
		ret.append( ", spouseDob=" + spouseDob );
		return ret.toString();
	}

}
