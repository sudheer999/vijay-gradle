package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.ReimbursementApproversDetails;
import com.dikshatech.beans.ReimbursementBean;
import com.dikshatech.common.utils.GenerateXls;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.Notification;
import com.dikshatech.common.utils.POIParser;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.common.utils.RequestEscalation;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.ChargeCodeDao;
import com.dikshatech.portal.dao.CurrencyTypeDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ModulePermissionDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.ReimbursementFinancialDataDao;
import com.dikshatech.portal.dao.ReimbursementReqDao;
import com.dikshatech.portal.dao.ReimbursementTypeDao;
import com.dikshatech.portal.dao.StatusDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.DivisonPk;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.DocumentsPk;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Handlers;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.InboxPk;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.ReimbursementFinancialData;
import com.dikshatech.portal.dto.ReimbursementFinancialDataPk;
import com.dikshatech.portal.dto.ReimbursementReq;
import com.dikshatech.portal.dto.ReimbursementReqPk;
import com.dikshatech.portal.dto.ReimbursementRequestForm;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.CurrencyTypeDaoException;
import com.dikshatech.portal.exceptions.DocumentsDaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.exceptions.ReimbursementFinancialDataDaoException;
import com.dikshatech.portal.exceptions.ReimbursementReqDaoException;
import com.dikshatech.portal.exceptions.ReimbursementTypeDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.ChargeCodeDaoFactory;
import com.dikshatech.portal.factory.CurrencyTypeDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ModulePermissionDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.ReimbursementFinancialDataDaoFactory;
import com.dikshatech.portal.factory.ReimbursementReqDaoFactory;
import com.dikshatech.portal.factory.ReimbursementTypeDaoFactory;
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

public class ReimbursementModel extends ActionMethods {

	private static Logger		logger			= LoggerUtil.getLogger(ReimbursementModel.class);
	private RequestEscalation	reqEscalation	= new RequestEscalation();

	
	// Put your Google API Server Key here
		static final String			MESSAGE_KEY			= "message";
		static final String			CATEGORY			= "moduleCategory";
	public enum StateType {
		ASSIGNED, ACCEPTED, UNKNOWN, Submitted;

		public static StateType getValue(String value) {
			try{
				return valueOf(value);
			} catch (Exception e){
				return UNKNOWN;
			}
		};
	}

	@Override
	public ActionResult defaultMethod(PortalForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		/*
		 * ReimbursementRequestForm reqdata = (ReimbursementRequestForm) form;
		 * EmpSerReqMapDao serReqMapDao = EmpSerReqMapDaoFactory.create();
		 * ReimbursementReqDao reimbursementDao =
		 * ReimbursementReqDaoFactory.create(); ServiceReqInfoDao reqinfoDao =
		 * ServiceReqInfoDaoFactory.create(); try { int id = 6; switch
		 * (DeleteTypes.getValue(form.getdType())) { case DELETE: if
		 * (reimbursementDao
		 * .findWhereEsrMapIdEquals(id)[0].getStatus().equals("Not Submitted"))
		 * { serReqMapDao.delete(new EmpSerReqMapPk(id)); } break; } } catch
		 * (Exception e) { }
		 */
		return result;
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
	
	/*@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}*/
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login loginDto = Login.getLogin(request);
		int presentEid=0;
		ReimbursementRequestForm reqData = (ReimbursementRequestForm) form;
		String esrMapIdsForsearchCriteria1 = null;
		try {
			esrMapIdsForsearchCriteria1 = getEsrMapIdsBasedOnSearchCriteria(reqData);
		} catch (EmpSerReqMapDaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EmpSerReqMapDao serReqMapDao = EmpSerReqMapDaoFactory.create();
		ReimbursementReqDao reimbursementReqDao = ReimbursementReqDaoFactory.create();
		//ProfileInfoDao profileinfodao=ProfileInfoDaoFactory.create();
		ChargeCodeDao chargeCodeDao = ChargeCodeDaoFactory.create();
		ReimbursementFinancialDataDao rFinancialDao = ReimbursementFinancialDataDaoFactory.create();
		RegionsDao regionDao = RegionsDaoFactory.create();
		DivisonDao divisionDao = DivisonDaoFactory.create();
		UsersDao uDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		ReimbursementReq dto = new ReimbursementReq();
		ProcessChain process_chain_dto = new ProcessChain();
		ProcessEvaluator processEvaluator = new ProcessEvaluator();
		ProfileInfo profileinfo=null;
		ProfileInfo profileinfo1=null;
		ReimbursementRequestForm reimbuForm =  (ReimbursementRequestForm) form;
		String flag1=reimbuForm.getBankFlag();
		Connection conn = null;
		//Set escalation
		int escEsrMapId = reqData.getEsrMapId();
		int escUserId = loginDto != null ? loginDto.getUserId() : 0;
		reqEscalation.setEscalationAction(escEsrMapId, escUserId);
		//If escalation action set current esrMapId
		if (reqEscalation.isEscalationAction()){
			reqEscalation.setEsrMapId(escEsrMapId);
			reqEscalation.setEscalationProcess(processEvaluator);
		}
		try{
			switch (ReceiveTypes.getValue(form.getrType())) {
			case TOAPPROVE:
				try{
					request.setAttribute("actionForm", isApprover(loginDto.getUserId()));
				} catch (Exception e){
					e.printStackTrace();
				}
				break;
				case RECEIVE:
					if( (reqData.getReimbuFlag()!=null) && reqData.getReimbuFlag().equalsIgnoreCase("OTHER") ){
					
					DivisonDao divisonDao=DivisonDaoFactory.create();
								
				//for masterdata 
				//	dto = reimbursementReqDao.findByDynamicSelect("SELECT R.REG_NAME,C.COMPANY_NAME FROM USERS U LEFT JOIN LEVELS L ON U.LEVEL_ID=L.ID LEFT JOIN DIVISON D ON  L.DIVISION_ID=D.ID LEFT JOIN REGIONS R ON R.ID=D.REGION_ID LEFT JOIN COMPANY C ON C.ID=R.COMPANY_ID LEFT JOIN PROFILE_INFO PI ON PI.ID=U.PROFILE_ID WHERE U.ID=?;)", new Object[] { new Integer(reqData.getEsrMapId()) })[0];
					
					
					if (reqData.getEsrMapId() != 0){
						dto = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ID=(SELECT MAX(ID) FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=?)", new Object[] { new Integer(reqData.getEsrMapId()) })[0];
					} else{
						dto = reimbursementReqDao.findByPrimaryKey(reqData.getId());
					}
					UsersDao usersDao = UsersDaoFactory.create();
					Users user = usersDao.findByPrimaryKeys(dto.getPaymentMadeToEmpId());
					profileinfo=profileInfoDao.findByPrimaryKey(user.getProfileId());
					Users user1 = usersDao.findByPrimaryKeys(dto.getOTHER_EMP_NAME());
					profileinfo1=profileInfoDao.findByPrimaryKey(user1.getProfileId());
					
					int remDivisonId = 0 ;
					int remDeptId = 0;
					String remDivisonName = null;
					String remDeptName = null;
					int reg_id = regionDao.findByDynamicSelect("SELECT * FROM REGIONS R LEFT JOIN DIVISON D ON D.REGION_ID=R.ID LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN PROFILE_INFO PI ON L.ID=PI.LEVEL_ID LEFT JOIN USERS U ON PI.ID=U.PROFILE_ID WHERE U.ID=?", new Object[] { new Integer(user.getId()) })[0].getId();
					
					Divison fetchedDivison = divisonDao.findByDynamicSelect("SELECT * FROM DIVISON D WHERE ID=(SELECT L.DIVISION_ID FROM LEVELS L WHERE L.ID=(SELECT U.LEVEL_ID FROM USERS U WHERE U.ID=?))", new Object[]{user.getId()})[0];
					if(fetchedDivison!=null){
						int parentId = fetchedDivison.getParentId();
						if(parentId>0){
							remDivisonId = fetchedDivison.getId();
							remDivisonName = fetchedDivison.getName();
							while(parentId > 0){
								fetchedDivison = divisonDao.findByPrimaryKey(parentId);
								parentId = fetchedDivison.getParentId();
							}
							remDeptId = fetchedDivison.getId();
							remDeptName = fetchedDivison.getName();							
						}else{
							remDeptId = fetchedDivison.getId();
							remDeptName = fetchedDivison.getName();
						}								
					}
			
					//for masterdata 
				//	dto = reimbursementReqDao.findByDynamicSelectwhere("SELECT R.REG_NAME,C.COMPANY_NAME FROM USERS U LEFT JOIN LEVELS L ON U.LEVEL_ID=L.ID LEFT JOIN DIVISON D ON  L.DIVISION_ID=D.ID LEFT JOIN REGIONS R ON R.ID=D.REGION_ID LEFT JOIN COMPANY C ON C.ID=R.COMPANY_ID LEFT JOIN PROFILE_INFO PI ON PI.ID=U.PROFILE_ID WHERE U.ID=?;", new Object[] { new Integer(user.getId()) })[0];
					ReimbursementReq[] reim =reimbursementReqDao.findWhereuserIdEquals(user.getId());	
					List<Object> ReimbursementReqData=new ArrayList<Object>();	
					if (reim != null && reim.length > 0){
						for (ReimbursementReq report1 : reim){
							dto.setCompany_ID(report1.getCompany_ID());
							dto.setRegName(report1.getRegName());
							dto.setCompanyName(report1.getCompanyName());
							dto.setDesignation(report1.getDesignation());
							
							ReimbursementReqData.add(report1);
						}
						}
					
					
					/*travelRequest2.setDepartmentId(travellersDeptId);
					travelRequest2.setDepartmentName(travellersDeptName);
					travelRequest2.setDivisionId(travellersDivisonId);					
					travelRequest2.setDivisionName(travellersDivisonName);
					*/
				/*	dto.setDepartmentId(remDeptId);
					dto.setDepartmentName(remDeptName);
					dto.setDivisionId(remDivisonId);					
					dto.setDivisionName(remDivisonName);*/
					
					
					dto.setRemark(null);
					ReimbursementFinancialData[] fdto = rFinancialDao.findWhereEsrmapIdEquals(dto.getEsrMapId());
					ReimbursementFinancialData[] arr = new ReimbursementFinancialData[fdto.length];
					int i = 0;
					for (ReimbursementFinancialData d : fdto){
						arr[i] = ReimbursementFinancialData.getReimbursementFinancialData(d);
						i++;
					}
					if (dto.getChargeCode() > 0){
						dto.setChargeCodeName(chargeCodeDao.findByPrimaryKey(dto.getChargeCode()).getChCodeName());
					}
					ReimbursementBean bean = DtoToBeanConverter.DtoToBeanConverter(dto, arr);
					ReimbursementReq checkApp2[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=?", new Object[] { dto.getEsrMapId() });
					if (loginDto.getUserId() == dto.getAssignTo()){
						bean.setStatus(loginDto.getUserId() == dto.getAssignTo() && dto.getStatus().equals(Status.REQUESTRAISED) ? Status.REQUESTRAISED : dto.getStatus());
					} else if (loginDto.getUserId() == dto.getRequesterId()){
						bean.setStatus(bean.getStatus().equalsIgnoreCase("Request Raised") && checkApp2.length > 2 && (checkApp2[1].getStatus().equalsIgnoreCase("Approved") && checkApp2[checkApp2.length - 1].getStatus().equalsIgnoreCase("Request Raised")) ? "Pending Approval" : bean.getStatus().equalsIgnoreCase(Status.INPROGRESS) ? bean.getOldStatus().equalsIgnoreCase(Status.CANCELREQUEST) ? Status.CANCELINPROGRESS : Status.PROCESSING : bean.getStatus().equalsIgnoreCase(Status.ASSIGNED) ? bean
								.getOldStatus().equalsIgnoreCase(Status.CANCELINPROGRESS) ? Status.CANCELREQUEST : Status.PENDINGAPPROVAL : bean.getStatus());
					}
					bean.setReqid(serReqMapDao.findByPrimaryKey(dto.getEsrMapId()).getReqId());
					ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
					process_chain_dto = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=26  WHERE UR.USER_ID=?", new Object[] { new Integer(bean.getRequesterId()) })[0];
					//Integer[] firstlevel = processEvaluator.approvers(process_chain_dto.getApprovalChain(), 1, bean.getRequesterId());
					//Integer[] secondlevel = processEvaluator.approvers(process_chain_dto.getApprovalChain(), 2, bean.getRequesterId());
					reqEscalation.enableEscalationPermission(processEvaluator);
					Integer[] thirdlevel = processEvaluator.handlers(process_chain_dto.getHandler(), bean.getRequesterId());
					reqEscalation.disableEscalationPermission(processEvaluator);
					Handlers[] h = new Handlers[thirdlevel.length];
					if (thirdlevel != null && thirdlevel.length > 0){
						int ii = 0;
						for (Integer single : thirdlevel){
							Handlers ob = new Handlers();
							ProfileInfo pi = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(single) })[0];
							ob.setId(single);
							ob.setName(pi.getFirstName() + " " + pi.getLastName());
							h[ii] = ob;
							ii++;
						}
					}
					bean.setHandlers(h);
					ArrayList<ReimbursementApproversDetails> list = new ArrayList<ReimbursementApproversDetails>();
					boolean showComm = true;
					for (ReimbursementReq eachreq : checkApp2){
						if (eachreq.getStatus().equals(Status.APPROVED) || eachreq.getStatus().equals(Status.REJECTED) || eachreq.getStatus().equals(Status.COMPLETED) || eachreq.getStatus().equals(Status.REVOKED)){
							if (eachreq.getAssignTo() == eachreq.getRequesterId()) continue;
							list.add(new ReimbursementApproversDetails(ModelUtiility.getInstance().getEmployeeName(eachreq.getActionTakenBy()), PortalUtility.getdd_MM_yyyy_hh_mm_a(eachreq.getActionTakenOn()), showComm ? eachreq.getRemark() : ""));
							if (eachreq.getAssignTo() == loginDto.getUserId().intValue()) showComm = false;
							if (eachreq.getStatus().equals(Status.COMPLETED) || eachreq.getOldStatus() != null) break;
						}
					}
					bean.setApproversDetails(list.toArray());
					/*ReimbursementReq rReq1[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=? AND STATUS='Request Raised'", new Object[] { dto.getEsrMapId() });
					if (rReq1.length > 0){
						bean.setRequestedOn(PortalUtility.formatDateddmmyyyyhhmmss(rReq1[0].getCreateDate()));
					}*/
					// send approver details to UI
					/*ReimbursementReq recentApprover[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE   ESR_MAP_ID=?  ORDER BY ACTION_TAKEN_ON DESC", new Object[] { dto.getEsrMapId() });
					ProfileInfo approverprofile = null;
					if (recentApprover.length > 1){
						if (recentApprover[0].getStatus().equals(Status.APPROVED) || recentApprover[0].getStatus().equals(Status.REJECTED) || recentApprover[0].getStatus().equals(Status.REVOKED) || recentApprover[0].getStatus().equals(Status.CANCELREQUEST) || recentApprover[0].getStatus().equals(Status.INPROGRESS) || recentApprover[0].getStatus().equals(Status.ASSIGNED)){
							if (recentApprover[0].getStatus().equals(Status.REVOKED)){
								if (recentApprover[0].getActionTakenBy() > 0){
									approverprofile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO  PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(recentApprover[0].getActionTakenBy()) })[0];
									bean.setApproverName(approverprofile.getFirstName() + " " + approverprofile.getLastName());
									bean.setApprovedDate(PortalUtility.formatDateddmmyyyyhhmmss(recentApprover[0].getActionTakenOn()));
								}
								bean.setRequestedOn(PortalUtility.formatDateddmmyyyyhhmmss(recentApprover[recentApprover.length - 1].getCreateDate()));
							} else{
								ReimbursementReq recentApprover2[] = null;
								if (recentApprover[0].getStatus().equals(Status.INPROGRESS) || recentApprover[0].getStatus().equals(Status.ASSIGNED) || recentApprover[0].getStatus().equals(Status.ON_HOLD)){
									String sql = "SELECT * FROM REIMBURSEMENT_REQ WHERE   ESR_MAP_ID=? AND STATUS = 'Approved' ORDER BY ACTION_TAKEN_ON DESC";
									if (Arrays.asList(firstlevel).contains(loginDto.getUserId()) && recentApprover[0].getStatus().equals(Status.ON_HOLD)){
										sql = "SELECT * FROM REIMBURSEMENT_REQ WHERE   ESR_MAP_ID=? AND STATUS = 'On Hold' ORDER BY ACTION_TAKEN_ON DESC";
									}
									recentApprover2 = reimbursementReqDao.findByDynamicSelect(sql, new Object[] { dto.getEsrMapId() });
									approverprofile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO  PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(recentApprover2[0].getActionTakenBy()) })[0];
									bean.setApprovedDate(PortalUtility.formatDateddmmyyyyhhmmss(recentApprover2[0].getActionTakenOn()));
									bean.setRequestedOn(PortalUtility.formatDateddmmyyyyhhmmss(recentApprover2[recentApprover2.length - 1].getCreateDate()));
								} else{
									approverprofile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO  PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(recentApprover[0].getActionTakenBy()) })[0];
									bean.setApprovedDate(PortalUtility.formatDateddmmyyyyhhmmss(recentApprover[0].getActionTakenOn()));
									bean.setRequestedOn(PortalUtility.formatDateddmmyyyyhhmmss(recentApprover[recentApprover.length - 1].getCreateDate()));
								}
								bean.setApproverName(approverprofile.getFirstName() + " " + approverprofile.getLastName());
							}
						} else if (recentApprover[0].getStatus().equals(Status.COMPLETED)){
							ReimbursementReq seconLevelApprover[] = reimbursementReqDao.findByDynamicWhere("STATUS = 'Approved' AND ESR_MAP_ID=? ORDER BY ACTION_TAKEN_ON DESC", new Object[] { dto.getEsrMapId() });
							approverprofile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO  PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(seconLevelApprover[0].getActionTakenBy()) })[0];
							bean.setApproverName(approverprofile.getFirstName() + " " + approverprofile.getLastName());
							bean.setApprovedDate(PortalUtility.formatDateddmmyyyyhhmmss(seconLevelApprover[0].getActionTakenOn()));
						}
					}*/
					/**
					 * set status list
					 */
					com.dikshatech.portal.dto.Status statusList[] = null;
					StatusDao statusDao = StatusDaoFactory.create();
					if (bean.getStatus().equalsIgnoreCase(Status.REQUESTRAISED)){
						statusList = statusDao.findByDynamicWhere("ID IN(?)", new Object[] { Status.getStatusId(Status.ASSIGNED) });
					} else{
						statusList = statusDao.findByDynamicWhere("ID IN(?,?,?)", new Object[] { Status.getStatusId(Status.COMPLETED), Status.getStatusId(Status.ASSIGNED), Status.getStatusId(Status.INPROGRESS) });
					}
					if (statusList != null) bean.setStatuslist(statusList);
					if(!(bean.getStatus().equalsIgnoreCase("Request saved")))
					bean.setRequestedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(dto.getCreateDate()));
					bean.setStatusId(Status.getStatusId(bean.getStatus()));
					bean.setMessageBody("");
					bean.setDepartmentId(remDeptId);
					bean.setOTHER_EMP_NAME(dto.getOTHER_EMP_NAME());
					bean.setDepartmentName(remDeptName);
					bean.setDesignation(dto.getDesignation());
					bean.setDivisionId(remDivisonId);					
					bean.setDivisionName(remDivisonName);
					bean.setCompany_ID(dto.getCompany_ID());
					bean.setRegName(dto.getRegName());
					bean.setCompanyName(dto.getCompanyName());
					bean.setOtherEmpNames((profileinfo.getFirstName()+ " " + profileinfo.getLastName()));
					bean.setPaymentMadeToEmpName((profileinfo1.getFirstName()+ " " + profileinfo1.getLastName()));
					bean.setPaymentMadeToEmpId(dto.getPaymentMadeToEmpId());
					bean.setRegionId(reg_id);
					if (dto.getPaymentMadeToEmpId()!=0){
						bean.setReimbuFlag("OTHER");
					}
					else{
						bean.setReimbuFlag("SELF");
					}
					if(dto.getOTHER_EMP_NAME()!=0){
						bean.setPaymentMadeToFlag("PAYMENT_OTHER");
					}
					else{
						bean.setPaymentMadeToFlag("PAYMENT_SELF");
					}
							
					request.setAttribute("actionForm", bean);
					}		
					
					else{
						

						if (reqData.getEsrMapId() != 0){
							dto = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ID=(SELECT MAX(ID) FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=?)", new Object[] { new Integer(reqData.getEsrMapId()) })[0];
						} else{
							dto = reimbursementReqDao.findByPrimaryKey(reqData.getId());
						}
						dto.setRemark(null);
						ReimbursementFinancialData[] fdto = rFinancialDao.findWhereEsrmapIdEquals(dto.getEsrMapId());
						ReimbursementFinancialData[] arr = new ReimbursementFinancialData[fdto.length];
						int i = 0;
						for (ReimbursementFinancialData d : fdto){
							arr[i] = ReimbursementFinancialData.getReimbursementFinancialData(d);
							i++;
						}
						if (dto.getChargeCode() > 0){
							dto.setChargeCodeName(chargeCodeDao.findByPrimaryKey(dto.getChargeCode()).getChCodeName());
						}
						ReimbursementBean bean = DtoToBeanConverter.DtoToBeanConverter(dto, arr);
						ReimbursementReq checkApp2[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=?", new Object[] { dto.getEsrMapId() });
						if (loginDto.getUserId() == dto.getAssignTo()){
							bean.setStatus(loginDto.getUserId() == dto.getAssignTo() && dto.getStatus().equals(Status.REQUESTRAISED) ? Status.REQUESTRAISED : dto.getStatus());
						} else if (loginDto.getUserId() == dto.getRequesterId()){
							bean.setStatus(bean.getStatus().equalsIgnoreCase("Request Raised") && checkApp2.length > 2 && (checkApp2[1].getStatus().equalsIgnoreCase("Approved") && checkApp2[checkApp2.length - 1].getStatus().equalsIgnoreCase("Request Raised")) ? "Pending Approval" : bean.getStatus().equalsIgnoreCase(Status.INPROGRESS) ? bean.getOldStatus().equalsIgnoreCase(Status.CANCELREQUEST) ? Status.CANCELINPROGRESS : Status.PROCESSING : bean.getStatus().equalsIgnoreCase(Status.ASSIGNED) ? bean
									.getOldStatus().equalsIgnoreCase(Status.CANCELINPROGRESS) ? Status.CANCELREQUEST : Status.PENDINGAPPROVAL : bean.getStatus());
						}
						bean.setReqid(serReqMapDao.findByPrimaryKey(dto.getEsrMapId()).getReqId());
						ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
						process_chain_dto = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=26  WHERE UR.USER_ID=?", new Object[] { new Integer(bean.getRequesterId()) })[0];
						//Integer[] firstlevel = processEvaluator.approvers(process_chain_dto.getApprovalChain(), 1, bean.getRequesterId());
						//Integer[] secondlevel = processEvaluator.approvers(process_chain_dto.getApprovalChain(), 2, bean.getRequesterId());
						reqEscalation.enableEscalationPermission(processEvaluator);
						Integer[] thirdlevel = processEvaluator.handlers(process_chain_dto.getHandler(), bean.getRequesterId());
						reqEscalation.disableEscalationPermission(processEvaluator);
						Handlers[] h = new Handlers[thirdlevel.length];
						if (thirdlevel != null && thirdlevel.length > 0){
							int ii = 0;
							for (Integer single : thirdlevel){
								Handlers ob = new Handlers();
								ProfileInfo pi = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(single) })[0];
								ob.setId(single);
								ob.setName(pi.getFirstName() + " " + pi.getLastName());
								h[ii] = ob;
								ii++;
							}
						}
						bean.setHandlers(h);
						ArrayList<ReimbursementApproversDetails> list = new ArrayList<ReimbursementApproversDetails>();
						boolean showComm = true;
						for (ReimbursementReq eachreq : checkApp2){
							if (eachreq.getStatus().equals(Status.APPROVED) || eachreq.getStatus().equals(Status.REJECTED) || eachreq.getStatus().equals(Status.COMPLETED) || eachreq.getStatus().equals(Status.REVOKED)){
								if (eachreq.getAssignTo() == eachreq.getRequesterId()) continue;
								list.add(new ReimbursementApproversDetails(ModelUtiility.getInstance().getEmployeeName(eachreq.getActionTakenBy()), PortalUtility.getdd_MM_yyyy_hh_mm_a(eachreq.getActionTakenOn()), showComm ? eachreq.getRemark() : ""));
								if (eachreq.getAssignTo() == loginDto.getUserId().intValue()) showComm = false;
								if (eachreq.getStatus().equals(Status.COMPLETED) || eachreq.getOldStatus() != null) break;
							}
						}
						bean.setApproversDetails(list.toArray());
						/*ReimbursementReq rReq1[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=? AND STATUS='Request Raised'", new Object[] { dto.getEsrMapId() });
						if (rReq1.length > 0){
							bean.setRequestedOn(PortalUtility.formatDateddmmyyyyhhmmss(rReq1[0].getCreateDate()));
						}*/
						// send approver details to UI
						/*ReimbursementReq recentApprover[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE   ESR_MAP_ID=?  ORDER BY ACTION_TAKEN_ON DESC", new Object[] { dto.getEsrMapId() });
						ProfileInfo approverprofile = null;
						if (recentApprover.length > 1){
							if (recentApprover[0].getStatus().equals(Status.APPROVED) || recentApprover[0].getStatus().equals(Status.REJECTED) || recentApprover[0].getStatus().equals(Status.REVOKED) || recentApprover[0].getStatus().equals(Status.CANCELREQUEST) || recentApprover[0].getStatus().equals(Status.INPROGRESS) || recentApprover[0].getStatus().equals(Status.ASSIGNED)){
								if (recentApprover[0].getStatus().equals(Status.REVOKED)){
									if (recentApprover[0].getActionTakenBy() > 0){
										approverprofile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO  PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(recentApprover[0].getActionTakenBy()) })[0];
										bean.setApproverName(approverprofile.getFirstName() + " " + approverprofile.getLastName());
										bean.setApprovedDate(PortalUtility.formatDateddmmyyyyhhmmss(recentApprover[0].getActionTakenOn()));
									}
									bean.setRequestedOn(PortalUtility.formatDateddmmyyyyhhmmss(recentApprover[recentApprover.length - 1].getCreateDate()));
								} else{
									ReimbursementReq recentApprover2[] = null;
									if (recentApprover[0].getStatus().equals(Status.INPROGRESS) || recentApprover[0].getStatus().equals(Status.ASSIGNED) || recentApprover[0].getStatus().equals(Status.ON_HOLD)){
										String sql = "SELECT * FROM REIMBURSEMENT_REQ WHERE   ESR_MAP_ID=? AND STATUS = 'Approved' ORDER BY ACTION_TAKEN_ON DESC";
										if (Arrays.asList(firstlevel).contains(loginDto.getUserId()) && recentApprover[0].getStatus().equals(Status.ON_HOLD)){
											sql = "SELECT * FROM REIMBURSEMENT_REQ WHERE   ESR_MAP_ID=? AND STATUS = 'On Hold' ORDER BY ACTION_TAKEN_ON DESC";
										}
										recentApprover2 = reimbursementReqDao.findByDynamicSelect(sql, new Object[] { dto.getEsrMapId() });
										approverprofile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO  PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(recentApprover2[0].getActionTakenBy()) })[0];
										bean.setApprovedDate(PortalUtility.formatDateddmmyyyyhhmmss(recentApprover2[0].getActionTakenOn()));
										bean.setRequestedOn(PortalUtility.formatDateddmmyyyyhhmmss(recentApprover2[recentApprover2.length - 1].getCreateDate()));
									} else{
										approverprofile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO  PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(recentApprover[0].getActionTakenBy()) })[0];
										bean.setApprovedDate(PortalUtility.formatDateddmmyyyyhhmmss(recentApprover[0].getActionTakenOn()));
										bean.setRequestedOn(PortalUtility.formatDateddmmyyyyhhmmss(recentApprover[recentApprover.length - 1].getCreateDate()));
									}
									bean.setApproverName(approverprofile.getFirstName() + " " + approverprofile.getLastName());
								}
							} else if (recentApprover[0].getStatus().equals(Status.COMPLETED)){
								ReimbursementReq seconLevelApprover[] = reimbursementReqDao.findByDynamicWhere("STATUS = 'Approved' AND ESR_MAP_ID=? ORDER BY ACTION_TAKEN_ON DESC", new Object[] { dto.getEsrMapId() });
								approverprofile = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO  PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(seconLevelApprover[0].getActionTakenBy()) })[0];
								bean.setApproverName(approverprofile.getFirstName() + " " + approverprofile.getLastName());
								bean.setApprovedDate(PortalUtility.formatDateddmmyyyyhhmmss(seconLevelApprover[0].getActionTakenOn()));
							}
						}*/
						/**
						 * set status list
						 */
						com.dikshatech.portal.dto.Status statusList[] = null;
						StatusDao statusDao = StatusDaoFactory.create();
						if (bean.getStatus().equalsIgnoreCase(Status.REQUESTRAISED)){
							statusList = statusDao.findByDynamicWhere("ID IN(?)", new Object[] { Status.getStatusId(Status.ASSIGNED) });
						} else{
							statusList = statusDao.findByDynamicWhere("ID IN(?,?,?)", new Object[] { Status.getStatusId(Status.COMPLETED), Status.getStatusId(Status.ASSIGNED), Status.getStatusId(Status.INPROGRESS) });
						}
						if (statusList != null) bean.setStatuslist(statusList);
						if(!(bean.getStatus().equalsIgnoreCase("Request saved")))
						bean.setRequestedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(dto.getCreateDate()));
						bean.setStatusId(Status.getStatusId(bean.getStatus()));
						bean.setMessageBody("");
						request.setAttribute("actionForm", bean);
	
					}
					
					
					break;
					
				case RECEIVEALL:
					
					DropDown dropDown = new DropDown();
					EmpSerReqMap[] empDto = serReqMapDao.findByDynamicSelect("SELECT * FROM EMP_SER_REQ_MAP WHERE REQUESTOR_ID=? AND REQ_TYPE_ID=5", new Object[] { loginDto.getUserId() });
					ReimbursementReq[] reqRFinal = null;
/*							if (empDto != null && empDto.length > 0){
						reqRFinal = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ID IN(SELECT MAX(ID) FROM REIMBURSEMENT_REQ WHERE  ESR_MAP_ID = ?) ", new Object[] { empDto[0].getId() });
					}*/
					ReimbursementReq[] fData = new ReimbursementReq[empDto.length];
					if (empDto.length > 0){
						int j = 0;
						for (EmpSerReqMap e : empDto){
							presentEid=e.getId();
							ReimbursementReq reqR=null;
							try{
						 reqR = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ID=(SELECT MAX(ID) FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=?)", new Object[] { e.getId() })[0];
							}
							catch (ArrayIndexOutOfBoundsException f){
								f.printStackTrace();
							}
							ReimbursementReq rReq[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=? ", new Object[] { e.getId() });
							ReimbursementReq checkApp[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=?", new Object[] { e.getId() });
							if (rReq.length > 0){
								if (!reqR.getStatus().equalsIgnoreCase(Status.REQUESTSAVED)){
									reqR.setRequestedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(rReq[0].getCreateDate()));
									reqR.setEsrReqId(e.getReqId());
								}
							}
							if(reqR!=null){
							if (reqR.getChargeCode() > 0){
								reqR.setChargeCodeName(chargeCodeDao.findByPrimaryKey(reqR.getChargeCode()).getChCodeName());
							}
							}						
							if(reqR!=null){
							reqR.setStatus(reqR.getStatus().equalsIgnoreCase("Request Raised") && checkApp.length > 2 && (checkApp[1].getStatus().equalsIgnoreCase("Approved") && checkApp[checkApp.length - 1].getStatus().equalsIgnoreCase("Request Raised")) ? "Pending Approval" : reqR.getStatus().equalsIgnoreCase(Status.INPROGRESS) ? reqR.getOldStatus().equalsIgnoreCase(Status.CANCELREQUEST) || reqR.getOldStatus().equalsIgnoreCase(Status.CANCELINPROGRESS) ? Status.CANCELINPROGRESS
									: Status.PROCESSING : reqR.getStatus().equalsIgnoreCase(Status.ASSIGNED) ? reqR.getOldStatus().equalsIgnoreCase(Status.CANCELREQUEST) || reqR.getOldStatus().equalsIgnoreCase(Status.CANCELINPROGRESS) ? Status.CANCELREQUEST : Status.PENDINGAPPROVAL : reqR.getStatus());
							reqR.setStatusId(Status.getStatusId(reqR.getStatus()));
							
							if (reqRFinal != null && reqRFinal.length > 0){
								if (reqRFinal[0].getStatus().equalsIgnoreCase(Status.COMPLETED) || reqRFinal[0].getStatus().equalsIgnoreCase(Status.REVOKED)){
									if (reqRFinal[0].getActionTakenBy() > 0){
										reqR.setHandlerId(String.valueOf(reqRFinal[0].getActionTakenBy()));
										reqR.setDateOfCompletion(PortalUtility.getdd_MM_yyyy_hh_mm_a(reqRFinal[0].getActionTakenOn()));
										reqR.setHandlerName(profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(reqRFinal[0].getActionTakenBy()) })[0].getFirstName());
									}
								}
							}
							reqR.setMessageBody("");
							fData[j] = reqR;
							j++;
						}
						}
						//					}
					}
					ReimbursementReq reimbursimentDto = new ReimbursementReq();
					reimbursimentDto.setReimbursementReq(fData);
					reimbursimentDto.setToApprove(isApprover(loginDto.getUserId()));
					reimbursimentDto.setToHandle(isHandler(loginDto.getUserId()));
					dropDown.setDetail(reimbursimentDto);
					request.setAttribute("actionForm", dropDown);
					break;
					
				case RECEIVENEW:
	
						
					
					DropDown dropDown1 = new DropDown();
					String esrMapIds = getEsrMapIdsBasedOnSearchCriteria(reqData);
					ReimbursementReq[] reqR = null;
					//if(esrMapIds!=null){
					reqR = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID IN(" + esrMapIds + ") AND ASSIGN_TO = ? ", new Object[] { loginDto.getUserId() });
					//}else{
					//	reqR = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ID IN(SELECT MAX(ID) FROM REIMBURSEMENT_REQ WHERE ASSIGN_TO = ? GROUP BY ESR_MAP_ID) ",
					//			new Object[]{ loginDto.getUserId()});
					//}			
					ReimbursementReq[] reqRFinaln = null;
					ReimbursementReq[] reqarr = new ReimbursementReq[reqR.length];
					int k = 0,
					count = 0;
					for (ReimbursementReq r : reqR){
						if (!(r.getAssignTo() == r.getRequesterId())){
							if (reqR != null && reqR.length > 0){
								reqRFinaln = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ID IN(SELECT MAX(ID) FROM REIMBURSEMENT_REQ WHERE  ESR_MAP_ID = ?) ", new Object[] { r.getEsrMapId() });
							}
							//					if (r.getAssignTo() == loginDto.getUserId()&& !r.getStatus().equals(Status.REVOKED)
							//							&& (r.getStatus().equals(Status.REQUESTRAISED) || r.getStatus().equals(Status.ON_HOLD) || r
							//									.getStatus().equals(Status.NEW)))
							if (r.getAssignTo() == loginDto.getUserId()){
								
								r.setRequestedBy(profileInfoDao.findByDynamicSelect1("SELECT CONCAT(FIRST_NAME, ' ', LAST_NAME) as EMPLOYEE_NAME FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(r.getRequesterId()) })[0].getEmployeeName());
								
								r.setEmpId(uDao.findByDynamicSelect1("SELECT EMP_ID FROM USERS U join REIMBURSEMENT_REQ RR ON  U.ID = RR.REQUESTER_ID  WHERE U.ID = ? ",new Object[] { new Integer(r.getRequesterId()) })[0].getEmpId());
								
								//r.setRequestedBy(profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(r.getRequesterId()) })[0].getFirstName());
								Divison div = divisionDao.findByDynamicSelect("SELECT * FROM DIVISON D LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN USERS U ON U.LEVEL_ID=L.ID WHERE U.ID=?", new Object[] { new Integer(r.getRequesterId()) })[0];
								if (div.getParentId() > 0){
									r.setRequesterDiv(div.getName());
									r.setRequesterDept(divisionDao.findByPrimaryKey(div.getParentId()).getName());
								} else{
									// if No div available for user
									r.setRequesterDept(div.getName());
								}
								if (r.getChargeCode() > 0){
									r.setChargeCodeName(chargeCodeDao.findByPrimaryKey(r.getChargeCode()).getChCodeName());
								}
								r.setRequestedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(r.getCreateDate()));
								r.setEsrReqId(serReqMapDao.findByPrimaryKey(r.getEsrMapId()).getReqId());
								r.setStatusId(Status.getStatusId(r.getStatus()));
								// r.setStatus(Status.NEW);
								if (reqRFinaln != null && reqRFinaln.length > 0){
									if (reqRFinaln[0].getStatus().equalsIgnoreCase(Status.COMPLETED) || reqRFinaln[0].getStatus().equalsIgnoreCase(Status.REVOKED)){
										if (reqRFinaln[0].getActionTakenBy() > 0){
											r.setHandlerId(String.valueOf(reqRFinaln[0].getActionTakenBy()));
											r.setDateOfCompletion(PortalUtility.getdd_MM_yyyy_hh_mm_a(reqRFinaln[0].getActionTakenOn()));
											if (!reqRFinaln[0].getStatus().equalsIgnoreCase(Status.REVOKED)) r.setHandlerName(profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(reqRFinaln[0].getActionTakenBy()) })[0].getFirstName());
										}
									}
								}
								if (r.getStatus().equalsIgnoreCase(Status.REQUESTRAISED) || r.getStatus().equalsIgnoreCase(Status.ON_HOLD)){
									count++;
								}
								r.setMessageBody("");
								reqarr[k] = r;
								k++;
							}
						}
					}
					dropDown1.setKey1(count);
					dropDown1.setDropDown(reqarr);
					request.setAttribute("actionForm", dropDown1);
					
					break;
					
				case HANDLER:
					DropDown dropDown2 = new DropDown();
					String esrMapIdsForsearchCriteria = getEsrMapIdsBasedOnSearchCriteria(reqData);
					ReimbursementReq[] reqR1 = null;
					reqR1 = reimbursementReqDao.findByDynamicQuery("SELECT * FROM REIMBURSEMENT_REQ  WHERE STATUS IN ('Request Raised','Cancel Request','Revoked','Completed','In-Progress','Assigned','Cancel In-Progress') AND  ASSIGN_TO=" + loginDto.getUserId() + " AND ESR_MAP_ID IN(" + esrMapIdsForsearchCriteria + ")");
					ReimbursementReq[] reqRFinalh = null;
					ReimbursementReq[] reqarr1 = new ReimbursementReq[reqR1.length];
					int k1 = 0,
					count1 = 0;
					for (ReimbursementReq r : reqR1){
						
							if (!(r.getAssignTo() == r.getRequesterId())){
						/*	
	
									if (reqR1 != null && reqR1.length > 0){
										
								reqRFinalh = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ID IN(SELECT MAX(ID) FROM REIMBURSEMENT_REQ WHERE  ESR_MAP_ID = ?) ", new Object[] { r.getEsrMapId() });
					
									}
						*/
							r.setRequestedBy(profileInfoDao.findByDynamicSelect1("SELECT CONCAT(FIRST_NAME, ' ', LAST_NAME) as EMPLOYEE_NAME FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(r.getRequesterId()) })[0].getEmployeeName());
							r.setEmpId(uDao.findByDynamicSelect1("SELECT DISTINCT EMP_ID FROM USERS U join REIMBURSEMENT_REQ RR ON  U.ID = RR.REQUESTER_ID  WHERE U.ID = ? ",new Object[] { new Integer(r.getRequesterId()) })[0].getEmpId());
							
							Divison div = divisionDao.findByDynamicSelect("SELECT * FROM DIVISON D LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN USERS U ON U.LEVEL_ID=L.ID WHERE U.ID=?", new Object[] { new Integer(r.getRequesterId()) })[0];
							if (div.getParentId() > 0){
								r.setRequesterDiv(div.getName());
								r.setRequesterDept(divisionDao.findByPrimaryKey(div.getParentId()).getName());
							
							} else
							{
							
								r.setRequesterDept(div.getName());
								
							}
							if (r.getChargeCode() > 0){
								r.setChargeCodeName(chargeCodeDao.findByPrimaryKey(r.getChargeCode()).getChCodeName());
							}
							r.setRequestedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(r.getCreateDate()));
							r.setEsrReqId(serReqMapDao.findByPrimaryKey(r.getEsrMapId()).getReqId());
							if (r.getStatus().equals(Status.REQUESTRAISED) || r.getStatus().equals(Status.CANCELREQUEST) || r.getStatus().equalsIgnoreCase(Status.CANCELINPROGRESS) || r.getStatus().equalsIgnoreCase(Status.INPROGRESS) || r.getStatus().equalsIgnoreCase(Status.ASSIGNED)){
								if (r.getStatus().equals(Status.CANCELREQUEST) || r.getStatus().equalsIgnoreCase(Status.CANCELINPROGRESS) || r.getStatus().equalsIgnoreCase(Status.INPROGRESS) || r.getStatus().equalsIgnoreCase(Status.ASSIGNED)){
									if (!(r.getActive() == (short) 1)){
										count1++;
										
									}
								} else{
									count1++;
							
								}
							}
							r.setStatusId(Status.getStatusId(r.getStatus()));
							if (r.getStatus().equalsIgnoreCase(Status.ASSIGNED) || (r.getStatus().equalsIgnoreCase(Status.CANCELREQUEST) && r.getOldStatus().equalsIgnoreCase(Status.CANCELREQUEST))){
								ReimbursementReq assignedTo[] = reimbursementReqDao.findByDynamicWhere("ESR_MAP_ID=? AND ACTIVE IS NULL AND STATUS IN ('" + Status.ASSIGNED + "' ,'" + Status.CANCELREQUEST + "')", new Object[] { new Integer(r.getEsrMapId()) });
							
							ProfileInfo assignedToProfile[] = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(assignedTo[0].getAssignTo()) });
							r.setStatus("Docked By " + assignedToProfile[0].getFirstName() + " " + assignedToProfile[0].getLastName());
								}
						
							if (reqRFinalh != null && reqRFinalh.length > 0){
								
								if (reqRFinalh[0].getStatus().equalsIgnoreCase(Status.COMPLETED) || reqRFinalh[0].getStatus().equalsIgnoreCase(Status.REVOKED)){
									if (reqRFinalh[0].getActionTakenBy() > 0){
										
										r.setHandlerId(String.valueOf(reqRFinalh[0].getActionTakenBy()));
										r.setDateOfCompletion(PortalUtility.getdd_MM_yyyy_hh_mm_a(reqRFinalh[0].getActionTakenOn()));
										r.setHandlerName(profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(reqRFinalh[0].getActionTakenBy()) })[0].getFirstName());
									}
								}
							}
							r.setMessageBody("");
							reqarr1[k1] = r;
							
							k1++;
	
						}
					
			}
					dropDown2.setKey1(count1);
					dropDown2.setDropDown(reqarr1);
					request.setAttribute("actionForm", dropDown2);
					break;
				case RECEIVEREPORT:{
					ReimbursementReq[] reqRFinalh2 = null;
					try
					{
						if(flag1!=null)
						{
							if(flag1.equals("HDFC"))
							{
				               DropDown dropDown3 = new DropDown();
				               ReimbursementReq[] reqR3 = null;
					
							ReimbursementReqDao reimbuReportDao = ReimbursementReqDaoFactory.create(conn);
					//		reqR3 = reimbuReportDao.findByDynamicSelect1("SELECT RR . *,U.EMP_ID, CONCAT(FIRST_NAME, ' ', LAST_NAME) as Name,P.FIRST_NAME,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO  END) AS ACC_NO, (CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME  ELSE F.PRIM_BANK_NAME END) AS BANK_NAME,RR.ACTION_TAKEN_BY,EP.REQ_ID FROM REIMBURSEMENT_REQ RR JOIN USERS U ON  RR.REQUESTER_ID = U.ID JOIN EMP_SER_REQ_MAP EP ON  EP.ID = RR.ESR_MAP_ID JOIN  PROFILE_INFO P ON P.ID = U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID WHERE  F.PRIM_BANK_NAME  LIKE '%HDFC%' AND  RR.STATUS  IN ('Request Raised' , 'Cancel Request','Revoked','Completed', 'In-Progress','Assigned','Cancel In-Progress') AND  ASSIGN_TO=" + loginDto.getUserId() + " AND ESR_MAP_ID IN(" + esrMapIdsForsearchCriteria1 + ") AND (RR.PAID IS null || RR.PAID = '') ORDER BY ESR_MAP_ID");
							reqR3 = reimbuReportDao.findByDynamicSelect1("SELECT RR . *,U.EMP_ID, CONCAT(FIRST_NAME, ' ', LAST_NAME) as Name,P.FIRST_NAME,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO  END) AS ACC_NO, (CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME  ELSE F.PRIM_BANK_NAME END) AS BANK_NAME,RR.ACTION_TAKEN_BY,EP.REQ_ID FROM REIMBURSEMENT_REQ RR JOIN USERS U ON  RR.REQUESTER_ID = U.ID JOIN EMP_SER_REQ_MAP EP ON  EP.ID = RR.ESR_MAP_ID JOIN  PROFILE_INFO P ON P.ID = U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID WHERE  F.PRIM_BANK_NAME  LIKE '%HDFC%' AND  RR.STATUS  IN ('Completed') AND  ASSIGN_TO=" + loginDto.getUserId() + " AND ESR_MAP_ID IN(" + esrMapIdsForsearchCriteria1 + ") AND (RR.PAID IS null || RR.PAID = '') ORDER BY ESR_MAP_ID");
							
							for(ReimbursementReq report12 : reqR3){
								if(report12.getActionTakenBy()>0){
									
								report12.setHandlerName(profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(report12.getActionTakenBy()) })[0].getFirstName());
								report12.setEsrReqId(serReqMapDao.findByPrimaryKey(report12.getEsrMapId()).getReqId());
								Divison div = divisionDao.findByDynamicSelect("SELECT * FROM DIVISON D LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN USERS U ON U.LEVEL_ID=L.ID WHERE U.ID=?", new Object[] { new Integer(report12.getRequesterId()) })[0];
								if (div.getParentId() > 0){
									report12.setRequesterDiv(div.getName());
									report12.setRequesterDept(divisionDao.findByPrimaryKey(div.getParentId()).getName());
								} else
								{
									
									report12.setRequesterDept(div.getName());
								}
								if (report12.getChargeCode() > 0){
									report12.setChargeCodeName(chargeCodeDao.findByPrimaryKey(report12.getChargeCode()).getChCodeName());
								}
								
							}
							}
							
							List<Object> BankList=new ArrayList<Object>();
							
							if(reqR3!=null && reqR3.length >0 ){
								for(ReimbursementReq reporta : reqR3){
								
									
									if(reporta.getPaid()==null){
										reporta.setPaid("unPaid");	
									}
											if(reporta.getRequesterId()!=loginDto.getUserId())
											{
												BankList.add(reporta);
											}
								}
							}
										
							
							reimbuForm.setListHdfc(BankList.toArray(new ReimbursementReq[BankList.size()]));
							
							request.setAttribute("actionForm",reimbuForm);
						
						}
						
						else
						{	ReimbursementReq[] reqRFinalh1 = null;
							ReimbursementReq[] reqR3 = null;
							ReimbursementReqDao reimbuReportDao = ReimbursementReqDaoFactory.create(conn);
							
							reqR3 = reimbuReportDao.findByDynamicSelect2("SELECT RR . *,U.EMP_ID, CONCAT(FIRST_NAME, ' ', LAST_NAME) as Name,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO  END) AS ACC_NO, (CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME  ELSE F.PRIM_BANK_NAME END) AS BANK_NAME ,RR.ACTION_TAKEN_BY , EP.REQ_ID FROM REIMBURSEMENT_REQ RR JOIN USERS U ON  RR.REQUESTER_ID = U.ID  JOIN EMP_SER_REQ_MAP EP ON  EP.ID = RR.ESR_MAP_ID JOIN  PROFILE_INFO P ON P.ID = U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID WHERE  F.PRIM_BANK_NAME NOT LIKE '%HDFC%' AND  RR.STATUS  IN ('Completed') AND  ASSIGN_TO=" + loginDto.getUserId() + " AND ESR_MAP_ID IN(" + esrMapIdsForsearchCriteria1 + ") AND (RR.PAID IS null || RR.PAID = '') ORDER BY ESR_MAP_ID");	
						//	reqR3 = reimbuReportDao.findByDynamicSelect2("SELECT RR . *,U.EMP_ID, CONCAT(FIRST_NAME, ' ', LAST_NAME) as Name,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO  END) AS ACC_NO, (CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME  ELSE F.PRIM_BANK_NAME END) AS BANK_NAME ,RR.ACTION_TAKEN_BY , EP.REQ_ID FROM REIMBURSEMENT_REQ RR JOIN USERS U ON  RR.REQUESTER_ID = U.ID  JOIN EMP_SER_REQ_MAP EP ON  EP.ID = RR.ESR_MAP_ID JOIN  PROFILE_INFO P ON P.ID = U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID WHERE  F.PRIM_BANK_NAME NOT LIKE '%HDFC%' AND  RR.STATUS  IN ('Request Raised' , 'Cancel Request','Revoked','Completed', 'In-Progress','Assigned','Cancel In-Progress') AND  ASSIGN_TO=" + loginDto.getUserId() + " AND ESR_MAP_ID IN(" + esrMapIdsForsearchCriteria1 + ") AND (RR.PAID IS null || RR.PAID = '') ORDER BY ESR_MAP_ID");
							for(ReimbursementReq report12 : reqR3){
								if(report12.getActionTakenBy()>0){
								
								report12.setHandlerName(profileInfoDao.findByDynamicSelect("SELECT  * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(report12.getActionTakenBy()) })[0].getFirstName());
								report12.setEsrReqId(serReqMapDao.findByPrimaryKey(report12.getEsrMapId()).getReqId());
								Divison div = divisionDao.findByDynamicSelect("SELECT * FROM DIVISON D LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN USERS U ON U.LEVEL_ID=L.ID WHERE U.ID=?", new Object[] { new Integer(report12.getRequesterId()) })[0];
								if (div.getParentId() > 0){
									report12.setRequesterDiv(div.getName());
									report12.setRequesterDept(divisionDao.findByPrimaryKey(div.getParentId()).getName());
								} else
								{
									
									report12.setRequesterDept(div.getName());
								}
								if (report12.getChargeCode() > 0){
									report12.setChargeCodeName(chargeCodeDao.findByPrimaryKey(report12.getChargeCode()).getChCodeName());
								}}
							}
						
							List<Object> BankList=new ArrayList<Object>();
							
							if(reqR3!=null && reqR3.length >0 ){
								for(ReimbursementReq reportb : reqR3){
								
									if(reportb.getPaid()==null){
									
										reportb.setPaid("unPaid");
									}																						
										if(reportb.getRequesterId()!=loginDto.getUserId())
										{
											BankList.add(reportb);
										}
		
								
								reimbuForm.setListNonHdfc(BankList.toArray(new ReimbursementReq[BankList.size()]));
							 
							request.setAttribute("actionForm",reimbuForm);
							}
							
						}
						
						return result;
						
					
					}
						}}
					catch (Exception e){
						e.printStackTrace();
						logger.error("REIMBURSEMENT RECEIVEREPORT : failed to fetch data", e);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
					
					
					}
					
					}
					break;
				
			   case RECEIVEALLPAIDANDUNPAID :{
				   
				   ReimbursementReq[] reqRFinalh2 = null;
					try
					{
						if(flag1!=null)
						{
							if(flag1.equals("HDFC"))
							{
				              DropDown dropDown3 = new DropDown();
					          ReimbursementReq[] reqR4 = null;
					          ReimbursementReqDao reimbuReportDao = ReimbursementReqDaoFactory.create(conn);
							//	reqR4 = reimbuReportDao.findPayDetailsHdfc("SELECT RR . *,U.EMP_ID, CONCAT(FIRST_NAME, ' ', LAST_NAME) as Name,P.FIRST_NAME,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO  END) AS ACC_NO, (CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME  ELSE F.PRIM_BANK_NAME END) AS BANK_NAME,RR.ACTION_TAKEN_BY,EP.REQ_ID FROM REIMBURSEMENT_REQ RR JOIN USERS U ON  RR.REQUESTER_ID = U.ID JOIN EMP_SER_REQ_MAP EP ON  EP.ID = RR.ESR_MAP_ID JOIN  PROFILE_INFO P ON P.ID = U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID WHERE  F.PRIM_BANK_NAME  LIKE '%HDFC%' AND  RR.STATUS  IN ('Request Raised' , 'Cancel Request','Revoked','Completed', 'In-Progress','Assigned','Cancel In-Progress') AND  ASSIGN_TO=" + loginDto.getUserId() + " AND ESR_MAP_ID IN(" + esrMapIdsForsearchCriteria1 + ")  ORDER BY ESR_MAP_ID");
							reqR4 = reimbuReportDao.findPayDetailsHdfc("SELECT RR . *,U.EMP_ID, CONCAT(FIRST_NAME, ' ', LAST_NAME) as Name,P.FIRST_NAME,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO  END) AS ACC_NO, (CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME  ELSE F.PRIM_BANK_NAME END) AS BANK_NAME,RR.ACTION_TAKEN_BY,EP.REQ_ID FROM REIMBURSEMENT_REQ RR JOIN USERS U ON  RR.REQUESTER_ID = U.ID JOIN EMP_SER_REQ_MAP EP ON  EP.ID = RR.ESR_MAP_ID JOIN  PROFILE_INFO P ON P.ID = U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID WHERE  F.PRIM_BANK_NAME  LIKE '%HDFC%' AND  RR.STATUS  IN ('Completed') AND  ASSIGN_TO=" + loginDto.getUserId() + " AND ESR_MAP_ID IN(" + esrMapIdsForsearchCriteria1 + ")  ORDER BY ESR_MAP_ID");
							
							for(ReimbursementReq report12 : reqR4){
								if(report12.getActionTakenBy()>0){
									
								report12.setHandlerName(profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(report12.getActionTakenBy()) })[0].getFirstName());
								report12.setEsrReqId(serReqMapDao.findByPrimaryKey(report12.getEsrMapId()).getReqId());
								Divison div = divisionDao.findByDynamicSelect("SELECT * FROM DIVISON D LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN USERS U ON U.LEVEL_ID=L.ID WHERE U.ID=?", new Object[] { new Integer(report12.getRequesterId()) })[0];
								if (div.getParentId() > 0){
									report12.setRequesterDiv(div.getName());
									report12.setRequesterDept(divisionDao.findByPrimaryKey(div.getParentId()).getName());
								} else
								{
									
									report12.setRequesterDept(div.getName());
								}
								if (report12.getChargeCode() > 0){
									report12.setChargeCodeName(chargeCodeDao.findByPrimaryKey(report12.getChargeCode()).getChCodeName());
								}
								
								
							}
							}
							
							List<Object> BankList=new ArrayList<Object>();
							
							if(reqR4!=null && reqR4.length >0 ){
								for(ReimbursementReq reportc : reqR4){
									
								
									
									if(reportc.getPaid()==null){
										reportc.setPaid("unPaid");	
									}
									if(reportc.getRequesterId()!=loginDto.getUserId())
									{
									BankList.add(reportc);
										
										}	
								}}
							reimbuForm.setListHdfc(BankList.toArray(new ReimbursementReq[BankList.size()]));
							
							request.setAttribute("actionForm",reimbuForm);
						
						}
							
							
							
							
						
						else
						{	ReimbursementReq[] reqRFinalh1 = null;
							ReimbursementReq[] reqR5 = null;
							ReimbursementReqDao reimbuReportDao = ReimbursementReqDaoFactory.create(conn);
							
							reqR5 = reimbuReportDao.findPayDetailsNonHdfc("SELECT RR . *,U.EMP_ID, CONCAT(FIRST_NAME, ' ', LAST_NAME) as Name,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO  END) AS ACC_NO, (CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME  ELSE F.PRIM_BANK_NAME END) AS BANK_NAME ,RR.ACTION_TAKEN_BY , EP.REQ_ID FROM REIMBURSEMENT_REQ RR JOIN USERS U ON  RR.REQUESTER_ID = U.ID  JOIN EMP_SER_REQ_MAP EP ON  EP.ID = RR.ESR_MAP_ID JOIN  PROFILE_INFO P ON P.ID = U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID WHERE  F.PRIM_BANK_NAME NOT LIKE '%HDFC%' AND  RR.STATUS  IN ('Completed') AND  ASSIGN_TO=" + loginDto.getUserId() + " AND ESR_MAP_ID IN(" + esrMapIdsForsearchCriteria1 + ") ORDER BY ESR_MAP_ID");
							
							
						//	reqR5 = reimbuReportDao.findPayDetailsNonHdfc("SELECT RR . *,U.EMP_ID, CONCAT(FIRST_NAME, ' ', LAST_NAME) as Name,(CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_ACC_NO ELSE F.PRIM_BANK_ACC_NO  END) AS ACC_NO, (CASE WHEN F.PRIM_BANK_ACC_NO IS NULL THEN F.SEC_BANK_NAME  ELSE F.PRIM_BANK_NAME END) AS BANK_NAME ,RR.ACTION_TAKEN_BY , EP.REQ_ID FROM REIMBURSEMENT_REQ RR JOIN USERS U ON  RR.REQUESTER_ID = U.ID  JOIN EMP_SER_REQ_MAP EP ON  EP.ID = RR.ESR_MAP_ID JOIN  PROFILE_INFO P ON P.ID = U.PROFILE_ID LEFT JOIN FINANCE_INFO F ON F.ID = U.FINANCE_ID WHERE  F.PRIM_BANK_NAME NOT LIKE '%HDFC%' AND  RR.STATUS  IN ('Request Raised' , 'Cancel Request','Revoked','Completed', 'In-Progress','Assigned','Cancel In-Progress') AND  ASSIGN_TO=" + loginDto.getUserId() + " AND ESR_MAP_ID IN(" + esrMapIdsForsearchCriteria1 + ") ORDER BY ESR_MAP_ID");
							for(ReimbursementReq report12 : reqR5){
								if(report12.getActionTakenBy()>0){
								
								report12.setHandlerName(profileInfoDao.findByDynamicSelect("SELECT  * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(report12.getActionTakenBy()) })[0].getFirstName());
								report12.setEsrReqId(serReqMapDao.findByPrimaryKey(report12.getEsrMapId()).getReqId());
								Divison div = divisionDao.findByDynamicSelect("SELECT * FROM DIVISON D LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN USERS U ON U.LEVEL_ID=L.ID WHERE U.ID=?", new Object[] { new Integer(report12.getRequesterId()) })[0];
								if (div.getParentId() > 0){
									report12.setRequesterDiv(div.getName());
									report12.setRequesterDept(divisionDao.findByPrimaryKey(div.getParentId()).getName());
								} else
								{
									
									report12.setRequesterDept(div.getName());
								}
								if (report12.getChargeCode() > 0){
									report12.setChargeCodeName(chargeCodeDao.findByPrimaryKey(report12.getChargeCode()).getChCodeName());
								}
								
								
							}
							}
							
							
							List<Object> BankList=new ArrayList<Object>();
							
							if(reqR5!=null && reqR5.length >0 ){
								for(ReimbursementReq reportd : reqR5){
								
	
									if(reportd.getPaid()==null){
									
										reportd.setPaid("unPaid");	 
									}
									
										if(reportd.getRequesterId()!=loginDto.getUserId())
										{
										BankList.add(reportd);
											
											}
										
									
								}
								reimbuForm.setListNonHdfc(BankList.toArray(new ReimbursementReq[BankList.size()]));
							 
							request.setAttribute("actionForm",reimbuForm);
							}
							
						}
						
						return result;
						
					}
					}
					catch (Exception e){
						e.printStackTrace();
						logger.error("REIMBURSEMENT RECEIVEREPORT : failed to fetch data", e);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
					
					
					}
			    }
                    break;
			
			    
			   
			    case RECEIVEPAY:{
			    	
			    	try{
			    		String updateReceivePay=null;
                         double total = 0;
	                     List<ReimbursementReq> list =new ArrayList<ReimbursementReq>();
	                     String bbrId=(reimbuForm.getRemesrMapId());
	                     String[] str=bbrId.split(",");
	                     
	                     ArrayList<Integer> bbr_Id=new ArrayList<Integer>();
	                     for(String w:str){  
	                         Integer x = Integer.valueOf(w);
	                         bbr_Id.add(x);
	                     }
	                     if(flag1!=null){
	                         
	                     updateReceivePay=ReimbursementReqDaoFactory.create(conn).updateAllReceivedPay( bbr_Id, flag1);
	                         
	                     }
	                     request.setAttribute("actionForm", updateReceivePay);
	                  
			    	}
	                   
			    	catch (Exception ex){
                        logger.error("REIMBURSEMENT RECEIVE : failed to fetch data", ex);
                        result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("deputation.receive.failed"));
                        return result;
                    }
	  	
			    }
			    break;
				
				case RECEIVETYPE:
					DropDown reimbursementType = new DropDown();
					ReimbursementTypeDao rTypeDao = ReimbursementTypeDaoFactory.create();
					reimbursementType.setDropDown(rTypeDao.findAll());
					request.setAttribute("actionForm", reimbursementType);
					break;
				case RECEIVECURRENCY:
					DropDown currencyType = new DropDown();
					CurrencyTypeDao cTypeDao = CurrencyTypeDaoFactory.create();
					currencyType.setDropDown(cTypeDao.findAll());
				/*	CurrencyDao currencyDao = CurrencyDaoFactory.create();
					Currency currency[] = currencyDao.findAll();
					currencyType.setDropDown(currency);*/
					request.setAttribute("actionForm", currencyType);
					break;
			}
		} catch (ArrayIndexOutOfBoundsException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.arrayoutofbound"));
			e.printStackTrace();
			logger.error("Exception in ESR-MAP-ID="+ presentEid);
		} catch (EmpSerReqMapDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (ReimbursementReqDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (ReimbursementFinancialDataDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (ProfileInfoDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.profileInfo"));
			e.printStackTrace();
		} catch (ReimbursementTypeDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (CurrencyTypeDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (Exception e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
			logger.error("Exception in ESR-MAP-ID="+ presentEid);
		}
		
		
		
		
		// TODO Auto-generated method stub
		return result;
	}

	private String getEsrMapIdsBasedOnSearchCriteria(ReimbursementRequestForm reqData) throws EmpSerReqMapDaoException {
		EmpSerReqMap[] empSerReqDetails = null;
		EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
		StringBuffer query = new StringBuffer("SELECT * FROM EMP_SER_REQ_MAP WHERE REQ_TYPE_ID=5 ");
		if (reqData.getMonth() != null || reqData.getSearchyear() != null || reqData.getSearchName() != null){
			if (reqData.getMonth() != null && reqData.getToMonth() != null) query.append(" AND (MONTH(SUB_DATE) BETWEEN " + reqData.getMonth() + " AND " + reqData.getToMonth() + ") ");
			else if (reqData.getMonth() != null) query.append(" AND MONTH(SUB_DATE)=" + reqData.getMonth() + " ");
			if (reqData.getSearchyear() != null) query.append(" AND YEAR(SUB_DATE)=" + reqData.getSearchyear() + " ");
			if (reqData.getSearchName() != null) query.append(" AND REQUESTOR_ID IN (SELECT ID FROM USERS U WHERE U.STATUS NOT IN ( 1, 2, 3 ) AND PROFILE_ID IN (SELECT ID FROM PROFILE_INFO WHERE (FIRST_NAME IS NOT NULL AND FIRST_NAME LIKE '%" + reqData.getSearchName() + "%') OR (LAST_NAME IS NOT NULL AND LAST_NAME LIKE '%" + reqData.getSearchName() + "%'))) ");
		} else{//default case
			query.append(" AND TIMESTAMPDIFF(DAY,SUB_DATE,NOW()) <= 30" + " ");
		}
		empSerReqDetails = empSerReqDao.findByDynamicSelect(query.toString(), null);
		List<Integer> esrMapIdList = null;
		String idList = null;
		if (empSerReqDetails != null && empSerReqDetails.length > 0){
			esrMapIdList = new ArrayList<Integer>(empSerReqDetails.length);
			for (EmpSerReqMap tempEsrRow : empSerReqDetails){
				esrMapIdList.add(tempEsrRow.getId());
			}
			idList = esrMapIdList.toString().replace('[', ' ').replace(']', ' ').trim();
		}
		return idList == null ? "0" : idList;
	}

	private boolean isApprover(Integer userId) {
		ReimbursementReqDao rDao = ReimbursementReqDaoFactory.create();
		boolean result=false;
		try{
			String sql = "ASSIGN_TO = ? AND STATUS IN('Request Raised','On Hold','Revoked','Approved','Rejected')";
			Object[] sqlParams = { userId };
			ReimbursementReq[] lReqs = rDao.findByDynamicWhere(sql, sqlParams);
			/*if (lReqs != null && lReqs.length > 0)
			{
				result=true;
			}*/
			//else return false;
			//	boolean isCorrect = false;
			if (lReqs != null && lReqs.length > 0){
				EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
				EmpSerReqMap empSerReqMap = empSerReqMapDao.findByPrimaryKey(lReqs[0].getEsrMapId());
				ProcessChain processChain = ProcessChainDaoFactory.create().findByPrimaryKey(empSerReqMap.getProcessChainId());
				ProcessEvaluator evaluator = new ProcessEvaluator();
				if (reqEscalation.isEscalationAction()){
					reqEscalation.setEscalationProcess(evaluator);
					//Evaluator is local so no need to disableEscalationPermission
					reqEscalation.enableEscalationPermission(evaluator);
				}
				evaluator.findLastAppLevel(empSerReqMap);
				String approvalChain = processChain.getApprovalChain();
				String apps[] = approvalChain.split(";");
				ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
				UsersDao usersDao = UsersDaoFactory.create();
				Users requestor = usersDao.findByPrimaryKey(empSerReqMap.getRequestorId());
				ProfileInfo requestorProfile = profileInfoDao.findByPrimaryKey(requestor.getProfileId());
				if (apps != null && apps.length > 0){
					for (int i = 0; i < apps.length; i++){
						Integer singlearray[] = evaluator.approvers(approvalChain, i + 1, empSerReqMap.getRequestorId());
						for (Integer singlelevel : singlearray){
							if (requestorProfile.getReportingMgr() == userId || requestorProfile.getHrSpoc() == userId || userId == singlelevel){
								result = true;
							}
						}
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	private boolean isHandler(Integer userId) {
		ReimbursementReqDao rDao = ReimbursementReqDaoFactory.create();
		boolean result = false;
		//boolean isCorrect = false;
		try{
			String sql = "ASSIGN_TO = ? AND  STATUS IN('Request Raised','Revoked' ,'Rejected','Cancel Request','Completed','In-Progress','Assigned','Cancel In-Progress')";
			Object[] sqlParams = { userId };
			ReimbursementReq[] lReqs = rDao.findByDynamicWhere(sql, sqlParams);
			if (lReqs.length > 0){
				EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
				EmpSerReqMap empSerReqMap = empSerReqMapDao.findByPrimaryKey(lReqs[0].getEsrMapId());
				ProcessChain processChain = ProcessChainDaoFactory.create().findByPrimaryKey(empSerReqMap.getProcessChainId());
				ProcessEvaluator evaluator = new ProcessEvaluator();
				//				evaluator.findLastAppLevel(empSerReqMap);
				if (reqEscalation.isEscalationAction()){
					reqEscalation.setEscalationProcess(evaluator);
					//Evaluator is local so no need to disableEscalationPermission
					reqEscalation.enableEscalationPermission(evaluator);
				}
				String handlerChain = processChain.getHandler();
				Integer apps[] = evaluator.handlers(handlerChain, empSerReqMap.getRequestorId());
				//ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
				//UsersDao usersDao = UsersDaoFactory.create();
				//Users requestor = usersDao.findByPrimaryKey(empSerReqMap.getRequestorId());
				//ProfileInfo requestorProfile = profileInfoDao.findByPrimaryKey(requestor.getProfileId());
				if (apps != null && apps.length > 0){
					for (Integer singlelevel : apps){
						if (userId.intValue() == singlelevel.intValue()){
							result = true;
						}
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		ReimbursementRequestForm reimbursementData = (ReimbursementRequestForm) form;
		ProcessChain process_chain_dto = new ProcessChain();
		UsersDao usersDao = UsersDaoFactory.create();
		EmpSerReqMapPk reqpk = new EmpSerReqMapPk();
		EmpSerReqMap empReqMapDto = new EmpSerReqMap();
		ReimbursementReqPk reimbursementpk = new ReimbursementReqPk();
		ReimbursementReqDao reimbursementReqDao = ReimbursementReqDaoFactory.create();
		ReimbursementFinancialDataDao reimbursementFinancialDataDao = ReimbursementFinancialDataDaoFactory.create();
		RegionsDao regionDao = RegionsDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		EmpSerReqMapDao emp_Ser_Req_Map_Dao = EmpSerReqMapDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		ModulePermissionDao modulePermissionDao = ModulePermissionDaoFactory.create();
		Date date = new Date();
		ProcessEvaluator evaluator = new ProcessEvaluator();
		Users userdata = new Users();
		ProfileInfo profileInfo = new ProfileInfo();
		MailGenerator mailGenarator = new MailGenerator();
		//SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy [HH:mm a]");
		InboxModel inboxModel = new InboxModel();
		ReimbursementReq reimbursementDtoData = null;
		// check user below l5 level
		try{
			Login loginDto = Login.getLogin(request);
			// To get region id get reg_div_id from Users Table
			userdata = usersDao.findWhereIdEquals(loginDto.getUserId())[0];
			switch (ActionMethods.SaveTypes.getValue(form.getsType())) {
				case SAVE:
					try{
						if (userdata != null){
							int reg_id = regionDao.findByDynamicSelect("SELECT * FROM REGIONS R LEFT JOIN DIVISON D ON D.REGION_ID=R.ID LEFT JOIN LEVELS L ON D.ID=L.DIVISION_ID LEFT JOIN PROFILE_INFO PI ON L.ID=PI.LEVEL_ID LEFT JOIN USERS U ON PI.ID=U.PROFILE_ID WHERE U.ID=?", new Object[] { new Integer(loginDto.getUserId()) })[0].getId();
							// profileInfo =
							// profileInfoDao.findByPrimaryKey(userdata.getProfileId());
							// Get Region Abbreviation from Region Table using
							// region id
							String reg_abb = regionDao.findByPrimaryKey(reg_id).getRefAbbreviation();
							// Get Process chain DTO object
							process_chain_dto = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=26  WHERE UR.USER_ID=?", new Object[] { new Integer(loginDto.getUserId()) })[0];
							int uniqueID = 1;
							EmpSerReqMap empMap[] = emp_Ser_Req_Map_Dao.findByDynamicSelect("SELECT * FROM EMP_SER_REQ_MAP WHERE ID=(SELECT MAX(ID) FROM EMP_SER_REQ_MAP WHERE REQ_TYPE_ID=5)", null);
							if (empMap.length > 0){
								String s = empMap[0].getReqId().split("-")[2];
								uniqueID = Integer.parseInt(s) + 1;
							}
							// save Data in EMP_SER_REQ_MAP TABLE
							empReqMapDto = new EmpSerReqMap();
							empReqMapDto.setSubDate(date);
							empReqMapDto.setReqId(reg_abb + "-RM-" + uniqueID);
							empReqMapDto.setReqTypeId(5);
							empReqMapDto.setRegionId(reg_id);
							empReqMapDto.setRequestorId(loginDto.getUserId());
							empReqMapDto.setProcessChainId(process_chain_dto.getId());
							empReqMapDto.setNotify(process_chain_dto.getNotification());
							reqpk = emp_Ser_Req_Map_Dao.insert(empReqMapDto);
							// save data in REIMBURSEMENT TABLE
							reimbursementData.setEsrMapId(reqpk.getId());
							reimbursementData.setStatus(Status.REQUESTSAVED);
							// insert into Reimbursement Financial Table
							double tamount = 0;
							double totalamount = 0;
							if (reimbursementData.getFinancialData() != null && reimbursementData.getFinancialData().length > 0){
								for (String s : reimbursementData.getFinancialData()){
									ReimbursementFinancialData financialData = new ReimbursementFinancialData();
									String data[] = s.replaceFirst("=", "").split("~=~");
									for (String d : data){
										financialData.setEsrmapId(reqpk.getId());
										String val1 = d.split("=")[0];
										String val2 = d.split("=")[1];
										if (val1.equals("dateOfOccurrence")){
											financialData.setDateOfOccurrence(PortalUtility.StringToDate(val2));
										} else if (val1.equals("summary")){
											financialData.setSummary(val2);
										} else if (val1.equals("type")){
											financialData.setType(val2);
										} else if (val1.equals("amount")){
											double amount = Double.valueOf(val2);
											financialData.setAmount(val2);
											tamount = tamount + amount;
										
											
										/*	if (val2.split("\\.")[1].equalsIgnoreCase("00")){
												totalamount = String.valueOf(tamount) + "0";
											}*/
										} else if (val1.equals("currency")){
											financialData.setCurrency(val2);
										} else if (val1.equals("receiptsAvailable")){
											financialData.setReceiptsAvailable(val2);
										} else if (val1.equals("receiptId")){
											financialData.setUploadReceiptsId(Integer.parseInt(val2));
										}
									}
									reimbursementFinancialDataDao.insert(financialData);
								}
							}
							
							
							
							
							
							
							
							reimbursementDtoData = ReimbursementReq.getReimbursementReqDto(reimbursementData);
							if(reimbursementData.getReimbuFlag().equalsIgnoreCase("OTHER")){
								//reimbursementDtoData.setOtherEmpId(reimbursementData.getOtherEmpId());
								System.out.println("paymentmade to id=="+reimbursementData.getPaymentMadeToEmpId());
								reimbursementDtoData.setReimbuFlag("OTHER");
								
								reimbursementDtoData.setPaymentMadeToEmpId(reimbursementData.getPaymentMadeToEmpId());
								if(reimbursementData.getPaymentMadeToFlag().equalsIgnoreCase("PAYMENT_OTHER")){
									reimbursementDtoData.setOTHER_EMP_NAME(reimbursementData.getOTHER_EMP_NAME());

									//reimbursementDtoData.setPaymentMadetoEmpId(reimbursementData.getPaymentMadetoEmpId());
								}else{
									reimbursementDtoData.setOTHER_EMP_NAME(0);
								}
								
								
							}
							else{
								reimbursementDtoData.setReimbuFlag("SELF");
								
							}
							reimbursementDtoData.setRequesterId(loginDto.getUserId());
							reimbursementpk = reimbursementReqDao.insert(reimbursementDtoData);
							ReimbursementReq reqDto = reimbursementReqDao.findByPrimaryKey(reimbursementpk);
							request.setAttribute("actionForm", reqDto);
						}
					} catch (ArrayIndexOutOfBoundsException e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.arrayoutofbound"));
						emp_Ser_Req_Map_Dao.delete(reqpk);
						e.printStackTrace();
					} catch (EmpSerReqMapDaoException e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
						e.printStackTrace();
					} catch (ReimbursementReqDaoException e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
						emp_Ser_Req_Map_Dao.delete(reqpk);
						e.printStackTrace();
					} catch (ReimbursementFinancialDataDaoException e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
						emp_Ser_Req_Map_Dao.delete(reqpk);
						e.printStackTrace();
					} catch (RegionsDaoException e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.regions"));
						emp_Ser_Req_Map_Dao.delete(reqpk);
						e.printStackTrace();
					} catch (Exception e){
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
						emp_Ser_Req_Map_Dao.delete(reqpk);
						e.printStackTrace();
					}
					break;
				case SUBMIT:
					profileInfo = profileInfoDao.findByPrimaryKey(userdata.getProfileId());
					if (profileInfo == null){ throw new ProfileInfoDaoException(""); }
					ReimbursementReq reimbursementReqdto = reimbursementReqDao.findWhereIdEquals(reimbursementData.getId())[0];
					ReimbursementFinancialData[] financialdto = reimbursementFinancialDataDao.findWhereEsrmapIdEquals(reimbursementReqdto.getEsrMapId());
					String currency = null;
					double tamount = 0;
					double totalamount =0;
					//requestor profile 
					Users userReq = usersDao.findByPrimaryKey(reimbursementReqdto.getRequesterId());
					ProfileInfo requestorPro = profileInfoDao.findByPrimaryKey(userReq.getProfileId());
					//RM profile 
					//Users rmReq = usersDao.findByPrimaryKey(requestorPro.getReportingMgr());
					//ProfileInfo rmProfile1 = profileInfoDao.findByPrimaryKey(rmReq.getProfileId());
					//hrspoc profile 
					Users hrspocRequ = usersDao.findByPrimaryKey(requestorPro.getHrSpoc());
					ProfileInfo hrspocPro = profileInfoDao.findByPrimaryKey(hrspocRequ.getProfileId());
					//login persons profile
					//ProfileInfo approverProf = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(loginDto.getUserId()).getProfileId());
					//ESR MAP details
					EmpSerReqMap empSerReqMapDto = emp_Ser_Req_Map_Dao.findByPrimaryKey(reimbursementReqdto.getEsrMapId());
					// Get Process Chain id
					int proc_chain_id = modulePermissionDao.findByDynamicSelect("SELECT * FROM MODULE_PERMISSION MP LEFT JOIN USER_ROLES UR ON MP.ROLE_ID=UR.ROLE_ID AND MODULE_ID=26 WHERE UR.USER_ID=?", new Object[] { new Integer(loginDto.getUserId()) })[0].getProcChainId();
					// Get Process chain DTO object
					process_chain_dto = processChainDao.findByPrimaryKey(proc_chain_id);
					if (process_chain_dto == null){ throw new ProcessChainDaoException(""); }
					Integer[] ids = evaluator.approvers(process_chain_dto.getApprovalChain(), 1, loginDto.getUserId());
					if (ids != null && ids.length > 0){
						if (reimbursementReqdto.getStatus().equals(Status.REQUESTSAVED)){
							// send Notification to user
							for (ReimbursementFinancialData d : financialdto){
								 double amount = Double.parseDouble(d.getAmount());
								tamount = tamount + amount;
								if (d.getAmount().split("\\.")[1].equalsIgnoreCase("00"))
								{
								totalamount = tamount;
								}
							else{
									totalamount = tamount;
								}
								currency = d.getCurrency();
							}
							//String amount = currency + " " + totalamount;
							//String templateName = MailSettings.REIMBURSEMENT_USER;
							//String candidateName = profileInfo.getFirstName();
							////					PortalMail portalmailForRequester=sendMailDetails(mailSubject,null, candidateName, amount, templateName,
							////						null, null, null, null, null, null, reimbursementReqdto.getEsrMapId());
							////					
							//					String reimbursement_message_bodyForRequester = mailGenarator.replaceFields(mailGenarator
							//						.getMailTemplate(portalmailForRequester.getTemplateName()), portalmailForRequester);
							//					
							//					reimbursementReqdto.setMessageBody(reimbursement_message_bodyForRequester);
							reimbursementReqdto.setStatus(Status.REQUESTRAISED);
							reimbursementReqdto.setCreateDate(new Date());
							reimbursementReqdto.setAssignTo(loginDto.getUserId());
							reimbursementReqDao.update(new ReimbursementReqPk(reimbursementReqdto.getId()), reimbursementReqdto);
							Date neDate = new Date();
							//					sendToInboxForReimbursement(reimbursementReqdto.getEsrMapId(), reimbursementReqdto.getId(), mailSubject,
							//						Status.REQUESTRAISED,loginDto.getUserId());
							ProfileInfo rm_profileInfo = null;
							for (int id : ids){
								reimbursementReqdto.setAssignTo(id);
								reimbursementReqdto.setId(0);
								reimbursementReqdto.setCreateDate(neDate);
								rm_profileInfo = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(id) })[0];
								// Get the PortalMail for reimbursement
								PortalMail portalMailbody = sendMailDetails(null, null, profileInfo.getFirstName(), currency + " " + totalamount, MailSettings.REIMBURSEMENT_HR_RM_MAIL, PortalUtility.getdd_MM_yyyy_hh_mm_a(neDate), null, rm_profileInfo.getFirstName(), null, rm_profileInfo.getFirstName(), null, reimbursementReqdto.getEsrMapId(), null);
								String reimbursement_message_body = mailGenarator.replaceFields(mailGenarator.getMailTemplate(portalMailbody.getTemplateName()), portalMailbody);
								reimbursementReqdto.setMessageBody(reimbursement_message_body);
								reimbursementReqdto.setRequesterId(loginDto.getUserId());
								reimbursementReqdto.setCreateDate(neDate);
								reimbursementReqdto.setStatus(Status.REQUESTRAISED);
								reimbursementpk = reimbursementReqDao.insert(reimbursementReqdto);
								// sending mail to RM with CC to HrSpoc
								Set<String> ccMailids = new HashSet<String>();
								Users users = usersDao.findByPrimaryKey(profileInfo.getHrSpoc());
								ProfileInfo hr_profileInfo = profileInfoDao.findByPrimaryKey(users.getProfileId());
								ccMailids.add(hr_profileInfo.getOfficalEmailId());
								//to RM
								String mailSubject = "New Reimbursement request [" + empSerReqMapDto.getReqId() + "] by " + requestorPro.getFirstName();
								PortalMail portalMail=sendMailDetails(mailSubject, rm_profileInfo.getOfficalEmailId(), profileInfo.getFirstName(), currency + " " + totalamount, MailSettings.REIMBURSEMENT_HR_RM_MAIL, PortalUtility.getdd_MM_yyyy_hh_mm_a(date), null, rm_profileInfo.getFirstName(), null, rm_profileInfo.getFirstName(), ccMailids, reimbursementReqdto.getEsrMapId(), hrspocPro.getFirstName());
								// sending Android Notification
								
								List<String> mailids=new ArrayList<String>();
								mailids.add(portalMail.getRecipientMailId());
								if(portalMail.getAllReceipientcCMailId()!=null){
								for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
								sendReimbursementNotification(mailSubject,mailids);
								
								// send to docking station of RM
								inboxModel.sendToDockingStation(reimbursementReqdto.getEsrMapId(), reimbursementReqdto.getId(), mailSubject, Status.REQUESTRAISED);
							}
						}
					}//no approvers in level one itself
					else{
						throw new ArrayIndexOutOfBoundsException("No approvers");
					}
					break;
			}
		} catch (ArrayIndexOutOfBoundsException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.arrayoutofbound"));
			e.printStackTrace();
		} catch (EmpSerReqMapDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (ReimbursementReqDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (ReimbursementFinancialDataDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (ProfileInfoDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.profileInfo"));
			e.printStackTrace();
		} catch (ProcessChainDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.approver"));
			e.printStackTrace();
		} catch (Exception e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ReimbursementRequestForm reqData = (ReimbursementRequestForm) form;
		ActionResult result = new ActionResult();
		ReimbursementReqDao reimbursementReqDao = ReimbursementReqDaoFactory.create();
		ReimbursementFinancialDataDao reimbursementFinancialDao = ReimbursementFinancialDataDaoFactory.create();
		EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
		ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
		// ServiceReqInfoDao reqInfoDao = ServiceReqInfoDaoFactory.create();
		InboxDao inboxDao = InboxDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		ReimbursementFinancialDataPk fPk = new ReimbursementFinancialDataPk();
		MailGenerator mailGenarator = new MailGenerator();
		//InboxModel inboxModel = new InboxModel();
		ProcessChain process_chain_dto = new ProcessChain();
		ProcessEvaluator processEvaluator = new ProcessEvaluator();
		//SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy [HH:mm a]");
		String mailSubject = "";
		Set<String> mailid = new HashSet<String>();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDaoinfo = ProfileInfoDaoFactory.create();
		String crny = "";
		MailGenerator mailGenerator = new MailGenerator();
		//Set escalation
		Login escLogin = Login.getLogin(request);
		int escEsrMapId = reqData.getEsrMapId();
		int escUserId = escLogin != null ? escLogin.getUserId() : 0;
		reqEscalation.setEscalationAction(escEsrMapId, escUserId);
		//If escalation action set current esrMapId
		if (reqEscalation.isEscalationAction()){
			reqEscalation.setEsrMapId(escEsrMapId);
			reqEscalation.setEscalationProcess(processEvaluator);
			//reqEscalation.enableEscalationPermission(processEvaluator);
		}
		// int userID = 0;
		try{
			Login loginDto = Login.getLogin(request);
			ReimbursementReq requiredData = null;
			EmpSerReqMap esrmapData = empSerReqMapDao.findByPrimaryKey(reqData.getEsrMapId());
			if (reqData.getuType().equalsIgnoreCase("EDIT") || (reqData.getuType().equalsIgnoreCase("CANCELED"))){
				if (reqData.getuType().equalsIgnoreCase("CANCELED")){
					requiredData = reimbursementReqDao.findByPrimaryKey(reqData.getId());
				} else{
					requiredData = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=? AND ASSIGN_TO = ? ORDER BY CREATE_DATE DESC", new Object[] { new Integer(esrmapData.getId()), 0 })[0];
				}
			} else{
				requiredData = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=? AND ASSIGN_TO = ? ORDER BY CREATE_DATE DESC", new Object[] { new Integer(esrmapData.getId()), loginDto.getUserId() })[0];
			}
			switch (UpdateTypes.getValue(form.getuType())) {
				case EDIT:
					ReimbursementReq reqDto = ReimbursementReq.getReimbursementReqDto(reqData);
					reqDto.setId(requiredData.getId());
					reqDto.setAssignTo(requiredData.getAssignTo());
					reqDto.setStatus(requiredData.getStatus());
					reqDto.setMessageBody(requiredData.getMessageBody());
					reqDto.setRequesterId(requiredData.getRequesterId());
					reqDto.setCreateDate(requiredData.getCreateDate());
					
					if(reqData.getReimbuFlag().equalsIgnoreCase("OTHER")){
						//reimbursementDtoData.setOtherEmpId(reimbursementData.getOtherEmpId());
						System.out.println("paymentmade to id=="+reqData.getPaymentMadeToEmpId());
						reqDto.setReimbuFlag(reqData.getReimbuFlag());
						reqDto.setPaymentMadeToEmpId(reqData.getPaymentMadeToEmpId());
					//	reqDto.setPaymentMadeToFlag("PAYMENT_OTHER");
						if(reqData.getPaymentMadeToFlag().equalsIgnoreCase("PAYMENT_OTHER")){
							reqDto.setOTHER_EMP_NAME(reqData.getOTHER_EMP_NAME());

							//reimbursementDtoData.setPaymentMadetoEmpId(reimbursementData.getPaymentMadetoEmpId());
						}else{
							reqDto.setOTHER_EMP_NAME(0);
						}
					}
					
					reimbursementReqDao.update(new ReimbursementReqPk(requiredData.getId()), reqDto);
					ArrayList<Integer> list = new ArrayList<Integer>();
					if (reqData.getFinancialData() != null && reqData.getFinancialData().length > 0){
						for (String s : reqData.getFinancialData()){
							ReimbursementFinancialData financialData = new ReimbursementFinancialData();
							String data[] = s.split(":")[0].replaceFirst("=", "").split("~=~");
							for (String d : data){
								financialData.setEsrmapId(reqData.getEsrMapId());
								String val1 = d.split("=")[0];
								String val2 = d.split("=")[1];
								if (val1.equals("dateOfOccurrence")){
									financialData.setDateOfOccurrence(PortalUtility.StringToDate(val2));
								} else if (val1.equals("summary")){
									financialData.setSummary(val2);
								} else if (val1.equals("type")){
									financialData.setType(val2);
								} else if (val1.equals("amount")){
									financialData.setAmount((val2));
								} else if (val1.equals("currency")){
									financialData.setCurrency(val2);
								} else if (val1.equals("receiptsAvailable")){
									financialData.setReceiptsAvailable(val2);
								} else if (val1.equals("receiptId")){
									financialData.setUploadReceiptsId(Integer.parseInt(val2));
								}
							}
							if (s.split(":").length > 1){
								fPk.setId(Integer.parseInt(s.split(":")[1]));
								financialData.setId(fPk.getId());
								reimbursementFinancialDao.update(fPk, financialData);
								list.add(fPk.getId());
							} else{
								ReimbursementFinancialDataPk pk = reimbursementFinancialDao.insert(financialData);
								list.add(pk.getId());
							}
						}
						ReimbursementFinancialData[] financialData = reimbursementFinancialDao.findWhereEsrmapIdEquals(reqData.getEsrMapId());
						for (ReimbursementFinancialData rf : financialData){
							if (!list.contains(rf.getId())){
								reimbursementFinancialDao.delete(new ReimbursementFinancialDataPk(rf.getId()));
							}
						}
					}
					break;
				case CANCELED:
					ReimbursementReq req = reimbursementReqDao.findByPrimaryKey(requiredData.getId());
					ReimbursementReq reqR = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ID=(SELECT MAX(ID) FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=?)", new Object[] { requiredData.getEsrMapId() })[0];
					//requestor profile 
					//Users userRequestor = usersDao.findByPrimaryKey(reqR.getRequesterId());
					//ProfileInfo requestorProfile = profileInfoDao.findByPrimaryKey(userRequestor.getProfileId());
					//RM profile 
					//Users rmRequestor = usersDao.findByPrimaryKey(requestorProfile.getReportingMgr());
					//ProfileInfo rmProfile = profileInfoDao.findByPrimaryKey(rmRequestor.getProfileId());
					//hrspoc profile 
					//Users hrspocRequestor = usersDao.findByPrimaryKey(requestorProfile.getHrSpoc());
					//ProfileInfo hrspocProfile = profileInfoDao.findByPrimaryKey(hrspocRequestor.getProfileId());
					if (reqR.getStatus().equalsIgnoreCase(Status.PENDINGAPPROVAL) || reqR.getStatus().equalsIgnoreCase(Status.REQUESTRAISED) || reqR.getStatus().equalsIgnoreCase(Status.APPROVED) || reqR.getStatus().equalsIgnoreCase(Status.REQUESTSAVED) || reqR.getStatus().equalsIgnoreCase(Status.ASSIGNED)){
						ReimbursementReq reqRRevoked[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE  ESR_MAP_ID=?", new Object[] { requiredData.getEsrMapId() });
						if (reqRRevoked != null && reqRRevoked.length > 0){
							for (ReimbursementReq requestRev : reqRRevoked){
								requestRev.setStatus(Status.REVOKED);
								reimbursementReqDao.update(new ReimbursementReqPk(requestRev.getId()), requestRev);
							}
							Inbox iBox[] = inboxDao.findWhereEsrMapIdEquals(requiredData.getEsrMapId());
							if (iBox != null && iBox.length > 0) for (Inbox siBox : iBox){
								siBox.setStatus(Status.REVOKED);
								inboxDao.update(new InboxPk(siBox.getId()), siBox);
							}
						}
					} else if (reqR.getStatus().equalsIgnoreCase(Status.COMPLETED) || reqR.getStatus().equalsIgnoreCase(Status.INPROGRESS)){
						try{
							process_chain_dto = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=26  WHERE UR.USER_ID=?", new Object[] { new Integer(req.getRequesterId()) })[0];
							//ProfileInfo userprofileInfo = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(req.getRequesterId()) })[0];
							//ProfileInfo approverprofileInfo = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(loginDto.getUserId()) })[0];
							Integer[] thirdlevel = processEvaluator.handlers(process_chain_dto.getHandler(), req.getRequesterId());
							for (Integer handler : thirdlevel){
								ReimbursementReq handlerreq = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID=?  AND  ASSIGN_TO =? ", new Object[] { requiredData.getEsrMapId(), handler })[0];
								handlerreq.setStatus("Cancel Request");
								if (reqR.getStatus().equalsIgnoreCase(Status.INPROGRESS)){
									handlerreq.setActiveNull(true);
								}
								reimbursementReqDao.update(new ReimbursementReqPk(handlerreq.getId()), handlerreq);
								if (reqR.getStatus().equalsIgnoreCase(Status.COMPLETED)){
									//copy template from inbox history
									JDBCUtiility jdbcUtiility = JDBCUtiility.getInstance();
									List mailTemplate = jdbcUtiility.getSingleColumn("SELECT MESSAGE_BODY FROM INBOX_HISTORY WHERE RECEIVER_ID=? AND ESR_MAP_ID=?", new Object[] { handler, handlerreq.getEsrMapId() });
									//insert in inbox
									mailSubject = "You have a reimbursement request to be processed";
									sendToInboxForReimbursement(handlerreq.getEsrMapId(), handlerreq.getId(), mailSubject, Status.CANCELREQUEST, handler, mailTemplate.get(0).toString(), false);
								} else if (reqR.getStatus().equalsIgnoreCase(Status.INPROGRESS)){
									Inbox inbCancelUpdate[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE RECEIVER_ID=? AND ASSIGNED_TO!=? AND ESR_MAP_ID=?", new Object[] { handler, loginDto.getUserId(), handlerreq.getEsrMapId() });
									if (inbCancelUpdate.length > 0){
										for (Inbox singleInbox : inbCancelUpdate){
											if (singleInbox.getAssignedTo() != loginDto.getUserId()){
												singleInbox.setStatus("Cancel Request");
												inboxDao.update(new InboxPk(singleInbox.getId()), singleInbox);
											}
										}
									}
								}
							}
							/**
							 * Update Inbox if inprogress to cancel request of requestor
							 */
							if (reqR.getStatus().equalsIgnoreCase(Status.INPROGRESS)){
								Inbox inbUpdate[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE RECEIVER_ID=? AND ASSIGNED_TO =? AND ESR_MAP_ID=?", new Object[] { reqR.getRequesterId(), 0, reqR.getEsrMapId() });
								if (inbUpdate.length > 0){
									if (inbUpdate[0].getAssignedTo() != loginDto.getUserId()){
										inbUpdate[0].setStatus("Cancel Request");
										inboxDao.update(new InboxPk(inbUpdate[0].getId()), inbUpdate[0]);
									}
								}
							}//if for update inbox if inprogress of requestor
						} catch (Exception e){
							e.printStackTrace();
						}
					}
					break;
				case ACCEPTED:
					boolean flag = false,
					noLevelOne = false,
					noLevelTwo = false;
					//sameapprovers = false;
					boolean forUser = false;
					double ttamount = 0;
					double totalamount = 0;
					String comments = "";
					Users reimbuUserforOther;
					Users reimbuUserforOtherpaymentmadeto;
					ProfileInfo reimbuRequestforProfile ;
					ProfileInfo reimbuRequestforProfilepaymrntmadeto ;
					ReimbursementReq req1 = reimbursementReqDao.findByPrimaryKey(requiredData.getId());
					//requestor profile 
										
					
					Users userRequestor1 = usersDao.findByPrimaryKey(req1.getRequesterId());
					ProfileInfo requestorProfile1 = profileInfoDao.findByPrimaryKey(userRequestor1.getProfileId());
					//RM profile 
					//Users rmRequestor1 = usersDao.findByPrimaryKey(requestorProfile1.getReportingMgr());
					//ProfileInfo rmProfile1 = profileInfoDao.findByPrimaryKey(rmRequestor1.getProfileId());
					//hrspoc profile 
					Users hrspocRequestor1 = usersDao.findByPrimaryKey(requestorProfile1.getHrSpoc());
					ProfileInfo hrspocProfile1 = profileInfoDao.findByPrimaryKey(hrspocRequestor1.getProfileId());
					//login persons profile
					ProfileInfo approverProfile = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(loginDto.getUserId()).getProfileId());
					ReimbursementReqPk reqPKacc = new ReimbursementReqPk();
					if (!req1.getStatus().equals(Status.ON_HOLD)){
						req1.setRemark(reqData.getRemark());
						reimbursementReqDao.update(new ReimbursementReqPk(req1.getId()), req1);
					}
					ReimbursementFinancialData[] fdata = reimbursementFinancialDao.findWhereEsrmapIdEquals(req1.getEsrMapId());
					boolean adminCheck = false;
					// check first level approval
					try{
						if (reqEscalation.isEscalationAction()){
							process_chain_dto = reqEscalation.getRequestProcessChain();
						} else{
							process_chain_dto = processChainDao.findByDynamicSelect("SELECT * FROM PROCESS_CHAIN PC LEFT JOIN MODULE_PERMISSION MP ON MP.PROC_CHAIN_ID=PC.ID LEFT JOIN USER_ROLES UR ON UR.ROLE_ID=MP.ROLE_ID AND MODULE_ID=26  WHERE UR.USER_ID=?", new Object[] { new Integer(req1.getRequesterId()) })[0];
						}
						ProfileInfo userprofileInfo = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(req1.getRequesterId()) })[0];
						ProfileInfo approverprofileInfo = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(loginDto.getUserId()) })[0];
						boolean escAction = reqEscalation.isEscalationAction();
						boolean escFirstLevel, escSecondLevel;
						Integer[] firstlevel = processEvaluator.approvers(process_chain_dto.getApprovalChain(), 1, req1.getRequesterId());
						escFirstLevel = checkEscalationActorBySequence(process_chain_dto.getId(), 1, req1.getRequesterId(), escEsrMapId, processEvaluator);
						if (escAction && escFirstLevel) reqEscalation.disableEscalationAction(processEvaluator);
						Integer[] secondlevel = processEvaluator.approvers(process_chain_dto.getApprovalChain(), 2, req1.getRequesterId());
						escSecondLevel = checkEscalationActorBySequence(process_chain_dto.getId(), 2, req1.getRequesterId(), escEsrMapId, processEvaluator);
						if (escAction && escSecondLevel){
							reqEscalation.disableEscalationAction(processEvaluator);
							firstlevel = processEvaluator.approvers(process_chain_dto.getApprovalChain(), 1, req1.getRequesterId());
						} else reqEscalation.enableEscalationAction(processEvaluator);
						Integer[] thirdlevel = processEvaluator.handlers(process_chain_dto.getHandler(), req1.getRequesterId());
						if (escAction && !escFirstLevel && !escSecondLevel){
							reqEscalation.disableEscalationAction(processEvaluator);
							secondlevel = processEvaluator.approvers(process_chain_dto.getApprovalChain(), 2, req1.getRequesterId());
						} else reqEscalation.enableEscalationAction(processEvaluator);
						// set CCmailIds of RM and HRspoc to mailid
						// RM info
						for (Integer id : firstlevel){
							Users RmUserInfo = usersDao.findByPrimaryKey(id);
							ProfileInfo RMprofileInfo = profileInfoDaoinfo.findByPrimaryKey(RmUserInfo.getProfileId());
							if (id != loginDto.getUserId()) mailid.add(RMprofileInfo.getOfficalEmailId());
						}
						// HrSpoc info
						for (Integer id : secondlevel){
							Users HrspocUserInfo = usersDao.findByPrimaryKey(id);
							ProfileInfo HrspocprofileInfo = profileInfoDaoinfo.findByPrimaryKey(HrspocUserInfo.getProfileId());
							if (id != loginDto.getUserId()) mailid.add(HrspocprofileInfo.getOfficalEmailId());
						}
						// if no second level then treat first as second.
						if (secondlevel.length == 0){
							secondlevel = firstlevel;
							firstlevel = new Integer[] { 0 };
						}
						String currency = "";
						for (ReimbursementFinancialData f : fdata){
							double totalamt = Double.parseDouble((f.getAmount()));
							totalamount = totalamount + totalamt;
							currency = f.getCurrency();
						}
						// ServiceReqInfo sReqinfo =
						// reqInfoDao.findWhereEsrMapIdEquals(req1.getEsrMapId())[0];
						comments = reqData.getRemark();
						EmpSerReqMap empSerReqMapDto = empSerReqMapDao.findByPrimaryKey(req1.getEsrMapId());
						if (Arrays.asList(firstlevel).contains(new Integer(loginDto.getUserId())) && (req1.getStatus().equals(Status.REQUESTRAISED) || req1.getStatus().equals(Status.ON_HOLD))){
							if (firstlevel.length > 0 && secondlevel.length > 0){
								Integer array[] = getSibblings(firstlevel, loginDto.getUserId());
								req1.setRemark(reqData.getRemark());
								req1.setStatus("Approved");
								req1.setActionTakenBy(loginDto.getUserId());
								req1.setActionTakenOn(new Date());
								reimbursementReqDao.update(new ReimbursementReqPk(req1.getId()), req1);
								for (Integer secondlInteger : secondlevel){
									ProfileInfo secondlPI = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(secondlInteger) })[0];
									if (Arrays.asList(secondlevel).contains(new Integer(loginDto.getUserId()))){
										req1.setStatus(Status.REQUESTRAISED);
										req1.setAssignTo(thirdlevel[0]);
										adminCheck = true;
									} else{
										req1.setStatus(Status.REQUESTRAISED);
										req1.setAssignTo(secondlInteger);
									}
									//					req1.setCreateDate(new Date());
									req1.setActionTakenBy(loginDto.getUserId());
									//req1.setActionTakenOn(req1.getActionTakenOn());
									req1.setId(0);
									req1.setRemark(null);
									reqPKacc = reimbursementReqDao.insert(req1);
									/**
									 * email and inbox to requestor CC to:- RMG manager
									 */
									ReimbursementFinancialData[] fdataa = reimbursementFinancialDao.findWhereEsrmapIdEquals(req1.getEsrMapId());
									mailid = new HashSet<String>();
									if(requiredData.getReimbuFlag().equals("OTHER")){
										 reimbuUserforOther = usersDao.findByPrimaryKeys(req1.getOTHER_EMP_NAME());
										 reimbuUserforOtherpaymentmadeto = usersDao.findByPrimaryKeys(req1.getPaymentMadeToEmpId());
										 reimbuRequestforProfile = profileInfoDao.findByPrimaryKey(reimbuUserforOther.getProfileId());
										 reimbuRequestforProfilepaymrntmadeto = profileInfoDao.findByPrimaryKey(reimbuUserforOtherpaymentmadeto.getProfileId());
										
										if(requiredData.getOTHER_EMP_NAME()>0 && requiredData.getOTHER_EMP_NAME()!=requiredData.getPaymentMadeToEmpId()){
											mailid.add(reimbuRequestforProfile.getOfficalEmailId());
											mailid.add(reimbuRequestforProfilepaymrntmadeto.getOfficalEmailId());	
										}
										else{
											mailid.add(reimbuRequestforProfilepaymrntmadeto.getOfficalEmailId());
										}						 
									}									
									for (ReimbursementFinancialData f : fdataa){
										crny = f.getCurrency();
										ttamount = ttamount + Double.parseDouble((f.getAmount()));
										if (f.getAmount().split("\\.")[1] != null){
											totalamount =ttamount+0;
										}
										currency = f.getCurrency();
									}
									//PortalMail mail = new PortalMail();
									// Send mail HrSpoc level 2people
									PortalMail portalMail = sendMailDetails("Reimbursement Request [" + empSerReqMapDto.getReqId() + "] Approved by " + approverProfile.getFirstName(), secondlPI.getOfficalEmailId(), requestorProfile1.getFirstName(), crny + " " + totalamount, MailSettings.REIMBURSEMENT_APPROVAL_RM, PortalUtility.getdd_MM_yyyy_hh_mm_a(req1.getCreateDate()), null, approverProfile.getFirstName(), comments, null, mailid, empSerReqMapDto.getId(), secondlPI.getFirstName());
									// sending Android Notification
									String message="Reimbursement Request [" + empSerReqMapDto.getReqId() + "] Approved by " + approverProfile.getFirstName();
									List<String> mailids=new ArrayList<String>();
									mailids.add(portalMail.getRecipientMailId());
									if(portalMail.getAllReceipientcCMailId()!=null){
									for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
									sendReimbursementNotification(message,mailids);
									
									String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.REIMBURSEMENT_APPROVAL_RM), portalMail);
									portalMail.setMailBody(bodyText);
									req1.setMessageBody(portalMail.getMailBody());
									// send to docking station of HR and other at level 2
									mailSubject = "Reimbursement Request [" + empSerReqMapDto.getReqId() + "] Approved by " + approverProfile.getFirstName();
									if (!adminCheck) sendToInboxForReimbursement(req1.getEsrMapId(), reqPKacc.getId(), mailSubject, Status.REQUESTRAISED, secondlInteger, portalMail.getMailBody(), false);
								}
								Inbox inb[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE RECEIVER_ID=? AND ASSIGNED_TO=? AND ESR_MAP_ID=?", new Object[] { loginDto.getUserId(), loginDto.getUserId(), req1.getEsrMapId() });
								if (inb.length > 0){
									inb[0].setStatus(req1.getStatus().equals(Status.REQUESTRAISED) ? "Approved" : req1.getStatus());
									inb[0].setIsDeleted(1);
									inboxDao.update(new InboxPk(inb[0].getId()), inb[0]);
								}
								//update approvers at same level inbox status and request table
								for (Integer singleR : array){
									ReimbursementReq reimbursementReqForSibbling[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID = ? AND ASSIGN_TO = ?", new Object[] { req1.getEsrMapId(), singleR });
									if (reimbursementReqForSibbling.length > 0){
										reimbursementReqForSibbling[0].setActionTakenBy(loginDto.getUserId());
										reimbursementReqForSibbling[0].setCreateDate(req1.getCreateDate());
										reimbursementReqForSibbling[0].setStatus(req1.getStatus().equals(Status.REQUESTRAISED) ? "Approved" : req1.getStatus());
										reimbursementReqForSibbling[0].setRemark(reqData.getRemark());
										reimbursementReqDao.update(new ReimbursementReqPk(reimbursementReqForSibbling[0].getId()), reimbursementReqForSibbling[0]);
										//update approvers at same level inbox status
										Inbox inb2[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE RECEIVER_ID=? AND ASSIGNED_TO!=? AND ESR_MAP_ID=?", new Object[] { singleR, loginDto.getUserId(), req1.getEsrMapId() });
										if (inb2.length > 0){
											for (Inbox singleInbox : inb2){
												if (singleInbox.getAssignedTo() != loginDto.getUserId()){
													singleInbox.setStatus(req1.getStatus().equals(Status.REQUESTRAISED) ? "Approved" : req1.getStatus());
													singleInbox.setIsDeleted(1);
													inboxDao.update(new InboxPk(singleInbox.getId()), singleInbox);
												}
											}
										}
									} else logger.debug("An escalation user without permission is being updated. Can't update. USER_ID: " + singleR);
								}
							} else{//no handlers in level one
								if (!(firstlevel.length > 0)){
									noLevelOne = true;
								} else if (!(secondlevel.length > 0)){
									noLevelTwo = true;
								}
								throw new Exception("No approvers in level");
							}
						} else if (!(firstlevel.length > 0)){//no handlers in level one
							noLevelOne = true;
							throw new Exception("No approvers in level");
						} else if (!adminCheck && Arrays.asList(secondlevel).contains(loginDto.getUserId()) && (req1.getStatus().equals(Status.REQUESTRAISED) || req1.getStatus().equals(Status.ON_HOLD))){
							if (secondlevel != null && secondlevel.length > 0){
								Integer array[] = getSibblings(secondlevel, loginDto.getUserId());
								req1.setStatus("Approved");
								req1.setActionTakenBy(loginDto.getUserId());
								req1.setRemark(reqData.getRemark());
								req1.setActionTakenOn(new Date());
								reimbursementReqDao.update(new ReimbursementReqPk(req1.getId()), req1);
								ReimbursementReqPk reqPK = new ReimbursementReqPk();
								//					// mail to requester
								//					mailSubject = "Reimbursement Request - Approved [" + empSerReqMapDto.getReqId() + "]"req1.setRemark(reqData.getRemark());
								reimbursementReqDao.update(new ReimbursementReqPk(req1.getId()), req1);;
								//					sendMailDetails(mailSubject, userprofileInfo.getOfficalEmailId(), userprofileInfo.getFirstName(),
								//							currency + " " + totalamount, MailSettings.REIMBURSEMENT_HR_ACCEPTED, null, null,
								//							approverprofileInfo.getFirstName(), null, null, null, empSerReqMapDto.getId(),hrspocProfile1.getFirstName());
								if (thirdlevel != null && thirdlevel.length > 0){
									for (int id : thirdlevel){
										req1.setStatus(Status.REQUESTRAISED);
										req1.setAssignTo(id);
										req1.setId(0);
										req1.setRemark(null);
										ProfileInfo handlerPI = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI JOIN USERS U ON U.PROFILE_ID=PI.ID WHERE U.ID=?", new Object[] { new Integer(id) })[0];
										PortalMail portalMailbody = new PortalMail();
										portalMailbody.setMailSubject("Diksha Lynx: You have a reimbursement request to be processed");
										portalMailbody = sendMailDetails(portalMailbody.getMailSubject(), null, userprofileInfo.getFirstName(), currency + " " + totalamount, MailSettings.REIMBURSEMENT_IN_FINANCEDEPT, new Date().toString(), String.valueOf(req1.getRequesterId()), approverprofileInfo.getFirstName(), null, handlerPI.getFirstName(), null, req1.getEsrMapId(), null);
										// sending Android Notification
										
										List<String> mailids=new ArrayList<String>();
										mailids.add(portalMailbody.getRecipientMailId());
										if(portalMailbody.getAllReceipientcCMailId()!=null){
										for(String mIds:portalMailbody.getAllReceipientcCMailId()) mailids.add(mIds);}
										sendReimbursementNotification(portalMailbody.getMailSubject(),mailids);
										
										String reimbursement_message_body = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.REIMBURSEMENT_IN_FINANCEDEPT), portalMailbody);
										reqPK = reimbursementReqDao.insert(req1);
										sendToInboxForReimbursement(req1.getEsrMapId(), reqPK.getId(), portalMailbody.getMailSubject(), Status.REQUESTRAISED, id, reimbursement_message_body, false);
									}
								} else{//no handler
									result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.nohandler.butapplied"));
								}
								Inbox inb[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE RECEIVER_ID=? AND ASSIGNED_TO=? AND ESR_MAP_ID=?", new Object[] { loginDto.getUserId(), loginDto.getUserId(), req1.getEsrMapId() });
								if (inb.length > 0){
									inb[0].setStatus(req1.getStatus().equals(Status.REQUESTRAISED) ? "Approved" : req1.getStatus());// prem
									inb[0].setIsDeleted(1);
									inboxDao.update(new InboxPk(inb[0].getId()), inb[0]);
								}
								ReimbursementFinancialData[] fdataa = reimbursementFinancialDao.findWhereEsrmapIdEquals(req1.getEsrMapId());
								mailid = new HashSet<String>();
								
								if(requiredData.getReimbuFlag().equals("OTHER")){
									 reimbuUserforOther = usersDao.findByPrimaryKeys(req1.getOTHER_EMP_NAME());
									 reimbuUserforOtherpaymentmadeto = usersDao.findByPrimaryKeys(req1.getPaymentMadeToEmpId());
									 reimbuRequestforProfile = profileInfoDao.findByPrimaryKey(reimbuUserforOther.getProfileId());
									 reimbuRequestforProfilepaymrntmadeto = profileInfoDao.findByPrimaryKey(reimbuUserforOtherpaymentmadeto.getProfileId());
									
									if(requiredData.getOTHER_EMP_NAME()>0 && requiredData.getOTHER_EMP_NAME()!=requiredData.getPaymentMadeToEmpId()){
										mailid.add(reimbuRequestforProfile.getOfficalEmailId());
										mailid.add(reimbuRequestforProfilepaymrntmadeto.getOfficalEmailId());	
									}
									else{
										mailid.add(reimbuRequestforProfilepaymrntmadeto.getOfficalEmailId());
									}						 
								}	
								
								//float total = 0;
								for (ReimbursementFinancialData f : fdataa){
									crny = f.getCurrency();
									ttamount = ttamount + Double.parseDouble((f.getAmount()));
									if (f.getAmount().split("\\.")[1] != null){
										totalamount = ttamount;
									}
									currency = f.getCurrency();
								}
								/**
								 * email requestor
								 * send CC to Finance dept and RMG manager
								 */
								Levels levels1 = LevelsDaoFactory.create().findByPrimaryKey(requestorProfile1.getLevelId());
								Divison divison1 = DivisonDaoFactory.create().findByPrimaryKey(levels1.getDivisionId());
								Regions region11 = RegionsDaoFactory.create().findByPrimaryKey(divison1.getRegionId());
								Notification notifi1 = new Notification(region11.getRefAbbreviation());
								int finance = notifi1.financeId;
								int rmgManager1 = notifi1.rmgManagerLevelId;
								List<String> financeEmails1 = UserModel.getUsersByDivision(finance, null);
								List<String> rmgManagerEmails1 = UserModel.getUsersByLevelId(rmgManager1, null);
								financeEmails1.addAll(rmgManagerEmails1);
								//String[] RecipientsCC = financeEmails1.toArray(new String[0]); // Collection to array
								mailid.addAll(financeEmails1);
								//PortalMail mail = new PortalMail();
								mailSubject = "Reimbursement Request [" + empSerReqMapDto.getReqId() + "] Approved ";
								PortalMail portalMail = sendMailDetails(mailSubject, requestorProfile1.getOfficalEmailId(), requestorProfile1.getFirstName(), currency + " " + totalamount, MailSettings.REIMBURSEMENT_HR_ACCEPTED, PortalUtility.getdd_MM_yyyy_hh_mm_a(req1.getCreateDate()), null, approverProfile.getFirstName(), comments, null, mailid, empSerReqMapDto.getId(), hrspocProfile1.getFirstName());
							//Sending Android Notification	
								List<String> mailids=new ArrayList<String>();
								mailids.add(portalMail.getRecipientMailId());
								if(portalMail.getAllReceipientcCMailId()!=null){
								for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
								sendReimbursementNotification(mailSubject,mailids);
								
								String bodyText = mailGenerator.replaceFields(mailGenerator.getMailTemplate(MailSettings.REIMBURSEMENT_HR_ACCEPTED), portalMail);
								portalMail.setMailBody(bodyText);
								forUser = true;
								req1.setMessageBody(portalMail.getMailBody());
								// send to docking station of Requestor
								if (!adminCheck){
									sendToInboxForReimbursement(req1.getEsrMapId(), reqPK.getId(), mailSubject, Status.APPROVED, userRequestor1.getId(), bodyText, forUser);
								}
								//update approvers at same level inbox status
								for (Integer singleR : array){
									ReimbursementReq reimbursementReqForSibbling[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID = ? AND ASSIGN_TO = ?", new Object[] { req1.getEsrMapId(), singleR });
									if (reimbursementReqForSibbling.length > 0){
										reimbursementReqForSibbling[0].setActionTakenBy(loginDto.getUserId());
										reimbursementReqForSibbling[0].setCreateDate(req1.getCreateDate());
										reimbursementReqForSibbling[0].setStatus(req1.getStatus().equals(Status.REQUESTRAISED) ? "Approved" : req1.getStatus());
										reimbursementReqForSibbling[0].setRemark(reqData.getRemark());
										reimbursementReqDao.update(new ReimbursementReqPk(reimbursementReqForSibbling[0].getId()), reimbursementReqForSibbling[0]);
										Inbox inb2[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE RECEIVER_ID=? AND ASSIGNED_TO!=? AND ESR_MAP_ID=?", new Object[] { singleR, loginDto.getUserId(), req1.getEsrMapId() });
										if (inb2.length > 0){
											for (Inbox singleInbox : inb2){
												if (singleInbox.getAssignedTo() != loginDto.getUserId()){
													singleInbox.setStatus(req1.getStatus().equals(Status.REQUESTRAISED) ? "Approved" : req1.getStatus());
													singleInbox.setIsDeleted(1);
													inboxDao.update(new InboxPk(singleInbox.getId()), singleInbox);
												}
											}
										}
									} else logger.debug("An escalation user without permission is being updated. Can't update. USER_ID: " + singleR);
								}
							} else{//no approver in level two
								noLevelTwo = true;
								throw new Exception("No approvers in level");
							}
						} else if (!(secondlevel.length > 0)){//no handlers in level 2
							noLevelTwo = true;
							throw new Exception("No approvers in level");
						}
					} catch (Exception e){
						flag = true;
						if (noLevelOne){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.loan.invalid.chain.one"));
						} else if (noLevelTwo){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.loan.invalid.chain.two"));
						} else{
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.reimburse.action.failed"));
						}
						e.printStackTrace();
					} finally{
						if (flag){}
					}
					break;
				case REJECTED:{
					float rejectedtamount = 0.00f;
					String rejectedtotalamount = "";
					EmpSerReqMap empSerReqMapDto = empSerReqMapDao.findByPrimaryKey(reqData.getEsrMapId());
					process_chain_dto = processChainDao.findByPrimaryKey(empSerReqMapDto.getProcessChainId());
					ReimbursementReq reimbursementReq = reimbursementReqDao.findByPrimaryKey(reqData.getId());
					reimbursementReq.setRemark(reqData.getRemark());
					Integer[] approversLevel1 = processEvaluator.approvers(process_chain_dto.getApprovalChain(), 1, reimbursementReq.getRequesterId());
					Integer[] approversLevel2 = processEvaluator.approvers(process_chain_dto.getApprovalChain(), 2, reimbursementReq.getRequesterId());
					Integer approversArray[] = null;
					if (reqEscalation.isEscalationAction()){
						if (reqEscalation.isSameLevel(escUserId, approversLevel1)) approversArray = approversLevel1;
						else approversArray = approversLevel2;
					} else{
						if (Arrays.asList(approversLevel1).contains(new Integer(loginDto.getUserId()))){
							approversArray = approversLevel1;
						} else{
							approversArray = approversLevel2;
						}
					}
					//notify all approvers who are in same level
					/*if (approversArray.length > 0){
						ReimbursementReqPk reqPK = null;
						for (Integer singleApp : approversArray){}
					}*/
					ProfileInfo userprofileInfo1 = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(reimbursementReq.getRequesterId()).getProfileId());
					ProfileInfo approverprofileInfo1 = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(loginDto.getUserId()).getProfileId());
					ProfileInfo RmProfile = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(userprofileInfo1.getReportingMgr()).getProfileId());
					ProfileInfo HrProfile = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(userprofileInfo1.getHrSpoc()).getProfileId());
					ReimbursementFinancialData[] fdata1 = reimbursementFinancialDao.findWhereEsrmapIdEquals(reimbursementReq.getEsrMapId());
					mailid = new HashSet<String>();
					//String totalamt = "";
					for (ReimbursementFinancialData f : fdata1){
						crny = f.getCurrency();
						rejectedtamount = rejectedtamount + Float.parseFloat(f.getAmount());
						if (f.getAmount().split("\\.")[1] != null){
							rejectedtotalamount = String.valueOf(rejectedtamount) + "0";
						}
					}
					//send mail to RM
					if (Arrays.asList(approversLevel2).contains(new Integer(loginDto.getUserId()))){
						mailid.add(RmProfile.getOfficalEmailId());
					}
					// Send mail Requester and CC to his HR spoc
					mailid.add(HrProfile.getOfficalEmailId());
					PortalMail portalMail = sendMailDetails("Reimbursement Request [" + empSerReqMapDto.getReqId() + "] Rejected", userprofileInfo1.getOfficalEmailId(), userprofileInfo1.getFirstName(), crny + " " + rejectedtotalamount, MailSettings.REIMBURSEMENT_REJECTED, PortalUtility.getdd_MM_yyyy_hh_mm_a(new Date()), null, approverprofileInfo1.getFirstName(), reqData.getRemark(), null, mailid, empSerReqMapDto.getId(), null);
					//Sending Android Notification
					String message=" Reimbursement Request [" + empSerReqMapDto.getReqId() + "] Rejected";
					List<String> mailids=new ArrayList<String>();
					mailids.add(portalMail.getRecipientMailId());
					if(portalMail.getAllReceipientcCMailId()!=null){
					for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
					sendReimbursementNotification(message,mailids);
					
					String messageBody = mailGenarator.replaceFields(mailGenarator.getMailTemplate(portalMail.getTemplateName()), portalMail);
					//update inbox
					//Inbox inboxArr[] = inboxDao.findByDynamicWhere("ESR_MAP_ID=?", new Object[] { reqData.getEsrMapId() });
					if (approversArray.length > 0){
						for (Integer singleR : approversArray){
							ReimbursementReq reimbursementReqForSibbling[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID = ? AND ASSIGN_TO = ?", new Object[] { reimbursementReq.getEsrMapId(), singleR });
							if (reimbursementReqForSibbling.length > 0){
								reimbursementReqForSibbling[0].setRemark(reqData.getRemark());
								reimbursementReqForSibbling[0].setStatus(Status.REJECTED);
								reimbursementReqForSibbling[0].setActionTakenBy(loginDto.getUserId());
								reimbursementReqForSibbling[0].setActionTakenOn(new Date());
								reimbursementReqDao.update(new ReimbursementReqPk(reimbursementReqForSibbling[0].getId()), reimbursementReqForSibbling[0]);
								reimbursementReqDao.update(new ReimbursementReqPk(reimbursementReqForSibbling[0].getId()), reimbursementReqForSibbling[0]);
								Inbox inb2[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE RECEIVER_ID=? AND ASSIGNED_TO=? AND ESR_MAP_ID=?", new Object[] { singleR, singleR, reqData.getEsrMapId() });
								if (inb2.length > 0){
									for (Inbox singleInbox : inb2){
										singleInbox.setStatus(Status.REJECTED);
										singleInbox.setMessageBody(messageBody);
										singleInbox.setIsDeleted(1);
										inboxDao.update(new InboxPk(singleInbox.getId()), singleInbox);
									}
								}
							} else logger.debug("An escalation user without permission is being updated. Can't update. USER_ID: " + singleR);
						}
					}
				}
					break;
				case HOLD:{
					float onholdtamount = 0.00f;
					String onholdotalamount = "";
					EmpSerReqMap empSerReqMapDto = empSerReqMapDao.findByPrimaryKey(reqData.getEsrMapId());
					process_chain_dto = processChainDao.findByPrimaryKey(empSerReqMapDto.getProcessChainId());
					ReimbursementReq reimbursementReq = reimbursementReqDao.findByPrimaryKey(reqData.getId());
					Integer[] approversLevel1 = processEvaluator.approvers(process_chain_dto.getApprovalChain(), 1, reimbursementReq.getRequesterId());
					Integer[] approversLevel2 = processEvaluator.approvers(process_chain_dto.getApprovalChain(), 2, reimbursementReq.getRequesterId());
					Integer approversArray[] = null;
					if (reqEscalation.isEscalationAction()){
						if (reqEscalation.isSameLevel(escUserId, approversLevel1)) approversArray = approversLevel1;
						else approversArray = approversLevel2;
					} else{
						if (Arrays.asList(approversLevel1).contains(new Integer(loginDto.getUserId()))){
							approversArray = approversLevel1;
						} else{
							approversArray = approversLevel2;
						}
					}
					ProfileInfo userprofileInfo1 = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(reimbursementReq.getRequesterId()).getProfileId());
					ProfileInfo approverprofileInfo1 = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(loginDto.getUserId()).getProfileId());
					//ProfileInfo RmProfile = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(userprofileInfo1.getReportingMgr()).getProfileId());
					ProfileInfo HrProfile = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(userprofileInfo1.getHrSpoc()).getProfileId());
					ReimbursementFinancialData[] fdata1 = reimbursementFinancialDao.findWhereEsrmapIdEquals(reimbursementReq.getEsrMapId());
					mailid = new HashSet<String>();
					for (ReimbursementFinancialData f : fdata1){
						crny = f.getCurrency();
						onholdtamount = onholdtamount + Float.parseFloat(f.getAmount());
						if (f.getAmount().split("\\.")[1] != null){
							onholdotalamount = String.valueOf(onholdtamount) + "0";
						}
					}
					// Send mail Requester and CC to his HR spoc
					mailid.add(HrProfile.getOfficalEmailId());
					PortalMail portalMail = sendMailDetails("Reimbursement Request [" + empSerReqMapDto.getReqId() + "] OnHold", userprofileInfo1.getOfficalEmailId(), userprofileInfo1.getFirstName(), crny + " " + onholdotalamount, MailSettings.REIMBURSEMENT_HOLD, PortalUtility.getdd_MM_yyyy_hh_mm_a(new Date()), null, approverprofileInfo1.getFirstName(), reqData.getRemark(), null, mailid, empSerReqMapDto.getId(), null);
				//Sending Android Notification	
					String message="Reimbursement Request [" + empSerReqMapDto.getReqId() + "] OnHold";
					List<String> mailids=new ArrayList<String>();
					mailids.add(portalMail.getRecipientMailId());
					if(portalMail.getAllReceipientcCMailId()!=null){
					for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
					sendReimbursementNotification(message,mailids);
					
					String messageBody = mailGenarator.replaceFields(mailGenarator.getMailTemplate(portalMail.getTemplateName()), portalMail);
					//update approvers at same level inbox status
					////notify all approvers who are in same level
					if (approversArray.length > 0){
						for (Integer singleR : approversArray){
							ReimbursementReq reimbursementReqForSibbling[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID = ? AND ASSIGN_TO = ?", new Object[] { reqData.getEsrMapId(), singleR });
							if (reimbursementReqForSibbling.length > 0){
								reimbursementReqForSibbling[0].setActionTakenBy(loginDto.getUserId());
								reimbursementReqForSibbling[0].setActionTakenOn(new Date());
								reimbursementReqForSibbling[0].setStatus(Status.ON_HOLD);
								reimbursementReqForSibbling[0].setRemark(reqData.getRemark());
								reimbursementReqDao.update(new ReimbursementReqPk(reimbursementReqForSibbling[0].getId()), reimbursementReqForSibbling[0]);
								Inbox inb2[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE RECEIVER_ID=? AND ASSIGNED_TO=? AND ESR_MAP_ID=?", new Object[] { singleR, singleR, reqData.getEsrMapId() });
								if (inb2.length > 0){
									for (Inbox singleInbox : inb2){
										singleInbox.setStatus(Status.ON_HOLD); // prem
										singleInbox.setMessageBody(messageBody);
										inboxDao.update(new InboxPk(singleInbox.getId()), singleInbox);
									}
								}
							} else logger.debug("An escalation user without permission is being updated. Can't update. USER_ID: " + singleR);
						}
					}
				}
					break;
				case ASSIGN:{
					boolean sameapprover = false, assignActive = false;
					String remarksbyHandler = reqData.getRemark();
					ReimbursementReq req4 = reimbursementReqDao.findByPrimaryKey(requiredData.getId());
					//maintain old status
					String oldStatus = req4.getStatus();
					req4.setOldStatus(oldStatus);
					reimbursementReqDao.update(new ReimbursementReqPk(req4.getId()), req4);
					String StatusToSet = "";
					try{
						if (reqData.getStatus().equalsIgnoreCase(Status.INPROGRESS) || reqData.getStatus().equalsIgnoreCase(Status.ASSIGNED)){
							StatusToSet = reqData.getStatus();
						} else{
							StatusToSet = requiredData.getStatus().equalsIgnoreCase(Status.REQUESTRAISED) || requiredData.getStatus().equalsIgnoreCase(Status.INPROGRESS) || requiredData.getStatus().equalsIgnoreCase(Status.ASSIGNED) ? Status.COMPLETED : requiredData.getStatus().equalsIgnoreCase("Cancel Request") || requiredData.getStatus().equalsIgnoreCase(Status.CANCELINPROGRESS) ? Status.REVOKED : requiredData.getStatus();
						}
						if (StatusToSet.equalsIgnoreCase(Status.INPROGRESS) && req4.getOldStatus().equalsIgnoreCase(Status.CANCELREQUEST)){
							StatusToSet = Status.CANCELINPROGRESS;
						}
						if (StatusToSet.equalsIgnoreCase(Status.ASSIGNED) && (req4.getOldStatus().equalsIgnoreCase(Status.CANCELINPROGRESS) || req4.getOldStatus().equalsIgnoreCase(Status.CANCELREQUEST))){
							StatusToSet = Status.CANCELREQUEST;
							assignActive = true;
						}
						req4.setStatus(StatusToSet);
						req4.setRemark(remarksbyHandler);
						ReimbursementReqPk reimbursementReqPk = new ReimbursementReqPk(req4.getId());
						req4.setActionTakenBy(loginDto.getUserId());
						req4.setActionTakenOn(new Date());
						req4.setActiveNull(true);
						if (StatusToSet.equalsIgnoreCase(Status.ASSIGNED) || assignActive){
							if (!(loginDto.getUserId() == reqData.getAssignedTo())){
								req4.setActive((short) 1); //1 means dont allow to take action
							}
						}
						reimbursementReqDao.update(reimbursementReqPk, req4);
						Inbox in1[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE RECEIVER_ID=? AND ASSIGNED_TO=? AND ESR_MAP_ID=?", new Object[] { loginDto.getUserId(), loginDto.getUserId(), req4.getEsrMapId() });
						if (in1.length > 0){
							in1[0].setStatus(StatusToSet);
							inboxDao.update(new InboxPk(in1[0].getId()), in1[0]);
						}
						EmpSerReqMap empSerReqMapDto = empSerReqMapDao.findByPrimaryKey(reqData.getEsrMapId());
						process_chain_dto = processChainDao.findByPrimaryKey(empSerReqMapDto.getProcessChainId());
						Integer[] handlers = processEvaluator.handlers(process_chain_dto.getHandler(), req4.getRequesterId());
						Integer array[] = getSibblings(handlers, loginDto.getUserId());
						for (Integer singleR : array){
					//	if(singleR==reqData.getAssignedTo()){
							ReimbursementReq reimbursementReqForSibbling[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID = ? AND ASSIGN_TO = ?", new Object[] { req4.getEsrMapId(), singleR });
							if (reimbursementReqForSibbling.length > 0){
								reimbursementReqForSibbling[0].setStatus(StatusToSet);
								reimbursementReqForSibbling[0].setActionTakenBy(req4.getActionTakenBy());
								reimbursementReqForSibbling[0].setActionTakenOn(req4.getActionTakenOn());
								reimbursementReqForSibbling[0].setCreateDate(reqData.getCreateDate());
								reimbursementReqForSibbling[0].setRemark(req4.getRemark());
								reimbursementReqForSibbling[0].setActiveNull(true);
								reimbursementReqForSibbling[0].setOldStatus(oldStatus);
								if (StatusToSet.equalsIgnoreCase(Status.INPROGRESS) || StatusToSet.equalsIgnoreCase(Status.CANCELINPROGRESS)){
									reimbursementReqForSibbling[0].setActive((short) 1); //1 means dont allow to take action
								}
								if (StatusToSet.equalsIgnoreCase(Status.ASSIGNED) || StatusToSet.equalsIgnoreCase(Status.CANCELREQUEST) || StatusToSet.equalsIgnoreCase(Status.CANCELINPROGRESS)){
									if (!(reimbursementReqForSibbling[0].getAssignTo() == reqData.getAssignedTo())){
										reimbursementReqForSibbling[0].setActive((short) 1); //1 means dont allow to take action
									}
								}
								reimbursementReqDao.update(new ReimbursementReqPk(reimbursementReqForSibbling[0].getId()), reimbursementReqForSibbling[0]);
								Inbox inb2[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE RECEIVER_ID=? AND ASSIGNED_TO!=? AND ESR_MAP_ID=?", new Object[] { singleR, loginDto.getUserId(), req4.getEsrMapId() });
								if (inb2.length > 0){
									for (Inbox singleInbox : inb2){
										if (singleInbox.getAssignedTo() != loginDto.getUserId()){
											singleInbox.setStatus(StatusToSet);
											inboxDao.update(new InboxPk(singleInbox.getId()), singleInbox);
										}
									}
								}
							} else logger.debug("REIMBURSEMENT_REQ not found for ESR_MAP_ID: " + req4.getEsrMapId() + ", ASSIGN_TO: " + singleR);
					//	}
						}
						if (StatusToSet.equalsIgnoreCase(Status.REVOKED)){
							ReimbursementReq reimbursementReqAllCancel[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID = ?", new Object[] { req4.getEsrMapId() });
							for (ReimbursementReq reimbursementReqAll : reimbursementReqAllCancel){
								reimbursementReqAll.setStatus(StatusToSet);
								reimbursementReqDao.update(new ReimbursementReqPk(reimbursementReqAll.getId()), reimbursementReqAll);
								Inbox inb2[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE ESR_MAP_ID=?", new Object[] { req4.getEsrMapId() });
								if (inb2.length > 0){
									for (Inbox singleInbox : inb2){
										if (singleInbox.getAssignedTo() != loginDto.getUserId()){
											singleInbox.setStatus(StatusToSet);
											singleInbox.setAssignedTo(0);// moving to notifications
											//if (!(singleInbox.getAssignedTo() == 0) || !(singleInbox.getReceiverId() == singleInbox.getRaisedBy())) singleInbox.setIsDeleted(1);
											inboxDao.update(new InboxPk(singleInbox.getId()), singleInbox);
										}
									}
								}
							}
						}//if status is revoked
						/**
						 * if status is inprogress change requestor status to in-progress
						 */
						if (StatusToSet.equalsIgnoreCase(Status.INPROGRESS) || StatusToSet.equalsIgnoreCase(Status.COMPLETED) || StatusToSet.equalsIgnoreCase(Status.CANCELINPROGRESS) || StatusToSet.equalsIgnoreCase(Status.CANCELREQUEST)){
							Inbox inbInp[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE ESR_MAP_ID=? AND ASSIGNED_TO= ? AND RECEIVER_ID=?", new Object[] { req4.getEsrMapId(), 0, req4.getRequesterId() });
							if (inbInp.length > 0){
								for (Inbox singleInbox : inbInp){
									singleInbox.setStatus(StatusToSet.equalsIgnoreCase(Status.INPROGRESS) ? Status.PROCESSING : StatusToSet);
									inboxDao.update(new InboxPk(singleInbox.getId()), singleInbox);
								}
							}
						}
						/**
						 * Delete all inbox entries related to this request once completed
						 */
						if (StatusToSet.equalsIgnoreCase(com.dikshatech.common.utils.Status.COMPLETED) || StatusToSet.equalsIgnoreCase(com.dikshatech.common.utils.Status.REVOKED)){
							Inbox deleteInbox[] = inboxDao.findByDynamicSelect("SELECT * FROM INBOX WHERE ESR_MAP_ID=? AND ASSIGNED_TO!=0", new Object[] { req4.getEsrMapId() });
							if (deleteInbox != null && deleteInbox.length > 0){
								for (Inbox singleInbox : deleteInbox){
									inboxDao.delete(new InboxPk(singleInbox.getId()));
								}
							}
						}
						/**
						 * Email to requestor and CC HRSPOC and RM
						 */
						if (StatusToSet.equalsIgnoreCase(Status.COMPLETED)){
							Users requestor = usersDao.findByPrimaryKey(req4.getRequesterId());
							ProfileInfo requestorProfileInfo = profileInfoDao.findByPrimaryKey(requestor.getProfileId());
							mailid = new HashSet<String>();
							//requestor HRSPOC
							Users requestorHRSPOC = usersDao.findByPrimaryKey(requestorProfileInfo.getHrSpoc());
							ProfileInfo requestorHRSPOCProfileInfo = profileInfoDao.findByPrimaryKey(requestorHRSPOC.getProfileId());
							mailid.add(requestorHRSPOCProfileInfo.getOfficalEmailId());
							//requestor RM
							Users requestorRM = usersDao.findByPrimaryKey(requestorProfileInfo.getReportingMgr());
							ProfileInfo requestorRMProfileInfo = profileInfoDao.findByPrimaryKey(requestorRM.getProfileId());
							mailid.add(requestorRMProfileInfo.getOfficalEmailId());
							ReimbursementFinancialData[] fData = reimbursementFinancialDao.findWhereEsrmapIdEquals(req4.getEsrMapId());
							double totalamt1 = 0;
							//String crny1 = "";
							for (ReimbursementFinancialData f : fData){
								crny = f.getCurrency();
								totalamt1 = totalamt1 + Double.parseDouble((f.getAmount()));
								if (f.getAmount().split("\\.")[1] != null){
									totalamount = totalamt1;
									
								}
							}
							EmpSerReqMap empSerReqMap = empSerReqMapDao.findByPrimaryKey(req4.getEsrMapId());
							PortalMail portalMail=sendMailDetails(oldStatus.equalsIgnoreCase("Cancel Request") ? "Your Reimbursement request "+empSerReqMap.getReqId()+" is cancelled" : "Your Reimbursement request "+empSerReqMap.getReqId()+" is processed", requestorProfileInfo.getOfficalEmailId(), requestorProfileInfo.getFirstName(), totalamt1 + " "+crny, MailSettings.REIMBURSEMENT_PROCESSED, new Date().toString(), null, null, null, null, mailid, req4.getEsrMapId(), null);
							//Sending Android Notification	
							String message=oldStatus.equalsIgnoreCase("Cancel Request") ? "Your Reimbursement request "+empSerReqMap.getReqId()+" is cancelled" : "Your Reimbursement request "+empSerReqMap.getReqId()+" is processed";
							List<String> mailids=new ArrayList<String>();
							mailids.add(portalMail.getRecipientMailId());
							if(portalMail.getAllReceipientcCMailId()!=null){
							for(String mIds:portalMail.getAllReceipientcCMailId()) mailids.add(mIds);}
							sendReimbursementNotification(message,mailids);
						}
					} catch (Exception e){
						if (sameapprover){
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.arrayoutofbound"));
						} else{
							result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
						}
						e.printStackTrace();
					}
				}//case assign ends
					break;
			}
		} catch (ArrayIndexOutOfBoundsException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.arrayoutofbound"));
			e.printStackTrace();
		} catch (EmpSerReqMapDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (ReimbursementReqDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (ReimbursementFinancialDataDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtoinsert"));
			e.printStackTrace();
		} catch (ProfileInfoDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.profileInfo"));
			e.printStackTrace();
		} catch (Exception e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}
		return result;
	}

	private Integer[] getSibblings(Integer[] approvers, int id) {
		List<Integer> list = new LinkedList<Integer>(Arrays.asList(approvers));
		java.util.Iterator it = list.iterator();
		while (it.hasNext()){
			Integer element = (Integer) it.next();
			if (element.equals(Integer.valueOf(id))){
				list.remove(element);
				break;
			}
		}
		return list.toArray(new Integer[0]);
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer[] upload(List<FileItem> fileItems, String docType, int id, HttpServletRequest request, String descriptions) {
		Integer fieldsId[] = new Integer[fileItems.size()];
		DocumentTypes dTypes = DocumentTypes.valueOf(docType);
		// Upload the File.
		switch (dTypes) {
			case REIMBURSEMENTLIST:
				List<Integer> notUploaded = new ArrayList<Integer>();
				if (fileItems != null && !fileItems.isEmpty()){
					FileItem file = (FileItem) fileItems.get(0);
					InputStream stream = null;
					try{
						stream = file != null ? file.getInputStream() : null;
					} catch (IOException e1){
						e1.printStackTrace();
					}
					if (stream != null) try{
						Vector<Vector<Object>> list = POIParser.parseXls(stream, 0);
						stream.close();
						if (list != null && !list.isEmpty()){
							int i = 0;
							ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
							EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
							ProcessEvaluator processEvaluator = new ProcessEvaluator();
							ReimbursementReqDao reimbursementReqDao = ReimbursementReqDaoFactory.create();
							for (Vector<Object> row : list){
								i++;
								try{
									String status = ((String) row.get(7)).replaceAll(" ", "");
									if (status.equalsIgnoreCase(Status.COMPLETED) || status.equalsIgnoreCase(Status.REVOKED)){
										int esrmpId = ((Double) row.get(0)).intValue();
										EmpSerReqMap empSerReqMapDto = empSerReqMapDao.findByPrimaryKey(esrmpId);
										ProcessChain process_chain_dto = processChainDao.findByPrimaryKey(empSerReqMapDto.getProcessChainId());
										Integer[] handlers = processEvaluator.handlers(process_chain_dto.getHandler(), empSerReqMapDto.getRequestorId());
										ModelUtiility.getInstance().deleteInboxEntries(esrmpId);
										boolean isCancelReq = false;
										for (Integer singleR : handlers){
											ReimbursementReq reimbursementReqForSibbling[] = reimbursementReqDao.findByDynamicSelect("SELECT * FROM REIMBURSEMENT_REQ WHERE ESR_MAP_ID = ? AND ASSIGN_TO = ?", new Object[] { esrmpId, singleR });
											if (reimbursementReqForSibbling[0].getStatus().equals(Status.CANCELREQUEST)) isCancelReq = true;
											reimbursementReqForSibbling[0].setOldStatus(reimbursementReqForSibbling[0].getStatus());
											reimbursementReqForSibbling[0].setStatus(reimbursementReqForSibbling[0].getStatus().equals(Status.CANCELREQUEST) ? Status.REVOKED : Status.COMPLETED);
											reimbursementReqForSibbling[0].setActionTakenBy(id);
											reimbursementReqForSibbling[0].setActionTakenOn(new Date());
											try{
												reimbursementReqForSibbling[0].setRemark("Ref: " + ((String) row.get(11)).replaceAll(" ", ""));
											} catch (ClassCastException e){
												reimbursementReqForSibbling[0].setRemark("Ref: " + (((Double) row.get(11)).intValue() + "").replaceAll(" ", ""));
											}
											reimbursementReqForSibbling[0].setActiveNull(true);
											reimbursementReqDao.update(new ReimbursementReqPk(reimbursementReqForSibbling[0].getId()), reimbursementReqForSibbling[0]);
										}
										if (isCancelReq) JDBCUtiility.getInstance().update("UPDATE REIMBURSEMENT_REQ SET STATUS=? WHERE ESR_MAP_ID=?", new Object[] { Status.REVOKED, esrmpId });
									} else notUploaded.add(i);
								} catch (Exception e){
									notUploaded.add(i);
								}
							}
						}
					} catch (Exception e){
						notUploaded = new ArrayList<Integer>();
						notUploaded.add(-1);
					}
				}
				Integer in[] = new Integer[notUploaded.size() == 0 ? 1 : notUploaded.size()];
				in[0] = 0;
				for (int i = 0, l = notUploaded.size(); i < l; i++)
					in[i] = notUploaded.get(i);
				return in;
			case REIMBURSEMENT:
				PortalData portalData = new PortalData();
				try{
					String Upload_fileName = "";
					// if user attached file then upload otherwise not.
					int i = 0;
					for (FileItem file : fileItems){
						if (file.getName() != null){
							Upload_fileName = portalData.saveFile(file, dTypes, id);
							if (Upload_fileName != null && !Upload_fileName.equalsIgnoreCase("")){
								Documents documents = new Documents();
								DocumentsDao documentsDao = DocumentsDaoFactory.create();
								documents.setFilename(Upload_fileName);
								documents.setDoctype(docType);
								documents.setDescriptions(descriptions);
								DocumentsPk documentsPk = documentsDao.insert(documents);
								fieldsId[i] = documentsPk.getId();
								i++;
								logger.info("File :" + file.getName() + "successfully uploaded :");
							}
						}
					}
					logger.info("The no of files uploaded  :" + fieldsId.length);
				} catch (FileNotFoundException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentsDaoException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
		}
		return fieldsId;
	}

	public PortalMail sendMailDetails(String mailSubject, String mailId, String candidateName, String amount, String templateName, String date, String requesterId, String approverName, String reason, String EmpFname, Set<String> ccmailIds, int empserReq, String hrspoc) {
		ReimbursementReqDao reimbursementReqDao = ReimbursementReqDaoFactory.create();
		ReimbursementFinancialDataDao reimbursementFinancialDataDao = ReimbursementFinancialDataDaoFactory.create();
		EmpSerReqMapDao empSerReqMapDao = EmpSerReqMapDaoFactory.create();
		LevelsDao levelsDao = LevelsDaoFactory.create();
		DivisonDao divisonDao = DivisonDaoFactory.create();
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		Levels levels=null;
		Users user=null;
		ProfileInfo reimbuOtherEmpDetails =null;
		try{
			PortalMail portalMail = new PortalMail();
			if (mailId != null){
			portalMail.setRecipientMailId(mailId);
				
			} else portalMail.setRecipientMailId("");
			if (mailSubject != null){
				portalMail.setMailSubject(mailSubject);
			} else portalMail.setMailSubject(" ");
			if (candidateName != null){
				portalMail.setCandidateName(candidateName);
			} else portalMail.setCandidateName(" ");
			if (amount != null){
				portalMail.setAmount("<b>"+amount+"</b>");
			} else portalMail.setAmount(" ");
			if (EmpFname != null){
				portalMail.setEmpFname(EmpFname);
			} else portalMail.setEmpFname(" ");
			portalMail.setTemplateName(templateName);
			if (approverName != null){
				portalMail.setApproverName(approverName);
			} else portalMail.setApproverName(" ");
			if (requesterId != null){
				portalMail.setRequesterId(requesterId);
			} else portalMail.setRequesterId(" ");
			if (date != null){
				portalMail.setDate(date);
			} else portalMail.setDate(" ");
			
			if (empserReq != 0){
				EmpSerReqMap empSerReqMap = empSerReqMapDao.findByPrimaryKey(empserReq);
				portalMail.setReqID(empSerReqMap.getReqId());
				ReimbursementReq reimbursementReq = reimbursementReqDao.findWhereEsrMapIdEquals(empserReq)[0];
				
				ReimbursementFinancialData reimbursementFinancialData[] = reimbursementFinancialDataDao.findWhereEsrmapIdEquals(empserReq);
				String sql = "select * from LEVELS where ID=(select LEVEL_ID from USERS where ID=?)";
				Users user1=null;
				user = usersDao.findByPrimaryKey(reimbursementReq.getRequesterId());
				portalMail.setEmployeeId(user.getEmpId());
			
				if((templateName.equalsIgnoreCase("REIMBURSEMENT_HR_OR_RM_MAIL.html") || templateName.equalsIgnoreCase("REIMBURSEMENT_IN_FINANCE.html") || templateName.equalsIgnoreCase("REIMBURSEMENT_APPROVAL_RM.html") || templateName.equalsIgnoreCase("REIMBURSEMENT_USER_HR.html")) && reimbursementReq.getReimbuFlag().equals("OTHER")){
					if(reimbursementReq.getOTHER_EMP_NAME()>0 && reimbursementReq.getOTHER_EMP_NAME()!=reimbursementReq.getPaymentMadeToEmpId()){
					 user1 = usersDao.findByPrimaryKeys(reimbursementReq.getOTHER_EMP_NAME());
					}
					else{
						 user1 = usersDao.findByPrimaryKeys(reimbursementReq.getPaymentMadeToEmpId());
					}
					 reimbuOtherEmpDetails = profileInfoDao.findByPrimaryKey(user1.getProfileId());
					levels = levelsDao.findByDynamicSelect(sql, new Object[] { (user1.getId()) })[0];
					Divison divison = divisonDao.findByPrimaryKey(new DivisonPk(levels.getDivisionId()));
					if (divison.getParentId() > 0){
						Divison divisonEmp = divisonDao.findByPrimaryKey(new DivisonPk(divison.getId()));
						portalMail.setEmpDivision(divisonEmp.getName());
					} else{
						portalMail.setEmpDivision("N.A");
					}
					int parentId = divison.getParentId();
					while (parentId != 0){
						divison = divisonDao.findByPrimaryKey(new DivisonPk(parentId));
						parentId = divison.getParentId();
					}
					
					portalMail.setEmpDepartment(divison.getName());
					portalMail.setEmpDesignation(levels.getDesignation());
					portalMail.setReimbuEmployeename(reimbuOtherEmpDetails.getFirstName());
					portalMail.setReimbuEmployeeId(user1.getEmpId()+"");
				}	
				
				else{
				 levels = levelsDao.findByDynamicSelect(sql, new Object[] { (reimbursementReq.getRequesterId()) })[0];
				Divison divison = divisonDao.findByPrimaryKey(new DivisonPk(levels.getDivisionId()));
				if (divison.getParentId() > 0){
					Divison divisonEmp = divisonDao.findByPrimaryKey(new DivisonPk(divison.getId()));
					portalMail.setEmpDivision(divisonEmp.getName());
				} else{
					portalMail.setEmpDivision("N.A");
				}
				int parentId = divison.getParentId();
				while (parentId != 0){
					divison = divisonDao.findByPrimaryKey(new DivisonPk(parentId));
					parentId = divison.getParentId();
				}
				portalMail.setEmpDepartment(divison.getName());
				portalMail.setEmpDesignation(levels.getDesignation());
				
				
			
				
				portalMail.setReimbuEmployeename(candidateName);
				portalMail.setReimbuEmployeeId(user.getEmpId()+"");
				}
		
				portalMail.setSerReqID(empSerReqMap.getReqId());
				StringBuffer reimBuffer = null;
				int h=1;
				for (ReimbursementFinancialData reimbursementdata : reimbursementFinancialData){
					
					if (reimBuffer == null){
						reimBuffer = new StringBuffer("<br>1.&nbsp;&nbsp;"+reimbursementdata.getType()+"&nbsp;-&nbsp;"+reimbursementdata.getCurrency()+"&nbsp;"+reimbursementdata.getAmount());
					} else{
						h++;
						reimBuffer.append("<br>"+h+".&nbsp;&nbsp;"+ reimbursementdata.getType()+"&nbsp;-&nbsp;"+reimbursementdata.getCurrency()+"&nbsp;"+reimbursementdata.getAmount());
					}
				}
				portalMail.setReimbursementType(reimBuffer.toString());
			} else{
				portalMail.setEmployeeId(0);
				portalMail.setReimbursementType("");
				portalMail.setReqID(" ");
			}
			// sending mail with CCmailids
			if (ccmailIds != null && ccmailIds.isEmpty() == false){
				String[] stringArray = Arrays.copyOf(ccmailIds.toArray(), ccmailIds.toArray().length, String[].class);
				portalMail.setAllReceipientcCMailId(stringArray);
			}
			if (reason != null){
				portalMail.setReason(reason);
			} else{
				portalMail.setReason(" ");
			}
			portalMail.setHrSPOC(hrspoc == null ? "" : hrspoc);
			if (mailId!= null && mailId.contains("@")){
				MailGenerator mailGenarator = new MailGenerator();
				mailGenarator.invoker(portalMail);
				
			}
			return portalMail;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private String[] getEmployeeDetails(int userId) {
		String divName = "N.A", depName = "N.A";
		try{
			DivisonDao divisionDao = DivisonDaoFactory.create();
			Divison division = divisionDao.findByDynamicSelect("SELECT D.* FROM DIVISON D JOIN LEVELS L ON L.DIVISION_ID=D.ID JOIN USERS U ON U.LEVEL_ID=L.ID AND U.ID=?", new Object[] { userId })[0];
			if (division != null){
				if (division.getParentId() != 0){
					divName = (division.getName());
					while (division.getParentId() != 0){
						division = divisionDao.findByPrimaryKey(division.getParentId());
					}
					if (division != null) depName = (division.getName());
				} else depName = (division.getName());
			}
		} catch (Exception e){}
		return new String[] { depName, divName };
	}

	public Attachements download(PortalForm form) {
		Attachements attachements = new Attachements();
		ReimbursementRequestForm req = (ReimbursementRequestForm) form;
		String seprator = File.separator;
		String path = "Data" + seprator;
		path = PropertyLoader.getEnvVariable() + seprator + path;
		// Get filename from id
		PortalData portalData = new PortalData();
		Login login = (Login) form.getObject();
		ReimbursementRequestForm reimbuForm = (ReimbursementRequestForm) form;
		
        
        String flag1=reimbuForm.getBankFlag();
        String brrid=reimbuForm.getRemesrMapId();
		String reeq = reimbuForm.getReqId();
		
		
		path = portalData.getfolder(path);
		switch (DownloadTypes.getValue(form.getdType())) {
			case REIMBURSEMENTLIST:
				try{
					Login login1 = (Login) form.getObject();
					ReimbursementReq reimbursementReqs[] = ReimbursementReqDaoFactory.create().findByDynamicQuery("SELECT * FROM REIMBURSEMENT_REQ  WHERE STATUS NOT IN ('Revoked','Completed') AND  ASSIGN_TO=" + login1.getUserId());
					ArrayList<String[]> list = new ArrayList<String[]>();
					if (reimbursementReqs != null && reimbursementReqs.length > 0){
						ReimbursementFinancialDataDao finDao = ReimbursementFinancialDataDaoFactory.create();
						for (ReimbursementReq reimbursementReq : reimbursementReqs){
							try{
								String req_id = (String) (JDBCUtiility.getInstance().getSingleColumn("SELECT REQ_ID FROM EMP_SER_REQ_MAP WHERE ID = ?", new Object[] { reimbursementReq.getEsrMapId() }).get(0));
								String emp_id = ((Integer) (JDBCUtiility.getInstance().getSingleColumn("SELECT EMP_ID FROM USERS WHERE ID = ?", new Object[] { reimbursementReq.getRequesterId() }).get(0))).toString();
								String name = ModelUtiility.getInstance().getEmployeeName(reimbursementReq.getRequesterId());
								String[] dep = getEmployeeDetails(reimbursementReq.getRequesterId());
								ReimbursementFinancialData[] finData = finDao.findWhereEsrmapIdEquals(reimbursementReq.getEsrMapId());
								String currency = "IND";
								String onDate = "";
								double total = 0;
								for (ReimbursementFinancialData data : finData){
									total += Double.parseDouble(data.getAmount());
									currency = data.getCurrency();
									onDate = PortalUtility.getdd_MM_yyyy(data.getDateOfOccurrence());
								}
								String entry[] = new String[] { reimbursementReq.getEsrMapId() + "", req_id, emp_id, name, total + "", currency, onDate, reimbursementReq.getStatus(), dep[0], dep[1], reimbursementReq.getProjectName() };
								list.add(entry);
							} catch (Exception e){
								logger.error("unable to get required fields to genarate reiembursement request list ", e);
							}
						}
					}
					attachements.setFileName(new GenerateXls().generateXlsRMReport(list, path + File.separator + "RM_" + PortalUtility.getdd_MM_yyyy(new Date()) + "_List.xls"));
					path = path + File.separator + attachements.getFileName();
					attachements.setFilePath(path);
				} catch (Exception e){
					// TODO: handle exception
				}
				break;
			default:
				try{
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
					Documents docNew = new Documents();
					docNew = documentsDao.findByPrimaryKey(req.getDocId());
					attachements.setFileName(docNew.getFilename());
					attachements.setFilePath(path + seprator + docNew.getFilename());
				} catch (Exception e){
					e.printStackTrace();
				}
				break;
		
		case  REIMBURSEMENTLISTD:{
    	   
	     if(flag1!=null)
	     {
		  String[] str=brrid.split(",");
	        ArrayList<Integer> arraylist=new ArrayList<Integer>();
	        for(String w:str){
	        	
	        	Integer x=Integer.valueOf(w);
	        	arraylist.add(x);
	        	
	        }
	        
	        String[] strr=reeq.split(",");
	        ArrayList<Integer> arraylistr=new ArrayList<Integer>();
	        for(String w:str){
	        	
	        	Integer x=Integer.valueOf(w);
	        	arraylistr.add(x);
	        	
	        }
		
		if (flag1.equals("HDFC")){
			
			try{
				attachements.setFileName(new GenerateXls().reimbursementReportHdfc(ReimbursementReqDaoFactory.create().findInternalReportDataHDFC(arraylist,reimbuForm.getBankFlag(),reimbuForm.getReqId()), path));
			} catch (Exception e){
				logger.error("unable to generate reimbursement report list", e);
			}
		}
		  else {
			
			try{
				attachements.setFileName(new GenerateXls().reimbursementReportNonHdfc(ReimbursementReqDaoFactory.create().findInternalReportDataNONHDFC(arraylist,reimbuForm.getBankFlag(),reimbuForm.getReqId()),path));
			
			} catch (Exception e){
				logger.error("unable to generate reimbursement report list", e);
			}
		
			
		  }	
	}
	else {
		try{
			attachements.setFileName(new GenerateXls().generateXlsRMReport(ReimbursementReqDaoFactory.create().findInternalReportData(reimbuForm.getEsrMapId()), path + File.separator + "Reimbursement Report_" + ".xls"));
		} catch (Exception e){
			logger.error("unable to generate reimbursement report list", e);
		}
		
		
	}
	
	
}
path = path + File.separator + attachements.getFileName();
attachements.setFilePath(path);
	

return attachements;
}
		return attachements;
		}
	
	
	
	/*public Attachements downloadReport(PortalForm form) {
		Login login = (Login) form.getObject();
		ReimbursementRequestForm reimbuForm = (ReimbursementRequestForm) form;
		
        
        String flag1=reimbuForm.getBankFlag();
        String brrid=reimbuForm.getEsrMapId()+"";

		Attachements attachements = new Attachements();
		PortalData portalData = new PortalData();
		String path = portalData.getfolder(portalData.getDirPath()) + "temp";
		try{
			File file = new File(path);
			if (!file.exists()) file.mkdir();
			switch (DownloadTypes.getValue(form.getdType())) {
				case REIMBURSEMENTLISTD:
					if(flag1!=null){
						  String[] str=brrid.split(",");
					        ArrayList<Integer> arraylist=new ArrayList<Integer>();
					        for(String w:str){
					        	
					        	Integer x=Integer.valueOf(w);
					        	arraylist.add(x);
					        	
					        }
						
						if (flag1.equals("HDFC")){
							
							try{
								attachements.setFileName(new GenerateXls().reimbursementReportHdfc(ReimbursementReqDaoFactory.create().findInternalReportDataHDFC(arraylist,reimbuForm.getBankFlag()), path + File.separator + "Reimbursement  Report_HDFC" + ".xls", path));
							} catch (Exception e){
								logger.error("unable to generate reimbursement report list", e);
							}
						}
						  else {
							
							try{
								attachements.setFileName(new GenerateXls().reimbursementReportNonHdfc(ReimbursementReqDaoFactory.create().findInternalReportDataNONHDFC(arraylist,reimbuForm.getBankFlag()), path + File.separator + "Reimbursement Report_NONHDFC"  + ".xls", path));
							} catch (Exception e){
								logger.error("unable to generate reimbursement report list", e);
							}
						
							
						  }	
					}
					else {
						try{
							attachements.setFileName(new GenerateXls().generateXlsRMReport(ReimbursementReqDaoFactory.create().findInternalReportData(reimbuForm.getEsrMapId()), path + File.separator + "Reimbursement Report_" + ".xls"));
						} catch (Exception e){
							logger.error("unable to generate reimbursement report list", e);
						}
						
						
					}
					
					
			}
			path = path + File.separator + attachements.getFileName();
			attachements.setFilePath(path);
					
		 }catch (Exception e){
			e.printStackTrace();
			logger.error("unable to generate reimbursement report " +e.getMessage());
		}
		return attachements;
	}*/

	private Inbox sendToInboxForReimbursement(int esrMapId, int rembursementReqId, String subject, String status, int userId, String messageBody, boolean forUser) {
		Inbox inbox = new Inbox();
		ReimbursementReqDao reimbursementReqDao = ReimbursementReqDaoFactory.create();
		try{
			String sql = "SELECT * FROM REIMBURSEMENT_REQ WHERE ID=? AND ESR_MAP_ID=? ORDER BY CREATE_DATE DESC";
			ReimbursementReq rmReq = reimbursementReqDao.findByDynamicSelect(sql, new Object[] { rembursementReqId, esrMapId })[0];
			inbox.setSubject(subject);
			inbox.setAssignedTo(forUser ? 0 : rmReq.getAssignTo());
			inbox.setRaisedBy(rmReq.getRequesterId());
			inbox.setStatus(status);
			inbox.setCreationDatetime(rmReq.getCreateDate());
			inbox.setIsRead(0);
			inbox.setIsDeleted(0);
			inbox.setCategory("REIMBURSEMENT");
			inbox.setEsrMapId(esrMapId);
			inbox.setMessageBody(messageBody);
			try{
				InboxDao inboxDao = InboxDaoFactory.create();
				// send request to assigned person
				inbox.setReceiverId(userId);
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

	protected boolean checkEscalationActorBySequence(int processChainId, int sequence, int raisedBy, int esrMapId, ProcessEvaluator processEvaluator) {
		String whereInbox = "RECEIVER_ID=ASSIGNED_TO AND IS_DELETED!=1 AND IS_ESCALATED=1 AND ESR_MAP_ID=? AND ASSIGNED_TO IN (";
		boolean result = false;
		try{
			ProcessChainDao processChainProvider = ProcessChainDaoFactory.create();
			ProcessChain processChain = processChainProvider.findByPrimaryKey(processChainId);
			InboxDao inboxProvider = InboxDaoFactory.create();
			Inbox[] inboxEntries = null;
			reqEscalation.disableEscalationAction(processEvaluator);
			Integer[] levelUserIds = processEvaluator.approvers(processChain.getApprovalChain(), sequence, raisedBy);
			reqEscalation.enableEscalationAction(processEvaluator);
			String actors = "";
			for (Integer element : levelUserIds){
				actors += element + ",";
			}
			if (actors.length() > 0) actors = actors.substring(0, actors.lastIndexOf(","));
			else actors = "0";
			whereInbox += actors + ")";
			inboxEntries = inboxProvider.findByDynamicWhere(whereInbox, new Object[] { esrMapId });
			if (inboxEntries.length > 0){
				result = true;
			}
		} catch (InboxDaoException ex){
			logger.error("Unable to check escalation sequence. Inbox error: " + ex.getMessage());
		} catch (ProcessChainDaoException ex){
			logger.error("Unable to check escalation sequence. Process chain error: " + ex.getMessage());
		}
		return (result);
			
	}

	
	
	
	private void sendReimbursementNotification(String message,List<String> mailIds)	{
		LoginModel lm=new LoginModel();
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
		lm.sendGcmNotification(message,ids,"REIMBURSEMENT");
	}
	}
	
