package com.dikshatech.beans;

public class ExperienceDetailBean {

	private String	currentExp;
	private String	totalExp;
	private String	previousExp;
	private String	isFresher;
	private String 	fileName;
	private Integer fileId;
	protected String fileDescriptions;	


	

	public String getFileDescriptions() {
		return fileDescriptions;
	}

	public void setFileDescriptions(String fileDescriptions) {
		this.fileDescriptions = fileDescriptions;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ExperienceDetailBean(String currentExp, String previousExp, String totalExp) {
		this.currentExp = currentExp;
		this.totalExp = totalExp;
		this.previousExp = previousExp;
	}

	public String getCurrentExp() {
		return currentExp;
	}

	public void setCurrentExp(String currentExp) {
		this.currentExp = currentExp;
	}

	public String getTotalExp() {
		return totalExp;
	}

	public void setTotalExp(String totalExp) {
		this.totalExp = totalExp;
	}

	public String getPreviousExp() {
		return previousExp;
	}

	public void setPreviousExp(String previousExp) {
		this.previousExp = previousExp;
	}

	public String getIsFresher() {
		return isFresher;
	}

	public void setIsFresher(String isFresher) {
		this.isFresher = isFresher;
	}
}
