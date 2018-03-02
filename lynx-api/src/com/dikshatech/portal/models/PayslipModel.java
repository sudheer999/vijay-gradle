package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import com.dikshatech.beans.PayslipBean;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.ModelUtiility;
import com.dikshatech.common.utils.Unzip;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.PayslipDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.DocumentsPk;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.Payslip;
import com.dikshatech.portal.exceptions.DocumentsDaoException;
import com.dikshatech.portal.exceptions.PayslipDaoException;
import com.dikshatech.portal.exceptions.UsersDaoException;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.PayslipDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.jdbc.PayslipDaoImpl;
import com.dikshatech.portal.mail.Attachements;

public class PayslipModel extends ActionMethods {

	private static final Logger	logger	= LoggerUtil.getLogger(PayslipModel.class);

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Payslip payslip = (Payslip) form;
		DocumentsDao documentsDao = DocumentsDaoFactory.create();
		try{
			documentsDao.delete(new DocumentsPk(payslip.getId()));
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		Login login = (Login) request.getSession().getAttribute("login");
		int userid = login.getUserId();
		ActionResult result = new ActionResult();
		PayslipDao payslipDao = PayslipDaoFactory.create();
		try{
			switch (ReceiveTypes.getValue(form.getrType())) {
				case RECEIVEALL:{
					try{
						if (!ModelUtiility.getInstance().getModulePermission(login, 37).isUpload()) break;
						// filtering added by guruath.rokkam
						Calendar cal = Calendar.getInstance();
						int year = cal.get(Calendar.YEAR);
						String wherecls = " AND Month_ = " + (cal.get(Calendar.MONTH) + 1);
						if (form.getSearchyear() != null){
							try{
								year = Integer.parseInt(form.getSearchyear());
							} catch (Exception e){}
						}
						if (form.getMonth() != null){
							try{
								wherecls = " AND Month_ = " + Integer.parseInt(form.getMonth());
							} catch (Exception e){}
						}
						if (form.getSearchName() != null && form.getSearchName().length() > 2){
							if (form.getMonth() == null) wherecls = "";
							try{
								wherecls += " AND (SELECT CONCAT(PF.FIRST_NAME,' ',PF.LAST_NAME)  FROM PROFILE_INFO PF WHERE PF.ID=(SELECT U.PROFILE_ID FROM USERS U WHERE U.EMP_ID=PY.USERID)) LIKE '%" + form.getSearchName() + "%' ";
							} catch (Exception e){}
						}
						Payslip[] payslip = payslipDao.findByDynamicSelect(PayslipDaoImpl.SQL_SELECT + " PY WHERE Year_= ? " + wherecls, new Object[] { year });
						// ended filtering
						PayslipBean[] payslipBean1 = new PayslipBean[payslip.length];
						int k = 0;
						for (com.dikshatech.portal.dto.Payslip payslip3 : payslip){
							PayslipBean payslipBeans = DtoToBeanConverter.DtoToBeanConverter(payslip3);
							payslipBean1[k] = payslipBeans;
							k++;
						}
						DropDown dropDown1 = new DropDown();
						dropDown1.setDropDown(payslipBean1);
						request.setAttribute("actionForm", dropDown1);
					} catch (Exception e){
						e.printStackTrace();
					}
					break;
				}
				case RECEIVE:{
					try{
						// filtering added by guruath.rokkam
						int year = Calendar.getInstance().get(Calendar.YEAR);// default current year.
						if (form.getSearchyear() != null){
							try{
								year = Integer.parseInt(form.getSearchyear());
							} catch (Exception e){}
						}
						com.dikshatech.portal.dto.Payslip[] payslip2 = payslipDao.findByDynamicWhere("UserID = (SELECT EMP_ID FROM USERS WHERE ID = ?) AND Year_= ? ", new Object[] { userid, year });
						// ended filtering
						PayslipBean[] payslipBean = new PayslipBean[payslip2.length];
						int j = 0;
						for (com.dikshatech.portal.dto.Payslip payslip3 : payslip2){
							PayslipBean payslipBeans = DtoToBeanConverter.DtoToBeanConverter(payslip3);
							payslipBean[j] = payslipBeans;
							j++;
						}
						DropDown dropDown2 = new DropDown();
						dropDown2.setDropDown(payslipBean);
						request.setAttribute("actionForm", dropDown2);
					} catch (Exception e){
						e.printStackTrace();
					}
					break;
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Attachements download(PortalForm form) {
		Attachements attachements = new Attachements();
		String seprator = File.separator;
		String path = "Data" + seprator;
		try{
			Payslip payslip = (Payslip) form;
			Login login = (Login) form.getObject();
			DocumentsDao documentsDao = DocumentsDaoFactory.create();
			Documents[] dacDocuments = documentsDao.findWhereIdEquals(payslip.getFileID());
			if (dacDocuments != null && dacDocuments.length == 1){
				PortalData portalData = new PortalData();
				path = portalData.getDirPath();
				if (ModelUtiility.getInstance().getModulePermission(login, 37).isUpload() || Integer.parseInt(dacDocuments[0].getFilename().substring(4, 8)) == ModelUtiility.getInstance().getEmployeeId(login.getUserId())){
					attachements.setFileName(dacDocuments[0].getFilename());
					path = portalData.getfolder(path);
					path = path + seprator + attachements.getFileName();
					attachements.setFilePath(path);
				}
			}
		} catch (DocumentsDaoException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return attachements;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer[] upload(List<FileItem> fileItems, String docType, int id, HttpServletRequest request, String description) {
		boolean statsWith = false;
		Integer fieldsId[] = null;
		Integer res[] = null;
		boolean isalreadyexist = false;
		ArrayList<String> badFormat = new ArrayList<String>();
		ArrayList<Integer> userNotExist = new ArrayList<Integer>();
		for (FileItem fileItem2 : fileItems){
			// logger.info("FileName: " + fileItem2.getName());
			PortalData portalData = new PortalData();
			DocumentTypes dTypes = DocumentTypes.valueOf(docType);
			UsersDao users = UsersDaoFactory.create();
			try{
				Documents documents = new Documents();
				DocumentsDao documentsDao = DocumentsDaoFactory.create();
				String fileName = portalData.saveFile(fileItem2, dTypes, id);
				String filedestinationpath = portalData.getDirPath();
				String temp_path = filedestinationpath + File.separator + "temp";
				OutputStream out = null;
				File tempfolder = new File(temp_path);
				tempfolder.mkdir();
				logger.info(filedestinationpath);
				Unzip unzip = new Unzip();
				String filepath = filedestinationpath + File.separator + fileName;
				//method to unzip the folder
				unzip.unzip(filepath, temp_path, fileName);
				File[] files = tempfolder.listFiles();
				fieldsId = new Integer[files.length];
				InputStream in = null;
				byte[] buf = new byte[1024];
				int len;
				PayslipDao payslipDao = PayslipDaoFactory.create();
				com.dikshatech.portal.dto.Payslip payslipDto = null;
				Collection usersList = users.findAllEmployeeIds();
				for (int i = 0; i < files.length; i++){
					String filename = files[i].getName();
					File f = new File(filedestinationpath + File.separator + filename);
					statsWith = filename.startsWith("DTPL");
					if (statsWith && filename.substring(filename.lastIndexOf(".") + 1).equals("pdf") && filename.length() == 18 && validateUser(filename.substring(4, 8), usersList)){
						if (f.exists()){
							isalreadyexist = true;
							f.delete();
						}
						out = new FileOutputStream(filedestinationpath + File.separator + files[i].getName());
						in = new FileInputStream(temp_path + File.separator + files[i].getName());
						while ((len = in.read(buf)) > 0){
							out.write(buf, 0, len);
						}
						if (!isalreadyexist){
							documents.setFilename(files[i].getName());
							documents.setDoctype(docType);
							documents.setDescriptions(description);
							documents.setId(0);
							Documents alreadysameFile[] = documentsDao.findWhereFilenameEquals(files[i].getName());
							if (alreadysameFile != null && alreadysameFile.length > 0){
								for (Documents doc : alreadysameFile){
									DocumentsPk docsPk = new DocumentsPk();
									docsPk.setId(doc.getId());
									documentsDao.delete(docsPk);
								}
							}
							DocumentsPk documentsPk = documentsDao.insert(documents);
							fieldsId[i] = documentsPk.getId();
							Documents documentDto = documentsDao.findByPrimaryKey(documentsPk.getId());
							payslipDto = new com.dikshatech.portal.dto.Payslip();
							File readingFile = new File(filedestinationpath + File.separator + documentDto.getFilename());
							logger.info(fieldsId[i]);
							if (readingFile.exists()){
								String STR = readingFile.getName();
								String temp = STR.substring(4, STR.length() - 10);
								int userID = Integer.parseInt(temp);
								payslipDto.setFileID(fieldsId[i]);
								payslipDto.setUserID(userID);
								payslipDto.setYear(Integer.parseInt(STR.substring(STR.length() - 10, STR.length() - 6)));
								payslipDto.setMonth(STR.substring(STR.length() - 6, STR.length() - 4));
								payslipDao.insert(payslipDto);
							}
							logger.info("File " + files[i].getName() + "is uploaded successfully");
							files[i].delete();
						}
					} else{
						try{
							if (statsWith && filename.substring(filename.lastIndexOf(".") + 1).equals("pdf") && filename.length() == 18){
								userNotExist.add(Integer.parseInt(filename.substring(4, 8)));
							} else badFormat.add(filename);
							files[i].delete();
						} catch (Exception e){
							logger.error("Bad File Name for payslips:" + filename);
						}
					}
				}
				File filetemp = new File(temp_path);
				if (filetemp.isDirectory()){
					File[] filearray = filetemp.listFiles();
					if (filearray != null && filearray.length > 0){
						for (int i = 0; i < filearray.length; i++){
							filearray[i].delete();
						}
					}
					filetemp.delete();
				}
				try{
					if (in != null && out != null){
						in.close();
						out.close();
					}
				} catch (Exception e){
					e.printStackTrace();
				}
			} catch (FileNotFoundException e){
				e.printStackTrace();
			} catch (DocumentsDaoException e){
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			} catch (PayslipDaoException e){
				e.printStackTrace();
			} catch (UsersDaoException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		res = new Integer[userNotExist.size()];
		for (int j = 0; j < userNotExist.size(); j++)
			res[j] = userNotExist.get(j);
		request.setAttribute("BADFILES", badFormat);
		return res;
	}

	private boolean validateUser(String substring, Collection usersList) {
		if (substring != null && substring.length() > 0) try{
			return usersList.contains(Integer.parseInt(substring) + "");
		} catch (Exception e){
			// TODO: handle exception
		}
		return false;
	}
 
	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
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
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		logger.info(Calendar.getInstance().get(Calendar.MONTH));
	}
}
