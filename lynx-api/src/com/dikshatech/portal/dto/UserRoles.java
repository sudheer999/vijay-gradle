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

public class UserRoles extends PortalForm implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8796191511540761032L;

	/** 
	 * This attribute maps to the column ID in the USER_ROLES table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column USER_ID in the USER_ROLES table.
	 */
	protected int userId;

	/** 
	 * This attribute represents whether the primitive attribute userId is null.
	 */
	protected boolean userIdNull = true;

	/** 
	 * This attribute maps to the column CANDIDATE_ID in the USER_ROLES table.
	 */
	protected int candidateId;

	/** 
	 * This attribute represents whether the primitive attribute candidateId is null.
	 */
	protected boolean candidateIdNull = true;

	/** 
	 * This attribute maps to the column ROLE_ID in the USER_ROLES table.
	 */
	protected int roleId;

	/** 
	 * This attribute represents whether the primitive attribute roleId is null.
	 */
	protected boolean roleIdNull = true;

	/**
	 * Method 'UserRoles'
	 * 
	 */
	public UserRoles()
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
	 * Method 'getUserId'
	 * 
	 * @return int
	 */
	public Integer getUserId()
	{
		return userId;
	}

	/**
	 * Method 'setUserId'
	 * 
	 * @param userId
	 */
	public void setUserId(int userId)
	{
		this.userId = userId;
		this.userIdNull = false;
	}

	/**
	 * Method 'setUserIdNull'
	 * 
	 * @param value
	 */
	public void setUserIdNull(boolean value)
	{
		this.userIdNull = value;
	}

	/**
	 * Method 'isUserIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isUserIdNull()
	{
		return userIdNull;
	}

	/**
	 * Method 'getCandidateId'
	 * 
	 * @return int
	 */
	public int getCandidateId()
	{
		return candidateId;
	}

	/**
	 * Method 'setCandidateId'
	 * 
	 * @param candidateId
	 */
	public void setCandidateId(int candidateId)
	{
		this.candidateId = candidateId;
		this.candidateIdNull = false;
	}

	/**
	 * Method 'setCandidateIdNull'
	 * 
	 * @param value
	 */
	public void setCandidateIdNull(boolean value)
	{
		this.candidateIdNull = value;
	}

	/**
	 * Method 'isCandidateIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isCandidateIdNull()
	{
		return candidateIdNull;
	}

	/**
	 * Method 'getRoleId'
	 * 
	 * @return int
	 */
	public int getRoleId()
	{
		return roleId;
	}

	/**
	 * Method 'setRoleId'
	 * 
	 * @param roleId
	 */
	public void setRoleId(int roleId)
	{
		this.roleId = roleId;
		this.roleIdNull = false;
	}

	/**
	 * Method 'setRoleIdNull'
	 * 
	 * @param value
	 */
	public void setRoleIdNull(boolean value)
	{
		this.roleIdNull = value;
	}

	/**
	 * Method 'isRoleIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isRoleIdNull()
	{
		return roleIdNull;
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
		
		if (!(_other instanceof UserRoles)) {
			return false;
		}
		
		final UserRoles _cast = (UserRoles) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (userId != _cast.userId) {
			return false;
		}
		
		if (userIdNull != _cast.userIdNull) {
			return false;
		}
		
		if (candidateId != _cast.candidateId) {
			return false;
		}
		
		if (candidateIdNull != _cast.candidateIdNull) {
			return false;
		}
		
		if (roleId != _cast.roleId) {
			return false;
		}
		
		if (roleIdNull != _cast.roleIdNull) {
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
		_hashCode = 29 * _hashCode + userId;
		_hashCode = 29 * _hashCode + (userIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + candidateId;
		_hashCode = 29 * _hashCode + (candidateIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + roleId;
		_hashCode = 29 * _hashCode + (roleIdNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return UserRolesPk
	 */
	public UserRolesPk createPk()
	{
		return new UserRolesPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.UserRoles: " );
		ret.append( "id=" + id );
		ret.append( ", userId=" + userId );
		ret.append( ", candidateId=" + candidateId );
		ret.append( ", roleId=" + roleId );
		return ret.toString();
	}

}