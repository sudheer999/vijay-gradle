package com.dikshatech.portal.models;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.CandidateDao;
import com.dikshatech.portal.dao.DocumentMappingDao;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.PassportInfoDao;
import com.dikshatech.portal.dao.UsersDao;
import com.dikshatech.portal.dao.VisaInfoDao;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.DocumentMapping;
import com.dikshatech.portal.dto.DocumentMappingPk;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.PassportInfo;
import com.dikshatech.portal.dto.PassportInfoPk;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.dto.UsersPk;
import com.dikshatech.portal.dto.VisaInfo;
import com.dikshatech.portal.factory.CandidateDaoFactory;
import com.dikshatech.portal.factory.DocumentMappingDaoFactory;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.PassportInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.factory.VisaInfoDaoFactory;
import com.dikshatech.portal.forms.PortalForm;

import java.util.Arrays;

public class PassportInfoModel extends ActionMethods {

	private static Logger	logger	= LoggerUtil.getLogger(PassportInfoModel.class);

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		PassportInfo passportInfo = (PassportInfo) form;
		PassportInfoDao PassportInfoDao = PassportInfoDaoFactory.create();
		PassportInfoPk passportInfoPk = new PassportInfoPk();
		passportInfoPk.setId(passportInfo.getId());
		try{
			PassportInfoDao.delete(passportInfoPk);
		} catch (Exception e){
			logger.info("Failed to delete Profile Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		PassportInfoDao PassportInfoDao = PassportInfoDaoFactory.create();
		ActionResult result = new ActionResult();
		PassportInfoPk passportInfoPk = new PassportInfoPk();
		Candidate candidate = new Candidate();
		CandidateDao c = CandidateDaoFactory.create();
		DocumentMapping documentMapping[] = null;
		DocumentsDao documentsDao = DocumentsDaoFactory.create();
		DocumentMappingDao mappingDao = DocumentMappingDaoFactory.create();
		try{
			switch (ReceiveTypes.getValue(form.getrType())) {
				case RECEIVE:
					PassportInfo passportInfo = (PassportInfo) form;
					PassportInfo passportinfoDto = new PassportInfo();
					if (passportInfo.getCandidateId() > 0){
						candidate = c.findByPrimaryKey(passportInfo.getCandidateId());
						passportInfoPk.setId(candidate.getPassportId());
						passportinfoDto.setCandidateId(candidate.getId());
						documentMapping = mappingDao.findWherePassportIdEquals(candidate.getPassportId());
					} else if (passportInfo.getUserId() > 0){
						Users user = new Users();
						UsersDao usersDao = UsersDaoFactory.create();
						user = usersDao.findByPrimaryKey(passportInfo.getUserId());
						passportInfoPk.setId(user.getPassportId());
						documentMapping = mappingDao.findWherePassportIdEquals(passportInfoPk.getId());
					}
					passportinfoDto = PassportInfoDao.findByPrimaryKey(passportInfoPk.getId());
					/**
					 * fetch file informations
					 */
					if (documentMapping != null && documentMapping.length > 0){
						Documents[] documentArr = new Documents[documentMapping.length];
						int i = 0;
						for (DocumentMapping tempDocMap : documentMapping){
							Documents newDocument = new Documents();
							Documents documentsFromDb = documentsDao.findByPrimaryKey(tempDocMap.getDocumentId());
							newDocument.setId(documentsFromDb.getId());
							String filename = documentsFromDb.getFilename();
							String extension = filename.substring(filename.lastIndexOf(".") + 1);
							String fileName1 = filename.substring(4, filename.lastIndexOf("~"));
							newDocument.setFilename(fileName1 + "." + extension);
							newDocument.setDoctype(documentsFromDb.getDoctype());
							newDocument.setDescriptions(documentsFromDb.getDescriptions());
							documentArr[i] = newDocument;
							i++;
						}
						passportinfoDto.setDocumentArr(documentArr);
					}
					if (passportinfoDto != null && passportinfoDto.getId() > 0){
						VisaInfo[] visaDetails = VisaInfoDaoFactory.create().findWherePassportIdEquals(passportinfoDto.getId());
						if (visaDetails != null && visaDetails.length > 0){
							passportinfoDto.setVisaInfo(visaDetails);
						}
					}
					request.setAttribute("actionForm", passportinfoDto);
					break;
			}
		} catch (Exception e){
			logger.info("Failed to receive single Passport Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		PassportInfo passportInfo = (PassportInfo) form;
		PassportInfoDao PassportInfoDao = PassportInfoDaoFactory.create();
		PassportInfoPk passportInfoPk = new PassportInfoPk();
		passportInfo.setId(passportInfo.getId() != 0 ? 0 : 0);
		CandidateModel candidateModel = new CandidateModel();
		Candidate candidate = new Candidate();
		CandidateDao c = CandidateDaoFactory.create();
		try{
			passportInfo.setModifiedBy(login.getUserId());
			passportInfoPk = PassportInfoDao.insert(passportInfo);
			passportInfo.setId(passportInfoPk.getId());
			addVisaDetails(passportInfo.getVisaInfo(), passportInfoPk.getId());
			/**
			 * Insert in candidate table the relevant id
			 */
			if (passportInfo.getCandidateId() > 0){
				candidate = c.findByPrimaryKey(passportInfo.getCandidateId());
				candidate.setPassportId(passportInfo.getId());
				candidate.setuType("UPDATECANDIDATE");
				candidateModel.update(candidate, request);
				request.setAttribute("Pk", passportInfoPk.getId());
			}
			/**
			 * add in User Table
			 */
			if (passportInfo.getUserId() != null && passportInfo.getUserId() > 0){
				UsersPk usersPk = new UsersPk();
				UsersDao usersDao = UsersDaoFactory.create();
				Users users = usersDao.findByPrimaryKey(passportInfo.getUserId());
				users.setPassportId(passportInfoPk.getId());
				usersPk.setId(users.getId());
				usersDao.update(usersPk, users);
			}
			/**
			 * Insert in document mapping table
			 */
			if (passportInfo.getFiles() != null){
				String[] files = passportInfo.getFiles().split("~");
				DocumentMapping documentMapping = new DocumentMapping();
				DocumentMappingDao mappingDao = DocumentMappingDaoFactory.create();
				if (files.length > 0) for (String fileIds : files){
					if (Integer.parseInt(fileIds) > 0){
						documentMapping.setDocumentId(Integer.parseInt(fileIds));
						documentMapping.setPassportId(passportInfoPk.getId());
						documentMapping.setId(documentMapping.getId() != 0 ? 0 : 0);
						mappingDao.insert(documentMapping);
					}
				}
			}
		} catch (Exception e){
			logger.info("Failed to save Profile Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		PassportInfo passportInfo = (PassportInfo) form;
		PassportInfoDao PassportInfoDao = PassportInfoDaoFactory.create();
		PassportInfoPk passportInfoPk = new PassportInfoPk();
		Candidate candidate = new Candidate();
		CandidateDao c = CandidateDaoFactory.create();
		String files[] = null;
		boolean contains = false;
		DocumentMapping documentMapping = new DocumentMapping();
		DocumentMappingDao mappingDao = DocumentMappingDaoFactory.create();
		DocumentMappingPk docMapPk = new DocumentMappingPk();
		try{
			if (passportInfo.getCandidateId() > 0){
				candidate = c.findByPrimaryKey(passportInfo.getCandidateId());
				passportInfoPk.setId(candidate.getPassportId());
				passportInfo.setId(candidate.getPassportId());
			} else if (passportInfo.getId() > 0){
				passportInfoPk.setId(passportInfo.getId());
			}
			passportInfo.setModifiedBy(login.getUserId());
			PassportInfoDao.update(passportInfoPk, passportInfo);
			addVisaDetails(passportInfo.getVisaInfo(), passportInfoPk.getId());
			/**
			 * Insert in DocumentMapping Table
			 */
			DocumentMapping documentMap[] = mappingDao.findWherePassportIdEquals(passportInfoPk.getId());
			if (passportInfo.getFiles() != null){
				files = passportInfo.getFiles().split("~");
				if (files != null && files.length > 0){
					Integer field[] = new Integer[files.length];
					for (int l = 0; l < files.length; l++){
						field[l] = Integer.parseInt(files[l]);
					}
					List<?> containArray = Arrays.asList(field);
					for (DocumentMapping doc : documentMap){
						contains = containArray.contains(doc.getDocumentId());
						if (!contains){
							DocumentMappingPk documentMappingPk = new DocumentMappingPk();
							documentMappingPk.setId(doc.getId());
							mappingDao.delete(documentMappingPk);
						}
					}
					if (field != null && field.length > 0){
						DocumentMapping docMap[] = new DocumentMapping[field.length];
						for (int i = 0; i < field.length; i++){
							documentMapping.setPassportId(passportInfoPk.getId());
							if (field != null && field[i] != 0){
								if (field[i] != 0){
									documentMapping.setDocumentId(field[i]);
									/**
									 * this query and for loop is for checking if already exists and deleting and reinserting new entries
									 */
									docMap = mappingDao.findByDynamicWhere("DOCUMENT_ID = ? AND PASSPORT_ID = ?", new Object[] { field[i], passportInfoPk.getId() });
									if (docMap != null && docMap.length > 0){
										for (int k = 0; k < docMap.length; k++){
											docMapPk.setId(docMap[k].getId());
											mappingDao.delete(docMapPk);
										}
									}
									documentMapping.setId(documentMapping.getId() != 0 ? 0 : 0);
									mappingDao.insert(documentMapping);
								}//field 0
							}
						}
					}
				}//if files not null
			}
		} catch (Exception e){
			logger.info("Failed to update Profile Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}
		return result;
	}

	private void addVisaDetails(Object[] visaInfo, int id) {
		JDBCUtiility.getInstance().update("DELETE FROM VISA_INFO WHERE PASSPORT_ID = ?", new Object[] { id });
		if (visaInfo != null && visaInfo.length > 0){
			VisaInfoDao visaInfoDao = VisaInfoDaoFactory.create();
			for (Object visaObj : visaInfo){
				try{
					if (visaObj == null) continue;
					String[] visaDetails = ((String) visaObj).split("~=~");
					VisaInfo visa = new VisaInfo();
					visa.setPassportId(id);
					visa.setVisaType(visaDetails[0]);
					visa.setCountry(visaDetails[1]);
					visa.setVisaFrom(PortalUtility.StringToDateDB(visaDetails[2]));
					visa.setVisaTo(PortalUtility.StringToDateDB(visaDetails[3]));
					visaInfoDao.insert(visa);
				} catch (Exception e){
					logger.error("unable to read some data for visa details", e);
				}
			}
		}
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
