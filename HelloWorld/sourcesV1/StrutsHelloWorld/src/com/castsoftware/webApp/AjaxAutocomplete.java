package com.castsoftware.webApp;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.opensymphony.xwork2.ActionSupport;

public class AjaxAutocomplete extends ActionSupport {
	private String data = "Afghanistan, Albania, Zimbabwe, India, Israel, United States, Uganda, Germany, Ghana, China, Czek Republic";
	private List<String> countries;
	private String country;
	
	public AjaxAutocomplete() {
	
	}
	public String execute() {
		countries = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(data, ",");

		while (st.hasMoreTokens()) {
			countries.add(st.nextToken().trim());
		}
		return SUCCESS;
	}
	public String getCountry() {
		return this.country;
	}

	public List<String> getCountries() {
		return countries;
	}

	public void setCountries(List<String> countries) {
		this.countries = countries;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
