package com.dikshatech.portal.factory;

import java.sql.Connection;


import com.dikshatech.portal.dao.PoDetailDao;

import com.dikshatech.portal.jdbc.PoDetailDaoImpl;


public class PoDetailDaoFactory {
	
	public static PoDetailDao create(){
		
		return new PoDetailDaoImpl();
		
	}

	public static PoDetailDao create(Connection conn)
	{
		return new PoDetailDaoImpl(conn);

	}
}



/*public static ActiveChargeCodeDao create(){
	
	return new ActiveChargeCodeDaoImpl();
	
}

public static ActiveChargeCodeDao create(Connection conn)
{
	return new ActiveChargeCodeDaoImpl(conn);

}*/