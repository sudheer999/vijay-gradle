package com.dikshatech.portal.exceptions;

public class ResourceStatusDaoException extends DaoException
{
	/**
	 * Method 'RequestTypeDaoException'
	 * 
	 * @param message
	 */
	public ResourceStatusDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'RequestTypeDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ResourceStatusDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}