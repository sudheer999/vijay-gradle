package com.dikshatech.portal.dto;

import java.io.Serializable;

public class RmgTimeSheetPk implements Serializable{
	
	protected int id;

	/** 
	 * This attribute represents whether the primitive attribute id is null.
	 */
	protected boolean idNull;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RmgTimeSheetPk()
	{
	}

	/**
	 * Method 'DepPerdiemReportPk'
	 * 
	 * @param id
	 */
	public RmgTimeSheetPk(final int id)
	{
		this.id = id;
	}
	
	public boolean isIdNull() {
		return idNull;
	}

	public void setIdNull(boolean idNull) {
		this.idNull = idNull;
	}
	
	
	

}
