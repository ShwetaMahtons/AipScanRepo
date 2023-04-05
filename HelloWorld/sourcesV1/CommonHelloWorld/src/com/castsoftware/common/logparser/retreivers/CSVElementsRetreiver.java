package com.castsoftware.common.logparser.retreivers;

import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.castsoftware.common.exceptions.InitializationException;
import com.castsoftware.common.logparser.ElementBound;
import com.castsoftware.common.logparser.ElementValue;

public class CSVElementsRetreiver {

	private static final org.apache.log4j.Logger logger = Logger
			.getLogger(CSVElementsRetreiver.class);
	private CSVReader csvReader = null;

	public CSVReader getCsvReader() {
		return csvReader;
	}

	public CSVElementsRetreiver(String CSVPathFileName, char csvSeparator) throws InitializationException {
		try {
			csvReader = new CSVReader(new FileReader(CSVPathFileName), csvSeparator);
		} catch (Exception e) {
			logger
					.log(Level.ERROR, "Error reading the file "
							+ CSVPathFileName);
			throw new InitializationException(e);
		}
	}

	protected Float toFloat(String value) {
		Float retFloat = null;
		
		if (value != null && value.length() > 0) {
			retFloat = Float.valueOf(value);
		}
		return retFloat;
	}
	
	protected Integer toInteger(String value) {
		Integer retInteger = null;
		
		if (value != null && value.length() > 0) {
			retInteger = Integer.valueOf(value);
		}
		return retInteger;
	}
	
	protected Boolean toBoolean(String value) {
		Boolean retBoolean = null;
		
		if (value != null && value.length() > 0) {
			retBoolean = Boolean.valueOf(value);
		}
		return retBoolean;
	}
	
	public HashMap<String, ElementValue> getElementBounds()
			throws InitializationException {
		Collection<ElementBound> collection = new Vector<ElementBound>();
		ElementBound elementBound;
		String[] nextLine;

		try {
			while ((nextLine = csvReader.readNext()) != null) {
				// nextLine[] is an array of values from the line
				elementBound = new ElementBound(nextLine[0],
						toFloat(nextLine[1]),
						toFloat(nextLine[2]),
						toFloat(nextLine[3]),
						toInteger(nextLine[4]),
						toInteger(nextLine[5]),
						toBoolean(nextLine[6]));
				collection.add(elementBound);
				logger.log(Level.DEBUG, "Element added : "
						+ elementBound);
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, "Error processing the CSV file "
					+ csvReader);
			throw new InitializationException(e);
		}
		//
		return null;
	}

	public HashMap<String, ElementValue> getElementValues()
			throws InitializationException {
		HashMap<String, ElementValue> hashMap = new HashMap<String, ElementValue>();
		ElementValue elementValue;
		String[] nextLine;
		try {
			while ((nextLine = csvReader.readNext()) != null) {
				// nextLine[] is an array of values from the line
				elementValue = new ElementValue(nextLine[0], toFloat(nextLine[1]));
				hashMap.put(elementValue.getKey(), elementValue);
				logger.log(Level.DEBUG, "Element added : "
						+ elementValue);
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, "Error processing the CSV file "
					+ csvReader);
			throw new InitializationException(e);
		}
		//
		return hashMap;
	}

}
