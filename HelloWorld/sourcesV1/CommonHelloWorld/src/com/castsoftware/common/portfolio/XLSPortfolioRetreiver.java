package com.castsoftware.common.portfolio;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.castsoftware.common.exceptions.InitializationException;

public class XLSPortfolioRetreiver extends PortfolioRetreiver {
	private static final Log logger = LogFactory
			.getLog(XLSPortfolioRetreiver.class);

	private String excelFile = null;
	private Configuration config = null;
	private Workbook workbook = null;

	private String excel_dotNet_sheetName;
	private int excel_dotNet_colNum_solution;
	private int excel_dotNet_colNum_dir;
	private int excel_dotNet_colNum_sa;
	private int excel_dotNet_colNum_ga;
	private int excel_dotNet_colNum_app;
	private int excel_dotNet_colNum_dotNetVersion;
	private int excel_dotNet_colNum_vsVersion;

	private String excel_cobol_sheetName;
	private int excel_cobol_colNum_sa;
	private int excel_cobol_colNum_ga;
	private int excel_cobol_colNum_app;
	private int excel_cobol_colNum_regexIn;
	private int excel_cobol_colNum_regexOut;
	private int excel_cobol_colNum_type;

	private String excel_portfolio_sheetName;
	private int excel_portfolio_colNum_met;
	private int excel_portfolio_colNum_tma;
	private int excel_portfolio_colNum_sa;
	private int excel_portfolio_colNum_ga;
	private int excel_portfolio_colNum_app;

	public XLSPortfolioRetreiver(String excelFile, String propertyFile)
			throws InitializationException {
		try {
			this.config = new PropertiesConfiguration(propertyFile);
		} catch (ConfigurationException e) {
			throw new InitializationException(e);
		}
		this.excelFile = excelFile;
		initConfiguration();
	}

	public XLSPortfolioRetreiver(String excelFile, Configuration config)
			throws InitializationException {
		this.config = config;
		this.excelFile = excelFile;
		initConfiguration();
	}

	private void initConfiguration() throws InitializationException {
		excel_dotNet_sheetName = config.getString("excel.dotNet.sheetName");
		excel_dotNet_colNum_sa = config.getInt("excel.dotNet.colNum.sa");
		excel_dotNet_colNum_ga = config.getInt("excel.dotNet.colNum.ga");
		excel_dotNet_colNum_app = config.getInt("excel.dotNet.colNum.app");
		excel_dotNet_colNum_solution = config
				.getInt("excel.dotNet.colNum.solution");
		excel_dotNet_colNum_dir = config.getInt("excel.dotNet.colNum.dir");
		excel_dotNet_colNum_dotNetVersion = config
				.getInt("excel.dotNet.colNum.dotNetVersion");
		excel_dotNet_colNum_vsVersion = config
				.getInt("excel.dotNet.colNum.vsVersion");
		//
		excel_cobol_sheetName = config.getString("excel.cobol.sheetName");
		excel_cobol_colNum_sa = config.getInt("excel.cobol.colNum.sa");
		excel_cobol_colNum_ga = config.getInt("excel.cobol.colNum.ga");
		excel_cobol_colNum_app = config.getInt("excel.cobol.colNum.app");
		excel_cobol_colNum_regexIn = config
				.getInt("excel.cobol.colNum.regexIn");
		excel_cobol_colNum_regexOut = config
				.getInt("excel.cobol.colNum.regexOut");
		excel_cobol_colNum_type = config.getInt("excel.cobol.colNum.type");
		//
		excel_portfolio_sheetName = config
				.getString("excel.portfolio.sheetName");
		//
		excel_portfolio_colNum_met = config
				.getInt("excel.portfolio.colNum.met");
		excel_portfolio_colNum_tma = config
				.getInt("excel.portfolio.colNum.tma");
		excel_portfolio_colNum_sa = config.getInt("excel.portfolio.colNum.sa");
		excel_portfolio_colNum_ga = config.getInt("excel.portfolio.colNum.ga");
		excel_portfolio_colNum_app = config
				.getInt("excel.portfolio.colNum.app");

	}

	protected Workbook getWorkbook() throws InitializationException {
		if (workbook == null) {
			try {
				workbook = Workbook.getWorkbook(new File(excelFile));
			} catch (IOException e) {
				throw new InitializationException("Error accessing the file "
						+ excelFile, e);
			} catch (BiffException e) {
				throw new InitializationException("Invalid Excel file "
						+ excelFile, e);
			}
		}
		return workbook;
	}

	protected Sheet getExcelSheet(String sheetName)
			throws InitializationException {
		Sheet s = getWorkbook().getSheet(sheetName);
		if (s == null) {
			throw new InitializationException("Could not read Excel sheet ["
					+ sheetName + ", file=" + excelFile);
		}
		return s;
	}

	public Portfolio processDotNet() throws InitializationException {
		Sheet sheet;
		int rows;
		Cell[] row;
		String sa, ga, app, solution, dir, dotNetVersion, vsVersion;
		PortfolioLevel level = null;
		PortfolioFileDotNet portfolioFile;

		sheet = getExcelSheet(excel_dotNet_sheetName);
		rows = sheet.getRows();
		for (int i = 1; i < rows; i++) {
			row = sheet.getRow(i);
			sa = row[excel_dotNet_colNum_sa].getContents();
			ga = row[excel_dotNet_colNum_ga].getContents();
			app = row[excel_dotNet_colNum_app].getContents();
			solution = row[excel_dotNet_colNum_solution].getContents();
			dir = row[excel_dotNet_colNum_dir].getContents();
			dotNetVersion = row[excel_dotNet_colNum_dotNetVersion]
					.getContents();
			vsVersion = row[excel_dotNet_colNum_vsVersion].getContents();
			//
			if (Portfolio.replaceDir(sa) == null) {
				throw new InitializationException("SA [" + sa + "] not found");
			} else {
				level = portfolio.getPortfolioLevel(PortfolioLevel.Type.SA, sa,
						null);
				if (Portfolio.replaceDir(ga) != null) {
					level = portfolio.getPortfolioLevel(PortfolioLevel.Type.GA,
							ga, level);
					if (Portfolio.replaceDir(app) != null) {
						level = portfolio.getPortfolioLevel(
								PortfolioLevel.Type.APP, app, level);
					}
				}
			}
			//
			portfolioFile = new PortfolioFileDotNet(solution,
					PortfolioFile.Type.DOTNET, level, dir,
					dotNetVersion, vsVersion);
			level.addFile(portfolioFile);
		}
		return portfolio;
	}

	public Portfolio processCobol() throws InitializationException {
		Sheet sheet;
		int rows;
		Cell[] row;
		String sa, ga, app, regexIn, regexOut, type;
		PortfolioLevel level = null;
		PortfolioFileCobol portfolioFile;

		sheet = getExcelSheet(excel_cobol_sheetName);
		rows = sheet.getRows();
		for (int i = 1; i < rows; i++) {
			row = sheet.getRow(i);
			sa = row[excel_cobol_colNum_sa].getContents();
			ga = row[excel_cobol_colNum_ga].getContents();
			app = row[excel_cobol_colNum_app].getContents();
			regexIn = row[excel_cobol_colNum_regexIn].getContents();
			regexOut = row[excel_cobol_colNum_regexOut].getContents();
			type = row[excel_cobol_colNum_type].getContents();
			//
			if (Portfolio.replaceDir(sa) == null) {
				throw new InitializationException("SA [" + sa + "] not found");
			} else {
				level = portfolio.getPortfolioLevel(PortfolioLevel.Type.SA, sa,
						null);
				if (Portfolio.replaceDir(ga) != null) {
					level = portfolio.getPortfolioLevel(PortfolioLevel.Type.GA,
							ga, level);
					if (Portfolio.replaceDir(app) != null) {
						level = portfolio.getPortfolioLevel(
								PortfolioLevel.Type.APP, app, level);
					}
				}
			}
			//
			portfolioFile = new PortfolioFileCobol(regexIn,
					PortfolioFile.Type.COBOL, level, regexIn, regexOut,
					type);
			level.addFile(portfolioFile);
		}
		return portfolio;
	}

	public Portfolio processPortfolio() throws InitializationException {
		Sheet sheet;
		int rows;
		Cell[] row;
		String sa, ga, app, met, tma;
		PortfolioLevel level = null;

		sheet = getExcelSheet(excel_portfolio_sheetName);
		rows = sheet.getRows();
		for (int i = 2; i < rows; i++) {
			row = sheet.getRow(i);
			tma = row[excel_portfolio_colNum_tma].getContents();
			met = row[excel_portfolio_colNum_met].getContents();
			sa = row[excel_portfolio_colNum_sa].getContents();
			ga = row[excel_portfolio_colNum_ga].getContents();
			app = row[excel_portfolio_colNum_app].getContents();
			//
			if (Portfolio.replaceDir(tma) == null) {
				throw new InitializationException("TMA [" + tma + "] not found");
			} else {
				level = portfolio.getPortfolioLevel(PortfolioLevel.Type.TMA,
						tma, null);
				if (Portfolio.replaceDir(sa) == null) {
					throw new InitializationException("SA [" + sa
							+ "] not found");
				} else {
					level = portfolio.getPortfolioLevel(PortfolioLevel.Type.SA,
							sa, level);
					if (Portfolio.replaceDir(ga) != null) {
						level = portfolio.getPortfolioLevel(
								PortfolioLevel.Type.GA, ga, level);
						if (Portfolio.replaceDir(app) != null) {
							level = portfolio.getPortfolioLevel(
									PortfolioLevel.Type.APP, app, level);
						}
					}
				}
			}
			//
			if (Portfolio.replaceDir(met) == null) {
				throw new InitializationException("MET [" + met + "] not found");
			} else {
				level = portfolio.getPortfolioLevel(PortfolioLevel.Type.MET,
						met, null);
				if (Portfolio.replaceDir(sa) == null) {
					throw new InitializationException("SA [" + sa
							+ "] not found");
				} else {
					level = portfolio.getPortfolioLevel(PortfolioLevel.Type.SA,
							sa, level);
					if (Portfolio.replaceDir(ga) != null) {
						level = portfolio.getPortfolioLevel(
								PortfolioLevel.Type.GA, ga, level);
						if (Portfolio.replaceDir(app) != null) {
							level = portfolio.getPortfolioLevel(
									PortfolioLevel.Type.APP, app, level);
						}
					}
				}
			}
		}
		//
		return portfolio;
	}
}
