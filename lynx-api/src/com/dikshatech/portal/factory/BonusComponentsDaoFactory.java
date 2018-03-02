package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.BonusComponentsDao;
import com.dikshatech.portal.jdbc.BonusComponentsDaoImpl;


public class BonusComponentsDaoFactory {

		/**
		 * Method 'create'
		 * 
		 * @return DepPerdiemComponentsDao
		 */
		public static BonusComponentsDao create()
		{
			return new BonusComponentsDaoImpl();
		}

		/**
		 * Method 'create'
		 * 
		 * @param conn
		 * @return DepPerdiemComponentsDao
		 */
		public static BonusComponentsDao create(Connection conn)
		{
			return new BonusComponentsDaoImpl( conn );
		}
}
