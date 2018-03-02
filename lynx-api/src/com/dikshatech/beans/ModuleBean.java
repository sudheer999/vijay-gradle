package com.dikshatech.beans;

import java.io.Serializable;
import java.util.Set;

public class ModuleBean implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private String				moduleName;
	private int					moduleId;
	private String				featureChain;
	private Set<Features>		features;
	private Features[]			featuresArr;
	private int					isModuleProcessChain;
	private String				isModulePermissionTypes;
	private int					isSelected;

	public String getModuleName() {
		return moduleName;
	}

	public int getModuleId() {
		return moduleId;
	}

	public String getFeatureChain() {
		return featureChain;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public void setFeatureChain(String featureChain) {
		this.featureChain = featureChain;
	}

	public void setFeatures(Set<Features> features) {
		this.features = features;
	}

	public Set<Features> getFeatures() {
		return features;
	}

	public Features[] getFeaturesArr() {
		return featuresArr;
	}

	public void setFeaturesArr(Features[] featuresArr) {
		this.featuresArr = featuresArr;
	}

	public int getIsModuleProcessChain() {
		return isModuleProcessChain;
	}

	public void setIsModuleProcessChain(int isModuleProcessChain) {
		this.isModuleProcessChain = isModuleProcessChain;
	}

	public String getIsModulePermissionTypes() {
		return isModulePermissionTypes;
	}

	public void setIsModulePermissionTypes(String isModulePermissionTypes) {
		this.isModulePermissionTypes = isModulePermissionTypes;
	}

	public int getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(int isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public String toString() {
		return "ModuleBean [moduleId=" + moduleId + ", moduleName=" + moduleName + "]";
	}
}
