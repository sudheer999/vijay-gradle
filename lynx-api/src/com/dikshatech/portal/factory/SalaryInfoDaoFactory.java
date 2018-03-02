/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.factory;

import java.sql.Connection;
import com.dikshatech.portal.dao.*;
import com.dikshatech.portal.jdbc.*;

public class SalaryInfoDaoFactory
{
	/**
	 * Method 'create'
	 * 
	 * @return SalaryInfoDao
	 */
	public static SalaryInfoDao create()
	{
		return new SalaryInfoDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return SalaryInfoDao
	 */
	public static SalaryInfoDao create(Connection conn)
	{
		return new SalaryInfoDaoImpl( conn );
	}
	
	/**
	 * Method 'create'
	 * 
	 * @return SalaryInfoDao
	 */
	public static SalaryInfoDao createReplica()
	{
		return new SalaryInfoForApprovalDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return SalaryInfoDao
	 */
	public static SalaryInfoDao createReplica(Connection conn)
	{
		return new SalaryInfoForApprovalDaoImpl( conn );
	}

}