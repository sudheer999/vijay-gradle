/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dto;

import java.io.Serializable;
import java.util.HashMap;

public class AppraisalIDP implements Serializable {

	/**
	 * This attribute maps to the column ID in the APPRAISAL_IDP table.
	 */
	protected int		id;
	/**
	 * This attribute maps to the column APPRAISAL_ID in the APPRAISAL_IDP table.
	 */
	protected int		appraisalId;
	/**
	 * This attribute maps to the column CAREER_PLAN in the APPRAISAL_IDP table.
	 */
	protected String	careerPlan;
	/**
	 * This attribute maps to the column OBJECTIVES in the APPRAISAL_IDP table.
	 */
	protected String	objectives;
	/**
	 * This attribute maps to the column SMART_GOALS in the APPRAISAL_IDP table.
	 */
	protected String	smartGoals;
	/**
	 * This attribute maps to the column FINAL_OBJECTIVES in the APPRAISAL_IDP table.
	 */
	protected String	finalObjectives;
	/**
	 * This attribute maps to the column COMMENTS in the APPRAISAL_IDP table.
	 */
	protected String	comments;
	/**
	 * This attribute maps to the column PROJECT_ACHIEVEMENTS in the APPRAISAL_IDP table.
	 */
	protected String	projectAchievements;

	/**
	 * Method 'AppraisalIDP'
	 */
	public AppraisalIDP() {}

	/**
	 * Method 'getId'
	 * 
	 * @return int
	 */
	public int getId() {
		return id;
	}

	public AppraisalIDP(int appraisalId, String careerPlan, String objectives, String smartGoals, String finalObjectives, String comments, String projectAchievements) {
		this.appraisalId = appraisalId;
		this.careerPlan = careerPlan;
		this.objectives = objectives;
		this.smartGoals = smartGoals;
		this.finalObjectives = finalObjectives;
		this.comments = comments;
		this.projectAchievements = projectAchievements;
	}

	public void setValues(String careerPlan, String objectives, String smartGoals, String finalObjectives, String comments, String projectAchievements) {
		this.careerPlan = careerPlan;
		this.objectives = objectives;
		this.smartGoals = smartGoals;
		this.finalObjectives = finalObjectives;
		this.comments = comments;
		this.projectAchievements = projectAchievements;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Method 'getAppraisalId'
	 * 
	 * @return int
	 */
	public int getAppraisalId() {
		return appraisalId;
	}

	/**
	 * Method 'setAppraisalId'
	 * 
	 * @param appraisalId
	 */
	public void setAppraisalId(int appraisalId) {
		this.appraisalId = appraisalId;
	}

	/**
	 * Method 'getCareerPlan'
	 * 
	 * @return String
	 */
	public String getCareerPlan() {
		return careerPlan;
	}

	/**
	 * Method 'setCareerPlan'
	 * 
	 * @param careerPlan
	 */
	public void setCareerPlan(String careerPlan) {
		this.careerPlan = careerPlan;
	}

	/**
	 * Method 'getObjectives'
	 * 
	 * @return String
	 */
	public String getObjectives() {
		return objectives;
	}

	/**
	 * Method 'setObjectives'
	 * 
	 * @param objectives
	 */
	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}

	/**
	 * Method 'getSmartGoals'
	 * 
	 * @return String
	 */
	public String getSmartGoals() {
		return smartGoals;
	}

	/**
	 * Method 'setSmartGoals'
	 * 
	 * @param smartGoals
	 */
	public void setSmartGoals(String smartGoals) {
		this.smartGoals = smartGoals;
	}

	/**
	 * Method 'getFinalObjectives'
	 * 
	 * @return String
	 */
	public String getFinalObjectives() {
		return finalObjectives;
	}

	/**
	 * Method 'setFinalObjectives'
	 * 
	 * @param finalObjectives
	 */
	public void setFinalObjectives(String finalObjectives) {
		this.finalObjectives = finalObjectives;
	}

	/**
	 * Method 'getComments'
	 * 
	 * @return String
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Method 'setComments'
	 * 
	 * @param comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * Method 'getProjectAchievements'
	 * 
	 * @return String
	 */
	public String getProjectAchievements() {
		return projectAchievements;
	}

	/**
	 * Method 'setProjectAchievements'
	 * 
	 * @param projectAchievements
	 */
	public void setProjectAchievements(String projectAchievements) {
		this.projectAchievements = projectAchievements;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other) {
		if (_other == null){ return false; }
		if (_other == this){ return true; }
		if (!(_other instanceof AppraisalIDP)){ return false; }
		final AppraisalIDP _cast = (AppraisalIDP) _other;
		if (id != _cast.id){ return false; }
		if (appraisalId != _cast.appraisalId){ return false; }
		if (careerPlan == null ? _cast.careerPlan != careerPlan : !careerPlan.equals(_cast.careerPlan)){ return false; }
		if (objectives == null ? _cast.objectives != objectives : !objectives.equals(_cast.objectives)){ return false; }
		if (smartGoals == null ? _cast.smartGoals != smartGoals : !smartGoals.equals(_cast.smartGoals)){ return false; }
		if (finalObjectives == null ? _cast.finalObjectives != finalObjectives : !finalObjectives.equals(_cast.finalObjectives)){ return false; }
		if (comments == null ? _cast.comments != comments : !comments.equals(_cast.comments)){ return false; }
		if (projectAchievements == null ? _cast.projectAchievements != projectAchievements : !projectAchievements.equals(_cast.projectAchievements)){ return false; }
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode() {
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		_hashCode = 29 * _hashCode + appraisalId;
		if (careerPlan != null){
			_hashCode = 29 * _hashCode + careerPlan.hashCode();
		}
		if (objectives != null){
			_hashCode = 29 * _hashCode + objectives.hashCode();
		}
		if (smartGoals != null){
			_hashCode = 29 * _hashCode + smartGoals.hashCode();
		}
		if (finalObjectives != null){
			_hashCode = 29 * _hashCode + finalObjectives.hashCode();
		}
		if (comments != null){
			_hashCode = 29 * _hashCode + comments.hashCode();
		}
		if (projectAchievements != null){
			_hashCode = 29 * _hashCode + projectAchievements.hashCode();
		}
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return AppraisalIDPPk
	 */
	public AppraisalIDPPk createPk() {
		return new AppraisalIDPPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.AppraisalIDP: ");
		ret.append("id=" + id);
		ret.append(", appraisalId=" + appraisalId);
		ret.append(", careerPlan=" + careerPlan);
		ret.append(", objectives=" + objectives);
		ret.append(", smartGoals=" + smartGoals);
		ret.append(", finalObjectives=" + finalObjectives);
		ret.append(", comments=" + comments);
		ret.append(", projectAchievements=" + projectAchievements);
		return ret.toString();
	}

	public HashMap<String, String> toHashMap() {
		return toHashMap(false);
	}

	public HashMap<String, String> toHashMap(boolean isAppraisee) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("smartGoals", smartGoals);
		map.put("careerPlan", careerPlan);
		map.put("objectives", objectives);
		if (!isAppraisee){
			map.put("finalObjectives", finalObjectives);
			map.put("comments", comments);
		}
		map.put("projectAchievements", projectAchievements);
		return map;
	}
}
