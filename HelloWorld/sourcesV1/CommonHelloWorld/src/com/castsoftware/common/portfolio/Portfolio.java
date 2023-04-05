package com.castsoftware.common.portfolio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.castsoftware.common.exceptions.InitializationException;

public class Portfolio {
	private static final Log logger = LogFactory.getLog(Portfolio.class);

	private List<HashMap<String, PortfolioLevel>> portfolio = new ArrayList<HashMap<String, PortfolioLevel>>();

	Portfolio() throws InitializationException {
		for (PortfolioLevel.Type t : PortfolioLevel.Type.values()) {
			if (!portfolio.add(new HashMap<String, PortfolioLevel>())) {
				throw new InitializationException();
			}
		}
	}

	private HashMap<String, PortfolioLevel> getPortfolioLevelList(
			PortfolioLevel.Type type) {
		return portfolio.get(type.getValue());
	}

	void addPortfolioLevel(PortfolioLevel portfolioLevel) {
		HashMap<String, PortfolioLevel> portfolioLevelList = getPortfolioLevelList(portfolioLevel
				.getType());

		if (!portfolioLevelList.containsKey(portfolioLevel.getName()
				.toLowerCase())) {
			portfolioLevelList.put(portfolioLevel.getName().toLowerCase(),
					portfolioLevel);
		}
	}

	PortfolioLevel getPortfolioLevel(PortfolioLevel.Type type, String name,
			PortfolioLevel parent) throws InitializationException {
		HashMap<String, PortfolioLevel> portfolioLevelList = getPortfolioLevelList(type);
		PortfolioLevel level;
		String toLowerCaseName;

		toLowerCaseName = name.toLowerCase();
		level = portfolioLevelList.get(toLowerCaseName);
		if (!portfolioLevelList.containsKey(toLowerCaseName)) {
			level = new PortfolioLevel(name, type, this, parent);
			portfolioLevelList.put(toLowerCaseName, level);
		}
		//
		if (parent != null) {
			parent.addNextLevel(level);
		}
		//
		return level;
	}

	private void listPortfolioLevels(PortfolioLevel.Type type,
			Collection<PortfolioLevel> returnList) {
		Collection<PortfolioLevel> topList = getPortfolioLevelList(type)
				.values();

		for (PortfolioLevel level : topList) {
			returnList.add(level);
			level.listPortfolioLevels(returnList);
		}
	}

	public Collection<PortfolioLevel> listPortfolioLevels(
			PortfolioLevel.Type type, boolean deep) {
		Collection<PortfolioLevel> returnList = new Vector<PortfolioLevel>();

		if (deep) {
			listPortfolioLevels(type, returnList);
		} else {
			returnList = getPortfolioLevelList(type).values();
		}
		return returnList;
	}

	public Collection<PortfolioLevel> listPortfolioLevels() {
		Collection<PortfolioLevel> returnList = new Vector<PortfolioLevel>();

		for (PortfolioLevel.Type type : PortfolioLevel.Type.values()) {
			returnList.addAll(getPortfolioLevelList(type).values());
		}
		//
		return returnList;
	}

	public Collection<PortfolioFile> listPortfolioFileDefinitions(
			PortfolioFile.Type type) {
		Collection<PortfolioFile> returnList = new Vector<PortfolioFile>();

		for (PortfolioLevel level : listPortfolioLevels()) {
			for (PortfolioFile file : level.getFileDefinitionList()) {
				if (type == null) {
					returnList.add(file);
				} else {
					if (file.getType().equals(type)) {
						returnList.add(file);
					}
				}
			}
		}
		return returnList;
	}

	public Collection<String> listPortfolioFilenames(PortfolioFile.Type type) {
		Collection<String> returnList = new Vector<String>();

		for (PortfolioFile f : listPortfolioFileDefinitions(type)) {
			if (f.getFilenameList() != null) {
				for (String s : f.getFilenameList()) {
					returnList.add(s);
				}
			}
		}
		return returnList;
	}

	public static String replaceDir(String dir) {

		if (dir == null) {
			return null;
		}
		dir = dir.trim();
		if (dir.equalsIgnoreCase("-")) {
			return null;
		}
		//
		if (dir.contains(":") || dir.contains("-") || dir.contains("?")) {
			dir = dir.replace(":", "_");
			dir = dir.replace("-", "_");
			dir = dir.replace("?", "_");
		}
		if (dir.contains("?") || dir.contains("*") || dir.contains("/")
				|| dir.contains("\\")) {
			dir = dir.replace("*", "_");
			dir = dir.replace("/", "_");
			dir = dir.replace("\\", "_");
		}
		if (dir.contains(" "))
			dir = dir.replace(" ", "");
		return dir;
	}

	public static void main(String arg[]) {
		XLSPortfolioRetreiver xlsRetreiver;
		SYSPortfolioRetreiver sysRetreiver;
		Portfolio portfolio = null;

		try {
			xlsRetreiver = new XLSPortfolioRetreiver(arg[0],
					"automation-global.properties");
			portfolio = xlsRetreiver.processPortfolio();
			portfolio = xlsRetreiver.processDotNet();
			portfolio = xlsRetreiver.processCobol();
			/*
			 * sysRetreiver = new SYSPortfolioRetreiver();
			 * sysRetreiver.completePortfolio(portfolio, arg[1], new String[] {
			 * "CBL", "SLN" });
			 */
		} catch (InitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		System.out.println("delete from ptf_portfolio;");
		int level_MET = 1, level_SA = 10, level_GA = 100, level_APP = 1000;
		String beginreq = "Insert into ptf_portfolio(ptf_id, ptf_parent_id, ptf_name, ptf_description, ptf_type) values (";

		for (PortfolioLevel l : portfolio.getPortfolioLevelList(
				PortfolioLevel.Type.MET).values()) {
			System.out.println(beginreq + level_MET + ", NULL, '"
					+ l.getName().replace('\'', ' ') + "', NULL, 'MET');");
			for (PortfolioLevel l2 : l.getNextLevelList().values()) {
				System.out.println(beginreq + level_SA + ", " + level_MET
						+ ", '" + l2.getName().replace('\'', ' ')
						+ "', NULL, 'SA');");
				for (PortfolioLevel l3 : l2.getNextLevelList().values()) {
					System.out.println(beginreq + level_GA + ", " + level_SA
							+ ", '" + l3.getName().replace('\'', ' ')
							+ "', NULL, 'GA');");
					for (PortfolioLevel l4 : l3.getNextLevelList().values()) {
						System.out.println(beginreq + level_APP++ + ", "
								+ level_GA + ", '"
								+ l4.getName().replace('\'', ' ')
								+ "', NULL, 'APP');");
					}
					level_GA++;
				}
				level_SA++;
			}
			level_MET++;
		}
		//
		for (PortfolioLevel l : portfolio.getPortfolioLevelList(
				PortfolioLevel.Type.TMA).values()) {
			System.out.println(beginreq + level_MET + ", NULL, '"
					+ l.getName().replace('\'', ' ') + "', NULL, 'TMA');");
			for (PortfolioLevel l2 : l.getNextLevelList().values()) {
				System.out.println(beginreq + level_SA + ", " + level_MET
						+ ", '" + l2.getName().replace('\'', ' ')
						+ "', NULL, 'SA');");
				for (PortfolioLevel l3 : l2.getNextLevelList().values()) {
					System.out.println(beginreq + level_GA + ", " + level_SA
							+ ", '" + l3.getName().replace('\'', ' ')
							+ "', NULL, 'GA');");
					for (PortfolioLevel l4 : l3.getNextLevelList().values()) {
						System.out.println(beginreq + level_APP++ + ", "
								+ level_GA + ", '"
								+ l4.getName().replace('\'', ' ')
								+ "', NULL, 'APP');");
					}
					level_GA++;
				}
				level_SA++;
			}
			level_MET++;
		}
	}
}
