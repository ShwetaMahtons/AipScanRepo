package com.castsoftware.common.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteCSV {

	public static FileWriter writer;

	public WriteCSV(String pathName, String fileName) throws IOException {
		File fDir = new File(pathName);
		if (fDir.exists()) {
			writer = new FileWriter(pathName + "/" + fileName);
		} else {
			fDir.mkdir();
			writer = new FileWriter(pathName + "/" + fileName);
		}
	}
	
	public WriteCSV(String filename) throws IOException {
		writer = new FileWriter(filename);
	}

	public void closeFile() throws IOException {
		writer.flush();
		writer.close();
	}

	public void writeEntry(String[] inputData) throws IOException {
		for (int i = 0; i < inputData.length; i++) {
			writer.append(inputData[i]);
			if (i < inputData.length - 1)
				writer.append(',');
		}
		writer.append('\n');
	}
}
