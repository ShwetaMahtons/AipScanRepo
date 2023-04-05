package com.castsoftware.common.logparser.retreivers;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.castsoftware.common.exceptions.InitializationException;
import com.castsoftware.common.logparser.ElementBound;
import com.castsoftware.common.logparser.ElementValue;

public class PROPElementsRetreiver extends AbstractElementsRetreiver {

	private static final org.apache.log4j.Logger logger = Logger
			.getLogger(PROPElementsRetreiver.class);

	String prefix;
	String group;

	public PROPElementsRetreiver(Configuration config, String prefix,
			String group) {
		super(config);
		this.prefix = prefix;
		this.group = group;
	}

	private void fillElementBound(HashMap<String, ElementBound> elementBounds,
			String propKey) throws InitializationException {
		ElementBound elementBound;
		String propKeys[];
		String key, propertie;

		propKeys = propKey.split("\\.");
		key = propKeys[2] + "_" + propKeys[3];
		//
		elementBound = elementBounds.get(key);
		if (elementBound == null) {
			elementBound = new ElementBound(propKeys[3]);
			elementBounds.put(key, elementBound);
		}
		//
		propertie = propKeys[4];
		if (propertie.equalsIgnoreCase("mustExists")) {
			elementBound.setMustExists(config.getBoolean(propKey));
		}
		if (propertie.equalsIgnoreCase("nbMax")) {
			elementBound.setNbMax(config.getInt(propKey));
		}
		if (propertie.equalsIgnoreCase("nbMin")) {
			elementBound.setNbMin(config.getInt(propKey));
		}
		if (propertie.equalsIgnoreCase("pctMax")) {
			elementBound.setPctMax(config.getFloat(propKey));
		}
		if (propertie.equalsIgnoreCase("pctMin")) {
			elementBound.setPctMin(config.getFloat(propKey));
		}
		if (propertie.equalsIgnoreCase("refValue")) {
			elementBound.setRefValue(config.getFloat(propKey));
		}
		if (propertie.equalsIgnoreCase("compare")) {
			elementBound.setCompare(config.getBoolean(propKey));
		}
	}

	@SuppressWarnings("unchecked")
	public void addElementBounds(HashMap<String, ElementBound> elementBounds)
			throws InitializationException {
		String propKey;
		int nbFilled = 0;
		
		for (Iterator<String> it = config.getKeys(prefix + ".bounds." + group); it
				.hasNext();) {
			propKey = it.next();
			logger.debug("Treat key [" + propKey + "]");
			fillElementBound(elementBounds, propKey);
			nbFilled++;
		}
		//
		if (nbFilled == 0) {
			throw new InitializationException("No key found for [" + prefix + ".bounds." + group + "]");
		}
		
	}
}
