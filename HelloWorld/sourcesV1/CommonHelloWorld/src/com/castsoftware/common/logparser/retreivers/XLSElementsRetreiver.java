package com.castsoftware.common.logparser.retreivers;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.castsoftware.common.exceptions.InitializationException;
import com.castsoftware.common.logparser.ElementBound;
import com.castsoftware.common.logparser.ElementValue;

public class XLSElementsRetreiver {
	
	private static final org.apache.log4j.Logger logger = Logger
	.getLogger(CSVElementsRetreiver.class);

	private Collection<ElementBound> elementBounds;
	private HashMap<String, ElementValue> elementValues;
	private Sheet currentSheet;
	
	public Sheet getCurrentSheet() {
		return currentSheet;
	}

	public XLSElementsRetreiver(String CSVPathFileName) throws InitializationException {
		try  {
		Workbook w = Workbook.getWorkbook(new File(CSVPathFileName));
		currentSheet = w.getSheet(1);
		} catch (Exception e) {
			logger.log(Level.ERROR, "Error reading the file " + CSVPathFileName);
			throw new InitializationException(e);
		}
	}
	
	public Collection<ElementBound> getElementBounds()
	{
		Sheet currentSheet = getCurrentSheet();
		int nbRows = currentSheet.getRows();
    	Cell[] rows = null;
    	Collection<ElementBound> collection = new Vector<ElementBound>();
    	ElementBound elementBound;
    	
    	for (int i = 1 ; i < nbRows ; i++){
    		rows = currentSheet.getRow(i);
    		elementBound = new ElementBound(
    				rows[0].getContents(),
    				Float.valueOf(rows[1].getContents()),
    				Float.valueOf(rows[2].getContents()),
    				Float.valueOf(rows[3].getContents()),
    				Integer.valueOf(rows[4].getContents()),
    				Integer.valueOf(rows[5].getContents()),
    				Boolean.valueOf(rows[6].getContents()));
    		collection.add(elementBound);
    	}
    	//
    	return collection;
	}
	
	public HashMap<String, ElementValue> getElementValues()
	{
		Sheet currentSheet = getCurrentSheet();
		int nbRows = currentSheet.getRows();
    	Cell[] rows = null;
    	HashMap<String, ElementValue> hashMap = new HashMap<String, ElementValue>();
    	ElementValue elementValue;
    	
    	for (int i = 1 ; i < nbRows ; i++){
    		rows = currentSheet.getRow(i);
    		elementValue = new ElementValue(
    				rows[0].getContents(),
    				Float.valueOf(rows[1].getContents()));
    		hashMap.put(elementValue.getKey(), elementValue);
    	}
    	//
    	return hashMap;
	}
	
	
}
