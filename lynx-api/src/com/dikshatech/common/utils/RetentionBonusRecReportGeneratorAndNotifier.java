package com.dikshatech.common.utils;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.RetentionBonusDao;
import com.dikshatech.portal.dao.RetentionBonusMasterDataDao;
import com.dikshatech.portal.dao.RetentionBonusReconciliationDao;
import com.dikshatech.portal.dao.RetentionBonusReconciliationReportDao;
import com.dikshatech.portal.dao.RetentionBonusReconciliationReqDao;
import com.dikshatech.portal.dao.RetentionEmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.SalaryDetailsDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.RetentionBonus;
import com.dikshatech.portal.dto.RetentionBonusMasterData;
import com.dikshatech.portal.dto.RetentionBonusReconciliation;
import com.dikshatech.portal.dto.RetentionBonusReconciliationReport;
import com.dikshatech.portal.dto.RetentionBonusReconciliationReq;
import com.dikshatech.portal.dto.Client;
import com.dikshatech.portal.dto.RetentionEmpSerReqMap;
import com.dikshatech.portal.dto.RetentionEmpSerReqMapPk;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.RollOn;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.RetentionBonusDaoException;
import com.dikshatech.portal.exceptions.RetentionBonusMasterDataDaoException;
import com.dikshatech.portal.exceptions.RetentionBonusReconciliationReportDaoException;
import com.dikshatech.portal.exceptions.RetentionBonusReconciliationReqDaoException;
import com.dikshatech.portal.exceptions.CurrencyDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemDaoException;
import com.dikshatech.portal.exceptions.DepPerdiemHistoryDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.ProjectDaoException;
import com.dikshatech.portal.exceptions.ProjectMappingDaoException;
import com.dikshatech.portal.exceptions.RollOnDaoException;
import com.dikshatech.portal.exceptions.SalaryDetailsDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.RetentionBonusDaoFactory;
import com.dikshatech.portal.factory.RetentionBonusMasterDataDaoFactory;
import com.dikshatech.portal.factory.RetentionBonusReconciliationDaoFactory;
import com.dikshatech.portal.factory.RetentionBonusReconciliationReportDaoFactory;
import com.dikshatech.portal.factory.RetentionBonusReconciliationReqDaoFactory;
import com.dikshatech.portal.factory.ClientDaoFactory;
import com.dikshatech.portal.factory.RetentionEmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.RollOnDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class RetentionBonusRecReportGeneratorAndNotifier {
	private static Logger	logger	= LoggerUtil.getLogger(RetentionBonusRecReportGeneratorAndNotifier.class);

	public int generateReportAndNotify() {
		return generateReportAndNotify(0);
		}
	public int generateReportAndNotify(int userId){

		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
		int val =Calendar.getInstance().get(Calendar.MONTH)+1; 
       String month=findStringFormatofMonth(val);
		//String month="FEBRUARY";
        if(month==null){
        	return 3;
        }
		else{
		//if (month.equalsIgnoreCase("MARCH")||month.equalsIgnoreCase("JUNE")||month.equalsIgnoreCase("SEPTEMBER")||month.equalsIgnoreCase("DECEMBER")){
        
		logger.info("Retention Bonus Report Generation Initiated for the Month of " + month + " " + year);
		RetentionBonusMasterDataDao bonusMasterDao = RetentionBonusMasterDataDaoFactory.create();
		RetentionEmpSerReqMapDao esrMapDao = RetentionEmpSerReqMapDaoFactory.create();
		RegionsDao regionsDao = RegionsDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		RetentionBonusReconciliationDao bonusDao = RetentionBonusReconciliationDaoFactory.create();
		ProcessEvaluator pEval = new ProcessEvaluator();
		RetentionEmpSerReqMapPk esrmapPk = null;
		try{
			int userRegionid = 1;
			RetentionBonusReconciliation details = null;
			RetentionBonusReconciliation Bonusdetails = null;
			ProcessChain processChainDto = processChainDao.findWhereProcNameEquals("IN_RETENTION_BONUS_RECON")[0];
			RetentionBonusReconciliation detailsList[] = bonusDao.findByDynamicWhere(" MONTH=? AND YEAR=? ", new Object[] { month, year });
			Integer[] lastLevelApproverIds = pEval.approvers(processChainDto.getApprovalChain(), 4, 1);
			
			if (detailsList != null && detailsList.length > 0){
				Bonusdetails = detailsList[0];
				if(Bonusdetails.getHtmlStatus().equals("Reviewed and Submitted") ||Bonusdetails.getHtmlStatus().equals("Reviewed and Re-Submitted")
						|| Bonusdetails.getHtmlStatus().equals("Completed") || Bonusdetails.getHtmlStatus().equals("Accepted") || Bonusdetails.getHtmlStatus().equals("Approved")){
					return 4;
				}
			if (userId == 0) return 1;
				details = detailsList[0];
				Set<Integer> toList = new HashSet<Integer>();
				Connection conn = null;
				try{
					conn = ResourceManager.getConnection();
					RetentionBonusReconciliationReqDao bonusReqDao = RetentionBonusReconciliationReqDaoFactory.create(conn);
					RetentionBonusReconciliationReq bonusReq[] = bonusReqDao.findWhereBonusIdEquals(details.getId());
					if (bonusReq != null && bonusReq.length > 0){
						for (RetentionBonusReconciliationReq req : bonusReq){
							toList.add(req.getAssignedTo());
							bonusReqDao.delete(req.createPk());
						}
					}
					JDBCUtiility.getInstance().update("DELETE FROM RETENTION_BONUS_RECONCILIATION_REQ WHERE BR_ID=?", new Object[] { details.getId() }, conn);
					JDBCUtiility.getInstance().update("DELETE FROM RETENTION_BONUS_REC_REPORT WHERE BR_ID=?", new Object[] { details.getId() }, conn);
					JDBCUtiility.getInstance().update("DELETE FROM RETENTION_BONUS_REC_EXCHANGE_RATE WHERE REP_ID=?", new Object[] { details.getId() }, conn);
					JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID=?", new Object[] { details.getEsrMapId() }, conn);
					JDBCUtiility.getInstance().update("DELETE FROM RETENTION_BONUS_MASTER_DATA WHERE MONTH=? AND YEAR=?", new Object[] { month, year }, conn);
				} catch (Exception e){
					e.printStackTrace();
				} finally{
					ResourceManager.close(conn);
				}
				details.setSubmittedOn(null);
				details.setStatus("Report Generated");
				details.setHtmlStatus("Report Generated");
				bonusDao.update(details.createPk(), details);
				try{
				String userName = ModelUtiility.getInstance().getEmployeeName(userId);
				sendMail(toList.toArray(new Integer[toList.size()]), lastLevelApproverIds, userName);
				} catch (Exception e){}
			} else{
				details = new RetentionBonusReconciliation();
				int maxBonusId = bonusDao.findMaxId();
				String reqId = regionsDao.findByPrimaryKey(userRegionid).getRefAbbreviation() + "-BR-" + (maxBonusId);
				RetentionEmpSerReqMap esrRow = new RetentionEmpSerReqMap();
				esrRow.setSubDate(new Date());
				esrRow.setReqId(reqId);
				esrRow.setReqTypeId(16);
				esrRow.setRegionId(userRegionid);
				esrRow.setRequestorId(1);
				esrRow.setProcessChainId(processChainDto.getId());
				esrRow.setNotify(processChainDto.getNotification());
				esrmapPk = esrMapDao.insert(esrRow);
				details.setEsrMapId(esrmapPk.getId());
				details.setMonth(month);
				details.setYear(year);
				details.setSubmittedOn(null);
				details.setStatus("Report Generated");
				details.setHtmlStatus("Report Generated");
				bonusDao.insert(details);
			}
			
			insertIntoBonusMaster(month,year);
			List<RetentionBonusReconciliationReport> list = bonusMasterDao.getBonusCurrencyMaps(month,year);// RETENTION_BONUS_MASTER_DATA
            insertReports(list, details, details.getId());
			Integer[] firstSeqApproverIds = pEval.handlers(processChainDto.getHandler(), 1);
			if (firstSeqApproverIds == null || firstSeqApproverIds.length == 0){
				logger.error("There are no approvers for retention bonus reconciliation. however report generated and not assinged to any one");
				return 2;
			}
			insertIntoReq(firstSeqApproverIds, details.getId(), 0);
			//String msgBody = sendMail(firstSeqApproverIds, lastLevelApproverIds, null);
		   //sendInboxNotification(firstSeqApproverIds, details.getEsrMapId(), msgBody, "Report Generated", "Retention Bonus Reconciliation Report Generated");
		
		
		} catch (Exception ex){
			ex.printStackTrace();
			logger.error("Retention Bonus Report Generation Failed : ", ex);
			return 2;}
		//} 
		}
		return 0;
	}
	
	public String findStringFormatofMonth(int value){
		String month=""; 
		//if(value==3||value==6||value==9||value==12){
			
			if(value==3 || value==03)       month= "MARCH"; 
			else if(value==6 || value==06)  month= "JUNE";
			else if(value==9)  month= "SEPTEMBER";
			else if(value==12) month= "DECEMBER";
			
			else if(value==1 || value==01) month= "JANUARY";
			else if(value==2 || value==02) month= "FEBRUARY";
			else if(value==4 || value==04) month= "APRIL";
			else if(value==5 || value==05) month= "MAY";
			else if(value==7 || value==07) month= "JULY";
			else if(value==8) month= "AUGUST";
			else if(value==10) month= "OCTOBER";
			else if(value==11) month= "NOVEMBER";
			
		
		//}
		//else {return null;}
		return month;
	}
	
	private void insertIntoReq(Integer[] approverIds, int bonusId, int seqId) throws RetentionBonusReconciliationReqDaoException {
		RetentionBonusReconciliationReq bonusReq = null;
		RetentionBonusReconciliationReqDao bonusReqDao = RetentionBonusReconciliationReqDaoFactory.create();
		for (Integer eachApproverId : approverIds){
			bonusReq = new RetentionBonusReconciliationReq();
			bonusReq.setBonusId(bonusId);
			bonusReq.setSeqId(seqId);
			bonusReq.setAssignedTo(eachApproverId);
			bonusReq.setReceivedOn(new Date());
			bonusReq.setSubmittedOn(null);// intentionally
			bonusReq.setIsEscalated(0);
			bonusReqDao.insert(bonusReq);
			
		}
	}
	
	private void sendInboxNotification(Integer[] receiverIds, int esrMapId, String msgBody, String status, String subject) throws 
	InboxDaoException {
		Inbox inbox = null;
		InboxDao inboxDao = InboxDaoFactory.create();
		for (Integer eachReceiverid : receiverIds){
			inbox = new Inbox();
			inbox.setReceiverId(eachReceiverid);
			inbox.setEsrMapId(esrMapId);
			inbox.setSubject(subject);
			inbox.setAssignedTo(0);
			inbox.setRaisedBy(1);
			inbox.setCreationDatetime(new Date());
			inbox.setStatus(status);
			inbox.setCategory("RETENTION_BONUS_REPORT");
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setMessageBody(msgBody);
			inboxDao.insert(inbox);
		}
	}
	
	private String sendMail(Integer[] toIds, Integer[] ccIds, String by) throws FileNotFoundException, 
	ProfileInfoDaoException, AddressException, UnsupportedEncodingException, MessagingException, /*RetentionDepPerdiemDaoException*/DepPerdiemDaoException {
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
		int val =Calendar.getInstance().get(Calendar.MONTH)+1; 
		String month=findStringFormatofMonth(val);
	    String msgBody = "";
		PortalMail portalMail = new PortalMail();
		portalMail.setTemplateName(MailSettings.RETENTION_BONUS_REPORT_AUTO_GENERATED);
		if (by != null){
			portalMail.setMailSubject("Retention Bonus Report Re-Generated for " + month +"  "+year+ " by " + by);
			portalMail.setMessageBody("The Retention Bonus report for the month " + month +"  "+year+ " has been re-generated by " + by);
		} else{
			portalMail.setMailSubject("Retention Bonus Report Generated for " + month);
			portalMail.setMessageBody("The Retention Bonus Report for the month " + month + " has been generated and is awaiting your action. Please do the needful.");
		}
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
			if (toIds != null && toIds.length > 0){
				portalMail.setAllReceipientMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE PI.ID IN(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID IN(" + ModelUtiility.getCommaSeparetedValues(toIds) + "))"));
			}
			if (ccIds != null && ccIds.length > 0){
				portalMail.setAllReceipientcCMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE PI.ID IN(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID IN(" + ModelUtiility.getCommaSeparetedValues(ccIds) + "))"));
			}
			mailGenerator.invoker(portalMail);
		}
		return msgBody;
	}
	
	private void insertReports(List<RetentionBonusReconciliationReport> list, RetentionBonusReconciliation details, int bonusId) throws 
	NumberFormatException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,/* RetentionDepPerdiemHistoryDaoException*/DepPerdiemHistoryDaoException,
	ProfileInfoDaoException, CurrencyDaoException, ProjectMappingDaoException, ProjectDaoException, 
	RetentionBonusReconciliationReportDaoException, SQLException {
		/* dateRelatedData [] = new Integer[]{year,presentDate,lastDateOfThisMonth}; */
		Connection conn = null;
	    conn = ResourceManager.getConnection();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		RetentionBonusReconciliationReportDao bonusReportDao = RetentionBonusReconciliationReportDaoFactory.create(conn);
		
		for (RetentionBonusReconciliationReport report : list){
			try{
				ProfileInfo[] userProfileInfo = profileInfoDao.findByDynamicWhere("ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[] { report.getUserId() });
				String client = null;
				double total=0;
				if (report.getClientName() == null){
					Client[] clients = ClientDaoFactory.create().findByDynamicSelect("SELECT C.* FROM CLIENT C JOIN PROJ_CLIENT_MAP PCM ON PCM.CLIENT_ID= C.ID JOIN PROJECT_MAPPING P ON  P.PROJECT_ID= PCM.PROJ_ID  WHERE USER_ID=? ORDER BY P.ID DESC LIMIT 0,1", new Object[] { report.getUserId() });
					if (clients != null && clients.length > 0){
						client = clients[0].getName();
					} else{
						client = "Diksha";
						logger.error("Retention bonus report : emp_name=" + userProfileInfo[0].getFirstName() + " is not associated with any client, bonus_id=" + bonusId);
					}
				} else client = report.getClientName();
				/*double qAmount = Double.parseDouble(report.getqBonus())/4;
				double cAmount = Double.parseDouble(report.getcBonus());
				if (qAmount <= 0 && cAmount<=0){
					logger.info("retention bonus amount is 0 for " + userProfileInfo[0].getFirstName() + ". skipping this entry from reports.");
					continue;
				}
				report.setqAmount(qAmount+"");
				report.setcAmount(cAmount + "");*/
				double rAmount = Double.parseDouble(report.getrBonus());
				
			if (report.getCurrencyType() == 1){
				//report.setqAmountInr(report.getqAmount());
				//report.setcAmountInr(report.getcAmount());
				report.setqAmountInr(report.getrBonus());
				}
			List<Object> BonusTerm = new ArrayList<Object>();
			 BonusTerm = JDBCUtiility.getInstance().getSingleColumn("SELECT RETENTION_INSTALLMENTS FROM RETENTION_BONUS WHERE USER_ID=?",new Object[] {report.getUserId()} );
			
			if(BonusTerm.get(0).equals(1) || BonusTerm.get(0) == " " || BonusTerm.get(0).equals(0)){
				rAmount = Math.round(Double.parseDouble(report.getrBonus())/12);
			}else if(BonusTerm.get(0).equals(2)){
				rAmount = Math.round(Double.parseDouble(report.getrBonus())/4);
			}else if(BonusTerm.get(0).equals(3)){
				rAmount = Math.round(Double.parseDouble(report.getrBonus())/2);
			}else{
				rAmount = Math.round(Double.parseDouble(report.getrBonus()));
			}
			
					// total amount needs to calculate 
			/*if(report.getMonth().equalsIgnoreCase("MARCH")) total+=qAmount+cAmount;
			else total+= qAmount;*/
			total+= rAmount;
			      report.setTotal(total+"");
				  report.setData(bonusId, userProfileInfo[0].getReportingMgr(), ModelUtiility.getInstance().getEmployeeName(userProfileInfo[0].getReportingMgr()), client);
				 			
		  // finding number of TotalQuater Days,project days and Global Bench Days
				int globalBenchDays=0;
		        int quatertotalDays=findtotaldays(report.getMonth());  // Finding Total Days of that Quater
		        int projectdays =findProjectDays(report.getUserId(),conn,report.getMonth()); // Finding Project Days of Bonus Quater	
		  if(projectdays>0)   
				globalBenchDays= quatertotalDays-projectdays;
		  else{
			    projectdays=0;
				globalBenchDays= quatertotalDays;
			     }
		report.setProjectDays(projectdays);
		report.setGlobalBenchDays(globalBenchDays);
		report.setTotalDays(quatertotalDays);
		
		bonusReportDao.insert(report);
		
			}
	catch (Exception e){
				e.printStackTrace();
	}
		}	
	}
	
	private void insertIntoBonusMaster(String month,int year){
		RetentionBonusMasterDataDao bonusMasterDao=RetentionBonusMasterDataDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		Users[] users;
		
		try{
			Connection conn = null;
		    conn = ResourceManager.getConnection();
		    SalaryDetailsDao salDetail=SalaryDetailsDaoFactory.create(conn);
			users = usersDao.findByDynamicWhere("STATUS=? AND EMP_ID>0 AND ID>3", new Object[] { 0 });
			/**
			 * Retention Bonus
			 */
			if (users != null && users.length > 0){
				for (Users eachUser : users){
				String rBonus=null;
				Date rDate=null;
				int	userId = eachUser.getId();
				int BonusTerm = 0;
				String FirstTerm = null;
				String SecondTerm = null;
				String ThirdTerm = null;
				String FourthTerm = null;
				
				//SalaryDetails[] finalsalary=salDetail.findByDynamicSelect("SELECT * FROM SALARY_DETAILS WHERE USER_ID = ? ORDER BY USER_ID", new Object[] {  new Integer(userId)} );
			RetentionBonusDao retentionData = RetentionBonusDaoFactory.create(conn);
			 RetentionBonus[] RetentionData = retentionData.findWhereUserIdEquals(userId);
			for( RetentionBonus Retentiondata :  RetentionData){
				rBonus = Retentiondata.getAmount();
				rDate = Retentiondata.getRetentionStartDate();
				String[] RetentionStartDate = rDate.toString().split("-");
				BonusTerm = Retentiondata.getRetentionInstallments();
				//System.out.println(RetentionStartDate[1]);
				if(RetentionStartDate[1].equalsIgnoreCase("01")){
					//Month = cal.get(Calendar.MONTH);
					int MonthFirstTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthFirstTerm = (MonthFirstTerm+3/3)+1;
					FirstTerm = findStringFormatofMonth(MonthFirstTerm);
					
					int MonthSecondTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthSecondTerm = (MonthSecondTerm+12/3)+1;
					SecondTerm = findStringFormatofMonth(MonthSecondTerm);
					
					int MonthThirdTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthThirdTerm = (MonthThirdTerm+21/3)+1;
					ThirdTerm = findStringFormatofMonth(MonthThirdTerm);
					
					int MonthFourthTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthFourthTerm = (MonthFourthTerm+30/3)+1;
					FourthTerm = findStringFormatofMonth(MonthFourthTerm);
					
				}else if(RetentionStartDate[1].equalsIgnoreCase("02") || RetentionStartDate[1].equalsIgnoreCase("03") ||RetentionStartDate[1].equalsIgnoreCase("04")){
					int MonthFirstTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthFirstTerm = (MonthFirstTerm+3/3)+1;
					FirstTerm = findStringFormatofMonth(MonthFirstTerm);
					
					int MonthSecondTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthSecondTerm = (MonthSecondTerm+12/3)+1;
					SecondTerm = findStringFormatofMonth(MonthSecondTerm);
					
					int MonthThirdTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthThirdTerm = (MonthThirdTerm+21/3)+1;
					ThirdTerm = findStringFormatofMonth(MonthThirdTerm);
					
					int MonthFourthTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthFourthTerm = (MonthFourthTerm-6/3)+1;
					FourthTerm = findStringFormatofMonth(MonthFourthTerm);
					
				}else if(RetentionStartDate[1].equalsIgnoreCase("05") || RetentionStartDate[1].equalsIgnoreCase("06") ||RetentionStartDate[1].equalsIgnoreCase("07")){
					int MonthFirstTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthFirstTerm = (MonthFirstTerm+3/3)+1;
					FirstTerm = findStringFormatofMonth(MonthFirstTerm);
					
					int MonthSecondTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthSecondTerm = (MonthSecondTerm+12/3)+1;
					SecondTerm = findStringFormatofMonth(MonthSecondTerm);
					
					int MonthThirdTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthThirdTerm = (MonthThirdTerm-15/3)+1;
					ThirdTerm = findStringFormatofMonth(MonthThirdTerm);
					
					int MonthFourthTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthFourthTerm = (MonthFourthTerm-6/3)+1;
					FourthTerm = findStringFormatofMonth(MonthFourthTerm);
					
				}else if(RetentionStartDate[1].equalsIgnoreCase("08") || RetentionStartDate[1].equalsIgnoreCase("09")){ //||RetentionStartDate[1].equalsIgnoreCase("10")
					int MonthFirstTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthFirstTerm = (MonthFirstTerm+3/3)+1;
					FirstTerm = findStringFormatofMonth(MonthFirstTerm);
					
					int MonthSecondTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthSecondTerm = (MonthSecondTerm-24/3)+1;
					SecondTerm = findStringFormatofMonth(MonthSecondTerm);
					
					int MonthThirdTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthThirdTerm = (MonthThirdTerm-15/3)+1;
					ThirdTerm = findStringFormatofMonth(MonthThirdTerm);
					
					int MonthFourthTerm = (Integer.parseInt(RetentionStartDate[1].substring(1)));
					MonthFourthTerm = (MonthFourthTerm-6/3)+1;
					FourthTerm = findStringFormatofMonth(MonthFourthTerm);
					
				}else if(RetentionStartDate[1].equalsIgnoreCase("10")){
					int MonthFirstTerm = (Integer.parseInt(RetentionStartDate[1]));
					MonthFirstTerm = (MonthFirstTerm+3/3)+1;
					FirstTerm = findStringFormatofMonth(MonthFirstTerm);
					
					int MonthSecondTerm = (Integer.parseInt(RetentionStartDate[1]));
					MonthSecondTerm = (MonthSecondTerm-24/3)+1;
					SecondTerm = findStringFormatofMonth(MonthSecondTerm);
					
					int MonthThirdTerm = (Integer.parseInt(RetentionStartDate[1]));
					MonthThirdTerm = (MonthThirdTerm-15/3)+1;
					ThirdTerm = findStringFormatofMonth(MonthThirdTerm);
					
					int MonthFourthTerm = (Integer.parseInt(RetentionStartDate[1]));
					MonthFourthTerm = (MonthFourthTerm-6/3)+1;
					FourthTerm = findStringFormatofMonth(MonthFourthTerm);
				}
				
				
				else{
					int MonthFirstTerm = (Integer.parseInt(RetentionStartDate[1]));
					MonthFirstTerm = (MonthFirstTerm-33/3)+1;
					FirstTerm = findStringFormatofMonth(MonthFirstTerm);
					
					int MonthSecondTerm = (Integer.parseInt(RetentionStartDate[1]));
					MonthSecondTerm = (MonthSecondTerm-24/3)+1;
					SecondTerm = findStringFormatofMonth(MonthSecondTerm);
					
					int MonthThirdTerm = (Integer.parseInt(RetentionStartDate[1]));
					MonthThirdTerm = (MonthThirdTerm-15/3)+1;
					ThirdTerm = findStringFormatofMonth(MonthThirdTerm);
					
					int MonthFourthTerm = (Integer.parseInt(RetentionStartDate[1]));
					MonthFourthTerm = (MonthFourthTerm-6/3)+1;
					FourthTerm = findStringFormatofMonth(MonthFourthTerm);
					
				}
			if(month.equalsIgnoreCase(FirstTerm) ||month.equalsIgnoreCase(SecondTerm) ||month.equalsIgnoreCase(ThirdTerm) ||month.equalsIgnoreCase(FourthTerm) || BonusTerm == 1 && Integer.parseInt(RetentionStartDate[0]) <= year ){	
				RetentionBonusMasterData bonus=new RetentionBonusMasterData(); 
					bonus.setUserId(userId);
					bonus.setCurrencyType(1);
					bonus.setrBonus(rBonus+"");
					bonus.setMonth(month);
					bonus.setYear(year);
					bonusMasterDao.insert(bonus);
			}
		 }// for loop end
		}//for loop end
		}// if condition end
		} catch (UsersDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	 /*catch (SalaryDetailsDaoException e){
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
		catch (RetentionBonusMasterDataDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	catch (SQLException e){
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (RetentionBonusDaoException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BonusRecReportGeneratorAndNotifier autoTrigger = new BonusRecReportGeneratorAndNotifier();
		autoTrigger.generateReportAndNotify();
	}
	
	public int findtotaldays(String month)
	 {
		int totalDays=0;
		Calendar aCalendar = Calendar.getInstance();
		Date firstDateOfBonusMonth=null;
		Date lastDateOfBonusMonth=null;
		
		if(month.equalsIgnoreCase("JANUARY")){
			aCalendar.set(Calendar.MONTH,0);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstDateOfBonusMonth = aCalendar.getTime();
			aCalendar.set(Calendar.MONTH,1);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastDateOfBonusMonth = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("FEBRUARY")){
			aCalendar.set(Calendar.MONTH,1);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstDateOfBonusMonth = aCalendar.getTime();
			aCalendar.set(Calendar.MONTH,2);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastDateOfBonusMonth = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("MARCH")){
			aCalendar.set(Calendar.MONTH,2);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstDateOfBonusMonth = aCalendar.getTime();
			aCalendar.set(Calendar.MONTH,3);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastDateOfBonusMonth = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("APRIL")){
			aCalendar.set(Calendar.MONTH,3);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstDateOfBonusMonth = aCalendar.getTime();
			aCalendar.set(Calendar.MONTH,4);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastDateOfBonusMonth = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("MAY")){
			aCalendar.set(Calendar.MONTH,4);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstDateOfBonusMonth = aCalendar.getTime();
			aCalendar.set(Calendar.MONTH,5);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastDateOfBonusMonth = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("JUNE")){
			aCalendar.set(Calendar.MONTH,5);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstDateOfBonusMonth = aCalendar.getTime();
			aCalendar.set(Calendar.MONTH,6);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastDateOfBonusMonth = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("JULY")){
			aCalendar.set(Calendar.MONTH,6);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstDateOfBonusMonth = aCalendar.getTime();
			aCalendar.set(Calendar.MONTH,7);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastDateOfBonusMonth = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("AUGUST")){
			aCalendar.set(Calendar.MONTH,7);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstDateOfBonusMonth = aCalendar.getTime();
			aCalendar.set(Calendar.MONTH,8);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastDateOfBonusMonth = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("SEPTEMBER")){
			aCalendar.set(Calendar.MONTH,8);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstDateOfBonusMonth = aCalendar.getTime();
			aCalendar.set(Calendar.MONTH,9);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastDateOfBonusMonth = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("OCTOBER")){
			aCalendar.set(Calendar.MONTH,9);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstDateOfBonusMonth = aCalendar.getTime();
			aCalendar.set(Calendar.MONTH,10);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastDateOfBonusMonth = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("NOVEMBER")){
			aCalendar.set(Calendar.MONTH,10);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstDateOfBonusMonth = aCalendar.getTime();
			aCalendar.set(Calendar.MONTH,11);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastDateOfBonusMonth = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("DECEMBER")){
			aCalendar.set(Calendar.MONTH,11);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstDateOfBonusMonth = aCalendar.getTime();
			aCalendar.set(Calendar.MONTH,12);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastDateOfBonusMonth = aCalendar.getTime();
		}
		
		
		long diff = lastDateOfBonusMonth.getTime() - firstDateOfBonusMonth.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		totalDays=(int)diffDays;
		 return totalDays;
	 }
	
	public int findProjectDays(int userId,Connection conn,String month) throws ParseException, RollOnDaoException{
		int projectDays=0;
		RollOn[] roll=null;
		roll=RollOnDaoFactory.create(conn).findByDynamicSelect("SELECT * FROM ROLL_ON RO JOIN USERS U ON RO.EMP_ID=U.ID JOIN ROLL_ON_PROJ_MAP RP ON RO.ID=RP.ROLL_ON_ID JOIN PROJECT PR ON RP.PROJ_ID=PR.ID AND CURRENT=1 WHERE U.ID=? AND U.ID > 3 AND U.EMP_ID>0", new Object[] { userId});		
		for (RollOn rolls : roll){
			
			Date projStartDate=rolls.getStartDate();
			Date projEndDate=rolls.getEndDate();
			int daysCount=0;
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			// Finding First Date and Last Date of Bonus Quater
				Date quaterFirstDate=findfirstBonusDate(month);
				Date quaterLastDate=findLastBonusDate(month);
		
	if(projStartDate!=null){
			if(projStartDate.before(quaterFirstDate)){
				String startDate=df.format(quaterFirstDate);
			    Date d1 =df.parse(startDate);
			    if(projEndDate==null){
			    	long diff = quaterLastDate.getTime() - d1.getTime();
					long diffDays = diff / (24 * 60 * 60 * 1000);	
					daysCount=(int)diffDays;
			    }else{
			    	String endDate=df.format(projEndDate);
			    	 Date d2 =df.parse(endDate);
			    	long diff = d2.getTime() - d1.getTime();
			    	long diffDays = diff / (24 * 60 * 60 * 1000);	
			    	daysCount=(int)diffDays;}
			   }
			else if(projStartDate.after(quaterFirstDate)){	
			    String startDate=df.format(projStartDate);
		        Date d1 =df.parse(startDate);
		        if(projEndDate==null){
			    	long diff = quaterLastDate.getTime() - d1.getTime();
					long diffDays = diff / (24 * 60 * 60 * 1000);	
					daysCount=(int)diffDays;
			    }else{
			    	String endDate=df.format(projEndDate);
			    	Date d2 =df.parse(endDate);
			    	long diff = d2.getTime() - d1.getTime();
			    	long diffDays = diff / (24 * 60 * 60 * 1000);	
			    	daysCount=(int)diffDays;}
			   }
			projectDays=daysCount;
	}	
	
	}
		return projectDays;	
	}
	
	
	public Date findfirstBonusDate(String month){
		Date firstBonusDate=null;
		Calendar aCalendar = Calendar.getInstance();
		
		if(month.equalsIgnoreCase("JANUARY")){
			aCalendar.set(Calendar.MONTH,0);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("FEBRUARY")){
			aCalendar.set(Calendar.MONTH,1);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("MARCH")){
			aCalendar.set(Calendar.MONTH,2);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("APRIL")){
			aCalendar.set(Calendar.MONTH,3);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("MAY")){
			aCalendar.set(Calendar.MONTH,4);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("JUNE")){
			aCalendar.set(Calendar.MONTH,5);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("JULY")){
			aCalendar.set(Calendar.MONTH,6);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("AUGUST")){
			aCalendar.set(Calendar.MONTH,7);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("SEPTEMBER")){
			aCalendar.set(Calendar.MONTH,8);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("OCTOBER")){
			aCalendar.set(Calendar.MONTH,9);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("NOVEMBER")){
			aCalendar.set(Calendar.MONTH,10);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("DECEMBER")){
			aCalendar.set(Calendar.MONTH,11);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, 0);
			firstBonusDate = aCalendar.getTime();
		}
		
		return firstBonusDate;
	}
	
	public Date findLastBonusDate(String month){
		Date lastBonusDate=null;
		Calendar aCalendar = Calendar.getInstance();
		
		if(month.equalsIgnoreCase("JANUARY")){
			aCalendar.set(Calendar.MONTH,1);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("FEBRUARY")){
			aCalendar.set(Calendar.MONTH,2);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("MARCH")){
			aCalendar.set(Calendar.MONTH,3);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
		lastBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("APRIL")){
			aCalendar.set(Calendar.MONTH,4);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("MAY")){
			aCalendar.set(Calendar.MONTH,5);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("JUNE")){
			aCalendar.set(Calendar.MONTH,6);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("JULY")){
			aCalendar.set(Calendar.MONTH,7);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("AUGUST")){
			aCalendar.set(Calendar.MONTH,8);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("SEPTEMBER")){
			aCalendar.set(Calendar.MONTH,9);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("OCTOBER")){
			aCalendar.set(Calendar.MONTH,10);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("NOVEMBER")){
			aCalendar.set(Calendar.MONTH,11);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastBonusDate = aCalendar.getTime();
		}else if(month.equalsIgnoreCase("DECEMBER")){
			aCalendar.set(Calendar.MONTH,12);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastBonusDate = aCalendar.getTime();
		}
	 return lastBonusDate;
	}
}
