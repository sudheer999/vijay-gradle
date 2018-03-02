package com.dikshatech.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.models.LeaveModel;

import java.util.Arrays;

public final class RequestEscalation {
	private static Logger	logger	= LoggerUtil.getLogger(RequestEscalation.class);
	//Fields used for escalation action processing
	private ProcessChain requestProcessChain;
	private int processChainId;
	private int esrMapId;
	
	//Flag to identify current action is escalation action
	private boolean isEscalationAction;
	private boolean isPermission;
	
	public int getProcessChainId() {
		return processChainId;
	}

	private void setProcessChainId(int processChainId) {
		this.processChainId = processChainId;
	}
	
	public int getEsrMapId() {
		return esrMapId;
	}

	/**
	 * Sets esrMapId, processChainId and requestProcessChain object for further usage. 
	 * @param esrMapId
	 */
	public void setEsrMapId(int esrMapId) {
		this.esrMapId = esrMapId;
		
		ProcessChain reqProcessChain=getProcessChain();
		
		if(reqProcessChain!=null){
			setRequestProcessChain(reqProcessChain);
			setProcessChainId(reqProcessChain.getId());
		}
		else
		{
			logger.debug("Unable to find process chain from EMP_SER_REQ_MAP for ID: " + getEsrMapId());
		}
	}
	
	

	public ProcessChain getRequestProcessChain() {
		return requestProcessChain;
	}

	private void setRequestProcessChain(ProcessChain requestProcessChain) {
		this.requestProcessChain = requestProcessChain;
	}

	/**
	 * Initialise the ProcessEvaluator class to do escalation user processing
	 * @param evaluator
	 */
	public void setEscalationProcess (ProcessEvaluator evaluator)
	{
		evaluator.setEscalationAction(true);
		evaluator.setProcessChainId(getProcessChainId());
	}
	

	public boolean isEscalationAction() {
		return isEscalationAction;
	}
	
	
	public boolean isPermission() {
		return isPermission;
	}

	public void setPermission(boolean isPermission) {
		this.isPermission = isPermission;
	}

	/**
	 * Checks if current action is escalation action and set the escalation processing flags,
	 * esrMapId (Employee service request id), processChainId and requestProcessChain object.
	 * @param esrMapId
	 * @param userId
	 */
	public void setEscalationAction(int esrMapId,int userId) {
		this.isEscalationAction=getEscalationAction(esrMapId, userId);
		if(isEscalationAction())
			setEsrMapId(esrMapId);
	}
	/**
	 * Disable escalation user processing on given ProcessEvaluator.
	 * @param evaluator
	 */
	public void disableEscalationAction(ProcessEvaluator evaluator)
	{
		if(evaluator!=null)
			evaluator.setEscalationAction(false);
		else
			logger.debug("Unable to disable escalation action. Process evaluator does not exist.");
	}
	/**
	 * Enables escalation user processing on given ProcessEvaluator.
	 * 
	 * @param evaluator
	 */

	public void enableEscalationAction(ProcessEvaluator evaluator)
	{
		if(evaluator!=null)
			evaluator.setEscalationAction(true);
		else
			logger.debug("Unable to enable escalation action. Process evaluator does not exist.");
	}
	
	/**
	 * Disable escalation user by permission on given ProcessEvaluator.
	 * Also resets permission in current RequestEscalation object.
	 * @param evaluator
	 */
	public void disableEscalationPermission(ProcessEvaluator evaluator)
	{
		if(evaluator!=null)
		{
			evaluator.setEscalationPermission(false);
			setPermission(false);
		}
		else
			logger.debug("Unable to disable escalation action. Process evaluator does not exist.");
	}
	/**
	 * Enables escalation user by permission on given ProcessEvaluator.
	 * Also sets permission in current RequestEscalation object.
	 * @param evaluator
	 */
	public void enableEscalationPermission(ProcessEvaluator evaluator)
	{
		if(evaluator!=null)
		{
			evaluator.setEscalationPermission(true);
			setPermission(true);
		}
		else
		{
			logger.debug("Unable to enable escalation action. Process evaluator does not exist.");
		}
	}

	/**
	 * Returns true if the current user action is done by escalation actor and not approver
	 * 
	 * @param esrMapId
	 * @param userId
	 * @return
	 */
	private  boolean  getEscalationAction(int esrMapId,int userId) {
		final String QUERY_INBOX_USER="SELECT * FROM INBOX WHERE IS_ESCALATED>0 AND ASSIGNED_TO=RECEIVER_ID AND ESR_MAP_ID=? AND RECEIVER_ID=?";
		boolean result=false;
		InboxDao inboxProvider=InboxDaoFactory.create();
		
		Inbox[] currentInbox;
		try {
			currentInbox = inboxProvider.findByDynamicSelect(QUERY_INBOX_USER, new Object[]{esrMapId,userId});
			if(currentInbox.length>0)
				result=true;
			else
				result=false;
			
			return(result);
			
		} catch (InboxDaoException e) {
			logger.error("Unable to set escalation action. Error in creating inbox entries.");
			e.printStackTrace();
			return(result);
		}
	}
	/**
	 * Returns a escalation user id's from current INBOX entries for ESR_MAP_ID set by setEscalationAction
	 * @see #setEscalationAction(int, int)
	 * @return
	 */
	public List<Integer> getEscalationUserIdsFromInbox()
	{
		final String QUERY_INBOX_ESCALATION="SELECT * FROM INBOX WHERE IS_ESCALATED=2 AND ESR_MAP_ID=?";
		final String QUERY_INBOX_ESCALATION_ACTOR="SELECT * FROM INBOX WHERE IS_ESCALATED=2 AND ASSIGNED_TO=RECEIVER_ID AND ESR_MAP_ID=?";
		
		List<Integer> escalationUsersIds=new ArrayList<Integer>();
		
		try{
			String currentQuery=null;
			if(isPermission())
				currentQuery=QUERY_INBOX_ESCALATION_ACTOR;
			else
				currentQuery=QUERY_INBOX_ESCALATION;
			
			InboxDao inboxProvider=InboxDaoFactory.create();
			Inbox[] currentEscalation=inboxProvider.findByDynamicSelect(currentQuery, new Object[]{getEsrMapId()});
			
			for(Inbox element:currentEscalation)
			{
				int escActorId=element.getReceiverId();
				
				if(!escalationUsersIds.contains(escActorId))
					escalationUsersIds.add(escActorId);
			}
			return(escalationUsersIds);
		}
		catch(InboxDaoException ex){
			logger.error("Unable to get escalation inbox entries for givem EMP_SER_REQ_ID.");
			return(escalationUsersIds);
		}
	}
	/**
	 * Returns a List<String> of escalation user id for current ESR_MAP_ID set by setEscalationAction.
	 * Use getEscalationUserIdsFromInbox.
	 * @see #getEscalationUserIdsFromInbox()
	 * @return
	 */
	public List<String> getEscalationUserIdStringList()
	{
		List<String> escUserIdList=new ArrayList<String>();
		List<Integer> escUserIds=getEscalationUserIdsFromInbox();
		for(Integer id:escUserIds)
		{
			escUserIdList.add(String.valueOf(id));
		}
		
		return(escUserIdList);
	}
	
	/**
	 * Returns a comma separated value string of escalation user id for current ESR_MAP_ID set by setEscalationAction.
	 * Use getEscalationUserIdStringList.
	 * @see #getEscalationUserIdStringList()
	 * @see #setEscalationAction(int, int)
	 * @return
	 */
	public String getEscalationUserIdCsv()
	{
		String escUserIdCsv="";
		
		List<String> escUserIdList=getEscalationUserIdStringList();
		
		if(escUserIdList!=null&&escUserIdList.size()>0)
		{
			escUserIdCsv=escUserIdList.toString().replace('[', ' ').replace(']', ' ').trim();
		}
		
		return(escUserIdCsv);
	}
	/**
	 * Returns process chain object corresponding to current ESR_MAP_ID field set by setEscalationAction method
	 * @see #setEscalationAction(int, int)
	 * @return
	 */
	private ProcessChain getProcessChain()
	{
		ProcessChain currentProcessChain=null;
		
		try{
			ProcessChainDao processChainProvider=ProcessChainDaoFactory.create();
			ProcessChain[] escProcessChain=
				processChainProvider.findByDynamicWhere("ID=(SELECT PROCESSCHAIN_ID FROM EMP_SER_REQ_MAP WHERE ID=?)",
						new Object[]{getEsrMapId()});
			if(escProcessChain.length>0)
			{
				currentProcessChain=escProcessChain[0];
			}
		}
		catch(ProcessChainDaoException ex){
			logger.error("Unable to obtain process chain information for current EMP_SER_REQ_MAP ID: " + getEsrMapId());
		}
		
		return(currentProcessChain);
	}

	/**
	 * Returns true if the escalation actor and actual approver were mapped to do a designated operation.
	 * @param escalationUserId User id of escalation actor.
	 * @param actorList List of users id's inclusive of escalation actor generated by application module. 
	 * @return
	 */
	
	@SuppressWarnings("rawtypes")
	public boolean isSameLevel(int escalationUserId,Integer[] actorList)
	{
		final String QUERY_INBOX_ACTUAL="SELECT * FROM INBOX WHERE IS_ESCALATED=1 AND ASSIGNED_TO=RECEIVER_ID AND ESR_MAP_ID=?";
		boolean result=false;
		try{
			InboxDao inboxProvider=InboxDaoFactory.create();
			Inbox[] requestInbox=inboxProvider.findByDynamicSelect(QUERY_INBOX_ACTUAL, new Object[]{getEsrMapId()});
			Inbox currentRequest=requestInbox[0];
			
			int actorId=currentRequest.getReceiverId();
			List actors=Arrays.asList(actorList);

			result=actors.contains(actorId)&&actors.contains(escalationUserId);
		}
		catch(InboxDaoException ex){
			logger.error("Unble to check if approver and escalation belongs to same level from INBOX for IS_ESCALATED=1 ESR_MAP_ID: " + getEsrMapId());
		}
		return(result);
	}
}
