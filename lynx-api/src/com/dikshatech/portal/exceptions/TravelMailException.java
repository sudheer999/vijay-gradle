package com.dikshatech.portal.exceptions;

public class TravelMailException extends Exception
{
	String msg;
	public TravelMailException() {
		msg="$$$$$ Exception was thrown when trying to send mail $$$$$";
	}
	
	public TravelMailException(String msg){
		this.msg = msg;
	}
}
