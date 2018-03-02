package com.dikshatech.portal.models;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.AppraisalBean;
import com.dikshatech.beans.AppraisalHRReport;
import com.dikshatech.beans.AppraisalListBean;
import com.dikshatech.beans.AppraisalReport;
import com.dikshatech.beans.AppraisalReportV1;
import com.dikshatech.beans.AppraisalUserDetails;
import com.dikshatech.beans.AppraisalsBean;
import com.dikshatech.beans.AppraisalsStatusBean;
import com.dikshatech.common.utils.GenerateXls;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.POIParser;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.AppraisalDao;
import com.dikshatech.portal.dao.AppraisalIDPDao;
import com.dikshatech.portal.dao.AppraisalMinutiaeDao;
import com.dikshatech.portal.dao.AppraisalProjectsDao;
import com.dikshatech.portal.dao.AppraisalRatingsDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Appraisal;
import com.dikshatech.portal.dto.AppraisalIDP;
import com.dikshatech.portal.dto.AppraisalMinutiae;
import com.dikshatech.portal.dto.AppraisalPk;
import com.dikshatech.portal.dto.AppraisalProjects;
import com.dikshatech.portal.dto.AppraisalRatings;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.UsersPk;
import com.dikshatech.portal.exceptions.AppraisalIDPDaoException;
import com.dikshatech.portal.exceptions.AppraisalMinutiaeDaoException;
import com.dikshatech.portal.exceptions.AppraisalProjectsDaoException;
import com.dikshatech.portal.exceptions.AppraisalRatingsDaoException;
import com.dikshatech.portal.factory.AppraisalDaoFactory;
import com.dikshatech.portal.factory.AppraisalIDPDaoFactory;
import com.dikshatech.portal.factory.AppraisalMinutiaeDaoFactory;
import com.dikshatech.portal.factory.AppraisalProjectsDaoFactory;
import com.dikshatech.portal.factory.AppraisalRatingsDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.AppraisalDetailsDaoImpl;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

/**
 * @author gurunath.rokkam
 */
public class AppraisalModel extends ActionMethods {

	private static final int		CURRENT_VERSION	= 1;
	private static final String		TECHNICAL		= "TECHNICAL";
	private static AppraisalModel	appraisalModel	= null;
	private static Logger			logger			= LoggerUtil.getLogger(AppraisalModel.class);
	MailGenerator					mailGenarator	= new MailGenerator();

	private AppraisalModel() {}

	public static AppraisalModel getInstance() {
		if (appraisalModel == null) appraisalModel = new AppraisalModel();
		return appraisalModel;
	}

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		if (ModelUtiility.hasModulePermission(login, 59)){
			try{
				AppraisalBean appraisal = (AppraisalBean) form;
				switch (ExecuteTypes.getValue(form.geteType())) {
					case ADDAPPRAISERS:
						updateApprisalusers(result, appraisal, request, false);
						break;
					case UPDATEAPPRAISERS:
						updateApprisalusers(result, appraisal, request, true);
						break;
					case REMOVEAPPRAISERS:
						removeApprisalusers(result, appraisal, request);
						break;
					case SENDAPPRAISALMAIL:
						sendAppraisalSalaryRevisionMail(result, appraisal, request);
						break;
					default:
						break;
				}
			} catch (Exception e){
				logger.error("", e);
			}
		} else{
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
		}
		return result;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		/*
		 * code for encrypting the existing data...
		 * Login login = Login.getLogin(request);
		 * if (ModelUtiility.hasModulePermission(login, 59)){
		 * logger.info("Encrypting appraisal data by userid : "+login.getUserId());
		 * AppraisalDetailsDaoImpl.getInstance().encryptOldData(form.getMonth());
		 * }
		 */
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		int userId = login.getUserId();
		AppraisalDao appraisalDao = AppraisalDaoFactory.create();
		AppraisalListBean list = new AppraisalListBean();
		DropDown dropdown = null;
		AppraisalBean appraisalBean = (AppraisalBean) form;
		String activePeriod = " AND START_DATE = (SELECT MAX(START_DATE) FROM APPRAISAL WHERE TIMESTAMPDIFF(DAY, START_DATE, CURDATE())>=0)";
		if (appraisalBean.getPeriod() != null && appraisalBean.getYear() != null) activePeriod = "AND PERIOD='" + appraisalBean.getPeriod() + "' AND YEAR=" + appraisalBean.getYear() + " ";
		boolean isRMG = (isRMGManager(login) == 1);
		switch (ReceiveTypes.getValue(form.getrType())) {
			case RECEIVEINFO:
				try{
					request.setAttribute("actionForm", list);
					Appraisal appraisal = appraisalDao.findByPrimaryKey(appraisalBean.getId());
					AppraisalUserDetails details = new AppraisalUserDetails();
					List<Object> desg = JDBCUtiility.getInstance().getSingleColumn("SELECT A.PROM_PROMOTEDAS FROM APPRAISAL_DETAILS A WHERE ID=?", new Object[] { appraisalBean.getId() });
					if (desg != null && desg.size() > 0 && desg.get(0) != null) details.setDesignation((String) desg.get(0));
					if (details.getDesignation() == null || details.getDesignation().equals("")){
						Levels level[] = LevelsDaoFactory.create().findByDynamicSelect("SELECT L.* FROM LEVELS L JOIN USERS U ON U.LEVEL_ID=L.ID AND U.ID=?", new Object[] { appraisal.getUserId() });
						if (level != null && level.length > 0 && level[0] != null){
							details.setDesignation(level[0].getDesignation());
						}
					}
					details.setName(ModelUtiility.getInstance().getEmployeeName(appraisal.getUserId()));
					details.setSubject("Diksha : RMG : " + appraisal.getPeriod() + " - " + appraisal.getYear() + " : Salary Revision ");
					details.setId(appraisalBean.getId() + "");
					request.setAttribute("actionForm", details);
				} catch (Exception e){}
				break;
			case RECEIVEALL:
				try{
					Appraisal appraisals[] = appraisalDao.findByDynamicWhere("USER_ID = ? AND ( TIMESTAMPDIFF(DAY, START_DATE, CURDATE())>=0 OR START_DATE IS NULL)", new Object[] { userId });
					setAppraiserNames(appraisals, false);
					setAppraiseeStatus(appraisals);
					list.setAppraisal(appraisals);
					list.setToApprove((JDBCUtiility.getInstance().getRowCount("FROM APPRAISAL WHERE APPRAISER=? AND STATUS NOT IN (?)", new Object[] { userId, Status.PAFAVAILABLE }) > 0) + "");
					list.setToHandle((isRMG) ? "true" : (JDBCUtiility.getInstance().getRowCount("FROM APPRAISAL WHERE HANDLER=?", new Object[] { userId }) > 0) + "");
					list.setToApproveCount((JDBCUtiility.getInstance().getRowCount("FROM APPRAISAL WHERE APPRAISER=? AND STATUS IN (?, ?, ?, ?) " + activePeriod, new Object[] { userId, Status.PAFSUBMITTED, Status.INPROGRESS, Status.REJECTEDBYRMG, Status.PENDING_APPRAISER })) + "");
					list.setToHandleCount((JDBCUtiility.getInstance().getRowCount("FROM APPRAISAL WHERE " + (isRMG ? "" : " HANDLER=" + userId + " AND ") + " STATUS IN (?)" + activePeriod, new Object[] { Status.PENDING_RMG })) + "");
					list.setToManage(true + "");
				} catch (Exception e){
					logger.error("", e);
				}
				request.setAttribute("actionForm", list);
				break;
			case RECEIVEALLTOAPPROVE:
				setallToApprove(list, userId, activePeriod);
				list.setToApproveCount((JDBCUtiility.getInstance().getRowCount("FROM APPRAISAL WHERE APPRAISER=? AND STATUS IN (?, ?, ?, ?) " + activePeriod, new Object[] { userId, Status.PAFSUBMITTED, Status.INPROGRESS, Status.REJECTEDBYRMG, Status.PENDING_APPRAISER })) + "");
				request.setAttribute("actionForm", list);
				break;
			case RECEIVEALLTOHANDLE:
				try{
					Appraisal appraisals[] = appraisalDao.findByDynamicWhere((isRMG ? "" : " HANDLER=" + userId + " AND ") + " USER_ID != ? " + activePeriod, new Object[] { userId });
					if (appraisals != null && appraisals.length > 0 && appraisals[0] != null) list.setPeriod(appraisals[0].getYear() + " - " + appraisals[0].getPeriod());
					setAppraiserNames(appraisals, false, true);
					list.setAppraisal(appraisals);
					list.setObject(getStatusCounts(appraisals));
					list.setToHandleCount((JDBCUtiility.getInstance().getRowCount("FROM APPRAISAL WHERE" + (isRMG ? "" : " HANDLER=" + userId + " AND ") + " STATUS IN (?)" + activePeriod, new Object[] { Status.PENDING_RMG })) + "");
				} catch (Exception e){
					logger.error("", e);
				}
				request.setAttribute("actionForm", list);
				break;
			case RECEIVE:
				Connection conn = null;
				request.setAttribute("actionForm", "");
				try{
					conn = ResourceManager.getConnection();
					appraisalDao = AppraisalDaoFactory.create(conn);
					Appraisal appraisal = null;
					if (appraisalBean.getEsr_map_id() != null){
						appraisal = appraisalDao.findByDynamicWhere("ESR_MAP_ID = " + appraisalBean.getEsr_map_id(), null)[0];
					} else appraisal = appraisalDao.findByPrimaryKey(appraisalBean.getId());
					if (appraisal.getAppraiser() != userId && appraisal.getUserId() != userId && appraisal.getHandler() != userId && !isRMG){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
						return result;
					}
					if (appraisal.getVersion() == 0){
						appraisalBean = AppraisalDetailsDaoImpl.getInstance().getAppraisaldetails(appraisal.getId());
						appraisalBean.setUserDetails(getAppraisalUserDetails(appraisal));
						setAppraiserNames(new Appraisal[] { appraisal }, false);
						appraisalBean.setObj(appraisal);
						appraisalBean.setAppraisalId(appraisal.getId() + "");
						appraisalBean.setRmgHandler(isRMGManager(login) == 1 ? userId + "" : "0");
						if (userId == appraisal.getAppraiser()){
							List<Object> levels = JDBCUtiility.getInstance().getSingleColumn("SELECT CONCAT(LL.LABEL,' - ',LL.DESIGNATION) FROM LEVELS L JOIN USERS U ON U.LEVEL_ID=L.ID  JOIN  LEVELS LL ON LL.DIVISION_ID= L.DIVISION_ID WHERE U.ID=?", new Object[] { appraisal.getUserId() });
							appraisalBean.setLevels(levels.toArray());
						}
						request.setAttribute("actionForm", appraisalBean);
					} else{
						// TODO get the detils for version 1
						AppraisalMinutiaeDao appraisalMinutiaeDao = AppraisalMinutiaeDaoFactory.create(conn);
						AppraisalMinutiae appraisalMinutiae = appraisalMinutiaeDao.findWhereAppraisalIdEquals(appraisal.getId());
						if (appraisalMinutiae == null){
							ArrayList<Appraisal> appraisals = new ArrayList<Appraisal>();
							appraisals.add(appraisal);
							setAppraisalMinutiae(appraisals, conn);
							appraisalMinutiae = appraisalMinutiaeDao.findByPrimaryKey(appraisal.getId());
						}
						Users user = UsersDaoFactory.create(conn).findByPrimaryKey(appraisal.getUserId());
						ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
						ProfileInfo userProfileInfo = profileInfoDao.findWhereUserIdEquals(appraisal.getUserId());
						ProfileInfo appraiserProfileInfo = profileInfoDao.findWhereUserIdEquals(appraisal.getAppraiser());
						appraisalMinutiae.setValues(userProfileInfo.getFirstName() + " " + userProfileInfo.getLastName(), userProfileInfo.getOfficalEmailId(), PortalUtility.getdd_MM_yyyy(userProfileInfo.getDateOfJoining()), user.getEmpId() + "", appraiserProfileInfo.getFirstName() + " " + appraiserProfileInfo.getLastName(), appraiserProfileInfo.getOfficalEmailId());
						HashMap<String, String> appmin = appraisalMinutiae.toHashMap();
						appmin.put("appraiser", appraisal.getAppraiser() + "");
						appmin.put("handler", appraisal.getHandler() + "");
						appmin.put("statusId", appraisal.getStatusId() + "");
						appmin.put("type", appraisal.getType());
						appraisalBean.setObj(appmin);
						// from inbox only esr_map_id will come, in that case need to send appraisal id to ui.
						appraisalBean.setId(appraisal.getId());
						appraisalBean.setRmgHandler(isRMGManager(login) == 1 ? userId + "" : "0");
						AppraisalProjectsDao appraisalProjectsDao = AppraisalProjectsDaoFactory.create(conn);
						HashMap<String, String>[] projects = appraisalProjectsDao.findAppraisalProjects(appraisal.getId());
						if ((projects == null || projects.length == 0)){
							if (TECHNICAL.equalsIgnoreCase(appraisal.getType())){
								result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.appraisal.noprojects"));
								break;
							} else{
								appraisalProjectsDao.insert(new AppraisalProjects(appraisal.getId(), "-", "-", new Date(), new Date(), ""));
								appmin.put("project", "");
							}
						} else{
							if (!TECHNICAL.equalsIgnoreCase(appraisal.getType())){
								appmin.put("project", projects[0].get("details"));
							} else{
								appraisalBean.setProjects(projects);
							}
						}
						AppraisalRatingsDao appraisalRatingsDao = AppraisalRatingsDaoFactory.create();
						if (TECHNICAL.equalsIgnoreCase(appraisal.getType()) && (appraisal.getStatus().equalsIgnoreCase(Status.PAFAVAILABLE) || appraisal.getStatus().equalsIgnoreCase(Status.REJECTEDBYAPPRAISER))) appraisalBean.setRatings(appraisalRatingsDao.findAllRatings(appraisal.getId()));
						else appraisalBean.setRatings(appraisalRatingsDao.findAppraisalRatings(appraisal.getId(), userId == appraisal.getUserId()));
						if (!TECHNICAL.equalsIgnoreCase(appraisal.getType()) && (appraisalBean.getRatings() == null || appraisalBean.getRatings().length == 0)){
							addSupportStaffQuestionsV1(appraisal.getId(), appraisalMinutiae.getDivision(), appraisalRatingsDao);
							appraisalBean.setRatings(appraisalRatingsDao.findAppraisalRatings(appraisal.getId(), userId == appraisal.getUserId()));
						}
						try{
							appraisalBean.setIdp(AppraisalIDPDaoFactory.create(conn).findWhereAppraisalIdEquals(appraisal.getId()).toHashMap(userId == appraisal.getUserId()));
						} catch (Exception e){
							logger.info("IDP record not yet created for this apraisal request" + appraisal.getId());
						}
						try{
							List<Object> userPhNo = JDBCUtiility.getInstance().getSingleColumn("SELECT PRIMARY_PHONE_NO FROM PERSONAL_INFO WHERE ID=(SELECT PERSONAL_ID FROM USERS WHERE ID=? )", new Object[] { appraisal.getUserId() });
							List<Object> aapPhNo = JDBCUtiility.getInstance().getSingleColumn("SELECT PRIMARY_PHONE_NO FROM PERSONAL_INFO WHERE ID=(SELECT PERSONAL_ID FROM USERS WHERE ID=?)", new Object[] { appraisal.getAppraiser() });
							if (userPhNo != null && userPhNo.size() == 1) appmin.put("phoneNo", (String) userPhNo.get(0));
							else appmin.put("phoneNo", "--");
							if (aapPhNo != null && aapPhNo.size() == 1) appmin.put("appraiserPhoneNo", (String) aapPhNo.get(0));
							else appmin.put("appraiserPhoneNo", "--");;
						} catch (Exception e){
							logger.error("", e);
						}
						request.setAttribute("actionForm", appraisalBean);
					}
				} catch (Exception e){
					logger.error("Unable to get the details of appraisal ID:" + appraisalBean.getId() + " OR ESR_MAP_ID : " + appraisalBean.getEsr_map_id(), e);
				} finally{
					ResourceManager.close(conn);
				}
				break;
			case RECEIVEPERIOD:
				dropdown = new DropDown();
				try{
					List<Object> periods = JDBCUtiility.getInstance().getSingleColumn("SELECT  DISTINCT(CONCAT(YEAR,'-',PERIOD)) FROM  APPRAISAL ", null);
					List<Object> periodReports = JDBCUtiility.getInstance().getSingleColumn("SELECT  DISTINCT(CONCAT(YEAR,'-',PERIOD)) FROM  APPRAISAL WHERE STATUS ='" + Status.COMPLETED + "'", null);
					AppraisalListBean[] periodslist = new AppraisalListBean[periods.size()];
					int i = 0;
					for (Object period : periods){
						String[] data = ((String) period).split("-");
						if (data.length == 2) periodslist[i++] = new AppraisalListBean(data[0], data[1], periodReports.contains(period));
					}
					dropdown.setDropDown(periodslist);
					request.setAttribute("actionForm", dropdown);
				} catch (Exception e){
					logger.error("", e);
				}
				break;
			case RECEIVEUSERS:
				dropdown = new DropDown();
				try{
					Appraisal appraisals[] = appraisalDao.findByDynamicWhere(" PERIOD=? AND YEAR=? ORDER BY APPRAISER", new Object[] { appraisalBean.getPeriod(), appraisalBean.getYear() });
					setAppraiserNames(appraisals, true);
					dropdown.setDropDown(appraisals);
				} catch (Exception e){
					logger.error("", e);
				}
				request.setAttribute("actionForm", dropdown);
				break;
			case RECEIVEUSERSBYAPPRAISER:
				try{
					Appraisal appraisals[] = appraisalDao.findByDynamicWhere(" PERIOD=? AND YEAR=? ORDER BY APPRAISER", new Object[] { appraisalBean.getPeriod(), appraisalBean.getYear() });
					setAppraiserNames(appraisals, true);
					request.setAttribute("actionForm", getusersByAppraiser(appraisals));
				} catch (Exception e){
					logger.error("", e);
					request.setAttribute("actionForm", new AppraisalsBean());
				}
				break;
			case APPRAISALREPORTS:
				dropdown = new DropDown();
				if (ModelUtiility.hasModulePermission(login, 59)){
					try{
						Appraisal appraisals[] = appraisalDao.findByDynamicWhere(" PERIOD=? AND YEAR=? AND STATUS=?", new Object[] { appraisalBean.getPeriod(), appraisalBean.getYear(), Status.COMPLETED });
						if (appraisals != null && appraisals.length > 0 && appraisals[0].getVersion() == 0) dropdown.setDropDown(setAppraisalReports(appraisals));
						else result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.appraisal.noreports"));
					} catch (Exception e){
						logger.error("", e);
					}
				} else{
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
				}
				request.setAttribute("actionForm", dropdown);
				break;
			case APPRAISALHRREPORTS:
				dropdown = new DropDown();
				if (ModelUtiility.hasModulePermission(login, 59)){
					try{
						Appraisal appraisals[] = appraisalDao.findByDynamicWhere(" PERIOD=? AND YEAR=? AND STATUS=?", new Object[] { appraisalBean.getPeriod(), appraisalBean.getYear(), Status.COMPLETED });
						if (appraisals != null && appraisals.length > 0 && appraisals[0].getVersion() == 0) dropdown.setDropDown(setAppraisalHRReports(appraisals));
						else result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.appraisal.noreports"));
					} catch (Exception e){
						logger.error("", e);
					}
				} else{
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
				}
				request.setAttribute("actionForm", dropdown);
				break;
			default:
				break;
		}
		return result;
	}

	private void addSupportStaffQuestionsV1(int appraisalId, String division, AppraisalRatingsDao dao) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(24);
		list.add(25);
		list.add(26);
		list.add(27);
		list.add(28);
		list.add(29);
		list.add(30);
		list.add(31);
		list.add(20);
		list.add(21);
		list.add(22);
		list.add(23);
		if (division.equalsIgnoreCase("Talent Acquisition")){
			list.add(32);
			list.add(33);
			list.add(34);
			list.add(35);
			list.add(36);
			list.add(37);
			list.add(38);
		} else if (division.equalsIgnoreCase("RMG")){
			list.add(39);
			list.add(40);
			list.add(41);
			list.add(42);
			list.add(43);
			list.add(44);
			list.add(45);
		} else if (division.equalsIgnoreCase("Finance")){
			list.add(41);
			list.add(46);
			list.add(47);
			list.add(48);
			list.add(49);
			list.add(50);
			list.add(52);
		} else if (division.equalsIgnoreCase("Operations")){
			list.add(41);
			list.add(51);
			list.add(52);
			list.add(53);
			list.add(54);
			list.add(55);
			list.add(56);
		} else /*if(division.equalsIgnoreCase("Business Development"))*/{
			list.add(57);
			list.add(58);
			list.add(59);
			list.add(60);
			list.add(61);
			list.add(62);
		}
		try{
			for (Integer entry : list){
				dao.insert(new AppraisalRatings(appraisalId, (short) entry.intValue(), (short) 0, (short) 0));
			}
		} catch (Exception e){}
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		try{
			Login login = Login.getLogin(request);
			int userId = login.getUserId().intValue();
			AppraisalBean appraisal = (AppraisalBean) form;
			AppraisalDao appraisalDao = AppraisalDaoFactory.create();
			Appraisal appraisalReq = appraisalDao.findByPrimaryKey(appraisal.getId());
			if (appraisalReq.getAppraiser() != userId && appraisalReq.getUserId() != userId){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
				return result;
			}
			switch (SaveTypes.getValue(form.getsType())) {
				case SAVE:
					if (appraisalReq.getVersion() == 0) saveApprisalDetails(result, appraisal, request);
					else if (appraisalReq.getVersion() == 1) saveApprisalDetailsV1(result, appraisal, appraisalReq, userId);
					break;
				case SUBMIT:
					if (appraisalReq.getVersion() == 0) saveApprisalDetails(result, appraisal, request);
					else if (appraisalReq.getVersion() == 1) saveApprisalDetailsV1(result, appraisal, appraisalReq, userId);
					if (appraisalReq.getEsr_map_id() == 0){
						EmpSerReqMapPk esrId = ModelUtiility.getInstance().createEmpSerReq(appraisalReq.getUserId(), "APP", 12);
						JDBCUtiility.getInstance().update("UPDATE APPRAISAL SET ESR_MAP_ID=? WHERE ID = ?", new Object[] { esrId.getId(), appraisal.getId() });
						appraisalReq.setEsr_map_id(esrId.getId());
					}
					if (result.getActionMessages().isEmpty()){
						JDBCUtiility.getInstance().update("UPDATE APPRAISAL SET STATUS=? WHERE ID = ?", new Object[] { Status.PAFSUBMITTED, appraisalReq.getId() });
						JDBCUtiility.getInstance().update("INSERT INTO APPRAISAL_REQ(APPRAISAL_ID, STATUS, COMMENTS, ACTION_BY) VALUES(?,?,?,?)", new Object[] { appraisalReq.getId(), Status.PAFSUBMITTED, "", userId });
						// sending mail
						ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
						ProfileInfo appraiserProfile = profileDao.findByDynamicWhere("ID = ( SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisalReq.getAppraiser() })[0];
						List<Object> userNameList = JDBCUtiility.getInstance().getSingleColumn("SELECT CONCAT(FIRST_NAME,' ',LAST_NAME) FROM PROFILE_INFO WHERE ID=(SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisalReq.getUserId() });
						String userName = (String) userNameList.get(0);
						List RMGManager = ModelUtiility.getInstance().getRMGManagerUserIds(login);
						if (!RMGManager.contains(appraisalReq.getHandler())) RMGManager.add(appraisalReq.getHandler());
						PortalMail pMail = sendMail(userName + " has submitted the PAF.", appraiserProfile.getFirstName() + " " + appraiserProfile.getLastName(), userName + " has submitted the PAF. Please complete the technical review on or before " + PortalUtility.getdd_MM_yyyy(appraisalReq.getAppraiserDueDate()), appraiserProfile.getOfficalEmailId(), profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN ("
								+ ModelUtiility.getCommaSeparetedValues(RMGManager) + "))"), null);
						String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
						JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID = ?", new Object[] { appraisalReq.getEsr_map_id() });
						new InboxModel().populateInboxForAppraisal(appraisalReq.getEsr_map_id(), pMail.getMailSubject(), Status.PAFSUBMITTED, appraisalReq.getAppraiser(), appraisalReq.getAppraiser(), userId, bodyText);
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
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		try{
			Login login = Login.getLogin(request);
			int userId = login.getUserId().intValue();
			AppraisalBean appraisal = (AppraisalBean) form;
			AppraisalDao appraisalDao = AppraisalDaoFactory.create();
			Appraisal appraisalReq = null;
			Set RMGManager = new HashSet(ModelUtiility.getInstance().getRMGManagerUserIds(login));;
			appraisalReq = appraisalDao.findByPrimaryKey(appraisal.getId());
			if (appraisalReq == null || (appraisalReq.getAppraiser() != userId && (appraisalReq.getStatus().equals(Status.PENDING_APPRAISER) || appraisalReq.getStatus().equals(Status.PAFSUBMITTED) || appraisalReq.getStatus().equals(Status.REJECTEDBYRMG))) || (appraisalReq.getStatus().equals(Status.PENDING_RMG) && appraisalReq.getHandler() != userId && !RMGManager.contains(userId))){
				logger.info("access denied for Appraisal ID :" + appraisal.getId() + " with login UserId:" + userId);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
				return result;
			}
			if (appraisalReq.getStatus().equals(Status.PENDING_RMG)){}
			if (appraisalReq.getEsr_map_id() == 0){
				EmpSerReqMapPk esrId = ModelUtiility.getInstance().createEmpSerReq(appraisalReq.getUserId(), "APP", 12);
				JDBCUtiility.getInstance().update("UPDATE APPRAISAL SET ESR_MAP_ID=? WHERE ID = ?", new Object[] { esrId.getId(), appraisal.getId() });
				appraisalReq.setEsr_map_id(esrId.getId());
			}
			switch (UpdateTypes.getValue(form.getuType())) {
				case ACCEPTED:
					if ((appraisalReq.getAppraiser() == userId && appraisalReq.getStatus().equals(Status.PAFSUBMITTED))){
						if (result.getActionMessages().isEmpty()){
							JDBCUtiility.getInstance().update("UPDATE APPRAISAL SET STATUS=? WHERE ID = ?", new Object[] { Status.INPROGRESS, appraisal.getId() });
							JDBCUtiility.getInstance().update("INSERT INTO APPRAISAL_REQ(APPRAISAL_ID, STATUS, COMMENTS, ACTION_BY) VALUES(?,?,?,?)", new Object[] { appraisal.getId(), Status.INPROGRESS, appraisal.getComments(), userId });
							JDBCUtiility.getInstance().update("UPDATE INBOX SET STATUS=? WHERE ESR_MAP_ID = ?", new Object[] { Status.INPROGRESS, appraisalReq.getEsr_map_id() });
						}
					}
					break;
				case APPROVE:
					if (!appraisalReq.getStatus().equals(Status.PENDING_RMG)){
						if (appraisalReq.getVersion() == 0) saveApprisalDetails(result, appraisal, request);
						else if (appraisalReq.getVersion() == 1) saveApprisalDetailsV1(result, appraisal, appraisalReq, userId);
					}
					if (result.getActionMessages().isEmpty()){
						String status = (appraisalReq.getStatus().equals(Status.PENDING_RMG)) ? Status.COMPLETED : Status.PENDING_RMG;
						JDBCUtiility.getInstance().update("UPDATE APPRAISAL SET STATUS=? WHERE ID = ?", new Object[] { status, appraisal.getId() });
						JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID = ?", new Object[] { appraisalReq.getEsr_map_id() });
						JDBCUtiility.getInstance().update("INSERT INTO APPRAISAL_REQ(APPRAISAL_ID, STATUS, COMMENTS, ACTION_BY) VALUES(?,?,?,?)", new Object[] { appraisal.getId(), status, appraisal.getComments(), userId });
						if (status.equals(Status.PENDING_RMG)){
							ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
							ProfileInfo handlerProfile = profileDao.findByDynamicWhere("ID = ( SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisalReq.getHandler() })[0];
							ProfileInfo appraiserProfile = profileDao.findByDynamicWhere("ID = ( SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisalReq.getAppraiser() })[0];
							ProfileInfo userProfile = profileDao.findByDynamicWhere("ID = ( SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisalReq.getUserId() })[0];
							String userName = userProfile.getFirstName() + " " + userProfile.getLastName();
							RMGManager.remove(new Integer(appraisalReq.getHandler()));
							String[] ccMailids = null;
							if (RMGManager.size() > 0) ccMailids = profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(RMGManager) + "))");
							PortalMail pMail = sendMail(appraiserProfile.getFirstName() + " " + appraiserProfile.getLastName() + " has submitted the PAF of " + userName, handlerProfile.getFirstName() + " " + handlerProfile.getLastName(), appraiserProfile.getFirstName() + " " + appraiserProfile.getLastName() + " has submitted the PAF of " + userName, handlerProfile.getOfficalEmailId(), ccMailids, null);
							String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
							RMGManager.add(new Integer(appraisalReq.getHandler()));
							InboxModel inbox = new InboxModel();
							for (Object handler : RMGManager){
								int handlerId = ((Integer) handler).intValue();
								inbox.populateInboxForAppraisal(appraisalReq.getEsr_map_id(), pMail.getMailSubject(), Status.PENDING_RMG, handlerId, handlerId, userId, bodyText);
							}
						} else if (status.equals(Status.COMPLETED)){
							ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
							ProfileInfo handlerProfile = profileDao.findByDynamicWhere("ID = ( SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisalReq.getHandler() })[0];
							ProfileInfo userProfile = profileDao.findByDynamicWhere("ID = ( SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisalReq.getUserId() })[0];
							String userName = userProfile.getFirstName() + " " + userProfile.getLastName();
							String[] ccMailids = null;
							RMGManager.add(new Integer(appraisalReq.getHandler()));
							InboxModel inbox = new InboxModel();
							if (appraisalReq.getVersion() > 0){
								AppraisalIDP appraisalIDP = AppraisalIDPDaoFactory.create().findWhereAppraisalIdEquals(appraisalReq.getId());
								StringBuffer messageBody = new StringBuffer();
								messageBody.append("Your IDP objectives to be achieved during the next assessment year are as follows.<br/><br/>");
								messageBody.append(appraisalIDP.getFinalObjectives());
								messageBody.append("<br/><br/>You will have an IDP review meeting after six months to discuss the progress on your goals. Please get in touch with your SPOC in case of any clarifications.<br/><br/>Thanks & Regards,<br/>");
								messageBody.append(handlerProfile.getFirstName() + " " + handlerProfile.getLastName());
								RMGManager.remove(new Integer(appraisalReq.getHandler()));
								RMGManager.add(userProfile.getReportingMgr());
								if (userProfile.getHrSpoc() > 3) RMGManager.add(userProfile.getHrSpoc());
								RMGManager.add(appraisalReq.getAppraiser());
								if (RMGManager.size() > 0) ccMailids = profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(RMGManager) + "))");
								PortalMail pMail = sendMail("IDP Objectives " + appraisalReq.getYear() + "-" + (Integer.parseInt(appraisalReq.getYear()) + 1), userName, messageBody.toString(), userProfile.getOfficalEmailId(), ccMailids, null);
								String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
								RMGManager.add(appraisalReq.getUserId());
								for (Object handler : RMGManager){
									int handlerId = ((Integer) handler).intValue();
									inbox.populateInboxForAppraisal(appraisalReq.getEsr_map_id(), pMail.getMailSubject(), Status.PENDING_RMG, 0, handlerId, userId, bodyText);
								}
							}
						}
					}
					break;
				case REJECTED:
					String status = (appraisalReq.getStatus().equals(Status.PENDING_RMG)) ? Status.REJECTEDBYRMG : Status.REJECTEDBYAPPRAISER;
					JDBCUtiility.getInstance().update("UPDATE APPRAISAL SET STATUS=? WHERE ID = ?", new Object[] { status, appraisal.getId() });
					JDBCUtiility.getInstance().update("DELETE FROM INBOX WHERE ESR_MAP_ID = ?", new Object[] { appraisalReq.getEsr_map_id() });
					JDBCUtiility.getInstance().update("INSERT INTO APPRAISAL_REQ(APPRAISAL_ID, STATUS, COMMENTS, ACTION_BY) VALUES(?,?,?,?)", new Object[] { appraisal.getId(), status, appraisal.getComments(), userId });
					if (status.equals(Status.REJECTEDBYAPPRAISER)){
						ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
						ProfileInfo userProfile = profileDao.findByDynamicWhere("ID = ( SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisalReq.getUserId() })[0];
						ProfileInfo appraiserProfile = profileDao.findByDynamicWhere("ID = ( SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisalReq.getAppraiser() })[0];
						String userName = userProfile.getFirstName() + " " + userProfile.getLastName();
						RMGManager.add(appraisalReq.getHandler());
						PortalMail pMail = sendMail("PAF is rejected by " + appraiserProfile.getFirstName() + " " + appraiserProfile.getLastName(), userName, "Your PAF is rejected, kindly resubmit.", userProfile.getOfficalEmailId(), profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(RMGManager) + "))"), appraisal.getComments());
						String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
						new InboxModel().populateInboxForAppraisal(appraisalReq.getEsr_map_id(), pMail.getMailSubject(), Status.REJECTEDBYAPPRAISER, 0, appraisalReq.getUserId(), appraisalReq.getAppraiser(), bodyText);
					} else{
						ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
						ProfileInfo handlerProfile = profileDao.findByDynamicWhere("ID = ( SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { userId })[0];
						ProfileInfo appraiserProfile = profileDao.findByDynamicWhere("ID = ( SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisalReq.getAppraiser() })[0];
						List<Object> userNameList = JDBCUtiility.getInstance().getSingleColumn("SELECT CONCAT(FIRST_NAME,' ',LAST_NAME) FROM PROFILE_INFO WHERE ID=(SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisalReq.getUserId() });
						String userName = (String) userNameList.get(0);
						RMGManager.add(appraisalReq.getHandler());
						RMGManager.remove(new Integer(userId));
						PortalMail pMail = sendMail(userName + "'s PAF is rejected by " + handlerProfile.getFirstName() + " " + handlerProfile.getLastName(), appraiserProfile.getFirstName() + " " + appraiserProfile.getLastName(), userName + "'s PAF is rejected, kindly resubmit.", appraiserProfile.getOfficalEmailId(), profileDao.findOfficalMailIdsWhere("WHERE ID IN ( SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(RMGManager) + "))"), appraisal.getComments());
						String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(pMail.getTemplateName()), pMail);
						new InboxModel().populateInboxForAppraisal(appraisalReq.getEsr_map_id(), pMail.getMailSubject(), Status.REJECTEDBYRMG, appraisalReq.getAppraiser(), appraisalReq.getAppraiser(), userId, bodyText);
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
		Attachements attachements = new Attachements();
		AppraisalDao appraisalDao = AppraisalDaoFactory.create();
		String path = null;
		if (ModelUtiility.hasModulePermission((Login) form.getObject(), 59)){
			try{
				AppraisalBean appraisal = (AppraisalBean) form;
				PortalData portalData = new PortalData();
				path = portalData.getfolder(portalData.getDirPath());
				File file = new File(path);
				if (!file.exists()) file.mkdir();
				switch (DownloadTypes.getValue(form.getdType())) {
					case APPRAISALHRDREPORTS:
						try{
							Appraisal appraisals[] = appraisalDao.findByDynamicWhere(" PERIOD=? AND YEAR=? AND STATUS=?", new Object[] { appraisal.getPeriod(), appraisal.getYear(), Status.COMPLETED });
							if (appraisals != null && appraisals.length > 0){
								switch (appraisals[0].getVersion()) {
									case 0:
										attachements.setFileName(new GenerateXls().generateXlsHRDReport(setAppraisalReports(appraisals), path + File.separator + "HRD_" + appraisal.getYear() + "_" + appraisal.getPeriod() + "_Reports.xls"));
										break;
									case 1:
										attachements.setFileName(new GenerateXls().appraisalReportV1(getAppraisalReportV1(appraisals), path + File.separator + "Appraisal_" + appraisal.getYear() + "_" + appraisal.getPeriod() + "_Reports.xls"));
								}
							}
						} catch (Exception e){
							logger.error("", e);
						}
						break;
					case APPRAISALHRREPORTS:
						try{
							Appraisal appraisals[] = appraisalDao.findByDynamicWhere(" PERIOD=? AND YEAR=? AND STATUS=?", new Object[] { appraisal.getPeriod(), appraisal.getYear(), Status.COMPLETED });
							if (appraisals != null && appraisals.length > 0 && appraisals[0].getVersion() == 0) attachements.setFileName(new GenerateXls().generateXlsHRReport(getAppraisalHRReports(appraisals), path + File.separator + "HR_" + appraisal.getYear() + "_" + appraisal.getPeriod() + "_Reports.xls"));
							else return null;
						} catch (Exception e){
							logger.error("", e);
						}
						break;
					default:
						break;
				}
				path = path + File.separator + attachements.getFileName();
				attachements.setFilePath(path);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return attachements;
	}

	private AppraisalReportV1[] getAppraisalReportV1(Appraisal[] appraisals) {
		List<AppraisalReportV1> list = new ArrayList<AppraisalReportV1>();
		AppraisalMinutiaeDao minutiaeDao = AppraisalMinutiaeDaoFactory.create();
		AppraisalIDPDao idpDao = AppraisalIDPDaoFactory.create();
		AppraisalProjectsDao projectsDao = AppraisalProjectsDaoFactory.create();
		AppraisalRatingsDao ratingsDao = AppraisalRatingsDaoFactory.create();
		for (Appraisal appraisal : appraisals){
			try{
				AppraisalReportV1 reportV1 = new AppraisalReportV1();
				reportV1.setId(ModelUtiility.getInstance().getEmployeeId(appraisal.getUserId()) + "");
				reportV1.setName(ModelUtiility.getInstance().getEmployeeName(appraisal.getUserId()));
				reportV1.setAppraiser(ModelUtiility.getInstance().getEmployeeName(appraisal.getAppraiser()));
				AppraisalMinutiae minutiae = minutiaeDao.findWhereAppraisalIdEquals(appraisal.getId());
				reportV1.setBillableDays(minutiae.getBillableDays() + "");
				reportV1.setDesignation(minutiae.getDesignation());
				AppraisalProjects[] projects = projectsDao.findWhereAppraisalIdEquals(appraisal.getId());
				StringBuffer location = new StringBuffer(), functionalArea = new StringBuffer(), projectType = new StringBuffer();
				for (AppraisalProjects project : projects){
					location.append(project.getClientName() + ",\n");
					functionalArea.append(project.getFunctionalArea() + ",\n");
					projectType.append(project.getProjectType() + ",\n");
				}
				reportV1.setProjectDetails(location.toString(), functionalArea.toString(), projectType.toString());
				AppraisalRatings[] ratings = ratingsDao.findWhereAppraisalIdEquals(appraisal.getId());
				int funcRatings = 0, funcRatingsCount = 0, perRatings = 0, perRatingsCount = 0;
				for (AppraisalRatings rating : ratings){
					if (rating.getQuestionId() > 10){
						perRatings += rating.getAppraiserRating();
						perRatingsCount++;
					} else{
						funcRatings += rating.getAppraiserRating();
						funcRatingsCount++;
					}
				}
				reportV1.setFunctionalAreaRatings(new DecimalFormat("0.0").format(funcRatings / funcRatingsCount));
				reportV1.setPersonalRatings(new DecimalFormat("0.0").format(perRatings / perRatingsCount));
				reportV1.setOveralScore(new DecimalFormat("0.0").format(((funcRatings / funcRatingsCount) * 0.7) + ((perRatings / perRatingsCount) * 0.3)));
				AppraisalIDP appraisalIDP = idpDao.findWhereAppraisalIdEquals(appraisal.getId());
				String objectvie = "";
				try{
					String objectives[] = appraisalIDP.getObjectives().split("~=~");
					objectvie = objectives[0] + " : " + objectives[4] + "\n" + objectives[1] + " : " + objectives[5] + "\n" + objectives[2] + " : " + objectives[6] + "\n" + objectives[3] + " : " + objectives[7];
				} catch (Exception e){
					objectvie = appraisalIDP.getObjectives();
				}
				reportV1.setIDPValues(appraisalIDP.getCareerPlan(), objectvie, appraisalIDP.getSmartGoals(), appraisalIDP.getFinalObjectives(), appraisalIDP.getComments());
				list.add(reportV1);
			} catch (AppraisalMinutiaeDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AppraisalProjectsDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AppraisalRatingsDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AppraisalIDPDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list.toArray(new AppraisalReportV1[list.size()]);
	}

	@Override
	public Integer[] upload(List<FileItem> fileItems, String docType, int id, HttpServletRequest request, String description) {
		if (fileItems != null && !fileItems.isEmpty()){
			FileItem file = (FileItem) fileItems.get(0);
			InputStream stream = null;
			int year = 0;
			String month = null;
			try{
				month = file.getName().substring(0, file.getName().indexOf("."));
				year = Integer.parseInt(month.split("_")[0]);
				month = month.split("_")[1];
				stream = file != null ? file.getInputStream() : null;
			} catch (Exception e1){
				e1.printStackTrace();
			}
			if (year == 0 || month == null){
				request.setAttribute("APPRAISAL_MESSAGE", "please rename file like Ex:2013_September");
				return new Integer[] { 0 };
			}
			if (stream != null) try{
				Vector<Vector<Object>> list = POIParser.parseXls(stream, 0);
				stream.close();
				if (list != null && !list.isEmpty()){
					AppraisalDao appraisalDao = AppraisalDaoFactory.create();
					Appraisal appraisals[] = appraisalDao.findByDynamicWhere(" PERIOD=? AND YEAR=?", new Object[] { month, year });
					if (appraisals == null || appraisals.length == 0 || appraisals[0].getVersion() == 0){
						request.setAttribute("APPRAISAL_MESSAGE", "please rename file like Ex:2013_September\nOR\nThere is no appraisal term for " + month + " " + year);
						return new Integer[] { 0 };
					}
					Map<Integer, Integer> userEmp = UsersDaoFactory.create().findAllUsersEmployeeIds(true);
					Map<Integer, Integer> appraisalsMap = new HashMap<Integer, Integer>();
					for (Appraisal appraisal : appraisals)
						appraisalsMap.put(userEmp.get(appraisal.getUserId()), appraisal.getId());
					switch (DocumentTypes.valueOf(docType)) {
						case APPRAISAL_PROJECTS:{
							Connection conn = ResourceManager.getConnection();
							conn.setAutoCommit(false);
							try{
				//				JDBCUtiility.getInstance().update("DELETE FROM APPRAISAL_PROJECTS WHERE APPRAISAL_ID IN (" + ModelUtiility.getCommaSeparetedValues(appraisalsMap.values()) + ")", null, conn);
								AppraisalProjectsDao projectsDao = AppraisalProjectsDaoFactory.create(conn);
								UsersDao userDao=UsersDaoFactory.create(conn);
								Set<Integer> empIds=new HashSet<Integer>();
								for (Vector<Object> data : list){
									if (appraisalsMap.containsKey((int) Float.parseFloat(data.get(0).toString()))){
										empIds.add((int) Float.parseFloat(data.get(0).toString()));
									}
								}
								
								for(Integer i:empIds)
								{
									Users[] user=userDao.findWhereEmpIdEquals(i);
									if(user!=null && user.length>0){
										Appraisal appraisals1[] = appraisalDao.findByDynamicWhere(" PERIOD=? AND YEAR=? AND USER_ID=?", new Object[] { month, year,user[0].getId()  });	
										if(appraisals1!=null && appraisals1.length>0){
											AppraisalProjects[] ap=projectsDao.findByDynamicWhere("APPRAISAL_ID=?", new Object[] { appraisals1[0].getId()});
											for(AppraisalProjects pro:ap)
											JDBCUtiility.getInstance().update("DELETE FROM APPRAISAL_PROJECTS WHERE APPRAISAL_ID ="+pro.getAppraisalId(), null, conn);
											
										}	
									}
								}
								
								for (Vector<Object> data : list){
									if (appraisalsMap.containsKey((int) Float.parseFloat(data.get(0).toString()))){
										
									/*	Users[] user=userDao.findWhereEmpIdEquals((int) Float.parseFloat(data.get(0).toString()));
										if(user!=null && user.length>0){
											Appraisal appraisals1[] = appraisalDao.findByDynamicWhere(" PERIOD=? AND YEAR=? AND USER_ID=?", new Object[] { month, year,user[0].getId()  });	
											if(appraisals1!=null && appraisals1.length>0){
												AppraisalProjects[] ap=projectsDao.findByDynamicWhere("APPRAISAL_ID=?", new Object[] { appraisals1[0].getId()});
												for(AppraisalProjects pro:ap)
												JDBCUtiility.getInstance().update("DELETE FROM APPRAISAL_PROJECTS WHERE APPRAISAL_ID ="+pro.getAppraisalId(), null, conn);
												
											}	
										}*/
										
										logger.info("inserting projects for employee " + data.get(0));
										projectsDao.insert(new AppraisalProjects(appraisalsMap.get((int) Float.parseFloat(data.get(0).toString())), data.get(1).toString(), data.get(2).toString(), (Date) data.get(3), (Date) data.get(4)));
									}
								}
								conn.commit();
								logger.info("commited appraisal projects for  " + month + " " + year);
							} catch (Exception e){
								logger.info("rolling back appraisal projects for  " + month + " " + year);
								conn.rollback();
								logger.error(e.getMessage(), e);
								throw new Exception(e.getMessage());
							} finally{
								ResourceManager.close(conn);
							}
						}
							request.setAttribute("APPRAISAL_MESSAGE", "Project details uploaded.");
							break;
						case BILLABLE_DAYS:{
							Connection conn = ResourceManager.getConnection();
							conn.setAutoCommit(false);
							try{
								for (Vector<Object> data : list){
									logger.info("updating appraisal billable days for  " + data.get(0));
									JDBCUtiility.getInstance().update("UPDATE APPRAISAL_MINUTIAE SET BILLABLE_DAYS=? WHERE APPRAISAL_ID=?", new Object[] { (int) Float.parseFloat(data.get(1).toString()), appraisalsMap.get((int) Float.parseFloat(data.get(0).toString())) }, conn);
								}
								conn.commit();
								logger.info("commited appraisal billable days for  " + month + " " + year);
							} catch (Exception e){
								logger.info("rolling back appraisal billable days for  " + month + " " + year);
								conn.rollback();
								logger.error(e.getMessage(), e);
								throw new Exception(e.getMessage());
							} finally{
								ResourceManager.close(conn);
							}
						}
							request.setAttribute("APPRAISAL_MESSAGE", "Billable days uploaded.");
							break;
						default:
							break;
					}
				}
			} catch (ClassCastException e){
				request.setAttribute("APPRAISAL_MESSAGE", "unable to update data. Please All from and to dates are in date format then try again.");
				logger.error(e.getMessage(), e);
			} catch (Exception e){
				request.setAttribute("APPRAISAL_MESSAGE", "unable to update data.Please try again.\n Cause:" + e.getMessage());
				logger.error(e.getMessage(), e);
			}
		}
		return new Integer[] { 0 };
	}

	private AppraisalUserDetails getAppraisalUserDetails(Appraisal appraisal) {
		AppraisalUserDetails userDetails = null;
		try{
			ProfileInfo userProfile = ProfileInfoDaoFactory.create().findByDynamicWhere("ID = ( SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisal.getUserId() })[0], appraiserProfile = ProfileInfoDaoFactory.create().findByDynamicWhere("ID = ( SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisal.getAppraiser() })[0];
			userDetails = new AppraisalUserDetails(userProfile.getFirstName() + " " + userProfile.getLastName(), userProfile.getOfficalEmailId(), PortalUtility.getdd_MM_yyyy(userProfile.getDateOfJoining()), appraiserProfile.getFirstName() + " " + appraiserProfile.getLastName(), appraiserProfile.getOfficalEmailId());
			setEmployeeDetails(userDetails, userProfile);
			userDetails.setReviewPeriod(appraisal.getYear() + "-" + appraisal.getPeriod());
			List<Object> prevExp = JDBCUtiility.getInstance().getSingleColumn("SELECT TIMESTAMPDIFF(MONTH,DATE_JOINING,DATE_RELIEVING) FROM EXPERIENCE_INFO WHERE ID=(SELECT EXPERIENCE_ID FROM USERS WHERE ID=?)", new Object[] { appraisal.getUserId() });
			List<Object> currExp = JDBCUtiility.getInstance().getSingleColumn("SELECT TIMESTAMPDIFF(MONTH,DATE_OF_JOINING,NOW()) FROM PROFILE_INFO WHERE ID=(SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisal.getUserId() });
			List<Object> userPhNo = JDBCUtiility.getInstance().getSingleColumn("SELECT PRIMARY_PHONE_NO FROM PERSONAL_INFO WHERE ID=(SELECT PERSONAL_ID FROM USERS WHERE ID=? )", new Object[] { appraisal.getUserId() });
			List<Object> empId = JDBCUtiility.getInstance().getSingleColumn("SELECT EMP_ID FROM USERS WHERE ID=?", new Object[] { appraisal.getUserId() });
			List<Object> aapPhNo = JDBCUtiility.getInstance().getSingleColumn("SELECT PRIMARY_PHONE_NO FROM PERSONAL_INFO WHERE ID=(SELECT PERSONAL_ID FROM USERS WHERE ID=?)", new Object[] { appraisal.getAppraiser() });
			if (userPhNo != null && userPhNo.size() == 1) userDetails.setPhoneNo((String) userPhNo.get(0));
			else userDetails.setPhoneNo("--");
			if (aapPhNo != null && aapPhNo.size() == 1) userDetails.setAppraiserphoneNo((String) aapPhNo.get(0));
			else userDetails.setAppraiserphoneNo("--");
			if (empId != null && empId.size() == 1 && empId.get(0) != null) userDetails.setEmployeeId(((Integer) empId.get(0)).intValue() + "");
			int currentExp = 0, previousExp = 0;
			if (currExp != null && currExp.size() == 1 && currExp.get(0) != null) currentExp = ((Long) currExp.get(0)).intValue();
			if (prevExp != null && prevExp.size() == 1 && prevExp.get(0) != null) previousExp = ((Long) prevExp.get(0)).intValue();
			userDetails.setExperiance((currentExp / 12) + "." + (currentExp % 12));
			userDetails.setTotalExp(((currentExp + previousExp) / 12) + "." + ((currentExp + previousExp) % 12));
		} catch (Exception e){
			logger.error("", e);
		}
		return userDetails;
	}

	private void setEmployeeDetails(AppraisalUserDetails userDetails, ProfileInfo profileInfoDto) {
		try{
			Levels level = LevelsDaoFactory.create().findByPrimaryKey(profileInfoDto.getLevelId());
			Divison division = DivisonDaoFactory.create().findByPrimaryKey(level.getDivisionId());
			userDetails.setDesignation(level.getDesignation());
			userDetails.setDivision(division.getName());
		} catch (Exception e){
			logger.error("Unable to get designation & division for profile Id" + profileInfoDto.getId(), e);
		}
	}

	private String[] getEmployeeDetails(int userId) {
		try{
			String divisionName = "N.A", departmentName = "N.A";
			DivisonDao divisionDao = DivisonDaoFactory.create();
			Divison division = null, divisions[] = divisionDao.findByDynamicWhere(" ID = ( SELECT DIVISION_ID FROM LEVELS WHERE ID = (SELECT LEVEL_ID FROM PROFILE_INFO WHERE ID = ( SELECT PROFILE_ID FROM USERS WHERE ID= ? ) ) )", new Object[] { userId });
			if (divisions != null && divisions.length >= 1) division = divisions[0];
			if (division != null){
				if (division.getParentId() != 0){
					divisionName = division.getName();
					while (division.getParentId() != 0){
						division = divisionDao.findByPrimaryKey(division.getParentId());
					}
					if (division != null) departmentName = division.getName();
				} else departmentName = division.getName();
			}
			return new String[] { departmentName, divisionName };
		} catch (Exception e){}
		return new String[] { "N.A", "N.A" };
	}

	private void setAppraiserNames(Appraisal[] appraisals, boolean details, boolean setSubDates) {
		HashMap<Integer, String> userNames = new HashMap<Integer, String>();
		for (Appraisal appraisal : appraisals){
			if (!userNames.containsKey(appraisal.getAppraiser()) || !userNames.containsKey(appraisal.getHandler()) || !userNames.containsKey(appraisal.getUserId())){
				for (Integer uIds : new Integer[] { appraisal.getAppraiser(), appraisal.getUserId(), appraisal.getHandler() }){
					if (userNames.containsKey(uIds)) continue;
					// if (appraisal.getHandler() == uIds.intValue() &&
					// !details) break;
					List<Object> userName = JDBCUtiility.getInstance().getSingleColumn("SELECT CONCAT(FIRST_NAME,' ',LAST_NAME) FROM PROFILE_INFO WHERE ID=(SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { uIds });
					if (userName != null && !userName.isEmpty()){
						userNames.put(uIds, (String) userName.get(0));
					} else userNames.put(uIds, " ");
				}
			}
			appraisal.setAppraiserName(userNames.get(appraisal.getAppraiser()));
			appraisal.setAppraiseeName(userNames.get(appraisal.getUserId()));
			appraisal.setHandlerName(userNames.get(appraisal.getHandler()));
			if (details){
				String dep[] = getEmployeeDetails(appraisal.getUserId());
				appraisal.setDepartment(dep[0]);
				appraisal.setDivision(dep[1]);
			}
			if (setSubDates){
				List<Object> appraiseeSubDate = JDBCUtiility.getInstance().getSingleColumn("SELECT ACTIONON FROM APPRAISAL_REQ WHERE ID = ( SELECT MAX(ID) FROM APPRAISAL_REQ WHERE APPRAISAL_ID = ? AND STATUS=? )  ", new Object[] { appraisal.getId(), Status.PAFSUBMITTED });
				if (appraiseeSubDate != null && !appraiseeSubDate.isEmpty() && appraiseeSubDate.get(0) != null) appraisal.setAppraiseeSubDate(PortalUtility.getdd_MM_yyyy((Date) appraiseeSubDate.get(0)));
				List<Object> appraiserSubDate = JDBCUtiility.getInstance().getSingleColumn("SELECT ACTIONON FROM APPRAISAL_REQ WHERE ID = ( SELECT MAX(ID) FROM APPRAISAL_REQ WHERE APPRAISAL_ID = ? AND STATUS=? )  ", new Object[] { appraisal.getId(), Status.PENDING_RMG });
				if (appraiserSubDate != null && !appraiserSubDate.isEmpty() && appraiserSubDate.get(0) != null) appraisal.setAppraiserSubDate(PortalUtility.getdd_MM_yyyy((Date) appraiserSubDate.get(0)));
			}
		}
	}

	private void setAppraiserNames(Appraisal[] appraisals, boolean details) {
		setAppraiserNames(appraisals, details, false);
	}

	private void saveApprisalDetailsV1(ActionResult result, AppraisalBean appraisal, Appraisal appraisalReq, int userId) {
		Connection conn = null;
		try{
			if (userId != appraisalReq.getUserId() && userId != appraisalReq.getAppraiser()) return;
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			// updating the ratings
			if (appraisal.getRatings() != null && appraisal.getRatings().length > 0){
				AppraisalRatingsDao ratingsDao = AppraisalRatingsDaoFactory.create(conn);
				AppraisalRatings[] ratings = ratingsDao.findWhereAppraisalIdEquals(appraisalReq.getId());
				Map<Integer, AppraisalRatings> ratingsMap = new HashMap<Integer, AppraisalRatings>();
				for (AppraisalRatings rating : ratings)
					ratingsMap.put(rating.getQuestionId().intValue(), rating);
				Set<Integer> updatedSet = new HashSet<Integer>();
				for (Object obj : appraisal.getRatings()){
					try{
						String data[] = ((String) obj).split("~=~");
						if (data != null && data.length == 3){
							int id = Integer.parseInt(data[0]);
							if (ratingsMap.containsKey(id)){
								AppraisalRatings rating = ratingsMap.get(id);
								if (userId == appraisalReq.getUserId()) rating.setAppraiseeRating(Short.parseShort(data[1]));
								else if (userId == appraisalReq.getAppraiser()) rating.setAppraiserRating(Short.parseShort(data[2]));
								ratingsDao.update(rating.createPk(), rating);
								updatedSet.add(rating.getQuestionId().intValue());
							}
							if (!updatedSet.contains(id)){
								ratingsDao.insert(new AppraisalRatings(appraisalReq.getId(), (short) id, Short.parseShort(data[1]), Short.parseShort(data[2])));
							}
						}
					} catch (Exception e){}
				}
				for (AppraisalRatings rating : ratings){
					if (!updatedSet.contains(rating.getQuestionId().intValue())){
						logger.info("deleting Appraisal Ratings record " + rating);
						ratingsDao.delete(rating.createPk());
					}
				}
			}
			conn.commit();
			// updating project details
			if (userId == appraisalReq.getUserId() && appraisal.getProjects() != null){
				AppraisalProjectsDao projectsDao = AppraisalProjectsDaoFactory.create(conn);
				AppraisalProjects projects[] = projectsDao.findWhereAppraisalIdEquals(appraisalReq.getId());
				if (!TECHNICAL.equalsIgnoreCase(appraisalReq.getType())){
					try{
						projects[0].setDetails((String) appraisal.getProjects()[0]);
						projectsDao.update(projects[0].createPk(), projects[0]);
					} catch (Exception e){
						logger.error("unable to update support Appraisal project details : " + appraisal.getProjects());
					}
				} else{
					Map<Integer, AppraisalProjects> projectsMap = new HashMap<Integer, AppraisalProjects>();
					for (AppraisalProjects project : projects)
						projectsMap.put(project.getId(), project);
					for (Object obj : appraisal.getProjects()){
						try{
							String data[] = ((String) obj).split("~=~");
							if (data != null && data.length == 4){
								int id = Integer.parseInt(data[0]);
								if (projectsMap.containsKey(id)){
									AppraisalProjects project = projectsMap.get(id);
									project.setFunctionalArea(data[1]);
									project.setProjectType(data[2]);
									project.setDetails(data[3]);
									projectsDao.update(project.createPk(), project);
								}
							}
						} catch (Exception e){
							logger.error("unable to update Appraisal project details : " + obj);
						}
					}
				}
			}
			conn.commit();
			// updating idp values.
			AppraisalIDPDao idpDao = AppraisalIDPDaoFactory.create(conn);
			AppraisalIDP idp = idpDao.findWhereAppraisalIdEquals(appraisalReq.getId());
			if (idp == null){
				if (userId != appraisalReq.getUserId()){
					logger.error("there is no idp record and appraisar trying to approve it.... \n this requires debug to fix the issue. " + appraisal);
					return;
				}
				idp = new AppraisalIDP(appraisalReq.getId(), appraisal.getCareerPlan(), appraisal.getObjectives(), appraisal.getSmartGoals(), "", "", appraisal.getProjectAchievements());
				idpDao.insert(idp);
			} else{
				if (userId == appraisalReq.getUserId()){
					idp.setValues(appraisal.getCareerPlan(), appraisal.getObjectives(), appraisal.getSmartGoals(), idp.getFinalObjectives(), idp.getComments(), appraisal.getProjectAchievements());
				} else{
					idp.setFinalObjectives(appraisal.getFinalObjectives());
					idp.setComments(appraisal.getComments());
				}
				idpDao.update(idp.createPk(), idp);
			}
			conn.commit();
		} catch (Exception e){
			try{
				conn.rollback();
			} catch (SQLException e1){}
			logger.error("error while updating appraisal details:" + appraisal, e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
		} finally{
			ResourceManager.close(conn);
		}
	}

	private void saveApprisalDetails(ActionResult result, AppraisalBean appraisal, HttpServletRequest request) {
		if (AppraisalDetailsDaoImpl.getInstance().updateAppraisaldetails(appraisal) == 0){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
		}
	}

	private void updateApprisalusers(ActionResult result, AppraisalBean appraisal, HttpServletRequest request, boolean update) {
		if (appraisal.getAppraiseeDueDate() == null || appraisal.getAppraiserDueDate() == null){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
			return;
		}
		if ((appraisal.getAppraisal() == null && !update)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.appraisal.noappraisees"));
			return;
		}
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			AppraisalDao appraisalDao = AppraisalDaoFactory.create(conn);
			Appraisal appraisals[] = appraisalDao.findByDynamicWhere(" PERIOD=? AND YEAR=? ", new Object[] { appraisal.getPeriod(), appraisal.getYear() });
			if (!update && appraisals.length > 0){
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.appraisals.periodExists", appraisal.getYear(), appraisal.getPeriod()));
				return;
			}
			ArrayList<Integer> updatedlist = new ArrayList<Integer>();
			ArrayList<Appraisal> addedlist = new ArrayList<Appraisal>();
			ArrayList<Appraisal> totalAppraisalList = new ArrayList<Appraisal>();
			if (appraisal.getAppraisal() != null) for (String entry : appraisal.getAppraisal()){
				try{
					String[] list = entry.split("~=~");
					if (list != null && list.length == 3){
						int appraisee = Integer.parseInt(list[0]);
						int appraiser = Integer.parseInt(list[1]);
						String type = list[2];
						Appraisal newAppraisal = getAppraisal(appraisals, appraisee);
						if (newAppraisal == null){
							newAppraisal = new Appraisal(0, appraisee, appraiser, Integer.parseInt(appraisal.getHandler()), type, appraisal.getPeriod(), Status.PAFAVAILABLE, new Date(), appraisal.getYear(), appraisal.getAppraiseeDueDate(), appraisal.getAppraiserDueDate(), CURRENT_VERSION);
							newAppraisal.setStartDate(appraisal.getStartDate());
							appraisalDao.insert(newAppraisal);
							addedlist.add(newAppraisal);
						} else{
							newAppraisal.setAppraisal(appraisee, appraiser, Integer.parseInt(appraisal.getHandler()), type, appraisal.getAppraiseeDueDate(), appraisal.getAppraiserDueDate());
							newAppraisal.setStartDate(appraisal.getStartDate());
							appraisalDao.update(new AppraisalPk(newAppraisal.getId()), newAppraisal);
						}
						totalAppraisalList.add(newAppraisal);
						updatedlist.add(appraisee);
					}
				} catch (Exception e){
					logger.error("Unable to update Appraisal Details. " + entry, e);
				}
			}
			if (update){
				List<Integer> toDelete = new ArrayList<Integer>();
				for (Appraisal app : appraisals)
					if (!updatedlist.contains(app.getUserId())) toDelete.add(app.getId());
				if (!toDelete.isEmpty()){
					JDBCUtiility.getInstance().update("DELETE FROM APPRAISAL WHERE ID IN ( " + ModelUtiility.getCommaSeparetedValues(toDelete) + ")", null, conn);
				}
			}
			setAppraisalMinutiae(totalAppraisalList, conn);
			conn.commit();
			Appraisal ret[] = new Appraisal[addedlist.size()];
			addedlist.toArray(ret);
			notifyPafAavailable(ret);
		} catch (Exception e){
			logger.error("Unable to update Appraisal Details. ", e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
		} finally{
			ResourceManager.close(conn);
		}
	}

	private void setAppraisalMinutiae(ArrayList<Appraisal> appraisals, Connection conn) {
		AppraisalMinutiaeDao appraisalMinutiaeDao = AppraisalMinutiaeDaoFactory.create(conn);
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create(conn);
		for (Appraisal appraisal : appraisals){
			if (appraisal.getVersion() == 1){
				AppraisalUserDetails userDetails = null;
				try{
					ProfileInfo userProfile = profileInfoDao.findByDynamicWhere("ID = ( SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisal.getUserId() })[0];
					userDetails = new AppraisalUserDetails();
					setEmployeeDetails(userDetails, userProfile);
					String[] exp = new ExperienceInfoModel().getExperianceDetails(appraisal.getUserId());
					AppraisalMinutiae appraisalMinutiae = new AppraisalMinutiae(appraisal.getId(), userDetails.getDesignation(), userDetails.getDivision(), ExperienceInfoModel.formatExperience(exp[0], false), ExperienceInfoModel.formatExperience(exp[2], false), getNextMonth(appraisal.getPeriod()) + " " + (Integer.parseInt(appraisal.getYear()) - 1) + "-" + appraisal.getPeriod() + " " + appraisal.getYear());
					AppraisalMinutiae appMinutiae = appraisalMinutiaeDao.findWhereAppraisalIdEquals(appraisal.getId());
					if (appMinutiae == null){
						appraisalMinutiaeDao.insert(appraisalMinutiae);
					} else{
						appraisalMinutiae.setId(appMinutiae.getId());
						appraisalMinutiae.setBillableDays(appMinutiae.getBillableDays());
						appraisalMinutiaeDao.update(appraisalMinutiae.createPk(), appraisalMinutiae);
					}
				} catch (Exception e){
					logger.error("", e);
				}
			}
		}
	}

	private String getNextMonth(String period) {
		if (period.equalsIgnoreCase("January")) return "February";
		if (period.equalsIgnoreCase("February")) return "March";
		if (period.equalsIgnoreCase("March")) return "April";
		if (period.equalsIgnoreCase("April")) return "May";
		if (period.equalsIgnoreCase("May")) return "June";
		if (period.equalsIgnoreCase("June")) return "July";
		if (period.equalsIgnoreCase("July")) return "August";
		if (period.equalsIgnoreCase("August")) return "September";
		if (period.equalsIgnoreCase("September")) return "October";
		if (period.equalsIgnoreCase("October")) return "November";
		if (period.equalsIgnoreCase("November")) return "December";
		if (period.equalsIgnoreCase("December")) return "January";
		return null;
	}

	private void removeApprisalusers(ActionResult result, AppraisalBean appraisal, HttpServletRequest request) {
		if (appraisal.getAppraisal() == null) return;
		try{
			JDBCUtiility.getInstance().update("DELETE FROM APPRAISAL WHERE USER_ID IN ( " + ModelUtiility.getCommaSeparetedValues(appraisal.getAppraisal()) + ") AND PERIOD=? AND YEAR=? ", new Object[] { appraisal.getPeriod(), appraisal.getYear() });
		} catch (Exception e){
			logger.error("Unable to delete Appraisal Details. ", e);
		}
	}

	private Appraisal getAppraisal(Appraisal appraisals[], int userId) {
		for (Appraisal app : appraisals)
			if (app.getUserId() == userId) return app;
		return null;
	}

	public AppraisalReport[] setAppraisalReports(Appraisal[] appraisals) {
		if (appraisals == null) return null;
		AppraisalReport reports[] = new AppraisalReport[appraisals.length];
		int count = 0;
		HashMap<Integer, String> userNames = new HashMap<Integer, String>();
		for (Appraisal appraisal : appraisals){
			try{
				AppraisalReport report = new AppraisalReport();
				AppraisalBean appraisalBean = AppraisalDetailsDaoImpl.getInstance().getAppraisaldetails(appraisal.getId());
				getAppraiserName(appraisal, userNames);
				report.setName(userNames.get(appraisal.getUserId()));
				List<Object> designation = JDBCUtiility.getInstance().getSingleColumn("SELECT DESIGNATION FROM LEVELS WHERE ID = (SELECT LEVEL_ID FROM PROFILE_INFO WHERE ID = ( SELECT PROFILE_ID FROM USERS WHERE ID= ? )) ", new Object[] { appraisal.getUserId() });
				if (designation != null && designation.size() >= 1 && designation.get(0) != null) report.setDesignation((String) designation.get(0));
				report.setNewDesignation(appraisalBean.getProm_promotedAs());
				double perRating = getRating(appraisalBean.getPers_ratings());
				report.setPersonalRating(new DecimalFormat("0").format(perRating));
				double proRating = getRating(appraisalBean.getProf_ratings());
				report.setProfessionalRating(new DecimalFormat("0").format(proRating));
				report.setOverallScore(new DecimalFormat("0").format(proRating * 0.7 + perRating * 0.3));
				report.setTotalProjectDays(appraisalBean.getProj_totalDays());
				StringBuffer appraiseeComments = new StringBuffer();
				String lineBreak = "\n\n";
				appraiseeComments.append(appraisalBean.getIndi_goals());
				appraiseeComments.append(lineBreak);
				appraiseeComments.append(appraisalBean.getIndi_time());
				appraiseeComments.append(lineBreak);
				appraiseeComments.append(appraisalBean.getIndi_plan());
				appraiseeComments.append(lineBreak);
				appraiseeComments.append(appraisalBean.getIndi_strengths());
				appraiseeComments.append(lineBreak);
				appraiseeComments.append(appraisalBean.getIndi_opportunities());
				appraiseeComments.append(lineBreak);
				appraiseeComments.append(appraisalBean.getIndi_timeBound());
				appraiseeComments.append(lineBreak);
				appraiseeComments.append(appraisalBean.getIndi_gaps());
				appraiseeComments.append(lineBreak);
				appraiseeComments.append(appraisalBean.getIndi_skils());
				appraiseeComments.append(lineBreak);
				appraiseeComments.append(appraisalBean.getIndi_performance());
				appraiseeComments.append(lineBreak);
				appraiseeComments.append(appraisalBean.getIndi_advancement());
				report.setAppraiseeComments(appraiseeComments.toString());
				report.setAppraiserComments(appraisalBean.getIndi_roadMap());
				report.setPromotability(appraisalBean.getProm_promotable());
				report.setHowSoon(appraisalBean.getProm_howSoon());
				report.setAppraiser(userNames.get(appraisal.getAppraiser()));
				report.setPromotedas(appraisalBean.getProm_promotedAs());
				List<Object> actionOn = JDBCUtiility.getInstance().getSingleColumn("SELECT ACTIONON FROM APPRAISAL_REQ WHERE ID = ( SELECT MAX(ID) FROM APPRAISAL_REQ WHERE APPRAISAL_ID = ?)  ", new Object[] { appraisal.getId() });
				if (actionOn != null && !actionOn.isEmpty() && actionOn.get(0) != null) report.setCompletionDate(PortalUtility.getdd_MM_yyyy((Date) actionOn.get(0)));
				else report.setCompletionDate(PortalUtility.getdd_MM_yyyy(new Date()));
				report.setSuggestions(appraisalBean.getProm_comments());
				report.setId(count + 1 + "");
				reports[count++] = report;
			} catch (Exception e){
				logger.error("", e);
			}
		}
		return reports;
	}

	public AppraisalHRReport[] setAppraisalHRReports(Appraisal[] appraisals) {
		if (appraisals == null) return null;
		AppraisalHRReport reports[] = new AppraisalHRReport[appraisals.length];
		int count = 0;
		HashMap<Integer, String> userNames = new HashMap<Integer, String>();
		for (Appraisal appraisal : appraisals){
			try{
				AppraisalHRReport report = new AppraisalHRReport();
				AppraisalBean appraisalBean = AppraisalDetailsDaoImpl.getInstance().getAppraisaldetails(appraisal.getId());
				appraisal.setAppraiser(0);
				getAppraiserName(appraisal, userNames);
				report.setObj(appraisal);
				String name = userNames.get(appraisal.getUserId());
				try{
					List<Object> empId = JDBCUtiility.getInstance().getSingleColumn("SELECT EMP_ID FROM USERS WHERE ID=? AND EMP_ID IS NOT NULL", new Object[] { appraisal.getUserId() });
					name += "(" + ((Integer) empId.get(0)).intValue() + ")";
				} catch (Exception e){
					logger.error("", e);
				}
				report.setName(name);
				report.setComments(appraisalBean.getFeed_comments());
				StringBuffer appraiseeComments = new StringBuffer();
				int i = 0;
				for (String row : appraisalBean.getFeed_ratings()){
					if (i != 0) appraiseeComments.append("~=~");
					appraiseeComments.append(row.split("~=~")[0]);
					i++;
				}
				report.setRatings(appraiseeComments.toString());
				report.setId(count + 1 + "");
				reports[count++] = report;
			} catch (Exception e){}
		}
		return reports;
	}

	public String[][] getAppraisalHRReports(Appraisal[] appraisals) {
		if (appraisals == null) return null;
		String reports[][] = new String[appraisals.length][];
		int count = 0;
		HashMap<Integer, String> userNames = new HashMap<Integer, String>();
		for (Appraisal appraisal : appraisals){
			try{
				int i = 0;
				String report[] = new String[20];
				AppraisalBean appraisalBean = AppraisalDetailsDaoImpl.getInstance().getAppraisaldetails(appraisal.getId());
				appraisal.setAppraiser(0);
				getAppraiserName(appraisal, userNames);
				report[i++] = count + 1 + "";
				String name = userNames.get(appraisal.getUserId());
				try{
					List<Object> empId = JDBCUtiility.getInstance().getSingleColumn("SELECT EMP_ID FROM USERS WHERE ID=? AND EMP_ID IS NOT NULL", new Object[] { appraisal.getUserId() });
					name += "(" + ((Integer) empId.get(0)).intValue() + ")";
				} catch (Exception e){
					logger.error("", e);
				}
				report[i++] = name;
				for (String row : appraisalBean.getFeed_ratings()){
					report[i++] = row.split("~=~")[0];
				}
				report[i++] = (appraisalBean.getFeed_comments());
				reports[count++] = report;
			} catch (Exception e){}
		}
		return reports;
	}

	private double getRating(String[] persRatings) {
		int count = 0, total = 0;
		for (String rating : persRatings){
			try{
				String ratings[] = rating.split("~=~");
				total += Integer.parseInt(ratings[1]);
				count++;
			} catch (Exception e){}
		}
		return count > 0 ? Double.parseDouble(new DecimalFormat("0.0").format(total / count)) : 0;
	}

	private void getAppraiserName(Appraisal appraisal, HashMap<Integer, String> userNames) {
		if (!userNames.containsKey(appraisal.getAppraiser()) || !userNames.containsKey(appraisal.getUserId())){
			for (Integer uIds : new Integer[] { appraisal.getUserId(), appraisal.getAppraiser(), appraisal.getHandler() }){
				if (userNames.containsKey(uIds)) continue;
				List<Object> userName = JDBCUtiility.getInstance().getSingleColumn("SELECT CONCAT(FIRST_NAME,' ',LAST_NAME) FROM PROFILE_INFO WHERE ID=(SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { uIds });
				if (userName != null && !userName.isEmpty()){
					userNames.put(uIds, (String) userName.get(0));
				} else userNames.put(uIds, " ");
			}
		}
	}

	private void setAppraiseeStatus(Appraisal[] appraisals) {
		if (appraisals != null && appraisals.length > 0){
			for (Appraisal appraisal : appraisals){
				if (appraisal.getStatus().equalsIgnoreCase(Status.REJECTEDBYRMG)){
					appraisal.setStatus(Status.PENDING_APPRAISER);
				}
			}
		}
	}

	private AppraisalsStatusBean getStatusCounts(Appraisal[] appraisals) {
		AppraisalsStatusBean stautsBean = new AppraisalsStatusBean();
		for (Appraisal app : appraisals){
			try{
				switch (Integer.parseInt(app.getStatusId())) {
					case 38:
						stautsBean.setAvailable(stautsBean.getAvailable() + 1);
						break;
					case 39:
						stautsBean.setSubmitted(stautsBean.getSubmitted() + 1);
						break;
					case 40:
						stautsBean.setPending_appraiser(stautsBean.getPending_appraiser() + 1);
						break;
					case 41:
						stautsBean.setPending_rmg(stautsBean.getPending_rmg() + 1);
						break;
					case 42:
						stautsBean.setRejectedByAppraiser(stautsBean.getRejectedByAppraiser() + 1);
						break;
					case 43:
						stautsBean.setRejectedByRMG(stautsBean.getRejectedByRMG() + 1);
						break;
					case 19:
						stautsBean.setCompleted(stautsBean.getCompleted() + 1);
						break;
					case 20:
						stautsBean.setIn_progress(stautsBean.getIn_progress() + 1);
						break;
				}
			} catch (Exception e){}
		}
		return stautsBean;
	}

	private AppraisalsBean getusersByAppraiser(Appraisal[] appraisals) {
		AppraisalsBean result = new AppraisalsBean();
		if (appraisals != null && appraisals.length > 0){
			result = new AppraisalsBean(appraisals[0].getYear(), appraisals[0].getAppraiseeDueDate(), appraisals[0].getAppraiserDueDate(), appraisals[0].getStartDate(), appraisals[0].getPeriod(), appraisals[0].getHandlerName(), appraisals[0].getHandler() + "");
			List<AppraisalsBean> appraiserList = new ArrayList<AppraisalsBean>();
			List<Object> appraisers = JDBCUtiility.getInstance().getSingleColumn("SELECT DISTINCT(APPRAISER) FROM APPRAISAL WHERE YEAR=? AND PERIOD=?  GROUP BY APPRAISER ORDER BY APPRAISER", new Object[] { result.getYear(), result.getPeriod() });
			for (Object app : appraisers){
				AppraisalsBean appraiserBean = new AppraisalsBean();
				int appraiser = ((Integer) app).intValue();
				appraiserBean.setAppraiserId(appraiser + "");
				List<AppraisalsBean> appraisalUsers = new ArrayList<AppraisalsBean>();
				for (Appraisal appraisal : appraisals){
					if (appraisal.getAppraiser() == appraiser){
						appraiserBean.setAppraiserName(appraisal.getAppraiserName());
						appraisalUsers.add(new AppraisalsBean(appraisal.getUserId() + "", appraisal.getAppraiseeName(), appraisal.getType(), appraisal.getStatus()));
					}
				}
				if (appraisalUsers.size() > 0) appraiserBean.setAppraisees(appraisalUsers.toArray());
				appraiserList.add(appraiserBean);
			}
			if (appraiserList.size() > 0) result.setAppraisers(appraiserList.toArray());
		}
		return result;
	}

	private void setallToApprove(AppraisalListBean list, int userId, String activePeriod) {
		try{
			Appraisal appraisals[] = AppraisalDaoFactory.create().findByDynamicWhere(" APPRAISER = ?  AND STATUS NOT IN ( ? )" + activePeriod, new Object[] { userId, Status.PAFAVAILABLE });
			setAppraiserNames(appraisals, false, true);
			if (appraisals != null && appraisals.length > 0 && appraisals[0] != null) list.setPeriod(appraisals[0].getYear() + " - " + appraisals[0].getPeriod());
			list.setAppraisal(appraisals);
		} catch (Exception e){
			logger.error("", e);
		}
	}

	private PortalMail sendMail(String subject, String handlerName, String messageBody, String toMailId, String[] ccMailIds, String comments) {
		PortalMail portalMail = new PortalMail();
		try{
			portalMail.setMailSubject(subject);
			portalMail.setTemplateName(MailSettings.APPRAISAL_PAF_REMINDER);
			portalMail.setMessageBody(messageBody);
			portalMail.setHandlerName(handlerName);
			portalMail.setComment(getCommentsForMail(comments));
			portalMail.setRecipientMailId(toMailId);
			portalMail.setAllReceipientcCMailId(ccMailIds);
			if (portalMail.getRecipientMailId() != null && portalMail.getRecipientMailId().contains("@")){
				mailGenarator.invoker(portalMail);
			}
			return portalMail;
		} catch (Exception e){}
		return portalMail;
	}

	private String getCommentsForMail(String comments) {
		if (comments != null && comments.length() > 0) return "<strong>Comments/Reason&nbsp;&nbsp;:</strong>&nbsp;&nbsp;" + comments + "<br/><br/>";
		return "";
	}

	public void appraisalReminders() {
		PortalMail portalMail = new PortalMail();
		portalMail.setTemplateName(MailSettings.APPRAISAL_PAF_REMINDER);
		portalMail.setMailSubject("Diksha Lynx: Appraisal Reminder");
		portalMail.setComment("");
		ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
		try{
			String[] ids = profileDao.findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( SELECT USER_ID FROM APPRAISAL WHERE TIMESTAMPDIFF(DAY,CURDATE(),APPRAISEEDUEDATE)=1 AND STATUS='" + Status.PAFAVAILABLE + "'))");
			if (ids != null && ids.length > 0){
				portalMail.setAllReceipientBCCMailId(ids);
				portalMail.setHandlerName("Appraisee");
				portalMail.setMessageBody("The last day to submit the duly filled PAF is " + PortalUtility.getdd_MM_yyyy(new Date(new Date().getTime() + 24 * 60 * 60 * 1000)) + ". Please submit the completed PAF at the earliest.");
				new MailGenerator().invoker(portalMail);
			}
		} catch (Exception e){}
		try{
			String[] ids = profileDao.findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( SELECT USER_ID FROM APPRAISAL WHERE TIMESTAMPDIFF(DAY,CURDATE(),APPRAISEEDUEDATE)=-1 AND STATUS='" + Status.PAFAVAILABLE + "'))");
			if (ids != null && ids.length > 0){
				portalMail.setAllReceipientBCCMailId(ids);
				portalMail.setHandlerName("Appraisee");
				portalMail.setMessageBody("The last date for PAF Submission has past and your PAF has not been submitted. Please submit the same at the earliest to avoid a delay in Appraisal process. Kindly Co-operate.");
				new MailGenerator().invoker(portalMail);
			}
		} catch (Exception e){}
		try{
			String[] ids = profileDao.findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( SELECT USER_ID FROM APPRAISAL WHERE TIMESTAMPDIFF(DAY,CURDATE(),APPRAISEEDUEDATE)<-1 AND STATUS='" + Status.PAFAVAILABLE + "'))");
			if (ids != null && ids.length > 0){
				portalMail.setAllReceipientBCCMailId(ids);
				portalMail.setHandlerName("Appraisee");
				portalMail.setMessageBody("RMG insists you to submit your completed PAF as soon as possible to avoid any inconvenience due to delayed appraisal. It's in the Apraisee's best interest to complete this process earnestly to avoid escalations. Any delay in submitting PAF would delay your performance appraisal process. Kindly Co-operate.");
				new MailGenerator().invoker(portalMail);
			}
		} catch (Exception e){}
		try{
			String[] ids = profileDao.findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( SELECT APPRAISER FROM APPRAISAL WHERE TIMESTAMPDIFF(DAY,CURDATE(),APPRAISERDUEDATE)=1 AND STATUS IN ('" + Status.PAFSUBMITTED + "','" + Status.INPROGRESS + "','" + Status.REJECTEDBYRMG + "')))");
			if (ids != null && ids.length > 0){
				portalMail.setAllReceipientBCCMailId(ids);
				portalMail.setHandlerName("Appraiser");
				portalMail.setMessageBody("The last day to complete the appraisal is " + PortalUtility.getdd_MM_yyyy(new Date(new Date().getTime() + 24 * 60 * 60 * 1000)) + ". Please finish the appraisal discussion and submit the completely filled PAF at the earliest.");
				new MailGenerator().invoker(portalMail);
			}
		} catch (Exception e){}
		try{
			JDBCUtiility.getInstance().update("UPDATE APPRAISAL SET STATUS='" + Status.PENDING_APPRAISER + "'  WHERE TIMESTAMPDIFF(DAY,CURDATE(),APPRAISERDUEDATE)<0 AND STATUS IN ('" + Status.INPROGRESS + "')", null);
			String[] ids = profileDao.findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( SELECT APPRAISER FROM APPRAISAL WHERE TIMESTAMPDIFF(DAY,CURDATE(),APPRAISERDUEDATE)<0 AND STATUS IN ('" + Status.PAFSUBMITTED + "','" + Status.INPROGRESS + "','" + Status.PENDING_APPRAISER + "','" + Status.REJECTEDBYRMG + "')))");
			if (ids != null && ids.length > 0){
				portalMail.setAllReceipientBCCMailId(ids);
				portalMail.setHandlerName("Appraiser");
				portalMail.setMessageBody("RMG insists you to submit your completed PAF as soon as possible to avoid any inconvenience due to delayed appraisal. It's in the Apraisee's best interest to complete this process earnestly to avoid escalations. Any delay in submitting PAF would delay your performance appraisal process. Kindly Co-operate.");
				new MailGenerator().invoker(portalMail);
			}
		} catch (Exception e){}
		try{
			notifyPafAavailable(AppraisalDaoFactory.create().findByDynamicWhere(" TIMESTAMPDIFF(DAY,CURDATE(),START_DATE)=0 AND STATUS='" + Status.PAFAVAILABLE + "'", null));
		} catch (Exception e){}
	}

	public void notifyPafAavailable1(ArrayList<Integer> addedlist) {
		try{
			PortalMail portalMail = new PortalMail();
			ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
			String usersList = "";
			if (addedlist != null && !addedlist.isEmpty()) usersList = "USER_ID IN (" + ModelUtiility.getCommaSeparetedValues(addedlist) + " ) AND ";
			String[] ids = profileDao.findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( SELECT USER_ID FROM APPRAISAL WHERE " + usersList + "TIMESTAMPDIFF(DAY,CURDATE(),START_DATE)=0 AND STATUS='" + Status.PAFAVAILABLE + "'))");
			ProfileInfo handler = profileDao.findByDynamicWhere(" ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( SELECT HANDLER FROM APPRAISAL WHERE TIMESTAMPDIFF(DAY,CURDATE(),START_DATE)=0 AND STATUS='" + Status.PAFAVAILABLE + "'))", null)[0];
			List<Object> years = JDBCUtiility.getInstance().getSingleColumn("SELECT MIN(YEAR) FROM APPRAISAL WHERE TIMESTAMPDIFF(DAY,CURDATE(),START_DATE)=0 AND STATUS='" + Status.PAFAVAILABLE + "'", null);
			int year = Integer.parseInt((String) years.get(0));
			if (ids != null && ids.length > 0){
				portalMail.setTemplateName(MailSettings.APPRAISAL_PAF_AVAILABLE);
				portalMail.setMailSubject("Diksha : RMG : Performance Appraisal " + (year - 1) + " - " + year);
				portalMail.setFromMailId(handler.getOfficalEmailId());
				portalMail.setAllReceipientBCCMailId(ids);
				portalMail.setHandlerName(handler.getFirstName() + " " + handler.getLastName());
				try{
					String filePath = PropertyLoader.getEnvVariable() + File.separator + "Data" + File.separator + "appraisals" + File.separator + "doc";
					File file = new File(filePath);
					if (file.exists() && file.list().length > 0){
						String outputFile = file.list()[0];
						portalMail.setFileSources(new Attachements[] { new Attachements(filePath + File.separator + outputFile, outputFile) });
					}
				} catch (Exception e){
					logger.error("unable to attach Appraisal Attachment to the mail.", e);
				}
				new MailGenerator().invoker(portalMail);
			}
		} catch (Exception e){}
	}

	public void notifyPafAavailable(Appraisal[] appraisalList) {
		try{
			PortalMail portalMail = new PortalMail();
			try{
				String filePath = PropertyLoader.getEnvVariable() + File.separator + "Data" + File.separator + "appraisals" + File.separator + "doc";
				File file = new File(filePath);
				if (file.exists() && file.list().length > 0){
					String outputFile = file.list()[0];
					portalMail.setFileSources(new Attachements[] { new Attachements(filePath + File.separator + outputFile, outputFile) });
				}
			} catch (Exception e){
				logger.error("unable to attach Appraisal Attachment to the mail.", e);
			}
			portalMail.setTemplateName(MailSettings.APPRAISAL_PAF_AVAILABLE);
			HashMap<Integer, String> emailIds = new HashMap<Integer, String>(), handlerNames = new HashMap<Integer, String>();
			for (Appraisal appraisal : appraisalList){
				if (appraisal.getStartDate().getTime() > new Date().getTime()) return;
				int year = Integer.parseInt(appraisal.getYear());
				portalMail.setMailSubject("Diksha : RMG : Performance Appraisal " + (year - 1) + " - " + year);
				setEmailsAndName(appraisal, emailIds, handlerNames);
				portalMail.setFromMailId(emailIds.get(appraisal.getHandler()));
				portalMail.setRecipientMailId(emailIds.get(appraisal.getUserId()));
				portalMail.setAllReceipientcCMailId(new String[] { emailIds.get(appraisal.getAppraiser()) });
				portalMail.setHandlerName(handlerNames.get(appraisal.getHandler()));
				new MailGenerator().invoker(portalMail);
			}
		} catch (Exception e){}
	}

	private void setEmailsAndName(Appraisal appraisal, HashMap<Integer, String> emailIds, HashMap<Integer, String> handlerNames) {
		if (!emailIds.containsKey(appraisal.getAppraiser()) || !emailIds.containsKey(appraisal.getUserId()) || !emailIds.containsKey(appraisal.getHandler())){
			for (Integer uId : new Integer[] { appraisal.getUserId(), appraisal.getAppraiser(), appraisal.getHandler() }){
				if (emailIds.containsKey(uId)) continue;
				List<Object> mailIds = JDBCUtiility.getInstance().getSingleColumn("SELECT OFFICAL_EMAIL_ID FROM PROFILE_INFO WHERE ID=(SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { uId });
				if (mailIds != null && !mailIds.isEmpty()){
					emailIds.put(uId, (String) mailIds.get(0));
				} else emailIds.put(uId, "");
			}
		}
		if (!handlerNames.containsKey(appraisal.getHandler())){
			List<Object> userName = JDBCUtiility.getInstance().getSingleColumn("SELECT CONCAT(FIRST_NAME,' ',LAST_NAME) FROM PROFILE_INFO WHERE ID=(SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { appraisal.getHandler() });
			if (userName != null && !userName.isEmpty()){
				handlerNames.put(appraisal.getHandler(), (String) userName.get(0));
			} else emailIds.put(appraisal.getHandler(), "");
		}
	}

	public int isRMGManager(Login loginSess) {
		return ModelUtiility.getInstance().getRMGManagerUserIds(loginSess).contains(loginSess.getUserId()) ? 1 : 0;
	}

	private void sendAppraisalSalaryRevisionMail(ActionResult result, AppraisalBean appraisalBean, HttpServletRequest request) {
		try{
			PortalMail pmail = new PortalMail();
			Appraisal appraisal = AppraisalDaoFactory.create().findByPrimaryKey(appraisalBean.getId());
			if (appraisal != null){
				if (appraisalBean.getMailSubject() == null || appraisalBean.getMailSubject().length() == 0 || appraisalBean.getRevisedCTC() == null || appraisalBean.getRevisedCTC().length() == 0 || appraisalBean.getEffectFrom() == null || appraisalBean.getEffectFrom().length() == 0){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
					return;
				}
				ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
				ProfileInfo userProfile = profileInfoDao.findByPrimaryKey(appraisal.getUserId());
				pmail.setTemplateName(MailSettings.APPRAISAL_SALARY_REVISION);
				pmail.setMailSubject(appraisalBean.getMailSubject());
				pmail.setAmount(appraisalBean.getRevisedCTC());
				pmail.setEmployeeName(userProfile.getFirstName() + " " + userProfile.getLastName());
				if (appraisalBean.getDesignation() != null && appraisalBean.getDesignation().equals("")){
					pmail.setEmpDesignation((appraisalBean.getDesignation() + "").substring((appraisalBean.getDesignation() + "").lastIndexOf("-") + 2));
				} else{
					Levels level = LevelsDaoFactory.create().findByPrimaryKey(userProfile.getLevelId());
					pmail.setEmpDesignation(level.getDesignation());
				}
				pmail.setDate(new SimpleDateFormat("MMM dd, yyyy").format(new SimpleDateFormat("dd-MM-yyyy").parse(appraisalBean.getEffectFrom())));
				pmail.setTimePeriod(appraisal.getYear());
				pmail.setRecipientMailId(userProfile.getOfficalEmailId());
				List cclist = ModelUtiility.getInstance().getRMGManagerUserIds(new UsersPk(appraisal.getUserId()));
				cclist.add(appraisal.getHandler());
				Login login = Login.getLogin(request);
				if (cclist.contains(login.getUserId())){
					cclist.add(userProfile.getReportingMgr());
					if (userProfile.getHrSpoc() > 3) cclist.add(userProfile.getHrSpoc());
					pmail.setAllReceipientcCMailId(profileInfoDao.findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN (" + ModelUtiility.getCommaSeparetedValues(cclist) + "))"));
					new MailGenerator().invoker(pmail);
				} else result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
			} else result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
		} catch (Exception e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.Loan.mail"));
		}
	}
}
