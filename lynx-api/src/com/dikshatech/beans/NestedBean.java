package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Set;

public class NestedBean implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3978712760164523137L;
	private int fieldId;
	private String fieldName;
	private String dataLevel;
	private String dataLevel1;
	private Set<NestedBean> nestedRecord;
	private Set<NestedBean> nestedRecord1;
	private int isSelected=0;
	public int getFieldId()
	{
		return fieldId;
	}
	public String getFieldName()
	{
		return fieldName;
	}
	public String getDataLevel()
	{
		return dataLevel;
	}
	public Set<NestedBean> getNestedRecord()
	{
		return nestedRecord;
	}
	public void setFieldId(int fieldId)
	{
		this.fieldId = fieldId;
	}
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}
	public void setDataLevel(String dataLevel)
	{
		this.dataLevel = dataLevel;
	}
	public void setNestedRecord(Set<NestedBean> nestedRecord)
	{
		this.nestedRecord = nestedRecord;
	}
	public void setDataLevel1(String dataLevel1)
	{
		this.dataLevel1 = dataLevel1;
	}
	public String getDataLevel1()
	{
		return dataLevel1;
	}
	public void setNestedRecord1(Set<NestedBean> nestedRecord1)
	{
		this.nestedRecord1 = nestedRecord1;
	}
	public Set<NestedBean> getNestedRecord1()
	{
		return nestedRecord1;
	}
	public int getIsSelected()
    {
    	return isSelected;
    }
	public void setIsSelected(int isSelected)
    {
    	this.isSelected = isSelected;
    }
	

}
