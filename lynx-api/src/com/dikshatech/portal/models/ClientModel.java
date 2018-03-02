package com.dikshatech.portal.models;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.dikshatech.beans.ClientDetailsBean;
import com.dikshatech.beans.NestedBean;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.AccessibilityTopLevelDao;
import com.dikshatech.portal.dao.ClientDao;
import com.dikshatech.portal.dao.ClientDivMapDao;
import com.dikshatech.portal.dao.CompanyDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.ModulePermissionDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.UserRolesDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.AccessibilityTopLevel;
import com.dikshatech.portal.dto.Client;
import com.dikshatech.portal.dto.ClientDivMap;
import com.dikshatech.portal.dto.ClientPk;
import com.dikshatech.portal.dto.Company;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ModulePermission;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.UserRoles;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.ClientDaoException;
import com.dikshatech.portal.exceptions.ClientDivMapDaoException;
import com.dikshatech.portal.exceptions.DivisonDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.factory.AccessibilityTopLevelDaoFactory;
import com.dikshatech.portal.factory.ClientDaoFactory;
import com.dikshatech.portal.factory.ClientDivMapDaoFactory;
import com.dikshatech.portal.factory.CompanyDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.ModulePermissionDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.UserRolesDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class ClientModel extends ActionMethods
{
	Logger	logger	= LoggerUtil.getLogger();

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
		ActionResult result = new ActionResult();

		switch (ActionMethods.ExecuteTypes.getValue(form.geteType()))
		{
			case DISABLECLIENT:
			{

				Client clientForm = (Client) form;
				ClientDao clientDao = ClientDaoFactory.create();

				try
				{
					Client client = clientDao.findByPrimaryKey(clientForm.getId());
					ClientPk clientPk = client.createPk();
					// client.setIsEnable("Disabled");
					clientDao.update(clientPk, client);

					request.setAttribute("actionForm", clientForm);
					result.setForwardName("success");
				} catch (ClientDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			case ENABLECLIENT:
			{
				Client clientForm = (Client) form;
				ClientDao clientDao = ClientDaoFactory.create();

				try
				{
					Client client = clientDao.findByPrimaryKey(clientForm.getId());
					ClientPk clientPk = client.createPk();
					// client.setIsEnable("Enabled");
					clientDao.update(clientPk, client);
					request.setAttribute("actionForm", clientForm);
					result.setForwardName("success");
				} catch (ClientDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			default:
				break;
		}
		return result;
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
		switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
		{
			case RECEIVEALLCLIENT:
			{
				Client clientForm = (Client) form;
				ClientDao clientDao = ClientDaoFactory.create();
				DivisonDao divDao = DivisonDaoFactory.create();
				RegionsDao regionDao = RegionsDaoFactory.create();
				Set< Client > clientSet = new HashSet< Client >();
				ClientDivMapDao mapDao = ClientDivMapDaoFactory.create();
				try
				{

					String sql = "SELECT ID, CLIENT.REGION_ID, CLIENT.DEPT_ID, CLIENT.NAME, CLIENT.DESCRIPTION," + "(SELECT COUNT(ID) AS PROJECTS FROM PROJ_CLIENT_MAP WHERE CLIENT_ID=CLIENT.ID) FROM CLIENT";

					// Object[] allClient = clientDao.findAll();
					Object[ ] allClient = clientDao.findByDynamicSelect(sql, null);
					for (Object object : allClient)
					{
						Client client = (Client) object;
						Client tempClient = new Client();
						Divison deptInfo = divDao.findByPrimaryKey(client.getDeptId());
						Regions regionInfo = regionDao.findByPrimaryKey(client.getRegionId());
						ClientDivMap[ ] clientDivions = mapDao.findByClient(client.getId());
						ClientDetailsBean clientBean = DtoToBeanConverter.PopulateBeanFromDto(clientDivions);
						tempClient.setId(client.getId());
						tempClient.setName(client.getName());

						tempClient.setDeptId(client.getDeptId());
						tempClient.setDeptName(deptInfo.getName());
						tempClient.setRegionId(client.getRegionId());
						tempClient.setRegionName(regionInfo.getRegName());

						tempClient.setDeptName(deptInfo.getName());

						tempClient.setDescription(client.getDescription());
						tempClient.setDivisions(clientBean.getDivisions());
						tempClient.setNoOfProjects(client.getNoOfProjects());
						clientSet.add(tempClient);
					}
					clientForm.setClients(clientSet);
					request.setAttribute("actionForm", clientForm);
					result.setForwardName("success");
				} catch (ClientDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DivisonDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientDivMapDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RegionsDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			}
			case RECEIVECLIENT:
			{
				Client clientForm = (Client) form;
				ClientDao clientDao = ClientDaoFactory.create();
				ClientDivMapDao mapDao = ClientDivMapDaoFactory.create();
				DivisonDao divisonDao = DivisonDaoFactory.create();
				RegionsDao regionDao = RegionsDaoFactory.create();
				try
				{
					Client client = clientDao.findByPrimaryKey(clientForm.getId());
					Divison deptInfo = divisonDao.findByPrimaryKey(client.getDeptId());
					ClientDivMap[ ] clientDivions = mapDao.findByClient(clientForm.getId());
					ClientDetailsBean clientBean = DtoToBeanConverter.PopulateBeanFromDto(clientDivions);

					Regions regionInfo = regionDao.findByPrimaryKey(client.getRegionId());
					clientForm.setName(client.getName());

					clientForm.setDeptId(client.getDeptId());
					clientForm.setDeptName(deptInfo.getName());
					clientForm.setRegionId(client.getRegionId());
					clientForm.setRegionName(regionInfo.getRegName());
					clientForm.setDivisions(clientBean.getDivisions());
					clientForm.setDescription(client.getDescription());

					request.setAttribute("actionForm", clientForm);
					result.setForwardName("success");

				} catch (ClientDaoException e)
				{
					e.printStackTrace();
				} catch (ClientDivMapDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DivisonDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RegionsDaoException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			case RECEIVEREGION:
			{
				try
				{
					Login loginDto = Login.getLogin(request);
					DropDown dropDown = new DropDown();
					AccessibilityTopLevelDao topLevelDao = AccessibilityTopLevelDaoFactory.create();
					DivisonDao divDao = DivisonDaoFactory.create();
					RegionsDao regionsDao = RegionsDaoFactory.create();
					CompanyDao compDao = CompanyDaoFactory.create();
					Set< Regions > r = new HashSet< Regions >();
					Set< Company > c = new HashSet< Company >();
					AccessibilityTopLevel[ ] topLevelDto = topLevelDao.findByDynamicSelect("SELECT * FROM ACCESSIBILITY_TOP_LEVEL ATL LEFT JOIN MODULE_PERMISSION MP ON ATL.ACCESSIBILITY_ID=MP.ACCESSIBILITY_ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=33 WHERE UR.ID=?", new Object[ ]
					{ new Integer(loginDto.getUserId()) });
					Object company[] = new Object[topLevelDto.length];
					for (AccessibilityTopLevel l2 : topLevelDto)
					{
						NestedBean companyData = new NestedBean();
						Company comp = compDao.findByPrimaryKey(Integer.parseInt(l2.getCompanyId()));
						if (!c.contains(comp))
						{
							c.add(comp);
							companyData.setDataLevel("Company");
							companyData.setFieldId(comp.getId());
							companyData.setFieldName(comp.getCompanyName());

							Set< NestedBean > region = new HashSet< NestedBean >();
							for (AccessibilityTopLevel l1 : topLevelDto)
							{
								Regions reg = regionsDao.findByPrimaryKey(Integer.parseInt(l1.getRegionId()));
								NestedBean regionData = new NestedBean();
								if (!r.contains(reg))
								{
									r.add(reg);
									regionData.setDataLevel("Region");
									regionData.setFieldId(reg.getId());
									regionData.setFieldName(reg.getRegName());
									Set< NestedBean > depts = new HashSet< NestedBean >();
									for (AccessibilityTopLevel l : topLevelDto)
									{
										Divison d = new Divison();
										NestedBean dept = new NestedBean();
										Set< NestedBean > divisions = new HashSet< NestedBean >();
										if (l.getDepartmentId().equals("0"))
											d = divDao.findByPrimaryKey(Integer.parseInt(l.getDivisionId()));
										else
											d = divDao.findByPrimaryKey(Integer.parseInt(l.getDepartmentId()));
										if (l.getDivisionId().equals("0") && l1.getRegionId().equals(l.getRegionId()))
										{
											dept.setFieldName(d.getName());
											dept.setFieldId(d.getId());
											dept.setDataLevel("Department");
											Divison[ ] dv = divDao.findWhereParentIdEquals(Integer.parseInt(l.getDepartmentId()));
											for (Divison di : dv)
											{
												NestedBean div = new NestedBean();
												div.setDataLevel("Division");
												div.setFieldId(di.getId());
												div.setFieldName(di.getName());
												divisions.add(div);
											}
											dept.setNestedRecord(divisions);
											depts.add(dept);

										}
										else if (l1.getRegionId().equals(l.getRegionId()))
										{
											Set< Divison > divCheck = new HashSet< Divison >();
											dept.setFieldName(d.getName());
											dept.setFieldId(d.getId());
											dept.setDataLevel("Department");
											divCheck.add(d);
											for (String id : l.getDivisionId().split(","))
											{
												Divison di = divDao.findByPrimaryKey(Integer.parseInt(id));
												NestedBean div = new NestedBean();
												div.setDataLevel("Division");
												div.setFieldId(di.getId());
												div.setFieldName(di.getName());
												if (!divCheck.contains(di))
													divisions.add(div);
												divCheck.add(di);
											}
											dept.setNestedRecord(divisions);
											depts.add(dept);
										}

									}
									regionData.setNestedRecord(depts);
									region.add(regionData);
								}
							}
							companyData.setNestedRecord(region);
							company[0] = companyData;
							dropDown.setDropDown(company);
							request.setAttribute("actionForm", dropDown);
						}
					}
					break;

				} catch (Exception e)
				{
					e.printStackTrace();
				}
				break;
			}
			default:
				break;
		}
		return result;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		ClientDao clientDao = ClientDaoFactory.create();
		ClientDivMapDao divMapDao = ClientDivMapDaoFactory.create();
		ClientPk clientPk = null;

		Client client = (Client) form;
		Client[ ] clients = null;

		UserRolesDao userRolesDao = UserRolesDaoFactory.create();
		ModulePermissionDao modulePermissionDao = ModulePermissionDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		String region = "None";
		Login login = Login.getLogin(request);
		try
		{
			clients = clientDao.findWhereNameEquals(client.getName());
			if (clients.length != 0)
			{
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.duplicate.client.name"));
				result.setForwardName("success");
				return result;
			}
			else
			{
				clientPk = clientDao.insert(client);
				if (clientPk != null && client.getDivIds() != null)
				{
					for (String divId : client.getDivIds())
					{
						ClientDivMap clientDivMap = new ClientDivMap();
						clientDivMap.setClientId(clientPk.getId());
						clientDivMap.setDivId(Integer.parseInt(divId));
						divMapDao.insert(clientDivMap);

					}

				}
				logger.debug("Client dto is:::" + client.getId());

				// send Mail Notification
				region = client.getRegionId() > 0 ? RegionsDaoFactory.create().findByPrimaryKey(client.getRegionId()).getRegName() : region;
				UserRoles userRolesDto = userRolesDao.findWhereUserIdEquals(form.getUserId().intValue());

				String sql = "SELECT * FROM MODULE_PERMISSION WHERE ROLE_ID=? AND MODULE_ID=32";
				Object[ ] sqlParam =
				{ userRolesDto.getRoleId() };

				ModulePermission modulePermisssionData = modulePermissionDao.findByDynamicSelect(sql, sqlParam)[0];
				ProcessChain processChainDto = processChainDao.findWhereIdEquals(modulePermisssionData.getProcChainId())[0];

				ProcessEvaluator pEvaluator = new ProcessEvaluator();

				/*
				 * ProcessChainBean processChainBean =
				 * DtoToBeanConverter.DtoToBeanConverterToParseProcessChain
				 * (processChainDto.getNotification());
				 */
				String addedBy = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(login.getUserId()).getProfileId()).getFirstName();
				
				String mailSubject = "New Client ["+client.getName()+"] added by ["+addedBy+"]";
				Integer[ ] userIds = pEvaluator.notifiers(processChainDto.getNotification(), form.getUserId());
				if (userIds.length > 0)
				{
					for (int id : userIds)
					{
						Users usersDto = usersDao.findByPrimaryKey(id);
						ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(usersDto.getProfileId());
						sendMailDetails(mailSubject, profileInfo.getOfficalEmailId(), profileInfo.getFirstName(), region, MailSettings.CLIENT_DETAILS_ADDED, clientPk.getId(), client.getName(), null,addedBy);
					}
				}

				request.setAttribute("actionForm", client);

				result.setForwardName("success");
			}
		} catch (ClientDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientDivMapDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
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
		Client clientForm = (Client) form;
		ClientDao clientDao = ClientDaoFactory.create();
		ClientPk clientPk = clientForm.createPk();
		ClientDivMapDao divMapDao = ClientDivMapDaoFactory.create();

		UserRolesDao userRolesDao = UserRolesDaoFactory.create();
		ModulePermissionDao modulePermissionDao = ModulePermissionDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		String region = "None";
		Login login = Login.getLogin(request);
		
		if(clientForm.isNameModified()){
			try{
				String WHERE_CLAUSE=" WHERE C.NAME='"+clientForm.getName()+"'";
				Client[] existingClients = clientDao.findByDynamicSelect("SELECT * FROM CLIENT C"+WHERE_CLAUSE +" AND C.ID!=?", new Object[]{clientForm.getId()});
				if(existingClients!=null && existingClients.length>0){
					logger.error("CLIENT UPDATE ERROR : NAME ALREADY EXISTS");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.duplicate.client.name"));
					result.setForwardName("success");
					return result;
				}
				logger.debug("CLIENT NAME--->"+clientForm.getName()+" IS UNIQUE");
			}catch(ClientDaoException cdex){
				logger.error("EXCEPTION WAS THROWN WHEN TRYING TO FETCH DATA FROM CLIENT FOR "+clientForm.getName(), cdex);
			}
		}
		
		
		try
		{
			clientDao.update(clientPk, clientForm);

			// Update Client and Division Map Table
			if (clientForm.getDivIds() != null)
			{
				// Delete Previuosly attached Div with CLient
				divMapDao.deleteAllByClient(clientForm.getId());

				// Make New Entry in Table
				for (String divId : clientForm.getDivIds())
				{
					ClientDivMap clientDivMap = new ClientDivMap();
					clientDivMap.setClientId(clientForm.getId());
					clientDivMap.setDivId(Integer.parseInt(divId));
					divMapDao.insert(clientDivMap);

				}

			}
			// send Mail Notification
			region = clientForm.getRegionId() > 0 ? RegionsDaoFactory.create().findByPrimaryKey(clientForm.getRegionId()).getRegName() : region;
			UserRoles userRolesDto = userRolesDao.findWhereUserIdEquals(form.getUserId().intValue());
			ModulePermission modulePermisssionData = modulePermissionDao.findByDynamicSelect("SELECT * FROM MODULE_PERMISSION WHERE ROLE_ID=? AND MODULE_ID=32", new Object[ ]
			{ new Integer(userRolesDto.getRoleId()) })[0];
			ProcessChain processChainDto = processChainDao.findWhereIdEquals(modulePermisssionData.getProcChainId())[0];

			ProcessEvaluator pEvaluator = new ProcessEvaluator();

			Integer[ ] userIds = pEvaluator.notifiers(processChainDto.getNotification(), form.getUserId());
			
			String updatedBy = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(login.getUserId()).getProfileId()).getFirstName();
			String mailSubject = "["+clientForm.getName()+"] details updated by ["+updatedBy+"]";

			if (userIds.length > 0)
			{
				for (int id : userIds)
				{
					Users usersDto = usersDao.findByPrimaryKey(id);
					ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(usersDto.getProfileId());
					sendMailDetails(mailSubject, profileInfo.getOfficalEmailId(), profileInfo.getFirstName(), region, MailSettings.CLIENT_DETAILS_UPDATED, clientPk.getId(), clientForm.getName(), null,updatedBy);
				}
			}
			request.setAttribute("actionForm", clientForm);
			result.setForwardName("success");

		} catch (ClientDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientDivMapDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public PortalMail sendMailDetails(String mailSubject, String mailId, String empFname, String region, String templateName, int clientId, String clientName, String fieldName , String clientCreatorName)
	{
		try
		{

			PortalMail portalMail = new PortalMail();
			portalMail.setRecipientMailId(mailId);
			portalMail.setMailSubject(mailSubject);
			portalMail.setEmpFname(empFname);
			portalMail.setRegion(region);
			portalMail.setTemplateName(templateName);
			portalMail.setFieldName(fieldName);
			portalMail.setClientName(clientName);
			portalMail.setClientId(clientId);
			portalMail.setClientCreatorName(clientCreatorName);//createdBy updatedBy
			portalMail.setOnDate(PortalUtility.formatDateToddmmyy(new Date()));//created/updated on dd-mm-yy
			
			if (mailId != null && mailId.contains("@"))
			{
				MailGenerator mailGenarator = new MailGenerator();
				mailGenarator.invoker(portalMail);
			}
			return portalMail;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
