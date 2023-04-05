package com.castsoftware.common.exceptions;

public abstract class AutomationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2605598771549471909L;

	protected AutomationException(Throwable cause) {
		super(cause);
	}

	public AutomationException() {
	}
	
	public AutomationException(String msg) {
		super(msg);
	}
	
	public AutomationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
