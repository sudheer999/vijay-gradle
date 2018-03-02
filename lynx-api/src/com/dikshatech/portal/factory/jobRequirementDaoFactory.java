package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.JobRequirementDao;
import com.dikshatech.portal.jdbc.JobRequirementDaoImpl;

public class jobRequirementDaoFactory {

	/**
	 * Method 'create'
	 * 
	 * @return AccessibilityLevelDao
	 */
	public static JobRequirementDao create()
	{
		return new JobRequirementDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return AccessibilityLevelDao
	 */
	public static JobRequirementDao create(Connection conn)
	{
		return new JobRequirementDaoImpl( conn );
	}
}
