package com.castsoftware.common.common.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVWriter;

import com.castsoftware.common.exceptions.InitializationException;
import com.castsoftware.common.exceptions.ProcessException;

public class WriteCSV {

	
	
	public static FileWriter writer;
	
	public WriteCSV(String pathName, String fileName){
		try
		{
		  File fDir= new File(pathName);
		  if (fDir.exists()){
			  writer = new FileWriter(pathName +"/"+ fileName);
		  }
		  else
		  {
			  fDir.mkdir();
			  writer = new FileWriter(pathName +"/"+ fileName);
		  }
		  
		  
		
		}
		catch(Exception e)
		{
		  e.printStackTrace();
		}	
	}
		

	public void closeFile() throws IOException{
		writer.flush();
		writer.close();
	}
	
	public  int writeEntry(String[] inputData){
		try
		{
			for(int i = 0; i < inputData.length; i++)
		     {
		        writer.append(inputData[i]);
		        if(i < inputData.length - 1)
		           writer.append(',');
		     }
		     writer.append('\n');
		}
		catch(Exception e)
		{
		  e.printStackTrace();
		}
		
		return 1;
	}
	

	

}
