
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
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.EmployeeBonus;
import com.dikshatech.common.utils.BonusRecReportGeneratorAndNotifier;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.GenerateXls;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.BonusComponentsDao;
import com.dikshatech.portal.dao.BonusRecReportHistoryDao;
import com.dikshatech.portal.dao.BonusReconciliationDao;
import com.dikshatech.portal.dao.BonusReconciliationHoldDao;
import com.dikshatech.portal.dao.BonusReconciliationReportDao;
import com.dikshatech.portal.dao.BonusReconciliationReqDao;
import com.dikshatech.portal.dao.DepPerdiemExchangeRatesDao;
import com.dikshatech.portal.dao.DepPerdiemHoldDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.SalaryDetailsDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.BonusComponents;
import com.dikshatech.portal.dto.BonusRecReportHistory;
import com.dikshatech.portal.dto.BonusReconciliation;
import com.dikshatech.portal.dto.BonusReconciliationHold;
import com.dikshatech.portal.dto.BonusReconciliationPk;
import com.dikshatech.portal.dto.BonusReconciliationReport;
import com.dikshatech.portal.dto.BonusReconciliationReq;
import com.dikshatech.portal.dto.BonusReconciliationReqPk;
import com.dikshatech.portal.dto.DepPerdiemExchangeRates;
import com.dikshatech.portal.dto.DepPerdiemHold;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.FinanceInfo;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.BonusReconciliationDaoException;
import com.dikshatech.portal.exceptions.BonusReconciliationReportDaoException;
import com.dikshatech.portal.exceptions.BonusReconciliationReqDaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.SalaryDetailsDaoException;
import com.dikshatech.portal.factory.BonusComponentsDaoFactory;
import com.dikshatech.portal.factory.BonusRecReportHistoryDaoFactory;
import com.dikshatech.portal.factory.BonusReconciliationDaoFactory;
import com.dikshatech.portal.factory.BonusReconciliationHoldDaoFactory;
import com.dikshatech.portal.factory.BonusReconciliationReportDaoFactory;
import com.dikshatech.portal.factory.BonusReconciliationReqDaoFactory;
import com.dikshatech.portal.factory.CurrencyDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemExchangeRatesDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemHoldDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.FinanceInfoDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
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


public class BonusReconciliationModel extends ActionMethods{
	
	int j;
	private static Logger		logger			= LoggerUtil.getLogger(BonusReconciliationModel.class);
	public static final short	AUTO			= 0;
	public static final short	DELETED			= 1;
	public static final short	ADDED			= 2;
	public static final short	MODIFIED		= 3;
	public static final short	ADDED_DELETED	= 4;
	public static final short	HOLD			= 7;
	public static final short	FIXED_DELETED	= 102;
	public static final short	FIXED_HOLD		= 103;
	public static final short	FIXED			= 104;
	public static final short	RELEASED		= 9;
	public static final short	REJECTED		= 11;
	public static final int		MODULEID		= 72;
	public static final short	Quarterly		= 100;
	public static final short	QuarterlyAnnual	= 101;
	public static final String	CATEGORY		= "BONUS RECONCILIATION";
	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		Login login = Login.getLogin(request);
		ActionResult result = new ActionResult();
		BonusReconciliation bonusForm = (BonusReconciliation) form;
		if (ModelUtiility.hasModulePermission(login, MODULEID)){
			int res = new BonusRecReportGeneratorAndNotifier().generateReportAndNotify(bonusForm.getId() == 0 ? 0 : login.getUserId());
			if (res == 1){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.initiate.confirm"));
			} else if (res == 2){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.initiate.error", "Bonus"));
			}
			else if(res==3){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.bonus.initiate.month"));
				logger.info("Bonus Report Generation Failed and Not permitted for this  month: ");
			}else if(res==4){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.initiate.err", "Bonus"));
			}
	//		else result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bonus.generated.success"));
	} else{
	result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));		
	logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access bonus reconciliation receive Urls without having permisson at :" + new Date());
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
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access bonus reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
		int usId=login.getUserId();
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			EmpSerReqMapDao esrDao = EmpSerReqMapDaoFactory.create(conn);
			BonusReconciliationDao bonusDao = BonusReconciliationDaoFactory.create(conn);
			DropDown dropdown = new DropDown();
			LevelsDao levelDao=LevelsDaoFactory.create(conn);
			BonusReconciliationReqDao bonusReqDao = BonusReconciliationReqDaoFactory.create(conn);
			BonusReconciliation bonusForm = (BonusReconciliation) form;
			//String flag =bonusForm.getBankFlag();
			String flag1=bonusForm.getBankFlag();
		int usid=	login.getUserId();
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
				case RECEIVEALL:{// show only !escalated requests which was once received by me
					try{
						BonusReconciliationReq[] bonusReqs = null;
						int levelss=0;
						bonusReqs = bonusReqDao.findByDynamicWhere(" ASSIGNED_TO=? AND IS_ESCALATED=0 GROUP BY BR_ID", new Object[] { login.getUserId() });
						dropdown.setKey1(5);// seqId

						 levelss =levelDao.findWhereUsersIdEquals(usId);
						dropdown.setLevelId(levelss);
						
						HashMap<Integer, BonusReconciliation> bonusMap = new HashMap<Integer, BonusReconciliation>();
						if (bonusReqs != null && bonusReqs.length > 0){
							HashMap<Integer, String> idReqIdMap = esrDao.getIdReqIdMap(16);
							if (idReqIdMap != null && idReqIdMap.size() > 0){
								for (BonusReconciliationReq eachBonusReq : bonusReqs){
			
									BonusReconciliation tempBonus = bonusDao.findByPrimaryKey(eachBonusReq.getBonusId());
									tempBonus.setReqId(idReqIdMap.get(tempBonus.getEsrMapId()));
									tempBonus.setStatus(tempBonus.getStatus());
									tempBonus.setHtmlStatus(tempBonus.getHtmlStatus());
									bonusMap.put(tempBonus.getId(), tempBonus);}}
						}
						for (Map.Entry<Integer, BonusReconciliation> entrySet : bonusMap.entrySet()){
							int bonusId = entrySet.getKey();
							BonusReconciliationReq bonusReq = bonusReqDao.findByDynamicWhere(" ASSIGNED_TO=? AND BR_ID=? ORDER BY ID DESC", new Object[] { login.getUserId(), bonusId })[0];
							if (bonusReq.getSubmittedOn() == null){
								bonusMap.get(bonusId).setActionButtonVisibility("enable");
							} else{
								bonusMap.get(bonusId).setActionButtonVisibility("disable");
							}
						}
						ProcessChainDao processChainDao = ProcessChainDaoFactory.create(conn);
						ProcessEvaluator pEval = new ProcessEvaluator();
						ProcessChain processChainDto = processChainDao.findWhereProcNameEquals("IN_BONUS_RECON")[0];
						// boolean found = false;
						for (int appLevel = 1; appLevel <= 4; appLevel++){
							Integer[] approverIds = pEval.approvers(processChainDto.getApprovalChain(), appLevel, 1);
							
							for (Integer eachApproverId : approverIds){
								
								if (eachApproverId ==usid ){
									if (appLevel==3){
										dropdown.setKey1(3);
									}else if (appLevel==4){
										dropdown.setKey1(4);
									}
									else 
									dropdown.setKey1(appLevel);
									break;
								}
								
							}
				//  Checking for the user Whether there are any escalated requests and setting the flag call the another url
							BonusReconciliationReq[] escBonusReq = bonusReqDao.findByDynamicWhere(" ASSIGNED_TO=? AND IS_ESCALATED !=0 ORDER BY ID DESC", new Object[] { login.getUserId() });
							if (escBonusReq != null && escBonusReq.length > 0){
								dropdown.setKey2(1);
							}
						}	
			    //   Generating List of all escalated Bonus Reconciliation Requests	
						List<BonusReconciliation> escalatedbonusList = receiveEscalations(login.getUserId());// only escalated requests
						if (escalatedbonusList != null){
				//  Generating List of all Bonus Reconciliation List	
					    List<BonusReconciliation> bonusList = new ArrayList(bonusMap.values());// non escalated requests
					    bonusList.addAll(escalatedbonusList);
					    BonusReconciliation[] bonuses = new BonusReconciliation[bonusList.size()];
						new ArrayList<BonusReconciliation>(bonusList).toArray(bonuses);
						dropdown.setDropDown(bonuses);
						}
					} catch (Exception ex){
						logger.error("BONUSRECONCILATION RECEIVEALL : failed to fetch data for userId=" + login.getUserId(), ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
					break;
				}
				case RECEIVEESCALATIONS:{}
				break;
				
			    case RECEIVEREPORT:{
			    	
					try{	
					
						if(flag1!=null){
							if(flag1.equals("HDFC")){
								BonusReconciliationReport[] bonusReports = null;
								BonusReconciliationReportDao bonusReportDao = BonusReconciliationReportDaoFactory.create(conn);
								
								bonusReports = bonusReportDao.findWhereBonusIdEqualshdfc(bonusForm.getBonusId());
								List<Object> BankList=new ArrayList<Object>();
								
								if(bonusReports!=null && bonusReports.length >0 ){
									for(BonusReconciliationReport report : bonusReports){
										
										if(report.getSalaryCycle()==null){
											report.setSalaryCycle("");
											
										}
										
										if(report.getPaid()==null){
											report.setPaid("unPaid");	
									
										
										BankList.add(report);
											
											}	
									}}
								bonusForm.setListHdfc(BankList.toArray(new BonusReconciliationReport[BankList.size()]));
								
								request.setAttribute("actionForm",bonusForm);
							
							}
							else{
								BonusReconciliationReport[] bonusReports = null;
								BonusReconciliationReportDao bonusReportDao = BonusReconciliationReportDaoFactory.create(conn);
								
								bonusReports = bonusReportDao.findWhereBonusIdEqualsnonHdfc(bonusForm.getBonusId());
								List<Object> BankList=new ArrayList<Object>();
								
								if(bonusReports!=null && bonusReports.length >0 ){
									for(BonusReconciliationReport report : bonusReports){
										
										if(report.getSalaryCycle()==null){
											report.setSalaryCycle("");
											
										}
										
										if(report.getPaid()==null){
										
											report.setPaid("unPaid");	 
										
										BankList.add(report);
											
											}	
									}
								bonusForm.setListNonHdfc(BankList.toArray(new BonusReconciliationReport[BankList.size()]));
								
								request.setAttribute("actionForm",bonusForm);
								
								
							}
							
							}
							}
						else {
						BonusReconciliationReq[] bonusReq = bonusReqDao.findByDynamicWhere(" BR_ID = ? AND ASSIGNED_TO=?  ORDER BY ID DESC", new Object[] { bonusForm.getBonusId(), login.getUserId() });
						BonusRecReportGeneratorAndNotifier notifier=new BonusRecReportGeneratorAndNotifier();
						if (bonusReq != null && bonusReq.length > 0 && bonusReq[0] != null){
							BonusReconciliationReport[] bonusReports = null;
							ProfileInfo[] profInfo = null;
							ProfileInfoDao profInfoDao=ProfileInfoDaoFactory.create(conn);
							BonusReconciliationReportDao bonusReportDao = BonusReconciliationReportDaoFactory.create(conn);
							if (bonusReq[0].getSeqId() == 2)
							{
								int bUserId = login.getUserId().intValue();
								if (bonusForm.getEscalatedFromId() > 0){
								// logged in person must see "this' person's resources
									bUserId = bonusForm.getEscalatedFromId();
								} else if (bonusReq[0].getIsEscalated() != 0){
									BonusReconciliationReq approverRecord = bonusReqDao.findByPrimaryKey(bonusReq[0].getIsEscalated());
									bUserId = approverRecord.getAssignedTo();
									}
								bonusReports = bonusReportDao.findWhereBonusIdEquals(bonusReq[0].getBonusId(), bUserId);
							} else {
								bonusReports = bonusReportDao.findWhereBonusIdEquals(bonusReq[0].getBonusId());
							}
							if (bonusReports != null && bonusReports.length > 0){
								List<BonusReconciliationReport> list = new ArrayList<BonusReconciliationReport>();
								List<Map<String, Object>> auto = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> added = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> modified = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> deleted = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> hold = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> released = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> rejected = new ArrayList<Map<String, Object>>();
								Map<String, String> currencyTypes = CurrencyDaoFactory.create(conn).getAllCurrencyTypes();
								double totalAmount = 0.0;
								for (BonusReconciliationReport report : bonusReports){
									if(!(report.getMonth()).equals(" ")||!(report.getMonth()).equals(null)){
									if (report.getqAmount() == null || report.getqAmountInr() == null || report.getTotal() == null||report.getqAmount() == null || report.getqAmountInr() == null||report.getqBonus() == null||report.getcBonus() == null)
									{
									if (report.getqBonus() == null){
										  double qBonus=findQuaterelyBonus(report.getUserId(),conn);
										  report.setqBonus(qBonus+"");
										}
									if (report.getqBonus() == null){
											 double cBonus=findCompanyBonus(report.getUserId(),conn);
											  report.setcBonus(cBonus+"");
									}			
							// getting quaterely bonus
									if (report.getqAmount() == null){	
										 double qBonus=findQuaterelyBonus(report.getUserId(),conn);
										 report.setqAmount((qBonus/4)+"");
									}
									if (report.getqAmountInr() == null) report.setqAmountInr(report.getqAmount());
							//getting yearly bonus
									if (report.getcAmount() == null){
										report.setcAmount(Double.parseDouble(report.getqBonus())+"");
										}
									if (report.getcAmountInr() == null) report.setcAmountInr(report.getcAmount());
									}
									
									/*if (report.getSalaryCycle() == null){
										//report.setcAmount(Double.parseDouble(report.getqBonus())+"");
										//report.setSalaryCycle(bonusForm.getSalaryCycle()); 
										}*/
								  report.setCurrencyName(currencyTypes.get(report.getCurrencyType() + ""));
								  report.setqAmount(new DecimalFormat("0.00").format(Math.round(Double.parseDouble(report.getqAmount())))+"");
								  report.setcAmount(new DecimalFormat("0.00").format(Math.round(Double.parseDouble(report.getcAmount())))+"");
								   
								/*  if(report.getTotalDays()<=0) { 
									  int totalDays=notifier.findtotaldays(report.getMonth());
									  report.setTotalDays(totalDays);
								  } 
								  if(report.getProjectDays()<=0) { 
									  int projectDays=notifier.findProjectDays(report.getUserId(), conn,report.getMonth());
									  report.setProjectDays(projectDays);
								  }
								  if(report.getGlobalBenchDays()<=0) {
									 int globalBenchDays=report.getTotalDays()-report.getProjectDays();   
				                    report.setGlobalBenchDays(globalBenchDays);
								  }*/
				                 
								bonusReportDao.update(report.createPk(),report);
								UsersDao usersDao = UsersDaoFactory.create();
								Users[] users = usersDao.findWhereIdEquals(report.getUserId());	
								int userId=report.getUserId();
								int empId = users[0].getEmpId();
								//report.setEmpId(userId);
								report.setEmpId(empId);
								profInfo=profInfoDao.findByDynamicSelect("SELECT PF.* FROM PROFILE_INFO PF JOIN USERS U ON PF.ID=U.PROFILE_ID JOIN BONUS_REC_REPORT BR ON BR.USER_ID = U.ID WHERE BR.USER_ID=?", new Object[] {  new Integer(userId)});  
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
											//report.setSalaryCycle(bonusForm.getSalaryCycle());
											report.setSalaryCycle(report.getSalaryCycle());
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
						
					}
				}}
						return result;
			} catch (Exception e){
				e.printStackTrace();
				logger.error("BONUSRECONCILIATION RECEIVEREPORTFORBONUS : failed to fetch data", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
			}
				}
	
					break;
					
					
			    case RECEIVEALLPAIDANDUNPAID :{
			    	int id=bonusForm.getBonusId();
                    
                   // String flag2=bonusForm.getBankFlag();
			    	
			    	try{
			    		
			    		
			    		BonusReconciliationReport[] bonusReconciliationReport =null;
			    		double total = 0;
			    			if(flag1!=null){
                            
			    				bonusReconciliationReport=BonusReconciliationReportDaoFactory.create(conn).findAllPaidAndUnpaid(bonusForm.getBonusId(), flag1);
			    				List<Object> BankList=new ArrayList<Object>();
			    				
		                        
	                             if (bonusReconciliationReport != null && bonusReconciliationReport.length > 0){
	                            
	                                
	                                for (BonusReconciliationReport report : bonusReconciliationReport){
	                                	
	                                	
	                                	if(report.getSalaryCycle()==null){
											report.setSalaryCycle("");
											
										}
	                                	
	                                 
	                                     if(report.getPaid()==null){
	                                        
	                                         report.setPaid("UnPaid");
	                                     }
	                                    
	                                    
	                                    BankList.add(report);
	                                
	                                        
	                                    }
	                                }
	                             if (flag1.equals("HDFC")){
	                                 bonusForm.setListHdfc(BankList.toArray(new BonusReconciliationReport[BankList.size()]));        
	                             }
	                             else {
	                            	 bonusForm.setListNonHdfc(BankList.toArray(new BonusReconciliationReport[BankList.size()]));
	                             }
	        
	                            request.setAttribute("actionForm", bonusForm);
			    			}
			    			return result;
			    		
			    	}
			    	catch (Exception ex){
                        logger.error("RECONCILIATION RECEIVE : failed to fetch data", ex);
                        result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
                        
                    }
			    }
                    break;
 	
			    
			    
			    case RECEIVEPAY:{
			    	
			    	try{
			    		String updateReceivePay=null;
                        
	                    //DepPerdiemReport depPerdiemReports=DepPerdiemReportDaoFactory.create(conn).findByPrimaryKey(perdiemForm.getDepId());
	                   // String flag1=bonusForm.getBankFlag();
	                    double total = 0;
	                     List<BonusReconciliationReport> list =new ArrayList<BonusReconciliationReport>();
	                     
	                     
	                     String bbrId=bonusForm.getBrrid();
	                     String[] str=bbrId.split(",");
	                     
	                     ArrayList<Integer> bbr_Id=new ArrayList<Integer>();
	                     for(String w:str){  
	                         Integer x = Integer.valueOf(w);
	                         bbr_Id.add(x);
	                     }
	                     if(flag1!=null){
	                         
	                     //    updateReceivePay=DepPerdiemReportDaoFactory.create(conn).updateAllReceivedPay(bonusForm.getBonusId(),bbr_Id,flag1);
	                     updateReceivePay=BonusReconciliationReportDaoFactory.create(conn).updateAllReceivedPay(bonusForm.getBonusId(), bbr_Id, flag1);
	                         
	                     }
	                     
	                     request.setAttribute("actionForm", updateReceivePay);
	                 
			    		
			    	}
			    	catch (Exception ex){
                        logger.error("BONUS RECONCILATION RECEIVE : failed to fetch data", ex);
                        result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
                        return result;
                    }
	  	
			    }
			    break;
					
			 case RECEIVE:{
				try{
					    BonusReconciliationReportDao ReportDao = BonusReconciliationReportDaoFactory.create(conn);
						BonusRecReportHistoryDao historyDao = BonusRecReportHistoryDaoFactory.create(conn);
						BonusReconciliationReport report = ReportDao.findByPrimaryKey(bonusForm.getId());
						Map<String, String> currencyTypes = null;
						BonusRecReportHistory[] history = historyDao.findByBonusReport(report.getId());
						int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
						String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
						List<Map<String, String>> historyList = new ArrayList<Map<String, String>>();
						if (report.getModifiedBy() != null && !report.getModifiedBy().equalsIgnoreCase(empName)){
						//if (report.getModifiedBy() != null){
							BonusRecReportHistory his = report.getBonusRecReportHistory();
		                    //report.setComments(null);
//		                    if (currencyTypes == null) currencyTypes = CurrencyDaoFactory.create(conn).getAllCurrencyTypes();
							his.setCurrencyName("INR");
							double total = Double.parseDouble(his.getTotal());
							//total = Double.parseDouble(his.getTotal());
							his.setTotal(total+"");
							//his.setTotal(his.getTotal());
							historyList.add(his.toHashMap());
						}
						if(report.getSalaryCycle() == null){
							report.setSalaryCycle("1");
						}
						
						Map<String, Object> map = report.toMap(2);
						for (BonusRecReportHistory hist : history){
//							if (currencyTypes == null) currencyTypes = CurrencyDaoFactory.create(conn).getAllCurrencyTypes();
							hist.setCurrencyName("INR");
							//hist.setTotal(report.getTotal());
							hist.setTotal(hist.getTotal());
							if(report.getMonth().equalsIgnoreCase("June") || report.getMonth().equalsIgnoreCase("September") ||report.getMonth().equalsIgnoreCase("December")){
								hist.setType(Quarterly);
							}else if(report.getMonth().equalsIgnoreCase("March")){
								hist.setType(QuarterlyAnnual);
							}
							historyList.add(hist.toHashMap());
						}
						if (historyList.size() > 0) map.put("history", historyList);
							BonusComponents[] components = BonusComponentsDaoFactory.create(conn).findWhereRepIdEquals(report.getId());
							if (components != null && components.length > 0) 
							map.put("components", components);
							request.setAttribute("actionForm", map);
							return result;
						
					} catch (Exception e){
						logger.error("BONUSRECONCILIATION RECEIVE : failed to fetch data", e);
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
						logger.error("BONUSRECONCILATION RECEIVEEMPLOYEESFORBONUS : failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
			 }
			break;
			 case RECEIVEALLREPORTINGMGR:{
				 List<Object> allReportingManagers=null;
				List <Object> initiators=JDBCUtiility.getInstance().getSingleColumn("SELECT ASSIGNED_TO FROM BONUS_RECONCILIATION_REQ WHERE SEQ_ID=0 AND BR_ID=?", new Object[] {bonusForm.getId()});
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
				 BonusReconciliationReport report = BonusReconciliationReportDaoFactory.create(conn).findByPrimaryKey(bonusForm.getId());
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
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
		} finally{
			ResourceManager.close(conn);
		}
		return result;
	}

	/* * * * * * * * Picks up the Quaterely bonus for Employee from Salary Detail Table * * * * * * * * */
	
	private Double findQuaterelyBonus(int userId,Connection conn) throws SalaryDetailsDaoException{
		SalaryDetailsDao salDetail=SalaryDetailsDaoFactory.create(conn);
		// SAL_ID is '9' for Quaterely Bonus
		SalaryDetails[] salary=salDetail.findByDynamicSelect("SELECT ID, USER_ID, CANDIDATE_ID, FIELD_LABEL, MONTHLY, ANNUAL, SUM, FIELDTYPE, SAL_ID FROM SALARY_DETAILS WHERE USER_ID = ? AND SAL_ID= 9 ORDER BY USER_ID", new Object[] {  new Integer(userId)} );
        double totalAmount = 0.0;
		if (salary != null && salary.length > 0){
		for (SalaryDetails sal : salary){
	    totalAmount=Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(sal.getAnnual()));
				}
			}
		return totalAmount;
		}
	
	/* * * * * * * * Picks up the Company bonus for Employee from Salary Detail Table * * * * * * * * */
	
	private Double findCompanyBonus(int userId,Connection conn) throws SalaryDetailsDaoException{
		double totAmt=0.0;
		SalaryDetailsDao salDetail=SalaryDetailsDaoFactory.create(conn);
		// SAL_ID is '10' for Company Bonus
		SalaryDetails[] cBonus=salDetail.findByDynamicSelect("SELECT ID, USER_ID, CANDIDATE_ID, FIELD_LABEL, MONTHLY, ANNUAL, SUM, FIELDTYPE, SAL_ID FROM SALARY_DETAILS WHERE USER_ID = ? AND SAL_ID= 10 ORDER BY USER_ID", new Object[] {  new Integer(userId)} );
		if (cBonus != null && cBonus.length > 0){
			for (SalaryDetails salCom : cBonus){
				 totAmt=Double.parseDouble(DesEncrypterDecrypter.getInstance().decrypt(salCom.getAnnual()));
				}
			}
		return totAmt;
		}
	
	private boolean canRelease(int id, Integer userId, Connection conn) {
		return JDBCUtiility.getInstance().getRowCount(" FROM BONUS_REC_HOLD WHERE USER_ID=? AND REP_ID=?", new Object[] { userId, id }, conn) > 0;
	}

	
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
	if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access bonus reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
		BonusReconciliation bonusForm = (BonusReconciliation) form;
		Connection conn = null;
		request.setAttribute("actionForm", "");
		switch (SaveTypes.getValue(form.getsType())) {
		case SAVE:
			try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			BonusReconciliationReportDao bonReportDao = BonusReconciliationReportDaoFactory.create(conn);
			BonusReconciliationReport bonusReport = bonReportDao.findByPrimaryKey(bonusForm.getId());
			int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
			String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
			if (bonusReport != null && (bonusReport.getModifiedBy() == null || !bonusReport.getModifiedBy().equalsIgnoreCase(empName))){
				bonusReport.setModifiedBy(empName);
				bonusReport.setModifiedOn(new Date());
				bonusReport.setqAmountInr(Double.parseDouble(bonusReport.getqAmount()) + ""); 
				bonusReport.setComments(bonusForm.getComments());
			
				
				BonusRecReportHistoryDaoFactory.create(conn).insert(bonusReport.getBonusRecReportHistory());
			}else if (bonusReport != null || (bonusReport.getModifiedBy() == null)){
				bonusReport.setModifiedBy(empName);
				bonusReport.setModifiedOn(new Date());
				bonusReport.setqAmountInr(Double.parseDouble(bonusReport.getqAmount()) + ""); 
				bonusReport.setComments(bonusForm.getComments());
				bonusReport.setSalaryCycle(bonusForm.getSalaryCycle());
				
				
				BonusRecReportHistoryDaoFactory.create(conn).insert(bonusReport.getBonusRecReportHistory());
			}
			
			 double totalbonus=Double.parseDouble(bonusReport.getTotal());
			 double totalCtc=0;
			 if(totalbonus<=0){
			 totalCtc=Double.parseDouble(bonusReport.getqAmount())+Double.parseDouble(bonusReport.getcAmount());
			 bonusReport.setTotal(totalCtc+"");
			 }else bonusReport.setTotal(totalbonus+"");
             bonusReport.setComments(bonusForm.getComments());
			 bonusReport.setModifiedBy(empName);
			 bonusReport.setModifiedOn(new Date());
			
				
			// bonusReport.setType(bonusReport.getType() == ADDED ||  bonusReport.getType() == HOLD || bonusReport.getType() == RELEASED || bonusReport.getType() == REJECTED ? bonusReport.getType() : MODIFIED);
			 if(bonusReport.getType() == ADDED ||  bonusReport.getType() == HOLD || bonusReport.getType() == RELEASED || bonusReport.getType() == REJECTED || bonusReport.getType() == AUTO || bonusReport.getType() == DELETED || bonusReport.getType() == ADDED_DELETED ){
		//		 System.out.println("Hello");
				 bonusReport.setType(MODIFIED);
			 }
		     bonusReport.setqAmountInr(Double.parseDouble(bonusReport.getqAmount()) + ""); 
		     bonusReport.setcAmountInr(Double.parseDouble(bonusReport.getcAmount()) + "");
  
		    // update components
			//id~=~reason~=~amount~=~currency~=~type
			BonusComponentsDao componentsDao = BonusComponentsDaoFactory.create(conn);
			BonusComponents[] components = componentsDao.findWhereRepIdEquals(bonusReport.getId());
			Map<String, BonusComponents> componentMap = new HashMap<String, BonusComponents>();
			if (components != null && components.length > 0){
				for (BonusComponents component : components){
					componentMap.put(component.getId().intValue() + "", component);}
			}
			
			if(bonusForm.getComponents() != null){	
				List<String> pbonuslist=new ArrayList<String>();
				for(String str:bonusForm.getComponents()){
					String[] strtemp=str.split("~=~");
					pbonuslist.add(strtemp[0]);
				}
				List<String> deletebonuslist=new ArrayList<String>();
				for (Map.Entry<String, BonusComponents> entry : componentMap.entrySet()){
					deletebonuslist.add(entry.getKey());
				}
				//now removing from delete list whch values are not needed
				deletebonuslist.removeAll(pbonuslist);
				for(String str:deletebonuslist){
					componentsDao.delete(componentMap.get(str).createPk());
				}
				}else{
					List<String> deletebonuslist=new ArrayList<String>();
					for (Map.Entry<String, BonusComponents> entry : componentMap.entrySet()){
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
        	  
        	  // bonusReport.setInitalBonusAmount("200.00"); 
        	  totalAmt=Double.parseDouble(bonusForm.getInitialBonusAmount());
        	  //System.out.println("Initial Bonus Amount :: "+totalAmt);
			for (String entry : bonusForm.getComponents()){
				String[] data = entry.split("~=~");
				
				if (data.length != 5 || data[0] == null || data[1] == null || data[2] == null || data[4] == null || data[3] == null || Integer.parseInt(data[3]) < 1){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.save.leave.failed"));
					logger.error("Unable to update components of bonus reports details. input: " + entry + ", " + bonusForm.getComponents());
					return result;}
				int id = Integer.parseInt(data[0]);
				BonusComponents bonusComponent = null;
				if (id > 0){
					bonusComponent = componentMap.get(id + "");
				} else{
					bonusComponent = new BonusComponents();
				}
				  bonusComponent.setValues(bonusReport.getId(),Short.parseShort(data[4]), data[1], Integer.parseInt(data[3]), data[2], empName, new Date(), "");
				  
				  // Calculating Final total Amount After Additions,Deductions and LWP   
				
				
					double total =Double.parseDouble(bonusComponent.getAmount());
                   // totalAmt=Double.parseDouble(bonusReport.getTotal());
					//bonusReport.setInitalBonusAmount("200.00");
					//System.out.println(bonusReport.getInitalBonusAmount());
					//totalAmt=Double.parseDouble(bonusReport.getInitialBonusAmount());
	            	//System.out.println("Initial Bonus Amount :: "+totalAmt);
					
				
					if (bonusComponent.getType().shortValue() == 1){
						totalAmt+=total; //Additions
						bonusReport.setTotal(Math.round(totalAmt)+ "");
						//bonusReport.setInitialBonusAmount(Math.round(totalAmt)+ "");
						
					}
					else if (bonusComponent.getType() == 2 || bonusComponent.getType() == 3){
						totalAmt-=total;  //Deductions and  LWP
						bonusReport.setTotal(Math.round(totalAmt)+ "");
						//bonusReport.setInitialBonusAmount(Math.round(totalAmt)+ "");
						
					}
					if (bonusComponent.getId() == null || bonusComponent.getId().intValue() < 1) 
						componentsDao.insert(bonusComponent);
					else{componentsDao.update(bonusComponent.createPk(), bonusComponent);
						componentMap.remove(bonusComponent.getId().intValue() + "");}
				
			}
			   for (Map.Entry<String, BonusComponents> entry : componentMap.entrySet()){
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
           
           
           bonusReport.setSalaryCycle(bonusForm.getSalaryCycle());
          
           bonReportDao.update(bonusReport.createPk(), bonusReport);
		    conn.commit();
			} catch (Exception e){
				e.printStackTrace();
				logger.error("Unable to save bonus reconciliation report", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.save.failed"));
			} finally{
				ResourceManager.close(conn);
			}
			break;
				
				
			case ADD:
				try{
					
					int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
					String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
					BonusReconciliationReportDao depReportDao = BonusReconciliationReportDaoFactory.create(conn);
					BonusReconciliationDao bonusDao=BonusReconciliationDaoFactory.create();
					BonusReconciliation bonus=null;
					List<String> ExistEmpName = new ArrayList<String>();
					List<String> AddedEmpName = new ArrayList<String>();
					String ExistEName = "";
					String AddedEName = "";
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					DepPerdiemExchangeRatesDao exchangeRatesDao = DepPerdiemExchangeRatesDaoFactory.create(conn);
					DepPerdiemExchangeRates[] rates = null;
					for (String str : bonusForm.getAddedEmpData()){
						// userid~#~amount~#~client~#~comments~#~currency
						String data[] = str.split("~#~");
						if (data.length != 5 || data[0] == null || data[1] == null || data[2] == null || data[3] == null || data[4] == null){ throw new Exception(); }
						int userId = Integer.parseInt(data[0]);
						ProfileInfo[] userProfileInfo = ProfileInfoDaoFactory.create(conn).findByDynamicWhere("ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { userId });
						BonusReconciliationReport report = new BonusReconciliationReport(bonusForm.getBonusId(), userId,data[1], Integer.parseInt(data[4]), userProfileInfo[0].getReportingMgr(), ModelUtiility.getInstance().getEmployeeName(userProfileInfo[0].getReportingMgr()), data[2], data[3],data[4]);
						BonusReconciliationReportDao BonusData = BonusReconciliationReportDaoFactory.create(conn);
						BonusReconciliationReport[] Bonus = BonusData.findWherebonusIdAndUserIdEquals(report.getBonusId(),report.getUserId());
						UsersDao usersDao = UsersDaoFactory.create();
						Users[] users = usersDao.findWhereIdEquals(userId);
						
						if(Bonus.length >= 0){
						bonus=bonusDao.findByPrimaryKey(bonusForm.getBonusId());
						if(bonus!=null){
							report.setMonth(bonus.getMonth());	
						}
						report.setqAmount(report.getTotal()+"");
						report.setqAmountInr(0+"");
						report.setqBonus(0+"");
						report.setcAmount(0+"");
						report.setcAmountInr(0+"");
						report.setcBonus(0+"");
						report.setModifiedBy(empName);
						report.setModifiedOn(new Date());
						report.setSalaryCycle(bonusForm.getSalaryCycle());
						report.setType(ADDED);
						
						//report.setEmpId(users[0].getEmpId());
						depReportDao.insert(report);
						conn.commit();
						AddedEmpName.add(userProfileInfo[0].getFirstName());
						}else{
							ExistEmpName.add(userProfileInfo[0].getFirstName());
						}
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
						//request.setAttribute("actionForm", form);
						return result; 
					}else if(!ExistEName.isEmpty() && !AddedEName.isEmpty()){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.add.f3",AddedEName,ExistEName));
						//result.setDispatchDestination("duplicateEntry");
						request.setAttribute("actionForm", "duplicateEntry");
						return result;
					}
					//result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.add.f",AddedEName,ExistEName));
					//result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.add.failed"));
					//logger.info("Unable to add duplicate entry!");
					
				} catch (Exception e){
					e.printStackTrace();
					try{
						conn.rollback();
					} catch (SQLException e1){
						logger.error("", e);
					}
					logger.error("Unable to ADD employee to bonus reconciliation report. input: ");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.add.failed"));
				}/* finally{
					ResourceManager.close(conn);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ExistEmpName,"errors.deputation.add.f"));
				}*/
				break;
			case DELETE:
				try{
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					BonusRecReportHistoryDao history=BonusRecReportHistoryDaoFactory.create();
					BonusReconciliationReportDao bonusReportDao =  BonusReconciliationReportDaoFactory.create(conn);
					BonusReconciliationReport bonusReport = bonusReportDao.findByPrimaryKey(bonusForm.getId());
					int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
					String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
					if (bonusReport != null && (bonusReport.getModifiedBy() == null || !bonusReport.getModifiedBy().equalsIgnoreCase(empName))){
						history.insert(bonusReport.getBonusRecReportHistory());
					}
					
					bonusReport.setComments("DELETED : " + (bonusForm.getComments() == null ? "" : bonusForm.getComments()));
					bonusReport.setType(bonusReport.getType()==ADDED?ADDED_DELETED:DELETED);
					bonusReport.setModifiedBy(empName);
					bonusReport.setModifiedOn(new Date());
					bonusReportDao.update(bonusReport.createPk(), bonusReport);
					conn.commit();
				} catch (Exception e){
					logger.error("Unable to DELETE employee to bonus reconciliation report. input: ");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.delete.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			case HOLD:
				try{
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					BonusReconciliationReportDao bonusReportDao =  BonusReconciliationReportDaoFactory.create(conn);
					
					BonusReconciliationReport bonusReport = bonusReportDao.findByPrimaryKey(bonusForm.getId());
					BonusRecReportHistoryDao history=BonusRecReportHistoryDaoFactory.create();
					if (bonusReport.getType() == DELETED){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.bonus.hold.deleted.failed"));
					}
					int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
					String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
					if (bonusReport != null && (bonusReport.getModifiedBy() == null || !bonusReport.getModifiedBy().equalsIgnoreCase(empName))){
						history.insert(bonusReport.getBonusRecReportHistory());
					}
					bonusReport.setComments("HOLD : " + (bonusForm.getComments() == null ? "" : bonusForm.getComments()));
					bonusReport.setType(HOLD);
					bonusReport.setModifiedBy(empName);
					bonusReport.setModifiedOn(new Date());
					bonusReportDao.update(bonusReport.createPk(), bonusReport);
				BonusReconciliationHoldDao holdDao =BonusReconciliationHoldDaoFactory.create(conn);
				holdDao.insert(new BonusReconciliationHold(bonusReport.getId(), login.getUserId(), bonusReport.getType(), (bonusForm.getComments() == null ? "" : bonusForm.getComments()),new Date()));
					conn.commit();
				} catch (Exception e){
					logger.error("Unable to HOLD employee to bonus reconciliation report. input: ");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.hold.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			case RELEASE:
				try{
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					BonusReconciliationReportDao bonusReportDao =  BonusReconciliationReportDaoFactory.create(conn);
					BonusReconciliationReport bonusReport = bonusReportDao.findByPrimaryKey(bonusForm.getId());
					BonusRecReportHistoryDao history=BonusRecReportHistoryDaoFactory.create();
					if (!(bonusReport.getType() ==HOLD)){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.bonus.release.hold.failed"));
					}
					BonusReconciliation bonus = BonusReconciliationDaoFactory.create(conn).findByPrimaryKey(bonusReport.getBonusId());
					int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId(), conn);
					String employeeName = ModelUtiility.getInstance().getEmployeeName(login.getUserId(), conn);
					String empName = employeeName + "(" + empId + ")";
					if (bonusReport != null && (bonusReport.getModifiedBy() == null || !bonusReport.getModifiedBy().equalsIgnoreCase(empName))){
						history.insert(bonusReport.getBonusRecReportHistory());
					}
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
					bonusReportDao.update(bonusReport.createPk(), bonusReport);
					JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE SUBJECT LIKE ? AND CATEGORY='BONUS RECONCILIATION' AND ESR_MAP_ID=? ", new Object[] { "%(" + ModelUtiility.getInstance().getEmployeeId(bonusReport.getUserId(), conn) + ")", bonus.getEsrMapId() });
					BonusReconciliationHoldDao holdDao = BonusReconciliationHoldDaoFactory.create(conn);
					if ("Approved".equalsIgnoreCase(bonus.getStatus()) || "Completed".equalsIgnoreCase(bonus.getStatus())){
						holdDao.insert(new BonusReconciliationHold(bonusReport.getId(), login.getUserId(), bonusReport.getType(), (bonusForm.getComments() == null ? "" : bonusForm.getComments()),new Date()));
						releaseMail(bonus, bonusReport, employeeName, login.getUserId(), conn);
					} else{
						JDBCUtiility.getInstance().update("DELETE FROM BONUS_REC_HOLD WHERE REP_ID=?", new Object[] { bonusReport.getId() });
					}
					conn.commit();
					
					
				} catch (Exception e){
					logger.error("Unable to RELEASE employee to bonus reconciliation report. input: ");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.release.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			case CHANGEREPORTINGMGR:{
				BonusReconciliationReportDao bonusReportDao =  BonusReconciliationReportDaoFactory.create(conn);
				try {
					conn = ResourceManager.getConnection();
				BonusReconciliationReport[] bonusReport = bonusReportDao.findByDynamicWhere("BR_ID= ? AND USER_ID= ?",new Object[] {bonusForm.getId(),bonusForm.getUserId()});
				if(bonusReport!=null && bonusReport.length>0){
					for(BonusReconciliationReport bonus:bonusReport){
						bonus.setManagerId(bonusForm.getManagerId());
						bonus.setManagerName(bonusForm.getManagerName());
						bonusReportDao.update(bonus.createPk(), bonus);}
				}
				} catch (BonusReconciliationReportDaoException e) {
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
					BonusReconciliationReportDao dao = BonusReconciliationReportDaoFactory.create(conn);
					BonusReconciliationReport report = dao.findByPrimaryKey(bonusForm.getId());
					if (bonusForm.getStatus() != null){
						report.setAccountType(Short.parseShort(bonusForm.getStatus()));
						dao.update(report.createPk(), report);
					}
				} catch (Exception e){
					logger.error("unable to parse " + bonusForm.getStatus() + " as integer while updating bank name of bonus report " + bonusForm.getId() + " Msg: " + e.getMessage());
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bonus.update.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			default:
				break;
		}

		//return null;
		return result;
	}
	
	
	
	private void releaseMail(BonusReconciliation bonus, BonusReconciliationReport bonusReport, String approverName, int userId, Connection conn) {
		try{
			PortalMail mail = new PortalMail();
			mail.setTemplateName(MailSettings.BONUS_RELEASE);
			mail.setEmployeeName(ModelUtiility.getInstance().getEmployeeName(bonusReport.getUserId(), conn));
			mail.setMailSubject("Diksha Lynx: Bonus release of " + mail.getEmployeeName());
			mail.setApproverName(approverName);
			mail.setBonusMonth(bonus.getMonth());
			mail.setComments(bonusReport.getComments());
			mail.setActionType(((bonusReport.getType() == RELEASED) ? "relseased" : "rejected"));
			ProcessChain processChainDto = ProcessChainDaoFactory.create(conn).findWhereProcNameEquals("IN_BONUS_RECON")[0];
			ProcessEvaluator pEval = new ProcessEvaluator();
			// get the senior RMG from level 1
			Integer[] srRmg = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);
			// get all the users involved in this hold process, means escalations
			List<Object> escUsers = JDBCUtiility.getInstance().getSingleColumn("SELECT USER_ID FROM BONUS_REC_HOLD WHERE REP_ID=?", new Object[] { bonusReport.getId() }, conn);
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
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access bonus reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
		
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ProcessEvaluator pEval = new ProcessEvaluator();
		BonusReconciliation bonusForm = (BonusReconciliation) form;
		DropDown dropdown = new DropDown();
		BonusReconciliationDao bonusDao = BonusReconciliationDaoFactory.create();
		BonusReconciliationReqDao bonusReqDao = BonusReconciliationReqDaoFactory.create();
	try{
		int esrMapId = bonusDao.findByPrimaryKey(bonusForm.getBonusId()).getEsrMapId();
		String msgBody = null;
    	BonusReconciliationReq bonusReq = bonusReqDao.findByDynamicWhere(" BR_ID=? AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[] { bonusForm.getBonusId(), (bonusForm.getEscalatedFromId() > 0) ? bonusForm.getEscalatedFromId() : login.getUserId() })[0];
		int isEscalated = bonusReq.getIsEscalated();
		if (isEscalated != 0){
			BonusReconciliationReq approverRecord = bonusReqDao.findByPrimaryKey(isEscalated);
			approverRecord.setSubmittedOn(new Date());
			bonusReqDao.update(new BonusReconciliationReqPk(approverRecord.getId()), approverRecord);
			BonusReconciliationReq[] otherEscalRecord = bonusReqDao.findWhereIsEscalatedEquals(isEscalated);
			if (otherEscalRecord != null && otherEscalRecord.length > 0){
				for (BonusReconciliationReq eachReq : otherEscalRecord){
					//if(approverRecord.equals(eachReq)){
						eachReq.setSubmittedOn(new Date());
						bonusReqDao.update(eachReq.createPk(), eachReq);
					//}
				}
			}
		} else if (isEscalated == 0){
			//BonusReconciliationReq[] escalatedRecords = bonusReqDao.findWhereIsEscalatedEquals(isEscalated);
			BonusReconciliationReq[] escalatedRecords = bonusReqDao.findWhereIsEscalatedEquals(bonusReq.getId());
			if (escalatedRecords != null && escalatedRecords.length > 0){
				
				//if(bonusForm.equals(escalatedRecords)){	
					for (BonusReconciliationReq eachReq : escalatedRecords){
						//if(bonusReq.equals(eachReq)){
							eachReq.setSubmittedOn(new Date());
							bonusReqDao.update(eachReq.createPk(), eachReq);
						//}
					}
				//}
			}
		}
		bonusReq.setSubmittedOn(new Date());
		bonusReqDao.update(new BonusReconciliationReqPk(bonusReq.getId()), bonusReq);
		
		BonusReconciliationReq[] bonusReqs = bonusReqDao.findByDynamicWhere(" BR_ID=? AND SEQ_ID=? ", new Object[] { bonusForm.getBonusId(), bonusReq.getSeqId() });
		BonusReconciliation bonus = bonusDao.findByPrimaryKey(bonusForm.getBonusId());
		ProcessChain processChainDto = processChainDao.findWhereProcNameEquals("IN_BONUS_RECON")[0];
		String beginCompletedTag = "<font color=\"#006600\">";
		String endCompletedTag = "</font>";
		String beginPendingTag = "<font color=\"#FF0000\">";
		String endPendingTag = "</font>";
		String bonusString = bonus.getMonth()+"";
		int bonusYear = bonus.getYear();
		String actionTakenBy = ProfileInfoDaoFactory.create().findByDynamicWhere(" ID = (SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName();
//		bonusReq.setSeqId(3);
		switch (bonusReq.getSeqId()) {
			case 0:{
				// new req rmg l2f2 specialist || rmg l2f3 senior specialist has taken action

				if (bonus.getStatus().equalsIgnoreCase("Report Generated")){
					bonus.setStatus("Submitted");
					bonus.setHtmlStatus(bonus.getStatus());
					bonus.setSubmittedOn(new Date());
					bonusDao.update(new BonusReconciliationPk(bonus.getId()), bonus);
					// Integer[] handlers = pEval.handlers(processChainDto.getHandler(), 1);
					// for(Integer eachHandler : handlers){
					/*
					 * if(eachHandler==login.getUserId()){
					 * continue;
					 * }
					 */
					BonusReconciliationReq[] reqs = bonusReqDao.findByDynamicWhere(" BR_ID=? AND SEQ_ID=0 AND SUBMITTED_ON IS NULL", new Integer[] { bonus.getId() });
					if (reqs != null && reqs.length > 0){
						for (BonusReconciliationReq eachReq : reqs){
							eachReq.setSubmittedOn(new Date());
							bonusReqDao.update(new BonusReconciliationReqPk(eachReq.getId()), eachReq);
						}
					}
					//Integer[] nextLevelApprovers1 = pEval.approvers(processChainDto.getApprovalChain(), 2, 1);
					Integer[] nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);
					//Integer[] lastLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 4, 1);
					Integer[] lastLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
					//insertIntoReq(nextLevelApprovers, bonusForm.getBonusId(), 2);
					insertIntoReq(nextLevelApprovers, bonusForm.getBonusId(), 1);
					msgBody = sendMail(bonus.getStatus(), nextLevelApprovers, lastLevelApprovers, null, bonusString);// this may need modification
																															
				sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, bonus.getStatus(), bonus.getStatus());
				}
			}
				break;
			case 1:
				try{
					//if (!(bonusReqs != null && bonusReqs.length > 0)){
						// everybody has taken action
						Integer[] nextLevelApprovers = null;
						int gotoSeqId = 0;
						//if (bonus.getStatus().equalsIgnoreCase("Reviewed")){
						if(bonus.getStatus().equalsIgnoreCase("Reviewed") || bonus.getStatus().equalsIgnoreCase("Submitted")||bonus.getStatus().equalsIgnoreCase("Re-Submitted")){
							bonus.setStatus("Reviewed and Submitted");
							gotoSeqId = 3;
							//gotoSeqId = 2;
							//bonusForm.setGotoSeqId(3);
						} else if (bonus.getStatus().equalsIgnoreCase("Rejected")){
							gotoSeqId = bonusForm.getGotoSeqId();
							//gotoSeqId = 2;
							if (gotoSeqId == 2){
								bonus.setStatus("Re-Submitted");
							} else{
								bonus.setStatus("Reviewed and Re-Submitted");
							}
						}
						nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), gotoSeqId, 1);
						insertIntoReq(nextLevelApprovers, bonusForm.getBonusId(), gotoSeqId);
						bonus.setHtmlStatus(bonus.getStatus());
						bonusDao.update(new BonusReconciliationPk(bonus.getId()), bonus);
						//Integer[] lastLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 4, 1);
						Integer[] lastLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
						msgBody = sendMail(bonus.getStatus(), nextLevelApprovers, lastLevelApprovers, null, bonusString);
						sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, bonus.getStatus(), bonus.getStatus());
				//	} else{
						// there are people who must take action
				//	}
				} catch (Exception ex){
					logger.error("BONUSRECONCILATION UPDATE CASE1: failed to update data", ex);
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
						bonusDao.update(new BonusReconciliationPk(bonus.getId()), bonus);
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
								BonusReconciliationReq fetchedBonusReq = bonusReqDao.findByDynamicWhere(" BR_ID=? AND SEQ_ID=2 AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[] { bonusForm.getBonusId(), eachAppId })[0];
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
						bonusDao.update(new BonusReconciliationPk(bonus.getId()), bonus);
					}
				} catch (Exception ex){
					logger.error("BONUSRECONCILATION UPDATE CASE2 : failed to update data", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bonus.update.failed"));
					result.setForwardName("success");
					return result;
				}
				break;
			case 3:
			//case 2:
				/*
				 * People who belong to this sequence can REJECT
				 */
				try{
					if (bonusForm.getCommentsByFinanceTeam() != null && bonusForm.getCommentsByFinanceTeam().length() > 0){
						// last stage of bonus request !
						bonus.setStatus("Completed");
			
				     	bonus.setHtmlStatus("Completed");
						bonusDao.update(new BonusReconciliationPk(bonus.getId()), bonus);
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
						bonusDao.update(new BonusReconciliationPk(bonus.getId()), bonus);
						sendDisbursalMailToEmployees(bonus.getId(),bonusString,bonusYear);//Mail to Employee
						break;
					}
					Integer[] nextLevelApprovers = null, toIds = null;
					int gotoSeqId = 0;
					if(bonus.getStatus().equalsIgnoreCase("Reviewed and Submitted")||
							bonus.getStatus().equalsIgnoreCase("Reviewed and Re-Submitted")){
					if (bonusForm.getIsRejected().equalsIgnoreCase("TRUE")){
						gotoSeqId = 1;
						bonus.setStatus("Rejected");
						//toIds = pEval.approvers(processChainDto.getApprovalChain(), 4, 1);
						toIds = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
					} else{
						gotoSeqId = 4;
						//gotoSeqId = 3;
						bonus.setStatus("Accepted");
						toIds = null;
					}
					
					nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), gotoSeqId, 1);
					insertIntoReq(nextLevelApprovers, bonusForm.getBonusId(), gotoSeqId);
					bonus.setHtmlStatus(bonus.getStatus());
					bonusDao.update(new BonusReconciliationPk(bonus.getId()), bonus);
					msgBody = sendMail(bonus.getStatus(), nextLevelApprovers, toIds, actionTakenBy, bonusString);// common to Accepted/Rejected
					sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, bonus.getStatus(), bonus.getStatus());
					
					}
				} catch (Exception ex){
					logger.error("BONUSRECONCILATION UPDATE CASE3 : failed to update data", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bonus.update.failed"));
					result.setForwardName("success");
					return result;
				}
				break;
			case 4:
			//case 3:
				try{
					Integer[] nextLevelApprovers = null;
					ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
					if (bonusForm.getIsRejected().equalsIgnoreCase("TRUE")){
						bonus.setStatus("Rejected");
						bonus.setHtmlStatus("Rejected");
						bonusDao.update(new BonusReconciliationPk(bonus.getId()), bonus);
						nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);
						insertIntoReq(nextLevelApprovers, bonusForm.getBonusId(), 1);
						msgBody = sendMail(bonus.getStatus(), nextLevelApprovers, null, actionTakenBy, bonusString);
						sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, bonus.getStatus(), bonus.getStatus());
					} else{
						String currentStatus = bonusDao.findByPrimaryKey(bonus.getId()).getStatus();
						if (currentStatus.equalsIgnoreCase("Pending Approval") || currentStatus.equalsIgnoreCase("Accepted") ){
							// both have approved..send this info to the person involved in the Finance team
							bonus.setStatus("Approved");
						//	String actionTaken = "Approved By:[ <b>" + beginCompletedTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName() + endCompletedTag;
						//	bonus.setHtmlStatus(actionTaken + "</b> ]");
							bonus.setHtmlStatus("Approved");
							nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
							//nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
							//nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 2, 1);
							insertIntoReq(nextLevelApprovers, bonusForm.getBonusId(), 3);
							msgBody = sendMail(bonus.getStatus(), nextLevelApprovers, null, null, bonusString);
							sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, bonus.getStatus(), bonus.getStatus());
						} /*else{
							// only one person has approved
							bonus.setStatus("Pending Approval");
							//ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
							String actionTaken = "Approved By:[ <b>" + beginCompletedTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName() + endCompletedTag + " ,";
							List<Integer> pendingApproverIds = new ArrayList(Arrays.asList(pEval.approvers(processChainDto.getApprovalChain(), 4, 1)));
							pendingApproverIds.remove(login.getUserId());
							for (Iterator<Integer> idIter = pendingApproverIds.iterator(); idIter.hasNext();){
								actionTaken += beginPendingTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { idIter.next() })[0].getFirstName() + endPendingTag;
							}
							bonus.setHtmlStatus(actionTaken + "</b> ]");
							bonus.setActionButtonVisibility("disabled");
						}*/
						bonusDao.update(new BonusReconciliationPk(bonus.getId()), bonus);
						/*nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
						insertIntoReq(nextLevelApprovers, bonusForm.getBonusId(), 3);
						msgBody = sendMail(bonus.getStatus(), nextLevelApprovers, null, null, bonusString);
						sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, bonus.getStatus(), bonus.getStatus());*/
					}
				} catch (Exception ex){
					logger.error("BONUSRECONCILATION UPDATE CASE4 : failed to update data", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bonus.update.failed"));
					result.setForwardName("success");
					return result;
				}
				break;
			
		}
		
		
	}catch (Exception ex){
		logger.error("BONUSRECONCILATION UPDATE : failed to update data", ex);
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
			inbox.setCategory("BONUS_REPORT");
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setMessageBody(msgBody);
			inboxDao.insert(inbox);
		}
	}
	
	
	private void insertIntoReq(Integer[] approverIds, int bonusId, int seqId) throws BonusReconciliationReqDaoException, ProfileInfoDaoException {
		BonusReconciliationReq bonusReq = null;
		BonusReconciliationReqDao bonusReqDao = BonusReconciliationReqDaoFactory.create();
		for (Integer eachApproverId : approverIds){
			bonusReq = new BonusReconciliationReq();
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
			subject = "Bonus Report reviewed by Business for " + bonusMonth;
			templateName = MailSettings.BONUS_REVIEWED;
		} else if (status.equalsIgnoreCase("Rejected")){
			subject = "Bonus Report for " + bonusMonth + " Rejected by " + args;
			templateName = MailSettings.BONUS_REJECTED;
		} else if (status.equalsIgnoreCase("Accepted")){
			subject = "Bonus Report for " + bonusMonth + " Accepted by " + args;
			templateName = MailSettings.BONUS_ACCEPTED;
		} else if (status.equalsIgnoreCase("Approved")){
			subject = "Final Bonus Report for " + bonusMonth + " Approved";
			templateName = MailSettings.BONUS_APPROVED;
		} else if (status.equalsIgnoreCase("Report Generated")){
			templateName = MailSettings.BONUS_REPORT_AUTO_GENERATED;
			subject = "Bonus Report Generated for " + bonusMonth;
		} else if (status.equalsIgnoreCase("Completed")){
			subject = "Final Bonus Report for " + bonusMonth + " ready for disbursal";
			templateName = MailSettings.BONUS_COMPLETED;
		} else if (status.equalsIgnoreCase("Reviewed and Submitted")){
			subject = "Bonus Report reviewed by RMG for " + bonusMonth;
			templateName = MailSettings.BONUS_REVIEWED_SUBMITTED;
		} else{
			/*
			 * Bonus Details Submitted
			 * Final Bonus Details Submitted
			 * Bonus Details Re-Submitted
			 */
			templateName = MailSettings.BONUS_SUBMITTED;
			subject = "Diksha Lynx: Bonus Report submitted for "+bonusMonth;
		}
		portalMail.setMailSubject(subject);
		portalMail.setTemplateName(templateName);
		portalMail.setCommentsByFinanceTeam(args);
		portalMail.setBonusAcceptedOrApproved(status);
//		portalMail.setOnDate(onDate);
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
		BonusReconciliation bonusForm = (BonusReconciliation) form;
		
        
        String flag=bonusForm.getBankFlag();
        String brrid=bonusForm.getBrrid();
	
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access bonus reconciliation receive Urls without having permisson at :" + new Date());
			return null;
		}
		Attachements attachements = new Attachements();
		//BonusReconciliation bonusForm = (BonusReconciliation) form;
		PortalData portalData = new PortalData();
		String path = portalData.getfolder(portalData.getDirPath()) + "temp";
		try{
			File file = new File(path);
			if (!file.exists()) file.mkdir();
			BonusReconciliation bonus = BonusReconciliationDaoFactory.create().findByPrimaryKey(bonusForm.getBonusId());
			switch (DownloadTypes.getValue(form.getdType())) {
				case BONUSREPORT:
					if(flag!=null){
						  String[] str=brrid.split(",");
					        ArrayList<Integer> arraylist=new ArrayList<Integer>();
					        for(String w:str){
					        	
					        	Integer x=Integer.valueOf(w);
					        	arraylist.add(x);
					        	
					        }
						
						if (flag.equals("HDFC")){
							
							try{
								String month = " Bonus for month " + bonus.getMonth() + " " + bonus.getYear();
								attachements.setFileName(new GenerateXls().generateBonusReportInExcelHDFC(BonusReconciliationReportDaoFactory.create().findInternalReportDataHDFC(bonusForm.getBonusId(),arraylist), path + File.separator + "Bonus Internal Report_" + month + ".xls", month));
							} catch (Exception e){
								logger.error("unable to generate bonus report list", e);
							}
						}
						  else {
							
							try{
								String month = " Bonus for month " + bonus.getMonth() + " " + bonus.getYear();
								attachements.setFileName(new GenerateXls().generateBonusReportInExcelNONHDFC(BonusReconciliationReportDaoFactory.create().findInternalReportDataNONHDFC(bonusForm.getBonusId(), arraylist), path + File.separator + "Bonus Internal Report_" + month + ".xls", month));
							} catch (Exception e){
								logger.error("unable to generate bonus report list", e);
							}
							System.out.println("NON HDFC MODEL");
							
						  }	
					}
					else {
						try{
							String month = " Bonus for month " + bonus.getMonth() + " " + bonus.getYear();
							attachements.setFileName(new GenerateXls().generateBonusReportInExcel(BonusReconciliationReportDaoFactory.create().findInternalReportData(bonusForm.getBonusId()), path + File.separator + "Bonus Internal Report_" + month + ".xls", month));
						} catch (Exception e){
							logger.error("unable to generate bonus report list", e);
						}
						
						
					}
					
					
			}
			path = path + File.separator + attachements.getFileName();
			attachements.setFilePath(path);
					
		 }catch (Exception e){
			e.printStackTrace();
		}
		return attachements;
	}
	
/*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! These are Escalation Part !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

	public static void main(String[] args) {
		JDBCUtiility.getInstance().update("UPDATE BONUS_REC_HOLD SET ACTION_ON = ADDDATE(ACTION_ON, INTERVAL -1 DAY)", null);
		new BonusReconciliationModel().escalationforBonusHold();
	}
	
	public void escalationforBonusHold() {
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			BonusReconciliationHold bonusHold[] = BonusReconciliationHoldDaoFactory.create(conn).findByDynamicSelect("SELECT BRH.* FROM BONUS_REC_HOLD BRH JOIN BONUS_REC_REPORT BRR ON BRR.ID=BRH.REP_ID WHERE BRR.TYPE=" + ReconciliationModel.HOLD + " ORDER BY BRH.ID", null);
			Map<String, Date> dateMap = new HashMap<String, Date>();
			for (BonusReconciliationHold hold :bonusHold){
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
		} catch (Exception e){} finally{
		}
	}
   
	
	private void sendBonusHoldTask(BonusReconciliationHold hold, Connection conn, int days) {
		try{
			BonusReconciliationReport bonusReport = BonusReconciliationReportDaoFactory.create(conn).findByPrimaryKey(hold.getRepId());
			BonusReconciliation bonus = BonusReconciliationDaoFactory.create(conn).findByPrimaryKey(bonusReport.getBonusId());
			PortalMail mail = new PortalMail();
			mail.setTemplateName(MailSettings.BONUS_HOLD_TASK);
			mail.setEmployeeName(ModelUtiility.getInstance().getEmployeeName(bonusReport.getUserId(), conn));
			mail.setMailSubject("Diksha Lynx: Bonus on hold of " + mail.getEmployeeName());
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
		} catch (Exception e){}
	}

	private void escBonusHold(BonusReconciliationHold hold, Connection conn, int days) {
		try{
			BonusReconciliationReport bonusReport = BonusReconciliationReportDaoFactory.create(conn).findByPrimaryKey(hold.getRepId());
			BonusReconciliation bonus = BonusReconciliationDaoFactory.create(conn).findByPrimaryKey(bonusReport.getBonusId());
			ProcessChain processChainDto = ProcessChainDaoFactory.create(conn).findWhereProcNameEquals("IN_BONUS_RECON")[0];
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
				DepPerdiemHoldDao holdDao = DepPerdiemHoldDaoFactory.create(conn);
				PortalMail mail = new PortalMail();
				mail.setTemplateName(MailSettings.BONUS_HOLD_ESC);
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
					holdDao.insert(new DepPerdiemHold(hold.getRepId(), Integer.parseInt(id), 7, "", hold.getId()));
					inbox.populateInbox(bonus.getEsrMapId(), subject, "On Hold", Integer.parseInt(id), Integer.parseInt(id), 1, bodyText, "PERDIEM_RECON");
				}
				inbox.populateInbox(bonus.getEsrMapId(), mail.getMailSubject(), "On Hold", 0, hold.getUserId(), 1, bodyText, "BONUS_REPORT");
			}
		} catch (Exception e){}
	}

	private List<String> getList(Object[] array) {
		List<String> l = new ArrayList<String>();
		for (Object o : array)
			l.add(o.toString());
		return l;
	}
                                                 
	private void reminderBonusHold(BonusReconciliationHold hold, Connection conn) {
		try{
			PortalMail mail = new PortalMail();
			mail.setTemplateName(MailSettings.BONUS_HOLD_REMINDER);
			BonusReconciliationReport bonusReport = BonusReconciliationReportDaoFactory.create(conn).findByPrimaryKey(hold.getRepId());
			BonusReconciliation bonus = BonusReconciliationDaoFactory.create(conn).findByPrimaryKey(bonusReport.getBonusId());
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
			inbox.populateInbox(bonus.getEsrMapId(), mail.getMailSubject(), "On Hold", 0, hold.getUserId(), 1, bodyText, "BONUS_REPORT");
		} catch (Exception e){}
	}
	
	private List<BonusReconciliation> receiveEscalations(int loggedInUserId) throws ProfileInfoDaoException {
		EmpSerReqMapDao esrDao = EmpSerReqMapDaoFactory.create();
		BonusReconciliationDao bonusDao = BonusReconciliationDaoFactory.create();
		BonusReconciliationReqDao bonusReqDao = BonusReconciliationReqDaoFactory.create();
		List<BonusReconciliation> bonusList = new ArrayList<BonusReconciliation>();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		try{
			BonusReconciliationReq[] bonusReqs = bonusReqDao.findByDynamicWhere(" ASSIGNED_TO=? AND IS_ESCALATED !=0 AND SUBMITTED_ON IS NULL ORDER BY ID DESC", new Object[] { loggedInUserId });
			EmpSerReqMap esr = null;
			if (bonusReqs != null && bonusReqs.length > 0){
				for (BonusReconciliationReq eachBonusReq : bonusReqs){
					BonusReconciliation tempBonus = bonusDao.findByPrimaryKey(eachBonusReq.getBonusId());
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
		} catch (BonusReconciliationReqDaoException e){
			logger.error("There is BonusReqDaoException occured while querying the escalated bonus records for the user " + loggedInUserId);
		} catch (BonusReconciliationDaoException e){
			e.printStackTrace();
		} catch (EmpSerReqMapDaoException e){
			e.printStackTrace();
		}
		return bonusList;
	}
	
	
	////
	
	public int findintFormatofMonth(String  month){
		
		int MonthVal=0;
		if(month.equalsIgnoreCase("JANUARY")){
			MonthVal=1;
		}else if(month.equalsIgnoreCase("FEBRUARY")){
			MonthVal=2;
		}else if(month.equalsIgnoreCase("MARCH")){
			MonthVal=3;
		}else if(month.equalsIgnoreCase("APRIL")){
			MonthVal=4;
		}else if(month.equalsIgnoreCase("MAY")){
			MonthVal=5;
		}else if(month.equalsIgnoreCase("JUNE")){
			MonthVal=6;
		}else if(month.equalsIgnoreCase("JULY")){
			MonthVal=7;
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
		if(value==3)       month= "MARCH"; 
			else if(value==6)  month= "JUNE";
			else if(value==9)  month= "SEPTEMBER";
			else if(value==12) month= "DECEMBER";
			
			else if(value==1) month= "JANUARY";
			else if(value==2) month= "FEBRUARY";
			else if(value==4) month= "APRIL";
			else if(value==5) month= "MAY";
			else if(value==7) month= "JULY";
			else if(value==8) month= "AUGUST";
			else if(value==10) month= "OCTOBER";
			else if(value==11) month= "NOVEMBER";
		return month;
	}
	
	//After completed bonus then mail send to employee
	private void sendDisbursalMailToEmployees(int id ,  String bonusMonth, int bonusYear){
		int MonthVal=0;
		int MonthV=0;
		int MonthVal2=0;
		String monthValNew = "";
		String monthValNew1 = "";
		String MonthV1 = "";
		int BonusYear=0;
		int monthVal=findintFormatofMonth(bonusMonth);
		/**
		 * Convert String month in Integer and again convert Integer month value to String 
		 */
		if(bonusMonth.equalsIgnoreCase("MARCH")){
			 MonthVal=monthVal-2;
			 monthValNew=findStringFormatofMonth(MonthVal);
			 MonthVal2 = monthVal+1;
			 monthValNew1=findStringFormatofMonth(MonthVal2);
			 BonusYear=bonusYear-1;
			 MonthV=monthVal-1;
			 MonthV1 = findStringFormatofMonth(MonthV);
		}else if(bonusMonth.equalsIgnoreCase("JUNE")){
			 MonthVal=monthVal-2;
			 monthValNew=findStringFormatofMonth(MonthVal);
			 //BonusYear=bonusYear+1;
		}else if(bonusMonth.equalsIgnoreCase("SEPTEMBER")){
			 MonthVal=monthVal-2;
			 monthValNew=findStringFormatofMonth(MonthVal);
		}else if(bonusMonth.equalsIgnoreCase("DECEMBER")){
			 MonthVal=monthVal-2;
			 monthValNew=findStringFormatofMonth(MonthVal);
		}
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			BonusReconciliationReport[] depPerdiemReports = BonusReconciliationReportDaoFactory.create(conn).findWhereBonusIdEquals(id);
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
			PortalMail portalMail = new PortalMail();
			portalMail.setTemplateName(MailSettings.BONUS_DISBURSAL);
			MailGenerator mailGenerator = new MailGenerator();
			DepPerdiemExchangeRates[] rates = DepPerdiemExchangeRatesDaoFactory.create(conn).findWhereRepIdEquals(id);
			Map<String, Double> exchangeRates = new HashMap<String, Double>();
			if (rates != null && rates.length > 0){
				for (DepPerdiemExchangeRates rate : rates)
					exchangeRates.put(rate.getCurrencyType() + "", rate.getAmount());
			}
			Map<String, List<BonusReconciliationReport>> depPerdiemReportsMap = new HashMap<String, List<BonusReconciliationReport>>();
			for (BonusReconciliationReport report : depPerdiemReports){
				if (report.getType() == DELETED || report.getType() == FIXED_DELETED ||report.getType() == HOLD ||report.getType() == FIXED_HOLD) continue;
				List<BonusReconciliationReport> reports = depPerdiemReportsMap.get(report.getUserId() + "");
				if (reports == null){
					reports = new ArrayList<BonusReconciliationReport>();
					depPerdiemReportsMap.put(report.getUserId() + "", reports);
				}
				reports.add(report);
			}
			for (Map.Entry<String, List<BonusReconciliationReport>> entry : depPerdiemReportsMap.entrySet()){
				try{
					List<BonusReconciliationReport> reportsList = entry.getValue();
					Collections.sort(reportsList);
					Date fromDate = null, toDate = null;
					int userId = 0;
					if (reportsList.size() > 0){
						for (BonusReconciliationReport report : reportsList){
							userId = report.getUserId();
							if (fromDate == null) fromDate = report.getFrom();
							else if (fromDate.after(report.getFrom())) fromDate = report.getFrom();
							if (toDate == null) toDate = report.getTo();
							else if (toDate.before(report.getTo())) toDate = report.getTo();
						}
					}
					portalMail.setBonusMonth(bonusMonth);
					portalMail.setMailSubject("Diksha: Bonus Statement: " + bonusMonth);
					ProfileInfo info = profileInfoDao.findWhereUserIdEquals(userId);
					portalMail.setEmployeeName(info.getFirstName() + " " + info.getLastName());
					portalMail.setRecipientMailId(info.getOfficalEmailId());
					if (info.getHrSpoc() > 3) portalMail.setAllReceipientcCMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE PI.ID IN(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID>3 AND U.ID IN(" + info.getHrSpoc() + "))"));
					StringBuffer body = new StringBuffer();
					double total = 0;
					for (BonusReconciliationReport report : reportsList){
						body.append("<tr><td>");
						/**
						 * Set Bonus Statement
						 */
						/*if(bonusMonth.equalsIgnoreCase("March")){
							body.append("Quarterly Performance Bonus");
							//body.append("<tr><td>&nbsp;");
							//body.append("Annual Performance Bonus");
						}else{*/
							body.append("Quarterly Performance Bonus");
						//}
						/*body.append("</td><td>");
						if(bonusMonth.equalsIgnoreCase("March")){
							//body.append("Quarterly Performance Bonus");
							//body.append("<tr><td>&nbsp;");
							body.append("Annual Performance Bonus");
						}*/
						body.append("</td><td align='center'>");
						/**
						 * Set Effectice From Date 
						 */
						if(bonusMonth.equalsIgnoreCase("June")){
							body.append("01-"+monthValNew+"-"+bonusYear);
						}else if(bonusMonth.equalsIgnoreCase("March")){
							body.append("01-"+monthValNew+"-"+bonusYear);
							//body.append("</td> <td align='center'>");
							//body.append("01-"+monthValNew1+"-"+bonusYear);
						}else if(bonusMonth.equalsIgnoreCase("December")){
							body.append("01-"+monthValNew+"-"+bonusYear);
						}else if(bonusMonth.equalsIgnoreCase("September")){
							body.append("01-"+monthValNew+"-"+bonusYear);
						}//from Date 
						/*body.append("</td><td>");
						 if(bonusMonth.equalsIgnoreCase("March")){
								//body.append("01-"+monthValNew+"-"+bonusYear);
								//body.append("</td> <td align='center'>");
								body.append("01-"+monthValNew1+"-"+bonusYear);
							}*/
						body.append("</td><td align='center'>");
						//body.append("01-"+monthValNew1+"-"+bonusYear);
						/**
						 * Set Effectice To Date
						 */
						if(bonusMonth.equalsIgnoreCase("June")){
							body.append("30-"+bonusMonth+"-"+bonusYear);
						}else if(bonusMonth.equalsIgnoreCase("March")){
							body.append("31-"+bonusMonth+"-"+bonusYear);
							//body.append("</td> <td align='center'>");
							//body.append("28-"+MonthV1+"-"+BonusYear);
							//body.append("</td></tr>");
						}else if(bonusMonth.equalsIgnoreCase("December")){
							body.append("31-"+bonusMonth+"-"+bonusYear);
						}else if(bonusMonth.equalsIgnoreCase("September")){
							body.append("30-"+bonusMonth+"-"+bonusYear);
						}//To date
						
						/*body.append("</td><td>");
						if(bonusMonth.equalsIgnoreCase("March")){
							//body.append("31-"+bonusMonth+"-"+bonusYear);
							//body.append("</td> <td align='center'>");
							body.append("28-"+MonthV1+"-"+BonusYear);
							//body.append("</td></tr>");
						}*/
						body.append("</td><td align='right'>");
						double NewAmount = Double.parseDouble(report.getqAmountInr());
						if(NewAmount == 0 || Double.parseDouble(report.getqAmountInr()) == 0){
							body.append(new DecimalFormat("0.00").format(Double.parseDouble(report.getqAmount())));
							total += Double.parseDouble(report.getqAmount());
						}else{
							body.append(new DecimalFormat("0.00").format(Double.parseDouble(report.getqAmountInr())));
							total += Double.parseDouble(report.getqAmountInr());
						}
						//body.append(new DecimalFormat("0.00").format(Double.parseDouble(report.getqAmountInr())));
						//total += Double.parseDouble(report.getqAmountInr());
						body.append("</td></tr>");
						/*if(bonusMonth.equalsIgnoreCase("March")){
							body.append("</td><td align='right'>");
							body.append(new DecimalFormat("0.00").format(Double.parseDouble(report.getcAmountInr())));
							total += Double.parseDouble(report.getcAmountInr());
							body.append("</td></tr>");
						}else{
							body.append("</td></tr>");
						}*/
						
					//}
					//body.append("<tr><td>");
						/*if(bonusMonth.equalsIgnoreCase("March")){
							body.append("<tr><td>");
						}*/
					/*if(bonusMonth.equalsIgnoreCase("March")){
						body.append("<tr><td>");
						//body.append("Quarterly Performance Bonus");
						//body.append("<tr><td>&nbsp;");
						body.append("Annual Performance Bonus");
					}
					body.append("</td><td align='center'>");
					if(bonusMonth.equalsIgnoreCase("March")){
						//body.append("01-"+monthValNew+"-"+bonusYear);
						//body.append("</td> <td align='center'>");
						body.append("01-"+monthValNew1+"-"+BonusYear);
					}
					body.append("</td><td align='center'>");
					if(bonusMonth.equalsIgnoreCase("March")){
						//body.append("31-"+bonusMonth+"-"+bonusYear);
						//body.append("</td> <td align='center'>");
						body.append("31-"+bonusMonth+"-"+bonusYear);
						//body.append("</td></tr>");
					}
					if(bonusMonth.equalsIgnoreCase("March")){
						body.append("</td><td align='right'>");
						body.append(new DecimalFormat("0.00").format(Double.parseDouble(report.getcAmountInr())));
						total += Double.parseDouble(report.getcAmountInr());
						body.append("</td></tr>");
					}*/
						if(bonusMonth.equalsIgnoreCase("March")){
							body.append("<tr><td>");
							body.append("Annual Performance Bonus");
							body.append("</td><td align='center'>");
							body.append("01-"+monthValNew1+"-"+BonusYear);
							body.append("</td><td align='center'>");
							body.append("31-"+bonusMonth+"-"+bonusYear);
							body.append("</td><td align='right'>");
							body.append(new DecimalFormat("0.00").format(Double.parseDouble(report.getcAmountInr())));
							total += Double.parseDouble(report.getcAmountInr());
							body.append("</td></tr>");
						}
					/*body.append("</td><td align='right'>");
					body.append(new DecimalFormat("0.00").format(Double.parseDouble(report.getcAmountInr())));
					total += Double.parseDouble(report.getcAmountInr());
					body.append("</td></tr>");*/
				}
					/**
					 * Set the bonus components
					 */
					for (BonusReconciliationReport report : reportsList){
						BonusComponents[] components = BonusComponentsDaoFactory.create().findWhereRepIdEquals(report.getId());
						if (components != null && components.length > 0){
							for (BonusComponents component : components){
								body.append("<tr><td>");
								body.append((component.getType() == 1 ? "Add " : "Less") + " : " + component.getReason());
								body.append("</td><td>");
								body.append("</td><td align='center'>");
								body.append("</td><td align='right'>");
								body.append((component.getType() == 1 ? "" : "-") + new DecimalFormat("0.00").format(Double.parseDouble(component.getAmount())));
								if (component.getType() == 1) total += Double.parseDouble(component.getAmount());
								else total -= Double.parseDouble(component.getAmount());
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
	
	}

}