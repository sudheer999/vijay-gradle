package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.AccessibilityLevelDao;
import com.dikshatech.portal.dao.RmgTimeSheetReconDao;
import com.dikshatech.portal.jdbc.AccessibilityLevelDaoImpl;
import com.dikshatech.portal.jdbc.RmgTimeSheetReconDaoImpl;

public class RmgTimeSheetReconDaoFactory {
	
	public static RmgTimeSheetReconDao create()
	{
		return new RmgTimeSheetReconDaoImpl();
	}

	
	public static RmgTimeSheetReconDao create(Connection conn)
	{
		return new RmgTimeSheetReconDaoImpl( conn );
	}

}
