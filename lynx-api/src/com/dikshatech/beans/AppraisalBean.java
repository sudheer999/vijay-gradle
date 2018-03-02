package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import com.dikshatech.portal.forms.PortalForm;

public class AppraisalBean extends PortalForm implements Serializable {

	private int						id;
	private String					handler;
	private String					period;
	private String					year;
	private String					status;
	private Date					startDate;
	private String					appraisal[];
	private String					appraisalId;
	private Date					appraiseeDueDate;
	private Date					appraiserDueDate;
	// for details for version 0
	private AppraisalUserDetails	userDetails;
	private String					proj_details[], proj_activity, proj_totalDays;
	private String					prof_ratings[], prof_practices, prof_likewise, prof_difficult, prof_most, prof_least;
	private String					pers_ratings[];
	private String					indi_goals, indi_time, indi_plan, indi_strengths, indi_opportunities, indi_timeBound, indi_gaps, indi_skils, indi_performance, indi_advancement, indi_roadMap;
	private String					feed_ratings[], feed_comments;
	private String					prom_ratings[], prom_comments, prom_promotable, prom_howSoon, prom_promotedAs;
	//
	private String					comments;
	private Object					levels[];
	private Object					obj;
	private String					rmgHandler;
	private String					esr_map_id;
	private String					mailSubject;
	private String					effectFrom;
	private String					revisedCTC;
	private String					designation;
	private String					name;
	// added for version 1.
	private Object[]				ratings;
	private Object[]				projects;
	private Object					idp;
	private String					careerPlan;
	private String					objectives;
	private String					smartGoals;
	private String					finalObjectives;
	private String					projectAchievements;
	private String         test;
	

	public int getId() {
		return id;
	}

	public String getHandler() {
		return handler;
	}

	public String getPeriod() {
		return period;
	}

	public String getYear() {
		return year;
	}

	public String getStatus() {
		return status;
	}

	public String[] getAppraisal() {
		return appraisal;
	}

	public AppraisalUserDetails getUserDetails() {
		return userDetails;
	}

	public String[] getProj_details() {
		return proj_details;
	}

	public String getProj_activity() {
		return proj_activity;
	}

	public String getProj_totalDays() {
		return proj_totalDays;
	}

	public String[] getProf_ratings() {
		return prof_ratings;
	}

	public String getProf_practices() {
		return prof_practices;
	}

	public String getProf_likewise() {
		return prof_likewise;
	}

	public String getProf_difficult() {
		return prof_difficult;
	}

	public String getProf_most() {
		return prof_most;
	}

	public String getProf_least() {
		return prof_least;
	}

	public String[] getPers_ratings() {
		return pers_ratings;
	}

	public String getIndi_goals() {
		return indi_goals;
	}

	public String getIndi_time() {
		return indi_time;
	}

	public String getIndi_plan() {
		return indi_plan;
	}

	public String getIndi_strengths() {
		return indi_strengths;
	}

	public String getIndi_opportunities() {
		return indi_opportunities;
	}

	public String getIndi_timeBound() {
		return indi_timeBound;
	}

	public String getIndi_gaps() {
		return indi_gaps;
	}

	public String getIndi_skils() {
		return indi_skils;
	}

	public String getIndi_performance() {
		return indi_performance;
	}

	public String getIndi_advancement() {
		return indi_advancement;
	}

	public String getIndi_roadMap() {
		return indi_roadMap;
	}

	public String[] getFeed_ratings() {
		return feed_ratings;
	}

	public String getFeed_comments() {
		return feed_comments;
	}

	public String[] getProm_ratings() {
		return prom_ratings;
	}

	public String getProm_comments() {
		return prom_comments;
	}

	public String getProm_promotable() {
		return prom_promotable;
	}

	public String getProm_howSoon() {
		return prom_howSoon;
	}

	public String getProm_promotedAs() {
		return prom_promotedAs;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAppraisal(String[] appraisal) {
		this.appraisal = appraisal;
	}

	public void setUserDetails(AppraisalUserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public void setProj_details(String[] projDetails) {
		proj_details = projDetails;
	}

	public void setProj_activity(String projActivity) {
		proj_activity = projActivity;
	}

	public void setProj_totalDays(String projTotalDays) {
		proj_totalDays = projTotalDays;
	}

	public void setProf_ratings(String[] profRatings) {
		prof_ratings = profRatings;
	}

	public void setProf_practices(String profPractices) {
		prof_practices = profPractices;
	}

	public void setProf_likewise(String profLikewise) {
		prof_likewise = profLikewise;
	}

	public void setProf_difficult(String profDifficult) {
		prof_difficult = profDifficult;
	}

	public void setProf_most(String profMost) {
		prof_most = profMost;
	}

	public void setProf_least(String profLeast) {
		prof_least = profLeast;
	}

	public void setPers_ratings(String[] persRatings) {
		pers_ratings = persRatings;
	}

	public void setIndi_goals(String indiGoals) {
		indi_goals = indiGoals;
	}

	public void setIndi_time(String indiTime) {
		indi_time = indiTime;
	}

	public void setIndi_plan(String indiPlan) {
		indi_plan = indiPlan;
	}

	public void setIndi_strengths(String indiStrengths) {
		indi_strengths = indiStrengths;
	}

	public void setIndi_opportunities(String indiOpportunities) {
		indi_opportunities = indiOpportunities;
	}

	public void setIndi_timeBound(String indiTimeBound) {
		indi_timeBound = indiTimeBound;
	}

	public void setIndi_gaps(String indiGaps) {
		indi_gaps = indiGaps;
	}

	public void setIndi_skils(String indiSkils) {
		indi_skils = indiSkils;
	}

	public void setIndi_performance(String indiPerformance) {
		indi_performance = indiPerformance;
	}

	public void setIndi_advancement(String indiAdvancement) {
		indi_advancement = indiAdvancement;
	}

	public void setIndi_roadMap(String indiRoadMap) {
		indi_roadMap = indiRoadMap;
	}

	public void setFeed_ratings(String[] feedRatings) {
		feed_ratings = feedRatings;
	}

	public void setFeed_comments(String feedComments) {
		feed_comments = feedComments;
	}

	public void setProm_ratings(String[] promRatings) {
		prom_ratings = promRatings;
	}

	public void setProm_comments(String promComments) {
		prom_comments = promComments;
	}

	public void setProm_promotable(String promPromotable) {
		prom_promotable = promPromotable;
	}

	public void setProm_howSoon(String promHowSoon) {
		prom_howSoon = promHowSoon;
	}

	public void setProm_promotedAs(String promPromotedAs) {
		prom_promotedAs = promPromotedAs;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getComments() {
		return comments;
	}

	public void setLevels(Object levels[]) {
		this.levels = levels;
	}

	public Object[] getLevels() {
		return levels;
	}

	public void setAppraiseeDueDate(Date appraiseeDueDate) {
		this.appraiseeDueDate = appraiseeDueDate;
	}

	public Date getAppraiseeDueDate() {
		return appraiseeDueDate;
	}

	public void setAppraiserDueDate(Date appraiserDueDate) {
		this.appraiserDueDate = appraiserDueDate;
	}

	public Date getAppraiserDueDate() {
		return appraiserDueDate;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Object getObj() {
		return obj;
	}

	public void setAppraisalId(String appraisalId) {
		this.appraisalId = appraisalId;
	}

	public String getAppraisalId() {
		return appraisalId;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setRmgHandler(String rmgHandler) {
		this.rmgHandler = rmgHandler;
	}

	public String getRmgHandler() {
		return rmgHandler;
	}

	public void setEsr_map_id(String esr_map_id) {
		this.esr_map_id = esr_map_id;
	}

	public String getEsr_map_id() {
		return esr_map_id;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getEffectFrom() {
		return effectFrom;
	}

	public void setEffectFrom(String effectFrom) {
		this.effectFrom = effectFrom;
	}

	public String getRevisedCTC() {
		return revisedCTC;
	}

	public void setRevisedCTC(String revisedCTC) {
		this.revisedCTC = revisedCTC;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object[] getRatings() {
		return ratings;
	}

	public void setRatings(Object[] ratings) {
		this.ratings = ratings;
	}

	public Object[] getProjects() {
		return projects;
	}

	public void setProjects(Object[] projects) {
		this.projects = projects;
	}

	public Object getIdp() {
		return idp;
	}

	public void setIdp(Object idp) {
		this.idp = idp;
	}

	public String getCareerPlan() {
		return careerPlan;
	}

	public void setCareerPlan(String careerPlan) {
		this.careerPlan = careerPlan;
	}

	public String getObjectives() {
		return objectives;
	}

	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}

	public String getSmartGoals() {
		return smartGoals;
	}

	public void setSmartGoals(String smartGoals) {
		this.smartGoals = smartGoals;
	}

	public String getFinalObjectives() {
		return finalObjectives;
	}

	public void setFinalObjectives(String finalObjectives) {
		this.finalObjectives = finalObjectives;
	}

	@Override
	public String toString() {
		return "AppraisalBean [id=" + id + ", " + (handler != null ? "handler=" + handler + ", " : "") + (period != null ? "period=" + period + ", " : "") + (year != null ? "year=" + year + ", " : "") + (status != null ? "status=" + status + ", " : "") + (startDate != null ? "startDate=" + startDate + ", " : "") + (appraisal != null ? "appraisal=" + Arrays.toString(appraisal) + ", " : "") + (appraisalId != null ? "appraisalId=" + appraisalId + ", " : "")
				+ (appraiseeDueDate != null ? "appraiseeDueDate=" + appraiseeDueDate + ", " : "") + (appraiserDueDate != null ? "appraiserDueDate=" + appraiserDueDate + ", " : "") + (userDetails != null ? "userDetails=" + userDetails + ", " : "") + (proj_details != null ? "proj_details=" + Arrays.toString(proj_details) + ", " : "") + (proj_activity != null ? "proj_activity=" + proj_activity + ", " : "") + (proj_totalDays != null ? "proj_totalDays=" + proj_totalDays + ", " : "")
				+ (prof_ratings != null ? "prof_ratings=" + Arrays.toString(prof_ratings) + ", " : "") + (prof_practices != null ? "prof_practices=" + prof_practices + ", " : "") + (prof_likewise != null ? "prof_likewise=" + prof_likewise + ", " : "") + (prof_difficult != null ? "prof_difficult=" + prof_difficult + ", " : "") + (prof_most != null ? "prof_most=" + prof_most + ", " : "") + (prof_least != null ? "prof_least=" + prof_least + ", " : "")
				+ (pers_ratings != null ? "pers_ratings=" + Arrays.toString(pers_ratings) + ", " : "") + (indi_goals != null ? "indi_goals=" + indi_goals + ", " : "") + (indi_time != null ? "indi_time=" + indi_time + ", " : "") + (indi_plan != null ? "indi_plan=" + indi_plan + ", " : "") + (indi_strengths != null ? "indi_strengths=" + indi_strengths + ", " : "") + (indi_opportunities != null ? "indi_opportunities=" + indi_opportunities + ", " : "")
				+ (indi_timeBound != null ? "indi_timeBound=" + indi_timeBound + ", " : "") + (indi_gaps != null ? "indi_gaps=" + indi_gaps + ", " : "") + (indi_skils != null ? "indi_skils=" + indi_skils + ", " : "") + (indi_performance != null ? "indi_performance=" + indi_performance + ", " : "") + (indi_advancement != null ? "indi_advancement=" + indi_advancement + ", " : "") + (indi_roadMap != null ? "indi_roadMap=" + indi_roadMap + ", " : "")
				+ (feed_ratings != null ? "feed_ratings=" + Arrays.toString(feed_ratings) + ", " : "") + (feed_comments != null ? "feed_comments=" + feed_comments + ", " : "") + (prom_ratings != null ? "prom_ratings=" + Arrays.toString(prom_ratings) + ", " : "") + (prom_comments != null ? "prom_comments=" + prom_comments + ", " : "") + (prom_promotable != null ? "prom_promotable=" + prom_promotable + ", " : "") + (prom_howSoon != null ? "prom_howSoon=" + prom_howSoon + ", " : "")
				+ (prom_promotedAs != null ? "prom_promotedAs=" + prom_promotedAs + ", " : "") + (comments != null ? "comments=" + comments + ", " : "") + (levels != null ? "levels=" + Arrays.toString(levels) + ", " : "") + (obj != null ? "obj=" + obj + ", " : "") + (rmgHandler != null ? "rmgHandler=" + rmgHandler + ", " : "") + (esr_map_id != null ? "esr_map_id=" + esr_map_id + ", " : "") + (mailSubject != null ? "mailSubject=" + mailSubject + ", " : "")
				+ (effectFrom != null ? "effectFrom=" + effectFrom + ", " : "") + (revisedCTC != null ? "revisedCTC=" + revisedCTC + ", " : "") + (designation != null ? "designation=" + designation + ", " : "") + (name != null ? "name=" + name + ", " : "") + (ratings != null ? "ratings=" + Arrays.toString(ratings) + ", " : "") + (projects != null ? "projects=" + Arrays.toString(projects) + ", " : "") + (idp != null ? "idp=" + idp + ", " : "")
				+ (careerPlan != null ? "careerPlan=" + careerPlan + ", " : "") + (objectives != null ? "objectives=" + objectives + ", " : "") + (smartGoals != null ? "smartGoals=" + smartGoals + ", " : "") + (finalObjectives != null ? "finalObjectives=" + finalObjectives + ", " : "") + (projectAchievements != null ? "projectAchievements=" + projectAchievements : "") + "]";
	}

	public String getProjectAchievements() {
		return projectAchievements;
	}

	public void setProjectAchievements(String projectAchievements) {
		this.projectAchievements = projectAchievements;
	}
}
