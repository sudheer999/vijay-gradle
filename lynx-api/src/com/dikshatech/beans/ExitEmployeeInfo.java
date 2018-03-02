package com.dikshatech.beans;

/**
 * @author gurunath.rokkam
 */
public class ExitEmployeeInfo {

	//for users
	private int			empId;
	private String		rmName;
	private String		noticePeriod;
	//for RM
	private String		empName;
	private String		dateOfJoining;
	private String		experiance;
	private String		ctc;
	private String		skillSet;
	private String		clientName;
	private String		Project;
	private String		Location;
	private String		poEndDate;
	//other
	private Object		object;
	private Object		obj;
	private Object[]	questions;

	public ExitEmployeeInfo() {}

	public ExitEmployeeInfo(int empId, String rmName, String noticePeriod) {
		this.empId = empId;
		this.rmName = rmName;
		this.noticePeriod = noticePeriod;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public String getExperiance() {
		return experiance;
	}

	public String getCtc() {
		return ctc;
	}

	public String getSkillSet() {
		return skillSet;
	}

	public String getClientName() {
		return clientName;
	}

	public String getPoEndDate() {
		return poEndDate;
	}

	public void setSkillSet(String skillSet) {
		this.skillSet = skillSet;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setPoEndDate(String poEndDate) {
		this.poEndDate = poEndDate;
	}

	public String getProject() {
		return Project;
	}

	public String getLocation() {
		return Location;
	}

	public Object getObject() {
		return object;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public void setExperiance(String experiance) {
		this.experiance = experiance;
	}

	public void setCtc(String ctc) {
		this.ctc = ctc;
	}

	public void setProject(String project) {
		Project = project;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public int getEmpId() {
		return empId;
	}

	public String getRmName() {
		return rmName;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public void setRmName(String rmName) {
		this.rmName = rmName;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Object getObj() {
		return obj;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setQuestions(Object[] questions) {
		this.questions = questions;
	}

	public Object[] getQuestions() {
		return questions;
	}
}
