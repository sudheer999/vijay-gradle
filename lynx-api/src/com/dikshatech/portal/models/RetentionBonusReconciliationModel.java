package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.EmployeeBonus;
import com.dikshatech.common.utils.GenerateXls;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.RetentionBonusRecReportGeneratorAndNotifier;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DepPerdiemHoldDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RetentionBonusComponentsDao;
import com.dikshatech.portal.dao.RetentionBonusDao;
import com.dikshatech.portal.dao.RetentionBonusRecReportHistoryDao;
import com.dikshatech.portal.dao.RetentionBonusReconciliationDao;
import com.dikshatech.portal.dao.RetentionBonusReconciliationHoldDao;
import com.dikshatech.portal.dao.RetentionBonusReconciliationReportDao;
import com.dikshatech.portal.dao.RetentionBonusReconciliationReqDao;
import com.dikshatech.portal.dao.RetentionEmpSerReqMapDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.DepPerdiemExchangeRates;
//import com.dikshatech.portal.dto.RetentionDepPerdiemExchangeRates;
//import com.dikshatech.portal.dto.RetentionDepPerdiemHold;
import com.dikshatech.portal.dto.DepPerdiemHold;
import com.dikshatech.portal.dto.FinanceInfo;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.RetentionBonus;
import com.dikshatech.portal.dto.RetentionBonusComponents;
import com.dikshatech.portal.dto.RetentionBonusRecReportHistory;
import com.dikshatech.portal.dto.RetentionBonusReconciliation;
import com.dikshatech.portal.dto.RetentionBonusReconciliationHold;
import com.dikshatech.portal.dto.RetentionBonusReconciliationPk;
import com.dikshatech.portal.dto.RetentionBonusReconciliationReport;
import com.dikshatech.portal.dto.RetentionBonusReconciliationReq;
import com.dikshatech.portal.dto.RetentionBonusReconciliationReqPk;
import com.dikshatech.portal.dto.RetentionEmpSerReqMap;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.RetentionBonusReconciliationDaoException;
import com.dikshatech.portal.exceptions.RetentionBonusReconciliationReportDaoException;
import com.dikshatech.portal.exceptions.RetentionBonusReconciliationReqDaoException;
import com.dikshatech.portal.exceptions.RetentionEmpSerReqMapDaoException;
import com.dikshatech.portal.factory.CurrencyDaoFactory;
//import com.dikshatech.portal.factory.RetentionDepPerdiemExchangeRatesDaoFactory;
//import com.dikshatech.portal.factory.RetentionDepPerdiemHoldDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemExchangeRatesDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemHoldDaoFactory;
import com.dikshatech.portal.factory.FinanceInfoDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RetentionBonusComponentsDaoFactory;
import com.dikshatech.portal.factory.RetentionBonusDaoFactory;
import com.dikshatech.portal.factory.RetentionBonusRecReportHistoryDaoFactory;
import com.dikshatech.portal.factory.RetentionBonusReconciliationDaoFactory;
import com.dikshatech.portal.factory.RetentionBonusReconciliationHoldDaoFactory;
import com.dikshatech.portal.factory.RetentionBonusReconciliationReportDaoFactory;
import com.dikshatech.portal.factory.RetentionBonusReconciliationReqDaoFactory;
import com.dikshatech.portal.factory.RetentionEmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.timer.EscalationJob;

public class RetentionBonusReconciliationModel extends ActionMethods{

	
	int j;
	private static Logger		logger							= LoggerUtil.getLogger(RetentionBonusReconciliationModel.class);
	public static final short	AUTO							= 0;
	public static final short	DELETED							= 1;
	public static final short	ADDED							= 2;
	public static final short	MODIFIED						= 3;
	public static final short	ADDED_DELETED					= 4;
	public static final short	HOLD							= 7;
	public static final short	RELEASED						= 9;
	public static final short	REJECTED						= 11;
	public static final int		MODULEID						= 81;
	public static final short	monthly							= 100;
	public static final short	monthlyQuarterly				= 101;
	public static final short	monthlyQuarterlyByAnnual		= 102;
	public static final short	monthlyQuarterlyByAnnualAnnual	= 103;
	public static final String	CATEGORY						= "RETENTION BONUS RECONCILIATION";
	public static final short 	FIXED_HOLD 						= 82;
	public static final short 	FIXED_DELETED 					= 83;
	public static final short 	FIXED_REJECTED					= 84;
	
	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		Login login = Login.getLogin(request);
		ActionResult result = new ActionResult();
		RetentionBonusReconciliation bonusForm = (RetentionBonusReconciliation) form;
		if (ModelUtiility.hasModulePermission(login, MODULEID)){
			int res = new RetentionBonusRecReportGeneratorAndNotifier().generateReportAndNotify(bonusForm.getId() == 0 ? 0 : login.getUserId());
			//int res = new RetentionBonusRecReportGeneratorAndNotifier().generateReportAndNotify(login.getUserId());
			if (res == 1){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.initiate.confirm"));
			} else if (res == 2){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.initiate.error", "Retention Bonus"));
			}else if(res==4){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.initiate.err", "Retention Bonus"));
			}
			else if(res==3){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.bonus.initiate.month"));
				logger.info(" Retention Bonus Report Generation Failed and Not permitted for this  month: ");
			}else if(res==4){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.initiate.err", "Retetntion Bonus"));
			}
	} else{
	result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));		
	logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access retention bonus reconciliation receive Urls without having permisson at :" + new Date());
		}
	return result;
	
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub 
		return null;
	}

	

	
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access retention bonus reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			RetentionEmpSerReqMapDao esrDao = RetentionEmpSerReqMapDaoFactory.create(conn);
			RetentionBonusReconciliationDao bonusDao = RetentionBonusReconciliationDaoFactory.create(conn);
			DropDown dropdown = new DropDown();
			RetentionBonusReconciliationReqDao bonusReqDao = RetentionBonusReconciliationReqDaoFactory.create(conn);
			RetentionBonusReconciliation bonusForm = (RetentionBonusReconciliation) form;
			
			
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
				case RECEIVEALL:{// show only !escalated requests which was once received by me
					try{
						RetentionBonusReconciliationReq[] bonusReqs = null;
						bonusReqs = bonusReqDao.findByDynamicWhere(" ASSIGNED_TO=? AND IS_ESCALATED=0 GROUP BY BR_ID", new Object[] { login.getUserId() });
						dropdown.setKey1(5);// seqId
						HashMap<Integer, RetentionBonusReconciliation> bonusMap = new HashMap<Integer, RetentionBonusReconciliation>();
						if (bonusReqs != null && bonusReqs.length > 0){
							HashMap<Integer, String> idReqIdMap = esrDao.getIdReqIdMap(16);
							if (idReqIdMap != null && idReqIdMap.size() > 0){
								for (RetentionBonusReconciliationReq eachBonusReq : bonusReqs){
			
									RetentionBonusReconciliation tempBonus = bonusDao.findByPrimaryKey(eachBonusReq.getBonusId());
									tempBonus.setReqId(idReqIdMap.get(tempBonus.getEsrMapId()));
									tempBonus.setStatus(tempBonus.getHtmlStatus());
									bonusMap.put(tempBonus.getId(), tempBonus);}}
						}
						for (Map.Entry<Integer, RetentionBonusReconciliation> entrySet : bonusMap.entrySet()){
							int bonusId = entrySet.getKey();
							RetentionBonusReconciliationReq bonusReq = bonusReqDao.findByDynamicWhere(" ASSIGNED_TO=? AND BR_ID=? ORDER BY ID DESC", new Object[] { login.getUserId(), bonusId })[0];
							if (bonusReq.getSubmittedOn() == null){
								bonusMap.get(bonusId).setActionButtonVisibility("enable");
							} else{
								bonusMap.get(bonusId).setActionButtonVisibility("disable");
							}
						}
						ProcessChainDao processChainDao = ProcessChainDaoFactory.create(conn);
						ProcessEvaluator pEval = new ProcessEvaluator();
						ProcessChain processChainDto = processChainDao.findWhereProcNameEquals("IN_RETENTION_BONUS_RECON")[0];
						// boolean found = false;
						for (int appLevel = 1; appLevel <= 4; appLevel++){
							Integer[] approverIds = pEval.approvers(processChainDto.getApprovalChain(), appLevel, 1);
							for (Integer eachApproverId : approverIds){
								if (eachApproverId == login.getUserId()){
									dropdown.setKey1(appLevel);
									break;
								}
							}
				//  Checking for the user Whether there are any escalated requests and setting the flag call the another url
							RetentionBonusReconciliationReq[] escBonusReq = bonusReqDao.findByDynamicWhere(" ASSIGNED_TO=? AND IS_ESCALATED !=0 ORDER BY ID DESC", new Object[] { login.getUserId() });
							if (escBonusReq != null && escBonusReq.length > 0){
								dropdown.setKey2(1);
							}
						}	
			    //   Generating List of all escalated Bonus Reconciliation Requests	
						List<RetentionBonusReconciliation> escalatedbonusList = receiveEscalations(login.getUserId());// only escalated requests
						if (escalatedbonusList != null){
				//  Generating List of all Bonus Reconciliation List	
					    List<RetentionBonusReconciliation> bonusList = new ArrayList(bonusMap.values());// non escalated requests
					    bonusList.addAll(escalatedbonusList);
					    RetentionBonusReconciliation[] bonuses = new RetentionBonusReconciliation[bonusList.size()];
						new ArrayList<RetentionBonusReconciliation>(bonusList).toArray(bonuses);
						dropdown.setDropDown(bonuses);
						}
					} catch (Exception ex){
						ex.printStackTrace();
						logger.error("RETENTIONBONUSRECONCILATION RECEIVEALL : failed to fetch data for userId=" + login.getUserId(), ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
					break;
				}
				case RECEIVEESCALATIONS:{
				break;}
			    case RECEIVEREPORT:{
					try{					
						RetentionBonusReconciliationReq[] bonusReq = bonusReqDao.findByDynamicWhere(" BR_ID = ? AND ASSIGNED_TO=?  ORDER BY ID DESC", new Object[] { bonusForm.getBonusId(), login.getUserId() });
						RetentionBonusRecReportGeneratorAndNotifier notifier=new RetentionBonusRecReportGeneratorAndNotifier();
						if (bonusReq != null && bonusReq.length > 0 && bonusReq[0] != null){
							RetentionBonusReconciliationReport[] bonusReports = null;
							ProfileInfo[] profInfo = null;
							ProfileInfoDao profInfoDao=ProfileInfoDaoFactory.create(conn);
							RetentionBonusReconciliationReportDao bonusReportDao = RetentionBonusReconciliationReportDaoFactory.create(conn);
							if (bonusReq[0].getSeqId() == 2)
							{
								int bUserId = login.getUserId().intValue();
								if (bonusForm.getEscalatedFromId() > 0){
								// logged in person must see "this' person's resources
									bUserId = bonusForm.getEscalatedFromId();
								} else if (bonusReq[0].getIsEscalated() != 0){
									RetentionBonusReconciliationReq approverRecord = bonusReqDao.findByPrimaryKey(bonusReq[0].getIsEscalated());
									bUserId = approverRecord.getAssignedTo();
									}
								bonusReports = bonusReportDao.findWhereBonusIdEquals(bonusReq[0].getBonusId(), bUserId);
							} else {
								bonusReports = bonusReportDao.findWhereBonusIdEquals(bonusReq[0].getBonusId());
							}
							if (bonusReports != null && bonusReports.length > 0){
								List<RetentionBonusReconciliationReport> list = new ArrayList<RetentionBonusReconciliationReport>();
								List<Map<String, Object>> auto = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> added = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> modified = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> deleted = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> hold = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> released = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> rejected = new ArrayList<Map<String, Object>>();
								Map<String, String> currencyTypes = CurrencyDaoFactory.create(conn).getAllCurrencyTypes();
								double totalAmount = 0.0;
								for (RetentionBonusReconciliationReport report : bonusReports){
									if(!(report.getMonth()).equals(" ")||!(report.getMonth()).equals(null)){
									if (report.getqAmountInr() == null) report.setqAmountInr(report.getrBonus());
										
								  report.setCurrencyName(currencyTypes.get(report.getCurrencyType() + ""));
								 report.setqAmountInr(new DecimalFormat("0.00").format(Math.round(Double.parseDouble(report.getrBonus())))+"");
								bonusReportDao.update(report.createPk(),report);
									
								UsersDao usersDao = UsersDaoFactory.create();
								Users[] users = usersDao.findWhereIdEquals(report.getUserId());
								
								int userId=report.getUserId();
								int empId = users[0].getEmpId();
								report.setEmpId(empId);
								//report.setEmpId(userId);
								profInfo=profInfoDao.findByDynamicSelect("SELECT PF.* FROM PROFILE_INFO PF JOIN USERS U ON PF.ID=U.PROFILE_ID JOIN RETENTION_BONUS_REC_REPORT BR ON BR.USER_ID = U.ID WHERE BR.USER_ID=?", new Object[] {  new Integer(userId)});  
								for (ProfileInfo profile : profInfo){ 
									report.setEmployeeName(profile.getFirstName()+" "+profile.getLastName());
								}
									report.setTotal(new DecimalFormat("0.00").format(Math.round(Double.parseDouble(report.getTotal())))+"");
									switch (report.getType()) {
										case AUTO:
											auto.add(report.toMap(1));
											totalAmount += Double.parseDouble(report.getTotal());
											break;
										case ADDED:
											added.add(report.toMap(1));
											totalAmount += Double.parseDouble(report.getTotal());
											break;
										case MODIFIED:
											totalAmount +=Double.parseDouble(report.getTotal());
											if (report.getModifiedBy() == null) list.add(report);
											else modified.add(report.toMap(1));
											break;
										case ADDED_DELETED:
										case DELETED:
											deleted.add(report.toMap(1));
											break;
										
										case HOLD:
											report.setRelease(canRelease(report.getId(), login.getUserId(), conn));
											hold.add(report.toMap(1));
											totalAmount += Double.parseDouble(report.getTotal());
											break;
									    case RELEASED:
											released.add(report.toMap(1));
											totalAmount += Double.parseDouble(report.getTotal());
											break;
										case REJECTED:
											rejected.add(report.toMap(1));
											break;
										default:
											break;}
									 }
									} 
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("auto", auto);
						map.put("added", added);
						map.put("modified", modified);
						map.put("deleted", deleted);
						map.put("hold", hold);
						map.put("released", released);
						map.put("rejected", rejected);
						map.put("total", new DecimalFormat("0.00").format(totalAmount));
						request.setAttribute("actionForm", map);
						return result;
					}
				}
			} catch (Exception e){
				e.printStackTrace();
				logger.error("RETENTION BONUSRECONCILIATION RECEIVEREPORTFORBONUS : failed to fetch data", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
			}
				}
	
					break;
			 case RECEIVE:{
				try{
					RetentionBonusReconciliationReportDao ReportDao = RetentionBonusReconciliationReportDaoFactory.create(conn);
					RetentionBonusRecReportHistoryDao historyDao = RetentionBonusRecReportHistoryDaoFactory.create(conn);
					RetentionBonusReconciliationReport report = ReportDao.findByPrimaryKey(bonusForm.getId());
					List<Object> BonusTerm = new ArrayList<Object>();
					 BonusTerm = JDBCUtiility.getInstance().getSingleColumn("SELECT RETENTION_INSTALLMENTS FROM RETENTION_BONUS WHERE USER_ID=?",new Object[] {report.getUserId()} );
					Map<String, String> currencyTypes = null;
					double rbonusAmount = 0;
					RetentionBonusRecReportHistory[] history = historyDao.findByBonusReport(report.getId());
						int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
						String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
						List<Map<String, String>> historyList = new ArrayList<Map<String, String>>();
						if (report.getModifiedBy() != null && !report.getModifiedBy().equalsIgnoreCase(empName)){
						RetentionBonusRecReportHistory his = report.getRetentionBonusRecReportHistory();
		                    his.setCurrencyName("INR");
							his.setTotal(his.getTotal());
							historyList.add(his.toHashMap());
						}
						Map<String, Object> map = report.toMap(2);
						for (RetentionBonusRecReportHistory hist : history){
							hist.setTotal(hist.getTotal());
							historyList.add(hist.toHashMap());
						}
						if (historyList.size() > 0) map.put("history", historyList);
						RetentionBonusComponents[] components = RetentionBonusComponentsDaoFactory.create(conn).findWhereRepIdEquals(report.getId());
							if (components != null && components.length > 0) 
							map.put("components", components);
							/*if(BonusTerm.size() ==  0 || BonusTerm.size() < 0 ){
								map.put("Term", 1);
								 
							}else if(BonusTerm.size() > 0 ){
								map.put("Term", BonusTerm.get(0));
							}*/
							 if(BonusTerm.size() == 0 || BonusTerm.size() < 0){
									rbonusAmount = Math.round(Double.parseDouble(report.getrBonus()));
									map.put("InitialBonusAmount", rbonusAmount);
									map.put("Term", 1);
							}else if(BonusTerm.get(0).equals(1)){
								rbonusAmount = Math.round(Double.parseDouble(report.getrBonus())/12);
								map.put("InitialBonusAmount", rbonusAmount);
								map.put("Term", 1);
							}else if(BonusTerm.get(0).equals(2)){
								rbonusAmount = Math.round(Double.parseDouble(report.getrBonus())/4);
								map.put("InitialBonusAmount", rbonusAmount);
								map.put("Term", 2);
							}else if(BonusTerm.get(0).equals(3)){
								rbonusAmount = Math.round(Double.parseDouble(report.getrBonus())/2);
								map.put("InitialBonusAmount", rbonusAmount);
								map.put("Term", 3);
							}else if(BonusTerm.get(0).equals(4)){
								rbonusAmount = Math.round(Double.parseDouble(report.getrBonus()));
								map.put("InitialBonusAmount", rbonusAmount);
								map.put("Term", 4);
							}
							request.setAttribute("actionForm", map);
							return result;
						
					} catch (Exception e){
						e.printStackTrace();
						logger.error("RETENTION BONUSRECONCILIATION RECEIVE : failed to fetch data", e);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
					}
				}
					break;
			 case RECEIVEEMPLOYEESFORBONUS:{
			   try{
					 boolean isFirstLevel = true;
					 bonusForm.setCompanyId(1);
					if (bonusForm.getBonusId()> 0){
						String status = bonusDao.findByPrimaryKey(bonusForm.getBonusId()).getStatus();
						if (!(status.equalsIgnoreCase("REPORT GENERATED") || status.equalsIgnoreCase("REJECTED") || status.equalsIgnoreCase("REVIEWED") || status.equalsIgnoreCase("Submitted"))){
							isFirstLevel = false;
						}
					}
					int loggedInPerson;
					if (bonusForm.getEscalatedFromId() > 0){
						// logged in person must see "this' person's resources
						loggedInPerson = bonusForm.getEscalatedFromId();
					} else{
						loggedInPerson = login.getUserId();// seqId = 2 :)
					}
					List<EmployeeBonus> employeeBonusList = UsersDaoFactory.create(conn).receiveAllEmployeesForBonus(new Integer[] { bonusForm.getCompanyId(), bonusForm.getBonusId() }, isFirstLevel, loggedInPerson);
					dropdown.setDropDown(employeeBonusList.toArray());
			
			   }catch (Exception ex){
				   ex.printStackTrace();
						logger.error("RETENTION BONUSRECONCILATION RECEIVEEMPLOYEESFORBONUS : failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
			 }
			break;
			 case RECEIVEALLREPORTINGMGR:{
				 List<Object> allReportingManagers=null;
				List <Object> initiators=JDBCUtiility.getInstance().getSingleColumn("SELECT ASSIGNED_TO FROM RETENTION_BONUS_RECONCILIATION_REQ WHERE SEQ_ID=0 AND BR_ID=?", new Object[] {bonusForm.getId()});
				int loggedInPerson = login.getUserId();
				String ini= initiators.toString().replace('[', ' ').replace(']', ' ').trim();
				String[] initiatorIds=ini.split(",");
				if(initiatorIds.length>1){
					for(int i=0;i<initiatorIds.length;i++){
						String temp= initiatorIds[i].trim();
				if(Integer.parseInt(temp)==loggedInPerson){
					List<Object> list=	 JDBCUtiility.getInstance().getSingleColumn("SELECT DISTINCT P.REPORTING_MGR FROM PROFILE_INFO P INNER JOIN USERS U ON P.REPORTING_MGR=U.ID AND U.STATUS=? AND U.EMP_ID > 1 ORDER BY U.ID",  new Object[] {0});
					String resourcesIds = list.toString().replace('[', ' ').replace(']', ' ').trim();
				    String SQL= "SELECT CONCAT(FIRST_NAME, LAST_NAME) FROM PROFILE_INFO WHERE ID in(" +resourcesIds+") AND ID>? ORDER BY ID";
				     allReportingManagers=	 JDBCUtiility.getInstance().getSingleColumn	(SQL,  new Object[] {3});
				     break;}
				}
				}
				else{
					if(Integer.parseInt(ini)==loggedInPerson){
					List<Object> list=	 JDBCUtiility.getInstance().getSingleColumn("SELECT DISTINCT P.REPORTING_MGR FROM PROFILE_INFO P INNER JOIN USERS U ON P.REPORTING_MGR=U.ID AND U.STATUS=? AND U.EMP_ID > 1 ORDER BY U.ID",  new Object[] {0});
					String resourcesIds = list.toString().replace('[', ' ').replace(']', ' ').trim();
				    String SQL= "SELECT CONCAT(FIRST_NAME, LAST_NAME) FROM PROFILE_INFO WHERE ID in(" +resourcesIds+") AND ID>? ORDER BY ID";
				     allReportingManagers=	 JDBCUtiility.getInstance().getSingleColumn	(SQL,  new Object[] {3});
				}
				     }
				dropdown.setDropDown(allReportingManagers.toArray());
			 }break;
			 case BANKACCOUNTS:{
				 RetentionBonusReconciliationReport report = RetentionBonusReconciliationReportDaoFactory.create(conn).findByPrimaryKey(bonusForm.getId());
					FinanceInfo finance = FinanceInfoDaoFactory.create(conn).findByUserId(report.getUserId());
					Map<String, String> map = new HashMap<String, String>();
					if (finance != null){
						if (finance.getPrimBankName() != null && !finance.getPrimBankName().trim().equals("")) map.put("primary", finance.getPrimBankName() + " - " + finance.getPrimBankAccNo());
						if (finance.getSecBankName() != null && !finance.getSecBankName().trim().equals("")) map.put("secondary", finance.getSecBankName() + " - " + finance.getSecBankAccNo());
						map.put("selected", String.valueOf(report.getAccountType()));
					}
					request.setAttribute("actionForm", map);
					return result;}
             }
			request.setAttribute("actionForm", dropdown);
		} catch (Exception e){
			e.printStackTrace();
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
		} finally{
			ResourceManager.close(conn);
		}
		return result;
	}
	
	private boolean canRelease(int id, Integer userId, Connection conn) {
		return JDBCUtiility.getInstance().getRowCount(" FROM RETENTION_BONUS_REC_HOLD WHERE USER_ID=? AND REP_ID=?", new Object[] { userId, id }, conn) > 0;
	}

	
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) throws RetentionBonusReconciliationReportDaoException {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
	if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access retention bonus reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
	RetentionBonusReconciliation bonusForm = (RetentionBonusReconciliation) form;
		Connection conn = null;
		request.setAttribute("actionForm", "");
		
		switch (SaveTypes.getValue(form.getsType())) {
			case SAVE:
				try{
				conn = ResourceManager.getConnection();
				conn.setAutoCommit(false);
				RetentionBonusReconciliationReportDao bonReportDao = RetentionBonusReconciliationReportDaoFactory.create(conn);
				RetentionBonusReconciliationReport bonusReport = bonReportDao.findByPrimaryKey(bonusForm.getId());
				int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
				String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
				if (bonusReport != null && (bonusReport.getModifiedBy() == null || !bonusReport.getModifiedBy().equalsIgnoreCase(empName))){
					bonusReport.setModifiedBy(empName);
					bonusReport.setModifiedOn(new Date());
					//bonusReport.setqAmountInr(Double.parseDouble(bonusReport.getqAmount()) + "");
					bonusReport.setqAmountInr(Double.parseDouble(bonusReport.getrBonus()) + ""); 
					bonusReport.setComments(bonusForm.getComments());
					RetentionBonusRecReportHistoryDaoFactory.create(conn).insert(bonusReport.getRetentionBonusRecReportHistory());
				}else if (bonusReport != null || (bonusReport.getModifiedBy() == null)){
					bonusReport.setModifiedBy(empName);
					bonusReport.setModifiedOn(new Date());
					//bonusReport.setqAmountInr(Double.parseDouble(bonusReport.getqAmount()) + "");
					bonusReport.setqAmountInr(Double.parseDouble(bonusReport.getrBonus()) + ""); 
					bonusReport.setComments(bonusForm.getComments());
					RetentionBonusRecReportHistoryDaoFactory.create(conn).insert(bonusReport.getRetentionBonusRecReportHistory());
				}
				
				 double totalbonus=Double.parseDouble(bonusReport.getTotal());
				
				 bonusReport.setTotal(totalbonus+"");
	             bonusReport.setComments(bonusForm.getComments());
				 bonusReport.setModifiedBy(empName);
				 bonusReport.setModifiedOn(new Date());
				 bonusReport.setType(bonusReport.getType() == ADDED ||  bonusReport.getType() == HOLD || bonusReport.getType() == RELEASED || bonusReport.getType() == REJECTED ? bonusReport.getType() : MODIFIED);	
			   
				 bonusReport.setqAmountInr(Double.parseDouble(bonusReport.getrBonus()) + ""); 
			     RetentionBonusComponentsDao componentsDao = RetentionBonusComponentsDaoFactory.create(conn);
			     RetentionBonusComponents[] components = componentsDao.findWhereRepIdEquals(bonusReport.getId());
				Map<String, RetentionBonusComponents> componentMap = new HashMap<String, RetentionBonusComponents>();
				if (components != null && components.length > 0){
					for (RetentionBonusComponents component : components){
						componentMap.put(component.getId().intValue() + "", component);}
				}
				if(bonusForm.getComponents() != null){	
					List<String> pbonuslist=new ArrayList<String>();
					for(String str:bonusForm.getComponents()){
						String[] strtemp=str.split("~=~");
						pbonuslist.add(strtemp[0]);
					}
					List<String> deletebonuslist=new ArrayList<String>();
					for (Map.Entry<String, RetentionBonusComponents> entry : componentMap.entrySet()){
						deletebonuslist.add(entry.getKey());
					}
					//now removing from delete list whch values are not needed
					deletebonuslist.removeAll(pbonuslist);
					for(String str:deletebonuslist){
						componentsDao.delete(componentMap.get(str).createPk());
					}
					}else{
						List<String> deletebonuslist=new ArrayList<String>();
						for (Entry<String, RetentionBonusComponents> entry : componentMap.entrySet()){
							deletebonuslist.add(entry.getKey());
						}
						//now removing from delete list whch values are not needed
						deletebonuslist.remove(components);
						for(String str:deletebonuslist){
							componentsDao.delete(componentMap.get(str).createPk());
						}
					}
               if (bonusForm.getComponents()!=null){
            	   double totalAmt=0.0;
            	  totalAmt=Double.parseDouble(bonusForm.getInitialBonusAmount());
            	 for (String entry : bonusForm.getComponents()){
					String[] data = entry.split("~=~");
					
					if (data.length != 5 || data[0] == null || data[1] == null || data[2] == null || data[4] == null || data[3] == null || Integer.parseInt(data[3]) < 1){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.save.leave.failed"));
						logger.error("Unable to update components of retention bonus reports details. input: " + entry + ", " + bonusForm.getComponents());
						return result;}
					int id = Integer.parseInt(data[0]);
					RetentionBonusComponents bonusComponent = null;
					if (id > 0){
						bonusComponent = componentMap.get(id + "");
					} else{
						bonusComponent = new RetentionBonusComponents();
					}
					  bonusComponent.setValues(bonusReport.getId(),Short.parseShort(data[4]), data[1], Integer.parseInt(data[3]), data[2], empName, new Date(), "");
					  
						double total =Double.parseDouble(bonusComponent.getAmount());
                      
						if (bonusComponent.getType().shortValue() == 1){
							totalAmt+=total; //Additions
							bonusReport.setTotal(Math.round(totalAmt)+ "");
						}
						else if (bonusComponent.getType() == 2 || bonusComponent.getType() == 3){
							totalAmt-=total;  //Deductions and  LWP
							bonusReport.setTotal(Math.round(totalAmt)+ "");
						}
						if (bonusComponent.getId() == null || bonusComponent.getId().intValue() < 1) 
							componentsDao.insert(bonusComponent);
						else{componentsDao.update(bonusComponent.createPk(), bonusComponent);
							componentMap.remove(bonusComponent.getId().intValue() + "");}
					
				}
				   for (Map.Entry<String, RetentionBonusComponents> entry : componentMap.entrySet()){
					logger.info("deleting the BonusComponents : " + entry.getValue());
					componentsDao.delete(entry.getValue().createPk());
					}
				    bonusReport.setTotal(Math.round(totalAmt)+ ""); 
           	}  //components table Update or Insert finished here
               else{
            	   double totalAmt=0.0;
              	  totalAmt=Double.parseDouble(bonusForm.getInitialBonusAmount());
              	 bonusReport.setTotal(Math.round(totalAmt)+ "");
               }
               bonReportDao.update(bonusReport.createPk(), bonusReport);
			    conn.commit();
				} catch (Exception e){
					e.printStackTrace();
					logger.error("Unable to save retention bonus reconciliation report", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.save.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			case ADD:
				try{
					int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
					String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
					RetentionBonusReconciliationReportDao depReportDao = RetentionBonusReconciliationReportDaoFactory.create(conn);
					RetentionBonusReconciliationDao bonusDao=RetentionBonusReconciliationDaoFactory.create();
					RetentionBonusReconciliation bonus=null;
					List<String> ExistEmpName = new ArrayList<String>();
					List<String> AddedEmpName = new ArrayList<String>();
					String ExistEName = "";
					String AddedEName = "";
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					for (String str : bonusForm.getAddedEmpData()){
								String data[] = str.split("~#~");
								if (data.length != 5 || data[0] == null || data[1] == null || data[2] == null || data[3] == null || data[4] == null){ throw new Exception(); }
								int userId = Integer.parseInt(data[0]);
								ProfileInfo[] userProfileInfo = ProfileInfoDaoFactory.create(conn).findByDynamicWhere("ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { userId });
								RetentionBonusReconciliationReport report = new RetentionBonusReconciliationReport(bonusForm.getBonusId(), userId,data[1], Integer.parseInt(data[4]), userProfileInfo[0].getReportingMgr(), ModelUtiility.getInstance().getEmployeeName(userProfileInfo[0].getReportingMgr()), data[2],data[3]);
								RetentionBonusReconciliationReportDao RetentionBonusData = RetentionBonusReconciliationReportDaoFactory.create(conn);
								//RetentionBonusReconciliationReport[] RetentionBonus = RetentionBonusData.findWhereUserIdEquals(report.getUserId());
								RetentionBonusReconciliationReport[] RetentionBonus = RetentionBonusData.findWherebonusIdEquals(report.getBonusId(),report.getUserId());
								UsersDao usersDao = UsersDaoFactory.create();
								Users[] users = usersDao.findWhereIdEquals(userId);
								//for(int i=0;i<=RetentionBonus.length;i++){
								//if(RetentionBonus[i].getBonusId() == report.getBonusId() ){
								if(RetentionBonus.length <= 0){
									
								bonus=bonusDao.findByPrimaryKey(bonusForm.getBonusId());
								if(bonus!=null){
									report.setMonth(bonus.getMonth());	
								}
								report.setqAmountInr(0+"");
								report.setrBonus(report.getTotal()+"");
								report.setModifiedBy(empName);
								report.setModifiedOn(new Date());
								report.setType(ADDED);
								//report.setEmpId(users[0].getEmpId());
								depReportDao.insert(report);
								conn.commit();
								AddedEmpName.add(userProfileInfo[0].getFirstName());
									}else{
										ExistEmpName.add(userProfileInfo[0].getFirstName());
										
									}
								//}
								//}
					  		}
					for(String str : ExistEmpName){
						ExistEName += str+",";
					}
					for(String s : AddedEmpName){
						AddedEName += s+",";
					}
					ResourceManager.close(conn);
					/*if(ExistEName.isEmpty()){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.add.f1",AddedEName));
					}else*/ if(AddedEName.isEmpty() && !ExistEName.isEmpty()){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.add.f2",ExistEName));
						//result.setDispatchDestination("duplicateEntry");
						return result; 
					}else if(!ExistEName.isEmpty() && !AddedEName.isEmpty()){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.add.f3",AddedEName,ExistEName));
						//result.setDispatchDestination("duplicateEntry");
						return result;
					}
				} catch (Exception e){
					e.printStackTrace();
					try{
						conn.rollback();
					} catch (SQLException e1){
						e1.printStackTrace();
						logger.error("", e);
					}
					logger.error("Unable to ADD employee to retention bonus reconciliation report. input: ");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.add.failed"));
				}/*finally{
					ResourceManager.close(conn);
				}*/
				break;
			case DELETE:
				try{
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					RetentionBonusRecReportHistoryDao history=RetentionBonusRecReportHistoryDaoFactory.create();
					RetentionBonusReconciliationReportDao bonusReportDao =  RetentionBonusReconciliationReportDaoFactory.create(conn);
					RetentionBonusReconciliationReport bonusReport = bonusReportDao.findByPrimaryKey(bonusForm.getId());
					int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
					String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
					bonusReport.setComments("DELETED : " + (bonusForm.getComments() == null ? "" : bonusForm.getComments()));
					bonusReport.setType(bonusReport.getType()==ADDED?ADDED_DELETED:DELETED);
					bonusReport.setModifiedBy(empName);
					bonusReport.setModifiedOn(new Date());
					RetentionBonusRecReportHistoryDaoFactory.create(conn).insert(bonusReport.getRetentionBonusRecReportHistory());
					bonusReportDao.update(bonusReport.createPk(), bonusReport);
					conn.commit();
					
				} catch (Exception e){
					e.printStackTrace();
					logger.error("Unable to DELETE employee to retention bonus reconciliation report. input: ");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.delete.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			case HOLD:
				try{
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					RetentionBonusReconciliationReportDao bonusReportDao =  RetentionBonusReconciliationReportDaoFactory.create(conn);
					
					RetentionBonusReconciliationReport bonusReport = bonusReportDao.findByPrimaryKey(bonusForm.getId());
					RetentionBonusRecReportHistoryDao history=RetentionBonusRecReportHistoryDaoFactory.create();
					if (bonusReport.getType() == DELETED){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.bonus.hold.deleted.failed"));
					}
					int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
					String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
					bonusReport.setComments("HOLD : " + (bonusForm.getComments() == null ? "" : bonusForm.getComments()));
					bonusReport.setType(HOLD);
					bonusReport.setModifiedBy(empName);
					bonusReport.setModifiedOn(new Date());
					bonusReportDao.update(bonusReport.createPk(), bonusReport);
					RetentionBonusReconciliationHoldDao holdDao =RetentionBonusReconciliationHoldDaoFactory.create(conn);
					RetentionBonusRecReportHistoryDaoFactory.create(conn).insert(bonusReport.getRetentionBonusRecReportHistory());
					holdDao.insert(new RetentionBonusReconciliationHold(bonusReport.getId(), login.getUserId(), bonusReport.getType(), (bonusForm.getComments() == null ? "" : bonusForm.getComments()),new Date()));
					conn.commit();
				} catch (Exception e){
					e.printStackTrace();
					logger.error("Unable to HOLD employee to retention bonus reconciliation report. input: ");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.hold.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			case RELEASE:
				try{
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					RetentionBonusReconciliationReportDao bonusReportDao =  RetentionBonusReconciliationReportDaoFactory.create(conn);
					RetentionBonusReconciliationReport bonusReport = bonusReportDao.findByPrimaryKey(bonusForm.getId());
					RetentionBonusRecReportHistoryDao history=RetentionBonusRecReportHistoryDaoFactory.create();
					if (!(bonusReport.getType() ==HOLD)){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.bonus.release.hold.failed"));
					}
					RetentionBonusReconciliation bonus = RetentionBonusReconciliationDaoFactory.create(conn).findByPrimaryKey(bonusReport.getBonusId());
					int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId(), conn);
					String employeeName = ModelUtiility.getInstance().getEmployeeName(login.getUserId(), conn);
					String empName = employeeName + "(" + empId + ")";
					bonusReport.setComments("RELEASE : " + (bonusForm.getComments() == null ? "" : bonusForm.getComments()));
					if ("Approved".equalsIgnoreCase(bonus.getStatus()) || "Completed".equalsIgnoreCase(bonus.getStatus())){
						if (bonusForm.getStatus() == null) bonusReport.setType(bonusReport.getType() == HOLD ? RELEASED : RELEASED);
						else bonusReport.setType(bonusReport.getType() == HOLD ? REJECTED : REJECTED);
					} else{
						if (bonusForm.getStatus() == null) bonusReport.setType(bonusReport.getType() == HOLD ? MODIFIED : MODIFIED);
						else bonusReport.setType(bonusReport.getType() == HOLD ? DELETED : DELETED);
					}
					bonusReport.setModifiedBy(empName);
					bonusReport.setModifiedOn(new Date());
					RetentionBonusRecReportHistoryDaoFactory.create(conn).insert(bonusReport.getRetentionBonusRecReportHistory());
					bonusReportDao.update(bonusReport.createPk(), bonusReport);
					JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE SUBJECT LIKE ? AND CATEGORY='RETENTION BONUS RECONCILIATION' AND ESR_MAP_ID=? ", new Object[] { "%(" + ModelUtiility.getInstance().getEmployeeId(bonusReport.getUserId(), conn) + ")", bonus.getEsrMapId() });
					RetentionBonusReconciliationHoldDao holdDao = RetentionBonusReconciliationHoldDaoFactory.create(conn);
					if ("Approved".equalsIgnoreCase(bonus.getStatus()) || "Completed".equalsIgnoreCase(bonus.getStatus())){
						holdDao.insert(new RetentionBonusReconciliationHold(bonusReport.getId(), login.getUserId(), bonusReport.getType(), (bonusForm.getComments() == null ? "" : bonusForm.getComments()),new Date()));
						releaseMail(bonus, bonusReport, employeeName, login.getUserId(), conn);
					} else{
						JDBCUtiility.getInstance().update("DELETE FROM RETENTION_BONUS_REC_HOLD WHERE REP_ID=?", new Object[] { bonusReport.getId() });
					}
					conn.commit();
					
					
				} catch (Exception e){
					e.printStackTrace();
					logger.error("Unable to RELEASE employee to bonus reconciliation report. input: ");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.release.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			case CHANGEREPORTINGMGR:{
				RetentionBonusReconciliationReportDao bonusReportDao =  RetentionBonusReconciliationReportDaoFactory.create(conn);
				try {
					conn = ResourceManager.getConnection();
					RetentionBonusReconciliationReport[] bonusReport = bonusReportDao.findByDynamicWhere("BR_ID= ? AND USER_ID= ?",new Object[] {bonusForm.getId(),bonusForm.getUserId()});
				if(bonusReport!=null && bonusReport.length>0){
					for(RetentionBonusReconciliationReport bonus:bonusReport){
						bonus.setManagerId(bonusForm.getManagerId());
						bonus.setManagerName(bonusForm.getManagerName());
						bonusReportDao.update(bonus.createPk(), bonus);}
				}
				} catch (RetentionBonusReconciliationReportDaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();}
			    finally{
				ResourceManager.close(conn);}
			}
			case BANKACCOUNTS:

				try{
					conn = ResourceManager.getConnection();
					RetentionBonusReconciliationReportDao dao = RetentionBonusReconciliationReportDaoFactory.create(conn);
					RetentionBonusReconciliationReport report = dao.findByPrimaryKey(bonusForm.getId());
					if (bonusForm.getStatus() != null){
						report.setAccountType(Short.parseShort(bonusForm.getStatus()));
						dao.update(report.createPk(), report);
					}
				} catch (Exception e){
					e.printStackTrace();
					logger.error("unable to parse " + bonusForm.getStatus() + " as integer while updating bank name of bonus report " + bonusForm.getId() + " Msg: " + e.getMessage());
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bonus.update.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			default:
				break;
		}

		return null;
	}
	
	
	
	private void releaseMail(RetentionBonusReconciliation bonus, RetentionBonusReconciliationReport bonusReport, String approverName, int userId, Connection conn) {
		try{
			PortalMail mail = new PortalMail();
			mail.setTemplateName(MailSettings.RETENTION_BONUS_RELEASE);
			mail.setEmployeeName(ModelUtiility.getInstance().getEmployeeName(bonusReport.getUserId(), conn));
			mail.setMailSubject("Diksha Lynx: Bonus release of " + mail.getEmployeeName());
			mail.setApproverName(approverName);
			mail.setBonusMonth(bonus.getMonth());
			mail.setComments(bonusReport.getComments());
			mail.setActionType(((bonusReport.getType() == RELEASED) ? "relseased" : "rejected"));
			ProcessChain processChainDto = ProcessChainDaoFactory.create(conn).findWhereProcNameEquals("IN_RETENTION_BONUS_RECON")[0];
			ProcessEvaluator pEval = new ProcessEvaluator();
			// get the senior RMG from level 1
			Integer[] srRmg = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);
			// get all the users involved in this hold process, means escalations
			List<Object> escUsers = JDBCUtiility.getInstance().getSingleColumn("SELECT USER_ID FROM RETENTION_BONUS_REC_HOLD WHERE REP_ID=?", new Object[] { bonusReport.getId() }, conn);
			// get the finance users.
			Integer[] finance = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
			Set<String> ccIds = new HashSet<String>();
			for (Object ech : escUsers)
				ccIds.add(((Integer) ech).toString());
			for (Integer ech : srRmg)
				ccIds.add(ech.toString());
			for (Integer ech : finance)
				ccIds.remove(ech.toString());
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
			if (finance != null && finance.length > 0){
				if (finance != null && finance.length == 1){
					mail.setHandlerName(ModelUtiility.getInstance().getEmployeeName(finance[0], conn));
				} else if (finance != null && finance.length > 1){
					StringBuffer toNames = new StringBuffer();
					for (int i = 0; i < finance.length; i++){
						toNames.append(ModelUtiility.getInstance().getEmployeeName(finance[i], conn));
						if (i != finance.length - 1) toNames.append(" / ");
					}
					mail.setHandlerName(toNames.toString());
				}
				mail.setAllReceipientMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE PI.ID IN(SELECT U.PROFILE_ID FROM USERS U WHERE STATUS=0 AND EMP_ID>0 AND U.ID IN(" + ModelUtiility.getCommaSeparetedValues(finance) + "))"));
			}
			if (ccIds != null && ccIds.size() > 0){
				mail.setAllReceipientcCMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE PI.ID IN(SELECT U.PROFILE_ID FROM USERS U WHERE STATUS=0 AND EMP_ID>0 AND U.ID IN(" + ModelUtiility.getCommaSeparetedValues(ccIds) + "))"));
			}
			new MailGenerator().invoker(mail);
		} catch (Exception e){
			e.printStackTrace();
		}
	}


	
	
	
	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access retention bonus reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
		
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ProcessEvaluator pEval = new ProcessEvaluator();
		RetentionBonusReconciliation bonusForm = (RetentionBonusReconciliation) form;
		DropDown dropdown = new DropDown();
		RetentionBonusReconciliationDao bonusDao = RetentionBonusReconciliationDaoFactory.create();
		RetentionBonusReconciliationReqDao bonusReqDao = RetentionBonusReconciliationReqDaoFactory.create();
	try{
		int esrMapId = bonusDao.findByPrimaryKey(bonusForm.getBonusId()).getEsrMapId();
		String msgBody = null;
		RetentionBonusReconciliationReq bonusReq = bonusReqDao.findByDynamicWhere(" BR_ID=? AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[] { bonusForm.getBonusId(), (bonusForm.getEscalatedFromId() > 0) ? bonusForm.getEscalatedFromId() : login.getUserId() })[0];
		int isEscalated = bonusReq.getIsEscalated();
		if (isEscalated != 0){
			RetentionBonusReconciliationReq approverRecord = bonusReqDao.findByPrimaryKey(isEscalated);
			approverRecord.setSubmittedOn(new Date());
			bonusReqDao.update(new RetentionBonusReconciliationReqPk(approverRecord.getId()), approverRecord);
			RetentionBonusReconciliationReq[] otherEscalRecord = bonusReqDao.findWhereIsEscalatedEquals(isEscalated);
			if (otherEscalRecord != null && otherEscalRecord.length > 0){
				for (RetentionBonusReconciliationReq eachReq : otherEscalRecord){
						eachReq.setSubmittedOn(new Date());
						bonusReqDao.update(eachReq.createPk(), eachReq);
				}
			}
		} else if (isEscalated == 0){
			RetentionBonusReconciliationReq[] escalatedRecords = bonusReqDao.findWhereIsEscalatedEquals(bonusReq.getId());
			if (escalatedRecords != null && escalatedRecords.length > 0){
					for (RetentionBonusReconciliationReq eachReq : escalatedRecords){
							eachReq.setSubmittedOn(new Date());
							bonusReqDao.update(eachReq.createPk(), eachReq);
					}
			}
		}
		bonusReq.setSubmittedOn(new Date());
		bonusReqDao.update(new RetentionBonusReconciliationReqPk(bonusReq.getId()), bonusReq);
		
		RetentionBonusReconciliationReq[] bonusReqs = bonusReqDao.findByDynamicWhere(" BR_ID=? AND SEQ_ID=? AND SUBMITTED_ON IS NULL ", new Object[] { bonusForm.getBonusId(), bonusReq.getSeqId() });
		RetentionBonusReconciliation bonus = bonusDao.findByPrimaryKey(bonusForm.getBonusId());
		ProcessChain processChainDto = processChainDao.findWhereProcNameEquals("IN_RETENTION_BONUS_RECON")[0];
		String beginCompletedTag = "<font color=\"#006600\">";
		String endCompletedTag = "</font>";
		String beginPendingTag = "<font color=\"#FF0000\">";
		String endPendingTag = "</font>";
		String bonusString = bonus.getMonth()+"";
		int bonusYear = bonus.getYear();
		String actionTakenBy = ProfileInfoDaoFactory.create().findByDynamicWhere(" ID = (SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName();
		switch (bonusReq.getSeqId()) {
			case 0:{
				// new req rmg l2f2 specialist || rmg l2f3 senior specialist has taken action
				if (bonus.getStatus().equalsIgnoreCase("Report Generated")){
					bonus.setStatus("Submitted");
					bonus.setHtmlStatus(bonus.getStatus());
					bonus.setSubmittedOn(new Date());
					bonusDao.update(new RetentionBonusReconciliationPk(bonus.getId()), bonus);
				
					RetentionBonusReconciliationReq[] reqs = bonusReqDao.findByDynamicWhere(" BR_ID=? AND SEQ_ID=0 AND SUBMITTED_ON IS NULL", new Integer[] { bonus.getId() });
					if (reqs != null && reqs.length > 0){
						for (RetentionBonusReconciliationReq eachReq : reqs){
							eachReq.setSubmittedOn(new Date());
							bonusReqDao.update(new RetentionBonusReconciliationReqPk(eachReq.getId()), eachReq);
						}
					}
					Integer[] nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);
					Integer[] lastLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
					insertIntoReq(nextLevelApprovers, bonusForm.getBonusId(), 1);
					msgBody = sendMail(bonus.getStatus(), nextLevelApprovers, lastLevelApprovers, null, bonusString);// this may need modification
																															
				sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, bonus.getStatus(), bonus.getStatus());
				}
			}
				break;
			case 1:
				try{
						// everybody has taken action
						Integer[] nextLevelApprovers = null;
						int gotoSeqId = 0;
						if(bonus.getStatus().equalsIgnoreCase("Reviewed") || bonus.getStatus().equalsIgnoreCase("Submitted")||bonus.getStatus().equalsIgnoreCase("Re-Submitted")){
							bonus.setStatus("Reviewed and Submitted");
							gotoSeqId = 3;
						} else if (bonus.getStatus().equalsIgnoreCase("Rejected")){
							gotoSeqId = bonusForm.getGotoSeqId();
							if (gotoSeqId == 2){
								bonus.setStatus("Re-Submitted");
							} else{
								bonus.setStatus("Reviewed and Re-Submitted");
							}
						}
						nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), gotoSeqId, 1);
						insertIntoReq(nextLevelApprovers, bonusForm.getBonusId(), gotoSeqId);
						bonus.setHtmlStatus(bonus.getStatus());
						bonusDao.update(new RetentionBonusReconciliationPk(bonus.getId()), bonus);
						Integer[] lastLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
						msgBody = sendMail(bonus.getStatus(), nextLevelApprovers, lastLevelApprovers, null, bonusString);
						sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, bonus.getStatus(), bonus.getStatus());
				} catch (Exception ex){
					ex.printStackTrace();
					logger.error("RETENTION BONUSRECONCILATION UPDATE CASE1: failed to update data", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bonus.update.failed"));
					result.setForwardName("success");
					return result;
				}
				break;
			case 2:
				try{
					// check if everyone has taken action in level 2
					if (!(bonusReqs != null && bonusReqs.length > 0)){
						Integer[] nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);// gotoSeqId=1
						insertIntoReq(nextLevelApprovers,bonusForm.getBonusId(), 1);
						bonus.setStatus("Reviewed");
						bonus.setHtmlStatus("Reviewed");
						bonusDao.update(new RetentionBonusReconciliationPk(bonus.getId()), bonus);
						Integer[] lastLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 4, 1);
						msgBody = sendMail(bonus.getStatus(), nextLevelApprovers, lastLevelApprovers, null, bonusString);
						sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, bonus.getStatus(), bonus.getStatus());
					} else{
						// there are people who must take action..
						ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
						StringBuilder statusWithName = new StringBuilder();
						Integer[] secondLevelApproverIds = pEval.approvers(processChainDto.getApprovalChain(), 2, 1);// gotoSeqId=1
						for (Integer eachAppId : secondLevelApproverIds){
							try{
								RetentionBonusReconciliationReq fetchedBonusReq = bonusReqDao.findByDynamicWhere(" BR_ID=? AND SEQ_ID=2 AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[] { bonusForm.getBonusId(), eachAppId })[0];
								ProfileInfo managerProfileInfo = profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { fetchedBonusReq.getAssignedTo() })[0];
								if (fetchedBonusReq.getSubmittedOn() == null){
									statusWithName.append(beginPendingTag).append(managerProfileInfo.getFirstName()).append(endPendingTag).append(" , ");
								} else{
									statusWithName.append(beginCompletedTag).append(managerProfileInfo.getFirstName()).append(endCompletedTag).append(" , ");
								}
							} catch (Exception e){}
						}
						int lastIndexOfComma = statusWithName.toString().lastIndexOf(',');
						statusWithName = statusWithName.replace(lastIndexOfComma, statusWithName.length(), " ");
						bonus.setHtmlStatus("Awaiting Approval[ <b>" + statusWithName.toString().trim() + " </b>]");
						bonus.setStatus("Awaiting Approvals");
						bonusDao.update(new RetentionBonusReconciliationPk(bonus.getId()), bonus);
					}
				} catch (Exception ex){
					ex.printStackTrace();
					logger.error("RETENTION BONUSRECONCILATION UPDATE CASE2 : failed to update data", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bonus.update.failed"));
					result.setForwardName("success");
					return result;
				}
				break;
			case 3:
				/*
				 * People who belong to this sequence can REJECT
				 */
				try{
					if (bonusForm.getCommentsByFinanceTeam() != null && bonusForm.getCommentsByFinanceTeam().length() > 0){
						// last stage of bonus request !
						bonus.setStatus("Completed");
						bonus.setHtmlStatus("Completed");
						bonusDao.update(new RetentionBonusReconciliationPk(bonus.getId()), bonus);
						Set<Integer> userIdsInProcessChain = new HashSet<Integer>();
						for (int appLevel = 1; appLevel <= 4; appLevel++){
							Integer[] approverIds = pEval.approvers(processChainDto.getApprovalChain(), appLevel, 1);
							for (Integer eachApproverId : approverIds){
								userIdsInProcessChain.add(eachApproverId);
							}
						}
						Integer[] handlerIds = pEval.handlers(processChainDto.getHandler(), 1);
						for (Integer eachHandlerId : handlerIds){
							userIdsInProcessChain.add(eachHandlerId);
						}
						Integer[] toIds = new Integer[userIdsInProcessChain.size()];
						userIdsInProcessChain.toArray(toIds);
						msgBody = sendMail(bonus.getStatus(), toIds, null, bonusForm.getCommentsByFinanceTeam(), bonusString);
						sendInboxNotification(toIds, esrMapId, msgBody, bonus.getStatus(), bonus.getStatus());
						bonus.setCompletedOn(new Date());
						bonusDao.update(new RetentionBonusReconciliationPk(bonus.getId()), bonus);
						sendDisbursalMailToEmployees(bonus.getId(),bonusString,bonusYear);
						break;
					}
					Integer[] nextLevelApprovers = null, toIds = null;
					int gotoSeqId = 0;
					if(bonus.getStatus().equalsIgnoreCase("Reviewed and Submitted")||
							bonus.getStatus().equalsIgnoreCase("Reviewed and Re-Submitted")){
					if (bonusForm.getIsRejected().equalsIgnoreCase("TRUE")){
						gotoSeqId = 1;
						bonus.setStatus("Rejected");
						//toIds = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
						toIds = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);
					} else{
						gotoSeqId = 4;
						bonus.setStatus("Accepted");
						toIds = null;
					}
					
					nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), gotoSeqId, 1);
					insertIntoReq(nextLevelApprovers, bonusForm.getBonusId(), gotoSeqId);
					bonus.setHtmlStatus(bonus.getStatus());
					bonusDao.update(new RetentionBonusReconciliationPk(bonus.getId()), bonus);
					msgBody = sendMail(bonus.getStatus(), nextLevelApprovers, toIds, actionTakenBy, bonusString);// common to Accepted/Rejected
					sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, bonus.getStatus(), bonus.getStatus());
					}
				} catch (Exception ex){
					ex.printStackTrace();
					logger.error("RETENTON BONUSRECONCILATION UPDATE CASE3 : failed to update data", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bonus.update.failed"));
					result.setForwardName("success");
					return result;
				}
				break;
			case 4:
				try{
					Integer[] nextLevelApprovers = null;
					if (bonusForm.getIsRejected().equalsIgnoreCase("TRUE")){
						bonus.setStatus("Rejected");
						bonus.setHtmlStatus("Rejected");
						bonusDao.update(new RetentionBonusReconciliationPk(bonus.getId()), bonus);
						nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);
						insertIntoReq(nextLevelApprovers, bonusForm.getBonusId(), 1);
						msgBody = sendMail(bonus.getStatus(), nextLevelApprovers, null, actionTakenBy, bonusString);
						sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, bonus.getStatus(), bonus.getStatus());
					} else{
						String currentStatus = bonusDao.findByPrimaryKey(bonus.getId()).getStatus();
						if (currentStatus.equalsIgnoreCase("Pending Approval") || currentStatus.equalsIgnoreCase("Accepted") ){
							// both have approved..send this info to the person involved in the Finance team
							bonus.setStatus("Approved");
							bonus.setHtmlStatus("Approved");
							//nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 2, 1);
							nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
							insertIntoReq(nextLevelApprovers, bonusForm.getBonusId(), 3);
							msgBody = sendMail(bonus.getStatus(), nextLevelApprovers, null, null, bonusString);
							sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, bonus.getStatus(), bonus.getStatus());
						} else{
							// only one person has approved
							bonus.setStatus("Pending Approval");
							ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
							String actionTaken = "Approved By:[ <b>" + beginCompletedTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName() + endCompletedTag + " ,";
							List<Integer> pendingApproverIds = new ArrayList(Arrays.asList(pEval.approvers(processChainDto.getApprovalChain(), 4, 1)));
							pendingApproverIds.remove(login.getUserId());
							for (Iterator<Integer> idIter = pendingApproverIds.iterator(); idIter.hasNext();){
								actionTaken += beginPendingTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { idIter.next() })[0].getFirstName() + endPendingTag;
							}
							bonus.setHtmlStatus(actionTaken + "</b> ]");
							bonus.setActionButtonVisibility("disabled");
						}
						bonusDao.update(new RetentionBonusReconciliationPk(bonus.getId()), bonus);
					}
				} catch (Exception ex){
					ex.printStackTrace();
					logger.error("RETENTION BONUSRECONCILATION UPDATE CASE4 : failed to update data", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bonus.update.failed"));
					result.setForwardName("success");
					return result;
				}
				break;
		}
	}catch (Exception ex){
		ex.printStackTrace();
		logger.error("RETENTION BONUSRECONCILATION UPDATE : failed to update data", ex);
		result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
		result.setForwardName("success");
		return result;
	}
	dropdown.setDropDown(null);
	request.setAttribute("actionForm", "");
	result.setForwardName("success");	
	return null;
	}
	
	private void sendInboxNotification(Integer[] receiverIds, int esrMapId, String msgBody, String status, String subject) throws InboxDaoException {
		Inbox inbox = null;
		InboxDao inboxDao = InboxDaoFactory.create();
		for (Integer eachReceiverid : receiverIds){
			inbox = new Inbox();
			inbox.setReceiverId(eachReceiverid);
			inbox.setEsrMapId(esrMapId);
			inbox.setSubject(subject);
			inbox.setAssignedTo(0);
			inbox.setRaisedBy(1);// what should be the value??
			inbox.setCreationDatetime(new Date());
			inbox.setStatus(status);
			inbox.setCategory("RETENTION_BONUS_REPORT");
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setMessageBody(msgBody);
			inboxDao.insert(inbox);
		}
	}
	
	
	private void insertIntoReq(Integer[] approverIds, int bonusId, int seqId) throws RetentionBonusReconciliationReqDaoException, ProfileInfoDaoException {
		RetentionBonusReconciliationReq bonusReq = null;
		RetentionBonusReconciliationReqDao bonusReqDao = RetentionBonusReconciliationReqDaoFactory.create();
		for (Integer eachApproverId : approverIds){
			bonusReq = new RetentionBonusReconciliationReq();
			bonusReq.setBonusId(bonusId);
			bonusReq.setSeqId(seqId);
			bonusReq.setAssignedTo(eachApproverId);
			bonusReq.setReceivedOn(new Date());
			bonusReq.setSubmittedOn(null);// required to check if all have taken action in a particular sequence
			bonusReq.setIsEscalated(0);
			bonusReqDao.insert(bonusReq);
		}
	}	
	
	private String sendMail(String status, Integer[] toIds, Integer[] ccIds, String args, String bonusMonth) throws FileNotFoundException, ProfileInfoDaoException, AddressException, UnsupportedEncodingException, MessagingException {
		String templateName = "";
		String subject = status;
		String msgBody = "";
		
		PortalMail portalMail = new PortalMail();
		portalMail.setBonusMonth(bonusMonth); 
		if (status.equalsIgnoreCase("Reviewed")){
			subject = "Retention Bonus Report reviewed by Business for " + bonusMonth;
			templateName = MailSettings.RETENTION_BONUS_REVIEWED;
		} else if (status.equalsIgnoreCase("Rejected")){
			subject = "Retention Bonus Report for " + bonusMonth + " Rejected by " + args;
			templateName = MailSettings.RETENTION_BONUS_REJECTED;
		} else if (status.equalsIgnoreCase("Accepted")){
			subject = "Retention Bonus Report for " + bonusMonth + " Accepted by " + args;
			templateName = MailSettings.RETENTION_BONUS_ACCEPTED;
		} else if (status.equalsIgnoreCase("Approved")){
			subject = "Final Retention Bonus Report for " + bonusMonth + " Approved";
			templateName = MailSettings.RETENTION_BONUS_APPROVED;
		} else if (status.equalsIgnoreCase("Report Generated")){
			templateName = MailSettings.RETENTION_BONUS_REPORT_AUTO_GENERATED;
			subject = "Retention Bonus Report Generated for " + bonusMonth;
		} else if (status.equalsIgnoreCase("Completed")){
			subject = "Final Retention Bonus Report for " + bonusMonth + " ready for disbursal";
			templateName = MailSettings.RETENTION_BONUS_COMPLETED;
		} else if (status.equalsIgnoreCase("Reviewed and Submitted")){
			subject = "Retention Bonus Report reviewed by RMG for " + bonusMonth;
			templateName = MailSettings.RETENTION_BONUS_REVIEWED_SUBMITTED;
		} else{
			/*
			 * Retention Bonus Details Submitted
			 * Final Retention Bonus Details Submitted
			 * Retention Bonus Details Re-Submitted
			 */
			templateName = MailSettings.RETENTION_BONUS_SUBMITTED;
			subject = "Diksha Lynx: Bonus Report submitted for "+bonusMonth;
		}
		portalMail.setMailSubject(subject);
		portalMail.setTemplateName(templateName);
		portalMail.setCommentsByFinanceTeam(args);
		portalMail.setBonusAcceptedOrApproved(status);
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
				//portalMail.setBonusMonth(bonusMonth.toString());
			}
					String ids= Arrays.asList(toIds).toString().replace('[', ' ').replace(']', ' ').trim();
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
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		
		return null;
	}
	
	
	public Attachements download(PortalForm form) {
		Login login = (Login) form.getObject();
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access retention bonus reconciliation receive Urls without having permisson at :" + new Date());
			return null;
		}
		Attachements attachements = new Attachements();
		RetentionBonusReconciliation bonusForm = (RetentionBonusReconciliation) form;
		PortalData portalData = new PortalData();
		String path = portalData.getfolder(portalData.getDirPath()) + "temp";
		try{
			File file = new File(path);
			if (!file.exists()) file.mkdir();
			RetentionBonusReconciliation bonus = RetentionBonusReconciliationDaoFactory.create().findByPrimaryKey(bonusForm.getBonusId());
			switch (DownloadTypes.getValue(form.getdType())) {
				case RETENTIONBONUSREPORT:
					try{
						String month = " Retention Bonus for month " + bonus.getMonth() + " " + bonus.getYear();
						attachements.setFileName(new GenerateXls().generateRBonusReportInExcel(RetentionBonusReconciliationReportDaoFactory.create().findInternalReportData(bonusForm.getBonusId()), path + File.separator + "Retention Bonus Internal Report_" + month + ".xls", month));
					} catch (Exception e){
						logger.error("unable to generate retention bonus report list", e);
					}
					break;
			}
			path = path + File.separator + attachements.getFileName();
			attachements.setFilePath(path);
		} catch (Exception e){
			e.printStackTrace();
		}
		return attachements;
	}
	
/*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! These are Escalation Part !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

	public static void main(String[] args) {
		JDBCUtiility.getInstance().update("UPDATE RETENTION_BONUS_REC_HOLD SET ACTION_ON = ADDDATE(ACTION_ON, INTERVAL -1 DAY)", null);
		new RetentionBonusReconciliationModel().escalationforBonusHold();
	}
	
	public void escalationforBonusHold() {
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			RetentionBonusReconciliationHold bonusHold[] = RetentionBonusReconciliationHoldDaoFactory.create(conn).findByDynamicSelect("SELECT BRH.* FROM RETENTION_BONUS_REC_HOLD BRH JOIN RETENTION_BONUS_REC_REPORT BRR ON BRR.ID=BRH.REP_ID WHERE BRR.TYPE=" + RetentionBonusReconciliationModel.HOLD + " ORDER BY BRH.ID", null);
			Map<String, Date> dateMap = new HashMap<String, Date>();
			for (RetentionBonusReconciliationHold hold :bonusHold){
				int daysCrossed = EscalationJob.getWorkingDaysCount(1, hold.getActionOn(), new Date());
				if (hold.getEscFrom() == 0 && hold.getActionOn() != null) dateMap.put(hold.getRepId() + "", hold.getActionOn());
				if (daysCrossed == 7 && hold.getEscFrom() == 0){
					sendBonusHoldTask(hold, conn, daysCrossed);
				} else if ((daysCrossed == 9 && hold.getEscFrom() == 0) || (daysCrossed == 2 && hold.getEscFrom() != 0)){
					if (hold.getEscFrom() > 0 && dateMap.containsKey(hold.getRepId() + "")) hold.setActionOn(dateMap.get(hold.getRepId() + ""));//make sure mail should contain actual hold date.
					escBonusHold(hold, conn, daysCrossed);
				} else if (daysCrossed >= 3 || hold.getEscFrom() > 0){
					if (dateMap.containsKey(hold.getRepId() + "")) hold.setActionOn(dateMap.get(hold.getRepId() + ""));//make sure mail should contain actual hold date.
					reminderBonusHold(hold, conn);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			ResourceManager.close(conn);
		}
	}

	
	private void sendBonusHoldTask(RetentionBonusReconciliationHold hold, Connection conn, int days) {
		try{
			RetentionBonusReconciliationReport bonusReport = RetentionBonusReconciliationReportDaoFactory.create(conn).findByPrimaryKey(hold.getRepId());
			RetentionBonusReconciliation bonus = RetentionBonusReconciliationDaoFactory.create(conn).findByPrimaryKey(bonusReport.getBonusId());
			PortalMail mail = new PortalMail();
			mail.setTemplateName(MailSettings.RETENTION_BONUS_HOLD_TASK);
			mail.setEmployeeName(ModelUtiility.getInstance().getEmployeeName(bonusReport.getUserId(), conn));
			mail.setMailSubject("Diksha Lynx: Retention Bonus on hold of " + mail.getEmployeeName());
		    mail.setDaysCrossed(days + "");
		    mail.setBonusMonth(bonusReport.getMonth());
			mail.setOnDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(hold.getActionOn()));
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
			ProfileInfo profileInfo = profileInfoDao.findWhereUserIdEquals(hold.getUserId());
			mail.setApproverName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
			mail.setRecipientMailId(profileInfo.getOfficalEmailId());
			MailGenerator generator = new MailGenerator();
			generator.invoker(mail);
			String bodyText = generator.replaceFields(generator.getMailTemplate(mail.getTemplateName()), mail);
			InboxModel inbox = new InboxModel();
			inbox.populateInbox(bonus.getEsrMapId(), mail.getMailSubject() + "(" + ModelUtiility.getInstance().getEmployeeId(bonusReport.getUserId(), conn) + ")", "On Hold", hold.getUserId(), hold.getUserId(), 1, bodyText, "BONUS_RECON");
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void escBonusHold(RetentionBonusReconciliationHold hold, Connection conn, int days) {
		try{
			RetentionBonusReconciliationReport bonusReport = RetentionBonusReconciliationReportDaoFactory.create(conn).findByPrimaryKey(hold.getRepId());
			RetentionBonusReconciliation bonus = RetentionBonusReconciliationDaoFactory.create(conn).findByPrimaryKey(bonusReport.getBonusId());
			ProcessChain processChainDto = ProcessChainDaoFactory.create(conn).findWhereProcNameEquals("IN_RETENTION_BONUS_RECON")[0];
			ProcessEvaluator pEval = new ProcessEvaluator();
			// get the senior RMG from leavel 1
			List<String> srRmg = getList(pEval.approvers(processChainDto.getApprovalChain(), 1, 1));
			// get the vp users.
			List<String> toList = null;
			if (hold.getEscFrom() == 0){
				if (srRmg.contains(hold.getUserId() + "")){
					toList = getList(pEval.approvers(processChainDto.getApprovalChain(), 4, 1));
				} else{
					toList = srRmg;
				}
			} else{
				List<String> vp = getList(pEval.approvers(processChainDto.getApprovalChain(), 4, 1));
				if (!vp.contains(hold.getUserId() + "")){
					toList = vp;
				} else return;
			}
			if (toList != null && !toList.isEmpty()){
				//RetentionDepPerdiemHoldDao holdDao = RetentionDepPerdiemHoldDaoFactory.create(conn);
				 DepPerdiemHoldDao holdDao =  DepPerdiemHoldDaoFactory.create(conn);
				PortalMail mail = new PortalMail();
				mail.setTemplateName(MailSettings.RETENTION_BONUS_HOLD_ESC);
				mail.setEmployeeName(ModelUtiility.getInstance().getEmployeeName(bonusReport.getUserId(), conn));
				mail.setMailSubject("Diksha Lynx: Escalation: Bonus on hold Of " + mail.getEmployeeName());
				mail.setBonusMonth(bonusReport.getMonth());
				mail.setDaysCrossed(days + "");
				mail.setOnDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(hold.getActionOn()));
				ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
				if (toList.size() == 1){
					mail.setHandlerName(ModelUtiility.getInstance().getEmployeeName(Integer.parseInt(toList.get(0)), conn));
				} else if (toList.size() > 1){
					StringBuffer toNames = new StringBuffer();
					for (int i = 0; i < toList.size(); i++){
						toNames.append(ModelUtiility.getInstance().getEmployeeName(Integer.parseInt(toList.get(i)), conn));
						if (i != toList.size() - 1) toNames.append(" / ");
					}
					mail.setHandlerName(toNames.toString());
				}
				mail.setAllReceipientMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE PI.ID IN(SELECT U.PROFILE_ID FROM USERS U WHERE STATUS=0 AND EMP_ID>0 AND U.ID IN(" + ModelUtiility.getCommaSeparetedValues(toList) + "))"));
				ProfileInfo profileInfo = profileInfoDao.findWhereUserIdEquals(hold.getUserId());
				mail.setAllReceipientcCMailId(new String[] { profileInfo.getOfficalEmailId() });
				mail.setApproverName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
				MailGenerator generator = new MailGenerator();
				generator.invoker(mail);
				String bodyText = generator.replaceFields(generator.getMailTemplate(mail.getTemplateName()), mail);
				InboxModel inbox = new InboxModel();
				String subject = mail.getMailSubject() + "(" + ModelUtiility.getInstance().getEmployeeId(bonusReport.getUserId(), conn) + ")";
				for (String id : toList){
					//holdDao.insert(new RetentionDepPerdiemHold(hold.getRepId(), Integer.parseInt(id), 7, "", hold.getId()));
					holdDao.insert(new  DepPerdiemHold(hold.getRepId(), Integer.parseInt(id), 7, "", hold.getId()));
					inbox.populateInbox(bonus.getEsrMapId(), subject, "On Hold", Integer.parseInt(id), Integer.parseInt(id), 1, bodyText, "PERDIEM_RECON");
				}
				inbox.populateInbox(bonus.getEsrMapId(), mail.getMailSubject(), "On Hold", 0, hold.getUserId(), 1, bodyText, "RETENTION_BONUS_REPORT");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private List<String> getList(Object[] array) {
		List<String> l = new ArrayList<String>();
		for (Object o : array)
			l.add(o.toString());
		return l;
	}
                                                 
	private void reminderBonusHold(RetentionBonusReconciliationHold hold, Connection conn) {
		try{
			PortalMail mail = new PortalMail();
			mail.setTemplateName(MailSettings.RETENTION_BONUS_HOLD_REMINDER);
			RetentionBonusReconciliationReport bonusReport = RetentionBonusReconciliationReportDaoFactory.create(conn).findByPrimaryKey(hold.getRepId());
			RetentionBonusReconciliation bonus = RetentionBonusReconciliationDaoFactory.create(conn).findByPrimaryKey(bonusReport.getBonusId());
			mail.setEmployeeName(ModelUtiility.getInstance().getEmployeeName(bonusReport.getUserId(), conn));
			mail.setMailSubject("Diksha Lynx: Bonus on hold reminder of " + mail.getEmployeeName());
			mail.setBonusMonth(bonusReport.getMonth());
			mail.setOnDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(hold.getActionOn()));
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
			ProfileInfo profileInfo = profileInfoDao.findWhereUserIdEquals(hold.getUserId());
			mail.setRecipientMailId(profileInfo.getOfficalEmailId());
			mail.setHandlerName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
			MailGenerator generator = new MailGenerator();
			generator.invoker(mail);
			String bodyText = generator.replaceFields(generator.getMailTemplate(mail.getTemplateName()), mail);
			InboxModel inbox = new InboxModel();
			inbox.populateInbox(bonus.getEsrMapId(), mail.getMailSubject(), "On Hold", 0, hold.getUserId(), 1, bodyText, "RETENTION_BONUS_REPORT");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private List<RetentionBonusReconciliation> receiveEscalations(int loggedInUserId) throws ProfileInfoDaoException {
		RetentionEmpSerReqMapDao esrDao = RetentionEmpSerReqMapDaoFactory.create();
		RetentionBonusReconciliationDao bonusDao = RetentionBonusReconciliationDaoFactory.create();
		RetentionBonusReconciliationReqDao bonusReqDao = RetentionBonusReconciliationReqDaoFactory.create();
		List<RetentionBonusReconciliation> bonusList = new ArrayList<RetentionBonusReconciliation>();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		try{
			RetentionBonusReconciliationReq[] bonusReqs = bonusReqDao.findByDynamicWhere(" ASSIGNED_TO=? AND IS_ESCALATED !=0 AND SUBMITTED_ON IS NULL ORDER BY ID DESC", new Object[] { loggedInUserId });
			RetentionEmpSerReqMap esr = null;
			if (bonusReqs != null && bonusReqs.length > 0){
				for (RetentionBonusReconciliationReq eachBonusReq : bonusReqs){
					RetentionBonusReconciliation tempBonus = bonusDao.findByPrimaryKey(eachBonusReq.getBonusId());
					esr = esrDao.findByPrimaryKey(tempBonus.getEsrMapId());
					tempBonus.setReqId(esr.getReqId());
					tempBonus.setStatus(tempBonus.getHtmlStatus());
					tempBonus.setGotoSeqId(eachBonusReq.getSeqId());
					
					if (eachBonusReq.getSubmittedOn() == null) tempBonus.setActionButtonVisibility("enable");
					int assignedTo = bonusReqDao.findByPrimaryKey(eachBonusReq.getIsEscalated()).getAssignedTo();
					ProfileInfo profileActionNotTaken = profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Integer[] { assignedTo })[0];
					tempBonus.setEscalatedFrom(profileActionNotTaken.getFirstName());
					tempBonus.setEscalatedFromId(assignedTo);
					bonusList.add(tempBonus);
				}
			}
		} catch (RetentionBonusReconciliationReqDaoException e){
			logger.error("There is RetentionBonusReqDaoException occured while querying the escalated bonus records for the user " + loggedInUserId);
		} catch (RetentionBonusReconciliationDaoException e){
			e.printStackTrace();
		} catch (RetentionEmpSerReqMapDaoException e){
			e.printStackTrace();
		}
		return bonusList;
	}
	
	///////////////
public int findintFormatofMonth(String  month){
		
		int MonthVal=0;
		if(month.equalsIgnoreCase("JANUARY")){
			MonthVal=01;
		}else if(month.equalsIgnoreCase("FEBRUARY")){
			MonthVal=02;
		}else if(month.equalsIgnoreCase("MARCH")){
			MonthVal=03;
		}else if(month.equalsIgnoreCase("APRIL")){
			MonthVal=04;
		}else if(month.equalsIgnoreCase("MAY")){
			MonthVal=05;
		}else if(month.equalsIgnoreCase("JUNE")){
			MonthVal=06;
		}else if(month.equalsIgnoreCase("JULY")){
			MonthVal=07;
		}else if(month.equalsIgnoreCase("AUGUST")){
			MonthVal=8;
		}else if(month.equalsIgnoreCase("SEPTEMBER")){
			MonthVal=9;
		}else if(month.equalsIgnoreCase("OCTOBER")){
			MonthVal=10;
		}else if(month.equalsIgnoreCase("NOVEMBER")){
			MonthVal=11;
		}else if(month.equalsIgnoreCase("DECEMBER")){
			MonthVal=12;
		}
		return MonthVal;
		
	}
	public String findStringFormatofMonth(int value){
		String month=""; 
			if(value==03 || value==3)       month= "MARCH"; 
			else if(value==06 || value==6)  month= "JUNE";
			else if(value==9)  month= "SEPTEMBER";
			else if(value==12) month= "DECEMBER";
			
			else if(value==01 || value==1) month= "JANUARY";
			else if(value==02 || value==2) month= "FEBRUARY";
			else if(value==04 || value==4) month= "APRIL";
			else if(value==05 || value==5) month= "MAY";
			else if(value==07 || value==7) month= "JULY";
			else if(value==8) month= "AUGUST";
			else if(value==10) month= "OCTOBER";
			else if(value==11) month= "NOVEMBER";
		
		return month;
	}
	public int LastDateOfTheMonth ( int MonthVal){
		if(MonthVal == 01 || MonthVal == 1  ) MonthVal=31;
		else if(MonthVal == 02 || MonthVal == 2 ) MonthVal=28;
		else if(MonthVal == 03 || MonthVal == 3 ) MonthVal=31;
		else if(MonthVal == 04 || MonthVal == 4 ) MonthVal=30;
		else if(MonthVal == 05 || MonthVal == 5 ) MonthVal=31;
		else if(MonthVal == 06 || MonthVal == 6 ) MonthVal=30;
		else if(MonthVal == 07 || MonthVal == 7 ) MonthVal=31;
		else if(MonthVal == 8) MonthVal=31;
		else if(MonthVal == 9) MonthVal=30;
		else if(MonthVal == 10) MonthVal=31;
		else if(MonthVal == 11) MonthVal=30;
		else if(MonthVal == 12) MonthVal=31;
		
		return MonthVal;
	}
	//After completed bonus then mail send to employee
	private void sendDisbursalMailToEmployees(int id ,  String bonusMonth, int bonusYear){
		int BonusYear=0;
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			RetentionBonusReconciliationReport[] depPerdiemReports = RetentionBonusReconciliationReportDaoFactory.create(conn).findWhereBonusIdEquals(id);
			
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
			PortalMail portalMail = new PortalMail();
			portalMail.setTemplateName(MailSettings.RETENTION_BONUS_DISBURSAL);
			MailGenerator mailGenerator = new MailGenerator();
			DepPerdiemExchangeRates[] rates =  DepPerdiemExchangeRatesDaoFactory.create(conn).findWhereRepIdEquals(id);
			Map<String, Double> exchangeRates = new HashMap<String, Double>();
			if (rates != null && rates.length > 0){
				for ( DepPerdiemExchangeRates rate : rates)
					exchangeRates.put(rate.getCurrencyType() + "", rate.getAmount());
			}
			Map<String, List<RetentionBonusReconciliationReport>> depPerdiemReportsMap = new HashMap<String, List<RetentionBonusReconciliationReport>>();
			for (RetentionBonusReconciliationReport report : depPerdiemReports){
				if (report.getType() == DELETED || report.getType() == FIXED_DELETED ||report.getType() == HOLD ||report.getType() == FIXED_HOLD) continue;
				List<RetentionBonusReconciliationReport> reports = depPerdiemReportsMap.get(report.getUserId() + "");
				if (reports == null){
					reports = new ArrayList<RetentionBonusReconciliationReport>();
					depPerdiemReportsMap.put(report.getUserId() + "", reports);
				}
				reports.add(report);
			}
			for (Map.Entry<String, List<RetentionBonusReconciliationReport>> entry : depPerdiemReportsMap.entrySet()){
			try{
					List<RetentionBonusReconciliationReport> reportsList = entry.getValue();
					Collections.sort(reportsList);
					Date fromDate = null, toDate = null;
					int userId = 0;
					if (reportsList.size() > 0){
						for (RetentionBonusReconciliationReport report : reportsList){
							userId = report.getUserId();
							if (fromDate == null) fromDate = report.getFrom();
							else if (fromDate.after(report.getFrom())) fromDate = report.getFrom();
							if (toDate == null) toDate = report.getTo();
							else if (toDate.before(report.getTo())) toDate = report.getTo();
						}
					}
					portalMail.setBonusMonth(bonusMonth);
					portalMail.setMailSubject("Diksha: Retention Bonus Statement: " + bonusMonth);
					ProfileInfo info = profileInfoDao.findWhereUserIdEquals(userId);
					portalMail.setEmployeeName(info.getFirstName() + " " + info.getLastName());
					portalMail.setRecipientMailId(info.getOfficalEmailId());
					if (info.getHrSpoc() > 3) portalMail.setAllReceipientcCMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE PI.ID IN(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID>3 AND U.ID IN(" + info.getHrSpoc() + "))"));
					StringBuffer body = new StringBuffer();
					double total = 0;
					
					for (RetentionBonusReconciliationReport report : reportsList){
						
						List<Object> BonusTerm = new ArrayList<Object>();
						String[] EmpRetentionInfoDates = null;
						int EmpRetentionDataIns = 0;
						String EmpRetentionDataAmt = null;
						String EmpBonusMonth= null;
						String EmpBonusMonthAugSept = null;
						int EmpBonusM = 0;
						BonusTerm = JDBCUtiility.getInstance().getSingleColumn("SELECT RETENTION_INSTALLMENTS FROM RETENTION_BONUS WHERE USER_ID=?",new Object[] {report.getUserId()} );
						RetentionBonusDao retentionData = RetentionBonusDaoFactory.create(conn);
						RetentionBonus[] RetentionData = retentionData.findWhereUserIdEquals(report.getUserId());
						for(RetentionBonus Retentiondata : RetentionData){
							 Date EmpRetentionData = Retentiondata.getRetentionStartDate();
							 EmpRetentionInfoDates = EmpRetentionData.toString().split("-");
							 EmpRetentionDataIns = Retentiondata.getRetentionInstallments();
							 EmpRetentionDataAmt = Retentiondata.getAmount();
						}
						body.append("<tr><td>");
						//body.append("Retention Bonus"+EmpRetentionDataIns);
						body.append("Retention Bonus");
						body.append("</td><td align='center'>");
						/*if(EmpRetentionDataIns == 1){
							if(EmpRetentionInfoDates[1].equalsIgnoreCase("08") || EmpRetentionInfoDates[1].equalsIgnoreCase("09") ){
								
								EmpBonusM =(Integer.parseInt(EmpRetentionInfoDates[1].substring(1)));
								EmpBonusMonth  = findStringFormatofMonth(EmpBonusM);
								 
							}else if(EmpRetentionInfoDates[1].equalsIgnoreCase("01") || EmpRetentionInfoDates[1].equalsIgnoreCase("02") || EmpRetentionInfoDates[1].equalsIgnoreCase("03")||
									EmpRetentionInfoDates[1].equalsIgnoreCase("04") || EmpRetentionInfoDates[1].equalsIgnoreCase("05")  ||
									EmpRetentionInfoDates[1].equalsIgnoreCase("06") || EmpRetentionInfoDates[1].equalsIgnoreCase("07")){
								
								EmpBonusM =(Integer.parseInt(EmpRetentionInfoDates[1]));
								EmpBonusMonth  = findStringFormatofMonth(EmpBonusM);
								 
							}else if(EmpRetentionInfoDates[1].equalsIgnoreCase("11") || EmpRetentionInfoDates[1].equalsIgnoreCase("12") || EmpRetentionInfoDates[1].equalsIgnoreCase("10")){
								
								EmpBonusM =(Integer.parseInt(EmpRetentionInfoDates[1]));
								EmpBonusMonth  = findStringFormatofMonth(EmpBonusM);
								 
							}
						}else if(EmpRetentionDataIns == 2){
							if(EmpRetentionInfoDates[1].equalsIgnoreCase("08") || EmpRetentionInfoDates[1].equalsIgnoreCase("09") ){
								
								EmpBonusM =(Integer.parseInt(EmpRetentionInfoDates[1].substring(1))+2);
								EmpBonusMonth  = findStringFormatofMonth(EmpBonusM);
								 
							}else if(EmpRetentionInfoDates[1].equalsIgnoreCase("01") || EmpRetentionInfoDates[1].equalsIgnoreCase("02") || EmpRetentionInfoDates[1].equalsIgnoreCase("03")||
									EmpRetentionInfoDates[1].equalsIgnoreCase("04") || EmpRetentionInfoDates[1].equalsIgnoreCase("05")  ||
									EmpRetentionInfoDates[1].equalsIgnoreCase("06") || EmpRetentionInfoDates[1].equalsIgnoreCase("07") || EmpRetentionInfoDates[1].equalsIgnoreCase("10") ){
								
								EmpBonusM =(Integer.parseInt(EmpRetentionInfoDates[1])+2);
								EmpBonusMonth  = findStringFormatofMonth(EmpBonusM);
								 
							}else if(EmpRetentionInfoDates[1].equalsIgnoreCase("11") || EmpRetentionInfoDates[1].equalsIgnoreCase("12")){
								
								EmpBonusM =(Integer.parseInt(EmpRetentionInfoDates[1])-10);
								EmpBonusMonth  = findStringFormatofMonth(EmpBonusM); 
								 
							}
						}else if(EmpRetentionDataIns == 3){
							if(EmpRetentionInfoDates[1].equalsIgnoreCase("08") || EmpRetentionInfoDates[1].equalsIgnoreCase("09") ){
								
								EmpBonusM =(Integer.parseInt(EmpRetentionInfoDates[1].substring(1))-7);
								EmpBonusMonth  = findStringFormatofMonth(EmpBonusM);
								 
							}else if(EmpRetentionInfoDates[1].equalsIgnoreCase("01") || EmpRetentionInfoDates[1].equalsIgnoreCase("02") || EmpRetentionInfoDates[1].equalsIgnoreCase("03")||
									EmpRetentionInfoDates[1].equalsIgnoreCase("04") || EmpRetentionInfoDates[1].equalsIgnoreCase("05")  ||
									EmpRetentionInfoDates[1].equalsIgnoreCase("06") || EmpRetentionInfoDates[1].equalsIgnoreCase("07")){
								
								EmpBonusM =(Integer.parseInt(EmpRetentionInfoDates[1])+5);
								EmpBonusMonth  = findStringFormatofMonth(EmpBonusM);
								 
							}else if(EmpRetentionInfoDates[1].equalsIgnoreCase("11") || EmpRetentionInfoDates[1].equalsIgnoreCase("12") || EmpRetentionInfoDates[1].equalsIgnoreCase("10")){
								
								EmpBonusM =(Integer.parseInt(EmpRetentionInfoDates[1])-7);
								EmpBonusMonth  = findStringFormatofMonth(EmpBonusM);
								 
							}
						}else if(EmpRetentionDataIns == 4){
							if(EmpRetentionInfoDates[1].equalsIgnoreCase("08") || EmpRetentionInfoDates[1].equalsIgnoreCase("09") ){
								
								EmpBonusM =(Integer.parseInt(EmpRetentionInfoDates[1].substring(1))-1);
								EmpBonusMonth  = findStringFormatofMonth(EmpBonusM);
								 
							}else if(EmpRetentionInfoDates[1].equalsIgnoreCase("01")){
								
								EmpBonusM =(Integer.parseInt(EmpRetentionInfoDates[1])+11);
								EmpBonusMonth  = findStringFormatofMonth(EmpBonusM);
								 
							}else if(EmpRetentionInfoDates[1].equalsIgnoreCase("11") || EmpRetentionInfoDates[1].equalsIgnoreCase("12") || EmpRetentionInfoDates[1].equalsIgnoreCase("10")){
								
								EmpBonusM =(Integer.parseInt(EmpRetentionInfoDates[1])-1);
								EmpBonusMonth  = findStringFormatofMonth(EmpBonusM);
								 
							}else if(EmpRetentionInfoDates[1].equalsIgnoreCase("02") || EmpRetentionInfoDates[1].equalsIgnoreCase("03")||
									EmpRetentionInfoDates[1].equalsIgnoreCase("04") || EmpRetentionInfoDates[1].equalsIgnoreCase("05")  ||
									EmpRetentionInfoDates[1].equalsIgnoreCase("06") || EmpRetentionInfoDates[1].equalsIgnoreCase("07")){
								
								EmpBonusM =(Integer.parseInt(EmpRetentionInfoDates[1])-1);
								EmpBonusMonth  = findStringFormatofMonth(EmpBonusM);
								 
							}
						}*/
						/**
						 * From Dates
						 */
						if(EmpRetentionDataIns == 1 || EmpRetentionDataIns == 0 ){
							body.append("01-"+bonusMonth+"-"+bonusYear);
						}else if(EmpRetentionDataIns == 2){
							if(findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1]))).equalsIgnoreCase("NOVEMBER") || findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1]))).equalsIgnoreCase("DECEMBER") ){
								BonusYear = bonusYear-1;
								body.append("01-"+findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1])))+"-"+BonusYear);
							}else if(findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1].substring(1)))).equalsIgnoreCase("AUGUST") || findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1].substring(1)))).equalsIgnoreCase("SEPTEMBER")){
								body.append("01-"+findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1].substring(1))))+"-"+BonusYear);
							}else{
								body.append("01-"+findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1])))+"-"+bonusYear);
							}
							//BonusYear = BonusYear-1;
							//body.append("01-"+findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1])))+"-"+BonusYear);
						}else if(EmpRetentionDataIns == 3 ){
							if(findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1].substring(1)))).equalsIgnoreCase("AUGUST") || findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1].substring(1)))).equalsIgnoreCase("SEPTEMBER")){
								BonusYear = bonusYear-1;
								body.append("01-"+findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1].substring(1))))+"-"+BonusYear);
							}else if(findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1]))).equalsIgnoreCase("OCTOBER") || findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1]))).equalsIgnoreCase("DECEMBER")||
									findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1]))).equalsIgnoreCase("NOVEMBER")){
								BonusYear = bonusYear-1;
								body.append("01-"+findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1])))+"-"+BonusYear);
							}else{
								body.append("01-"+findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1])))+"-"+bonusYear);
							}
						}else if(EmpRetentionDataIns == 4){
							if(findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1]))).equalsIgnoreCase("FEBRUARY") || findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1]))).equalsIgnoreCase("MARCH")
							||findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1]))).equalsIgnoreCase("APRIL")|| findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1]))).equalsIgnoreCase("MAY")
							||findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1]))).equalsIgnoreCase("JUNE") || findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1]))).equalsIgnoreCase("JULY") ||
							findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1]))).equalsIgnoreCase("OCTOBER") || findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1]))).equalsIgnoreCase("NOVEMBER")
							|| findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1]))).equalsIgnoreCase("DECEMBER")){
								BonusYear = bonusYear-1;
								body.append("01-"+findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1])))+"-"+BonusYear);
							}else if(findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1].substring(1)))).equalsIgnoreCase("AUGUST") || findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1].substring(1)))).equalsIgnoreCase("SEPTEMBER")){
								BonusYear = bonusYear-1;
								body.append("01-"+findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1].substring(1))))+"-"+BonusYear);
							}else{
								body.append("01-"+findStringFormatofMonth((Integer.parseInt(EmpRetentionInfoDates[1])))+"-"+bonusYear);
							}
						}
						/**
						 * To Dates
						 */
						body.append("</td><td align='center'>");
						
						if(EmpRetentionDataIns == 1 || EmpRetentionDataIns == 0 ){
							body.append(LastDateOfTheMonth(findintFormatofMonth(bonusMonth))+"-"+bonusMonth+"-"+bonusYear);
						}
						/**
						 * Quarterly Bonus
						 */
						else if(EmpRetentionDataIns == 2){
							//if(bonusMonth.equalsIgnoreCase(EmpBonusMonth)){
								body.append(LastDateOfTheMonth(findintFormatofMonth(bonusMonth))+"-"+bonusMonth+"-"+bonusYear);
							//}
						}
						/**
						 * Bi-Annual Bonus
						 */
						else if(EmpRetentionDataIns == 3){
							//if(bonusMonth.equalsIgnoreCase(EmpBonusMonth)){
								body.append(LastDateOfTheMonth(findintFormatofMonth(bonusMonth))+"-"+bonusMonth+"-"+bonusYear);
							//}
						}
						/**
						 * Annual Bonus
						 */
						else if(EmpRetentionDataIns == 4){
							//if(bonusMonth.equalsIgnoreCase(EmpBonusMonth)){
								body.append(LastDateOfTheMonth(findintFormatofMonth(bonusMonth))+"-"+bonusMonth+"-"+bonusYear);
							//}
						}
						else{
							logger.error("Unable to send Bonus disbursal mail to employee.Bonus Month and Employee Bonus Term is not Matched");
						}
						//To date
						body.append("</td><td align='right'>");
						if(EmpRetentionDataIns == 1){
							body.append(new DecimalFormat("0.00").format(Math.round(Double.parseDouble(report.getrBonus())/12)));
						}else if(EmpRetentionDataIns == 2){
							body.append(new DecimalFormat("0.00").format(Math.round(Double.parseDouble(report.getrBonus())/4)));
						}else if(EmpRetentionDataIns == 3){
							body.append(new DecimalFormat("0.00").format(Math.round(Double.parseDouble(report.getrBonus())/2)));
						}else if(EmpRetentionDataIns == 0){
							body.append(new DecimalFormat("0.00").format(Math.round(Double.parseDouble(report.getrBonus()))));
						}
						else{
							body.append(new DecimalFormat("0.00").format(Double.parseDouble(report.getrBonus())));
						}
						if(EmpRetentionDataIns == 1){
							total += Math.round(Double.parseDouble(report.getrBonus())/12);
							
						}else if(EmpRetentionDataIns == 2){
							total += Math.round(Double.parseDouble(report.getrBonus())/4);
						}else if(EmpRetentionDataIns == 3){
							total += Math.round(Double.parseDouble(report.getrBonus())/2);
						}else if(EmpRetentionDataIns == 0){
							total += Math.round(Double.parseDouble(report.getrBonus()));
						}
						else{
							total += Double.parseDouble(String.format("%.0f", Double.parseDouble(report.getrBonus())));
						}
						body.append("</td></tr>");
					}
					
					for (RetentionBonusReconciliationReport report : reportsList){
						RetentionBonusComponents[] components = RetentionBonusComponentsDaoFactory.create().findWhereRepIdEquals(report.getId());
						if (components != null && components.length > 0){
							for (RetentionBonusComponents component : components){
								body.append("<tr><td>");
								body.append((component.getType() == 1 ? "Add " : "Less") + " : " + component.getReason());
								body.append("</td><td>");
								body.append("</td><td align='center'>");
								body.append("</td><td align='right'>");
								body.append((component.getType() == 1 ? "" : "-") + new DecimalFormat("0.00").format(Double.parseDouble(component.getAmount())));
								if (component.getType() == 1){
									total += Double.parseDouble(component.getAmount());
								}
								else{
									total -= Double.parseDouble(component.getAmount());
								}
								body.append("</td></tr>");
							}
						}
					}
					body.append("<tr><td colspan='3' align='right'> Total &nbsp;&nbsp;&nbsp;</td><td align='right'>");
					body.append(new DecimalFormat("0.00").format(total));
					body.append("</td></tr>");
					portalMail.setMessageBody(body.toString());
					mailGenerator.invoker(portalMail);
				} catch (Exception e){
					e.printStackTrace();
					logger.error("Unable to send Bonus disbursal mail to employee." + entry.getValue(), e);
				}
			}
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}//
}
