package com.castsoftware.common.file;

public class FileInfo {

	private String fileName;
	private String filePath;
	private long fileCRC;
	private long fileLOC;
	

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFileCRC(long l) {
		this.fileCRC = l;
	}

	public long getFileCRC() {
		return fileCRC;
	}

	public void setFileLOC(long l) {
		this.fileLOC = l;
	}
	
	public long getFileLOC() {
		return fileLOC;
	}
	

	
	
	public boolean equals(FileInfo actClass){
		boolean isEqual=true;
		if (this.fileName.compareTo(actClass.fileName)!=0){isEqual=false;}
		else if (this.fileLOC!=actClass.fileLOC){isEqual=false;}
		else if (this.fileCRC!=actClass.fileCRC){isEqual=false;}
	
		return isEqual;
		
	}
	public boolean equalsName(FileInfo actClass){
		boolean isEqual=true;
		if (this.fileName.compareTo(actClass.fileName)<0){isEqual=false;}
	
		return isEqual;
		
	}
}
