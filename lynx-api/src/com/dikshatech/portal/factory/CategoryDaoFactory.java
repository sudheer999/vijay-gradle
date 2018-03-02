package com.dikshatech.portal.factory;
import com.dikshatech.portal.dao.*;
import com.dikshatech.portal.jdbc.*;
import java.sql.Connection;



public class CategoryDaoFactory {

			public static CategoryDao create()
			{
				return new CategoryDaoImpl();
			}

			/**
			 * Method 'create'
			 * 
			 * @param conn
			 * @return CategoryDao
			 */
			public static CategoryDao create(Connection conn)
			{
				return new CategoryDaoImpl(conn);
			}

		}

		
		
		



