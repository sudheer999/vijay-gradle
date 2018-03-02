package com.dikshatech.portal.file.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PropertyLoader;
import com.dikshatech.portal.dao.CandidateDao;
import com.dikshatech.portal.dao.LoginDao;
import com.dikshatech.portal.dao.ProfileInfoDao;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.exceptions.LoginDaoException;
import com.dikshatech.portal.factory.CandidateDaoFactory;
import com.dikshatech.portal.factory.LoginDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;

public class PortalData{

	private static Logger		logger		= LoggerUtil.getLogger(PortalData.class);

	private static final String	modelPkg	= "com.dikshatech.portal.models.";
	public static final String	EDU_DOC		= "EDD_";
	public static final String	EXP_DOC		= "EXD_";
	public static final String	FIN_DOC		= "FIN_";
	public static final String	PAS_DOC		= "PAS_";
	public static final String	OFF_LETTER	= "OFL_";
	public static final String	SAL_RECO	= "SRL_";
	public static final String	INDUCT_DOC	= "IDO_";
	public static final String	PAYSLIP_DOC	= "";
	public static final String	LEAVE_DOC	= "LV_";
	public static final String	CONFIRMATION_LETTER	= "CONFL_";	
	public static final String FBP_DECLARATION="FBP_";

	//Abbreviations for files being uploaded
	public static final String	RESUME_DOC	= "RES_";
	public static final String	TRAVEL_DOC	= "TL_";
	public static final String	REIMBURSEMENT_DOC	= "REIMB_";
	public static final String	ISSUE_TICKET_DOC	= "IT_";
	
	private static final String	seprator	= File.separator;
	private String				path		= "Data" + seprator;
	boolean employee=false;
	private static boolean		pathSet		= false,longName=false;
	private String callerName = "";
	
	public String saveFile(FileItem fileItem, DocumentTypes dTypes, int id) throws FileNotFoundException{
		try{
			throw new Exception("Error");
		} catch (Exception e){
			callerName = e.getStackTrace()[1].getClassName();
		}
		return saveFile(fileItem, dTypes, id,null);
	}
	
	
	public String saveFile(FileItem fileItem, DocumentTypes dTypes, int id, String exten) throws FileNotFoundException{
		boolean flag = false;
		String uniquefileName;
		String fileName = getFileName(dTypes, fileItem);
		Date date = new Date();
		
		if (callerName == null || callerName.equals("")){
			try{
				throw new Exception("Error");
			} catch (Exception e){
				callerName = e.getStackTrace()[1].getClassName();
			}
		}
		if (callerName.equals(modelPkg + "CandidateModel")){
			path += "candidate";
			try{
				int candidateId = id;
				//find out if the folder already exists
				//pattern  : cand_10_vijay_jayaram
				path += seprator;
				StringBuilder candidateFolderName = new StringBuilder(path);
				candidateFolderName.append("cand").append("_").append(candidateId).append("_");
				CandidateDao candidateDao = CandidateDaoFactory.create();
				ProfileInfoDao profileInfoDao = ProfileInfoDaoFactory.create();
				Candidate candidate = candidateDao.findByPrimaryKey(candidateId);
				ProfileInfo candidateProfileInfo = profileInfoDao.findByPrimaryKey(candidate.getProfileId());
				candidateFolderName.append(candidateProfileInfo.getFirstName()).append("_").append(candidateProfileInfo.getLastName());			
				
				File candidateFolder = new File(candidateFolderName.toString());
				if(!(candidateFolder.exists() && candidateFolder.isDirectory())){
					candidateFolder.mkdir();
				}
				
				path = candidateFolder.getPath();
				uniquefileName = fileName;
				
			}catch(Exception ex){
				uniquefileName = null;
			}			
			
		}else if (callerName.equals(modelPkg + "UserModel")){
			//path += "employee";
			 path += "candidate";
			 employee=true;
			 uniquefileName=getDBFileName(fileName,id);
			
		}
	  else if (callerName.equals(modelPkg + "ResourceReqModel")){
		 path += "candidate";
		 employee=true;
		String DBFilename = fileName.substring(0,fileName.lastIndexOf("."));
			String extension=fileName.substring(fileName.lastIndexOf(".")+1);
			uniquefileName=DBFilename + "~" + "Resume_Resource."+extension;
	   }else if (callerName.equals(modelPkg + "PayslipModel")){
			employee=true;
			path += "payslip";
			uniquefileName = getDBFileName(fileName, id);
		}else if (callerName.equals(modelPkg + "LeaveModel")){
			path += "leave";
			uniquefileName = LEAVE_DOC+(fileName.replace(fileName.substring(0, fileName.lastIndexOf(".")),fileName.substring(0, fileName.lastIndexOf("."))+"_"+exten));
		}else if (callerName.equals(modelPkg + "NewsModel")){
			path += "news";
			uniquefileName = fileName;
		}else if (callerName.equals(modelPkg + "ReimbursementModel")){
			employee=true;
			path += "reimbursement";
			uniquefileName = REIMBURSEMENT_DOC+date.getTime()+"_"+getDBFileName(fileName, id);
		}else if (callerName.equals(modelPkg + "ItModel")){
			employee=true;
			path += "itsupport";
			//uniquefileName = getDBFileName(fileName, id);
			uniquefileName = ISSUE_TICKET_DOC+date.getTime()+"_"+fileName;
		}else if (callerName.equals(modelPkg + "TravelModel")){
			path += "travel";
			uniquefileName = TRAVEL_DOC+date.getTime()+"_"+fileName;
		}else if (callerName.equals(modelPkg + "ResumeManagementModel")){
			path += "resumemanagement";
			uniquefileName = RESUME_DOC+date.getTime()+"_"+fileName;
			//(fileName.replace(fileName.substring(0, fileName.lastIndexOf(".")),fileName.substring(0, fileName.lastIndexOf("."))+"_"+exten));
			//uniquefileName = fileName+"_"+date.getTime();
		}else{
			uniquefileName = fileName;
		}		
		String fPath = getDirPath();
		if (!fileItem.getName().equals("")){
			try{
				// File writeFile = new File(fPath, fileName);
				//if file name length is greater than 100 characters dont allow ro upload
				if(uniquefileName.length()>100){
					longName=true;
					throw new Exception("File name exceed's 100 characters, Cannot upload File");
				}
				File fileDir=new File(fPath);
				// create directory if not exist.
				if(!fileDir.exists())fileDir.mkdir();
				File writeFile = new File(fileDir, uniquefileName);
				long fileNo=1;
				while (exten!=null && writeFile.exists()){
					if (fileNo == 1) uniquefileName = (uniquefileName.replace(uniquefileName.substring(0, uniquefileName.lastIndexOf(".")), uniquefileName.substring(0, uniquefileName.lastIndexOf(".")) + "_" + fileNo));
					else uniquefileName = (uniquefileName.replace(uniquefileName.substring(0, uniquefileName.lastIndexOf("_") + 2), uniquefileName.substring(0, uniquefileName.lastIndexOf("_")) + "_" + fileNo));
					writeFile = new File(fileDir, uniquefileName);
				}
				writeFile.createNewFile(); 
				flag = writeFile(fileItem, writeFile);
			}
			catch (Exception e)
			{
				if(longName){
					return null;
				}				
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		if (flag)
		{
			
			return uniquefileName;
		}
		return null;

	}
	public String getfolder(String path)
	{
		String callerName = "";
		try
		{
			throw new Exception("Error");
		} catch (Exception e)
		{
			callerName = e.getStackTrace()[1].getClassName();
		}

		if (callerName.equals(modelPkg + "CandidateModel"))
		{
			path += "candidate";
		}
		else if(callerName.equals(modelPkg + "LeaveModel"))
		{
			path += "leave";
		}
		else if(callerName.equals(modelPkg + "PayslipModel"))
		{
			path += "payslip";
		}
		else if (callerName.equals(modelPkg + "UserModel"))
		{
//			path += "employee"; //need path to be same foldeer candidate
			path+="candidate";
		}
		else if (callerName.equals(modelPkg + "ReimbursementModel"))
		{
			path += "reimbursement";
		}
		else if (callerName.equals(modelPkg + "ItModel"))
		{
			path += "itsupport";
		}
		else if (callerName.equals(modelPkg + "TravelModel"))
		{
			path += "travel";
		}
		else if (callerName.equals(modelPkg + "NewsModel"))
		{
			path += "news";
		}
		else if (callerName.equals(modelPkg + "AppraisalModel"))
		{
			path += "appraisals";
		}
		else if (callerName.equals(modelPkg + "SodexoModel"))
		{
			path += "sodexo";
		}
		else if (callerName.equals(modelPkg + "ResumeManagementModel"))
		{
			path += "resumemanagement";
		}
		else if (callerName.equals(modelPkg + "ResourceReqModel"))
		{
			path += "candidate"; //need path to be same foldeer candidate
		}
		return path;

	}

	private String getFileName(DocumentTypes dTypes, FileItem fileItem)
	{
		String fileName = "";
		if (!fileItem.getName().equals(""))
		{
			switch (dTypes)
			{
			case EDUCATIONAL:
				fileName = EDU_DOC + fileItem.getName();
				break;
			case EXPERIENCE:
				fileName = EXP_DOC + fileItem.getName();
				break;
			case FINANCIAL:
				fileName = FIN_DOC + fileItem.getName();
				break;
			case PASSPORT:
				fileName = PAS_DOC + fileItem.getName();
				break;
			case OFFER_LETTER:
				fileName = OFF_LETTER + fileItem.getName();
				break;
			case INDUCTION:
				fileName = INDUCT_DOC + fileItem.getName();
				break;
			case REIMBURSEMENT:
				fileName = fileItem.getName();
				break;
			case ITSUPPORT:
				fileName = fileItem.getName();
				break;
			case PROPOSED_RESOURCE:
				fileName = fileItem.getName();
				break;
			default:
				fileName = fileItem.getName();
				break;
			}

		}
		return fileName;
	}

	private String getDBFileName(String filename, int id)
	{
		String DBFilename = filename;

		LoginDao loginDao = LoginDaoFactory.create();
		Login login[];
		try
		{
			 if(!filename.equals("")){
					DBFilename = filename.substring(0,filename.lastIndexOf("."));
					String extension=filename.substring(filename.lastIndexOf(".")+1);
					if(employee){
						login = loginDao.findByDynamicWhere("USER_ID = ? ",new Object[]{id} );
					}else{
					 login = loginDao.findByDynamicWhere("CANDIDATE_ID = ? ",new Object[]{id} );
					}
					if(login.length>0){
						DBFilename=DBFilename + "~" + login[0].getUserName().split("@")[0]+"."+extension;
						logger.info("FileName for DB: " + DBFilename);
					}
					}
		}
		catch (LoginDaoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DBFilename;
	}

	public String getDirPath()
	{
		return PropertyLoader.getEnvVariable() + seprator + path;
	}

	public boolean writeFile(FileItem fileItem, File writeFile)
			throws Exception
	{
		boolean result = false;
		fileItem.write(writeFile);
		result = true;

		return result;
	}

}

