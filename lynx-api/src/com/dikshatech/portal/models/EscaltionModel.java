package com.dikshatech.portal.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dikshatech.common.db.MyDBConnect;
import com.dikshatech.common.utils.EscPermissionBean;
import com.dikshatech.common.utils.EscalationParser;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.EscalationDao;
import com.dikshatech.portal.dto.Escalation;
import com.dikshatech.portal.dto.EscalationPk;
import com.dikshatech.portal.exceptions.EscalationDaoException;
import com.dikshatech.portal.factory.EscalationDaoFactory;
import com.dikshatech.portal.forms.PortalForm;

public class EscaltionModel extends ActionMethods {

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
		String newEscalationIds="";
		
		ActionResult result = new ActionResult();
		Escalation escForm = (Escalation) form;
		escForm.setPcId(Integer.valueOf(escForm.getProcessChainId()));
		Connection conn = MyDBConnect.getConnect();
		EscalationParser escParser = new EscalationParser();
		EscalationDao escDao = EscalationDaoFactory.create(conn);
		List<Escalation> esclists = escParser.getEscalations(escForm.getProcessChainId(), new String("APPROVAL") , escForm.getApprovalChainEsc());
		List<Escalation> eschandlerlists = escParser.getEscalations(escForm.getProcessChainId(), new String("HANDLER") , escForm.getHandlerChainEsc());
		esclists.addAll(eschandlerlists);
		try{
			conn.setAutoCommit(false);
		}catch(SQLException e){
			e.printStackTrace();
		}
		for (Iterator iterator = esclists.iterator(); iterator.hasNext();) {
			Escalation escalation = (Escalation) iterator.next();
			try {
				Escalation tempEscalation[] = escDao.findByDynamicWhere(" PC_ID = ? AND ESCAL_TYPE = ? AND ( LEVEL_ID = ? OR USERS_ID = ? ) ", 
						new Object[]{escalation.getPcId(), escalation.getEscalType() , escalation.getLevelId(), escalation.getUsersId()});
				if(tempEscalation != null && tempEscalation.length > 0){
					int escTempId = tempEscalation[0].getId();
					escalation.setId(escTempId);
					escDao.update(new EscalationPk(escalation.getId()), escalation);
					newEscalationIds+=escTempId +",";
				}else {
					escDao.insert(escalation);
					newEscalationIds+=escalation.getId() +",";
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		newEscalationIds= newEscalationIds.substring(0,newEscalationIds.lastIndexOf(","));
		Escalation[] escToBeDeleted = null;
		try {
			escToBeDeleted = escDao.findByDynamicSelect("select * from ESCALATION WHERE PC_ID = ? AND ID NOT in ( " + newEscalationIds + ") " , 
					new Object[]{escForm.getProcessChainId()});
			
			if(escToBeDeleted != null && escToBeDeleted.length > 0){
				for (int i = 0; i < escToBeDeleted.length; i++) {
					escDao.delete(escToBeDeleted[i].createPk());
				}
				conn.commit();
			}
		} catch (EscalationDaoException e1) {
			e1.printStackTrace();
		} catch (SQLException ex){
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		Connection conn = MyDBConnect.getConnect();
		escForm = (Escalation) form;
		EscalationParser escParser = new EscalationParser();
		EscalationDao escDao = EscalationDaoFactory.create(conn);
		List<Escalation> esclists = escParser.getEscalations(escForm.getProcessChainId(), new String("APPROVAL") , escForm.getApprovalChainEsc());
		List<Escalation> eschandlerlists = escParser.getEscalations(escForm.getProcessChainId(), new String("HANDLER") , escForm.getHandlerChainEsc());
		esclists.addAll(eschandlerlists);
		try {
			conn.setAutoCommit(false);
			for(Escalation escElement:esclists)
				escDao.insert(escElement);
			conn.commit();
			}
		catch (Exception ex) {
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
				try {
					MyDBConnect.close(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
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
				if(escalationform != null && escalationform.getUsersId() != null){
					escalationform.setActorId(escalationform.getUsersId());
				} else if(escalationform.isLevelIdNull() !=true){
					escalationform.setActorId(String.valueOf(escalationform.getLevelId()));
				}
				String escalto = escalationform.getEscalTo();
				List<EscPermissionBean> escPermissionBeans = escalationParser.getEscalationPermissionsForUsers(escalto);
				EscPermissionBean[] escBeans = (EscPermissionBean[]) escPermissionBeans.toArray(new EscPermissionBean[escPermissionBeans.size()]);
				//escalationform.setEscPermissionBeans((EscPermissionBean[])escPermissionBeans.toArray());
				escalationform.setEscPermissionBeans(escBeans);
				
				request.setAttribute("actionForm", escalationform);
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
		Connection conn = MyDBConnect.getConnect();
		Escalation escForm = (Escalation) form;
		EscalationDao escDao = EscalationDaoFactory.create(conn);
		switch (ActionMethods.DeleteTypes.getValue(form.getdType())) {
			case DELETE : {
				try{
					conn.setAutoCommit(false);
					String escId =  escForm.getEscalationId();
					Escalation escToBeDeleted[] = escDao.findByDynamicWhere(" ID = "+ escId , null);
					escDao.delete(escToBeDeleted[0].createPk());
					conn.commit();
				}catch (SQLException e) {
					try {
						conn.rollback();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					e.printStackTrace();
				}catch (EscalationDaoException e){
					e.printStackTrace();
				}finally{
					if (conn != null){
						try {
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				break;
			}
			
			case DELETEALL : {
				try {
					conn.setAutoCommit(false);
					String pcId = escForm.getProcessChainId();
					Escalation[] escToBeDeleted = escDao.findByDynamicWhere(" PC_ID = " + pcId, null);
					for (int i = 0; i < escToBeDeleted.length; i++) {
						escDao.delete(escToBeDeleted[i].createPk());
					}
					conn.commit();
				} catch (SQLException e) {
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
