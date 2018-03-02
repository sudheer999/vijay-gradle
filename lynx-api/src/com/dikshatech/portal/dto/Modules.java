/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dto;

import java.io.Serializable;

public class Modules implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the MODULES table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column NAME in the MODULES table.
	 */
	protected String name;

	/** 
	 * This attribute maps to the column PARENT_ID in the MODULES table.
	 */
	protected int parentId;

	/** 
	 * This attribute represents whether the primitive attribute parentId is null.
	 */
	protected boolean parentIdNull = true;

	/** 
	 * This attribute maps to the column IS_PROC_CHAIN in the MODULES table.
	 */
	protected int isProcChain;

	/** 
	 * This attribute maps to the column PERMISSION_TYPES in the MODULES table.
	 */
	protected String permissionTypes;

	/**
	 * Method 'Modules'
	 * 
	 */
	public Modules()
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
	 * Method 'getName'
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Method 'setName'
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Method 'getParentId'
	 * 
	 * @return int
	 */
	public int getParentId()
	{
		return parentId;
	}

	/**
	 * Method 'setParentId'
	 * 
	 * @param parentId
	 */
	public void setParentId(int parentId)
	{
		this.parentId = parentId;
		this.parentIdNull = false;
	}

	/**
	 * Method 'setParentIdNull'
	 * 
	 * @param value
	 */
	public void setParentIdNull(boolean value)
	{
		this.parentIdNull = value;
	}

	/**
	 * Method 'isParentIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isParentIdNull()
	{
		return parentIdNull;
	}

	/**
	 * Method 'getIsProcChain'
	 * 
	 * @return int
	 */
	public int getIsProcChain()
	{
		return isProcChain;
	}

	/**
	 * Method 'setIsProcChain'
	 * 
	 * @param isProcChain
	 */
	public void setIsProcChain(int isProcChain)
	{
		this.isProcChain = isProcChain;
	}

	/**
	 * Method 'getPermissionTypes'
	 * 
	 * @return String
	 */
	public String getPermissionTypes()
	{
		return permissionTypes;
	}

	/**
	 * Method 'setPermissionTypes'
	 * 
	 * @param permissionTypes
	 */
	public void setPermissionTypes(String permissionTypes)
	{
		this.permissionTypes = permissionTypes;
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
		
		if (!(_other instanceof Modules)) {
			return false;
		}
		
		final Modules _cast = (Modules) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (name == null ? _cast.name != name : !name.equals( _cast.name )) {
			return false;
		}
		
		if (parentId != _cast.parentId) {
			return false;
		}
		
		if (parentIdNull != _cast.parentIdNull) {
			return false;
		}
		
		if (isProcChain != _cast.isProcChain) {
			return false;
		}
		
		if (permissionTypes == null ? _cast.permissionTypes != permissionTypes : !permissionTypes.equals( _cast.permissionTypes )) {
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
		if (name != null) {
			_hashCode = 29 * _hashCode + name.hashCode();
		}
		
		_hashCode = 29 * _hashCode + parentId;
		_hashCode = 29 * _hashCode + (parentIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + isProcChain;
		if (permissionTypes != null) {
			_hashCode = 29 * _hashCode + permissionTypes.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ModulesPk
	 */
	public ModulesPk createPk()
	{
		return new ModulesPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.Modules: " );
		ret.append( "id=" + id );
		ret.append( ", name=" + name );
		ret.append( ", parentId=" + parentId );
		ret.append( ", isProcChain=" + isProcChain );
		ret.append( ", permissionTypes=" + permissionTypes );
		return ret.toString();
	}

}