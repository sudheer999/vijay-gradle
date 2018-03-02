package com.dikshatech.portal.exceptions;

public class ReqResMapDaoException extends DaoException{

	/**
	 * Method 'ResourceReqMappingDaoException'
	 * 
	 * @param message
	 */
	public ReqResMapDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'ResourceReqMappingDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ReqResMapDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
