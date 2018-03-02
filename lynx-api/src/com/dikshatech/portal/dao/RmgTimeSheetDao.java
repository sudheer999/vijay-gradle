package com.dikshatech.portal.dao;


import java.util.ArrayList;
import java.util.List;

import com.dikshatech.portal.dto.RmgTimeSheet;
import com.dikshatech.portal.dto.RmgTimeSheetPk;
import com.dikshatech.portal.exceptions.RmgTimeSheetDaoException;

public interface RmgTimeSheetDao {

	
	public RmgTimeSheet insert(RmgTimeSheet dto)throws RmgTimeSheetDaoException;
	
	public RmgTimeSheet insertDataBy(RmgTimeSheet dto)throws RmgTimeSheetDaoException;
	
	public RmgTimeSheet[] getTimeSheetReconcialition(int id)throws RmgTimeSheetDaoException;
	
	public RmgTimeSheet[] findByAll(int id,String flag) throws RmgTimeSheetDaoException;
	
	public void update(RmgTimeSheetPk pk, RmgTimeSheet dto) throws RmgTimeSheetDaoException;
	
	public RmgTimeSheet[] findByAl() throws RmgTimeSheetDaoException;
	
	
}
