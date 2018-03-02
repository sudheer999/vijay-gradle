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

public class LeaveLwp implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the LEAVE_LWP table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column USER_ID in the LEAVE_LWP table.
	 */
	protected int userId;

	/** 
	 * This attribute maps to the column MONTH_CYCLE in the LEAVE_LWP table.
	 */
	protected String monthCycle;

	/** 
	 * This attribute maps to the column LWP in the LEAVE_BALANCE table.
	 */
	protected float lwp;

	

	/** 
	 * This attribute represents whether the primitive attribute lwp is null.
	 */
	protected boolean lwpNull = true;

	/**
	 * Method 'LeaveLwp'
	 * 
	 */
	public LeaveLwp()
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
	public int getUserId()
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
	}
	
	/**
	 * Method 'getId'
	 * 
	 * @return int
	 */
	public String getMonthCycle()
	{
		return monthCycle;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setMonthCycle(String monthCycle)
	{
		this.monthCycle = monthCycle;
	}

	

	/**
	 * Method 'getLwp'
	 * 
	 * @return int
	 */
	public float getLwp()
	{
		return lwp;
	}

	/**
	 * Method 'setLwp'
	 * 
	 * @param lwp
	 */
	public void setLwp(float lwp)
	{
		this.lwp = lwp;
		this.lwpNull = false;
	}

	/**
	 * Method 'setLwpNull'
	 * 
	 * @param value
	 */
	public void setLwpNull(boolean value)
	{
		this.lwpNull = value;
	}

	/**
	 * Method 'isLwpNull'
	 * 
	 * @return boolean
	 */
	public boolean isLwpNull()
	{
		return lwpNull;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeaveLwp other = (LeaveLwp) obj;
		if (id != other.id)
			return false;
		if (Float.floatToIntBits(lwp) != Float.floatToIntBits(other.lwp))
			return false;
		if (lwpNull != other.lwpNull)
			return false;
		if (monthCycle == null) {
			if (other.monthCycle != null)
				return false;
		} else if (!monthCycle.equals(other.monthCycle))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + Float.floatToIntBits(lwp);
		result = prime * result + (lwpNull ? 1231 : 1237);
		result = prime * result + ((monthCycle == null) ? 0 : monthCycle.hashCode());
		result = prime * result + userId;
		return result;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return LeaveBalancePk
	 */
	public LeaveLwpPk createPk()
	{
		return new LeaveLwpPk(id);
	}

	

	

	

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	@Override
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.dikshatech.portal.dto.LeaveBalance: " );
		ret.append( "id=" + id );
		ret.append( ", userId=" + userId );
		ret.append( ", monthCycle=" + monthCycle );
		ret.append( ", lwp=" + lwp );
		return ret.toString();
	}


}