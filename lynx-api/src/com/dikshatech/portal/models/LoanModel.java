package com.dikshatech.portal.models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.HandlerAction;
import com.dikshatech.beans.LoanDetailsBean;
import com.dikshatech.beans.Roles;
import com.dikshatech.beans.UserLogin;
import com.dikshatech.common.utils.Loan;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.Notification;
import com.dikshatech.common.utils.ProcessEvaluator;
import com.dikshatech.common.utils.Status;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.EmpSerReqMapDao;
import com.dikshatech.portal.dao.InboxDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.LoanDetailsDao;
import com.dikshatech.portal.dao.LoanEligibilityCriteriaDao;
import com.dikshatech.portal.dao.LoanRequestDao;
import com.dikshatech.portal.dao.LoanTypeDao;
import com.dikshatech.portal.dao.ModulePermissionDao;
import com.dikshatech.portal.dao.ModulesDao;
import com.dikshatech.portal.dao.ProcessChainDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.RequestTypeDao;
import com.dikshatech.portal.dao.SalaryDetailsDao;
import com.dikshatech.portal.dao.StatusDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.EmpSerReqMap;
import com.dikshatech.portal.dto.EmpSerReqMapPk;
import com.dikshatech.portal.dto.Handlers;
import com.dikshatech.portal.dto.Inbox;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.LoanDetails;
import com.dikshatech.portal.dto.LoanDetailsPk;
import com.dikshatech.portal.dto.LoanEligibilityCriteria;
import com.dikshatech.portal.dto.LoanRequest;
import com.dikshatech.portal.dto.LoanRequestPk;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ModulePermission;
import com.dikshatech.portal.dto.Modules;
import com.dikshatech.portal.dto.ProcessChain;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.RequestType;
import com.dikshatech.portal.dto.SalaryDetails;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.DivisonDaoException;
import com.dikshatech.portal.exceptions.EmpSerReqMapDaoException;
import com.dikshatech.portal.exceptions.LevelsDaoException;
import com.dikshatech.portal.exceptions.LoanDetailsDaoException;
import com.dikshatech.portal.exceptions.LoanEligibilityCriteriaDaoException;
import com.dikshatech.portal.exceptions.LoanRequestDaoException;
import com.dikshatech.portal.exceptions.ModulePermissionDaoException;
import com.dikshatech.portal.exceptions.ModulesDaoException;
import com.dikshatech.portal.exceptions.ProcessChainDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.exceptions.RequestTypeDaoException;
import com.dikshatech.portal.exceptions.ServiceRequestDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.EmpSerReqMapDaoFactory;
import com.dikshatech.portal.factory.InboxDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.LoanDetailsDaoFactory;
import com.dikshatech.portal.factory.LoanEligibilityCriteriaDaoFactory;
import com.dikshatech.portal.factory.LoanRequestDaoFactory;
import com.dikshatech.portal.factory.LoanTypeDaoFactory;
import com.dikshatech.portal.factory.ModulePermissionDaoFactory;
import com.dikshatech.portal.factory.ModulesDaoFactory;
import com.dikshatech.portal.factory.ProcessChainDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.RequestTypeDaoFactory;
import com.dikshatech.portal.factory.SalaryDetailsDaoFactory;
import com.dikshatech.portal.factory.StatusDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;



public class LoanModel extends ActionMethods
{
	public static Logger logger = LoggerUtil.getLogger(LoanModel.class);
	
	EmpSerReqMapDao eMapDao = EmpSerReqMapDaoFactory.create();
	LoanDetailsDao loanDetlDao=LoanDetailsDaoFactory.create();	
	ProcessChainDao pChainDao = ProcessChainDaoFactory.create();
	UsersDao usersDao = UsersDaoFactory.create();
	LoanRequestDao loanReqDao = LoanRequestDaoFactory.create();
	LoanDetailsPk loanDetailsPk=null;
	
	ProcessChain proChain;
	ProcessEvaluator pEvaluator = new ProcessEvaluator();
	EmpSerReqMapPk eMapPk = null;
	EmpSerReqMap eReqMap = new EmpSerReqMap();
	ModulePermissionDao mDao = ModulePermissionDaoFactory.create();
	RegionsDao rDao = RegionsDaoFactory.create();
	ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
	MailGenerator mailGenarator = new MailGenerator();
	PortalMail portalMail=null;
	InboxModel inboxModel = new InboxModel();
	StatusDao statusDao=StatusDaoFactory.create();
	LoanTypeDao loanTypeDao=LoanTypeDaoFactory.create();
	
	LevelsDao levelDao = LevelsDaoFactory.create();
	DivisonDao divDao = DivisonDaoFactory.create();
	LoanEligibilityCriteriaDao loanECDao = LoanEligibilityCriteriaDaoFactory.create();
	Inbox inbox = null;
	SalaryDetailsDao salDao = SalaryDetailsDaoFactory.create();
	
	Modules module = null;
	ModulesDao moduleDao = ModulesDaoFactory.create();
	RequestType reqType = null;
	RequestTypeDao reqDao = RequestTypeDaoFactory.create();
	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		LoanDetails loanDetlDto = (LoanDetails) form;
		LoanDetailsDao loanDao = LoanDetailsDaoFactory.create();

		for (Integer id : loanDetlDto.getLoanIds())
		{

			try
			{
				loanDao.delete(new LoanDetailsPk(id));
			}
			catch (LoanDetailsDaoException e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request)
	{
		
		return null;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException
	{
		
		return null;
	}
	public ActionResult submit(PortalForm form, HttpServletRequest request)
	{
		
		
		return null;
	}
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		DropDown dropdown=null;
		Login login = Login.getLogin(request);
		LoanDetails[] loanDtoArr = null;
		LoanDetailsBean[] loanBeanArr = null;
		Users userDto = null;
		ProfileInfo profileInfoDto = null;
		String empSerReq=null;
		
		LoanRequest[] loanReqArr  = null;
		LoanDetailsBean loanBean = null;
		LoanEligibilityCriteria lECDto =  null;
		LoanEligibilityCriteria lECDto1 =  null;
		LoanDetails loanDto=null;
		Levels levelDto =null;
		try
		{
			int i;
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
			{
				case RECEIVEALLLOAN:
					logger.info("<<<<<<<>>>>>>>>>INSIDE RECEIVE ALL LOAN<<<<<<<<>>>>>>>>");
					dropdown= (DropDown)form;
					loanDtoArr = loanDetlDao.findWhereRequesterIdEquals(login.getUserId());
					loanBeanArr = new LoanDetailsBean[loanDtoArr.length];
					i=0;
					//LoanDetailsBean tmpLonBean =null;
					Object[] toHand=new Object[2];
			        boolean isApprov;
			        //boolean isHand;
					for (LoanDetails tmpLonDto : loanDtoArr)
					{
						loanBean = DtoToBeanConverter.DtoTOBeanConverter(tmpLonDto,null);
						
						LoanRequest[] loanReq = loanReqDao.findByLoanDetails(tmpLonDto.getId());
						
						if(loanReq.length!=0)
						{
							EmpSerReqMap[] empSerMap = eMapDao.findByDynamicSelect("select * from EMP_SER_REQ_MAP where ID=? ", new Object[]{loanReq[0].getEsrMapId()});
							if(empSerMap.length>0)
							{
								empSerReq = empSerMap[0].getReqId();
								loanBean.setLoanReqId(empSerReq);
							}
						}
						else
						{
							empSerReq = " ";
							loanBean.setLoanReqId(empSerReq);
						}
						loanBean.setLoanReceiveId(tmpLonDto.getId());
						loanBeanArr[i] = loanBean;
						i++;
						/*if(isApprover(login.getUserId()))
						{
							loanBean.setToApprove(isApprover(login.getUserId()));
						}
						
						if(isHandler(login.getUserId()))
						{
							loanBean.setToHandle(isHandler(login.getUserId()));
						}*/
					}
					/*if(isApprover(login.getUserId()))
					{
						loanBean.setToApprove(isApprover(login.getUserId()));
					}
					
					if(isHandler(login.getUserId()))
					{
						loanBean.setToHandle(isHandler(login.getUserId()));
					}*/
					LoanDetails allDto = new LoanDetails();
					allDto.setToApprove(isApprover(login.getUserId()));
					allDto.setToHandle(isHandler(login.getUserId()));
					allDto.setLoanBeanArr(loanBeanArr);
										
					dropdown.setDetail(allDto);
									
					request.setAttribute("actionForm", dropdown);
					
					
				break;
				case RECEIVELOAN:
				
					logger.info("<<<<<<<>>>>>>>>>INSIDE SINGLE RECEIVE LOAN<<<<<<<<>>>>>>>>");
					LoanDetails loanDtoForm = (LoanDetails) form;
					loanBeanArr = new LoanDetailsBean[1];
					LoanDetails loanform =  loanDetlDao.findByPrimaryKey((loanDtoForm.getLoanReceiveId()));
					loanBean=DtoToBeanConverter.DtoTOBeanConverter(loanform,null);
					userDto = usersDao.findByPrimaryKey(loanform.getRequesterId());
					profileInfoDto=profileInfoDao.findByPrimaryKey(userDto.getProfileId());
					LoanRequest[] tmploanReq = loanReqDao.findByDynamicSelect("SELECT * FROM LOAN_REQUEST WHERE STATUS_ID = "+loanform.getStatusId()+" AND LOAN_ID = ?", new Object[]{loanform.getId()});
					double amount=0;
					amount += loanform.getEligibilityAmount();
					
					loanBean.setAvailableAmt(amount);
					if(tmploanReq.length!=0)
					{
						EmpSerReqMap[] empSerMap = eMapDao.findByDynamicSelect("select * from EMP_SER_REQ_MAP where ID=? ", new Object[]{tmploanReq[0].getEsrMapId()});
						if(empSerMap.length>0)
						{	
							empSerReq = empSerMap[0].getReqId();
							loanBean.setLoanReqId(empSerReq);
						}
					}
					loanBean.setLoanReqId(empSerReq);
					for(LoanRequest tmpReq : tmploanReq)
					{	
    					if(tmpReq.getActionTakenBy()!=0)
    					{
        					userDto = usersDao.findByPrimaryKey(tmpReq.getActionTakenBy());
        					profileInfoDto=profileInfoDao.findByPrimaryKey(userDto.getProfileId());
        					loanBean.setResponseBy(profileInfoDto.getFirstName());
        					loanBean.setResponseDate(tmpReq.getActionTakenDate().toString());
    					}
    					if(tmpReq.getComments()!=null)
    					{
    						loanBean.setComment(tmpReq.getComments());
    					}
					}
					loanBean.setRequestedOn(loanform.getApplyDate().toString());
    				loanBean.setLoanReceiveId(loanform.getId());
					loanBeanArr[0]=loanBean;
					request.setAttribute("actionForm", loanBeanArr);
				break;
				case RECEIVEALLTOAPPROVE:
					logger.info("<<<<<<<>>>>>>>>>INSIDE RECEIVE ALL LOAN TO APPROVE<<<<<<<<>>>>>>>>");
					dropdown= (DropDown)form;
					
					loanReqArr = loanReqDao.findByDynamicSelect("select * from LOAN_REQUEST where STATUS_ID!=16 AND SEQUENCE !=0 AND ASSIGN_TO=?", new Object[]{login.getUserId()});
					HandlerAction hAction = new HandlerAction();
					int showApprov=0;
					HashMap<Integer, LoanDetailsBean> beanMap = new HashMap<Integer, LoanDetailsBean>();
					//Set<LoanDetailsBean>
					if(loanReqArr.length!=0)
					{
    					loanBeanArr = new LoanDetailsBean[loanReqArr.length];
       					i=0;
       					boolean  flag = true;
       					for (LoanRequest tmpLonReq : loanReqArr)
    					{
       						flag = true;
       						LoanRequest testLoanArr[] = loanReqDao.findByDynamicSelect("select * from LOAN_REQUEST where STATUS_ID in(2,3,11,12,13,14,18,19,20,21) AND LOAN_ID=?", new Object[]{tmpLonReq.getLoanId()});
       						if(beanMap.containsKey(tmpLonReq.getLoanId()))
							{
       							beanMap.remove(tmpLonReq.getLoanId());
							}
       						
       						loanBean = DtoToBeanConverter.DtoTOBeanConverter(tmpLonReq, null);
        					Users user = usersDao.findByPrimaryKey(tmpLonReq.getLoanUserId());
        					profileInfoDto = profileInfoDao.findByPrimaryKey(user.getProfileId());
        					
        					loanBean.setRequestedBy(profileInfoDto.getFirstName());
        					loanBean.setRequestedOn(tmpLonReq.getCreatedDatetime().toString());
        					loanBean.setLoanReceiveId(tmpLonReq.getLoanId());
        					
        					if(tmpLonReq!=null)
        					{
        						EmpSerReqMap[] empSerMap = eMapDao.findByDynamicSelect("select * from EMP_SER_REQ_MAP where ID=? ", new Object[]{tmpLonReq.getEsrMapId()});
        						if(empSerMap.length>0)
        						{	
        							empSerReq = empSerMap[0].getReqId();
        							loanBean.setLoanReqId(empSerReq);
        						}
        					}
        					loanBean.setLoanReqId(empSerReq);
        					if(testLoanArr.length>0)
        					{
        						for(LoanRequest testReq : testLoanArr)
        						{
        							if(testReq.getLoanId() == tmpLonReq.getLoanId()){
        								loanBean.setLoanStatus(Status.getStatus(testReq.getStatusId()));
        								if(testReq.getStatusId()==Status.getStatusId(Status.ON_HOLD) && testReq.getActionTakenBy()==login.getUserId()){
                    						hAction.setApprove(1);
                        					hAction.setReject(1);
        								}
        								hAction.setApprove(0);
                						hAction.setReject(0);
                						hAction.setOnHold(0);
                						flag = false;
        							}
        							else if(testReq.getStatusId()==Status.getStatusId(Status.CANCEL)){
        								loanBean.setLoanStatus(Status.CANCEL);
                						hAction.setApprove(1);
                						hAction.setReject(1);
                						hAction.setOnHold(1);
                						++showApprov;
        							}
        						}
        					}
        					else if(tmpLonReq.getStatusId()==Status.getStatusId(Status.SUBMITTED) && flag){
            					loanBean.setLoanStatus(Status.NEW);
            					hAction.setApprove(1);
            					hAction.setReject(1);
            					hAction.setOnHold(1);
            					++showApprov;
            				}	
        					
        					loanBean.setHandlerAction(hAction);	
        					
        					beanMap.put(tmpLonReq.getLoanId(), loanBean);
        					i++;
        					dropdown.setDetail(beanMap.values().toArray());
        				}
       				}
					dropdown.setKey1(showApprov);
					
					request.setAttribute("actionForm", dropdown);
				break;
				case RECEIVETOAPPROVE:
					logger.info("<<<<<<<>>>>>>>>>INSIDE SINGLE RECEIVE LOAN TO APPROVE<<<<<<<<>>>>>>>>");					
					LoanDetails approveloanForm = (LoanDetails) form;
					loanBeanArr = new LoanDetailsBean[1];
					HandlerAction handler = new HandlerAction();
					InboxDao iDao = InboxDaoFactory.create();
					Inbox inbox= null;
					if(approveloanForm.getEsrqmId()!=0)
					{
						
						inbox= iDao.findByDynamicSelect("select * from INBOX where ESR_MAP_ID=? order by ID desc", new Object[]{approveloanForm.getEsrqmId()})[0];
						if(inbox !=null)
						{
							loanReqArr = loanReqDao.findByDynamicSelect("select * from LOAN_REQUEST where ESR_MAP_ID=? AND ASSIGN_TO= ? ", new Object[]{inbox.getEsrMapId(), login.getUserId()});
							for(LoanRequest loanReq : loanReqArr)
        					{
        						/*if(loanReq.getAssignTo()==login.getUserId()&&loanReq.getStatusId()==Status.getStatusId(inbox.getStatus()))
        						{*/
        							loanDto = loanDetlDao.findByPrimaryKey(loanReq.getLoanId());
                				
        							loanBean=DtoToBeanConverter.DtoTOBeanConverter(loanReq,null);
        							loanBean.setGrossSalary(loanDto.getGrossSalary());			
        							loanBean.setNetSalary(loanDto.getNetSalary());
        							loanBean.setEligibilityAmt(loanDto.getEligibilityAmount());
        							loanBean.setEmiEligibility(loanDto.getEmiEligibility());
        							loanBean.setEmiPeriod(loanDto.getEmiPeriod());
        							loanBean.setLoanReceiveId(loanDto.getId());
        							loanBean.setPurpose(loanDto.getPurpose());
        							loanBean.setRequestedOn(loanReq.getCreatedDatetime().toString());
        							//profileInfoDto=profileInfoDao.findByPrimaryKey(loanReq.getLoanUserId());
        							userDto = usersDao.findByPrimaryKey(loanReq.getLoanUserId());
        							profileInfoDto=profileInfoDao.findByPrimaryKey(userDto.getProfileId());
                					               				
        							if(loanReq!=null)
        							{
        								EmpSerReqMap empSerMap = eMapDao.findByPrimaryKey(loanReq.getEsrMapId());
        								if(empSerMap!=null)
        									empSerReq = empSerMap.getReqId();
        							}
        							if (loanReq.getActionTakenBy() != 0 && ((loanReq.getStatusId() == Status.getStatusId(Status.ACCEPTED)) 
											||(loanReq.getStatusId() == Status.getStatusId(Status.REJECTED)) 
											||(loanReq.getStatusId() == Status.getStatusId(Status.ON_HOLD))
											||loanReq.getStatusId() == Status.getStatusId(Status.CANCEL))) {
        								if(loanReq.getStatusId() == Status.getStatusId(Status.ON_HOLD) && loanReq.getActionTakenBy() == login.getUserId()){
        									handler.setApprove(1);
        									handler.setReject(1);
        								}
        								else{
        									handler.setApprove(0);
        									handler.setReject(0);
        									handler.setOnHold(0);
        								}
        								
        							}
        							else{
        								handler.setApprove(1);
    									handler.setReject(1);
    									handler.setOnHold(1);
    									
        							}
        							loanBean.setHandlerAction(handler);	
                					loanBean.setLoanReqId(empSerReq);
        							loanBean.setRequestedBy(profileInfoDto.getFirstName());
                        			/*
                        				levelDto = levelDao.findByPrimaryKey(profileInfoDto.getLevelId());
                        				divDto  = divDao.findByPrimaryKey(levelDto.getDivisionId());
                        				loanBean.setDepartment(divDto.getName());
                        				loanBean.setDesignation(levelDto.getDesignation());
                        			*/
        							loanBeanArr[0]=loanBean;
        							request.setAttribute("actionForm", loanBeanArr);
        						//}
        					}
						}
					}	
					if(approveloanForm.getLoanReceiveId()!=0)
        			{
        				loanReqArr = loanReqDao.findByDynamicSelect("select * from LOAN_REQUEST where LOAN_ID = ? AND ASSIGN_To= ? ", new Object[]{approveloanForm.getLoanReceiveId(), login.getUserId()});
        				for(LoanRequest loanReq : loanReqArr)
        				{
        					loanDto = loanDetlDao.findByPrimaryKey(loanReq.getLoanId());
                			loanBean=DtoToBeanConverter.DtoTOBeanConverter(loanReq,null);
                    		loanBean.setGrossSalary(loanDto.getGrossSalary());			
                    		loanBean.setNetSalary(loanDto.getNetSalary());
                    		loanBean.setEligibilityAmt(loanDto.getEligibilityAmount());
                    		loanBean.setEmiEligibility(loanDto.getEmiEligibility());
                    		loanBean.setEmiPeriod(loanDto.getEmiPeriod());
                    		loanBean.setLoanReceiveId(loanDto.getId());
                    		loanBean.setPurpose(loanDto.getPurpose());
                    		loanBean.setRequestedOn(loanReq.getCreatedDatetime().toString());
                    		userDto = usersDao.findByPrimaryKey(loanReq.getLoanUserId());
							profileInfoDto=profileInfoDao.findByPrimaryKey(userDto.getProfileId());
                    		
                    				
                       		if(loanReq!=null)
                    		{
                    			EmpSerReqMap empSerMap = eMapDao.findByPrimaryKey(loanReq.getEsrMapId());
                    			if(empSerMap!=null)
                    				empSerReq = empSerMap.getReqId();
                    		}
                       		if (loanReq.getActionTakenBy() != 0 && ((loanReq.getStatusId() == Status.getStatusId(Status.ACCEPTED)) 
									||(loanReq.getStatusId() == Status.getStatusId(Status.REJECTED)) 
									||(loanReq.getStatusId() == Status.getStatusId(Status.ON_HOLD))
									||loanReq.getStatusId() == Status.getStatusId(Status.CANCEL))) {
								if(loanReq.getStatusId() == Status.getStatusId(Status.ON_HOLD) && loanReq.getActionTakenBy() == login.getUserId()){
									handler.setApprove(1);
									handler.setReject(1);
								}
								else{
									handler.setApprove(0);
									handler.setReject(0);
									handler.setOnHold(0);
								}
								
							}
							else{
								handler.setApprove(1);
								handler.setReject(1);
								handler.setOnHold(1);
								
							}
							loanBean.setHandlerAction(handler);	
                    		loanBean.setLoanReqId(empSerReq);
                    		loanBean.setRequestedBy(profileInfoDto.getFirstName());
                    			/*
                    			levelDto = levelDao.findByPrimaryKey(profileInfoDto.getLevelId());
                    			divDto  = divDao.findByPrimaryKey(levelDto.getDivisionId());
                    			loanBean.setDepartment(divDto.getName());
                    			loanBean.setDesignation(levelDto.getDesignation());
                    			*/
                    		loanBeanArr[0]=loanBean;
                    		request.setAttribute("actionForm", loanBeanArr);
        			}
            					
        	}
					
			
				break;
				case NEWLOANRECEIVE:
					logger.info("<<<<<<<>>>>>>>>>INSIDE NEW LOAN RECEIVE<<<<<<<<>>>>>>>>");
					loanDto = (LoanDetails)form; 
					userDto = usersDao.findByPrimaryKey(login.getUserId());
					profileInfoDto=profileInfoDao.findByPrimaryKey(userDto.getProfileId());
					levelDto = levelDao.findByPrimaryKey(profileInfoDto.getLevelId()); 
					LoanEligibilityCriteria lECDtoArr[] = loanECDao.findWhereLabelEquals(levelDto.getLabel());
					double amt = 0;
					//Object[] loanData =new Object[2];
					List<Object> loan  = new ArrayList<Object>();
					lECDto = lECDtoArr[0];
					lECDto1 = lECDtoArr[1];
					loanBeanArr = new LoanDetailsBean[1];
					loanBean = new LoanDetailsBean();
					if(lECDtoArr!=null && lECDtoArr.length>0)
					{
						if(loanDto.getLoanTypeId()==1)
						{
    						logger.info("<<<<<<<>>>>>>>>>PERSONAL LOAN<<<<<<<<>>>>>>>>");
    						LoanDetails newPerLon[] = loanDetlDao.findByDynamicSelect("select * from LOAN_DETAILS where REQUESTER_ID = ?" , new Object[]{login.getUserId()});
    						for(LoanDetails chkLon : newPerLon)
    						{
    							if(chkLon.getStatusId()==Status.getStatusId(Status.NOT_SUBMITTED))
    								amt = 0;
    							else if(chkLon.getLoanTypeId()==loanDto.getLoanTypeId())
    									amt += chkLon.getApprovedAmount();
    							//prevLoans.put(chkLon.getApplyDate().toString(), chkLon.getApprovedAmount());
    							//loanData[0]=chkLon.getApplyDate().toString();
    							//loanData[1]=chkLon.getApprovedAmount();
    							List<Object> req = new ArrayList<Object>();
    							req.add(chkLon.getApplyDate().toString());
    							req.add(Loan.getLoanType(chkLon.getLoanTypeId()));
    							req.add(chkLon.getApprovedAmount());
    							loan.add(req);
    						}
    						loanBean.setLoanReqList(loan);
    						if(lECDto.getLabel().equals("L3"))
        					{
        						loanBean.setEligibilityAmt(lECDto.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto.getEmiEligibility());
        						
        					}
        					if(lECDto.getLabel().equals("L4"))
        					{
        						loanBean.setEligibilityAmt(lECDto.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto.getEmiEligibility());
        					}
        					else if(lECDto.getLabel().equals("L5"))
        					{
        						loanBean.setEligibilityAmt(lECDto.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto.getEmiEligibility());
        					}
        					else if(lECDto.getLabel().equals("L6"))
        					{
        						loanBean.setEligibilityAmt(lECDto.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto.getEmiEligibility());
        					}
        					else if(lECDto.getLabel().equals("L7"))
        					{
        						loanBean.setEligibilityAmt(lECDto.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto.getEmiEligibility());
        					}
        					else if(lECDto.getLabel().equals("L8"))
        					{
        						loanBean.setEligibilityAmt(lECDto.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto.getEmiEligibility());
        					}
        					else if(lECDto.getLabel().equals("L9"))
        					{
        						loanBean.setEligibilityAmt(lECDto.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto.getEmiEligibility());
        					}
        					else if(lECDto.getLabel().equals("L10"))
        					{
        						loanBean.setEligibilityAmt(lECDto.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto.getEmiEligibility());
        					}
        					else if(lECDto.getLabel().equals("L11"))
        					{
        						loanBean.setEligibilityAmt(lECDto.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto.getEmiEligibility());
        					}
        					else if(lECDto.getLabel().equals("L12"))
        					{
        						loanBean.setEligibilityAmt(lECDto.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto.getEmiEligibility());
        					}
    					}
    					if(loanDto.getLoanTypeId()==2)
    					{
    						logger.info("<<<<<<<>>>>>>>>>LAPTOP LOAN<<<<<<<<>>>>>>>>");
    						LoanDetails newLapLon[] = loanDetlDao.findByDynamicSelect("select * from LOAN_DETAILS where REQUESTER_ID = ? and LOAN_TYPE_ID = "+loanDto.getLoanTypeId(), new Object[]{login.getUserId()});
    						for(LoanDetails chkLon : newLapLon)
    						{
    							if(chkLon.getStatusId()==Status.getStatusId(Status.NOT_SUBMITTED))
    								amt = 0;
    							else if(chkLon.getLoanTypeId() == loanDto.getLoanTypeId())
    								amt += chkLon.getApprovedAmount();
    							//amt += chkLon.getApprovedAmount();
    							List<Object> req = new ArrayList<Object>();
    							req.add(chkLon.getApplyDate().toString());
    							req.add(Loan.getLoanType(chkLon.getLoanTypeId()));
    							req.add(chkLon.getApprovedAmount());
    							loan.add(req);
    						}
    						if(lECDto1.getLabel().equals("L3"))
        					{
        						loanBean.setEligibilityAmt(lECDto1.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto1.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto1.getEmiEligibility());
        					}
        					if(lECDto1.getLabel().equals("L4"))
        					{
        						loanBean.setEligibilityAmt(lECDto1.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto1.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto1.getEmiEligibility());
        					}
        					else if(lECDto1.getLabel().equals("L5"))
        					{
        						loanBean.setEligibilityAmt(lECDto1.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto1.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto1.getEmiEligibility());
        					}
        					else if(lECDto1.getLabel().equals("L6"))
        					{
        						loanBean.setEligibilityAmt(lECDto1.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto1.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto1.getEmiEligibility());
        					}
        					else if(lECDto1.getLabel().equals("L7"))
        					{
        						loanBean.setEligibilityAmt(lECDto1.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto1.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto1.getEmiEligibility());
        					}
        					else if(lECDto.getLabel().equals("L8"))
        					{
        						loanBean.setEligibilityAmt(lECDto1.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto1.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto1.getEmiEligibility());
        					}
        					else if(lECDto1.getLabel().equals("L9"))
        					{
        						loanBean.setEligibilityAmt(lECDto1.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto1.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto1.getEmiEligibility());
        					}
        					else if(lECDto1.getLabel().equals("L10"))
        					{
        						loanBean.setEligibilityAmt(lECDto1.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto1.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto1.getEmiEligibility());
        					}
        					else if(lECDto1.getLabel().equals("L11"))
        					{
        						loanBean.setEligibilityAmt(lECDto1.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto1.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto1.getEmiEligibility());
        					}
        					else if(lECDto1.getLabel().equals("L12"))
        					{
        						loanBean.setEligibilityAmt(lECDto1.getMaxAmountLimit());
        						loanBean.setAvailableAmt(lECDto1.getMaxAmountLimit()-amt);
        						loanBean.setEmiEligibility(lECDto1.getEmiEligibility());
        					}
    					}
								
        				userDto = usersDao.findByPrimaryKey(login.getUserId());
    					SalaryDetails[] salDtlDto = salDao.findWhereUserIdEquals(userDto.getId());
    					double[] sal = grossSalaryCalc(salDtlDto);
    					loanBean.setEmpId(login.getUserId());
    					loanBean.setGrossSalary(sal[0]);
    					loanBean.setNetSalary(sal[1]);
    					loanBeanArr[0]=loanBean;
    					request.setAttribute("actionForm", loanBeanArr);
    				}
					else
					{
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.submit"));
						result.setForwardName("success");
						//e.printStackTrace();
					}
				break;
				case RECEIVEALLTYPES:
					logger.info("<<<<<<<>>>>>>>>>INSIDE RECEIVE ALL LOAN TYPES<<<<<<<<>>>>>>>>");
					dropdown = (DropDown) form;
					 Object ll[]= loanTypeDao.findAll();
					dropdown.setDropDown(ll);
					request.setAttribute("actionForm", dropdown);
				break;
				case RECEIVEALLSTATUS:
					logger.info("<<<<<<<>>>>>>>>>INSIDE RECEIVE ALL STATUS<<<<<<<<>>>>>>>>");
					dropdown = (DropDown) form;
					ArrayList<com.dikshatech.portal.dto.Status> status = new ArrayList<com.dikshatech.portal.dto.Status>();
					com.dikshatech.portal.dto.Status inProgressStatus = statusDao.findByPrimaryKey(Status.getStatusId(Status.INPROGRESS));
					com.dikshatech.portal.dto.Status completeStatus = statusDao.findByPrimaryKey(Status.getStatusId(Status.COMPLETE));
					status.add(inProgressStatus);
					
					status.add(completeStatus);
					dropdown.setDropDown(status.toArray());
					request.setAttribute("actionForm", dropdown);
				break;
				case RECEIVELOANHANDLERS:
					logger.info("<<<<<<<>>>>>>>>>INSIDE RECEIVE ALL LOAN HANDLERS<<<<<<<<>>>>>>>>");
					dropdown = (DropDown) form;
					setProChain(request);
					loanDtoArr = loanDetlDao.findAll();
					loanReqArr = loanReqDao.findByDynamicSelect("select * from LOAN_REQUEST where LOAN_ID=? ", new Object[]{loanDtoArr[0].getId()});
					Object[] usersId= pEvaluator.handlers(proChain.getHandler(),loanReqArr[0].getLoanUserId());
					
					Handlers[] handlersArr = new Handlers[usersId.length];
					
					int l = 0;
					for (Object id : usersId)
					{	
						Handlers handlers = new Handlers();
						Users user = usersDao.findByPrimaryKey((Integer)id);
						profileInfoDto = profileInfoDao.findByPrimaryKey(user.getProfileId());
						handlers.setId(user.getId());
						handlers.setName(profileInfoDto.getFirstName());
						handlersArr[l] = handlers;
						l++;
					}
					
					dropdown.setDropDown(handlersArr);
					request.setAttribute("actionForm", dropdown);
					
				break;
				case RECEIVEALLTOHANDLE:
					logger.info("<<<<<<<>>>>>>>>>INSIDE  RECEIVE ALL LOAN TO HANDLE<<<<<<<<>>>>>>>>");
					LoanRequest[] loanSeqArr  = null;
					dropdown = (DropDown)form;
					loanReqArr = loanReqDao.findByDynamicSelect("select * from LOAN_REQUEST where ASSIGN_TO = ? AND SEQUENCE = 0 ", new Object[]{login.getUserId()});
					int showhandle=0;			
					loanBeanArr = new LoanDetailsBean[loanReqArr.length];
					i=0;
					boolean flag=true;
					HashMap<Integer, LoanDetailsBean> beanHandMap = new HashMap<Integer, LoanDetailsBean>();
					HandlerAction toHandAction = new HandlerAction();
					for (LoanRequest tmpLonReq : loanReqArr)
					{
						if(beanHandMap.containsKey(tmpLonReq.getLoanId()))
						{
							beanHandMap.remove(tmpLonReq.getLoanId());
						}
   						
						
							userDto =usersDao.findByPrimaryKey(loanReqArr[0].getLoanUserId());
							profileInfoDto=profileInfoDao.findByPrimaryKey(userDto.getProfileId());
							LoanDetailsBean tmpLonBean = DtoToBeanConverter.DtoTOBeanConverter(tmpLonReq, null);
            				
            				tmpLonBean.setRequestedBy(profileInfoDto.getFirstName());
            				tmpLonBean.setRequestedOn(tmpLonReq.getCreatedDatetime().toString());
            				tmpLonBean.setLoanReceiveId(tmpLonReq.getLoanId());
            				
            				if(tmpLonReq!=null)
            				{
            					EmpSerReqMap empSerMap = eMapDao.findByPrimaryKey(tmpLonReq.getEsrMapId());
            					if(empSerMap!=null)
            					{	
            						empSerReq = empSerMap.getReqId();
            						tmpLonBean.setLoanReqId(empSerReq);
            					}
            				}
            				tmpLonBean.setLoanReqId(empSerReq);
            				
            				if(tmpLonReq.getStatusId()==Status.getStatusId(Status.COMPLETE)
            						||tmpLonReq.getStatusId()==Status.getStatusId(Status.INPROGRESS))
             				{
             					tmpLonBean.setLoanStatus(Status.getStatus(tmpLonReq.getStatusId()));
             					//loanBeanArr[i] = tmpLonBean;
             					flag=false;
             				}
            				else if((tmpLonReq.getStatusId()==Status.getStatusId(Status.SUBMITTED))	&& tmpLonReq.getActionTakenBy()==0)
    						{
            					tmpLonBean.setLoanStatus(Status.NEW);
            					//loanBeanArr[i] = tmpLonBean;
            					
            					++showhandle;
    						}
            				else if(tmpLonReq.getStatusId()==Status.getStatusId(Status.ASSIGNED)
            						
            						||tmpLonReq.getStatusId()==Status.getStatusId(Status.CANCEL))
            				{
            					tmpLonBean.setLoanStatus(Status.getStatus(tmpLonReq.getStatusId()));
            					//loanBeanArr[i] = tmpLonBean;
            					++showhandle;
            				}
            				
            				beanHandMap.put(tmpLonReq.getLoanId(), tmpLonBean);
    					i++;
        			}
					dropdown.setDetail(beanHandMap.values().toArray());
					dropdown.setKey1(showhandle);
					request.setAttribute("actionForm", dropdown);
				break;
				case RECEIVETOHANDLE:
					logger.info("<<<<<<<>>>>>>>>>INSIDE  SINGLE RECEIVE LOAN TO HANDLE<<<<<<<<>>>>>>>>");
					LoanDetails handloanForm = (LoanDetails) form;
					loanBeanArr = new LoanDetailsBean[1];
					InboxDao inDao = InboxDaoFactory.create();
					Inbox ibox= null;
									
					if(handloanForm.getEsrqmId()!=0)
					{
						//ibox= inDao.findByPrimaryKey(handloanForm.getLoanReceiveId());
						ibox = inDao.findByDynamicSelect("select * from INBOX where ESR_MAP_ID=? AND ASSIGNED_TO=? order by ID desc", new Object[]{handloanForm.getEsrqmId(), login.getUserId() })[0];
						if(ibox!=null)
						{
							loanReqArr = loanReqDao.findByDynamicSelect("select * from LOAN_REQUEST where ESR_MAP_ID=? AND ASSIGN_TO= ? ", new Object[]{ibox.getEsrMapId(),login.getUserId()});
							for(LoanRequest loanReq : loanReqArr)
							{
								if(loanReq.getAssignTo()==login.getUserId()&&loanReq.getStatusId()==Status.getStatusId(ibox.getStatus()))
								{
					       			LoanRequest lnHandlReq = loanReq;
                    				loanDto = loanDetlDao.findByPrimaryKey(loanReq.getLoanId());
                    				loanBean=DtoToBeanConverter.DtoTOBeanConverter(lnHandlReq,null);
                    				loanBean.setGrossSalary(loanDto.getGrossSalary());			
                    				loanBean.setNetSalary(loanDto.getNetSalary());
                    				loanBean.setEligibilityAmt(loanDto.getEligibilityAmount());
                    				loanBean.setEmiEligibility(loanDto.getEmiEligibility());
                    				loanBean.setEmiPeriod(loanDto.getEmiPeriod());
                    				loanBean.setLoanReceiveId(loanDto.getId());
                    				loanBean.setPurpose(loanDto.getPurpose());
                    				userDto = usersDao.findByPrimaryKey(lnHandlReq.getLoanUserId());
                    				profileInfoDto=profileInfoDao.findByPrimaryKey(userDto.getProfileId());
                    				if(lnHandlReq!=null)
                    				{
                    					EmpSerReqMap empSerMap = eMapDao.findByPrimaryKey(lnHandlReq.getEsrMapId());
                    					if(empSerMap!=null)
                    						empSerReq = empSerMap.getReqId();
                    				}
                    				loanBean.setLoanReqId(empSerReq);
                    				loanBean.setRequestedBy(profileInfoDto.getFirstName());
                    								
                    				loanBeanArr[0]=loanBean;
                    				request.setAttribute("actionForm", loanBeanArr);
                    				
                    			}
							}
						}
					}
					
					if(handloanForm.getLoanReceiveId()!=0)
        			{
        				loanReqArr = loanReqDao.findByDynamicSelect("select * from LOAN_REQUEST where LOAN_ID = ? AND ASSIGN_To= ? ", new Object[]{handloanForm.getLoanReceiveId(), login.getUserId()});
        				for(LoanRequest loanReq : loanReqArr)
        				{
        					LoanRequest lnHandlReq = loanReq;
            				loanDto = loanDetlDao.findByPrimaryKey(lnHandlReq.getLoanId());
            				
            				loanBean=DtoToBeanConverter.DtoTOBeanConverter(lnHandlReq,null);
                			loanBean.setGrossSalary(loanDto.getGrossSalary());			
                			loanBean.setNetSalary(loanDto.getNetSalary());
                			loanBean.setEligibilityAmt(loanDto.getEligibilityAmount());
                			loanBean.setEmiEligibility(loanDto.getEmiEligibility());
                			loanBean.setEmiPeriod(loanDto.getEmiPeriod());
                			loanBean.setLoanReceiveId(loanDto.getId());
                			loanBean.setPurpose(loanDto.getPurpose());
                			userDto = usersDao.findByPrimaryKey(lnHandlReq.getLoanUserId());
                			profileInfoDto=profileInfoDao.findByPrimaryKey(userDto.getProfileId());
                			if(lnHandlReq!=null)
                			{
                				EmpSerReqMap empSerMap = eMapDao.findByPrimaryKey(lnHandlReq.getEsrMapId());
                				if(empSerMap!=null)
                					empSerReq = empSerMap.getReqId();
                			}
                			loanBean.setLoanReqId(empSerReq);
                			loanBean.setRequestedBy(profileInfoDto.getFirstName());
                							
                			loanBeanArr[0]=loanBean;
                			request.setAttribute("actionForm", loanBeanArr);
            			}
        			}
					break;	
        	}	
				
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	/*public final ProcessChain getProcessChainForFeatures(int empSerReqId)
	{
		logger.info("<<<<<<<>>>>>>>>>INSIDE  getProcessChainForFeatures<<<<<<<<>>>>>>>>");
		ProcessChainDao processDao = ProcessChainDaoFactory.create();
		ProcessChain[] pChain = null;
		try
		{
			Object[] sqlParams = {empSerReqId};
			processDao.setMaxRows(1);
			pChain = processDao.findByDynamicWhere(" ID = (SELECT PROCESSCHAIN_ID FROM EMP_SER_REQ_MAP WHERE ID = ?)", sqlParams);
		}
		catch (ProcessChainDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pChain.length == 0 ? null : pChain[0];

	}*/
	private double[] grossSalaryCalc(SalaryDetails[] salDto)
	{
		IntegerPerm intPerm = new IntegerPerm();
		logger.info("<<<<<<<>>>>>>>>> INSIDE  grossSalaryCalc <<<<<<<<>>>>>>>>");
		double sal[] = new double[2];
		float gross=0;
		float pf=0;
		float basicSal, concession, HRA, splAllowance;
		for(SalaryDetails salary : salDto)
		{
			//loan salary fields commented need to change later
			//gross=0;
			if(salary.getFieldLabel().equals("Basic Salary"))
			{
				//basicSal = intPerm.decipher(salary.getMonthly());
				
				//gross = gross+basicSal;
			}
			else if(salary.getFieldLabel().equals("Conveyance"))
			{
				//concession = intPerm.decipher(salary.getMonthly());
				//gross = gross+concession;
			}
			else if(salary.getFieldLabel().equals("HRA"))
			{
				//HRA = intPerm.decipher(salary.getMonthly());
				//gross = gross+HRA;
			}
			else if(salary.getFieldLabel().equals("Special Allowances"))
			{
				//splAllowance = intPerm.decipher(salary.getMonthly());
				//gross = gross+splAllowance;
			}
			else if(salary.getFieldLabel().equals("Profident Fund"))
			{
				//pf = intPerm.decipher(salary.getMonthly());
			}
		}
		sal[0] = gross;
		sal[1] = gross - (pf+200);
		return sal;
	}

	private boolean isApprover(int uId)
	{
		logger.info("<<<<<<<>>>>>>>>> INSIDE  isApprover <<<<<<<<>>>>>>>>");
		boolean result = false;
		try
		{
			//String sql = "ASSIGN_TO = ? AND ACTION_TAKEN_BY IS NULL";
			String sql = "ASSIGN_TO = ?";
			Object[] sqlParams = {uId};
			LoanRequest[] loanReqs = loanReqDao.findByDynamicWhere(sql, sqlParams);
			if(loanReqs.length > 0)
			{
				result = true;
			}	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	private boolean isHandler(int uId)
	{
		logger.info("<<<<<<<>>>>>>>>> INSIDE  isHandler <<<<<<<<>>>>>>>>");
		boolean result = false;
		try
		{
			String sql = "ASSIGN_TO = ? AND SEQUENCE =0 ";
			Object[] sqlParams = {uId};
			LoanRequest[] loanReqs = loanReqDao.findByDynamicWhere(sql, sqlParams);
			if(loanReqs.length > 0)
			{
				result = true;
			}	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		logger.info("<<<<<<<>>>>>>>>> INSIDE  SAVE Method <<<<<<<<>>>>>>>>");
		ActionResult result =new ActionResult();
		boolean flag =true;
		try
		{
			LoanDetails loanDetailDto=(LoanDetails)form;
			Login loginDto=Login.getLogin(request);
			int userId=loginDto.getUserId();
			
			switch(ActionMethods.SaveTypes.getValue(form.getsType()))
			{
				case SAVE:
					logger.info("<<<<<<<>>>>>>>>> INSIDE  SAVE case: saving new loan <<<<<<<<>>>>>>>>");
					Users loanUsr = usersDao.findByPrimaryKey(loginDto.getUserId());
					ProfileInfo userInfo = profileInfoDao.findByPrimaryKey(loanUsr.getProfileId());
					loanDetailDto.setRequesterId(loginDto.getUserId());
					loanDetailDto.setStatusId(Status.getStatusId(Status.NOT_SUBMITTED));
					eReqMap=createESRM(request);
					if(loanDetailDto.getId()==0)
					{
						eReqMap.setReqId("Not Requested");
						
					}
					eMapPk=eMapDao.insert(eReqMap);
					eReqMap.setId(eMapPk.getId());
					
					loanDetailsPk=loanDetlDao.insert(loanDetailDto);
					flag=false;			
					
					break;
				case SUBMIT:
					logger.info("<<<<<<<>>>>>>>>> INSIDE  SUBMIT case: SUBMIT new loan <<<<<<<<>>>>>>>>");
					ProcessEvaluator evaluator = new ProcessEvaluator();
					setProChain(request);
					String ERR_CODE = evaluator.validateProcessChain(proChain, loginDto.getUserId(), false, true);
					
					try{
						Integer.parseInt(ERR_CODE);//200   O.K
					}catch(NumberFormatException ex){
						logger.error("SERVICE REQUEST PRE-PREPROCESS FAILED FOR REQUESTER_ID="+loginDto.getUserId()+" SERVICE_REQUEST IS SODEXO ERR_CODE="+ERR_CODE);
						result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.servicerequest",ERR_CODE));
						result.setForwardName("success");
						return result;
					}
					//Getting ProcessChain Object from RequestUtility
					
					
					int appLevel=1;
					if(validateApproChain(appLevel, userId))
					{
						if(loanDetailDto.getLoanReceiveId()!=0)
						{
								LoanDetails[] arrLoanDto=loanDetlDao.findWhereIdEquals(loanDetailDto.getLoanReceiveId());
								LoanDetails loanDto = arrLoanDto[0];
								loanDto.setLoanTypeId(loanDetailDto.getLoanTypeId());
								loanDto.setApprovedAmount(loanDetailDto.getApprovedAmount());
								loanDto.setEmiPeriod(loanDetailDto.getEmiPeriod());
								loanDto.setPurpose(loanDetailDto.getPurpose());
								
								loanDto.setStatusId(Status.getStatusId(Status.SUBMITTED));
								loanDto.setApplyDate(new Date());
								logger.info(loanDto.getId());
								loanDetlDao.update(new LoanDetailsPk(loanDto.getId()), loanDto);
								
								eReqMap=createESRM(request);
								eReqMap.setReqId(eReqMap.getReqId() + loanDto.getId());
								eMapPk=eMapDao.insert(eReqMap);
								eReqMap.setId(eMapPk.getId());
								
								//Approving the Loan and Notifying the Approver and Loan requester  
								approveAndNotify(form, request, loanDto);
								flag=false;
							}
					}
					else
					{
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.submit"));
						 result.setForwardName("success");
						 return result;
					}
				break;
					
				
				case SAVEANDSUBMIT:
					logger.info("<<<<<<<>>>>>>>>> INSIDE  SAVEANDSUBMIT case: SAVE and SUBMIT new loan <<<<<<<<>>>>>>>>");
					if(true)
					{
						LoanDetails[] loanDetail=loanDetlDao.findWhereApplyDateEquals(new Date());
						loanDetailDto.setRequesterId(userId);
						 if(loanDetail.length>0)
						 {
							 result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("duplicate.loan.date"));
							 result.setForwardName("success");
							 return result;
						 }
					}
				
					//Getting ProcessChain Object from RequestUtility
					setProChain(request);
					int appLvl=1;
					if(validateApproChain(appLvl, userId))
					{
						loanDetailDto.setStatusId(Status.getStatusId(Status.SUBMITTED));
						loanDetailDto.setApplyDate(new Date());
						loanDetailsPk=loanDetlDao.insert(loanDetailDto);
						//loanDetlDao.update(new LoanDetailsPk(loanDetailDto.getId()), loanDetailDto);
						
						eReqMap=createESRM(request);
						eReqMap.setReqId(eReqMap.getReqId() + loanDetailDto.getId());
						eMapPk=eMapDao.insert(eReqMap);
						if(eMapPk!=null)
						eReqMap.setId(eMapPk.getId());
						//Approving the Loan and Notifying the Approver and Loan requester  
						approveAndNotify(form, request, loanDetailDto);
						flag=false;
					}
					else
						{
							result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.submit"));
						    result.setForwardName("success");
						    return result;
						}
					break;
			}
		}catch (LoanDetailsDaoException e) {
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.submit"));
			result.setForwardName("success");
			e.printStackTrace();
		}
		catch (EmpSerReqMapDaoException e)
		{
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.submit"));
			result.setForwardName("success");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ServiceRequestDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UsersDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ProfileInfoDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception e)
		{
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.submit"));
			result.setForwardName("success");
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(flag && eMapPk!=null){
					eMapDao.delete(eMapPk);
					loanDetlDao.delete(loanDetailsPk);
				}
			}
			catch (EmpSerReqMapDaoException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (LoanDetailsDaoException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public EmpSerReqMap createESRM(HttpServletRequest request) throws EmpSerReqMapDaoException, ServiceRequestDaoException
	{
		logger.info("<<<<<<<>>>>>>>>> INSIDE  createESRM Method<<<<<<<<>>>>>>>>");
		Login login = Login.getLogin(request);
		//ServiceRequestDao sReqDao=ServiceRequestDaoFactory.create();
		try
		{
			UserLogin userLogin = login.getUserLogin();
			Regions regions = rDao.findByPrimaryKey(userLogin.getRegionId());
			Object[] objs = userLogin.getRoles().toArray();
			Roles roles = (Roles) objs[0];
			module = moduleDao.findWhereNameEquals("LOAN")[0];
			reqType = reqDao.findByFeatureId(module.getId());
			ModulePermission mPermission = mDao.findByRoleAndModule(roles.getRoleId(), module.getId());
			
			eReqMap.setSubDate(new Date());
			eReqMap.setReqTypeId(4);
			eReqMap.setRegionId(userLogin.getRegionId());
			eReqMap.setRequestorId(login.getUserId());
			eReqMap.setProcessChainId(mPermission.getProcChainId());
			
			eReqMap.setReqId(regions.getRefAbbreviation() + "-"+reqType.getAbbrevation()+"-");
		}
		catch (ModulePermissionDaoException e)
		{
			e.printStackTrace();
		}
		catch (RegionsDaoException e)
		{
			e.printStackTrace();
		}
		catch (ModulesDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (RequestTypeDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return eReqMap;
	}
	
	
	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		Levels levelDto = null;
		ActionResult result = new ActionResult();
		LoanRequestPk loanReqPk=null;
		boolean flagLoan = true;
		boolean flagReq = true;
		boolean flagNotify = true;
		boolean flagInb = true;
		try
		{
			Login login = Login.getLogin(request);
			LoanDetails loanDtlDto = (LoanDetails)form;
			LoanRequest[] loanReqArr = null;
			ProfileInfo profileInfoDto = null;
			int lnRcvId = loanDtlDto.getLoanReceiveId();
			
			String actionType = loanDtlDto.getActionType();
			 
			if(actionType!=null && actionType.equalsIgnoreCase("onhold"))
			{
				actionType="on hold";
			}
			LoanDetails  temploanDto = loanDetlDao.findByPrimaryKey(lnRcvId);
			setProChain(request);
			
			Users userDto = usersDao.findByPrimaryKey(login.getUserId());
			profileInfoDto = profileInfoDao.findByPrimaryKey(userDto.getProfileId());
			
			Integer[] notifierIds=null ; 
			Integer[] handlers = null ;
			String templateName = null;
			String mailSubject = null;
			String status=null;
			String messageBody=null;
			double avlblAmt =  temploanDto.getEligibilityAmount();
			double apldAmt = 	temploanDto.getApprovedAmount();
			int emiDuration = temploanDto.getEmiPeriod();
			
			String approver = profileInfoDto.getFirstName();
			
			String lonTyp=null;
			
			String reqId = null;
			Users uDto = null;
			profileInfoDto = null;
			String requester =null;		
			LoanRequest loanReq = null;
			/*LoanRequest loanReq= loanReqDao.findByDynamicSelect("SELECT * FROM LOAN_REQUEST WHERE LOAN_ID = ? AND ASSIGN_TO = ? GROUP BY SEQUENCE DESC", 
														new Object[]{ temploanDto.getId(), login.getUserId() })[0];*/ 
			if(!form.getuType().equalsIgnoreCase("EDIT"))
			{
    			loanReq= loanReqDao.findByDynamicSelect("SELECT * FROM LOAN_REQUEST WHERE LOAN_ID = ?  ORDER BY CREATED_DATETIME DESC ", 
    					new Object[]{ temploanDto.getId() })[0];
    			loanReqArr = loanReqDao.findByDynamicSelect("SELECT * FROM LOAN_REQUEST WHERE ESR_MAP_ID = ? AND SEQUENCE = ?  AND  STATUS_ID=?", new Object[]
    		                                                   { loanReq.getEsrMapId(), loanReq.getSequence(), loanReq.getStatusId() });
    			if ((loanReqArr.length <= 0))
    			{
    				loanReqArr = loanReqDao.findByDynamicSelect("SELECT * FROM TIMESHEET_REQ WHERE ESRQM_ID = ? AND SEQUENCE = ? ",
    													new Object[]{ loanReq.getEsrMapId(), loanReq.getSequence() });
    			}
    			eReqMap = eMapDao.findByPrimaryKey(loanReq.getEsrMapId());
    			
    			notifierIds = pEvaluator.notifiers(proChain.getNotification(), loanReq.getLoanUserId());
    			handlers = new ProcessEvaluator().handlers(proChain.getHandler(), pEvaluator.findLastAppLevel(eReqMap));
    			lonTyp=Loan.getLoanType(loanReq.getLoanTypeId());
    			
    			reqId = eReqMap.getReqId();
    			uDto = usersDao.findByPrimaryKey(temploanDto.getRequesterId());
    			profileInfoDto = profileInfoDao.findByPrimaryKey(uDto.getProfileId());
    			requester = profileInfoDto.getFirstName();
			}
			switch (ActionMethods.UpdateTypes.getValue(form.getuType()))
			{
				case ACCEPTREJECT:
					
					if(temploanDto.getStatusId()==Status.getStatusId(Status.SUBMITTED)||temploanDto.getStatusId()==Status.getStatusId(Status.CANCEL)||temploanDto.getStatusId()==Status.getStatusId(Status.ON_HOLD))
					{
						int id=0;
						if(actionType.equalsIgnoreCase((Status.ACCEPTED))||actionType.equalsIgnoreCase((Status.REJECTED))||actionType.equalsIgnoreCase((Status.ON_HOLD)))
						{
							LoanRequest loanNotificationDto=null;
							for(int i=0;i<loanReqArr.length;i++)
							{
								Users approverDto = usersDao.findByPrimaryKey(loanReqArr[i].getAssignTo());
    							ProfileInfo nxtAppProfileInfo = profileInfoDao.findByPrimaryKey(approverDto.getProfileId());
            					String employeeName = nxtAppProfileInfo.getFirstName();
            					String submitDate = loanReqArr[i].getCreatedDatetime().toString();
            					
    							if(actionType.equalsIgnoreCase((Status.REJECTED)))
    							{
    								templateName = MailSettings.LOAN_TEMPLATE;
    								mailSubject = "Loan Request is Rejected ";
    								status = Status.REJECTED;
    								messageBody = "Loan Request "+ reqId +" is Rejected by approver "+ approver +" on "+ submitDate;
    							}
    							else if(actionType.equalsIgnoreCase((Status.ACCEPTED)))
    							{
    								templateName = MailSettings.LOAN_TEMPLATE; 
    								mailSubject = "Loan Request is Accepted ";
    								status = Status.ACCEPTED;
    								messageBody = "Loan Request "+ reqId +" is Approved by approver "+ approver +" on "+ submitDate;
    							}
    							else if(actionType.equalsIgnoreCase((Status.ON_HOLD)))
    							{
    								templateName = MailSettings.LOAN_TEMPLATE; 
    								mailSubject = "Loan Request is On Hold ";
    								status = Status.ON_HOLD;
    								messageBody = "Loan Request "+ reqId + " is On Hold by approver "+ approver +" on "+ submitDate;
    							}
    							if(loanReqArr[i].getStatusId()==Status.getStatusId(Status.CANCEL))
    							{
    								userDto = usersDao.findByPrimaryKey(loanReqArr[i].getLoanUserId());
    								profileInfoDto = profileInfoDao.findByPrimaryKey(userDto.getProfileId());
    								templateName = MailSettings.LOAN_TEMPLATE;
    								mailSubject = "Loan Request Cancelled";
    								status = Status.CANCELLED;
    								messageBody = "Loan Cancel Request "+ reqId +" is Approved by approver "+ approver +" on "+ submitDate;
    							}
    							
    							/**
    							 * sending mail to Approver in list
    							 */
            					portalMail = sendEmailToRecipient(loanReqArr[i].getAssignTo(),templateName+"@"+messageBody,employeeName,avlblAmt,apldAmt,
        						emiDuration,lonTyp,reqId,mailSubject,approver,submitDate,loanReqArr[i].getComments(),loanReqArr[i].getLoanUserId());
        						
            					/**
            					 * updating DB with approver Action
            					 */
        						if(id==0&&loanReqArr[i].getAssignTo()==login.getUserId())
        						{
        							loanReqArr[i].setComments(loanDtlDto.getComment());
        							loanNotificationDto=updateActionTaken(loanReqArr[i],loanReqArr[i].getAssignTo(),actionType);
        							id=1;
        						}
        							
        						String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(templateName), portalMail);	
        						inbox = inboxModel.sendToDockingStation(loanReqArr[i].getEsrMapId(), loanReqArr[i].getId(), mailSubject+"@"+status,bodyText);
        							
        						/**
        						 * Notifying notifier by populating inbox
        						 */
        						for(int j=0;j<notifierIds.length; j++)
        						{
        							Users notifiedUsr = usersDao.findByPrimaryKey(notifierIds[j]);
        							ProfileInfo notifierInfo = profileInfoDao.findByPrimaryKey(notifiedUsr.getProfileId());
        							/*sendEmailToRecipient(notifierIds[j],templateName,notifierInfo.getFirstName(),avlblAmt,apldAmt,
        								emiDuration,lonTyp,reqId,mailSubject,approver,null,null,null);*/
        								//inbox = inboxModel.sendToDockingStation(loanReqArr[i].getEsrMapId(), loanReqArr[i].getId(), mailSubject+"-"+status,bodyText);
        								//inboxModel.notify(loanReqArr[i].getEsrMapId(), inbox);
        								
        							messageBody ="Loan request is "+status+" by approver " +approver+" on "+new Date().toString();
        							
        							portalMail.setMessageBody(messageBody);
        							
        							bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.LOAN_TEMPLATE), portalMail);
        							inbox.setMessageBody(bodyText);
        							inboxModel.notify(loanReqArr[i].getEsrMapId(), inbox);
        						}	
							}
							
							int appLevel = pEvaluator.findLastAppLevel(eReqMap) + 1;
							Integer[] approverGroupIds = new ProcessEvaluator().approvers(proChain.getApprovalChain(), appLevel, loanReq.getLoanUserId());
							String comments =  loanDtlDto.getComment();
							Users approverDto = usersDao.findByPrimaryKey(login.getUserId().intValue());
							ProfileInfo profileOfApprover = profileInfoDao.findByPrimaryKey(approverDto.getProfileId());
							String submitDate = new Date().toString();
    						int empId = loanReq.getLoanUserId();
    						String bodyText;
    						if(approverGroupIds.length < 1)
    						{
    							/*if(!(actionType.equalsIgnoreCase(Status.REJECTED)) && !(actionType.equalsIgnoreCase(Status.ON_HOLD)) && !(loanReq.getStatusId()==Status.getStatusId(Status.CANCEL)))
   								{*/
    								
    								
    								if(actionType.equalsIgnoreCase((Status.REJECTED)))
        							{
        									temploanDto.setStatusId(Status.getStatusId(Status.REJECTED));
        									mailSubject = "Your Loan Request is Rejected";
        	    							templateName = MailSettings.LOAN_TEMPLATE; 
        									messageBody = "Loan Request "+ reqId +" is Rejected by approver "+ approver +" on "+ submitDate;
        							}							
        							else if(actionType.equalsIgnoreCase((Status.ACCEPTED)))
        							{
        								temploanDto.setStatusId(Status.getStatusId(Status.ACCEPTED));
        								mailSubject = "Your Loan Request is Accepted";
        	    						templateName = MailSettings.LOAN_TEMPLATE; 
        								 messageBody = "Loan Request "+ reqId +" is Approved by approver "+ approver +" on "+ submitDate;
        							}
        							else if(actionType.equalsIgnoreCase((Status.ON_HOLD)))
        							{
        								temploanDto.setStatusId(Status.getStatusId(Status.ON_HOLD));
        								mailSubject = "Your Loan Request is On HOLD";
        	    						templateName = MailSettings.LOAN_TEMPLATE; 
        								messageBody = "Loan Request "+ reqId + " is On Hold by approver "+ approver +" on "+ submitDate;
        							}
        							loanDetlDao.update(new LoanDetailsPk(temploanDto.getId()), temploanDto);
        							flagLoan = false;
        							
        							portalMail = sendEmailToRecipient(uDto.getId(),templateName+"@"+messageBody,requester,avlblAmt,apldAmt,
        														emiDuration,lonTyp,reqId,mailSubject,approver,submitDate,comments,empId);
        							bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(templateName), portalMail);
        							// --|Sending notification to the requestor
       								inboxModel.populateInboxForLoanRequestor(loanReq.getEsrMapId(), loanReq.getSequence(), uDto.getId(),mailSubject, bodyText);
    						}
       						if(handlers.length >= 1)
    						{
       							if(!(actionType.equalsIgnoreCase(Status.REJECTED)) && !(actionType.equalsIgnoreCase(Status.ON_HOLD)) && !(loanReq.getStatusId()==Status.getStatusId(Status.CANCEL)))
    							{       								
       								for (int i = 0; i < handlers.length; i++)
       	            				{
       	            					messageBody = "New Loan request is received from " +profileInfoDto.getFirstName()+" on "+submitDate;						
       	                				if(actionType.equalsIgnoreCase((Status.ACCEPTED)))
       	                				{
       	                					temploanDto.setStatusId(Status.getStatusId(Status.ACCEPTED));
       	                					mailSubject = "New Loan Request is Submitted";
       	                	    			templateName = MailSettings.LOAN_TEMPLATE;
       	                				}
       	                				
       	                				loanDetlDao.update(new LoanDetailsPk(temploanDto.getId()), temploanDto);
       	                				comments =  loanDtlDto.getComment();
       	                				
       	                				approverDto = usersDao.findByPrimaryKey(handlers[i]);
       	                				profileOfApprover = profileInfoDao.findByPrimaryKey(approverDto.getProfileId());
       	                			
       	                				approver = profileOfApprover.getFirstName();
       	                				lonTyp=Loan.getLoanType(loanReq.getLoanTypeId());
       	                				userDto = usersDao.findByPrimaryKey(temploanDto.getRequesterId());
       	                        		ProfileInfo requesterInfo = profileInfoDao.findByPrimaryKey(userDto.getProfileId());
       	                        		                        					
       	                        		requester = requesterInfo.getFirstName();
       	                				avlblAmt =  temploanDto.getEligibilityAmount();
       	                    			apldAmt = 	temploanDto.getApprovedAmount();
       	                    			emiDuration = temploanDto.getEmiPeriod();
       	                    			submitDate = temploanDto.getApplyDate().toString();
       	                    			empId = loanReq.getLoanUserId();
       	                				portalMail = sendEmailToRecipient(userDto.getId(),templateName+"@"+messageBody,approver,avlblAmt,apldAmt,
       	                    											emiDuration,lonTyp,reqId,mailSubject,requester,submitDate,comments,empId);
       	                				//String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(templateName), portalMail);
       	                				
       	                    			// --|Sending notification to the requestor
       	                				LoanRequest loanReqDto = new LoanRequest();
           	            				loanReqDto.setLoanUserId(temploanDto.getRequesterId());
           	            				loanReqDto.setAssignTo(handlers[i]);
           	            				loanReqDto.setStatusId(Status.getStatusId(Status.SUBMITTED));
           	            				loanReqDto.setLoanId(temploanDto.getId());
           	            				loanReqDto.setEsrMapId(loanReq.getEsrMapId());
           	            				loanReqDto.setRequestedLoanAmt(temploanDto.getApprovedAmount());
           	            				loanReqDto.setEmiPeriod(temploanDto.getEmiPeriod());
           	            				loanReqDto.setComments(temploanDto.getComment());
           	            				loanReqDto.setLoanTypeId(temploanDto.getLoanTypeId());
           	            				loanReqDto.setSequence(0);
           	            								
           	            				bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.LOAN_TEMPLATE), portalMail);
           	            						loanReqDto.setEmailData(bodyText);
           	            							
           	            				loanReqPk = 	loanReqDao.insert(loanReqDto);
           	            				flagReq = false;				
           	            				Inbox inbox = inboxModel.sendToDockingStation(loanReqDto.getEsrMapId(), loanReqPk.getId(), mailSubject+"@"+Status.SUBMITTED,bodyText);
           	            				flagInb = false;
           	            				//inboxModel.notify(loanNotificationDto.getEsrMapId(), inbox);
           	            				for(int j=0;j<notifierIds.length; j++)
            							{
            								Users notifiedUsr = usersDao.findByPrimaryKey(notifierIds[j]);
            								ProfileInfo notifierInfo = profileInfoDao.findByPrimaryKey(notifiedUsr.getProfileId());
            								/*sendEmailToRecipient(notifierIds[j],templateName,notifierInfo.getFirstName(),avlblAmt,apldAmt,
            									emiDuration,lonTyp,reqId,mailSubject,approver,null,null,null);*/
            									//inbox = inboxModel.sendToDockingStation(loanReq.getEsrMapId(), loanReq.getId(), mailSubject+"-"+Status.ACCEPTED,bodyText);
            							
            								
            							
            								messageBody ="New Loan request is Assigned to " +approver+" on "+submitDate;
            								/*sendEmailToRecipient(notifierIds[i],templateName,notifierInfo.getFirstName(),avlblAmt,apldAmt,
            								emiDuration,LonTyp,reqId,mailSubject,requester,submitDate,null, empId);*/
            								portalMail.setEmployeeName(notifierInfo.getFirstName());
            								portalMail.setMessageBody(messageBody);
            								bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.LOAN_TEMPLATE), portalMail);
            								inbox.setMessageBody(bodyText);
            								inboxModel.notify(loanReqDto.getEsrMapId(), inbox);
            								flagNotify = false;
            							
            							}
           	            			}
       								
        						}
    							else
   								{
   									if(actionType.equalsIgnoreCase(Status.ON_HOLD))
   										temploanDto.setStatusId(Status.getStatusId(Status.ON_HOLD));
   									if(actionType.equalsIgnoreCase(Status.REJECTED))
   	   									temploanDto.setStatusId(Status.getStatusId(Status.REJECTED));
   									loanDetlDao.update(new LoanDetailsPk(temploanDto.getId()), temploanDto);
   									flagLoan = false;
   								}
   							
   	        				}
   						else
   						{
   								if(!(actionType.equalsIgnoreCase(Status.REJECTED)) && !(actionType.equalsIgnoreCase(Status.ON_HOLD)) && !(loanReq.getStatusId()==Status.getStatusId(Status.CANCEL)))
   								{
    												
        							for (int i = 0; i < approverGroupIds.length; i++)
        							{
        								Users nxtLvlusr = 	usersDao.findByPrimaryKey(approverGroupIds[i]);						
        							 	ProfileInfo nxtUsrInfo=profileInfoDao.findByPrimaryKey(nxtLvlusr.getProfileId());
        								templateName = MailSettings.LOAN_TEMPLATE;
            							mailSubject = "New Loan Request Submitted ";
            							String employeeName = nxtUsrInfo.getFirstName();
            							
            							submitDate = temploanDto.getApplyDate().toString();
            							String comment = loanDtlDto.getComment();
            							messageBody = "New Loan request is received from " +requester+" on "+submitDate;
            							
            							status = Status.getStatus(temploanDto.getStatusId());
            							//Employee information
            							if(status.equalsIgnoreCase(Status.ON_HOLD))
            							{
            								status=Status.SUBMITTED;
            								temploanDto.setStatusId(Status.getStatusId(status));
            							}
            							empId = loanReq.getLoanUserId();
        				     			
        								portalMail = sendEmailToRecipient(approverGroupIds[i],templateName+"@"+messageBody,employeeName,avlblAmt,apldAmt,
        																						emiDuration,lonTyp,reqId,mailSubject,requester,submitDate,comment,empId);
        								LoanRequest loanReqDto = new LoanRequest();
        								loanReqDto.setLoanUserId(temploanDto.getRequesterId());
        								loanReqDto.setAssignTo(approverGroupIds[i]);
        								loanReqDto.setStatusId(Status.getStatusId(status));
        								loanReqDto.setLoanId(temploanDto.getId());
        								loanReqDto.setEsrMapId(loanReq.getEsrMapId());
        								loanReqDto.setRequestedLoanAmt(temploanDto.getApprovedAmount());
        								loanReqDto.setEmiPeriod(temploanDto.getEmiPeriod());
        								loanReqDto.setComments(temploanDto.getComment());
        								loanReqDto.setLoanTypeId(temploanDto.getLoanTypeId());
        								loanReqDto.setSequence(appLevel);
        								bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.LOAN_TEMPLATE),
        																	portalMail);
        								loanReqDto.setEmailData(bodyText);
        								loanReqPk = 	loanReqDao.insert(loanReqDto);
        								flagReq = false;    								
        								//Sending for inbox notification to the approver
        								Inbox inbox = inboxModel.sendToDockingStation(loanReqDto.getEsrMapId(), loanReqPk.getId(), mailSubject+"@"+status,bodyText);
        								//inboxModel.notify(loanReqDto.getEsrMapId(), inbox);
       								}
        							
   								}		
   							
   								else
   								{
   									if(actionType.equalsIgnoreCase(Status.ON_HOLD))
   										temploanDto.setStatusId(Status.getStatusId(Status.ON_HOLD));
   									else if(actionType.equalsIgnoreCase(Status.REJECTED))
   	   									temploanDto.setStatusId(Status.getStatusId(Status.REJECTED));
   									else if(loanReq.getStatusId()==Status.getStatusId(Status.CANCEL))
   	   									temploanDto.setStatusId(Status.getStatusId(Status.CANCELLED));
   									flagLoan = false;
   									//loanDetlDao.update(new LoanDetailsPk(temploanDto.getId()), temploanDto);
   								}
   								temploanDto.setStatusId(Status.getStatusId(status));
    					}
    					loanDetlDao.update(new LoanDetailsPk(temploanDto.getId()), temploanDto);
    					flagLoan = false;
					}
				}
					else
					{
						if(temploanDto.getStatusId()==Status.getStatusId(Status.ACCEPTED))
						{
							result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("accepted.loan.request"));
							result.setForwardName("success");
							return result;
						}
					}
			
				break;
				case EDIT:
					
					LoanDetails updatedloanDto = loanDetlDao.findByPrimaryKey(loanDtlDto.getLoanReceiveId());
					double amt=0;
					amt += updatedloanDto.getApprovedAmount();
					
					updatedloanDto.setLoanTypeId(loanDtlDto.getLoanTypeId());
					updatedloanDto.setApprovedAmount(loanDtlDto.getApprovedAmount());
					updatedloanDto.setEmiPeriod(loanDtlDto.getEmiPeriod());
					updatedloanDto.setPurpose(loanDtlDto.getPurpose());
					levelDto = levelDao.findByPrimaryKey(userDto.getLevelId()); 
					int loanType = loanDtlDto.getLoanTypeId();
					LoanEligibilityCriteria[] lec = loanECDao.findByDynamicSelect("select * from LOAN_ELIGIBILITY_CRITERIA where TYPE_ID =?", new Object[]{loanType});
					for(LoanEligibilityCriteria tmpLec : lec)
					{
						if(tmpLec.getLabel().equalsIgnoreCase(levelDto.getLabel()))
						{
							updatedloanDto.setEligibilityAmount(tmpLec.getMaxAmountLimit());
						}
					}
					
					//updatedloanDto.setEligibilityAmount(loanDtlDto.getEligibilityAmount());
					loanDetlDao.update(new LoanDetailsPk(updatedloanDto.getId()), updatedloanDto);
				break;
				case ASSIGN:
					LoanRequest[] assignedLoanReqArr= loanReqDao.findByDynamicSelect("SELECT * FROM LOAN_REQUEST WHERE LOAN_ID = ? AND SEQUENCE = 0 order by ID desc", new Object[]{ temploanDto.getId()}); 
					LoanRequest assignLoanReq =null;
					LoanDetails assignedLoan = loanDetlDao.findByPrimaryKey(loanDtlDto.getLoanReceiveId());
					String requesterMessagebody = null;
					if(temploanDto.getStatusId()==Status.getStatusId(Status.ACCEPTED)||temploanDto.getStatusId()==Status.getStatusId(Status.COMPLETE))
					{
						for(LoanRequest tmp : assignedLoanReqArr)
						{
							assignLoanReq = tmp;
							String bodyText=null;
							mailSubject="Loan Request ";
							templateName = MailSettings.LOAN_TEMPLATE;
							String comments =  loanDtlDto.getComment();
        					String submitDate = temploanDto.getApplyDate().toString();
							int empId = loanReq.getLoanUserId();
        				 						
        					if((loanDtlDto.getStatus().equalsIgnoreCase(Status.COMPLETE)))// && assignLoanReq.getAssignTo()==login.getUserId())
        					{
        						assignedLoan.setStatusId(Status.getStatusId(Status.COMPLETED));
        						assignLoanReq.setStatusId(Status.getStatusId(loanDtlDto.getStatus()));
        						assignLoanReq.setActionTakenBy(login.getUserId());
        						messageBody = "Loan Request Process is Completed by "+approver+" and notified to "+requester+" !!!";
        						requesterMessagebody = "Your Loan Request Process is Completed by "+approver+" !!!";
        						
        						
        					}
        					else if((loanDtlDto.getStatus().equalsIgnoreCase(Status.INPROGRESS)))
        					{
        						if(assignLoanReq.getAssignTo()==login.getUserId())
        						{
        							if(assignLoanReq.getAssignTo()==loanDtlDto.getAssignTo())
        							{
        								assignLoanReq.setStatusId(Status.getStatusId(Status.ASSIGNED));
        								messageBody = "You have Assigned a Loan Request to yourself to complete";
        							}
        							else
        							{
        								assignLoanReq.setStatusId(Status.getStatusId(Status.INPROGRESS));
        								messageBody = "Loan Request Process is In-Progress by "+approver+"!!!";
        							}
        						}
        						else
        						{
        							assignLoanReq.setStatusId(Status.getStatusId(Status.ASSIGNED));
        							messageBody = "You are Assigned to Complete a Loan Request process by "+approver+"!!!";
        						}
        						//assignedLoan.setStatusId(Status.getStatusId(Status.INPROGRESS));
        						assignLoanReq.setActionTakenBy(login.getUserId());
        						requesterMessagebody = "Loan Request Process is In-Progress by "+approver+"!!!";
        						assignedLoan.setStatusId(Status.getStatusId(Status.INPROGRESS));
        					}
        					
        					loanDetlDao.update(new LoanDetailsPk(assignedLoan.getId()), assignedLoan);
	       					flagLoan = false;
        					assignLoanReq.setComments(loanDtlDto.getComment());
        					loanReqDao.update( new LoanRequestPk(assignLoanReq.getId()), assignLoanReq);
        					flagReq = false;
        					portalMail = sendEmailToRecipient(assignLoanReq.getAssignTo(),templateName+"@"+messageBody,approver,avlblAmt,apldAmt,
									emiDuration,lonTyp,reqId,mailSubject,requester,submitDate,comments,empId);
        					
    						bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(templateName), portalMail);
    						
    						inbox = inboxModel.sendToDockingStation(assignLoanReq.getEsrMapId(), assignLoanReq.getId(), "New Loan Request Submitted"+"@"+Status.getStatus(assignedLoan.getStatusId()),bodyText);
    						
    						/**
    						 * |Sending notification to the requestor
    						 */
    						portalMail.setMessageBody(requesterMessagebody);
    						bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.LOAN_TEMPLATE), portalMail);
    						inbox.setMessageBody(bodyText);
    						inboxModel.populateInboxForLoanRequestor(assignLoanReq.getEsrMapId(), assignLoanReq.getSequence(),assignLoanReq.getLoanUserId(),mailSubject+"@"+Status.getStatus(assignedLoan.getStatusId()), bodyText);
    						flagInb = false;
    						for(int j=0;j<notifierIds.length; j++)
        					{
        						Users notifiedUsr = usersDao.findByPrimaryKey(notifierIds[j]);
        						ProfileInfo notifierInfo = profileInfoDao.findByPrimaryKey(notifiedUsr.getProfileId());
        						/*sendEmailToRecipient(notifierIds[j],templateName+"-"+messageBody,notifierInfo.getFirstName(),avlblAmt,apldAmt,
            								emiDuration,lonTyp,reqId,mailSubject,requester,null,null,null);
        							inbox = inboxModel.sendToDockingStation(assignLoanReq.getEsrMapId(), assignLoanReq.getId(), mailSubject+"-"+status,bodyText);*/
        						
        						messageBody ="Loan request is"+ loanDtlDto.getStatus()+" handled by " +approver+" on this "+submitDate;
        						portalMail.setEmployeeName(notifierInfo.getFirstName());
        						portalMail.setMessageBody(messageBody);
        						bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.LOAN_TEMPLATE), portalMail);
        						inbox.setMessageBody(bodyText);
        						inboxModel.notify(inbox.getEsrMapId(), inbox);
        						flagInb = false;
        						flagNotify = false;
        					}
        					
        					
        				}						
				}
				else
				{
					if(temploanDto.getStatusId()==Status.getStatusId(Status.CANCEL))
					{
						result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("cancel.loan.request"));
						result.setForwardName("success");
						return result;
					}
				}
				break;	
				case CANCELREQUEST:
					LoanRequest[] cancelLoanReqArr =null;
					String bodyString;
					int appLevel = pEvaluator.findLastAppLevel(eReqMap);
					cancelLoanReqArr = loanReqDao.findByDynamicSelect("SELECT * FROM LOAN_REQUEST WHERE LOAN_ID = ? AND SEQUENCE = "+appLevel, new Object[]{ temploanDto.getId() });
													
					LoanDetails cancelLoan = loanDetlDao.findByPrimaryKey(loanDtlDto.getLoanReceiveId());
					
					
					
					portalMail = sendEmailToRecipient(uDto.getId(),MailSettings.LOAN_TEMPLATE+"@"+"cancel Request for Loan Request "+reqId+" submitted successfully" ,
							requester,avlblAmt,apldAmt,emiDuration,lonTyp,reqId,"subject",requester,cancelLoan.getApplyDate().toString(),loanDtlDto.getComment(),uDto.getId());
					
					for(LoanRequest cancelReq : cancelLoanReqArr)
    				{	
    					messageBody = "Cancel Request for Loan "+eReqMap.getReqId()+" is raised by requester "+ profileInfoDto.getFirstName()+" !!!";
    					Users approvUsr = usersDao.findByPrimaryKey(cancelReq.getAssignTo());
						ProfileInfo approvInfo = profileInfoDao.findByPrimaryKey(approvUsr.getProfileId());
    					portalMail.setEmployeeName(approvInfo.getFirstName());
    					portalMail.setMessageBody(messageBody);
    					bodyString = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.LOAN_TEMPLATE), portalMail);
    					inbox = inboxModel.sendToDockingStation(cancelReq.getEsrMapId(), cancelReq.getId(), "Loan Cancel Request Submitted"+"@"+Status.CANCEL,bodyString);
    					flagInb = false;
        				for(int j=0;j<notifierIds.length; j++)
        				{
        					Users notifiedUsr = usersDao.findByPrimaryKey(notifierIds[j]);
    						ProfileInfo notifierInfo = profileInfoDao.findByPrimaryKey(notifiedUsr.getProfileId());
    						/*sendEmailToRecipient(notifierIds[j],templateName+"-"+messageBody,notifierInfo.getFirstName(),avlblAmt,apldAmt,
        								emiDuration,lonTyp,reqId,mailSubject,requester,null,null,null);
    							inbox = inboxModel.sendToDockingStation(loanReqArr[i].getEsrMapId(), loanReqArr[i].getId(), mailSubject+"-"+status,bodyText);
    						*/
    						portalMail.setEmployeeName(notifierInfo.getFirstName());
    						portalMail.setMessageBody(messageBody);
    						bodyString = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.LOAN_TEMPLATE), portalMail);
    						inbox.setMessageBody(bodyString);
    						inboxModel.notify(inbox.getEsrMapId(), inbox);
    						flagNotify = false;
        				}
        				LoanRequest newLoanReq = new LoanRequest();
        				newLoanReq.setEsrMapId(cancelReq.getEsrMapId());
        				newLoanReq.setCreatedDatetime(cancelReq.getCreatedDatetime());
        				newLoanReq.setAssignTo(cancelReq.getAssignTo());
        				newLoanReq.setActionTakenBy(cancelReq.getActionTakenBy());
        				newLoanReq.setActionTakenDate(cancelReq.getActionTakenDate());
        				newLoanReq.setEmiPeriod(cancelReq.getEmiPeriod());
        				newLoanReq.setComments(loanDtlDto.getComment());
        				newLoanReq.setRequestedLoanAmt(cancelReq.getRequestedLoanAmt());
        				newLoanReq.setEmailData(cancelReq.getEmailData());
        				newLoanReq.setStatusId(Status.getStatusId(Status.CANCEL));
        				newLoanReq.setLoanId(cancelReq.getLoanId());
        				newLoanReq.setLoanTypeId(cancelReq.getLoanTypeId());
        				newLoanReq.setLoanUserId(cancelReq.getLoanUserId());
        				newLoanReq.setSequence(cancelReq.getSequence());
        				loanReqPk = loanReqDao.insert(newLoanReq);
        				flagReq = false;
        				cancelLoan.setStatusId(Status.getStatusId(Status.CANCEL));
    					
        				loanDetlDao.update(new LoanDetailsPk(cancelLoan.getId()), cancelLoan);
        				flagLoan = false;
        				
        				//cancelReq.setStatusId(Status.getStatusId(Status.CANCEL));
    					//cancelReq.setComments(loanDtlDto.getComment());
        				
        				//loanReqDao.update(new LoanRequestPk(cancelReq.getId()), cancelReq);
					}	
					
				break;
				case CANCELED:
					/*
					LoanRequest[] cancelLoanArr = loanReqDao.findByDynamicSelect("SELECT * FROM LOAN_REQUEST WHERE STATUS_ID  = 14 AND LOAN_ID = ?", new Object[]{ temploanDto.getId() });
					LoanDetails canceledLoan = loanDetlDao.findByPrimaryKey(loanDtlDto.getLoanReceiveId());
					canceledLoan.setStatusId(Status.getStatusId(Status.CANCELLED));
					loanDetlDao.update(new LoanDetailsPk(canceledLoan.getId()), canceledLoan);
					
					LoanRequest loanCancelReq = new LoanRequest();
					
					loanCancelReq.setEsrMapId(cancelLoanArr[0].getEsrMapId());
					loanCancelReq.setRequestedLoanAmt(cancelLoanArr[0].getRequestedLoanAmt());
					loanCancelReq.setEmiPeriod(cancelLoanArr[0].getEmiPeriod());
					loanCancelReq.setLoanId(cancelLoanArr[0].getLoanId());
					loanCancelReq.setLoanTypeId(cancelLoanArr[0].getLoanTypeId());
					loanCancelReq.setLoanUserId(cancelLoanArr[0].getLoanUserId());
					loanCancelReq.setEmailData(cancelLoanArr[0].getEmailData());
					loanCancelReq.setAssignTo(login.getUserId());
					loanCancelReq.setStatusId(Status.getStatusId(Status.CANCEL));
					loanCancelReq.setComments(loanDtlDto.getComment());
					LoanRequestPk cancelloanReqPk = loanReqDao.insert(loanCancelReq);
					
					inbox = inboxModel.sendToDockingStation(loanCancelReq.getEsrMapId(), cancelloanReqPk.getId(), "New Loan Cancel Request Submitted",Status.ASSIGNED);
					inboxModel.notify(loanCancelReq.getEsrMapId(), inbox); 
					
					String template = MailSettings.LOAN_CANCELED_NOTIFIED_TO_REQUESTER;
					String mailSub = "Loan Cancel Request Approved ";
					String cancelerName = profileInfoDto.getFirstName();
					
					
					String submitDate = temploanDto.getApplyDate().toString();
					int empId = loanReq.getLoanUserId();
				
					String comment=loanDtlDto.getComment();
					portalMail = sendEmailToRecipient(userDto.getId(),template,null,avlblAmt,apldAmt,
					emiDuration,lonTyp,reqId,mailSub,requester,submitDate,comment,empId);
					String bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(template), portalMail);
						// --|Sending notification to the requestor
						inboxModel.populateInboxForLoanRequestor(loanReq.getEsrMapId(), loanReq.getSequence(), userDto.getId(),mailSub, bodyText);
						
						// --| Sending email to the HRD
						if (profileInfoDto.getHrSpoc() > 0)
						{
							
							sendEmailToRecipient(profileInfoDto.getHrSpoc(),template,cancelerName,avlblAmt,apldAmt,
									emiDuration,lonTyp,reqId,mailSub,requester,submitDate,comment,empId);
						}
						if (profileInfoDto.getReportingMgr() > 0)
						{
							sendEmailToRecipient(profileInfoDto.getReportingMgr(),template,cancelerName,avlblAmt,apldAmt,
							emiDuration,lonTyp,reqId,mailSub,requester,submitDate,null,empId);
						}*/
					break;
					
		}
			
				
	}
	catch (UsersDaoException e) {
		result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.user"));
		result.setForwardName("success");
		e.printStackTrace();
	}
	catch (ProfileInfoDaoException e) {
		result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.profile"));
		result.setForwardName("success");
		e.printStackTrace();
	}
	catch (LoanRequestDaoException e)
	{
		result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.loanReq"));
		result.setForwardName("success");
		e.printStackTrace();
	}
	catch (FileNotFoundException e)
	{
		e.printStackTrace();
	}
	catch (LoanDetailsDaoException e)
	{
		result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.loanReq"));
		result.setForwardName("success");
		e.printStackTrace();
	}
	catch (EmpSerReqMapDaoException e)
	{
		result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.submit"));
		result.setForwardName("success");
		e.printStackTrace();
	}
		catch (LoanEligibilityCriteriaDaoException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	catch (LevelsDaoException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	catch(ArrayIndexOutOfBoundsException e)
	{
		result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.notsubmitted.cancel"));
		result.setForwardName("success");
		//e.printStackTrace();
		//e.printStackTrace();
	}
	finally
	{
		try
		{
			if(flagReq && flagLoan)
				loanReqDao.delete(loanReqPk);
		}
		catch (LoanRequestDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		return result;
}
	private LoanRequest updateActionTaken(LoanRequest loanRequest, Integer id, String actionType) throws LoanRequestDaoException
	{
		LoanRequest loanReqDto = new LoanRequest();
			
		
		loanReqDto = loanRequest;

		loanReqDto.setActionTakenBy(id);
		loanReqDto.setActionTakenDate(new Date());
					
		
		if(actionType.equalsIgnoreCase((Status.REJECTED)))
		{
			loanReqDto.setStatusId(Status.getStatusId(Status.REJECTED));
		}
		else if(actionType.equalsIgnoreCase((Status.ACCEPTED)))
		{
			loanReqDto.setStatusId(Status.getStatusId(Status.ACCEPTED));
		}
		else if(actionType.equalsIgnoreCase((Status.ON_HOLD)))
		{
			loanReqDto.setStatusId(Status.getStatusId(Status.ON_HOLD));
		}
		loanReqDao.update(new LoanRequestPk(loanReqDto.getId()), loanReqDto);
		return loanReqDto;
		
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}
	public ActionResult setProChain(HttpServletRequest request)
	{
		ProcessEvaluator procEval = new ProcessEvaluator();
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		UserLogin userLogin = login.getUserLogin();
		Object[] objs = userLogin.getRoles().toArray();
		Roles roles = (Roles) objs[0];
		Object[] sqlParams ={ roles.getRoleId() };
		Integer[] user=null;
		boolean procFlag=false;
		try
		{
			proChain = pChainDao.findByDynamicWhere("ID = (SELECT PROC_CHAIN_ID FROM MODULE_PERMISSION WHERE ROLE_ID = ? AND MODULE_ID = 25 )",	sqlParams)[0];
			/*String[] chain = proChain.getApprovalChain().split(";");
			//int i = chain.length;
			for(int i=1; i<=chain.length; i++)
			{
				user = procEval.approvers(proChain.getApprovalChain(), i, login.getUserId());
			}
			if(user.length <= 0)
			{
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.invalid.chain"));
				result.setForwardName("success");
				return result;
			}
			logger.info("user in Approval Chain = "+user);*/
		}
		catch (ProcessChainDaoException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public boolean validateApproChain(int appLevel,int raisedBy)
	{
		//ActionResult result = new ActionResult();
		boolean flag = true;
		Integer[] approvers =getAllApprovers(appLevel, raisedBy);
		if (approvers.length > 0 && approvers!=null )
		{
			for (Object approver : approvers)
			{
				try
				{
					Users userDto = usersDao.findByPrimaryKey(Integer.parseInt(approver.toString()));
					if (userDto == null)
					{
						flag = false;
					}
				}
				catch (UsersDaoException e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			flag = false;
			
		}

		return flag;
	}
	public Integer[] getAllApprovers(int appLevel,int raisedBy)
	{
		Integer[] approversArr=null;
		try
		{
	
			 approversArr= pEvaluator.approvers(proChain.getApprovalChain(), appLevel, raisedBy);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return approversArr;
	}
	public void approveAndNotify(PortalForm form, HttpServletRequest request, LoanDetails loanDto)
	{
		ActionResult result = new ActionResult();
		LoanRequest loanReqDto = null;
		LoanRequestPk loanReqPk = null;
		boolean flagReq = true;
		boolean flagNotify = true;
		boolean flagInb = true;
		logger.info("<<<<<<<>>>>>>>>> INSIDE  approveAndNotify Method<<<<<<<<>>>>>>>>");
		try
		{
			if (loanDto != null)
			{
				Login login = Login.getLogin(request);
				Users loanUser = usersDao.findByPrimaryKey(login.getUserId());
				
				UserLogin userLogin = login.getUserLogin();
				ProfileInfo loanUserInfo = profileInfoDao.findByPrimaryKey(loanUser.getProfileId());
							
				// |--Inserting details into Loan Approve Template
				boolean isNotified = true;
				String templateName = MailSettings.LOAN_TEMPLATE;
				String mailSubject = "New Loan Request Submitted ";
				double avlblAmt =  loanDto.getEligibilityAmount();
				double apldAmt = 	loanDto.getApprovedAmount();
				int emiDuration = loanDto.getEmiPeriod();
				
				String LonTyp=Loan.getLoanType(loanDto.getLoanTypeId());
				String reqId = eReqMap.getReqId();
				
				String requester=loanUserInfo.getFirstName();
				String submitDate = loanDto.getApplyDate().toString();
				String status  = Status.getStatus(loanDto.getStatusId());
				int empId = loanDto.getRequesterId();
				String messageBody = "New Loan request is received from " +requester+" on "+submitDate;
				Users hrUser = usersDao.findByPrimaryKey(loanUserInfo.getHrSpoc());
				ProfileInfo hrUserInfo = profileInfoDao.findByPrimaryKey(hrUser.getProfileId());
				
				/*logger.info("<<<<<<<>>>>>>>>> sending notification to Hr <<<<<<<<>>>>>>>>");
				if(hrUserInfo.getOfficalEmailId() != null)
				{
					String employeeName = hrUserInfo.getFirstName();
					sendEmailToRecipient(hrUserInfo.getHrSpoc(),templateName+"@"+messageBody,employeeName,avlblAmt,apldAmt,
							emiDuration,LonTyp,reqId,mailSubject,requester,submitDate,null, empId);
				}	
				*/
				logger.info("<<<<<<<>>>>>>>>> sending notification to Finance Head <<<<<<<<>>>>>>>>");
				Regions region = rDao.findByPrimaryKey(userLogin.getRegionId());
    			Notification notification = new Notification(region.getRefAbbreviation());
    			
    			int FINANCEHEAD = notification.financeHeadLevelId;
    			Users finUsersArr[] = usersDao.findByDynamicSelect("select * from USERS where LEVEL_ID = ?", new Object[]{FINANCEHEAD});
				if(finUsersArr.length!=0)
				{
					for(Users finUser : finUsersArr)
					{
    					ProfileInfo finUserInfo = profileInfoDao.findByPrimaryKey(finUser.getProfileId());
    								
    					if(finUserInfo.getOfficalEmailId() != null)
    					{
    						String employeeName = finUserInfo.getFirstName();
    						sendEmailToRecipient(hrUserInfo.getHrSpoc(),templateName+"@"+messageBody,employeeName,avlblAmt,apldAmt,
    							emiDuration,LonTyp,reqId,mailSubject,requester,submitDate,null, empId);
    					}
					}
				}
				logger.info("<<<<<<<>>>>>>>>> sending notification to Approvers <<<<<<<<>>>>>>>>");			
				int appLevel = 1;
				Integer[] approverGroupIds = pEvaluator.approvers(proChain.getApprovalChain(), appLevel, login.getUserId());
				Integer[] notifierIds = pEvaluator.notifiers(proChain.getNotification(), login.getUserId());
				/*portalMail = sendEmailToRecipient(approverGroupIds[i],templateName,employeeName,avlblAmt,apldAmt,
						emiDuration,LonTyp,reqId,mailSubject,requester,submitDate,null, empId);
				*/
				
				String bodyText =null;
				for (int i = 0; i < approverGroupIds.length; i++)
				{
					Users approveUser = usersDao.findByPrimaryKey(approverGroupIds[i]);
					ProfileInfo approveUserInfo = profileInfoDao.findByPrimaryKey(approveUser.getProfileId());
					messageBody = "New Loan request is received from " +requester+" on "+submitDate;					
					String employeeName = approveUserInfo.getFirstName();
					portalMail = sendEmailToRecipient(approverGroupIds[i],templateName+"@"+messageBody,employeeName,avlblAmt,apldAmt,
																			emiDuration,LonTyp,reqId,mailSubject,requester,submitDate,null, empId);
					loanReqDto=new LoanRequest();
					loanReqDto.setLoanUserId(login.getUserId());
					loanReqDto.setAssignTo(approverGroupIds[i]);
					loanReqDto.setStatusId(loanDto.getStatusId());
				
					loanReqDto.setLoanId(loanDto.getId());
					loanReqDto.setEsrMapId(eMapPk.getId());
					loanReqDto.setRequestedLoanAmt(loanDto.getApprovedAmount());
					loanReqDto.setEmiPeriod(loanDto.getEmiPeriod());
					loanReqDto.setLoanTypeId(loanDto.getLoanTypeId());
					loanReqDto.setSequence(1);
					bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.LOAN_TEMPLATE),
														portalMail);
					loanReqDto.setEmailData(bodyText);
					loanReqPk = 	loanReqDao.insert(loanReqDto);
					flagReq = false;
					/**
					 * Sending for inbox notification to the approver
					 */
					inbox = inboxModel.sendToDockingStation(loanReqDto.getEsrMapId(), loanReqPk.getId(), mailSubject+"@"+status, bodyText);
					
					/**
					 * notifieing to notifiers by populating Inbox.
					 */
					logger.info("<<<<<<<>>>>>>>>> sending notification to Notifiers <<<<<<<<>>>>>>>>");
					for(int j=0;j<notifierIds.length; j++)
					{
						Users notifiedUsr = usersDao.findByPrimaryKey(notifierIds[j]);
						ProfileInfo notifierInfo = profileInfoDao.findByPrimaryKey(notifiedUsr.getProfileId());
						messageBody ="New Loan request is raised by " +requester+" on "+submitDate;
						/*sendEmailToRecipient(notifierIds[i],templateName,notifierInfo.getFirstName(),avlblAmt,apldAmt,
						emiDuration,LonTyp,reqId,mailSubject,requester,submitDate,null, empId);*/
						portalMail.setEmployeeName(notifierInfo.getFirstName());
						portalMail.setMessageBody(messageBody);
						bodyText = mailGenarator.replaceFields(mailGenarator.getMailTemplate(MailSettings.LOAN_TEMPLATE), portalMail);
						inbox.setMessageBody(bodyText);
						inboxModel.notify(loanReqDto.getEsrMapId(), inbox);
						flagNotify = false;
					}
				}
				/**
				 *  Sending notification to LOAN Requestor
				 */
				logger.info("<<<<<<<>>>>>>>>> sending notification to Loan Requester <<<<<<<<>>>>>>>>");
				if(loanUserInfo.getOfficalEmailId()!=null)
				{
					String employeeName = loanUserInfo.getFirstName();
					String template=MailSettings.LOAN_TEMPLATE;
					portalMail = sendEmailToRecipient(login.getUserId(),template+"@"+" Your Loan Request "+reqId+" is submitted successfully",requester,avlblAmt,apldAmt,
							emiDuration,LonTyp,reqId,mailSubject,employeeName,submitDate,null,empId);
				}
				
			}

		}
		catch (LoanRequestDaoException e)
		{
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.loanReq"));
			result.setForwardName("success");
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UsersDaoException e)
		{
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.user"));
			result.setForwardName("success");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ProfileInfoDaoException e)
		{
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.profile"));
			result.setForwardName("success");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		catch (RegionsDaoException e)
		{
			result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.loan.region"));
			result.setForwardName("success");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(flagReq){
					loanReqDao.delete(loanReqPk);
					eMapDao.delete(eMapPk);
				}
			}
			catch (LoanRequestDaoException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (EmpSerReqMapDaoException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	
	public PortalMail sendEmailToRecipient(Integer userId,String templateName,String employeeName,double avlblAmt,double apldAmt,
			int emiDuration,String loanType,String reqId,String mailSubject,String requester,String submitDate,String comments, Integer empId)
	{
		PortalMail portalMail = new PortalMail();
		ActionResult result = new ActionResult();
		try
		{
			String[] templateBody = templateName.split("@"); 
			mailSubject = "New Loan Request Submitted - "+loanType+" - "+reqId;
			ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
			Users userApproverDto = usersDao.findByPrimaryKey(userId);
			ProfileInfo profileInfoDto = profileInfoDao.findByPrimaryKey(userApproverDto.getProfileId());
			if(mailSubject!=null)
			portalMail.setMailSubject(mailSubject);
			if(templateBody[0]!=null)
			portalMail.setTemplateName(templateBody[0]);
			if(employeeName!=null)
				portalMail.setEmployeeName(employeeName);
			if(templateBody.length>1)
			{
				portalMail.setMessageBody(templateBody[1]);
			}
			portalMail.setAvlblAmt(avlblAmt);
			if(apldAmt!=0);
			portalMail.setApldAmt(apldAmt);
			if(emiDuration!=0);
			portalMail.setEmiDuration(emiDuration);
			if(loanType!=null)
			portalMail.setLoanType(loanType);
			if(reqId!=null)
			portalMail.setReqId(reqId);
			if(requester!=null)
				portalMail.setRequester(requester);
			if(requester!=null)
			portalMail.setApproverName(requester);
			if(empId!=null)
			{
				Users emp = usersDao.findByPrimaryKey(empId);
				ProfileInfo empInfoDto = profileInfoDao.findByPrimaryKey(emp.getProfileId());
				portalMail.setEmployeeId(emp.getEmpId());
				portalMail.setEmpFname(empInfoDto.getFirstName());
				String division[] = getDivision(empInfoDto);
				portalMail.setEmpDivision(division[0]);
				portalMail.setEmpDesignation(division[1]);
						
			}
			portalMail.setSubmitDate(submitDate);
			
			if(comments!=null)
				portalMail.setComments(comments);
			
			
			portalMail.setRecipientMailId(profileInfoDto.getOfficalEmailId());
			if (profileInfoDto.getOfficalEmailId() != null && profileInfoDto.getOfficalEmailId().contains("@"))
			{
				MailGenerator mailGenarator = new MailGenerator();
				mailGenarator.invoker(portalMail);
			}

		}
		catch (AddressException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (MessagingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.Loan.mail"));
			
		}
		return (portalMail != null ? portalMail : null);

	}

	private String[] getDivision(ProfileInfo empInfoDto)
	{
		String divDes[]=new String[2];
		try
		{
			Levels lvl= levelDao.findByPrimaryKey(empInfoDto.getLevelId());
			Divison divDto = divDao.findByPrimaryKey(lvl.getDivisionId());
			divDes[0] = divDto.getName();
			divDes[1] = lvl.getDesignation();;
		}
		catch (LevelsDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (DivisonDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return divDes;
	}


}
