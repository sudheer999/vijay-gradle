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

public class LeaveProject extends PortalForm implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the LEAVE_PROJECT table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column PROJECT_ID in the LEAVE_PROJECT table.
	 */
	protected String projectId;

	/** 
	 * This attribute maps to the column PROJECT_NAME in the LEAVE_PROJECT table.
	 */
	protected String projectName;

	/** 
	 * This attribute maps to the column CHARGE_CODE in the LEAVE_PROJECT table.
	 */
	protected String chargeCode;

	/** 
	 * This attribute maps to the column TITLE in the LEAVE_PROJECT table.
	 */
	protected String title;

	/** 
	 * This attribute maps to the column LEAVE_MASTER_ID in the LEAVE_PROJECT table.
	 */
	protected int leaveMasterId;

	/**
	 * Method 'LeaveProject'
	 * 
	 */
	public LeaveProject()
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
	 * Method 'getProjectId'
	 * 
	 * @return String
	 */
	public String getProjectId()
	{
		return projectId;
	}

	/**
	 * Method 'setProjectId'
	 * 
	 * @param projectId
	 */
	public void setProjectId(String projectId)
	{
		this.projectId = projectId;
	}

	/**
	 * Method 'getProjectName'
	 * 
	 * @return String
	 */
	public String getProjectName()
	{
		return projectName;
	}

	/**
	 * Method 'setProjectName'
	 * 
	 * @param projectName
	 */
	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	/**
	 * Method 'getChargeCode'
	 * 
	 * @return String
	 */
	public String getChargeCode()
	{
		return chargeCode;
	}

	/**
	 * Method 'setChargeCode'
	 * 
	 * @param chargeCode
	 */
	public void setChargeCode(String chargeCode)
	{
		this.chargeCode = chargeCode;
	}

	/**
	 * Method 'getTitle'
	 * 
	 * @return String
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Method 'setTitle'
	 * 
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Method 'getLeaveMasterId'
	 * 
	 * @return int
	 */
	public int getLeaveMasterId()
	{
		return leaveMasterId;
	}

	/**
	 * Method 'setLeaveMasterId'
	 * 
	 * @param leaveMasterId
	 */
	public void setLeaveMasterId(int leaveMasterId)
	{
		this.leaveMasterId = leaveMasterId;
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
		
		if (!(_other instanceof LeaveProject)) {
			return false;
		}
		
		final LeaveProject _cast = (LeaveProject) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (projectId == null ? _cast.projectId != projectId : !projectId.equals( _cast.projectId )) {
			return false;
		}
		
		if (projectName == null ? _cast.projectName != projectName : !projectName.equals( _cast.projectName )) {
			return false;
		}
		
		if (chargeCode == null ? _cast.chargeCode != chargeCode : !chargeCode.equals( _cast.chargeCode )) {
			return false;
		}
		
		if (title == null ? _cast.title != title : !title.equals( _cast.title )) {
			return false;
		}
		
		if (leaveMasterId != _cast.leaveMasterId) {
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
		if (projectId != null) {
			_hashCode = 29 * _hashCode + projectId.hashCode();
		}
		
		if (projectName != null) {
			_hashCode = 29 * _hashCode + projectName.hashCode();
		}
		
		if (chargeCode != null) {
			_hashCode = 29 * _hashCode + chargeCode.hashCode();
		}
		
		if (title != null) {
			_hashCode = 29 * _hashCode + title.hashCode();
		}
		
		_hashCode = 29 * _hashCode + leaveMasterId;
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return LeaveProjectPk
	 */
	public LeaveProjectPk createPk()
	{
		return new LeaveProjectPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.LeaveProject: " );
		ret.append( "id=" + id );
		ret.append( ", projectId=" + projectId );
		ret.append( ", projectName=" + projectName );
		ret.append( ", chargeCode=" + chargeCode );
		ret.append( ", title=" + title );
		ret.append( ", leaveMasterId=" + leaveMasterId );
		return ret.toString();
	}

}