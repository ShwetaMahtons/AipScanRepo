package com.castsoftware.common;


import java.util.StringTokenizer;

import com.castsoftware.common.batch.Batch;

public class FakeClassForCASTPurpose {
	/**
	 * Fake method for dashboard violation purpose only with method invocation in a loop
	 * @return
	 * @throws Exception
	 */
	public String fakeMethodWithMethodInvocationInALoop() throws Exception {
		String title = new String();  
		
	      
	      StringTokenizer st = new StringTokenizer("my string for CAST violation test purpose");      
	      for (int i=0; i<st.countTokens();i++ ){
	    	  // bla bla bla
	      }

	      StringTokenizer st1 = new StringTokenizer("my string for CAST violation test purpose 2");      
	      for (int i=0; i<st1.countTokens();i++ ){
	    	  // bla bla bla
	      }

	      
	      StringTokenizer st2 = new StringTokenizer("my string for CAST violation test purpose 3");      
	      for (int i=0; i<st2.countTokens();i++ ){
	    	  // bla bla bla
	      }

	      StringTokenizer st3 = new StringTokenizer("my string for CAST violation test purpose 4");      
	      for (int i=0; i<st3.countTokens();i++ ){
	    	  // bla bla bla
	      }

	      StringTokenizer st4 = new StringTokenizer("my string for CAST violation test purpose 5");      
	      for (int i=0; i<st4.countTokens();i++ ){
	    	  // bla bla bla
	      }

	      return title;
	}
	
	public String fakeMethodWithStringConcatenation(){
		 String result = "hello";
		 for (int i = 0; i < 1500; i++) {
		 result += "world"; // VIOLATION
		 }
		 return result;
	}
	
	public String fakeMethodWithStringConcatenation2(){
		 String result = "hello";
		 for (int i = 0; i < 1500; i++) {
		 result += "world"; // VIOLATION
		 }
		 return result;
	}
	
	public boolean equals(Object other) {
		if (this==other) return true;
		if (other==null) return false;
		// VIOLATION
		if ( !(other instanceof Batch)) return false;
		return true;	
	}
	
		
	
}
