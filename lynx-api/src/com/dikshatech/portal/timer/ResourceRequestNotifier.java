package com.dikshatech.portal.timer;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.ResourceReqMappingDao;
import com.dikshatech.portal.dao.ResourceRequirementDao;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ProfileInfoPk;
import com.dikshatech.portal.dto.ResourceReqMapping;
import com.dikshatech.portal.dto.ResourceRequirement;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.ResourceReqMappingDaoFactory;
import com.dikshatech.portal.factory.ResourceRequirementDaoFactory;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class ResourceRequestNotifier  implements Job{

	private static Logger	logger	= Logger.getLogger(ResourceRequestNotifier.class);

    
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		Connection conn = null;
		EmpSerReqMapDao esrMapDao=EmpSerReqMapDaoFactory.create();
		ProfileInfoDao profileDao=ProfileInfoDaoFactory.create();
		ResourceReqMappingDao mapDao=ResourceReqMappingDaoFactory.create(conn);
		ResourceRequirementDao resReqDao=ResourceRequirementDaoFactory.create(conn);
		PortalMail portalMail=new PortalMail();
		List<String> list=new ArrayList<String>();
		try{
			logger.info("ResourceRequestNotifier execute method invoked.");
			ResourceRequirement[] resourceRequirements=resReqDao.findByDynamicWhere("REQUIREMENT_DATE< ? AND MAIN_STATUS_ID IN(1,2)", new Object[]{dateFormat.format(new Date())});
			for (ResourceRequirement eachResourceReq : resourceRequirements){
				ResourceRequirement tempReq = resReqDao.findByPrimaryKey(eachResourceReq.getId());
				EmpSerReqMap esr=esrMapDao.findByPrimaryKey(tempReq.getEsrMapId());
				ProfileInfo requestorInfo=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {esr.getRequestorId()})	[0];
				if(requestorInfo!=null){
					logger.debug("Requestor MailID"+ requestorInfo.getOfficalEmailId());
					list.add(requestorInfo.getOfficalEmailId());
				}
				ResourceReqMapping[] resourceList = mapDao.findByReqId(eachResourceReq.getId());
				for(ResourceReqMapping resourceReqMapping:resourceList){
					ProfileInfo assignedToInfo=profileDao.findByDynamicSelect("Select P.* from PROFILE_INFO P join USERS U ON P.ID=U.PROFILE_ID where U.ID=?", new Object[] {resourceReqMapping.getAssignedTo()})	[0];
					if(assignedToInfo!=null){
						String assignedToEmailId= assignedToInfo.getOfficalEmailId();
						list.add(assignedToEmailId);
						logger.debug("assignedToEmailId "+ assignedToEmailId);
					}
				}
				
				portalMail.setReqTitle(eachResourceReq.getReqTitle());
				portalMail.setReqDetails(eachResourceReq.getReqDetails());
				portalMail.setNoOfPosition(eachResourceReq.getNoOfPosition());
				portalMail.setProfitability(eachResourceReq.getProfitability());
				portalMail.setAssignedTo(eachResourceReq.getAssignedTo());
				portalMail.setMandatorySkills(eachResourceReq.getMandatorySkills());
				portalMail.setAdditionalSkills(eachResourceReq.getAdditionalSkills());
				portalMail.setYearsOfExperience(eachResourceReq.getYearsOfExperience());
				portalMail.setRelevantExperience(eachResourceReq.getRelevantExperience());
				portalMail.setLocation(eachResourceReq.getLocation());
				portalMail.setPositionName(eachResourceReq.getPositionName());
				portalMail.setRequiredFor(eachResourceReq.getRequiredFor());
				portalMail.setInterviewer(eachResourceReq.getInterviewer());
				portalMail.setAllReceipientMailId(list.toArray(new String[list.size()]));
				portalMail.setMailSubject("Resource requirement : "+eachResourceReq.getId()+ " could not be serviced. ");
				portalMail.setTemplateName(MailSettings.RESOURCE_REQ_EXPIRED);
				portalMail.setReqDate(eachResourceReq.getReqDate());
				new MailGenerator().invoker(portalMail);
			}
			
		
			
		} catch (Exception e){
			logger.error("error occurred while executing the ResourceRequestNotifier handler");
			e.printStackTrace();
		}
		//move old request details to backup table...
	}
	
}

 
