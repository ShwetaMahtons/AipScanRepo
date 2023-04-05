package com.castsoftware.common.exec;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.log4j.Logger;

import com.castsoftware.common.exceptions.ProcessException;

public class RuntimeX {

	private static final org.apache.log4j.Logger logger = Logger
			.getLogger(RuntimeX.class);

	public static int exec(String command, String arguments[], File workingDir)
			throws ProcessException {
		CommandLine commandLine = null;
		DefaultExecutor executor;
		int exitValue = 1;

		logger.debug("Lauching command [" + command + "]");
		commandLine = CommandLine.parse(command);
		if (arguments != null) {
			commandLine.addArguments(arguments);
		}
		executor = new DefaultExecutor();
		executor.setExitValue(0);
		if (workingDir != null) {
			executor.setWorkingDirectory(workingDir);
		}
		try {
			exitValue = executor.execute(commandLine);
		} catch (ExecuteException e) {
			exitValue = e.getExitValue();
			logger.error(e);
			// throw new ProcessException(e);
		} catch (IOException e) {
			exitValue = 2;
			logger.error(e);
			// throw new ProcessException(e);
		}
		return exitValue;
	}

	public static int exec(String command) throws ProcessException {
		return exec(command, null, (File) null);
	}

	public static int exec(String command, String arguments[], String workDir)
			throws ProcessException {
		File workDirFile = null;

		if (workDir != null) {
			workDirFile = new File(workDir);
		}
		return exec(command, null, workDirFile);
	}

	public static int exec(String command, String arguments[])
			throws ProcessException {
		return exec(command, arguments, (File) null);
	}
}
