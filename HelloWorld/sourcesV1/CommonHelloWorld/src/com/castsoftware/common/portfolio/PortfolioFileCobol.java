package com.castsoftware.common.portfolio;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PortfolioFileCobol extends PortfolioFile {
	private static final Log logger = LogFactory
	.getLog(PortfolioFileCobol.class);

	private String regexInc = null;
	private String regexExc = null;
	private String progType;

	public PortfolioFileCobol(String name, Type type,
			PortfolioLevel portfolioLevel, String regexInc, String regexExc,
			String progType) {
		super(name, type, portfolioLevel);
		//
		this.regexInc = regexInc;
		this.regexExc = regexExc;
		this.progType = progType;
	}

	public String getProgType() {
		return progType;
	}

	public void setProgType(String progType) {
		this.progType = progType;
	}

	public String getRegexInc() {
		return regexInc;
	}

	public String getRegexExc() {
		return regexExc;
	}
	
	public String computeDir() {
		String dir = "";
		PortfolioLevel level;

		level = portfolioLevel;
		while (level != null) {
			dir = FilenameUtils.concat(Portfolio.replaceDir(level.getName()), dir);
			level = level.getPreviousLevel();
		}
		return dir;
	}

	@Override
	public boolean match(String filename) {
		filename = FilenameUtils.getName(filename);
		return (filename.matches(regexInc) && !filename.matches(regexExc));
	}
}
