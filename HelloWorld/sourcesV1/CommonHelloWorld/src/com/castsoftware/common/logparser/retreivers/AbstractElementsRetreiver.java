package com.castsoftware.common.logparser.retreivers;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

public abstract class AbstractElementsRetreiver {

	private static final org.apache.log4j.Logger logger = Logger
			.getLogger(AbstractElementsRetreiver.class);
	protected Configuration config;

	protected AbstractElementsRetreiver(Configuration config) {
		this.config = config;
	}
}
