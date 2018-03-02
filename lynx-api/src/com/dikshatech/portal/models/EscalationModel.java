package com.dikshatech.portal.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.dikshatech.common.utils.EscPermissionBean;
import com.dikshatech.common.utils.EscalationParser;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.EscalationDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dto.Escalation;
import com.dikshatech.portal.dto.EscalationPk;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.exceptions.EscalationDaoException;
import com.dikshatech.portal.exceptions.LevelsDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.factory.EscalationDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.ResourceManager;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EscalationModel extends ActionMethods {
	
	Logger logger = LoggerUtil.getLogger(EscalationModel.class);

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Method to call for updating the edited process chain Escalation configuration to save in the existing records of TBL Escalation
	 */
	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		final String WHERE_DUP_ESCALATION="PC_ID=? AND (USERS_ID=? OR LEVEL_ID=?)";
		boolean flagRedundant=false;
		ActionResult result = new ActionResult();
		Escalation escForm = (Escalation) form;
		escForm.setPcId(Integer.valueOf(escForm.getProcessChainId()));
		Connection conn=null;
		EscalationParser escParser = new EscalationParser();
		EscalationDao escDao = EscalationDaoFactory.create(conn);
		List<Escalation> esclists = escParser.getEscalations(escForm.getProcessChainId(), new String("APPROVAL") , escForm.getApprovalChainEsc());
		List<Escalation> eschandlerlists = escParser.getEscalations(escForm.getProcessChainId(), new String("HANDLER") , escForm.getHandlerChainEsc());
		esclists.addAll(eschandlerlists);
		
		List<String> emptyLevelList=new ArrayList<String>();
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
		}catch(SQLException e){
			e.printStackTrace();
		}
		for (Iterator iterator = esclists.iterator(); iterator.hasNext();) {
			Escalation escalation = (Escalation) iterator.next();
			try {
				int levelId=escalation.getLevelId();
				if(isLevelEmpty(levelId))
				{
					emptyLevelList.add(getLevelName(levelId));
				}
				else
				{
					Escalation tempEscalation[] = escDao.findByDynamicWhere(" PC_ID = ? AND ESCAL_TYPE = ? AND ( LEVEL_ID = ? OR USERS_ID = ? ) ", 
							new Object[]{escalation.getPcId(), escalation.getEscalType() , escalation.getLevelId(), escalation.getUsersId()});
					if(tempEscalation != null && tempEscalation.length > 0){
						int escTempId = tempEscalation[0].getId();
						escalation.setId(escTempId);
						escDao.update(new EscalationPk(escalation.getId()), escalation);
					}else {
						Escalation[] existingEsc=escDao.findByDynamicWhere(WHERE_DUP_ESCALATION, 
								new Object[]{escalation.getPcId(),escalation.getUsersId(),escalation.getLevelId()});
						if(existingEsc!=null&&existingEsc.length<1)
						{
							escDao.insert(escalation);
						}
						else
						{
							logger.debug("Unbale to add escalation record for USER_ID: " + escalation.getUserId()
									+", LEVEL_ID: "+escalation.getLevelId() +", PC_ID: " + escalation.getPcId());
							flagRedundant=true;
						}
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.unabletosave"));
			}
		}
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//Delete code commented for update permission
//		newEscalationIds= newEscalationIds.substring(0,newEscalationIds.lastIndexOf(","));
//		Escalation[] escToBeDeleted = null;
//		try {
//			escToBeDeleted = escDao.findByDynamicSelect("select * from ESCALATION WHERE PC_ID = ? AND ID NOT in ( " + newEscalationIds + ") " , 
//					new Object[]{escForm.getProcessChainId()});
//			
//			if(escToBeDeleted != null && escToBeDeleted.length > 0){
//				for (int i = 0; i < escToBeDeleted.length; i++) {
//					escDao.delete(escToBeDeleted[i].createPk());
//				}
//				conn.commit();
//			}
//		} catch (EscalationDaoException e1) {
//			e1.printStackTrace();
//			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.unabletosave"));
//		} catch (SQLException ex){
//			try {
//				conn.rollback();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
		//TODO: This is a quick fix to avoid duplicate entries of RM/HRSPOC for approval/hander escalation
		//It is required to find inbox entry is in approval or handler chain to correctly apply
		//escalation if RM/HRSPOC comes as escalation user in both list. Logic not developed as on 01-Mar-2013.
		if(flagRedundant)
		{
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.partialupdate"));
		}
		
		if(emptyLevelList.size()>0)
		{
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.emptylevel",new Object[]{emptyLevelList.toString()}));
		}

		return result;
	}
	
	/**
	 * Method to call for Inserting the process chain Escalation configuration to TBL ESCALATION
	 */
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Escalation escForm = null;
		Connection conn = null;
		escForm = (Escalation) form;
		EscalationParser escParser = new EscalationParser();
		EscalationDao escDao = EscalationDaoFactory.create(conn);
		List<Escalation> esclists = escParser.getEscalations(escForm.getProcessChainId(), new String("APPROVAL") , escForm.getApprovalChainEsc());
		List<Escalation> eschandlerlists = escParser.getEscalations(escForm.getProcessChainId(), new String("HANDLER") , escForm.getHandlerChainEsc());
		List<String> emptyLevelList=new ArrayList<String>();
		
		esclists.addAll(eschandlerlists);
		try {
			conn=ResourceManager.getConnection();
			conn.setAutoCommit(false);
			for(Escalation escElement:esclists){
				int levelId=escElement.getLevelId();
				if(isLevelEmpty(levelId))
				{
					emptyLevelList.add(getLevelName(levelId));
				}
				else
				{
					Integer i = escElement.getDueDays();
					if(escElement != null && i != null)
						escDao.insert(escElement);
				}
			}
			conn.commit();
			}
		catch (Exception ex) {
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.unabletosave"));
			try {
				conn.rollback();
				ex.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		finally{
			if(conn!=null)
			{
				ResourceManager.close(conn);
			}
		}
		
		if(emptyLevelList.size()>0)
		{
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.emptylevel",new Object[]{emptyLevelList.toString()}));
		}
		
		
		
		return result;
	}
	/**
	 * Return true if there are employees in given level id, false otherwise.
	 * Used by @see {@link #update(PortalForm, HttpServletRequest)}
	 * Used by @see #save(PortalForm, HttpServletRequest)
	 * @param levelId
	 * @return
	 */
	protected boolean isLevelEmpty(int levelId)
	{
		boolean flagEmpty=true;
		
		if(levelId>0)
		{
			try
			{
				ProfileInfoDao profileProvider=ProfileInfoDaoFactory.create();
				ProfileInfo[] profiles=profileProvider.findWhereLevelIdEquals(levelId);
				

				if(profiles.length>0)
					flagEmpty=false;
				else
					flagEmpty=true;
			}
			catch(ProfileInfoDaoException ex){
				logger.error("Unable to determine if level has no users for LEVEL_ID: " + levelId);
				logger.error(ex.getMessage());
			}
		}
		else
		{
			flagEmpty=false;
		}
		
		return(flagEmpty);
	}
	
	protected String getLevelName(int levelId)
	{
		String levelName="NIL";
		try{
			LevelsDao levelProvider=LevelsDaoFactory.create();
			Levels currentLevel=levelProvider.findByPrimaryKey(levelId);
			
			if(currentLevel!=null)
				levelName=currentLevel.getLabel() +", "+currentLevel.getDesignation();
			else
				levelName="NIL";
		}catch (LevelsDaoException ex) {
			logger.error("Unable to get level name for LEVEL_ID: " + levelId);
			logger.error(ex.getMessage());
		}
		return(levelName);
	}
	
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		logger.debug("Inside the escalation Model");
		ActionResult result = new ActionResult();
		EscalationDao escalationDao = EscalationDaoFactory.create();
		switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
		
			case ESCALATION: {
				Escalation escalationform = (Escalation) form;
				EscalationParser escalationParser = new EscalationParser();
				try {
					escalationform = escalationDao.findByPrimaryKey(Integer.valueOf(escalationform.getEscalationId()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(escalationform != null){
					if(escalationform.getUsersId() != null){
						escalationform.setActorId(escalationform.getUsersId());
					} else if(escalationform.isLevelIdNull() ==false){
						escalationform.setActorId(String.valueOf(escalationform.getLevelId()));
					}
					String escalto = escalationform.getEscalTo();
					List<EscPermissionBean> escPermissionBeans = escalationParser.getEscalationPermissionsForUsers(escalto);
					EscPermissionBean[] escBeans = (EscPermissionBean[]) escPermissionBeans.toArray(new EscPermissionBean[escPermissionBeans.size()]);
					//escalationform.setEscPermissionBeans((EscPermissionBean[])escPermissionBeans.toArray());
					escalationform.setEscPermissionBeans(escBeans);
					request.setAttribute("actionForm", escalationform);
				} else{
					logger.debug("There is no such ID like " + "in the Escalations as specified in the request");
				}
				break;						
			}	
		}
		return result;
	}

	/**
	 * Method to call for deleting the Escalation TBL entries
	 */
	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Connection conn = null;
		Escalation escForm = (Escalation) form;
		EscalationDao escDao = EscalationDaoFactory.create(conn);
		switch (ActionMethods.DeleteTypes.getValue(form.getdType())) {
			case DELETE : {
				try{
					conn=ResourceManager.getConnection();
					conn.setAutoCommit(false);
					String escId =  escForm.getEscalationId();
					Escalation escToBeDeleted[] = escDao.findByDynamicWhere(" ID = "+ escId , null);
					if(escToBeDeleted != null && escToBeDeleted.length > 0){
						escDao.delete(escToBeDeleted[0].createPk());
					}
					conn.commit();
				}catch (SQLException e) {
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.unabletodelete"));
					try {
						conn.rollback();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					e.printStackTrace();
				}catch (EscalationDaoException e){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.unabletosave"));
					e.printStackTrace();
				}finally{
					if (conn != null){
						ResourceManager.close(conn);
					}
				}
				break;
			}
			
			case DELETEALL : {
				try {
					conn=ResourceManager.getConnection();
					conn.setAutoCommit(false);
					String pcId = escForm.getProcessChainId();
					Escalation[] escToBeDeleted = escDao.findByDynamicWhere(" PC_ID = " + pcId, null);
					if(escToBeDeleted != null && escToBeDeleted.length > 0){
						for (int i = 0; i < escToBeDeleted.length; i++) {
							escDao.delete(escToBeDeleted[i].createPk());
						}
					}
					conn.commit();
				} catch (SQLException e) {
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.unabletosave"));
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				} catch (EscalationDaoException e) {
					e.printStackTrace();
				}
				break;
			}
			
			case DELETEMANY:{
				try{
					conn=ResourceManager.getConnection();
					conn.setAutoCommit(false);
					String escId =  escForm.getEscalationId();
					if(escId!=null&&escId.length()>0)
					{
						String[] escIds=escId.split(",");
						for(String delEscId:escIds)
						{
							String tempEscId=delEscId.trim();
							
							if(tempEscId.length()>0)
							{
								escDao.delete(new EscalationPk(Integer.parseInt(tempEscId)));
							}
						}
						
						conn.commit();
					}
					else
					{
						logger.debug("Escalation id in empty for DELETEMANY.");
					}
					
					
				}catch (SQLException e) {
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.unabletodelete"));
					try {
						conn.rollback();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					e.printStackTrace();
				}catch (EscalationDaoException e){
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.unabletosave"));
					e.printStackTrace();
				}finally{
					if (conn != null){
						ResourceManager.close(conn);
					}
				}
				break;
			}
		}	
		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult defaultMethod(PortalForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}
