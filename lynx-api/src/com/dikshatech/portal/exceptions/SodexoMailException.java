package com.dikshatech.portal.exceptions;


public class SodexoMailException extends Exception {

	String msg;
	public SodexoMailException() {
		msg="$$$$$ Exception was thrown when trying to send mail $$$$$";
	}
	
	public SodexoMailException(String msg){
		this.msg = msg;
	}
	
}
