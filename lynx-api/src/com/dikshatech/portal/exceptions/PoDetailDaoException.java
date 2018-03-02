package com.dikshatech.portal.exceptions;

public class PoDetailDaoException extends DaoException{
	
	public PoDetailDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'PoDetailsDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public PoDetailDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
