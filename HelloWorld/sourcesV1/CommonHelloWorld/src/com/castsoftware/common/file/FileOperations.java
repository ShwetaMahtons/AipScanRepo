package com.castsoftware.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileOperations {

		
		 public static boolean deleteFile(String srFile){
			 boolean returnCopyOK = true; 
			 File file = new File(srFile);
			 boolean success = file.delete();
			if (!success) {
				 returnCopyOK=false;
			}

			return returnCopyOK;
		 }
	
		 
		 public static boolean renameFile(String srFile, String dtFile){
			 boolean returnCopyOK = true;
			// File (or directory) with old name
			File file = new File(srFile);
			// File (or directory) with new name
			File file2 = new File(dtFile);
			    
			// Rename file (or directory)
			boolean success = file.renameTo(file2);
			if (!success) {
			   returnCopyOK=false;
			 }

			 return returnCopyOK;
		 }
		
		
		  public static boolean copyFile(String srFile, String dtFile){
			    
			  boolean returnCopyOK = true;
			  
			  try{
		      File f1 = new File(srFile);
		      File f2 = new File(dtFile);
		      InputStream in = new FileInputStream(f1);
		      
		      //For Append the file.
//		      OutputStream out = new FileOutputStream(f2,true);

		      //For Overwrite the file.
		      OutputStream out = new FileOutputStream(f2);

		      byte[] buf = new byte[1024];
		      int len;
		      while ((len = in.read(buf)) > 0){
		        out.write(buf, 0, len);
		      }
		      in.close();
		      out.close();
		      System.out.println("File copied.");
		    }
		    catch(FileNotFoundException ex){
		    	returnCopyOK=false;
		    }
		    catch(IOException e){
		    	returnCopyOK=false;  
		    }
		    return returnCopyOK;
		  }
}

