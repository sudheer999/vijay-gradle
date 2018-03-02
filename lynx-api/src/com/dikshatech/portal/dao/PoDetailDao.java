package com.dikshatech.portal.dao;

import java.util.ArrayList;

import com.dikshatech.portal.dto.PoDetail;
import com.dikshatech.portal.dto.PoDetails;
import com.dikshatech.portal.exceptions.ActiveChargeCodeDaoException;
import com.dikshatech.portal.exceptions.PoDetailDaoException;

public interface PoDetailDao {
	
	public PoDetail[] findByPoIdAndEmpId(int poId, int empId)throws PoDetailDaoException;
	
	public String updateAllPoDetailsById(int poId,PoDetails dto)throws PoDetailDaoException; 

}
