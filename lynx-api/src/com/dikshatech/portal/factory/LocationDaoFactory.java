package com.dikshatech.portal.factory;


import java.sql.Connection;
import com.dikshatech.portal.dao.*;
import com.dikshatech.portal.jdbc.*;

public class LocationDaoFactory {


		/**
		 * Method 'create'
		 * 
		 * @return RegionsDao
		 */
		public static LocationDao create()
		{
			return new LocationDaoImpl();
		}

		/**
		 * Method 'create'
		 * 
		 * @param conn
		 * @return RegionsDao
		 */
		public static LocationDao create(Connection conn)
		{
			return new LocationDaoImpl(conn);
		}

	}

	
	
	


