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

public class IssueApproverChainReqDaoFactory
{
	/**
	 * Method 'create'
	 * 
	 * @return IssueApproverChainReqDao
	 */
	public static IssueApproverChainReqDao create()
	{
		return new IssueApproverChainReqDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return IssueApproverChainReqDao
	 */
	public static IssueApproverChainReqDao create(Connection conn)
	{
		return new IssueApproverChainReqDaoImpl( conn );
	}

}