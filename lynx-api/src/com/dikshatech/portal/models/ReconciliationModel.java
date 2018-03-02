package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.EmployeePerdiem;
import com.dikshatech.beans.PerdiemReportBean;
import com.dikshatech.beans.SalaryReportBean;
import com.dikshatech.common.utils.ExportType;
import com.dikshatech.common.utils.GenerateXls;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.POIParser;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.common.utils.ReconciliationReportGeneratorAndNotifier;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DepPerdiemComponentsDao;
import com.dikshatech.portal.dao.DepPerdiemDao;
import com.dikshatech.portal.dao.DepPerdiemExchangeRatesDao;
import com.dikshatech.portal.dao.DepPerdiemHoldDao;
import com.dikshatech.portal.dao.DepPerdiemLeaveDao;
import com.dikshatech.portal.dao.DepPerdiemReportDao;
import com.dikshatech.portal.dao.DepPerdiemReportHistoryDao;
import com.dikshatech.portal.dao.DepPerdiemReqDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.PerdiemInAdvanceDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dto.DepPerdiem;
import com.dikshatech.portal.dto.DepPerdiemComponents;
import com.dikshatech.portal.dto.DepPerdiemExchangeRates;
import com.dikshatech.portal.dto.DepPerdiemHold;
import com.dikshatech.portal.dto.DepPerdiemLeave;
import com.dikshatech.portal.dto.DepPerdiemPk;
import com.dikshatech.portal.dto.DepPerdiemReport;
import com.dikshatech.portal.dto.DepPerdiemReportHistory;
import com.dikshatech.portal.dto.DepPerdiemReq;
import com.dikshatech.portal.dto.DepPerdiemReqPk;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.FinanceInfo;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.PerdiemInAdvance;
import com.dikshatech.portal.dto.PerdiemInAdvancePk;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.SalaryReconciliationReport;
import com.dikshatech.portal.exceptions.DepPerdiemComponentsDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemHistoryDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemReportDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemReqDaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.PerdiemInAdvanceDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.factory.CurrencyDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemComponentsDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemExchangeRatesDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemHoldDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemLeaveDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemReportDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemReportHistoryDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemReqDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.FinanceInfoDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.PerdiemInAdvanceDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
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
import com.dikshatech.portal.timer.EscalationJob;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReconciliationModel extends ActionMethods {

	private static Logger		logger			= LoggerUtil.getLogger(ReconciliationModel.class);
	public static final short	AUTO			= 0;
	public static final short	DELETED			= 1;
	public static final short	ADDED			= 2;
	public static final short	MODIFIED		= 3;
	public static final short	ADDED_DELETED	= 4;
	public static final short	FIXED			= 5;
	public static final short	FIXED_DELETED	= 6;
	public static final short	HOLD			= 7;
	public static final short	FIXED_HOLD		= 8;
	public static final short	RELEASED		= 9;
	public static final short	FIXED_RELEASED	= 10;
	public static final short	REJECTED		= 11;
	public static final short	FIXED_REJECTED	= 12;
	public static final int		MODULEID		= 64;

	protected java.sql.Connection	userConn;
	
	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		Login login = Login.getLogin(request);
		ActionResult result = new ActionResult();
		DepPerdiem perdiemForm = (DepPerdiem) form;
		if (ModelUtiility.hasModulePermission(login, MODULEID)){
		
			int res = new ReconciliationReportGeneratorAndNotifier().generateReportAndNotify(perdiemForm.getId() == 0 ? 0 : login.getUserId());
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
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access perdiem reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			EmpSerReqMapDao esrDao = EmpSerReqMapDaoFactory.create(conn);
			DepPerdiemDao perdiemDao = DepPerdiemDaoFactory.create(conn);
			DropDown dropdown = new DropDown();
			DepPerdiemReqDao perdiemReqDao = DepPerdiemReqDaoFactory.create(conn);
			LevelsDao levelDao=LevelsDaoFactory.create(conn);
			
			DepPerdiem perdiemForm = (DepPerdiem) form;
			DepPerdiem dp=new DepPerdiem();
			dp.setIsnotiBusiness("0");
			ProcessChainDao processChainDao = ProcessChainDaoFactory.create(conn);
			ProcessEvaluator pEval = new ProcessEvaluator();
			ProcessChain processChainDto = processChainDao.findWhereProcNameEquals("IN_DEPUTATION_PROC")[0];
			
			// Added newly for only view(Business Level)
			int usId=login.getUserId();
			Integer[] viewersIds = pEval.notifiers(processChainDto.getNotification(), 1);
			for(Integer eachViewer:viewersIds){
				if(eachViewer==usId){
					dp.setIsnotiBusiness("1");
				}
				
			}
			
			
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
				case RECEIVEALL:{// show only !escalated requests which was once received by me
					try{
						DepPerdiemReq[] perdiemReqs = null;
					
						int levelss=0;
						if(dp.getIsnotiBusiness().equalsIgnoreCase("1")){
						perdiemReqs	= perdiemReqDao.findAll();
						}else
						perdiemReqs = perdiemReqDao.findByDynamicWhere(" ASSIGNED_TO=? AND IS_ESCALATED=0 GROUP BY DEP_ID", new Object[] { login.getUserId() });
						dropdown.setKey1(50);// seqId
						
						 levelss =levelDao.findWhereUsersIdEquals(usId);
						dropdown.setLevelId(levelss);
					
						HashMap<Integer, DepPerdiem> perdiemMap = new HashMap<Integer, DepPerdiem>();
					
						if (perdiemReqs != null && perdiemReqs.length > 0){
							HashMap<Integer, String> idReqIdMap = esrDao.getIdReqIdMap(16);
							if (idReqIdMap != null && idReqIdMap.size() > 0){
								for (DepPerdiemReq eachPerdiemReq : perdiemReqs){
									DepPerdiem tempPerdiem = perdiemDao.findByPrimaryKey(eachPerdiemReq.getDepId());
									tempPerdiem.setReqId(idReqIdMap.get(tempPerdiem.getEsrMapId()));
						//			tempPerdiem.setStatus(tempPerdiem.getHtmlStatus());
									if ("Report Generated".equals(tempPerdiem.getStatus())){
										DepPerdiemExchangeRates[] rates = DepPerdiemExchangeRatesDaoFactory.create(conn).findWhereRepIdEquals(tempPerdiem.getId());
										if (rates == null || rates.length == 0) tempPerdiem.setrType("1");
									}
									perdiemMap.put(tempPerdiem.getId(), tempPerdiem);
								}
							}
						}
						
						if(dp.getIsnotiBusiness().equalsIgnoreCase("0")){
						for (Map.Entry<Integer, DepPerdiem> entrySet : perdiemMap.entrySet()){
							int depId = entrySet.getKey();
							DepPerdiemReq perdiemReq = perdiemReqDao.findByDynamicWhere(" ASSIGNED_TO=? AND DEP_ID=? ORDER BY ID DESC", new Object[] { login.getUserId(), depId })[0];
							int s=perdiemReq.getSeqId();
							System.out.println("seqid"+s);
							
							if (perdiemReq.getSubmittedOn() == null){
								perdiemMap.get(depId).setActionButtonVisibility("enable");
								
							} else{
								perdiemMap.get(depId).setActionButtonVisibility("disable");
							}
						}
						}
						// boolean found = false;
						for (int appLevel = 1; appLevel <= 4; appLevel++){
							Integer[] approverIds = pEval.approvers(processChainDto.getApprovalChain(), appLevel, 1);
							
							for (Integer eachApproverId : approverIds){
								
								if (eachApproverId == usId){
							// hard coded view because finance changed appLevel 2 and final approval changed to appLevel 3		
								if(appLevel==2){
										dropdown.setKey1(3);
									}else if(appLevel==3){
										dropdown.setKey1(3);
									}else if(appLevel==4)
									{
										dropdown.setKey1(4);
									}
									else
									dropdown.setKey1(appLevel);
									break;
								}
							}
						// Checking for the user Whether there are any escalated requests and setting the flag call the another url
							DepPerdiemReq[] escPerdiemReq = perdiemReqDao.findByDynamicWhere(" ASSIGNED_TO=? AND IS_ESCALATED !=0 ORDER BY ID DESC", new Object[] { login.getUserId() });
							if (escPerdiemReq != null && escPerdiemReq.length > 0){
								dropdown.setKey2(1);
							}
						}
						
						// Added newly for only view(Business Level)		
	//					Integer[] viewersIds = pEval.notifiers(processChainDto.getNotification(), 1);
						for(Integer eachViewer:viewersIds){
							if(eachViewer==usId){
								dropdown.setKey1(100);
							}
							
						}
						
						// Added newly for only Initiate view		
						Integer[] handlersIds = pEval.notifiers(processChainDto.getHandler(), 1);
						for(Integer eachHanler:handlersIds){
							if(eachHanler==usId){
								dropdown.setKey1(5);
							}
							
						}
						
						List<DepPerdiem> escalatedPerdiemList = receiveEscalations(login.getUserId());// only escalated requests
						if (escalatedPerdiemList != null){}
						List<DepPerdiem> perdiemList = new ArrayList(perdiemMap.values());// non escalated requests
						perdiemList.addAll(escalatedPerdiemList);
						DepPerdiem[] depPerdiems = new DepPerdiem[perdiemList.size()];
						new ArrayList<DepPerdiem>(perdiemList).toArray(depPerdiems);
						
						dropdown.setDropDown(depPerdiems);
					} catch (Exception ex){
						logger.error("RECONCILATION RECEIVEALL : failed to fetch data for userId=" + login.getUserId(), ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
					break;
				}
				case RECEIVEESCALATIONS:{
					break;
				}
				case RECEIVEREPORT:{
					//String flag = depPerdiemReport.getBankflag();
					String flag=perdiemForm.getBankFlag();
					
					
					
					try{
						DepPerdiemReport[] depPerdiemReports=null;
						DepPerdiemReq[] perdiemReq=null;
						if(dp.getIsnotiBusiness().equalsIgnoreCase("1")){
						perdiemReq	= perdiemReqDao.findAll();
							}else
						perdiemReq= perdiemReqDao.findByDynamicWhere(" DEP_ID = ? AND ASSIGNED_TO=?  ORDER BY ID DESC", new Object[] { perdiemForm.getDepId(), login.getUserId() });
						if (perdiemReq != null && perdiemReq.length > 0 && perdiemReq[0] != null){							
							
							DepPerdiemReportDao depPerdiemReportDao = DepPerdiemReportDaoFactory.create(conn);
							int bUserId = login.getUserId().intValue();
						
						if (flag!=null){
							
							if (flag.equals("HDFC_BANK")){
								depPerdiemReports = depPerdiemReportDao.findWhereDepIdEqualsHdfc(perdiemForm.getDepId());
								
								List<Object> BankList=new ArrayList<Object>();
																													
								if (depPerdiemReports != null && depPerdiemReports.length > 0){
									
									for (DepPerdiemReport report : depPerdiemReports){
										
										
										
										
										if(report.getSalaryCycle()==null){
											report.setSalaryCycle("");
											
										}
										
										if(report.getModifiedOn()==null || report.getModifiedOn().equals("")){
											report.setModifiedOn(null);
											
										}
										
										
										if(report.getPaid()==null){
											 
											 report.setPaid("Un Paid");
										 }
										BankList.add(report);
										}
									
									}
								perdiemForm.setListHdfc(BankList.toArray(new DepPerdiemReport[BankList.size()]));
							request.setAttribute("actionForm", perdiemForm);
								
							//	request.setAttribute("actionForm", map);
								
								
							}
							else {
								
								depPerdiemReports = depPerdiemReportDao.findWhereDepIdEqualsNonHdfc(perdiemForm.getDepId());
							
							List<Object> BankList=new ArrayList<Object>();
							
							
								if (depPerdiemReports != null && depPerdiemReports.length > 0){
							
								
								for (DepPerdiemReport report : depPerdiemReports){
									
									
									if(report.getSalaryCycle()==null){
										report.setSalaryCycle("");
										
									}
									
									 if(report.getPaid()==null){
										 
										 report.setPaid("UnPaid");
									 }
									 
									
									BankList.add(report);
								
										
									}
								}
								perdiemForm.setListNonHdfc(BankList.toArray(new DepPerdiemReport[BankList.size()]));
						 
		
							           request.setAttribute("actionForm", perdiemForm);
							
							
							}
						}	
						
						else {
						    depPerdiemReports = depPerdiemReportDao.findWhereDepIdEquals(perdiemForm.getDepId());
							// Corp,Customer Billable,Diksha Billable,Business ,RMG, TAT, Finance, Bench ,Resigned	
							if (depPerdiemReports != null && depPerdiemReports.length > 0){
								List<Map<String, Object>> auto = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> added = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> modified = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> deleted = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> fixed = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> hold = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> released = new ArrayList<Map<String, Object>>();
								List<Map<String, Object>> rejected = new ArrayList<Map<String, Object>>();
								Map<String, String> currencyTypes = CurrencyDaoFactory.create(conn).getAllCurrencyTypes();
								double totalAmount = 0.0;
								for (DepPerdiemReport report : depPerdiemReports){
									if (report.getAmount() == null || report.getAmountInr() == null || report.getTotal() == null){
										if (report.getAmount() == null) report.setAmount(Double.parseDouble(report.getPerdiem()) * report.getPayableDays() + "");
										if (report.getAmountInr() == null) report.setAmountInr(report.getAmount());
										if (report.getTotal() == null) report.setTotal(report.getAmount());
										if (report.getFrom() == null) report.setFrom(new Date());
										if (report.getTo() == null) report.setTo(new Date());
										depPerdiemReportDao.update(report.createPk(), report);
									}
									report.setSalaryCycle(report.getSalaryCycle());
									report.setCurrencyName(currencyTypes.get(report.getCurrencyType() + ""));
									switch (report.getType()) {
										case AUTO:
											auto.add(report.toMap(1,report.getCategory()));
											totalAmount += Double.parseDouble(report.getTotal());
											break;
										case ADDED:
											 
											added.add(report.toMap(1,report.getCategory()));
											totalAmount += Double.parseDouble(report.getTotal());
											break;
										case MODIFIED:
											
											modified.add(report.toMap(1,report.getCategory()));
											totalAmount += Double.parseDouble(report.getTotal());
											break;
										case DELETED:
										case ADDED_DELETED://added deleted
										case FIXED_DELETED:// fixed deletd
											deleted.add(report.toMap(1,report.getCategory()));
											break;
										case FIXED:
										/*	if (perdiemReq[0].getSeqId() == 2){
											DepPerdiemReport[] tempFixedPerdiemReport=null;
											tempFixedPerdiemReport=depPerdiemReportDao.findByDynamicWhere(" ID=? AND DEP_ID=? AND MANAGER_ID=? AND TYPE=5 ",new Object[]{report.getId(),perdiemForm.getDepId(), bUserId});
											
											if(tempFixedPerdiemReport!=null && tempFixedPerdiemReport.length>0){
												for(DepPerdiemReport tempRep:tempFixedPerdiemReport){
												tempRep.setEmpId(report.getEmpId());
												tempRep.setEmployeeName(report.getEmployeeName());
												tempRep.setCurrencyName(report.getCurrencyName());
								            fixed.add(tempRep.toMap(1));}}
											}
											else*/ fixed.add(report.toMap(1,report.getCategory()));
											totalAmount += Double.parseDouble(report.getTotal());
											break;
										case HOLD:
										case FIXED_HOLD:
											report.setRelease(canRelease(report.getId(), login.getUserId(), conn));
											hold.add(report.toMap(1,report.getCategory()));
											totalAmount += Double.parseDouble(report.getTotal());
											break;
										case RELEASED:
										case FIXED_RELEASED:
											released.add(report.toMap(1,report.getCategory()));
											totalAmount += Double.parseDouble(report.getTotal());
											break;
										case REJECTED:
										case FIXED_REJECTED:
											rejected.add(report.toMap(1,report.getCategory()));
											break;
										default:
											break;
									}
								}
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("auto", auto);
								map.put("added", added);
								map.put("modified", modified);
								map.put("deleted", deleted);
								map.put("fixed", fixed);
								map.put("hold", hold);
								map.put("released", released);
								map.put("rejected", rejected);
								map.put("total", new DecimalFormat("0.00").format(totalAmount));
								
								request.setAttribute("actionForm", map);
							}
							}
								return result;
							
						}
					} catch (Exception e){
						logger.error("RECONCILATION RECEIVEEMPLOYEESFORPERDIEM : failed to fetch data", e);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
					}
					break;
				}
				case RECEIVEALLPAIDANDUNPAID:{
					
					int id=perdiemForm.getDepId();
					
					String flag=perdiemForm.getBankFlag();
                   
               	
					try{
						DepPerdiemReport[] depPerdiemReports=null;

						double total = 0;
						if(flag!=null){
							
							depPerdiemReports=DepPerdiemReportDaoFactory.create(conn).findAllPaidAndUnpaid(perdiemForm.getDepId(), flag);
	                        
					
							List<Object> BankList=new ArrayList<Object>();
						 
						 
						 	if (depPerdiemReports != null && depPerdiemReports.length > 0){
							
								
								for (DepPerdiemReport report : depPerdiemReports){
									
									
									if(report.getSalaryCycle()==null){
										report.setSalaryCycle("");
										
									}
									
									 if(report.getPaid()==null){
										 
										 report.setPaid("Un Paid");
									 }
									 
									
									BankList.add(report);
								
										
									}
								}
						 	if (flag.equals("HDFC_BANK")){
						 		perdiemForm.setListHdfc(BankList.toArray(new DepPerdiemReport[BankList.size()]));		
						 	}
						 	else {
						 		perdiemForm.setListNonHdfc(BankList.toArray(new DepPerdiemReport[BankList.size()]));
						 	}
		
							request.setAttribute("actionForm", perdiemForm);
							
						}	
						return result;
					}	
					catch (Exception ex){
                        logger.error("RECONCILATION RECEIVE : failed to fetch data", ex);
                        result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
                        
                }
				}
					break;
			
				
				
			case RECEIVEPAY: {
				DepPerdiem sss = new DepPerdiem();
				String s2 = sss.getBankFlag();
				try {
					String updateReceivePay = null;

					DepPerdiem depPerdiemReports = DepPerdiemDaoFactory.create(conn)
							.findByPrimaryKey(perdiemForm.getDepId());
					perdiemForm.setTerm(getTerm(depPerdiemReports.getMonth(), depPerdiemReports.getYear()));
					DepPerdiemReport dep = new DepPerdiemReport();

					String flag = perdiemForm.getBankFlag();
					double total = 0;
					double valueRounded1 = 0;
					List<DepPerdiemReport> list = new ArrayList<DepPerdiemReport>();

					String ddpid = perdiemForm.getTotalAmount();// getDeppid();

					String[] str = ddpid.split(",");

					ArrayList<Integer> amt = new ArrayList<Integer>();
					for (String w : str) {

						total += Float.valueOf(w);
						valueRounded1 = Math.round(total * 100D) / 100D;

					}
					request.setAttribute("actionForm", updateReceivePay);
					MailGenerator mailGenerator = new MailGenerator();
					PortalMail pMail = new PortalMail();
					List<String> allReceipientcCMailId = new ArrayList<String>();
					Properties pro = PropertyLoader.loadProperties("conf.Portal.properties");
					String regAbbrivation = "IN";
					String mailId;
					mailId = pro.getProperty("mailId." + regAbbrivation + ".CEO");
					allReceipientcCMailId.add(mailId);
					pMail.setMailSubject("Amount Debited");
					pMail.setTotalAmount(Double.toString(valueRounded1));
					pMail.setTemplateName(MailSettings.AMOUNT_TO_BE_DEDUCTED);
					pMail.setAllReceipientMailId(
							allReceipientcCMailId.toArray(new String[allReceipientcCMailId.size()]));
					mailGenerator.invoker(pMail);

				}

				catch (Exception ex) {
					logger.error("RECONCILATION RECEIVE : failed to fetch data", ex);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("deputation.receive.failed"));
					return result;
				}
				break;
			}
				/*
				 * if seqId in(0,1) show all resources
				 * if seqId = 2 show resources reporting to logged in user
				 * if perdiemForm.getEscalatedFromId() > 0 ==> show resources reporting to this person
				 */
				case RECEIVE:{
					DepPerdiemReportDao depPerdiemReportDao = DepPerdiemReportDaoFactory.create(conn);
					try{
						DepPerdiemReportHistoryDao historyDao = DepPerdiemReportHistoryDaoFactory.create(conn);
						DepPerdiemReport report = depPerdiemReportDao.findByPrimaryKey(perdiemForm.getId());
						/*Map<String,String> deductions=new HashMap<String,String>();
						
						deductions.put("LwpDays", report.getLwpDays() !=null ? String.valueOf(report.getLwpDays()) : "0.0");
						deductions.put("SalaryAdvanceDeduction", report.getSalaryAdvanceDeduction() !=null ? report.getSalaryAdvanceDeduction() : "0.0");
						deductions.put("TravelAdvanceDeduction", report.getTravelAdvanceDeduction() !=null ? report.getTravelAdvanceDeduction() : "0.0");*/
					
						
						ArrayList<Object> a1 = new ArrayList<Object>();
						Map<String,String> deductions = new HashMap<String,String>();
						a1.add(deductions);
						deductions.put("LwpDays", report.getLwpDays() !=null ? String.valueOf(report.getLwpDays()) : "0.0");
						deductions.put("SalaryAdvanceDeduction", report.getSalaryAdvanceDeduction() !=null ? report.getSalaryAdvanceDeduction() : "0.0");
						deductions.put("TravelAdvanceDeduction", report.getTravelAdvanceDeduction() !=null ? report.getTravelAdvanceDeduction() : "0.0");
						/*deductions.add(report.getLwpDays() !=null ? String.valueOf(report.getLwpDays()) : "0.0");
						deductions.add(report.getSalaryAdvanceDeduction() !=null ? report.getSalaryAdvanceDeduction() : "0.0");
						deductions.add(report.getTravelAdvanceDeduction() !=null ? report.getTravelAdvanceDeduction() : "0.0");
						*/
						/*Map<String,String> deductions = new HashMap<String,String>();
						a1.add(deductions);
						deductions.put("LwpDays", "25.5");
						deductions.put("SalaryAdvanceDeduction", "750.5");
						deductions.put("TravelAdvanceDeduction", "4225.5");*/
		                Map<String, String> currencyTypes = null;
						DepPerdiemReportHistory[] history = historyDao.findByDepPerdiemReport(report.getId());
						int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
						String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
						List<Map<String, String>> historyList = new ArrayList<Map<String, String>>();
						if (report.getModifiedBy() != null && !report.getModifiedBy().equalsIgnoreCase(empName)){
							DepPerdiemReportHistory his = report.getDepPerdiemReportHistory();
							report.setComments(null);
							if (currencyTypes == null) currencyTypes = CurrencyDaoFactory.create(conn).getAllCurrencyTypes();
							his.setCurrencyName(currencyTypes.get(his.getCurrencyType() + ""));
							historyList.add(his.toHashMap());
						}
						
						//case 2 important
						Map<String, Object> map = report.toMap1(2);
						for (DepPerdiemReportHistory his : history){
							if (currencyTypes == null) currencyTypes = CurrencyDaoFactory.create(conn).getAllCurrencyTypes();
							his.setCurrencyName(currencyTypes.get(his.getCurrencyType() + ""));
							historyList.add(his.toHashMap());
						}
						if (historyList.size() > 0) map.put("history", historyList);
						DepPerdiemComponents[] components = DepPerdiemComponentsDaoFactory.create(conn).findWhereRepIdEquals(report.getId());
						if (components != null && components.length > 0) map.put("components", components);
						DepPerdiemLeave[] leave = DepPerdiemLeaveDaoFactory.create(conn).findWhereRepIdEquals(report.getId());
						if (leave != null && leave.length > 0) map.put("leave", leave);
						map.put("deductions", deductions);
					//	String a="<?xml version='1.0' encoding='UTF-8'?><actionForm><currencyType>1</currencyType><lwpDays>0.0</lwpDays><amount>12000.00</amount><perdiem>800.00</perdiem><comments /><type>5</type><LwpDays>0.0</LwpDays><deductions><SalaryAdvanceDeduction>0.0</SalaryAdvanceDeduction><TravelAdvanceDeduction>0.0</TravelAdvanceDeduction></deductions><total>12000.00</total><payableDays>15.0</payableDays><salaryCycle /><amountInr>12000.00</amountInr></actionForm>";
						request.setAttribute("actionForm", map);
						return result;
					} catch (Exception e){
						e.printStackTrace();
					}
				}
					break;
				case RECEIVEEMPLOYEESFORPERDIEM:{
					try{
						boolean isFirstLevel = true;
						if (perdiemForm.getDepId() > 0){
							String status = perdiemDao.findByPrimaryKey(perdiemForm.getDepId()).getStatus();
							if (!(status.equalsIgnoreCase("REPORT GENERATED") || status.equalsIgnoreCase("REJECTED") || status.equalsIgnoreCase("SUBMITTED"))){
								isFirstLevel = false;
							}
						}
						int loggedInPerson;
						if (perdiemForm.getEscalatedFromId() > 0){
							// logged in person must see "this' person's resources
							loggedInPerson = perdiemForm.getEscalatedFromId();
						} else{
							loggedInPerson = login.getUserId();// seqId = 2 :)
						}
						List<EmployeePerdiem> employeePerdiemList = UsersDaoFactory.create(conn).receiveAllEmployeesForPerdiem(new Integer[] { perdiemForm.getCompanyId(), perdiemForm.getDepId() }, isFirstLevel, loggedInPerson);
						dropdown.setDropDown(employeePerdiemList.toArray());
					} catch (Exception ex){
						logger.error("RECONCILATION RECEIVEEMPLOYEESFORPERDIEM : failed to fetch data", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
						result.setForwardName("success");
						return result;
					}
				}
					break;
				case CURRENCYRATES:{
					try{
						request.setAttribute("actionForm", getCurrencies(perdiemForm.getDepId()));
						return result;
					} catch (Exception e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				case BANKACCOUNTS:{
					DepPerdiemReport report = DepPerdiemReportDaoFactory.create(conn).findByPrimaryKey(perdiemForm.getId());
					FinanceInfo finance = FinanceInfoDaoFactory.create(conn).findByUserId(report.getUserId());
					Map<String, String> map = new HashMap<String, String>();
					if (finance != null){
						if (finance.getPrimBankName() != null && !finance.getPrimBankName().trim().equals("")) map.put("primary", finance.getPrimBankName() + " - " + finance.getPrimBankAccNo());
						if (finance.getSecBankName() != null && !finance.getSecBankName().trim().equals("")) map.put("secondary", finance.getSecBankName() + " - " + finance.getSecBankAccNo());
						map.put("selected", String.valueOf(report.getAccountType()));
					}
					request.setAttribute("actionForm", map);
					return result;
				}
			}
			request.setAttribute("actionForm", dropdown);
		} catch (Exception e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
		} finally{
			ResourceManager.close(conn);
		}
		return result;
	}

	private String getTerm(String month, int year) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean canRelease(int id, Integer userId, Connection conn) {
		return JDBCUtiility.getInstance().getRowCount(" FROM DEP_PERDIEM_HOLD WHERE USER_ID=? AND REP_ID=?", new Object[] { userId, id }, conn) > 0;
	}

	private Map<String, Object> getCurrencies(int id) throws Exception {
		DepPerdiemExchangeRates[] rates = DepPerdiemExchangeRatesDaoFactory.create().findWhereRepIdEquals(id);
		List<Object> reportCurrecyList = JDBCUtiility.getInstance().getSingleColumn("SELECT DISTINCT CURRENCY_TYPE FROM DEP_PERDIEM_REPORT WHERE DEP_ID=?", new Object[] { id });
		List<Integer> list = new ArrayList<Integer>();
		List<DepPerdiemExchangeRates> currencyList = new ArrayList<DepPerdiemExchangeRates>();
		Map<String, String> currencyTypes = CurrencyDaoFactory.create().getAllCurrencyTypes();
		for (Object cur : reportCurrecyList)
			if (((Long) cur).intValue() != 1) list.add(((Long) cur).intValue());
		if (rates != null && rates.length > 0){
			for (DepPerdiemExchangeRates exchangeRates : rates){
				if (list.contains(new Integer((int) exchangeRates.getCurrencyType()))){
					exchangeRates.setCurrencyName(currencyTypes.get(exchangeRates.getCurrencyType() + ""));
					list.remove(new Integer(exchangeRates.getCurrencyType()));
				}
				// comment bellow line as it is optional so he can show dropdown to edit currency type. kept as amruth asked.
				exchangeRates.setCurrencyName(currencyTypes.get(exchangeRates.getCurrencyType() + ""));
				currencyList.add(exchangeRates);
			}
		}
		for (int curr : list){
			currencyList.add(new DepPerdiemExchangeRates(id, (short) curr, 0, currencyTypes.get(curr + "")));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", currencyList);
		return map;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access perdiem reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
		DepPerdiem perdiemForm = (DepPerdiem) form;
		Connection conn = null;
		request.setAttribute("actionForm", "");// dont send unwanted data to ui again.....
		switch (SaveTypes.getValue(form.getsType())) {
		case SAVE:
			try{
				if (Short.parseShort(perdiemForm.getCurrency()) < 0){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.save.failed"));
					return result;
				}
				conn = ResourceManager.getConnection();
				conn.setAutoCommit(false);
				DepPerdiemReportDao depReportDao = DepPerdiemReportDaoFactory.create(conn);
				DepPerdiemExchangeRatesDao exchangeRatesDao = DepPerdiemExchangeRatesDaoFactory.create(conn);
				DepPerdiemExchangeRates[] rates = exchangeRatesDao.findWhereRepIdEquals(perdiemForm.getDepId());;
				DepPerdiemReport perdiemReport = depReportDao.findByPrimaryKey(perdiemForm.getId());
				int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
				String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
				if (perdiemReport != null && (perdiemReport.getModifiedBy() == null || !perdiemReport.getModifiedBy().equalsIgnoreCase(empName))){
					DepPerdiemReportHistoryDaoFactory.create(conn).insert(perdiemReport.getDepPerdiemReportHistory());
				}
				if (perdiemForm.getAmount() == null) perdiemForm.setAmount(Double.parseDouble(perdiemForm.getPerdiem()) * perdiemForm.getPayableDays() + "");
				double perdiemAmount = Double.parseDouble(perdiemForm.getAmount()), totalAmount = 0;
				perdiemReport.setPerdiem(perdiemForm.getPerdiem());
				perdiemReport.setComments(perdiemForm.getComments());
				perdiemReport.setCurrencyType(Short.parseShort(perdiemForm.getCurrency()));
				perdiemReport.setPayableDays(perdiemForm.getPayableDays());
				perdiemReport.setAmount(perdiemForm.getAmount());
				perdiemReport.setModifiedBy(empName);
				perdiemReport.setLwpDays(23.4f);
				perdiemReport.setSalaryAdvanceDeduction("0");
				perdiemReport.setTravelAdvanceDeduction("0");
				perdiemReport.setNoOfModifications(Integer.parseInt(perdiemReport.getNoOfModifications())+1+"");
				for (String entry : perdiemForm.getComponents()){
					String[] data = entry.split("~=~");
					if(data[0].equals("1")){
						if(data[1].equalsIgnoreCase("Salary in Advance")){
							perdiemReport.setSalaryAdvanceDeduction(data[2]);
							
						}
						if(data[1].equalsIgnoreCase("Travel in Advance")){
							perdiemReport.setTravelAdvanceDeduction(data[2]);
							
						}
					}
				}
					
				
				/*perdiemReport.setLwpDays(perdiemForm.getLwpDays());
				perdiemReport.setSalaryAdvanceDeduction(perdiemForm.getSalaryAdvanceDeduction());
				perdiemReport.setTravelAdvanceDeduction(perdiemForm.getTravelAdvanceDeduction());*/
				perdiemReport.setModifiedOn(new Date());
			if (perdiemReport.getType() == ADDED || perdiemReport.getType() == FIXED || perdiemReport.getType() == HOLD || perdiemReport.getType() == RELEASED || perdiemReport.getType() == REJECTED || perdiemReport.getType() == ADDED_DELETED  );
				{
					perdiemReport.setType(MODIFIED);
					
				}
						
			
				// get the amount in INR
				if (perdiemReport.getCurrencyType() > 1){
					double exchangeRate = 0.0f;
					for (DepPerdiemExchangeRates rate : rates){																																																																																																																																																																																																																																		
						if (rate.getCurrencyType() == perdiemReport.getCurrencyType()){
							exchangeRate = rate.getAmount();
							break;
						}
					}
					if (exchangeRate == 0){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.add.currency.failed", ""));
						logger.error("Currency rate is not availabe for one of seleted type. input: " + perdiemReport);
						return result;
					}
					totalAmount = perdiemAmount * exchangeRate;
				} else totalAmount = perdiemAmount;
				perdiemReport.setAmountInr(totalAmount + "");
				perdiemReport.setSalaryCycle(perdiemForm.getSalaryCycle());
				// update leave on deputition
				//id~=~from~=~to~=~no of days~=~amount per day~=~currency
				DepPerdiemLeaveDao leaveDao = DepPerdiemLeaveDaoFactory.create(conn);
				DepPerdiemLeave[] leaves = leaveDao.findWhereRepIdEquals(perdiemReport.getId());
				Map<String, DepPerdiemLeave> leavesMap = new HashMap<String, DepPerdiemLeave>();
				if (leaves != null && leaves.length > 0){
					for (DepPerdiemLeave leave : leaves){
						leavesMap.put(leave.getId().intValue() + "", leave);
					}
				}
				if (perdiemForm.getLeaves() != null){
					for (String entry : perdiemForm.getLeaves()){
						String[] data = entry.split("~=~");
						if (data.length != 6 || data[0] == null || data[1] == null || data[2] == null || data[3] == null || data[4] == null || data[5] == null || Integer.parseInt(data[5]) < 1){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.save.leave.failed"));
							logger.error("Unable to update leave on deputition details. input: " + entry + ", " + perdiemForm.getLeaves());
							return result;
						}
						int id = Integer.parseInt(data[0]);
						DepPerdiemLeave perdiemLeave = null;
						if (id > 0){
							perdiemLeave = leavesMap.get(id + "");
							if (perdiemLeave == null){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.save.leave.failed"));
								logger.error("Unable to update leave on deputition details. There is no record with given id. input: " + entry + ", " + perdiemForm.getLeaves());
								return result;
							}
						} else{
							perdiemLeave = new DepPerdiemLeave();
						}
						perdiemLeave.setValues(perdiemReport.getId(), PortalUtility.StringToDateDB(data[1]), PortalUtility.StringToDateDB(data[2]), Float.parseFloat(data[3]), data[4], Integer.parseInt(data[5]), empName, new Date(), "");
						double exchangeRate = 1;
						if (perdiemLeave.getCurrency().intValue() > 1){
							for (DepPerdiemExchangeRates rate : rates){
								if (rate.getCurrencyType() == perdiemLeave.getCurrency()){
									exchangeRate = rate.getAmount();
									break;
								}
							}
						}
						//perdiemLeave.setTotal(Double.parseDouble(perdiemLeave.getAmount()) * perdiemLeave.getDuration() + "");
						double leaveAmountInr = Double.parseDouble(perdiemLeave.getAmount()) * perdiemLeave.getDuration() * exchangeRate;
						perdiemLeave.setAmountInr(leaveAmountInr + "");
						totalAmount -= leaveAmountInr;
						if (perdiemLeave.getId() == null || perdiemLeave.getId().intValue() == 0) leaveDao.insert(perdiemLeave);
						else{
							leaveDao.update(perdiemLeave.createPk(), perdiemLeave);
							leavesMap.remove(perdiemLeave.getId().intValue() + "");
						}
					}
				}
				for (Map.Entry<String, DepPerdiemLeave> entry : leavesMap.entrySet()){
					logger.info("deleting the DepPerdiemLeave : " + entry.getValue());
					leaveDao.delete(entry.getValue().createPk());
				}
				// update components
				//id~=~reason~=~amount~=~currency~=~type
				DepPerdiemComponentsDao componentsDao = DepPerdiemComponentsDaoFactory.create(conn);
				DepPerdiemComponents[] components = componentsDao.findWhereRepIdEquals(perdiemReport.getId());
				Map<String, DepPerdiemComponents> componentMap = new HashMap<String, DepPerdiemComponents>();
				if (components != null && components.length > 0){
					for (DepPerdiemComponents component : components){
						componentMap.put(component.getId().intValue() + "", component);
					}
				}
				if (perdiemForm.getComponents() != null){
					for (String entry : perdiemForm.getComponents()){
						String[] data = entry.split("~=~");
						if(!data[0].equals("1")){
						if (data.length != 5 || data[0] == null || data[1] == null || data[2] == null || data[4] == null || data[3] == null || Integer.parseInt(data[3]) < 1){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.save.leave.failed"));
							logger.error("Unable to update compontes of perdiem reports details. input: " + entry + ", " + perdiemForm.getComponents());
							return result;
						}
						int id = Integer.parseInt(data[0]);
						DepPerdiemComponents perdiemComponent = null;
						if (id > 0){
							perdiemComponent = componentMap.get(id + "");
						} else{
							perdiemComponent = new DepPerdiemComponents();
						}
						perdiemComponent.setValues(perdiemReport.getId(), Short.parseShort(data[4]), data[1], Integer.parseInt(data[3]), data[2], empName, new Date(), "");
						double exchangeRate = 1;
						if (perdiemComponent.getCurrency().intValue() > 1){
							for (DepPerdiemExchangeRates rate : rates){
								if (rate.getCurrencyType() == perdiemComponent.getCurrency()){
									exchangeRate = rate.getAmount();
									break;
								}
							}
						}
						double leaveAmountInr = Double.parseDouble(perdiemComponent.getAmount()) * exchangeRate;
						perdiemComponent.setAmountInr(leaveAmountInr + "");
						if (perdiemComponent.getType().shortValue() == 1) totalAmount += leaveAmountInr;
						else if (perdiemComponent.getType() == 2) totalAmount -= leaveAmountInr;
						if (perdiemComponent.getId() == null || perdiemComponent.getId().intValue() < 1) componentsDao.insert(perdiemComponent);
						else{
							componentsDao.update(perdiemComponent.createPk(), perdiemComponent);
							componentMap.remove(perdiemComponent.getId().intValue() + "");
						}
						}
					}
				}
				for (Map.Entry<String, DepPerdiemComponents> entry : componentMap.entrySet()){
					logger.info("deleting the DepPerdiemComponents : " + entry.getValue());
					componentsDao.delete(entry.getValue().createPk());
				}
				perdiemReport.setTotal(totalAmount + "");
				depReportDao.update(perdiemReport.createPk(), perdiemReport);
				conn.commit();
			} catch (Exception e){
				logger.error("Unablet to save perdiem reconciliation report", e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.save.failed"));
			} finally{
				ResourceManager.close(conn);
			}
			break;
			case ADD:
				try{
					int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
					String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
					//ProjectMappingDao projectMappingDao = ProjectMappingDaoFactory.create();
					// Map<String, String> currencyTypes = CurrencyDaoFactory.create().getAllCurrencyTypes();
					// List<DepPerdiemReport> addedList = new ArrayList<DepPerdiemReport>();
					DepPerdiemReportDao depReportDao = DepPerdiemReportDaoFactory.create(conn);
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					DepPerdiemExchangeRatesDao exchangeRatesDao = DepPerdiemExchangeRatesDaoFactory.create(conn);
					DepPerdiemExchangeRates[] rates = null;
					for (String str : perdiemForm.getAddedEmpData()){
						// id~#~perdiem~#~payableDays~#~currency~#~comments~#~from~#~to~#~client
						String data[] = str.split("~#~");
						if (data.length != 8 || data[0] == null || data[1] == null || data[2] == null || data[3] == null || data[4] == null || data[5] == null || data[6] == null || data[7] == null){ throw new Exception(); }
						int userId = Integer.parseInt(data[0]);
						ProfileInfo[] userProfileInfo = ProfileInfoDaoFactory.create(conn).findByDynamicWhere("ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { userId });
						DepPerdiemReport report = new DepPerdiemReport(perdiemForm.getDepId(), userId, Float.parseFloat(data[2]), new DecimalFormat("0.00").format(Double.parseDouble(data[1])), Short.parseShort(data[3]), userProfileInfo[0].getReportingMgr(), ModelUtiility.getInstance().getEmployeeName(userProfileInfo[0].getReportingMgr()), data[7]);
						double perdiemAmount = report.getPayableDays() * Double.parseDouble((report.getPerdiem()));
						report.setAmount(perdiemAmount + "");
						report.setFrom(PortalUtility.StringToDateDB(data[5]));
						report.setTo(PortalUtility.StringToDateDB(data[6]));
						if (report.getTo().before(report.getFrom())){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.add.dates.wrong", userProfileInfo[0].getFirstName()));
							logger.error("dates are invalid input: " + str);
							return result;
						}
						if (report.getCurrencyType() == 1){
							report.setAmountInr(report.getAmount());
							//TODO loan amount needs to deduct
							report.setTotal(report.getAmount());
						} else{
							if (rates == null) rates = exchangeRatesDao.findWhereRepIdEquals(perdiemForm.getDepId());
							double exchangeRate = 0.0;
							for (DepPerdiemExchangeRates rate : rates){
								if (rate.getCurrencyType() == report.getCurrencyType()){
									exchangeRate = rate.getAmount();
									break;
								}
							}
							if (exchangeRate == 0){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.add.currency.failed", userProfileInfo[0].getFirstName()));
								logger.error("Currency rate is not availabe for one of seleted type. input: " + str);
								return result;
							}
							report.setAmountInr(perdiemAmount * exchangeRate + "");
							//TODO loan amount needs to deduct
							report.setTotal(perdiemAmount * exchangeRate + "");
						}
						report.setComments(data[4]);
						report.setModifiedBy(empName);
						report.setModifiedOn(new Date());
						report.setSalaryCycle(perdiemForm.getSalaryCycle());
						report.setType(ADDED);
						depReportDao.insert(report);
						conn.commit();
						/*
						 * perdiemReport.setEmployeeName(ModelUtiility.getInstance().getEmployeeName(userId));
						 * perdiemReport.setEmpId(ModelUtiility.getInstance().getEmployeeId(userId) + "");
						 * perdiemReport.setCurrencyName(currencyTypes.get(perdiemReport.getCurrencyType() + ""));
						 * addedList.add(perdiemReport);
						 */
					}
					// request.setAttribute("actionForm", addedList);
				} catch (Exception e){
					try{
						conn.rollback();
					} catch (SQLException e1){
						logger.error("", e);
					}
					logger.error("", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.add.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			case DELETE:
				try{
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					DepPerdiemReportDao depReportDao = DepPerdiemReportDaoFactory.create(conn);
					DepPerdiemReport perdiemReport = depReportDao.findByPrimaryKey(perdiemForm.getId());
					int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
					String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
					if (perdiemReport != null && (perdiemReport.getModifiedBy() == null || !perdiemReport.getModifiedBy().equalsIgnoreCase(empName))){
						DepPerdiemReportHistoryDaoFactory.create().insert(perdiemReport.getDepPerdiemReportHistory());
					}
					perdiemReport.setComments("DELETED : " + (perdiemForm.getComments() == null ? "" : perdiemForm.getComments()));
					perdiemReport.setType(perdiemReport.getType() == FIXED ? FIXED_DELETED : DELETED);
					perdiemReport.setModifiedBy(empName);
					perdiemReport.setModifiedOn(new Date());
					depReportDao.update(perdiemReport.createPk(), perdiemReport);
					conn.commit();
				} catch (Exception e){
					logger.error("", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.delete.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			case HOLD:
				try{
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					DepPerdiemReportDao depReportDao = DepPerdiemReportDaoFactory.create(conn);
					DepPerdiemReport perdiemReport = depReportDao.findByPrimaryKey(perdiemForm.getId());
					if (perdiemReport.getType() == FIXED_DELETED || perdiemReport.getType() == DELETED){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.hold.deleted.failed"));
					}
					int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId());
					String empName = ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + "(" + empId + ")";
					if (perdiemReport != null && (perdiemReport.getModifiedBy() == null || !perdiemReport.getModifiedBy().equalsIgnoreCase(empName))){
						DepPerdiemReportHistoryDaoFactory.create().insert(perdiemReport.getDepPerdiemReportHistory());
					}
					perdiemReport.setComments("HOLD : " + (perdiemForm.getComments() == null ? "" : perdiemForm.getComments()));
					perdiemReport.setType(perdiemReport.getType() == FIXED ? FIXED_HOLD : HOLD);
					perdiemReport.setModifiedBy(empName);
					perdiemReport.setModifiedOn(new Date());
					depReportDao.update(perdiemReport.createPk(), perdiemReport);
					DepPerdiemHoldDao holdDao = DepPerdiemHoldDaoFactory.create(conn);
					holdDao.insert(new DepPerdiemHold(perdiemReport.getId(), login.getUserId(), perdiemReport.getType(), (perdiemForm.getComments() == null ? "" : perdiemForm.getComments())));
					conn.commit();
				} catch (Exception e){
					logger.error("", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.hold.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			case RELEASE:
				try{
					conn = ResourceManager.getConnection();
					conn.setAutoCommit(false);
					DepPerdiemReportDao depReportDao = DepPerdiemReportDaoFactory.create(conn);
					DepPerdiemReport perdiemReport = depReportDao.findByPrimaryKey(perdiemForm.getId());
					if (!(perdiemReport.getType() == FIXED_HOLD) && !(perdiemReport.getType() == HOLD)){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.release.hold.failed"));
					}
					DepPerdiem perdiem = DepPerdiemDaoFactory.create(conn).findByPrimaryKey(perdiemReport.getDepId());
					int empId = ModelUtiility.getInstance().getEmployeeId(login.getUserId(), conn);
					String employeeName = ModelUtiility.getInstance().getEmployeeName(login.getUserId(), conn);
					String empName = employeeName + "(" + empId + ")";
					if (perdiemReport != null && (perdiemReport.getModifiedBy() == null || !perdiemReport.getModifiedBy().equalsIgnoreCase(empName))){
						DepPerdiemReportHistoryDaoFactory.create().insert(perdiemReport.getDepPerdiemReportHistory());
					}
					perdiemReport.setComments("RELEASE : " + (perdiemForm.getComments() == null ? "" : perdiemForm.getComments()));
					if ("Approved".equalsIgnoreCase(perdiem.getStatus()) || "Completed".equalsIgnoreCase(perdiem.getStatus())){
						if (perdiemForm.getStatus() == null) perdiemReport.setType(perdiemReport.getType() == FIXED_HOLD ? FIXED_RELEASED : RELEASED);
						else perdiemReport.setType(perdiemReport.getType() == FIXED_HOLD ? FIXED_REJECTED : REJECTED);
					} else{
						if (perdiemForm.getStatus() == null) perdiemReport.setType(perdiemReport.getType() == FIXED_HOLD ? FIXED : MODIFIED);
						else perdiemReport.setType(perdiemReport.getType() == FIXED_HOLD ? FIXED_DELETED : DELETED);
					}
					perdiemReport.setModifiedBy(empName);
					perdiemReport.setModifiedOn(new Date());
					depReportDao.update(perdiemReport.createPk(), perdiemReport);
					JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE SUBJECT LIKE ? AND CATEGORY='PERDIEM_RECON' AND ESR_MAP_ID=? ", new Object[] { "%(" + ModelUtiility.getInstance().getEmployeeId(perdiemReport.getUserId(), conn) + ")", perdiem.getEsrMapId() });
					DepPerdiemHoldDao holdDao = DepPerdiemHoldDaoFactory.create(conn);
					
					if ("Approved".equalsIgnoreCase(perdiem.getStatus()) || "Completed".equalsIgnoreCase(perdiem.getStatus())){
						holdDao.insert(new DepPerdiemHold(perdiemReport.getId(), login.getUserId(), perdiemReport.getType(), (perdiemForm.getComments() == null ? "" : perdiemForm.getComments())));
						String newStatus="";
						if(perdiemForm.getStatus()!=null && perdiemForm.getStatus().equalsIgnoreCase("1")){
							newStatus="rejected";
					}else newStatus="released";
					releaseMail(perdiem, perdiemReport, employeeName, login.getUserId(), conn,newStatus);
					} else{
						JDBCUtiility.getInstance().update("DELETE FROM DEP_PERDIEM_HOLD WHERE REP_ID=?", new Object[] { perdiemReport.getId() });
					}
					conn.commit();
				} catch (Exception e){
					logger.error("", e);
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.release.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			case CURRENCYRATES:
				updateCurrencyRates(perdiemForm, result);
				try{
					request.setAttribute("actionForm", getCurrencies(perdiemForm.getDepId()));
				} catch (Exception e){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
				}
				break;
			case BANKACCOUNTS:

				try{
					conn = ResourceManager.getConnection();
					DepPerdiemReportDao dao = DepPerdiemReportDaoFactory.create(conn);
					DepPerdiemReport report = dao.findByPrimaryKey(perdiemForm.getId());
					if (perdiemForm.getStatus() != null){
						report.setAccountType(Short.parseShort(perdiemForm.getStatus()));
						dao.update(report.createPk(), report);
					}
				} catch (Exception e){
					logger.error("unable to parse " + perdiemForm.getStatus() + " as integer while updating bank name of perdiem report " + perdiemForm.getId() + " Msg: " + e.getMessage());
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
				} finally{
					ResourceManager.close(conn);
				}
				break;
			default:
				break;
		}
		return result;
	}

	private void releaseMail(DepPerdiem perdiem, DepPerdiemReport perdiemReport, String approverName, int userId, Connection conn,String status) {
		try{
			PortalMail mail = new PortalMail();
			mail.setTemplateName(MailSettings.PERDIEM_RELEASE);
			mail.setEmployeeName(ModelUtiility.getInstance().getEmployeeName(perdiemReport.getUserId(), conn));
			mail.setMailSubject("Per-diem " +status +" of  "+ mail.getEmployeeName());

			mail.setApproverName(approverName);
			mail.setReason(status);
			mail.setPerdiemTerm(getPerdiemPeriodTermStr(perdiem));
			mail.setComments(perdiemReport.getComments());
			mail.setActionType(((perdiemReport.getType() == RELEASED || perdiemReport.getType() == FIXED_RELEASED) ? "relseased" : "rejected"));
			ProcessChain processChainDto = ProcessChainDaoFactory.create(conn).findWhereProcNameEquals("IN_DEPUTATION_PROC")[0];
			ProcessEvaluator pEval = new ProcessEvaluator();
			// get the senior RMG from leavel 1
			Integer[] srRmg = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);
			// get all the users involved in this hold process, means escalations
			List<Object> escUsers = JDBCUtiility.getInstance().getSingleColumn("SELECT USER_ID FROM DEP_PERDIEM_HOLD WHERE REP_ID=?", new Object[] { perdiemReport.getId() }, conn);
			// get the finance users.
			Integer[] finance = pEval.approvers(processChainDto.getApprovalChain(), 2, 1);
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
		//		mail.setAllReceipientMailId(new String[] {"manoj.h@dikshatech.com"});
			}
			if (ccIds != null && ccIds.size() > 0){
				mail.setAllReceipientcCMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE PI.ID IN(SELECT U.PROFILE_ID FROM USERS U WHERE STATUS=0 AND EMP_ID>0 AND U.ID IN(" + ModelUtiility.getCommaSeparetedValues(ccIds) + "))"));
			}
			new MailGenerator().invoker(mail);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void updateCurrencyRates(DepPerdiem perdiemForm, ActionResult result) {
		Connection conn = null;
		try{
			DepPerdiem perdiem = DepPerdiemDaoFactory.create().findByPrimaryKey(perdiemForm.getDepId());
			
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			DepPerdiemExchangeRatesDao exchangeRatesDao = DepPerdiemExchangeRatesDaoFactory.create(conn);
			DepPerdiemExchangeRates[] erates = DepPerdiemExchangeRatesDaoFactory.create().findWhereRepIdEquals(perdiem.getId());
			
			Map<Short, Double> ratesMap = new HashMap<Short, Double>();
			for (String data : perdiemForm.getCurrencyArray()){
				//id~=~currencyId~=~amount
				String[] dataArr = data.split("~=~");
				int id = Integer.parseInt(dataArr[0]);
				DepPerdiemExchangeRates rates = null;
				if (id > 0){
					rates = exchangeRatesDao.findByPrimaryKey(id);
					if (rates.getRepId() != perdiem.getId()){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.currency.failed"));
						logger.error("report ids not matching to update currecy types");
						return;
					}
					if (!(Short.parseShort(dataArr[1]) == rates.getCurrencyType() && Double.parseDouble(dataArr[2]) == rates.getAmount())){
						rates.setValues(Short.parseShort(dataArr[1]), Double.parseDouble(dataArr[2]));
						exchangeRatesDao.update(rates.createPk(), rates);
					}
					ratesMap.put(rates.getCurrencyType(), rates.getAmount());
				} else{
					for (DepPerdiemExchangeRates rate : erates){
						if (Short.parseShort(dataArr[1]) == rate.getCurrencyType()){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.currency.duplicate"));
							logger.error("currency rate already exits..");
							return;
						}
					}
					ratesMap.put(Short.parseShort(dataArr[1]), Double.parseDouble(dataArr[2]));
					exchangeRatesDao.insert(new DepPerdiemExchangeRates(perdiem.getId(), Short.parseShort(dataArr[1]), Double.parseDouble(dataArr[2]), null));
				}
			}
			if (!perdiem.getTerm().startsWith("T") && (perdiemForm.getStatus() == null || !perdiemForm.getStatus().equalsIgnoreCase("INSERTCURRENCYRATES"))){
				updateReportsWithCurrencyRates(ratesMap, perdiem.getId(), conn);
			}
			conn.commit();
		} catch (Exception e){
			try{
				conn.rollback();
			} catch (SQLException e1){
				logger.error("Unable to update the currecy types rollback error", e1);
			}
			logger.error("Unable to update the currecy types. input array : " + Arrays.toString(perdiemForm.getCurrencyArray()), e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.deputation.currency.failed"));
		} finally{
			ResourceManager.close(conn);
		}
	}

	private void updateReportsWithCurrencyRates(Map<Short, Double> ratesMap, int perdiemReportId, Connection conn) throws Exception {
		if (ratesMap == null || ratesMap.isEmpty()) return;
		List<Short> list = new ArrayList<Short>();
		for (Map.Entry<Short, Double> entry : ratesMap.entrySet()){
			list.add(entry.getKey());
		}
		if (list.isEmpty()) return;
		DepPerdiemReportDao depReportDao = DepPerdiemReportDaoFactory.create(conn);
		DepPerdiemComponentsDao componentsDao = DepPerdiemComponentsDaoFactory.create(conn);
		DepPerdiemLeaveDao leaveDao = DepPerdiemLeaveDaoFactory.create(conn);
		DepPerdiemReport[] perdiemReport = depReportDao.findByDynamicWhere("DEP_ID = " + perdiemReportId + " AND (CURRENCY_TYPE IN (" + ModelUtiility.getCommaSeparetedValues(list) + ") OR MODIFIED_BY IS NOT NULL)", null);
		for (DepPerdiemReport report : perdiemReport){
			double amountInr = report.getCurrencyType() != 1 ? Double.parseDouble(report.getAmount()) * ratesMap.get(new Short((short) report.getCurrencyType())) : Double.parseDouble(report.getAmount());
			report.setAmountInr(amountInr + "");
			DepPerdiemComponents[] components = componentsDao.findWhereRepIdEquals(report.getId());
			if (components != null && components.length > 0){
				for (DepPerdiemComponents component : components){
					double compValue = component.getCurrency().intValue() != 1 ? Double.parseDouble(component.getAmount()) * ratesMap.get(new Short(component.getCurrency().shortValue())) : Double.parseDouble(component.getAmountInr());
					if (component.getType() == 1) amountInr += compValue;
					else if (component.getType() == 2) amountInr -= compValue;
					if (component.getCurrency().intValue() == 1) continue;
					component.setAmountInr(compValue + "");
					componentsDao.update(component.createPk(), component);
				}
			}
			DepPerdiemLeave[] leaves = leaveDao.findWhereRepIdEquals(report.getId());
			if (leaves != null && leaves.length > 0){
				for (DepPerdiemLeave leave : leaves){
					double compValue = leave.getCurrency().intValue() != 1 ? Double.parseDouble(leave.getAmount()) * leave.getDuration() * ratesMap.get(new Short(leave.getCurrency().shortValue())) : Double.parseDouble(leave.getAmountInr());
					amountInr -= compValue;
					if (leave.getCurrency().intValue() == 1) continue;
					leave.setAmountInr(compValue + "");
					leaveDao.update(leave.createPk(), leave);
				}
			}
			report.setTotal(amountInr + "");
			depReportDao.update(report.createPk(), report);
		}
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
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ProcessEvaluator pEval = new ProcessEvaluator();
		DepPerdiem perdiemForm = (DepPerdiem) form;
		DropDown dropdown = new DropDown();
		DepPerdiemDao perdiemDao = DepPerdiemDaoFactory.create();
		DepPerdiemReqDao perdiemReqDao = DepPerdiemReqDaoFactory.create();
		try{
			int esrMapId = perdiemDao.findByPrimaryKey(perdiemForm.getDepId()).getEsrMapId();
			String msgBody = null;
			// DepPerdiemReq perdiemReq = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND ASSIGNED_TO=? ORDER BY ID DESC", new
			// Object[]{perdiemForm.getDepId(),login.getUserId()})[0];
			// perdiemReq.setSubmittedOn(new Date());
			// perdiemReqDao.update(new DepPerdiemReqPk(perdiemReq.getId()), perdiemReq);
			// boolean submit = (request.getParameter("submit") + "").equalsIgnoreCase("false");
			// Added for the escalation Fix Starts here : Praneeth Ramesh : 17 - June - 2013
			//DepPerdiemReq perdiemReq = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND ASSIGNED_TO=? AND SUBMITTED_ON IS NULL ORDER BY ID DESC", new Object[] { perdiemForm.getDepId(), (perdiemForm.getEscalatedFromId() > 0) ? perdiemForm.getEscalatedFromId() : login.getUserId() })[0];
			DepPerdiemReq perdiemReq = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[] { perdiemForm.getDepId(), (perdiemForm.getEscalatedFromId() > 0) ? perdiemForm.getEscalatedFromId() : login.getUserId() })[0];
			
			int isEscalated = perdiemReq.getIsEscalated();
			if (isEscalated != 0){
				DepPerdiemReq approverRecord = perdiemReqDao.findByPrimaryKey(isEscalated);
				approverRecord.setSubmittedOn(new Date());
				perdiemReqDao.update(new DepPerdiemReqPk(approverRecord.getId()), approverRecord);
				DepPerdiemReq[] otherEscalRecord = perdiemReqDao.findWhereIsEscalatedEquals(isEscalated);
				if (otherEscalRecord != null && otherEscalRecord.length > 0){
					for (DepPerdiemReq eachReq : otherEscalRecord){
						eachReq.setSubmittedOn(new Date());
						perdiemReqDao.update(eachReq.createPk(), eachReq);
					}
				}
			} else if (isEscalated == 0){
				DepPerdiemReq[] escalatedRecords = perdiemReqDao.findWhereIsEscalatedEquals(perdiemReq.getId());
				if (escalatedRecords != null && escalatedRecords.length > 0){
					for (DepPerdiemReq eachReq : escalatedRecords){
						eachReq.setSubmittedOn(new Date());
						perdiemReqDao.update(eachReq.createPk(), eachReq);
					}
				}
			}
			// Gurunath.rokkam added this code to allow to save data.
			// if (!submit){
			perdiemReq.setSubmittedOn(new Date());
			perdiemReqDao.update(new DepPerdiemReqPk(perdiemReq.getId()), perdiemReq);
			// }
			// Added for the escalation Fix Ends here : Praneeth Ramesh : 17 - June - 2013
			DepPerdiemReq[] perdiemReqs = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND SEQ_ID=? AND SUBMITTED_ON IS NULL ", new Object[] { perdiemForm.getDepId(), perdiemReq.getSeqId() });
			DepPerdiem perdiem = perdiemDao.findByPrimaryKey(perdiemForm.getDepId());
			ProcessChain processChainDto = processChainDao.findWhereProcNameEquals("IN_DEPUTATION_PROC")[0];
			String beginCompletedTag = "<font color=\"#006600\">";
			String endCompletedTag = "</font>";
			String beginPendingTag = "<font color=\"#FF0000\">";
			String endPendingTag = "</font>";
			String perdiemPeriodStr = getPerdiemPeriodTermStr(perdiem);
			String actionTakenBy = ProfileInfoDaoFactory.create().findByDynamicWhere(" ID = (SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName();
			// if (!submit){
			switch (perdiemReq.getSeqId()) {
				case 0:{
					// new req rmg l2f2 specialist || rmg l2f3 senior specialist has taken action
					if (perdiem.getStatus().equalsIgnoreCase("Report Generated")){
						perdiem.setStatus("Submitted");
						perdiem.setHtmlStatus(perdiem.getStatus());
						perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
						// Integer[] handlers = pEval.handlers(processChainDto.getHandler(), 1);
						// for(Integer eachHandler : handlers){
						/*
						 * if(eachHandler==login.getUserId()){
						 * continue;
						 * }
						 */
						// Escalation Fix Starts for the updating all the records at first level when one approver approves - Praneeth Ramesh - 20
						// June 2013
						DepPerdiemReq[] reqs = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND SEQ_ID=0 AND SUBMITTED_ON IS NULL", new Integer[] { perdiem.getId() });
						if (reqs != null && reqs.length > 0){
							for (DepPerdiemReq eachReq : reqs){
								eachReq.setSubmittedOn(new Date());
								perdiemReqDao.update(new DepPerdiemReqPk(eachReq.getId()), eachReq);
							}
						}
						// Escalation Fix Ends for the updating all the records at first level when one approver approves - Praneeth Ramesh - 20 June
						// 2013
						// }
						Integer[] nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);
						Integer[] lastLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
						insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 1);
						msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, lastLevelApprovers, null, perdiemPeriodStr);// this may need
																																// modification
						sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
					}else if (perdiem.getStatus().equalsIgnoreCase("Rejected")){
						//		gotoSeqId = perdiemForm.getGotoSeqId();
						Integer[] nextLevelApprovers = null;
					
							perdiem.setStatus("Submitted");
							perdiem.setHtmlStatus("Submitted");
						 perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
						nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);
						insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 1);
						msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, null, actionTakenBy, perdiemPeriodStr);
						sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
				}
				}
					break;
				case 1:
					try{
		//				if (!(perdiemReqs != null && perdiemReqs.length > 0)){
							// everybody has taken action
							Integer[] nextLevelApprovers = null;
							int gotoSeqId = 0;
			//				if (perdiem.getStatus().equalsIgnoreCase("Reviewed")){
							if (perdiem.getStatus().equalsIgnoreCase("Submitted")||perdiem.getStatus().equalsIgnoreCase("Re-Submitted")){
								
								if (perdiemForm.getIsRejected().equalsIgnoreCase("TRUE")){
									perdiem.setStatus("Rejected");
									perdiem.setHtmlStatus("Rejected");
									perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
									nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 6, 0);
									insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 0);
									msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, null, actionTakenBy, perdiemPeriodStr);
									sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
									break;
								
								
							}
								else{
								perdiem.setStatus("Reviewed and Submitted");
								perdiem.setHtmlStatus("Reviewed and Submitted");
								gotoSeqId = 2;
								nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), gotoSeqId, 1);
								insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), gotoSeqId);
								perdiem.setHtmlStatus(perdiem.getStatus());
								perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
								Integer[] lastLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
								msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, lastLevelApprovers, null, perdiemPeriodStr);
								sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
							} 
							}
							
					/*	} else{
							// there are people who must take action
						}*/
					} catch (Exception ex){
						logger.error("RECONCILATION UPDATE : failed to update data (case:1)", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
						result.setForwardName("success");
						return result;
					}
					break;
				/*case 2:
					try{
						// check if everyone has taken action in level 2
			//			if (!(perdiemReqs != null && perdiemReqs.length > 0)){
						if (perdiem.getStatus().equalsIgnoreCase("Submitted")||perdiem.getStatus().equalsIgnoreCase("Re-Submitted")){
							Integer[] nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);// gotoSeqId=1
							insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 1);
							perdiem.setStatus("Reviewed");
							perdiem.setHtmlStatus("Reviewed");
							perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
							Integer[] presentLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 2, 1);
							for (Integer eachAppId : presentLevelApprovers){
								DepPerdiemReq fetchedPerdiemReq = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND SEQ_ID=2 AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[] { perdiemForm.getDepId(), eachAppId })[0];
								fetchedPerdiemReq.setSubmittedOn(new Date());
								perdiemReqDao.update(new DepPerdiemReqPk(fetchedPerdiemReq.getId()), fetchedPerdiemReq);
							}
							Integer[] lastLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 4, 1);
							msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, lastLevelApprovers, null, perdiemPeriodStr);
							sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
						}
						
						
						
					} else{
							// there are people who must take action..
							ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
							StringBuilder statusWithName = new StringBuilder();
							Integer[] secondLevelApproverIds = pEval.approvers(processChainDto.getApprovalChain(), 2, 1);// gotoSeqId=1
							for (Integer eachAppId : secondLevelApproverIds){
								try{
									DepPerdiemReq fetchedPerdiemReq = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND SEQ_ID=2 AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[] { perdiemForm.getDepId(), eachAppId })[0];
									ProfileInfo managerProfileInfo = profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { fetchedPerdiemReq.getAssignedTo() })[0];
									if (fetchedPerdiemReq.getSubmittedOn() == null){
										statusWithName.append(beginPendingTag).append(managerProfileInfo.getFirstName()).append(endPendingTag).append(" , ");
									} else{
										statusWithName.append(beginCompletedTag).append(managerProfileInfo.getFirstName()).append(endCompletedTag).append(" , ");
									}
								} catch (Exception e){}
							}
							int lastIndexOfComma = statusWithName.toString().lastIndexOf(',');
							statusWithName = statusWithName.replace(lastIndexOfComma, statusWithName.length(), " ");
							perdiem.setHtmlStatus("Awaiting Approval[ <b>" + statusWithName.toString().trim() + " </b>]");
							perdiem.setStatus("Awaiting Approvals");
							perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
						}
						
					} catch (Exception ex){
						logger.error("RECONCILATION UPDATE : failed to update data (case:2)", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
						result.setForwardName("success");
						return result;
					}
					break;*/
				case 2:
					/*
					 * People who belong to this sequence can REJECT
					 */
					try{
						if (perdiemForm.getCommentsByFinanceTeam() != null && perdiemForm.getCommentsByFinanceTeam().length() > 0){
							// last stage of perdiem request !
							perdiem.setStatus("Completed");
							perdiem.setHtmlStatus("Completed");
							perdiem.setCompletedOn(new Date());
							perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
							updatePerdiemInAdvance(perdiem);
							Set<Integer> userIdsInProcessChain = new HashSet<Integer>();
							for (int appLevel = 1; appLevel <= 3; appLevel++){
								Integer[] approverIds = pEval.approvers(processChainDto.getApprovalChain(), appLevel, 1);
								for (Integer eachApproverId : approverIds){
									userIdsInProcessChain.add(eachApproverId);
								}
							}
							Integer[] handlerIds = pEval.handlers(processChainDto.getHandler(), 1);
							for (Integer eachHandlerId : handlerIds){
								userIdsInProcessChain.add(eachHandlerId);
							}
							Integer[] presentLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 2, 1);
							for (Integer eachAppId : presentLevelApprovers){
								DepPerdiemReq fetchedPerdiemReq = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND SEQ_ID=2 AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[] { perdiemForm.getDepId(), eachAppId })[0];
								fetchedPerdiemReq.setSubmittedOn(new Date());
								perdiemReqDao.update(new DepPerdiemReqPk(fetchedPerdiemReq.getId()), fetchedPerdiemReq);
							}
							
							Integer[] toIds = new Integer[userIdsInProcessChain.size()];
							userIdsInProcessChain.toArray(toIds);
							msgBody = sendMail(perdiem.getStatus(), toIds, null, perdiemForm.getCommentsByFinanceTeam(), perdiemPeriodStr);
							sendInboxNotification(toIds, esrMapId, msgBody, perdiem.getStatus()," Per-Diem report completed for the period "+ perdiemPeriodStr );
							sendDisbursalMailToEmployees(perdiem.getId());
							sendMail(perdiemForm);
							break;
						}
						
						Integer[] nextLevelApprovers = null, toIds = null;
						int gotoSeqId = 0;
						if(perdiem.getStatus().equalsIgnoreCase("Reviewed and Submitted")||perdiem.getStatus().equalsIgnoreCase("Reviewed and Re-Submitted")){
						 if (perdiemForm.getIsRejected().equalsIgnoreCase("TRUE")){
							perdiem.setStatus("Rejected");
							perdiem.setHtmlStatus("Rejected");
							perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
							nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 6, 0);
							insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 0);
							msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, null, actionTakenBy, perdiemPeriodStr);
							sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
							break;
						} else{
							Integer[] presentLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 2, 1);
							for (Integer eachAppId : presentLevelApprovers){
								DepPerdiemReq fetchedPerdiemReq = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND SEQ_ID=2 AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[] { perdiemForm.getDepId(), eachAppId })[0];
								fetchedPerdiemReq.setSubmittedOn(new Date());
								perdiemReqDao.update(new DepPerdiemReqPk(fetchedPerdiemReq.getId()), fetchedPerdiemReq);
							}
							gotoSeqId = 3;
							perdiem.setStatus("Reviewed and Submitted");
							toIds = null;
						}
						
						nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), gotoSeqId, 1);
						insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), gotoSeqId);
						perdiem.setHtmlStatus(perdiem.getStatus());
						perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
						msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, toIds, actionTakenBy, perdiemPeriodStr);// common to Accepted/Rejected
						sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
						}
					} catch (Exception ex){
						logger.error("RECONCILATION UPDATE : failed to update data(case:3)", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
						result.setForwardName("success");
						return result;
					}
					break;
				case 3:
					try{
						ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
						Integer[] nextLevelApprovers = null;
						if(perdiem.getStatus().equalsIgnoreCase("Reviewed and Submitted")||perdiem.getStatus().equalsIgnoreCase("Reviewed and Re-Submitted")){
							 if (perdiemForm.getIsRejected().equalsIgnoreCase("TRUE")){
									perdiem.setStatus("Rejected");
									perdiem.setHtmlStatus("Rejected");
									perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
									nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 6, 0);
									insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 0);
									msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, null, actionTakenBy, perdiemPeriodStr);
									sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
									break;
								}else{
							String currentStatus = perdiemDao.findByPrimaryKey(perdiem.getId()).getStatus();
							if (currentStatus.equalsIgnoreCase("Pending Approval") || currentStatus.equalsIgnoreCase("Reviewed and Submitted") ){
								// both have approved..send this info to the person involved in the Finance team
								
								perdiem.setStatus("Accepted");
						//		String actionTaken = "Approved By:[ <b>" + beginCompletedTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName() + endCompletedTag  ;
						//		perdiem.setHtmlStatus(actionTaken + "</b> ]");
								perdiem.setHtmlStatus("Accepted");
								nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 4, 1);
								insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 4);
								msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, null, null, perdiemPeriodStr);
								sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
							} else{
								// only one person has approved
								perdiem.setStatus("Pending Approval");
								
								String actionTaken = "Approved By:[ <b>" + beginCompletedTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName() + endCompletedTag + " ,";
								List<Integer> pendingApproverIds = new ArrayList(Arrays.asList(pEval.approvers(processChainDto.getApprovalChain(), 3, 1)));
								pendingApproverIds.remove(login.getUserId());
								for (Iterator<Integer> idIter = pendingApproverIds.iterator(); idIter.hasNext();){
									actionTaken += beginPendingTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { idIter.next() })[0].getFirstName() + endPendingTag;
								}
								perdiem.setHtmlStatus(actionTaken + "</b> ]");
							}
							perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
						}
						}
					}catch (Exception ex){
						logger.error("RECONCILATION UPDATE : failed to update data(case:3)", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
						result.setForwardName("success");
						return result;
					}
					sendMail(perdiemForm);
					break;
					
					//CEO Approval
				case 4:
					try{
						ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
						Integer[] nextLevelApprovers = null;
						if(perdiem.getStatus().equalsIgnoreCase("Reviewed and Submitted")||perdiem.getStatus().equalsIgnoreCase("Reviewed and Re-Submitted")){
							 if (perdiemForm.getIsRejected().equalsIgnoreCase("TRUE")){
									perdiem.setStatus("Rejected");
									perdiem.setHtmlStatus("Rejected");
									perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
									nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 6, 0);
									insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 0);
									msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, null, actionTakenBy, perdiemPeriodStr);
									sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
									break;
								}
						}else{
							String currentStatus = perdiemDao.findByPrimaryKey(perdiem.getId()).getStatus();
							if (currentStatus.equalsIgnoreCase("Accepted")){
								// both have approved..send this info to the person involved in the Finance team
								perdiem.setStatus("Approved");
						//		String actionTaken = "Approved By:[ <b>" + beginCompletedTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName() + endCompletedTag  ;
						//		perdiem.setHtmlStatus(actionTaken + "</b> ]");
								perdiem.setHtmlStatus("Approved");
								nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 5, 1);
								insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 5);
								msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, null, null, perdiemPeriodStr);
								sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
							}/* else{
								// only one person has approved
								perdiem.setStatus("Pending Approval");
								
								String actionTaken = "Approved By:[ <b>" + beginCompletedTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName() + endCompletedTag + " ,";
								List<Integer> pendingApproverIds = new ArrayList(Arrays.asList(pEval.approvers(processChainDto.getApprovalChain(), 3, 1)));
								pendingApproverIds.remove(login.getUserId());
								for (Iterator<Integer> idIter = pendingApproverIds.iterator(); idIter.hasNext();){
									actionTaken += beginPendingTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { idIter.next() })[0].getFirstName() + endPendingTag;
								}
								perdiem.setHtmlStatus(actionTaken + "</b> ]");
							}*/
							perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
						}
					 }catch (Exception ex){
						logger.error("RECONCILATION UPDATE : failed to update data(case:3)", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
						result.setForwardName("success");
						return result;
					}
					break;
					
				case 5:
					try{
						ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
						Integer[] nextLevelApprovers = null;
						if(perdiem.getStatus().equalsIgnoreCase("Reviewed and Submitted")||perdiem.getStatus().equalsIgnoreCase("Reviewed and Re-Submitted")){
							 if (perdiemForm.getIsRejected().equalsIgnoreCase("TRUE")){
									perdiem.setStatus("Rejected");
									perdiem.setHtmlStatus("Rejected");
									perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
									nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 6, 0);
									insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 0);
									msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, null, actionTakenBy, perdiemPeriodStr);
									sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
									break;
								}
						}else{
							String currentStatus = perdiemDao.findByPrimaryKey(perdiem.getId()).getStatus();
							if (currentStatus.equalsIgnoreCase("Approved")){
								// both have approved..send this info to the person involved in the Finance team
								perdiem.setStatus("Completed");
						//		String actionTaken = "Approved By:[ <b>" + beginCompletedTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName() + endCompletedTag  ;
						//		perdiem.setHtmlStatus(actionTaken + "</b> ]");
								perdiem.setHtmlStatus("Completed");
								//nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 5, 1);
								//insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 5);
								//msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, null, null, perdiemPeriodStr);
								//sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
							}/* else{
								// only one person has approved
								perdiem.setStatus("Pending Approval");
								
								String actionTaken = "Approved By:[ <b>" + beginCompletedTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName() + endCompletedTag + " ,";
								List<Integer> pendingApproverIds = new ArrayList(Arrays.asList(pEval.approvers(processChainDto.getApprovalChain(), 3, 1)));
								pendingApproverIds.remove(login.getUserId());
								for (Iterator<Integer> idIter = pendingApproverIds.iterator(); idIter.hasNext();){
									actionTaken += beginPendingTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { idIter.next() })[0].getFirstName() + endPendingTag;
								}
								perdiem.setHtmlStatus(actionTaken + "</b> ]");
							}*/
							perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
						}
					} catch (Exception ex){
						logger.error("RECONCILATION UPDATE : failed to update data(case:3)", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
						result.setForwardName("success");
						return result;
					}
					break;
			}
			// }
		} catch (Exception ex){
			logger.error("RECONCILATION UPDATE : failed to update data (main)", ex);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
			result.setForwardName("success");
			return result;
		}
		dropdown.setDropDown(null);
		request.setAttribute("actionForm", "");
		result.setForwardName("success");
		return null;
	}
	
	private String sendMail(DepPerdiem perdiemForm) {
		PortalMail pmail = new PortalMail();
		Connection conn = null;
		MailGenerator generator = new MailGenerator();
		String body = null;
		int depId = perdiemForm.getDepId();
		
		try {
			DepPerdiemReport[] reconciliationsReports = DepPerdiemReportDaoFactory.create().findWhereDepIdEquals(depId);
			int sum_of_no_of_modification = 0;
			float count = 0;
			for(DepPerdiemReport depPerDiemReport:reconciliationsReports){
				sum_of_no_of_modification = sum_of_no_of_modification + Integer.parseInt(depPerDiemReport.getNoOfModifications());
				count = count + 1;
			}
			
			float percentage = (sum_of_no_of_modification * 100)/ count;
			BigDecimal bd = new BigDecimal(Float.toString(percentage));
	        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
	  //      System.out.println(bd.toString());
			String modifications = bd.toString();
	//		System.out.println(modifications);
			List<String> allReceipientcCMailId = new ArrayList<String>();
			String[] recipientMailId = new String[1];
			String[] recipientCCMailId = new String[2];
			recipientMailId[0] = "vamsi.krishna@dikshatech.com";
	//		recipientMailId[0] = "shivi.suhane@dikshatech.com";
//			recipientCCMailId[0] = "rmg@dikshatech.com";
	//		recipientCCMailId[1] = "ananda@dikshatech.com";
			pmail.setAllReceipientMailId(recipientMailId);
			pmail.setAllReceipientcCMailId(recipientCCMailId);
			pmail.setMailSubject("No of Modifications done on Per-Diem Registry");
			pmail.setTemplateName(MailSettings.MODIFICATION_PERDIEM_REGISTRY);
			pmail.setNoOfEmployees(Float.toString(count));
			pmail.setNoOfModifications(Integer.toString(sum_of_no_of_modification));
			pmail.setPercentage(modifications);
			generator.invoker(pmail);
			
		} catch (Exception e) {
			logger.error("Unable to send mail ");
		}
		
		
		
		return body;
	}
	
	
	
	
	
	/*@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access perdiem reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ProcessEvaluator pEval = new ProcessEvaluator();
		DepPerdiem perdiemForm = (DepPerdiem) form;
		DropDown dropdown = new DropDown();
		DepPerdiemDao perdiemDao = DepPerdiemDaoFactory.create();
		DepPerdiemReqDao perdiemReqDao = DepPerdiemReqDaoFactory.create();
		try{
			int esrMapId = perdiemDao.findByPrimaryKey(perdiemForm.getDepId()).getEsrMapId();
			String msgBody = null;
			// DepPerdiemReq perdiemReq = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND ASSIGNED_TO=? ORDER BY ID DESC", new
			// Object[]{perdiemForm.getDepId(),login.getUserId()})[0];
			// perdiemReq.setSubmittedOn(new Date());
			// perdiemReqDao.update(new DepPerdiemReqPk(perdiemReq.getId()), perdiemReq);
			// boolean submit = (request.getParameter("submit") + "").equalsIgnoreCase("false");
			// Added for the escalation Fix Starts here : Praneeth Ramesh : 17 - June - 2013
			DepPerdiemReq perdiemReq = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND ASSIGNED_TO=? AND SUBMITTED_ON IS NULL ORDER BY ID DESC", new Object[] { perdiemForm.getDepId(), (perdiemForm.getEscalatedFromId() > 0) ? perdiemForm.getEscalatedFromId() : login.getUserId() })[0];
			int isEscalated = perdiemReq.getIsEscalated();
			if (isEscalated != 0){
				DepPerdiemReq approverRecord = perdiemReqDao.findByPrimaryKey(isEscalated);
				approverRecord.setSubmittedOn(new Date());
				perdiemReqDao.update(new DepPerdiemReqPk(approverRecord.getId()), approverRecord);
				DepPerdiemReq[] otherEscalRecord = perdiemReqDao.findWhereIsEscalatedEquals(isEscalated);
				if (otherEscalRecord != null && otherEscalRecord.length > 0){
					for (DepPerdiemReq eachReq : otherEscalRecord){
						eachReq.setSubmittedOn(new Date());
						perdiemReqDao.update(eachReq.createPk(), eachReq);
					}
				}
			} else if (isEscalated == 0){
				DepPerdiemReq[] escalatedRecords = perdiemReqDao.findWhereIsEscalatedEquals(perdiemReq.getId());
				if (escalatedRecords != null && escalatedRecords.length > 0){
					for (DepPerdiemReq eachReq : escalatedRecords){
						eachReq.setSubmittedOn(new Date());
						perdiemReqDao.update(eachReq.createPk(), eachReq);
					}
				}
			}
			// Gurunath.rokkam added this code to allow to save data.
			// if (!submit){
			perdiemReq.setSubmittedOn(new Date());
			perdiemReqDao.update(new DepPerdiemReqPk(perdiemReq.getId()), perdiemReq);
			// }
			// Added for the escalation Fix Ends here : Praneeth Ramesh : 17 - June - 2013
			DepPerdiemReq[] perdiemReqs = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND SEQ_ID=? AND SUBMITTED_ON IS NULL ", new Object[] { perdiemForm.getDepId(), perdiemReq.getSeqId() });
			DepPerdiem perdiem = perdiemDao.findByPrimaryKey(perdiemForm.getDepId());
			ProcessChain processChainDto = processChainDao.findWhereProcNameEquals("IN_DEPUTATION_PROC")[0];
			String beginCompletedTag = "<font color=\"#006600\">";
			String endCompletedTag = "</font>";
			String beginPendingTag = "<font color=\"#FF0000\">";
			String endPendingTag = "</font>";
			String perdiemPeriodStr = getPerdiemPeriodTermStr(perdiem);
			String actionTakenBy = ProfileInfoDaoFactory.create().findByDynamicWhere(" ID = (SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName();
			// if (!submit){
			switch (perdiemReq.getSeqId()) {
				case 0:{
					// new req rmg l2f2 specialist || rmg l2f3 senior specialist has taken action
					if (perdiem.getStatus().equalsIgnoreCase("Report Generated")){
						perdiem.setStatus("Submitted");
						perdiem.setHtmlStatus(perdiem.getStatus());
						perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
						// Integer[] handlers = pEval.handlers(processChainDto.getHandler(), 1);
						// for(Integer eachHandler : handlers){
						
						 * if(eachHandler==login.getUserId()){
						 * continue;
						 * }
						 
						// Escalation Fix Starts for the updating all the records at first level when one approver approves - Praneeth Ramesh - 20
						// June 2013
						DepPerdiemReq[] reqs = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND SEQ_ID=0 AND SUBMITTED_ON IS NULL", new Integer[] { perdiem.getId() });
						if (reqs != null && reqs.length > 0){
							for (DepPerdiemReq eachReq : reqs){
								eachReq.setSubmittedOn(new Date());
								perdiemReqDao.update(new DepPerdiemReqPk(eachReq.getId()), eachReq);
							}
						}
						// Escalation Fix Ends for the updating all the records at first level when one approver approves - Praneeth Ramesh - 20 June
						// 2013
						// }
						Integer[] nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 2, 1);
						Integer[] lastLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 4, 1);
						insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 2);
						msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, lastLevelApprovers, null, perdiemPeriodStr);// this may need
																																// modification
						sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
					}
				}
					break;
				case 1:
					try{
		//				if (!(perdiemReqs != null && perdiemReqs.length > 0)){
							// everybody has taken action
							Integer[] nextLevelApprovers = null;
							int gotoSeqId = 0;
							if (perdiem.getStatus().equalsIgnoreCase("Reviewed")){
								perdiem.setStatus("Reviewed and Submitted");
								gotoSeqId = 3;
							} else if (perdiem.getStatus().equalsIgnoreCase("Rejected")){
								gotoSeqId = perdiemForm.getGotoSeqId();
								if (gotoSeqId == 2){
									perdiem.setStatus("Re-Submitted");
								} else{
									perdiem.setStatus("Reviewed and Re-Submitted");
								}
							}
							nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), gotoSeqId, 1);
							insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), gotoSeqId);
							perdiem.setHtmlStatus(perdiem.getStatus());
							perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
							Integer[] lastLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 4, 1);
							msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, lastLevelApprovers, null, perdiemPeriodStr);
							sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
						} else{
							// there are people who must take action
						}
					} catch (Exception ex){
						logger.error("RECONCILATION UPDATE : failed to update data (case:1)", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
						result.setForwardName("success");
						return result;
					}
					break;
				case 2:
					try{
						// check if everyone has taken action in level 2
			//			if (!(perdiemReqs != null && perdiemReqs.length > 0)){
						if (perdiem.getStatus().equalsIgnoreCase("Submitted")||perdiem.getStatus().equalsIgnoreCase("Re-Submitted")){
							Integer[] nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);// gotoSeqId=1
							insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 1);
							perdiem.setStatus("Reviewed");
							perdiem.setHtmlStatus("Reviewed");
							perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
							Integer[] presentLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 2, 1);
							for (Integer eachAppId : presentLevelApprovers){
								DepPerdiemReq fetchedPerdiemReq = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND SEQ_ID=2 AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[] { perdiemForm.getDepId(), eachAppId })[0];
								fetchedPerdiemReq.setSubmittedOn(new Date());
								perdiemReqDao.update(new DepPerdiemReqPk(fetchedPerdiemReq.getId()), fetchedPerdiemReq);
							}
							Integer[] lastLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 4, 1);
							msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, lastLevelApprovers, null, perdiemPeriodStr);
							sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
						}
						
						
						
					} else{
							// there are people who must take action..
							ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
							StringBuilder statusWithName = new StringBuilder();
							Integer[] secondLevelApproverIds = pEval.approvers(processChainDto.getApprovalChain(), 2, 1);// gotoSeqId=1
							for (Integer eachAppId : secondLevelApproverIds){
								try{
									DepPerdiemReq fetchedPerdiemReq = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND SEQ_ID=2 AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[] { perdiemForm.getDepId(), eachAppId })[0];
									ProfileInfo managerProfileInfo = profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { fetchedPerdiemReq.getAssignedTo() })[0];
									if (fetchedPerdiemReq.getSubmittedOn() == null){
										statusWithName.append(beginPendingTag).append(managerProfileInfo.getFirstName()).append(endPendingTag).append(" , ");
									} else{
										statusWithName.append(beginCompletedTag).append(managerProfileInfo.getFirstName()).append(endCompletedTag).append(" , ");
									}
								} catch (Exception e){}
							}
							int lastIndexOfComma = statusWithName.toString().lastIndexOf(',');
							statusWithName = statusWithName.replace(lastIndexOfComma, statusWithName.length(), " ");
							perdiem.setHtmlStatus("Awaiting Approval[ <b>" + statusWithName.toString().trim() + " </b>]");
							perdiem.setStatus("Awaiting Approvals");
							perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
						}
						
					} catch (Exception ex){
						logger.error("RECONCILATION UPDATE : failed to update data (case:2)", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
						result.setForwardName("success");
						return result;
					}
					break;
				case 3:
					
					 * People who belong to this sequence can REJECT
					 
					try{
						if (perdiemForm.getCommentsByFinanceTeam() != null && perdiemForm.getCommentsByFinanceTeam().length() > 0){
							// last stage of perdiem request !
							perdiem.setStatus("Completed");
							perdiem.setHtmlStatus("Completed");
							perdiem.setCompletedOn(new Date());
							perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
							updatePerdiemInAdvance(perdiem);
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
							Integer[] presentLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
							for (Integer eachAppId : presentLevelApprovers){
								DepPerdiemReq fetchedPerdiemReq = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND SEQ_ID=3 AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[] { perdiemForm.getDepId(), eachAppId })[0];
								fetchedPerdiemReq.setSubmittedOn(new Date());
								perdiemReqDao.update(new DepPerdiemReqPk(fetchedPerdiemReq.getId()), fetchedPerdiemReq);
							}
							
							Integer[] toIds = new Integer[userIdsInProcessChain.size()];
							userIdsInProcessChain.toArray(toIds);
							msgBody = sendMail(perdiem.getStatus(), toIds, null, perdiemForm.getCommentsByFinanceTeam(), perdiemPeriodStr);
							sendInboxNotification(toIds, esrMapId, msgBody, perdiem.getStatus()," Per-Diem report completed for the period "+ perdiemPeriodStr );
							sendDisbursalMailToEmployees(perdiem.getId());
							break;
						}
						
						Integer[] nextLevelApprovers = null, toIds = null;
						int gotoSeqId = 0;
						if(perdiem.getStatus().equalsIgnoreCase("Reviewed and Submitted")||perdiem.getStatus().equalsIgnoreCase("Reviewed and Re-Submitted")){
						if (perdiemForm.getIsRejected().equalsIgnoreCase("TRUE")){
							gotoSeqId = 1;
							perdiem.setStatus("Rejected");
							toIds = pEval.approvers(processChainDto.getApprovalChain(), 4, 1);
						} else{
							Integer[] presentLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
							for (Integer eachAppId : presentLevelApprovers){
								DepPerdiemReq fetchedPerdiemReq = perdiemReqDao.findByDynamicWhere(" DEP_ID=? AND SEQ_ID=3 AND ASSIGNED_TO=? ORDER BY ID DESC", new Object[] { perdiemForm.getDepId(), eachAppId })[0];
								fetchedPerdiemReq.setSubmittedOn(new Date());
								perdiemReqDao.update(new DepPerdiemReqPk(fetchedPerdiemReq.getId()), fetchedPerdiemReq);
							}
							gotoSeqId = 4;
							perdiem.setStatus("Accepted");
							toIds = null;
						}
						
						nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), gotoSeqId, 1);
						insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), gotoSeqId);
						perdiem.setHtmlStatus(perdiem.getStatus());
						perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
						msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, toIds, actionTakenBy, perdiemPeriodStr);// common to Accepted/Rejected
						sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
						}
					} catch (Exception ex){
						logger.error("RECONCILATION UPDATE : failed to update data(case:3)", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
						result.setForwardName("success");
						return result;
					}
					break;
				case 4:
					try{
						Integer[] nextLevelApprovers = null;
						if (perdiemForm.getIsRejected().equalsIgnoreCase("TRUE")){
							perdiem.setStatus("Rejected");
							perdiem.setHtmlStatus("Rejected");
							perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
							nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 1, 1);
							insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 1);
							msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, null, actionTakenBy, perdiemPeriodStr);
							sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
						} else{
							String currentStatus = perdiemDao.findByPrimaryKey(perdiem.getId()).getStatus();
							if (currentStatus.equalsIgnoreCase("Pending Approval")){
								// both have approved..send this info to the person involved in the Finance team
								perdiem.setStatus("Approved");
								perdiem.setHtmlStatus("Approved");
								nextLevelApprovers = pEval.approvers(processChainDto.getApprovalChain(), 3, 1);
								insertIntoReq(nextLevelApprovers, perdiemForm.getDepId(), 3);
								msgBody = sendMail(perdiem.getStatus(), nextLevelApprovers, null, null, perdiemPeriodStr);
								sendInboxNotification(nextLevelApprovers, esrMapId, msgBody, perdiem.getStatus(), perdiem.getStatus());
							} else{
								// only one person has approved
								perdiem.setStatus("Pending Approval");
								ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
								String actionTaken = "Approved By:[ <b>" + beginCompletedTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { login.getUserId() })[0].getFirstName() + endCompletedTag + " ,";
								List<Integer> pendingApproverIds = new ArrayList(Arrays.asList(pEval.approvers(processChainDto.getApprovalChain(), 4, 1)));
								pendingApproverIds.remove(login.getUserId());
								for (Iterator<Integer> idIter = pendingApproverIds.iterator(); idIter.hasNext();){
									actionTaken += beginPendingTag + profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { idIter.next() })[0].getFirstName() + endPendingTag;
								}
								perdiem.setHtmlStatus(actionTaken + "</b> ]");
							}
							perdiemDao.update(new DepPerdiemPk(perdiem.getId()), perdiem);
						}
					} catch (Exception ex){
						logger.error("RECONCILATION UPDATE : failed to update data(case:4)", ex);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
						result.setForwardName("success");
						return result;
					}
					break;
			}
			// }
		} catch (Exception ex){
			logger.error("RECONCILATION UPDATE : failed to update data (main)", ex);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.update.failed"));
			result.setForwardName("success");
			return result;
		}
		dropdown.setDropDown(null);
		request.setAttribute("actionForm", "");
		result.setForwardName("success");
		return null;
	}
*/

	
	public void updatePerdiemInAdvance(DepPerdiem perdiem){
		DepPerdiemReport[] report=null;
		DepPerdiemComponents[] components=null;
		PerdiemInAdvance advance=null;
		DepPerdiemComponentsDao depPerdiemComponentsDao=DepPerdiemComponentsDaoFactory.create();
		DepPerdiemDao depPerdiemDao=DepPerdiemDaoFactory.create();
		DepPerdiemReportDao depPerdiemReportDao=DepPerdiemReportDaoFactory.create();
		PerdiemInAdvanceDao perdiemInAdvanceDao=PerdiemInAdvanceDaoFactory.create();
		float amt=0;
		float balance=0;
		float paid=0;
		String term="";
		try {
			
			if(perdiem.getTerm().equalsIgnoreCase("First")){
				term="1";
			}else term="2";
			
			report=depPerdiemReportDao.findWhereDepIdEquals(perdiem.getId());
			if(report!=null){
				for(DepPerdiemReport eachReport:report){
					
						components=depPerdiemComponentsDao.findWhereRepIdEquals(eachReport.getId());
						if(components!=null){
							for(DepPerdiemComponents comp:components){
					// checking Components table and getting the Perdiem In Advance Deduction			
								if(comp.getReason().equalsIgnoreCase("Per-Diem in Advance")){
									amt=Float.valueOf(comp.getAmountInr());
									if(amt>0){
										advance=perdiemInAdvanceDao.findByPrimaryKey(eachReport.getUserId());
										if(advance!=null){
				// checking the term condition (first or second) 						
									if(term.equalsIgnoreCase(advance.getTerms())){
										PerdiemInAdvancePk pk=new PerdiemInAdvancePk();
										pk.setId(advance.getId());
										balance=Float.valueOf(advance.getAdvanceBal());
										paid=Float.valueOf(advance.getAdvancePaid());
										balance=balance-amt;
										paid=paid+amt;
				//updating advance table balance and paid amount						
										advance.setAdvanceBal(balance+"");
										advance.setAdvancePaid(paid+"");
										perdiemInAdvanceDao.update(pk,advance);
										
									}
								}	
							}
									
								}
							  }
							}
					
					
				}
				
			}
		

		
		} catch (DepPerdiemReportDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (DepPerdiemComponentsDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (PerdiemInAdvanceDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public String getPerdiemPeriodTermStr(DepPerdiem perdiem) {
		String perdiemTerm = null;
		
		
		if(perdiem.getTerm().equalsIgnoreCase("Monthly")){
			
		perdiemTerm = "16th " + PortalUtility.getFullMonthName(previmonth(perdiem.getMonth())) + " to " + "15th" + " " + perdiem.getMonth();
		}
		else if (perdiem.getTerm().equalsIgnoreCase("First")){
			perdiemTerm = "1st " + perdiem.getMonth() + " - 15th " + perdiem.getMonth();
		} else{
			perdiemTerm = "16th " + perdiem.getMonth() + " - " + getMonthEnd(perdiem.getMonth(), perdiem.getYear()) + " " + perdiem.getMonth();
		}
		return perdiemTerm;
	}
	
	public static int previmonth(String month) {
		
		int y = Integer.parseInt(PortalUtility.getMonthNumber(month.substring(0, 3))) - 1;
				if (y==0){
							return y=12;	
						}
				else{
							return y;
		}
				
			}

	private String getMonthEnd(String month, int year) {
		if (month == null) return "30th";
		if (month.equalsIgnoreCase("January") || month.equalsIgnoreCase("March") || month.equalsIgnoreCase("May") || month.equalsIgnoreCase("July") || month.equalsIgnoreCase("August") || month.equalsIgnoreCase("October") || month.equalsIgnoreCase("December")) return "31st";
		if (month.equalsIgnoreCase("February")){
			if (year % 4 == 0) return "29th";
			return "28th";
		}
		if (month.equalsIgnoreCase("April") || month.equalsIgnoreCase("June") || month.equalsIgnoreCase("September") || month.equalsIgnoreCase("November")) return "30th";
		return "30th";
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		return null;
	}

	private void insertIntoReq(Integer[] approverIds, int depId, int seqId) throws DepPerdiemReqDaoException, DepPerdiemHistoryDaoException, ProfileInfoDaoException {
		DepPerdiemReq depPerdiemReq = null;
		DepPerdiemReqDao perdiemReqDao = DepPerdiemReqDaoFactory.create();
		for (Integer eachApproverId : approverIds){
			depPerdiemReq = new DepPerdiemReq();
			depPerdiemReq.setDepId(depId);
			depPerdiemReq.setSeqId(seqId);
			depPerdiemReq.setAssignedTo(eachApproverId);
			depPerdiemReq.setReceivedOn(new Date());
			depPerdiemReq.setSubmittedOn(null);// required to check if all have taken action in a particular sequence
			depPerdiemReq.setIsEscalated(0);
			perdiemReqDao.insert(depPerdiemReq);
		}
	}
	
	
	
	

	private static Date getLastDateOfMonth(int year, int month) {
		if (month > 0){
			month--;
		}
		Calendar calendar = new GregorianCalendar(year, month, Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
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
			inbox.setCategory("PERDIEM_REPORT");
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setMessageBody(msgBody);
			inboxDao.insert(inbox);
		}
	}

	private String sendMail(String status, Integer[] toIds, Integer[] ccIds, String args, String perdiemTerm) throws FileNotFoundException, ProfileInfoDaoException, AddressException, UnsupportedEncodingException, MessagingException {
		String templateName = "";
		String subject = status;
		String msgBody = "";
		PortalMail portalMail = new PortalMail();
		if (status.equalsIgnoreCase("Reviewed")){
			subject = "Per diem Report reviewed by Business for " + perdiemTerm;
			templateName = MailSettings.PERDIEM_REVIEWED;
		} else if (status.equalsIgnoreCase("Rejected")){
			subject = "Per diem Report for " + perdiemTerm + " Rejected by " + args;
			templateName = MailSettings.PERDEIM_REJECTED;
		} else if (status.equalsIgnoreCase("Accepted")){
			subject = "Per diem Report for " + perdiemTerm + " Accepted by " + args;
			templateName = MailSettings.PERDEIM_ACCEPTED;
		} else if (status.equalsIgnoreCase("Approved")){
			subject = "Final Per diem Report for " + perdiemTerm + " Approved";
			templateName = MailSettings.PERDEIM_APROVED;
		} else if (status.equalsIgnoreCase("Report Generated")){
			templateName = MailSettings.PERDIEM_REPORT_AUTO_GENERATED;
			subject = "Per diem Report Generated for " + perdiemTerm;
		} else if (status.equalsIgnoreCase("Completed")){
			subject = "Final Per diem Report for " + perdiemTerm + " ready for disbursal";
			templateName = MailSettings.PERDIEM_COMPLETED;
		} else if (status.equalsIgnoreCase("Reviewed and Submitted")){
			subject = "Per diem Report reviewed by RMG for " + perdiemTerm;
			templateName = MailSettings.PERDIEM_REVIEWED_AND_SUBMITTED;
		} else{
			/*
			 * Perdiem Details Submitted
			 * Final Perdiem Details Submitted
			 * Perdiem Details Re-Submitted
			 */
			templateName = MailSettings.PERDIEM_SUBMITTED;
			subject = "Per diem Report submitted for " + perdiemTerm;
		}
		portalMail.setMailSubject(subject);
		portalMail.setTemplateName(templateName);
		portalMail.setPerdiemTerm(perdiemTerm); // (presentDate<15)?"(1-15)":"(16-"+lastDateOfThisMonth+")" +" of "+month+" "+year);
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

	private String generateFName(String term, ExportType ext) {
		String fileName = term + "." + ext;
		return fileName;
	}

	@Override
	public Attachements download(PortalForm form) {
		Login login = (Login) form.getObject();
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access per diem reconciliation receive Urls without having permisson at :" + new Date());
			return null;
		}
		Attachements attachements = new Attachements();
		DepPerdiem perdiemForm = (DepPerdiem) form;
		PortalData portalData = new PortalData();
		String flag=perdiemForm.getBankFlag();
		String deepid=perdiemForm.getDeppid();
		
		
		String path = portalData.getfolder(portalData.getDirPath()) + "temp";
		try{
			File file = new File(path);
			if (!file.exists()) file.mkdir();
			DepPerdiem perdiem = DepPerdiemDaoFactory.create().findByPrimaryKey(perdiemForm.getDepId());
			switch (DownloadTypes.getValue(form.getdType())) {
			case PERDIEMREPORT:
				
				if (flag!=null){
					String[] str = deepid.split(",");
					
					 
					 
					 ArrayList<Integer> arraylist=new ArrayList<Integer>();
				     for (String ww:str){
				    	 Integer inter=Integer.valueOf(ww);
				    	 arraylist.add(inter);
				    	
				    	 
				     }
					
					
					if (flag.equals("HDFC_BANK")){
						
						try{ 
							double total = 0;
							double  valueRounded1 = 0;
							 List<SalaryReconciliationReport> listHDFC = new ArrayList<SalaryReconciliationReport>();
							
								String amountDHDFC=perdiemForm.getAmount();
							    String[] strAmtHDFC=amountDHDFC.split(",");
								
							    ArrayList<Integer> amountTD=new ArrayList<Integer>();
							    for(String w:strAmtHDFC){  
							    	
							    	total+=Float.valueOf(w);
							    	  valueRounded1 = Math.round(total * 100D) / 100D;
							    }
							    
								MailGenerator mailGenerator = new MailGenerator();
								PortalMail pMail = new PortalMail();
								List<String> allReceipientcCMailId = new ArrayList<String>();
								
								Properties pro = PropertyLoader.loadProperties("conf.Portal.properties");
								String regAbbrivation =  "IN";
								String mailId;
								mailId = (pro.getProperty("mailId." + regAbbrivation + ".CEO"));
								allReceipientcCMailId.add(mailId);
								pMail.setMailSubject("Amount Payable");
								pMail.setTotalAmount(String.valueOf(valueRounded1));
								pMail.setTemplateName(MailSettings.AMOUNT_DEDUCTED);
								pMail.setAllReceipientMailId(allReceipientcCMailId.toArray(new String[allReceipientcCMailId.size()]));
								mailGenerator.invoker(pMail);
							
							String term = perdiem.getTerm() + " term of " + perdiem.getMonth() + " " + perdiem.getYear();
							attachements.setFileName(new GenerateXls().generatePerdiemReportInExcelHdfc(DepPerdiemReportDaoFactory.create().findInternalReportDataHDFC(perdiemForm.getDepId(),arraylist), path + File.separator + "Perdiem Internal Report_" + term + ".xls", term));
						} catch (Exception e){
							logger.error("Unable to generate delayed timesheet list", e);
						}
					}
					
					else {
						try{ 
							double total = 0;
							double  valueRounded1 = 0;
							 List<SalaryReconciliationReport> listHDFC = new ArrayList<SalaryReconciliationReport>();
							
								String amountDHDFC=perdiemForm.getAmount();
							    String[] strAmtHDFC=amountDHDFC.split(",");
								
							    ArrayList<Integer> amountTD=new ArrayList<Integer>();
							    for(String w:strAmtHDFC){  
							    	
							    	total+=Float.valueOf(w);
							    	  valueRounded1 = Math.round(total * 100D) / 100D;
							    }
							    
								MailGenerator mailGenerator = new MailGenerator();
								PortalMail pMail = new PortalMail();
								List<String> allReceipientcCMailId = new ArrayList<String>();
								Properties pro = PropertyLoader.loadProperties("conf.Portal.properties");
								String regAbbrivation =  "IN";
								String mailId;
								mailId = (pro.getProperty("mailId." + regAbbrivation + ".CEO"));
								allReceipientcCMailId.add(mailId);
								pMail.setMailSubject("Amount Payable");
								pMail.setTotalAmount(String.valueOf(valueRounded1));
								pMail.setTemplateName(MailSettings.AMOUNT_DEDUCTED);
								pMail.setAllReceipientMailId(allReceipientcCMailId.toArray(new String[allReceipientcCMailId.size()]));
								mailGenerator.invoker(pMail);
							
							String term = perdiem.getTerm() + " term of " + perdiem.getMonth() + " " + perdiem.getYear();
							attachements.setFileName(new GenerateXls().generatePerdiemReportInExcelNONHDFC(DepPerdiemReportDaoFactory.create().findInternalReportDataNONHDFC(perdiemForm.getDepId(),arraylist), path + File.separator + "Perdiem Internal Report_" + term + ".xls", term));
						} catch (Exception e){
							logger.error("Unable to generate delayed timesheet list", e);
						}
						
					}
					
				}
				else{
					try{
						String term = perdiem.getTerm() + " term of " + perdiem.getMonth() + " " + perdiem.getYear();
						attachements.setFileName(new GenerateXls().generatePerdiemReportInExcel(DepPerdiemReportDaoFactory.create().findInternalReportData(perdiemForm.getDepId()), path + File.separator + "Perdiem Internal Report_" + term + ".xls", term));
					} catch (Exception e){
						logger.error("Unable to generate delayed timesheet list", e);
					}
				
				}
					break;
				case BANKLETTER:
					String fileName = generateFName(("PerDiem_Reconciliation_ICICI_BankLetter" + perdiem.getTerm() + " term of " + perdiem.getMonth()), ExportType.pdf);
					List<PerdiemReportBean> arrayList_perdiem = DepPerdiemReportDaoFactory.create().findBankReport(perdiem.getId(), 1);
					JasperReport jasperReport = (JasperReport) JRLoader.loadObject(PropertyLoader.getEnvVariable() + File.separator + "jasper" + File.separator + "/perdiembankletter.jasper");
					Map params = new HashMap();
					params.put(JRParameter.REPORT_LOCALE, Locale.US);
					params.put("MONTH", perdiem.getMonth() + " - " + perdiem.getYear());
					JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(arrayList_perdiem));
					JasperExportManager.exportReportToPdfFile(jasperPrint, path + File.separator + fileName);
					attachements.setFileName(fileName);
					/*	try{
							String term = "ICICI Bank latter for" + perdiem.getTerm() + " term of " + perdiem.getMonth() + " " + perdiem.getYear();
							attachements.setFileName(new GenerateXls().generatePerdiemBankLetter(DepPerdiemReportDaoFactory.create().findBankReport(perdiem.getId(), 1), path, term, perdiem.getMonth() + "-" + perdiem.getYear()));
						} catch (Exception e){
							logger.error("unable to generate Salary Letter report xls" + e.getMessage());
						}*/
					break;
				case ICICIREPORT:
					try{
						String term = "ICICI Bank report for" + perdiem.getTerm() + " term of " + perdiem.getMonth() + " " + perdiem.getYear();
						String month = perdiem.getMonth().substring(0, 3).toUpperCase();
						if (!perdiem.getTerm().equalsIgnoreCase("first")){
							Calendar cal = Calendar.getInstance();
							cal.setTime(getLastDateOfMonth(perdiem.getYear(), Integer.parseInt(PortalUtility.getMonthNumber(month))));
							month += " 16 - " + cal.get(Calendar.DAY_OF_MONTH);
						} else month += " 1 - 15";
						attachements.setFileName(new GenerateXls().generateICICIReport(DepPerdiemReportDaoFactory.create().findBankReport(perdiem.getId(), 1), path, term, " " + month));
					} catch (Exception e){
						logger.error("Unable to generate per-diem Letter report xls" + e.getMessage());
					}
					break;
				case OTHERREPORT:
					try{
						String term = "other Bank report for" + perdiem.getTerm() + " term of " + perdiem.getMonth() + " " + perdiem.getYear();
						String month = perdiem.getMonth().substring(0, 3).toUpperCase();
						if (!perdiem.getTerm().equalsIgnoreCase("first")){
							Calendar cal = Calendar.getInstance();
							cal.setTime(getLastDateOfMonth(perdiem.getYear(), Integer.parseInt(PortalUtility.getMonthNumber(month))));
							month += " 16 - " + cal.get(Calendar.DAY_OF_MONTH);
						} else month += " 1 - 15";
						attachements.setFileName(new GenerateXls().generateOtherReport(DepPerdiemReportDaoFactory.create().findBankReport(perdiem.getId(), 0), path, term, " " + month));
					} catch (Exception e){
						logger.error("Unable to generate per-diem Letter report xls" + e.getMessage());
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

	public void escalationforPerdiemHold() {
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			DepPerdiemHold perdiemHold[] = DepPerdiemHoldDaoFactory.create(conn).findByDynamicSelect("SELECT DPH.* FROM DEP_PERDIEM_HOLD DPH JOIN DEP_PERDIEM_REPORT DPR ON DPR.ID=DPH.REP_ID WHERE DPR.TYPE=" + ReconciliationModel.HOLD + " ORDER BY DPH.ID", null);
			Map<String, Date> dateMap = new HashMap<String, Date>();
			for (DepPerdiemHold hold : perdiemHold){
				int daysCrossed = EscalationJob.getWorkingDaysCount(1, hold.getActionOn(), new Date());
				if (hold.getEscFrom() == 0 && hold.getActionOn() != null) dateMap.put(hold.getRepId() + "", hold.getActionOn());
				if (daysCrossed == 7 && hold.getEscFrom() == 0){
					sendPerdiemHoldTask(hold, conn, daysCrossed);
				} else if ((daysCrossed == 9 && hold.getEscFrom() == 0) || (daysCrossed == 2 && hold.getEscFrom() != 0)){
					if (hold.getEscFrom() > 0 && dateMap.containsKey(hold.getRepId() + "")) hold.setActionOn(dateMap.get(hold.getRepId() + ""));//make sure mail should contain actual hold date.
					escPerdiemHold(hold, conn, daysCrossed);
				} else if (daysCrossed >= 3 || hold.getEscFrom() > 0){
					if (dateMap.containsKey(hold.getRepId() + "")) hold.setActionOn(dateMap.get(hold.getRepId() + ""));//make sure mail should contain actual hold date.
					reminderPerdiemHold(hold, conn);
				}
			}
		} catch (Exception e){} finally{
			ResourceManager.close(conn);
		}
	}

	private void sendPerdiemHoldTask(DepPerdiemHold hold, Connection conn, int days) {
		try{
			DepPerdiemReport perdiemReport = DepPerdiemReportDaoFactory.create(conn).findByPrimaryKey(hold.getRepId());
			DepPerdiem perdiem = DepPerdiemDaoFactory.create(conn).findByPrimaryKey(perdiemReport.getDepId());
			PortalMail mail = new PortalMail();
			mail.setTemplateName(MailSettings.PERDIEM_HOLD_TASK);
			mail.setEmployeeName(ModelUtiility.getInstance().getEmployeeName(perdiemReport.getUserId(), conn));
			mail.setMailSubject("Diksha Lynx: Per diem on hold of " + mail.getEmployeeName());
			mail.setPerdiemTerm(getPerdiemPeriodTermStr(perdiem));
			mail.setDaysCrossed(days + "");
			mail.setOnDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(hold.getActionOn()));
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
			ProfileInfo profileInfo = profileInfoDao.findWhereUserIdEquals(hold.getUserId());
			mail.setApproverName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
			mail.setRecipientMailId(profileInfo.getOfficalEmailId());
			MailGenerator generator = new MailGenerator();
			generator.invoker(mail);
			String bodyText = generator.replaceFields(generator.getMailTemplate(mail.getTemplateName()), mail);
			InboxModel inbox = new InboxModel();
			inbox.populateInbox(perdiem.getEsrMapId(), mail.getMailSubject() + "(" + ModelUtiility.getInstance().getEmployeeId(perdiemReport.getUserId(), conn) + ")", "On Hold", hold.getUserId(), hold.getUserId(), 1, bodyText, "PERDIEM_RECON");
		} catch (Exception e){}
	}

	private void escPerdiemHold(DepPerdiemHold hold, Connection conn, int days) {
		try{
			DepPerdiemReport perdiemReport = DepPerdiemReportDaoFactory.create(conn).findByPrimaryKey(hold.getRepId());
			DepPerdiem perdiem = DepPerdiemDaoFactory.create(conn).findByPrimaryKey(perdiemReport.getDepId());
			ProcessChain processChainDto = ProcessChainDaoFactory.create(conn).findWhereProcNameEquals("IN_DEPUTATION_PROC")[0];
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
				mail.setTemplateName(MailSettings.PERDIEM_HOLD_ESC);
				mail.setEmployeeName(ModelUtiility.getInstance().getEmployeeName(perdiemReport.getUserId(), conn));
				mail.setMailSubject("Diksha Lynx: Escalation: Per diem on hold of " + mail.getEmployeeName());
				mail.setPerdiemTerm(getPerdiemPeriodTermStr(perdiem));
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
				String subject = mail.getMailSubject() + "(" + ModelUtiility.getInstance().getEmployeeId(perdiemReport.getUserId(), conn) + ")";
				for (String id : toList){
					holdDao.insert(new DepPerdiemHold(hold.getRepId(), Integer.parseInt(id), 7, "", hold.getId()));
					inbox.populateInbox(perdiem.getEsrMapId(), subject, "On Hold", Integer.parseInt(id), Integer.parseInt(id), 1, bodyText, "PERDIEM_RECON");
				}
				inbox.populateInbox(perdiem.getEsrMapId(), mail.getMailSubject(), "On Hold", 0, hold.getUserId(), 1, bodyText, "PERDIEM_REPORT");
			}
		} catch (Exception e){}
	}

	private List<String> getList(Object[] array) {
		List<String> l = new ArrayList<String>();
		for (Object o : array)
			l.add(o.toString());
		return l;
	}

	private void reminderPerdiemHold(DepPerdiemHold hold, Connection conn) {
		try{
			PortalMail mail = new PortalMail();
			mail.setTemplateName(MailSettings.PERDIEM_HOLD_REMINDER);
			DepPerdiemReport perdiemReport = DepPerdiemReportDaoFactory.create(conn).findByPrimaryKey(hold.getRepId());
			DepPerdiem perdiem = DepPerdiemDaoFactory.create(conn).findByPrimaryKey(perdiemReport.getDepId());
			mail.setEmployeeName(ModelUtiility.getInstance().getEmployeeName(perdiemReport.getUserId(), conn));
			mail.setMailSubject("Diksha Lynx: Reminder: Per diem on hold of " + mail.getEmployeeName());
			mail.setPerdiemTerm(getPerdiemPeriodTermStr(perdiem));
			mail.setOnDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(hold.getActionOn()));
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
			ProfileInfo profileInfo = profileInfoDao.findWhereUserIdEquals(hold.getUserId());
			mail.setRecipientMailId(profileInfo.getOfficalEmailId());
			mail.setHandlerName(profileInfo.getFirstName() + " " + profileInfo.getLastName());
			MailGenerator generator = new MailGenerator();
			generator.invoker(mail);
			String bodyText = generator.replaceFields(generator.getMailTemplate(mail.getTemplateName()), mail);
			InboxModel inbox = new InboxModel();
			inbox.populateInbox(perdiem.getEsrMapId(), mail.getMailSubject(), "On Hold", 0, hold.getUserId(), 1, bodyText, "PERDIEM_REPORT");
		} catch (Exception e){}
	}

	private List<DepPerdiem> receiveEscalations(int loggedInUserId) throws ProfileInfoDaoException {
		EmpSerReqMapDao esrDao = EmpSerReqMapDaoFactory.create();
		DepPerdiemDao perdiemDao = DepPerdiemDaoFactory.create();
		DepPerdiemReqDao perdiemReqDao = DepPerdiemReqDaoFactory.create();
		List<DepPerdiem> perdiemList = new ArrayList<DepPerdiem>();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		try{
			DepPerdiemReq[] perdiemReqs = perdiemReqDao.findByDynamicWhere(" ASSIGNED_TO=? AND IS_ESCALATED !=0 AND SUBMITTED_ON IS NULL ORDER BY ID DESC", new Object[] { loggedInUserId });
			EmpSerReqMap esr = null;
			if (perdiemReqs != null && perdiemReqs.length > 0){
				for (DepPerdiemReq eachPerdiemReq : perdiemReqs){
					DepPerdiem tempPerdiem = perdiemDao.findByPrimaryKey(eachPerdiemReq.getDepId());
					esr = esrDao.findByPrimaryKey(tempPerdiem.getEsrMapId());
					tempPerdiem.setReqId(esr.getReqId());
					tempPerdiem.setStatus(tempPerdiem.getHtmlStatus());
					tempPerdiem.setGotoSeqId(eachPerdiemReq.getSeqId());
					if (eachPerdiemReq.getSubmittedOn() == null) tempPerdiem.setActionButtonVisibility("enable");
					int assignedTo = perdiemReqDao.findByPrimaryKey(eachPerdiemReq.getIsEscalated()).getAssignedTo();
					ProfileInfo profileActionNotTaken = profileInfoDao.findByDynamicWhere(" ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Integer[] { assignedTo })[0];
					tempPerdiem.setEscalatedFrom(profileActionNotTaken.getFirstName());
					tempPerdiem.setEscalatedFromId(assignedTo);
					perdiemList.add(tempPerdiem);
				}
			}
		} catch (DepPerdiemReqDaoException e){
			logger.error("There is DepPerdiemReqDaoException occured while querying the escalated per diem records for the user " + loggedInUserId);
		} catch (DepPerdiemDaoException e){
			e.printStackTrace();
		} catch (EmpSerReqMapDaoException e){
			e.printStackTrace();
		}
		return perdiemList;
	}

	private void sendDisbursalMailToEmployees(int id) {
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			DepPerdiemReport[] depPerdiemReports = DepPerdiemReportDaoFactory.create(conn).findWhereDepIdEquals(id);
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
			PortalMail portalMail = new PortalMail();
			portalMail.setTemplateName(MailSettings.PERDIEM_DISBURSAL);
			MailGenerator mailGenerator = new MailGenerator();
			DepPerdiemExchangeRates[] rates = DepPerdiemExchangeRatesDaoFactory.create(conn).findWhereRepIdEquals(id);
			Map<String, Double> exchangeRates = new HashMap<String, Double>();
			if (rates != null && rates.length > 0){
				for (DepPerdiemExchangeRates rate : rates)
					exchangeRates.put(rate.getCurrencyType() + "", rate.getAmount());
			}
			Map<String, List<DepPerdiemReport>> depPerdiemReportsMap = new HashMap<String, List<DepPerdiemReport>>();
			for (DepPerdiemReport report : depPerdiemReports){
				if (report.getType() == DELETED || report.getType() == FIXED_DELETED ||report.getType() == HOLD ||report.getType() == FIXED_HOLD) continue;
				List<DepPerdiemReport> reports = depPerdiemReportsMap.get(report.getUserId() + "");
				if (reports == null){
					reports = new ArrayList<DepPerdiemReport>();
					depPerdiemReportsMap.put(report.getUserId() + "", reports);
				}
				reports.add(report);
			}
			for (Map.Entry<String, List<DepPerdiemReport>> entry : depPerdiemReportsMap.entrySet()){
				try{
					List<DepPerdiemReport> reportsList = entry.getValue();
					Collections.sort(reportsList);
					Date fromDate = null, toDate = null;
					int userId = 0;
					if (reportsList.size() > 0){
						for (DepPerdiemReport report : reportsList){
							userId = report.getUserId();
							if (fromDate == null) fromDate = report.getFrom();
							else if (fromDate.after(report.getFrom())) fromDate = report.getFrom();
							if (toDate == null) toDate = report.getTo();
							else if (toDate.before(report.getTo())) toDate = report.getTo();
						}
					}
					portalMail.setPerdiemTerm(PortalUtility.getdd_MM_yyyy(fromDate) + " - " + PortalUtility.getdd_MM_yyyy(toDate));
					portalMail.setMailSubject("Diksha: Per diem Statement: " + portalMail.getPerdiemTerm());
					ProfileInfo info = profileInfoDao.findWhereUserIdEquals(userId);
					portalMail.setEmployeeName(info.getFirstName() + " " + info.getLastName());
					portalMail.setRecipientMailId(info.getOfficalEmailId());
					if (info.getHrSpoc() > 3) portalMail.setAllReceipientcCMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE PI.ID IN(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID>3 AND U.ID IN(" + info.getHrSpoc() + "))"));
					StringBuffer body = new StringBuffer();
					double total = 0;
					for (DepPerdiemReport report : reportsList){
						body.append("<tr><td>");
						body.append(PortalUtility.getdd_MM_yyyy(report.getFrom()) + " - " + PortalUtility.getdd_MM_yyyy(report.getTo()));
						body.append("</td><td>");
						body.append(report.getClientName());
						if (report.getType() == FIXED) body.append("(Fixed)");
						body.append("</td><td align='center'>");
						body.append(PortalUtility.getdd_MM_yyyy(report.getFrom()));
						body.append("</td><td align='center'>");
						body.append(PortalUtility.getdd_MM_yyyy(report.getTo()));
						body.append("</td><td align='right'>");
						body.append(new DecimalFormat("0.00").format(report.getCurrencyType() == 1 ? Double.parseDouble(report.getPerdiem()) : Float.parseFloat(report.getPerdiem()) * exchangeRates.get(report.getCurrencyType() + "").floatValue()));
						body.append("</td><td align='center'>");
						body.append(report.getPayableDays());
						body.append("</td><td align='right'>");
						body.append(new DecimalFormat("0.00").format(Double.parseDouble(report.getAmountInr())));
						total += Double.parseDouble(report.getAmountInr());
						body.append("</td></tr>");
					}
					body.append("<tr><td colspan='7'>&nbsp;</td></tr>");
					for (DepPerdiemReport report : reportsList){
						DepPerdiemLeave[] leave = DepPerdiemLeaveDaoFactory.create().findWhereRepIdEquals(report.getId());
						if (leave != null && leave.length > 0){
							for (DepPerdiemLeave leave2 : leave){
								body.append("<tr><td>");
								body.append("Less : Leave on Deputation");
								body.append("</td><td>");
								body.append("</td><td align='center'>");
								body.append(PortalUtility.getdd_MM_yyyy(leave2.getLeaveFrom()));
								body.append("</td><td align='center'>");
								body.append(PortalUtility.getdd_MM_yyyy(leave2.getLeaveTo()));
								body.append("</td><td align='right'>");
								body.append(new DecimalFormat("0.00").format(leave2.getCurrency() == 1 ? Double.parseDouble(leave2.getAmount()) : Float.parseFloat(leave2.getAmount()) * exchangeRates.get(leave2.getCurrency() + "").floatValue()));
								body.append("</td><td align='center'>");
								body.append(leave2.getDuration());
								body.append("</td><td align='right'>-");
								body.append(new DecimalFormat("0.00").format(Double.parseDouble(leave2.getAmountInr())));
								total -= Double.parseDouble(leave2.getAmountInr());
								body.append("</td></tr>");
							}
						}
					}
					for (DepPerdiemReport report : reportsList){
						DepPerdiemComponents[] components = DepPerdiemComponentsDaoFactory.create().findWhereRepIdEquals(report.getId());
						if (components != null && components.length > 0){
							for (DepPerdiemComponents component : components){
								body.append("<tr><td>");
								body.append((component.getType() == 1 ? "Add " : "Less") + " : " + component.getReason());
								body.append("</td><td>");
								body.append("</td><td align='center'>");
								body.append("</td><td align='center'>");
								body.append("</td><td align='center'>");
								//body.append(component.getCurrency() == 1 ? new DecimalFormat("0.00").format(Double.parseDouble(component.getAmount())) : new DecimalFormat("0.00").format(Float.parseFloat(component.getAmount()) * exchangeRates.get(component.getCurrency() + "").floatValue()));
								body.append("</td><td align='center'>");
								body.append("</td><td align='right'>");
								body.append((component.getType() == 1 ? "" : "-") + new DecimalFormat("0.00").format(Double.parseDouble(component.getAmountInr())));
								if (component.getType() == 1) total += Double.parseDouble(component.getAmountInr());
								else total -= Double.parseDouble(component.getAmountInr());
								body.append("</td></tr>");
							}
						}
					}
					body.append("<tr><td colspan='6' align='right'> Total &nbsp;&nbsp;&nbsp;</td><td align='right'>");
					body.append(new DecimalFormat("0.00").format(total));
					body.append("</td></tr>");
					portalMail.setMessageBody(body.toString());
					mailGenerator.invoker(portalMail);
				} catch (Exception e){
					logger.error("Unable to send per diem disbursal mail to employee." + entry.getValue(), e);
				}
			}
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public Integer[] upload(List<FileItem> fileItems, String docType, int uploadSrId, HttpServletRequest request, String description) {
		
		Integer fieldsId[] = new Integer[fileItems.size()];
		DocumentTypes dTypes = DocumentTypes.valueOf(docType);
		Integer[] nonsuccess = new Integer[fileItems.size()];
		Connection conn = null;
		PreparedStatement stmt = null;
	
		switch (dTypes) {
		case PER_XLS:{
			
			if (fileItems != null && !fileItems.isEmpty()){
				FileItem file = (FileItem) fileItems.get(0);
				InputStream stream = null;
				InputStream stream1 = null;
				float sal = 0;
				String mailId = "";
				String name = null ;
				int month = Calendar.getInstance().get(Calendar.MONTH);
				Vector<Vector<Object>> list = null;
			
				try{
			//		month = Integer.parseInt(file.getName().substring(0, file.getName().indexOf(".")));
					stream = file != null ? file.getInputStream() : null;
					stream1 = file != null ? file.getInputStream() : null;
				} catch (IOException e1){
					e1.printStackTrace();
				}
				if (month == 0){
					request.setAttribute("SALARY UPLOAD MESSAGE", "please rename file like 09.xls(for month 09)");
					return new Integer[] { 0 };
				}
				ArrayList notuudatedList = new ArrayList();
				
				if (stream != null) 
					try{
					 list = POIParser.parseXlsSal(stream1, 0);
					 if(list.equals("")||list.isEmpty())
					{
						 list = POIParser.parseXlsSal(stream, 1);	
					}
					stream.close();
					if (list != null && !list.isEmpty()){
						Map<Integer, Integer> userids = UsersDaoFactory.create().findAllUsersEmployeeIds();
//						int sizeOfList = list.size();
//						int c = 0;
					/*	Connection conn = null;
						PreparedStatement stmt = null;*/
						final boolean isConnSupplied = (userConn != null);
						conn = isConnSupplied ? userConn : ResourceManager.getConnection();
						
						for (Vector<Object> row : list){
						/*	c++;
							if(c != sizeOfList) {*/
							int userId = userids.get(((Double) row.get(1)).intValue());
							sal = (Float) row.get(3);
					
							try{
								ResultSet res = null;
								SalaryReportBean salaryReportBean = null;
								String sql="UPDATE DEP_PERDIEM_REPORT SET PAID=? WHERE  USER_ID IN("+userId+") AND SR_ID IN ("+uploadSrId +") ";
								int i=0;
								stmt = conn.prepareStatement(sql);
								stmt.setObject(1, "paid");
								int affectedrow=stmt.executeUpdate();
								logger.debug("PAID STATUS UPDATED IN DEP_PERDIEM_REPORT ROW AFFECTED"+affectedrow);
								ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
								String profileInfo = ProfileInfoDaoFactory.create(conn).findByDynamicSelect1("SELECT CONCAT(FIRST_NAME, ' ', LAST_NAME) as EMPLOYEE_NAME FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(userId) })[0].getEmployeeName();
								ProfileInfo[] profileInfo1 = ProfileInfoDaoFactory.create(conn).findByOfficialEmailId("SELECT OFFICAL_EMAIL_ID FROM PROFILE_INFO P LEFT JOIN USERS U ON U.PROFILE_ID = P.ID WHERE U.ID = ?",new Object[] {userId});
								for (ProfileInfo profileInfo2 : profileInfo1) {
									mailId =profileInfo2.getOfficalEmailId();
								}
								PortalMail pMail = new PortalMail();
								MailGenerator mailGenerator = new MailGenerator();
								List<String> allReceipientcCMailId = new ArrayList<String>();
								allReceipientcCMailId.add(mailId);
								pMail.setAllReceipientMailId(allReceipientcCMailId.toArray(new String[allReceipientcCMailId.size()]));
								pMail.setMailSubject("Payment Notification");
								pMail.setFinalSalary(String.valueOf(sal));
								pMail.setEmployeeName(profileInfo);
								pMail.setTemplateName(MailSettings.PAYMENT_NOTIFICATION);
								mailGenerator.invoker(pMail);
								fieldsId[0] = uploadSrId;
								nonsuccess[0] = uploadSrId;
								}
							catch(SQLException e){
								e.printStackTrace();
								nonsuccess = null;
							}
						}
					}
						
					} catch (Exception e) {
						nonsuccess[0] = uploadSrId;
					
					}
				finally {
					ResourceManager.close(conn);
					ResourceManager.close(stmt);
					
				}
			}
			
			
		}
		}
		return nonsuccess;
		}
	

	public static void main(String[] args) {
		JDBCUtiility.getInstance().update("UPDATE dep_perdiem_hold SET ACTION_ON = ADDDATE(ACTION_ON, INTERVAL -1 DAY)", null);
		new ReconciliationModel().escalationforPerdiemHold();
	}
	

}
