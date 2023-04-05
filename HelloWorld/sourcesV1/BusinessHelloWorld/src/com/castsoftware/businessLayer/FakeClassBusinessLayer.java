package com.castsoftware.businessLayer;

import java.util.HashMap;

import com.castsoftware.common.batch.Batch;

public class FakeClassBusinessLayer {

	public String stringConcatenationExample(){
		String result = "hello";
		for (int i = 0; i < 1500; i++) {
		result += "hello"; // VIOLATION
		}
		return "";
	}
	
	
	public void method () {
		HashMap<Batch,AccessDatabase > exList = new HashMap<Batch, AccessDatabase>();
		for(AccessDatabase adb : exList.values()){
			if (adb instanceof AccessDatabase) { // VIOLATION
				try {
					((AccessDatabase) adb).fakeMethodWithExeceptionViolation();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				//sfsdfsdf
			}
		}		
		int i = 1;
		int j = 2;
		Integer ia = new Integer(1);
		Integer ib= new Integer(3);

		if (i == j) {} // OK: i and j are primitive types
		if (ia == ib) {} // VIOLATION
		if (ia != ib) {} // VIOLATION	
	}

	public String stringConcatenationExample2(){
		String result = "hello";
		for (int i = 0; i < 1500; i++) {
		result += "hello"; // VIOLATION
		}
		
		int i = 1;
		int j = 2;
		Integer ia = new Integer(1);
		Integer ib= new Integer(3);

		if (i == j) {} // OK: i and j are primitive types
		if (ia == ib) {} // VIOLATION
		if (ia != ib) {} // VIOLATION		
		for (int it = 0; it < 10; it++) { 
			if (true) {
			break; // Violation
		}
		}
		for (int itt = 0; itt < 10; itt++) { 
			if (true) {
			break; // Violation
		}
		}
		for (int ite = 0; ite < 10; ite++) { 
			if (true) {
			break; // Violation
		}	
		}
		for (int ita = 0; ita < 10; ita++) { 
			if (true) {
			break; // Violation
			}
		}
		return "";
	}
	
//	private void commentedOutMethod2(int trigger) {
//		switch (trigger) {
//		case 1:
//			checkIt(trigger);
//			break;
//		case 2:
//			checkIt(trigger);
//			break;
//		case 3:
//			checkIt(trigger);
//			break;
//		case 4:
//			checkIt(trigger);
//			break;
//		}		
//	}
	
//	/**
//	 * 
//	 * @param trigger
//	 */
//	private void commentedOutMethod3(int trigger) {
//		switch (trigger) {
//		case 1:
//			checkIt(trigger);
//			break;
//		case 2:
//			checkIt(trigger);
//			break;
//		case 3:
//			checkIt(trigger);
//			break;
//		case 4:
//			checkIt(trigger);
//			break;
//		}		
//	}

	}