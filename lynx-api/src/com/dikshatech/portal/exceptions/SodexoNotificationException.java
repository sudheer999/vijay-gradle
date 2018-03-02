package com.dikshatech.portal.exceptions;

public class SodexoNotificationException extends Exception {

	String msg;
	public SodexoNotificationException() {
		msg = "$$$$$ Exception was thrown when trying to notify handlers $$$$$";
	}
	
	public SodexoNotificationException(String msg){
		this.msg = msg;
	}
	
}
