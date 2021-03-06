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

public class LeaveType implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the LEAVE_TYPE table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column LEAVE_TYPE in the LEAVE_TYPE table.
	 */
	protected String leaveType;

	/**
	 * Method 'LeaveType'
	 * 
	 */
	public LeaveType()
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
	 * Method 'getLeaveType'
	 * 
	 * @return String
	 */
	public String getLeaveType()
	{
		return leaveType;
	}

	/**
	 * Method 'setLeaveType'
	 * 
	 * @param leaveType
	 */
	public void setLeaveType(String leaveType)
	{
		this.leaveType = leaveType;
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
		
		if (!(_other instanceof LeaveType)) {
			return false;
		}
		
		final LeaveType _cast = (LeaveType) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (leaveType == null ? _cast.leaveType != leaveType : !leaveType.equals( _cast.leaveType )) {
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
		if (leaveType != null) {
			_hashCode = 29 * _hashCode + leaveType.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return LeaveTypePk
	 */
	public LeaveTypePk createPk()
	{
		return new LeaveTypePk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.LeaveType: " );
		ret.append( "id=" + id );
		ret.append( ", leaveType=" + leaveType );
		return ret.toString();
	}

}
