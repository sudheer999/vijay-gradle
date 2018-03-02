package com.dikshatech.portal.exceptions;

public class SodexoRollbackException extends Exception {

	String msg;
	public SodexoRollbackException() {
		msg="$$$$$ Exception was thrown when trying to do Rollback $$$$$";
	}
	
	public SodexoRollbackException(String msg){
		this.msg = msg;
	}
	
}
