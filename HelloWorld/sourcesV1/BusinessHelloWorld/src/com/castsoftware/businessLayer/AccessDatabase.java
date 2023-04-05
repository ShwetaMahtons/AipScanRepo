package com.castsoftware.businessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import com.castsoftware.common.Utils_ST;


public class AccessDatabase {


	/**
	 * get facture method
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String getFacture() throws ClassNotFoundException,SQLException{
      Class.forName("oracle.jdbc.driver.OracleDriver");
      String url = "jdbc:oracle:thin:@localhost:1521/cqcuser";
      String title = new String();
      Connection cnt;
		try {
			cnt = DriverManager.getConnection(url,"participating_jee","cast");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		}
      Statement stt = cnt.createStatement();
      ResultSet rs = stt.executeQuery("SELECT name FROM hw_facture where id=1");
      while (rs.next()) {
         title = rs.getString("name");
      }
      stt.close();
      cnt.close();
      return title;
	}

	/**
	 * get company method
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String getCompany() throws ClassNotFoundException, SQLException{
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      String url = "jdbc:oracle:thin:@localhost:1521/cqcuser";
	      String title = new String();
	      Connection cnt = DriverManager.getConnection(url,"participating_jee","cast");
	      Statement stt = cnt.createStatement();
	      ResultSet rs = stt.executeQuery("SELECT name FROM hw_company where id_COMPANY=1");
	      while (rs.next()) {
	         title = rs.getString("name");
	         title = Utils_ST.returnSubString(title, 5);
	      }
	      stt.close();
	      cnt.close();
	      return title;
		}
	
	/**
	 * get contrat method
	 * @return String
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String getContrat() throws ClassNotFoundException, SQLException{
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      String url = "jdbc:oracle:thin:@localhost:1521/cqcuser";
	      String title = new String();
	      Connection cnt = DriverManager.getConnection(url,"participating_jee","cast");
	      Statement stt = cnt.createStatement();
	      ResultSet rs = stt.executeQuery("SELECT name FROM hw_contrat where id_contrat=1");
	      while (rs.next()) {
	         title = rs.getString("name");
	      }
	      stt.close();
	      cnt.close();
	      return title;
		}
	
	/**
	 * Fake method for dashboard violation purpose only
	 * @return
	 * @throws Exception
	 */
	public String fakeMethodWithExeceptionViolation() throws Exception {
		String title = new String();  
		try {
		  Class.forName("oracle.jdbc.driver.OracleDriver");
	      String url = "jdbc:oracle:thin:@localhost:1521/cqcuser";
	      
	      Connection cnt = DriverManager.getConnection(url,"participating_jee","cast");
	      Statement stt = cnt.createStatement();
	      ResultSet rs = stt.executeQuery("SELECT name FROM hw_contrat where id_contrat=1");
	      while (rs.next()) {
	         title = rs.getString("name");
	      }
	      stt.close();
	      cnt.close();
	      }catch (Exception e){
	    	throw e;  
	      }
	      return title;
	}
	/**
	 * Fake method for dashboard violation purpose only	
	 * @return
	 * @throws Exception
	 */
	public String fakeMethodWithExeceptionViolationEmptyCatch() throws Exception {
		String title = new String();  
		try {
		  Class.forName("oracle.jdbc.driver.OracleDriver");
	      String url = "jdbc:oracle:thin:@localhost:1521/cqcuser";
	      
	      Connection cnt = DriverManager.getConnection(url,"participating_jee","cast");
	      Statement stt = cnt.createStatement();
	      ResultSet rs = stt.executeQuery("SELECT name FROM hw_contrat where id_contrat=1");
	      while (rs.next()) {
	         title = rs.getString("name");
	      }
	      stt.close();
	      cnt.close();
	      }catch (Exception e){
	    	// empty catch
	      }
	      return title;
	}
	
	/**
	 * Fake method for dashboard violation purpose only
	 * @return
	 * @throws Exception
	 */
	public String fakeMethodWithExeceptionViolationNoThrowingException() throws Exception {
		String title = new String();  
		try {
		  Class.forName("oracle.jdbc.driver.OracleDriver");
	      String url = "jdbc:oracle:thin:@localhost:1521/cqcuser";
	      
	      Connection cnt = DriverManager.getConnection(url,"participating_jee","cast");
	      Statement stt = cnt.createStatement();
	      ResultSet rs = stt.executeQuery("SELECT name FROM hw_contrat where id_contrat=1");
	      while (rs.next()) {
	         title = rs.getString("name");
	      }
	      stt.close();
	      cnt.close();
	      }catch (Exception e){
	    	// no throwing exception 
	      }
	      return title;
	}
	
	/**
	 * Fake method for dashboard violation purpose only with method invocation in a loop
	 * @return
	 * @throws Exception
	 */
	public String fakeMethodWithMethodInvocationInALoop() throws Exception {
		String title = new String();  
		try {
		  Class.forName("oracle.jdbc.driver.OracleDriver");
	      String url = "jdbc:oracle:thin:@localhost:1521/cqcuser";
	      
	      Connection cnt = DriverManager.getConnection(url,"participating_jee","cast");
	      Statement stt = cnt.createStatement();
	      ResultSet rs = stt.executeQuery("SELECT name FROM hw_contrat where id_contrat=1");
	      while (rs.next()) {
	         title = rs.getString("name");
	      }
	      stt.close();
	      cnt.close();
	      }catch (Exception e){
	    	// no throwing exception 
	      }
	      
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
	

	/**
	 * Fake method for dashboard violation purpose only
	 * @return
	 * @throws Exception
	 */
	public String fakeMethodWithIfStatementUse(){
		try{
			Integer in =  new Integer("11");
		}catch(Exception e){
			e.getMessage();
			try {
				finalize();
			} catch (Throwable es) {
				// TODO Auto-generated catch block
				es.printStackTrace();
			}
		}
		boolean i= true;
		String returnVal = "OK";
		if (i==true){
			returnVal = "KO";
		}else{
			returnVal = "OK";
		}		
		if (isFakeMethodForIfStatenemtTRUE()==true){
			returnVal = "KO";
		}else{
			returnVal = "OK";
		}		
		if (isFakeMethodForIfStatenemtFALSE()==true){
			returnVal = "KO";
		}else{
			returnVal = "OK";
		}		
		return returnVal;
	}
	/**
	 * Fake method for dashboard violation purpose only
	 * @return
	 * @throws Exception
	 */
	public String fakeMethodWithIfStatementUseWithOutSpace(){
		String returnVal = "";
		if(isFakeMethodForIfStatenemtFALSE()==true){
			returnVal = "KO";
		}else{
			returnVal = "OK";
		}
		
		
		return returnVal;
	}
	
	
	/**
	 * Fake method for dashboard violation purpose only
	 * @return
	 * @throws Exception
	 */
	public String fakeMethodWithIfStatementUseWithSpace(){
		String returnVal = "";
		if (      isFakeMethodForIfStatenemtFALSE()==    true    ){
			returnVal = "KO";
		}else{
			returnVal = "OK";
		}
		
		
		return returnVal;
	}
	
		
	
	
	/**
	 * Fake method for ifStatement
	 * @return true
	 */
	public boolean isFakeMethodForIfStatenemtTRUE() {
		return true;
	}

	
	/**
	 * Fake method for ifStatement
	 * @return false
	 */
	public boolean isFakeMethodForIfStatenemtFALSE() {
		return false;
	}

	
	/**
	 * Fake method for dashboard violation purpose only
	 * @return
	 * @throws Exception
	 */
	public String fakeMethodWithHighComplexity() throws Exception {
		String title = new String();
		int trigger = 0;
		if(title.equals("test")){
			
		}else if (title.equals("test2")){
			switch (trigger) {
			case 1:
				checkIt(trigger);
				break;
			case 2:
				checkIt(trigger);
				break;
			case 3:
				checkIt(trigger);
				break;
			case 4:
				checkIt(trigger);
				break;
			}
		}else if (title.equals("test3")){
			switch (trigger) {
			case 1:
				checkIt(trigger);
				break;
			case 2:
				checkIt(trigger);
				break;
			case 3:
				checkIt(trigger);
				break;
			case 4:
				checkIt(trigger);
				break;
			}
		}else if (title.equals("test4")){
			switch (trigger) {
			case 1:
				checkIt(trigger);
				break;
			case 2:
				checkIt(trigger);
				break;
			case 3:
				checkIt(trigger);
				break;
			case 4:
				checkIt(trigger);
				break;
			}
		}else if (title.equals("test5")){
			switch (trigger) {
			case 1:
				checkIt(trigger);
				break;
			case 2:
				checkIt(trigger);
				break;
			case 3:
				checkIt(trigger);
				break;
			case 4:
				checkIt(trigger);
				break;
			}
		}else if (title.equals("test6")){
			switch (trigger) {
			case 1:
				checkIt(trigger);
				break;
			case 2:
				checkIt(trigger);
				break;
			case 3:
				checkIt(trigger);
				break;
			case 4:
				checkIt(trigger);
				break;
			}
		}else if (title.equals("test7")){
			switch (trigger) {
			case 1:
				checkIt(trigger);
				break;
			case 2:
				checkIt(trigger);
				break;
			case 3:
				checkIt(trigger);
				break;
			case 4:
				checkIt(trigger);
				break;
			}
		}else if (title.equals("test8")){
			switch (trigger) {
			case 1:
				checkIt(trigger);
				break;
			case 2:
				checkIt(trigger);
				break;
			case 3:
				checkIt(trigger);
				break;
			case 4:
				checkIt(trigger);
				break;
			}
		
		}
		return "";
		}

	private void checkIt(int trigger) {
		switch (trigger) {
		case 1:
			checkIt(trigger);
			break;
		case 2:
			checkIt(trigger);
			break;
		case 3:
			checkIt(trigger);
			break;
		case 4:
			checkIt(trigger);
			break;
		}		
	}
		
	
}

