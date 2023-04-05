package com.castsoftware.common.file;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.castsoftware.common.exceptions.ProcessException;

public class FileUtils {

	private static final org.apache.log4j.Logger logger = Logger
			.getLogger(FileUtils.class);

	/**
	 * 
	 * @param fileList
	 * @param fileRootDirectory
	 * @param pathFileRegexpInc
	 * @param pathFileRegexpExc if equals <b>null</b> all files will be retrieve
	 * @return
	 */
	private static List<File> getFilesFromFolderRegexp(List<File> fileList,
			File fileRootDirectory, String pathFileRegexpInc,
			String pathFileRegexpExc) {
		File[] filesInput;
		File fileInput;

		filesInput = fileRootDirectory.listFiles();
		for (int i = 0; i < filesInput.length; i++) {
			fileInput = filesInput[i];
			if (fileInput.isFile()) {		
				if(pathFileRegexpInc == null){
					fileList.add(fileInput);
				}else if (fileInput.getParent().toLowerCase().matches(
						pathFileRegexpInc.toLowerCase())) {
					if (pathFileRegexpExc != null) {
						if (!fileInput.getParent().toLowerCase().matches(
								pathFileRegexpExc.toLowerCase())) {
							fileList.add(fileInput);
						}
					} else {
						fileList.add(fileInput);
					}
				}
			}
			if (fileInput.isDirectory()) {
				getFilesFromFolderRegexp(fileList, fileInput,
						pathFileRegexpInc, pathFileRegexpExc);
			}
		}
		return fileList;
	}

	/**
	 * get files from a folder. comparison made according to Folder name
	 * 
	 * @param pathRootDirectory
	 * @param pathFileRegexpInc
	 *            regExp for searching file name
	 * @param pathFileRegexpExc
	 *            regExp for exlusion file name
	 * @return List<File>
	 * @throws ProcessException
	 */
	public static List<File> getFilesFromFolderRegexp(String pathRootDirectory,
			String pathFileRegexpInc, String pathFileRegexpExc)
			throws ProcessException {
		File fileRootDirectory = null;
		List<File> fileList = null;

		fileList = new Vector<File>();
		fileRootDirectory = new File(pathRootDirectory);
		if (!fileRootDirectory.exists()) {
			logger.error("Root directory not found [Root=" + pathRootDirectory
					+ ", RegExpInc=" + pathFileRegexpInc + ", RegExpExc="
					+ pathFileRegexpExc + "]");
			throw new ProcessException();
		} else {
			try {
				fileList = getFilesFromFolderRegexp(fileList,
						fileRootDirectory, pathFileRegexpInc, pathFileRegexpExc);
			} catch (Exception e) {
				logger.error(
						"Error retrieving files matching the regular expressions [Root="
								+ pathRootDirectory + ", RegExpInc="
								+ pathFileRegexpInc + ", RegExpExc="
								+ pathFileRegexpExc + "]", e);
				throw new ProcessException(e);
			}
		}
		return fileList;
	}

	private static boolean matchRegex(String regex[], String name) {

		for (int i = 0; i < regex.length; i++) {
			if (!name.toLowerCase().matches(regex[i].toLowerCase())) {
				return false;
			}
		}
		return true;
	}

	private static List<File> getFilesFromRegexp(List<File> fileList,
			File fileRootDirectory, String regexinc[], String regexexc[]) {
		File[] filesInput;
		File fileInput;
		String pathfilename;

		filesInput = fileRootDirectory.listFiles();
		for (int i = 0; i < filesInput.length; i++) {
			fileInput = filesInput[i];
			pathfilename = fileInput.getAbsolutePath();
			if (fileInput.isFile()) {
				if (matchRegex(regexinc, pathfilename)) {
					if (regexexc == null || !matchRegex(regexexc, pathfilename)) {
						fileList.add(fileInput);
					}
				}
			}
			if (fileInput.isDirectory()) {
				getFilesFromRegexp(fileList, fileInput, regexinc, regexexc);
			}
		}
		return fileList;
	}

	public static List<File> getFilesFromRegexp(String pathRootDirectory,
			String regexinc[], String regexexc[]) throws ProcessException {
		File fileRootDirectory = null;
		List<File> fileList = null;

		fileList = new Vector<File>();
		fileRootDirectory = new File(pathRootDirectory);
		if (!fileRootDirectory.exists()) {
			logger.error("Root directory not found [Root=" + pathRootDirectory
					+ ", RegExpInc=" + regexinc + ", RegExpExc=" + regexexc
					+ "]");
			throw new ProcessException();
		} else {
			try {
				fileList = getFilesFromRegexp(fileList, fileRootDirectory,
						regexinc, regexexc);
			} catch (Exception e) {
				logger.error(
						"Error retrieving files matching the regular expressions [Root="
								+ pathRootDirectory + ", RegExpInc=" + regexinc
								+ ", RegExpExc=" + regexexc + "]", e);
				throw new ProcessException(e);
			}
		}
		return fileList;
	}

	public static List<File> getFilesFromRegexp(String pathRootDirectory,
			String pathFileRegexpInc, String pathFileRegexpExc)
			throws ProcessException {
		return getFilesFromRegexp(pathRootDirectory,
				new String[] { pathFileRegexpInc },
				new String[] { pathFileRegexpExc });
	}

	public static String replace(String string, String replaceWhat,
			String replaceWith) {
		return string.replace((CharSequence) replaceWhat,
				(CharSequence) replaceWith);
	}

	public static String normalizeFilename(String filename) {
		filename = replace(filename, " ", "_");
		filename = replace(filename, "-", "_");
		return filename;
	}

	public static String doubleBackslash(String filename) {
		return filename.replace((CharSequence) "\\", (CharSequence) "\\\\");
	}

	public static String retreiveCommonBase(String dir1, String dir2) {
		dir1 = FilenameUtils.getPath(dir1);
		dir2 = FilenameUtils.getPath(dir2);
		dir1 = FilenameUtils.normalize(dir1).toLowerCase();
		dir2 = FilenameUtils.normalize(dir2).toLowerCase();
		//
		if (dir1.equalsIgnoreCase(dir2)) {
			return dir1;
		}
		//
		if (dir1.length() > dir2.length()) {
			if (dir1.startsWith(dir2)) {
				return dir2;
			}
		}
		//
		if (dir2.length() > dir2.length()) {
			if (dir2.startsWith(dir1)) {
				return dir1;
			}
		}
		return null;
	}

	public static HashMap<String, File> convertToHashMap(List<File> fileList,
			String regex, String matcher) {
		HashMap<String, File> hashMap = new HashMap<String, File>();

		for (File f : fileList) {
			logger.debug(f.getAbsolutePath() + " " + regex + " " + matcher);
			hashMap.put(f.getAbsolutePath().replaceAll(regex, matcher), f);
		}
		//
		return hashMap;
	}
}
