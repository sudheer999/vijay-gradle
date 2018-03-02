package com.dikshatech.common.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.CandidateReqDao;
import com.dikshatech.portal.dao.EscalationDao;
import com.dikshatech.portal.dao.IssueApproverChainReqDao;
import com.dikshatech.portal.dao.LeaveMasterDao;
import com.dikshatech.portal.dao.LoanRequestDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.TimesheetReqDao;
import com.dikshatech.portal.dao.TravelReqDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.CandidateReq;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.Escalation;
import com.dikshatech.portal.dto.IssueApproverChainReq;
import com.dikshatech.portal.dto.LeaveMaster;
import com.dikshatech.portal.dto.LoanRequest;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.TimesheetReq;
import com.dikshatech.portal.dto.TravelReq;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.CandidateReqDaoException;
import com.dikshatech.portal.exceptions.EscalationDaoException;
import com.dikshatech.portal.exceptions.IssueApproverChainReqDaoException;
import com.dikshatech.portal.exceptions.LeaveMasterDaoException;
import com.dikshatech.portal.exceptions.LoanRequestDaoException;
import com.dikshatech.portal.exceptions.TimesheetReqDaoException;
import com.dikshatech.portal.exceptions.TravelReqDaoException;
import com.dikshatech.portal.factory.CandidateReqDaoFactory;
import com.dikshatech.portal.factory.EscalationDaoFactory;
import com.dikshatech.portal.factory.IssueApproverChainReqDaoFactory;
import com.dikshatech.portal.factory.LeaveMasterDaoFactory;
import com.dikshatech.portal.factory.LoanRequestDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.TimesheetReqDaoFactory;
import com.dikshatech.portal.factory.TravelReqDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;

public class ProcessEvaluator
{

	public static final String HRSPOC = "HRSPOC";
	public static final String RPMGR = "RM";
	private static final int SPOC = 1;
	private static final int RM = 2;

	private static final int LEAVE_REQ = 1;
	private static final int TRAVELL_REQ = 2;
	private static final int SODEXO_REQ = 3;
	private static final int LOAN_REQ = 4;
	private static final int REIMBURSEMENT_REQ = 5;
	private static final int IT_SUPPORT_REQ = 6;
	private static final int CANDIDATE_REQ = 7;
	private static final int TIMESHEET_REQ = 8;
	private static final int ROLLON_REQ = 9;
	private static final int ISSUES = 10;
	//Flag to enable disable escalation user processing
	private boolean isEscalationAction;
	private boolean isEscalationPermission;
	//Field used for escalation user processing
	private int processChainId;
	
	
	static Logger	logger		= LoggerUtil.getLogger(ProcessEvaluator.class);
	public boolean isEscalationAction() {
		return isEscalationAction;
	}
	
	public boolean isEscalationPermission() {
		return isEscalationPermission;
	}

	public void setEscalationPermission(boolean isEscalationPermission) {
		this.isEscalationPermission = isEscalationPermission;
	}

	public int getProcessChainId() {
		return processChainId;
	}

	public void setProcessChainId(int processChainId) {
		this.processChainId = processChainId;
	}

	public void setEscalationAction(boolean isEscalationAction) {
		this.isEscalationAction = isEscalationAction;
	}
	/**
	 * Returns list of escalation user-id's for a particular level.
	 * Used by approver(), handlers(), and findUserIdByLevelid()
	 * @param escUserId
	 * @param levelId
	 * @param raisedBy
	 * @return
	 */
	protected List<Integer> getEscalationUserIds(String escUserId,int levelId,int raisedBy)
	{
		final String QUERY_ESCALATION="SELECT * from ESCALATION WHERE PC_ID=? AND (USERS_ID=? OR LEVEL_ID=?)";
		
		List<Integer> escalationUsersIds=new ArrayList<Integer>();
		try {
			EscalationDao escProvider=EscalationDaoFactory.create();
			Escalation[] escalations=escProvider.findByDynamicSelect(QUERY_ESCALATION, new Object[]{processChainId,escUserId,levelId});
			//for(Escalation element:escalations)
			//{
			Escalation currentEsc=escalations.length>0? escalations[0]:null;
			if(currentEsc!=null)
			{
				String escalTo=currentEsc.getEscalTo();
				EscalationParser parser=new EscalationParser();
				List<EscPermissionBean> escPermission=parser.getEscalationPermissionsForUsers(escalTo);
				for(EscPermissionBean element:escPermission)
				{
					String userId=element.getuserId();
					int escActorId=0;
					if(userId.equals(HRSPOC))
					{
						escActorId=getHRSPOCId(raisedBy);
					}
					else if(userId.equals(RPMGR))
					{
						escActorId=getRMId(raisedBy);
					}
					else
					{
						escActorId=Integer.parseInt(userId);
					}
					
					if(isEscalationPermission())
					{
						if(element.isPermission())
							escalationUsersIds.add(escActorId);
					}
					else
						escalationUsersIds.add(escActorId);
				}
			}
			//}
		} catch (EscalationDaoException e) {
			logger.error("Error in getting escalation entries.");
			e.printStackTrace();
		}

		return(escalationUsersIds);
	}

	/**
	 * 
	 * @param eReqMap
	 * @return
	 */
	public int findLastAppLevel(EmpSerReqMap eReqMap)
	{
		int lastApproval = 0;

		try
		{
			switch (eReqMap.getReqTypeId())
			{
			case LEAVE_REQ:
                
				lastApproval=getLeaveLastApproval(eReqMap.getId());
				break;

			case TRAVELL_REQ:
				lastApproval = getTravelLastApproval(eReqMap.getId());
				break;

			case SODEXO_REQ:

				break;

			case LOAN_REQ:
				lastApproval = getLoanLastApproval(eReqMap.getId());
				break;

			case REIMBURSEMENT_REQ:

				break;

			case IT_SUPPORT_REQ:

				break;

			case CANDIDATE_REQ:
				lastApproval = getCandidateLastApproval(eReqMap.getId());
				break;

			case TIMESHEET_REQ:
				lastApproval = getTimesheetLastApproval(eReqMap.getId());
				break;

			case ROLLON_REQ:

				break;
				
			case ISSUES:
				lastApproval = getIssueTicketNextApproval(eReqMap.getId());
				break;	
			default:
				break;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return lastApproval;
	}

	private int getLoanLastApproval(int id) throws LoanRequestDaoException
	{
		int lastApproval = 0;
		LoanRequestDao loanReqDao = LoanRequestDaoFactory.create();
		Object[] sqlParams ={ id };
		loanReqDao.setMaxRows(1);
		LoanRequest[] loanReq =loanReqDao.findByDynamicWhere("ESR_MAP_ID = ? ORDER BY SEQUENCE DESC",sqlParams);
		if(loanReq!=null)
		{
			lastApproval = loanReq[0].getSequence();
		}
		return lastApproval;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws TimesheetReqDaoException
	 */
	private int getTimesheetLastApproval(int id) throws TimesheetReqDaoException
	{
		int lastApproval = 0;

		TimesheetReqDao tDao = TimesheetReqDaoFactory.create();
		Object[] sqlParams =
		{ id };

		tDao.setMaxRows(1);
		TimesheetReq[] tReqs = tDao.findByDynamicWhere("ESRQM_ID = ? ORDER BY SEQUENCE DESC", sqlParams);
		if (tReqs != null && tReqs.length > 0)
		{
			if (tReqs[0].getSequence() > 0)
			{
				lastApproval = tReqs[0].getSequence();
			}
		}

		return lastApproval;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws IssueApproverChainReqDaoException 	 
	 */
	private int getIssueTicketNextApproval(int id) throws IssueApproverChainReqDaoException 
	{
		int nextApproval = 0;

		IssueApproverChainReqDao  issueApprChainReqDao=IssueApproverChainReqDaoFactory.create();
		Object[] sqlParams =
		{ id };

		issueApprChainReqDao.setMaxRows(1);
		IssueApproverChainReq[] issueReq = issueApprChainReqDao.findByDynamicWhere("ESRQM_ID = ? ORDER BY SEQUENCE DESC", sqlParams);
		if (issueReq != null && issueReq.length > 0)
		{
			if (issueReq[0].getSequence() > 0)
			{
				nextApproval = issueReq[0].getSequence();
			}
		}

		return nextApproval;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws CandidateReqDaoException
	 */
	private int getCandidateLastApproval(int id) throws CandidateReqDaoException
	{
		int lastApproval = 0;

		CandidateReqDao cDao = CandidateReqDaoFactory.create();
		Object[] sqlParams =
		{ id };

		cDao.setMaxRows(1);
		CandidateReq[] cReqs = cDao.findByDynamicWhere("ESRQM_ID = ? ORDER BY CREATEDATETIME DESC", sqlParams);
		if (cReqs != null && cReqs.length > 0)
		{
			if (cReqs[0].getServed() > 0)
			{
				lastApproval = cReqs[0].getServed();
			}
		}

		return lastApproval;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws TravelReqDaoException
	 */
	private int getTravelLastApproval(int id) throws TravelReqDaoException
	{
		int lastApproval = 0;

		TravelReqDao tDao = TravelReqDaoFactory.create();
		Object[] sqlParams =
		{ id };

		tDao.setMaxRows(1);
		TravelReq[] tReqs = tDao.findByDynamicSelect("SELECT * FROM TRAVEL_REQ TR LEFT JOIN TRAVEL T ON TR.TL_REQ_ID = T.ID WHERE T.ESRQM_ID = ?",
				sqlParams);
		if (tReqs != null && tReqs.length > 0)
		{
			if (tReqs[0].getAppLevel() > 0)
			{
				lastApproval = tReqs[0].getAppLevel();
			}
		}

		return lastApproval;
	}

	private Set<Integer> findUserIdByLevelId(List<Integer> levelIds,int raisedBy)
	{
		Set<Integer> userIds = new HashSet<Integer>();
		UsersDao uDao = UsersDaoFactory.create();

		try
		{
			for (int levelId : levelIds)
			{
				Object[] sqlParams =
				{ levelId };
				Users[] users = uDao.findByDynamicSelect(
						"SELECT * FROM USERS U LEFT JOIN PROFILE_INFO P ON P.ID = U.PROFILE_ID WHERE U.STATUS = 0 AND P.LEVEL_ID = ?", sqlParams);
				if (users != null && users.length > 0)
				{
					for (Users users2 : users)
					{
						userIds.add(users2.getId());
					}
				}
				
				if(isEscalationAction())
				{
					List<Integer> escalationUsers=getEscalationUserIds("NIL", levelId, raisedBy);
					for(Integer element: escalationUsers)
					{
						userIds.add(element);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return userIds;
	}

	/**
	 * 
	 * @param appString
	 * @param appLevel
	 * @param raisedBy
	 * @return
	 */
	public Integer[] approvers(String appString, int appLevel, int raisedBy)
	{		
		Set<Integer> userIds = new HashSet<Integer>();
		List<Integer> levelIds = new ArrayList<Integer>();
		Integer[] users = new Integer[2];
		List<Integer> escalationUsers=new ArrayList<Integer>();
		
		if (appString != null && appString.length() > 0)
		{
			String[] tempLevels = appString.split(";");
			if (tempLevels.length >= appLevel && appLevel != 0)
			{
				String[] approvers = tempLevels[appLevel - 1].split(",");
				for (String str : approvers)
				{
					try
					{
						levelIds.add(Integer.parseInt(str));
					}
					catch (Exception e)
					{
						if (str.equals(HRSPOC))
						{
							users[0] = getHRSPOCId(raisedBy);
						}
						else if (str.equals(RPMGR))
						{
							users[1] = getRMId(raisedBy);
						}
						if(isEscalationAction())
						{
							List<Integer> escUserIds=getEscalationUserIds(str, 0,raisedBy);
							for(Integer element:escUserIds)
							{
								escalationUsers.add(element);
							}
						}
					}
				}
				userIds = findUserIdByLevelId(levelIds,raisedBy);
				//Code added to include escalation users in approver list
				if(isEscalationAction())
				{
					if(escalationUsers!=null)
					{
						for(Integer element: escalationUsers)
						{
							userIds.add(element);
						}
					}
				}
				
				if (users[0] != null)
				{
					userIds.add(users[0]);
				}
				if (users[1] != null)
				{
					userIds.add(users[1]);
				}
			}
		}
		
		if(userIds.contains(raisedBy))
		{
			userIds.remove(raisedBy);
		}
		
		return userIds.toArray(new Integer[userIds.size()]);
	}

	/**
	 * 
	 * @param handlerString
	 * @param raisedBy
	 * @return
	 */
	public Integer[] handlers(String handlerString, int raisedBy)
	{
		Set<Integer> userIds = new HashSet<Integer>();
		List<Integer> levelIds = new ArrayList<Integer>();
		Integer[] users = new Integer[2];
		if (handlerString != null && handlerString.length() > 0)
		{
			String[] temp = handlerString.split("\\|");
			String[] approvers = temp[1].split(",");
			List<Integer> escalationUsers=new ArrayList<Integer>();
			for (String str : approvers)
			{
				try
				{
					levelIds.add(Integer.parseInt(str));
				}
				catch (Exception e)
				{
					if (str.equals(HRSPOC)){
						users[0] = getHRSPOCId(raisedBy);						
					}
					else if (str.equals(RPMGR)){
						users[1] = getRMId(raisedBy);						
					}
					if(isEscalationAction())
					{
						List<Integer> escUserIds=getEscalationUserIds(str, 0,raisedBy);
						for(Integer element:escUserIds)
						{
							escalationUsers.add(element);
						}
					}

					/*if (str.equals(HRSPOC))
					{
						int hrspocId = getHRSPOCId(raisedBy);
						if (hrspocId != 0)
						{
							levelIds.add(hrspocId);
						}
					}
					else if (str.equals(RPMGR))
					{
						int rmId = getRMId(raisedBy);
						if (rmId != 0)
						{
							levelIds.add(rmId);
						}
					}*/
				}
			}
			userIds = findUserIdByLevelId(levelIds,raisedBy);
			
			//Code added to include escalation users in approver list
			if(escalationUsers!=null)
			{
				for(Integer element: escalationUsers)
				{
					userIds.add(element);
				}
			}


			if (users[0] != null)
				userIds.add(users[0]);
			if (users[1] != null)
				userIds.add(users[1]);
		}
		return userIds.toArray(new Integer[userIds.size()]);
	}

	/**
	 * 
	 * @param notifierString
	 * @param raisedBy
	 * @return
	 */
	public Integer[] notifiers(String notifierString, int raisedBy)	{
		Set<Integer> userIds = new HashSet<Integer>();
		List<Integer> levelIds = new ArrayList<Integer>();
		Integer[] users = new Integer[2];
		if (notifierString != null && notifierString.length() > 0){
			String[] temp = notifierString.split("\\|");
			String[] approvers = temp[1].split(",");
			for (String str : approvers){
				try{
					levelIds.add(Integer.parseInt(str));
				}catch (Exception e){
					if (str.equals(HRSPOC)){
						users[0] = getHRSPOCId(raisedBy);						
					}
					else if (str.equals(RPMGR)){
						users[1] = getRMId(raisedBy);						
					}
					/*if (str.equals(HRSPOC))
					{
						levelIds.add(getHRSPOCId(raisedBy));
					}
					else if (str.equals(RPMGR))
					{
						levelIds.add(getRMId(raisedBy));
					}*/
				}
			}
			//Disables escalation user processing for notifiers list
			boolean tempIsEsc=isEscalationAction();
			setEscalationAction(false);

			userIds = findUserIdByLevelId(levelIds,raisedBy);
			
			setEscalationAction(tempIsEsc);

			if (users[0] != null)
				userIds.add(users[0]);
			if (users[1] != null)
				userIds.add(users[1]);						
		}
		return userIds.toArray(new Integer[userIds.size()]);
	}

	/**
	 * 
	 * @param raisedBy
	 * @return
	 */
	private int getHRSPOCId(int raisedBy)
	{
		return getData(raisedBy, 1);
	}

	/**
	 * 
	 * @param raisedBy
	 * @return
	 */
	private int getRMId(int raisedBy)
	{
		return getData(raisedBy, 2);
	}

	/**
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	private int getData(int userId, int type)
	{
		int result = 0;
		ProfileInfoDao pInfoDao = ProfileInfoDaoFactory.create();
		Object[] sqlParams =
		{ userId };
		String sql = "SELECT * FROM PROFILE_INFO P LEFT JOIN USERS U ON U.PROFILE_ID = P.ID WHERE U.ID = ?";
		try
		{
			pInfoDao.setMaxRows(1);
			ProfileInfo[] profileInfo = pInfoDao.findByDynamicSelect(sql, sqlParams);
			if (profileInfo != null && profileInfo.length > 0)
			{

				switch (type)
				{
				case SPOC:
					if (!profileInfo[0].isHrSpocNull())
					{
						result = profileInfo[0].getHrSpoc();
					}
					break;

				case RM:
					if (!profileInfo[0].isReportingMgrNull())
					{
						result = profileInfo[0].getReportingMgr();
					}
					break;

				default:
					break;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws LeaveMasterDaoException 
	 * @throws LeaveReqDaoException
	 */
	private int getLeaveLastApproval(int id) throws  LeaveMasterDaoException
	{
		int lastApproval = 0;

		LeaveMasterDao  leaveDao = LeaveMasterDaoFactory.create();
		Object[] sqlParams =
		{ id };

		leaveDao.setMaxRows(1);
		LeaveMaster[] leaveDto = leaveDao.findByDynamicWhere("ESR_MAP_ID = ? ORDER BY APPLIED_DATE_TIME DESC", sqlParams);
		if (leaveDto != null && leaveDto.length > 0)
		{
			if (leaveDto[0].getServed() > 0)
			{
				lastApproval = leaveDto[0].getServed();
			}
		}

		return lastApproval;
	}

	/*public static void main(String[] args)
	{
		ProcessEvaluator pEvaluator = new ProcessEvaluator();
		String approvalChain = "1,0,0,0|HRSPOC,32;102";
		String handlerChain = "1,0,0,0|RM,31,32,RM,102";
		String notifierChain = "1,0,0,0|HRSPOC";

		for (Integer user : pEvaluator.notifiers(notifierChain, 11))
		{
			logger.info(user);
		}

	}*/
	
	public String validateProcessChain(ProcessChain processChain, int requesterId, boolean checkActiveApprovers, boolean checkActiveHandlers){
		logger.debug("validateProcessChain ---> PROCESS_CHAIN_ID="+processChain.getId()+" BEGIN");
		String ERR_CODE = "200";//OK
		ProcessEvaluator pEval = new ProcessEvaluator();
		
		if(checkActiveApprovers){
			String approvalString = processChain.getApprovalChain();		
			if(approvalString!=null){
				String[] approvalChainSeq = approvalString.split(";");
				for(int appLevel=0 ; appLevel < approvalChainSeq.length ; appLevel++){
					Integer[] approverIds = pEval.approvers(approvalString, appLevel+1, requesterId);				
					if(approverIds.length > 0)
						logger.debug("validateProcessChain ---> ERR_CODE "+ERR_CODE+" found active users in APPROVAL_LEVEL="+appLevel+" for PROCESS_CHAIN_ID="+processChain.getId());
					else{
						ERR_CODE="ERR-003";//approval string was found in process-chain. Found NO active users
						logger.error("validateProcessChain ---> ERR_CODE "+ERR_CODE+" found NO active users in APPROVAL_LEVEL="+appLevel+" for PROCESS_CHAIN_ID="+processChain.getId());
						return ERR_CODE;
					}								
				}			
				
			}else{
				ERR_CODE="ERR-002";
				logger.error("validateProcessChain ---> ERR_CODE "+ERR_CODE+" could not find ApprovalString for PROCESS_CHAIN_ID="+processChain.getId());
				return ERR_CODE;
			}
		}
		
		if(checkActiveHandlers){
			String handlerString = processChain.getHandler();		
			if(handlerString!=null){
				Integer[] handlerIds = pEval.handlers(handlerString, requesterId);
				if(handlerIds.length > 0){
					logger.debug("validateProcessChain ---> ERR_CODE  "+ERR_CODE+" found active users among the HandlerIds fetched for PROCESS_CHAIN_ID="+processChain.getId());
					/*
					 * if there is only one handler and the handler himself has requested for Sodexo
					 * show an error pop-up saying "cannot process"
					 */
					if(handlerIds.length==1){
						if(handlerIds[0] == requesterId){
							ERR_CODE="ERR-007";
							logger.error("validateProcessChain ---> ERR_CODE "+ERR_CODE+
									" the requester himself is the only available handler for PROCESS_CHAIN_ID="+processChain.getId());	
						}
						
					}
				}
				else{
					ERR_CODE="ERR-005";
					logger.error("validateProcessChain ---> ERR_CODE "+ERR_CODE+" found NO active users among the HandlerIds fetched for PROCESS_CHAIN_ID="+processChain.getId());				
				}
			}else{
				ERR_CODE="ERR-004";
				logger.error("validateProcessChain ---> ERR_CODE "+ERR_CODE+" could not find HandlerString for PROCESS_CHAIN_ID="+processChain.getId());
				return ERR_CODE;
			}					
			
		}
		
		logger.debug("validateProcessChain ---> ERR_CODE "+ERR_CODE+" PROCESS_CHAIN_ID="+processChain.getId()+" END");
		return ERR_CODE;
		
	}	
	
}
