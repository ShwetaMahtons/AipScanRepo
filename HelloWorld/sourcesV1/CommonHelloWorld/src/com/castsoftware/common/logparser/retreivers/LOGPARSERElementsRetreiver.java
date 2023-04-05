package com.castsoftware.common.logparser.retreivers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.castsoftware.common.exceptions.InitializationException;
import com.castsoftware.common.exceptions.ProcessException;
import com.castsoftware.common.exec.RuntimeX;
import com.castsoftware.common.file.FileUtils;
import com.castsoftware.common.logparser.ElementBound;
import com.castsoftware.common.logparser.ElementValue;

public class LOGPARSERElementsRetreiver extends AbstractElementsRetreiver {

	private static final org.apache.log4j.Logger logger = Logger
			.getLogger(LOGPARSERElementsRetreiver.class);

	private String fromClause;
	private String group = null;
	private String prefix = null;

	public LOGPARSERElementsRetreiver(Configuration config, String prefix,
			String group, String fromClause) {
		super(config);
		this.prefix = prefix;
		this.group = group;
		this.fromClause = fromClause;
		//
		((AbstractConfiguration) config).setThrowExceptionOnMissing(true);
	}

	private String preprocess(String prefix, String group, String fromClause)
			throws ProcessException {
		String tempQueryFilename, csvFilename = null, command, commandReplaced;
		String configRequestProp, configRequestValue;
		int i = 1;
		FileWriter fileWriter;

		command = config.getString(prefix + ".preprocessor.command");
		csvFilename = prefix + "_" + group + "_results.csv";
		configRequestProp = prefix + ".preprocessor." + group + ".request." + i;
		//
		try {
			fileWriter = new FileWriter(csvFilename, false);
			fileWriter.close();
		} catch (IOException e) {
			throw new ProcessException(e);
		}
		//
		while (config.containsKey(configRequestProp)) {
			configRequestValue = config.getString(configRequestProp);
			tempQueryFilename = prefix + "_" + group + "_request_" + i
					+ "_query.sql";
			// Create query file
			try {
				fileWriter = new FileWriter(tempQueryFilename);
				fileWriter.write(configRequestValue);
				fileWriter.close();
			} catch (IOException e) {
				throw new ProcessException(e);
			}
			//
			commandReplaced = command.replaceAll("\\{tempqueryfile\\}",
					tempQueryFilename);
			commandReplaced = commandReplaced.replaceAll("\\{fromClause\\}",
					FileUtils.doubleBackslash(fromClause));
			commandReplaced = commandReplaced.replaceAll("\\{tempcsvfile\\}",
					csvFilename);
			logger.info("Lauch the command [" + commandReplaced + "]");
			RuntimeX.exec(commandReplaced);
			//
			i++;
			configRequestProp = prefix + ".preprocessor." + group + ".request."
					+ i;
		}
		//
		return csvFilename;
	}

	private void fillElementValuesFromCSV(
			HashMap<String, ElementValue> elementValues, String filename,
			char sep) throws ProcessException {
		ElementValue elementValue;
		CSVReader csvReader;
		String[] csvLine;

		try {
			csvReader = new CSVReader(new FileReader(filename), sep);
			csvLine = csvReader.readNext();
			while (csvLine != null) {
				elementValue = new ElementValue(csvLine[0], Float
						.valueOf(csvLine[1]));
				elementValues.put(group + "_" + csvLine[0], elementValue);
				logger.debug(elementValue + " created");
				csvLine = csvReader.readNext();
			}
		} catch (FileNotFoundException e) {
			throw new ProcessException(e);
		} catch (IOException e) {
			throw new ProcessException(e);
		}
	}

	public HashMap<String, ElementBound> getElementBounds()
			throws InitializationException, ProcessException {
		return null;
	}

	public void addElementValues(HashMap<String, ElementValue> elementValues)
			throws ProcessException {
		String cvsFilename;
		//
		cvsFilename = preprocess(prefix, group, fromClause);
		fillElementValuesFromCSV(elementValues, cvsFilename, ',');
	}
}
