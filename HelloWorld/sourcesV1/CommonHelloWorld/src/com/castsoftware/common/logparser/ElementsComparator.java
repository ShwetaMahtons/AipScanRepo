package com.castsoftware.common.logparser;

import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.castsoftware.common.exceptions.ProcessException;
import com.castsoftware.common.exec.ReturnCode;

public class ElementsComparator {

	private static final org.apache.log4j.Logger logger = Logger
			.getLogger(ElementsComparator.class);

	private int compareElement(ElementBound elementBound, float value,
			Float refValue) throws ProcessException {
		int returnCode = ReturnCode.RETURN_CODE_OK;
		boolean compared = false;

		logger.debug(value + " compare to " + elementBound);
		if (refValue != null) {
			// Pourcentage Minimun accepte
			if (elementBound.getPctMin() != null) {
				if (value < (refValue * (1 - (elementBound.getPctMin()) / 100))) {
					logger.error(value + " is out of bounds [PCT_MIN] of "
							+ elementBound);
					returnCode = ReturnCode.RETURN_CODE_ERROR;
				}
				compared = true;
			}
			// Pourcentage Maximum accepte
			if (elementBound.getPctMax() != null) {
				if (value > (refValue * (1 + (elementBound.getPctMax() / 100)))) {
					logger.error(value + " is out of bounds [PCT_MAX] of "
							+ elementBound);
					returnCode = ReturnCode.RETURN_CODE_ERROR;
				}
				compared = true;
			}
			// Nombre minium accepte
			if (elementBound.getNbMin() != null) {
				if (value < refValue + elementBound.getNbMin()) {
					logger.error(value + " is out of bounds [NB_MIN] of "
							+ elementBound);
					returnCode = ReturnCode.RETURN_CODE_ERROR;
				}
				compared = true;
			}
			// Nombre Maximum accepte
			if (elementBound.getNbMax() != null) {
				if (value > refValue + elementBound.getNbMax()) {
					logger.error(value + " is out of bounds [NB_MAX] of "
							+ elementBound);
					return ReturnCode.RETURN_CODE_ERROR;
				}
				compared = true;
			}
		} else {
			// Nombre minium accepte
			if (elementBound.getNbMin() != null) {
				if (value < elementBound.getNbMin()) {
					logger.error(value + " is out of bounds [NB_MIN] of "
							+ elementBound);
					returnCode = ReturnCode.RETURN_CODE_ERROR;
				}
				compared = true;
			}
			// Nombre Maximum accepte
			if (elementBound.getNbMax() != null) {
				if (value > elementBound.getNbMax()) {
					logger.error(value + " is out of bounds [NB_MAX] of "
							+ elementBound);
					return ReturnCode.RETURN_CODE_ERROR;
				}
				compared = true;
			}
		}
		//
		if (!compared) {
			throw new ProcessException("Nothing was compared [" + value + ","
					+ refValue + "," + elementBound);
		}

		//
		if (returnCode == ReturnCode.RETURN_CODE_OK) {
			logger.debug(value + " is inside the bounds of " + elementBound
					+ " for " + refValue);
		}
		//
		elementBound.setCompared(true);
		return returnCode;
	}

	private int compareElement(ElementBound elementBound,
			ElementValue elementValue) throws ProcessException {
		int errorCode;

		if (!elementBound.getKey().equalsIgnoreCase(elementValue.getKey())) {
			logger.error("Keys are different [" + elementBound + ", "
					+ elementValue + "]");
			return ReturnCode.RETURN_CODE_ERROR;
		}
		errorCode = compareElement(elementBound, elementValue.getValue(),
				elementBound.getRefValue());
		elementBound.setCompared(true);
		elementValue.setCompared(true);
		//
		return errorCode;
	}

	private int compareElement(ElementBound elementBound,
			ElementValue elementValue, ElementValue elementValueRef)
			throws ProcessException {
		int errorCode;

		if (elementValueRef == null) {
			return compareElement(elementBound, elementValue);
		}
		//
		if (!elementBound.getKey().equalsIgnoreCase(elementValue.getKey())
				|| !elementBound.getKey().equalsIgnoreCase(
						elementValueRef.getKey())) {
			logger.error("Keys are different [" + elementBound + ", "
					+ elementValueRef + ", " + elementValue + "]");
			return ReturnCode.RETURN_CODE_ERROR;
		}
		errorCode = compareElement(elementBound, elementValue.getValue(),
				elementValueRef.getValue());
		elementBound.setCompared(true);
		elementValueRef.setCompared(true);
		elementValue.setCompared(true);
		//
		return errorCode;
	}

	public int compareElements(HashMap<String, ElementBound> elementBounds,
			HashMap<String, ElementValue> elementValues,
			HashMap<String, ElementValue> elementValuesRef)
			throws ProcessException {
		int returnCode = ReturnCode.RETURN_CODE_OK, returnCodeDetail;
		ElementValue elementValue, elementValueRef = null;
		ElementBound elementBound = null;
		Collection<String> elementBoundKeyList;

		elementBoundKeyList = elementBounds.keySet();
		for (String elementBoundKey : elementBoundKeyList) {
			elementBound = elementBounds.get(elementBoundKey);
			elementValue = elementValues.get(elementBoundKey);
			if (elementValue == null) {
				if (elementBound.isMustExists()) {
					logger.error(elementBound
							+ " : This key is mandatory and cannot be found");
					returnCode = ReturnCode.RETURN_CODE_ERROR;
				} else {
					logger
							.warn(elementBound
									+ " : This key is NOT mandatory and was not found in the log");
				}
			} else {
				if (elementValuesRef != null) {
					elementValueRef = elementValuesRef.get(elementBoundKey);
					logger.debug("Found " + elementValueRef);
				}
				returnCodeDetail = compareElement(elementBound, elementValue,
						elementValueRef);
				returnCode = (returnCodeDetail == 0) ? returnCode
						: returnCodeDetail;
			}
		}
		//
		return returnCode;
	}

	public void completeLogs(HashMap<String, ElementValue> elementValues) {
		if (elementValues == null) {
			return;
		}
		//
		for (ElementValue e : (Collection<ElementValue>) elementValues.values()) {
			if (!e.isCompared()) {
				logger.log(Level.WARN, e + " does not have any bounds!");
			}
		}
	}

}
