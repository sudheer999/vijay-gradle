package com.dikshatech.portal.models;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.HandlerAction;
import com.dikshatech.beans.SodexoBean;
import com.dikshatech.common.utils.GenerateXls;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.common.utils.RequestEscalation;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.SodexoDenominationsDao;
import com.dikshatech.portal.dao.SodexoDetailsDao;
import com.dikshatech.portal.dao.SodexoReqDao;
import com.dikshatech.portal.dao.StatusDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.DivisonPk;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Handlers;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.InboxPk;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.LevelsPk;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProcessChainPk;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.ProfileInfoPk;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.SodexoDetails;
import com.dikshatech.portal.dto.SodexoDetailsPk;
import com.dikshatech.portal.dto.SodexoReq;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.UsersPk;
import com.dikshatech.portal.exceptions.DivisonDaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.LevelsDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.exceptions.SodexoDenominationsDaoException;
import com.dikshatech.portal.exceptions.SodexoDetailsDaoException;
import com.dikshatech.portal.exceptions.SodexoMailException;
import com.dikshatech.portal.exceptions.SodexoReqDaoException;
import com.dikshatech.portal.exceptions.SodexoRollbackException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.SodexoDenominationsDaoFactory;
import com.dikshatech.portal.factory.SodexoDetailsDaoFactory;
import com.dikshatech.portal.factory.SodexoReqDaoFactory;
import com.dikshatech.portal.factory.StatusDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;
import com.dikshatech.portal.timer.ParseTemplate;



public class SodexoModel extends ActionMethods {
	
	private static Logger logger = LoggerUtil.getLogger(SodexoModel.class);
	private RequestEscalation reqEscalation=new RequestEscalation();
	
	SodexoDetailsDao sodexoDetailsDao = SodexoDetailsDaoFactory.create();
	SodexoReqDao sodexoReqDao = SodexoReqDaoFactory.create();
	EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
	ProcessChainDao processChainDao = ProcessChainDaoFactory.create();
	ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
	InboxDao inboxDao = InboxDaoFactory.create();
	
	Hashtable<Integer,String> idReqIdTable;//ID(key)   REQUEST_ID(value)
	Hashtable<Integer,Integer> sodexIdEsrMapIdTable;//SD_ID(key)      ESR_MAP_ID(value)
		
	SodexoDetails[] submittedSodexoRequest;//contains rows fetched from SODEXO_DETAILS for SUBMITTED data
	SodexoReq[] sodexoReqDetails;//contains rows fetched from SODEXO_REQ	
	Login login;
		
	
	
	
	@Override
	public ActionResult defaultMethod(PortalForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request){
		EmpSerReqMapDao empSerReqDao = EmpSerReqMapDaoFactory.create();
		ActionResult result = new ActionResult();
		login = Login.getLogin(request);
		SodexoDetails detailsDto = (SodexoDetails) form;
		DropDown dropdown = new DropDown();
		List<SodexoBean> sodexoResultList = new ArrayList<SodexoBean>();
		
		//Set escalation action
		reqEscalation.setEscalationAction(detailsDto.getEsrMapId(), login.getUserId());
		
		switch (ActionMethods.ReceiveTypes.getValue(detailsDto.getrType())) {

		//my-request
		case RECEIVEALL:
			
			try {
				EmpSerReqMap[] esrMapDetails = empSerReqDao.findByDynamicWhere(" REQUESTOR_ID=? AND REQ_TYPE_ID=? ORDER BY SUB_DATE DESC",new Object[] { login.getUserId(),new Integer(3) });
				if ((esrMapDetails != null) && esrMapDetails.length > 0) {
					sodexoResultList = extractIdsAndPopulateSodexoBean(esrMapDetails);					
				}	
				
				//fetch and send Sodexo-denominations				
				SodexoDenominationsDao denomDao = SodexoDenominationsDaoFactory.create();
				dropdown.setDetail(Arrays.asList(denomDao.findAll()));
				
				dropdown.setDropDown(sodexoResultList.toArray());
				request.setAttribute("actionForm", dropdown);
			}catch (EmpSerReqMapDaoException ex) {
				logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH ESR ROW FOR RQUESTER_ID"+login.getUserId(), ex);
			} catch (SodexoDenominationsDaoException ex) {
				logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH SODEXO_DENOMINATIONS ", ex);				
			}
			
			break;
			
			//my-request
		case RECEIVE:
					
			SodexoBean receiveBean = new SodexoBean();
			try {
				SodexoDetails detailsInfo =sodexoDetailsDao.findByPrimaryKey(new SodexoDetailsPk(detailsDto.getId()));													
				receiveBean.setRequestId(detailsDto.getReqId());	//UI must send this				
				receiveBean.setAmountAvailed(detailsInfo.getAmountAvailed());
				receiveBean.setAmountEligible(detailsInfo.getAmountEligible());
				receiveBean.setBalanceAmount(detailsInfo.getAmountEligible() - detailsInfo.getAmountAvailed());
				//receiveBean.setRequestedOn(detailsInfo.getRequestedOn()); //was taking Date as parameter
				receiveBean.setRequestedOn(PortalUtility.formatDateToddmmyyyyhhmmss(detailsInfo.getRequestedOn()));//changed to String
				receiveBean.setStatus(Status.getStatus(detailsInfo.getStatus()));
				receiveBean.setStatusId(detailsInfo.getStatus());
				
				if(detailsInfo.getAddressFlag()==0){
					//default corporate address
					Properties props= PropertyLoader.loadProperties("conf.Portal.properties");
					receiveBean.setDeliveryAddress(props.getProperty("diksha.corporateaddress"));
					receiveBean.setAddressHtml(props.getProperty("diksha.corporateaddress"));
				}else{
					//user has selected Other address
					receiveBean.setDeliveryAddress(detailsInfo.getDeliveryAddress());
					receiveBean.setAddressHtml(detailsInfo.getAddressHtml());
				}
				receiveBean.setAddressFlag(detailsInfo.getAddressFlag());				
			} catch (SodexoDetailsDaoException ex) {
				logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH ROW FROM SODEXO_DETAILS FOR ID="+detailsDto.getId(), ex);				
			}
			dropdown.setDetail(receiveBean);
			request.setAttribute("actionForm", dropdown);
			break;

			
			
		case RECEIVEALLTOHANDLE:
			
			List<SodexoBean> resultList = new ArrayList<SodexoBean>();				
			EmpSerReqMap[] empSerReqDetails = null;
			int toHandleCount = 0 ;
			
			try{
				
				StringBuffer query=new StringBuffer("SELECT * FROM EMP_SER_REQ_MAP WHERE REQ_TYPE_ID=3 ");
				
				if (detailsDto.getMonth() != null || detailsDto.getSearchyear() != null || detailsDto.getSearchName() != null)
				{
					if (detailsDto.getMonth() != null && detailsDto.getToMonth() != null) query.append(" AND (MONTH(SUB_DATE) BETWEEN " + detailsDto.getMonth() + " AND " + detailsDto.getToMonth() +") ");
					else if (detailsDto.getMonth() != null) query.append(" AND MONTH(SUB_DATE)=" + detailsDto.getMonth() + " ");
					if (detailsDto.getSearchyear() != null) query.append(" AND YEAR(SUB_DATE)=" + detailsDto.getSearchyear() + " ");
					if (detailsDto.getSearchName() != null) query.append(" AND REQUESTOR_ID IN (SELECT ID FROM USERS U WHERE U.STATUS NOT IN ( 1, 2, 3 ) AND PROFILE_ID IN (SELECT ID FROM PROFILE_INFO WHERE (FIRST_NAME IS NOT NULL AND FIRST_NAME LIKE '%" + detailsDto.getSearchName() + "%') OR (LAST_NAME IS NOT NULL AND LAST_NAME LIKE '%" + detailsDto.getSearchName() + "%'))) ");
				} 
				else {//default case
					query.append(" AND TIMESTAMPDIFF(DAY,SUB_DATE,NOW()) <= 30"+" ");
				}
								
				empSerReqDetails = empSerReqDao.findByDynamicSelect(query.toString(), null);
				
				if(empSerReqDetails!=null && empSerReqDetails.length >0){
					resultList = extractIdsAndPopulateSodexoBean(empSerReqDetails);
									
					//ToHandle[count]
					for(Iterator<SodexoBean> iter = resultList.iterator();iter.hasNext();){
						SodexoBean countBean = iter.next();
						countBean = populateRequestorDetails(countBean,detailsDto);
						if(countBean.getStatus().equalsIgnoreCase(Status.REQUESTRAISED)){
							toHandleCount++;
						}
												
					}						
				}	
			}catch (EmpSerReqMapDaoException ex) {			
				logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH ROW FROM ESR FOR REQ_TYPE_ID=3", ex);
			}	
						
			dropdown.setCount(toHandleCount);
			dropdown.setDropDown(resultList.toArray());
			request.setAttribute("actionForm", dropdown);
			
			break;
			

		
			
		case RECEIVETOHANDLE:
			
			try{
				EmpSerReqMap[] esrDetails;
				esrDetails = empSerReqDao.findWhereReqTypeIdEquals(3);//SODEXO REQUEST TYPE_ID = 3
				if(esrDetails.length >0)
					resultList = extractIdsAndPopulateSodexoBean(esrDetails);				
				
				SodexoDetails sodexoDetailsFetched = null;
				SodexoReq sodexoReqFetched = null;
				
				if(detailsDto.getId()>0){
					//ASSIGN is pressed from TO_HANDLE tab
					sodexoDetailsFetched = sodexoDetailsDao.findByPrimaryKey(new SodexoDetailsPk(detailsDto.getId()));
				}else{
					//ASSIGN is pressed from INBOX tab
					sodexoReqFetched = sodexoReqDao.findByDynamicWhere(" ESR_MAP_ID=? ORDER BY ID DESC", new Object[]{new Integer(detailsDto.getEsrMapId())})[0];
					sodexoDetailsFetched = sodexoDetailsDao.findByPrimaryKey(new SodexoDetailsPk(sodexoReqFetched.getSdId()));
				}
				Handlers[] handlersArray=null;
				SodexoBean bean = null;
				bean = populateRequestorDetails(new SodexoBean(),sodexoDetailsFetched);
				
				//Enable escalation user to take action
				ProcessChain processChain=null;
				ProcessEvaluator evaluator = new ProcessEvaluator();

				if(reqEscalation.isEscalationAction())
				{
					processChain=reqEscalation.getRequestProcessChain();
					reqEscalation.setEscalationProcess(evaluator);
					reqEscalation.enableEscalationPermission(evaluator);
				}
				else
				{
					processChain = getRequestorProcessChain(sodexoDetailsFetched.getRequestorId());
				}
				
				
				Integer[] handlersUserIds = evaluator.handlers(processChain.getHandler(),sodexoDetailsFetched.getRequestorId());
				handlersArray = new Handlers[handlersUserIds.length];
				//with these userIds extract name and their id from PROFILE_INFO
				
				int index=0;
				for(Integer handlerId : handlersUserIds){
					ProfileInfo handlerProfileInfo = null;
					try {
						handlerProfileInfo = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI WHERE PI.ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[]{handlerId})[0];
						Handlers handler = new Handlers();
						handler.setName(handlerProfileInfo.getFirstName());
						handler.setId(handlerId);						
						handlersArray[index++] = handler;	
					} catch (ProfileInfoDaoException ex) {
						logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH ROW FROM PROFILE_INFO FOR USER_ID="+handlerId, ex);
					}										
				}			
				
				//to know who is the assigned_handler for a particular request
				Inbox[] inboxHandlerInfo = null;
				try {
					if(sodexoReqFetched == null){
						//ASSIGN is pressed from TO_HANDLE tab
						sodexoReqFetched = sodexoReqDao.findByDynamicSelect("select * from SODEXO_REQ where SD_ID=?", new Object[]{new Integer(sodexoDetailsFetched.getId())})[0];
					}
					inboxHandlerInfo = inboxDao.findByDynamicWhere("ESR_MAP_ID=? AND ASSIGNED_TO IS NOT NULL AND ASSIGNED_TO != 0 ORDER BY ID DESC LIMIT 0,1", new Object[]{new Integer(sodexoReqFetched.getEsrMapId())});
				} catch (InboxDaoException ex) {
					logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH ROW FROM INBOX FOR ESR_MAP_ID="+sodexoReqFetched.getEsrMapId(), ex);
				}
				if(inboxHandlerInfo.length > 0){
					bean.setAssignedTo(inboxHandlerInfo[0].getAssignedTo());
				}
			
				bean.setHandlersArray(handlersArray);
				
				StatusDao statusDao = StatusDaoFactory.create();
				
				inboxHandlerInfo = inboxDao.findWhereEsrMapIdEquals(detailsDto.getEsrMapId());
				com.dikshatech.portal.dto.Status[] statusArray = null;
				
				//if there is only one handler display all statuses in the drop-down
				
				Integer[] handlerIds = new ProcessEvaluator().handlers(getRequestorProcessChain(sodexoDetailsFetched.getRequestorId()).getHandler(),sodexoDetailsFetched.getRequestorId());
				if(inboxHandlerInfo!=null && inboxHandlerInfo.length>0){
					if(inboxHandlerInfo[0].getStatus().contains("Docked By") || handlerIds.length==1 || inboxHandlerInfo[0].getStatus().equals(Status.WORKINPROGRESS)){					
						statusArray = new com.dikshatech.portal.dto.Status[4];
						com.dikshatech.portal.dto.Status tempStatus = new com.dikshatech.portal.dto.Status();
						tempStatus.setStatus("Select a Status");
						statusArray[0]=tempStatus;
						statusArray[1]=statusDao.findByPrimaryKey(Status.getStatusId(Status.ASSIGNED));					
						//it was assigned to someone before
						statusArray[2]=statusDao.findByPrimaryKey(Status.getStatusId(Status.WORKINPROGRESS));
						statusArray[3]=statusDao.findByPrimaryKey(Status.getStatusId(Status.PROCESSED));					
					}else{
						statusArray = new com.dikshatech.portal.dto.Status[2];
						com.dikshatech.portal.dto.Status tempStatus = new com.dikshatech.portal.dto.Status();
						tempStatus.setStatus("Select a Status");
						statusArray[0]=tempStatus;
						statusArray[1]=statusDao.findByPrimaryKey(Status.getStatusId(Status.ASSIGNED));								
					}
				}				
								
				bean.setStatusArray(statusArray);
				dropdown.setDetail(bean);
				request.setAttribute("actionForm", dropdown);
				
			}catch(Exception ex){
				logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH DATA ... FOR ... RECEIVETOHANDLE ", ex);
			}
				break;			
				
				

		case RECEIVENEW:
			try {
				SodexoDetails[] fetchedSodexoDetails = sodexoDetailsDao.findByDynamicWhere(" REQUESTOR_ID=? AND STATUS=? ORDER BY ID DESC LIMIT 0,1",new Object[] { login.getUserId(),Status.getStatusId(Status.PROCESSED)});
				SodexoBean sodexoBean = new SodexoBean();
				if(fetchedSodexoDetails.length>0){
					//the user has availed sodexo before	
					if(fetchedSodexoDetails[0].getSrType().equalsIgnoreCase("WITHDRAW")){
						sodexoBean.setAmountEligible(2200);
						sodexoBean.setAmountAvailed(null);						
					}else{
						//AVAIL || CHANGE
						sodexoBean.setAmountEligible(fetchedSodexoDetails[0].getAmountEligible());
						sodexoBean.setAmountAvailed(fetchedSodexoDetails[0].getAmountAvailed());						
					}			
					if(fetchedSodexoDetails[0].getAddressFlag()==0){
						//default corporate address
						Properties props= PropertyLoader.loadProperties("conf.Portal.properties");
						sodexoBean.setDeliveryAddress((String)props.get("diksha.corporateaddress"));
					}else{
						
						/**
						 * before
						 * 
						 * delivery address was handled as a String
						 * //user has selected Other address
						 * SodexoBean.setDeliveryAddress(fetchedSodexoDetails[0].getDeliveryAddress());
						 * */
						
						/**
						 * june 13
						 * 
						 * Delivery address will be captured the way it is entered by the Requester
						 * */
						sodexoBean.setDeliveryAddress(fetchedSodexoDetails[0].getDeliveryAddress());
						sodexoBean.setAddressHtml(fetchedSodexoDetails[0].getAddressHtml());
					}	
					sodexoBean.setAddressFlag(fetchedSodexoDetails[0].getAddressFlag());
				}	
				sodexoBean.setAmountEligible(2200);
				dropdown.setDetail(sodexoBean);
				request.setAttribute("actionForm", dropdown);
				} catch (SodexoDetailsDaoException ex) {
					logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH DATA FROM SODEXO_DETAILS FOR REQUESTER_ID="+login.getUserId()+" AND STATUS="+Status.getStatusId(Status.PROCESSED), ex);
			    }
			break;				
			
			
			
		case RECEIVEBUTTONINFO:
			
			HandlerAction handlerAction = new HandlerAction();		
			
			try {
				SodexoReq[] handlerReq = sodexoReqDao.findWhereAssignedToEquals(login.getUserId());
				if(handlerReq.length > 0){
					handlerAction.setAssign(1);
				}
			} catch (SodexoReqDaoException ex) {
				logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH DATA FROM SODEXO_REQ FOR ASSIGNED_TO="+login.getUserId(), ex);
			}		
				
			try {				
				SodexoDetails[] lastestSodexoReq = sodexoDetailsDao.findByDynamicWhere(" REQUESTOR_ID=? ORDER BY ID DESC LIMIT 0,1",new Object[]{new Integer(login.getUserId())});
				if(lastestSodexoReq.length > 0){					
					if(Status.getStatus(lastestSodexoReq[0].getStatus()).equalsIgnoreCase(Status.REQUESTRAISED)){// || Status.getStatus(lastestSodexoReq[0].getStatus()).equalsIgnoreCase(Status.WORKINPROGRESS)){
							handlerAction.setCancelVisible(true);
					}else if (Status.getStatus(lastestSodexoReq[0].getStatus()).equalsIgnoreCase(Status.PROCESSED)){
						if(lastestSodexoReq[0].getSrType().equalsIgnoreCase("WITHDRAW")){
							handlerAction.setAvailVisible(true);
						}else{
							handlerAction.setChangeVisible(true);
							handlerAction.setWithdrawVisible(true);
						}
					}else if(Status.getStatus(lastestSodexoReq[0].getStatus()).equalsIgnoreCase(Status.REVOKED)){
						if(lastestSodexoReq[0].getSrType().equalsIgnoreCase("AVAIL")){
							handlerAction.setAvailVisible(true);
						}else{
							handlerAction.setChangeVisible(true);
							handlerAction.setWithdrawVisible(true);
						}
					}					
				}else{
					//logged in user has not requested for sodexo benefit
					handlerAction.setAvailVisible(true);
				}
				
			} catch (SodexoDetailsDaoException ex) {
				logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH ROW FROM SODEXO_DETAILS FOR REQUESTER_ID="+login.getUserId(), ex);
			}
			
			DropDown dropDown = new DropDown();
			dropDown.setDetail(handlerAction);
			request.setAttribute("actionForm",dropDown);
			break;			
		}
		return result;
	}

	
	private SodexoBean populateRequestorDetails(SodexoBean sbean,SodexoDetails detailsDto){
		Users userDetails = null;
		ProfileInfo profileDetails = null;
		Levels levelDetails = null;
		Divison departmentDetails = null;
		Divison divisonDetails = null;
		SodexoDetails sodexoDetailsRow = null;
		UsersDao usersDao = UsersDaoFactory.create();
		
		try {
			if(detailsDto.getId() == 0){					
				//RECEIVEALLTOHANDLE
			sodexoDetailsRow = sodexoDetailsDao.findByPrimaryKey(new SodexoDetailsPk(sbean.getId()));				
			}else{
				//RECEIVETOHANDLE
			sodexoDetailsRow = detailsDto;//sodexoDetailsRow = sodexoDetailsDao.findByPrimaryKey(new SodexoDetailsPk(detailsDto.getId()));					
			}
			
			sbean = populateSodexoBean(sodexoDetailsRow);		
			userDetails = usersDao.findByPrimaryKey(new UsersPk(sodexoDetailsRow.getRequestorId()));
			
			//fetching the Requester details
			if(userDetails != null){
				sbean.setEmpId(userDetails.getEmpId());//employee id
				profileDetails = profileInfoDao.findByPrimaryKey(new ProfileInfoPk(userDetails.getProfileId()));
				if(profileDetails != null){
					sbean.setRequestorName(profileDetails.getFirstName() + " " +profileDetails.getLastName());//requestedBy
					LevelsDao levelsDao = LevelsDaoFactory.create();
					levelDetails = levelsDao.findByPrimaryKey(new LevelsPk(profileDetails.getLevelId()));
					if(levelDetails != null){
						sbean.setDesignation(levelDetails.getDesignation());//Designation
						DivisonDao divisonDao = DivisonDaoFactory.create();
						departmentDetails = divisonDao.findByPrimaryKey(new DivisonPk(levelDetails.getDivisionId()));
						if(departmentDetails != null){
							if(departmentDetails.getParentId()==0){
								//department only
								sbean.setDepartment(departmentDetails.getName());
							}else{
								//Division exists
								sbean.setDivison(departmentDetails.getName());
								divisonDetails = divisonDao.findByPrimaryKey(new DivisonPk(departmentDetails.getParentId()));
								if(divisonDetails != null){
									sbean.setDepartment(divisonDetails.getName());
								}
																	
							}
						}
						
					}						
				}
			}			
												
		} catch (SodexoDetailsDaoException ex) {
			logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH ROW FROM SODEXO_DETAILS FOR ID="+sbean.getId(), ex);
		} catch (ProfileInfoDaoException ex) {
			logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH ROW FROM PROFILE_INFO FOR ID="+userDetails.getProfileId(), ex);
		} catch (UsersDaoException ex) {
			logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH ROW FROM USERS FOR ID="+sodexoDetailsRow.getRequestorId(), ex);
		} catch (LevelsDaoException ex) {
			logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH ROW FROM LEVELS FOR ID="+profileDetails.getLevelId(), ex);
		} catch (DivisonDaoException ex) {
			logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH ROW FROM DIVISON FOR ID="+levelDetails.getDivisionId(), ex);
		} 		
		return sbean;
	}
	
	
	
	
	private List<SodexoBean> extractIdsAndPopulateSodexoBean(EmpSerReqMap[] esrMapDetails) {
		List<SodexoBean> sodexoResultList = new ArrayList<SodexoBean>();
		idReqIdTable=null;
		sodexIdEsrMapIdTable=null;		
		
		idReqIdTable = extractIdInformation(esrMapDetails);// ID(key) REQUEST_ID(value)
		String fetchedEsrMapIds = idReqIdTable.keySet().toString().replace('[', ' ').replace(']', ' ').trim();
		try {
			sodexoReqDetails = sodexoReqDao.findByDynamicWhereIn(" ESR_MAP_ID IN("+ fetchedEsrMapIds + ")");
			sodexIdEsrMapIdTable = extractIdInformation(sodexoReqDetails);// SD_ID(key) ESR_MAP_ID(value)
			String fetchedSdIds = sodexIdEsrMapIdTable.keySet().toString().replace('[', ' ').replace(']', ' ').trim();
			submittedSodexoRequest = sodexoDetailsDao.findByDynamicWhereIn(" ID IN (" + fetchedSdIds+ ") ORDER BY REQUESTED_ON DESC");
		} catch (SodexoReqDaoException ex) {
			logger.error("SODEXO : EXCEPTION IN extractIdsAndPopulateSodexoBean()..PROBABLE REASON..fetchedEsrMapIds WAS NULL",ex);			
		} catch (SodexoDetailsDaoException ex) {
			logger.error("SODEXO : EXCEPTION IN extractIdsAndPopulateSodexoBean()..PROBABLE REASON..fetchedSdIds WAS NULL",ex);			
		}
		
		for (SodexoDetails row : submittedSodexoRequest) {
			sodexoResultList.add(populateSodexoBean(row));
		}
		
		return sodexoResultList;
	}

	
	
	private SodexoBean populateSodexoBean(SodexoDetails row) {
			
		SodexoBean bean = null;
		if(sodexIdEsrMapIdTable!=null && sodexIdEsrMapIdTable.containsKey(row.getId())){
			bean = new SodexoBean();
			bean.setRequestId(idReqIdTable.get(sodexIdEsrMapIdTable.get(row.getId())));
			bean.setEsrMapId(sodexIdEsrMapIdTable.get(row.getId()));
			for(SodexoReq reqRow:sodexoReqDetails){
				//arrive at that row which is submitted
				if(reqRow.getSdId() != row.getId()){
					continue;
				}
				bean.setActionBy(reqRow.getActionBy());
				bean.setActionDate(reqRow.getActionDate());
				bean.setAssignedTo(reqRow.getAssignedTo());					
			}				
		}				
		bean.setId(row.getId());
		bean.setAmountAvailed(row.getAmountAvailed());
		bean.setAmountEligible(row.getAmountEligible());
		bean.setBalanceAmount(row.getAmountEligible() - row.getAmountAvailed());
		bean.setStatus(Status.getStatus(row.getStatus()));
		
		//bean.setRequestedOn(row.getRequestedOn());	was Date before...changed to String
		bean.setRequestedOn(PortalUtility.formatDateToddmmyyyyhhmmss(row.getRequestedOn()));
		bean.setStatusId(row.getStatus());
		bean.setSodexoRequestType(row.getSrType());//AVAIL  CHANGE   WITHDRAW
		if(row.getAddressFlag()==0){
			//default corporate address
			Properties props= PropertyLoader.loadProperties("conf.Portal.properties");
			bean.setDeliveryAddress(props.getProperty("diksha.corporateaddress"));
			bean.setAddressHtml(props.getProperty("diksha.corporateaddress"));
		}else{
			//user has selected Other address
			bean.setDeliveryAddress(row.getDeliveryAddress());//address in String format
			bean.setAddressHtml(row.getAddressHtml());//send the address as entered by requester	
		}
		bean.setAddressFlag(row.getAddressFlag());//send the addressFlag to UI		
		
		//set requestor's fName & lName
		try {
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			UsersDao usersDao = UsersDaoFactory.create();
			ProfileInfo userProfileInfo = profileInfoDao.findByPrimaryKey(usersDao.findByPrimaryKey(row.getRequestorId()).getProfileId());
			String requestorFullName = userProfileInfo.getFirstName() +" " +userProfileInfo.getLastName();
			bean.setRequestorName(requestorFullName);
		} catch (Exception e) {
			logger.error("sodexo exception : exception was thrown when trying to fetch requestor's first-name & last-name from profile_info table", e);
		}
		
		//logic for assign button visibility
		Inbox[] fetchedInboxRows = null;
		try{
			
			fetchedInboxRows = inboxDao.findWhereEsrMapIdEquals(bean.getEsrMapId());
			if(fetchedInboxRows!=null && fetchedInboxRows.length>0){
				bean.setStatus(fetchedInboxRows[0].getStatus());
			}			
			
			fetchedInboxRows = inboxDao.findByDynamicWhere(" ESR_MAP_ID=? AND ASSIGNED_TO=?", new Object[]{bean.getEsrMapId(),login.getUserId()});
			if(fetchedInboxRows!=null && fetchedInboxRows.length>0){
				bean.setStatus(fetchedInboxRows[0].getStatus());				
				//this request is assigned to me
				bean.setAssignButtonflag(true);
			}else{
				//this request is not assigned to me
				bean.setAssignButtonflag(false);
			}			
		}catch(Exception ex){
			bean.setAssignButtonflag(false);
			bean.setStatus("ERROR_AT_"+bean.getEsrMapId());
		}
		
		return bean;		
	}

	/*
	 * based on the array type received, this method extracts the details and puts it in a Hashtable
	 * @returns above populated Hashtable
	 * */
	private Hashtable extractIdInformation(Object[] info){
		if(info instanceof EmpSerReqMap[]){
			EmpSerReqMap[] details = (EmpSerReqMap[])info;
			Hashtable<Integer,String> idReqIdTable = new Hashtable<Integer,String>();
			for(EmpSerReqMap row : details){
				idReqIdTable.put(row.getId(),row.getReqId());			
			}
			return idReqIdTable;
		}
		else if(info instanceof SodexoReq[]){
			SodexoReq[] sodexoReqDetails = (SodexoReq[])info;
			Hashtable<Integer,Integer> sodexIdEsrMapIdTable = new Hashtable<Integer,Integer>();
			for(SodexoReq row : sodexoReqDetails){
				sodexIdEsrMapIdTable.put(row.getSdId(),row.getEsrMapId());			
			}
			return sodexIdEsrMapIdTable;
		}else{
			logger.error("SODEXO : extractIdInformation received an Object which was not recognised");
			return null;//none matched	
		}
		
	}
	
	
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		SodexoDetails detailsDto = (SodexoDetails) form;
		Login login = Login.getLogin(request);
		SodexoDetailsPk sodexoDetailsPk = null;
		boolean flag = false;
		
		
		switch (SaveTypes.getValue(detailsDto.getsType())) {
		
		case SUBMIT:
			
			ProcessChain requesterPc = getRequestorProcessChain(login.getUserId());
			ProcessEvaluator evaluator = new ProcessEvaluator();
			String ERR_CODE = evaluator.validateProcessChain(requesterPc, login.getUserId(), false, true);
			
			try{
				Integer.parseInt(ERR_CODE);//200   O.K
			}catch(NumberFormatException ex){
				logger.error("SERVICE REQUEST PRE-PREPROCESS FAILED FOR REQUESTER_ID="+login.getUserId()+" SERVICE_REQUEST IS \"SODEXO\" ERR_CODE="+ERR_CODE);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.servicerequest",ERR_CODE));
				result.setForwardName("success");
				return result;
			}
						
			try {
				detailsDto.setRequestorId(login.getUserId());
				detailsDto.setRequestedOn(new Date());
				detailsDto.setStatus(Status.getStatusId(Status.REQUESTRAISED));
				if(detailsDto.getSrType().equalsIgnoreCase("WITHDRAW")){
					//set Eligibility amount to 2200
					detailsDto.setAmountEligible(2200);
				}
				
				/**
				 * if the addressFlag==1  deliveryAddress has the required String
				 * 
				 * if the addressFlag==0  deliveryAddress is null & this is what we want
				 * 
				 * So the data is already set, just persist it.
				 * */
				
				if(detailsDto.getAddressFlag()==1){					
					String address = detailsDto.getDeliveryAddress().replace('\r', ' ').trim();//carriage-return
					detailsDto.setDeliveryAddress(address); 					 					
				}
				
				if(detailsDto.getSrType().equalsIgnoreCase("WITHDRAW"))
					logger.debug("SODEXO_SUBMIT_ACTION : REQUESTER_ID="+login.getUserId()+" HAS WITHDRAWN THE SODEXO");					
				else
					logger.debug("SODEXO_SUBMIT_ACTION : REQUESTER_ID="+login.getUserId()+" HAS REQUESTED SODEXO WHICH WILL BE DELIVERED TO "+detailsDto.getDeliveryAddress()+" addressFlag="+detailsDto.getAddressFlag());
				sodexoDetailsPk = sodexoDetailsDao.insert(detailsDto);
				logger.debug("SODEXO_SUBMIT_ACTION : INSERTED DATA INTO SODEXO_DETAILS");
			} catch (SodexoDetailsDaoException e) {
				logger.error("SODEXO_SUBMIT_ACTION DID NOT COMPLETE FOR REQUESTER_ID="+login.getUserId(), e);
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sodexo.avail.failed"));
				result.setForwardName("success");				
			}
			if (sodexoDetailsPk != null) 
				flag = submitSodexo(detailsDto); 
			
			if(!flag){
				/*result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sodexo.avail.retry"));
				result.setForwardName("success");	*/
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.servicerequest","ERR-006"));
				result.setForwardName("success");
				return result;
			}
						
			break;
		}
		return result;
	}

	
	public ProcessChain getRequestorProcessChain(int userId){
		ProcessChain processChain = null;
		try {
			processChain = processChainDao.findByDynamicSelect("select * from PROCESS_CHAIN where ID=(select PROC_CHAIN_ID from MODULE_PERMISSION where ROLE_ID=(select ROLE_ID from USER_ROLES where USER_ID=?) " +
					"AND MODULE_ID=24)", new Object[]{new Integer(userId)})[0];
			
			
		} catch (ProcessChainDaoException ex) {
			logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO FETCH PROCESS-CHAIN FOR REQUESTER_ID="+userId, ex);			
		}
		return processChain;
	}

	private boolean submitSodexo(SodexoDetails sodexoDetails){
		
		EmpSerReqMapPk empSerReqMapPk = null;	
		ProcessChain processChain = getRequestorProcessChain(sodexoDetails.getRequestorId());
		ProcessEvaluator processEvaluator = new ProcessEvaluator();
		String requesterTemplate = null;
		
		String esrIdAndReqId = insertIntoEmpSerReqMap(sodexoDetails);
		String[] esrMapIds = esrIdAndReqId.split(";");//esrMapIds[0] = primarykey    esrMapIds[1] = IN-SO-0001
		empSerReqMapPk = new EmpSerReqMapPk(Integer.parseInt(esrMapIds[0]));
		boolean flag = false;
		if(empSerReqMapPk != null & empSerReqMapPk.getId() != 0){
			
				/*
				 * insert into SODEXO_DETAILS was success
				 * insert into EMP_SER_REQ_MAP was success
				 * now with the respective PK's, insert "required number of rows" into SODEXO_REQ 
				 * */
								
			String srType = sodexoDetails.getSrType();
			
			if(srType.equalsIgnoreCase("AVAIL")){				
					requesterTemplate=MailSettings.Notification_SODEXO_SUBMITTED_To_REQUESTOR;			
			}
			else if(srType.equalsIgnoreCase("CHANGE")){					
					requesterTemplate=MailSettings.Notification_SODEXO_AMOUNT_CHANGED_TO_REQUESTOR;
			}else{
				//WITHDRAWN
					requesterTemplate=MailSettings.Notification_SODEXO_WITHDRAWN_TO_REQUESTOR;
			}		
			
			Integer[ ] handlerIds = processEvaluator.handlers(processChain.getHandler(), sodexoDetails.getRequestorId());				
			
			flag = insertIntoSodexoReqAndSendToDockingStation(handlerIds,empSerReqMapPk.getId(),esrMapIds[1],sodexoDetails.getId(),srType);
						
			if(flag){
				logger.debug("SODEXO_SUBMIT_ACTION : INSERTED DATA INTO SODEXO_DETAILS SODEXO_REQ AND EMP_SER_REQ_MAP TABLES");				
			}else{
				logger.error("SODEXO_SUBMIT_ACTION : DATA INSERTION FAILED, BEGIN_ROLLBACK");
				try{
					rollback(sodexoDetails.getId(),esrMapIds[0]);
				}catch(SodexoRollbackException srex){
					logger.error("SODEXO_SEVERE_ERROR : ROLLBACK FAILED : EXCEPTION WAS THROWN DURING ROLLBACK", srex);
				}
			}			
			
		}else{
			logger.error("SODEXO_SUBMIT_ACTION : DATA INSERTION INTO EMP_SER_REQ_MAP RETURNED null AS P.K");
		}
		return flag;
			
	}
	

	private void rollback(int sdId, String esrMapId) throws SodexoRollbackException {
		int esrId =Integer.parseInt(esrMapId);
		logger.debug("SODEXO_ROLLBACK : BEGIN_ROLLBACK");
		try{
			sodexoDetailsDao.delete(new SodexoDetailsPk(sdId));
			logger.debug("SODEXO_ROLLBACK : DELETED A ROW FROM SODEXO_DETAILS FOR ID="+sdId);
			EmpSerReqMapDaoFactory.create().delete(new EmpSerReqMapPk(esrId));
			logger.debug("SODEXO_ROLLBACK : DELETED A ROW FROM EMP_SER_REQ_MAP FOR ID="+esrId);
			logger.debug("SODEXO_ROLLBACK : COMPLETED");
		}catch(Exception ex){
			throw new SodexoRollbackException();
		}
		
	}

	/*
	 * insert required number of rows into SODEXO_REQ 
	 * the number of rows depends on the number of handlers in the "finance department"
	 * after inserting into SODEXO_REQ, we immediately send this information to "ASSIGNED_TO's" docking station	 
	 * we also send "required" information through e-mail based on the templates
	 *  
	 * */
	private boolean insertIntoSodexoReqAndSendToDockingStation(Integer[] handlerIds, int esrMapId, String reqId,Integer sdId,String srType){
		InboxModel inboxModel = new InboxModel();
		//String updateInboxQuery = "update INBOX SET ASSIGNED_TO=NULL where ESR_MAP_ID IN (select DISTINCT ESR_MAP_ID from SODEXO_REQ SR where SR.SD_ID IN(select ID from SODEXO_DETAILS SD where SD.STATUS=25))";
		String msgBody = null;
		String subject = null;		
		boolean flag = true;
		String templateName;		
		ProfileInfo requesterProfileInfo = null;
		try{
			requesterProfileInfo = ProfileInfoDaoFactory.create().findByPrimaryKey(UsersDaoFactory.create().findByPrimaryKey(
							sodexoDetailsDao.findByPrimaryKey(sdId).getRequestorId()).getProfileId());			
		}catch(Exception ex){
			
		}
		
		if(srType.equalsIgnoreCase("CHANGE")){
			subject = "Changes in Sodexo Request by ["+requesterProfileInfo.getFirstName()+" "+requesterProfileInfo.getLastName()+"]";
			templateName = MailSettings.SODEXO_AMOUNT_CHANGED_TO_HANDLER;
		}else if(srType.equalsIgnoreCase("WITHDRAW")){
			subject = "Withdrawal of Sodexo Benefit by ["+requesterProfileInfo.getFirstName()+" "+requesterProfileInfo.getLastName()+"]";
			templateName = MailSettings.SODEXO_WITHDRAWN_TO_HANDLER;
		}else{
			//AVAIL
			subject = "New Sodexo Request by ["+requesterProfileInfo.getFirstName()+" "+requesterProfileInfo.getLastName()+"]";
			templateName = MailSettings.SODEXO_REQ_RECEIVED;
		}
		
		try{
			
			try{
				msgBody = sendMail(handlerIds,subject,templateName,sdId,reqId,true);					
			}catch(SodexoMailException smex){
				logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO SEND MAIL TO HANDLERS", smex);
				flag = false;
			}
					
			
			for(Integer handlerId : handlerIds){
				logger.debug("SODEXO : PREPARING TO SEND MAIL TO HANDLER_ID="+handlerId);
				SodexoReq sodexoReq = new SodexoReq();//for each handler a row is inserted into SODEXO_REQ
				sodexoReq.setActionBy(0);
				sodexoReq.setActionDate(null);
				sodexoReq.setAssignedTo(handlerId);
				sodexoReq.setEsrMapId(esrMapId);
				sodexoReq.setSdId(sdId);		
			
				sodexoReq.setMessageBody(msgBody); //data is set into entity...now we must persist it
				sodexoReqDao.insert(sodexoReq);
				logger.debug("sodexo : inserted a row into SODEXO_REQ for SD_ID="+sodexoReq.getSdId()+" ESR_MAP_ID="+sodexoReq.getEsrMapId());
				//sending info to docking station
				inboxModel.sendToDockingStation(esrMapId, sodexoReq.getId(), subject, Status.REQUESTRAISED);
				logger.debug("SODEXO : INSERTED INTO INBOX FOR HANDLER_Id="+handlerId);
				/*
				 * initially no handlers will be assigned
				 * but when the request is submitted and INBOX is populated, ASSIGNED_TO will be set with  list of available HANDLER's
				 * it should be set to NULL indicating that no handler is assigned
				 * */	
				//JDBCUtiility.getInstance().update(updateInboxQuery,null);	
				//inbox = inboxDao.findByDynamicSelect("select * from INBOX WHERE ESR_MAP_ID=? AND ASSIGNED_TO IS NULL ORDER BY ID DESC", new Object[]{new Integer(esrMapId),})[0];				
			}
			
		}catch(SodexoReqDaoException ex){
			logger.error("SODEXO : EXCEPTION WAS THROWN WHEN TRYING TO INSERT INTO SODEXO_REQ ", ex);
			flag = false;
		} 
		
		return flag;		
	}
	
		
	/*
	 *MAIL.TO : all handlers from requester's processChain
	 *MAIL.CC : requester's HR_SPOC 
	 */
	
	private String sendMail(Integer toUserIds[], String subject,String mailTemplateName, Integer sodexoDetailsId ,String reqId,boolean wishToSendMail) throws SodexoMailException{
		PortalMail portalMail = new PortalMail();
		String msgBody =null;
		try {
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			LevelsDao levelsDao = LevelsDaoFactory.create();
			DivisonDao divisionDao = DivisonDaoFactory.create();
			UsersDao usersDao = UsersDaoFactory.create();
			SodexoDetails details = sodexoDetailsDao.findByPrimaryKey(sodexoDetailsId);//SD_ID same as SodexoDetails.ID 
			
			if(toUserIds.length>1){//more than one handlers are present
				String[] mailIds = fetchOfficialMailIds(toUserIds);
				portalMail.setAllReceipientMailId(mailIds);				//append each mailId and use it in Mail.TO part
			}else{
				//same as before
				String query = "WHERE PI.ID IN (SELECT U.PROFILE_ID FROM USERS U WHERE U.ID="+toUserIds[0]+")";
				String[] mailId = profileInfoDao.findOfficalMailIdsWhere(query);
				portalMail.setRecipientMailId(mailId[0]);				
			}
			
			ProfileInfo empProfileInfo = profileInfoDao.findByDynamicSelect("SELECT * FROM PROFILE_INFO PI WHERE PI.ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.ID=?)", new Object[]{toUserIds[0]})[0];
			portalMail.setEmpFname(empProfileInfo.getFirstName());//Dear [EmpFname] for ASSIGNED_TO template
			portalMail.setHandlerName(empProfileInfo.getFirstName());
			
			//common mail stuff
			portalMail.setMailSubject(subject);						
			portalMail.setTemplateName(mailTemplateName);		
								
			//sodexo specific data based on SODEXO_SUBMITTED template
			portalMail.setReqId(reqId);			
			portalMail.setAmountAvailed(details.getAmountAvailed());
			portalMail.setAmountEligible(details.getAmountEligible());
			portalMail.setSubmitDate(PortalUtility.formatDateToddmmyyyyhhmmss(details.getRequestedOn()));//submitted on
			
			if(details.getAddressFlag()==0){
				//default corporate address
				Properties props= PropertyLoader.loadProperties("conf.Portal.properties");
				portalMail.setDeliveryAddress((String)props.get("diksha.corporateaddress"));
			}else				
				portalMail.setDeliveryAddress(details.getDeliveryAddress());
						
			if(details.getSrType().equalsIgnoreCase("WITHDRAW")){
				portalMail.setDeliveryAddress("N.A");
			}
			
			Users requester = usersDao.findByPrimaryKey(new UsersPk(details.getRequestorId()));
			portalMail.setRequestorId(requester.getEmpId());//requester's EMPLOYEE_ID
			
			//when srType=CHANGE
			//if(subject.contains("change")){
				SodexoDetails[] requestorDetails = sodexoDetailsDao.findByDynamicWhere(" REQUESTOR_ID=? AND STATUS=27 ORDER BY ID DESC",new Object[]{new Integer(details.getRequestorId())});
				if(requestorDetails.length > 0){
					portalMail.setPreviousAvailedAmount(requestorDetails[0].getAmountAvailed());					
				}
			//}
						
			String requestorSql = "SELECT * FROM PROFILE_INFO PFINFO LEFT JOIN USERS U ON U.PROFILE_ID=PFINFO.ID WHERE U.ID=? ";
			ProfileInfo profileReqInfo[] = profileInfoDao.findByDynamicSelect(requestorSql,	new Object[] {new Integer(details.getRequestorId())});
			portalMail.setRequestorFirstName(profileReqInfo[0].getFirstName());//requester's first name
			
			/*
			 *hrspoc will be picked up from the process-chain
			 *& not from PROFILE_INFO table
			 */
			
			ProcessChain requesterProcessChain = getRequestorProcessChain(details.getRequestorId());
			
			if(requesterProcessChain.getNotification()!=null && requesterProcessChain.getNotification().length()>0){
				Integer[] hrspocId = new ProcessEvaluator().notifiers(requesterProcessChain.getNotification(), details.getRequestorId());
				//Integer[] hrspocId = new Integer[]{profileReqInfo[0].getHrSpoc()};
				portalMail.setAllReceipientcCMailId(fetchOfficialMailIds(hrspocId));				
			}
						
			Levels levelsRowFetched = levelsDao.findWhereIdEquals(profileReqInfo[0].getLevelId())[0];
			portalMail.setEmpDesignation(levelsRowFetched.getDesignation());//requester's designation
			
			Divison divisionRowFetched = divisionDao.findByPrimaryKey(levelsRowFetched.getDivisionId());
			int parentId = divisionRowFetched.getParentId();
			while(parentId>0){
				divisionRowFetched = divisionDao.findByPrimaryKey(parentId);
				parentId = divisionRowFetched.getParentId();
			}
			portalMail.setEmpDepartment(divisionRowFetched.getName());//requester's department
			
			MailGenerator mailGenerator = new MailGenerator();
			msgBody = mailGenerator.replaceFields(mailGenerator.getMailTemplate(portalMail.getTemplateName()), portalMail);
			if(wishToSendMail){
				mailGenerator.invoker(portalMail);								
				logger.debug("SODEXO : SENT MAIL TO USER_ID="+toUserIds+" & REQUESTER's HR_SPOC WITH USER_ID= FOR REQ_ID="+reqId);
			}
		} catch (Exception e) {
			logger.error("SODEXO : MAIL SENDING FAILED", e);
			throw new SodexoMailException();
		}
		
		return msgBody;
	}

	
	
	private String[] fetchOfficialMailIds(Integer[] toUserIds) throws ProfileInfoDaoException {
		String ids = Arrays.asList(toUserIds).toString().replace("[", " ").replace("]"," ").trim();
		String whereClause = " WHERE PI.ID IN (SELECT U.PROFILE_ID FROM USERS U WHERE U.ID IN ("+ids+") )";     //PROFILE_INFO AS PI
		ProfileInfoDao pInfoDao = ProfileInfoDaoFactory.create();
		return (pInfoDao.findOfficalMailIdsWhere(whereClause));
	}

	/*
	 * 
	 * returns the primaryKey of inserted row and the unique request id generated
	 * which looks like : 10;IN-SO-0001
	 * split this and use it
	 * */
	private String insertIntoEmpSerReqMap(SodexoDetails sodexoDetails) {
		EmpSerReqMap empSerReqRow = new EmpSerReqMap();
		EmpSerReqMapPk empSerReqMapPk = null;
		RegionsDao regionsDao = RegionsDaoFactory.create();
		
		String sodexoIdAsString = sodexoDetails.getId()+" ".toString().trim();
		if(sodexoIdAsString.length() == 1){
			sodexoIdAsString = "000"+sodexoIdAsString;
		}else if(sodexoIdAsString.length() == 2){
			sodexoIdAsString = "00"+sodexoIdAsString;
		}else if(sodexoIdAsString.length() == 3){
			sodexoIdAsString = "0"+sodexoIdAsString;
		}else{
			logger.debug("**** sodexo : insert Into EMP_SER_REQ_MAP : sodexoDetails.Id received is : "+sodexoIdAsString+" ****");
		}
		
		try {
		
			ProcessChain pc = getRequestorProcessChain(sodexoDetails.getRequestorId());
			Integer[] handlerIds = new ProcessEvaluator().handlers(pc.getHandler(),sodexoDetails.getRequestorId());
			String siblings = Arrays.asList(handlerIds).toString().replace('[', ' ').replace(']',' ').trim();
			Regions[] regions = regionsDao.findByDynamicSelect("select * from REGIONS R where R.ID=(select D.REGION_ID from DIVISON D where D.ID=(select L.DIVISION_ID from LEVELS L where L.ID=(Select U.LEVEL_ID from USERS U where ID=?)))", new Object[]{new Integer(sodexoDetails.getRequestorId())});
			
			empSerReqRow.setSubDate(new Date());
			empSerReqRow.setReqTypeId(3);//later.....retrieve this information from some properties file
			empSerReqRow.setRegionId(regions[0].getId());
			empSerReqRow.setRequestorId(sodexoDetails.getRequestorId());
			empSerReqRow.setProcessChainId(pc.getId());
			empSerReqRow.setReqId(regions[0].getRefAbbreviation() + "-SO-".concat(sodexoIdAsString));//IN-SO-0001
			empSerReqRow.setSiblings(siblings);
			empSerReqMapPk = empSerReqDao.insert(empSerReqRow);
			sodexoIdAsString = empSerReqRow.getReqId();//need to send it back			
			logger.debug("SODEXO : INSERTED DATA INTO EMP_SER_REQ_MAP FOR ID="+empSerReqMapPk.getId()+" & REQ_ID="+sodexoIdAsString);
		} catch (EmpSerReqMapDaoException e) {
			e.printStackTrace();
		} catch (RegionsDaoException e) {
			e.printStackTrace();
		} 
		return Integer.valueOf(empSerReqMapPk.getId()).toString().concat(";").concat(sodexoIdAsString);				
	}
	
	
	
	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		SodexoDetails detailsDto = (SodexoDetails) form;
		login = Login.getLogin(request);
		EmpSerReqMapDao esrDao = EmpSerReqMapDaoFactory.create();
		Inbox inbox = null;
		//InboxDao inboxDao = InboxDaoFactory.create();
		ProcessEvaluator pEval = new ProcessEvaluator();
		
		//Set escalation
		int escEsrMapId=detailsDto.getEsrMapId();
		int escUserId=login.getUserId();
		reqEscalation.setEscalationAction(escEsrMapId, escUserId);
		//If escalation action set current esrMapId
		if(reqEscalation.isEscalationAction())
		{
			reqEscalation.setEsrMapId(escEsrMapId);
		}
		
		ProcessChain processChain = null;
		EmpSerReqMap esrRow = null;
		String deleteInboxEntries = "DELETE FROM INBOX WHERE ESR_MAP_ID="+detailsDto.getEsrMapId();
		switch (UpdateTypes.getValue(detailsDto.getuType())) {		
			
		case ASSIGN:
			/*
			 * what i need : assignedToHandlerId , empSerReqMapId, previousStatus
			 * 
			 * what i should do : choose proper template  : on_assign and get the msg_body parsed (send no mails)
			 * update INBOX set status = 'Docked by handlerName' , msg_body='' where esr_map_id=?
			 * make sure main status remains same (SODEXO_DETAILS.STATUS)
			 * 
			 * 
			 * if the previousStatus was not Request Raised then update the status to previousStatus (ignore 'Docked by handlerName')
			 * 
			 * update : ASSIGN button visibility (i may have to explore ModelUtiility)
			 */
			try{			
				esrRow = esrDao.findByPrimaryKey(detailsDto.getEsrMapId());
				Integer[] toUsers = new Integer[]{detailsDto.getAssignedToHandler()};
				String msgBody = sendMail(toUsers,"Sodexo request assigned"+" ["+esrRow.getReqId()+"]",MailSettings.SODEXO_REQ_ASSIGNED_TO_HANDLER,detailsDto.getId(),esrRow.getReqId(),true);
				updateHandlersInbox(esrRow,detailsDto,msgBody);

				//updateMainStatus(detailsDto.getEsrMapId(),Status.REQUESTRAISED);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			break;
			

		
		
		case INPROGRESS:
			/*
			 * what i need : empSerReqMapId
			 * 
			 * what i should do : update INBOX set status='WORK IN-PROGRESS' where ESR_MAP_ID=empSerReqMapId
			 * update MainStatus to 'WORK IN-PROGRESS'
			 * 
			 * update : ASSIGN button visibility (i may have to explore ModelUtiility)
			 */
			
			try{
				esrRow = esrDao.findByPrimaryKey(detailsDto.getEsrMapId());
				detailsDto.setPreviousStatus(Status.WORKINPROGRESS);
				updateHandlersInbox(esrRow,detailsDto,null);
				
				updateMainStatus(detailsDto.getEsrMapId(),Status.WORKINPROGRESS);
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			break;
			
		
		
		case CANCELED:
			/*
			 * requester can cancel a sodexo request if the status is in REQUEST_RAISED (iff)
			 * send mail to all handlers 
			 * update the main status to REVOKED
			 * remove all entries from INBOX
			 */
			try{
				esrRow = esrDao.findByPrimaryKey(detailsDto.getEsrMapId());
				
				if(reqEscalation.isEscalationAction())
				{
					processChain=reqEscalation.getRequestProcessChain();
					reqEscalation.setEscalationProcess(pEval);
				}
				else
				{
					processChain = getRequestorProcessChain(login.getUserId());
				}
				
				Integer[ ] handlerIds = pEval.handlers(processChain.getHandler(), login.getUserId());
				ProfileInfo requesterProfileInfo = ProfileInfoDaoFactory.create().findByPrimaryKey(
						UsersDaoFactory.create().findByPrimaryKey(login.getUserId()).getProfileId());
				sendMail(handlerIds,"Sodexo Request Revoked by "+requesterProfileInfo.getFirstName()+" "+requesterProfileInfo.getLastName(),MailSettings.SODEXO_REQ_CANCELLED,detailsDto.getId(),esrRow.getReqId(),true);
				
				updateMainStatus(detailsDto.getEsrMapId(),Status.REVOKED);
				
				//to delete entries from INBOX
				inboxDao.executeUpdate(deleteInboxEntries);
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
						
			break;
			
			
		default:
			/*
			 * you are here because the handler has PROCESSSED the request
			 * 
			 * on processing : status changes to PROCESSED (there is no CANCEL_REQUEST going thru the process chain)
			 * keep the functionality as before
			 * remove INBOX entries (one for each handler) by deleting them . (i may have to explore ModelUtiility)
			 * 
			 */
			try{
				updateMainStatus(detailsDto.getEsrMapId(), Status.PROCESSED);
				
				//to delete entries from INBOX
				inboxDao.executeUpdate(deleteInboxEntries);				
			}catch(Exception ex){
				ex.printStackTrace();				
			}
			
			break;
					
		}
		return result;
	}
	
	
	
	private void updateMainStatus(int esrMapId, String status) throws SodexoDetailsDaoException, SodexoReqDaoException {
		SodexoDetails sodexoDetailsRow = sodexoDetailsDao.findByPrimaryKey(sodexoReqDao.findWhereEsrMapIdEquals(esrMapId)[0].getSdId());
		sodexoDetailsRow.setStatus(Status.getStatusId(status));
		sodexoDetailsDao.update(new SodexoDetailsPk(sodexoDetailsRow.getId()), sodexoDetailsRow);		
	}	
	private void updateHandlersInbox(EmpSerReqMap esrRow, SodexoDetails detailsDto, String msgBody) throws Exception {
		//common for both statuses
		ProcessChain pcRow=null;
		
		ProcessEvaluator pEval = new ProcessEvaluator();
		if(reqEscalation.isEscalationAction())
		{
			pcRow = reqEscalation.getRequestProcessChain();
			reqEscalation.setEscalationProcess(pEval);
		}
		else
		{
			pcRow = processChainDao.findByPrimaryKey(new ProcessChainPk(esrRow.getProcessChainId()));
		}
		
		String handlerString = pcRow.getHandler();
		Integer[] handlerIds = pEval.handlers(handlerString, detailsDto.getRequestorId());
		ParseTemplate pTemplate = new ParseTemplate();
		
		if(!(detailsDto.getAssignedToHandler()>0)){
			detailsDto.setAssignedToHandler(login.getUserId());//WORK IN-PROGRESS
		}
		
		Inbox fetchedInboxRow = inboxDao.findByDynamicWhere(" ESR_MAP_ID=? AND RECEIVER_ID=?", new Object[]{esrRow.getId(),detailsDto.getAssignedToHandler()})[0];		
	    if(msgBody!=null){
	    	fetchedInboxRow.setMessageBody(msgBody);
	    }
		//common to both statuses
		fetchedInboxRow.setSubject("Sodexo request assigned");	
		fetchedInboxRow.setIsRead(0);
		
		String inboxStatus = fetchedInboxRow.getStatus();
		
		if(inboxStatus.contains("Docked By") || detailsDto.getPreviousStatus().equalsIgnoreCase(Status.REQUESTRAISED) || detailsDto.getPreviousStatus().equalsIgnoreCase(Status.WORKINPROGRESS) ){
			ProfileInfo assineeProfileInfo = profileInfoDao.findByPrimaryKey(UsersDaoFactory.create().findByPrimaryKey(detailsDto.getAssignedToHandler()).getProfileId());
			String tempStatus = null;
			if(detailsDto.getPreviousStatus().equalsIgnoreCase(Status.WORKINPROGRESS)){
				tempStatus = Status.WORKINPROGRESS;
			}else{
				tempStatus = "Docked By "+assineeProfileInfo.getFirstName()+" "+assineeProfileInfo.getLastName();
			}
			fetchedInboxRow.setStatus(tempStatus);
			fetchedInboxRow.setAssignedTo(detailsDto.getAssignedToHandler());
			inboxDao.update(new InboxPk(fetchedInboxRow.getId()), fetchedInboxRow);
			
			//find other handlers and update their INBOX too
			for(Integer handler:handlerIds){
				if(detailsDto.getAssignedToHandler().equals(handler)){
					//this handler's INBOX is already updated...ignore					
					continue;
				}			
				
				Inbox[] foundEntries = inboxDao.findByDynamicWhere(" ESR_MAP_ID=? AND RECEIVER_ID=?", new Object[]{esrRow.getId(),handler});
				if(foundEntries.length>0){
					fetchedInboxRow=foundEntries[0];
					fetchedInboxRow.setSubject("Sodexo request ["+esrRow.getReqId()+"] has been assigned");
					fetchedInboxRow.setAssignedTo(detailsDto.getAssignedToHandler());
					fetchedInboxRow.setStatus(tempStatus);				
					fetchedInboxRow.setIsRead(0);
					fetchedInboxRow.setIsDeleted(0);
											
					if(msgBody!=null){
						String msg = msgBody;
						msg = pTemplate.updateTagData("salutation", "Hi ", msg);
						msg = pTemplate.updateTagData("replaceWithBlank", " Sodexo Request is assigned : ", msg);
									
						fetchedInboxRow.setMessageBody(msg);
					}
					
					inboxDao.update(new InboxPk(fetchedInboxRow.getId()), fetchedInboxRow);
				}
				else
					logger.debug("Unable to update inbox entry for ESR_MAP_ID: " + esrRow.getId() +", RECEIVER_ID: " + handler+". No entry found.");
			}
			
		}else {
			//WORK IN-PROGRESS			
			String updateInboxQuery = "UPDATE INBOX SET STATUS='"+Status.WORKINPROGRESS+"' , IS_READ=0 , IS_DELETED=0 WHERE ESR_MAP_ID="+esrRow.getId();
			inboxDao.executeUpdate(updateInboxQuery);					
		}		
		
	}	

	@Override
	public Attachements download(PortalForm form) {
		Attachements attachements = new Attachements();
		SodexoDetailsDao sodexoDetailsDao = SodexoDetailsDaoFactory.create();
		String path = null;		
			try{				
				PortalData portalData = new PortalData();
				path = portalData.getfolder(portalData.getDirPath());
				File file = new File(path);
				if (!file.exists()) file.mkdir();
				switch (DownloadTypes.getValue(form.getdType())) {
					case SODEXOREPORT:
						try{
							SodexoDetails[] sodexoBean = sodexoDetailsDao.findByDynamicWhere("ID IN (SELECT max(ID) FROM SODEXO_DETAILS WHERE STATUS=? AND AMOUNT_AVAILED>0 GROUP BY REQUESTOR_ID )", new Object[] {27});
							if(sodexoBean!=null && sodexoBean.length>0){
								String sodexoReport[][]=new String[sodexoBean.length][5];
								int i=0;
								for (SodexoDetails bean:sodexoBean ){
										List<Object> empIDList = JDBCUtiility.getInstance().getSingleColumn("SELECT EMP_ID FROM USERS WHERE ID=?", new Object[] { bean.getRequestorId()});
										List<Object> userNameList = JDBCUtiility.getInstance().getSingleColumn("SELECT CONCAT(FIRST_NAME,' ',LAST_NAME) FROM PROFILE_INFO WHERE ID=(SELECT PROFILE_ID FROM USERS WHERE ID=?)", new Object[] {bean.getRequestorId() });
										sodexoReport[i++]=new String[]{i+"",((Integer)empIDList.get(0)).intValue()+"",((String)userNameList.get(0)),bean.getAmountAvailed()+"",bean.getDeliveryAddress()};
								}
								attachements.setFileName(new GenerateXls().generateSodexoXls(sodexoReport, path + File.separator + "SODEXO_"+PortalUtility.getdd_MM_yyyy(new Date())+"_Reports.xls"));
							}							
							
						} catch (Exception e){
							// TODO: handle exception
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

}