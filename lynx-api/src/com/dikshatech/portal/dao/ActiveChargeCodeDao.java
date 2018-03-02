package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.ActiveChargeCode;

import com.dikshatech.portal.dto.PoDetail;
import com.dikshatech.portal.exceptions.ActiveChargeCodeDaoException;


public interface ActiveChargeCodeDao {
	

	

	/** 
	 * Returns the rows from the CHARGE_CODE table that matches the specified primary-key value.
	 */
	public ActiveChargeCode[] findByAll() throws ActiveChargeCodeDaoException;
	
	

	



}
