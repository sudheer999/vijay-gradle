package com.dikshatech.portal.models;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.ContactTypeDao;
import com.dikshatech.portal.dto.ContactType;
import com.dikshatech.portal.dto.ContactTypePk;
import com.dikshatech.portal.exceptions.ContactTypeDaoException;
import com.dikshatech.portal.factory.ContactTypeDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;

public class ContactTypeModel extends ActionMethods {
	ContactTypeDao contactTypeDao  = ContactTypeDaoFactory.create();
	private static Logger logger = LoggerUtil.getLogger(CandidateModel.class);
	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		ContactType contactType  = (ContactType)form;
		DropDown dd= new DropDown();
		 try
		{
			ContactType contacts[]= contactTypeDao.findWhereCandidateIdEquals(contactType.getCandidateId());
			if(contacts!=null && contacts.length>0){
				dd.setDropDown(contacts);
			}
			request.setAttribute("actionForm", dd);
		}
		catch (ContactTypeDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public ContactType[] populateDtos(String[] delimitedStr)
	{
		ContactType[] contactType = new ContactType[delimitedStr.length];
		boolean flag=false;
		int j = 0;
		for (String eduInfo : delimitedStr)
		{
			ContactType info = new ContactType();
			String[] colfields = eduInfo.split("~=~");

			for (int i = 0; i < colfields.length; i++)
			{
				String array[]=colfields[i].split("=");
				flag=array.length>1?true:false;
				
				if (colfields[i].split("=")[0].equals("id"))
				{
					info.setId(Integer.parseInt(colfields[i].split("=")[1]));
				}
				else if (colfields[i].split("=")[0].equals("name"))
				{
					if(flag)
					info.setName(colfields[i].split("=")[1]);
				}
				else if (colfields[i].split("=")[0].equals("type"))
				{
					if(flag)
					info.setType(colfields[i].split("=")[1]);
				}
				else if (colfields[i].split("=")[0].equals("designation"))
				{
					if(flag)
					info.setDesignation(colfields[i].split("=")[1]);
				}
				else if (colfields[i].split("=")[0].equals("relationship"))
				{
					if(flag)
					info.setRelationship(colfields[i].split("=")[1]);
				}
				else if (colfields[i].split("=")[0].equals("emailId"))
				{
					if(flag)
					info.setEmailId(colfields[i].split("=")[1]);
				}
				else if (colfields[i].split("=")[0].equals("phoneNumber"))
				{
					if(flag)
					info.setPhoneNumber(colfields[i].split("=")[1]);
				}
				else if (colfields[i].split("=")[0].equals("candidateId"))
				{
					if(flag)
					info.setCandidateId(Integer.parseInt(colfields[i].split("=")[1]));
				}
				else if (colfields[i].split("=")[0].equals("userId"))
				{
					if(flag)
					info.setUserId(Integer.parseInt(colfields[i].split("=")[1]));
				}
				
			}
			contactType[j] = info;
			j++;
		}

		return contactType;
	}
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		ContactType contactType  = (ContactType)form;
		contactType.setId(0);
		try
		{
			if(contactType.getContactType()!=null && contactType.getContactType().length>0){
				ContactType contactType3[]= populateDtos(contactType.getContactType());
				String[] primaryKeys = new String[contactType3.length];
				int i=0;
				for(ContactType contactType2:contactType3){
					if(contactType.getUserId()>0){
						int id=contactType.getUserId();
						contactType2.setUserId(id);
					}
				   ContactTypePk contactTypePk=contactTypeDao.insert(contactType2);
				   primaryKeys[i]=Integer.toString(contactTypePk.getId());
				   i++;
				}
				request.setAttribute("ID", primaryKeys);
				contactType.setPrimaryKeys(primaryKeys);
			}
			
			
		}
		catch (Exception ne)
		{
			logger.info("Failed to save Contact type Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			ne.printStackTrace();
		}

		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		ContactType contactType  = (ContactType)form;
	
		try
		{
			if(contactType.getContactType()!=null && contactType.getContactType().length>0){
				ContactType contactType3[]= populateDtos(contactType.getContactType());
				String[] primaryKeys = new String[contactType3.length];
				int i=0;
				for(ContactType contactType2:contactType3){
					ContactTypePk contactTypePk = new ContactTypePk();
					if(contactType2.getId()>0){
						contactTypePk.setId(contactType2.getId());
						contactTypeDao.update(contactTypePk,contactType2);
						primaryKeys[i]=Integer.toString(contactTypePk.getId());
						   i++;
					}
					request.setAttribute("ID", primaryKeys);
					contactType.setPrimaryKeys(primaryKeys);
				}
			}
			
			
		}
		catch (Exception ne)
		{
			logger.info("Failed to save Contact type Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			ne.printStackTrace();
		}

		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}


}
