package com.dikshatech.portal.mail;

import java.io.Serializable;

public class Attachements implements Serializable {

	private String	filePath;
	private String	fileName;

	public Attachements() {}

	public Attachements(String filePath, String fileName) {
		this.filePath = filePath;
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
