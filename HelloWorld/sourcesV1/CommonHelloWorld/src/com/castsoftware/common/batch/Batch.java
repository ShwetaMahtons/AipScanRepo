package com.castsoftware.common.batch;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.castsoftware.common.exceptions.InitializationException;
import com.castsoftware.common.exceptions.ProcessException;
import com.castsoftware.common.exec.ReturnCode;

public abstract class Batch {
	static Logger logger = Logger.getLogger(Batch.class);
	private HelpFormatter helpFormatter = null;
	protected Options options = null;
	
	protected Configuration config;
	protected CommandLine cmd;
	protected String propertiesFile = "";
	
	/**
	 * customize arguments to retrieve
	 */
	abstract protected void createCmdArgOptions();
	
	/**
	 * batch processing ...
	 * @throws InitializationException
	 * @throws ProcessException
	 */
	abstract protected void traitementBatch() throws InitializationException, ProcessException;
	
	/**
	 * retrieve arguments
	 */
	abstract protected void getArgument();
	
	abstract protected String getDefaultPropertieFile();
	abstract protected void getProperties();
	/**
	 * Format and print the CLI options help
	 */
	protected void printHelp() {
		// Automatically generate the help statement
		helpFormatter = new HelpFormatter();
		helpFormatter.printHelp("DispatchVM", options);
	}
	
	/**
	 * Construtor
	 */
	public Batch(String[] args,final String pPropertieFile) {
		super();
		options = new Options();
		// Help
		options.addOption(new Option("help", "print the help"));	
		String propertiesFilePath = System.getProperty("user.dir") + "\\conf\\log4j.properties";		
		int returnCode = ReturnCode.RETURN_CODE_OK;
		createCmdArgOptions();
		try {
			parseCmdArgs(args,pPropertieFile);
			getArgument();
			PropertyConfigurator.configure(propertiesFilePath);
		} catch (Exception e1) {
			printHelp();
			logger.error("ERREUR dans les arguments du programme");
			System.exit(ReturnCode.RETURN_CODE_ERROR);
		}
		// loading properties from properties file
		try {
			loadProperties();
			getProperties();
		} catch (InitializationException e1) {
			logger.error("ERREUR dans le chargement du fichier de propriete");
			System.exit(ReturnCode.RETURN_CODE_ERROR);
		}						
		try{
			traitementBatch();
		} catch (InitializationException e) {
			logger.error("ERREUR"+e.getMessage());
			returnCode = ReturnCode.RETURN_CODE_ERROR;
		} catch (ProcessException e) {
			logger.error("ERREUR"+e.getMessage());
			returnCode = ReturnCode.RETURN_CODE_ERROR;
		}		
		logger.info("Return Code = " + returnCode);
		System.exit(returnCode);
	}



	/**
	 * chargement des propriétés du fichier de propriété
	 * 
	 * @throws InitializationException
	 */
	protected void loadProperties() throws InitializationException {
		try {
			config = new PropertiesConfiguration(propertiesFile);
		} catch (org.apache.commons.configuration.ConfigurationException e) {
			throw new InitializationException(
					"ERREUR dans le chargement du fichier de propriete\n");
		}
	}
	/**
	 * Parsing commands arguments
	 * @param args
	 * @throws InitializationException
	 */
	protected void parseCmdArgs(String[] args, final String pPropertieFile) throws InitializationException{		
		try {
			cmd = new GnuParser().parse(options, args);
		} catch (ParseException e) {
			throw new InitializationException(
					"ERREUR dans la lecture des arguments du programme");
		}
		if (cmd.hasOption("help")) {
			printHelp();
		}
		propertiesFile = (cmd.hasOption("p")) ? cmd.getOptionValue("p"): getDefaultPropertieFile()+ ".properties";
	}
	
	public Configuration getConfig(){ return config;}
}	
