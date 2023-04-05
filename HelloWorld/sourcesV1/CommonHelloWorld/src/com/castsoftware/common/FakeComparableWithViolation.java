package com.castsoftware.common;

import com.castsoftware.common.batch.Batch;

public class FakeComparableWithViolation implements Comparable<Batch>{

	// for CAST violation purpose only
	public int compareTo(Batch o) {
		if ( !(o instanceof Batch)) 
			return 1;
		return 0;
	}

}
