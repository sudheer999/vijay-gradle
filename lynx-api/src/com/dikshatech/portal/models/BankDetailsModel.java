package com.dikshatech.portal.models;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.BankDetalilsBean;
import com.dikshatech.beans.UserLogin;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.Notification;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.BankDetailsDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.BankDetails;
import com.dikshatech.portal.dto.BankDetailsPk;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.BankDetailsDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.BankDetailsDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.MailGenerator;
import com.dikshatech.portal.mail.MailSettings;
import com.dikshatech.portal.mail.PortalMail;

public class BankDetailsModel extends ActionMethods
{
	private static Logger	logger	= LoggerUtil.getLogger(BankDetailsModel.class);
	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		
		BankDetailsDao bankDetailsDao= BankDetailsDaoFactory.create();
		BankDetails bankDetails= (BankDetails)form;
		BankDetailsPk  bankDetailsPk = new BankDetailsPk();
		ActionResult result = new ActionResult();
		try
		{
		bankDetailsPk.setId(bankDetails.getId());
			  bankDetailsDao.delete(bankDetailsPk);
		}
		catch (BankDetailsDaoException e)
		{
			logger.info("Failed to delete Details");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.bankdetailsnotsaved"));
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
		ActionResult result = new ActionResult();
		DropDown  dropDown= new DropDown();
		BankDetailsDao bankDetailsDao= BankDetailsDaoFactory.create();
		BankDetalilsBean [] bankDetailsBean=null;
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
		
		try
		{
			
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
			{
			case RECEIVEALL:
			BankDetails bankDetails[]= bankDetailsDao.findAll();
			bankDetailsBean= new  BankDetalilsBean [bankDetails.length];
				int i=0;
			for(BankDetails bank:bankDetails){
				Users user=usersDao.findByPrimaryKey(bank.getCreatedBy());
				ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(user.getProfileId());
				BankDetalilsBean bankBean=DtoToBeanConverter.DtoToBeanConverter(bank);
				bankBean.setCreatedByName(profileInfo.getFirstName() + " "+ profileInfo.getLastName());
				bankDetailsBean[i]=bankBean;
				i++;
			}
			dropDown.setDropDown(bankDetailsBean);
			request.setAttribute("actionForm", dropDown);
			break;
			
			case RECEIVE:
				dropDown=(DropDown)form;
				BankDetails bankDetails2=bankDetailsDao.findByPrimaryKey(dropDown.getKey1());
				
				Users user=usersDao.findByPrimaryKey(bankDetails2.getCreatedBy());
				ProfileInfo profileInfo = profileInfoDao.findByPrimaryKey(user.getProfileId());
					bankDetails2.setCreatedOn((bankDetails2.getCreatedOn()));
				bankDetails2.setCreatedByName(profileInfo.getFirstName() + " "+ profileInfo.getLastName());
				
				if(bankDetails2.getUpdatedBy()>0){
				user=usersDao.findByPrimaryKey(bankDetails2.getUpdatedBy());
				profileInfo = profileInfoDao.findByPrimaryKey(user.getProfileId());
				bankDetails2.setUpdatedByName(profileInfo.getFirstName() + " "+ profileInfo.getLastName());
				}
				request.setAttribute("actionForm", bankDetails2);
				break;
				
			default:
				break;
			
			}//switch ends
		}
		catch (BankDetailsDaoException e)
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
		
		
		return result;
	}

	
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		BankDetailsDao bankDetailsDao= BankDetailsDaoFactory.create();
		BankDetails bankDetails= (BankDetails)form;
		ActionResult result = new ActionResult();
		MailGenerator mailGenerator= new MailGenerator();
		PortalMail portalMail= new PortalMail();
		Login login = Login.getLogin(request);
		UsersDao usersDao = UsersDaoFactory.create();
		ProfileInfoDao profielInfoDao = ProfileInfoDaoFactory.create();
		String selectUsers = "SELECT U.ID, U.EMP_ID, U.LEVEL_ID, U.REG_DIV_ID, U.PROFILE_ID, U.FINANCE_ID, U.NOMINEE_ID, U.PASSPORT_ID, U.PERSONAL_ID, U.COMPLETE, U.STATUS, U.CREATE_DATE,U.USER_CREATED_BY,U.EXPERIENCE_ID, U.SKILLSET_ID, U.OTHERS, U.ACTION_BY  FROM USERS U ";

		String sqlForFinanceHeadByLevel=selectUsers + " LEFT JOIN PROFILE_INFO P ON P.ID = U.PROFILE_ID  WHERE P.LEVEL_ID = ? ";
		
		try
		{
			bankDetails.setCreatedOn(PortalUtility.getdd_MM_yyyy_hh_mm_a(Calendar.getInstance().getTime()));
			bankDetails.setCreatedBy(login.getUserId());
			BankDetailsPk  bankDetailsPk =  bankDetailsDao.insert(bankDetails);
			
			
			UserLogin userLogin = login.getUserLogin();
			Users createdBy=usersDao.findByPrimaryKey(login.getUserId());
			ProfileInfo createdByProfile = profielInfoDao.findByPrimaryKey(createdBy.getProfileId());
			String AllRecipientCC[]=new String[1];
			AllRecipientCC[0]=userLogin.getOfficialEmaiID();
			
			
			/**
			 * Send email to Finance department Head 
			 * Send email CC to user who added
			 */
			RegionsDao   regionsDao = RegionsDaoFactory.create();
			Regions region = regionsDao.findByPrimaryKey(userLogin.getRegionId());
			Notification notification = new Notification(region.getRefAbbreviation());
			
			int FINANCEHEAD = notification.financeHeadLevelId;
			Users financeHead[]=usersDao.findByDynamicSelect(sqlForFinanceHeadByLevel,new Object[]{new Integer(FINANCEHEAD)});
				try{
					if ( financeHead != null )
					{
					for(Users user1:financeHead){
								ProfileInfo profile = profielInfoDao.findByPrimaryKey(user1.getProfileId());
								portalMail.setMailSubject("New bank details added by "+ createdByProfile.getFirstName() );
								portalMail.setRecipientMailId(profile.getOfficalEmailId());
								portalMail.setProjectId(bankDetailsPk.getId());
								portalMail.setProjectName(bankDetails.getBankName());
								portalMail.setEmpFname(profile.getFirstName());//finance dep head name
								portalMail.setEmpLName(createdByProfile.getFirstName());//added by name
								portalMail.setDate(PortalUtility.getdd_MM_yyyy_hh_mm_a(new Date()));//date when added
								portalMail.setTemplateName(MailSettings.NEW_BANK_DETAILS);
								portalMail.setAllReceipientcCMailId(AllRecipientCC);
								mailGenerator.invoker(portalMail);
						}
					}
				}catch(ProfileInfoDaoException e){
					e.printStackTrace();
				}
			
			
		}
		catch (BankDetailsDaoException e)
		{
			logger.info("Failed to save Bank Details");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.bankdetailsnotsaved"));
			e.printStackTrace();
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
		catch (RegionsDaoException e)
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
				
		return result;
	}

	public String[] getAllFinanceMailId(Users[] financeHead){
		
		return null;
		
	}
	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		Login login = Login.getLogin(request);
		BankDetailsDao bankDetailsDao= BankDetailsDaoFactory.create();
		BankDetails bankDetails= (BankDetails)form;
		BankDetailsPk  bankDetailsPk = new BankDetailsPk();
		ActionResult result = new ActionResult();
		try
		{
			BankDetails bankDetails2=bankDetailsDao.findByPrimaryKey(bankDetails.getId());
			bankDetails.setCreatedOn(bankDetails2.getCreatedOn());
		      bankDetailsPk.setId(bankDetails.getId());
		      bankDetails.setUpdatedBy(login.getUserId());
			  bankDetailsDao.update(bankDetailsPk,bankDetails);
		}
		catch (BankDetailsDaoException e)
		{
			logger.info("Failed to save Update Details");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.bankdetailsnotsaved"));
			e.printStackTrace();
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

/*public BankDetalilsBean[] getBankDetailsBean()
{
	return BankDetailsBean;
}

public void setBankDetailsBean(BankDetalilsBean[] bankDetailsBean)
{
	BankDetailsBean = bankDetailsBean;
}*/
