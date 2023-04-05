/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.castsoftware.webapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import com.castsoftware.businessLayer.AccessDatabase;
import com.castsoftware.common.DateUtils_ST;


/**
 *
 * @author nlam@castsoftware.com
 */
public class ExcelAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private final static String SUCCESS = "success";
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ExcelForm excelForm = (ExcelForm) form;
        UserData userData = new UserData();
        excelForm.setUserList(userData.loadData());
        
        method();
		
        return mapping.findForward(SUCCESS);
    }
    
    private void method(){
    	 try {
 			String s = getNameFromDB();
 			Date madate= new Date();
 			madate = DateUtils_ST.parseDate(s);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    }
    
    /**
     * get facture name from Database
     * @return
     * @throws Exception
     */
	public String getNameFromDB() throws Exception {
		AccessDatabase testJdbc = new AccessDatabase();
		String facture =  testJdbc.getFacture();
		return facture;
	}

	/**
	 * Forbidden access to database must be detected by Architecture checker
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String getFactureDirectAccess() throws ClassNotFoundException, SQLException{
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      String url = "jdbc:oracle:thin:@localhost:1521/cqcuser";
	      String title = new String();
	      Connection cnt = DriverManager.getConnection(url,"participating_jee","cast");
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
	 * Forbidden access to database must be detected by Architecture checker
	 * @return String
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String getBusinessTypeDirectAccess() throws ClassNotFoundException, SQLException{
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      String url = "jdbc:oracle:thin:@localhost:1521/cqcuser";
	      String title = new String();
	      Connection cnt = DriverManager.getConnection(url,"participating_jee","cast");
	      Statement stt = cnt.createStatement();
	      ResultSet rs = stt.executeQuery("SELECT name FROM HW_BUSINESS_TYPE where id=1");
	      while (rs.next()) {
	         title = rs.getString("name");
	      }
	      stt.close();
	      cnt.close();
	      return title;
		}
	
	/**
	 * Forbidden access to database must be detected by Architecture checker
	 * @return String
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String getCompanyTypeDirectAccess() throws ClassNotFoundException, SQLException{
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      String url = "jdbc:oracle:thin:@localhost:1521/cqcuser";
	      String title = new String();
	      Connection cnt = DriverManager.getConnection(url,"participating_jee","cast");
	      Statement stt = cnt.createStatement();
	      ResultSet rs = stt.executeQuery("SELECT name FROM HW_COMPANY_TYPE where id=1");
	      while (rs.next()) {
	         title = rs.getString("name");
	      }
	      stt.close();
	      cnt.close();
	      return title;
		}

}

