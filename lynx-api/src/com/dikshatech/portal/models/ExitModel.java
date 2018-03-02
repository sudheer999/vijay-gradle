package com.dikshatech.portal.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.ExitEmployeListBean;
import com.dikshatech.beans.ExitEmployeeBean;
import com.dikshatech.beans.ExitEmployeeInfo;
import com.dikshatech.beans.ExitNOCListBean;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.Status;
import com.dikshatech.common.utils.TDSUtility;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.ExitAdminNocDao;
import com.dikshatech.portal.dao.ExitClosureDao;
import com.dikshatech.portal.dao.ExitDeclarationDao;
import com.dikshatech.portal.dao.ExitEmpUsersMapDao;
import com.dikshatech.portal.dao.ExitEmployeeDao;
import com.dikshatech.portal.dao.ExitFinanceNocDao;
import com.dikshatech.portal.dao.ExitItNocDao;
import com.dikshatech.portal.dao.ExitQuestionnaireDao;
import com.dikshatech.portal.dao.ExitQuestionsMapDao;
import com.dikshatech.portal.dao.ExitWithdrawDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.ExitAdminNoc;
import com.dikshatech.portal.dto.ExitAdminNocPk;
import com.dikshatech.portal.dto.ExitClosure;
import com.dikshatech.portal.dto.ExitDeclaration;
import com.dikshatech.portal.dto.ExitEmpUsersMap;
import com.dikshatech.portal.dto.ExitEmployee;
import com.dikshatech.portal.dto.ExitEmployeePk;
import com.dikshatech.portal.dto.ExitFinanceNoc;
import com.dikshatech.portal.dto.ExitFinanceNocPk;
import com.dikshatech.portal.dto.ExitItNoc;
import com.dikshatech.portal.dto.ExitItNocPk;
import com.dikshatech.portal.dto.ExitQuestionnaire;
import com.dikshatech.portal.dto.ExitQuestionnairePk;
import com.dikshatech.portal.dto.ExitQuestionsMap;
import com.dikshatech.portal.dto.ExitRequest;
import com.dikshatech.portal.dto.ExitWithdraw;
import com.dikshatech.portal.dto.ExitWithdrawPk;
import com.dikshatech.portal.dto.Holidays;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.PoDetails;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.SkillSet;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.ExitAdminNocDaoFactory;
import com.dikshatech.portal.factory.ExitClosureDaoFactory;
import com.dikshatech.portal.factory.ExitDeclarationDaoFactory;
import com.dikshatech.portal.factory.ExitEmpUsersMapDaoFactory;
import com.dikshatech.portal.factory.ExitEmployeeDaoFactory;
import com.dikshatech.portal.factory.ExitFinanceNocDaoFactory;
import com.dikshatech.portal.factory.ExitItNocDaoFactory;
import com.dikshatech.portal.factory.ExitQuestionnaireDaoFactory;
import com.dikshatech.portal.factory.ExitQuestionsMapDaoFactory;
import com.dikshatech.portal.factory.ExitRequestDaoFactory;
import com.dikshatech.portal.factory.ExitWithdrawDaoFactory;
import com.dikshatech.portal.factory.HolidaysDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.PoDetailsDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.SkillSetDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.ExitQuestionsMapDaoImpl;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

/**
 * @author gurunath.rokkam
 */
public class ExitModel extends ActionMethods {

	private static final int	APPROVER			= 1;
	private static final int	HANDLER				= 2;
	private static final int	FINANCE				= 3;
	private static final int	IT					= 4;
	private static final int	ADMIN				= 5;
	private static final int	NOTIFIER			= 6;
	private static final String	NOC_FINANCE			= "FINANCE";
	private static final String	NOC_IT				= "IT";
	private static final String	NOC_ADMIN			= "ADMIN";
	private static final String	NOC_RMG				= "RMG";
	private static final int	PRIOR_DAYS_TO_NOC	= 2;
	private static ExitModel	exitModel			= null;
	private static Logger		logger				= LoggerUtil.getLogger(ExitModel.class);
	MailGenerator				mailGenarator		= new MailGenerator();

	private ExitModel() {};

	public enum NocType {
		FINANCE, IT, ADMIN;

		public static NocType getValue(String value) {
			return valueOf(value);
		};
	}

	public static ExitModel getInstance() {
		if (exitModel == null) exitModel = new ExitModel();
		return exitModel;
	}

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		int userId = login.getUserId();
		ExitEmployeeBean exitBean = (ExitEmployeeBean) form;
		try{
			switch (ExecuteTypes.getValue(form.geteType())) {
				case UPDATE:
					List<Integer> notifiers = getNotifiers(exitBean.getId());
					if (notifiers.contains(userId)){
						//ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
						UsersDao usersDao = UsersDaoFactory.create();
						ExitEmployeeDao exitEmployeeDao = ExitEmployeeDaoFactory.create();
						ExitEmployee exitEmp = exitEmployeeDao.findByPrimaryKey(exitBean.getId());
						long count = JDBCUtiility.getInstance().getRowCount("FROM PROFILE_INFO P JOIN USERS U ON U.PROFILE_ID=P.ID AND U.STATUS NOT IN (1, 2, 3) WHERE HR_SPOC=? OR REPORTING_MGR=?", new Object[] { exitEmp.getUserId(), exitEmp.getUserId() });
						if (count > 0 || exitEmp.getStatusId() != Status.getStatusId(Status.EXIT_FORMALITIES)){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.separation.error"));
							break;
						}
						exitEmp.setStatusId(Status.getStatusId(Status.SEPERATED));
						exitEmployeeDao.update(new ExitEmployeePk(exitEmp.getId()), exitEmp);
						//ProfileInfo userProfile = profileDao.findWhereUserIdEquals(exitEmp.getUserId());
						Users user = usersDao.findByPrimaryKey(exitEmp.getUserId());
						user.setStatus((short) 2);
						usersDao.update(user.createPk(), user);
						/*PortalMail pMail = new PortalMail();
						pMail.setMailSubject(userProfile.getFirstName() + " " + userProfile.getLastName() + "'s relieving confirmation");
						pMail.setTemplateName(MailSettings.EXIT_EMPLOYEE_RELIEVING);
						pMail.setSubmitDate(PortalUtility.getdd_MM_yyyy(new Date()));
						setEmployeeDetails(pMail, user, userProfile);
						List<Integer> cclist = new ArrayList<Integer>();
						cclist.addAll(getHandlers(exitEmp.getId()));
						cclist.addAll(getApprovers(exitEmp.getId()));
						cclist.addAll(notifiers);
						cclist.addAll(getITHandlers(exitEmp.getId()));
						cclist.addAll(getAdminHandlers(exitEmp.getId()));
						cclist.addAll(getFinanceHandlers(exitEmp.getId()));
						pMail.setRecipientMailId(userProfile.getOfficalEmailId());
						if (cclist.size() > 0) pMail.setAllReceipientcCMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(cclist) + ") AND STATUS NOT IN (1,2,3)) AND OFFICAL_EMAIL_ID IS NOT NULL"));
						mailGenarator.invoker(pMail);*/
					} else result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
					break;
				default:
					break;
			}
		} catch (Exception e){
			logger.error("", e);
		}
		return result;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		int userId = login.getUserId();
		ExitEmployeeDao exitEmployeeDao = ExitEmployeeDaoFactory.create();
		ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
		ExitEmployeeBean exitBean = (ExitEmployeeBean) form;
		switch (ReceiveTypes.getValue(form.getrType())) {
			case RECEIVEINFO:
				ExitEmployeeInfo eeinfo = null;
				try{
					ExitEmployee exitEmp = null;
					if (exitBean.getId() > 0){
						exitEmp = exitEmployeeDao.findByPrimaryKey(exitBean.getId());
						userId = exitEmp.getUserId();
					}
					ProfileInfo userProfile = profileDao.findWhereUserIdEquals(userId);
					List<Object> userNameList = JDBCUtiility.getInstance().getSingleColumn("SELECT EMP_ID FROM USERS WHERE ID=?", new Object[] { userId });
					eeinfo = new ExitEmployeeInfo(((Integer) userNameList.get(0)).intValue(), ModelUtiility.getInstance().getEmployeeName(userProfile.getReportingMgr()), userProfile.getNoticePeriod() + "");
					if (exitEmp != null){
						//if (exitEmp.getNoticePeriod() <= 0){
						exitEmp.setNoticePeriod(userProfile.getNoticePeriod() - exitEmp.getNoticePeriod());
						//}
						eeinfo.setObj(exitEmp);
						eeinfo.setNoticePeriod(null);
					}
				} catch (Exception e){
					logger.error("unable to receive user info.", e);
				}
				request.setAttribute("actionForm", eeinfo);
				break;
			case RECEIVEALL:
				ExitEmployeListBean list = new ExitEmployeListBean();
				try{
					String query = "SELECT E.ID, E.ESR_MAP_ID, (SELECT COUNT(*) FROM EXIT_EMP_USERS_MAP EM WHERE EM.EXIT_ID=E.ID AND TYPE=?) AS USER_ID, E.REASON, E.STATUS_ID, E.SUBMITTEDON, E.LAST_WORKING_DAY, E.BUY_BACK, ESRM.REQ_ID AS COMMENTS, E.EMPLOYEE_NOTE, E.NOTICE_PERIOD FROM EXIT_EMPLOYEE E JOIN EMP_SER_REQ_MAP ESRM ON ESRM.ID=E.ESR_MAP_ID AND E.USER_ID=?";
					list.setList(exitEmployeeDao.findByDynamicSelect(query, new Object[] { FINANCE, userId }));
					// check is already resigned or not and set the flag...
					list.setFalseToAllFields();
					if (list.getList() != null && list.getList().length > 0){
						for (Object obj : list.getList()){
							ExitEmployee entry = (ExitEmployee) obj;
							if (entry.getStatusId() != Status.getStatusId(Status.WITHDRAWN)) list.setIsResigned("true");
							if (entry.getStatusId() == Status.getStatusId(Status.REJECTEDBYRMG)) entry.setStatusId(Status.getStatusId(Status.PENDING_RMG));
							if (entry.getUserId() > 0) entry.setEnableNoc("true");
							else entry.setEnableNoc("false");
							entry.setUserId(userId);
							entry.setRequestId(entry.getComments());
							entry.setReason(entry.getReason().replace("\r", " ").replace("\r\n", " ").replace("\n", " "));
							entry.setComments(null);
						}
					}
					if (JDBCUtiility.getInstance().getRowCount("FROM EXIT_EMPLOYEE E JOIN EXIT_EMP_USERS_MAP EM ON E.ID=EM.EXIT_ID WHERE EM.USER_ID=? AND TYPE=?", new Object[] { userId, APPROVER }) > 0) list.setToApprove("true");
					if (JDBCUtiility.getInstance().getRowCount("FROM EXIT_EMPLOYEE E JOIN EXIT_EMP_USERS_MAP EM ON E.ID=EM.EXIT_ID WHERE EM.USER_ID=? AND TYPE=?", new Object[] { userId, NOTIFIER }) > 0) list.setToview("true");
					if (JDBCUtiility.getInstance().getRowCount("FROM EXIT_EMPLOYEE E JOIN EXIT_EMP_USERS_MAP EM ON E.ID=EM.EXIT_ID WHERE EM.USER_ID=? AND TYPE=?", new Object[] { userId, HANDLER }) > 0) list.setToHandle("true");
					if (JDBCUtiility.getInstance().getRowCount("FROM EXIT_EMPLOYEE E JOIN EXIT_EMP_USERS_MAP EM ON E.ID=EM.EXIT_ID WHERE EM.USER_ID=? AND TYPE IN (" + ADMIN + "," + IT + "," + FINANCE + ")", new Object[] { userId }) > 0) list.setToHandleNoc("true");
				} catch (Exception e){
					logger.error("unable to receive info.", e);
				}
				request.setAttribute("actionForm", list);
				break;
			case RECEIVEALLTOAPPROVE:
				ExitEmployeListBean emplist = new ExitEmployeListBean();
				try{
					ExitEmployee empList[] = exitEmployeeDao.findByDynamicSelect(
							"SELECT E.ID, E.ESR_MAP_ID, U.EMP_ID AS USER_ID, E.REASON, E.STATUS_ID, E.SUBMITTEDON, E.LAST_WORKING_DAY, E.BUY_BACK, (SELECT ESRM.REQ_ID FROM EMP_SER_REQ_MAP ESRM WHERE ESRM.ID = E.ESR_MAP_ID) AS COMMENTS, CONCAT(P.FIRST_NAME,' ',P.LAST_NAME) AS EMPLOYEE_NOTE, E.NOTICE_PERIOD FROM EXIT_EMPLOYEE E JOIN EXIT_EMP_USERS_MAP EEUM ON E.ID=EEUM.EXIT_ID AND EEUM.USER_ID= ? AND EEUM.TYPE = ? JOIN USERS U ON U.ID=E.USER_ID JOIN PROFILE_INFO P ON U.PROFILE_ID=P.ID",
							new Object[] { userId, APPROVER });
					int count = 0;
					for (ExitEmployee exitEmp : empList){
						if (exitEmp.getStatusId() == Status.getStatusId(Status.RESIGNED) || exitEmp.getStatusId() == Status.getStatusId(Status.INPROGRESS)) count++;
						exitEmp.setReason(exitEmp.getReason().replace("\r", " ").replace("\r\n", " ").replace("\n", " "));
					}
					emplist.setCount(count + "");
					emplist.setList(empList);
				} catch (Exception e){
					logger.error("unable to receive info.", e);
				}
				request.setAttribute("actionForm", emplist);
				break;
			case RECEIVEALLFORHR:
				ExitEmployeListBean empHRlist = new ExitEmployeListBean();
				try{
					ExitEmployee empList[] = exitEmployeeDao
							.findByDynamicSelect(
									"SELECT E.ID, E.ESR_MAP_ID, U.EMP_ID AS USER_ID, E.REASON, E.STATUS_ID, E.SUBMITTEDON, E.LAST_WORKING_DAY, E.BUY_BACK, (SELECT ESRM.REQ_ID FROM EMP_SER_REQ_MAP ESRM WHERE ESRM.ID = E.ESR_MAP_ID) AS COMMENTS, CONCAT(P.FIRST_NAME,' ',P.LAST_NAME) AS EMPLOYEE_NOTE, E.NOTICE_PERIOD FROM EXIT_EMPLOYEE E JOIN EXIT_EMP_USERS_MAP EEUM ON E.ID=EEUM.EXIT_ID AND EEUM.USER_ID= ? AND EEUM.TYPE = ? JOIN USERS U ON U.ID=E.USER_ID JOIN PROFILE_INFO P ON U.PROFILE_ID=P.ID  WHERE E.STATUS_ID IN (47,48,49,50)",
									new Object[] { userId, NOTIFIER });
					int count = 0;
					for (ExitEmployee exitEmp : empList){
						if (exitEmp.getStatusId() == Status.getStatusId(Status.EXIT_FORMALITIES)){
							count++;
							exitEmp.setCanRelieve(JDBCUtiility.getInstance().getRowCount(" FROM EXIT_EMPLOYEE E JOIN EXIT_ADMIN_NOC EAN ON EAN.EXIT_ID = E.ID AND EAN.STATUS_ID=2 AND E.ID=? JOIN EXIT_IT_NOC EIN ON EIN.EXIT_ID = E.ID AND EIN.STATUS_ID=2 JOIN EXIT_FINANCE_NOC EFN ON EFN.EXIT_ID = E.ID AND EFN.STATUS_ID=2 JOIN EXIT_QUESTIONNAIRE EQ ON EQ.EXIT_ID = E.ID  JOIN EXIT_DECLARATION ED ON ED.EXIT_ID = E.ID", new Object[] { exitEmp.getId() }) + "");
						} else exitEmp.setCanRelieve("0");
						exitEmp.setReason(exitEmp.getReason().replace("\r", " ").replace("\r\n", " ").replace("\n", " "));
					}
					empHRlist.setCount(count + "");
					empHRlist.setList(empList);
				} catch (Exception e){
					logger.error("unable to receive info.", e);
				}
				request.setAttribute("actionForm", empHRlist);
				break;
			case RECEIVEALLTOHANDLE:
				ExitEmployeListBean emphlist = new ExitEmployeListBean();
				try{
					ExitEmployee empList[] = exitEmployeeDao.findByDynamicSelect(
							"SELECT E.ID, E.ESR_MAP_ID, U.EMP_ID AS USER_ID, E.REASON, E.STATUS_ID, E.SUBMITTEDON, E.LAST_WORKING_DAY, E.BUY_BACK, (SELECT ESRM.REQ_ID FROM EMP_SER_REQ_MAP ESRM WHERE ESRM.ID = E.ESR_MAP_ID) AS COMMENTS, CONCAT(P.FIRST_NAME,' ',P.LAST_NAME) AS EMPLOYEE_NOTE, E.NOTICE_PERIOD FROM EXIT_EMPLOYEE E JOIN EXIT_EMP_USERS_MAP EEUM ON E.ID=EEUM.EXIT_ID AND EEUM.USER_ID= ? AND EEUM.TYPE = ? JOIN USERS U ON U.ID=E.USER_ID JOIN PROFILE_INFO P ON U.PROFILE_ID=P.ID",
							new Object[] { userId, HANDLER });
					int count = 0;
					for (ExitEmployee exitEmp : empList){
						if (exitEmp.getStatusId() == Status.getStatusId(Status.PENDING_RMG)) count++;
					}
					emphlist.setCount(count + "");
					emphlist.setList(empList);
				} catch (Exception e){
					logger.error("unable to receive info.", e);
				}
				request.setAttribute("actionForm", emphlist);
				break;
			case RECEIVEDETAILS:
				ExitEmployeeInfo exiteinfo = new ExitEmployeeInfo();
				try{
					ExitEmployee exitEmployee = exitEmployeeDao.findByPrimaryKey(exitBean.getId());
					Users user = UsersDaoFactory.create().findByPrimaryKey(exitEmployee.getUserId());
					ProfileInfo userProfile = profileDao.findByPrimaryKey(user.getProfileId());
					exiteinfo.setDateOfJoining(PortalUtility.getdd_MM_yyyy(userProfile.getDateOfJoining()));
					exitEmployee.setNoticePeriod(userProfile.getNoticePeriod());
					exiteinfo.setEmpName(userProfile.getFirstName() + " " + userProfile.getLastName());
					exiteinfo.setEmpId(user.getEmpId());
					exiteinfo.setCtc(new TDSUtility().getIncomeFromSalary(user.getId()) + "");
					// set experiance
					List<Object> currExp = JDBCUtiility.getInstance().getSingleColumn("SELECT TIMESTAMPDIFF(MONTH,DATE_OF_JOINING,NOW()) FROM PROFILE_INFO WHERE ID=?", new Object[] { userProfile.getId() });
					int currentExp = 0;
					if (currExp != null && currExp.size() == 1 && currExp.get(0) != null) currentExp = ((Long) currExp.get(0)).intValue();
					exiteinfo.setExperiance((currentExp / 12) + "." + (currentExp % 12));
					//set skill set
					String skills = "";
					if (user.getSkillsetId() != null && user.getSkillsetId().length() > 0){
						SkillSet[] skillsList = SkillSetDaoFactory.create().findByDynamicWhere("ID IN (" + user.getSkillsetId() + ")", null);
						for (SkillSet skill : skillsList){
							if (skill != null && skill.getSkillname() != null) skills += skill.getSkillname() + ", ";
						}
						if (skills.indexOf(", ") > 0) skills = skills.substring(0, skills.length() - 2);
					}
					exiteinfo.setSkillSet(skills);
					// podetatils
					exiteinfo.setClientName("N.A");
					exiteinfo.setProject("N.A");
					exiteinfo.setPoEndDate("N.A");
					exiteinfo.setLocation("N.A");
					try{
						PoDetails poDetails[] = PoDetailsDaoFactory.create().findByDynamicWhere("ID=(SELECT PO_ID FROM PO_EMP_MAP WHERE EMP_ID=? AND CURRENT = 1)", new Object[] { user.getId() });
						if (poDetails != null && poDetails.length > 0 && poDetails[0] != null){
							if (poDetails[0].getPoEndDate() != null) exiteinfo.setPoEndDate(PortalUtility.getdd_MM_yyyy(poDetails[0].getPoEndDate()));
							List<Object> projLoc = JDBCUtiility.getInstance().getSingleColumn("SELECT CITY FROM PROJ_LOCATIONS WHERE PROJ_ID = ?", new Object[] { poDetails[0].getProjId() });
							if (projLoc != null && projLoc.size() > 0) exiteinfo.setLocation((String) projLoc.get(0));
							List<Object> projName = JDBCUtiility.getInstance().getSingleColumn("SELECT NAME FROM PROJECT WHERE ID = ?", new Object[] { poDetails[0].getProjId() });
							if (projName != null && projName.size() > 0) exiteinfo.setProject((String) projName.get(0));
							List<Object> projClient = JDBCUtiility.getInstance().getSingleColumn("SELECT NAME FROM CLIENT C JOIN PROJ_CLIENT_MAP PCM ON C.ID=PCM.CLIENT_ID  AND PCM.PROJ_ID = ?", new Object[] { poDetails[0].getProjId() });
							if (projClient != null && projClient.size() > 0) exiteinfo.setClientName((String) projClient.get(0));
						}
					} catch (Exception e){
						logger.error("unable to receive project details.", e);
					}
					exiteinfo.setQuestions(new ExitQuestionsMapDaoImpl().getQuestionsWithAnswers(exitBean.getId()));
					exiteinfo.setObj(exitEmployee);
				} catch (Exception e){
					logger.error("unable to receive info.", e);
				}
				request.setAttribute("actionForm", exiteinfo);
				break;
			case RECEIVEALLNOC:
				try{
					ExitEmployeListBean nocbean = new ExitEmployeListBean();
					List<ExitNOCListBean> noclist = new ArrayList<ExitNOCListBean>();
					addToNocList(
							exitEmployeeDao.findByDynamicSelect("SELECT E.ID,E.ESR_MAP_ID, U.EMP_ID AS USER_ID, E.REASON, EIN.STATUS_ID AS STATUS_ID,EIN.SUBMITTED_ON AS SUBMITTEDON,E.LAST_WORKING_DAY,E.BUY_BACK, '" + NOC_IT
									+ "' AS COMMENTS , CONCAT(P.FIRST_NAME,' ',P.LAST_NAME) AS EMPLOYEE_NOTE, E.NOTICE_PERIOD  FROM EXIT_EMPLOYEE E JOIN EXIT_EMP_USERS_MAP EEUM ON  E.ID=EEUM.EXIT_ID AND EEUM.USER_ID = ? AND EEUM.TYPE = ? JOIN EXIT_IT_NOC EIN ON E.ID=EIN.EXIT_ID JOIN USERS U ON U.ID=E.USER_ID JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID", new Object[] { userId, IT }), noclist);
					int count = noclist.size();
					if (count > 0) nocbean.setItNoc("true");
					addToNocList(
							exitEmployeeDao.findByDynamicSelect("SELECT E.ID,E.ESR_MAP_ID, U.EMP_ID AS USER_ID, E.REASON, EAN.STATUS_ID AS STATUS_ID,EAN.SUBMITTED_ON AS SUBMITTEDON,E.LAST_WORKING_DAY,E.BUY_BACK, '" + NOC_ADMIN
									+ "' AS COMMENTS , CONCAT(P.FIRST_NAME,' ',P.LAST_NAME) AS EMPLOYEE_NOTE, E.NOTICE_PERIOD  FROM EXIT_EMPLOYEE E JOIN EXIT_EMP_USERS_MAP EEUM ON  E.ID=EEUM.EXIT_ID AND EEUM.USER_ID = ? AND EEUM.TYPE = ? JOIN EXIT_ADMIN_NOC EAN ON E.ID=EAN.EXIT_ID JOIN USERS U ON U.ID=E.USER_ID JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID", new Object[] { userId, ADMIN }), noclist);
					if (count != noclist.size()) nocbean.setAdminNoc("true");
					count = noclist.size();
					addToNocList(
							exitEmployeeDao.findByDynamicSelect("SELECT E.ID,E.ESR_MAP_ID, U.EMP_ID AS USER_ID, E.REASON, EFN.STATUS_ID AS STATUS_ID,EFN.SUBMITTED_ON AS SUBMITTEDON,E.LAST_WORKING_DAY,E.BUY_BACK, '" + NOC_FINANCE
									+ "' AS COMMENTS , CONCAT(P.FIRST_NAME,' ',P.LAST_NAME) AS EMPLOYEE_NOTE, E.NOTICE_PERIOD  FROM EXIT_EMPLOYEE E JOIN EXIT_EMP_USERS_MAP EEUM ON  E.ID=EEUM.EXIT_ID AND EEUM.USER_ID = ? AND EEUM.TYPE = ? JOIN EXIT_FINANCE_NOC EFN ON E.ID=EFN.EXIT_ID JOIN USERS U ON U.ID=E.USER_ID JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID", new Object[] { userId, FINANCE }), noclist);
					if (count != noclist.size()) nocbean.setFinanceNoc("true");
					nocbean.setList(noclist.toArray());
					request.setAttribute("actionForm", nocbean);
				} catch (Exception e){
					logger.error("unable to receive NOC info.", e);
				}
				break;
			case RECEIVENOC:
				try{
					if (exitBean.getType() != null && exitBean.getId() > 0){
						if (exitBean.getType().equals(NOC_FINANCE)){
							ExitFinanceNoc[] exitfin = ExitFinanceNocDaoFactory.create().findByDynamicSelect("SELECT EN.* FROM EXIT_FINANCE_NOC EN JOIN EXIT_EMP_USERS_MAP EEUM ON EN.EXIT_ID=? AND EN.EXIT_ID=EEUM.EXIT_ID JOIN EXIT_EMPLOYEE EE ON EE.ID= EN.EXIT_ID AND (((EEUM.USER_ID=? OR EE.USER_ID=?)) OR (EEUM.TYPE=? AND EEUM.USER_ID=?)) LIMIT 0,1", new Object[] { exitBean.getId(), userId, userId, FINANCE, userId, });
							if (exitfin == null || exitfin.length == 0 || exitfin[0] == null) exitfin = new ExitFinanceNoc[] { new ExitFinanceNoc() };
							if (exitfin[0].getStatusId() == Status.getStatusId(Status.SUBMITTED)){
								ExitEmployee exitEmp = ExitEmployeeDaoFactory.create().findByPrimaryKey(exitBean.getId());
								if (exitEmp.getUserId() == userId){
									exitfin = new ExitFinanceNoc[] { new ExitFinanceNoc() };
									exitfin[0].setStatusId(Status.getStatusId(Status.SUBMITTED));
								}
							}
							request.setAttribute("actionForm", exitfin[0]);
							break;
						} else if (exitBean.getType().equals(NOC_IT)){
							ExitItNoc[] exitit = ExitItNocDaoFactory.create().findByDynamicSelect("SELECT EN.* FROM EXIT_IT_NOC EN JOIN EXIT_EMP_USERS_MAP EEUM ON EN.EXIT_ID=? AND EN.EXIT_ID=EEUM.EXIT_ID JOIN EXIT_EMPLOYEE EE ON EE.ID= EN.EXIT_ID AND (((EEUM.USER_ID=? OR EE.USER_ID=?)) OR (EEUM.TYPE=? AND EEUM.USER_ID=?)) LIMIT 0,1", new Object[] { exitBean.getId(), userId, userId, IT, userId, });
							if (exitit == null || exitit.length == 0 || exitit[0] == null) exitit = new ExitItNoc[] { new ExitItNoc() };
							if (exitit[0].getStatusId() == Status.getStatusId(Status.SUBMITTED)){
								ExitEmployee exitEmp = ExitEmployeeDaoFactory.create().findByPrimaryKey(exitBean.getId());
								if (exitEmp.getUserId() == userId){
									exitit = new ExitItNoc[] { new ExitItNoc() };
									exitit[0].setStatusId(Status.getStatusId(Status.SUBMITTED));
								}
							}
							request.setAttribute("actionForm", exitit[0]);
							break;
						} else if (exitBean.getType().equals(NOC_ADMIN)){
							ExitAdminNoc[] exitadmin = ExitAdminNocDaoFactory.create().findByDynamicSelect("SELECT EN.* FROM EXIT_ADMIN_NOC EN JOIN EXIT_EMP_USERS_MAP EEUM ON EN.EXIT_ID=? AND EN.EXIT_ID=EEUM.EXIT_ID JOIN EXIT_EMPLOYEE EE ON EE.ID= EN.EXIT_ID AND (((EEUM.USER_ID=? OR EE.USER_ID=?)) OR (EEUM.TYPE=? AND EEUM.USER_ID=?)) LIMIT 0,1", new Object[] { exitBean.getId(), userId, userId, ADMIN, userId, });
							if (exitadmin == null || exitadmin.length == 0 || exitadmin[0] == null) exitadmin = new ExitAdminNoc[] { new ExitAdminNoc() };
							if (exitadmin[0].getStatusId() == Status.getStatusId(Status.SUBMITTED)){
								ExitEmployee exitEmp = ExitEmployeeDaoFactory.create().findByPrimaryKey(exitBean.getId());
								if (exitEmp.getUserId() == userId){
									exitadmin = new ExitAdminNoc[] { new ExitAdminNoc() };
									exitadmin[0].setStatusId(Status.getStatusId(Status.SUBMITTED));
								}
							}
							request.setAttribute("actionForm", exitadmin[0]);
							break;
						}
					}
				} catch (Exception e){
					logger.error("unable to receive info.", e);
				}
				request.setAttribute("actionForm", form);
				break;
			case RECEIVEDECLARATION:
				ExitDeclaration exitDeclaration = null;
				try{
					if (exitBean.getId() > 0){
						exitDeclaration = ExitDeclarationDaoFactory.create().findWhereExitIdEquals(exitBean.getId());
					}
				} catch (Exception e){
					logger.error("", e);
				}
				if (exitDeclaration == null) exitDeclaration = new ExitDeclaration();
				request.setAttribute("actionForm", exitDeclaration);
				break;
			case RECEIVEQUESTIONNARIE:
				ExitQuestionnaire exitQuestionnaire = null;
				ExitEmployee exitEmployee = null;
				try{
					if (exitBean.getId() > 0){
						exitEmployee = exitEmployeeDao.findByPrimaryKey(exitBean.getId());
						exitQuestionnaire = ExitQuestionnaireDaoFactory.create().findWhereExitIdEquals(exitBean.getId());
						if (exitQuestionnaire != null && exitQuestionnaire.getSubmittedOn() == null){
							if (exitEmployee.getUserId() != userId) exitQuestionnaire = null;
						}
					}
				} catch (Exception e){
					logger.error("", e);
				}
				if (exitQuestionnaire == null) exitQuestionnaire = new ExitQuestionnaire();
				exitQuestionnaire.setEmployeeDetails(getEmployeeDetails(exitEmployee));
				request.setAttribute("actionForm", exitQuestionnaire);
				break;
			case RECEIVECLOSURE:{
				ExitClosure exitClosure = new ExitClosure();
				try{
					if (exitBean.getId() > 0){
						ExitClosureDao exitClosureDao = ExitClosureDaoFactory.create();
						if (exitBean.getType() != null){
							int type = (exitBean.getType().equals(NOC_ADMIN)) ? ADMIN : (exitBean.getType().equals(NOC_IT)) ? IT : (exitBean.getType().equals(NOC_FINANCE)) ? FINANCE : (exitBean.getType().equals(NOC_RMG)) ? NOTIFIER : -1;
							if (type != ADMIN && type != IT && type != FINANCE){
								exitClosure.setList(exitClosureDao.findByDynamicWhere(" EXIT_ID = ?", new Object[] { exitBean.getId() }));
								exitClosure.setAction((int) JDBCUtiility.getInstance().getRowCount("FROM EXIT_CLOSURE WHERE  DEPT_TYPE = ? AND EXIT_ID=?", new Object[] { NOTIFIER, exitBean.getId() }));
							} else exitClosure.setList(exitClosureDao.findByDynamicWhere(" EXIT_ID = ? AND DEPT_TYPE = ? ", new Object[] { exitBean.getId(), type }));
						}
					}
				} catch (Exception e){
					logger.error("", e);
				}
				request.setAttribute("actionForm", exitClosure);
			}
				break;
			default:
				request.setAttribute("actionForm", form);
				break;
		}
		return result;
	}

	private String[] getEmployeeDetails(ExitEmployee exitEmployee) {
		try{
			Users users = UsersDaoFactory.create().findByPrimaryKey(exitEmployee.getUserId());
			ProfileInfo profileInfo = ProfileInfoDaoFactory.create().findByPrimaryKey(users.getProfileId());
			Levels level = LevelsDaoFactory.create().findByPrimaryKey(profileInfo.getLevelId());
			return new String[] { profileInfo.getFirstName() + " " + profileInfo.getLastName(), users.getEmpId() + "", level.getDesignation(), PortalUtility.getdd_MM_yyyy(profileInfo.getDateOfJoining()), PortalUtility.getdd_MM_yyyy(exitEmployee.getSubmittedon()), PortalUtility.getdd_MM_yyyy(exitEmployee.getLastWorkingDay()) };
		} catch (Exception e){}
		return null;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		try{
			Login login = Login.getLogin(request);
			int userId = login.getUserId();
			ExitEmployeeDao exitEmployeeDao = ExitEmployeeDaoFactory.create();
			ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
			UsersDao usersDao = UsersDaoFactory.create();
			EmpSerReqMapDao esrmDao = EmpSerReqMapDaoFactory.create();
			ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
			ExitEmployeeBean exitBean = (ExitEmployeeBean) form;
			InboxModel inbox = new InboxModel();
			switch (SaveTypes.getValue(form.getsType())) {
				case SAVE:{
					ExitEmployee exitEmp = exitEmployeeDao.findByPrimaryKey(exitBean.getId());
					EmpSerReqMap esrm = esrmDao.findByPrimaryKey(exitEmp.getEsrMapId());
					List<Integer> approverGroupIds = getApprovers(exitBean.getId());
					if (approverGroupIds.size() == 0 || !approverGroupIds.contains(userId) || (exitEmp.getStatusId() != Status.getStatusId(Status.RESIGNED) && exitEmp.getStatusId() != Status.getStatusId(Status.INPROGRESS) && exitEmp.getStatusId() != Status.getStatusId(Status.REJECTEDBYRMG))){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
						break;
					}
					List<ExitQuestionsMap> exitQm = new ArrayList<ExitQuestionsMap>();
					for (String que : exitBean.getQuestions()){
						exitQm.add(new ExitQuestionsMap(Integer.parseInt(que.substring(0, que.indexOf("~=~"))), exitEmp.getId(), que.substring(que.indexOf("~=~") + 3)));
					}
					JDBCUtiility.getInstance().update("DELETE FROM EXIT_QUESTIONS_MAP WHERE EXIT_ID=?", new Object[] { exitEmp.getId() });
					ExitQuestionsMapDao eqmDao = ExitQuestionsMapDaoFactory.create();
					for (ExitQuestionsMap eqm : exitQm)
						eqmDao.insert(eqm);
					if (exitBean.getSubmit() != null && exitBean.getSubmit().equals("true")){
						exitEmp.setStatusId(Status.getStatusId(Status.PENDING_RMG));
						exitEmployeeDao.update(new ExitEmployeePk(exitEmp.getId()), exitEmp);
						ExitRequestDaoFactory.create().insert(new ExitRequest(exitEmp.getId(), userId, exitEmp.getStatusId(), new Date(), exitBean.getComments()));
						PortalMail pMail = new PortalMail();
						ProfileInfo userProfile = profileDao.findWhereUserIdEquals(exitEmp.getUserId());
						Users user = usersDao.findByPrimaryKey(exitEmp.getUserId());
						pMail.setMailSubject("Resignation  -  " + userProfile.getFirstName() + " " + userProfile.getLastName() + " on " + PortalUtility.getdd_MM_yyyy(exitEmp.getSubmittedon()));
						pMail.setReason(exitEmp.getReason());
						pMail.setApproverName(getUserName(userId));
						pMail.setTemplateName(MailSettings.EXIT_FORM_SUBMITTED_TO_HANDLER);
						pMail.setSubmitDate(PortalUtility.getdd_MM_yyyy(exitEmp.getSubmittedon()));
						setEmployeeDetails(pMail, user, userProfile);
						List<Integer> srRmg = getHandlers(exitEmp.getId());
						srRmg.remove(new Integer(exitEmp.getUserId()));
						if (srRmg.size() == 1) pMail.setHandlerName(getUserName(srRmg.get(0)));
						else pMail.setHandlerName("RMG");
						if (srRmg.size() > 0) pMail.setAllReceipientMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(srRmg) + ") AND STATUS NOT IN (1,2,3))"));
						List<Integer> notifiers = getNotifiers(exitEmp.getId());
						notifiers.remove(new Integer(exitEmp.getUserId()));
						if (notifiers.size() > 0) pMail.setAllReceipientcCMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(notifiers) + ") AND STATUS NOT IN (1,2,3))"));
						mailGenarator.invoker(pMail);
						String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
						ModelUtiility.getInstance().deleteInboxEntries(exitEmp.getEsrMapId());
						for (Object notifier : notifiers)
							inbox.populateInboxForExitEmployee(esrm.getId(), pMail.getMailSubject(), Status.PENDING_RMG, 0, ((Integer) notifier).intValue(), userId, bodyText);
						for (Object handler : srRmg)
							inbox.populateInboxForExitEmployee(esrm.getId(), pMail.getMailSubject(), Status.PENDING_RMG, ((Integer) handler).intValue(), ((Integer) handler).intValue(), userId, bodyText);
					} else if (exitEmp.getStatusId() != Status.getStatusId(Status.REJECTEDBYRMG) && exitEmp.getStatusId() != Status.getStatusId(Status.INPROGRESS)){
						exitEmp.setStatusId(Status.getStatusId(Status.INPROGRESS));
						exitEmployeeDao.update(new ExitEmployeePk(exitEmp.getId()), exitEmp);
					}
					request.setAttribute("actionForm", exitEmp);
					break;
				}
				case REJECTED:{
					ExitEmployee exitEmp = exitEmployeeDao.findByPrimaryKey(exitBean.getId());
					EmpSerReqMap esrm = esrmDao.findByPrimaryKey(exitEmp.getEsrMapId());
					List<Integer> approverGroupIds = getApprovers(exitBean.getId());
					if (approverGroupIds.size() == 0 || !approverGroupIds.contains(userId) || (exitEmp.getStatusId() != Status.getStatusId(Status.RESIGNED) && exitEmp.getStatusId() != Status.getStatusId(Status.INPROGRESS) && exitEmp.getStatusId() != Status.getStatusId(Status.REJECTEDBYRMG))){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
						break;
					}
					exitEmp.setStatusId(Status.getStatusId(Status.REJECTED));
					exitEmployeeDao.update(new ExitEmployeePk(exitEmp.getId()), exitEmp);
					ExitRequestDaoFactory.create().insert(new ExitRequest(exitEmp.getId(), userId, exitEmp.getStatusId(), new Date(), exitBean.getComments()));
					PortalMail pMail = new PortalMail();
					ProfileInfo userProfile = profileDao.findWhereUserIdEquals(exitEmp.getUserId());
					pMail.setHandlerName(getUserName(userId));
					pMail.setMailSubject("Resignation Rejected by " + pMail.getHandlerName());
					pMail.setReason(exitEmp.getReason());
					pMail.setComments(exitBean.getComments());
					pMail.setTemplateName(MailSettings.EXIT_EMPLOYEE_REJECTED);
					pMail.setSubmitDate(PortalUtility.getdd_MM_yyyy(exitEmp.getSubmittedon()));
					pMail.setEmpFname(userProfile.getFirstName() + " " + userProfile.getLastName());
					List<Integer> srRmg = getHandlers(exitEmp.getId());
					srRmg.addAll(getNotifiers(exitEmp.getId()));
					srRmg.addAll(approverGroupIds);
					srRmg.remove(new Integer(exitEmp.getUserId()));
					pMail.setRecipientMailId(userProfile.getOfficalEmailId());
					if (srRmg.size() > 0) pMail.setAllReceipientcCMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(srRmg) + ") AND STATUS NOT IN (1,2,3))"));
					mailGenarator.invoker(pMail);
					String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
					ModelUtiility.getInstance().deleteInboxEntries(exitEmp.getEsrMapId());
					inbox.populateInboxForExitEmployee(esrm.getId(), pMail.getMailSubject(), Status.REJECTED, exitEmp.getUserId(), exitEmp.getUserId(), userId, bodyText);
					for (Object notifier : srRmg)
						inbox.populateInboxForExitEmployee(esrm.getId(), pMail.getMailSubject(), Status.REJECTED, 0, ((Integer) notifier).intValue(), userId, bodyText);
					request.setAttribute("actionForm", exitEmp);
					break;
				}
				case SUBMIT:
					if (exitBean.getReason() != null && exitBean.getReason().length() > 0){
						if (JDBCUtiility.getInstance().getRowCount(" FROM " + exitEmployeeDao.getTableName() + " E WHERE USER_ID=? AND STATUS_ID NOT IN ( ?, ?)", new Object[] { userId, Status.getStatusId(Status.REJECTED), Status.getStatusId(Status.WITHDRAWN) }) > 0){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.exit.oldrequestnotcompleted"));
							request.setAttribute("actionForm", null);
							return result;
						}
						EmpSerReqMapPk esrmpk = ModelUtiility.getInstance().createEmpSerReq(userId, "EXIT", 14);
						EmpSerReqMap esrm = esrmDao.findByPrimaryKey(esrmpk);
						ProcessChain process_chain_dto = processChainDao.findByDynamicSelect("SELECT PC.* FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=63  WHERE UR.USER_ID=?", new Object[] { new Integer(userId) })[0];
						esrm.setProcessChainId(process_chain_dto.getId());
						esrm.setNotify(process_chain_dto.getNotification());
						esrmDao.update(new EmpSerReqMapPk(esrm.getId()), esrm);
						ExitEmployee exitemp = new ExitEmployee(userId, esrmpk.getId(), exitBean.getReason(), Status.getStatusId(Status.RESIGNED), new Date());
						ExitEmployeePk exitEmployeePk = exitEmployeeDao.insert(exitemp);
						ProcessEvaluator processEvaluator = new ProcessEvaluator();
						Integer[] approverGroupIds = processEvaluator.approvers(process_chain_dto.getApprovalChain(), 1, userId);
						Integer[] handlers = processEvaluator.approvers(process_chain_dto.getApprovalChain(), 2, userId);
						if (handlers == null || handlers.length == 0) handlers = approverGroupIds;
						List notifiers = getNotifiers(process_chain_dto.getNotification(), userId);
						setLinkedUsers(approverGroupIds, handlers, notifiers, exitEmployeePk.getId());
						PortalMail pMail = new PortalMail();
						ProfileInfo userProfile = profileDao.findWhereUserIdEquals(userId);
						Users user = usersDao.findByPrimaryKey(userId);
						pMail.setMailSubject("Resignation  -  " + userProfile.getFirstName() + " " + userProfile.getLastName() + " on " + PortalUtility.getdd_MM_yyyy(exitemp.getSubmittedon()));
						pMail.setReason(exitBean.getReason());
						if (approverGroupIds.length > 1) pMail.setHandlerName("Approver");
						else pMail.setHandlerName(getUserName(approverGroupIds[0]));
						pMail.setTemplateName(MailSettings.EXIT_FORM_SUBMITTED_TO_APPROVER);
						if (approverGroupIds.length > 0) pMail.setAllReceipientMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(approverGroupIds) + ") AND STATUS NOT IN (1,2,3))"));
						pMail.setSubmitDate(PortalUtility.getdd_MM_yyyy(exitemp.getSubmittedon()));
						setEmployeeDetails(pMail, user, userProfile);
						List srRmg = new ArrayList<Integer>();
						srRmg.addAll(Arrays.asList(handlers));
						srRmg.addAll(notifiers);
						srRmg.remove(new Integer(userId));
						srRmg = new ArrayList(new HashSet(srRmg));
						if (srRmg.size() > 0) pMail.setAllReceipientcCMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(srRmg) + ") AND STATUS NOT IN (1,2,3))"));
						mailGenarator.invoker(pMail);
						String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
						ModelUtiility.getInstance().deleteInboxEntries(esrm.getId());
						for (Integer approver : approverGroupIds)
							inbox.populateInboxForExitEmployee(esrm.getId(), pMail.getMailSubject(), Status.RESIGNED, approver, approver, userId, bodyText);
						for (Object handler : srRmg){
							inbox.populateInboxForExitEmployee(esrm.getId(), pMail.getMailSubject(), Status.RESIGNED, 0, ((Integer) handler).intValue(), userId, bodyText);
						}
						request.setAttribute("actionForm", exitemp);
					} else{
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
						request.setAttribute("actionForm", null);
					}
					break;
				case SUBMITNOC:{
					ExitEmployee exitEmp = exitEmployeeDao.findByPrimaryKey(exitBean.getId());
					if (exitEmp == null || exitEmp.getUserId() != userId){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
						break;
					}
					List<Integer> to = null;
					switch (NocType.getValue(exitBean.getType())) {
						case FINANCE:{
							ExitFinanceNocDao exitFinanceNocDao = ExitFinanceNocDaoFactory.create();
							ExitFinanceNoc exitFinanceNoc = exitFinanceNocDao.findWhereExitIdEquals(exitBean.getId());
							if (exitFinanceNoc != null && exitFinanceNoc.getStatusId() != Status.getStatusId(Status.REJECTED)){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.actiontaken"));
								break;
							}
							to = getFinanceHandlers(exitEmp.getId());
							if (exitFinanceNoc != null){
								exitFinanceNoc.setStatusId(Status.getStatusId(Status.SUBMITTED));
								exitFinanceNocDao.update(new ExitFinanceNocPk(exitFinanceNoc.getId()), exitFinanceNoc);
							} else exitFinanceNocDao.insert(new ExitFinanceNoc(exitBean.getId(), Status.getStatusId(Status.SUBMITTED)));
						}
							break;
						case IT:{
							ExitItNocDao ExitItNocDao = ExitItNocDaoFactory.create();
							ExitItNoc exitItNoc = ExitItNocDao.findWhereExitIdEquals(exitBean.getId());
							if (exitItNoc != null && exitItNoc.getStatusId() != Status.getStatusId(Status.REJECTED)){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.actiontaken"));
								break;
							}
							if (exitItNoc != null){
								exitItNoc.setStatusId(Status.getStatusId(Status.SUBMITTED));
								ExitItNocDao.update(new ExitItNocPk(exitItNoc.getId()), exitItNoc);
							} else ExitItNocDao.insert(new ExitItNoc(exitBean.getId(), Status.getStatusId(Status.SUBMITTED)));
							to = getITHandlers(exitEmp.getId());
						}
							break;
						case ADMIN:{
							ExitAdminNocDao ExitAdminNocDao = ExitAdminNocDaoFactory.create();
							ExitAdminNoc exitAdminNoc = ExitAdminNocDao.findWhereExitIdEquals(exitBean.getId());
							if (exitAdminNoc != null && exitAdminNoc.getStatusId() != Status.getStatusId(Status.REJECTED)){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.actiontaken"));
								break;
							}
							if (exitAdminNoc != null){
								exitAdminNoc.setStatusId(Status.getStatusId(Status.SUBMITTED));
								ExitAdminNocDao.update(new ExitAdminNocPk(exitAdminNoc.getId()), exitAdminNoc);
							} else ExitAdminNocDao.insert(new ExitAdminNoc(exitBean.getId(), Status.getStatusId(Status.SUBMITTED)));
							to = getAdminHandlers(exitEmp.getId());
						}
							break;
						default:
							break;
					}
					Users user = usersDao.findByPrimaryKey(exitEmp.getUserId());
					ProfileInfo userprofile = profileDao.findWhereUserIdEquals(exitEmp.getUserId());
					PortalMail pMail = new PortalMail();
					pMail.setMailSubject("No objection Certificate submitted by " + userprofile.getFirstName() + " " + userprofile.getLastName());
					pMail.setTemplateName(MailSettings.EXIT_EMPLOYEE_NOC_SUBMITTED);
					setEmployeeDetails(pMail, user, userprofile);
					if (to.size() > 0) pMail.setAllReceipientMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(to) + ") AND STATUS NOT IN (1,2,3))"));
					List<Integer> notifiers = getNotifiers(exitEmp.getId());
					if (notifiers.size() > 0) pMail.setAllReceipientcCMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(notifiers) + ") AND STATUS NOT IN (1,2,3))"));
					mailGenarator.invoker(pMail);
					String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
					for (Integer handler : to)
						inbox.populateInboxForExitEmployee(exitEmp.getEsrMapId(), pMail.getMailSubject(), Status.SUBMITTED, handler, handler, exitEmp.getUserId(), bodyText);
					inbox.populateInboxForExitEmployee(exitEmp.getEsrMapId(), pMail.getMailSubject(), Status.SUBMITTED, 0, exitEmp.getUserId(), exitEmp.getUserId(), bodyText);
				}
					break;
				case FINANCENOC:{
					List<Integer> handler = getFinanceHandlers(exitBean.getId());
					if (!handler.contains(userId)){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
						break;
					}
					ExitFinanceNocDao exitFinanceNocDao = ExitFinanceNocDaoFactory.create();
					ExitFinanceNoc exitFinanceNoc = exitFinanceNocDao.findWhereExitIdEquals(exitBean.getId());
					if (exitFinanceNoc == null || exitFinanceNoc.getStatusId() == Status.getStatusId(Status.ACCEPTED)){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
						break;
					}
					exitFinanceNoc.setAgreementRecover(exitBean.getAgreementRecover());
					exitFinanceNoc.setChargedAccount(exitBean.getChargedAccount());
					exitFinanceNoc.setChargedAmount(exitBean.getChargedAmount());
					exitFinanceNoc.setChargedOn(exitBean.getChargedOn());
					exitFinanceNoc.setDue(exitBean.getDue());
					exitFinanceNoc.setDueAccount(exitBean.getDueAccount());
					exitFinanceNoc.setExitId(exitBean.getId());
					exitFinanceNoc.setLwpFrom(exitBean.getLwpFrom());
					exitFinanceNoc.setLwpTo(exitBean.getLwpTo());
					exitFinanceNoc.setPending(exitBean.getPending());
					exitFinanceNoc.setPerdiemFrom(exitBean.getPerdiemFrom());
					exitFinanceNoc.setPerdiemTo(exitBean.getPerdiemTo());
					exitFinanceNoc.setRemarks(exitBean.getRemarks());
					exitFinanceNoc.setSalaryFrom(exitBean.getSalaryFrom());
					exitFinanceNoc.setSalaryRecover(exitBean.getSalaryRecover());
					exitFinanceNoc.setSalaryTo(exitBean.getSalaryTo());
					if (exitBean.getSubmit() != null && !exitBean.getSubmit().equals("SAVE")){
						if (exitBean.getSubmit().equalsIgnoreCase(Status.ACCEPTED)) exitFinanceNoc.setStatusId(Status.getStatusId(Status.ACCEPTED));
						else if (exitBean.getSubmit().equalsIgnoreCase(Status.REJECTED)){
							exitFinanceNoc.setStatusId(Status.getStatusId(Status.REJECTED));
						} else exitFinanceNoc.setStatusId(Status.getStatusId(Status.SUBMITTED));
						exitFinanceNoc.setSubmittedOn(new Date());
					} else exitFinanceNoc.setStatusId(Status.getStatusId(Status.SUBMITTED));
					exitFinanceNocDao.update(new ExitFinanceNocPk(exitFinanceNoc.getId()), exitFinanceNoc);
					if (exitFinanceNoc.getStatusId() != Status.getStatusId(Status.SUBMITTED)){
						JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID=(SELECT MAX(ESR_MAP_ID) FROM EXIT_EMPLOYEE WHERE ID = ? ) AND ASSIGNED_TO=RECEIVER_ID AND ASSIGNED_TO IN (" + ModelUtiility.getCommaSeparetedValues(handler) + ")", new Object[] { exitBean.getId() });
						sendNocActionMail(exitFinanceNoc, NocType.FINANCE, userId);
					}
				}
					break;
				case ITNOC:{
					List<Integer> handler = getITHandlers(exitBean.getId());
					if (!handler.contains(userId)){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
						break;
					}
					ExitItNocDao exitItNocDao = ExitItNocDaoFactory.create();
					ExitItNoc exitItNoc = exitItNocDao.findWhereExitIdEquals(exitBean.getId());
					if (exitItNoc == null || exitItNoc.getStatusId() == Status.getStatusId(Status.ACCEPTED)){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
						break;
					}
					exitItNoc.setAccessories(exitBean.getAccessories());
					exitItNoc.setCompanyAssets(exitBean.getCompanyAssets());
					exitItNoc.setDissabledDomainId(exitBean.getDissabledDomainId());
					exitItNoc.setDissabledEmailId(exitBean.getDissabledEmailId());
					exitItNoc.setFailureAmmount(exitBean.getFailureAmount());
					exitItNoc.setMobile(exitBean.getMobile());
					exitItNoc.setSystem(exitBean.getSystem());
					exitItNoc.setRemarks(exitBean.getRemarks());
					if (exitBean.getSubmit() != null && !exitBean.getSubmit().equals("SAVE")){
						if (exitBean.getSubmit().equalsIgnoreCase(Status.ACCEPTED)) exitItNoc.setStatusId(Status.getStatusId(Status.ACCEPTED));
						else if (exitBean.getSubmit().equalsIgnoreCase(Status.REJECTED)){
							exitItNoc.setStatusId(Status.getStatusId(Status.REJECTED));
						} else exitItNoc.setStatusId(Status.getStatusId(Status.SUBMITTED));
					} else exitItNoc.setStatusId(Status.getStatusId(Status.SUBMITTED));
					exitItNocDao.update(new ExitItNocPk(exitItNoc.getId()), exitItNoc);
					if (exitItNoc.getStatusId() != Status.getStatusId(Status.SUBMITTED)){
						JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID=(SELECT MAX(ESR_MAP_ID) FROM EXIT_EMPLOYEE WHERE ID = ? ) AND ASSIGNED_TO=RECEIVER_ID AND ASSIGNED_TO IN (" + ModelUtiility.getCommaSeparetedValues(handler) + ")", new Object[] { exitBean.getId() });
						sendNocActionMail(exitItNoc, NocType.IT, userId);
					}
				}
					break;
				case ADMINNOC:{
					List<Integer> handler = getAdminHandlers(exitBean.getId());
					if (!handler.contains(userId)){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
						break;
					}
					ExitAdminNocDao exitAdminNocDao = ExitAdminNocDaoFactory.create();
					ExitAdminNoc exitAdminNoc = exitAdminNocDao.findWhereExitIdEquals(exitBean.getId());
					if (exitAdminNoc == null || exitAdminNoc.getStatusId() == Status.getStatusId(Status.ACCEPTED)){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
						break;
					}
					exitAdminNoc.setCompanyAssets(exitBean.getCompanyAssets());
					exitAdminNoc.setFailureAmount(exitBean.getFailureAmount());
					exitAdminNoc.setIdCard(exitBean.getIdCard());
					exitAdminNoc.setInsuranceCard(exitBean.getInsuranceCard());
					exitAdminNoc.setLockerKeys(exitBean.getLockerKeys());
					exitAdminNoc.setStudyMeterials(exitBean.getStudyMeterials());
					exitAdminNoc.setRemarks(exitBean.getRemarks());
					if (exitBean.getSubmit() != null && !exitBean.getSubmit().equals("SAVE")){
						if (exitBean.getSubmit().equalsIgnoreCase(Status.ACCEPTED)) exitAdminNoc.setStatusId(Status.getStatusId(Status.ACCEPTED));
						else if (exitBean.getSubmit().equalsIgnoreCase(Status.REJECTED)){
							exitAdminNoc.setStatusId(Status.getStatusId(Status.REJECTED));
						} else exitAdminNoc.setStatusId(Status.getStatusId(Status.SUBMITTED));
					} else exitAdminNoc.setStatusId(Status.getStatusId(Status.SUBMITTED));
					exitAdminNocDao.update(new ExitAdminNocPk(exitAdminNoc.getId()), exitAdminNoc);
					if (exitAdminNoc.getStatusId() != Status.getStatusId(Status.SUBMITTED)){
						JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID=(SELECT MAX(ESR_MAP_ID) FROM EXIT_EMPLOYEE WHERE ID = ? ) AND ASSIGNED_TO=RECEIVER_ID AND ASSIGNED_TO IN (" + ModelUtiility.getCommaSeparetedValues(handler) + ")", new Object[] { exitBean.getId() });
						sendNocActionMail(exitAdminNoc, NocType.ADMIN, userId);
					}
				}
					break;
				case WITHDRAW:{
					try{
						ExitEmployee exitEmp = exitEmployeeDao.findByPrimaryKey(exitBean.getId());
						ExitWithdrawDao exitWithdrawDao = ExitWithdrawDaoFactory.create();
						if (exitEmp == null){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
							break;
						}
						EmpSerReqMap esrm = EmpSerReqMapDaoFactory.create().findByPrimaryKey(exitEmp.getEsrMapId());
						Users user = usersDao.findByPrimaryKey(exitEmp.getUserId());
						ProfileInfo userProfile = profileDao.findByPrimaryKey(user.getProfileId());
						List<Integer> approvers = getApprovers(exitEmp.getId());
						List<Integer> handlers = getHandlers(exitEmp.getId());
						List<Integer> notifiers = getNotifiers(exitEmp.getId());
						ExitWithdraw exitWithdraw = null, exitWithdraws[] = exitWithdrawDao.findByDynamicSelect("SELECT * FROM EXIT_WITHDRAW WHERE ID=(SELECT MAX(ID) FROM EXIT_WITHDRAW WHERE  EXIT_ID=?)", new Object[] { exitEmp.getId() });
						if (exitWithdraws != null && exitWithdraws.length > 0) exitWithdraw = exitWithdraws[0];
						if (exitEmp.getUserId() == userId){
							if (exitEmp.getStatusId() == Status.getStatusId(Status.WITHDRAWN_RAISED) || exitEmp.getStatusId() == Status.getStatusId(Status.WITHDRAWN)){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
								break;
							}
							if (exitEmp.getStatusId() != Status.getStatusId(Status.REJECTED)){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.exit.withdrawnotallowed"));
								break;
							}
							if (exitBean.getReason() == null){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
								break;
							}
							int statusId = 0;
							statusId = (Status.getStatusId(Status.WITHDRAWN_RAISED));
							exitWithdrawDao.insert(new ExitWithdraw(exitEmp.getId(), statusId, exitBean.getReason(), new Date(), exitEmp.getStatusId()));
							exitEmp.setStatusId(statusId);
							exitEmployeeDao.update(new ExitEmployeePk(exitEmp.getId()), exitEmp);
							ModelUtiility.getInstance().deleteInboxEntries(exitEmp.getEsrMapId());
							PortalMail pMail = new PortalMail();
							pMail.setTemplateName(MailSettings.EXIT_EMPLOYEE_WITHDRAWN_SELF_COMPLETED);
							pMail.setMailSubject("Resignation Withdrawn by " + userProfile.getFirstName() + " " + userProfile.getLastName());
							pMail.setHandlerName(ModelUtiility.getInstance().getEmployeeName(approvers.get(0)));
							pMail.setSubmitDate(PortalUtility.getdd_MM_yyyy(new Date()));
							pMail.setReason(exitBean.getReason());
							setEmployeeDetails(pMail, user, userProfile);
							Set<Integer> cc = new HashSet<Integer>();
							cc.addAll(handlers);
							cc.addAll(notifiers);
							cc.remove(new Integer(userId));
							if (approvers.size() > 0) pMail.setAllReceipientMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(approvers) + ") AND STATUS NOT IN (1,2,3))"));
							if (cc.size() > 0) pMail.setAllReceipientcCMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(cc) + ") AND STATUS NOT IN (1,2,3))"));
							mailGenarator.invoker(pMail);
							String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
							for (Integer id : cc)
								inbox.populateInboxForExitEmployee(esrm.getId(), pMail.getMailSubject(), Status.WITHDRAWN_RAISED, 0, id.intValue(), userId, bodyText);
							for (Integer id : approvers)
								inbox.populateInboxForExitEmployee(esrm.getId(), pMail.getMailSubject(), Status.WITHDRAWN_RAISED, id, id.intValue(), userId, bodyText);
						} else if (approvers.contains(userId)){
							if (exitWithdraw == null || exitEmp.getStatusId() != Status.getStatusId(Status.WITHDRAWN_RAISED) || exitBean.getSubmit() == null){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
								break;
							}
							if (exitBean.getComments() == null) exitBean.setComments("N.A");
							if (exitBean.getSubmit().equalsIgnoreCase(Status.ACCEPTED)){
								exitEmp.setStatusId(Status.getStatusId(Status.WITHDRAWN));
								exitWithdraw.setStatusId(Status.getStatusId(Status.WITHDRAWN));
								/*PortalMail pMail = new PortalMail();
								pMail.setTemplateName(MailSettings.EXIT_EMPLOYEE_WITHDRAWN_COMPLETD);
								pMail.setMailSubject(userProfile.getFirstName() + " " + userProfile.getLastName() + "'s withdraw resignation request accepted [" + esrm.getReqId() + "]");
								pMail.setReason(exitWithdraw.getReason());
								pMail.setActionType(Status.ACCEPTED);
								pMail.setComments(exitBean.getComments());
								pMail.setHandlerName(getUserName(userId));
								setEmployeeDetails(pMail, user, userProfile);
								Set<Integer> cc = new HashSet<Integer>();
								cc.addAll(handlers);
								cc.addAll(notifiers);
								cc.addAll(approvers);
								cc.remove(new Integer(userId));
								cc.remove(new Integer(exitEmp.getUserId()));
								pMail.setRecipientMailId(userProfile.getOfficalEmailId());
								if (cc.size() > 0)pMail.setAllReceipientcCMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(cc) + ") AND STATUS NOT IN (1,2,3))"));
								mailGenarator.invoker(pMail);
								cc.add(exitEmp.getUserId());
								String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
								for (Integer id : cc)
									inbox.populateInboxForExitEmployee(esrm.getId(), pMail.getMailSubject(), Status.WITHDRAWN, 0, id, userId, bodyText);*/
								ModelUtiility.getInstance().deleteInboxEntries(esrm.getId());
								exitWithdrawDao.update(new ExitWithdrawPk(exitWithdraw.getId()), exitWithdraw);
								exitEmployeeDao.update(new ExitEmployeePk(exitEmp.getId()), exitEmp);
								ExitRequestDaoFactory.create().insert(new ExitRequest(exitEmp.getId(), userId, exitEmp.getStatusId(), new Date(), exitBean.getComments()));
							} else{
								exitEmp.setStatusId(exitWithdraw.getOnStatusId());
								exitWithdraw.setStatusId(Status.getStatusId(Status.REJECTED));
								exitWithdrawDao.update(new ExitWithdrawPk(exitWithdraw.getId()), exitWithdraw);
								exitEmployeeDao.update(new ExitEmployeePk(exitEmp.getId()), exitEmp);
								ExitRequestDaoFactory.create().insert(new ExitRequest(exitEmp.getId(), userId, Status.getStatusId(Status.REJECTED), new Date(), exitBean.getComments()));
								JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID=? AND RECEIVER_ID=ASSIGNED_TO AND RECEIVER_ID IN (" + ModelUtiility.getCommaSeparetedValues(approvers) + ")", new Object[] { exitEmp.getEsrMapId() });
								PortalMail pMail = new PortalMail();
								pMail.setTemplateName(MailSettings.EXIT_EMPLOYEE_WITHDRAWN_COMPLETD);
								pMail.setMailSubject(userProfile.getFirstName() + " " + userProfile.getLastName() + "'s withdraw resignation request rejected [" + esrm.getReqId() + "]");
								pMail.setReason(exitWithdraw.getReason());
								pMail.setComments(exitBean.getComments());
								pMail.setHandlerName(getUserName(userId));
								setEmployeeDetails(pMail, user, userProfile);
								pMail.setActionType(Status.REJECTED);
								Set<Integer> cc = new HashSet<Integer>();
								cc.addAll(handlers);
								cc.addAll(notifiers);
								cc.addAll(approvers);
								cc.remove(new Integer(userId));
								cc.remove(new Integer(exitEmp.getUserId()));
								pMail.setRecipientMailId(userProfile.getOfficalEmailId());
								if (cc.size() > 0) pMail.setAllReceipientcCMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(cc) + ") AND STATUS NOT IN (1,2,3))"));
								mailGenarator.invoker(pMail);
								cc.add(exitEmp.getUserId());
								String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
								for (Integer id : cc)
									inbox.populateInboxForExitEmployee(esrm.getId(), pMail.getMailSubject(), Status.REJECTED, 0, id, userId, bodyText);
							}
						} else{
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
							break;
						}
					} catch (Exception e){
						logger.error("", e);
					}
				}
					break;
				case SUBMITDECLARATION:{
					ExitEmployee exitEmp = exitEmployeeDao.findByPrimaryKey(exitBean.getId());
					if (exitEmp == null){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
						break;
					}
					if (exitEmp.getUserId() != userId){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
						break;
					}
					ExitDeclarationDao exitDeclarationDao = ExitDeclarationDaoFactory.create();
					ExitDeclaration exitDeclaration = exitDeclarationDao.findWhereExitIdEquals(exitBean.getId());
					if (exitDeclaration != null){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.dec.submitted"));
						break;
					}
					exitDeclaration = new ExitDeclaration(exitBean.getId(), exitBean.getNote());
					exitDeclarationDao.insert(exitDeclaration);
					List<Integer> notifiers = getNotifiers(exitEmp.getId());
					PortalMail pMail = new PortalMail();
					ProfileInfo userProfile = profileDao.findWhereUserIdEquals(exitEmp.getUserId());
					Users user = usersDao.findByPrimaryKey(exitEmp.getUserId());
					pMail.setMailSubject("Declaration letter submitted by " + userProfile.getFirstName() + " " + userProfile.getLastName());
					pMail.setTemplateName(MailSettings.EXIT_EMPLOYEE_RELIEVING);
					pMail.setActionType("declaration letter");
					setEmployeeDetails(pMail, user, userProfile);
					pMail.setAllReceipientcCMailId(new String[] { userProfile.getOfficalEmailId() });
					if (notifiers.size() > 0) pMail.setAllReceipientMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(notifiers) + ") AND STATUS NOT IN (1,2,3)) AND OFFICAL_EMAIL_ID IS NOT NULL"));
					if (notifiers.size() == 1) pMail.setHandlerName(getUserName(notifiers.get(0)));
					else pMail.setHandlerName("All");
					mailGenarator.invoker(pMail);
					notifiers.addAll(getHandlers(exitEmp.getId()));
					HashSet<Integer> allNotifiers = new HashSet<Integer>(notifiers);
					String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
					for (Integer id : allNotifiers){
						inbox.populateInboxForExitEmployee(exitEmp.getEsrMapId(), pMail.getMailSubject(), Status.SUBMITTED, 0, id, userId, bodyText);
					}
					break;
				}
				case SUBMITQUESTIONNARIE:{
					ExitEmployee exitEmp = exitEmployeeDao.findByPrimaryKey(exitBean.getId());
					if (exitEmp == null){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
						break;
					}
					if (exitEmp.getUserId() != userId){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
						break;
					}
					try{
						ExitQuestionnaireDao exitQuestionnaireDao = ExitQuestionnaireDaoFactory.create();
						ExitQuestionnaire exitQuestionnaire = exitQuestionnaireDao.findWhereExitIdEquals(exitBean.getId());
						if (exitQuestionnaire == null){
							exitQuestionnaire = new ExitQuestionnaire(exitBean.getId(), exitBean.getReason(), exitBean.getPreventableSteps(), exitBean.getNewEmpSituation(), exitBean.getResponsibilities(), exitBean.getConcerns(), exitBean.getRecommendations(), exitBean.getRejoining(), exitBean.getWorkingWithUs(), exitBean.getComments(), exitBean.getSubmit() == null ? null : new Date());
							exitQuestionnaireDao.insert(exitQuestionnaire);
						} else{
							if (exitQuestionnaire.getSubmittedOn() != null){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.dec.submitted"));
								break;
							}
							exitQuestionnaire.setFileds(exitBean.getId(), exitBean.getReason(), exitBean.getPreventableSteps(), exitBean.getNewEmpSituation(), exitBean.getResponsibilities(), exitBean.getConcerns(), exitBean.getRecommendations(), exitBean.getRejoining(), exitBean.getWorkingWithUs(), exitBean.getComments(), exitBean.getSubmit() == null ? null : new Date());
							exitQuestionnaireDao.update(new ExitQuestionnairePk(exitQuestionnaire.getId()), exitQuestionnaire);
						}
						if (exitBean.getSubmit() != null){
							List<Integer> notifiers = getNotifiers(exitEmp.getId());
							PortalMail pMail = new PortalMail();
							ProfileInfo userProfile = profileDao.findWhereUserIdEquals(exitEmp.getUserId());
							Users user = usersDao.findByPrimaryKey(exitEmp.getUserId());
							pMail.setMailSubject("Exit Questionnaire form submitted by " + userProfile.getFirstName() + " " + userProfile.getLastName());
							pMail.setTemplateName(MailSettings.EXIT_EMPLOYEE_RELIEVING);
							pMail.setActionType("questionnaire");
							setEmployeeDetails(pMail, user, userProfile);
							pMail.setAllReceipientcCMailId(new String[] { userProfile.getOfficalEmailId() });
							if (notifiers.size() > 0) pMail.setAllReceipientMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(notifiers) + ") AND STATUS NOT IN (1,2,3)) AND OFFICAL_EMAIL_ID IS NOT NULL"));
							if (notifiers.size() == 1) pMail.setHandlerName(getUserName(notifiers.get(0)));
							else pMail.setHandlerName("All");
							mailGenarator.invoker(pMail);
							notifiers.addAll(getHandlers(exitEmp.getId()));
							HashSet<Integer> allNotifiers = new HashSet<Integer>(notifiers);
							String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
							for (Integer id : allNotifiers){
								inbox.populateInboxForExitEmployee(exitEmp.getEsrMapId(), pMail.getMailSubject(), Status.SUBMITTED, 0, id, userId, bodyText);
							}
						}
					} catch (Exception e){
						logger.error("", e);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
					}
					break;
				}
				case SUBMITCLOSURE:{
					if (exitBean.getId() <= 0){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
						break;
					}
					Connection con = null;
					try{
						con = ResourceManager.getConnection();
						con.setAutoCommit(false);
						ExitClosureDao exitClosureDao = ExitClosureDaoFactory.create(con);
						if (exitBean.getType() != null){
							int type = (exitBean.getType().equals(NOC_ADMIN)) ? ADMIN : (exitBean.getType().equals(NOC_IT)) ? IT : (exitBean.getType().equals(NOC_FINANCE)) ? FINANCE : (exitBean.getType().equals(NOC_RMG)) ? NOTIFIER : -1;
							if (type > 0 && JDBCUtiility.getInstance().getRowCount("FROM EXIT_CLOSURE WHERE EXIT_ID=? AND DEPT_TYPE=?", new Object[] { exitBean.getId(), type }) == 0){
								for (String question : exitBean.getQuestions()){
									String[] data = question.split("~=~");
									exitClosureDao.insert(new ExitClosure(exitBean.getId(), type, data[0], Integer.parseInt(data[1]), PortalUtility.StringToDateDB(data[2]), new Date()));
								}
							} else result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
							con.commit();
							ExitEmployee exitEmp = exitEmployeeDao.findByPrimaryKey(exitBean.getId());
							List<Integer> notifiers = getNotifiers(exitEmp.getId());
							notifiers.addAll(getHandlers(exitEmp.getId()));
							HashSet<Integer> allNotifiers = new HashSet<Integer>(notifiers);
							PortalMail pmail = new PortalMail();
							pmail.setTemplateName(MailSettings.EXIT_EMPLOYEE_CLOSURE);
							Users user = UsersDaoFactory.create().findByDynamicSelect("SELECT U.* FROM USERS U JOIN EXIT_EMPLOYEE E ON E.ID=? AND U.ID=E.USER_ID", new Object[] { exitEmp.getId() })[0];
							ProfileInfo pf = profileDao.findByPrimaryKey(user.getProfileId());
							setEmployeeDetails(pmail, user, pf);
							pmail.setEmpDepartment(exitBean.getType());
							MailGenerator mailGenerator = new MailGenerator();
							String mailBody = mailGenerator.replaceFields(mailGenerator.getMailTemplate(pmail.getTemplateName()), pmail);
							for (Integer id : allNotifiers){
								inbox.populateInboxForExitEmployee(exitEmp.getEsrMapId(), "Closure form submitted for " + pmail.getEmpFname() + "  " + pmail.getEmpLName() + " by " + exitBean.getType(), Status.SUBMITTED, 0, id, userId, mailBody);
							}
						}
					} catch (Exception e){
						con.rollback();
						logger.error("unable to submit closure", e);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
					} finally{
						ResourceManager.close(con);
					}
				}
				default:
					break;
			}
		} catch (Exception e){
			logger.error("", e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
		}
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		try{
			Login login = Login.getLogin(request);
			int userId = login.getUserId();
			ExitEmployeeDao exitEmployeeDao = ExitEmployeeDaoFactory.create();
			ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
			UsersDao usersDao = UsersDaoFactory.create();
			EmpSerReqMapDao esrmDao = EmpSerReqMapDaoFactory.create();
			ExitEmployeeBean exitBean = (ExitEmployeeBean) form;
			ExitEmployee exitEmp = exitEmployeeDao.findByPrimaryKey(exitBean.getId());
			if (exitEmp == null){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
				return result;
			}
			List<Integer> handlers = getHandlers(exitEmp.getId());
			if (exitEmp.getUserId() == userId || exitEmp.getStatusId() != Status.getStatusId(Status.PENDING_RMG) || !handlers.contains(new Integer(userId))){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
				return result;
			}
			EmpSerReqMap esrm = esrmDao.findByPrimaryKey(exitEmp.getEsrMapId());
			switch (UpdateTypes.getValue(form.getuType())) {
				case ACCEPTED:{
					exitEmp.setBuyBack(exitBean.getBuyBack());
					exitEmp.setNoticePeriod(exitBean.getNoticePerid());
					exitEmp.setComments(exitBean.getComments());
					exitEmp.setLastWorkingDay(exitBean.getLastWorkingDay());
					exitEmp.setEmployeeNote(exitBean.getNote());
					exitEmp.setStatusId(Status.getStatusId(Status.SERVING_NOTICE_PERIOD));
					exitEmployeeDao.update(new ExitEmployeePk(exitEmp.getId()), exitEmp);
					ExitRequestDaoFactory.create().insert(new ExitRequest(exitEmp.getId(), userId, exitEmp.getStatusId(), new Date(), exitBean.getComments()));
					JDBCUtiility.getInstance().update("UPDATE PROFILE_INFO P JOIN USERS U ON U.ID = ? AND U.PROFILE_ID = P.ID SET P.DATE_OF_SEPERATION= ? ", new Object[] { exitEmp.getUserId(), exitEmp.getLastWorkingDay() });
					PortalMail pMail = new PortalMail();
					ProfileInfo userProfile = profileDao.findWhereUserIdEquals(exitEmp.getUserId());
					List<Integer> approverGroupIds = getApprovers(exitEmp.getId());
					pMail.setMailSubject("Resignation Acceptance");
					pMail.setReason(exitBean.getReason());
					pMail.setEmployeeName(getUserName(userProfile.getReportingMgr()));
					pMail.setBuyBack("");
					pMail.setComments("");
					if (exitEmp.getBuyBack() > 0) pMail.setBuyBack("<tr><td class='ttd'>Notice period buy back </td><td class='ttd'>:</td><td class='ttd'> " + exitEmp.getBuyBack() + "days</td></tr>");
					if (exitEmp.getEmployeeNote() != null && exitEmp.getEmployeeNote().length() > 0) pMail.setComments("<tr><td class='ttd'>Employee Note </td><td class='ttd'>:</td><td class='ttd'>" + exitEmp.getEmployeeNote() + "</td></tr>");
					pMail.setDateOfSeperation(PortalUtility.getdd_MM_yyyy(exitEmp.getLastWorkingDay()));
					pMail.setTimePeriod((userProfile.getNoticePeriod() - exitEmp.getNoticePeriod()) + "days");
					pMail.setTemplateName(MailSettings.EXIT_EMPLOYEE_ACCEPTANCE);
					pMail.setRecipientMailId(userProfile.getOfficalEmailId());
					pMail.setSubmitDate(PortalUtility.getdd_MM_yyyy(exitEmp.getSubmittedon()));
					pMail.setEmpFname(userProfile.getFirstName() + " " + userProfile.getLastName());
					List<Integer> srRmg = new ArrayList<Integer>();
					srRmg.addAll(handlers);
					srRmg.addAll(approverGroupIds);
					srRmg.addAll(getNotifiers(exitEmp.getId()));
					srRmg.remove(new Integer(exitEmp.getUserId()));
					srRmg = new ArrayList<Integer>(new HashSet<Integer>(srRmg));
					if (srRmg.size() > 0) pMail.setAllReceipientcCMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(srRmg) + ") AND STATUS NOT IN (1,2,3))"));
					mailGenarator.invoker(pMail);
					String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
					ModelUtiility.getInstance().deleteInboxEntries(esrm.getId());
					srRmg.add(new Integer(exitEmp.getUserId()));
					InboxModel inbox = new InboxModel();
					for (Object handler : srRmg)
						inbox.populateInboxForExitEmployee(esrm.getId(), pMail.getMailSubject(), Status.ACCEPTED, 0, ((Integer) handler).intValue(), userId, bodyText);
					request.setAttribute("actionForm", exitEmp);
					checkToEnableNoc(exitEmp);
				}
					break;
				case REJECTED:{
					exitEmp.setStatusId(Status.getStatusId(Status.REJECTEDBYRMG));
					exitEmp.setEmployeeNote(exitBean.getNote());
					exitEmp.setComments(exitBean.getComments());
					exitEmployeeDao.update(new ExitEmployeePk(exitEmp.getId()), exitEmp);
					ExitRequestDaoFactory.create().insert(new ExitRequest(exitEmp.getId(), userId, exitEmp.getStatusId(), new Date(), exitBean.getComments()));
					Users user = usersDao.findByPrimaryKey(exitEmp.getUserId());
					ProfileInfo userProfile = profileDao.findWhereUserIdEquals(exitEmp.getUserId());
					List srRmg = new ArrayList<Integer>();
					List<Integer> approvers = getApprovers(exitEmp.getId());
					srRmg.addAll(handlers);
					srRmg.addAll(getNotifiers(exitEmp.getId()));
					srRmg.remove(new Integer(exitEmp.getUserId()));
					srRmg = new ArrayList(new HashSet(srRmg));
					PortalMail pMail = new PortalMail();
					pMail.setHandlerName(getUserName(userId));
					pMail.setMailSubject("Resignation Rejected by " + pMail.getHandlerName());
					if (approvers.size() > 0) pMail.setAllReceipientMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(approvers) + ") AND STATUS NOT IN (1,2,3))"));
					pMail.setApproverName("All");
					if (approvers.size() == 1){
						pMail.setApproverName(getUserName(approvers.get(0)));
					}
					setEmployeeDetails(pMail, user, userProfile);
					pMail.setTemplateName(MailSettings.EXIT_EMPLOYEE_REJECTED_RMG);
					if (srRmg.size() > 0) pMail.setAllReceipientcCMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(srRmg) + ") AND STATUS NOT IN (1,2,3))"));
					mailGenarator.invoker(pMail);
					String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
					ModelUtiility.getInstance().deleteInboxEntries(esrm.getId());
					srRmg.add(new Integer(exitEmp.getUserId()));
					InboxModel inbox = new InboxModel();
					for (Object handler : srRmg){
						inbox.populateInboxForExitEmployee(esrm.getId(), pMail.getMailSubject(), Status.REJECTEDBYRMG, 0, ((Integer) handler).intValue(), userId, bodyText);
					}
					for (Integer approver : approvers){
						inbox.populateInboxForExitEmployee(esrm.getId(), pMail.getMailSubject(), Status.REJECTEDBYRMG, approver, approver, userId, bodyText);
					}
					request.setAttribute("actionForm", exitEmp);
				}
					break;
				default:
					break;
			}
		} catch (Exception e){
			logger.error("", e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
		}
		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		return null;
	}

	@Override
	public Attachements download(PortalForm form) {
		return null;
	}

	private void setEmployeeDetails(PortalMail portalMail, Users userDto, ProfileInfo profileInfoDto) {
		try{
			LevelsDao levelsdao = LevelsDaoFactory.create();
			DivisonDao divisionDao = DivisonDaoFactory.create();
			Levels level = levelsdao.findByPrimaryKey(profileInfoDto.getLevelId());
			Divison division = divisionDao.findByPrimaryKey(level.getDivisionId());
			portalMail.setEmpDesignation(level.getDesignation());
			portalMail.setEmpFname(profileInfoDto.getFirstName());
			portalMail.setEmpLName(profileInfoDto.getLastName());
			portalMail.setEmpId(userDto.getEmpId() + "");
			portalMail.setOfferedDepartment("N.A");
			portalMail.setEmpDivision("N.A");
			if (division != null){
				if (division.getParentId() != 0){
					portalMail.setEmpDivision(division.getName());
					while (division.getParentId() != 0){
						division = divisionDao.findByPrimaryKey(division.getParentId());
					}
					if (division != null) portalMail.setOfferedDepartment(division.getName());
				} else portalMail.setOfferedDepartment(division.getName());
			}
		} catch (Exception e){}
	}

	public List getNotifiers(String notifierString, int userId) {
		Integer[] notifiers = new ProcessEvaluator().notifiers(notifierString, userId);
		List list = new ArrayList<Integer>();
		if (notifiers != null) for (Integer notifier : notifiers){
			if (notifier.intValue() > 2) list.add(notifier);
		}
		return list;
	}

	private String getUserName(Integer id) {
		List<Object> userNameList = JDBCUtiility.getInstance().getSingleColumn("SELECT CONCAT(FIRST_NAME,' ',LAST_NAME) FROM PROFILE_INFO WHERE ID=(SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { id });
		return (String) userNameList.get(0);
	}

	private void setLinkedUsers(Integer[] approverGroupIds, Integer[] handlers, List notifiers, int exitId) {
		ExitEmpUsersMapDao exitEmpUsersMapDao = ExitEmpUsersMapDaoFactory.create();
		try{
			for (Integer approverGroupId : approverGroupIds)
				if (approverGroupId != null) exitEmpUsersMapDao.insert(new ExitEmpUsersMap(exitId, approverGroupId.intValue(), APPROVER));
			for (Integer handler : handlers)
				if (handler != null) exitEmpUsersMapDao.insert(new ExitEmpUsersMap(exitId, handler.intValue(), HANDLER));
			for (Object notifier : notifiers)
				if (notifier != null) exitEmpUsersMapDao.insert(new ExitEmpUsersMap(exitId, ((Integer) notifier).intValue(), NOTIFIER));
		} catch (Exception e){
			logger.error("unable to save users in exit emp users map", e);
		}
	}

	private void setLinkedUsers(Integer[] finance, Integer[] it, Integer[] admin, int exitId, int userId) {
		ExitEmpUsersMapDao exitEmpUsersMapDao = ExitEmpUsersMapDaoFactory.create();
		try{
			if (finance != null) for (Integer handler : finance)
				if (handler != null && userId != handler.intValue()) exitEmpUsersMapDao.insert(new ExitEmpUsersMap(exitId, handler.intValue(), FINANCE));
			if (it != null) for (Integer handler : it)
				if (handler != null && userId != handler.intValue()) exitEmpUsersMapDao.insert(new ExitEmpUsersMap(exitId, handler.intValue(), IT));
			if (admin != null) for (Integer handler : admin)
				if (handler != null && userId != handler.intValue()) exitEmpUsersMapDao.insert(new ExitEmpUsersMap(exitId, handler.intValue(), ADMIN));
		} catch (Exception e){
			logger.error("unable to save users in exit emp users map", e);
		}
	}

	public List<Integer> getApprovers(int exitId) {
		return getUsersList(exitId, APPROVER);
	}

	public List<Integer> getHandlers(int exitId) {
		return getUsersList(exitId, HANDLER);
	}

	public List<Integer> getNotifiers(int exitId) {
		return getUsersList(exitId, NOTIFIER);
	}

	public List<Integer> getFinanceHandlers(int exitId) {
		return getUsersList(exitId, FINANCE);
	}

	public List<Integer> getITHandlers(int exitId) {
		return getUsersList(exitId, IT);
	}

	public List<Integer> getAdminHandlers(int exitId) {
		return getUsersList(exitId, ADMIN);
	}

	public List<Integer> getUsersList(int exitId, int type) {
		List<Integer> list = new ArrayList<Integer>();
		try{
			List<Object> userids = JDBCUtiility.getInstance().getSingleColumn("SELECT USER_ID FROM EXIT_EMP_USERS_MAP WHERE EXIT_ID=? AND TYPE=?", new Object[] { exitId, type });
			for (Object users : userids)
				if (type != 3 || ((Integer) users).intValue() > 2) list.add((Integer) users);
		} catch (Exception e){
			logger.error("unable to receive users in exit emp users map", e);
		}
		return list;
	}

	private void addToNocList(ExitEmployee[] exitEmployee, List<ExitNOCListBean> noclist) {
		for (ExitEmployee employee : exitEmployee){
			noclist.add(new ExitNOCListBean(employee.getId() + "", employee.getUserId() + "", employee.getEmployeeNote(), PortalUtility.getdd_MM_yyyy(employee.getLastWorkingDay()), employee.getSubmittedOn(), employee.getComments(), employee.getStatus(), employee.getStatusId() + "", employee.getBuyBack() + ""));
		}
	}

	private void sendNocActionMail(Object noc, NocType type, int userId) {
		try{
			PortalMail pMail = new PortalMail();
			pMail.setTemplateName(MailSettings.EXIT_EMPLOYEE_NOC_ACTION);
			int exitId = 0;
			List<Integer> cc = null;
			switch (type) {
				case FINANCE:
					ExitFinanceNoc exitfinNoc = (ExitFinanceNoc) noc;
					exitId = exitfinNoc.getExitId();
					pMail.setEmpDepartment(NOC_FINANCE);
					if (exitfinNoc.getStatusId() == 2){
						pMail.setActionType("Cleared");
						pMail.setReason("");
					} else{
						pMail.setActionType("withheld");
						pMail.setReason(" Reason: " + (exitfinNoc.getRemarks() == null ? "N.A" : exitfinNoc.getRemarks() + "<br /><br />"));
					}
					cc = getFinanceHandlers(exitId);
					break;
				case IT:
					ExitItNoc exititNoc = (ExitItNoc) noc;
					exitId = exititNoc.getExitId();
					pMail.setEmpDepartment(NOC_IT);
					if (exititNoc.getStatusId() == 2){
						pMail.setActionType("Cleared");
						pMail.setReason("");
					} else{
						pMail.setActionType("withheld");
						pMail.setReason(" Reason: " + (exititNoc.getRemarks() == null ? "N.A" : exititNoc.getRemarks() + "<br /><br />"));
					}
					cc = getITHandlers(exitId);
					break;
				case ADMIN:
					ExitAdminNoc exitAdminNoc = (ExitAdminNoc) noc;
					exitId = exitAdminNoc.getExitId();
					pMail.setEmpDepartment(NOC_ADMIN);
					if (exitAdminNoc.getStatusId() == 2){
						pMail.setActionType("Cleared");
						pMail.setReason("");
					} else{
						pMail.setActionType("withheld");
						pMail.setReason(" Reason: " + (exitAdminNoc.getRemarks() == null ? "N.A" : exitAdminNoc.getRemarks() + "<br /><br />"));
					}
					cc = getAdminHandlers(exitId);
					break;
				default:
					break;
			}
			if (exitId > 0){
				pMail.setHandlerName(ModelUtiility.getInstance().getEmployeeName(userId));
				ExitEmployee exitEmp = ExitEmployeeDaoFactory.create().findByPrimaryKey(exitId);
				JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID=? AND RECEIVER_ID=ASSIGNED_TO AND RECEIVER_ID IN (" + ModelUtiility.getCommaSeparetedValues(cc) + ")", new Object[] { exitEmp.getEsrMapId() });
				ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
				Users user = UsersDaoFactory.create().findByDynamicSelect("SELECT U.* FROM USERS U JOIN EXIT_EMPLOYEE E ON E.ID=? AND U.ID=E.USER_ID", new Object[] { exitId })[0];
				ProfileInfo pf = profileDao.findByPrimaryKey(user.getProfileId());
				List<Integer> notifier = getNotifiers(exitId);
				if (notifier.size() == 1) pMail.setHandlerName(getUserName(notifier.get(0)));
				else pMail.setHandlerName("All");
				if (notifier.size() > 0) pMail.setAllReceipientMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(notifier) + ") AND STATUS NOT IN (1,2,3))"));
				cc.addAll(getHandlers(exitId));
				cc.add(user.getId());
				cc = new ArrayList(new HashSet(cc));
				if (cc.size() > 0) pMail.setAllReceipientcCMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(cc) + ") AND STATUS NOT IN (1,2,3))"));
				setEmployeeDetails(pMail, user, pf);
				pMail.setMailSubject("No objection certificate " + pMail.getActionType() + " for " + pMail.getEmpFname() + " " + pMail.getEmpLName() + "  by " + pMail.getEmpDepartment());
				mailGenarator.invoker(pMail);
				InboxModel inbox = new InboxModel();
				cc.addAll(notifier);
				String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
				for (Integer id : cc)
					inbox.populateInboxForExitEmployee(exitEmp.getEsrMapId(), pMail.getMailSubject(), pMail.getActionType().equalsIgnoreCase("Cleared") ? Status.ACCEPTED : Status.REJECTED, 0, id.intValue(), userId, bodyText);
			}
		} catch (Exception e){}
	}

	private void checkToEnableNoc(ExitEmployee exitEmp) {
		// get the days left.
		int daydiff = (int) ((exitEmp.getLastWorkingDay().getTime() - PortalUtility.formateDateTimeToDateFormat(new Date()).getTime()) / (24 * 60 * 60 * 1000));
		if (daydiff < 30){
			// get the next 2nd working day & compare with last working day, if it comes befor then enable noc. 
			int next2ndworkingDay = getNextWorkingDayDifferance(1, PortalUtility.formateDateTimeToDateFormat(new Date()), 2);
			if (daydiff - next2ndworkingDay <= 0){
				sendNocNotifications(new ExitEmployee[] { exitEmp });
			}
		}
	}

	public void enableNOCToSubmit() {
		Date date = PortalUtility.formateDateTimeToDateyyyyMMdd(new Date());
		try{
			Regions[] regions = RegionsDaoFactory.create().findAll();
			for (Regions region : regions){
				int priorNextWorkingDay = getNextWorkingDayDifferance(region.getId(), date, PRIOR_DAYS_TO_NOC);
				if (priorNextWorkingDay == 0) return;
				ExitEmployee[] exitEmps = ExitEmployeeDaoFactory.create().findByDynamicSelect("SELECT EE.* FROM EXIT_EMPLOYEE EE JOIN USERS U ON DATEDIFF(EE.LAST_WORKING_DAY,CURDATE()) BETWEEN ? AND ? AND U.ID = EE.USER_ID AND  U.STATUS NOT IN (1, 2, 3) JOIN PROFILE_INFO PF ON  U.PROFILE_ID = PF.ID JOIN LEVELS L ON L.ID=PF.LEVEL_ID JOIN DIVISON D ON D.ID = L.DIVISION_ID AND D.REGION_ID = ? ", new Object[] { PRIOR_DAYS_TO_NOC, priorNextWorkingDay, region.getId() });
				sendNocNotifications(exitEmps);
			}
		} catch (Exception e){
			logger.error("unable to enable NOC ", e);
		}
	}

	public void sendNocNotifications(ExitEmployee[] exitEmps) {
		//RequestEscalation reqEscalation=new RequestEscalation();
		InboxModel inbox = new InboxModel();
		ProcessEvaluator processEvaluator = new ProcessEvaluator();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
		ExitEmployeeDao exitEmployeeDao = ExitEmployeeDaoFactory.create();
		//Set escalation action
		//reqEscalation.setEscalationAction(esrMapId, userId);
		for (ExitEmployee exitEmp : exitEmps){
			if (exitEmp.getStatusId() == Status.getStatusId(Status.SEPERATED)) continue;
			try{
				exitEmp.setStatusId(Status.getStatusId(Status.EXIT_FORMALITIES));
				exitEmployeeDao.update(new ExitEmployeePk(exitEmp.getId()), exitEmp);
				ProcessChain process_chain_dto = null;
				process_chain_dto = processChainDao.findByDynamicSelect("SELECT PC.* FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=63  WHERE UR.USER_ID=?", new Object[] { exitEmp.getUserId() })[0];
				Integer[] finance = processEvaluator.approvers(process_chain_dto.getApprovalChain(), FINANCE, exitEmp.getUserId());
				Integer[] it = processEvaluator.approvers(process_chain_dto.getApprovalChain(), IT, exitEmp.getUserId());
				Integer[] admin = processEvaluator.approvers(process_chain_dto.getApprovalChain(), ADMIN, exitEmp.getUserId());
				setLinkedUsers(finance, it, admin, exitEmp.getId(), exitEmp.getUserId());
				//No objection Certificates are available
				PortalMail pMail = new PortalMail();
				ProfileInfo userProfile = profileDao.findWhereUserIdEquals(exitEmp.getUserId());
				pMail.setMailSubject("Exit Formalities");
				pMail.setEmpFname(userProfile.getFirstName() + " " + userProfile.getLastName());
				pMail.setTemplateName(MailSettings.EXIT_EMPLOYEE_NOC_AVAILABLE);
				pMail.setRecipientMailId(userProfile.getOfficalEmailId());
				List<Integer> notifiers = getNotifiers(exitEmp.getId());
				if (notifiers.size() > 0) pMail.setAllReceipientcCMailId(profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(notifiers) + ") AND STATUS NOT IN (1,2,3))"));
				mailGenarator.invoker(pMail);
				String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
				inbox.populateInboxForExitEmployee(exitEmp.getEsrMapId(), pMail.getMailSubject(), Status.NEW, 0, exitEmp.getUserId(), 1, bodyText);
			} catch (Exception e){
				logger.error("unable to enable Noc for :" + exitEmp, e);
			}
		}
	}

	public int getNextWorkingDayDifferance(int regionId, Date date, int days) {
		try{
			Holidays[] holiday = HolidaysDaoFactory.create().findByDynamicSelect("SELECT * FROM HOLIDAYS WHERE CAL_ID IN (SELECT ID FROM CALENDAR WHERE REGION = ? AND YEAR=? ) AND DATE_PICKER BETWEEN ? AND ADDDATE(?,180) ORDER BY DATE_PICKER ASC", new Object[] { regionId, PortalUtility.getyear(date), date, date });
			if (holiday != null && holiday.length > 0 && holiday[0].getDatePicker().equals(date)) return 0;
			int daysBefore = days, nextdays = 0;
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			while (nextdays < days){
				cal.add(Calendar.DAY_OF_MONTH, 1);
				if (cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
					daysBefore += 1;
					continue;
				}
				String dat = PortalUtility.getdd_MM_yyyy(cal.getTime());
				boolean tocontinue = false;
				if (holiday != null && holiday.length > 0) for (Holidays tmpHoliday : holiday){
					if (dat.equals(PortalUtility.getdd_MM_yyyy(tmpHoliday.getDatePicker()))){
						daysBefore += 1;
						tocontinue = true;
						break;
					}
				}
				if (tocontinue) continue;
				nextdays++;
			}
			return daysBefore;
		} catch (Exception e){}
		return days;
	}
}
