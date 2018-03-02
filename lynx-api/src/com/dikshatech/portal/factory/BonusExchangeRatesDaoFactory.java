package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.BonusExchangeRatesDao;
import com.dikshatech.portal.jdbc.BonusExchangeRatesDaoImpl;


public class BonusExchangeRatesDaoFactory {
	/**
	 * Method 'create'
	 * 
	 * @return BonusExchangeRatesDao
	 */
	public static BonusExchangeRatesDao create()
	{
		return new BonusExchangeRatesDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return BonusExchangeRatesDao
	 */
	public static BonusExchangeRatesDao create(Connection conn)
	{
		return new BonusExchangeRatesDaoImpl( conn );
	}
}
