package com.castsoftware.common.exceptions;

public class ProcessException extends AutomationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6594934872947882755L;

	public ProcessException() {
	}

	public ProcessException(String msg) {
		super(msg);
	}

	public ProcessException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ProcessException(String msg, Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}
}
