package com.castsoftware.common.logparser;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.castsoftware.common.exceptions.InitializationException;
import com.castsoftware.common.exceptions.ProcessException;
import com.castsoftware.common.exec.ReturnCode;
import com.castsoftware.common.file.DirUtils;
import com.castsoftware.common.file.FileUtils;
import com.castsoftware.common.logparser.retreivers.LOGPARSERElementsRetreiver;
import com.castsoftware.common.logparser.retreivers.PROPElementsRetreiver;

public class ContentComparator {

	private static final org.apache.log4j.Logger logger = Logger
			.getLogger(ContentComparator.class);

	private String groups[];
	private String prefix;
	private String contentType;
	private Configuration config;
	private String type;
	private String regexInc;
	private String regexExc;
	private String match;
	private boolean compare;

	// contentType = COBOL / DOTNET / ANARUN
	public ContentComparator(Configuration config, String prefix) {
		this.config = config;
		this.prefix = prefix;
		//
		((AbstractConfiguration) config).setThrowExceptionOnMissing(true);
		((AbstractConfiguration) config).setListDelimiter('|');
	}

	private void initProperties(String contentType) {
		this.contentType = contentType;
		this.groups = config.getStringArray(prefix + ".values." + contentType
				+ ".groups");
		this.type = config.getString(prefix + ".values." + contentType
				+ ".compare.type");
		this.regexInc = config.getString(prefix + ".values." + contentType
				+ ".compare.regexInc");
		this.regexExc = config.getString(prefix + ".values." + contentType
				+ ".compare.regexExc");
		this.match = config.getString(prefix + ".values." + contentType
				+ ".compare.match", "A");
		this.compare = config.getBoolean(prefix + ".values." + contentType
				+ ".compare");
	}

	private HashMap<String, ElementBound> getBoundsFromProperties()
			throws InitializationException {
		PROPElementsRetreiver propRetreiver;
		HashMap<String, ElementBound> elementBoundList = new HashMap<String, ElementBound>();

		for (int i = 0; i < groups.length; i++) {
			propRetreiver = new PROPElementsRetreiver(config, prefix, groups[i]);
			propRetreiver.addElementBounds(elementBoundList);
		}
		return elementBoundList;
	}

	public HashMap<String, ElementValue> getValuesFromLogparser(
			String fromClause) throws ProcessException {
		LOGPARSERElementsRetreiver logparserRetreiver;
		HashMap<String, ElementValue> elementValueList = new HashMap<String, ElementValue>();

		for (int i = 0; i < groups.length; i++) {
			logparserRetreiver = new LOGPARSERElementsRetreiver(config, prefix,
					groups[i], fromClause);
			logparserRetreiver.addElementValues(elementValueList);
		}
		//
		return elementValueList;
	}

	public int compare(String contentType, String baseDirV1, String baseDirV2,
			String p_regexInc, String p_regexExc)
			throws InitializationException, ProcessException {
		HashMap<String, ElementBound> elementBoundList = null;
		HashMap<String, ElementValue> newElementValueList = null;
		HashMap<String, ElementValue> refElementValueList = null;
		String replaceV1, replaceV2;
		List<File> fileListV1, fileListV2;
		ElementsComparator comparator = new ElementsComparator();
		int returnCode = ReturnCode.RETURN_CODE_OK;
		String regexIncValue = null, regexExcValue = null;

		initProperties(contentType);
		elementBoundList = getBoundsFromProperties();
		//
		regexIncValue = regexInc;
		if (p_regexInc != null && p_regexInc.length() > 0) {
			regexIncValue = p_regexInc;
		}
		regexExcValue = regexExc;
		if (p_regexExc != null && p_regexExc.length() > 0) {
			regexExcValue = p_regexExc;
		}
		//
		if (type.equalsIgnoreCase("file")) {
			fileListV1 = FileUtils.getFilesFromRegexp(baseDirV1, regexIncValue,
					regexExcValue);
			if (fileListV1.size() <= 0) {
				throw new ProcessException("Files not found for [directory="
						+ baseDirV1 + ", regexinc=" + regexIncValue
						+ ", regexexc=" + regexExcValue + "]");
			}
			for (File fileV1 : fileListV1) {
				replaceV1 = fileV1.getAbsolutePath().replaceAll(regexIncValue,
						match);
				if (compare && baseDirV2 != null && baseDirV2.length() > 0) {
					fileListV2 = FileUtils.getFilesFromRegexp(baseDirV2,
							regexIncValue, regexExcValue);
					if (fileListV2.size() <= 0) {
						throw new ProcessException(
								"Files not found for [directory=" + baseDirV2
										+ ", regexinc=" + regexIncValue
										+ ", regexexc=" + regexExcValue + "]");
					}
					for (File fileV2 : fileListV2) {
						replaceV2 = fileV2.getAbsolutePath().replaceAll(
								regexIncValue, match);
						if (replaceV2.equalsIgnoreCase(replaceV1)) {
							newElementValueList = getValuesFromLogparser(fileV1
									.getAbsolutePath());
							refElementValueList = getValuesFromLogparser(fileV2
									.getAbsolutePath());
							returnCode = ReturnCode.maxLevel(returnCode,
									comparator.compareElements(
											elementBoundList,
											newElementValueList,
											refElementValueList));
						}
					}
				} else {
					newElementValueList = getValuesFromLogparser(fileV1
							.getAbsolutePath());
					returnCode = ReturnCode.maxLevel(returnCode, comparator
							.compareElements(elementBoundList,
									newElementValueList, null));
				}
			}
		}
		//
		if (type.equalsIgnoreCase("dir")) {
			newElementValueList = getValuesFromLogparser(baseDirV1);
			refElementValueList = null;
			if (compare && baseDirV2 != null && baseDirV2.length() > 0) {
				refElementValueList = getValuesFromLogparser(baseDirV2);
			}
			returnCode = ReturnCode.maxLevel(returnCode, comparator
					.compareElements(elementBoundList, newElementValueList,
							refElementValueList));
		}
		//
		if (type.equalsIgnoreCase("dirs")) {
			fileListV1 = DirUtils.getDirsFromRegexp(baseDirV1, regexIncValue,
					regexExcValue, true);
			if (fileListV1.size() <= 0) {
				throw new ProcessException("Files not found for [directory="
						+ baseDirV1 + ", regexinc=" + regexIncValue
						+ ", regexexc=" + regexExcValue + "]");
			}
			for (File fileV1 : fileListV1) {
				replaceV1 = fileV1.getAbsolutePath().replaceAll(regexIncValue,
						match);
				if (compare && baseDirV2 != null && baseDirV2.length() > 0) {
					fileListV2 = DirUtils.getDirsFromRegexp(baseDirV2,
							regexIncValue, regexExcValue, true);
					if (fileListV2.size() <= 0) {
						throw new ProcessException(
								"Files not found for [directory=" + baseDirV2
										+ ", regexinc=" + regexIncValue
										+ ", regexexc=" + regexExcValue + "]");
					}
					for (File fileV2 : fileListV2) {
						replaceV2 = fileV2.getAbsolutePath().replaceAll(
								regexIncValue, match);
						if (replaceV2.equalsIgnoreCase(replaceV1)) {
							newElementValueList = getValuesFromLogparser(fileV1
									.getAbsolutePath());
							refElementValueList = getValuesFromLogparser(fileV2
									.getAbsolutePath());
							returnCode = ReturnCode.maxLevel(returnCode,
									comparator.compareElements(
											elementBoundList,
											newElementValueList,
											refElementValueList));
						}
					}
				} else {
					newElementValueList = getValuesFromLogparser(fileV1
							.getAbsolutePath());
					returnCode = ReturnCode.maxLevel(returnCode, comparator
							.compareElements(elementBoundList,
									newElementValueList, null));
				}
			}
		}
		//
		return returnCode;
	}
}
