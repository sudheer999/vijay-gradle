package com.dikshatech.beans;

import java.io.Serializable;

public class AppraisalReportV1 implements Serializable {

	private String	id;
	private String	name;
	private String	designation;
	private String	appraiser;
	private String	billableDays;
	private String	client;
	private String	functionalArea;
	private String	projectType;
	private String	functionalAreaRatings;
	private String	personalRatings;
	private String	overalScore;
	private String	longTermGoal;
	private String	smartGoal;
	private String	objectives;
	private String	finalObjectives;
	private String	comments;

	public void setProjectDetails(String client, String functionalArea, String projectType) {
		setClient(client);
		setFunctionalArea(functionalArea);
		setProjectType(projectType);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAppraiser() {
		return appraiser;
	}

	public void setAppraiser(String appraiser) {
		this.appraiser = appraiser;
	}

	public String getBillableDays() {
		return billableDays;
	}

	public void setBillableDays(String billableDays) {
		this.billableDays = billableDays;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getFunctionalArea() {
		return functionalArea;
	}

	public void setFunctionalArea(String functionalArea) {
		this.functionalArea = functionalArea;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getFunctionalAreaRatings() {
		return functionalAreaRatings;
	}

	public void setFunctionalAreaRatings(String functionalAreaRatings) {
		this.functionalAreaRatings = functionalAreaRatings;
	}

	public String getPersonalRatings() {
		return personalRatings;
	}

	public void setPersonalRatings(String personalRatings) {
		this.personalRatings = personalRatings;
	}

	public String getOveralScore() {
		return overalScore;
	}

	public void setOveralScore(String overalScore) {
		this.overalScore = overalScore;
	}

	public String getLongTermGoal() {
		return longTermGoal;
	}

	public void setLongTermGoal(String longTermGoal) {
		this.longTermGoal = longTermGoal;
	}

	public String getSmartGoal() {
		return smartGoal;
	}

	public void setSmartGoal(String smartGoal) {
		this.smartGoal = smartGoal;
	}

	public String getObjectives() {
		return objectives;
	}

	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}

	public String getFinalObjectives() {
		return finalObjectives;
	}

	public void setFinalObjectives(String finalObjectives) {
		this.finalObjectives = finalObjectives;
	}

	@Override
	public String toString() {
		return "AppraisalReportV1 [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name + ", " : "") + (designation != null ? "designation=" + designation + ", " : "") + (appraiser != null ? "appraiser=" + appraiser + ", " : "") + (billableDays != null ? "billableDays=" + billableDays + ", " : "") + (client != null ? "client=" + client + ", " : "") + (functionalArea != null ? "functionalArea=" + functionalArea + ", " : "")
				+ (projectType != null ? "projectType=" + projectType + ", " : "") + (functionalAreaRatings != null ? "functionalAreaRatings=" + functionalAreaRatings + ", " : "") + (personalRatings != null ? "personalRatings=" + personalRatings + ", " : "") + (overalScore != null ? "overalScore=" + overalScore + ", " : "") + (longTermGoal != null ? "longTermGoal=" + longTermGoal + ", " : "") + (smartGoal != null ? "smartGoal=" + smartGoal + ", " : "")
				+ (objectives != null ? "objectives=" + objectives + ", " : "") + (finalObjectives != null ? "finalObjectives=" + finalObjectives + ", " : "") + (comments != null ? "comments=" + comments : "") + "]";
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setIDPValues(String longTermGoal, String objectives, String smartGoals, String finalObjectives, String comments) {
		setLongTermGoal(longTermGoal);
		setObjectives(objectives);
		setSmartGoal(smartGoals);
		setFinalObjectives(finalObjectives);
		setComments(comments);
	}
}