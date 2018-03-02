package com.dikshatech.portal.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.ChargeCodeDao;
import com.dikshatech.portal.dao.CompanyDao;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.PoDetailsDao;
import com.dikshatech.portal.dao.PoEmpMapDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dao.ProjectDao;
import com.dikshatech.portal.dao.ProjectMapDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.ChargeCode;
import com.dikshatech.portal.dto.ChargeCodePk;
import com.dikshatech.portal.dto.Company;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.PoDetail;
import com.dikshatech.portal.dto.PoDetails;
import com.dikshatech.portal.dto.PoDetailsPk;
import com.dikshatech.portal.dto.PoEmpMap;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Project;
import com.dikshatech.portal.dto.ProjectMap;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.ChargeCodeDaoException;
import com.dikshatech.portal.exceptions.CompanyDaoException;
import com.dikshatech.portal.exceptions.DivisonDaoException;
import com.dikshatech.portal.exceptions.PoDetailDaoException;
import com.dikshatech.portal.exceptions.PoDetailsDaoException;
import com.dikshatech.portal.exceptions.PoEmpMapDaoException;
import com.dikshatech.portal.exceptions.ProfileInfoDaoException;
import com.dikshatech.portal.exceptions.ProjectDaoException;
import com.dikshatech.portal.exceptions.ProjectMapDaoException;
import com.dikshatech.portal.exceptions.RegionsDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.ChargeCodeDaoFactory;
import com.dikshatech.portal.factory.CompanyDaoFactory;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.PoDetailDaoFactory;
import com.dikshatech.portal.factory.PoDetailsDaoFactory;
import com.dikshatech.portal.factory.PoEmpMapDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.ProjectDaoFactory;
import com.dikshatech.portal.factory.ProjectMapDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.ResourceManager;

public class PoDetailsModel extends ActionMethods
{
	private static Logger	logger	= LoggerUtil.getLogger(PoDetailsModel.class);

	@Override
    public ActionResult defaultMethod(PortalForm form, HttpServletRequest request,
            HttpServletResponse response)
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
		ActionResult result = new ActionResult();
	    switch (ActionMethods.ExecuteTypes.getValue(form.geteType()))
        {
        case ENABLEPODETAIL:
        {
        	PoDetails detailsForm = (PoDetails) form;
        	PoDetailsDao poDao = PoDetailsDaoFactory.create();
        	try
            {
	            PoDetails poDetail = poDao.findByPrimaryKey(detailsForm.getId());
	            poDetail.setIsDisable(detailsForm.getIsDisable());
	            PoDetailsPk detailsPk = poDetail.createPk();
	            poDao.update(detailsPk, poDetail);
            }
            catch (PoDetailsDaoException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
	        
	        break;
        }
        case DISABLEPODETAIL:
        {
        	PoDetails detailsForm = (PoDetails) form;
        	PoDetailsDao poDao = PoDetailsDaoFactory.create();
        	try
            {
	            PoDetails poDetail = poDao.findByPrimaryKey(detailsForm.getId());
	            poDetail.setIsDisable(detailsForm.getIsDisable());
	            PoDetailsPk detailsPk = poDetail.createPk();
	            poDao.update(detailsPk, poDetail);
	            result.setForwardName("success");
            }
            catch (PoDetailsDaoException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
        	break;
        }
        default:
	        break;
        }
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
	   switch (ActionMethods.ReceiveTypes.getValue(form.getrType()))
	   {
	   case RECEIVEPODETAILS:
	   {
		   
		   PoDetails poForm = (PoDetails) form;
		   PoDetailsDao detailsDao = PoDetailsDaoFactory.create();
		   PoDetails poDetail = null;
		   PoEmpMapDao poEmpDao = PoEmpMapDaoFactory.create();
		   Set<PoEmpMap> poEmpSet = new HashSet<PoEmpMap>();
		   ProjectDao projDao = ProjectDaoFactory.create();
		   UsersDao userDao = UsersDaoFactory.create();
		   ProfileInfoDao profileDao = ProfileInfoDaoFactory.create(); 
		   ChargeCodeDao chCodeDao = ChargeCodeDaoFactory.create();
		   UsersDao usersDao=UsersDaoFactory.create();
		   ProfileInfoDao profileInfoDao=ProfileInfoDaoFactory.create();
			  
		   try
		   {
			   poDetail = detailsDao.findByPrimaryKey(poForm.getId());
			   // Find Project Detail
			   Project project = projDao.findByPrimaryKey(poDetail.getProjId());
			   poForm.setProjId(project.getId());
			   poForm.setProjName(project.getName());
			   
			   poForm.setId(poDetail.getId());
			//   poForm.setPoStDate(PortalUtility.fromStringToDate(poDetail.getPoStDate().toString()));
			   poForm.setPoStDate(poDetail.getPoStDate());
			   //poForm.setPoEndDate(PortalUtility.fromStringToDate(poDetail.getPoEndDate().toString()));
			   poForm.setPoEndDate(poDetail.getPoEndDate());
			   poForm.setPoDuration(poDetail.getPoDuration());
			   poForm.setPaymentTerms(poDetail.getPaymentTerms());
			   if (poDetail.getPoNumber() != null)
			   {
					poForm.setPoNumber(poDetail.getPoNumber());
					poForm.setPoDate(poDetail.getPoDate());
			   }
			   PoEmpMap[] poEmpDetails = poEmpDao.findByDynamicSelect("SELECT * FROM PO_EMP_MAP PEM WHERE PEM.PO_ID=? AND CURRENT=1", new Object[]{poDetail.getId()});
			   //PoEmpMap[] poEmpDetails = poEmpDao.findWherePoIdEquals(poDetail.getId());
				   poForm.setNoOfResource(poEmpDetails.length);
				   for (PoEmpMap poEmpMap : poEmpDetails)
				   {
					   Users user = userDao.findByPrimaryKey(poEmpMap.getEmpId());
					   ProfileInfo profile = profileDao.findByPrimaryKey(user.getProfileId());
					   poEmpMap.setUserId(user.getId());
					   if(user.getEmpId()>0)
						   poEmpMap.setEmpId(user.getEmpId());
					   poEmpMap.setUser(profile.getFirstName()+" "+profile.getLastName());
					   poEmpSet.add(poEmpMap);
		            
				   }
				   poForm.setPoEmployees(poEmpSet);
				   // Get Charge Code Detail
				   ChargeCode[] chCodeObj = chCodeDao.findWherePoIdEquals(poDetail.getId());
				   for (ChargeCode chargeCode : chCodeObj)
				   {
					   poForm.setChCode(chargeCode.getChCode());
					   poForm.setChCodeName(chargeCode.getChCodeName());
					   
					   String authUsers=chargeCode.getAuthUsers();
					   Users user=usersDao.findByPrimaryKey(Integer.parseInt(authUsers));
					   ProfileInfo profileInfo=profileInfoDao.findByPrimaryKey(user.getProfileId());
					   
					   poForm.setOwnerName(profileInfo.getFirstName()+" "+profileInfo.getLastName());
					   poForm.setAuthorisedUsers(authUsers);
					   
				   }
			     
			   request.setAttribute("actionForm",poForm);
			   result.setForwardName("success");
		   }
		   catch (PoDetailsDaoException e)
		   {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
        catch (PoEmpMapDaoException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        catch (ProjectDaoException e)
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
        catch (ChargeCodeDaoException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
       /* catch (ParseException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }*/
		   
		    break;
 
	   }
	   
	   
	   
	   default:
	    break;
	   }	
	    return result;
    }

	@Override
    public ActionResult save(PortalForm form, HttpServletRequest request)
    {
		Login login=Login.getLogin(request);
		ActionResult result =  new ActionResult();
	    PoDetails poDetailForm = (PoDetails) form;
	    PoDetailsDao poDao = PoDetailsDaoFactory.create();
	    PoDetailsPk poPk = null;
	    Project project = null;
	    ProjectDao projDao = ProjectDaoFactory.create();
	    PoEmpMapDao poEmpDao = PoEmpMapDaoFactory.create();
	    String chargeCode = null;
	    ProjectMapDao projMapDao = ProjectMapDaoFactory.create();
	    CompanyDao compDao = CompanyDaoFactory.create();
	    RegionsDao regDao = RegionsDaoFactory.create();
	    DivisonDao divDao = DivisonDaoFactory.create();
	    Regions region = null;
	    Divison department = null;
	    Company company = null;
	    ChargeCodeDao chDao = ChargeCodeDaoFactory.create();
	     //String activeFlag="ACTIVE_CHARGE_CODE";
	    /*
	     *During creation of new charge-code, we need to check if there is an already existing charge-code with the same name in the same project
	     *i.e. Project_one can have charge-codes {cc1,cc2,cc3}  & Project_two can have charge-codes {cc1,cc2,cc3}
	     *but this is should not allowed ---------> Project_temp with charge-codes {cc1,cc1} . . ..  which will be restricted by the following code 
	     */
	    
	    try{
	    	ChargeCode[] existingChargeCodes = chDao.findWhereChCodeNameEquals(poDetailForm.getChCodeName());
	    	if(existingChargeCodes!=null && existingChargeCodes.length>0){
	    		ArrayList<Integer> poIdList = new ArrayList<Integer>(existingChargeCodes.length);
	    		for(ChargeCode eachChargeCode:existingChargeCodes)
	    			poIdList.add(eachChargeCode.getPoId());
	    		final String ids = poIdList.toString().replace('[', ' ').replace(']', ' ').trim();
	    		PoDetails[] poDetails = poDao.findByDynamicSelect("SELECT * FROM PO_DETAILS PO WHERE PO.ID IN("+ids+") AND PO.PROJ_ID=?", new Object[]{poDetailForm.getProjId()});
	    		
	    		/*if(poDetails!=null && poDetails.length>0){
		    		result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.duplicate.chargecode.name"));
					result.setForwardName("success");
					return result;
		    	}*/
	    	}	    	
	    	    	
	    }catch(Exception ex){
	    	logger.error("New Charge-code : Exception was thrown when trying to check if a chargecode already exists with the name:"+poDetailForm.getChCodeName());
	    	result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.duplicate.chargecode.error"));
			result.setForwardName("success");
			return result;
	    }
	    
	    try
        { 
	    	if("ACTIVE_CHARGE_CODE".equals(poDetailForm.getActiveFlag())){
	    		
				
					Connection conn = ResourceManager.getConnection();
					PoDetail dto=new PoDetail();
					String updatePoDetailChargeCode=PoDetailDaoFactory.create(conn).updateAllPoDetailsById(poDetailForm.getPoEmapId(), poDetailForm);
					request.setAttribute("actionForm", updatePoDetailChargeCode);
				 
	    	
	   	
	    	}else{
	    		
	    		poPk = poDao.insert(poDetailForm);
	        
	        // Get Charge Code Details
	        project = projDao.findByPrimaryKey(poDetailForm.getProjId());
	    	ProjectMap[] projMaps = projMapDao.findWhereProjIdEquals(project.getId());
	    	for (ProjectMap projectMap : projMaps)
            {
	    		company = compDao.findByPrimaryKey(project.getCompanyId());
	    		if(new Integer(projectMap.getRegionId())!=null)
		        {
		        	region = regDao.findByPrimaryKey(projectMap.getRegionId());
		        }
		        if(new Integer(projectMap.getDeptId())!=null)
		        {
		        	department = divDao.findByPrimaryKey(projectMap.getDeptId());
		        }
	    	    
            }
	    	
	        // Set and Save ChargeCode
	        ChargeCode chcode = new ChargeCode();
	        chargeCode = setChargeCodeInfo(company,region,department,poPk.getId());
	        chcode.setPoId(poPk.getId());
	        chcode.setChCode(chargeCode);
	        chcode.setChCodeName(poDetailForm.getChCodeName());
	        
	        if(poDetailForm.getAuthorisedUsers()!=null)
	        {
	        	chcode.setAuthUsers(poDetailForm.getAuthorisedUsers());
	        }
	        else
	        {
	        	chcode.setAuthUsers(login.getUserId().toString());
	        }
	        
	        chDao.insert(chcode);
	        	
	        if(poDetailForm.getEmpDetails()!=null)
	        {
	        	for (String text : poDetailForm.getEmpDetails())//vijay jayaram : PO_EMP_MAP.INACTIVE = 1    implies.... false/no
                {
	        		PoEmpMap poEmp = DtoToBeanConverter.stringToBeanConverter(text);//CURRENT updated to 0
	        		poEmp.setPoId(poPk.getId());
	        		//poEmp.setInactive(poDetailForm.getIsDisable());
	        		poEmp.setCurrent((short)1);
	        		poEmpDao.insert(poEmp);
                }
	        	
	        }}
	        result.setForwardName("success");
        }
        /*catch (PoDetailsDaoException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }*/
        catch (ProjectDaoException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        catch (PoEmpMapDaoException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        catch (CompanyDaoException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        catch (RegionsDaoException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        catch (DivisonDaoException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        catch (ChargeCodeDaoException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        catch (ProjectMapDaoException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PoDetailDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PoDetailsDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return result;
    }

	@Override
    public ActionResult update(PortalForm form, HttpServletRequest request)
    {
		Login login=Login.getLogin(request);
	    ActionResult result = new ActionResult();
	    PoDetails poForm = (PoDetails) form;
	    PoDetailsDao poDao = PoDetailsDaoFactory.create();
	    PoEmpMapDao poEmpDao = PoEmpMapDaoFactory.create();
	    ChargeCodeDao chDao = ChargeCodeDaoFactory.create();
	    
	    poForm.setAuthorisedUsers(poForm.getUserId().toString());//check if this fix works as expected
	    
	    //to check .. on editing... if the charge-code name is unique or not..under the selected project
	    logger.debug("charge-code update : received isValidationRequired="+poForm.getValidation());	    
	    if(poForm.getValidation().equalsIgnoreCase("1")){
	    	try{
			   	ChargeCode[] existingChargeCodes = ChargeCodeDaoFactory.create().findByDynamicSelect("SELECT * FROM CHARGE_CODE CC WHERE CC.PO_ID IN(" +
			   			"SELECT POD.ID FROM PO_DETAILS POD WHERE POD.PROJ_ID=?)", new Object[]{poForm.getProjId()});
			   	if(existingChargeCodes!=null && existingChargeCodes.length>0){
			   		ArrayList<String>existingNamesList = new ArrayList<String>();
			   		for(ChargeCode eachChargeCode : existingChargeCodes)
			   			existingNamesList.add(eachChargeCode.getChCodeName());
			   		
			   		if(existingNamesList.size()>0){
			   			if(existingNamesList.contains(poForm.getChCodeName())){
			   				result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.duplicate.chargecode.name"));
			   				result.setForwardName("success");
			   				return result;
			   			}
			   		}
			   	}    	
			   	
			   }catch(Exception ex){
			   	result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.duplicate.chargecode.error"));
				result.setForwardName("success");
				return result;
			   }	
			   logger.debug("charge-code update validation completed....proceeding to save the changes");
	    }	        
	    	       
	    try
        {
	        PoDetailsPk poDetailPk = poForm.createPk();
	        poDao.update(poDetailPk, poForm);
	        logger.debug("PoDetails is updated:::");
	        if(poForm.getEmpDetails()!=null)
	        {
	        	poEmpDao.deleteAllByPoDetail(poForm.getId());
	        	for (String text : poForm.getEmpDetails())
                {
	        		PoEmpMap poEmp = DtoToBeanConverter.stringToBeanConverter(text);
	        		poEmp.setPoId(poDetailPk.getId());
	        		//poEmp.setInactive(poForm.getIsDisable());
	        		poEmp.setCurrent((short)1);
	        		poEmpDao.insert(poEmp);
                }
	        	
	        }
	        if(poForm.getChCodeName()!=null)
	        {
	        	ChargeCode[] chrgecodeArr = chDao.findWherePoIdEquals(poDetailPk.getId());
	        	for (ChargeCode chargeCode : chrgecodeArr)
                {
	                ChargeCodePk chCodePk = chargeCode.createPk();
	                chargeCode.setChCodeName(poForm.getChCodeName());
	                
	                if(poForm.getAuthorisedUsers()!=null)
	    	        {
	                	chargeCode.setAuthUsers(poForm.getAuthorisedUsers());
	    	        }
	    	        else
	    	        {
	    	        	chargeCode.setAuthUsers(login.getUserId().toString());
	    	        }
	                chDao.update(chCodePk, chargeCode);
                }
	        }
	        result.setForwardName("success");
        }
        catch (PoDetailsDaoException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        catch (PoEmpMapDaoException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        catch (ChargeCodeDaoException e)
        {
	        // TODO Auto-generated catch block
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
	
	private String setChargeCodeInfo(Company company, Regions region, Divison department, int poId)
	{
		String chargecode = null;

		if (region != null && department!=null)
		{
			chargecode = region.getRefAbbreviation() + "-" + department.getName().substring(0,3) + "-" + poId;
		}
		else if(region != null && department==null)
		{
			chargecode = company.getCompanyName().substring(0,2) + "-"+region.getRefAbbreviation() + "-" + poId;
		}
		else 
		{

			chargecode = company.getCompanyName().substring(0,2) + "-"+ poId;
		}
		return chargecode;

	}
	
	/*
	 * An employee can be associated with a charge-code in following two ways : 
	 * (1)Roll_on to a project having charge-code
	 * (2)associated during the creation of new charge-code
	 * In either case, we need to update this information in PO_EMP_MAP.CURRENT
	 * The PO_EMP_MAP.CURRENT = 1 indicates the latest charge-code the employee is associated with. 
	 */
	public static void updatePoEmpMap(int empId) throws Exception{//empId == userId
		String updateQuery = "UPDATE PO_EMP_MAP PEM SET PEM.CURRENT=0 WHERE PEM.EMP_ID="+empId;
		PoEmpMapDaoFactory.create().updatePoEmpMap(updateQuery);
	}
		
}