package com.castsoftware.common.portfolio;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.castsoftware.common.exceptions.InitializationException;

public class PortfolioLevel {

	public enum Type {
		TMA(0), MET(1), SA(2), GA(3), APP(4);

		private final int value;

		private Type(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}

		public static Type getNextType(Type type) {
			if (type.equals(TMA) || type.equals(MET)) {
				return SA;
			}
			if (type.equals(SA)) {
				return GA;
			}
			if (type.equals(GA)) {
				return APP;
			}
			return null;
		}
	}

	private static final Log logger = LogFactory.getLog(PortfolioLevel.class);

	protected String name;
	protected Type type;
	protected PortfolioLevel previousLevel;
	protected Collection<PortfolioFile> portfolioFileList = new Vector<PortfolioFile>();
	protected HashMap<String, PortfolioLevel> nextLevelList = new HashMap<String, PortfolioLevel>();
	protected Portfolio portfolio;

	public PortfolioLevel(String name, Type type, Portfolio portfolio,
			PortfolioLevel previousLevel) throws InitializationException {
		if (name == null || name.trim().length() <= 0) {
			throw new InitializationException(
					"the name of n element of the portfolio is blank!");
		}
		this.name = name;
		this.type = type;
		this.previousLevel = previousLevel;
		this.portfolio = portfolio;
	}

	public void addFile(PortfolioFile portfolioFile) {
		portfolioFileList.add(portfolioFile);
	}

	public HashMap<String, PortfolioLevel> getNextLevelList() {
		return nextLevelList;
	}

	private PortfolioLevel getNextLevel(String name)
			throws InitializationException {
		PortfolioLevel nextLevel;
		Type nextLevelType = null;
		String lowerCaseName;

		lowerCaseName = name.toLowerCase();
		nextLevel = nextLevelList.get(lowerCaseName);
		// logger.debug("Level : " + lowerCaseName + " = " + nextLevel +
		// " value " + this );
		if (!nextLevelList.containsKey(lowerCaseName)) {
			nextLevelType = Type.getNextType(this.type);
			nextLevel = new PortfolioLevel(name, nextLevelType, portfolio, this);
			portfolio.addPortfolioLevel(nextLevel);
			nextLevelList.put(lowerCaseName, nextLevel);
			// logger.debug("Level : " + nextLevel + " created in level : "+
			// this);
		}
		return nextLevel;
	}

	public void addNextLevel(PortfolioLevel nextLevel) {
		nextLevelList.put(nextLevel.getName().toLowerCase(), nextLevel);
	}

	public PortfolioLevel getPreviousLevel() {
		return previousLevel;
	}

	public Collection<PortfolioFile> getPortfolioFileList() {
		return portfolioFileList;
	}

	public String getName() {
		return name;
	}

	public String getFolderName() {
		return Portfolio.replaceDir(name);
	}

	public Type getType() {
		return type;
	}

	public Collection<PortfolioFile> getFileDefinitionList() {
		return portfolioFileList;
	}

	public Collection<PortfolioFile.Type> listFileDefinitionTypeList(
			boolean deep) {
		Collection<PortfolioFile.Type> list = new Vector<PortfolioFile.Type>();

		for (PortfolioFile f : getPortfolioFileList()) {
			if (!list.contains(f.getType())) {
				list.add(f.getType());
			}
		}
		//
		for (PortfolioLevel l : listPortfolioLevels(deep)) {
			for (PortfolioFile f : l.getPortfolioFileList()) {
				if (!list.contains(f.getType())) {
					list.add(f.getType());
				}
			}
		}
		//
		return list;
	}

	public boolean containsFiles(boolean deep) {
		for (PortfolioFile f : portfolioFileList) {
			if (f.getFilenameList() != null) {
				return true;
			}
		}
		//
		if (deep) {
			for (PortfolioLevel l : nextLevelList.values()) {
				if (l.containsFiles(true)) {
					return true;
				}
			}
		}
		//
		return false;
	}

	private void listFilenames(boolean deep, Collection<String> filenameList) {
		for (PortfolioFile f : portfolioFileList) {
			if (f.getFilenameList() != null) {
				filenameList.addAll(f.getFilenameList());
			}
		}
		//
		if (deep) {
			for (PortfolioLevel l : nextLevelList.values()) {
				l.listFilenames(true, filenameList);
			}
		}
	}

	public Collection<String> listFilenames(boolean deep) {
		Collection<String> filenameList = new Vector<String>();

		listFilenames(deep, filenameList);
		return filenameList;
	}

	void listPortfolioLevels(Collection<PortfolioLevel> returnList) {
		for (PortfolioLevel p : nextLevelList.values()) {
			returnList.add(p);
			p.listPortfolioLevels(returnList);
		}
	}

	public Collection<PortfolioLevel> listPortfolioLevels(boolean deep) {
		Collection<PortfolioLevel> returnList = new Vector<PortfolioLevel>();

		if (!deep) {
			return nextLevelList.values();
		} else {
			listPortfolioLevels(returnList);
		}
		return returnList;
	}

	@Override
	public String toString() {
		return "PortfolioLevel [name=" + name + ", type=" + type
				+ ", nextLevels=" + nextLevelList + "]";
	}
}