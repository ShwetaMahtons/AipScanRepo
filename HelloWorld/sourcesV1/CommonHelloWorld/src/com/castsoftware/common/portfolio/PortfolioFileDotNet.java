package com.castsoftware.common.portfolio;

import org.apache.commons.io.FilenameUtils;

public class PortfolioFileDotNet extends PortfolioFile {
	private String dir;
	private String dotNetVersion;
	private String vsVersion;
	
	public PortfolioFileDotNet(String name, Type type, PortfolioLevel portfolioLevel, String dir, String dotNetVersion, String vsVersion) {
		super(name, type, portfolioLevel);
		//
		this.dir = dir;
		this.dotNetVersion = dotNetVersion;
		this.vsVersion = vsVersion;
	}
	
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getDotNetVersion() {
		return dotNetVersion;
	}

	public void setDotNetVersion(String dotNetVersion) {
		this.dotNetVersion = dotNetVersion;
	}

	public String getVsVersion() {
		return vsVersion;
	}

	public void setVsVersion(String vsVersion) {
		this.vsVersion = vsVersion;
	}
	
	public boolean match(String filename) {
		return FilenameUtils.getName(filename).equalsIgnoreCase(name);
	}
}
