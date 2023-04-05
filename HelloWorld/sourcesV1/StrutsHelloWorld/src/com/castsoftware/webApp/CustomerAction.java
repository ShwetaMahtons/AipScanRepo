package com.castsoftware.webApp;

import com.opensymphony.xwork2.ActionSupport;

public class CustomerAction extends ActionSupport {
	private String name;
	private Integer age;
	private String email;
	private String telephone;


	/**
	 * addcustomer method
	 * @return String
	 */
	public String addCustomer() {
		return SUCCESS;
	}

	/**
	 * getter for name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter for name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter for Age
	 * @return String
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * Setter for age
	 * @param age
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * getter for email
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * setter for email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * getter for telephone
	 * @return String
	 */
	public String getTelephone() {
		return telephone;
	}
	
	/**
	 * setter for telephone
	 * @param telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	// fake method for Training purpose.
	public String testEmail(){
		String returnVal = "";
		String email = getEmail();
		if(email.indexOf("@") == -1){
			returnVal += "warning please put a validated email";
		}

		if(email.indexOf(".") == -1){
			returnVal += "warning please put a validated email";
		}
		
		if(email.length() == 0){
			returnVal += "set the email";
		}	
		return returnVal;
	}

}
