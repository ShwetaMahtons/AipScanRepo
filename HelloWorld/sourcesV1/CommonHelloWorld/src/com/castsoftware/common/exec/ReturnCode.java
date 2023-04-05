package com.castsoftware.common.exec;

public class ReturnCode {
	public static int RETURN_CODE_OK = 0;
	public static int RETURN_CODE_WARN = 1;
	public static int RETURN_CODE_ERROR = 2;
	public static int RETURN_CODE_CRASH = 3;
	
	public static int maxLevel(int level1, int level2) {
		return (level1 > level2 ? level1 : level2);
	}
	
	public static int minLevel(int level1, int level2) {
		return (level1 < level2 ? level1 : level2);
	}
}
