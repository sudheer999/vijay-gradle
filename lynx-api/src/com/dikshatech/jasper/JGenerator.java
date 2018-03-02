package com.dikshatech.jasper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;

import com.dikshatech.common.db.MyDBConnect;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PropertyLoader;

public class JGenerator
{
	private static Logger logger = LoggerUtil.getLogger(JGenerator.class);
	private static String templatePath;
	public static final String CANDIDATE = "Candidate";
	public static final String SALARYReconciliation = "SALARYReconciliation";
	public static final String PERDIEM_BANKLETTER = "PERDIEM_BANKLETTER";
	public static final String PERDiem = "PERDiem";
	public static final String EMPLOYEE = "Users";
	public static final String FBP_DECLARATION="FBP";
	public static final String SALARY="Salary";
	
	public JGenerator()
	{
		logger.debug("Setting template path for jasper");
		String portalHome = PropertyLoader.getEnvVariable();
		templatePath = portalHome + File.separator + "jasper" + File.separator;
	}

	private JasperReport getJReport(String jrxmlTemplate)
	{
		final String TEMPLATE_EXT=".jrxml";
		final String COMPILED_EXT=".jasper";
		
		boolean isCompiled=true;
		String compiledReportName=null;
		
		int pos=jrxmlTemplate.lastIndexOf(TEMPLATE_EXT);
		
		if(pos!=-1)
		{
			compiledReportName=jrxmlTemplate.substring(0,pos) + COMPILED_EXT;
		}
		else
		{
			logger.error("Invalid jrxml template name: "  + templatePath + jrxmlTemplate);
		}
		
		
		JasperReport report = null;
		try
		{
			File jasperFile=new File(templatePath + compiledReportName);
			if(jasperFile.isFile()&&jasperFile.exists())
				isCompiled=true;
			else
				isCompiled=false;

			InputStream input = null;
			if(isCompiled)
			{
				report=(JasperReport) JRLoader.loadObject(jasperFile);
			}
			else
			{
				JasperCompileManager.compileReportToFile(templatePath + jrxmlTemplate,templatePath + compiledReportName);
				
				jasperFile=new File(templatePath + compiledReportName);
				
				if(jasperFile.isFile())
					report=(JasperReport) JRLoader.loadObject(jasperFile);
				else
					logger.error("Unable to compile report to a compiled file: " + templatePath + compiledReportName 
							+ " for template: " +templatePath+ jrxmlTemplate);
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		return report;
	}

	@SuppressWarnings("unchecked")
	private JasperPrint getJPrint(JasperReport report, Connection con, HashMap<String, Object> params) throws JRException
	{
//		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(params);
		return JasperFillManager.fillReport(report, params,con);
	}
	
	
	@SuppressWarnings("unchecked")
	private JasperPrint getJPrint1(JasperReport report, List<?> params) throws JRException
	{
		Map parameters = new HashMap();
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(params);
		return JasperFillManager.fillReport(report,parameters, beanColDataSource);
	}
	
	
	@SuppressWarnings("unchecked")
	private JasperPrint getJPrint2(JasperReport report, List<?> params,String month_name) throws JRException
	{
		Map parameters = new HashMap();
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(params);
		return JasperFillManager.fillReport(report,parameters, beanColDataSource);
	}
	
	
	
	/**
	 * 
	 * @param moduleName/foldername for which module candidate /User/calendar the document is
	 * @param fileName name of file eg.offerletter.pdf etc etc
	 * @return path where to place file
	 * @throws FileNotFoundException
	 */
	public static String getOutputFile(String moduleName, String fileName)
	{
		String portalHome = PropertyLoader.getEnvVariable();
		String path = portalHome + File.separator + "Data" + File.separator;
		if (moduleName.equalsIgnoreCase("Users"))
		{
			path = path + File.separator + "employee" + File.separator + fileName;
		}
		else if (moduleName.equalsIgnoreCase("Candidate"))
		{
			path = path + File.separator + "candidate" + File.separator + fileName;
		}
		else if (moduleName.equalsIgnoreCase("calendar"))
		{
			path = path + File.separator + "calendar" + File.separator + fileName;
		} else if (moduleName.equalsIgnoreCase("fbp")){
			path = path + File.separator + "fbp" + File.separator + fileName;
		} else if (moduleName.equalsIgnoreCase("Salary")){
			path = path + File.separator + "Salary" + File.separator + fileName;
		}
		else if (moduleName.equalsIgnoreCase("SalaryReconciliation")){
			//path = path + File.separator + "salary" + File.separator + fileName;
			path = path + File.separator + fileName;
		}
		else if (moduleName.equalsIgnoreCase("PERDIEM_BANKLETTER")){
			
			path = path + File.separator + fileName;
			//String path1 = portalHome + File.separator + "Data" + File.separator + "temp";
			//path1 = path1 + File.separator + fileName;
			
		}

		return path;
	}
	
	
	/*private void removeBlankPage(List<JRPrintPage> pages) {
		      for (Iterator<JRPrintPage> i=pages.iterator(); i.hasNext();) {
	          JRPrintPage page = i.next();
          System.out.println(page.getElements().size());
          
	      if (page.getElements().size() <=14||page.getElements().size()>= 130 && page.getElements().size()<= 139||page.getElements().size()==35||page.getElements().size()==18)
          i.remove();
	          }
	      
	  }*/
	
	
/*private void deleteHeader(List<JRPrintPage> pages){
     boolean detailFound = false;
for (Iterator<JRPrintPage> i = pages.iterator();
		i.hasNext();) {
    JRPrintPage page = i.next();
 
    if(!detailFound){
    Iterator<Object> itt = page.getElements().iterator();

						while (itt.hasNext()) {
							
							
						JRPrintElement element = (JRPrintElement) itt.next();
				
						if (element.getKey() != null && element.getKey().contains("header")) {
							itt.remove();
								detailFound=true;}
    }
    }}		
	}*/
	
	/**
	 * This method is invoked to generate a pdf document from jrxmlTemplate as
	 * given fileName called from modelClass with the modelName.
	 * 
	 * @param modelName :name of model from which folder to get,which are named according to module eg candidate,employeee,news,calendar
	 * @param outFileName : name of Actual generated file
	 * @param jrxmlTemplate : which jrxml to use
	 * @param params : pass runtime input values to report generation query fields or fill value in jrxml
	 * @return
	 */
	public boolean generateFile(String modelName, String outFileName, String jrxmlTemplate, HashMap<String, Object> params)
	{
		boolean flag = false;
		String filePath = null;
		//get respective template from specified path in compiled form
		JasperReport jReport = getJReport(jrxmlTemplate);
	
		Connection con = MyDBConnect.getConnect();
		JasperPrint jPrint;
		OutputStream oStream = null;
		try
		{
			params.put("LOGO", templatePath + File.separator + "logo.png");
			params.put("SUBREPORT_DIR", templatePath);
			
			//get filepath of output file to be generated
			filePath = getOutputFile(modelName, outFileName);
			//fill all data in  report 
			jPrint = getJPrint(jReport, con, params);
	       // For removing blank pages generated through Jasper	
		    List jPages	=jPrint.getPages();
		    int size=jPages.size();
		    int i=9;
		                 while(i<size){
			                jPages.remove(i);
			                size--; }
         //	   removeBlankPage(jPages);
			oStream = new FileOutputStream(new File(filePath));
			//conver jrxml to pdf output file 
			JasperExportManager.exportReportToPdfStream(jPrint, oStream);
			flag = true;
		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		try
		{
			oStream.close();
			con.close();
		}
		catch (SQLException e)
		{
			logger.error("Failed to close Connection.");
		}
		catch (IOException e)
		{
			logger.error("Failed to close OutputStream.");
		}

		return flag;
	}

	public boolean generateFile1(String modelName, String outFileName, String jrxmlTemplate, List<?> params)
	{
		boolean flag = false;
		String filePath = null;
		
		
		//get respective template from specified path in compiled form
		JasperReport jReport = getJReport(jrxmlTemplate);
		//Connection con = MyDBConnect.getConnect();
		JasperPrint jPrint;
		OutputStream oStream = null;
		try
		{
			
			//get filepath of output file to be generated
			filePath = getOutputFile(modelName, outFileName);
			//fill all data in  report 
			jPrint = getJPrint1(jReport,params);
			oStream = new FileOutputStream(new File(filePath));
			//conver jrxml to pdf output file 
			JasperExportManager.exportReportToPdfStream(jPrint, oStream);
			
			flag = true;
		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		try
		{
			oStream.close();
			
		}
		catch (IOException e)
		{
			logger.error("Failed to close OutputStream.");
		}

		return flag;
	}
	
	
	
	
	public boolean generateFile2(String modelName, String outFileName, String jrxmlTemplate, List<?> params,String month_name)
	{
		boolean flag = false;
		String filePath = null;
		
		
		//get respective template from specified path in compiled form
		JasperReport jReport = getJReport(jrxmlTemplate);
		//Connection con = MyDBConnect.getConnect();
		JasperPrint jPrint;
		OutputStream oStream = null;
		try
		{
			
			//get filepath of output file to be generated
			filePath = getOutputFile(modelName, outFileName);
			//fill all data in  report 
			jPrint = getJPrint2(jReport,params,month_name);
			oStream = new FileOutputStream(new File(filePath));
			//conver jrxml to pdf output file 
			JasperExportManager.exportReportToPdfStream(jPrint, oStream);
			
			flag = true;
		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		try
		{
			oStream.close();
			
		}
		catch (IOException e)
		{
			logger.error("Failed to close OutputStream.");
		}

		return flag;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * This method is invoked to generate a xls document from jrxmlTemplate as
	 * given fileName called from modelClass with the modelName.
	 * 
	 * @param modelName :name of model from which folder to get,which are named according to module eg candidate,employeee,news,calendar
	 * @param outFileName : name of Actual generated file
	 * @param jrxmlTemplate : which jrxml to use
	 * @param params : pass runtime input values to report generation query fields or fill value in jrxml
	 * @return
	 */
	public boolean generateXlsFile(String modelName, String outFileName, String jrxmlTemplate, HashMap<String, Object> params)
	{
		boolean flag = false;
		String filePath = null;
		//get respective template from specified path in compiled form
		JasperReport jReport = getJReport(jrxmlTemplate);
		Connection con = MyDBConnect.getConnect();
		JasperPrint jPrint;
		OutputStream oStream = null;
		try
		{
			params.put("LOGO", templatePath + File.separator + "logo.png");
			params.put("SUBREPORT_DIR", templatePath);
			
			//get filepath of output file to be generated
			filePath = getOutputFile(modelName, outFileName);
			//fill all data in  report 
			jPrint = getJPrint(jReport, con, params);
			oStream = new FileOutputStream(new File(filePath));
			//conver jrxml to xls output file 
			JRXlsExporter xlsExporter=new JRXlsExporter();
			
			xlsExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jPrint);
			xlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, oStream);
			
			xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
			xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			
			xlsExporter.exportReport();
			
			flag = true;
		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		try
		{
			oStream.close();
			con.close();
		}
		catch (SQLException e)
		{
			logger.error("Failed to close Connection.");
		}
		catch (IOException e)
		{
			logger.error("Failed to close OutputStream.");
		}

		return flag;
	}

	/*
	 * public static void main(String[] args) { JGenerator jGenerator = new
	 * JGenerator(); HashMap< String, Object> params = new HashMap();
	 * params.put("ID", 8); params.put("CANDIDATE_ID", 8);
	 * 
	 * jGenerator.generateFile("Candidate", "CandidateOfferLetter.pdf",
	 * JTemplates.OFFER_LETTER, params); }
	 */
}
