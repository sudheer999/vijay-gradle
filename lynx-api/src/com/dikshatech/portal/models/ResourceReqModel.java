package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.DropDownBean;
import com.dikshatech.beans.ProfileListBean;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.ReqResMapDao;
import com.dikshatech.portal.dao.ResourceReqMapHistoryDao;
import com.dikshatech.portal.dao.ResourceReqMappingDao;
import com.dikshatech.portal.dao.ResourceRequirementDao;
import com.dikshatech.portal.dao.ResourceStatusDao;
import com.dikshatech.portal.dao.UserRolesDao;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.DocumentsPk;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ReqResMap;
import com.dikshatech.portal.dto.ReqResMapPk;
import com.dikshatech.portal.dto.ResourceReqMapHistory;
import com.dikshatech.portal.dto.ResourceReqMapping;
import com.dikshatech.portal.dto.ResourceReqMappingPk;
import com.dikshatech.portal.dto.ResourceRequirement;
import com.dikshatech.portal.dto.ResourceRequirementPk;
import com.dikshatech.portal.dto.ResourceStatus;
import com.dikshatech.portal.dto.UserRoles;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.DocumentsDaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.ReqResMapDaoFactory;
import com.dikshatech.portal.factory.ResourceReqMapHistoryDaoFactory;
import com.dikshatech.portal.factory.ResourceReqMappingDaoFactory;
import com.dikshatech.portal.factory.ResourceRequirementDaoFactory;
import com.dikshatech.portal.factory.ResourceStatusDaoFactory;
import com.dikshatech.portal.factory.UserRolesDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.timer.ResourceRequestNotifier;


public class ResourceReqModel extends ActionMethods {
	private static Logger		logger			= LoggerUtil.getLogger(ResourceReqModel.class);
	public static final int		MODULEID		= 79;
	public static final int		STATUS_ID		= 19;
	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		ProfileInfoDao profileDao=ProfileInfoDaoFactory.create();
		DropDown dropdown=new DropDown();
		EmpSerReqMapDao emp_Ser_Req_Map_Dao = EmpSerReqMapDaoFactory.create();
		EmpSerReqMapPk reqpk = new EmpSerReqMapPk();
		EmpSerReqMap empReqMapDto = new EmpSerReqMap();
		RegionsDao regionDao = RegionsDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ProcessChain process_chain_dto = new ProcessChain();
		ResourceRequirementDao resReqDao=ResourceRequirementDaoFactory.create();
		ResourceReqMappingDao   resMapDao=ResourceReqMappingDaoFactory.create();
		ReqResMapDao reqResMapDao=ReqResMapDaoFactory.create();
		ResourceReqMappingPk mapPk=new ResourceReqMappingPk();
		ResourceRequirementPk resPk=new ResourceRequirementPk();
	    
		UserRolesDao roleDao=UserRolesDaoFactory.create();
		ResourceReqMapHistoryDao historyDao=ResourceReqMapHistoryDaoFactory.create();
		try{
			Login login = Login.getLogin(request);
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access perdiem reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
		ResourceRequirement resForm = (ResourceRequirement) form;
		Connection conn = null;
		request.setAttribute("actionForm", "");// don't send unwanted data to ui again.....
		switch (SaveTypes.getValue(form.getsType())) {
			case SAVE:
				try{
					ActionResult actionResult=validate(resForm, request);
					if(!actionResult.getActionMessages().isEmpty()){
						return actionResult;
					}
					
					String toMailId="";
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					int reg_id = regionDao.findByDynamicSelect("SELECT * FROM REGIONS R LEFT JOIN DIVISON D ON D.REGION_ID=R.ID LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN PROFILE_INFO PI ON L.ID=PI.LEVEL_ID LEFT JOIN USERS U ON PI.ID=U.PROFILE_ID WHERE U.ID=?", new Object[] { new Integer(login.getUserId()) })[0].getId();
					// Get Region Abbreviation from Region Table using
					String reg_abb = regionDao.findByPrimaryKey(reg_id).getRefAbbreviation();
					// Get Process chain DTO object
					process_chain_dto = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=26  WHERE UR.USER_ID=?", new Object[] { new Integer(login.getUserId()) })[0];
					int uniqueID = 1;
					EmpSerReqMap empMap[] = emp_Ser_Req_Map_Dao.findByDynamicSelect("SELECT * FROM EMP_SER_REQ_MAP WHERE ID=(SELECT MAX(ID) FROM EMP_SER_REQ_MAP WHERE REQ_TYPE_ID=20)", null);
					if (empMap.length > 0){
						String s = empMap[0].getReqId().split("-")[2];
						uniqueID = Integer.parseInt(s) + 1;
					}
					// save Data in EMP_SER_REQ_MAP TABLE
					empReqMapDto = new EmpSerReqMap();
					empReqMapDto.setSubDate(new Date());
					empReqMapDto.setReqId(reg_abb + "-RR-" + uniqueID);
					empReqMapDto.setReqTypeId(20);
					empReqMapDto.setRegionId(reg_id);
					empReqMapDto.setRequestorId(login.getUserId());
					empReqMapDto.setProcessChainId(process_chain_dto.getId());
					empReqMapDto.setNotify(process_chain_dto.getNotification());
					reqpk = emp_Ser_Req_Map_Dao.insert(empReqMapDto);
					resForm.setEsrMapId(reqpk.getId());
					resForm.setCreateDate(new Date());
				
					// save Data in RESOURCE_REQUIREMENT TABLE
					resPk=resReqDao.insert(resForm);
					
					PortalMail portalMail = new PortalMail();
					ProfileInfo[] pf=null;
					ProfileInfo tempPf=null;
					List<ProfileInfo> newPf=new ArrayList<ProfileInfo>();
				if(resForm.getAssignedTo()==0){
					String sql="SELECT * FROM USER_ROLES where ROLE_ID=?";
					UserRoles[] list=roleDao.findByDynamicSelect(sql, new Object[] { 12 });
					for(UserRoles eachRole:list){
						tempPf=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {eachRole.getUserId()})[0];
				   newPf.add(tempPf);
					}
				pf = new ProfileInfo[newPf.size()];
					new ArrayList<ProfileInfo>(newPf).toArray(pf);
					portalMail.setCandidateName("All");
				}else{
				 pf=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {resForm.getAssignedTo()});
				 portalMail.setCandidateName(pf[0].getFirstName());
				}
				ProfileInfo pf1=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {login.getUserId()})	[0];
				
				portalMail.setSenderName(pf1.getFirstName());
				portalMail.setFieldName(reg_abb + "-RR-" + uniqueID);
				portalMail.setMailSubject("Diksha: New Resource Requirement Raised");
				portalMail.setTemplateName(MailSettings.RESOURCE_REQ_CREATED);
				PortalMail mail=sendMailToAll(portalMail,pf,resForm.getMailnotifiers(),pf1.getOfficalEmailId());	
				logger.info("New Resource request has been raised by "+pf1.getFirstName());	 
				} catch (Exception e){
					e.printStackTrace();
					logger.error("Unable to save new  resource requirement", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.save.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				ResourceRequestNotifier notifier=new  ResourceRequestNotifier();
				break;
			case SAVECANDIDATE:
				try{
					ResourceReqMapping map=new ResourceReqMapping();
					map=populateDto(resForm,map);
					map.setLastUpdatedBy(login.getUserId());
					map.setIsSelected(0);
					mapPk=resMapDao.insert(map);
					
                    if(resForm.getReqId()!=null){
                    	 ReqResMap reqRes=new ReqResMap();
                    	int reqId=Integer.parseInt(resForm.getReqId());
                    	reqRes.setReqId(reqId);
    					reqRes.setResourceId(mapPk.getId());
    					reqRes.setStatusId(resForm.getStatusId());
    					reqRes.setClosed(0);
    					reqResMapDao.insert(reqRes);
                    }
                   
					// Adding an entry in History table
					ResourceReqMapHistory history=new ResourceReqMapHistory();
					history=populateHistory(map,history);
					history.setModifiedBy(login.getUserId());
					historyDao.insert(history);
			//		PortalMail mail=sendMail(map,login.getUserId());
					
				} catch (Exception e){
					e.printStackTrace();
					logger.error("Unable to Propose a Candidate", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.save.failed"));
				} finally{
					ResourceManager.close(conn);
				}
			
				break;
		}
		} catch (Exception ex){
			ex.printStackTrace();
			logger.error("RESOURCE REQ UPDATE : failed to update data (main)", ex);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
			result.setForwardName("success");
			return result;
		}
		
		dropdown.setDropDown(null);
		request.setAttribute("actionForm", "");
		result.setForwardName("success");
		return null;
	
		
	}

	private ResourceReqMapping populateDto(ResourceRequirement resForm,ResourceReqMapping map) {
		if(resForm!=null){
		map.setResourceName(resForm.getResourceName());
		map.setEmailId(resForm.getEmailId());
		map.setContactNo(resForm.getContactNo());
		map.setTotalExp(resForm.getTotalExp());
		map.setRelevantExp(resForm.getRelevantExp() );
		map.setCurrentCompExp(resForm.getCurrentCompExp());
		map.setCurrentEmployer(resForm.getCurrentEmployer());
		map.setCurrentRole(resForm.getCurrentRole());
		map.setSkillSet(resForm.getSkillSet());
		map.setLeavingReason(resForm.getLeavingReason());
		map.setCtc(resForm.getCtc());
		map.setEctc(resForm.getEctc());
		map.setNoticePeriod(resForm.getNoticePeriod()) ;
		map.setOfferInHand(resForm.getOfferInHand()) ;
		map.setOptionForEarlyJoining(resForm.getOptionForEarlyJoining()) ;
		map.setConditionForEarlyJoining(resForm.getConditionForEarlyJoining()) ;
		map.setCurrentLocation(resForm.getCurrentLocation()) ;
		map.setLocConstraint(resForm.getLocConstraint()) ;
		map.setComments(resForm.getComments());
		map.setAttachmentId(resForm.getAttachmentId());
		}

		return map;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		
		Login login = Login.getLogin(request);
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access perdiem reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			EmpSerReqMapDao esrDao = EmpSerReqMapDaoFactory.create(conn);
			ResourceRequirementDao resReqDao=ResourceRequirementDaoFactory.create(conn);
			ResourceRequirementPk reqPk=new ResourceRequirementPk();
			ResourceReqMappingDao mapDao=ResourceReqMappingDaoFactory.create(conn);
			ReqResMapDao dao=ReqResMapDaoFactory.create(conn);
			ResourceStatusDao statusDao=ResourceStatusDaoFactory.create(conn);
			ProfileInfoDao profileDao=ProfileInfoDaoFactory.create();
			UserRolesDao roleDao=UserRolesDaoFactory.create();
			EmpSerReqMapDao esrMapDao=EmpSerReqMapDaoFactory.create();
			ResourceReqMapHistoryDao historyDao=ResourceReqMapHistoryDaoFactory.create();
			ResourceRequirement resForm = (ResourceRequirement) form;
			DropDown dropdown=new DropDown();
			// Added newly for only view(Business Level)
			int usId=login.getUserId();
		
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
			
				case RECEIVEALL:{// show only !escalated requests which was once received by me
					try{
						int view=0;
						ResourceRequirement[] resourceReqs = null;
							resourceReqs	= resReqDao.findAll();
					//		resourceReqs = resReqDao.findByDynamicWhere(" ASSIGNED_TO=? GROUP BY ID", new Object[] { login.getUserId() });
						
						HashMap<Integer, ResourceRequirement> resourceMap = new HashMap<Integer, ResourceRequirement>();
						if (resourceReqs != null && resourceReqs.length > 0){
							HashMap<Integer, String> idReqIdMap = esrDao.getIdReqIdMap(20);
							if (idReqIdMap != null && idReqIdMap.size() > 0){
								for (ResourceRequirement eachResourceReq : resourceReqs){
									int candidateSelected=0;
									int candidateProposed=0;
									ReqResMap[] mapReq=null;
									ReqResMap[] mapReq1=null;
									ResourceRequirement tempReq = resReqDao.findByPrimaryKey(eachResourceReq.getId());
									tempReq.setEsrMapReqId(idReqIdMap.get(tempReq.getEsrMapId()));
									String employmentType=resReqDao.getEmploymentTypeByID(eachResourceReq.getEmploymentTypeId());
									tempReq.setEmploymentType(employmentType);
									EmpSerReqMap esr=esrMapDao.findByPrimaryKey(tempReq.getEsrMapId());
									ProfileInfo requestorName=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {esr.getRequestorId()})	[0];
									if(requestorName!=null){
										tempReq.setRaisedBy(esr.getRequestorId());
										tempReq.setRaisedByName(requestorName.getFirstName());
									}
									if(tempReq.getAssignedTo()==0){
										tempReq.setAssignedToName("");
									}else{
									ProfileInfo assignedToName=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {tempReq.getAssignedTo()})	[0];
									if(assignedToName!=null){
										tempReq.setAssignedToName(assignedToName.getFirstName());
									}
									}
									mapReq=dao.findByReqId(eachResourceReq.getId());
									mapReq1=dao.findByDynamicWhere("REQ_ID=? AND CLOSED=1", new Object[] {eachResourceReq.getId()});
									if(mapReq!=null && mapReq.length>0){
										candidateProposed=mapReq.length;
									}
									if(mapReq1!=null && mapReq1.length>0){
										candidateSelected=mapReq1.length;
									}
									tempReq.setNocandiselected(candidateSelected);
									tempReq.setNoofproposedcandidate(candidateProposed);
									
									if(candidateSelected==tempReq.getNoOfPosition())
									{
										reqPk.setId(tempReq.getId());
										tempReq.setMainStatusId(18);
										resReqDao.update(reqPk, tempReq);
										tempReq.setMainStatus("Closed");
									}else{
										String resStatus=statusDao.getMainStatusByID(eachResourceReq.getMainStatusId());
										if(resStatus!=null){
											tempReq.setMainStatus(resStatus);	
										}
									}
									String d = new SimpleDateFormat("dd-MM-yyyy").format(tempReq.getCreateDate());
									tempReq.setCreateDate1( d);
									resourceMap.put(tempReq.getId(), tempReq);
								}
							}
						}
						
						List<ResourceRequirement> resourceList = new ArrayList(resourceMap.values());// non escalated requests
					
						ResourceRequirement[] resourceRequirements = new ResourceRequirement[resourceList.size()];
						new ArrayList<ResourceRequirement>(resourceList).toArray(resourceRequirements);
						dropdown.setDropDown(resourceRequirements);
						// Find login user id TAT 
						int isTatuser=0;
						String sql="SELECT P.*,U.ID as user_id FROM PROFILE_INFO P join USERS U  ON U.PROFILE_ID=P.ID join LEVELS L ON U.LEVEL_ID=L.ID JOIN DIVISON D ON L.DIVISION_ID=D.ID where U.STATUS=0 AND D.ID=?";
						ProfileInfo[] list=profileDao.findByDynamicSelect(sql, new Object[] { 12 });
						for(ProfileInfo eachProfile:list){
							Users[] u=UsersDaoFactory.create().findWhereProfileIdEquals(eachProfile.getId());
							for(Users userTat:u){
								if(userTat.getId()==login.getUserId()){
									isTatuser=1;
								}
							}
						}
						dropdown.setKey1(isTatuser);
						request.setAttribute("actionForm", dropdown);
					} catch (Exception ex){
						ex.printStackTrace();
						logger.error("RESOURCE REQ RECEIVEALL : failed to fetch data for userId=" + login.getUserId(), ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
					break;
				}
				case RECEIVE:{// show only !escalated requests which was once received by me
					try{
						
					
						ResourceRequirement rr=resReqDao.findByPrimaryKey(resForm.getId());
						if(rr!=null){
						String resStatus=statusDao.getMainStatusByID(rr.getMainStatusId());
						if(resStatus!=null){
							rr.setMainStatus(resStatus);	
						}
						String employmentType=resReqDao.getEmploymentTypeByID(rr.getEmploymentTypeId());
						rr.setEmploymentType(employmentType);
						EmpSerReqMap esr=esrMapDao.findByPrimaryKey(rr.getEsrMapId());
						if(esr!=null){
							rr.setReqId(esr.getReqId());
						}
						ProfileInfo requestorName=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {esr.getRequestorId()})	[0];
						if(requestorName!=null){
							rr.setRaisedBy(esr.getRequestorId());
							rr.setRaisedByName(requestorName.getFirstName());
						}
						ProfileInfo interviewerName=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {rr.getInterviewer()})	[0];
						if(requestorName!=null){
							rr.setInterviewerName(interviewerName.getFirstName());
						}
						if( rr.getAssignedTo()>0){
						ProfileInfo assignToName=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {rr.getAssignedTo()})	[0];
						if(assignToName!=null){
							rr.setAssignedToName(assignToName.getFirstName());
						}
						}else{
							rr.setAssignedToName("");
						}
						// Find login user id TAT 
						int isTatuser=0;
						String sql="SELECT P.*,U.ID as user_id FROM PROFILE_INFO P join USERS U  ON U.PROFILE_ID=P.ID join LEVELS L ON U.LEVEL_ID=L.ID JOIN DIVISON D ON L.DIVISION_ID=D.ID where U.STATUS=0 AND D.ID=?";
						ProfileInfo[] list=profileDao.findByDynamicSelect(sql, new Object[] { 12 });
						for(ProfileInfo eachProfile:list){
							Users[] u=UsersDaoFactory.create().findWhereProfileIdEquals(eachProfile.getId());
							for(Users userTat:u){
								if(userTat.getId()==login.getUserId()){
									isTatuser=1;
								}
							}
						}
						rr.setIsTatuser(isTatuser);
						String d = new SimpleDateFormat("dd-MM-yyyy").format(rr.getCreateDate());
						rr.setCreateDate1( d);
						}	
						request.setAttribute("actionForm", rr);
						
					} catch (Exception ex){
						logger.error("RESOURCE REQ RECEIVE : failed to fetch data for userId=" + login.getUserId(), ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
					break;
				}
				case RECEIVEALLRESOURCESTATUS:{
					try{
						ResourceStatus[] status=statusDao.findAll();
						dropdown.setDropDown(status);
						request.setAttribute("actionForm", dropdown);
					} catch (Exception ex){
						logger.error("RESOURCE RECEIVEALLRESOURCESTATUS : failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
				}
					break;
				case RECEIVE_ALL_EMPLOYMENT_TYPES:{
					try{
						DropDownBean[] downBeans=resReqDao.getEmploymentTypes();
						dropdown.setDropDown(downBeans);
						request.setAttribute("actionForm", dropdown);
					} catch (Exception ex){
						logger.error("RESOURCE RECEIVE_ALL_EMPLOYMENT_TYPES : failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
				}
					break;
				case RECEIVE_ALL_MAIN_STATUS:{
					try{
						DropDownBean[] downBeans=statusDao.getResourcesMainStatus();
						dropdown.setDropDown(downBeans);
						request.setAttribute("actionForm", dropdown);
					} catch (Exception ex){
						logger.error("RESOURCE RECEIVE_ALL_MAIN_STATUS : failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
				}
					break;
				case RECEIVEALLTATASSIGNEES:{
					try{
						
						String sql="SELECT P.*,U.ID as user_id FROM PROFILE_INFO P join USERS U  ON U.PROFILE_ID=P.ID join LEVELS L ON U.LEVEL_ID=L.ID JOIN DIVISON D ON L.DIVISION_ID=D.ID where D.ID=?";
						ProfileInfo[] list=profileDao.findByDynamicSelect(sql, new Object[] { 12 });
						List<ProfileInfo> profileList=new ArrayList<ProfileInfo>();
						List<ProfileListBean> profileBean=new ArrayList<ProfileListBean>();
						ProfileListBean bean=null;
						Map<String,String> map=new HashMap<String, String>();
						for(ProfileInfo eachProfile:list){
							Users[] u=UsersDaoFactory.create().findWhereProfileIdEquals(eachProfile.getId());
							if(u.length>0){
								if(u[0].getStatus()==0){
								bean=new ProfileListBean();
								bean.setFirstName(eachProfile.getFirstName());
								bean.setLastName(eachProfile.getLastName());
								
									bean.setUserId(u[0].getId());
									profileBean.add(bean);
								}
							}
						}
						ProfileListBean[] resourceRequirements = new ProfileListBean[profileBean.size()];
						new ArrayList<ProfileListBean>(profileBean).toArray(resourceRequirements);
						dropdown.setDropDown(resourceRequirements);
						
						request.setAttribute("actionForm", dropdown);
					} catch (Exception ex){
						logger.error("RESOURCE RECEIVEALLRESOURCESTATUS: failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
				}
					break;
				case RECEIVEPROPOSEDCANDIDATES:{
					try{
						ResourceReqMapping mapReq=null;
						ReqResMap[] reqMap=null;
						List<ResourceReqMapping> reqList=new ArrayList<ResourceReqMapping>();
						ResourceRequirement rr=resReqDao.findByPrimaryKey(resForm.getId());
						if(rr!=null){
						EmpSerReqMap esrMap=esrMapDao.findByPrimaryKey(rr.getEsrMapId());	
						reqMap=dao.findByReqId(resForm.getId());
						for(ReqResMap eachReq:reqMap){
						mapReq=mapDao.findByPrimaryKey(eachReq.getResourceId());
						ResourceStatus resStatus1=statusDao.findByPrimaryKey(eachReq.getStatusId());
						if(resStatus1!=null){
							mapReq.setStatus(resStatus1.getName());	
							mapReq.setStatusId(eachReq.getStatusId());
						}
						/*	ProfileInfo assignedToName=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {eachReq.getLastUpdatedBy()})	[0];
						if(assignedToName!=null){
							eachReq.setLastUpdatedByName(assignedToName.getFirstName());
						}*/
				        if(esrMap!=null){
				        	mapReq.setReqTypeId(esrMap.getReqId());
				        }
				        mapReq.setReqId(eachReq.getReqId());
				        mapReq.setClosed(eachReq.getClosed());
				        if(mapReq.getClosed()==2){
				        	ReqResMap[] resourceRequirements=dao.findByDynamicWhere("RESOURCE_ID=? AND CLOSED=1",  new Object[] {mapReq.getId()});
				        	if(resourceRequirements!=null && resourceRequirements.length==1){
				        		ResourceRequirement rrTemp=resReqDao.findByPrimaryKey(resourceRequirements[0].getReqId());
				        		EmpSerReqMap esrMapTemp=esrMapDao.findByPrimaryKey(rrTemp.getEsrMapId());
				        		mapReq.setClosedReqTypeId(esrMapTemp.getReqId());
				        	}
				        }else{
				        	mapReq.setClosedReqTypeId("0");
				        }
				        reqList.add(mapReq);
						}
						
						}	
						ResourceReqMapping[] resourceReq = new ResourceReqMapping[reqList.size()];
						new ArrayList<ResourceReqMapping>(reqList).toArray(resourceReq);
						dropdown.setDropDown(resourceReq);
						request.setAttribute("actionForm", dropdown);
					} catch (Exception ex){
						logger.error("RESOURCE RECEIVEPROPOSEDCANDIDATES: failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
				}
					break;
				case RECEIVESINGLECANDIDATES:{
					try{
						ResourceReqMapping rr=mapDao.findByPrimaryKey(resForm.getId());
						int reqId=Integer.parseInt(resForm.getReqId());
						
						
						if(rr!=null){
							
							if(reqId>0){
							ResourceRequirement newReq=resReqDao.findByPrimaryKey(reqId);
							EmpSerReqMap esr = esrDao.findByPrimaryKey(newReq.getEsrMapId());
							if(esr!=null){
						    	rr.setReqTypeId(esr.getReqId());
						    }
 	
							ReqResMap[] resourceRequirements=dao.findByDynamicWhere("REQ_ID=? AND RESOURCE_ID=?",  new Object[] {reqId, rr.getId()});	
						    if(resourceRequirements!=null && resourceRequirements.length==1){
						    	rr.setStatusId(resourceRequirements[0].getStatusId());
						    	ResourceStatus resStatus1=statusDao.findByPrimaryKey(rr.getStatusId());
								if(resStatus1!=null){
									rr.setStatus(resStatus1.getName());	
								} 
						    }
							}
							
							DocumentsDao documentsDao = DocumentsDaoFactory.create();
							Documents docNew =null;
							docNew = documentsDao.findByPrimaryKey(rr.getAttachmentId());
							if(docNew!=null){
								rr.setFileDescription(docNew.getDescriptions());
							}
						// Add History Details
						ResourceReqMapHistory[] hist=historyDao.findByMapId(rr.getId());
						List<ResourceReqMapHistory> histList=new ArrayList<ResourceReqMapHistory>();
						if(hist!=null && hist.length>0){
						for(ResourceReqMapHistory eachHist:hist){	
						ProfileInfo modifiedByName=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {eachHist.getModifiedBy()})	[0];
						if(modifiedByName!=null){
							eachHist.setModifiedByName(modifiedByName.getFirstName());
						}
						histList.add(eachHist);
						}
						}
						ResourceReqMapHistory[] histTemp = new ResourceReqMapHistory[histList.size()];
						new ArrayList<ResourceReqMapHistory>(histList).toArray(histTemp);
						rr.setMapHistory(histTemp);
						}
						request.setAttribute("actionForm", rr);
					} catch (Exception ex){
						logger.error("RESOURCE RECEIVEPROPOSEDCANDIDATES: failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
				}
					break;
				case RECEIVEALLRESOURCES:{
					try{
				//		ResourceReqMapping[] rr=mapDao.findAll();	
						ResourceReqMapping[] rr=mapDao.findByDynamicWhere("IS_SELECTED=0", null);
						dropdown.setDropDown(rr);
						request.setAttribute("actionForm", dropdown);
					} catch (Exception ex){
						ex.printStackTrace();
						logger.error("RESOURCE RECEIVEALLRESOURCES: failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
				}
					break;
				case RECEIVEALLSELECTEDRESOURCES:{
					try{
						List<ResourceReqMapping> list=new ArrayList<ResourceReqMapping>();
						ResourceReqMapping[] rr=mapDao.findByDynamicWhere("IS_SELECTED=1", null);
						for(ResourceReqMapping eachRr:rr){
							ReqResMap[] mapReq1=dao.findByDynamicWhere("RESOURCE_ID=? AND CLOSED=1", new Object[] {eachRr.getId()});
							if(mapReq1!=null && mapReq1.length==1){
								ResourceRequirement rReq=resReqDao.findByPrimaryKey(mapReq1[0].getReqId());
								EmpSerReqMap esr = esrDao.findByPrimaryKey(rReq.getEsrMapId());
								eachRr.setReqTypeId(esr.getReqId());
							}
							list.add(eachRr);
						}
						ResourceReqMapping[] resourceRequirements = new ResourceReqMapping[list.size()];
						new ArrayList<ResourceReqMapping>(list).toArray(resourceRequirements);
						dropdown.setDropDown(resourceRequirements);
				//		dropdown.setDropDown(rr);
						request.setAttribute("actionForm", dropdown);
					} catch (Exception ex){
						ex.printStackTrace();
						logger.error("RESOURCE RECEIVEALLRESOURCES: failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
				}
					break;	
				case RECEIVEALLREQUESTIDS:{
					try{
						ResourceRequirement[] rr=resReqDao.findByDynamicWhere("MAIN_STATUS_ID!=18", null);
				        List<ResourceRequirement> list=new ArrayList<ResourceRequirement>();
						for(ResourceRequirement eachReq:rr){
						 EmpSerReqMap esr = esrDao.findByPrimaryKey(eachReq.getEsrMapId());
						 eachReq.setReqTypeId(esr.getReqId());
							ProfileInfo requestorName=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {esr.getRequestorId()})	[0];
							if(requestorName!=null){
								eachReq.setRaisedByName(requestorName.getFirstName());
							}
						 list.add(eachReq);
						}
						ResourceRequirement[] resourceRequirements = new ResourceRequirement[list.size()];
						new ArrayList<ResourceRequirement>(list).toArray(resourceRequirements);
						dropdown.setDropDown(resourceRequirements);
						request.setAttribute("actionForm", dropdown);
					} catch (Exception ex){
						ex.printStackTrace();
						logger.error("RESOURCE RECEIVEPROPOSEDCANDIDATES: failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
				}
					break;	
			}
		} catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
		} finally{
			ResourceManager.close(conn);
		}
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		

		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
		logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access perdiem reconciliation receive Urls without having permisson at :" + new Date());
		return result;
		}

		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			ResourceRequirementDao resReqDao=ResourceRequirementDaoFactory.create(conn);
			ResourceReqMappingDao mapDao=ResourceReqMappingDaoFactory.create(conn);
			ResourceReqMapHistoryDao historyDao=ResourceReqMapHistoryDaoFactory.create(conn);
			ResourceStatusDao statusDao=ResourceStatusDaoFactory.create(conn);
			ProfileInfoDao profileDao=ProfileInfoDaoFactory.create();
			EmpSerReqMapDao esrMapDao=EmpSerReqMapDaoFactory.create();
			ResourceRequirement req=null;
			ResourceRequirementPk resPk=new ResourceRequirementPk();
			ResourceReqMappingPk mapPk=new ResourceReqMappingPk();
			ReqResMapDao reqResMapDao=ReqResMapDaoFactory.create();
		    ReqResMapPk  reqResMapPk=new ReqResMapPk();
			
			ResourceRequirement resForm = (ResourceRequirement) form;
			switch (UpdateTypes.getValue(form.getuType())) {
			case EDIT:
				req=resReqDao.findByPrimaryKey(resForm.getId());
				if(req!=null){
				resPk.setId(req.getId());
				resForm.setEsrMapId(req.getEsrMapId());
				resForm.setCreateDate(req.getCreateDate());
				resReqDao.update(resPk, resForm);
				}
				break;
			case UPDATE:
				int reqId=Integer.parseInt(resForm.getReqId());
				ResourceReqMapping rr=mapDao.findByPrimaryKey(resForm.getId());
				rr=populateDto(resForm,rr);
				rr.setLastUpdatedBy(login.getUserId());
				mapPk.setId(resForm.getId());
				// If Candidate Status is closed 
		  if(resForm.getStatusId()==STATUS_ID){
					rr.setIsSelected(1);
					mapDao.update(mapPk, rr);
					
					// Validating The number of Resources Already Selected for that Request
					ResourceRequirement tempReq = resReqDao.findByPrimaryKey(reqId);
					int totalPosition=tempReq.getNoOfPosition();
					int totalSelected=0;
					ReqResMap[] mapReq1=reqResMapDao.findByDynamicWhere("REQ_ID=? AND CLOSED=1", new Object[] {reqId});
					if(mapReq1!=null && mapReq1.length>0){
						totalSelected=mapReq1.length;
					}
					
					if(totalPosition<=totalSelected){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("resourcereq.invalid.positionFilled"));
						return result;
					}
					// If Total Position is greater than number of selected than Select this resource for that particular request
					else{
					
					ReqResMap[] reqResee=reqResMapDao.findByDynamicWhere("REQ_ID=? AND RESOURCE_ID=?",  new Object[] {reqId,resForm.getId()});
					if(reqResee!=null && reqResee.length>0){
						for(ReqResMap r:reqResee){
						reqResMapPk.setId(r.getId());
						r.setStatusId(resForm.getStatusId());
						r.setClosed(1);
						reqResMapDao.update(reqResMapPk, r);
						}
					}
					
					ReqResMap[] otherReqProposed=reqResMapDao.findByDynamicWhere("RESOURCE_ID=? AND CLOSED=0",  new Object[] {resForm.getId()});
					if(otherReqProposed!=null && otherReqProposed.length>0){
						for(ReqResMap r1:otherReqProposed){
						reqResMapPk.setId(r1.getId());
						r1.setClosed(2);
						reqResMapDao.update(reqResMapPk, r1);
						}
					}
					}
				  }
				//If Candidate Status is not closed than just update that Resource along with status for that Requirement
		 else{
					mapDao.update(mapPk, rr);
					ReqResMap[] reqMapping=reqResMapDao.findByDynamicWhere("REQ_ID=? AND RESOURCE_ID=?",  new Object[] {reqId,resForm.getId()});
					if(reqMapping!=null && reqMapping.length==1){
						for(ReqResMap r2:reqMapping){
						reqResMapPk.setId(r2.getId());
						r2.setStatusId(resForm.getStatusId());
						reqResMapDao.update(reqResMapPk, r2);
						}
					}
				}
				
				// Adding an entry in History table
				ResourceReqMapHistory history=new ResourceReqMapHistory();
				history=populateHistory(rr,history);
				history.setModifiedBy(login.getUserId());
				historyDao.insert(history);
				break;
				
			case UPDATERESOURCE:
				ResourceReqMapping resource=mapDao.findByPrimaryKey(resForm.getId());
				rr=populateDto(resForm,resource);
				rr.setLastUpdatedBy(login.getUserId());
				mapPk.setId(resForm.getId());
				mapDao.update(mapPk, resource);
				
				break;
			case PROCEED:
				ReqResMap reqRes=new ReqResMap();
			    reqRes.setReqId(Integer.parseInt(resForm.getReqId()));
				reqRes.setResourceId(resForm.getId());
				reqRes.setStatusId(1);
				reqRes.setClosed(0);
				ReqResMap[] reqResArr=reqResMapDao.findByDynamicWhere("REQ_ID=? AND RESOURCE_ID=?",  new Object[] {reqRes.getReqId(),reqRes.getResourceId()});
				if(reqResArr.length>0){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("resourcereq.invalid.candidateproceed"));
					return result;
				}
				reqResMapDao.insert(reqRes);
				// Sending mail
				ResourceRequirement mailReq=resReqDao.findByPrimaryKey(reqRes.getReqId());
				ResourceReqMapping mailResource=mapDao.findByPrimaryKey(reqRes.getResourceId());
				
				PortalMail mail=sendMail(mailResource,mailReq,login.getUserId(),resForm.getMailnotifiers());
			break;
			
			}
			
			logger.info("Resource Request with Id:"+resForm.getId()+" has been updated successfully");
		} catch (Exception e){
			e.printStackTrace();
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
		} finally{
			ResourceManager.close(conn);
		}
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		ResourceRequirement resForm = (ResourceRequirement) form;	
		ActionResult actionResult=new ActionResult();
		boolean projectName=false;
		boolean clientName=false;
		
		if( resForm.getReqTitle()==null || resForm.getReqTitle().trim().equalsIgnoreCase(""))
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.reqtitle"));
		
		if( resForm.getReqDetails() ==null || resForm.getReqDetails().trim().equalsIgnoreCase(""))
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.reqdetails"));
		
		if( resForm.getNoOfPosition()==0)
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.noofposition"));
		
		if( resForm.getProfitability()==null || resForm.getProfitability().trim().equalsIgnoreCase("") )
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.profitability"));
		
		if( resForm.getClosure() ==null || resForm.getClosure().trim().equalsIgnoreCase(""))
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.closure"));
		
		/*if( resForm.getAssignedTo()==0)
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.assignedto"));*/
		
		/*if( resForm.getMainStatusId() ==0 )
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.mainstatusid"));
		*/
		if(resForm.getReqDate()==null  )
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.reqdate"));
		
		if( resForm.getMandatorySkills()==null || resForm.getMandatorySkills().trim().equalsIgnoreCase("") )
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.mandatoryskills"));
		
		if( resForm.getAdditionalSkills()==null || resForm.getAdditionalSkills().trim().equalsIgnoreCase("") )
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.additionalskills"));
		
		if( resForm.getYearsOfExperience() ==null || resForm.getYearsOfExperience().trim().equalsIgnoreCase(""))
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.yearsofexperience"));
		
		if( resForm.getRelevantExperience()==null || resForm.getRelevantExperience().trim().equalsIgnoreCase("") )
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.relevantexperience"));
		
		if( resForm.getLocation() ==null || resForm.getLocation().trim().equalsIgnoreCase(""))
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.location"));
		
		if( resForm.getPositionName() ==null || resForm.getPositionName().trim().equalsIgnoreCase(""))
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.positionname"));
		
		if( resForm.getRequiredFor() ==null || resForm.getRequiredFor().trim().equalsIgnoreCase(""))
				actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.requiredfor"));
			else{
				String requiredFor=resForm.getRequiredFor();
					if(requiredFor.equalsIgnoreCase("Internal")){
						projectName=true;
					}else{
						clientName=true;
					}
			}
				
		if( clientName && (resForm.getClientName() ==null || resForm.getClientName().trim().equalsIgnoreCase("")))
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.clientname"));
		
		if( resForm.getInterviewer() ==null || resForm.getInterviewer().trim().equalsIgnoreCase(""))
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.interviewer"));
		
		if(projectName && (resForm.getProjectName() ==null || resForm.getProjectName().trim().equalsIgnoreCase("")))
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.projectname"));
		
		if( resForm.getEmploymentTypeId() ==0 )
			actionResult.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("resourcereq.invalid.EmploymentTypeId"));
		
		return  actionResult;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult defaultMethod(PortalForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	public PortalMail sendMailToAll(PortalMail portalMail,ProfileInfo[] prof,String mailNotifiers,String senderEmailId) {
		MailGenerator mailGenerator=new MailGenerator();
		List<String> ids=new ArrayList<String>();
		try {
		    for(ProfileInfo pro:prof){
		    	ids.add(pro.getOfficalEmailId());	
		    }
		    
			mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail);
			String[] mailIds = new String[ids.size()];
			new ArrayList<String>(ids).toArray(mailIds);
			portalMail.setAllReceipientMailId(mailIds);
			String[] ccMailIds=null;
			if(!mailNotifiers.equalsIgnoreCase("")){
				mailNotifiers=mailNotifiers+",";	
			   String ccMail=mailNotifiers.replaceAll(",", "@dikshatech.com;");
			   ccMail=ccMail+senderEmailId+";";
			   ccMailIds=ccMail.split(";");
			   portalMail.setAllReceipientcCMailId(ccMailIds);
			   
			}else{
				ccMailIds=new String[1];
				ccMailIds[0]=senderEmailId;
				portalMail.setAllReceipientcCMailId(ccMailIds);
			}
				mailGenerator.invoker(portalMail);
				logger.info("Mail has been sent successfully to " +mailIds);
				logger.info("CC Mail has been sent successfully to " +ccMailIds);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return portalMail;
	}
	private boolean validateResourceRequirementForm(ActionResult actionResult,ResourceRequirement resForm){
		if(resForm!=null){
			//if()
		}
		
		return false;
		
	}
	
	

	private ResourceReqMapHistory populateHistory(ResourceReqMapping resForm,ResourceReqMapHistory map) {
		if(resForm!=null){
		map.setResMapId(resForm.getId());
		map.setResourceName(resForm.getResourceName());
		map.setTotalExp(resForm.getTotalExp());
		map.setRelevantExp(resForm.getRelevantExp() );
		map.setCurrentEmployer(resForm.getCurrentEmployer());
		map.setCurrentRole(resForm.getCurrentRole());
		map.setCtc(resForm.getCtc());
		map.setEctc(resForm.getEctc());
		map.setNoticePeriod(resForm.getNoticePeriod()) ;
		map.setComments(resForm.getComments());
		}

		return map;
	}
	
	
	public PortalMail sendMail(ResourceReqMapping map,ResourceRequirement req,int loginId,String mailNotifiers) {
		EmpSerReqMapDao emp_Ser_Req_Map_Dao = EmpSerReqMapDaoFactory.create();
		ResourceRequirementDao resReqDao=ResourceRequirementDaoFactory.create();
		ResourceReqMappingDao   resMapDao=ResourceReqMappingDaoFactory.create();;
		ProfileInfoDao profileDao=ProfileInfoDaoFactory.create();
		MailGenerator mailGenerator=new MailGenerator();
		PortalMail portalMail = new PortalMail();
		try {
			
			// Sending mail each time when candidate proposed
			 EmpSerReqMap esr = emp_Ser_Req_Map_Dao.findByPrimaryKey(req.getEsrMapId());
			 ProfileInfo pf = profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {esr.getRequestorId()})	[0];
			ProfileInfo pf1=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {loginId})	[0];
			ProfileInfo interviewerName=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {Integer.parseInt(req.getInterviewer())})	[0];

			portalMail.setReceiverName(pf.getFirstName());
			portalMail.setSenderName(pf1.getFirstName());
			portalMail.setMailSubject("New profile proposed for- Requirement Id: "+ esr.getReqId() +" by TAT "+pf1.getFirstName());
			portalMail.setTemplateName(MailSettings.RESOURCE_REQ_UPDATED);
	// Requirement Details		
			portalMail.setReqId(esr.getReqId());
			portalMail.setReqTitle(req.getReqTitle());
			portalMail.setNoOfPosition(req.getNoOfPosition());
			portalMail.setMandatorySkills(req.getMandatorySkills());
			portalMail.setYearsOfExperience(req.getYearsOfExperience());
			portalMail.setRelevantExperience(req.getRelevantExperience());
			portalMail.setInterviewer(interviewerName.getFirstName()+" "+interviewerName.getLastName());
			portalMail.setClientName(req.getClientName());
			portalMail.setProposedDate(new java.sql.Date(new Date().getTime()));
	//proposed Candidate details
			portalMail.setCandidateName(map.getResourceName());
			portalMail.setContactNo(map.getContactNo());
			portalMail.setTotalExp(map.getTotalExp());
			portalMail.setRelevantExp(map.getRelevantExp());
			portalMail.setCurrentCompExp(map.getCurrentCompExp());
			portalMail.setSkillSet(map.getSkillSet());
			portalMail.setCurrentEmployer(map.getCurrentEmployer());
			portalMail.setCurrentRole(map.getCurrentRole());
			portalMail.setCtc(map.getCtc());
			portalMail.setEctc(map.getEctc());
			portalMail.setOfferInHand(map.getOfferInHand());
			portalMail.setLeavingReason(map.getLeavingReason());
			portalMail.setNoticePeriod(map.getNoticePeriod());
			portalMail.setOptionForEarlyJoining(map.getOptionForEarlyJoining());
			portalMail.setConditionForEarlyJoining(map.getConditionForEarlyJoining());
			portalMail.setCurrentLocation(map.getCurrentLocation());
			portalMail.setLocConstraint(map.getLocConstraint());
			portalMail.setComments(map.getComments());
			portalMail.setRecipientMailId(pf.getOfficalEmailId());
			
			String[] ccMailIds=null;
			if(!mailNotifiers.equalsIgnoreCase("")){
				mailNotifiers=mailNotifiers+",";	
			   String ccMail=mailNotifiers.replaceAll(",", "@dikshatech.com;");
			   ccMailIds=ccMail.split(";");
			   portalMail.setAllReceipientcCMailId(ccMailIds);
			   
			}
			// Adding Resume attachment
			int attachId=map.getAttachmentId();
			if(attachId>0){
			String seprator = File.separator;
			String path = "Data" + seprator;
			path = PropertyLoader.getEnvVariable() + seprator + path;
			// Get filename from id
			PortalData portalData = new PortalData();
			path = portalData.getfolder(path);
			DocumentsDao documentsDao = DocumentsDaoFactory.create();
			Documents docNew = new Documents();
			try {
				
				docNew = documentsDao.findByPrimaryKey(attachId);
				Attachements attachements = new Attachements();
				attachements.setFileName(docNew.getFilename());
				attachements.setFilePath(path + seprator + docNew.getFilename());
				Attachements a[]=new Attachements[1];
				a[0]=attachements;
				portalMail.setFileSources(a);
			} catch (DocumentsDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
			
				mailGenerator.invoker(portalMail);
				logger.info("Mail has been sent successfully to " +portalMail.getAllReceipientMailId());
				logger.info("CC Mail has been sent successfully to " +portalMail.getAllReceipientcCMailId());
				} catch (ProfileInfoDaoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
		    } catch (EmpSerReqMapDaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		   
		    } catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return portalMail;
	}
	
	
	
	@Override
	public Integer[] upload(List<FileItem> fileItems, String docType, int id, HttpServletRequest request, String description) {
		boolean isUpload = true;
		Integer fieldsId[] = new Integer[fileItems.size()];
		int i = 0;
		for (FileItem fileItem2 : fileItems){
			logger.info("Inside candidate model.");
			logger.info("FileName: " + fileItem2.getName());
			logger.info("FileType: " + fileItem2.getContentType());
			logger.info("FileSize: " + fileItem2.getSize());
			String[] temp = fileItem2.getName().split("\\.");
			logger.info("FileExtension: " + temp[temp.length - 1]);
			PortalData portalData = new PortalData();
			DocumentTypes dTypes = DocumentTypes.valueOf(docType);
			try{
				String fileName = portalData.saveFile(fileItem2, dTypes, id);
				String DBFilename = fileName;
				if (fileName != null){
					/**
					 * save fileName in dataBase
					 */
					Documents documents = new Documents();
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
					documents.setFilename(DBFilename);
					documents.setDoctype(docType);
					documents.setDescriptions(description);
					try{
						DocumentsPk documentsPk = documentsDao.insert(documents);
						fieldsId[i] = documentsPk.getId();
					} catch (DocumentsDaoException e){
						e.printStackTrace();
					} 
					i++;
				} else{
					isUpload = false;
				}
			} catch (FileNotFoundException e){
				isUpload = false;
				e.printStackTrace();
			}
		}
		if (isUpload){
			logger.info("File uploaded successfully.");
		} else{
			logger.info("File uploaded failed.");
		}
		// request.getSession(false).setAttribute("fileId",fieldsId);
		return fieldsId;
	}

	public Attachements download(PortalForm form) {
		Attachements attachements = new Attachements();
		ResourceRequirement req = (ResourceRequirement) form;
		String seprator = File.separator;
		String path = "Data" + seprator;
		path = PropertyLoader.getEnvVariable() + seprator + path;
		// Get filename from id
		PortalData portalData = new PortalData();
		path = portalData.getfolder(path);
		switch (DownloadTypes.getValue(form.getdType())) {
			case RESOURCERESUME:
				try{
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
					Documents docNew = new Documents();
					docNew = documentsDao.findByPrimaryKey(req.getAttachmentId());
					attachements.setFileName(docNew.getFilename());
					attachements.setFilePath(path + seprator + docNew.getFilename());
				} catch (Exception e){
					e.printStackTrace();
				}
				break;
		}
		return attachements;
	}


}

