package com.castsoftware.common.batch;

import org.apache.commons.cli.OptionBuilder;
import org.apache.log4j.Logger;


import com.castsoftware.common.batch.Batch;
import com.castsoftware.common.exceptions.InitializationException;
import com.castsoftware.common.exceptions.ProcessException;

public class Template extends Batch{
	static Logger logger = Logger.getLogger(Template.class);
	// set here propertie file name 
	public static final String RPOPERTIES_FILE_NAME="Template";
	// BEGIN add argument to retrieve here ... 
	String outputReport=null; 
	// END add argument to retrieve here ...
	
	
	public Template(String[] args) {
		super(args,Template.RPOPERTIES_FILE_NAME);
	}

	public static void main(String[] args) {	
		Template Template = new Template(args);
	}	
	
	@Override
	protected void createCmdArgOptions() {
		options.addOption(OptionBuilder.hasArg().withDescription("Property file").create('p'));
		options.addOption(OptionBuilder.hasArg().withDescription("output report").create('o'));
	}
	
	/**
	 * Parsing commands arguments
	 * @param args
	 * @throws InitializationException
	 */
	@Override
	protected void getArgument(){
		
		outputReport = (cmd.hasOption("o")) ? cmd.getOptionValue("o"): null;
	}

	@Override
	protected void traitementBatch() throws InitializationException,
			ProcessException {
		// TODO Auto-generated method stub		
	}

	protected String getDefaultPropertieFile(){return Template.RPOPERTIES_FILE_NAME;}

	@Override
	protected void getProperties() {
		// TODO Auto-generated method stub
		
	}
}
