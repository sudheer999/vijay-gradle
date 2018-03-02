package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import com.dikshatech.beans.ResumeManagementBean;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.ResumeManagementDao;
import com.dikshatech.portal.dao.ResumeSkillSetDao;
import com.dikshatech.portal.dao.UsStateDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.DocumentsPk;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ResumeManagement;
import com.dikshatech.portal.dto.ResumeManagementPk;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.DocumentsDaoException;
import com.dikshatech.portal.exceptions.ResumeManagementDaoException;
import com.dikshatech.portal.exceptions.ResumeSkillSetDaoException;
import com.dikshatech.portal.exceptions.UsStateDaoException;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.ResumeManagementDaoFactory;
import com.dikshatech.portal.factory.ResumeSkillSetDaoFactory;
import com.dikshatech.portal.factory.UsStateDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class ResumeManagementModel extends ActionMethods {

	
	private static Logger	logger		= LoggerUtil.getLogger(ResumeManagementModel.class);
	private static ResumeManagementModel	resumeManagementModel	= null;
	private ResumeManagementModel() {}

	public static ResumeManagementModel getInstance() {
		
		if (resumeManagementModel == null) resumeManagementModel = new ResumeManagementModel();
		return resumeManagementModel;
		
	}
	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		
		ResumeManagementDao resumeDao =ResumeManagementDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();		
		Login login = Login.getLogin(request);		
		ActionResult result = new ActionResult();
		MailGenerator mailGenerator = new MailGenerator();
		PortalMail pMail = new PortalMail();		
		try {
			ResumeManagement resumeDto = (ResumeManagement) form;
			resumeDto.setRegionId(login.getUserLogin().getRegionId());
			ResumeManagementPk pk = new ResumeManagementPk();
			resumeDto.setUserId(login.getUserId() );
			Users loginUser = usersDao.findByPrimaryKey(login.getUserId());			
			ProfileInfo loginUserProfile = profileInfoDao.findByPrimaryKey(loginUser.getProfileId());
			Users rmUser = usersDao.findByPrimaryKey(loginUserProfile.getReportingMgr());
			ProfileInfo rmUserProfile = profileInfoDao.findByPrimaryKey(rmUser.getProfileId());
			pMail.setMailSubject("New Resume Created ");
			pMail.setOnDate(PortalUtility.formatDateddMMyyyy(new Date()));
			pMail.setFromMailId(loginUserProfile.getOfficalEmailId());
			pMail.setCandidateName(resumeDto.getCandidateFirstName() +" "+resumeDto.getCandidateLastName());
			pMail.setEmployeeName(loginUserProfile.getFirstName()+" "+loginUserProfile.getLastName());
			pMail.setRecipientMailId(rmUserProfile.getOfficalEmailId());
			pk = resumeDao.insert(resumeDto);
			pMail.setTemplateName(MailSettings.RESUMEMANAGEMENT_NEW);	
			mailGenerator.invoker(pMail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		
		ActionResult result = new ActionResult();
		DropDown dropDown = new DropDown();
		ResumeManagementDao resumeDao =ResumeManagementDaoFactory.create();
		ResumeManagementBean[] resumeManageBean = null;
		Login login = Login.getLogin(request);
		int userid=login.getUserId();
		ResumeManagement resumeDto;
		ResumeManagement resumeManagementAll[] = null;
		int i = 0;
		try {
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
			case RECEIVEALL:		
				
				Map<Integer,Integer> idRecruitIdMap = resumeDao.getIdRecruitIdMap();				
				
				List<Integer> removeRecruitIds = new ArrayList<Integer>();
				ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
				UsersDao usersDao = UsersDaoFactory.create();
				/*
				 * This may not be required
				 * int loggedInRegionId = login.getUserLogin().getRegionId();
				 * LevelsDao levelsDao = LevelsDaoFactory.create();
				 * DivisonDao divisonDao = DivisonDaoFactory.create();
				*/
				
				//if the logged-in person belongs to Business Development...he should be able to see all requests in his/her region
				if(login.getUserLogin().getDivisionId() == 4){
					resumeManagementAll = resumeDao.findWhereRegionIdEquals(login.getUserLogin().getRegionId());
					if(resumeManagementAll!=null && resumeManagementAll.length>0){
						resumeManageBean = new ResumeManagementBean[resumeManagementAll.length];
						i=0;
						for (ResumeManagement resumeManagementSingle : resumeManagementAll) {
							ResumeManagementBean resumeManagementBeanSingle = DtoToBeanConverter.DtoToBeanConverter(resumeManagementSingle);
							resumeManageBean[i] = resumeManagementBeanSingle;
							i++;
						}
					}					
					dropDown.setDropDown(resumeManageBean);
					request.setAttribute("actionForm", dropDown);
				}else{
					try{
						
						for(Entry<Integer, Integer> eachEntry:idRecruitIdMap.entrySet()){
							/*
							 * find all the recruitIds for whom "logged-in" person is the R.M
							 */
							int eachRecruitId = eachEntry.getValue();
							if(eachRecruitId==login.getUserId()){
								//logged-in person must see his/her own requests
								continue;
							}
							ProfileInfo recruitInfo = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(eachRecruitId).getProfileId());
							/*
							 * this may not be required
							 * Integer recruitRegionId = divisonDao.findByPrimaryKey(levelsDao.findByPrimaryKey(recruitInfo.getLevelId()).getDivisionId()).getRegionId();
							 */
							if(recruitInfo.getReportingMgr() != login.getUserId()){
								removeRecruitIds.add(eachEntry.getKey());
							}	
						}
						
						//remove the id-recruitIds (from the map) for whom the loggedIn person is not the R.M(if any) & belongs to different region
						for(Iterator<Integer>iter=removeRecruitIds.iterator();iter.hasNext();){
							idRecruitIdMap.remove(iter.next());
						}
						removeRecruitIds.clear();

						if(idRecruitIdMap.size()>0){
							String ids = idRecruitIdMap.keySet().toString().replace('[', ' ').replace(']', ' ').trim();
							resumeManagementAll = resumeDao.findByDynamicWhere(" ID IN("+ids+")", null);
							resumeManageBean = new ResumeManagementBean[resumeManagementAll.length];
							i=0;
							for (ResumeManagement resumeManagementSingle : resumeManagementAll) {
								ResumeManagementBean resumeManagementBeanSingle = DtoToBeanConverter.DtoToBeanConverter(resumeManagementSingle);
								resumeManageBean[i] = resumeManagementBeanSingle;
								i++;
							}
							dropDown.setDropDown(resumeManageBean);
							request.setAttribute("actionForm", dropDown);
						}else{
							dropDown.setDropDown(resumeManageBean);
							request.setAttribute("actionForm", dropDown);
						}
						
					}catch(Exception ex){
						logger.error("ERROR...ResumeManagement RECEIVEALL could not be processed.....");
					}
				}
				
				
				
				
				/*if(login.getUserLogin().getDivisionId()==23 && login.getUserLogin().getRegionId()==1){
					resumeManagementAll=resumeDao.findAll();
				}else{
					resumeManagementAll = resumeDao.findByDynamicWhere("RECRUT_ID=?",new Object[]{userid});
				}*/
				
			break;
			case RECEIVE:
				resumeDto= (ResumeManagement) form;
				ResumeManagement resumeManagement= resumeDao.findByPrimaryKey(resumeDto.getId());
				if (resumeManagement.getResume() != null) {
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
					Documents documents;
					try {
						documents = documentsDao.findByPrimaryKey(Integer.parseInt(resumeManagement.getResume()));
						resumeManagement.setResumeDesc(documents.getDescriptions());
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DocumentsDaoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
				request.setAttribute("actionForm", resumeManagement);
		    break;
			case RESUMESEARCHRECEIVE:
				resumeDto = (ResumeManagement) form;
				StringBuffer skillquery= new StringBuffer();
				StringBuffer statesquery= new StringBuffer();
				String fName= new String();
				String lName= new String();
				String vType= new String();
				String reLocation= new String();
				String skillSetStr=new String();
				String statesStr=new String();				
				if(resumeDto.getSkillSet()!=null){
					skillSetStr=resumeDto.getSkillSet();
				}
				if(resumeDto.getSkillSet()!=null){
					 statesStr=resumeDto.getState();
				}
				StringBuffer query;
				if(login.getUserLogin().getDivisionId()==23){
					query = new StringBuffer("SELECT * FROM RESUME_MANAGEMENT WHERE IS_DELETE=0");
				}else{
					query = new StringBuffer("SELECT * FROM RESUME_MANAGEMENT WHERE IS_DELETE=0 AND RECRUT_ID="+userid);
				}
				if(resumeDto.getCandidateFirstName()!=null){
					fName=(" AND CANDIDATE_FIRST_NAME="+"'"+resumeDto.getCandidateFirstName()+"'");
				}				
				if(resumeDto.getCandidateLastName()!=null){
					lName=(" AND CANDIDATE_LAST_NAME="+"'"+resumeDto.getCandidateLastName()+"'");
				}				
				if(resumeDto.getvType()!=null){
					vType=(" AND VISA_TYPE="+"'"+resumeDto.getvType()+"'");
				}	
				if(resumeDto.getRelocation()==2){
					reLocation="";
				}else{
					reLocation=(" AND RELOCATION="+resumeDto.getRelocation());
				}				
				int s=0;
				if(skillSetStr!=null)
				for (String skill :skillSetStr.split(",")){
					if(s==0){
						skillquery.append(" AND SKILL_SET LIKE "+"'%"+skill+"%'");						
					}else{
						skillquery.append(" OR SKILL_SET LIKE "+"'%"+skill+"%'");	
					}
					s++;
				}				
				int k=0;
				if(statesStr!=null)
				for (String state :statesStr.split(",")){
					if(k==0){
						statesquery.append(" AND STATE LIKE "+"'%"+state+"%'");
					}else{
						statesquery.append(" OR STATE LIKE "+"'%"+state+"%'");
					}
					k++;
				}							
				if(skillquery.length()>0){
					query.append(skillquery);
				}
				if(statesquery.length()>0){
					query.append(statesquery);
				}
				if(fName.length()>0){
					query.append(fName);
				}
				if(lName.length()>0){
					query.append(lName);
				}
				if(vType.length()>0){
					query.append(vType);
				}
				if(reLocation.length()>0){
					query.append(reLocation);
				}
				logger.trace("The Searching Query for Resumes :"+query);
				resumeManagementAll = resumeDao.findByDynamicSelect(query.toString(), null);
				resumeManageBean = new ResumeManagementBean[resumeManagementAll.length];
				i = 0;
				for (ResumeManagement resumeManagementSingle : resumeManagementAll) {
					ResumeManagementBean resumeManagementBeanSingle = DtoToBeanConverter
							.DtoToBeanConverter(resumeManagementSingle);
					resumeManageBean[i] = resumeManagementBeanSingle;
					i++;
				}
				dropDown.setDropDown(resumeManageBean);
				request.setAttribute("actionForm", dropDown);
			break;
			default:
			break;

			}// switch ends
		} catch (ResumeManagementDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {		
		ResumeManagementDao resumeDao =ResumeManagementDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();		
		ResumeManagement resumeDto = (ResumeManagement) form;
		ResumeManagementPk resumeManagementPk = new ResumeManagementPk();
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);		
		MailGenerator mailGenerator = new MailGenerator();
		PortalMail pMail = new PortalMail();
		try {			
			ResumeManagement existingResumeDto = resumeDao.findByPrimaryKey(resumeDto.getId());
			Users loginUser = usersDao.findByPrimaryKey(login.getUserId());	
			ProfileInfo loginUserProfile = profileInfoDao.findByPrimaryKey(loginUser.getProfileId());
			Users rmUser = usersDao.findByPrimaryKey(loginUserProfile.getReportingMgr());
			ProfileInfo rmUserProfile = profileInfoDao.findByPrimaryKey(rmUser.getProfileId());
			resumeDto.setUserId(existingResumeDto.getUserId() );			
			resumeManagementPk.setId(existingResumeDto.getId());			
			resumeDao.update(resumeManagementPk, resumeDto);
			pMail.setMailSubject("Resume Updated ");
			pMail.setOnDate(PortalUtility.formatDateddMMyyyy(new Date()));
			pMail.setFromMailId(loginUserProfile.getOfficalEmailId());
			pMail.setCandidateName(resumeDto.getCandidateFirstName() +" "+resumeDto.getCandidateLastName());
			pMail.setEmployeeName(loginUserProfile.getFirstName()+" "+loginUserProfile.getLastName());
			pMail.setRecipientMailId(rmUserProfile.getOfficalEmailId());
			resumeDto.setRecrutId(existingResumeDto.getRecrutId());//creator's id retained
			resumeDto.setRegionId(existingResumeDto.getRegionId());//region id retained
			resumeDao.update(resumeManagementPk,resumeDto);
			pMail.setTemplateName(MailSettings.RESUMEMANAGEMENT_NEW);	
			mailGenerator.invoker(pMail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;		
	}
	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		
		ResumeManagementDao resumeDao =ResumeManagementDaoFactory.create();
		ResumeManagement resumeDto = (ResumeManagement) form;
		ResumeManagementPk pk = new ResumeManagementPk();
		ActionResult result = new ActionResult();
		try {
			pk.setId(resumeDto.getId());			
			resumeDao.delete(pk);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;		
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		
		ActionResult result = null;	
		DropDown dropdown = (DropDown) form;		
		switch (ActionMethods.ExecuteTypes.getValue(form.geteType())) {
			case RESUMESKILLSET: 
			{			
				ResumeSkillSetDao resumeSkillSetDao = ResumeSkillSetDaoFactory.create();
				result = new ActionResult();
				try {
					dropdown.setDropDown(resumeSkillSetDao.findAll());
				} catch (ResumeSkillSetDaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
			}
			case USSTATES: 
			{			
				UsStateDao usStateDao = UsStateDaoFactory.create();
				result = new ActionResult();
				try {
					dropdown.setDropDown(usStateDao.findAll());
				} catch (UsStateDaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
			}
		}
		return result;
	}

	@Override
	public Integer[ ] upload(List<FileItem> fileItems, String docType,
			int id, HttpServletRequest request,String description)
	{
		boolean isUpload = true;
		Integer fieldsId[] = new Integer[fileItems.size()];
		int i = 0;
		for (FileItem fileItem2 : fileItems)
		{
			logger.info("FileName: " + fileItem2.getName());
			PortalData portalData = new PortalData();
			DocumentTypes dTypes = DocumentTypes.valueOf(docType);
			try
			{
				String fileName = portalData.saveFile(fileItem2, dTypes, id);
				String DBFilename = fileName;
				logger.info("filename" + fileName);
				if (fileName != null)
				{
					Documents documents = new Documents();
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
					documents.setFilename(DBFilename);
					documents.setDoctype(docType);
					documents.setDescriptions(description);
					logger.info("The file " + fileName
							+ " successfully uploaded");
					try
					{
						DocumentsPk documentsPk = documentsDao
								.insert(documents);
						fieldsId[i] = documentsPk.getId();
					} catch (DocumentsDaoException e)
					{
						e.printStackTrace();
					}

					i++;
				}
				else
				{
					throw new FileNotFoundException();
				}

			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		if (isUpload)
		{
			logger.info("File uploaded successfully.");
		}
		else
		{
			logger.info("File uploaded failed.");
		}
		return fieldsId;
	}
	@Override
	public Attachements download(PortalForm form)
	{
		Attachements attachements = new Attachements();
		String seprator = File.separator;
		String path = "Data" + seprator;
		try
		{
			ResumeManagement rm = (ResumeManagement) form;
			DocumentsDao documentsDao = DocumentsDaoFactory.create();
			Documents[ ] dacDocuments = documentsDao
					.findWhereIdEquals(Integer.parseInt(rm.getResume()));
			PortalData portalData = new PortalData();
			path = portalData.getDirPath();
			attachements.setFileName(dacDocuments[0].getFilename());

			path = portalData.getfolder(path);
			path = path + seprator + attachements.getFileName();
			attachements.setFilePath(path);
		} catch (DocumentsDaoException e)
		{
			// TODO Auto-generated catch block
			logger.info("failed to download check the file path:",e);
			e.printStackTrace();
		}
		return attachements;

	}
	@Override	
	public ActionResult defaultMethod(PortalForm form,
				HttpServletRequest request, HttpServletResponse response) {
			// TODO Auto-generated method stub
			return null;
		}
	
	}
