package com.dikshatech.beans;

import java.io.Serializable;

public class Features implements Serializable
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private String				moduleName;
	private int				moduleId;
	private String				featureName;
	private int				featureId;
	private int				parentId;

	private boolean			save;
	private boolean			view;
	private boolean			remove;
	private boolean			approve;
	private boolean			reject;
	private boolean			edit;
	private boolean			enable;
	private boolean			disable;
	private boolean			resendOffer;
	private boolean			markAsEmployee;
	private boolean			generateOffer;
	private boolean			noShow;
	private boolean			cancel;
	private boolean			rollOn;
	private boolean			submit;
	private boolean			download;
	private boolean			upload;
	private boolean			email;
	private boolean			assign;
	private boolean			hold;

	private String				permissionLabels;
	private int				isProcessChain;
	private String				permissionTypes;

	private int				processChainId;
	private int				AccessibilityId;
	private String				processChainName;
	private String				accessibilitySelected;
	private String				modulePermissionLabels;
	private int				isSelected;

	public String getFeatureName()
	{
		return featureName;
	}

	public int getFeatureId()
	{
		return featureId;
	}

	public int getParentId()
	{
		return parentId;
	}

	public boolean isSave()
	{
		return save;
	}

	public boolean isView()
	{
		return view;
	}

	public boolean isRemove()
	{
		return remove;
	}

	public boolean isApprove()
	{
		return approve;
	}

	public boolean isReject()
	{
		return reject;
	}

	public void setFeatureName(String featureName)
	{
		this.featureName = featureName;
	}

	public void setFeatureId(int featureId)
	{
		this.featureId = featureId;
	}

	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}

	public void setSave(boolean save)
	{
		this.save = save;
	}

	public void setView(boolean view)
	{
		this.view = view;
	}

	public void setRemove(boolean remove)
	{
		this.remove = remove;
	}

	public void setApprove(boolean approve)
	{
		this.approve = approve;
	}

	public void setReject(boolean reject)
	{
		this.reject = reject;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleId(int moduleId)
	{
		this.moduleId = moduleId;
	}

	public int getModuleId()
	{
		return moduleId;
	}

	public int getIsProcessChain()
	{
		return isProcessChain;
	}

	public void setIsProcessChain(int isProcessChain)
	{
		this.isProcessChain = isProcessChain;
	}

	public String getPermissionLabels()
	{
		return permissionLabels;
	}

	public void setPermissionLabels(String permissionLabels)
	{
		this.permissionLabels = permissionLabels;
	}

	public String getPermissionTypes()
	{
		return permissionTypes;
	}

	public void setPermissionTypes(String permissionTypes)
	{
		this.permissionTypes = permissionTypes;
	}

	public boolean isMarkAsEmployee()
	{
		return markAsEmployee;
	}

	public void setMarkAsEmployee(boolean markAsEmployee)
	{
		this.markAsEmployee = markAsEmployee;
	}

	public int getProcessChainId()
	{
		return processChainId;
	}

	public void setProcessChainId(int processChainId)
	{
		this.processChainId = processChainId;
	}

	public int getAccessibilityId()
	{
		return AccessibilityId;
	}

	public void setAccessibilityId(int accessibilityId)
	{
		AccessibilityId = accessibilityId;
	}

	public boolean isEdit()
	{
		return edit;
	}

	public void setEdit(boolean modify)
	{
		this.edit = modify;
	}

	public boolean isEnable()
	{
		return enable;
	}

	public void setEnable(boolean enable)
	{
		this.enable = enable;
	}

	public boolean isDisable()
	{
		return disable;
	}

	public void setDisable(boolean disable)
	{
		this.disable = disable;
	}

	public boolean isResendOffer()
	{
		return resendOffer;
	}

	public void setResendOffer(boolean resendOffer)
	{
		this.resendOffer = resendOffer;
	}

	public String getProcessChainName()
	{
		return processChainName;
	}

	public void setProcessChainName(String processChainName)
	{
		this.processChainName = processChainName;
	}

	public String getModulePermissionLabels()
	{
		return modulePermissionLabels;
	}

	public void setModulePermissionLabels(String modulePermissionLabels)
	{
		this.modulePermissionLabels = modulePermissionLabels;
	}

	public String getAccessibilitySelected()
	{
		return accessibilitySelected;
	}

	public void setAccessibilitySelected(String accessibilitySelected)
	{
		this.accessibilitySelected = accessibilitySelected;
	}

	public boolean isGenerateOffer()
	{
		return generateOffer;
	}

	public void setGenerateOffer(boolean generateOffer)
	{
		this.generateOffer = generateOffer;
	}

	public int getIsSelected()
	{
		return isSelected;
	}

	public void setIsSelected(int isSelected)
	{
		this.isSelected = isSelected;
	}

	public boolean isNoShow()
	{
		return noShow;
	}

	public boolean isCancel()
	{
		return cancel;
	}

	public boolean isRollOn()
	{
		return rollOn;
	}

	public boolean isSubmit()
	{
		return submit;
	}

	public boolean isDownload()
	{
		return download;
	}

	public boolean isUpload()
	{
		return upload;
	}

	public boolean isEmail()
	{
		return email;
	}

	public void setNoShow(boolean noShow)
	{
		this.noShow = noShow;
	}

	public void setCancel(boolean cancel)
	{
		this.cancel = cancel;
	}

	public void setRollOn(boolean rollOn)
	{
		this.rollOn = rollOn;
	}

	public void setSubmit(boolean submit)
	{
		this.submit = submit;
	}

	public void setDownload(boolean download)
	{
		this.download = download;
	}

	public void setUpload(boolean upload)
	{
		this.upload = upload;
	}

	public void setEmail(boolean email)
	{
		this.email = email;
	}

	public boolean isAssign()
	{
		return assign;
	}

	public void setAssign(boolean assign)
	{
		this.assign = assign;
	}

	public boolean isHold()
	{
		return hold;
	}

	public void setHold(boolean hold)
	{
		this.hold = hold;
	}

	@Override
	public String toString() {
		return "Features [moduleId=" + featureId + ", moduleName=" + featureName + "]";
	}

}
