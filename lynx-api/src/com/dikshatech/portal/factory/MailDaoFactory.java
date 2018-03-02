package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.MailDao;
import com.dikshatech.portal.jdbc.MailDaoImpl;

public class MailDaoFactory
{/*
	*//**
	 * Method 'create'
	 * 
	 * @return MailDao
	 *//*
	public static MailDao create()
	{
		return new MailDaoImpl();
	}

	*//**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return MailDao
	 *//*
	public static MailDao create(Connection conn)
	{
		return new MailDaoImpl( conn );
	}

*/}
