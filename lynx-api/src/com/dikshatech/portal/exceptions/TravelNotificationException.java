package com.dikshatech.portal.exceptions;

public class TravelNotificationException extends Exception 
{
	String msg;
	
	public TravelNotificationException()
	{
		msg = "$$$$$ Exception was thrown when trying to send notification $$$$$";
	}

}
