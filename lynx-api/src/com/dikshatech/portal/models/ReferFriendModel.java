package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.ReferFriendBean;
import com.dikshatech.beans.UserLogin;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.JobRequirementDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.ReferFriendDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.DocumentsPk;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.JobRequirement;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ReferFriend;
import com.dikshatech.portal.dto.ReferFriendPk;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.DocumentsDaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.ReferFriendDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.ReferFriendDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.factory.jobRequirementDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class ReferFriendModel extends ActionMethods
{

	private static Logger	logger	= LoggerUtil.getLogger(ItModel.class);

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
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
		ReferFriend referFrdReq = (ReferFriend) form;
		ReferFriendDao referFriendDao = ReferFriendDaoFactory.create();
		try
		{
			switch (ReceiveTypes.getValue(form.getrType()))
			{
				case RECEIVE:
				{
//					boolean accessProfiles=false;
//					ReferFriend refFrd = new ReferFriend();
//					String referFrdLevelIds = notification.referFriendLevelIds;
//					String levelIds[]=referFrdLevelIds.split(",");
//					if(levelIds.length>0){
//						for(String str : levelIds)
//						{
//							if(userLevel.getId()==Integer.parseInt(str))
//							{
//								accessProfiles=true;
//							}
//						}
//					}
//					refFrd.setAccessProfiles(accessProfiles);
//					request.setAttribute("actionForm", refFrd);
				}
				break;
				
				case RECEIVESINGLEPROFILE:
				{
					ReferFriend referFrd = referFriendDao.findByPrimaryKey(referFrdReq.getId());
										
					ReferFriend referFriendArr[]={referFrd};
					ArrayList<ReferFriendBean> referFriendBeanLst = this.getReferFriendBeanLst(referFriendArr);
					ReferFriendBean refFrdBean=null;
					if(referFriendBeanLst!=null && referFriendBeanLst.size()>0)
					{
						refFrdBean = referFriendBeanLst.get(0);
					}
					
					request.setAttribute("actionForm", refFrdBean);
				}
				break;
				
				case RECEIVEALL:
				{
					ReferFriend referFriendArr[] =  referFriendDao.findByDynamicWhere("IS_DELETED=0", new Object[]{});
					ArrayList<ReferFriendBean> referFriendBeanLst = getReferFriendBeanLst(referFriendArr);
					
					ReferFriend refFrdBean = new ReferFriend();
					refFrdBean.setReferFriendBeanLst(referFriendBeanLst);
					request.setAttribute("actionForm", refFrdBean);
  					break;
				}
				case RECIEVEALLREQUIREMENTS:
				{
					JobRequirement job=new JobRequirement();
					JobRequirement[] requirement=null;
					List<JobRequirement> list=new ArrayList<JobRequirement>();
					JobRequirementDao jobRequirementDao=jobRequirementDaoFactory.create();
					requirement=jobRequirementDao.findByDynamicSelect("SELECT ID, JOB_TITLE, JOB_DESCRIPTION, POSTED_BY, POSTED_ON, IS_ACTIVE, REQ_TAG_ID, EXPERIENCE, LOCATION, POSITION, SKILLS FROM JOB_REQUIREMENT", null);
				    if(requirement!=null){
				    	for(JobRequirement req:requirement){
				    		if(req.getIsActive()==1){
				    			list.add(req);
				    		}
				    	}
				   job.setJobReqList(list);
				    }
					request.setAttribute("actionForm", job);
  					break;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
		}
		return result;
	}
	
	private ArrayList<ReferFriendBean> getReferFriendBeanLst(ReferFriend referFriendArr[])
	{
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
		EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
		
		ArrayList<ReferFriendBean> referFriendBeanLst=new ArrayList<ReferFriendBean>();
		ReferFriendBean rfb=null;
		if(referFriendArr.length>0)
		{
			for(ReferFriend RF : referFriendArr)
			{
				try{
					rfb = new ReferFriendBean();
					rfb.setId(RF.getId());
					rfb.setEsrMapId(RF.getEsrMapId());
					rfb.setSummary(RF.getSummary());
					rfb.setAttachment(RF.getAttachment());
					rfb.setReferredTo(RF.getReferredTo());
					rfb.setDepartment(RF.getDepartment());
					rfb.setExperienceLavel(RF.getExperienceLavel());
					rfb.setSubmissionDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(RF.getCreateDate()));
					rfb.setReferredBy(RF.getReferredBy());
					rfb.setReqId(empSerReqDao.findByPrimaryKey(RF.getEsrMapId()).getReqId());
					
					ProfileInfo ReferrerProfile = profileDao.findByPrimaryKey(usersDao.findByPrimaryKey(RF.getReferredBy()).getProfileId());
					rfb.setReferredByName(ReferrerProfile.getFirstName()+" "+ReferrerProfile.getLastName());
					
					referFriendBeanLst.add(rfb);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return referFriendBeanLst;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		ReferFriend referFriendReq = (ReferFriend) form;
		ReferFriendDao referFrdReqtDao = ReferFriendDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		RegionsDao regionDao = RegionsDaoFactory.create();
		DivisonDao divisionDao = DivisonDaoFactory.create();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		EmpSerReqMapPk reqpk = new EmpSerReqMapPk();
		Users requestedUser = new Users();
		MailGenerator mailGenarator = new MailGenerator();
		
		try
		{
			Login loginDto = Login.getLogin(request);
			requestedUser = usersDao.findByPrimaryKey(loginDto.getUserId());
			ProfileInfo requesterProfile = profileInfoDao.findByPrimaryKey(requestedUser.getProfileId());
			Levels empLevel = levelsDao.findByPrimaryKey(requestedUser.getLevelId());
			Divison requesterDivision = divisionDao.findByPrimaryKey(empLevel.getDivisionId());
			String empDesignation=empLevel.getDesignation();
			String empDivision=requesterDivision.getName();
			String empDepartment=null;
			if(requesterDivision.getParentId()==0)
			{
				empDepartment=requesterDivision.getName();
			}
			else
			{
				empDepartment = divisionDao.findByPrimaryKey(requesterDivision.getParentId()).getName();
			}
						
			switch (ActionMethods.SaveTypes.getValue(form.getsType()))
			{
				case SAVE:
					try
					{
						Date date = new Date();
						if (requestedUser != null)
						{
							int reg_id = regionDao.findByDynamicSelect("SELECT * FROM REGIONS R LEFT JOIN DIVISON D ON D.REGION_ID=R.ID LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN PROFILE_INFO PI ON L.ID=PI.LEVEL_ID LEFT JOIN USERS U ON PI.ID=U.PROFILE_ID WHERE U.ID=?", new Object[ ]
							{ new Integer(loginDto.getUserId()) })[0].getId();
							
							String reg_abb = regionDao.findByPrimaryKey(reg_id).getRefAbbreviation();
														
							// save Data in EMP_SER_REQ_MAP TABLE
							int uniqueID = 1;
							EmpSerReqMap empMap[] = empSerReqDao.findByDynamicSelect("SELECT * FROM EMP_SER_REQ_MAP WHERE ID=(SELECT MAX(ID) FROM EMP_SER_REQ_MAP WHERE REQ_TYPE_ID=13)", null);
							if (empMap.length > 0)
							{
								String s = empMap[0].getReqId().split("-")[2];
								uniqueID = Integer.parseInt(s) + 1;
							}
							EmpSerReqMap empReqMapDto = new EmpSerReqMap();
							empReqMapDto.setSubDate(date);  
							empReqMapDto.setReqId(reg_abb + "-RF-" + uniqueID);
							empReqMapDto.setReqTypeId(13);
							empReqMapDto.setRegionId(reg_id);
							empReqMapDto.setRequestorId(loginDto.getUserId());
							empReqMapDto.setProcessChainId(0);
							reqpk = empSerReqDao.insert(empReqMapDto);
							// insert into REFER_FRIEND_REQUEST
							ReferFriend referFriendRequest = new ReferFriend();
							referFriendRequest.setEsrMapId(reqpk.getId());
							referFriendRequest.setSummary(referFriendReq.getSummary());
							referFriendRequest.setAttachment(referFriendReq.getAttachment());
							referFriendRequest.setReferredTo(referFriendReq.getReferredTo());
							referFriendRequest.setDepartment(referFriendReq.getDepartment());
							referFriendRequest.setExperienceLavel(referFriendReq.getExperienceLavel());
							referFriendRequest.setReferredBy(loginDto.getUserId());
							referFriendRequest.setCreateDate(new Date());
						    referFriendRequest.setJobReqId(referFriendReq.getJobReqId());
							
						
							ReferFriendPk rfpk = referFrdReqtDao.insert(referFriendRequest);
							
							/**
							 * Sending the mails
							 */
							ProfileInfo userprofileInfo = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(loginDto.getUserId()) })[0];
							
						PortalMail portalMail = sendMailDetails("Diksha Lynx: Profile refered By - " +userprofileInfo.getFirstName(),userprofileInfo.getFirstName(),MailSettings.REFER_FRIEND,referFriendReq,null,requestedUser.getEmpId(),request);
						}
					} catch (RegionsDaoException e)
					{
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.regions"));
						empSerReqDao.delete(reqpk);
						e.printStackTrace();
					} catch (ArrayIndexOutOfBoundsException e)
					{
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.arrayoutofbound"));
						empSerReqDao.delete(reqpk);
						e.printStackTrace();
					} catch (EmpSerReqMapDaoException e)
					{
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
						e.printStackTrace();
					} catch (ReferFriendDaoException e)
					{
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
						empSerReqDao.delete(reqpk);
						e.printStackTrace();
					} catch (Exception e)
					{
						e.printStackTrace();
						empSerReqDao.delete(reqpk);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
					}
				} // end of switch
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
			e.printStackTrace();
		} catch (EmpSerReqMapDaoException e)
		{
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
		}

		return result;
	}
	
	
	@Override
    public ActionResult update(PortalForm form, HttpServletRequest request)
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		ReferFriend referFrdReq = (ReferFriend) form;
		ActionResult result = new ActionResult();
		ReferFriendDao referFriendDao = ReferFriendDaoFactory.create();
		
		try
		{
			//Login loginDto = Login.getLogin(request);
			ReferFriend referFrd = referFriendDao.findByPrimaryKey(referFrdReq.getId());
			referFrd.setIsDeleted( (short)1 );
			referFriendDao.update(new ReferFriendPk(referFrd.getId()), referFrd);
			
		} catch (Exception e)
		{
			e.printStackTrace();
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.delete"));
		}
		return result;
		
	}
	

	@Override
	public Integer[] upload(List<FileItem> fileItems, String docType, int id,HttpServletRequest request, String description){
		boolean isUpload = true;
		Integer fieldsId[] = new Integer[fileItems.size()];
		int i = 0;
		for (FileItem fileItem2 : fileItems) {
			logger.error("FileName: " + fileItem2.getName());
			PortalData portalData = new PortalData();
			DocumentTypes dTypes = DocumentTypes.valueOf(docType);
			try {
				String fileName = portalData.saveFile(fileItem2, dTypes, id);
				String DBFilename = fileName;
				logger.info("filename" + fileName);
				if (fileName != null) {
					Documents documents = new Documents();
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
					documents.setFilename(DBFilename);
					documents.setDoctype(docType);
					documents.setDescriptions(description);
					logger.error("The file " + fileName	+ " successfully uploaded");
					try {
						DocumentsPk documentsPk = documentsDao.insert(documents);
						fieldsId[i] = documentsPk.getId();
					} catch (DocumentsDaoException e) {
						e.printStackTrace();
					}
					i++;
				} else {
					isUpload=false;
				}
			} catch (FileNotFoundException e) {
				logger.error("file not found", e);
				isUpload=false;
				e.printStackTrace();
			}
		}
		if (isUpload) {
			logger.error("File uploaded successfully.");
		} else {
			logger.error("File uploaded failed.");
		}
		return fieldsId;
	}

	
	
	@Override
	public Attachements download(PortalForm form) {
		Attachements attachements = new Attachements();
		String seprator = File.separator;
		String path = "Data" + seprator;
		try {
			ReferFriend referFrd = (ReferFriend) form;
			DocumentsDao documentsDao = DocumentsDaoFactory.create();
			Documents[] dacDocuments = documentsDao.findWhereIdEquals(referFrd.getAttachment());
			PortalData portalData = new PortalData();
			path = portalData.getDirPath();
			attachements.setFileName(dacDocuments[0].getFilename());
			path = portalData.getfolder(path);
			path = path + seprator + attachements.getFileName();
			attachements.setFilePath(path);
		} catch (DocumentsDaoException e) {
			// TODO Auto-generated catch block
			logger.error("failed to download check the file path:", e);
			e.printStackTrace();
		}
		return attachements;
	}
	

	@Override
    public ActionResult validate(PortalForm form, HttpServletRequest request)
    {
	    // TODO Auto-generated method stub
	    return null;
    }
	
	
	private PortalMail sendMailDetails(String subject,String loggedInUserName,String template,ReferFriend referFriend,  String[] ccMailIds,int loggedInEmpId, HttpServletRequest request) {
		return sendMailDetails(subject,loggedInUserName, template,referFriend,ccMailIds,loggedInEmpId, request, null);
	}
	


	/**
	 * @author gurunath.rokkam
	 * @return PortalMail
	 */
	private PortalMail sendMailDetails(String subject,String loggedInUserName,String template,ReferFriend referFriend, String[] ccMailIds,int loggedInEmpId, HttpServletRequest request ,String[] toMailIds) {
		PortalMail portalMail = new PortalMail();
		try{
			if (request != null){
				portalMail.setServerId(request.getRequestURL().toString().replaceAll(request.getServletPath(), ""));
			}
		/* List<String> mailIdTAG=UserModel.getUsersByDivision1(2);
			 List<String> mailIdRMG=UserModel.getUsersByDivision1(26);
			 String ids = Arrays.asList(mailIdTAG).toString().replace('[', ' ').replace(']', ' ').trim();
            String[] allIds=ids.split(",");*/
			
			//Hard coding mail Ids
			//String[] allIds={"shambhavi@dikshatech.com","spoorthi.m@dikshatech.com", "hajira.begum@dikshatech.com", "priyanka.s@dikshatech.com"};
			String[] allIds={"bhavani.n@dikshatech.com","asma.hassan@dikshatech.com", "hajira.begum@dikshatech.com", "priyanka.s@dikshatech.com"};
	//		String[] allIds={"vamsi.krishna@dikshatech.com"};

			if( referFriend.getAttachment() != 0) {
				String filePath = PropertyLoader.getEnvVariable() + File.separator + "Data" ;
				DocumentsDao documentsDao = DocumentsDaoFactory.create();
				Documents doc = documentsDao.findByPrimaryKey(referFriend.getAttachment());
				Attachements attachment = new Attachements();
				Attachements attachments[] = new Attachements[1];
				attachment.setFileName(doc.getFilename());
				attachment.setFilePath(filePath + File.separator+ doc.getFilename());
				attachments[0] = attachment;
				portalMail.setFileSources(attachments);
			}
			portalMail.setRefereeName(referFriend.getReferredTo());
			portalMail.setRefereeDept(referFriend.getDepartment());
			portalMail.setRefereeExpLevel(referFriend.getExperienceLavel());
			portalMail.setEmployeeId(loggedInEmpId);
			
			portalMail.setEmpId(String.valueOf(loggedInEmpId));
			//portalMail.setEmployeeName(loggedInUserName);
			Login loginDto = Login.getLogin(request);
			UserLogin userLogin = loginDto.getUserLogin();
			portalMail.setEmployeeName(userLogin.getUserName());
			portalMail.setEmpEmailId(userLogin.getOfficialEmaiID());
			portalMail.setMailSubject(subject);
			portalMail.setTemplateName(template);
		
			portalMail.setAllReceipientMailId(allIds);
			UsersDao usersDao = UsersDaoFactory.create();
			
			
			if( (portalMail.getRecipientMailId() != null && portalMail.getRecipientMailId().contains("@")) || (portalMail.getAllReceipientMailId() != null ))
			{
				MailGenerator mailGenarator = new MailGenerator();
				mailGenarator.invoker(portalMail);
			}
			
		
			 
			/* if(mailIdRMG!=null){
	
				 String mailids = Arrays.asList(mailIdRMG).toString().replace('[', ' ').replace(']', ' ').trim();
				
				    String[] rmgMailIds=mailids.split(",");
				 
			 
				if( referFriend.getAttachment() != 0) {
					String filePath = PropertyLoader.getEnvVariable() + File.separator + "Data" ;
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
					Documents doc = documentsDao.findByPrimaryKey(referFriend.getAttachment());
					Attachements attachment = new Attachements();
					Attachements attachments[] = new Attachements[1];

					attachment.setFileName(doc.getFilename());
					attachment.setFilePath(filePath + File.separator+ doc.getFilename());
					
					attachments[0] = attachment;
					portalMail.setFileSources(attachments);
				}
				portalMail.setRefereeName(referFriend.getReferredTo());
				portalMail.setRefereeDept(referFriend.getDepartment());
				portalMail.setRefereeExpLevel(referFriend.getExperienceLavel());
				portalMail.setEmployeeId(loggedInEmpId);
				
				portalMail.setEmpId(String.valueOf(loggedInEmpId));
				
				portalMail.setEmployeeName(loggedInUserName);
				portalMail.setMailSubject(subject);
				portalMail.setTemplateName(template);
				portalMail.setAllReceipientMailId(rmgMailIds);
			
				if( (portalMail.getRecipientMailId() != null && portalMail.getRecipientMailId().contains("@")) || (portalMail.getAllReceipientMailId() != null ))
				{
					MailGenerator mailGenarator = new MailGenerator();
					mailGenarator.invoker(portalMail);
				}
			 }*/
			return portalMail;
		} catch (Exception e){}
		return portalMail;
	}
	
}
