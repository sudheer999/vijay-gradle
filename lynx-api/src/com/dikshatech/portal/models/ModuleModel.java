package com.dikshatech.portal.models;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dikshatech.beans.Features;
import com.dikshatech.common.utils.EnumUtil;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.ModulesDao;
import com.dikshatech.portal.dto.Modules;
import com.dikshatech.portal.exceptions.ModulesDaoException;
import com.dikshatech.portal.factory.ModulesDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;

public class ModuleModel extends ActionMethods
{

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		
		ModulesDao modulesDao = ModulesDaoFactory.create();
		
		try
		{
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
			{
				case ALLMODULESFEATURES:
				{
					DropDown dropDown = (DropDown) form;
					Modules[] modules = modulesDao.findWhereParentIdEquals(0);
					
					com.dikshatech.beans.ModuleBean[] modulesBeanArr = new com.dikshatech.beans.ModuleBean[modules.length];
					int j = 0;
					for (Modules tempModulesDto : modules)
					{
						
						boolean isProcessChain=true;
						
						com.dikshatech.beans.ModuleBean modulesBean = new com.dikshatech.beans.ModuleBean();
						/*if (tempModulesDto.getParentId() == 0)
						{*/
							
							modulesBean.setModuleId(tempModulesDto.getId());
							modulesBean.setModuleName(tempModulesDto.getName());
							modulesBean.setIsModulePermissionTypes(EnumUtil.checkPermissions(tempModulesDto.getPermissionTypes()));
							Modules[] childModules = modulesDao
							        .findWhereParentIdEquals(tempModulesDto
							                .getId());
							Features[] featuresBeanArray = new Features[childModules.length];
							
							if (childModules.length > 0)
							{
								
								int i = 0;
								for (Modules childMod : childModules)
								{
									com.dikshatech.beans.Features features = new com.dikshatech.beans.Features();
									features
									        .setParentId(tempModulesDto.getId());
									features.setFeatureName(childMod.getName());
									features.setFeatureId(childMod.getId());
									features.setIsProcessChain(childMod.getIsProcChain());
									if(isProcessChain){
										
										if(childMod.getIsProcChain()>0){
											
											modulesBean.setIsModuleProcessChain(childMod.getIsProcChain());
											isProcessChain=false;
										}
									}
									features.setPermissionLabels(EnumUtil.checkPermissions(childMod.getPermissionTypes()));
									
									featuresBeanArray[i] = features;
									i++;
								}
								modulesBean.setFeaturesArr(featuresBeanArray);
							}
							
						//}// End of if statement
						modulesBeanArr[j] = modulesBean;
						j++;
					}
					
					if (modulesBeanArr.length > 0)
						dropDown.setDropDown(modulesBeanArr);
					request.setAttribute("actionForm",
					        dropDown);
					break;
				}
			}// End of switch stmt
			
		}
		
		catch (ModulesDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

}