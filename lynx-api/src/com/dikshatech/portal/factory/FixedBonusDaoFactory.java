package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.FixedBonusDao;
import com.dikshatech.portal.jdbc.FixedBonusDaoImpl;


public class FixedBonusDaoFactory {
	/**
	 * Method 'create'
	 * 
	 * @return FixedBonusDao
	 */
	public static FixedBonusDao create()
	{
		return new FixedBonusDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return FixedBonusDao
	 */
	public static FixedBonusDao create(Connection conn)
	{
		return new FixedBonusDaoImpl( conn );
	}


}
