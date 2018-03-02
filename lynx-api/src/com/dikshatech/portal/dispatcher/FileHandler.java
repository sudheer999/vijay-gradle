package com.dikshatech.portal.dispatcher;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dto.CompareErrorReport;
import com.dikshatech.portal.dto.CompareErrorReportList;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.forms.PortalForm;

/**
 * Servlet implementation class FileHandler
 */
public class FileHandler extends HttpServlet {

	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= LoggerUtil.getLogger(FileHandler.class);
	PortalForm					portalForm			= new PortalForm();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Inside doPost method");
		response.setContentType("text/plain");
		ServletOutputStream out = response.getOutputStream();
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		List<FileItem> fileItems = new ArrayList<FileItem>();
		String cType = null;
		String docType = null;
		Integer fileId[] = null;
		int userId = 0;
		String description = null;
		boolean longName = false;
		try{
			if (isMultipart){
				// Create a factory for disk-based file items
				FileItemFactory factory = new DiskFileItemFactory();
				// Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload(factory);
				//set max size which user can upload
				upload.setSizeMax(4 * 1024 * 1024);//4MB - 4194304 in bytes
				// Parse the request
				List /* FileItem */<?> items = upload.parseRequest(request);
				// Process the uploaded items
				Iterator<?> iter = items.iterator();
				Hashtable<String, String> hashRequest = new Hashtable<String, String>();
				while (iter.hasNext()){
					FileItem item = (FileItem) iter.next();
					if (item.isFormField()){
						logger.info(item.getFieldName());
						/* processFormField(item); */
						if (item.getFieldName().equals("cType")){
							cType = item.getString();
						} else if (item.getFieldName().equals("docType")){
							docType = item.getString();
						} else if (item.getFieldName().equals("candidateId")){
							userId = Integer.parseInt(item.getString());
						} else if (item.getFieldName().equals("descriptions")){
							description = item.getString();
						} else if (item.getFieldName().equals("chklstStatusId")){
							request.setAttribute("chklstStatusId", Integer.parseInt(item.getString()));
						}else if (item.getFieldName().equals("uploadSrId")){
							userId = Integer.parseInt(item.getString());
						}
						// Plain request parameters will come here.
						String reqName = item.getFieldName();
						String value = item.getString();
						if (value.length() > 0) hashRequest.put(reqName, value);
					} else{
						fileItems.add(item);
					}
				}
				fileId = portalForm.getComponentObject(cType).upload(fileItems, docType, userId, request, description);
				//fetching description from Documents
				if (fileId != null && fileId[0] != null){
					switch (DocumentTypes.valueOf(docType)) {
						case LEAVES_XLS:{
							if (fileId != null){
								if (fileId.length == 1 && fileId[0].equals(-1)) out.println("Error While Reading Leave Information.");
								else if (fileId.length == 1 && fileId[0].equals(0)) out.println("Leave information uploaded successfully.");
								else if (fileId.length > 0){
									out.println("Leave information uploaded successfully.");
									out.println(" Error while updating these employees : " + Arrays.asList(fileId).toString().replace("[", "").replace("]", ""));
									logger.info("Error while updating these employees : " + Arrays.asList(fileId).toString());
								}
							}
						}
							break;
						case PAYSLIP:{
							ArrayList bad = (ArrayList) request.getAttribute("BADFILES");
							if (fileId.length > 0 || (bad != null && !bad.isEmpty())){
								out.println("Payslips uploaded successfully.");
								out.println("ERROR:");
								logger.info("Payslips uploaded successfully.");
								if (fileId.length > 0) out.println(("Invalid Employee Id(s): " + Arrays.asList(fileId).toString().replace("[", "").replace("]", "")));
								if (bad != null && !bad.isEmpty()) out.println(("InProper format file(s): " + bad.toString().replace("[", "").replace("]", "")));
								logger.info("Error while uplaoding these users : " + Arrays.asList(fileId).toString());
							} else if (fileId.length == 0) out.println("Payslips uploaded successfully.");
						}
							break;
						case REIMBURSEMENTLIST:{
							if (fileId != null){
								if (fileId.length == 1 && fileId[0].equals(-1)) out.println("Error While Reading Information.");
								else if (fileId.length == 1 && fileId[0].equals(0)) out.println("Details uploaded successfully.");
								else if (fileId.length > 0){
									//out.println("Details uploaded successfully.");
									out.println(" Error while updating these rows : " + Arrays.asList(fileId).toString().replace("[", "").replace("]", ""));
									logger.info("Error while updating these rows : " + Arrays.asList(fileId).toString());
								}
							}
						}
							break;
						case INSURANCE_XLS:{
							if (fileId != null){
								if (fileId.length == 1 && (fileId[0].equals(0) || fileId[0].equals(-1))){
									if (fileId[0].equals(0)){
										out.println(" File uploaded successfully. ");
									} else if (fileId[0].equals(-1)){
										out.println("Error while inserting some information due to internal error.\nSome records might be inserted!.\nRemove all inserted records while uplaoding next time.\nPlease contact admin for more info.");
										logger.error("Error While Inserting Information ");
									}
								} else if (fileId.length >= 1){
									out.println("Error While Reading Information at following rows : \n" + Arrays.asList(fileId).toString().replace("[", "").replace("]", ""));
									logger.error("Error While Reading Information at following rows : \n" + Arrays.asList(fileId).toString().replace("[", "").replace("]", ""));
								}
							}
						}
							break;
						case FBP_XLS:{
							if (fileId != null && fileId.length > 0){
								String incorr = "0";
								for (int i : fileId)
									incorr += "," + i;
								out.println("Data is not correct for the employees " + incorr + " Hence skipped for them. " + " Please retry for them configuring correctly");
							}
						}
							break;
						case BENEFIT_XLS:{
							if (fileId != null && fileId.length > 0){
								String incorr = "0";
								for (int i : fileId)
									incorr += "," + i;
								out.println("Data is not correct for the Rows " + incorr + " Hence skipped for them. " + " Please retry configuring those rows correctly");
							}
						}
							break;
						case TDS_XLS:
							logger.info((String) request.getAttribute("TDS_MESSAGE"));
							out.println((String) request.getAttribute("TDS_MESSAGE"));
							break;
						case TIMESHEET_CONSULTANTS:
							logger.info((String) request.getAttribute("TIMESHEET_MESSAGE"));
							out.println((String) request.getAttribute("TIMESHEET_MESSAGE"));
							break;
						case APPRAISAL_PROJECTS:
						case BILLABLE_DAYS:
							logger.info((String) request.getAttribute("APPRAISAL_MESSAGE"));
							out.println((String) request.getAttribute("APPRAISAL_MESSAGE"));
							break;
							
						case NETSALARY_XLS:
					
						List<CompareErrorReport> compareList = (List<CompareErrorReport>)request.getAttribute("REPORTLIST");
						CompareErrorReportList list =  new 	CompareErrorReportList();
						list.setList(compareList);
						
						try {
							JAXBContext context = JAXBContext.newInstance(CompareErrorReportList.class);
							Marshaller m = context.createMarshaller();
							m.marshal(list, out);
						} catch (JAXBException e) {
							e.printStackTrace();
						}
						break;
						
						case GROSSSALARY_XLS:
							
							List<CompareErrorReport> compareList1 = (List<CompareErrorReport>)request.getAttribute("REPORTLIST");
							CompareErrorReportList list1 =  new 	CompareErrorReportList();
							list1.setList(compareList1);
							
							try {
								JAXBContext context = JAXBContext.newInstance(CompareErrorReportList.class);
								Marshaller m = context.createMarshaller();
								m.marshal(list1, out);
							} catch (JAXBException e) {
								e.printStackTrace();
							}
							break;
							
					/*	case SAL_XLS:
							if (fileId != null && fileId[0] != null){
								throw new Exception("File  uploaded");
							}
							break;*/
						
							
						default:{
							DocumentsDao documentsDao = DocumentsDaoFactory.create();
							Documents documents = documentsDao.findByPrimaryKey(fileId[0]);
							if (fileId != null && fileId.length > 0){
								out.println(String.valueOf(fileId[0]) + "~=~" + documents.getDescriptions());
								logger.info("Saved document successfully with Id: " + fileId[0]);
							}
						}
							break;
					}
				} else{
					longName = true;
					throw new Exception("File not uploaded");
				}
			}
		} catch (Exception e){
			if (longName){
				fileId[0] = -1;
				out.println(String.valueOf(fileId[0]) + "~=~" + "File not uploaded");
				logger.info("File not uploaded");
			} else{
				fileId = new Integer[1];
				fileId[0] = -2;
				out.println(String.valueOf(fileId[0]) + "~=~" + "File Size greater than 4MB");
			}
			e.printStackTrace();
			logger.error(e.getStackTrace());
		}
	}

	public void setValue(Object obj, Object field, Object value) throws IllegalAccessException, InvocationTargetException {
		Method[] methods = obj.getClass().getMethods();
		Object object = value;
		String type = null;
		for (Method method : methods){
			if (method.getName().equalsIgnoreCase("set" + field.toString())){
				Class paramType[] = method.getParameterTypes();
				for (int j = 0; j < paramType.length; j++){
					if (paramType[j].getName().equalsIgnoreCase("int")){
						type = "int";
					} else if (paramType[j].getName().equalsIgnoreCase("java.util.Date")){
						type = "java.util.Date";
					} else if (paramType[j].getName().equalsIgnoreCase("[Ljava.lang.String;")){
						type = "StringArray";
					}
				}
				try{
					if (type != null && type.equals("int")){
						Integer id = new Integer(value.toString());
						method.invoke(obj, id.intValue());
					} else if (type != null && type.equals("java.util.Date")){
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
						Date convertedDate = null;
						try{
							convertedDate = dateFormat.parse(value.toString());
						} catch (ParseException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						method.invoke(obj, convertedDate);
					} else if (type != null && type.equals("StringArray")){
						String str = (String) value;
						// String array[]= new String[]{str};
						Object array = new String[] { str };
						method.invoke(obj, array);
					} else{
						method.invoke(obj, object);
					}
				} catch (Exception e){
					e.printStackTrace();
				}
				break;
			}
		}
	}
	

/*
	private XStream xstream = null;

	public String toXMLString(Object object) {
		return xstream.toXML(object);
	}*/
}
