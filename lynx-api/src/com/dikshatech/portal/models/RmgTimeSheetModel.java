package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.common.utils.GenerateXls;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.RmgTimeSheetReconciliationNotifier;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.InvoiceReconciliationDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RmgTimeSheetDao;
import com.dikshatech.portal.dao.RmgTimeSheetReconDao;
import com.dikshatech.portal.dao.RmgTimeSheetReqDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Invoicedto;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.RmgTimeSheet;
import com.dikshatech.portal.dto.RmgTimeSheetPk;
import com.dikshatech.portal.dto.RmgTimeSheetReco;
import com.dikshatech.portal.dto.RmgTimeSheetRecoPk;
import com.dikshatech.portal.dto.RmgTimeSheetReq;
import com.dikshatech.portal.dto.RmgTimeSheetReqPk;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.RmgTimeSheetDaoException;
import com.dikshatech.portal.exceptions.RmgTimeSheetReqDaoException;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.InvoiceReconciliationDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RmgTimeSheetDaoFactory;
import com.dikshatech.portal.factory.RmgTimeSheetReconDaoFactory;
import com.dikshatech.portal.factory.RmgTimeSheetReqDaoFactory;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class RmgTimeSheetModel extends ActionMethods{
	private static Logger						logger				= LoggerUtil.getLogger(RmgTimeSheetModel.class);
	public static final int						MODULEID			= 83;
	private static final int BILLABLE     = 1;
	private static final int NON_BILLABLE = 2;
	private static final int SUPPORT      = 3;
	private static final int MAINTENANCE  = 4;

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request){
		return null;
	}

	public ActionResult receive(PortalForm form, HttpServletRequest request)
	{
		
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access TimeSheetreconciliation reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
		Connection conn = null;
		try{
		DropDown dropDown = new DropDown();
		RmgTimeSheetDao timeSheet=RmgTimeSheetDaoFactory.create(); 
		RmgTimeSheetReconDao rmgTimeSheetReconDao=RmgTimeSheetReconDaoFactory.create();
		
		switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
		{
		
		case RECEIVEREPORT:{
			
			
			//RmgTimeSheetDao regionsDao = RmgTimeSheetDaoFactory.create();
			RmgTimeSheetReco report = (RmgTimeSheetReco) form;
			RmgTimeSheet[] timeSheetReco=null;
			try {
				//int id=5;
				
				if("0".equals(report.getTimeSheetRecoFlag())){
				 timeSheetReco=timeSheet.findByAll(report.getId(),report.getTimeSheetRecoFlag());
				//Create 4 List 
				}
				
				
				if (timeSheetReco != null && timeSheetReco.length > 0){
				List<Map<String, Object>> billable = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> nonBillable = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> support = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> maintenance = new ArrayList<Map<String, Object>>();
				//List<Map<String, Object>> noProjectType = new ArrayList<Map<String, Object>>();
				int billableCount=0;
				int nonBillableCount=0;
				int supportCount=0;
				int maintenanceCount=0;
				int totalCount=0;
				//int noProjectTypeCount=0;
				int i=0;
				//for(ActiveChargeCode data : activeChargeCodeData){
				

				
				for(RmgTimeSheet data:timeSheetReco){
					if(data.getProjectType()!=null){
			
					if("BILLABLE".equalsIgnoreCase(data.getProjectType())){
						billableCount++;
						report.setProjType(1);
					}
					else if("NON_BILLABLE".equals(data.getProjectType())){
						nonBillableCount++;
						report.setProjType(2);
					}
					else{
						if("SUPPORT".equals(data.getProjectType())){
							supportCount++;
							report.setProjType(3);
						}else if("MAINTENANCE".equals(data.getProjectType())){
							maintenanceCount++;
							report.setProjType(4);
						}
					}
					
					
				switch (report.getProjType()) {
				case BILLABLE:
					billable.add(data.toMap(1));
					break;
				case NON_BILLABLE:
					nonBillable.add(data.toMap(1));
					break;
				case SUPPORT:
					support.add(data.toMap(1));
					break;
				case MAINTENANCE:
					maintenance.add(data.toMap(1));
				/*case NO_PROJECT_TYPE:
					noProjectType.add(data.toMap(1));*/
				
				default:
					break;
			}
					}
		
				totalCount=billableCount+nonBillableCount+supportCount+maintenanceCount;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("billableCount", billableCount);
				map.put("nonBillableCount", nonBillableCount);
				map.put("supportCount", supportCount);
				map.put("maintenanceCount", maintenanceCount);
				//map.put("noProjectTypeCount", noProjectTypeCount);
				map.put("billable", billable);
				map.put("nonBillable", nonBillable);
				map.put("support", support);
				map.put("maintenance", maintenance);
				map.put("totalCount", totalCount);
				//map.put("noProjectType", noProjectType);
			    
				request.setAttribute("actionForm", map);
				result.setForwardName("success");
				}
				}
			 } catch (RmgTimeSheetDaoException e) {
					logger.error("TIMESHEET RECONCILATION  RECEIVEREPORT : failed to fetch data for userId=" + login.getUserId(), e);
				e.printStackTrace();
			}
		break;	
		}
		
          case RECEIVEUPDATEDREPORT:{
			
			
			RmgTimeSheetReco report = (RmgTimeSheetReco) form;
			RmgTimeSheet[] timeSheetReco=null;
			try {
				//int id=5;
				
				if("1".equals(report.getTimeSheetRecoFlag())){
				 timeSheetReco=timeSheet.findByAll(report.getId(),report.getTimeSheetRecoFlag());
				//Create 4 List 
				}
				
				
				if (timeSheetReco != null && timeSheetReco.length > 0){
				List<Map<String, Object>> billable = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> nonBillable = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> support = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> maintenance = new ArrayList<Map<String, Object>>();
				int billableCount=0;
				int nonBillableCount=0;
				int supportCount=0;
				int maintenanceCount=0;
				int totalCount=0;
				//int noProjectTypeCount=0;
				int i=0;
				

				
				for(RmgTimeSheet data:timeSheetReco){
					if(data.getProjectType()!=null){
			
					if("BILLABLE".equalsIgnoreCase(data.getProjectType())){
						billableCount++;
						report.setProjType(1);
					}
					else if("NON_BILLABLE".equals(data.getProjectType())){
						nonBillableCount++;
						report.setProjType(2);
					}
					else{
						if("SUPPORT".equals(data.getProjectType())){
							supportCount++;
							report.setProjType(3);
						}else if("MAINTENANCE".equals(data.getProjectType())){
							maintenanceCount++;
							report.setProjType(4);
						}
					}
					
					
				switch (report.getProjType()) {
				case BILLABLE:
					billable.add(data.toMap(1));
					break;
				case NON_BILLABLE:
					nonBillable.add(data.toMap(1));
					break;
				case SUPPORT:
					support.add(data.toMap(1));
					break;
				case MAINTENANCE:
					maintenance.add(data.toMap(1));
				/*case NO_PROJECT_TYPE:
					noProjectType.add(data.toMap(1));*/
				
				default:
					break;
			}
					}
		
				totalCount=billableCount+nonBillableCount+supportCount+maintenanceCount;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("billableCount", billableCount);
				map.put("nonBillableCount", nonBillableCount);
				map.put("supportCount", supportCount);
				map.put("maintenanceCount", maintenanceCount);
				map.put("billable", billable);
				map.put("nonBillable", nonBillable);
				map.put("support", support);
				map.put("maintenance", maintenance);
				map.put("totalCount", totalCount);
			    request.setAttribute("actionForm", map);
				result.setForwardName("success");
				}
				}
			 } catch (RmgTimeSheetDaoException e) {
					logger.error("TIMESHEET RECONCILATION  RECEIVEREPORT : failed to fetch data for userId=" + login.getUserId(), e);
				e.printStackTrace();
			}
		break;	
		}
		
		case RECEIVEALL:{// show only !escalated requests which was once received by me
			try{
				conn = ResourceManager.getConnection();
				LevelsDao levelDao=LevelsDaoFactory.create(conn);
				int usId=login.getUserId();
				
				
			  DivisonDao divisondao=DivisonDaoFactory.create();
                String s1=null;
                Divison[] str=divisondao.findByDynamicSelect("SELECT D.* FROM USERS U LEFT JOIN PROFILE_INFO P ON P.ID = U.PROFILE_ID left join USER_ROLES UR on UR.ID=U.ID left join ROLES R on R.ID=UR.ROLE_ID left join LEVELS ls on ls.ID=U.LEVEL_ID join DIVISON D on D.ID=ls.DIVISION_ID where U.ID=?",new Object[]{login.getUserId()});
                if(str.length!=0){
                    for(Divison division:str){
                        s1=division.getName();
                    }
                }
                
                if(s1.equalsIgnoreCase("RMG")){
                    dropDown.setKey1(2);
                }
                if(s1.equalsIgnoreCase("Finance")){
                    dropDown.setKey1(3);
                }

				
				int levelss =levelDao.findWhereUsersIdEquals(usId);
				dropDown.setLevelId(levelss);
				
				RmgTimeSheetReco[] rmgTimeSheetReco=null;
				rmgTimeSheetReco=rmgTimeSheetReconDao.findAll();
				HashMap<Integer, RmgTimeSheetReco> timeSheetReconciliation = new HashMap<Integer, RmgTimeSheetReco>();
				ArrayList<Integer> key=new ArrayList<Integer>();
				for(RmgTimeSheetReco data:rmgTimeSheetReco){
					timeSheetReconciliation.put(data.getId(), data);
					
				}
				key.add(levelss);
				List<RmgTimeSheetReco> rmgTimeSheetR = new ArrayList(timeSheetReconciliation.values());// non escalated requests
				
				RmgTimeSheetReco[] rmg = new RmgTimeSheetReco[rmgTimeSheetR.size()];
				new ArrayList<RmgTimeSheetReco>(rmgTimeSheetR).toArray(rmg);
				dropDown.setDropDown(rmg);
				request.setAttribute("actionForm", dropDown);
				return result;
			
			} catch (Exception ex){
				logger.error("TIMESHEET RECONCILATION  RECEIVEALL : failed to fetch data for userId=" + login.getUserId(), ex);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("timesheetReconciliation.receive.failed"));
				result.setForwardName("success");
				
			}
		
		}
			
			   }
		
		}catch(Exception ex){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("timesheetReconciliation.receive.failed"));
		} finally{
			ResourceManager.close(conn);
		}
		
		 return result;
		}
	  public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access timesheetReconciliation reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ProcessEvaluator pEval = new ProcessEvaluator();
		RmgTimeSheetReco rmgTimeSheetReco = (RmgTimeSheetReco) form;
		DropDown dropdown = new DropDown();
		RmgTimeSheetReconDao rmgTimeSheetReconDao = RmgTimeSheetReconDaoFactory.create();
		InvoiceReconciliationDao invoicereconciliatondao=InvoiceReconciliationDaoFactory.create();
		RmgTimeSheetReqDao rmgTimeSheetReqDao = RmgTimeSheetReqDaoFactory.create();
		RmgTimeSheetDao timeSheetReconciliation=RmgTimeSheetDaoFactory.create(); 
	    String rmg=rmgTimeSheetReco.getRmgTimeSheetFlag();
		try{
			RmgTimeSheetReco rmgTimeSheetR = rmgTimeSheetReconDao.findByPrimaryKey(rmgTimeSheetReco.getId());
		if(rmgTimeSheetReco.getRmgTimeSheetFlag().equals("saveTimesheet")){
			
			 if(rmgTimeSheetReco.getTimeSheetDetails()!=null)
		        {
			   for (String text : rmgTimeSheetReco.getTimeSheetDetails())
			   
		              {
			        		RmgTimeSheet rmgTimeSheet = DtoToBeanConverter.rmgTimeSheetDetailsToBeanConverter(text);
			        	
			        		if("0".equals(rmgTimeSheet.getTr_Flag())){
			        			rmgTimeSheet.setLeave(0);
			        			rmgTimeSheet.setStatus(null);
			        			rmgTimeSheet.setTimeSheet_Cato(null);
			        			rmgTimeSheet.setActionBy(null);
			        			timeSheetReconciliation.update(new RmgTimeSheetPk(rmgTimeSheet.getRmgTimeSheetReportId()), rmgTimeSheet);
			        		}
			        		if("1".equals(rmgTimeSheet.getTr_Flag())){
			        			timeSheetReconciliation.update(new RmgTimeSheetPk(rmgTimeSheet.getRmgTimeSheetReportId()), rmgTimeSheet);
			        		}
			        		rmgTimeSheetR.setStatus("Saved");
							rmgTimeSheetR.setHtmlStatus(rmgTimeSheetR.getStatus());
							rmgTimeSheetReconDao.update(new RmgTimeSheetRecoPk(rmgTimeSheetR.getId()), rmgTimeSheetR);
	
		             }
		        	
		         }
			
		}else{
			
			 int esrMapId = rmgTimeSheetReconDao.findByPrimaryKey(rmgTimeSheetReco.getId()).getEsrMapId();
			 RmgTimeSheetReq rmgTimeSheetReq = rmgTimeSheetReqDao.findByDynamicWhere(" RMG_TIMESHEET_ID=? AND ASSIGNED_TO=? AND SUBMITTED_ON IS NULL ORDER BY ID DESC", new Object[] { rmgTimeSheetReco.getId(), (rmgTimeSheetReco.getEscalatedFromId() > 0) ? rmgTimeSheetReco.getEscalatedFromId() : login.getUserId() })[0];
             int isEscalated = rmgTimeSheetReq.getIsEscalated();
						 if(rmgTimeSheetReco.getTimeSheetDetails()!=null)
					        {
						 
					        	for (String text : rmgTimeSheetReco.getTimeSheetDetails())
						   
				              {
					        		RmgTimeSheet rmgTimeSheet = DtoToBeanConverter.rmgTimeSheetDetailsToBeanConverter(text);
					        	
					        		if("0".equals(rmgTimeSheet.getTr_Flag())){
					        			rmgTimeSheet.setLeave(0);
					        			rmgTimeSheet.setStatus(null);
					        			rmgTimeSheet.setTimeSheet_Cato(null);
					        			rmgTimeSheet.setActionBy(null);
					        			timeSheetReconciliation.update(new RmgTimeSheetPk(rmgTimeSheet.getRmgTimeSheetReportId()), rmgTimeSheet);
					        		}
					        		if("1".equals(rmgTimeSheet.getTr_Flag())){
					        			timeSheetReconciliation.update(new RmgTimeSheetPk(rmgTimeSheet.getRmgTimeSheetReportId()), rmgTimeSheet);
					        		}
					        		
					        		rmgTimeSheetR.setStatus("Saved");
									rmgTimeSheetR.setHtmlStatus(rmgTimeSheetR.getStatus());
									rmgTimeSheetReconDao.update(new RmgTimeSheetRecoPk(rmgTimeSheetR.getId()), rmgTimeSheetR);
			
				             }
					        	
					         }
						 
			 
			if (isEscalated != 0){
				RmgTimeSheetReq approverRecord = rmgTimeSheetReqDao.findByPrimaryKey(isEscalated);
				approverRecord.setSubmittedOn(new Date());
				rmgTimeSheetReqDao.update(new RmgTimeSheetReqPk(approverRecord.getId()), approverRecord);
				RmgTimeSheetReq[] otherEscalRecord = rmgTimeSheetReqDao.findWhereIsEscalatedEquals(isEscalated);
				if (otherEscalRecord != null && otherEscalRecord.length > 0){
					for (RmgTimeSheetReq eachReq : otherEscalRecord){
						eachReq.setSubmittedOn(new Date());
						rmgTimeSheetReqDao.update(eachReq.createPk(), eachReq);
					}
				}
			} else if (isEscalated == 0){
				RmgTimeSheetReq[] escalatedRecords = rmgTimeSheetReqDao.findWhereIsEscalatedEquals(rmgTimeSheetReq.getId());
				if (escalatedRecords != null && escalatedRecords.length > 0){
					for (RmgTimeSheetReq eachReq : escalatedRecords){
						eachReq.setSubmittedOn(new Date());
						rmgTimeSheetReqDao.update(eachReq.createPk(), eachReq);
					}
				}
			}
			
			rmgTimeSheetReq.setSubmittedOn(new Date());
			rmgTimeSheetReqDao.update(new RmgTimeSheetReqPk(rmgTimeSheetReq.getId()), rmgTimeSheetReq);
			RmgTimeSheetReq[] perdiemReqs = rmgTimeSheetReqDao.findByDynamicWhere(" RMG_TIMESHEET_ID=? AND SEQ_ID=? AND SUBMITTED_ON IS NULL ", new Object[] { rmgTimeSheetReco.getId(), rmgTimeSheetReq.getSeqId() });
			rmgTimeSheetR.setInvoiceStatus("null");
            ProcessChain processChainDto = processChainDao.findWhereProcNameEquals("IN_TIMESHEET_RECONCILIATION")[0];
			String beginCompletedTag = "<font color=\"#006600\">";
			String endCompletedTag = "</font>";
			String beginPendingTag = "<font color=\"#FF0000\">";
			String endPendingTag = "</font>";
			String actionTakenBy = ProfileInfoDaoFactory.create().findByDynamicWhere(" ID = (SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName();
		
			switch (rmgTimeSheetReq.getSeqId()) {
				case 0:{
					
					if (rmgTimeSheetR.getStatus().equalsIgnoreCase("Report Generated")||rmgTimeSheetR.getStatus().equalsIgnoreCase("saved")){
								rmgTimeSheetR.setStatus("Submitted");
								rmgTimeSheetR.setHtmlStatus(rmgTimeSheetR.getStatus());
								rmgTimeSheetReconDao.update(new RmgTimeSheetRecoPk(rmgTimeSheetR.getId()), rmgTimeSheetR);
								
								RmgTimeSheetReq[] reqs = rmgTimeSheetReqDao.findByDynamicWhere(" RMG_TIMESHEET_ID=? AND SEQ_ID=0 AND SUBMITTED_ON IS NULL", new Integer[] {  rmgTimeSheetR.getId() });
		
								if (reqs != null && reqs.length > 0){
									for (RmgTimeSheetReq eachReq : reqs){
										eachReq.setSubmittedOn(new Date());
										rmgTimeSheetReqDao.update(new RmgTimeSheetReqPk(eachReq.getId()), eachReq);
									}
								}
								Integer[] nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);
								Integer[] lastLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
								insertIntoReq(nextLevelApprovers, rmgTimeSheetReco.getId(), 1);
								//msgBody = sendMail(RmgTimeSheetR.getStatus(), nextLevelApprovers, lastLevelApprovers, null, perdiemPeriodStr);// this may need
								//sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, RmgTimeSheetR.getStatus(), RmgTimeSheetR.getStatus());
							}
				}
					break;
				case 1:
					try{
		 
							Integer[] nextLevelApprovers = null;
							int gotoSeqId = 0;
							
							
							if(rmgTimeSheetReco.getIsRejected().equalsIgnoreCase("TRUE")){
								rmgTimeSheetR.setStatus("Rejected");
								rmgTimeSheetR.setHtmlStatus("Rejected");
								rmgTimeSheetReconDao.update(new RmgTimeSheetRecoPk(rmgTimeSheetR.getId()), rmgTimeSheetR);
								
								
							}else{
								
								rmgTimeSheetR.setStatus("Completed");
								rmgTimeSheetR.setHtmlStatus("Completed");
								rmgTimeSheetR.setInvoiceStatus("Submitted");
								rmgTimeSheetR.setCompletedOn(new Date());
								rmgTimeSheetReconDao.update(new RmgTimeSheetRecoPk(rmgTimeSheetR.getId()), rmgTimeSheetR);
								
								Invoicedto[] invoicedata=invoicereconciliatondao.findAllReport(rmgTimeSheetR.getId());
								Invoicedto invoiceData=new Invoicedto();
								  if(invoicedata!=null)
							        {
							        	
							        	for (Invoicedto invoiceData1 : invoicedata)
						              {
							        		
							        		invoiceData.setTimeSheet_Id(rmgTimeSheetR.getId());
							        	    invoiceData.setTimesheetReportId(invoiceData1.getTimesheetReportId());//timesheetreportId is getting set here
							        		invoiceData.setUser_Id(invoiceData1.getUser_Id());
							        		invoiceData.setIseditinvoice("0");
							        		invoicereconciliatondao.saveForInvoiceReconciliation(invoiceData);
						              }
							        	
							        }
								
								
							}
							Integer[] firstSeqApproverIds = pEval.handlers(processChainDto.getHandler(), 1);
						    insertIntoReq(firstSeqApproverIds, rmgTimeSheetReco.getId(), 0);
			 
							
					 
					} catch (Exception ex){
						logger.error("TIMESHEET RECONCILATION  UPDATE : failed to update data (case:1)", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("timesheetReconciliation.update.failed"));
						result.setForwardName("success");
						return result;
					}
					break;
			 
					}
			
		
			
		}
		
		} catch (Exception ex){
			logger.error("TIMESHEET RECONCILATION UPDATE : failed to update data (main)", ex);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("timesheetReconciliation.update.failed"));
			result.setForwardName("success");
			return result;
		}
		dropdown.setDropDown(null);
		request.setAttribute("actionForm", "");
		result.setForwardName("success");
		return null;
	}
	
	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		Login login = Login.getLogin(request);
		ActionResult result = new ActionResult();
		RmgTimeSheetReco rmgTimeSheetReco = (RmgTimeSheetReco) form;
		if (ModelUtiility.hasModulePermission(login, MODULEID)){
		
			int res = new RmgTimeSheetReconciliationNotifier().generateReportAndNotify(rmgTimeSheetReco.getId() == 0 ? 0 : login.getUserId());
			if (res == 1){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.initiate.confirm"));
			} else if (res == 2){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.initiate.error", "Per-diem"));
			}
		} else{
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access perdiem reconciliation receive Urls without having permisson at :" + new Date());
		}
		return result;
	}

	@Override
	public ActionResult defaultMethod(PortalForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	private RmgTimeSheet[] generateTimeSheetReconciliation(int id) throws RmgTimeSheetDaoException {
		
		return RmgTimeSheetDaoFactory.create().getTimeSheetReconcialition(id); // moved the code in SalaryReconcliationDaoImpl.java
	}
	
	
	public Attachements download(PortalForm form) {
		Attachements attachements = new Attachements();
		if (ModelUtiility.hasModulePermission((Login) form.getObject(), MODULEID)){
			RmgTimeSheetReco rmgTimeSheetReco = (RmgTimeSheetReco) form;
			PortalData portalData = new PortalData();
			String path = portalData.getfolder(portalData.getDirPath()) + "temp";
			
			try{
				File file = new File(path);
				if (!file.exists()) file.mkdir();
			                
								attachements.setFileName(new GenerateXls().generateTimeSheetReconciliation(generateTimeSheetReconciliation(rmgTimeSheetReco.getId()), path));
					path = path + File.separator + attachements.getFileName();
				attachements.setFilePath(path);
			} catch (Exception e){
				e.printStackTrace();
			}
		} else{
			logger.info(ModelUtiility.getInstance().getEmployeeName(((Login) form.getObject()).getUserId()) + " trying to download TimesheetReconciliation  reports without having permisson at :" + new Date());
		}
		return attachements;
	}
	
	
	private void insertIntoReq(Integer[] approverIds, int rmTimeeSheetId, int seqId) throws  RmgTimeSheetReqDaoException {
		RmgTimeSheetReq rmgTimeSheetReq = null;
		RmgTimeSheetReqDao rmgTimeSheetReqDao = RmgTimeSheetReqDaoFactory.create();
		for (Integer eachApproverId : approverIds){
			rmgTimeSheetReq = new RmgTimeSheetReq();
			rmgTimeSheetReq.setRMG_TIMESHEET_ID(rmTimeeSheetId);;
			rmgTimeSheetReq.setSeqId(seqId);
			rmgTimeSheetReq.setAssignedTo(eachApproverId);
			rmgTimeSheetReq.setReceivedOn(new Date());
			rmgTimeSheetReq.setSubmittedOn(null);// intentionally
			rmgTimeSheetReq.setIsEscalated(0);
			rmgTimeSheetReqDao.insert(rmgTimeSheetReq);
		}
	}
	
	private String sendMail(String status, Integer[] toIds, Integer[] ccIds, String args, String timeSheetReconciliation) throws FileNotFoundException, ProfileInfoDaoException, AddressException, UnsupportedEncodingException, MessagingException {
		String templateName = "";
		String subject = status;
		String msgBody = "";
		PortalMail portalMail = new PortalMail();
		if (status.equalsIgnoreCase("Completed")){
			subject = "TimeSheet Report reviewed by Business for " + timeSheetReconciliation;
			templateName = MailSettings.TIMESHEET_RECONCILIATION_COMPLETED;
		} else if (status.equalsIgnoreCase("Rejected")){
			subject = "TimeSheet Report for " + timeSheetReconciliation + " Rejected by " + args;
			templateName = MailSettings.TIMESHEET_RECONCILIATION_REJECTED;
		} else{
			
			templateName = MailSettings.TIMESHEET_RECONCILIATION_SUBMITTED;
			subject = "TimeSheet Report Report submitted for " + timeSheetReconciliation;
		}
		portalMail.setMailSubject(subject);
		portalMail.setTemplateName(templateName);
		portalMail.setPerdiemTerm(timeSheetReconciliation); // (presentDate<15)?"(1-15)":"(16-"+lastDateOfThisMonth+")" +" of "+month+" "+year);
		portalMail.setCommentsByFinanceTeam(args);
		portalMail.setPerdiemAcceptedOrApproved(status);
		MailGenerator mailGenerator = new MailGenerator();
		msgBody = mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail);
		if (toIds != null && toIds.length > 0){
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			if (toIds != null && toIds.length == 1){
				portalMail.setApproverName(ModelUtiility.getInstance().getEmployeeName(toIds[0]));
			} else if (toIds != null && toIds.length > 1){
				StringBuffer toNames = new StringBuffer();
				for (int i = 0; i < toIds.length; i++){
					toNames.append(ModelUtiility.getInstance().getEmployeeName(toIds[i]));
					if (i != toIds.length - 1) toNames.append(" / ");
				}
				portalMail.setApproverName(toNames.toString());
			}
			String ids = Arrays.asList(toIds).toString().replace('[', ' ').replace(']', ' ').trim();
			String[] mailIds = profileInfoDao.findOfficalMailIdsWhere("WHERE PI.ID IN(SELECT U.PROFILE_ID FROM USERS U WHERE STATUS=0 AND EMP_ID>0 AND U.ID IN(" + ids + "))");
			portalMail.setAllReceipientMailId(mailIds);
			if (ccIds != null && ccIds.length > 0){
				ids = Arrays.asList(ccIds).toString().replace('[', ' ').replace(']', ' ').trim();
				mailIds = profileInfoDao.findOfficalMailIdsWhere("WHERE PI.ID IN(SELECT U.PROFILE_ID FROM USERS U WHERE STATUS=0 AND EMP_ID>0 AND U.ID IN(" + ids + "))");
				portalMail.setAllReceipientcCMailId(mailIds);
			}
			mailGenerator.invoker(portalMail);
		}
		return msgBody;
	}
}
