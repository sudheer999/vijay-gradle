package com.dikshatech.portal.dispatcher;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.CandidateChecklistDocsDao;
import com.dikshatech.portal.dao.CandidateChecklistStatusDao;
import com.dikshatech.portal.dao.CandidateDao;
import com.dikshatech.portal.dao.ExitEmployeeDao;
import com.dikshatech.portal.dao.LeaveMasterDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.CandidateChecklistDocs;
import com.dikshatech.portal.dto.CandidateChecklistStatus;
import com.dikshatech.portal.dto.CandidateChecklistStatusPk;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.ExitEmployee;
import com.dikshatech.portal.dto.LeaveMaster;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.factory.CandidateChecklistDocsDaoFactory;
import com.dikshatech.portal.factory.CandidateChecklistStatusDaoFactory;
import com.dikshatech.portal.factory.CandidateDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.ExitEmployeeDaoFactory;
import com.dikshatech.portal.factory.LeaveMasterDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.models.CandidateModel;
import com.dikshatech.portal.models.LeaveModel;

/**
 * Servlet implementation class Validate
 */
public class Validate extends HttpServlet {

	private static final long	serialVersionUID	= 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Validate() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) {
		try{
			String param = request.getParameter("cId");
			String type = request.getParameter("type");
			String paramEmp = request.getParameter("eId");
			if (param != null||paramEmp!=null){
				if (type!=null && type.equalsIgnoreCase("LEAVE")){
					aprroveLeave(request, response);
				} 
				else  if (type!=null && type.equalsIgnoreCase("anniversary")){
					
					if (paramEmp != null){
					sendEmploymentJobAnniversaryMailForERS(request, response);
					String statusERS = (request.getParameter("sId"));
					//		String redirectURL = "";
							 if (statusERS.equals("4")){
								 String redirectURL = "showMessage.jsp?TYPE=" + Integer.parseInt(statusERS);
								 response.sendRedirect("./pages/" + redirectURL);
							 }
					
					}
				}
				
				else{
					boolean reject = false;
					PortalForm form = null;
					String validateKey = request.getParameter("uuid");
					String status = request.getParameter("sId");
					reject = Boolean.parseBoolean(request.getParameter("Reject"));
					if (!reject){
						status = "2";
					}
					String redirectURL = "";
					CandidateModel cModel = new CandidateModel();
					CandidateDao cDao = CandidateDaoFactory.create();
					Candidate candidate = cDao.findByPrimaryKey(Integer.parseInt(param));
					if (candidate != null && candidate.getUuid() != null){
						if (candidate.getUuid().equals(validateKey)){
							candidate.setuType("ACCEPTREJECTOFFER");
							/**
							 * Offer Accepted
							 */
							if (status.equals("2")){
								candidate.setIsAccepted(Integer.parseInt(status));
								redirectURL = "offerAccept.jsp";
								form = (Candidate) candidate;
								cModel.update(form, request);
								
								//prepare candidate check-list
								CandidateChecklistStatusDao checklistStatusDao = CandidateChecklistStatusDaoFactory.create();
								CandidateChecklistDocsDao checklistDocsDao = CandidateChecklistDocsDaoFactory.create();
								
								//check and update if he had already accepted the offer letter before
								CandidateChecklistStatus[] checkList = checklistStatusDao.findWhereCandIdEquals(candidate.getId());
								if(checkList!=null && checkList.length>0){
									for(CandidateChecklistStatus eachChecklist : checkList){
										eachChecklist.setIsUploaded(0);
										eachChecklist.setStatus("Not-Uploaded");
										checklistStatusDao.update(new CandidateChecklistStatusPk(eachChecklist.getId()), eachChecklist);
									}
								}else{
									//else insert
									CandidateChecklistDocs[] checklistDocs = checklistDocsDao.findAll();
									CandidateChecklistStatus checkListStatus;
									for(CandidateChecklistDocs eachDoc : checklistDocs){
										checkListStatus = new CandidateChecklistStatus();
										checkListStatus.setCandId(candidate.getId());
										checkListStatus.setChklstDocId(eachDoc.getId());
										checkListStatus.setIsUploaded(0);
										checkListStatus.setStatus("Not-Uploaded");
										checklistStatusDao.insert(checkListStatus);
									}
								}
								
								response.sendRedirect("./pages/" + redirectURL);
							}
							/**
							 * Offer Rejected
							 */
							else if (status.equals("3")){
								if (reject){
									candidate.setIsAccepted(Integer.parseInt(status));
									candidate.setComments(request.getParameter("comments") == null ? "" : request.getParameter("comments"));
									form = (Candidate) candidate;
									cModel.update(form, request);
									redirectURL = "offerReject.jsp";
									response.sendRedirect("./pages/" + redirectURL);
								} else{
									redirectURL = "aboutDiksha.html";
									response.sendRedirect("./pages/" + redirectURL);
								}
							}
						}
					} else{
						String statusString = candidate.getStatus() == 2 ? "Accepted" : "Rejected";
						redirectURL = "actionTaken.jsp?statusString=" + statusString;
						response.sendRedirect("./pages/" + redirectURL);
					}
				}
			}
			
			
		
		
		} catch (Exception e){
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	private void aprroveLeave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String validateKey = request.getParameter("uuid");
		String status = request.getParameter("sId");
		String param = request.getParameter("cId");
		Integer assignedTo = Integer.parseInt(request.getParameter("assignedTo"));
		LeaveMasterDao lDao = LeaveMasterDaoFactory.create();;
		LeaveMaster leaveMaster[] = lDao.findWhereUuidEquals(validateKey);
		LeaveMaster leaMaster = new LeaveMaster();
		if (leaveMaster != null && leaveMaster.length == 1 && Integer.parseInt(param) == leaveMaster[0].getId()){
			leaMaster.setLeavemasterIds(new Integer[] { leaveMaster[0].getId() });
			leaMaster.setuType("APPROVEREJECTCANCELL");
			leaMaster.setAssignedTo(assignedTo);
			leaMaster.setComment(request.getParameter("comments") == null ? "" : request.getParameter("comments"));
			leaMaster.setRemark(leaMaster.getComment());
			leaMaster.setIsActionType(Integer.parseInt(status));
			LeaveModel lm = (LeaveModel) new PortalForm().getComponentObject("LEAVE");
			ActionResult result = lm.update(leaMaster, request);
			if (result != null && result.getActionMessages() != null && !result.getActionMessages().isEmpty()){
				response.getWriter().println("Sorry we are unble to process.");
				response.getWriter().println("CAUSE: " + result.getActionMessages());
			} else{
				String redirectURL = "showMessage.jsp?TYPE=" + Integer.parseInt(status);
				response.sendRedirect("./pages/" + redirectURL);
			}
		} else{
			String redirectURL = "showMessage.jsp?TYPE=3";
			response.sendRedirect("./pages/" + redirectURL);
		}
	}

protected void serviceRmg(HttpServletRequest request, HttpServletResponse response) {
	try{
		String param = request.getParameter("eId");
		String type = request.getParameter("type");
		if (param != null){
			if (type!=null && type.equalsIgnoreCase("anniversary")){
				sendEmploymentJobAnniversaryMailForERS(request, response);
				
			}
		}
}
	catch (Exception e){
		e.printStackTrace();
		//logger.error("unable to send Employment Job Anniversary Reminder mail for " + region.getRegName(), e);
	}
	}


private void sendEmploymentJobAnniversaryMailForERS(HttpServletRequest request, HttpServletResponse response) throws Exception {
	// TODO Auto-generated method stub

	try{
		//SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = PortalUtility.formateDateTimeToDateyyyyMMdd(new Date());
		String CurrentDate =  PortalUtility.getdd_MM_yyyy(new Date());
		Date today;
		String CurrentDateM;
		DateFormat dateFormat;
 
		dateFormat = DateFormat
				.getDateInstance(DateFormat.LONG);
		today = new Date();
		CurrentDateM = dateFormat.format(today);
 
		System.out.println(CurrentDateM);


		
		
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
		ProfileInfo[] eeUm = null;
		ProfileInfoDao profileInfoDaoJ = ProfileInfoDaoFactory.create();
			try{
				
				ProfileInfo profileInfos2[] = profileInfoDao.findWhereDateOfJoiningEqualsCurremtDate(currentMonth,currentDate);

				for(ProfileInfo profileInfo2 : profileInfos2){
					String validateKey = request.getParameter("uuid");
					String status = request.getParameter("sId");
					int a = profileInfo2.getId();
					eeUm = profileInfoDaoJ.findByUserId("SELECT EMP_ID FROM USERS where PROFILE_ID  = ("+a+") ",new Object[] {});
					for(ProfileInfo eUm:eeUm)
					{
						int id = eUm.getId();
					
					String paramEmp = request.getParameter("eId");
					if(id==  Integer.parseInt(paramEmp))
					{
					
					
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
						int yearofdifference = (YearDifference);
						if((currentDate.equals(joiningDate) && currentMonth.equals(joiningMonth) || currentDate == joiningDate && currentMonth == joiningMonth
							|| JoiningDPart[0].equals(CurrentD[0]) && JoiningDPart[1].equals(CurrentD[1])) && (Integer.parseInt(currentYear) > Integer.parseInt(joiningYear))){
							pMail.setAllReceipientMailId(allReceipientcCMailId.toArray(new String[allReceipientcCMailId.size()]));
							long year = PortalUtility.getCurrentyear();
							pMail.setMailSubject("Diksha : RMG : " + year + " : Congratulations");
							
							pMail.setCurrentDate(CurrentDateM);
							pMail.setEmployeeName(profileInfo2.getFirstName());
							pMail.setEmpPeriod(yearofdifference);
						
						
							if(pMail.getEmpPeriod()==1)
							{
								pMail.setTemplateName(MailSettings.ANNIVERSARYJ_1);
							}
							if(pMail.getEmpPeriod()==2)
							{
								pMail.setTemplateName(MailSettings.ANNIVERSARYJ_2);
							}
							if(pMail.getEmpPeriod()==3)
							{
								pMail.setTemplateName(MailSettings.ANNIVERSARYJ_3);
							}
							if(pMail.getEmpPeriod()==4)
							{
								pMail.setTemplateName(MailSettings.ANNIVERSARYJ_4);
							}
							if(pMail.getEmpPeriod()==5)
							{
								pMail.setTemplateName(MailSettings.ANNIVERSARYJ_5);
							}
							if(pMail.getEmpPeriod()>5)
							{
								pMail.setTemplateName(MailSettings.ANNIVERSARY_M5);
								mailGenerator.invoker(pMail);
							}
							else
							{
								mailGenerator.invoker(pMail);
							}
							
							    
						
						}
					} catch (Exception e){
						e.printStackTrace();
					//	logger.error("unable to send Employment Job Anniversary Reminder mail : " + profileInfo2, e);
					}
						 
				}
				}
				}}
			} catch (Exception e){
				e.printStackTrace();
				//logger.error("unable to send Employment Job Anniversary Reminder mail for " + region.getRegName(), e);
			}
	//	}
	} catch (Exception e){
		e.printStackTrace();
	//	logger.error("unable to send Employment Job Anniversary Reminder mail", e);
	}

}
}


