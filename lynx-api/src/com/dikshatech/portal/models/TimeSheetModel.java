package com.dikshatech.portal.models;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.ProjectBean;
import com.dikshatech.beans.TimeSheetDetailsBean;
import com.dikshatech.beans.TimeSheetHoursBean;
import com.dikshatech.beans.TimesheetBean;
import com.dikshatech.beans.TimesheetDelayedBean;
import com.dikshatech.beans.UserTaskListBean;
import com.dikshatech.common.utils.EnumUtil;
import com.dikshatech.common.utils.GenerateXls;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.POIParser;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.PoDetailsDao;
import com.dikshatech.portal.dao.PoEmpMapDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.ProjTasksDao;
import com.dikshatech.portal.dao.ProjectDao;
import com.dikshatech.portal.dao.ProjectMappingDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.TimeSheetDetailsDao;
import com.dikshatech.portal.dao.TimesheetReqDao;
import com.dikshatech.portal.dao.UserTaskTimesheetMapDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.ChargeCode;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Holidays;
import com.dikshatech.portal.dto.LeaveMaster;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.PoEmpMap;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ProjTasks;
import com.dikshatech.portal.dto.Project;
import com.dikshatech.portal.dto.ProjectMapping;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.TimeSheetDetails;
import com.dikshatech.portal.dto.TimeSheetDetailsPk;
import com.dikshatech.portal.dto.TimesheetReq;
import com.dikshatech.portal.dto.TimesheetReqPk;
import com.dikshatech.portal.dto.UserTaskTimesheetMap;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.TimeSheetDetailsDaoException;
import com.dikshatech.portal.exceptions.TimesheetReqDaoException;
import com.dikshatech.portal.exceptions.UserTaskTimesheetMapDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.ChargeCodeDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.HolidaysDaoFactory;
import com.dikshatech.portal.factory.LeaveMasterDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.PoDetailsDaoFactory;
import com.dikshatech.portal.factory.PoEmpMapDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.ProjTasksDaoFactory;
import com.dikshatech.portal.factory.ProjectDaoFactory;
import com.dikshatech.portal.factory.ProjectMappingDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.TimeSheetDetailsDaoFactory;
import com.dikshatech.portal.factory.TimesheetReqDaoFactory;
import com.dikshatech.portal.factory.UserTaskTimesheetMapDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class TimeSheetModel extends ActionMethods {

	private static Logger	logger		= LoggerUtil.getLogger(TimeSheetModel.class);
	private final String	TIMESHEET	= "TIMESHEET";

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		TimeSheetDetails timeSheetDetailsDto = (TimeSheetDetails) form;
		timeSheetDetailsDto.setStatus("CANCELLED");
		return submit(timeSheetDetailsDto, request);
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		result = this.submit(form, request);
		return result;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			TimeSheetDetailsDao timeSheetdetailDao = TimeSheetDetailsDaoFactory.create(conn);
			UserTaskTimesheetMapDao userTaslTsMapDao = UserTaskTimesheetMapDaoFactory.create(conn);
			ProjectDao projectDao = ProjectDaoFactory.create(conn);
			//UserTaskFilledDao userTaskFilledDao = UserTaskFilledDaoFactory.create(conn);
			ProjTasksDao projTaskDao = ProjTasksDaoFactory.create(conn);
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
				case TOAPPROVE:
					try{
						Login login = Login.getLogin(request);
						request.setAttribute("actionForm", (login.getUserId() == 38) ? true : isApprover(login.getUserId()));
					} catch (Exception e){
						e.printStackTrace();
					}
					break;
				case RECEIVETIMESHEET:{
					TimeSheetDetails timeSheetdto = (TimeSheetDetails) form;
					TimeSheetDetails timeSheetDto = timeSheetdetailDao.findByPrimaryKey(timeSheetdto.getId());
					if (timeSheetDto == null || timeSheetDto.getStartDate() == null || timeSheetDto.getEndDate() == null){
						logger.info(timeSheetDto);
						if (timeSheetDto != null) timeSheetdetailDao.delete(new TimeSheetDetailsPk(timeSheetDto.getId()));
						request.setAttribute("actionForm", timeSheetdto);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.tm.invaliddates"));
						break;
					}
					Login login = Login.getLogin(request);
					int userId = login.getUserId().intValue();
					// give temperary access to vikram chandra be half of abhishek.
					if (userId == 38){
						userId = 24;
					}
					TimesheetReq[] timeSheetreq = TimesheetReqDaoFactory.create(conn).findByDynamicSelect("SELECT * FROM TIMESHEET_REQ WHERE ID=(SELECT MAX(ID) FROM TIMESHEET_REQ WHERE TSHEET_ID= ? AND ASSIGNED_TO=?)", new Object[] { timeSheetdto.getId(), userId });
					TimesheetBean timeSheetBean = DtoToBeanConverter.DtoToBeanConverter(timeSheetDto, null);
					timeSheetBean.setStartDate(PortalUtility.formatDateddMMyyyy(timeSheetDto.getStartDate()));
					timeSheetBean.setEndDate(PortalUtility.formatDateddMMyyyy(timeSheetDto.getEndDate()));
					timeSheetBean.setUserId(timeSheetDto.getUserId() + "");
					timeSheetBean.setUserName(ModelUtiility.getInstance().getEmployeeName(timeSheetDto.getUserId(), conn));
					if (!timeSheetDto.getStatus().equals(Status.APPROVED) && timeSheetreq != null && timeSheetreq.length > 0 && ModelUtiility.getInstance().isValidApprover(timeSheetreq[0].getEsrqmId(), userId, conn)){
						timeSheetBean.setEsrMapId(timeSheetreq[0].getEsrqmId() + "");
						if (ModelUtiility.getInstance().isAssignedApprover(timeSheetreq[0].getEsrqmId(), userId, conn)){
							timeSheetBean.setApprove("1");
							timeSheetBean.setReject("1");
							if (!timeSheetreq[0].getStatus().equals(Status.ON_HOLD)) timeSheetBean.setOnHold("1");
						}
						timeSheetBean.setAssign("1");
					} else if (timeSheetDto.getStatus().equals(Status.APPROVED) && timeSheetDto.getUserId().intValue() != userId){
						timeSheetBean.setToCancell("1");
					}
					UserTaskTimesheetMap[] userTaskMaparr = userTaslTsMapDao.findWhereTsIdEquals(timeSheetdto.getId());
					UserTaskListBean[] userTaskListBeanArr = new UserTaskListBean[userTaskMaparr.length];
					Map<Integer, Project> projectMap = new HashMap<Integer, Project>();
					int j = 0;
					for (int i = 0; i < userTaskMaparr.length; i++){
						UserTaskListBean userTaskListBean = new UserTaskListBean();
						UserTaskTimesheetMap userTaskMapDto = userTaskMaparr[i];
						if (projectMap.isEmpty() || !projectMap.containsKey(new Integer(userTaskMapDto.getProjectId()))){
							Project projectsDto = projectDao.findByPrimaryKey(userTaskMapDto.getProjectId());
							userTaskListBean.setProjectId(projectsDto.getId() + "");
							userTaskListBean.setProjectName(projectsDto.getName());
							projectMap.put(new Integer(userTaskMapDto.getProjectId()), projectsDto);
						} else{
							Project projectsDtoFromMap = projectMap.get(new Integer(userTaskMapDto.getProjectId()));
							userTaskListBean.setProjectId(projectsDtoFromMap.getId() + "");
							userTaskListBean.setProjectName(projectsDtoFromMap.getName());
						}
						userTaskListBean.setTaskName(userTaskMapDto.getTaskName());
						userTaskListBean.setEtc(new DecimalFormat("0.00").format(userTaskMapDto.getEtc()));
						userTaskListBean.setTotalEtc(new DecimalFormat("0.00").format(userTaskMapDto.getTotalEtc()));
						ProjTasks[] projTasksarr = projTaskDao.findByDynamicSelect("SELECT * FROM PROJ_TASKS WHERE PROJ_ID=?", new Object[] { userTaskListBean.getProjectId() });
						if (projTasksarr.length > 0){
							String allTask = "";
							for (ProjTasks projTask : projTasksarr){
								allTask += projTask.getTasks() + "|";
							}
							userTaskListBean.setAllTask(allTask);
						}
						TimeSheetHoursBean[] hourDayArr1 = new TimeSheetHoursBean[7];
						hourDayArr1[0] = new TimeSheetHoursBean("Monday", timeSheetDto.getStartDate().toString(), userTaskMapDto.getMonDetails());
						hourDayArr1[1] = new TimeSheetHoursBean("Tuesday", PortalUtility.getEndDateOfTheWeek(timeSheetDto.getStartDate(), 1), userTaskMapDto.getTueDetails());
						hourDayArr1[2] = new TimeSheetHoursBean("Wednesday", PortalUtility.getEndDateOfTheWeek(timeSheetDto.getStartDate(), 2), userTaskMapDto.getWedDetails());
						hourDayArr1[3] = new TimeSheetHoursBean("Thursday", PortalUtility.getEndDateOfTheWeek(timeSheetDto.getStartDate(), 3), userTaskMapDto.getThuDetails());
						hourDayArr1[4] = new TimeSheetHoursBean("Friday", PortalUtility.getEndDateOfTheWeek(timeSheetDto.getStartDate(), 4), userTaskMapDto.getFriDetails());
						hourDayArr1[5] = new TimeSheetHoursBean("Saturday", PortalUtility.getEndDateOfTheWeek(timeSheetDto.getStartDate(), 5), userTaskMapDto.getSatDetails());
						hourDayArr1[6] = new TimeSheetHoursBean("Sunday", PortalUtility.getEndDateOfTheWeek(timeSheetDto.getStartDate(), 6), userTaskMapDto.getSunDetails());
						/*UserTaskFilled[] userTaskFilledArr = userTaskFilledDao.findByUserTaskTimesheetMap(userTaskMapDto.getId());
						TimeSheetHoursBean[] hourDayArr = new TimeSheetHoursBean[userTaskFilledArr.length];
						boolean flag = true;
						for (int k = 0; k < userTaskFilledArr.length; k++){
							TimeSheetHoursBean timeSheetHoursBean = new TimeSheetHoursBean();
							UserTaskFilled userTaskFilledDto = userTaskFilledArr[k];
							if (flag){
								String startDate = timeSheetDto.getStartDate().toString();
								String day = PortalUtility.getDayOfParticularDate(startDate);
								String[] hoursCommentArr = userTaskFilledDto.getHoursSpent().split("\\|");
								timeSheetHoursBean.setDate(startDate);
								timeSheetHoursBean.setDay(day);
								if (hoursCommentArr != null && hoursCommentArr.length > 0){
									timeSheetHoursBean.setHour(hoursCommentArr[0]);
									if (hoursCommentArr.length > 1) timeSheetHoursBean.setComment(hoursCommentArr[1]);
								}
								hourDayArr[k] = timeSheetHoursBean;
								flag = false;
							} else{
								String nextDate = PortalUtility.getEndDateOfTheWeek(timeSheetDto.getStartDate(), k);
								String day = PortalUtility.getDayOfParticularDate(nextDate);
								String[] hoursCommentArr = userTaskFilledDto.getHoursSpent().split("\\|");
								timeSheetHoursBean.setDate(nextDate);
								timeSheetHoursBean.setDay(day);
								timeSheetHoursBean.setHour(hoursCommentArr[0]);
								if (hoursCommentArr.length > 1) timeSheetHoursBean.setComment(hoursCommentArr[1]);
								hourDayArr[k] = timeSheetHoursBean;
							}
						}*/
						userTaskListBean.setTmeSheetHourCommentarr(hourDayArr1);
						userTaskListBeanArr[j] = userTaskListBean;
						j++;
					}// End of for loop
					timeSheetBean.setUserTasklistbean(userTaskListBeanArr);
					List<Object> comments = JDBCUtiility.getInstance().getSingleColumn("SELECT CONCAT((SELECT CONCAT(FIRST_NAME,' ',LAST_NAME) FROM PROFILE_INFO PF WHERE PF.ID=(SELECT PROFILE_ID FROM USERS U WHERE U.ID=TR.ACTION_BY)),' : ',COMMENTS) FROM TIMESHEET_REQ TR where TSHEET_ID=? AND COMMENTS IS NOT NULL AND  CHAR_LENGTH(COMMENTS)>0", new Object[] { timeSheetDto.getId() });
					String appComments = "";
					for (Object comment : comments)
						appComments += comment + "\n";
					timeSheetBean.setComp_off(timeSheetDto.getComp_off());
					timeSheetBean.setComments(appComments);
					TimeSheetDetailsBean bean = new TimeSheetDetailsBean();
					bean.setTimeSheetBeanArray(new TimesheetBean[] { timeSheetBean });
					request.setAttribute("actionForm", bean);
					break;
				}
				case RECEIVEALLTIMESHEET:{
					Login login = Login.getLogin(request);
					StringBuffer query = new StringBuffer("USER_ID = ? ");
					/*if (dropdown.getMonth() != null) query.append(" AND MONTH(START_DATE)=" + dropdown.getMonth() + " ");
					if (dropdown.getSearchyear() != null) query.append(" AND YEAR(START_DATE)=" + dropdown.getSearchyear() + " ");
					else*/
					if (form.isAndroid()) query.append(" AND (TIMESTAMPDIFF(DAY,START_DATE,NOW()) <= 180 OR STATUS != '" + Status.APPROVED + "')");
					else query.append(" AND (TIMESTAMPDIFF(DAY,START_DATE,NOW()) <= 360 OR STATUS != '" + Status.APPROVED + "')");
					TimeSheetDetails[] tempTimeSheetdtoArr = timeSheetdetailDao.findByDynamicWhere(query.toString(), new Object[] { login.getUserId() });
					TimesheetBean[] timeSheetbeanArr = new TimesheetBean[tempTimeSheetdtoArr.length];
					int i = 0;
					for (TimeSheetDetails tempUserTmShtDto : tempTimeSheetdtoArr){
						if (tempUserTmShtDto.getStartDate() == null || tempUserTmShtDto.getEndDate() == null){
							timeSheetdetailDao.delete(new TimeSheetDetailsPk(tempUserTmShtDto.getId()));
							continue;
						}
						TimesheetBean timeSheetBean = DtoToBeanConverter.DtoToBeanConverter(tempUserTmShtDto, null);
						timeSheetbeanArr[i] = timeSheetBean;
						i++;
					}
					TimeSheetDetailsBean details = new TimeSheetDetailsBean();
					// for vikram chandra changes
					details.setToApprove((login.getUserId() == 38) ? "true" : isApprover(login.getUserId()) + "");
					details.setTimeSheetBeanArray(timeSheetbeanArr);
					request.setAttribute("actionForm", details);
					break;
				}
				case TSHEETFORAPPROVE:{
					DropDown dropdown = (DropDown) form;
					TimeSheetDetailsBean details = new TimeSheetDetailsBean();
					Login login = Login.getLogin(request);
					int userId = login.getUserId().intValue();
					// give temperary access to vikram chandra be half of abhishek.
					if (userId == 38){
						userId = 24;
					}
					// start filtering
				StringBuffer query = new StringBuffer("SELECT distinct T.ID,T.START_DATE,T.END_DATE,T.STATUS,T.SUBMISSION_DATE,T.USER_ID,T.COMP_OFF,T.IS_DELAYED,TR.TSHEET_ID,TR.STATUS FROM TIME_SHEET_DETAILS T  LEFT JOIN TIMESHEET_REQ TR ON TR.TSHEET_ID = T.ID WHERE ASSIGNED_TO = ?  AND T.STATUS IN ('Submitted' , 'Approved', 'On Hold')");
			
					
			//				StringBuffer query = new StringBuffer("SELECT T.ID,START_DATE,END_DATE,S.STATUS,SUBMISSION_DATE,USER_ID,COMP_OFF,IS_DELAYED FROM TIME_SHEET_DETAILS T,(SELECT TSHEET_ID, STATUS FROM TIMESHEET_REQ  WHERE ID IN (SELECT MAX(ID) FROM TIMESHEET_REQ WHERE ASSIGNED_TO=? GROUP BY TSHEET_ID) AND STATUS IN ('" + Status.SUBMITTED + "','" + Status.APPROVED + "','" + Status.ON_HOLD + "')) S WHERE T.ID=S.TSHEET_ID ");
				/*	if (dropdown.getMonth() != null || dropdown.getSearchyear() != null || dropdown.getSearchName() != null){
						if (dropdown.getMonth() != null && dropdown.getToMonth() != null) query.append(" AND (MONTH(START_DATE) BETWEEN " + dropdown.getMonth() + " AND " + dropdown.getToMonth() + ") ");
						else if (dropdown.getMonth() != null) query.append(" AND MONTH(START_DATE)=" + dropdown.getMonth() + " ");
						if (dropdown.getSearchyear() != null) query.append(" AND YEAR(START_DATE)=" + dropdown.getSearchyear() + " ");
						if (dropdown.getSearchName() != null) query.append(" AND (SELECT CONCAT(PF.FIRST_NAME,' ',PF.LAST_NAME) FROM PROFILE_INFO PF WHERE PF.ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=T.USER_ID)) LIKE '%" + dropdown.getSearchName() + "%' ");
					} 
					else query.append(" AND (TIMESTAMPDIFF(DAY,START_DATE,NOW()) <= 30 OR S.STATUS IN ('" + Status.SUBMITTED + "'))");*/
					// end filtering
					TimeSheetDetails[] timeSheetDtoArr = timeSheetdetailDao.findByDynamicSelect(query.toString(), new Object[] { userId });
					int count = 0;
					if (timeSheetDtoArr.length > 0){
						Map<Integer, String> timeSheetmap = new HashMap<Integer, String>();
						TimesheetBean[] timeSheetbeanArr = new TimesheetBean[timeSheetDtoArr.length];
						int j = 0;
						for (TimeSheetDetails timeSheetReq : timeSheetDtoArr){
							if (timeSheetReq.getStartDate() == null || timeSheetReq.getEndDate() == null){
								timeSheetdetailDao.delete(new TimeSheetDetailsPk(timeSheetReq.getId()));
								continue;
							}
							if (!timeSheetmap.containsKey(timeSheetReq.getUserId())){
								timeSheetmap.put(timeSheetReq.getUserId(), ModelUtiility.getInstance().getEmployeeName(timeSheetReq.getUserId()));
							}
							String raisedBy = timeSheetmap.get(timeSheetReq.getUserId());
							TimesheetBean timeSheetBean = DtoToBeanConverter.DtoToBeanConverter(timeSheetReq, raisedBy);
							timeSheetBean.setStatus(timeSheetReq.getStatus());
							List<Object> esrqid = JDBCUtiility.getInstance().getSingleColumn("SELECT MAX(ESRQM_ID) FROM TIMESHEET_REQ WHERE TSHEET_ID=?", new Object[] { timeSheetReq.getId() });
							timeSheetBean.setEsrMapId(((Integer) esrqid.get(0)).intValue() + "");
							if (!timeSheetReq.getStatus().equals(Status.APPROVED) && !timeSheetReq.getStatus().equals(Status.REJECTED) && !timeSheetReq.getStatus().equals(Status.CANCELLED) && esrqid != null && !esrqid.isEmpty()){
								if (ModelUtiility.getInstance().isValidApprover(((Integer) esrqid.get(0)).intValue(), userId, conn)){
									if (ModelUtiility.getInstance().isAssignedApprover(((Integer) esrqid.get(0)).intValue(), userId, conn)){
										count++;
										timeSheetBean.setApprove("1");
										timeSheetBean.setReject("1");
										if (!timeSheetReq.getStatus().equals(Status.ON_HOLD)) timeSheetBean.setOnHold("1");
									}
									timeSheetBean.setAssign("1");
								}
								try{
									timeSheetBean.setStatus((String) JDBCUtiility.getInstance().getSingleColumn("SELECT STATUS FROM INBOX WHERE ESR_MAP_ID=? AND RECEIVER_ID=ASSIGNED_TO", new Object[] { timeSheetBean.getEsrMapId() }).get(0));
								} catch (Exception e){}
							} else if (timeSheetReq.getStatus().equals(Status.APPROVED)){
								timeSheetBean.setToCancell("1");
							}
							timeSheetbeanArr[j] = timeSheetBean;
							j++;
						}
						details.setTimeSheetBeanArray(timeSheetbeanArr);
					}
					details.setNoOfTsheettoApprove(count + "");
					request.setAttribute("actionForm", details);
					break;
				}
				default:
					break;
			}// End of switch stmt
		} catch (TimeSheetDetailsDaoException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			ResourceManager.close(conn);
		}
		return result;
	}

	private boolean isApprover(Integer userId) {
		TimesheetReqDao tDao = TimesheetReqDaoFactory.create();
		boolean result = false;
		try{
			String sql = "ASSIGNED_TO = ?";
			Object[] sqlParams = { userId };
			TimesheetReq[] tReqs = tDao.findByDynamicWhere(sql, sqlParams);
			if (tReqs.length > 0){
				result = true;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		try{
			TimeSheetDetails timesheetdetailsDto = (TimeSheetDetails) form;
			TimeSheetDetailsDao timeSheetdetailDao = TimeSheetDetailsDaoFactory.create();
			//UserTaskFilledDao userTaskFilledDao = UserTaskFilledDaoFactory.create();
			UserTaskTimesheetMapDao userTaskTimesheetDao = UserTaskTimesheetMapDaoFactory.create();
			switch (ActionMethods.SaveTypes.getValue(form.getsType())) {
				case SAVE:{
					if (validateDate(timesheetdetailsDto.getStartDate(), timesheetdetailsDto.getEndDate())){
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.tm.invaliddates"));
						return result;
					}
					Login loginDto = Login.getLogin(request);
					String sql = "START_DATE= ? AND USER_ID=?";
					Date date1 = PortalUtility.formateDateTimeToDateFormat(timesheetdetailsDto.getStartDate());
					Object[] sqlParams = { date1, loginDto.getUserId().intValue() };
					TimeSheetDetails[] timeSheetArr = timeSheetdetailDao.findByDynamicWhere(sql, sqlParams);
					if (timeSheetArr.length > 0){
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("duplicate.tsheet.date"));
						break;
					}
					timesheetdetailsDto.setStatus("New");
					timesheetdetailsDto.setUserId(loginDto.getUserId().intValue());
					timesheetdetailsDto.setIsDelayed((short) 0);
					TimeSheetDetailsPk timesheetPk = timeSheetdetailDao.insert(timesheetdetailsDto);
					if (timesheetPk != null && timesheetdetailsDto.getTimeSheetDataArr().length > 0){
						if (logger.isTraceEnabled()) logger.trace(Arrays.asList(timesheetdetailsDto.getTimeSheetDataArr()));
						for (String parsableStr : timesheetdetailsDto.getTimeSheetDataArr()){
							UserTaskTimesheetMap userTaskTimeSheetMapDto = null;
							Object[] objArr = DtoToBeanConverter.populateFormFromString(parsableStr);
							userTaskTimeSheetMapDto = (UserTaskTimesheetMap) objArr[0];
							userTaskTimeSheetMapDto.setTsId(timesheetPk.getId());
							/*UserTaskTimesheetMapPk userTaskTSMapPk =*/userTaskTimesheetDao.insert(userTaskTimeSheetMapDto);
							/*if (userTaskTSMapPk != null && objArr != null && objArr.length > 1){
								String[] hourSpentArr = ((String) objArr[1]).split("~!~");
								for (int i = 0; i < hourSpentArr.length; i++){
									UserTaskFilled userTaskFilledDto = new UserTaskFilled();
									userTaskFilledDto.setHoursSpent(hourSpentArr[i]);
									userTaskFilledDto.setMapId(userTaskTSMapPk.getId());
									userTaskFilledDao.insert(userTaskFilledDto);
								}
							}*/
						}
					}
					break;
				}
				case SUBMIT:{
					//Login loginDto = Login.getLogin(request);				
					for (int id : timesheetdetailsDto.getTimeSheetIds()){
						TimeSheetDetails tempTimeSheetdto1 = timeSheetdetailDao.findByPrimaryKey(id);
						tempTimeSheetdto1.setStatus(Status.SUBMITTED);
						tempTimeSheetdto1.setSubmissionDate(new Date());
						final short isDelayed = (short) ((isSubmitDelayed(tempTimeSheetdto1) == true) ? 1 : 0);
						tempTimeSheetdto1.setIsDelayed(isDelayed);
						timeSheetdetailDao.update(new TimeSheetDetailsPk(tempTimeSheetdto1.getId()), tempTimeSheetdto1);
						// --| calling time sheet service requset method
						this.timeSheetServiceReqInvoke(form, request, tempTimeSheetdto1);
					}
					break;
				}
				case SAVEANDSUBMIT:{
					if (validateDate(timesheetdetailsDto.getStartDate(), timesheetdetailsDto.getEndDate())){
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.tm.invaliddates"));
						return result;
					}
					Login loginDto = Login.getLogin(request);
					String sql = "START_DATE= ? AND USER_ID=?";
					Date date1 = PortalUtility.formateDateTimeToDateFormat(timesheetdetailsDto.getStartDate());
					Object[] sqlParams = { date1, loginDto.getUserId().intValue() };
					TimeSheetDetails[] timeSheetArr = timeSheetdetailDao.findByDynamicWhere(sql, sqlParams);
					if (timeSheetArr.length > 0 && timeSheetArr[0].getId() != timesheetdetailsDto.getId()){
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("duplicate.tsheet.date"));
						break;
					}
					TimeSheetDetails tempTimeSheetdto1 = timeSheetdetailDao.findByPrimaryKey(timesheetdetailsDto.getId());
					boolean isInsert = false;
					if (tempTimeSheetdto1 == null){
						tempTimeSheetdto1 = timesheetdetailsDto;
						isInsert = true;
					}
					tempTimeSheetdto1.setStatus(Status.SUBMITTED);
					tempTimeSheetdto1.setUserId(loginDto.getUserId().intValue());
					tempTimeSheetdto1.setSubmissionDate(new Date());
					tempTimeSheetdto1.setStartDate(timesheetdetailsDto.getStartDate());
					tempTimeSheetdto1.setEndDate(timesheetdetailsDto.getEndDate());
					final short isDelayed = (short) ((isSubmitDelayed(tempTimeSheetdto1) == true) ? 1 : 0);
					tempTimeSheetdto1.setIsDelayed(isDelayed);
					if (isInsert){
						TimeSheetDetailsPk userTaskTSMapPk = timeSheetdetailDao.insert(tempTimeSheetdto1);
						tempTimeSheetdto1.setId(userTaskTSMapPk.getId());
					} else timeSheetdetailDao.update(new TimeSheetDetailsPk(tempTimeSheetdto1.getId()), tempTimeSheetdto1);
					UserTaskTimesheetMap[] userTaskTimeSheetMapDtoArr = userTaskTimesheetDao.findByDynamicSelect("SELECT * FROM USER_TASK_TIMESHEET_MAP WHERE TS_ID= ? ORDER BY ID ASC", new Object[] { tempTimeSheetdto1.getId() });
					try{
						updateTimesheetFields(timesheetdetailsDto, userTaskTimeSheetMapDtoArr, userTaskTimesheetDao);
						timeSheetServiceReqInvoke(form, request, tempTimeSheetdto1);
					} catch (Exception e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					/*for (UserTaskTimesheetMap mapids : userTaskTimeSheetMapDtoArr)
						userTaskTimesheetDao.delete(new UserTaskTimesheetMapPk(mapids.getId()));
					if (timesheetdetailsDto.getTimeSheetDataArr() != null && timesheetdetailsDto.getTimeSheetDataArr().length > 0){
						if (logger.isTraceEnabled()) logger.trace(Arrays.asList(timesheetdetailsDto.getTimeSheetDataArr()));
						for (String parsableStr : timesheetdetailsDto.getTimeSheetDataArr()){
							UserTaskTimesheetMap userTaskTimeSheetMapDto = null;
							Object[] objArr = DtoToBeanConverter.populateFormFromString(parsableStr);
							userTaskTimeSheetMapDto = (UserTaskTimesheetMap) objArr[0];
							userTaskTimeSheetMapDto.setTsId(tempTimeSheetdto1.getId());
							UserTaskTimesheetMapPk userTaskTSMapPk = userTaskTimesheetDao.insert(userTaskTimeSheetMapDto);
							if (userTaskTSMapPk != null && objArr != null && objArr.length > 1){
								String[] hourSpentArr = ((String) objArr[1]).split("~!~");
								for (int i = 0; i < hourSpentArr.length; i++){
									UserTaskFilled userTaskFilledDto = new UserTaskFilled();
									userTaskFilledDto.setHoursSpent(hourSpentArr[i]);
									userTaskFilledDto.setMapId(userTaskTSMapPk.getId());
									userTaskFilledDao.insert(userTaskFilledDto);
								}
							}
						}
					}*/
					// --| calling time sheet service request method
					break;
				}
				default:
					break;
			}// End of switch
		} // End of try
		catch (TimeSheetDetailsDaoException e){
			e.printStackTrace();
		} catch (UserTaskTimesheetMapDaoException e){
			e.printStackTrace();
		}
		return result;
	}

	private boolean isSubmitDelayed(TimeSheetDetails tsDto) {
		return (tsDto.getSubmissionDate().after(tsDto.getEndDate()));
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		try{
			TimeSheetDetails timesheetdetailsDto = (TimeSheetDetails) form;
			if (timesheetdetailsDto.getId() == 0){
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.insufficientdata"));
				return result;
			}
			if (validateDate(timesheetdetailsDto.getStartDate(), timesheetdetailsDto.getEndDate())){
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.tm.invaliddates"));
				return result;
			}
			TimeSheetDetailsDao timeSheetdetailDao = TimeSheetDetailsDaoFactory.create();
			//UserTaskFilledDao userTaskFilledDao = UserTaskFilledDaoFactory.create();
			UserTaskTimesheetMapDao userTaskTimesheetDao = UserTaskTimesheetMapDaoFactory.create();
			Login loginDto = Login.getLogin(request);
			String sql = "START_DATE= ? AND USER_ID=?";
			Date date1 = PortalUtility.formateDateTimeToDateFormat(timesheetdetailsDto.getStartDate());
			TimeSheetDetails timeSheetDet = timeSheetdetailDao.findByPrimaryKey(timesheetdetailsDto.getId());
			Object[] sqlParams = { date1, timeSheetDet.getUserId() + "" };
			TimeSheetDetails[] timeSheetArr = timeSheetdetailDao.findByDynamicWhere(sql, sqlParams);
			Login login = Login.getLogin(request);
			int userId = login.getUserId().intValue();
			// give temperary access to vikram chandra be half of abhishek.
			if (userId == 38){
				userId = 24;
			}
			if (timeSheetArr.length > 0 && timeSheetArr[0].getId() != timesheetdetailsDto.getId()){
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("duplicate.tsheet.date"));
				return result;
			}
			// if reqester updates 
			UserTaskTimesheetMap[] userTaskTimeSheetMapDtoArr = userTaskTimesheetDao.findByDynamicSelect("SELECT * FROM USER_TASK_TIMESHEET_MAP WHERE TS_ID= ? ORDER BY ID ASC", new Object[] { timesheetdetailsDto.getId() });
			if (timeSheetDet.getUserId().intValue() == loginDto.getUserId().intValue()){
				timeSheetDet.setStartDate(timesheetdetailsDto.getStartDate());
				timeSheetDet.setEndDate(timesheetdetailsDto.getEndDate());
				timeSheetdetailDao.update(new TimeSheetDetailsPk(timeSheetDet.getId()), timeSheetDet);
				updateTimesheetFields(timesheetdetailsDto, userTaskTimeSheetMapDtoArr, userTaskTimesheetDao);
			} else{ //if approver changes ETC.
				TimesheetReqDao timeSheetReqDao = TimesheetReqDaoFactory.create();
				if (timeSheetArr.length == 0){
					result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.actiontaken"));
					return result;
				}
				TimesheetReq timeSheetReq = null;
				try{
					if (EnumUtil.PermissionType.CANCELLED != EnumUtil.PermissionType.getValue(timesheetdetailsDto.getStatus())) timeSheetReq = timeSheetReqDao.findByDynamicSelect("SELECT * FROM TIMESHEET_REQ WHERE SEQUENCE=(SELECT SEQUENCE FROM TIMESHEET_REQ WHERE ID=(SELECT MAX(ID) FROM TIMESHEET_REQ WHERE TSHEET_ID= ?)) AND ESRQM_ID=(SELECT MAX(ESRQM_ID) FROM TIMESHEET_REQ WHERE TSHEET_ID= ?) AND ASSIGNED_TO=?", new Object[] { timeSheetDet.getId(), timeSheetDet.getId(), userId })[0];
					else timeSheetReq = timeSheetReqDao.findByDynamicSelect("SELECT * FROM TIMESHEET_REQ WHERE ID = (SELECT max(ID) FROM TIMESHEET_REQ WHERE TSHEET_ID= ?  AND ASSIGNED_TO=?)", new Object[] { timeSheetDet.getId(), userId })[0];
				} catch (Exception e){
					result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
					return result;
				}
				if (!ModelUtiility.getInstance().isValidApprover(timeSheetReq.getEsrqmId(), userId)){
					result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
					return result;
				}
				if (timeSheetReq.getStatus().equalsIgnoreCase(Status.APPROVED)){
					result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.actiontaken"));
					return result;
				}
				if (timesheetdetailsDto.getTimeSheetDataArr().length > 0 && userTaskTimeSheetMapDtoArr.length > 0 && timesheetdetailsDto.getTimeSheetDataArr().length == userTaskTimeSheetMapDtoArr.length){
					updateTimesheetFields(timesheetdetailsDto, userTaskTimeSheetMapDtoArr, userTaskTimesheetDao);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	private void updateTimesheetFields(TimeSheetDetails timesheetdetailsDto, UserTaskTimesheetMap[] userTaskTimeSheetMapDtoArr, UserTaskTimesheetMapDao userTaskTimesheetDao) throws Exception {
		/*for (UserTaskTimesheetMap mapids : userTaskTimeSheetMapDtoArr)
			userTaskTimesheetDao.delete(new UserTaskTimesheetMapPk(mapids.getId()));*/
		if (timesheetdetailsDto.getTimeSheetDataArr().length > 0){
			if (logger.isTraceEnabled()) logger.trace(Arrays.asList(timesheetdetailsDto.getTimeSheetDataArr()));
			int count = 0;
			for (String parsableStr : timesheetdetailsDto.getTimeSheetDataArr()){
				UserTaskTimesheetMap userTaskTimeSheetMapDto = null;
				Object[] objArr = DtoToBeanConverter.populateFormFromString(parsableStr);
				userTaskTimeSheetMapDto = (UserTaskTimesheetMap) objArr[0];
				if (count < userTaskTimeSheetMapDtoArr.length){
					userTaskTimeSheetMapDto.setId(userTaskTimeSheetMapDtoArr[count].getId());
				}
				userTaskTimeSheetMapDto.setTsId(timesheetdetailsDto.getId());
				if (userTaskTimeSheetMapDto.getId() == 0) userTaskTimesheetDao.insert(userTaskTimeSheetMapDto);
				else userTaskTimesheetDao.update(userTaskTimeSheetMapDto.createPk(), userTaskTimeSheetMapDto);
				count++;
				/*if (userTaskTSMapPk != null && objArr != null && objArr.length > 1){
					String[] hourSpentArr = ((String) objArr[1]).split("~!~");
					for (int i = 0; i < hourSpentArr.length; i++){
						UserTaskFilled userTaskFilledDto = new UserTaskFilled();
						userTaskFilledDto.setHoursSpent(hourSpentArr[i].length() > 249 ? hourSpentArr[i].substring(0, 249) : hourSpentArr[i]);
						userTaskFilledDto.setMapId(userTaskTSMapPk.getId());
						userTaskFilledDao.insert(userTaskFilledDto);
					}
				}*/
			}
			if (count < userTaskTimeSheetMapDtoArr.length){
				for (; count < userTaskTimeSheetMapDtoArr.length; count++)
					userTaskTimesheetDao.delete(userTaskTimeSheetMapDtoArr[count].createPk());
			}
		}
	}

	private boolean validateDate(Date startDate, Date endDate) {
		if (startDate != null && endDate != null) return (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24) == 7;
		return false;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		return null;
	}

	public ActionResult submit(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		try{
			MailGenerator mailGenarator = new MailGenerator();
			InboxModel inboxModel = new InboxModel();
			TimeSheetDetails timesheetdetailsDto = (TimeSheetDetails) form;
			TimeSheetDetailsDao timeSheetdetailDao = TimeSheetDetailsDaoFactory.create();
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			UsersDao userDao = UsersDaoFactory.create();
			TimesheetReqDao timeSheetReqDao = TimesheetReqDaoFactory.create();
			TimesheetReq timeSheetReq = null;
			ProcessChain processChainDto = null;
			Login login = Login.getLogin(request);
			int userId = login.getUserId().intValue();
			// give temperary access to vikram chandra be half of abhishek.
			if (userId == 38){
				userId = 24;
			}
			if (timesheetdetailsDto.getTimeSheetIds().length > 0){
				Integer[] timeSheetIdArray = timesheetdetailsDto.getTimeSheetIds();
				Users userApproverDto = userDao.findByPrimaryKey(userId);
				ProfileInfo profileInfoDtoOfApprover = profileInfoDao.findByPrimaryKey(userApproverDto.getProfileId());
				for (int j = 0; j < timeSheetIdArray.length; j++){
					TimeSheetDetails tempTimeSheetdto = timeSheetdetailDao.findByPrimaryKey(timeSheetIdArray[j]);
					try{
						if (EnumUtil.PermissionType.CANCELLED != EnumUtil.PermissionType.getValue(timesheetdetailsDto.getStatus())) timeSheetReq = timeSheetReqDao.findByDynamicSelect("SELECT * FROM TIMESHEET_REQ WHERE SEQUENCE=(SELECT SEQUENCE FROM TIMESHEET_REQ WHERE ID=(SELECT MAX(ID) FROM TIMESHEET_REQ WHERE TSHEET_ID= ?)) AND ESRQM_ID=(SELECT MAX(ESRQM_ID) FROM TIMESHEET_REQ WHERE TSHEET_ID= ?) AND ASSIGNED_TO=?", new Object[] { tempTimeSheetdto.getId(), tempTimeSheetdto.getId(), userId })[0];
						else timeSheetReq = timeSheetReqDao.findByDynamicSelect("SELECT * FROM TIMESHEET_REQ WHERE ID = (SELECT max(ID) FROM TIMESHEET_REQ WHERE TSHEET_ID= ?  AND ASSIGNED_TO=?)", new Object[] { tempTimeSheetdto.getId(), userId })[0];
					} catch (Exception e){
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
						continue;
					}
					if (!ModelUtiility.getInstance().isValidApprover(timeSheetReq.getEsrqmId(), userId)){
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
						continue;
					}
					processChainDto = getProcessChainForFeatures(timeSheetReq.getEsrqmId());
					Users userDto = userDao.findByPrimaryKey(tempTimeSheetdto.getUserId().intValue());
					ProfileInfo profileInfoDto = profileInfoDao.findByPrimaryKey(userDto.getProfileId());
					if (tempTimeSheetdto.getStatus().equalsIgnoreCase(Status.NEW) || tempTimeSheetdto.getStatus().equalsIgnoreCase(Status.CANCELLED) || tempTimeSheetdto.getStatus().equalsIgnoreCase(Status.REJECTED)){
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.reqcancelled", "Time-sheet (" + tempTimeSheetdto.getStartDate() + " to " + tempTimeSheetdto.getEndDate() + ") "));
						continue;
					}
					if (timeSheetReq.getAssignedTo() != userId && EnumUtil.PermissionType.CANCELLED != EnumUtil.PermissionType.getValue(timesheetdetailsDto.getStatus())){
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.actiontaken"));
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.or"));
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.notauthorised"));
						continue;
					}
					switch (EnumUtil.PermissionType.getValue(timesheetdetailsDto.getStatus())) {
						case APPROVE:{
							if (timeSheetReq.getStatus().equalsIgnoreCase(Status.SUBMITTED) || timeSheetReq.getStatus().equalsIgnoreCase(Status.ON_HOLD) || timeSheetReq.getStatus().equalsIgnoreCase(Status.PENDINGAPPROVAL)){
								if (tempTimeSheetdto.getStatus().equalsIgnoreCase(Status.SUBMITTED) && timesheetdetailsDto.getComp_off() == 0){
									tempTimeSheetdto.setComp_off(0);
								}
								/*TimesheetReq timesheetReqDto = new TimesheetReq();
								timesheetReqDto.setRaisedBy(tempTimeSheetdto.getUserId().intValue());
								timesheetReqDto.setStatus(Status.APPROVED);
								if (timesheetdetailsDto.getComments() != null) timesheetReqDto.setComments(timesheetdetailsDto.getComments());
								timesheetReqDto.setTsheetId(tempTimeSheetdto.getId());
								timesheetReqDto.setEsrqmId(timeSheetReq.getEsrqmId());
								timesheetReqDto.setSummary("TIME-SHEET IS  APPROVED");
								timesheetReqDto.setActionBy(login.getUserId().intValue());
								timesheetReqDto.setSequence(timeSheetReq.getSequence());
								timeSheetReqDao.insert(timesheetReqDto);*/
								timeSheetReq.setStatus(Status.APPROVED);
								timeSheetReq.setSummary("TIME-SHEET IS  APPROVED");
								timeSheetReq.setActionBy(userId);
								if (timesheetdetailsDto.getComments() != null) timeSheetReq.setComments(timesheetdetailsDto.getComments());
								timeSheetReqDao.update(timeSheetReq.createPk(), timeSheetReq);
								updateStatusOfsiblingUsers(Status.APPROVED, userId, timeSheetReq.getEsrqmId(), timeSheetReq.getSequence());
								int appLevel = timeSheetReq.getSequence() + 1;
								ProcessEvaluator evaluator = new ProcessEvaluator();
								Integer[] approverGroupIds = evaluator.approvers(processChainDto.getApprovalChain(), appLevel, tempTimeSheetdto.getUserId());
								if (approverGroupIds.length < 1){
									tempTimeSheetdto.setStatus(Status.APPROVED);
									timeSheetdetailDao.update(new TimeSheetDetailsPk(tempTimeSheetdto.getId()), tempTimeSheetdto);
									addCompOff(tempTimeSheetdto.getComp_off(), tempTimeSheetdto.getUserId().intValue());
									// if it is final approve then we have to place latest entry with max id of other same request entries...
									int prevReqId = timeSheetReq.getId();
									timeSheetReq.setId(0);
									timeSheetReqDao.insert(timeSheetReq);
									timeSheetReqDao.delete(new TimesheetReqPk(prevReqId));
									String timePeriod = PortalUtility.getdd_MM_yyyy(tempTimeSheetdto.getStartDate()) + " to " + PortalUtility.getdd_MM_yyyy(tempTimeSheetdto.getEndDate());
									if (timesheetdetailsDto.getComments() != null) tempTimeSheetdto.setComments(timesheetdetailsDto.getComments());
									List<String> ccMailIds = new ArrayList<String>();
									ccMailIds.addAll(getHRAndRMEmailIds(profileInfoDto.getHrSpoc(), profileInfoDto.getReportingMgr()));
									try{
										Integer[] notifiers = evaluator.notifiers(processChainDto.getNotification(), tempTimeSheetdto.getUserId());
										if (notifiers != null && notifiers.length > 0) ccMailIds.addAll(Arrays.asList(profileInfoDao.findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN ( " + ModelUtiility.getCommaSeparetedValues(notifiers) + ")) AND OFFICAL_EMAIL_ID IS NOT NULL")));
									} catch (Exception e){
										logger.error("unable to find notifiers" + e.getMessage());
									}
									PortalMail portalMail = sendEmailToRecipient("Timesheet Approved by " + profileInfoDtoOfApprover.getFirstName() + " for " + timePeriod, MailSettings.TS_NOTIFY_USER, tempTimeSheetdto, userDto, profileInfoDto, profileInfoDtoOfApprover, ccMailIds);
									// sending Android Notification
									String message="Timesheet Approved by " + profileInfoDtoOfApprover.getFirstName() + " for " + timePeriod;
									List<String> mailids=new ArrayList<String>();
									mailids.add(portalMail.getRecipientMailId());
									if(portalMail.getAllReceipientcCMailId()!=null){
									for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
									sendAndroidLeaveNotification(message,mailids);
									
									String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.TS_NOTIFY_USER), portalMail);
									//timesheetReqDto.setMessageBody(bodyText);
									//timeSheetReqDao.update(new TimesheetReqPk(timesheetReqDto.getId()), timesheetReqDto);
									//inboxModel.populateInboxForTimeSheetRequestor(timeSheetReq.getEsrqmId(), timeSheetReq.getSequence(), userDto.getId(), "TIME-SHEET IS  APPROVED", bodyText, Status.APPROVED);
									inboxModel.populateInbox(timeSheetReq.getEsrqmId(), portalMail.getMailSubject(), timeSheetReq.getStatus(), 0, userDto.getId(), timeSheetReq.getRaisedBy(), bodyText, TIMESHEET);
								} else{
									tempTimeSheetdto.setStatus(Status.PENDINGAPPROVAL);
									timeSheetdetailDao.update(new TimeSheetDetailsPk(tempTimeSheetdto.getId()), tempTimeSheetdto);
									if (timesheetdetailsDto.getComments() != null) tempTimeSheetdto.setComments(timesheetdetailsDto.getComments());
									ModelUtiility.getInstance().updateSiblings(approverGroupIds, timeSheetReq.getEsrqmId());
									for (int approverId : approverGroupIds){
										String templateName = MailSettings.TSHEET_SUBMITTED_TO_APPROVER;
										String timePeriod = PortalUtility.getdd_MM_yyyy(tempTimeSheetdto.getStartDate()) + " to " + PortalUtility.getdd_MM_yyyy(tempTimeSheetdto.getEndDate());
										String mailSubject = "Diksha Lynx: Timesheet Submitted by " + profileInfoDto.getFirstName() + " " + profileInfoDto.getLastName() + " for " + timePeriod;
										ProfileInfo approverProfileInfo = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO WHERE ID= (SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { approverId })[0];
										PortalMail portalMail = sendEmailToRecipient(mailSubject, templateName, tempTimeSheetdto, userDto, profileInfoDto, approverProfileInfo, null);
										// sending Android Notification
										
										List<String> mailids=new ArrayList<String>();
										mailids.add(portalMail.getRecipientMailId());
										if(portalMail.getAllReceipientcCMailId()!=null){
										for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
										sendAndroidLeaveNotification(mailSubject,mailids);
										
										
										TimesheetReq tempTimesheetReqDto = new TimesheetReq();
										tempTimesheetReqDto.setRaisedBy(tempTimeSheetdto.getUserId().intValue());
										tempTimesheetReqDto.setAssignedTo(approverId);
										tempTimesheetReqDto.setStatus(Status.SUBMITTED);
										tempTimesheetReqDto.setTsheetId(tempTimeSheetdto.getId());
										tempTimesheetReqDto.setEsrqmId(timeSheetReq.getEsrqmId());
										tempTimesheetReqDto.setSequence(appLevel);
										tempTimesheetReqDto.setSummary("TIME-SHEET IS SUBMITTED");
										String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.TSHEET_SUBMITTED_TO_APPROVER), portalMail);
										//tempTimesheetReqDto.setMessageBody(bodyText);
										/*TimesheetReqPk timesheetReqPk1 =*/timeSheetReqDao.insert(tempTimesheetReqDto);
										inboxModel.populateInbox(timeSheetReq.getEsrqmId(), portalMail.getMailSubject(), tempTimesheetReqDto.getStatus(), tempTimesheetReqDto.getAssignedTo(), tempTimesheetReqDto.getAssignedTo(), tempTimesheetReqDto.getRaisedBy(), bodyText, TIMESHEET);
										//inboxModel.sendToDockingStation(tempTimesheetReqDto.getEsrqmId(), timesheetReqPk1.getId(), tempTimesheetReqDto.getSummary(), tempTimesheetReqDto.getStatus());
									}
								}
							} else{
								result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.actiontaken"));
							}
							break;
						}
						case REJECT:{
							if (timeSheetReq.getStatus().equalsIgnoreCase(Status.SUBMITTED) || timeSheetReq.getStatus().equalsIgnoreCase(Status.ON_HOLD)){
								// |--Inserting record into TimesheetReq table
								String timePeriod = PortalUtility.getdd_MM_yyyy(tempTimeSheetdto.getStartDate()) + " to " + PortalUtility.getdd_MM_yyyy(tempTimeSheetdto.getEndDate());
								String mailSubject = "Diksha Lynx: Timesheet Rejected by " + profileInfoDtoOfApprover.getFirstName() + " for " + timePeriod;
								if (timesheetdetailsDto.getComments() != null) tempTimeSheetdto.setComments(timesheetdetailsDto.getComments());
								List<String> ccMailIds = new ArrayList<String>();
								ccMailIds.addAll(getHRAndRMEmailIds(profileInfoDto.getHrSpoc(), -1));
								if (timesheetdetailsDto.getComments() != null) tempTimeSheetdto.setComments(timesheetdetailsDto.getComments());
								PortalMail portalMail = sendEmailToRecipient(mailSubject, MailSettings.TS_NOTIFY_USER, tempTimeSheetdto, userDto, profileInfoDto, profileInfoDtoOfApprover, ccMailIds);
								// sending Android Notification
								List<String> mailids=new ArrayList<String>();
								mailids.add(portalMail.getRecipientMailId());
								if(portalMail.getAllReceipientcCMailId()!=null){
								for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
								sendAndroidLeaveNotification(mailSubject,mailids);
								
								String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.TS_NOTIFY_USER), portalMail);
								//inboxModel.populateInboxForTimeSheetRequestor(timeSheetReq.getEsrqmId(), timeSheetReq.getSequence(), userDto.getId(), "TIME-SHEET IS REJECTED", bodyText, Status.REJECTED);
								inboxModel.populateInbox(timeSheetReq.getEsrqmId(), portalMail.getMailSubject(), Status.REJECTED, 0, userDto.getId(), userId, bodyText, TIMESHEET);
								TimesheetReq timesheetReqDto = new TimesheetReq();
								timesheetReqDto.setRaisedBy(tempTimeSheetdto.getUserId().intValue());
								timesheetReqDto.setAssignedTo(timeSheetReq.getAssignedTo());
								timesheetReqDto.setStatus(Status.REJECTED);
								if (timesheetdetailsDto.getComments() != null) timesheetReqDto.setComments(timesheetdetailsDto.getComments());
								timesheetReqDto.setTsheetId(tempTimeSheetdto.getId());
								timesheetReqDto.setEsrqmId(timeSheetReq.getEsrqmId());
								timesheetReqDto.setSummary("TIME-SHEET IS REJECTED BY USER ID:" + login.getUserId().intValue());
								//timesheetReqDto.setMessageBody(bodyText);
								timesheetReqDto.setActionBy(userId);
								timesheetReqDto.setSequence(timeSheetReq.getSequence());
								timeSheetReqDao.insert(timesheetReqDto);
								updateStatusOfsiblingUsers(Status.REJECTED, userId, timeSheetReq.getEsrqmId(), timeSheetReq.getSequence());
								tempTimeSheetdto.setStatus(Status.REJECTED);
								tempTimeSheetdto.setComp_off(0);
								timeSheetdetailDao.update(new TimeSheetDetailsPk(tempTimeSheetdto.getId()), tempTimeSheetdto);
							} else result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.actiontaken"));
							break;
						}
						case CANCELLED:{
							if (!tempTimeSheetdto.getStatus().equalsIgnoreCase(Status.NEW) && !tempTimeSheetdto.getStatus().equalsIgnoreCase(Status.CANCELLED) && !tempTimeSheetdto.getStatus().equalsIgnoreCase(Status.REJECTED)){
								TimesheetReq timesheetReqDto = new TimesheetReq();
								timesheetReqDto.setRaisedBy(tempTimeSheetdto.getUserId().intValue());
								timesheetReqDto.setAssignedTo(userId);
								timesheetReqDto.setActionBy(userId);
								timesheetReqDto.setStatus(Status.CANCELLED);
								if (timesheetdetailsDto.getComments() != null) timesheetReqDto.setComments(timesheetdetailsDto.getComments());
								timesheetReqDto.setTsheetId(tempTimeSheetdto.getId());
								timesheetReqDto.setEsrqmId(timeSheetReq.getEsrqmId());
								timesheetReqDto.setSummary("TIME-SHEET IS CANCELLED");
								TimesheetReqPk timesheetReqPk = timeSheetReqDao.insert(timesheetReqDto);
								addCompOff(-tempTimeSheetdto.getComp_off(), tempTimeSheetdto.getUserId().intValue());
								if (timesheetReqPk != null){
									tempTimeSheetdto.setStatus(Status.CANCELLED);
									tempTimeSheetdto.setComp_off(0);
									timeSheetdetailDao.update(new TimeSheetDetailsPk(tempTimeSheetdto.getId()), tempTimeSheetdto);
								}
							} else result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.tm.cantcancel"));
							break;
						}
						case ONHOLD:{
							if (timeSheetReq.getStatus().equalsIgnoreCase(Status.SUBMITTED)){
								//String timePeriod = PortalUtility.getdd_MM_yyyy(tempTimeSheetdto.getStartDate()) + " to " + PortalUtility.getdd_MM_yyyy(tempTimeSheetdto.getEndDate());
								//String mailSubject = "Timesheet is put on hold by " + profileInfoDtoOfApprover.getFirstName() + " for " + timePeriod;
								if (timesheetdetailsDto.getComments() != null) timeSheetReq.setComments(timesheetdetailsDto.getComments());
								//List<String> ccMailIds = new ArrayList<String>();
								//ccMailIds.addAll(getHRAndRMEmailIds(profileInfoDto.getHrSpoc(), -1));
								//PortalMail portalMail = sendEmailToRecipient(mailSubject, MailSettings.TS_NOTIFY_USER, tempTimeSheetdto, userDto, profileInfoDto, profileInfoDtoOfApprover, ccMailIds);
								//String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.TS_NOTIFY_USER), portalMail);
								/*TimesheetReq timesheetReqDto = new TimesheetReq();
								timesheetReqDto.setRaisedBy(tempTimeSheetdto.getUserId().intValue());
								timesheetReqDto.setAssignedTo(timeSheetReq.getAssignedTo());
								timesheetReqDto.setStatus(Status.ON_HOLD);
								timesheetReqDto.setTsheetId(tempTimeSheetdto.getId());
								timesheetReqDto.setComments(tempTimeSheetdto.getComments());
								timesheetReqDto.setEsrqmId(timeSheetReq.getEsrqmId());
								timesheetReqDto.setSummary("TIME-SHEET IS ON ONHOLD");
								timesheetReqDto.setSequence(timeSheetReq.getSequence());
								timesheetReqDto.setMessageBody(bodyText);
								timesheetReqDto.setActionBy(login.getUserId().intValue());
								timeSheetReqDao.insert(timesheetReqDto);*/
								//timeSheetReq.setMessageBody(bodyText);
								timeSheetReqDao.update(new TimesheetReqPk(timeSheetReq.getId()), timeSheetReq);
								updateStatusOfsiblingUsers(Status.ON_HOLD, userId, timeSheetReq.getEsrqmId(), timeSheetReq.getSequence());
								//  MAIL TO REQUESTER FOR ONHOLD
								//tempTimeSheetdto.setStatus(Status.ON_HOLD);
								//timeSheetdetailDao.update(new TimeSheetDetailsPk(tempTimeSheetdto.getId()), tempTimeSheetdto);
							} else result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.actiontaken"));
						}
							break;
						default:
							break;
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	private void addCompOff(float compoffCount, int userId) {
		if (compoffCount != 0){
			JDBCUtiility.getInstance().update("UPDATE LEAVE_BALANCE SET COMP_OFF = COMP_OFF+? WHERE USER_ID=?", new Object[] { compoffCount, userId });
		}
	}

	private void updateStatusOfsiblingUsers(String status, int actionBy, int esrqmId, int sequence) {
		try{
			TimesheetReqDao tm = TimesheetReqDaoFactory.create();
			if (!status.equals(Status.ON_HOLD)){
				ModelUtiility.getInstance().deleteInboxEntries(esrqmId);
			} else{
				tm.update("UPDATE INBOX SET STATUS=? WHERE ESR_MAP_ID=?", new Object[] { status, esrqmId });
			}
			tm.update("UPDATE TIMESHEET_REQ SET STATUS=?,ACTION_BY=? WHERE ESRQM_ID=? AND SEQUENCE=? ", new Object[] { status, actionBy, esrqmId, sequence });
		} catch (TimesheetReqDaoException e){
			e.printStackTrace();
		}
	}

	/**
	 * @author gurunath.rokkam
	 * @param portalMail
	 * @param userId
	 * @param userDto
	 * @param profileInfoDto
	 */
	/*
	private static List<String> getHandlersEmails(Integer userId) {
	try{
		Regions rgnDto = RegionsDaoFactory.create().findByDynamicSelect("SELECT * FROM REGIONS R JOIN DIVISON D ON R.ID=D.REGION_ID JOIN LEVELS L ON L.DIVISION_ID=D.ID JOIN USERS U ON U.LEVEL_ID=L.ID WHERE U.ID=?", new Object[] { userId })[0];
		Notification notification = new Notification(rgnDto.getRefAbbreviation());
		String[] ids = ProfileInfoDaoFactory.create().findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS U JOIN LEVELS L ON L.ID=U.LEVEL_ID WHERE DIVISION_ID=" + notification.financeId + " AND U.STATUS NOT IN (1,2,3) )");
		if (ids != null) return Arrays.asList(ids);
	} catch (Exception e){}
	return new ArrayList<String>();
	}*/
	/**
	 * @author gurunath.rokkam
	 * @param hruserId
	 * @param rmuserId
	 * @return List of email ids.
	 */
	private List<String> getHRAndRMEmailIds(Integer hruserId, Integer rmuserId) {
		try{
			String[] ids = ProfileInfoDaoFactory.create().findOfficalMailIdsWhere("WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID IN (" + hruserId + "," + rmuserId + "))");
			if (ids != null) return Arrays.asList(ids);
		} catch (Exception e){}
		return new ArrayList<String>();
	}

	/**
	 * @author gurunath.rokkam
	 * @param portalMail
	 * @param userDto
	 * @param profileInfoDto
	 * @throws Exception
	 */
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
		} catch (Exception e){
			// TODO: handle exception
		}
	}

	/**
	 * @author gurunath.rokkam
	 * @param portalMail
	 * @param userId
	 */
	private void setProjectDetails(PortalMail portalMail, int userId) {
		try{
			Project p = ProjectDaoFactory.create().findByDynamicSelect("SELECT * FROM PROJECT WHERE ID = (SELECT PROJ_ID FROM ROLL_ON_PROJ_MAP WHERE ROLL_ON_ID =(SELECT ID FROM ROLL_ON WHERE EMP_ID=? AND CURRENT=1))", new Object[] { userId })[0];
			portalMail.setProjectId(p.getId());
			portalMail.setProjectName(p.getName());
			ChargeCode[] c = ChargeCodeDaoFactory.create().findByDynamicSelect("SELECT * FROM CHARGE_CODE WHERE ID=(SELECT CH_CODE_ID FROM ROLL_ON WHERE EMP_ID=? AND CURRENT=1)", new Object[] { userId });
			if (c != null && c.length > 0 && c[0] != null){
				portalMail.setChargeCodeTitle(c[0].getChCodeName() == null ? "N.A" : c[0].getChCodeName());
			} else portalMail.setChargeCodeTitle("N.A");
		} catch (Exception e){
			portalMail.setProjectId(0);
			portalMail.setProjectName("N.A");
			portalMail.setChargeCodeTitle("N.A");
		}
	}

	/**
	 * @author gurunath.rokkam
	 * @param tempTimeSheetdto
	 * @param request
	 * @return total comp-offs for timesheet
	 */
	private float getCompoffCount(TimeSheetDetails tempTimeSheetdto, Login login) {
		UserTaskTimesheetMapDao utDao = UserTaskTimesheetMapDaoFactory.create();
		//UserTaskFilledDao UtaskDao = UserTaskFilledDaoFactory.create();
		//UserTaskFilled[] usrTaskFld = null;
		UserTaskTimesheetMap[] usrTaskMap = null;
		Date startDate = new Date(tempTimeSheetdto.getStartDate().getTime());
		Date endDate = new Date(tempTimeSheetdto.getEndDate().getTime());
		Calendar holiCal = Calendar.getInstance();
		Map<Integer, Double> holidayMap = new HashMap<Integer, Double>();
		float compOff = 0;
		try{
			usrTaskMap = utDao.findWhereTsIdEquals(tempTimeSheetdto.getId());
			String startDat = PortalUtility.getdd_MM_yyyy(startDate);
			List<String> workingDaysList = getHolidayWorkingDays();
			List<String> workingDList = new ArrayList<String>();
			for (int ii = 0; ii < 7;){
				for (String day : workingDaysList){
					if (day.equals(startDat)) workingDList.add(ii + "");
				}
				startDat = PortalUtility.getdd_MM_yyyy(new Date(startDate.getTime() + (++ii * 24 * 60 * 60 * 1000)));
			}
			Holidays[] holiday = HolidaysDaoFactory.create().findByDynamicSelect("SELECT * FROM HOLIDAYS WHERE CAL_ID IN (SELECT ID FROM CALENDAR WHERE REGION = ?) AND DATE_PICKER BETWEEN ? AND ? ", new Object[] { login.getUserLogin().getRegionId(), startDate, endDate });
			if (holiday != null && holiday.length > 0) for (Holidays tmpHoliday : holiday){
				holiCal.setTime(tmpHoliday.getDatePicker());
				// in calender S-1,M-2,..S-7
				// in usrTaskFld M-0,T-1...SAT-5,SUN-6
				if (holiCal.get(Calendar.DAY_OF_WEEK) == 1) continue;
				if (!workingDList.contains((holiCal.get(Calendar.DAY_OF_WEEK) - 2) + "")) holidayMap.put(holiCal.get(Calendar.DAY_OF_WEEK) - 2, 0.0);
			}
			// for saturday and sunday.
			if (!workingDList.contains("5")) holidayMap.put(5, 0.0);
			if (!workingDList.contains("6")) holidayMap.put(6, 0.0);
			for (UserTaskTimesheetMap taskMap : usrTaskMap){
				/*usrTaskFld = UtaskDao.findByUserTaskTimesheetMap(taskMap.getId());
				if (holidayMap.size() > 0 && usrTaskFld != null && usrTaskFld.length > 0){
					Set<Integer> keys = holidayMap.keySet();
					for (int i : keys){
						if (usrTaskFld.length >= i){
							double time = 0.0;
							try{
								String[] taskArr = usrTaskFld[i].getHoursSpent().split("\\|");
								time = Double.parseDouble(taskArr[0]);
							} catch (Exception e){
								e.printStackTrace();
							}
							holidayMap.put(i, addTime(holidayMap.get(i), time));
						}
					}
				}*/
				Set<Integer> keys = holidayMap.keySet();
				for (int i : keys){
					double time = 0.0;
					try{
						switch (i) {
							case 0:
								time = Double.parseDouble(taskMap.getMonDetails()[0]);
								break;
							case 1:
								time = Double.parseDouble(taskMap.getTueDetails()[0]);
								break;
							case 2:
								time = Double.parseDouble(taskMap.getWedDetails()[0]);
								break;
							case 3:
								time = Double.parseDouble(taskMap.getThuDetails()[0]);
								break;
							case 4:
								time = Double.parseDouble(taskMap.getFriDetails()[0]);
								break;
							case 5:
								time = Double.parseDouble(taskMap.getSatDetails()[0]);
								break;
							case 6:
								time = Double.parseDouble(taskMap.getSunDetails()[0]);
								break;
							default:
								break;
						}
					} catch (Exception e){
						e.printStackTrace();
					}
					holidayMap.put(i, addTime(holidayMap.get(i), time));
				}
			}
			if (holidayMap.size() > 0){
				Set<Integer> keys = holidayMap.keySet();
				for (int i : keys){
					if (holidayMap.get(i) > 0){
						compOff = holidayMap.get(i) >= 7 ? compOff + 1 : (compOff + 0.5f);
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return compOff;
	}

	private List<String> getHolidayWorkingDays() {
		try{
			String filePath = PropertyLoader.getEnvVariable() + File.separator + "Data" + File.separator + "leave" + File.separator + "doc";
			File file = new File(filePath);
			if (file.exists() && file.list().length > 0){
				String outputFile = file.list()[0];
				Scanner sc = new Scanner(new File(filePath + File.separator + outputFile));
				List<String> workingDays = new ArrayList<String>();
				while (sc.hasNextLine())
					workingDays.add(sc.nextLine());
				return workingDays;
			}
		} catch (Exception e){
			logger.error("unable to fetch holidays as working days....", e);
		}
		return new ArrayList<String>();
	}

	/**
	 * @author gurunath.rokkam
	 * @param CurrTime
	 * @param addTime
	 * @return total time by adding two times
	 */
	private double addTime(double CurrTime, double addTime) {
		//consider only first two digits after '.'
		// EX: if 2.3699-->2.36
		CurrTime = ((int) (CurrTime * 100) + 0.0) / 100;
		addTime = ((int) (addTime * 100) + 0.0) / 100;
		return Double.parseDouble(new DecimalFormat("0.00").format((((int) CurrTime) + ((int) addTime)) + ((int) (((CurrTime * 100 - ((int) CurrTime) * 100) + (addTime * 100 - ((int) addTime) * 100)) / 60)) + (((CurrTime * 100 - ((int) CurrTime) * 100) + (addTime * 100 - ((int) addTime) * 100)) % 60 / 100)));
	}

	private void timeSheetServiceReqInvoke(PortalForm form, HttpServletRequest request, TimeSheetDetails timesheetDto) {
		try{
			if (timesheetDto != null){
				MailGenerator mailGenarator = new MailGenerator();
				InboxModel inboxModel = new InboxModel();
				Login login = Login.getLogin(request);
				EmpSerReqMapDao empSerDao = EmpSerReqMapDaoFactory.create();
				ProcessChainDao pDao = ProcessChainDaoFactory.create();
				TimesheetReqDao timeSheetReqDao = TimesheetReqDaoFactory.create();
				ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
				UsersDao userDao = UsersDaoFactory.create();
				Users userDto = userDao.findByPrimaryKey(login.getUserId());
				ProfileInfo profileInfoDto = profileInfoDao.findByPrimaryKey(userDto.getProfileId());
				Set<com.dikshatech.beans.Roles> roles = login.getUserLogin().getRoles();
				com.dikshatech.beans.Roles roles2 = (com.dikshatech.beans.Roles) roles.toArray()[0];
				Object[] sqlParams = { roles2.getRoleId(), 36 };
				pDao.setMaxRows(1);
				String SQL = "ID = (SELECT PROC_CHAIN_ID FROM MODULE_PERMISSION WHERE ROLE_ID = ? AND MODULE_ID = ?)";
				ProcessChain pChain = pDao.findByDynamicWhere(SQL, sqlParams)[0];
				// |--Inserting record into empSerReqMap table
				RegionsDao regionsDao = RegionsDaoFactory.create();
				Regions regions = regionsDao.findByPrimaryKey(login.getUserLogin().getRegionId());
				EmpSerReqMap eMap = null;
				ProcessEvaluator pEvaluator = new ProcessEvaluator();
				int appLevel = 1;
				Integer[] approverGroupIds = pEvaluator.approvers(pChain.getApprovalChain(), appLevel, login.getUserId());
				EmpSerReqMap[] esmap = empSerDao.findByDynamicSelect("SELECT * FROM EMP_SER_REQ_MAP WHERE REQ_ID=? AND REQUESTOR_ID=?", new Object[] { regions.getRefAbbreviation() + "-TS-" + timesheetDto.getId(), login.getUserId() });
				if (esmap != null && esmap.length > 0 && esmap[0] != null){
					eMap = esmap[0];
					if (approverGroupIds != null && approverGroupIds.length > 0) eMap.setSiblings(Arrays.toString(approverGroupIds).replace("[", "").replace("]", ""));
					empSerDao.update(new EmpSerReqMapPk(eMap.getId()), eMap);
					JDBCUtiility.getInstance().update("UPDATE TIMESHEET_REQ SET SEQUENCE = 0-SEQUENCE WHERE ESRQM_ID=? AND SEQUENCE>0 ", new Object[] { eMap.getId() });
					ModelUtiility.getInstance().deleteInboxEntries(eMap.getId());
				} else{
					eMap = new EmpSerReqMap();
					eMap.setSubDate(new Date());
					eMap.setReqId(regions.getRefAbbreviation() + "-TS-" + timesheetDto.getId());
					eMap.setReqTypeId(8);
					eMap.setRegionId(regions.getId());
					eMap.setRequestorId(login.getUserId());
					eMap.setProcessChainId(pChain.getId());
					if (approverGroupIds != null && approverGroupIds.length > 0) eMap.setSiblings(Arrays.toString(approverGroupIds).replace("[", "").replace("]", ""));
					EmpSerReqMapPk eMapPk = empSerDao.insert(eMap);
					eMap.setId(eMapPk.getId());
				}
				// update the comp off
				timesheetDto.setComp_off(getCompoffCount(timesheetDto, login));
				TimeSheetDetailsDaoFactory.create().update(new TimeSheetDetailsPk(timesheetDto.getId()), timesheetDto);
				for (int i = 0; i < approverGroupIds.length; i++){
					// |--Inserting record into TimesheetReq table
					String templateName = MailSettings.TSHEET_SUBMITTED_TO_APPROVER;
					String timePeriod = PortalUtility.getdd_MM_yyyy(timesheetDto.getStartDate()) + " " + "to" + " " + PortalUtility.getdd_MM_yyyy(timesheetDto.getEndDate()) + "";
					String mailSubject = "Diksha Lynx: Timesheet Submitted by " + profileInfoDto.getFirstName() + " " + profileInfoDto.getLastName() + " for " + timePeriod;
					ProfileInfo approverProfileInfo = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO WHERE ID= (SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] { approverGroupIds[i] })[0];
					PortalMail portalMail = sendEmailToRecipient(mailSubject, templateName, timesheetDto, userDto, profileInfoDto, approverProfileInfo, null);
					// sending Android Notification
					List<String> mailids=new ArrayList<String>();
					mailids.add(portalMail.getRecipientMailId());
					if(portalMail.getAllReceipientcCMailId()!=null){
					for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
					sendAndroidLeaveNotification(mailSubject,mailids);
					
					TimesheetReq timesheetReqDto = new TimesheetReq();
					timesheetReqDto.setRaisedBy(login.getUserId());
					timesheetReqDto.setAssignedTo(approverGroupIds[i]);
					timesheetReqDto.setStatus(timesheetDto.getStatus());
					timesheetReqDto.setTsheetId(timesheetDto.getId());
					timesheetReqDto.setEsrqmId(eMap.getId());
					timesheetReqDto.setSummary("TIME-SHEET SUBMITTED");
					timesheetReqDto.setSequence(1);
					String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.TSHEET_SUBMITTED_TO_APPROVER), portalMail);
					//timesheetReqDto.setMessageBody(bodyText);
					/*TimesheetReqPk timesheetReqPk =*/timeSheetReqDao.insert(timesheetReqDto);
					// --| Sending for inbox notification to the approver
					//inboxModel.sendToDockingStation(timesheetReqDto.getEsrqmId(), timesheetReqPk.getId(), timesheetReqDto.getSummary(), timesheetReqDto.getStatus());
					inboxModel.populateInbox(timesheetReqDto.getEsrqmId(), mailSubject, timesheetReqDto.getStatus(), timesheetReqDto.getAssignedTo(), timesheetReqDto.getAssignedTo(), timesheetReqDto.getRaisedBy(), bodyText, TIMESHEET);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @author gurunath.rokkam
	 * @param mailSubject
	 * @param templateName
	 * @param timesheetDto
	 * @param candidateDto
	 * @param candidateProfileInfo
	 * @param approverProfileInfo
	 * @param ccMailIds
	 * @return PortalMail Object
	 */
	private PortalMail sendEmailToRecipient(String mailSubject, String templateName, TimeSheetDetails timesheetDto, Users candidateDto, ProfileInfo candidateProfileInfo, ProfileInfo approverProfileInfo, List<String> ccMailIds) {
		PortalMail portalMail = new PortalMail();
		try{
			portalMail.setMailSubject(mailSubject);
			portalMail.setTemplateName(templateName);
			portalMail.setCandidateName(candidateProfileInfo.getFirstName());
			if (templateName.equals(MailSettings.TSHEET_SUBMITTED_TO_APPROVER)) portalMail.setRecipientMailId(approverProfileInfo.getOfficalEmailId());
			else{
				portalMail.setRecipientMailId(candidateProfileInfo.getOfficalEmailId());
				if (mailSubject.contains("Approved")) portalMail.setActionType("Approved");
				else if (mailSubject.contains("Rejected")) portalMail.setActionType("Rejected");
				else if (mailSubject.contains("put on hold")) portalMail.setActionType("put on hold");
			}
			portalMail.setTsheetApprover(approverProfileInfo.getFirstName());
			portalMail.setTimePeriod(PortalUtility.getdd_MM_yyyy(timesheetDto.getStartDate()) + " " + "to" + " " + PortalUtility.getdd_MM_yyyy(timesheetDto.getEndDate()));
			portalMail.setTsheetDueDate(PortalUtility.getdd_MM_yyyy(timesheetDto.getEndDate()));
			portalMail.setTsheetSubmissionDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(new Date(timesheetDto.getSubmissionDate().getTime())));
			portalMail.setComment(getCommentsForMail(timesheetDto.getComments()));
			setEmployeeDetails(portalMail, candidateDto, candidateProfileInfo);
			setProjectDetails(portalMail, candidateDto.getId());
			if (ccMailIds != null && !ccMailIds.isEmpty()) portalMail.setAllReceipientcCMailId(Arrays.copyOf(ccMailIds.toArray(), ccMailIds.toArray().length, String[].class));
			if (portalMail.getRecipientMailId() != null && portalMail.getRecipientMailId().contains("@")) new MailGenerator().invoker(portalMail);
		} catch (Exception e){
			e.printStackTrace();
		}
		return portalMail;
	}

	public final ProcessChain getProcessChainForFeatures(int empSerReqId) {
		ProcessChainDao processDao = ProcessChainDaoFactory.create();
		ProcessChain[] pChain = null;
		try{
			Object[] sqlParams = { empSerReqId };
			processDao.setMaxRows(1);
			pChain = processDao.findByDynamicWhere(" ID = (SELECT PROCESSCHAIN_ID FROM EMP_SER_REQ_MAP WHERE ID = ?)", sqlParams);
		} catch (ProcessChainDaoException e){
			e.printStackTrace();
		}
		return pChain.length == 0 ? null : pChain[0];
	}

	/**
	 * @author gurunath.rokkam
	 * @param comments
	 * @return Html formatted comments
	 */
	private String getCommentsForMail(String comments) {
		if (comments != null && comments.length() > 0) return "<strong>Comments/Reason:</strong>&nbsp;" + comments + "<br/><br/>";
		return "";
	}

	@Override
	public Attachements download(PortalForm form) {
		Attachements attachements = new Attachements();
		TimeSheetDetails timesheetdetailsDto = (TimeSheetDetails) form;
		String seprator = File.separator;
		String path = "Data" + seprator;
		try{
			PortalData portalData = new PortalData();
			path = portalData.getfolder(portalData.getDirPath());
			File file = new File(path);
			if (!file.exists()) file.mkdir();
			switch (DownloadTypes.getValue(form.getdType())) {
				case DELAYEDTIMESHEETLIST:
					//if (ModelUtiility.hasModulePermission((Login) form.getObject(), 17)) 
					try{
						path += seprator + "temp";
						File file1 = new File(path);
						if (!file1.exists()) file1.mkdir();
						TimeSheetDetailsDao tSheetDao = TimeSheetDetailsDaoFactory.create();
						TimeSheetDetails[] delayedTimesheets = tSheetDao.findByDynamicWhere("START_DATE=? AND IS_DELAYED=1", new Object[] { timesheetdetailsDto.getStartDate() });
						List<TimesheetDelayedBean> delayedBeanList = new ArrayList<TimesheetDelayedBean>();
						UsersDao usersDao = UsersDaoFactory.create();
						ProfileInfoDao profileinfoDao = ProfileInfoDaoFactory.create();
						PoEmpMapDao poEmpMapDao = PoEmpMapDaoFactory.create();
						PoDetailsDao poDetailsdao = PoDetailsDaoFactory.create();
						ProjectDao projectDao = ProjectDaoFactory.create();
						ProjectMappingDao projectMappingDao = ProjectMappingDaoFactory.create();
						Project project;
						ProjectMapping projectMapping;
						Users user;
						ProfileInfo profileinfo;
						TimesheetDelayedBean delayedBean;
						for (TimeSheetDetails eachTimesheet : delayedTimesheets){
							delayedBean = new TimesheetDelayedBean();
							//employee details
							user = usersDao.findByPrimaryKey(eachTimesheet.getUserId());
							profileinfo = profileinfoDao.findByPrimaryKey(user.getProfileId());
							delayedBean.setEmpId(user.getEmpId());
							delayedBean.setEmpName(profileinfo.getFirstName() + " " + profileinfo.getLastName());
							//R.M details
							user = usersDao.findByPrimaryKey(profileinfo.getReportingMgr());
							profileinfo = profileinfoDao.findByPrimaryKey(user.getProfileId());
							delayedBean.setRmName(profileinfo.getFirstName() + " " + profileinfo.getLastName());
							//project details
							PoEmpMap[] fetchedPoEmpMap = poEmpMapDao.findByDynamicWhere(" EMP_ID=? AND CURRENT=1", new Object[] { eachTimesheet.getUserId() });
							if (fetchedPoEmpMap.length > 0){
								int projId = poDetailsdao.findByPrimaryKey(fetchedPoEmpMap[0].getPoId()).getProjId();
								project = projectDao.findByPrimaryKey(projId);
								delayedBean.setProjectName(project.getName());
							} else{
								//"looks like" employee is associated with a project having no charge code
								projectMapping = projectMappingDao.findByDynamicWhere("USER_ID=? ORDER BY ID DESC", new Object[] { eachTimesheet.getUserId() })[0];
								project = projectDao.findByPrimaryKey(projectMapping.getProjectId());
								delayedBean.setProjectName(project.getName());
							}
							delayedBeanList.add(delayedBean);
						}
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String fromDate = sdf.format(timesheetdetailsDto.getStartDate());
						String endDate = sdf.format(new Date());
						if (timesheetdetailsDto.getEndDate() != null){
							endDate = sdf.format(timesheetdetailsDto.getEndDate());
						}
						attachements.setFileName(new GenerateXls().generateDelayedTsSubmission(delayedBeanList, path + File.separator + "TIMESHEET_" + fromDate + "_TO_" + endDate + ".xls", fromDate, endDate));//TIMESHEET_fromdate_todate
					} catch (Exception e){
						logger.error("unable to generate delayed timesheet list", e);
					}
					break;
				case TIMESHEETREPORT:
					try{
						List<String[]> report = generateTSRport(form);
						attachements.setFileName(new GenerateXls().generateTSRport(report, path + File.separator + "TIMESHEET_REPORT_" + form.getMonth() + "_" + new Date().getTime() + ".xls"));//TIMESHEET_fromdate_todate
					} catch (Exception e){
						logger.error("unable to generate timesheet report", e);
					}
					break;
			/*default:
				try{
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
					int fileID = Integer.parseInt(leaveMaster.getAttachment());
					Documents[] dacDocuments = documentsDao.findWhereIdEquals(fileID);
					path = portalData.getDirPath();
					attachements.setFileName(dacDocuments[0].getFilename());
					path = portalData.getfolder(path);
				} catch (DocumentsDaoException e){
					e.printStackTrace();
				}
				break;*/
			}
			path = path + File.separator + attachements.getFileName();
			attachements.setFilePath(path);
		} catch (Exception e){
			e.printStackTrace();
		}
		return attachements;
	}

	private List<String[]> generateTSRport(PortalForm form) throws Exception {
		Connection conn = ResourceManager.getConnection();
		UsersDao userDao = UsersDaoFactory.create(conn);
		Login login = null;
		/*if (form == null){
			form = new PortalForm();
			form.setActionFlag(1);
			form.setMonth("082013");
			login = new Login();
			login.setUserId(11);
		} else */login = (Login) form.getObject();
		TimeSheetDetailsDao timeSheetDetailsDao = TimeSheetDetailsDaoFactory.create(conn);
		UserTaskTimesheetMapDao mapDao = UserTaskTimesheetMapDaoFactory.create(conn);
		TimeSheetDetails timeSheetDetails[] = null;
		Calendar calendar = Calendar.getInstance(), calendar1 = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("MMyyyy").parse(form.getMonth()));
		calendar1.setTime(new SimpleDateFormat("MMyyyy").parse(form.getMonth()));
		int startday = calendar.get(Calendar.DAY_OF_WEEK);
		Date monthStart = calendar.getTime();
		if (startday > 2) calendar.add(Calendar.DAY_OF_MONTH, -(calendar.get(Calendar.DAY_OF_WEEK) - 2));
		else if (startday == 1){
			calendar.add(Calendar.DAY_OF_MONTH, -6);
			startday = 8;
		}
		calendar.add(Calendar.DAY_OF_MONTH, 6);
		Date enddate = calendar.getTime();
		calendar.setTime(monthStart);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date monthEnd = calendar.getTime();
		String query = null;
		if (ModelUtiility.hasModulePermission(login, 17)){
			if (form.getActionFlag().intValue() == 2) query = "SELECT U.* FROM USERS U WHERE U.STATUS NOT IN (1,2,3) AND EMP_ID!=0  ORDER BY EMP_ID";
			else if (form.getActionFlag().intValue() == 1) query = "SELECT U.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID WHERE U.STATUS NOT IN (1,2,3) AND EMP_ID!=0 AND HR_SPOC=" + login.getUserId() + " ORDER BY EMP_ID";
			else query = "SELECT U.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID WHERE U.STATUS NOT IN (1,2,3) AND EMP_ID!=0 AND REPORTING_MGR=" + login.getUserId() + " ORDER BY EMP_ID";
		} else{
			query = "SELECT U.* FROM USERS U JOIN PROFILE_INFO P ON P.ID=U.PROFILE_ID WHERE U.STATUS NOT IN (1,2,3) AND EMP_ID!=0 AND REPORTING_MGR=" + login.getUserId() + " ORDER BY EMP_ID";
		}
		Users[] users = userDao.findByDynamicSelect(query, null);
		List<String[]> list = new ArrayList<String[]>();
		Map<Integer, ProjectBean> projectMap = getProjectMap();
		for (Users user : users){
			
			timeSheetDetails = timeSheetDetailsDao.findByDynamicSelect("SELECT * FROM TIME_SHEET_DETAILS T WHERE (START_DATE BETWEEN ? AND ? OR  END_DATE BETWEEN ? AND ?) AND USER_ID=? ORDER BY USER_ID,START_DATE", new Object[] { monthStart, monthEnd, monthStart, monthEnd, user.getId() });
			
			
		//	timeSheetDetails = timeSheetDetailsDao.findByDynamicSelect("SELECT * FROM TIME_SHEET_DETAILS T WHERE (START_DATE BETWEEN ? AND ? OR  END_DATE BETWEEN ? AND ?) AND USER_ID=? AND STATUS='Approved'  ORDER BY USER_ID,START_DATE", new Object[] { monthStart, monthEnd, monthStart, monthEnd, user.getId() });
			int weekStartday = startday;
			calendar1.setTime(monthStart);
			List<Date> publicHolidays = getHolidays(user.getId(), monthStart, monthEnd);
			List<Date> leavesTaken = getLeavesTaken(user.getId(), monthStart, monthEnd, publicHolidays);
			Date weekend = new Date(enddate.getTime());
			boolean checkenddate = false;
			String[] rowStr = new String[38];
			rowStr[0] = user.getEmpId() + "";
			rowStr[1] = ModelUtiility.getInstance().getEmployeeName(user.getId());
			byte count = 7;
			float toatalAtc = 0.0f;
			int projectId = 0;
			do{
				TimeSheetDetails sheetDetails = null;
				if (timeSheetDetails != null && timeSheetDetails.length > 0){
					for (TimeSheetDetails details : timeSheetDetails)
						if (details.getEndDate().equals(weekend)){
							sheetDetails = details;
							break;
						}
				}
				UserTaskTimesheetMap taskTimesheetMap = null, timesheetMap[] = sheetDetails == null ? null : mapDao.findWhereTsIdEquals(sheetDetails.getId());
				if (timesheetMap != null && timesheetMap.length > 0 && timesheetMap[0] != null) taskTimesheetMap = timesheetMap[0];
				for (; weekStartday <= 8; weekStartday++){
					try{
						if (checkenddate){
							if (calendar.getTime().getTime() > monthEnd.getTime()) break;
							calendar.add(Calendar.DAY_OF_MONTH, 1);
						}
						String timeCommentString = "N.A";
						if (taskTimesheetMap != null){
							projectId = taskTimesheetMap.getProjectId();
							float time = 0;
							for (UserTaskTimesheetMap map : timesheetMap){
								switch (weekStartday) {
									case 2:
										timeCommentString = map.getMon();
										break;
									case 3:
										timeCommentString = map.getTue();
										break;
									case 4:
										timeCommentString = map.getWed();
										break;
									case 5:
										timeCommentString = map.getThu();
										break;
									case 6:
										timeCommentString = map.getFri();
										break;
									case 7:
										timeCommentString = map.getSat();
										break;
									case 1:
									case 8:
										timeCommentString = map.getSun();
										break;
									default:
										break;
								}
								time = (float) addTime(getWorkingTime(timeCommentString), time);
							}
							timeCommentString = time + "|";
						}
						if (timeCommentString.equalsIgnoreCase("N.A")){
							if (publicHolidays.contains(calendar1.getTime())) rowStr[count++] = "H";
							else if (leavesTaken.contains(calendar1.getTime())) rowStr[count++] = "L";
							else if (weekStartday == 1 || weekStartday == 8 || weekStartday == 7) rowStr[count++] = "W";
							else rowStr[count++] = "";
						} else{
							float time = getWorkingTime(timeCommentString);
							if (publicHolidays.contains(calendar1.getTime())) rowStr[count++] = "H" + (time == 0 ? "" : "-" + time);
							else if (leavesTaken.contains(calendar1.getTime())) rowStr[count++] = "L" + (time == 0 ? "" : "-" + time);
							else if (weekStartday == 1 || weekStartday == 8 || weekStartday == 7) rowStr[count++] = "W" + (time == 0 ? "" : "-" + time);
							else rowStr[count++] = time + "";
							toatalAtc = (float) addTime(toatalAtc, time);
						}
						calendar1.add(Calendar.DAY_OF_MONTH, 1);
					} catch (Exception e){}
				}
				if (checkenddate) break;
				weekStartday = 2;
				calendar.setTime(weekend);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				//weekstart = calendar.getTime();
				calendar.add(Calendar.DAY_OF_MONTH, 6);
				weekend = calendar.getTime();
				if (weekend.getTime() > monthEnd.getTime()){
					calendar.add(Calendar.DAY_OF_MONTH, -6);
					checkenddate = true;
				}
			} while (true);
			if (projectId == 0){
				List<Object> projectIdObj = JDBCUtiility.getInstance().getSingleColumn("SELECT PROJ_ID FROM ROLL_ON_PROJ_MAP R WHERE ROLL_ON_ID IN (SELECT ID FROM ROLL_ON R WHERE EMP_ID=? AND CURRENT=1)", new Object[] { user.getId() });
				if (projectIdObj.size() > 0 && projectIdObj.get(0) != null) projectId = Integer.parseInt(projectIdObj.get(0).toString());
				else projectId = 3;
			}
			ProjectBean projectBean = (ProjectBean) projectMap.get(projectId);
			if (projectBean != null){
				rowStr[2] = projectBean.getClientName() == null ? "Diksha" : projectBean.getClientName();
				rowStr[3] = projectBean.getProjectName() == null ? "Diksha" : projectBean.getProjectName();
				rowStr[4] = projectBean.getLocation() == null ? "Bangalore" : projectBean.getLocation();
			} else{
				rowStr[2] = "";
				rowStr[3] = "";
				rowStr[4] = "";
			}
			rowStr[5] = toatalAtc + "";
			rowStr[6] = leavesTaken.size() + "";
			//logger.info(Arrays.toString(rowStr));
			list.add(rowStr);
		}
		return list;
	}

	private Map<Integer, ProjectBean> getProjectMap() {
		try{
			Map<Integer, ProjectBean> map = new HashMap<Integer, ProjectBean>();
			for (ProjectBean bean : ProjectDaoFactory.create().getProjectDetails("", null)){
				map.put(bean.getId(), bean);
			}
			return map;
		} catch (Exception e){}
		return null;
	}

	private List<Date> getHolidays(int userId, Date startDate, Date endDate) {
		List<Date> holidays = new ArrayList<Date>();
		try{
			Holidays[] holiday = HolidaysDaoFactory.create().findByDynamicSelect("SELECT H.* FROM HOLIDAYS H JOIN CALENDAR C ON H.CAL_ID=C.ID  JOIN DIVISON D ON  C.REGION = D.REGION_ID JOIN LEVELS L ON D.ID=L.DIVISION_ID JOIN USERS U ON U.LEVEL_ID=L.ID  WHERE U.ID=? AND H.DATE_PICKER BETWEEN ? AND ? ", new Object[] { userId, startDate, endDate });
			if (holiday != null && holiday.length > 0) for (Holidays tmpHoliday : holiday){
				holidays.add(tmpHoliday.getDatePicker());
			}
		} catch (Exception e){
			logger.error("unable to get holidays list for user Id :" + userId + " errorMsg:" + e.getMessage());
		}
		return holidays;
	}

	private List<Date> getLeavesTaken(int userId, Date monthStart, Date monthEnd, List<Date> holidayList) {
		List<Date> leaves = new ArrayList<Date>();
		try{
			LeaveMaster[] leaveMasters = LeaveMasterDaoFactory.create().findByDynamicSelect("SELECT LM.* FROM LEAVE_MASTER LM WHERE ID IN (SELECT MAX(L.ID) FROM LEAVE_MASTER L JOIN EMP_SER_REQ_MAP ESRM ON L.ESR_MAP_ID=ESRM.ID WHERE ESRM.REQUESTOR_ID=? AND STATUS IN ('Accepted','Completed')  GROUP BY ESR_MAP_ID) AND (FROM_DATE BETWEEN ? AND ? OR TO_DATE BETWEEN ? AND ?)", new Object[] { userId, monthStart, monthEnd, monthStart, monthEnd });
			for (LeaveMaster master : leaveMasters){
				Calendar calfromDate = Calendar.getInstance();
				Calendar calmonthStart = Calendar.getInstance();
				Calendar caltoDate = Calendar.getInstance();
				Calendar calmonthEnd = Calendar.getInstance();
				calfromDate.setTime(master.getFromDate());
				calmonthStart.setTime(monthStart);
				caltoDate.setTime(master.getToDate());
				calmonthEnd.setTime(monthEnd);
				if (master.getFromDate().before(monthStart)){
					do{
						int day = calmonthStart.get(Calendar.DAY_OF_WEEK);
						if (day != 1 & day != 7 && !holidayList.contains(calmonthStart.getTime())){
							leaves.add(calmonthStart.getTime());
						}
						calmonthStart.add(Calendar.DAY_OF_MONTH, 1);
					} while (calmonthStart.getTime().getTime() <= master.getToDate().getTime());
				} else if (master.getToDate().after(monthEnd)){
					do{
						int day = calfromDate.get(Calendar.DAY_OF_WEEK);
						if (day != 1 & day != 7 && !holidayList.contains(calfromDate.getTime())){
							leaves.add(calfromDate.getTime());
						}
						calfromDate.add(Calendar.DAY_OF_MONTH, 1);
					} while (calfromDate.getTime().getTime() <= calmonthEnd.getTime().getTime());
				} else{
					do{
						int day = calfromDate.get(Calendar.DAY_OF_WEEK);
						if (day != 1 & day != 7 && !holidayList.contains(calfromDate.getTime())){
							leaves.add(calfromDate.getTime());
						}
						calfromDate.add(Calendar.DAY_OF_MONTH, 1);
					} while (calfromDate.getTime().getTime() <= master.getToDate().getTime());
				}
			}
		} catch (Exception e){
			logger.error("unable to read leaves..." + e);
		}
		return leaves;
	}

	private float getWorkingTime(String timeStr) {
		try{
			return Float.parseFloat(timeStr.split("\\|")[0]);
		} catch (Exception e){
			logger.error("Unable to parse timeStr---" + e);
			return 0;
		}
	}

	public static void main(String[] args) throws Exception {
		//new GenerateXls().generateTSRport(new TimeSheetModel().generateTSRport(null), "Data" + File.separator + "TIMESHEET_REPORT_" + new Date().getTime() + ".xls");
		//logger.info(new TimeSheetModel().getLeavesTaken(36, PortalUtility.fromStringToDate("01/08/2013"), PortalUtility.fromStringToDate("31/08/2013"), new TimeSheetModel().getHolidays(37, PortalUtility.fromStringToDate("01/08/2013"), PortalUtility.fromStringToDate("31/08/2013"))));
		//Calendar c = Calendar.getInstance();
		///logger.info("jhjjjj-----" + PortalUtility.returnMonth(c.get(Calendar.MONTH) + 1));
		System.out.println("0.0".split(".")[1]);
	}

	@Override
	public Integer[] upload(List<FileItem> fileItems, String docType, int id, HttpServletRequest request, String description) {
		if (!ModelUtiility.hasModulePermission(Login.getLogin(request), 17)){
			request.setAttribute("TIMESHEET_MESSAGE", "Access Denied !!!");
			return new Integer[] { 0 };
		}
		List<Integer> notUploaded = new ArrayList<Integer>();
		if (fileItems != null && !fileItems.isEmpty()){
			FileItem file = (FileItem) fileItems.get(0);
			InputStream stream = null;
			try{
				stream = file != null ? file.getInputStream() : null;
			} catch (IOException e1){
				e1.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();
			try{
				logger.info("uploading timesheet details with " + file.getName());
				logger.info(file.getName().length() + " " + file.getName().indexOf("."));
				String filename = file.getName().substring(0, file.getName().indexOf("."));
				Integer.parseInt(filename);
				calendar.setTime(new SimpleDateFormat("MMyyyy").parse(file.getName().substring(0, 6)));
			} catch (Exception e1){
				logger.error(e1.getMessage());
				request.setAttribute("TIMESHEET_MESSAGE", "Please rename the file name like MMYYYY.xls\n(Ex: 012013.xls for Jan 2013) and try uploading again.");
				return new Integer[] { 0 };
			}
			int startday = calendar.get(Calendar.DAY_OF_WEEK);
			Date monthStart = calendar.getTime();
			if (startday > 2) calendar.add(Calendar.DAY_OF_MONTH, -(calendar.get(Calendar.DAY_OF_WEEK) - 2));
			else if (startday == 1){
				calendar.add(Calendar.DAY_OF_MONTH, -6);
				startday = 8;
			}
			Date startdate = calendar.getTime();
			calendar.add(Calendar.DAY_OF_MONTH, 6);
			Date enddate = calendar.getTime();
			calendar.setTime(monthStart);
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Date monthEnd = calendar.getTime();
			if (stream != null) try{
				Vector<Vector<Object>> list = POIParser.parseXls(stream, 0);
				stream.close();
				if (list != null && !list.isEmpty()){
					Connection conn = ResourceManager.getConnection();
					UsersDao userDao = UsersDaoFactory.create(conn);
					TimeSheetDetailsDao timeSheetDetailsDao = TimeSheetDetailsDaoFactory.create(conn);
					UserTaskTimesheetMapDao mapDao = UserTaskTimesheetMapDaoFactory.create(conn);
					int empId = -1;
					for (Vector<Object> row : list){
						try{
							try{
								empId = (int) Float.parseFloat(row.get(0) + "");
							} catch (Exception e){
								// skip if emp id is not valid data type.
								continue;
							}
							Users[] user = userDao.findWhereEmpIdEquals(empId);
							//skip if employee id not present in database.
							if (user == null || user.length != 1 || user[0] == null){
								notUploaded.add(empId);
								continue;
							}
							JDBCUtiility.getInstance().update("INSERT INTO CONSULTANTS VALUES ( ? )", new Object[] { user[0].getId() });
							int project = 3;
							try{
								project = ProjectDaoFactory.create(conn).findWhereNameEquals(row.get(3).toString())[0].getId();
							} catch (Exception e){
								logger.error("unable to get the project id for timesheet of " + empId);
							}
							int column = 6, weekStartday = startday;
							Date weekstart = new Date(startdate.getTime());
							Date weekend = new Date(enddate.getTime());
							boolean checkenddate = false;
							do{
								TimeSheetDetails sheetDetails, timeSheetDetails[] = timeSheetDetailsDao.findWhereStartDateEquals(weekstart, user[0].getId());
								if (timeSheetDetails != null && timeSheetDetails.length > 0 && timeSheetDetails[0] != null) sheetDetails = timeSheetDetails[0];
								else{
									sheetDetails = new TimeSheetDetails();
									sheetDetails.setStartDate(weekstart);
									sheetDetails.setEndDate(weekend);
									sheetDetails.setStatus(Status.APPROVED);
									sheetDetails.setSubmissionDate(new Date());
									sheetDetails.setUserId(user[0].getId());
									timeSheetDetailsDao.insert(sheetDetails);
								}
								UserTaskTimesheetMap taskTimesheetMap, timesheetMap[] = mapDao.findWhereTsIdEquals(sheetDetails.getId());
								if (timesheetMap != null && timesheetMap.length > 0 && timesheetMap[0] != null) taskTimesheetMap = timesheetMap[0];
								else{
									taskTimesheetMap = new UserTaskTimesheetMap();
									taskTimesheetMap.setProjectId(project);
									taskTimesheetMap.setTsId(sheetDetails.getId());
								}
								if (row.get(4) != null) taskTimesheetMap.setTaskName(row.get(4) + "");
								double toatalAtc = 0.0;
								for (; weekStartday <= 8; weekStartday++){
									try{
										if (checkenddate){
											if (calendar.getTime().getTime() > monthEnd.getTime()) break;
											calendar.add(Calendar.DAY_OF_MONTH, 1);
										}
										Object timeObj = row.get(column++);
										Object desc = row.get(column++);
										float time = Float.parseFloat(timeObj + "");
										toatalAtc = addTime(toatalAtc, time);
										if (time <= 0 && desc == null) continue;
										String detail = time + "|" + (desc == null ? "" : desc);
										switch (weekStartday) {
											case 2:
												taskTimesheetMap.setMon(detail);
												break;
											case 3:
												taskTimesheetMap.setTue(detail);
												break;
											case 4:
												taskTimesheetMap.setWed(detail);
												break;
											case 5:
												taskTimesheetMap.setThu(detail);
												break;
											case 6:
												taskTimesheetMap.setFri(detail);
												break;
											case 7:
												taskTimesheetMap.setSat(detail);
												break;
											case 1:
											case 8:
												taskTimesheetMap.setSun(detail);
												break;
											default:
												break;
										}
									} catch (Exception e){}
								}
								taskTimesheetMap.setTotalEtc((float) toatalAtc);
								if (taskTimesheetMap.getId() > 0) mapDao.update(taskTimesheetMap.createPk(), taskTimesheetMap);
								else mapDao.insert(taskTimesheetMap);
								logger.info("timesheet updated for " + empId + " period: " + weekstart + " and " + weekend);
								if (checkenddate) break;
								weekStartday = 2;
								calendar.setTime(weekend);
								calendar.add(Calendar.DAY_OF_MONTH, 1);
								weekstart = calendar.getTime();
								calendar.add(Calendar.DAY_OF_MONTH, 6);
								weekend = calendar.getTime();
								if (weekend.getTime() > monthEnd.getTime()){
									calendar.add(Calendar.DAY_OF_MONTH, -6);
									checkenddate = true;
								}
							} while (true);
						} catch (Exception e){
							if (!notUploaded.contains(empId)) notUploaded.add(empId);
						}
					}
					if (conn != null) conn.close();
				}
			} catch (Exception e){
				request.setAttribute("TIMESHEET_MESSAGE", "unable to read the file.\n please try again.");
			}
		}
		request.setAttribute("TIMESHEET_MESSAGE", "Timesheet updated successfully." + (notUploaded.size() > 0 ? "\nBut following employees data is unable to update.\nEMP ID(S): " + ModelUtiility.getCommaSeparetedValues(notUploaded) : ""));
		return new Integer[] { 0 };
	}
	
	
	
	private void sendAndroidLeaveNotification(String message,List<String> mailIds)	{
		LoginModel rmModel=new LoginModel();
		List<Integer> ids=new ArrayList<Integer>();
		ProfileInfo[] pf=null;
		ProfileInfoDao profileInfoDao=ProfileInfoDaoFactory.create();
		Users[] user=null;
		UsersDao usersDao=UsersDaoFactory.create();
		if(mailIds!=null){
			for(String mId: mailIds ){
			try {
				pf=profileInfoDao.findWhereOfficalEmailIdEquals(mId);
				if(pf!=null && pf.length>0){
						user=usersDao.findWhereProfileIdEquals(pf[0].getId());
						ids.add(user[0].getId());
				}
				
			} catch (ProfileInfoDaoException e) {
				e.printStackTrace();
			} catch (UsersDaoException e) {
				e.printStackTrace();}
			}
		}
		rmModel.sendGcmNotification(message,ids,"TIMESHEET");
	}
}