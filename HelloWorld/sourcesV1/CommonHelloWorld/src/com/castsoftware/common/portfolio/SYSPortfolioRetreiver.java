package com.castsoftware.common.portfolio;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.castsoftware.common.exceptions.InitializationException;

public class SYSPortfolioRetreiver extends PortfolioRetreiver {

	private static final Log logger = LogFactory
	.getLog(SYSPortfolioRetreiver.class);
		
	public SYSPortfolioRetreiver() throws InitializationException {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public Portfolio completePortfolio(Portfolio portfolio, String dir, String extension[]) {
		Collection<File> sysFileList;
		Collection<PortfolioFile> xlsFileList;
		String filename;
		
		this.portfolio = portfolio;
		sysFileList  = FileUtils.listFiles(new File(dir), extension, true);
		xlsFileList = portfolio.listPortfolioFileDefinitions(null);
		for (File sysFile : sysFileList) {
			filename = FilenameUtils.removeExtension(sysFile.getName());
			for (PortfolioFile xlsFile : xlsFileList) {
				if (xlsFile.match(filename)) {
					xlsFile.addFilename(sysFile.getAbsolutePath());
				}
			}
		}
		return portfolio;
	}
}
