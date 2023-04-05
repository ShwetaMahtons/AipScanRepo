package com.castsoftware.common.file;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.log4j.Logger;

import com.castsoftware.common.exceptions.ProcessException;

public class DirUtils {

	private static final org.apache.log4j.Logger logger = Logger
			.getLogger(DirUtils.class);

	private static List<File> getDirsFromRegexp(List<File> dirList,
			File fileRootDirectory, String pathFileRegexpInc,
			String pathFileRegexpExc, boolean continueAfterFinding) {
		File[] filesInput;
		File fileInput;
		String pathfilename;
		boolean hasFound = false;

		filesInput = fileRootDirectory.listFiles();
		for (int i = 0; i < filesInput.length; i++) {
			fileInput = filesInput[i];
			pathfilename = fileInput.getAbsolutePath();
			if (fileInput.isDirectory()) {
				logger.debug("Directory to test [Directory"
						+ fileInput.getAbsolutePath() + ", RegexInc="
						+ pathFileRegexpInc + ", RegexExc=" + pathFileRegexpExc
						+ "]");
				if (pathfilename.toLowerCase().matches(
						pathFileRegexpInc.toLowerCase())) {
					if (pathFileRegexpExc == null || !pathfilename.toLowerCase().matches(
							pathFileRegexpExc.toLowerCase())) {
						dirList.add(fileInput);
						hasFound = true;
					}
				}
				if (!hasFound || continueAfterFinding) {
					getDirsFromRegexp(dirList, fileInput, pathFileRegexpInc,
							pathFileRegexpExc, continueAfterFinding);
				}
			}
		}
		return dirList;
	}

	public static List<File> getDirsFromRegexp(String pathRootDirectory,
			String pathFileRegexpInc, String pathFileRegexpExc,
			boolean continueAfterFinding) throws ProcessException {
		File fileRootDirectory = null;
		List<File> dirList = null;

		dirList = new Vector<File>();
		fileRootDirectory = new File(pathRootDirectory);
		if (!fileRootDirectory.exists()) {
			logger.error("Root directory not found [Root=" + pathRootDirectory
					+ ", RegexInc=" + pathFileRegexpInc + ", RegexExc="
					+ pathFileRegexpExc + "]");
			throw new ProcessException();
		} else {
			try {
				dirList = getDirsFromRegexp(dirList, fileRootDirectory,
						pathFileRegexpInc, pathFileRegexpExc,
						continueAfterFinding);
			} catch (Exception e) {
				logger.error(
						"Error retrieving files matching the regular expressions [Root="
								+ pathRootDirectory + ", RegexInc="
								+ pathFileRegexpInc + ", RegexExp="
								+ pathFileRegexpInc + "]", e);
				throw new ProcessException(e);
			}
		}
		return dirList;
	}

	public static File getMaxParentDirectory(String currentDir) {
		File currentDirFile, parentDirFile, dirFile, maxDirFile = null;
		File[] listDirFile;
		String maxDirName = "";

		currentDirFile = new File(currentDir);
		parentDirFile = currentDirFile.getParentFile();
		listDirFile = parentDirFile
				.listFiles((FileFilter) DirectoryFileFilter.INSTANCE);
		for (int i = 0; i < listDirFile.length; i++) {
			dirFile = listDirFile[i];
			if (!dirFile.getName().equalsIgnoreCase(currentDirFile.getName())
					&& dirFile.getName().compareToIgnoreCase(maxDirName) > 0) {
				maxDirName = dirFile.getName();
				maxDirFile = dirFile;
				logger.debug("Max dirName = " + maxDirName);
			}
		}
		//
		return maxDirFile;
	}
}
