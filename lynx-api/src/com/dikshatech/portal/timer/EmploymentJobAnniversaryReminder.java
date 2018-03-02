package com.dikshatech.portal.timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
public class EmploymentJobAnniversaryReminder implements Job {

	boolean					flag	= false;
	private static Logger	logger	= LoggerUtil.getLogger(EmploymentJobAnniversaryReminder.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		try{
			
			sendEmploymentJobAnniversaryMailRmg();
		} catch (Exception e){
			logger.debug("error in Employment Job Anniversary Reminder scheduler.....");
			e.printStackTrace();
		}
	}

	
	
	@SuppressWarnings("unused")
	private void sendEmploymentJobAnniversaryMail() {

		try{
			//SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = PortalUtility.formateDateTimeToDateyyyyMMdd(new Date());
			String CurrentDate =  PortalUtility.getdd_MM_yyyy(new Date());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String[] CurrentD = CurrentDate.split("-");
			String currentDate = CurrentD[0];
			String currentMonth = CurrentD[1];
			String currentYear = CurrentD[2];
			MailGenerator mailGenerator = new MailGenerator();
			PortalMail pMail = new PortalMail();
		//	UsersDao usersDao = UsersDaoFactory.create();
			LevelsDao levelsDao = LevelsDaoFactory.create();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		//	Regions[] regions1 = RegionsDaoFactory.create().findAll();
			Users[] u =null;
			UsersDao uIdao = UsersDaoFactory.create();
		/*	for (Regions region1 : regions1){
				Notification notifi1 = new Notification(region1.getRefAbbreviation());
				int rmgdept = notifi1.hrdId;*/
				//List<String> rmgEmails = UserModel.getUsersByDivision(rmgdept, null);
				try{
					ProfileInfo profileInfos2[] = profileInfoDao.findWhereDateOfJoiningEqualsCurremtDate(currentMonth,currentDate);
	
					for(ProfileInfo profileInfo2 : profileInfos2){
						if(profileInfo2.getDateOfSeperation() == null){
							 
						try{
							Levels levels = levelsDao.findByPrimaryKey(profileInfo2.getLevelId());
							Divison div = DivisonDaoFactory.create().findByPrimaryKey(levels.getDivisionId());
							String division = "N.A";
							division = div.getName();
							
							
							String JoiningDate = PortalUtility.getdd_MM_yyyy(profileInfo2.getDateOfJoining());
							String[] JoiningDPart = JoiningDate.split("-");
							String joiningDate = JoiningDPart[0];
							String joiningMonth = JoiningDPart[1];
							String joiningYear = JoiningDPart[2];
							//Details of Employee Reporting Manager 
							List<String> allReceipientcCMailId = new ArrayList<String>();
							ProfileInfo[] ReportingManagerDetail = profileInfoDao.findWhereIdEquals(profileInfo2.getReportingMgr());
							ProfileInfo EmpReportingManagerDetail=(ProfileInfo)ReportingManagerDetail[0];
							allReceipientcCMailId.add(EmpReportingManagerDetail.getOfficalEmailId());
							//Detals of Employee SPOC details
							ProfileInfo[] SPOCDetail = profileInfoDao.findWhereIdEquals(profileInfo2.getHrSpoc());
							ProfileInfo EmpSPOCDetail=(ProfileInfo)SPOCDetail[0];
							allReceipientcCMailId.add(EmpSPOCDetail.getOfficalEmailId());
							allReceipientcCMailId.add(profileInfo2.getOfficalEmailId());
							//Differnece of Joining Year And Current Year
							int CurrentYear = Integer.parseInt(currentYear);
							int JoiningYear = Integer.parseInt(joiningYear);
							int YearDifference = CurrentYear-JoiningYear;
							String yearofdifference = NumberToWord(YearDifference);
							if((currentDate.equals(joiningDate) && currentMonth.equals(joiningMonth) || currentDate == joiningDate && currentMonth == joiningMonth
								|| JoiningDPart[0].equals(CurrentD[0]) && JoiningDPart[1].equals(CurrentD[1])) && (Integer.parseInt(currentYear) > Integer.parseInt(joiningYear))){
								
							//Users user = usersDao.findWhereProfileIdEquals(profileInfo2.getId())[0];
							//pMail.setAllReceipientcCMailId(rmgEmails.toArray(new String[rmgEmails.size()]));
					//		pMail.setRecipientMailId(EmpSPOCDetail.getOfficalEmailId());
							pMail.setAllReceipientMailId(allReceipientcCMailId.toArray(new String[allReceipientcCMailId.size()]));
							//pMail.setCandidateName(profileInfo2.getFirstName());
					//		pMail.setRecipientMailId(profileInfo2.getOfficalEmailId());
							pMail.setMailSubject("Diksha: Employment Job Anniversary Reminder - " + profileInfo2.getFirstName());
							//pMail.setCandidateName(profileInfo2.getFirstName());
							//pMail.setEmpId(String.valueOf(user.getEmpId()));//
							pMail.setEmployementPeriod(yearofdifference);
							pMail.setCurrentDate(CurrentDate);
							pMail.setEmployeeName(profileInfo2.getFirstName());
							
							u = uIdao.findWhereProfileIdEquals("SELECT * FROM USERS  WHERE PROFILE_ID= ("+profileInfo2.getId()+") ",new Object[] {});
							for(Users user:u)
							{
								pMail.setEmployeeId(user.getEmpId());
							}
							pMail.setEmpDivision(division);
							pMail.setEmpDesignation(levels.getDesignation());
							
							//pMail.setOfferedDesignation(levels.getDesignation());
							//pMail.setCandidateDOJ(PortalUtility.formatDateddMMyyyy(profileInfo2.getDateOfJoining()));
							pMail.setTemplateName(MailSettings.EMPLOYEE_JOB_ANNIVERSARY_REMINDER_CONFIRM);
							mailGenerator.invoker(pMail);
							
							}
						} catch (Exception e){
							e.printStackTrace();
							logger.error("unable to send Employment Job Anniversary Reminder mail : " + profileInfo2, e);
						}
							 
					}
					}
					
				} catch (Exception e){
					e.printStackTrace();
					//logger.error("unable to send Employment Job Anniversary Reminder mail for " + region.getRegName(), e);
				}
		//	}
		} catch (Exception e){
			e.printStackTrace();
			logger.error("unable to send Employment Job Anniversary Reminder mail", e);
		}
	
	}
	
	private void sendEmploymentJobAnniversaryMailRmg() {
		
		
		try{
			Date date = PortalUtility.formateDateTimeToDateyyyyMMdd(new Date());
			String CurrentDate =  PortalUtility.getdd_MM_yyyy(new Date());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String[] CurrentD = CurrentDate.split("-");
			String currentDate = CurrentD[0];
			String currentMonth = CurrentD[1];
			String currentYear = CurrentD[2];
			MailGenerator mailGenerator = new MailGenerator();
			PortalMail pMail = new PortalMail();
			LevelsDao levelsDao = LevelsDaoFactory.create();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			Users[] u =null;
			int id = 0;
			UsersDao uIdao = UsersDaoFactory.create();
			ProfileInfo[] eeUm = null;
			ProfileInfoDao profileInfoDaoJ = ProfileInfoDaoFactory.create();
				try{
					ProfileInfo profileInfos2[] = profileInfoDao.findWhereDateOfJoiningEqualsCurremtDate(currentMonth,currentDate);
					eeUm = profileInfoDaoJ.findByUserId("SELECT STATUS FROM USERS WHERE PROFILE_ID  = ("+profileInfos2[0].getId()+") ",new Object[] {});
					for(ProfileInfo eUm:eeUm)
					{
					 id = eUm.getId();
					}
					for(ProfileInfo profileInfo2 : profileInfos2){
						if(profileInfo2.getDateOfSeperation() == null&&id==0){
							 
						try{
							Levels levels = levelsDao.findByPrimaryKey(profileInfo2.getLevelId());
							Divison div = DivisonDaoFactory.create().findByPrimaryKey(levels.getDivisionId());
							String division = "N.A";
							division = div.getName();
							String JoiningDate = PortalUtility.getdd_MM_yyyy(profileInfo2.getDateOfJoining());
							String[] JoiningDPart = JoiningDate.split("-");
							String joiningDate = JoiningDPart[0];
							String joiningMonth = JoiningDPart[1];
							String joiningYear = JoiningDPart[2];
							List<String> allReceipientcCMailId = new ArrayList<String>();
							allReceipientcCMailId.add("rmg@dikshatech.com");					
			//				allReceipientcCMailId.add("vamsi.krishna@dikshatech.com");
							int CurrentYear = Integer.parseInt(currentYear);
							int JoiningYear = Integer.parseInt(joiningYear);
							int YearDifference = CurrentYear-JoiningYear;
							String yearofdifference = NumberToWord(YearDifference);
							if((currentDate.equals(joiningDate) && currentMonth.equals(joiningMonth) || currentDate .equals(joiningDate)  && currentMonth .equals(joiningMonth) 
								|| JoiningDPart[0].equals(CurrentD[0]) && JoiningDPart[1].equals(CurrentD[1])) && (Integer.parseInt(currentYear) > Integer.parseInt(joiningYear))){

							pMail.setAllReceipientMailId(allReceipientcCMailId.toArray(new String[allReceipientcCMailId.size()]));
					
							pMail.setMailSubject("Diksha: Employment Job Anniversary Reminder - " + profileInfo2.getFirstName());

							pMail.setEmployementPeriod(yearofdifference);
							pMail.setCurrentDate(CurrentDate);
							pMail.setEmployeeName(profileInfo2.getFirstName());
							
							u = uIdao.findWhereProfileIdEquals("SELECT * FROM USERS  WHERE PROFILE_ID= ("+profileInfo2.getId()+") ",new Object[] {});
							for(Users user:u)
							{
								pMail.setEmployeeId(user.getEmpId());
							}
							pMail.setEmpDivision(division);
							pMail.setEmpDesignation(levels.getDesignation());
							UUID uuid = UUID.randomUUID();
							pMail.setUuid(uuid.toString());
							String acceptLink = "validate?uuid=" + pMail.getUuid() + "&eId=" + pMail.getEmployeeId() + "&sId=4" + "&type=anniversary" ;
							pMail.setAcceptOfferLink(acceptLink);
							pMail.setServerId("http://lynx.dikshatech.com/DikshaPortalv2.0"); //live
//							pMail.setServerId("http://172.16.1.3:7013/DikshaPortalv2.0"); //test
							
							pMail.setTemplateName(MailSettings.EMPLOYEE_JOB_ANNIVERSARY_REMINDER_CONFIRM);
							mailGenerator.invoker(pMail);
							
							}
						} catch (Exception e){
							e.printStackTrace();
							logger.error("unable to send Employment Job Anniversary Reminder mail : " + profileInfo2, e);
						}
							 
					}
					}
					
				} catch (Exception e){
					e.printStackTrace();
					//logger.error("unable to send Employment Job Anniversary Reminder mail for " + region.getRegName(), e);
				}
		//	}
		} catch (Exception e){
			e.printStackTrace();
			logger.error("unable to send Employment Job Anniversary Reminder mail", e);
		}
	
	}


	
	private String NumberToWord(int yearDifference) {
		

	        if (yearDifference == 0) { return "zero"; }
	        
	        String prefix = "";
	        
	        /*if (number < 0) {
	            number = -number;
	            prefix = "negative";
	        }*/
	        
	        String current = "";
	        int place = 0;
	        
	        do {
	            int YearDifference = yearDifference % 1000;
	            if (YearDifference != 0){
	                String s = convertLessThanOneThousand(YearDifference);
	                current = s + specialNames[place] + current;
	            }
	            place++;
	            yearDifference /= 1000;
	        } while (yearDifference > 0);
	        
	        return (prefix + current).trim();
	    }

	private String convertLessThanOneThousand(int yearDifference) {

        String current;
        
        if (yearDifference % 100 < 20){
            current = numNames[yearDifference % 100];
            yearDifference /= 100;
        }
        else {
            current = numNames[yearDifference % 10];
            yearDifference /= 10;
            
            current = tensNames[yearDifference % 10] + current;
            yearDifference /= 10;
        }
        if (yearDifference == 0) return current;
        return numNames[yearDifference] + " hundred" + current;
	}
	 private static final String[] specialNames = {
	        "",
	        " thousand",
	        " million",
	        " billion",
	        " trillion",
	        " quadrillion",
	        " quintillion"
	    };
	    
	    private static final String[] tensNames = {
	        "",
	        " ten",
	        " twenty",
	        " thirty",
	        " forty",
	        " fifty",
	        " sixty",
	        " seventy",
	        " eighty",
	        " ninety"
	    };
	    
	    private static final String[] numNames = {
	        "",
	        " one",
	        " two",
	        " three",
	        " four",
	        " five",
	        " six",
	        " seven",
	        " eight",
	        " nine",
	        " ten",
	        " eleven",
	        " twelve",
	        " thirteen",
	        " fourteen",
	        " fifteen",
	        " sixteen",
	        " seventeen",
	        " eighteen",
	        " nineteen"
	    };
}