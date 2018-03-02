package com.dikshatech.common.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.DepPerdiemComponentsDao;
import com.dikshatech.portal.dao.DepPerdiemDao;
import com.dikshatech.portal.dao.DepPerdiemReportDao;
import com.dikshatech.portal.dao.DepPerdiemReqDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.PerdiemMasterDataDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.RmgTimeSheetDao;
import com.dikshatech.portal.dao.RmgTimeSheetReconDao;
import com.dikshatech.portal.dao.RmgTimeSheetReqDao;
import com.dikshatech.portal.dto.ActiveChargeCode;
import com.dikshatech.portal.dto.Client;
import com.dikshatech.portal.dto.DepPerdiem;
import com.dikshatech.portal.dto.DepPerdiemComponents;
import com.dikshatech.portal.dto.DepPerdiemReport;
import com.dikshatech.portal.dto.DepPerdiemReq;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.PerdiemInAdvance;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.RmgTimeSheet;
import com.dikshatech.portal.dto.RmgTimeSheetReco;
import com.dikshatech.portal.dto.RmgTimeSheetReq;
import com.dikshatech.portal.exceptions.CurrencyDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemHistoryDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemReqDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.ProjectDaoException;
import com.dikshatech.portal.exceptions.ProjectMappingDaoException;
import com.dikshatech.portal.exceptions.RmgTimeSheetReqDaoException;
import com.dikshatech.portal.factory.ClientDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemComponentsDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemReportDaoFactory;
import com.dikshatech.portal.factory.DepPerdiemReqDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.PerdiemInAdvanceDaoFactory;
import com.dikshatech.portal.factory.PerdiemMasterDataDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.RmgTimeSheetDaoFactory;
import com.dikshatech.portal.factory.RmgTimeSheetReconDaoFactory;
import com.dikshatech.portal.factory.RmgTimeSheetReqDaoFactory;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.models.DtoToBeanConverter;

public class RmgTimeSheetReconciliationNotifier {
	
	private static Logger	logger	= LoggerUtil.getLogger(RmgTimeSheetReconciliationNotifier.class);
	
	
	public int generateReportAndNotify(int userId) {
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
		String month = new SimpleDateFormat("MMMM").format(new Date());
		int presentDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
		int lastDateOfThisMonth = (Integer.parseInt(new SimpleDateFormat("dd").format(getLastDateOfMonth(year, Integer.parseInt(new SimpleDateFormat("MM").format(new Date()))))));
		logger.info("timesheetReconciliation Report Generation Initiated for the " + ((presentDate < 15) ? "First" : "Second" + " Term of " + month + " " + year));
		/*PerdiemMasterDataDao perdiemMasterDao = PerdiemMasterDataDaoFactory.create();
		EmpSerReqMapDao esrMapDao = EmpSerReqMapDaoFactory.create();
		RegionsDao regionsDao = RegionsDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		DepPerdiemDao depPerdiemDao = DepPerdiemDaoFactory.create();
		ProcessEvaluator pEval = new ProcessEvaluator();*/
		
		EmpSerReqMapDao esrMapDao = EmpSerReqMapDaoFactory.create();
		RegionsDao regionsDao = RegionsDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ProcessEvaluator pEval = new ProcessEvaluator();
		RmgTimeSheetReconDao rmgTimeSheetReconDao=RmgTimeSheetReconDaoFactory.create();
		RmgTimeSheetDao rmgTimeSheetDao=RmgTimeSheetDaoFactory.create();
		
		
		EmpSerReqMapPk esrmapPk = null;
		try{
			int userRegionid = 1;
			RmgTimeSheetReco details = null;
			ProcessChain processChainDto = processChainDao.findWhereProcNameEquals("IN_TIMESHEET_RECONCILIATION")[0];
			/*DepPerdiem detailsList[] = depPerdiemDao.findByDynamicWhere(" MONTH=? AND YEAR=? AND TERM=?", new Object[] { month, year, (presentDate < 15) ? "First" : "Second" });*/
			RmgTimeSheetReco detailsList[]=rmgTimeSheetReconDao.findByDynamicWhere(" MONTH=? AND YEAR=? AND TERM=?", new Object[] { month, year, "Monthly" });
			//RmgTimeSheetReco detailsList[] = rmgTimeSheetReconDao.findByDynamicWhere(" MONTH=? AND YEAR=? AND TERM=?", new Object[] { month, year, "Monthly" });
			Integer[] lastLevelApproverIds = pEval.approvers(processChainDto.getApprovalChain(), 4, 1);
			if (detailsList != null && detailsList.length > 0){
				if (userId == 0) return 1;
				details = detailsList[0];
				Set<Integer> toList = new HashSet<Integer>();
				Connection conn = null;
				try{
					conn = ResourceManager.getConnection();
					RmgTimeSheetReqDao rmgTimeSheetReqDao = RmgTimeSheetReqDaoFactory.create(conn);
					RmgTimeSheetReq rmgTimeSheetReq[] = rmgTimeSheetReqDao.findWhereDepIdEquals(details.getId());
					if (rmgTimeSheetReq != null && rmgTimeSheetReq.length > 0){
						for (RmgTimeSheetReq req : rmgTimeSheetReq){
							toList.add(req.getAssignedTo());
							rmgTimeSheetReqDao.delete(req.createPk());
						}
					}
					JDBCUtiility.getInstance().update("DELETE FROM RMG_TIMESHEET_REPORT WHERE RMG_TIMESHEET_ID=?", new Object[] { details.getId() }, conn);
					JDBCUtiility.getInstance().update("DELETE FROM RMG_TIMESHEET_REQ WHERE RMG_TIMESHEET_ID=?", new Object[] { details.getId() }, conn);
					JDBCUtiility.getInstance().update("DELETE FROM INVOICE_RECONCILIATION_REPORT WHERE RMG_ID=?", new Object[] { details.getId() }, conn);
					//JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID=?", new Object[] { details.getEsrMapId() }, conn);
				} catch (Exception e){
					logger.error("There is an timesheetReconciliation exception " + e.getMessage(), e);  
					e.printStackTrace();
				} finally{
					ResourceManager.close(conn);
				}
				details.setSubmittedOn(new Date());
				details.setStatus("Report Generated");
				details.setHtmlStatus("Report Generated");
				rmgTimeSheetReconDao.update(details.createPk(), details);
				try{
					String userName = ModelUtiility.getInstance().getEmployeeName(userId);
					//sendMail(toList.toArray(new Integer[toList.size()]), lastLevelApproverIds, userName);
				} catch (Exception e){
					logger.error("There is an timesheetReconciliation exception " + e.getMessage(), e);  
				}
			} else{
				details = new RmgTimeSheetReco();
				int maxDepId = rmgTimeSheetReconDao.findMaxId();
				String reqId = regionsDao.findByPrimaryKey(userRegionid).getRefAbbreviation() + "-MR-" + (maxDepId);
				EmpSerReqMap esrRow = new EmpSerReqMap();
				esrRow.setSubDate(new Date());
				esrRow.setReqId(reqId);
				esrRow.setReqTypeId(16);
				esrRow.setRegionId(userRegionid);
				esrRow.setRequestorId(1);
				esrRow.setProcessChainId(processChainDto.getId());
				esrRow.setNotify(processChainDto.getNotification());
				esrmapPk = esrMapDao.insert(esrRow);
				//
				details.setEsrMapId(esrmapPk.getId());
				//details.setTerm((presentDate < 15) ? "First" : "Second");
				details.setTerm("Monthly");
				details.setMonth(month);
				details.setYear(year);
				details.setSubmittedOn(new Date());
				details.setStatus("Report Generated");
				details.setHtmlStatus("Report Generated");
				rmgTimeSheetReconDao.insert(details);
			}
			Date[] dates = getStartEndDates(PortalUtility.formateDateTimeToDateyyyyMMdd(new Date()));
			//System.out.println("Start Date ::: " +dates[0]);
			//System.out.println("End Date ::: "+dates[1]);
			RmgTimeSheet[] list = rmgTimeSheetDao.findByAl();// PERDIEM_MASTER_DATA

			//List<DepPerdiemReport> list = perdiemMasterDao.getPerdiemCurrencyMaps(dates[0], dates[1]);// PERDIEM_MASTER_DATA
			Integer[] dateRelatedData = new Integer[] { year, presentDate, lastDateOfThisMonth };
			insertReports(list, details, details.getId(), dateRelatedData);
			Integer[] firstSeqApproverIds = pEval.handlers(processChainDto.getHandler(), 1);
			if (firstSeqApproverIds == null || firstSeqApproverIds.length == 0){
				logger.error("There are no approvers for TimeSheet reconciliation. however report generated and not assinged to any one");
				return 2;
			}
			insertIntoReq(firstSeqApproverIds, details.getId(), 0);
			//String msgBody = sendMail(firstSeqApproverIds, lastLevelApproverIds, null);
			//sendInboxNotification(firstSeqApproverIds, details.getEsrMapId(), msgBody, "Report Generated", "Reconciliation Report Generated");
		} catch (Exception e){
			logger.error("There is an timesheetReconciliation exception " + e.getMessage(), e);  
			return 2;
		}
		return 0;
	}
	
	
	private static Date getLastDateOfMonth(int year, int month) {
		if (month > 0){
			month--;
		}
		Calendar calendar = new GregorianCalendar(year, month, Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}
	
	private Date[] getStartEndDates(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Date startDate = calendar.getTime();
		Date lastDate = calendar.getTime();
		/*if (calendar.get(Calendar.DAY_OF_MONTH) < 16){
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			startDate = calendar.getTime();
			calendar.set(Calendar.DAY_OF_MONTH, 15);
			lastDate = calendar.getTime();
		} else{
			calendar.set(Calendar.DAY_OF_MONTH, 16);
			startDate = calendar.getTime();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			lastDate = calendar.getTime();
		}*/
		
		if(calendar.get(Calendar.DAY_OF_MONTH) < 16){
			calendar.set(Calendar.DAY_OF_MONTH, 15);
			lastDate = calendar.getTime();
			
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 16);
			startDate = calendar.getTime();
		}else{
			calendar.set(Calendar.DAY_OF_MONTH, 16);
			startDate = calendar.getTime();
			
			calendar.add(Calendar.MONTH, +1);
			calendar.set(Calendar.DAY_OF_MONTH, 15);
			lastDate = calendar.getTime();
		}
		
		/*calendar.set(Calendar.DAY_OF_MONTH, 15);
		lastDate = calendar.getTime();
		
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 16);
		startDate = calendar.getTime();*/
		
		return new Date[] { startDate, lastDate };
	}
	
	private void insertReports(RmgTimeSheet[] list, RmgTimeSheetReco details, int RmgTimeSheetId, Integer[] dateRelatedData) throws NumberFormatException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, DepPerdiemHistoryDaoException, ProfileInfoDaoException, CurrencyDaoException, ProjectMappingDaoException, ProjectDaoException {
		/* dateRelatedData [] = new Integer[]{year,presentDate,lastDateOfThisMonth}; */
		int presentDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
		/*ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		DepPerdiemReportDao depPerdiemReportDao = DepPerdiemReportDaoFactory.create();
		DepPerdiemComponentsDao componentsDao = DepPerdiemComponentsDaoFactory.create();*/

			try{
				
				
				RmgTimeSheetDao timeSheetReconciliation=RmgTimeSheetDaoFactory.create(); 
				  
				  try{
					 
					  
					  
					  if(list!=null)
				        {
				        	//poEmpDao.deleteAllByPoDetail(poForm.getId());
				        	for (RmgTimeSheet report : list)
			              {
				        		
				        		report.setRmg_timeSheet_Id(RmgTimeSheetId);
				        		timeSheetReconciliation.insert(report);
			              }
				        	
				        }
					  
				  }catch(Exception e){
					  logger.error("There is an timesheetReconciliation exception " + e.getMessage(), e);  
				  }
			} catch (Exception e){
				e.printStackTrace();
			}
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
	
	
	}


