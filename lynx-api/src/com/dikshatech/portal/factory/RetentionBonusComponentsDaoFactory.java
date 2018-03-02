package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.RetentionBonusComponentsDao;
import com.dikshatech.portal.jdbc.RetentionBonusComponentsDaoImpl;

public class RetentionBonusComponentsDaoFactory {


	/**
	 * Method 'create'
	 * 
	 * @return DepPerdiemComponentsDao
	 */
	public static RetentionBonusComponentsDao create()
	{
		return new RetentionBonusComponentsDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return DepPerdiemComponentsDao
	 */
	public static RetentionBonusComponentsDao create(Connection conn)
	{
		return new RetentionBonusComponentsDaoImpl( conn );
	}

}
