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

public class ModuleAlert extends PortalForm implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * This attribute maps to the column ID in the MODULE_ALERT table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column MODULE_ID in the MODULE_ALERT table.
	 */
	protected int moduleId;

	/** 
	 * This attribute represents whether the primitive attribute moduleId is null.
	 */
	protected boolean moduleIdNull = true;

	/** 
	 * This attribute maps to the column MODIFY_DATETIME in the MODULE_ALERT table.
	 */
	protected Date modifyDatetime;

	/**
	 * Method 'ModuleAlert'
	 * 
	 */
	public ModuleAlert()
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
	 * Method 'getModuleId'
	 * 
	 * @return int
	 */
	public int getModuleId()
	{
		return moduleId;
	}

	/**
	 * Method 'setModuleId'
	 * 
	 * @param moduleId
	 */
	public void setModuleId(int moduleId)
	{
		this.moduleId = moduleId;
		this.moduleIdNull = false;
	}

	/**
	 * Method 'setModuleIdNull'
	 * 
	 * @param value
	 */
	public void setModuleIdNull(boolean value)
	{
		this.moduleIdNull = value;
	}

	/**
	 * Method 'isModuleIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isModuleIdNull()
	{
		return moduleIdNull;
	}

	/**
	 * Method 'getModifyDatetime'
	 * 
	 * @return Date
	 */
	public Date getModifyDatetime()
	{
		return modifyDatetime;
	}

	/**
	 * Method 'setModifyDatetime'
	 * 
	 * @param modifyDatetime
	 */
	public void setModifyDatetime(Date modifyDatetime)
	{
		this.modifyDatetime = modifyDatetime;
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
		
		if (!(_other instanceof ModuleAlert)) {
			return false;
		}
		
		final ModuleAlert _cast = (ModuleAlert) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (moduleId != _cast.moduleId) {
			return false;
		}
		
		if (moduleIdNull != _cast.moduleIdNull) {
			return false;
		}
		
		if (modifyDatetime == null ? _cast.modifyDatetime != modifyDatetime : !modifyDatetime.equals( _cast.modifyDatetime )) {
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
		_hashCode = 29 * _hashCode + moduleId;
		_hashCode = 29 * _hashCode + (moduleIdNull ? 1 : 0);
		if (modifyDatetime != null) {
			_hashCode = 29 * _hashCode + modifyDatetime.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ModuleAlertPk
	 */
	public ModuleAlertPk createPk()
	{
		return new ModuleAlertPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.ModuleAlert: " );
		ret.append( "id=" + id );
		ret.append( ", moduleId=" + moduleId );
		ret.append( ", modifyDatetime=" + modifyDatetime );
		return ret.toString();
	}

}
