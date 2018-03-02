package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.ResourceRequirementDao;
import com.dikshatech.portal.jdbc.ResourceRequirementDaoImpl;

public class ResourceRequirementDaoFactory {
	/**
	 * Method 'create'
	 * 
	 * @return RequestTypeDao
	 */
	public static ResourceRequirementDao create()
	{
		return new ResourceRequirementDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return RequestTypeDao
	 */
	public static ResourceRequirementDao create(Connection conn)
	{
		return new ResourceRequirementDaoImpl( conn );
	}

}