package com.dikshatech.portal.factory;

import java.sql.Connection;


import com.dikshatech.portal.dao.ActiveChargeCodeDao;

import com.dikshatech.portal.jdbc.ActiveChargeCodeDaoImpl;



public class ActiveChargeCodeDaoFactory {
	
	public static ActiveChargeCodeDao create(){
		
		return new ActiveChargeCodeDaoImpl();
		
	}
	
	public static ActiveChargeCodeDao create(Connection conn)
	{
		return new ActiveChargeCodeDaoImpl(conn);
	
	}

}
