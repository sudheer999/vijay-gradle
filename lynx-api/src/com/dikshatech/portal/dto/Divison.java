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

public class Divison extends PortalForm implements Serializable
{
	/**
	 * This attribute maps to the column ID in the DIVISON table.
	 */
	protected int id;

	/**
	 * This attribute maps to the column NAME in the DIVISON table.
	 */
	protected String name;

	/**
	 * This attribute maps to the column PARENT_ID in the DIVISON table.
	 */
	protected int parentId;

	/**
	 * This attribute maps to the column REGION_ID in the DIVISON table.
	 */
	protected int regionId;

	/**
	 * Method 'Divison'
	 * 
	 */
	public Divison()
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
	}

	/**
	 * Method 'getRegionId'
	 * 
	 * @return int
	 */
	public int getRegionId()
	{
		return regionId;
	}

	/**
	 * Method 'setRegionId'
	 * 
	 * @param regionId
	 */
	public void setRegionId(int regionId)
	{
		this.regionId = regionId;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (_other == null)
		{
			return false;
		}

		if (_other == this)
		{
			return true;
		}

		if (!(_other instanceof Divison))
		{
			return false;
		}

		final Divison _cast = (Divison) _other;
		if (id != _cast.id)
		{
			return false;
		}

		if (name == null ? _cast.name != name : !name.equals(_cast.name))
		{
			return false;
		}

		if (parentId != _cast.parentId)
		{
			return false;
		}

		if (regionId != _cast.regionId)
		{
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
		if (name != null)
		{
			_hashCode = 29 * _hashCode + name.hashCode();
		}

		_hashCode = 29 * _hashCode + parentId;
		_hashCode = 29 * _hashCode + regionId;
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DivisonPk
	 */
	public DivisonPk createPk()
	{
		return new DivisonPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.Divison: ");
		ret.append("id=" + id);
		ret.append(", name=" + name);
		ret.append(", parentId=" + parentId);
		ret.append(", regionId=" + regionId);
		return ret.toString();
	}
}
