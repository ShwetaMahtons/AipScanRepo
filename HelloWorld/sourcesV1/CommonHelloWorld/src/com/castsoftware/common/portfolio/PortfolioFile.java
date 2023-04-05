package com.castsoftware.common.portfolio;

import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class PortfolioFile {
	private static final Log logger = LogFactory
	.getLog(PortfolioFile.class);

	public enum Type {
		COBOL, DOTNET
	}

	protected Type type = null;
	protected String name = null;

	protected PortfolioLevel portfolioLevel = null;
	protected Collection<String> filenameList = null;

	public PortfolioFile(String name, Type type, PortfolioLevel portfolioLevel) {
		this.name = name;
		this.type = type;
		this.portfolioLevel = portfolioLevel;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public void addFilename(String filename) {
		if (filenameList == null) {
			filenameList = new Vector<String>();
		}
		filenameList.add(filename);
	}

	public Collection<String> getFilenameList() {
		return filenameList;
	}
	
	public boolean containsFiles() {
		return filenameList.size() > 0; 
	}
	
	public PortfolioLevel getPortfolioLevel() {
		return portfolioLevel;
	}

	public PortfolioLevel getPreviousLevel(PortfolioLevel.Type type) {
		PortfolioLevel level;

		level = portfolioLevel;
		while (level != null) {
			if (level.getType().equals(type)) {
				return level;
			}
			level = level.getPreviousLevel();
		}
		return null;
	}
	
	public abstract boolean match(String filename);
	
	@Override
	public String toString() {
		return "PortfolioFile [name=" + name + ", type=" + type + "]";
	}
}
