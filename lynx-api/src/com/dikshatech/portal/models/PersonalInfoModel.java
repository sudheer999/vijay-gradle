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
import com.dikshatech.portal.dao.AddressDao;
import com.dikshatech.portal.dao.CandidateDao;
import com.dikshatech.portal.dao.PersonalInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Address;
import com.dikshatech.portal.dto.AddressPk;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.PersonalInfo;
import com.dikshatech.portal.dto.PersonalInfoPk;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.UsersPk;
import com.dikshatech.portal.exceptions.AddressDaoException;
import com.dikshatech.portal.exceptions.PersonalInfoDaoException;
import com.dikshatech.portal.factory.AddressDaoFactory;
import com.dikshatech.portal.factory.CandidateDaoFactory;
import com.dikshatech.portal.factory.PersonalInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.PortalForm;

public class PersonalInfoModel extends ActionMethods
{	protected java.sql.Connection	userConn;
	private static Logger logger = LoggerUtil.getLogger(PersonalInfoModel.class);
	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		PersonalInfo  personalinfo=(PersonalInfo)form;
		PersonalInfoDao personalinfodao  =  PersonalInfoDaoFactory.create();
		PersonalInfoPk personalInfoPk=new PersonalInfoPk();
		personalInfoPk.setId(personalinfo.getId());
        try{
        	   personalinfodao.delete(personalInfoPk);
        	    
		}
		catch (Exception e)
		{
			logger.info("Failed to delete Profile Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
			
		}
		return result;
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
		PersonalInfo  personalinfo=(PersonalInfo)form;
		PersonalInfoDao personalinfodao  =  PersonalInfoDaoFactory.create();
		ActionResult result = new ActionResult();
		Candidate candidate= new Candidate();
		CandidateDao cDao1 = CandidateDaoFactory.create();
		
		AddressDao addressDao =  AddressDaoFactory.create();
		try
		{
			
			if(personalinfo.getCandidateId()>0){
				candidate.setId(personalinfo.getCandidateId());
				candidate = cDao1.findByPrimaryKey(candidate.getId());
				personalinfo.setId(candidate.getPersonalId());
			}else if(personalinfo.getUserId()!=null && personalinfo.getUserId()>0){
				 
			   
				Users user= new Users();
				UsersDao usersDao= UsersDaoFactory.create();
				user = usersDao.findByPrimaryKey(personalinfo.getUserId());
				personalinfo.setId(user.getPersonalId());
				
			}
			   PersonalInfo personalinfoDto;
				personalinfoDto = personalinfodao.findByPrimaryKey(personalinfo.getId());
				/**
				 * for address fields
				 */
				if(personalinfoDto.getPermanentAddress()>0){
					 Address permAddress=addressDao.findByPrimaryKey(personalinfoDto.getPermanentAddress());
					 personalinfoDto.setPermAddress(getAddressFields(permAddress));
					}
					if(personalinfoDto.getCurrentAddress() >0){
						 Address currAddress=addressDao.findByPrimaryKey(personalinfoDto.getCurrentAddress());
						 personalinfoDto.setCurrAddress(getAddressFields(currAddress));
					}
					//address ~ city ~ state ~zip ~ country
				request.setAttribute("actionForm", personalinfoDto);
		}catch (Exception e)
			{
				logger.info("Failed to receive single Personal Info");
				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
				e.printStackTrace();
			}
				
				
		
		return result;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		PersonalInfo  personalinfo=(PersonalInfo)form;
		personalinfo.setId(personalinfo.getId()!= 0?0:0);
		PersonalInfoDao personalinfodao  =  PersonalInfoDaoFactory.create();
		AddressDao addressDao =  AddressDaoFactory.create();
		AddressPk  addressPk = new AddressPk();
		AddressPk addressPk1= new AddressPk();
		PersonalInfoPk personalInfoPk=new PersonalInfoPk();
		CandidateModel candidateModel= new CandidateModel();
	   	  Candidate candidate= new Candidate();
	     	CandidateDao c= CandidateDaoFactory.create();
		   
		
        try{
        	//if(personalinfo.getuType()!=null  && !personalinfo.getuType().equals("replica")){
        		if(personalinfo.getuType()==null){
        	if(personalinfo.getPermAddress()!=null){
        		Address permAddress=setAddressFields(personalinfo.getPermAddress());
        		if(permAddress!=null)
        		addressPk=addressDao.insert(permAddress);
        		
        	}
        	
        	if(personalinfo.getCurrAddress()!=null){
        		Address currAddress=setAddressFields(personalinfo.getCurrAddress());
        		if(currAddress!=null)
        		addressPk1=addressDao.insert(currAddress);
        	}
        	    personalinfo.setPermanentAddress(addressPk.getId());
        	    personalinfo.setCurrentAddress(addressPk1.getId());
        	}
        		personalinfo.setModifiedBy(login.getUserId());
        	    personalInfoPk=personalinfodao.insert(personalinfo);
        	    personalinfo.setId(personalInfoPk.getId());
        	    
        	    /* 
        	 	 * Insert in candidate table the relevant id
    	       	 */
        	    if(personalinfo.getCandidateId()>0){
    	       	candidate=c.findByPrimaryKey(personalinfo.getCandidateId());
    	       	candidate.setPersonalId(personalinfo.getId());
    	       	candidate.setuType("UPDATECANDIDATE");
    	       	candidateModel.update(candidate, request);
        	    }
        	    /**
    			 * add in User Table
    			 */
    			if(personalinfo.getUserId()!=null && personalinfo.getUserId()>0){
    		    UsersPk usersPk= new UsersPk();
    			UsersDao usersDao = UsersDaoFactory.create();
    			Users users= usersDao.findByPrimaryKey(personalinfo.getUserId());
    			users.setPersonalId(personalinfo.getId());
    			usersPk.setId(users.getId());
    			usersDao.update(usersPk,users);
    			}
        	    
		if(personalinfo.getsType().equalsIgnoreCase("SAVE"))
		{
    	PersonalInfoDao pfdao = PersonalInfoDaoFactory.create();
		Users user = null;
		PersonalInfo pi = (PersonalInfo) form;
		int userID = user.getId();
	
			pfdao.updatetBlood(pi);
        }
        }
		catch (Exception e)
		{
			logger.info("Failed to save Profile Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
			
		}
		request.setAttribute("personalId", personalInfoPk);
		return result;
	}

	public Address setAddressFields(String add){
	 Address permAddress= new Address();
  	  String address[]=add.split("~");
  	  if(address.length>0){
  	  if(!address[0].equals(" "))
  	  permAddress.setAddress(address[0]);
  	  
  	 if(address.length>1)
  	 if(!address[1].equals(" "))
  	  permAddress.setCity(address[1]);
  	 
  	if(address.length>2)
  	 if(!address[2].equals(" "))
  	  	  permAddress.setState(address[2]);
  	 
  	if(address.length>3)
  	 if(!address[3].equals(""))
  	  permAddress.setZipcode(Integer.parseInt(address[3]));
  	
  	 if(address.length==5)
  	 if(!address[4].equals(" "))
  	  permAddress.setCountry(address[4]);
  	  return permAddress;
  	  }else{
  		  return null;
  	  }
		
	}

	public String getAddressFields(Address address) {
		String add = new String();
		if (address != null){
			add = address.getAddress();
			add = add + "~" + address.getCity();
			add = add + "~" + address.getState();
			add = add + "~" + address.getZipcode();
			add = add + "~" + address.getCountry();
		}
		return add;
	}
	
	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		PersonalInfo  personalinfo=(PersonalInfo)form;
		PersonalInfoDao personalinfodao  =  PersonalInfoDaoFactory.create();
		PersonalInfoPk personalInfoPk=new PersonalInfoPk();
		
		 Candidate candidate= new Candidate();
	     	CandidateDao c= CandidateDaoFactory.create();
	     	Address permAddress=new Address();
	     	Address currAddress= new Address();
	     	
	    	AddressDao addressDao =  AddressDaoFactory.create();
			 AddressPk  addressPk = new AddressPk();
			 AddressPk addressPk1= new AddressPk();
		
       try{
    	   //emerPhoneNo=44444444444
    	   //emerPhoneNo
    	   if(personalinfo.getCandidateId()>0){
				
				candidate.setId(personalinfo.getCandidateId());
				candidate = c.findByPrimaryKey(candidate.getId());
				personalinfo.setId(candidate.getPersonalId());
				personalInfoPk.setId(candidate.getPersonalId());
				        	   
    	   }
    		
           if(personalinfo.getuType().equalsIgnoreCase("UPDATECORDOVA"))
    		{
    	    	PersonalInfoDao pfdao = PersonalInfoDaoFactory.create();
    			PersonalInfo pi = (PersonalInfo) form;
    		
    				pfdao.updatetBlood(pi);
    	        }
    	   //else{
    			if(personalinfo.getPermAddress()!=null){
    				if(personalinfo.getuType()!=null && personalinfo.getuType().equals("candiatePersonal")){
    					permAddress=addressDao.findByPrimaryKey(personalinfo.getPermanentAddress());
    				}else{
    					permAddress=setAddressFields(personalinfo.getPermAddress());
    				}
    	       		if(personalinfo.getPermanentAddress()>0){
    	       			addressPk.setId(personalinfo.getPermanentAddress());
    	       			permAddress.setId(personalinfo.getPermanentAddress());
    	       			addressDao.update(addressPk,permAddress);
    	       		}else{
    	       			if(permAddress!=null)
    	       			addressPk=addressDao.insert(permAddress);
    	       		}
    	       		personalinfo.setPermanentAddress(addressPk.getId()); 
    	       	}
    	       	
    	       	if(personalinfo.getCurrAddress()!=null){
    	       		if(personalinfo.getuType().equals("candiatePersonal")){
    	       			currAddress=addressDao.findByPrimaryKey(personalinfo.getCurrentAddress());
    				}else{
    					 currAddress=setAddressFields(personalinfo.getCurrAddress());
    				}
    	       		
    	       		if(personalinfo.getCurrentAddress()>0){
    	       			addressPk1.setId(personalinfo.getCurrentAddress());
    	       			currAddress.setId(personalinfo.getCurrentAddress());
    	       			addressDao.update(addressPk1,currAddress);
    	       		}else{
    	       			if(currAddress!=null)
    	       			addressPk1=addressDao.insert(currAddress);
    	       		}
    	       		personalinfo.setCurrentAddress(addressPk1.getId());
    	       	}
    	       	
    	       
				personalInfoPk.setId(personalinfo.getId());
    	  // }
				personalinfo.setModifiedBy(login.getUserId());
		   personalinfodao.update(personalInfoPk,personalinfo);
	
	
	}
       
       
       catch (AddressDaoException   e)
		{
			logger.info("Failed to save Personal Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failedtosaveaddress"));
			e.printStackTrace();
			
		}
		catch (Exception e)
		{
			logger.info("Failed to save Profile Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
			
		}
		return result;
		
	}
	
	
	public ActionResult updatePersonalInfo(PortalForm form, HttpServletRequest request) throws SQLException, PersonalInfoDaoException {
		ActionResult result = new ActionResult();
		PersonalInfo pi = (PersonalInfo) form;
		PersonalInfoDao pfdao = PersonalInfoDaoFactory.create();
		Users user = null;
		int userID = user.getId();
	
			pfdao.updatetBlood(pi);
	
			
		
	
		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
