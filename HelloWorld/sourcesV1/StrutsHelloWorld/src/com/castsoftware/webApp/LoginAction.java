package com.castsoftware.webApp;


import java.util.Date;

import com.castsoftware.businessLayer.AccessDatabase;
import com.castsoftware.common.DateUtils_ST;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	private String username;
	private String password;

	public String execute() {
		try {
			String s = getNameFromDB();
			Date madate= new Date();
			madate = DateUtils_ST.parseDate(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getNameFromDB() throws Exception {
		AccessDatabase testJdbc = new AccessDatabase();
		return testJdbc.getFacture();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
