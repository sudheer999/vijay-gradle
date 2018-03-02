package com.dikshatech.portal.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.dikshatech.beans.Features;
import com.dikshatech.beans.ModuleBean;
import com.dikshatech.common.utils.EnumUtil;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.AccessibilityLevelDao;
import com.dikshatech.portal.dao.ModuleActionDao;
import com.dikshatech.portal.dao.ModulePermissionDao;
import com.dikshatech.portal.dao.ModulesDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.RolesDao;
import com.dikshatech.portal.dao.UserRolesDao;
import com.dikshatech.portal.dto.AccessibilityLevel;
import com.dikshatech.portal.dto.ModuleAction;
import com.dikshatech.portal.dto.ModuleActionPk;
import com.dikshatech.portal.dto.ModulePermission;
import com.dikshatech.portal.dto.ModulePermissionPk;
import com.dikshatech.portal.dto.Modules;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.Roles;
import com.dikshatech.portal.dto.RolesPk;
import com.dikshatech.portal.dto.UserRoles;
import com.dikshatech.portal.exceptions.ModuleActionDaoException;
import com.dikshatech.portal.exceptions.ModulePermissionDaoException;
import com.dikshatech.portal.exceptions.RolesDaoException;
import com.dikshatech.portal.factory.AccessibilityLevelDaoFactory;
import com.dikshatech.portal.factory.ModuleActionDaoFactory;
import com.dikshatech.portal.factory.ModulePermissionDaoFactory;
import com.dikshatech.portal.factory.ModulesDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.RolesDaoFactory;
import com.dikshatech.portal.factory.UserRolesDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;

public class RoleModel extends ActionMethods
{
	private static Logger logger = LoggerUtil.getLogger(RoleModel.class);

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		Roles rolesDto = (Roles) form;
		RolesDao rolesDao = RolesDaoFactory.create();
		UserRolesDao userRolesDao = UserRolesDaoFactory.create();
		try
		{
			StringBuffer holdNotDeletedIds = new StringBuffer();
			for (Integer id : rolesDto.getRoleIds())
			{

				UserRoles[] userRolesArr = userRolesDao.findByDynamicSelect("SELECT * FROM USER_ROLES WHERE ROLE_ID = ?", new Object[]
				{ id.intValue() });

				if (userRolesArr.length == 0)
				{
					rolesDao.delete(new RolesPk(id.intValue()));
				}
				else
				{
					holdNotDeletedIds.append(id.intValue() + ",");
				}

			}

			if (holdNotDeletedIds.length() > 0)
			{

				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("roles.delete.error", holdNotDeletedIds.toString()));
				result.setForwardName("success");

			}

		}
		catch (RolesDaoException rde)
		{
			// TODO Auto-generated catch block
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Unable to delete Role.Please detach"));
			result.setForwardName("success");

		}
		catch (Exception e)
		{

			e.printStackTrace();
		}

		return result;
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

	@SuppressWarnings("unchecked")
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request)
	{

		ActionResult result = new ActionResult();
		RolesDao rolesDao = RolesDaoFactory.create();
		ModulePermissionDao modulePermissionDao = ModulePermissionDaoFactory.create();
		ModulesDao modulesDao = ModulesDaoFactory.create();
		ModuleActionDao moduleActionDao = ModuleActionDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		AccessibilityLevelDao accessibilityLevelDao = AccessibilityLevelDaoFactory.create();
		try
		{

			switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
			{

			case RECEIVEALLROLES:
			{
				DropDown dropDown = (DropDown) form;
				dropDown.setDropDown(rolesDao.findAll());
				request.setAttribute("actionForm", dropDown);
				break;
			}

			case RECEIVEROLES:
			{
				Roles rolesDto = (Roles) form;
				Roles dbRolesDto = rolesDao.findByPrimaryKey(rolesDto.getId());
				ModulePermission[] modulePermissionDtos = modulePermissionDao.findWhereRoleIdEquals(rolesDto.getId());
				Modules[] modules = modulesDao.findWhereParentIdEquals(0);

				List<Modules> modulesList = Arrays.asList(modules);
				List<String> featuresList = new ArrayList<String>();

				com.dikshatech.beans.Roles roleBean = DtoToBeanConverter.DtoToBeanConverter(dbRolesDto);

				Set<ModuleBean> moduleBeansSet = new HashSet<ModuleBean>();

				Set<Integer> parentIdSet = new HashSet<Integer>();
				for (ModulePermission mPermissionDto : modulePermissionDtos)
				{

					Modules featuresDto = modulesDao.findByPrimaryKey(mPermissionDto.getModuleId());
					featuresList.add(String.valueOf(featuresDto.getId()));
					if(featuresDto.getParentId()!=0)
					parentIdSet.add(featuresDto.getParentId());

				}
				Map<Integer, Modules> modulesMap = new HashMap<Integer, Modules>();
				Map<Integer, Object> parentChildMap = new HashMap<Integer, Object>();

				for (Object modules1 : modulesList)
				{
					Modules tempModules = (Modules) modules1;
					Modules[] childModules = modulesDao.findWhereParentIdEquals(tempModules.getId());
					Map<Integer, Modules> featureMap = new HashMap<Integer, Modules>();

					for (Modules childMod : childModules)
					{

						featureMap.put(childMod.getId(), childMod);

					}
					parentChildMap.put(new Integer(tempModules.getId()), featureMap);
					modulesMap.put(new Integer(tempModules.getId()), tempModules);
				}

				for (Integer parentId : parentIdSet)
				{

					Set<Features> featuresBeansSet = new HashSet<Features>();
					com.dikshatech.beans.ModuleBean moduleBean = new com.dikshatech.beans.ModuleBean();

					ModuleAction[] moduleActionDtoArr = moduleActionDao.findByDynamicSelect(
							"SELECT * FROM MODULE_ACTION  WHERE ROLE_ID =? AND MODULE_ID=? ", new Object[]
							{ rolesDto.getId(), parentId.intValue() });

					String child = moduleActionDtoArr[0].getActionChain();

					String[] items = child.split(",");

					List<String> templist = null;
					if (items.length > 0)
					{

						templist = Arrays.asList(items);

					}
					// --|Already mapped features with role

					Set<Integer> fetureSetId = new HashSet<Integer>();
					HashMap<Integer, Modules> childMap = (HashMap<Integer, Modules>) parentChildMap.get(parentId);
					Set<Integer> childKey = childMap.keySet();
					for (String featureIdStr : templist)
					{
							try{
						Features featureBean = new Features();
						Modules parentsDto = modulesDao.findByPrimaryKey(parentId.intValue());
						Modules tempFeaturesDto = modulesDao.findByPrimaryKey(Integer.parseInt(featureIdStr));
						ModulePermission mPermissionDto = modulePermissionDao.findByRoleAndModule(rolesDto.getId(), tempFeaturesDto.getId());
						AccessibilityLevel accessibilityLevelDto = accessibilityLevelDao.findByPrimaryKey(mPermissionDto.getAccessibilityId());

						featureBean = DtoToBeanConverter.DtoToBeanConverter(featureBean, accessibilityLevelDto);

						featureBean.setFeatureId(tempFeaturesDto.getId());
						featureBean.setFeatureName(tempFeaturesDto.getName());
						featureBean.setParentId(tempFeaturesDto.getParentId());
						featureBean.setIsProcessChain(tempFeaturesDto.getIsProcChain());
						featureBean.setIsSelected(1);
						if (tempFeaturesDto.getIsProcChain() == 1)
						{

							featureBean.setIsProcessChain(1);
							moduleBean.setIsModuleProcessChain(1);
						}
						if (mPermissionDto.getProcChainId() != 0)
						{
							featureBean.setIsProcessChain(1);
							moduleBean.setIsModuleProcessChain(1);
							featureBean.setProcessChainId(mPermissionDto.getProcChainId());
							ProcessChain processChain = processChainDao.findByPrimaryKey(mPermissionDto.getProcChainId());
							featureBean.setProcessChainName(processChain.getProcName());
						}
						featureBean.setAccessibilityId(accessibilityLevelDto.getId());

						featureBean.setPermissionLabels(EnumUtil.checkPermissions(tempFeaturesDto.getPermissionTypes()));
						//|-- Feature based accessibility selected
						featureBean = DtoToBeanConverter.DtoToBeanConverter(featureBean);

						moduleBean.setModuleId(parentId.intValue());
						moduleBean.setModuleName(parentsDto.getName());
						moduleBean.setIsModulePermissionTypes(EnumUtil.checkPermissions(tempFeaturesDto.getPermissionTypes()));

						featuresBeansSet.add(featureBean);
						fetureSetId.add(Integer.parseInt(featureIdStr));

						childMap.remove(Integer.parseInt(featureIdStr));
							} catch (Exception e){}
					}
					// --|List of unmapped features of same module

					boolean isProcessChain = true;
					for (Integer childId : childKey)
					{

						Modules childModule = (Modules) childMap.get(childId);

						com.dikshatech.beans.Features features = new com.dikshatech.beans.Features();
						features.setParentId(parentId.intValue());
						features.setFeatureName(childModule.getName());
						features.setFeatureId(childModule.getId());
						features.setIsProcessChain(childModule.getIsProcChain());
						if (isProcessChain)
						{

							if (childModule.getIsProcChain() > 0)
							{

								moduleBean.setIsModuleProcessChain(childModule.getIsProcChain());
								isProcessChain = false;
							}
						}
						features.setPermissionLabels(EnumUtil.checkPermissions(childModule.getPermissionTypes()));
						

						featuresBeansSet.add(features);

					}
					if (childMap.isEmpty())
					{
						moduleBean.setIsSelected(1);
					}
					else
					{
						moduleBean.setIsSelected(2);
					}
					moduleBean.setFeatures(featuresBeansSet);

					moduleBeansSet.add(moduleBean);

					modulesMap.remove(parentId);

				}
				// --All others modules which are still unmapped with this role
				Set<Integer> modulesKey = modulesMap.keySet();
				for (Integer otherId : modulesKey)
				{

					boolean isProcessChain = true;
					com.dikshatech.beans.ModuleBean modulesBean = new com.dikshatech.beans.ModuleBean();
					Modules newModule = (Modules) modulesMap.get(otherId);

					modulesBean.setModuleId(newModule.getId());
					modulesBean.setModuleName(newModule.getName());
					modulesBean.setIsModulePermissionTypes(EnumUtil.checkPermissions(newModule.getPermissionTypes()));

					Modules[] childModules = modulesDao.findWhereParentIdEquals(newModule.getId());

					Set<Features> featuresBeansSet = new HashSet<Features>();

					for (Modules childMod : childModules)
					{
						com.dikshatech.beans.Features features = new com.dikshatech.beans.Features();
						features.setParentId(newModule.getId());
						features.setFeatureName(childMod.getName());
						features.setFeatureId(childMod.getId());
						features.setIsProcessChain(childMod.getIsProcChain());
						if (isProcessChain)
						{

							if (childMod.getIsProcChain() > 0)
							{

								modulesBean.setIsModuleProcessChain(childMod.getIsProcChain());
								isProcessChain = false;
							}
						}
						features.setPermissionLabels(EnumUtil.checkPermissions(childMod.getPermissionTypes()));

						featuresBeansSet.add(features);

					}
					modulesBean.setFeatures(featuresBeansSet);
					moduleBeansSet.add(modulesBean);
				}

				roleBean.setModules(moduleBeansSet);
				rolesDto.setRolesBean(roleBean);
				request.setAttribute("actionForm", rolesDto);
				break;
			}// End of case RECEIVEROLES

			}// End of Switch stmt
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		Roles rolesDto = (Roles) form;
		RolesDao rolesDao = RolesDaoFactory.create();
		ModuleActionDao moduleActionDao = ModuleActionDaoFactory.create();

		ModulePermissionDao modulePermissionDao = ModulePermissionDaoFactory.create();
		try
		{

			// --|Check duplication of Role name

			Roles[] rolesArr = rolesDao.findByDynamicSelect("SELECT * FROM ROLES WHERE NAME = ?", new Object[]
			{ rolesDto.getName() });

			if (rolesArr.length > 0)
			{

				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("roles.name.duplicate", rolesDto.getName()));
				result.setForwardName("success");
				return result;
			}

			RolesPk rolesPk = rolesDao.insert(rolesDto);
			logger.trace("New Roles saved in Data Base with Id" + rolesPk.toString());

			if (rolesPk != null && rolesDto.getModuleAccessLevels().length > 0)
			{

				if (logger.isTraceEnabled())
					logger.trace(Arrays.asList(rolesDto.getModuleAccessLevels()));

				HashMap<String, String> featureMap = new HashMap<String, String>();

				for (String moduleStr : rolesDto.getModuleAccessLevels())

				{

					String[] strArr = moduleStr.split("\\|");

					ModulePermission modulePermissionDto = new ModulePermission();

					modulePermissionDto.setRoleId(rolesPk.getId());
					modulePermissionDto.setModuleId(Integer.parseInt(strArr[1]));
					if (Integer.parseInt(strArr[2]) != 0)
						modulePermissionDto.setProcChainId(Integer.parseInt(strArr[2]));
					modulePermissionDto.setAccessibilityId(Integer.parseInt(strArr[3]));
					modulePermissionDao.insert(modulePermissionDto);

					if (featureMap.isEmpty())
					{

						featureMap.put(strArr[0], strArr[1]);
					}
					else
					{
						if (!featureMap.containsKey(strArr[0]))
						{
							featureMap.put(strArr[0], strArr[1]);
						}
						else
						{
							String str = featureMap.get(strArr[0]);
							str = str + "," + strArr[1];
							featureMap.put(strArr[0], str);
						}
					}

				}// End of for loop
				// --|saving data in Module Action table
				Set<String> keySet = featureMap.keySet();
				Iterator<String> keySetIterator = keySet.iterator();

				while (keySetIterator.hasNext())
				{
					ModuleAction moduleAction = new ModuleAction();
					String key = keySetIterator.next();
					moduleAction.setRoleId(rolesPk.getId());
					moduleAction.setModuleId(Integer.parseInt(key));
					moduleAction.setActionChain(featureMap.get(key));
					moduleActionDao.insert(moduleAction);
				}

			}
		}
		catch (RolesDaoException rde)
		{
			// TODO Auto-generated catch block
			rde.printStackTrace();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();

		try
		{
			Roles rolesDto = (Roles) form;
			RolesDao rolesDao = RolesDaoFactory.create();
			ModuleActionDao moduleActionDao = ModuleActionDaoFactory.create();
			ModulePermissionDao modulePermissionDao = ModulePermissionDaoFactory.create();
           //|--Below ArrayList is to delete all single feature mapped with a role.Single feature is not comming from UI
			Set<Integer> featurSet=new HashSet<Integer>(); 
			ArrayList<Integer> listOfModulesId=null;
			Roles tempRolesDT = rolesDao.findByPrimaryKey(rolesDto.getId());

			tempRolesDT.setName(rolesDto.getName());

			rolesDao.update(new RolesPk(rolesDto.getId()), tempRolesDT);

			logger.trace("Roles with id " + tempRolesDT.getId() + " updated in Data Base");

			if (tempRolesDT != null && rolesDto.getModuleAccessLevels().length > 0)
			{

				String sql="SELECT MODULE_ID FROM  MODULE_ACTION WHERE ROLE_ID=?";
				//|-- Below is Dedicated method to find all module mapped with single role
				 listOfModulesId=modulePermissionDao.findByDynamicSelectAllModulesIdBasedOnRole(sql,new Object[]{rolesDto.getId()});
				
				if (logger.isTraceEnabled())
					logger.trace(Arrays.asList(rolesDto.getModuleAccessLevels()));
				HashMap<String, String> featureMap = new HashMap<String, String>();
				
				for (String moduleStr : rolesDto.getModuleAccessLevels())

				{

					String[] strArr = moduleStr.split("\\|");

					ModulePermission modulePermissionDto = modulePermissionDao.findByRoleAndModule(rolesDto.getId(), Integer.parseInt(strArr[1]));
					featurSet.add(Integer.parseInt(strArr[0])); 
					if (modulePermissionDto != null)
					{
						if (Integer.parseInt(strArr[2]) != 0)
						{
							modulePermissionDto.setProcChainId(Integer.parseInt(strArr[2]));

						}
						else
						{
							modulePermissionDto.setProcChainIdNull(true);
						}

						modulePermissionDto.setAccessibilityId(Integer.parseInt(strArr[3]));
						modulePermissionDao.update(new ModulePermissionPk(modulePermissionDto.getId()), modulePermissionDto);

					}
					else
					{

						ModulePermission modulePermissionTempDto = new ModulePermission();

						modulePermissionTempDto.setRoleId(rolesDto.getId());
						modulePermissionTempDto.setModuleId(Integer.parseInt(strArr[1]));
						if (Integer.parseInt(strArr[2]) != 0)
							modulePermissionTempDto.setProcChainId(Integer.parseInt(strArr[2]));
						modulePermissionTempDto.setAccessibilityId(Integer.parseInt(strArr[3]));
						modulePermissionDao.insert(modulePermissionTempDto);
					}
					// /--|To edit in ModuleAction Table

					if (featureMap.isEmpty())
					{

						featureMap.put(strArr[0], strArr[1]);
					}
					else
					{
						if (!featureMap.containsKey(strArr[0]))
						{
							featureMap.put(strArr[0], strArr[1]);
						}
						else
						{
							String str = featureMap.get(strArr[0]);
							str = str + "," + strArr[1];
							featureMap.put(strArr[0], str);
						}
					}

				} // End of for loop

				Set<String> keySet = featureMap.keySet();
				Iterator<String> keySetIterator = keySet.iterator();

				while (keySetIterator.hasNext())
				{

					String key = keySetIterator.next();

					ModuleAction[] moduleActionArr = moduleActionDao.findByDynamicSelect(
							"SELECT ID,ROLE_ID,MODULE_ID,ACTION_CHAIN FROM MODULE_ACTION WHERE ROLE_ID = ? AND MODULE_ID = ? ", new Object[]
							{ rolesDto.getId(), Integer.parseInt(key) });

					if (moduleActionArr.length > 0)
					{

						ModuleAction moduleActionDto = moduleActionArr[0];
						moduleActionDto.setRoleId(rolesDto.getId());
						moduleActionDto.setModuleId(Integer.parseInt(key));

						// |-- if existing feature unmapped with the role delete
						// it

						String existingFeatures = moduleActionDto.getActionChain();
						List<String> newList = Arrays.asList(featureMap.get(key).split(","));
						if (!existingFeatures.equals(""))
						{

							String[] strArray = existingFeatures.split(",");

							for (int i = 0; i < strArray.length; i++)
							{

								if (newList.contains(strArray[i]))
								{

									continue;
								}
								try{
									ModulePermission modulePermission = modulePermissionDao.findByRoleAndModule(rolesDto.getId(), Integer.parseInt(strArray[i]));
									modulePermissionDao.delete(new ModulePermissionPk(modulePermission.getId()));
								} catch (Exception e){
									logger.error("unable to delete module permission. module Id:" + strArray[i] + " Role Id:" + rolesDto.getId());
								}
							}
						}
						moduleActionDto.setActionChain(featureMap.get(key));
						moduleActionDao.update(new ModuleActionPk(moduleActionDto.getId()), moduleActionDto);
					}
					else
					{

						ModuleAction moduleAction = new ModuleAction();

						moduleAction.setRoleId(rolesDto.getId());
						moduleAction.setModuleId(Integer.parseInt(key));
						moduleAction.setActionChain(featureMap.get(key));
						moduleActionDao.insert(moduleAction);

					}

				}//End of While loop

			}// EO if statement
			
			if(!listOfModulesId.isEmpty()){
				List<Integer> featurList = new ArrayList<Integer>(featurSet);
				listOfModulesId.removeAll(featurList);
				
				   if(!listOfModulesId.isEmpty()){
					   
					   for(Integer moduleId :listOfModulesId ){
						   
						   ModuleAction[] moduleActionArr = moduleActionDao.findByDynamicSelect(
									"SELECT ID,ROLE_ID,MODULE_ID,ACTION_CHAIN FROM MODULE_ACTION WHERE ROLE_ID = ? AND MODULE_ID = ? ", new Object[]
									{ rolesDto.getId(), moduleId  });
						   
						   String featurestr=moduleActionArr[0].getActionChain();
						   
						   String[] strArray = featurestr.split(",");
						   
						if (strArray.length > 0){
							for (int i = 0; i < strArray.length; i++){
								try{
									ModulePermission modulePermission = modulePermissionDao.findByRoleAndModule(rolesDto.getId(), Integer.parseInt(strArray[i]));
									modulePermissionDao.delete(new ModulePermissionPk(modulePermission.getId()));
								} catch (Exception e){
									logger.error("unable to delete module permission. module Id:" + strArray[i] + " Role Id:" + rolesDto.getId());
								}
							}
							moduleActionDao.delete(new ModuleActionPk(moduleActionArr[0].getId()));
						}
						   
						   
						   
					   }
					   
					   
				   }
			}
			result.setForwardName("success");
		}

		catch (RolesDaoException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		catch (ModulePermissionDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (NumberFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ModuleActionDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		String switchValue = "UNKNOWN";
		ActionResult result = new ActionResult();
		result.setMethodResult(false);

		switchValue = (String) request.getSession(false).getAttribute("valid");
		if (request.getSession(false).getAttribute("valid") != null)
		{
			switchValue = (String) request.getSession(false).getAttribute("valid");
		}
		switch (ActionMethods.ValidationTypes.getValue(switchValue))
		{
		case ISVALIDFOREDIT:
		{

			break;
		}
		case UNKNOWN:
		{

			logger.info("No match found for switch");
			break;
		}
		}
		return result;
	}

}