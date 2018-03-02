package com.dikshatech.beans;

import java.io.Serializable;

public class AppraisalReport implements Serializable {

	private String	id;
	private String	name;
	private String	designation;
	private String	newDesignation;
	private String	professionalRating;
	private String	personalRating;
	private String	overallScore;
	private String	totalProjectDays;
	private String	appraiseeComments;
	private String	appraiserComments;
	private String	promotability;
	private String	howSoon;
	private String	promotedas;
	private String	appraiser;
	private String	completionDate;
	private String	suggestions;

	public String getName() {
		return name;
	}

	public String getDesignation() {
		return designation;
	}

	public String getNewDesignation() {
		return newDesignation;
	}

	public String getProfessionalRating() {
		return professionalRating;
	}

	public String getPersonalRating() {
		return personalRating;
	}

	public String getOverallScore() {
		return overallScore;
	}

	public String getTotalProjectDays() {
		return totalProjectDays;
	}

	public String getAppraiseeComments() {
		return appraiseeComments;
	}

	public String getAppraiserComments() {
		return appraiserComments;
	}

	public String getPromotability() {
		return promotability;
	}

	public String getHowSoon() {
		return howSoon;
	}

	public String getPromotedas() {
		return promotedas;
	}

	public String getAppraiser() {
		return appraiser;
	}

	public String getCompletionDate() {
		return completionDate;
	}

	public String getSuggestions() {
		return suggestions;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setNewDesignation(String newDesignation) {
		this.newDesignation = newDesignation;
	}

	public void setProfessionalRating(String professionalRating) {
		this.professionalRating = professionalRating;
	}

	public void setPersonalRating(String personalRating) {
		this.personalRating = personalRating;
	}

	public void setOverallScore(String overallScore) {
		this.overallScore = overallScore;
	}

	public void setTotalProjectDays(String totalProjectDays) {
		this.totalProjectDays = totalProjectDays;
	}

	public void setAppraiseeComments(String appraiseeComments) {
		this.appraiseeComments = appraiseeComments;
	}

	public void setAppraiserComments(String appraiserComments) {
		this.appraiserComments = appraiserComments;
	}

	public void setPromotability(String promotability) {
		this.promotability = promotability;
	}

	public void setHowSoon(String howSoon) {
		this.howSoon = howSoon;
	}

	public void setPromotedas(String promotedas) {
		this.promotedas = promotedas;
	}

	public void setAppraiser(String appraiser) {
		this.appraiser = appraiser;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	public void setSuggestions(String suggestions) {
		this.suggestions = suggestions;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
