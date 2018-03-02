/**
 * @author gurunath.rokkam
 */
package com.dikshatech.portal.exceptions;

public class SessionExpiredException extends RuntimeException {

	public SessionExpiredException() {
		super("Session has been expired");
	}

	public SessionExpiredException(String message) {
		super(message);
	}

	public SessionExpiredException(String message, Throwable cause) {
		super(message, cause);
	}
}
