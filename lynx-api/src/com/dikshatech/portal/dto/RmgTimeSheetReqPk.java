package com.dikshatech.portal.dto;

import java.io.Serializable;

public class RmgTimeSheetReqPk implements Serializable{
	
	protected int id;

	/** 
	 * This attribute represents whether the primitive attribute id is null.
	 */
	protected boolean idNull;
	
	public RmgTimeSheetReqPk()
	{
	}

	/**
	 * Method 'DepPerdiemReqPk'
	 * 
	 * @param id
	 */
	public RmgTimeSheetReqPk(final int id)
	{
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isIdNull() {
		return idNull;
	}

	public void setIdNull(boolean idNull) {
		this.idNull = idNull;
	}
	
	
	

}
