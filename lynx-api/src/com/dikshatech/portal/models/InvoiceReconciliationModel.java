package com.dikshatech.portal.models;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.common.utils.GenerateXls;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DivisonDao;
import com.dikshatech.portal.dao.InvoiceReconciliationDao;
import com.dikshatech.portal.dao.InvoiceReconciliationReportDao;
import com.dikshatech.portal.dao.LevelsDao;
import com.dikshatech.portal.dao.RmgTimeSheetReconDao;
import com.dikshatech.portal.dto.Divison;
import com.dikshatech.portal.dto.InvoiceReconciliation;
import com.dikshatech.portal.dto.Invoicedto;
import com.dikshatech.portal.dto.Levels;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.RmgTimeSheetReco;
import com.dikshatech.portal.dto.RmgTimeSheetRecoPk;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.factory.DivisonDaoFactory;
import com.dikshatech.portal.factory.InvoiceReconciliationDaoFactory;
import com.dikshatech.portal.factory.InvoiceReconciliationReportDaoFactory;
import com.dikshatech.portal.factory.LevelsDaoFactory;
import com.dikshatech.portal.factory.RmgTimeSheetReconDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.ResourceManager;
import com.dikshatech.portal.mail.Attachements;



public class InvoiceReconciliationModel extends ActionMethods {
	
	private static Logger		logger			= LoggerUtil.getLogger(InvoiceReconciliationModel.class);
	public static final short	AUTO			= 0;
	public static final short	DELETED			= 1;
	public static final short	ADDED			= 2;
	public static final short	MODIFIED		= 3;
	public static final short	ADDED_DELETED	= 4;
	public static final short	FIXED			= 5;
	public static final short	FIXED_DELETED	= 6;
	public static final short	HOLD			= 7;
	public static final short	FIXED_HOLD		= 8;
	public static final short	RELEASED		= 9;
	public static final short	FIXED_RELEASED	= 10;
	public static final short	REJECTED		= 11;
	public static final short	FIXED_REJECTED	= 12;
	public static final int		MODULEID		= 82;


	
	private int getDivisionId(int userId, Connection conn) {
		try{
			LevelsDao levelsdao = LevelsDaoFactory.create(conn);
			DivisonDao divisionDao = DivisonDaoFactory.create(conn);
			Users user = UsersDaoFactory.create(conn).findByPrimaryKey(userId);
			Levels level = levelsdao.findByPrimaryKey(user.getLevelId());
			Divison division = divisionDao.findByPrimaryKey(level.getDivisionId());
			if (division != null){
				if (division.getParentId() != 0){
					while (division.getParentId() != 0){
						division = divisionDao.findByPrimaryKey(division.getParentId());
					}
					if (division != null) return division.getId();
				} else return division.getId();
			}
		} catch (Exception e){}
		return 0;
	}
	
	
	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
	
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access invoice  reconciliation receive Urls without having permisson at :" + new Date());
			return result;
		}
		
		InvoiceReconciliation invoform=(InvoiceReconciliation) form;
	
		Connection conn = null;
		request.setAttribute("actionForm", "");
		InvoiceReconciliationDao invDao=InvoiceReconciliationDaoFactory.create();
		RmgTimeSheetReconDao rmgdao=RmgTimeSheetReconDaoFactory.create();
		InvoiceReconciliationReportDao invoicereconciliatonReportdao=InvoiceReconciliationReportDaoFactory.create();
		InvoiceReconciliation invodto=new InvoiceReconciliation();
		RmgTimeSheetReco dto=new RmgTimeSheetReco();
		switch (SaveTypes.getValue(form.getsType())) {
		case SAVE:
			try {
				
				if(invoform.getInvoiceDetails()!=null){
					
					for (String invoicedetail : invoform.getInvoiceDetails())
					{
					
						
							Invoicedto Invoicedetails = DtoToBeanConverter.invoiceReconciliationDetailsToBeanConverter(invoicedetail); 
							if("0".equals(Invoicedetails.getIseditinvoice())){
								Invoicedetails.setInvoiceAmount(0);
								Invoicedetails.setInvoiceDate(null);
								Invoicedetails.setActionDate(null);
								Invoicedetails.setInvoiceStatus(null);
								Invoicedetails.setCollectionAmount(0);
								Invoicedetails.setIseditinvoice("0");
								invDao.update(Invoicedetails);
							}
							if("1".equals(Invoicedetails.getIseditinvoice())){
								invDao.update(Invoicedetails);
							}
							
						
						
					}
					RmgTimeSheetReco[] invo=invoicereconciliatonReportdao.findByTimeSheetId(invoform.getId());
					
					if (invo != null && invo.length > 0){
						for (RmgTimeSheetReco report1 : invo){
					
						
							dto.setEsrMapId(report1.getEsrMapId());
							dto.setTerm(report1.getTerm());
							dto.setMonth(report1.getMonth());
							dto.setYear(report1.getYear());
							dto.setSubmittedOn(report1.getSubmittedOn());
							dto.setCompletedOn(report1.getCompletedOn());
							dto.setStatus(report1.getStatus());
							
							dto.setInvoiceStatus("Saved");
							
						}
							}
					
					dto.setId(invoform.getId());
					rmgdao.update(new RmgTimeSheetRecoPk(invoform.getId()), dto) ;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Throwing error while saving InvoiceReconciliation :" + new Date());

			}
			finally{
				ResourceManager.close(conn);
			}
			break;
		case SUBMIT:{
			
			try {
				
				
				if(invoform.getInvoiceDetails()!=null){
					
					for (String invoicedetail : invoform.getInvoiceDetails())
					{
					
						
							Invoicedto Invoicedetails = DtoToBeanConverter.invoiceReconciliationDetailsToBeanConverter(invoicedetail); 
							if("0".equals(Invoicedetails.getIseditinvoice())){
								Invoicedetails.setInvoiceAmount(0);
								Invoicedetails.setInvoiceDate(null);
								Invoicedetails.setActionDate(null);
								Invoicedetails.setInvoiceStatus(null);
								Invoicedetails.setCollectionAmount(0);
								Invoicedetails.setIseditinvoice("0");
								invDao.update(Invoicedetails);
							}
							if("1".equals(Invoicedetails.getIseditinvoice())){
								invDao.update(Invoicedetails);
							}
							
						
						
					}
				
                     RmgTimeSheetReco[] invo=invoicereconciliatonReportdao.findByTimeSheetId(invoform.getId());
					
					if (invo != null && invo.length > 0){
						for (RmgTimeSheetReco report1 : invo){
					
						
							dto.setEsrMapId(report1.getEsrMapId());
							dto.setTerm(report1.getTerm());
							dto.setMonth(report1.getMonth());
							dto.setYear(report1.getYear());
							dto.setSubmittedOn(report1.getSubmittedOn());
							dto.setCompletedOn(report1.getCompletedOn());
							dto.setStatus(report1.getStatus());
							
							dto.setInvoiceStatus("Completed");
							
						}
							}
					
					dto.setId(invoform.getId());
					rmgdao.update(new RmgTimeSheetRecoPk(invoform.getId()), dto) ;
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Throwing error while submit InvoiceReconciliation :" + new Date());
			}
			finally{
				ResourceManager.close(conn);
			}
			break;
			
				}
		}
			
		return result;
	}

	
	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		ActionResult result = new ActionResult();
		InvoiceReconciliation invoform=(InvoiceReconciliation) form;
		Login login = Login.getLogin(request);
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.access.denied"));
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access perdiem reconciliation receive Urls without having permisson at :" + new Date());
		}
		Connection conn = null;
		//	System.out.println((getDetails(login.getUserId(), conn)));
		try{
			conn = ResourceManager.getConnection();
			InvoiceReconciliationDao invoicereconciliatondao=InvoiceReconciliationDaoFactory.create();
			InvoiceReconciliationReportDao invoicereconciliatonReportdao=InvoiceReconciliationReportDaoFactory.create();
			invoform.setIsFinance(getDivisionId(login.getUserId(), conn) == 3 ? "1" : "0");
			
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
			case RECEIVEALL:{
				
				InvoiceReconciliation[] invoiceReconciliations=null;
				
				List<Object> invoiceReconciliationData=new ArrayList<Object>();
				
				
				InvoiceReconciliation[] invo=invoicereconciliatonReportdao.findAll();
				
				if (invo != null && invo.length > 0){
				for (InvoiceReconciliation report1 : invo){

					invoiceReconciliationData.add(report1);
				
				}
					}
				

				invoform.setInvoiceList(invoiceReconciliationData.toArray(new InvoiceReconciliation[invoiceReconciliationData.size()]));		
				
				request.setAttribute("actionForm",invoform );
				break;
			}
			case RECEIVEREPORT:{

				Invoicedto[] Invoicedto=null;
				Invoicedto invoiceData=new Invoicedto();
				List<Object> invoiceReconciliationData=new ArrayList<Object>();
				
				
				Invoicedto[] invoicedata=invoicereconciliatondao.findWhereInvoiceId(invoform.getId(),invoform.getIseditinvoice());
				
				List<Map<String, Object>> billable = new ArrayList<Map<String, Object>>();
				if (invoicedata != null && invoicedata.length > 0){
					int i=0;
				for (Invoicedto report1 : invoicedata){
			
					if("BILLABLE".equalsIgnoreCase(report1.getProjectType())){
						billable.add(report1.toMap(1));
						invoiceReconciliationData.add(billable);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("billable", billable);
						request.setAttribute("actionForm", map);
						result.setForwardName("success");
						
					}
				    
				
				}
					}
				
			
				break;
			}
			
			
			case RECEIVEUPDATEDREPORT:{

				Invoicedto[] Invoicedto=null;
				Invoicedto invoiceData=new Invoicedto();
				List<Object> invoiceReconciliationData=new ArrayList<Object>();
				
				try{
				
				Invoicedto[] invoicedata=invoicereconciliatondao.findWhereInvoiceId(invoform.getId(),invoform.getIseditinvoice());
				
				List<Map<String, Object>> billable = new ArrayList<Map<String, Object>>();
				if (invoicedata != null && invoicedata.length > 0){
					int i=0;
				for (Invoicedto report1 : invoicedata){
				
					if("BILLABLE".equalsIgnoreCase(report1.getProjectType())){
						billable.add(report1.toMap(1));
						invoiceReconciliationData.add(billable);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("billable", billable);
						request.setAttribute("actionForm", map);
						result.setForwardName("success");
						
					}
				    
				
				}
					}
				}catch(Exception e){
					e.printStackTrace();
					logger.info("Throwing error while RECEIVEUPDATEDREPORT InvoiceReconciliation :" + new Date());
				}
				
			
				break;
			}
			case RECEIVE:{
				
				Invoicedto[] Invoicedto=null;
				List<Object> invoiceReconciliationData=new ArrayList<Object>();
				
				
				Invoicedto[] invoicedata=invoicereconciliatondao.findWhereInvoiceReportId(invoform.getId());
				
				try{
					if (invoicedata != null && invoicedata.length > 0){
						for (Invoicedto report1 : invoicedata){

							invoiceReconciliationData.add(report1);
						
						}
							}
						
						invoform.setInvoiceList(invoiceReconciliationData.toArray(new Invoicedto[invoiceReconciliationData.size()]));		
						
						request.setAttribute("actionForm",invoform );
						
						break;
				}catch(Exception e){
					e.printStackTrace();
					logger.info("Throwing error while RECEIVE InvoiceReconciliation :" + new Date());
					
				}
				
				
			}
			
			}
			
		}
	
	catch (Exception e){
		e.printStackTrace();
		logger.info("Throwing error while receiving InvoiceReconciliation :" + new Date());
	}
		 finally{
				ResourceManager.close(conn);
			}
		return result;
	}
	
	@Override
	public Attachements download(PortalForm form) {
		Login login = (Login) form.getObject();
		InvoiceReconciliation invoform=(InvoiceReconciliation) form;
		
		if (!ModelUtiility.hasModulePermission(login, MODULEID)){
			logger.info(ModelUtiility.getInstance().getEmployeeName(login.getUserId()) + " trying to access per diem reconciliation receive Urls without having permisson at :" + new Date());
			return null;
		}
		Attachements attachements = new Attachements();
		//Invoicedto invoicedto=(Invoicedto) form;
		
		PortalData portalData = new PortalData();
	
		
		String path = portalData.getfolder(portalData.getDirPath()) + "temp";
		try{
			
			File file = new File(path);
			if (!file.exists()) file.mkdir();
			
			switch (DownloadTypes.getValue(form.getdType())) {
			case INVOICEREPORT:
				try{
				String term = "InvoiceReconciliationReport";
				attachements.setFileName(new GenerateXls().generateInvoiceReconciliationReport(InvoiceReconciliationDaoFactory.create().findWhereInvoiceidForReport(invoform.getId()), path + File.separator + term +".xls"));
				}
				catch (Exception e) {
					logger.error("Unable to generate INVOICE REPORT", e);
				}
				
			break;
			case FINALREPORT:
				try{
				String term = "FINALReconciliationReport";
				attachements.setFileName(new GenerateXls().generateFinalReconciliationReport(InvoiceReconciliationDaoFactory.create().findWhereFinalReconciliationReport(invoform.getId()), path + File.separator + term +".xls"));
				}
				catch (Exception e) {
					e.printStackTrace();
					logger.error("Unable to generate FINAL REPORT", e);
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
	
	
	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
	
		
			return null;
		}


	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}
