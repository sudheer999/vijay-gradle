package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.dikshatech.beans.HandlerAction;
import com.dikshatech.beans.IssueResolutionBean;
import com.dikshatech.beans.UserBean;
import com.dikshatech.beans.UserLogin;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.IssueCommentsDao;
import com.dikshatech.portal.dao.IssueCommentsMapDao;
import com.dikshatech.portal.dao.IssueResolutionDetailsDao;
import com.dikshatech.portal.dao.ItRequestCategoryDao;
import com.dikshatech.portal.dao.ItRequestDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.StatusDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.DocumentsPk;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Handlers;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.InboxPk;
import com.dikshatech.portal.dto.IssueComments;
import com.dikshatech.portal.dto.IssueCommentsMap;
import com.dikshatech.portal.dto.IssueCommentsPk;
import com.dikshatech.portal.dto.IssueResolutionDetails;
import com.dikshatech.portal.dto.IssueResolutionDetailsPk;
import com.dikshatech.portal.dto.ItRequest;
import com.dikshatech.portal.dto.ItRequestPk;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.DocumentsDaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.IssueCommentsDaoException;
import com.dikshatech.portal.exceptions.IssueCommentsMapDaoException;
import com.dikshatech.portal.exceptions.IssueResolutionDetailsDaoException;
import com.dikshatech.portal.exceptions.ItRequestDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.IssueCommentsDaoFactory;
import com.dikshatech.portal.factory.IssueCommentsMapDaoFactory;
import com.dikshatech.portal.factory.IssueResolutionDetailsDaoFactory;
import com.dikshatech.portal.factory.ItRequestCategoryDaoFactory;
import com.dikshatech.portal.factory.ItRequestDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.StatusDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class ItModel extends ActionMethods {

	private static Logger	logger	= LoggerUtil.getLogger(ItModel.class);

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		ItRequest itReq = (ItRequest) form;
		ItRequestDao itReqDao = ItRequestDaoFactory.create();
		try{
			ItRequest itRequest = itReqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { itReq.getEsrMapId() })[0];
			itRequest.setIsDeleted(1);
			itReqDao.update(new ItRequestPk(itRequest.getId()), itRequest);
		} catch (ItRequestDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	//remove it later--runs only once-to repair data
	public void ItModelDataRepair() throws IssueResolutionDetailsDaoException, ItRequestDaoException {
		IssueResolutionDetailsDao ird = IssueResolutionDetailsDaoFactory.create();
		ItRequestDao itDao = ItRequestDaoFactory.create();
		ItRequest itReqArr[] = itDao.findByDynamicWhere("REQUESTER_ID=RECEIVER_ID AND STATUS NOT IN('Request Saved','Revoked')", new Object[] {});
		if (itReqArr.length == 0){
			return;
		} else{
			IssueResolutionDetails irdtls[] = ird.findAll();
			if (irdtls.length == 0){
				for (ItRequest itrequest : itReqArr){
					IssueResolutionDetails irdtl = new IssueResolutionDetails();
					irdtl.setEsrMapId(itrequest.getEsrMapId());
					irdtl.setFirstStatus("Request Raised");
					if (itrequest.getStatus().equalsIgnoreCase(Status.RESOLVED) || itrequest.getStatus().equalsIgnoreCase(Status.CLOSED)){
						irdtl.setLastStatus(itrequest.getStatus());
						ItRequest it = itDao.findByDynamicWhere("ESR_MAP_ID=? AND ASSIGN_TO>0", new Object[] { itrequest.getEsrMapId() })[0];
						irdtl.setResolvedBy(it.getAssignTo());
					}
					irdtl.setRequesterComments(itrequest.getComments());
					irdtl.setRequestedOn(itrequest.getCreateDate());
					irdtl.setResolvedOn(itrequest.getLastModifiedDate());
					irdtl.setClosedOn(itrequest.getLastModifiedDate());
					ird.insert(irdtl);
				}
			} else{
				boolean found = false;
				for (ItRequest itrequest : itReqArr){
					for (IssueResolutionDetails issueResolutionDetails : irdtls){
						if (itrequest.getEsrMapId() == issueResolutionDetails.getEsrMapId()){
							found = true;
						}
					}
					if (found == false){
						IssueResolutionDetails irdtl = new IssueResolutionDetails();
						irdtl.setEsrMapId(itrequest.getEsrMapId());
						irdtl.setFirstStatus("Request Raised");
						if (itrequest.getStatus().equalsIgnoreCase(Status.RESOLVED) || itrequest.getStatus().equalsIgnoreCase(Status.CLOSED)){
							irdtl.setLastStatus(itrequest.getStatus());
							ItRequest it = itDao.findByDynamicWhere("ESR_MAP_ID=? AND ASSIGN_TO>0", new Object[] { itrequest.getEsrMapId() })[0];
							irdtl.setResolvedBy(it.getAssignTo());
						}
						irdtl.setRequesterComments(itrequest.getComments());
						irdtl.setRequestedOn(itrequest.getCreateDate());
						irdtl.setResolvedOn(itrequest.getLastModifiedDate());
						irdtl.setClosedOn(itrequest.getLastModifiedDate());
						ird.insert(irdtl);
					}
				}
			}
		}
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		ItRequestDao itReqDao = ItRequestDaoFactory.create();
		ProfileInfoDao profileDao = ProfileInfoDaoFactory.create();
		EmpSerReqMapDao serReqMapDao = EmpSerReqMapDaoFactory.create();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		DivisonDao divisionDao = DivisonDaoFactory.create();
		DocumentsDao documentDao = DocumentsDaoFactory.create();
		ItRequestCategoryDao catDao = ItRequestCategoryDaoFactory.create();
		//ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		StatusDao sDao = StatusDaoFactory.create();
		IssueCommentsDao issueCommentsDao = IssueCommentsDaoFactory.create();
		IssueResolutionDetailsDao issueResolutionDetailsDao = IssueResolutionDetailsDaoFactory.create();
		// evaluator = new ProcessEvaluator();
		ModelUtiility modelUtiility = ModelUtiility.getInstance();
		try{
			Login loginDto = Login.getLogin(request);
			DropDown dropDown = new DropDown();
			switch (ReceiveTypes.getValue(form.getrType())) {
				case RECEIVECATEGORY:
					ItRequestCategoryDao itcatDao = ItRequestCategoryDaoFactory.create();
					dropDown.setDropDown(itcatDao.findAll());
					request.setAttribute("actionForm", dropDown);
					break;
				case RECEIVE:{
					ItRequest itrequest = (ItRequest) form;
					ItRequest reqDto = itReqDao.findByPrimaryKey(itrequest.getId());
					EmpSerReqMap empSerReqMap = serReqMapDao.findByPrimaryKey(reqDto.getEsrMapId());
					Users requestedUser = usersDao.findByPrimaryKey(reqDto.getRequesterId());
					ProfileInfo requsterProfile = profileDao.findByPrimaryKey(requestedUser.getProfileId());
					reqDto.setRequestedBy(requsterProfile.getFirstName() + " " + requsterProfile.getLastName());
					reqDto.setEmployeeId(requestedUser.getEmpId());
					Levels level = levelsDao.findByDynamicSelect("SELECT * FROM LEVELS L JOIN USERS U ON U.LEVEL_ID=L.ID WHERE U.ID=?", new Object[] { new Integer(reqDto.getRequesterId()) })[0];
					reqDto.setRequsterDesign(level.getDesignation());
					reqDto.setRequsterDept(divisionDao.findByPrimaryKey(level.getDivisionId()).getName());
					reqDto.setEsrReqID(empSerReqMap.getReqId());
					//reqDto.setRequestedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(reqDto.getCreateDate()));
					reqDto.setFileName(reqDto.getAttachment() > 0 ? documentDao.findByPrimaryKey(reqDto.getAttachment()).getFilename() : null);
					reqDto.setCategoryId(catDao.findWhereCategoryEquals(reqDto.getCategory())[0].getId());
					List<String> handlerLst = modelUtiility.getSiblingUsersList(reqDto.getEsrMapId());
					if (handlerLst != null && handlerLst.size() > 0){
						UserBean[] userBeans = UsersDaoFactory.create().findAllUserNames(ModelUtiility.getCommaSeparetedValues(handlerLst), null);
						Handlers handlers[] = new Handlers[userBeans.length];
						int i = 0;
						for (UserBean userBean : userBeans){
							Handlers ha = new Handlers();
							ha.setId(Integer.parseInt(userBean.getId()));
							ha.setName(userBean.getFirstName() + " " + userBean.getLastName());
							handlers[i] = ha;
							i++;
						}
						reqDto.setHandlers(handlers);
						//to get dynamic status list
						String SQL2 = "SELECT * FROM IT_REQUEST  where ESR_MAP_ID=? AND RECEIVER_ID=?";
						ItRequest itRequest = itReqDao.findByDynamicSelect(SQL2, new Object[] { reqDto.getEsrMapId(), loginDto.getUserId() })[0];
						ArrayList<com.dikshatech.portal.dto.Status> statusLst = new ArrayList<com.dikshatech.portal.dto.Status>();
						boolean assign = false, inProgress = false, resolved = false;
						if (handlerLst.size() == 1){
							if (itRequest.getStatus().equalsIgnoreCase(Status.REQUESTRAISED) || itRequest.getStatus().equalsIgnoreCase(Status.REOPENED)){
								assign = true;
								inProgress = true;
								resolved = true;
							} else if (itRequest.getStatus().contains("Docked By")){
								inProgress = true;
								resolved = true;
							} else if (itRequest.getStatus().equalsIgnoreCase(Status.INPROGRESS)){
								resolved = true;
							}
						} else{
							if (itRequest.getStatus().equalsIgnoreCase(Status.REQUESTRAISED) || itRequest.getStatus().equalsIgnoreCase(Status.REOPENED)){
								assign = true;
							} else if (itRequest.getStatus().contains("Docked By")){
								assign = true;
								inProgress = true;
								resolved = true;
							} else if (itRequest.getStatus().equalsIgnoreCase(Status.INPROGRESS)){
								assign = true;
								resolved = true;
							}
						}
						if (assign == true){
							statusLst.add(sDao.findByDynamicWhere("STATUS='" + Status.ASSIGNED + "'", new Object[] {})[0]);
						}
						if (inProgress == true){
							statusLst.add(sDao.findByDynamicWhere("STATUS='" + Status.INPROGRESS + "'", new Object[] {})[0]);
						}
						if (resolved == true){
							statusLst.add(sDao.findByDynamicWhere("STATUS='" + Status.RESOLVED + "'", new Object[] {})[0]);
						}
						com.dikshatech.portal.dto.Status[] statusArr = new com.dikshatech.portal.dto.Status[statusLst.size()];
						statusLst.toArray(statusArr);
						reqDto.setStatuslist(statusArr);
					}
					//to get requester comments
					String query1 = "SELECT CONCAT(date_format(REQUESTED_ON,'%d-%m-%Y %h:%i'),' : ',REQUESTER_COMMENTS) AS REQUESTER_COMMENTS FROM ISSUE_RESOLUTION_DETAILS WHERE ESR_MAP_ID=" + reqDto.getEsrMapId() + " AND REQUESTER_COMMENTS IS NOT NULL";
					ArrayList<String> requesterCommentsLst = issueResolutionDetailsDao.getRequesterComments(query1);
					if (requesterCommentsLst == null || requesterCommentsLst.size() < 1){
						requesterCommentsLst = new ArrayList<String>();
						requesterCommentsLst.add(reqDto.getComments());
					}
					if (!requesterCommentsLst.isEmpty()){
						String requesterComments[] = new String[requesterCommentsLst.size()];
						requesterCommentsLst.toArray(requesterComments);
						reqDto.setRequesterComments(requesterComments);
					}
					// to get handler comments				 
					String query2 = "SELECT CONCAT((SELECT CONCAT(FIRST_NAME,' ',LAST_NAME) FROM PROFILE_INFO WHERE ID IN (SELECT PROFILE_ID FROM USERS WHERE ID=ICM.USER_ID)),' : ',IC.COMMENT) AS ISSUE_COMMENTS " + "from ISSUE_COMMENTS IC, ISSUE_COMMENTS_MAP ICM where IC.ID = ICM.COMMENT_ID AND ICM.ESR_MAP_ID=" + reqDto.getEsrMapId();
					ArrayList<String> resultList = issueCommentsDao.getIssueComments(query2);
					String handlerComments[] = new String[resultList.size()];
					resultList.toArray(handlerComments);
					reqDto.setHandlercomments(handlerComments);
					//to get previous resolution details of the request
					String query3 = "SELECT * from ISSUE_RESOLUTION_DETAILS where ESR_MAP_ID=? AND REQUESTED_ON NOT IN(select MAX(REQUESTED_ON) from ISSUE_RESOLUTION_DETAILS where ESR_MAP_ID=?) ";
					IssueResolutionDetails resolutionDetailsArr[] = null;
					resolutionDetailsArr = issueResolutionDetailsDao.findByDynamicSelect(query3, new Object[] { reqDto.getEsrMapId(), reqDto.getEsrMapId() });
					if (resolutionDetailsArr != null && resolutionDetailsArr.length > 0){
						IssueResolutionBean issueResolutionBean[] = new IssueResolutionBean[resolutionDetailsArr.length];
						IssueResolutionBean isRlnBn = null;
						int i = 0;
						for (IssueResolutionDetails resDtls : resolutionDetailsArr){
							isRlnBn = new IssueResolutionBean();
							isRlnBn.setStatus1(resDtls.getFirstStatus());
							isRlnBn.setRequestedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resDtls.getRequestedOn()));
							isRlnBn.setStatus2(Status.RESOLVED);
							isRlnBn.setResolvedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resDtls.getResolvedOn()));
							if(resDtls.getResolvedBy()>0){
								String resolvedBy = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(resDtls.getResolvedBy()).getProfileId()).getFirstName();
								isRlnBn.setResolvedBy(resolvedBy);
							}
							if (resDtls.getLastStatus()!=null && resDtls.getLastStatus().equalsIgnoreCase(Status.CLOSED)){
								isRlnBn.setStatus3(Status.CLOSED);
								isRlnBn.setClosedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resDtls.getClosedOn()));
							}
							issueResolutionBean[i++] = isRlnBn;
						}
						reqDto.setPreviousResolutionDetails(issueResolutionBean);
					}
					//to get present details of request
					String query4 = "SELECT * from ISSUE_RESOLUTION_DETAILS where ESR_MAP_ID=? AND REQUESTED_ON IN(select MAX(REQUESTED_ON) from ISSUE_RESOLUTION_DETAILS where ESR_MAP_ID=?) ";
					resolutionDetailsArr = issueResolutionDetailsDao.findByDynamicSelect(query4, new Object[] { reqDto.getEsrMapId(), reqDto.getEsrMapId() });
					if (resolutionDetailsArr != null && resolutionDetailsArr.length > 0){
						reqDto.setRequestedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resolutionDetailsArr[0].getRequestedOn()));
						if (resolutionDetailsArr[0].getLastStatus() != null){
							if (resolutionDetailsArr[0].getLastStatus().equalsIgnoreCase(Status.RESOLVED)){
								reqDto.setResolvedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resolutionDetailsArr[0].getResolvedOn()));
								ItRequest[] fetchedItRequests = itReqDao.findByDynamicWhere(" ESR_MAP_ID=? ORDER BY ID DESC ", new Object[] { reqDto.getEsrMapId() });
								if (fetchedItRequests != null && fetchedItRequests.length > 0){
									if(fetchedItRequests[0].getAssignTo()>0){
									ProfileInfo handlerProfile = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(fetchedItRequests[0].getAssignTo()).getProfileId());
									reqDto.setTempStatus(handlerProfile.getFirstName() + " " + handlerProfile.getLastName());
									}
								}
							} else if (resolutionDetailsArr[0].getLastStatus()!=null && resolutionDetailsArr[0].getLastStatus().equalsIgnoreCase(Status.CLOSED)){
								reqDto.setResolvedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resolutionDetailsArr[0].getResolvedOn()));
								reqDto.setClosedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(resolutionDetailsArr[0].getClosedOn()));
							}
						}
					}
					//other
					//reqDto.setTempStatus(""); commented... was resetting the handler's name for RESOLVED status
					if (!reqDto.getStatus().equalsIgnoreCase(Status.RESOLVED) && !reqDto.getStatus().equalsIgnoreCase(Status.CLOSED) && !reqDto.getStatus().equalsIgnoreCase(Status.REVOKED) && !reqDto.getStatus().equalsIgnoreCase(Status.REQUESTSAVED)){
						if (reqDto.getAssignTo() == loginDto.getUserId()){
							reqDto.setAssigned(true);
						} else if (!reqDto.getStatus().equalsIgnoreCase(Status.REQUESTRAISED) && !reqDto.getStatus().equalsIgnoreCase(Status.REOPENED)){
							ItRequest itR = itReqDao.findByDynamicSelect("SELECT * FROM IT_REQUEST WHERE ASSIGN_TO>0 AND ESR_MAP_ID=?", new Object[] { reqDto.getEsrMapId() })[0];
							ProfileInfo handlerProfile = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(itR.getAssignTo()).getProfileId());
							//reqDto.setTempStatus("Docked By " + handlerProfile.getFirstName()); 
							reqDto.setTempStatus(handlerProfile.getFirstName() + " " + handlerProfile.getLastName());//bugFix by vijay jayaram
						}
					}
					request.setAttribute("actionForm", reqDto);
				}
					break;
				case RECEIVEALL:{
					ItRequest itReqArr[] = itReqDao.findByDynamicSelect("SELECT * FROM IT_REQUEST WHERE RECEIVER_ID=? AND RECEIVER_ID=REQUESTER_ID AND IS_DELETED=0", new Object[] { loginDto.getUserId() });
					ItRequest itReqArrNew[] = new ItRequest[itReqArr.length];
					int j = 0;
					if (itReqArr != null && itReqArr.length > 0){
						EmpSerReqMap empSerReqMap = null;
						for (ItRequest itReq : itReqArr){
							empSerReqMap = serReqMapDao.findByPrimaryKey(itReq.getEsrMapId());
							itReq.setEsrReqID(empSerReqMap.getReqId());
							itReq.setRequestedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(itReq.getCreateDate()));
							if (itReq.getStatus().equalsIgnoreCase(Status.REQUESTSAVED)){
								itReq.setRequestedOn(null);
								itReq.setCreateDate(null);
							}
							itReqArrNew[j] = itReq;
							j++;
						}
					}
					//dropDown.setDetail(handler);
					dropDown.setDropDown(itReqArrNew);
					request.setAttribute("actionForm", dropDown);
				}
					break;
				case RECEIVENEW:{
					ItRequest itrequest = (ItRequest) form;
					DropDown dropDown1 = new DropDown();
					StringBuffer query = new StringBuffer("SELECT * FROM IT_REQUEST  WHERE  RECEIVER_ID=?");
					if (itrequest.getMonth() != null || itrequest.getSearchyear() != null || itrequest.getSearchName() != null){
						if (itrequest.getMonth() != null && itrequest.getToMonth() != null) query.append(" AND (MONTH(CREATE_DATE) BETWEEN " + itrequest.getMonth() + " AND " + itrequest.getToMonth() + ") ");
						else if (itrequest.getMonth() != null) query.append(" AND MONTH(CREATE_DATE)=" + itrequest.getMonth() + " ");
						if (itrequest.getSearchyear() != null) query.append(" AND YEAR(CREATE_DATE)=" + itrequest.getSearchyear() + " ");
						if (itrequest.getSearchName() != null) query.append(" AND REQUESTER_ID IN (SELECT ID FROM USERS U WHERE U.STATUS NOT IN ( 1, 2, 3 ) AND PROFILE_ID IN (SELECT ID FROM PROFILE_INFO WHERE (FIRST_NAME IS NOT NULL AND FIRST_NAME LIKE '%" + itrequest.getSearchName() + "%') OR (LAST_NAME IS NOT NULL AND LAST_NAME LIKE '%" + itrequest.getSearchName() + "%'))) ");
					} else{
						query.append(" AND TIMESTAMPDIFF(DAY,CREATE_DATE,NOW()) <= 30" + " ");
					}
					query.append(" ORDER BY CREATE_DATE DESC");
					ItRequest[] itReqreqArr = itReqDao.findByDynamicSelect(query.toString(), new Object[] { new Integer(loginDto.getUserId()) });
					ItRequest[] itReqArr = new ItRequest[itReqreqArr.length];
					HandlerAction handler1 = new HandlerAction();
					int k = 0;
					int count = 0;
					for (ItRequest itReq : itReqreqArr){
						EmpSerReqMap serReqMapDto = serReqMapDao.findByPrimaryKey(itReq.getEsrMapId());
						if (itReq.getReceiverId() != itReq.getRequesterId()){
							if (itReq.getAssignTo() == itReq.getReceiverId()){
								if (itReq.getStatus().equalsIgnoreCase(Status.REQUESTRAISED) || itReq.getStatus().contains("Docked By") || itReq.getStatus().equalsIgnoreCase(Status.INPROGRESS) || itReq.getStatus().contains(Status.REOPENED)){
									itReq.setAssigned(true);
								} else{
									itReq.setAssigned(false);
								}
							} else{
								itReq.setAssigned(false);
							}
							handler1.setAssign(1);
							itReq.setRequestedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(itReq.getCreateDate()));
							ProfileInfo requsterProfile1 = profileDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(itReq.getRequesterId()) })[0];
							itReq.setRequestedBy(requsterProfile1.getFirstName());
							itReq.setEsrReqId(serReqMapDto.getReqId());
							itReq.setCategoryId(catDao.findWhereCategoryEquals(itReq.getCategory())[0].getId());
							if (itReq.getStatus().equals(Status.RESOLVED) || itReq.getStatus().equals(Status.CLOSED)){
								itReq.setDateOfCompletion(PortalUtility.getdd_MM_yyyy_hh_mm_a(itReq.getLastModifiedDate()));
							} else if (itReq.getAssignTo() == itReq.getReceiverId() && !itReq.getStatus().equals(Status.REVOKED)){
								count++;
							}
							itReqArr[k] = itReq;
							k++;
						}
					}
					dropDown1.setDetail(handler1);
					dropDown1.setCount(count);
					dropDown1.setDropDown(itReqArr);
					request.setAttribute("actionForm", dropDown1);
				}
					break;
			}//end of switch
		} catch (Exception e){
			e.printStackTrace();
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
		}
		return result;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		ItRequest itrequest = (ItRequest) form;
		ItRequestDao itReqDao = ItRequestDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		MailGenerator mailGenarator = new MailGenerator();
		EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		RegionsDao regionDao = RegionsDaoFactory.create();
		DivisonDao divisionDao = DivisonDaoFactory.create();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		IssueResolutionDetailsDao issueResolutionDetailsDao = IssueResolutionDetailsDaoFactory.create();
		ProcessEvaluator evaluator = new ProcessEvaluator();
		EmpSerReqMapPk reqpk = new EmpSerReqMapPk();
		Users requestedUser = new Users();
		try{
			Login loginDto = Login.getLogin(request);
			UserLogin userLogin = loginDto.getUserLogin();
			requestedUser = usersDao.findByPrimaryKey(loginDto.getUserId());
			ProfileInfo requesterProfile = profileInfoDao.findByPrimaryKey(requestedUser.getProfileId());
			//ProfileInfo hrSpocProfile=profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(requesterProfile.getHrSpoc()).getProfileId());
			//ProfileInfo rmProfile=profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(requesterProfile.getReportingMgr()).getProfileId());
			ProcessChain processChain[] = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=27 WHERE UR.USER_ID=?", new Object[] { new Integer(loginDto.getUserId()) });
			Levels empLevel = levelsDao.findByPrimaryKey(requestedUser.getLevelId());
			Divison requesterDivision = divisionDao.findByPrimaryKey(empLevel.getDivisionId());
			String empDesignation = empLevel.getDesignation();
			String empDivision = requesterDivision.getName();
			String empDepartment = null;
			if (requesterDivision.getParentId() == 0){
				empDepartment = requesterDivision.getName();
			} else{
				empDepartment = divisionDao.findByPrimaryKey(requesterDivision.getParentId()).getName();
			}
			ArrayList<Users> handlerList = null;
			ArrayList<Users> notifierList = null;
			String handlers = null, notifiers = null, notifierIds = null, handlerIds = null;
			Object[] sqlParams = null;
			if (processChain[0] != null){
				handlers = processChain[0].getHandler();
				if (handlers != null && !handlers.equals("")){
					handlerList = getUserLstByLevelIds(handlers, requesterProfile);//11 July
					if (handlerList.contains(requestedUser)){
						handlerList.remove(requestedUser);
					}
					if (handlerList != null){
						handlerIds = getUserIdsFromUserList(handlerList);
					}
				}
				notifiers = processChain[0].getNotification();
				if (notifiers != null && !notifiers.equals("")){
					notifierList = getUserLstByLevelIds(notifiers, requesterProfile);
					if (notifierList != null){
						notifierIds = getUserIdsFromUserList(notifierList);
					}
				}
			}
			if (handlerList == null || handlerList.size() < 1){
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.nohandler.toProcessRequest"));
				return result;
			}
			switch (ActionMethods.SaveTypes.getValue(form.getsType())) {
				case SAVE:
					try{
						Date date = new Date();
						if (requestedUser != null){
							int reg_id = userLogin.getRegionId();
							// Get Region Abbreviation from Region Table using region id
							String reg_abb = regionDao.findByPrimaryKey(reg_id).getRefAbbreviation();
							// save Data in EMP_SER_REQ_MAP TABLE
							int uniqueID = 1;
							EmpSerReqMap empMap[] = empSerReqDao.findByDynamicSelect("SELECT * FROM EMP_SER_REQ_MAP WHERE ID=(SELECT MAX(ID) FROM EMP_SER_REQ_MAP WHERE REQ_TYPE_ID=6)", null);
							if (empMap.length > 0){
								String s = empMap[0].getReqId().split("-")[2];
								uniqueID = Integer.parseInt(s) + 1;
							}
							EmpSerReqMap empReqMapDto = new EmpSerReqMap();
							empReqMapDto.setSubDate(date);
							empReqMapDto.setReqId(reg_abb + "-IS-" + uniqueID);
							empReqMapDto.setReqTypeId(6);
							empReqMapDto.setRegionId(reg_id);
							empReqMapDto.setRequestorId(loginDto.getUserId());
							empReqMapDto.setProcessChainId(processChain[0].getId());
							empReqMapDto.setNotify(processChain[0].getNotification());
							empReqMapDto.setSiblings(handlerIds);
							reqpk = empSerReqDao.insert(empReqMapDto);
							// insert into ITREQUEST
							itrequest.setEsrMapId(reqpk.getId());
							itrequest.setRequesterId(loginDto.getUserId());
							itrequest.setReceiverId(loginDto.getUserId());
							itrequest.setStatus(Status.REQUESTSAVED);
							ItRequestPk itpk = itReqDao.insert(itrequest);
							itrequest.setId(itpk.getId());
							if (!itrequest.isSaveSubmit()){
								break;
							}
						}
					} catch (RegionsDaoException e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.regions"));
						empSerReqDao.delete(reqpk);
						e.printStackTrace();
					} catch (ArrayIndexOutOfBoundsException e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.arrayoutofbound"));
						empSerReqDao.delete(reqpk);
						e.printStackTrace();
					} catch (EmpSerReqMapDaoException e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
						e.printStackTrace();
					} catch (ItRequestDaoException e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
						empSerReqDao.delete(reqpk);
						e.printStackTrace();
					} catch (Exception e){
						e.printStackTrace();
						empSerReqDao.delete(reqpk);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
					}
				case SUBMIT:
					InboxDao inboxDao = InboxDaoFactory.create();
					InboxModel inbox = new InboxModel();
					ItRequest itrequest1 = itReqDao.findByPrimaryKey(itrequest.getId());
					PortalMail portalMailInfoForSubmit = new PortalMail();
					EmpSerReqMap empMap = empSerReqDao.findByPrimaryKey(itrequest1.getEsrMapId());
					Integer[] idshandler = evaluator.handlers(processChain[0].getHandler(), loginDto.getUserId());
					if (idshandler != null && idshandler.length > 0){
						if (itrequest1.getStatus().equals(Status.REQUESTSAVED) && itrequest.isUpdateSubmit()){
							itrequest.setId(itrequest1.getId());
							itrequest.setEsrMapId(itrequest1.getEsrMapId());
							itrequest.setStatus(Status.REQUESTRAISED);
							itrequest.setRequesterId(itrequest1.getRequesterId());
							itrequest.setAssignTo(0);
							itrequest.setCreateDate(new Date());
							itrequest.setLastModifiedDate(new Date());
							itReqDao.update(new ItRequestPk(itrequest.getId()), itrequest);
							empMap.setSiblings(handlerIds);
							empSerReqDao.update(new EmpSerReqMapPk(empMap.getId()), empMap);
						}
						//changes done on Oct-29 by Ganesh
						IssueResolutionDetails issueResolutionDetails = new IssueResolutionDetails();
						issueResolutionDetails.setEsrMapId(itrequest.getEsrMapId());
						issueResolutionDetails.setFirstStatus(Status.REQUESTRAISED);
						if (itrequest.getComments() != null && itrequest.getComments().trim().length() > 0){
							issueResolutionDetails.setRequesterComments(itrequest.getComments());
						}
						issueResolutionDetails.setRequestedOn(new Date());
						issueResolutionDetailsDao.insert(issueResolutionDetails);
						//send mail to requester
						String mailSubject = "Diksha Lynx: IT Support Request " + empMap.getReqId() + " - Acknowledgement";
						portalMailInfoForSubmit.setRecipientMailId(requesterProfile.getOfficalEmailId());
						portalMailInfoForSubmit.setMailSubject(mailSubject);
						portalMailInfoForSubmit.setEmpFname(requesterProfile.getFirstName());
						portalMailInfoForSubmit.setTemplateName(MailSettings.NOTIFICATION_ITREQUEST_SUBMITTED);
						portalMailInfoForSubmit.setSummary(itrequest1.getSummary());
						portalMailInfoForSubmit.setType(itrequest1.getCategory());
						portalMailInfoForSubmit.setAutoIssueReqId(empMap.getReqId());
						portalMailInfoForSubmit.setFieldChanged(null);
						PortalMail portalMailbodyforSubmit = sendMailDetails(request, itrequest.getId(), portalMailInfoForSubmit);
						// populate INBOX entry for requester
						String messagebody = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.NOTIFICATION_ITREQUEST_SUBMITTED), portalMailbodyforSubmit);
						itrequest.setMessageBody(messagebody);
						itReqDao.update(new ItRequestPk(itrequest.getId()), itrequest);
						sendToInboxForITRequest(empMap.getId(), itrequest.getId(), mailSubject, Status.REQUESTRAISED, loginDto.getUserId());
						ItRequest itrequest2 = itReqDao.findByPrimaryKey(itrequest.getId());
						itrequest2.setStatus(Status.REQUESTRAISED);
						itrequest2.setCreateDate(new Date());
						itrequest2.setLastModifiedDate(new Date());
						itrequest2.setAssignTo(0);
						itrequest2.setReceiverId(itrequest2.getRequesterId());
						itReqDao.update(new ItRequestPk(itrequest2.getId()), itrequest2);
						if (handlerList != null && handlerList.size() > 0){
							String temp = "";
							if (handlerList.size() == 1){
								for (Users user : handlerList){
									Users userApproverDto = usersDao.findByPrimaryKey(user.getId());
									ProfileInfo profileInfoDto = profileInfoDao.findByPrimaryKey(userApproverDto.getProfileId());
									temp = profileInfoDto.getFirstName();
								}
							} else{
								temp = "All";
							}
							//send mails to handlers & CC to notifiers
							mailSubject = "Diksha Lynx: IT Support Request " + empMap.getReqId() + " by " + requesterProfile.getFirstName();
							portalMailInfoForSubmit = new PortalMail();
							portalMailInfoForSubmit.setMailSubject(mailSubject);
							portalMailInfoForSubmit.setEmpFname(temp);
							portalMailInfoForSubmit.setTemplateName(MailSettings.ITREQUEST_SUBMITTED_HANDLER);
							portalMailInfoForSubmit.setIssueDescription(itrequest1.getDescription());
							portalMailInfoForSubmit.setType(itrequest1.getCategory());
							portalMailInfoForSubmit.setAutoIssueReqId(empMap.getReqId());
							portalMailInfoForSubmit.setIssueSubmissionDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(new Date()));
							portalMailInfoForSubmit.setRequestorEmpId(requestedUser.getEmpId());
							portalMailInfoForSubmit.setRequestorFirstName(requesterProfile.getFirstName());
							portalMailInfoForSubmit.setRequestorLastName(requesterProfile.getLastName());
							portalMailInfoForSubmit.setEmpDesignation(empDesignation);
							portalMailInfoForSubmit.setEmpDepartment(empDepartment);
							portalMailInfoForSubmit.setEmpDivision(empDivision);
							portalMailInfoForSubmit.setAllReceipientMailId(fetchOfficialMailIds(handlerIds));
							if (notifierIds != null){
								portalMailInfoForSubmit.setAllReceipientcCMailId(fetchOfficialMailIds(notifierIds));
							}
							mailGenarator.invoker(portalMailInfoForSubmit);
							//populate inbox entry for handlers
							String mailBody = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.ITREQUEST_SUBMITTED_HANDLER), portalMailInfoForSubmit);
							for (Users user : handlerList){
								itrequest2.setMessageBody(mailBody);
								itrequest2.setId(0);
								itrequest2.setAssignTo(user.getId());
								itrequest2.setReceiverId(itrequest2.getAssignTo());
								itrequest2.setCreateDate(new Date());
								itrequest2.setLastModifiedDate(new Date());
								itrequest2.setStatus(Status.REQUESTRAISED);
								itrequest2.setAttachment(itrequest.getAttachment());
								ItRequestPk itpk = itReqDao.insert(itrequest2);
								Inbox inboxNew = inbox.populateInboxFromITrequest(itrequest2.getEsrMapId(), itpk.getId(), mailSubject, Status.REQUESTRAISED);
								inboxNew.setReceiverId(user.getId());
								inboxDao.insert(inboxNew);
							}
						}
					} else //if no handlers to process request
					{
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.nohandler.toProcessRequest"));
					}
					break;
				case REOPEN:{
					JDBCUtiility jdbcUtility = JDBCUtiility.getInstance();
					//deleting old entries (given to handlers) of the request(from IT_REQUEST & INBOX table) before inserting new entries
					sqlParams = new Object[] { itrequest.getEsrMapId(), loginDto.getUserId() };
					String deleteQuery_ItRequest = "DELETE FROM IT_REQUEST where ESR_MAP_ID=? AND REQUESTER_ID!=RECEIVER_ID AND REQUESTER_ID=?";
					jdbcUtility.update(deleteQuery_ItRequest, sqlParams);
					String deleteQuery_Inbox = "DELETE FROM INBOX where ESR_MAP_ID=?";
					jdbcUtility.update(deleteQuery_Inbox, new Object[] { itrequest.getEsrMapId() });
					//updating it_request entry given to requester with  status "REOPENED"
					ItRequest loggedInUsersItRequest = itReqDao.findByDynamicWhere("ESR_MAP_ID=? AND REQUESTER_ID=RECEIVER_ID AND REQUESTER_ID=?", sqlParams)[0];
					loggedInUsersItRequest.setStatus(Status.REOPENED);
					loggedInUsersItRequest.setComments(null);
					loggedInUsersItRequest.setCreateDate(new Date());
					loggedInUsersItRequest.setLastModifiedDate(new Date());
					itReqDao.update(new ItRequestPk(loggedInUsersItRequest.getId()), loggedInUsersItRequest);
					//updating siblings in EMP_SER_REQ_MAP table
					EmpSerReqMap empSerReqMap = empSerReqDao.findByPrimaryKey(itrequest.getEsrMapId());
					empSerReqMap.setSiblings(handlerIds);
					empSerReqDao.update(new EmpSerReqMapPk(empSerReqMap.getId()), empSerReqMap);
					//inserting new entries for handlers and sending mail
					if (handlerList != null && handlerList.size() > 0){
						String temp = "";
						if (handlerList.size() == 1){
							for (Users user : handlerList){
								Users handlerDto = usersDao.findByPrimaryKey(user.getId());
								ProfileInfo profileInfoDto = profileInfoDao.findByPrimaryKey(handlerDto.getProfileId());
								temp = profileInfoDto.getFirstName();
							}
						} else{
							temp = "All";
						}
						//send mails to handlers & CC to notifiers
						String mailSubject = "IT Support Request " + empSerReqMap.getReqId() + " is Reopened by " + requesterProfile.getFirstName();
						portalMailInfoForSubmit = new PortalMail();
						portalMailInfoForSubmit.setMailSubject(mailSubject);
						portalMailInfoForSubmit.setEmpFname(temp);
						portalMailInfoForSubmit.setTemplateName(MailSettings.ITREQUEST_REOPENED_HANDLER);
						portalMailInfoForSubmit.setIssueDescription(loggedInUsersItRequest.getDescription());
						portalMailInfoForSubmit.setType(loggedInUsersItRequest.getCategory());
						portalMailInfoForSubmit.setAutoIssueReqId(empSerReqMap.getReqId());
						portalMailInfoForSubmit.setIssueSubmissionDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(empSerReqMap.getSubDate()));
						portalMailInfoForSubmit.setRequestorEmpId(requestedUser.getEmpId());
						portalMailInfoForSubmit.setRequestorFirstName(requesterProfile.getFirstName());
						portalMailInfoForSubmit.setRequestorLastName(requesterProfile.getLastName());
						portalMailInfoForSubmit.setEmpDesignation(empDesignation);
						portalMailInfoForSubmit.setEmpDepartment(empDepartment);
						portalMailInfoForSubmit.setEmpDivision(empDivision);
						portalMailInfoForSubmit.setAllReceipientMailId(fetchOfficialMailIds(handlerIds));
						if (notifierIds != null){
							portalMailInfoForSubmit.setAllReceipientcCMailId(fetchOfficialMailIds(notifierIds));
						}
						mailGenarator.invoker(portalMailInfoForSubmit);
						//populate INBOX & IT_REQUEST entry for handlers
						String mailBody = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.ITREQUEST_REOPENED_HANDLER), portalMailInfoForSubmit);
						ItRequest itrequest2 = null;
						inboxDao = InboxDaoFactory.create();
						inbox = new InboxModel();
						for (Users user : handlerList){
							itrequest2 = new ItRequest();
							itrequest2.setEsrMapId(loggedInUsersItRequest.getEsrMapId());
							itrequest2.setCategory(loggedInUsersItRequest.getCategory());
							itrequest2.setSummary(loggedInUsersItRequest.getSummary());
							itrequest2.setDescription(loggedInUsersItRequest.getDescription());
							itrequest2.setAttachment(loggedInUsersItRequest.getAttachment());
							itrequest2.setComments(loggedInUsersItRequest.getComments());
							itrequest2.setStatus(Status.REOPENED);
							itrequest2.setRequesterId(loggedInUsersItRequest.getRequesterId());
							itrequest2.setAssignTo(user.getId());
							itrequest2.setReceiverId(itrequest2.getAssignTo());
							itrequest2.setMessageBody(mailBody);
							itrequest2.setCreateDate(loggedInUsersItRequest.getCreateDate());
							itrequest2.setLastModifiedDate(loggedInUsersItRequest.getLastModifiedDate());
							ItRequestPk itpk = itReqDao.insert(itrequest2);
							Inbox inboxNew = inbox.populateInboxFromITrequest(itrequest2.getEsrMapId(), itpk.getId(), mailSubject, Status.REOPENED);
							inboxNew.setReceiverId(user.getId());
							inboxDao.insert(inboxNew);
						}
					}
					// create an entry in ISSUE_RESOLUTION_DETAILS table
					IssueResolutionDetails issueResolutionDetails = new IssueResolutionDetails();
					issueResolutionDetails.setEsrMapId(itrequest.getEsrMapId());
					issueResolutionDetails.setFirstStatus(Status.REOPENED);
					if (itrequest.getComments() != null && itrequest.getComments().trim().length() > 0){
						issueResolutionDetails.setRequesterComments(itrequest.getComments());
					}
					issueResolutionDetails.setRequestedOn(new Date());
					issueResolutionDetailsDao.insert(issueResolutionDetails);
				}
					break;
			}//end of switch
		} catch (ArrayIndexOutOfBoundsException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.approver"));
			e.printStackTrace();
		} catch (EmpSerReqMapDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (ItRequestDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
		}
		return result;
	}

	//to get Users List from level Ids
	public static final ArrayList<Users> getUserLstByLevelIds(String notifiers, ProfileInfo requestorProfileInfoDto) throws UsersDaoException {
		UsersDao userDao = UsersDaoFactory.create();
		Object[] sqlParams = null;
		//notifiers=(notifiers.trim()).replace('|', ',');
		String handlers[] = notifiers.split("\\|");
		String tempArr[] = null;
		if (handlers.length == 1){
			tempArr = handlers[0].split(",");
		} else{
			tempArr = handlers[1].split(",");
		}
		String levels = "0";
		ArrayList<Users> handlerOrNotifiersList = new ArrayList<Users>();
		Users RM = null, HRSPOC = null;
		for (int i = 0; i < tempArr.length; i++){
			if (tempArr[i].equals("RM")){
				RM = userDao.findByPrimaryKey(requestorProfileInfoDto.getReportingMgr());
			} else if (tempArr[i].equals("HRSPOC")){
				HRSPOC = userDao.findByPrimaryKey(requestorProfileInfoDto.getHrSpoc());
			} else{
				levels = levels + "," + tempArr[i];
			}
		}
		Users[] userLst = userDao.findByDynamicWhere("LEVEL_ID IN (" + levels.trim() + ")", sqlParams);
		for (Users user : userLst){
			short status = user.getStatus();
			if (user.getId() > 0 && status != 1 && status != 2 && status != 3){
				handlerOrNotifiersList.add(user);
			}
		}
		if (RM != null){
			handlerOrNotifiersList.add(RM);
		}
		if (HRSPOC != null){
			handlerOrNotifiersList.add(HRSPOC);
		}
		return handlerOrNotifiersList;
	}

	public static final String getUserIdsFromUserList(ArrayList<Users> userList) {
		String userIds = "0";
		for (Users user : userList){
			if (user.getId() != 0){
				userIds += "," + user.getId();
			}
		}
		return userIds;
	}

	public static final String[] fetchOfficialMailIds(String ids) throws ProfileInfoDaoException {
		String whereClause = " WHERE PI.ID IN (SELECT U.PROFILE_ID FROM USERS U WHERE U.ID IN (" + ids + ") )"; //PROFILE_INFO AS PI
		ProfileInfoDao pInfoDao = ProfileInfoDaoFactory.create();
		return (pInfoDao.findOfficalMailIdsWhere(whereClause));
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ItRequest itrequest = (ItRequest) form;
		ActionResult result = new ActionResult();
		//InboxModel inbox = new InboxModel();
		ItRequestDao itreqDao = ItRequestDaoFactory.create();
		InboxDao inboxDao = InboxDaoFactory.create();
		UsersDao userDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		IssueResolutionDetailsDao issueResolutionDetailsDao = IssueResolutionDetailsDaoFactory.create();
		try{
			ItRequest reqDto = itreqDao.findByPrimaryKey(itrequest.getId());
			EmpSerReqMap empMap = empSerReqDao.findByPrimaryKey(reqDto.getEsrMapId());
			String mailSubject = "";
			Login loginDto = Login.getLogin(request);
			Users requestedUser = userDao.findByPrimaryKey(reqDto.getRequesterId());
			ProfileInfo loggedInUserProfile = profileInfoDao.findByPrimaryKey(userDao.findByPrimaryKey(loginDto.getUserId()).getProfileId());
			ProfileInfo requesterProfile = profileInfoDao.findByPrimaryKey(requestedUser.getProfileId());
		//	ProfileInfo requesterRmProfile = profileInfoDao.findByPrimaryKey(userDao.findByPrimaryKey(requesterProfile.getReportingMgr()).getProfileId());
		//	ProfileInfo requesterHrProfile = profileInfoDao.findByPrimaryKey(userDao.findByPrimaryKey(requesterProfile.getHrSpoc()).getProfileId());
			ProcessChain pChain = processChainDao.findByPrimaryKey(empMap.getProcessChainId());
			ArrayList<Users> handlerList = null;
			ArrayList<Users> notifierList = null;
			String handlers = null, notifiers = null, notifierIds = null;// handlerIds = null;
		//	Object[] sqlParams = null;
			if (pChain != null){
				handlers = pChain.getHandler();
				if (handlers != null && !handlers.equals("")){
					handlerList = getUserLstByLevelIds(handlers, requesterProfile);//11 July
					if (handlerList.contains(requestedUser)){
						handlerList.remove(requestedUser);
					}
					/*if (handlerList != null){
						handlerIds = getUserIdsFromUserList(handlerList);
					}*/
				}
				notifiers = pChain.getNotification();
				if (notifiers != null && !notifiers.equals("")){
					notifierList = getUserLstByLevelIds(notifiers, requesterProfile);
					if (notifierList != null){
						notifierIds = getUserIdsFromUserList(notifierList);
					}
				}
			}
			if (handlerList == null || handlerList.size() < 1){
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.nohandler.toProcessRequest"));
				return result;
			}
			switch (UpdateTypes.getValue(form.getuType())) {
				case EDIT:
					if (reqDto.getStatus().equals(Status.REQUESTSAVED)){
						itrequest.setId(reqDto.getId());
						itrequest.setEsrMapId(reqDto.getEsrMapId());
						if (itrequest.getAttachment() == 0) itrequest.setAttachment(reqDto.getAttachment());
						itrequest.setStatus(reqDto.getStatus());
						itrequest.setRequesterId(reqDto.getRequesterId());
						itrequest.setAssignTo(reqDto.getAssignTo());
						itrequest.setCreateDate(new Date());
						itrequest.setReceiverId(loginDto.getUserId());
						itreqDao.update(new ItRequestPk(itrequest.getId()), itrequest);
					}
					break;
				case ASSIGN:{
					ProfileInfo assigneeProfile = profileInfoDao.findByPrimaryKey(userDao.findByPrimaryKey(itrequest.getAssignTo()).getProfileId());
					//save comments if any
					this.saveComments(itrequest, loginDto);
					mailSubject = " IT Support request " + empMap.getReqId() + " raised by " + requesterProfile.getFirstName() + " is Docked By " + assigneeProfile.getFirstName();
					//changes done by ganesh
					ItRequest itreqArr[] = itreqDao.findByDynamicWhere("ESR_MAP_ID=? AND RECEIVER_ID!=REQUESTER_ID", new Object[] { new Integer(reqDto.getEsrMapId()) });
					String nextStatus = null;
					if (itreqArr != null && itreqArr.length > 0){
						if (itreqArr[0].getStatus().equalsIgnoreCase(Status.INPROGRESS)){
							nextStatus = Status.INPROGRESS;
						} else{
							nextStatus = "Docked By " + assigneeProfile.getFirstName();
						}
						for (int i = 0; i < itreqArr.length; i++){
							if (itrequest.getAssignTo() == itreqArr[i].getReceiverId()){
								itreqArr[i].setAssignTo(itreqArr[i].getReceiverId());
							} else{
								itreqArr[i].setAssignTo(0);
							}
							if (itreqArr[i].getReceiverId() != itreqArr[i].getRequesterId()){
								itreqArr[i].setStatus(nextStatus);
								itreqArr[i].setLastModifiedDate(new Date());
							}
							ItRequestPk itReqPk = new ItRequestPk();
							itReqPk.setId(itreqArr[i].getId());
							itreqDao.update(itReqPk, itreqArr[i]);
						}
					}
					Object[] Params = { reqDto.getEsrMapId() };
					Inbox inboxArr[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE ESR_MAP_ID=?", Params);
					if (inboxArr.length > 0){
						for (int i = 0; i < inboxArr.length; i++){
							if (itrequest.getAssignTo() == inboxArr[i].getReceiverId()){
								inboxArr[i].setAssignedTo(inboxArr[i].getReceiverId());
								inboxArr[i].setIsRead(0);
								inboxArr[i].setIsDeleted(0);
							} else{
								inboxArr[i].setAssignedTo(0);
							}
							if (inboxArr[i].getReceiverId() != inboxArr[i].getRaisedBy()){
								inboxArr[i].setStatus(nextStatus);
								inboxArr[i].setSubject(mailSubject);
								inboxArr[i].setCreationDatetime(new Date());
							}
							InboxPk inboxPK = new InboxPk();
							inboxPK.setId(inboxArr[i].getId());
							inboxDao.update(inboxPK, inboxArr[i]);
						}
					}
				}
					break;
				case INPROGRESS:{
					ItRequest itreqArr[] = itreqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { new Integer(reqDto.getEsrMapId()) });
					if (itreqArr != null && itreqArr.length > 0){
						for (int i = 0; i < itreqArr.length; i++){
							if (itreqArr[i].getAssignTo() != itrequest.getAssignTo()){
								itreqArr[i].setAssignTo(0);
							}
							itreqArr[i].setStatus(Status.INPROGRESS);
							itreqArr[i].setLastModifiedDate(new Date());
							ItRequestPk itReqPk = new ItRequestPk();
							itReqPk.setId(itreqArr[i].getId());
							itreqDao.update(itReqPk, itreqArr[i]);
						}
					}
					Object[] Params = { reqDto.getEsrMapId() };
					Inbox inboxArr[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE ESR_MAP_ID=?", Params);
					mailSubject = "IT Support request " + empMap.getReqId() + " is In-Progress ";
					if (inboxArr.length > 0){
						for (int i = 0; i < inboxArr.length; i++){
							if (inboxArr[i].getAssignedTo() != itrequest.getAssignTo()){
								inboxArr[i].setAssignedTo(0);
							}
							inboxArr[i].setStatus(Status.INPROGRESS);
							inboxArr[i].setCreationDatetime(new Date());
							inboxArr[i].setSubject(mailSubject);
							//inboxArr[0].setMessageBody(messagebody);
							inboxArr[i].setIsRead(0);
							inboxArr[i].setIsDeleted(0);
							InboxPk inboxPK = new InboxPk();
							inboxPK.setId(inboxArr[i].getId());
							inboxDao.update(inboxPK, inboxArr[i]);
						}
					}
					//save comments if any
					this.saveComments(itrequest, loginDto);
				}
					break;
				case RESOLVED:{
					//save comments if any
					this.saveComments(itrequest, loginDto);
					//update ITREQUEST table
					ItRequest itreqArr[] = itreqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { new Integer(reqDto.getEsrMapId()) });
					if (itreqArr != null && itreqArr.length > 0){
						for (int i = 0; i < itreqArr.length; i++){
							if (itreqArr[i].getAssignTo() != itrequest.getAssignTo()){
								itreqArr[i].setAssignTo(0);
							}
							itreqArr[i].setStatus(Status.RESOLVED);
							itreqArr[i].setLastModifiedDate(new Date());
							ItRequestPk itReqPk = new ItRequestPk();
							itReqPk.setId(itreqArr[i].getId());
							itreqDao.update(itReqPk, itreqArr[i]);
						}
					}
					MailGenerator mailGenarator = new MailGenerator();
					PortalMail portalMail = new PortalMail();
					// Send mail to handler(one who resolved) & CC to notifiers
					String comments = "Not Available";
					if (itrequest.getRemark() != null && itrequest.getRemark().trim().length() > 0){
						comments = itrequest.getRemark();
					}
					mailSubject = "Diksha Lynx: IT Support Request " + empMap.getReqId() + " - Resolution ";
					portalMail.setMailSubject(mailSubject);
					portalMail.setEmpFname(requesterProfile.getFirstName());
					portalMail.setTemplateName(MailSettings.ITREQUEST_NOTIFICATION_HANDLERS);
					portalMail.setSummary(reqDto.getSummary());
					portalMail.setType(reqDto.getCategory());
					portalMail.setAutoIssueReqId(empMap.getReqId());
					portalMail.setDateOfAction(PortalUtility.getdd_MM_yyyy_hh_mm_a(new Date()));
					portalMail.setIssueReqStatus(Status.RESOLVED);
					portalMail.setComments(comments);
					portalMail.setRecipientMailId(requesterProfile.getOfficalEmailId());
					ArrayList<String> list = new ArrayList<String>();
					list.add(loggedInUserProfile.getOfficalEmailId());
					if (notifierIds != null){
						for (String emailId : fetchOfficialMailIds(notifierIds))
							list.add(emailId);
					}
					portalMail.setAllReceipientcCMailId(list.toArray(new String[list.size()]));
					mailGenarator.invoker(portalMail);
					// update INBOX for Requester and handlers
					Object[] Params = { reqDto.getEsrMapId() };
					Inbox inboxArr[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE ESR_MAP_ID=?", Params);
					String messagebody = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.ITREQUEST_NOTIFICATION_HANDLERS), portalMail);
					if (inboxArr.length > 0){
						for (int i = 0; i < inboxArr.length; i++){
							inboxArr[i].setStatus(Status.RESOLVED);
							inboxArr[i].setSubject(mailSubject);
							inboxArr[i].setMessageBody(messagebody);
							inboxArr[i].setAssignedTo(0);
							inboxArr[i].setIsRead(0);
							inboxArr[i].setIsDeleted(0);
							inboxArr[i].setCreationDatetime(new Date());
							InboxPk inboxPK = new InboxPk();
							inboxPK.setId(inboxArr[i].getId());
							inboxDao.update(inboxPK, inboxArr[i]);
						}
					}
					//update ISSUE_RESOLUTION_DETAILS TABLE
					IssueResolutionDetails issueResolutionDetails = issueResolutionDetailsDao.findByDynamicWhere("ESR_MAP_ID=? AND RESOLVED_BY IS NULL", new Object[] { reqDto.getEsrMapId() })[0];
					issueResolutionDetails.setLastStatus(Status.RESOLVED);
					issueResolutionDetails.setResolvedOn(new Date());
					issueResolutionDetails.setResolvedBy(loginDto.getUserId());
					issueResolutionDetailsDao.update(new IssueResolutionDetailsPk(issueResolutionDetails.getId()), issueResolutionDetails);
				}
					break;
				case CANCELED:{
					ItRequest itreqArr[] = itreqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { new Integer(reqDto.getEsrMapId()) });
					if (itreqArr != null && itreqArr.length > 0){
						for (int i = 0; i < itreqArr.length; i++){
							itreqArr[i].setStatus(Status.REVOKED);
							itreqArr[i].setLastModifiedDate(new Date());
							ItRequestPk itReqPk = new ItRequestPk();
							itReqPk.setId(itreqArr[i].getId());
							itreqDao.update(itReqPk, itreqArr[i]);
						}
					}
					Object[] Params = { reqDto.getEsrMapId() };
					Inbox inboxArr[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE ESR_MAP_ID=?", Params);
					mailSubject = "IT Support request " + empMap.getReqId() + " is Revoked ";
					if (inboxArr.length > 0){
						for (int i = 0; i < inboxArr.length; i++){
							inboxArr[i].setStatus(Status.REVOKED);
							inboxArr[i].setSubject(mailSubject);
							//inboxArr[0].setMessageBody(messagebody);
							inboxArr[i].setAssignedTo(0);
							inboxArr[i].setIsRead(0);
							inboxArr[i].setIsDeleted(0);
							inboxArr[i].setCreationDatetime(new Date());
							InboxPk inboxPK = new InboxPk();
							inboxPK.setId(inboxArr[i].getId());
							inboxDao.update(inboxPK, inboxArr[i]);
						}
					}
				}
					break;
				case CLOSEDBYREQUESTOR:{
					ItRequest itreqArr[] = itreqDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { new Integer(reqDto.getEsrMapId()) });
					if (itreqArr != null && itreqArr.length > 0){
						for (int i = 0; i < itreqArr.length; i++){
							itreqArr[i].setStatus(Status.CLOSED);
							ItRequestPk itReqPk = new ItRequestPk();
							itReqPk.setId(itreqArr[i].getId());
							itreqDao.update(itReqPk, itreqArr[i]);
						}
					}
					Object[] Params = { reqDto.getEsrMapId() };
					Inbox inboxArr[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE ESR_MAP_ID=?", Params);
					mailSubject = "IT Support request " + empMap.getReqId() + " is Closed";
					if (inboxArr.length > 0){
						for (int i = 0; i < inboxArr.length; i++){
							inboxArr[i].setStatus(Status.CLOSED);
							inboxArr[i].setSubject(mailSubject);
							//inboxArr[0].setMessageBody(messagebody);
							inboxArr[i].setAssignedTo(0);
							inboxArr[i].setIsRead(0);
							inboxArr[i].setIsDeleted(0);
							inboxArr[i].setCreationDatetime(new Date());
							InboxPk inboxPK = new InboxPk();
							inboxPK.setId(inboxArr[i].getId());
							inboxDao.update(inboxPK, inboxArr[i]);
						}
					}
				}
					//update ISSUE_RESOLUTION_DETAILS TABLE
					IssueResolutionDetails issueResolutionDetails = issueResolutionDetailsDao.findByDynamicWhere("ESR_MAP_ID=? ORDER BY REQUESTED_ON DESC", new Object[] { reqDto.getEsrMapId() })[0];
					issueResolutionDetails.setLastStatus(Status.CLOSED);
					issueResolutionDetails.setClosedOn(new Date());
					issueResolutionDetailsDao.update(new IssueResolutionDetailsPk(issueResolutionDetails.getId()), issueResolutionDetails);
					break;
			}//end of switch
		} catch (Exception e){
			e.printStackTrace();
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
		}
		return result;
	}

	/**
	 * @author ganesha.mn
	 *         method to save It Request comments
	 * @param itrequest
	 *            ,loginDto
	 * @throws IssueCommentsMapDaoException
	 *             ,IssueCommentsDaoException,ItRequestDaoException
	 */
	private void saveComments(ItRequest itrequest, Login loginDto) throws IssueCommentsMapDaoException, IssueCommentsDaoException, ItRequestDaoException {
		ItRequest reqDto = ItRequestDaoFactory.create().findByPrimaryKey(itrequest.getId());
		if (itrequest.getRemark() != null && itrequest.getRemark().trim().length() > 0){
			IssueCommentsDao issueCommentsDao = IssueCommentsDaoFactory.create();
			IssueComments issueComents = new IssueComments();
			issueComents.setComment("[" + PortalUtility.getdd_MM_yyyy_hh_mm_a(new Date()) + "] : " + itrequest.getRemark());
			issueComents.setCommentDate(new Date());
			IssueCommentsPk issueCommentPk = issueCommentsDao.insert(issueComents);
			if (issueCommentPk != null){
				IssueCommentsMapDao issueCommentMapDao = IssueCommentsMapDaoFactory.create();
				IssueCommentsMap issueCommentsMap = new IssueCommentsMap();
				issueCommentsMap.setEsrMapId(reqDto.getEsrMapId());
				issueCommentsMap.setCommentId(issueCommentPk.getId());
				issueCommentsMap.setUserId(loginDto.getUserId());
				issueCommentMapDao.insert(issueCommentsMap);
			}
		}
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public PortalMail sendMailDetails(HttpServletRequest request, int itReqId, PortalMail portalMail) {
		ItRequestDao itreqDao = ItRequestDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		DivisonDao divisionDao = DivisonDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		Login login = (Login) request.getSession(false).getAttribute("login");
		try{
			ItRequest itRequest = itreqDao.findByPrimaryKey(itReqId);
			Users users = usersDao.findByPrimaryKey(itRequest.getRequesterId());
			ProfileInfo profileReqInfo[] = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PFINFO LEFT JOIN USERS U ON U.PROFILE_ID=PFINFO.ID WHERE U.ID=?", new Object[] { new Integer(itRequest.getRequesterId()) });
			Levels levels = levelsDao.findWhereIdEquals(profileReqInfo[0].getLevelId())[0];
			Divison division = divisionDao.findWhereIdEquals(levels.getDivisionId())[0];
			ProfileInfo profileInfoHandlerLogdin = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PFINFO LEFT JOIN USERS U ON U.PROFILE_ID=PFINFO.ID WHERE U.ID=?", new Object[] { new Integer(login.getUserId()) })[0];
			/*
			 * code added by Sasmita Sabar
			 */
			//finding Department for requester
			int parentId = division.getParentId();
			while (parentId > 0){
				division = divisionDao.findByPrimaryKey(parentId);
				parentId = division.getParentId();
			}
			portalMail.setRequestorEmpId(users.getEmpId());
			portalMail.setRequestorFirstName(profileReqInfo[0].getFirstName());
			portalMail.setRequestorLastName(profileReqInfo[0].getLastName());
			portalMail.setEmpDesignation(levels.getDesignation());//requestor's designation
			portalMail.setEmpDivision(division.getName());//requestor's department name
			portalMail.setApproverName(profileInfoHandlerLogdin.getFirstName() + " " + profileInfoHandlerLogdin.getLastName());
			boolean allMailIdsFlag = true;
			if (portalMail.getAllReceipientMailId() != null){
				for (String eachMailId : portalMail.getAllReceipientMailId()){
					if (!(eachMailId.contains("@"))){
						allMailIdsFlag = false;
						break;
					}
				}
			}
			if ((portalMail.getRecipientMailId() != null && (portalMail.getRecipientMailId()).contains("@")) || (portalMail.getAllReceipientMailId() != null && allMailIdsFlag == true)){
				MailGenerator mailGenarator = new MailGenerator();
				mailGenarator.invoker(portalMail);
			}
			return portalMail;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public Integer[] upload(List<FileItem> fileItems, String docType, int id, HttpServletRequest request, String description) {
		Integer fieldsId[] = new Integer[fileItems.size()];
		DocumentTypes dTypes = DocumentTypes.valueOf(docType);
		// Upload the File.
		PortalData portalData = new PortalData();
		try{
			String Upload_fileName = "";
			// if user attached file then upload otherwise not.
			int i = 0;
			for (FileItem file : fileItems){
				if (file.getName() != null){
					Upload_fileName = portalData.saveFile(file, dTypes, id);
					Documents documents = new Documents();
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
					documents.setFilename(Upload_fileName);
					documents.setDoctype(docType);
					DocumentsPk documentsPk = documentsDao.insert(documents);
					fieldsId[i] = documentsPk.getId();
					i++;
					logger.info("File :" + file.getName() + "successfully uploaded :");
				}
			}
			logger.info("The no of files uploaded  :" + fieldsId.length);
		} catch (FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentsDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fieldsId;
	}

	public Attachements download(PortalForm form) {
		Attachements attachements = new Attachements();
		ItRequest req = (ItRequest) form;
		String seprator = File.separator;
		String path = "Data" + seprator;
		path = PropertyLoader.getEnvVariable() + seprator + path;
		// Get filename from id
		PortalData portalData = new PortalData();
		path = portalData.getfolder(path);
		try{
			DocumentsDao documentsDao = DocumentsDaoFactory.create();
			Documents docNew = new Documents();
			docNew = documentsDao.findByPrimaryKey(req.getAttachment());
			attachements.setFileName(docNew.getFilename());
			attachements.setFilePath(path + seprator + docNew.getFilename());
		} catch (Exception e){
			e.printStackTrace();
		}
		return attachements;
	}

	private Inbox sendToInboxForITRequest(int esrMapId, int reqId, String subject, String status, int userId) {
		Inbox inbox = new Inbox();
		ItRequestDao itreqDao = ItRequestDaoFactory.create();
		try{
			String sql = "SELECT * FROM IT_REQUEST WHERE ID=? AND ESR_MAP_ID=?";
			ItRequest itReq = itreqDao.findByDynamicSelect(sql, new Object[] { reqId, esrMapId })[0];
			inbox.setSubject(subject);
			inbox.setRaisedBy(itReq.getRequesterId());
			inbox.setStatus(status);
			inbox.setCreationDatetime(itReq.getCreateDate());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("ITSUPPORT");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(itReq.getMessageBody());
			try{
				InboxDao inboxDao = InboxDaoFactory.create();
				// send request to assigned person
				inbox.setReceiverId(userId);
				inbox.setAssignedTo(0);
				InboxPk inboxPk = inboxDao.insert(inbox);
				inbox.setId(inboxPk.getId());
			} catch (InboxDaoException e){
				e.printStackTrace();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return inbox;
	}

	/*private ProfileInfo getProfileObjForUser(int userId) {
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		String sql = "SELECT * FROM PROFILE_INFO PFINFO LEFT JOIN USERS U ON U.PROFILE_ID=PFINFO.ID WHERE U.ID=? ";
		ProfileInfo profileInfo[] = null;
		try{
			profileInfo = profileInfoDao.findByDynamicSelect(sql, new Object[] { userId });
		} catch (Exception e){
			e.printStackTrace();
			logger.info(e);
		}
		return profileInfo[0];
	}*/
}
