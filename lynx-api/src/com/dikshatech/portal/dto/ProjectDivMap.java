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

public class ProjectDivMap implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the PROJECT_DIV_MAP table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column PROJ_ID in the PROJECT_DIV_MAP table.
	 */
	protected int projId;

	/** 
	 * This attribute maps to the column DIV_ID in the PROJECT_DIV_MAP table.
	 */
	protected int divId;

	/**
	 * Method 'ProjectDivMap'
	 * 
	 */
	public ProjectDivMap()
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
	 * Method 'getProjId'
	 * 
	 * @return int
	 */
	public int getProjId()
	{
		return projId;
	}

	/**
	 * Method 'setProjId'
	 * 
	 * @param projId
	 */
	public void setProjId(int projId)
	{
		this.projId = projId;
	}

	/**
	 * Method 'getDivId'
	 * 
	 * @return int
	 */
	public int getDivId()
	{
		return divId;
	}

	/**
	 * Method 'setDivId'
	 * 
	 * @param divId
	 */
	public void setDivId(int divId)
	{
		this.divId = divId;
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
		
		if (!(_other instanceof ProjectDivMap)) {
			return false;
		}
		
		final ProjectDivMap _cast = (ProjectDivMap) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (projId != _cast.projId) {
			return false;
		}
		
		if (divId != _cast.divId) {
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
		_hashCode = 29 * _hashCode + projId;
		_hashCode = 29 * _hashCode + divId;
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ProjectDivMapPk
	 */
	public ProjectDivMapPk createPk()
	{
		return new ProjectDivMapPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.ProjectDivMap: " );
		ret.append( "id=" + id );
		ret.append( ", projId=" + projId );
		ret.append( ", divId=" + divId );
		return ret.toString();
	}

}
