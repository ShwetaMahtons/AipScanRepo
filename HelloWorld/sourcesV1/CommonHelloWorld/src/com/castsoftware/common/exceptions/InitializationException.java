package com.castsoftware.common.exceptions;

public class InitializationException extends AutomationException {
	private static final long serialVersionUID = -5351372669130950620L;

	public InitializationException(Throwable cause){
		super(cause);
	}
	
	public InitializationException(String msg){
		super(msg);
	}
	
	public InitializationException(){
	}
	
	public InitializationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
