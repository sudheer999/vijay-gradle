package com.dikshatech.portal.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.ExperienceBean;
import com.dikshatech.beans.ExperienceDetailBean;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DocumentMappingDao;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.ExperienceInfoDao;
import com.dikshatech.portal.dto.Candidate;
import com.dikshatech.portal.dto.DocumentMapping;
import com.dikshatech.portal.dto.DocumentMappingPk;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.ExperienceInfo;
import com.dikshatech.portal.dto.ExperienceInfoPk;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.dto.ProfileInfo;
import com.dikshatech.portal.dto.Users;
import com.dikshatech.portal.exceptions.DocumentMappingDaoException;
import com.dikshatech.portal.exceptions.ExperienceInfoDaoException;
import com.dikshatech.portal.factory.CandidateDaoFactory;
import com.dikshatech.portal.factory.DocumentMappingDaoFactory;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.ExperienceInfoDaoFactory;
import com.dikshatech.portal.factory.ProfileInfoDaoFactory;
import com.dikshatech.portal.factory.UsersDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;

public class ExperienceInfoModel extends ActionMethods {

	private static Logger	logger	= LoggerUtil.getLogger(ExperienceInfoModel.class);

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		ExperienceInfo experienceInfo = (ExperienceInfo) form;
		ExperienceInfoDao experienceInfoDao = ExperienceInfoDaoFactory.create();
		ExperienceInfoPk experienceinfopk = new ExperienceInfoPk();
		experienceinfopk.setId(experienceInfo.getId());
		try{
			experienceInfoDao.delete(experienceinfopk);
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
		ActionResult result = new ActionResult();
		ExperienceInfo experienceInfo = (ExperienceInfo) form;
		ExperienceInfoDao experienceInfoDao = ExperienceInfoDaoFactory.create();
		DocumentMapping documentMapping[];
		DocumentsDao documentsDao = DocumentsDaoFactory.create();
		experienceInfo.setUserId(experienceInfo.getId() > 0 ? experienceInfo.getId() : 0);
		try{
			DropDown dropDown = new DropDown();
			if (experienceInfo.getCandidateId() > 0 || experienceInfo.getUserId() > 0){
				ExperienceInfo experienceinfo2[] = null;
				ProfileInfo profileInfo = null, profileInfos[] = null;
				Users user = UsersDaoFactory.create().findByPrimaryKey(experienceInfo.getUserId());
				if (experienceInfo.getCandidateId() > 0){
					Candidate candidate = CandidateDaoFactory.create().findByPrimaryKey(experienceInfo.getCandidateId());
					if (candidate.getExperienceId() == -1){
						dropDown.setCount(1);
					}
					experienceinfo2 = experienceInfoDao.findWhereCandidateIdEquals(experienceInfo.getCandidateId());
					profileInfos = ProfileInfoDaoFactory.create().findByDynamicSelect("SELECT P.* FROM PROFILE_INFO P JOIN CANDIDATE C ON C.ID=? AND C.PROFILE_ID=P.ID", new Object[] { experienceInfo.getCandidateId() });
					if (profileInfos != null && profileInfos.length == 1) profileInfo = profileInfos[0];
				} else if (experienceInfo.getUserId() > 0){
					if (user.getExperienceId() == -1){
						dropDown.setCount(1);
					}
					experienceinfo2 = experienceInfoDao.findWhereUserIdEquals(experienceInfo.getUserId());
					profileInfos = ProfileInfoDaoFactory.create().findByDynamicSelect("SELECT P.* FROM PROFILE_INFO P JOIN USERS U ON U.ID=? AND U.PROFILE_ID=P.ID", new Object[] { experienceInfo.getUserId() });
					if (profileInfos != null && profileInfos.length == 1) profileInfo = profileInfos[0];
				}
				String prevExp = "0-0-0";
				String currExp = "0-0-0";
				String totalExp = "0-0-0";
				if (dropDown.getCount() != 1){
					ExperienceBean[] edu = new ExperienceBean[experienceinfo2.length];
				
					DocumentMappingDao mappingDao = DocumentMappingDaoFactory.create();
					for (int i = 0; i < experienceinfo2.length; i++){
						ExperienceBean expBean = DtoToBeanConverter.DtoToBeanConverter(experienceinfo2[i]);
						prevExp = addExperiance(expBean.getDateJoining(), expBean.getDateRelieving(), prevExp);
						edu[i] = expBean;
					}
					int k = 0;
					for (ExperienceBean exBean : edu){
						documentMapping = mappingDao.findWhereExperienceIdEquals(exBean.getId());
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
								edu[k].setDocumentArr(documentArr);
							}
						}// if documents exist for that row
						k++;
					}
					List<Documents> list = new ArrayList<Documents>();
					//Integer[] backGroudDocumentMapping = mappingDao.findBackGroundVerivicationId( experienceInfo.getUserId());
					Integer[] backGroudDocumentMapping = mappingDao.findBackGroundVerivicationId(experienceInfo.getUserId());

					DocumentMapping backGroundDocumentMapping[];
					//int j=0;
					for (Integer bGC : backGroudDocumentMapping){
						backGroundDocumentMapping = mappingDao.findWhereBackGroudVeriId(bGC);
						if (backGroundDocumentMapping != null && backGroundDocumentMapping.length > 0){
							for (DocumentMapping tempDocMap : backGroundDocumentMapping){
								Documents newBackGroundDocument = new Documents();
								Documents documentsFromDb = documentsDao.findByPrimaryKey(tempDocMap.getDocumentId());
								newBackGroundDocument.setId(documentsFromDb.getId());
								String filename = documentsFromDb.getFilename();
								String extension = filename.substring(filename.lastIndexOf(".") + 1);
								String fileName1 = filename.substring(4, filename.lastIndexOf("~"));
								newBackGroundDocument.setFilename(fileName1 + "." + extension);
								newBackGroundDocument.setDoctype(documentsFromDb.getDoctype());
								newBackGroundDocument.setDescriptions(documentsFromDb.getDescriptions());
								newBackGroundDocument.setBackGroundVerificationDocId(bGC);
								/*documentVerification[l] = newBackGroundDocument;
								 * 
								l++;*/
								
								list.add(newBackGroundDocument);
								//edu[j].setBackGroundVerification(list);
							}
						}// if documents exist for that row
						//j++;
					}
					list.toArray(new Documents[list.size()]);
					
					dropDown.setObject(list);
					dropDown.setDropDown(edu);
				}
				if (profileInfo != null){
					currExp = PortalUtility.getDuration(profileInfo.getDateOfJoining(), (user.getStatus() == 0) ? new Date() : (profileInfo.getDateOfSeperation() == null) ? new Date() : profileInfo.getDateOfSeperation());
				}
				totalExp = addExperiance(currExp, prevExp);
				ExperienceDetailBean detailBean = new ExperienceDetailBean(formatExperience(currExp), formatExperience(prevExp), formatExperience(totalExp));
				detailBean.setIsFresher(dropDown.getCount() + "");
			
				Documents documentsFromDb = documentsDao.findByUserId(experienceInfo.getUserId());
				if(documentsFromDb!=null){
				detailBean.setFileId(documentsFromDb.getId());
				detailBean.setFileDescriptions(documentsFromDb.getDescriptions());
				detailBean.setFileName(documentsFromDb.getFilename());
				}
				dropDown.setDetail(detailBean);
			}
			request.setAttribute("actionForm", dropDown);
		} catch (Exception e){
			logger.info("Failed to receive single Education Info ");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author gurunath.rokkam
	 * @param userId
	 * @return Returns experiance details in the format of string array [ current experiance, previous experiance, total experiance]
	 */
	public String[] getExperianceDetails(int userId) {
		try{
			ExperienceInfo[] experienceinfos = ExperienceInfoDaoFactory.create().findWhereUserIdEquals(userId);
			ProfileInfo profileInfo = ProfileInfoDaoFactory.create().findByDynamicSelect("SELECT P.* FROM PROFILE_INFO P JOIN USERS U ON U.ID=? AND U.PROFILE_ID=P.ID", new Object[] { userId })[0];
			String prevExp = "0-0-0";
			String currExp = PortalUtility.getDuration(profileInfo.getDateOfJoining(), new Date());
			String totalExp = "0-0-0";
			for (ExperienceInfo info : experienceinfos){
				prevExp = addExperiance(info.getDateJoining(), info.getDateRelieving(), prevExp);
			}
			totalExp = addExperiance(currExp, prevExp);
			return new String[] { currExp, prevExp, totalExp };
		} catch (Exception e){
			e.printStackTrace();
		}
		return new String[] { "0-0-0", "0-0-0", "0-0-0" };
	}

	public static String formatExperience(String experience) {
		return formatExperience(experience, true);
	}

	public static String formatExperience(String experience, boolean includeDays) {
		if (experience != null){
			String exp[] = experience.split("-");
			if (exp.length == 3){ return exp[0] + " years " + exp[1] + " months " + (includeDays ? exp[2] + " days." : ""); }
		}
		return experience;
	}

	String addExperiance(String currExp, String prevExp) {
		String totalExp = "0-0-0";
		if (currExp != null && prevExp != null){
			String currExpArr[] = currExp.split("-");
			String ttlExpArr[] = prevExp.split("-");
			if (currExpArr.length == 3 && ttlExpArr.length == 3){
				int days = Integer.parseInt(ttlExpArr[2]) + Integer.parseInt(currExpArr[2]);
				int months = Integer.parseInt(ttlExpArr[1]) + Integer.parseInt(currExpArr[1]) + (days / 30);
				ttlExpArr[2] = (days % 30) + "";
				ttlExpArr[0] = (Integer.parseInt(ttlExpArr[0]) + Integer.parseInt(currExpArr[0]) + (months / 12)) + "";
				ttlExpArr[1] = (months % 12) + "";
				totalExp = ttlExpArr[0] + "-" + ttlExpArr[1] + "-" + ttlExpArr[2];
			}
		}
		return totalExp;
	}

	public String addExperiance(Date dateJoining, Date dateRelieving, String totalExp) {
		String currentExp = PortalUtility.getDuration(dateJoining, dateRelieving);
		return addExperiance(currentExp, totalExp);
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		ExperienceInfo experienceInfo = (ExperienceInfo) form;
		if (experienceInfo.getId() > 0 && (experienceInfo.getIsFresher() + "").equals("1")){
			JDBCUtiility.getInstance().update("UPDATE USERS SET EXPERIENCE_ID=-1 WHERE ID=?", new Object[] { experienceInfo.getId() });
			JDBCUtiility.getInstance().update("DELETE FROM EXPERIENCE_INFO WHERE USER_ID=?", new Object[] { experienceInfo.getId() });
			return result;
		} else if (experienceInfo.getCandidateId() > 0 && (experienceInfo.getIsFresher() + "").equals("1")){
			JDBCUtiility.getInstance().update("UPDATE CANDIDATE SET EXPERIENCE_ID=-1 WHERE ID=?", new Object[] { experienceInfo.getCandidateId() });
			JDBCUtiility.getInstance().update("DELETE FROM EXPERIENCE_INFO WHERE CANDIDATE_ID=?", new Object[] { experienceInfo.getCandidateId() });
			return result;
		}
		experienceInfo.setUserId(experienceInfo.getId() > 0 ? experienceInfo.getId() : 0);
		experienceInfo.setId(0);
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		String[] primaryKeys = new String[experienceInfo.getFields().length];
		String fields[] = experienceInfo.getFields();
		ExperienceInfoDao experienceinfodao = ExperienceInfoDaoFactory.create();
		ExperienceInfoPk experienceinfoPk = new ExperienceInfoPk();
		//Integer[] rowid = null;
		try{
			int j = 0;
			String rowfields[];
			for (String rows : fields){
				rowfields = rows.split(",");
				//rowid = new Integer[rowfields.length];
				for (String eduInfo : rowfields){
					ExperienceInfo info = new ExperienceInfo();
					String[] colfields = eduInfo.split("~=~");
					for (int i = 0; i < colfields.length; i++){
						if (colfields[i].split("=")[0].equals("company")){
							info.setCompany(colfields[i].split("=")[1]);
						} else if (colfields[i].split("=")[0].equals("currentEmp")){
							info.setCurrentEmp(Short.parseShort(colfields[i].split("=")[1]));
						} else if (colfields[i].split("=")[0].equals("dateJoining")){
							info.setDateJoining(PortalUtility.StringToDate(colfields[i].split("=")[1]));
						} else if (colfields[i].split("=")[0].equals("dateRelieving")){
							info.setDateRelieving(PortalUtility.StringToDate(colfields[i].split("=")[1]));
						} else if (colfields[i].split("=")[0].equals("joiningDesignation")){
							info.setJoiningDesignation((colfields[i].split("=")[1]));
						} else if (colfields[i].split("=")[0].equals("leavingDesignation")){
							info.setLeavingDesignation(colfields[i].split("=")[1]);
						} else if (colfields[i].split("=")[0].equals("rptMgrName")){
							info.setRptMgrName(colfields[i].split("=")[1]);
						} else if (colfields[i].split("=")[0].equals("hrName")){
							info.setHrName(colfields[i].split("=")[1]);
						} else if (colfields[i].split("=")[0].equals("employmentType")){
							info.setEmploymentType(colfields[i].split("=")[1]);
						} else if (colfields[i].split("=")[0].equals("empCode")){
							info.setEmpCode(colfields[i].split("=")[1]);
						} else if (colfields[i].split("=")[0].equals("leavingReason")){
							info.setLeavingReason(colfields[i].split("=")[1]);
						} else if (colfields[i].split("=")[0].equals("files")){
							info.setFiles(colfields[i].split("=")[1]);
						}
					}// end of setting column's end of for
					if (!experienceInfo.isUserIdNull() && experienceInfo.getUserId() > 0){
						info.setUserId(experienceInfo.getUserId() > 0 ? experienceInfo.getUserId() : 0);
					} else{
						info.setCandidateId(experienceInfo.getCandidateId());
					}
					info.setModifiedBy(login.getUserId());
					experienceinfoPk = experienceinfodao.insert(info);
					//					rowid[j] = experienceinfoPk.getId();
					primaryKeys[j] = Integer.toString(experienceinfoPk.getId());
					if (info.getFiles() != null){
						map.put(experienceinfoPk.getId(), info.getFiles());
					}
					j++;
				}
			}
			experienceInfo.setFields(primaryKeys);
			/**
			 * Insert in document mapping table
			 */
			String strfile = null;
			String files[] = null;
			// int field[]=(int[]) request.getSession().getAttribute("fileId");
			DocumentMapping documentMapping = new DocumentMapping();
			DocumentMappingDao mappingDao = DocumentMappingDaoFactory.create();
			Set<Integer> rowKey = map.keySet();
			Integer key;
			if (!rowKey.isEmpty()){
				Iterator<Integer> it = rowKey.iterator();
				while (it.hasNext()){
					key = (Integer) it.next();
					if (map.get(key) != null){
						strfile = map.get(key);
						files = strfile.split("~");
						Integer field[] = new Integer[files.length];
						for (int l = 0; l < files.length; l++){
							field[l] = Integer.parseInt(files[l]);
						}
						if (field.length > 0){
							for (int i = 0; i < field.length; i++){
								documentMapping.setExperienceId(key);
								if (field != null && field[i] != 0){
									documentMapping.setDocumentId(field[i]);
									documentMapping.setId(documentMapping.getId() != 0 ? 0 : 0);
									mappingDao.insert(documentMapping);
								}
							}
						}
					}// if loop to map if upload doc exist
				}// it.next
			}// row key for ends
			
			String[] data=experienceInfo.getBackGroundVeri();
			if(data!=null){
				for(String text:experienceInfo.getBackGroundVeri()){
					ExperienceInfo exp=DtoToBeanConverter.backGroundVerificationDocToBeanConverter(text);
					int filess=exp.getVerificationFileId();
					mappingDao.updateBackGroundVerivication(experienceInfo.getUserId(),filess);
					
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
		ExperienceInfo experienceInfo = (ExperienceInfo) form;
		DocumentMapping documentMapping = new DocumentMapping();
		DocumentMappingPk docMapPk = new DocumentMappingPk();
		DocumentMappingDao mappingDao = DocumentMappingDaoFactory.create();
		ExperienceInfoDao experienceInfoDao = ExperienceInfoDaoFactory.create();
		ExperienceInfo expDBArr[] = null;
		List<Integer> newList = new ArrayList<Integer>();
		boolean contains = false;
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		switch (UpdateTypes.getValue(form.getuType())) {
			case UPDATE:
				try{
					if (experienceInfo.getId() > 0 && (experienceInfo.getIsFresher() + "").equals("1")){
						JDBCUtiility.getInstance().update("UPDATE USERS SET EXPERIENCE_ID=-1 WHERE ID=?", new Object[] { experienceInfo.getId() });
						JDBCUtiility.getInstance().update("DELETE FROM EXPERIENCE_INFO WHERE USER_ID=?", new Object[] { experienceInfo.getId() });
						return result;
					} else if (experienceInfo.getCandidateId() > 0 && (experienceInfo.getIsFresher() + "").equals("1")){
						JDBCUtiility.getInstance().update("UPDATE CANDIDATE SET EXPERIENCE_ID=-1 WHERE ID=?", new Object[] { experienceInfo.getCandidateId() });
						JDBCUtiility.getInstance().update("DELETE FROM EXPERIENCE_INFO WHERE CANDIDATE_ID=?", new Object[] { experienceInfo.getCandidateId() });
						return result;
					}
					String[] primaryKeys = new String[experienceInfo.getFields().length];
					experienceInfo.setUserId(experienceInfo.getId() > 0 ? experienceInfo.getId() : 0);
					if (experienceInfo.getUserId() > 0){
						JDBCUtiility.getInstance().update("UPDATE USERS SET EXPERIENCE_ID=0 WHERE ID=?", new Object[] { experienceInfo.getId() });
						expDBArr = experienceInfoDao.findWhereUserIdEquals(experienceInfo.getUserId());
					} else if (experienceInfo.getCandidateId() > 0){
						expDBArr = experienceInfoDao.findWhereCandidateIdEquals(experienceInfo.getCandidateId());
						JDBCUtiility.getInstance().update("UPDATE CANDIDATE SET EXPERIENCE_ID=0 WHERE ID=?", new Object[] { experienceInfo.getCandidateId() });
					}
					for (ExperienceInfo exp : populateDtos(experienceInfo.getFields())){
						newList.add(exp.getId());
					}
					/**
					 * check if id exists if not then delete that row newList is new
					 * list ui sent,expDBArr existing in db
					 */
					for (int i = 0; i < expDBArr.length; i++){
						if (!(newList.contains(expDBArr[i].getId()))){
							ExperienceInfoPk expDeletePk = new ExperienceInfoPk();
							expDeletePk.setId(expDBArr[i].getId());
							experienceInfoDao.delete(expDeletePk);
						}
					}
					int index = 0;
					for (ExperienceInfo expInfo : populateDtos(experienceInfo.getFields())){
						ExperienceInfoPk expPk = new ExperienceInfoPk();
						if (experienceInfo.getCandidateId() > 0){
							expInfo.setCandidateId(experienceInfo.getCandidateId());
						} else if (experienceInfo.getUserId() > 0){
							expInfo.setUserId(experienceInfo.getId() > 0 ? experienceInfo.getId() : 0);
						}
						expInfo.setModifiedBy(login.getUserId());
						if (expInfo.getId() > 0){
							expPk.setId(expInfo.getId());
							experienceInfoDao.update(expPk, expInfo);
						} else{
							expPk = experienceInfoDao.insert(expInfo);
						}
						primaryKeys[index] = Integer.toString(expPk.getId());
						index++;
						map.put(expPk.getId(), expInfo.getFiles());
					}
					experienceInfo.setFields(primaryKeys);
					/**
					 * Insert in document mapping table
					 */
					String strfile = null;
					String files[] = null;
					
					
					// int field[]=(int[])
					// request.getSession().getAttribute("fileId");
					Set<Integer> rowKey = map.keySet();
					Integer key;
					if (!rowKey.isEmpty()){
						Iterator<Integer> it = rowKey.iterator();
						while (it.hasNext()){
							key = (Integer) it.next();
							if (map.get(key) != null){
								strfile = map.get(key);
								files = strfile.split("~");
								Integer field[] = new Integer[files.length];
								for (int l = 0; l < files.length; l++){
									field[l] = Integer.parseInt(files[l]);
								}
								/**
								 * this query and for loop is for
								 * checking if already exists and
								 * deleting and reinserting new entries
								 */
								DocumentMapping docMap1[] = mappingDao.findByDynamicWhere("EXPERIENCE_ID = ?", new Object[] { key });
								List<?> containArray = Arrays.asList(field);
								for (DocumentMapping doc : docMap1){
									contains = containArray.contains(doc.getDocumentId());
									if (!contains){
										DocumentMappingPk documentMappingPk = new DocumentMappingPk();
										documentMappingPk.setId(doc.getId());
										mappingDao.delete(documentMappingPk);
									}
								}
								if (field.length > 0){
									DocumentMapping docMap[] = new DocumentMapping[field.length];
									for (int i = 0; i < field.length; i++){
										documentMapping.setExperienceId(key);
										if (field != null && field[i] != 0){
											documentMapping.setDocumentId(field[i]);
											docMap = mappingDao.findByDynamicWhere("DOCUMENT_ID = ? AND EXPERIENCE_ID = ?", new Object[] { field[i], key });
											if (docMap != null && docMap.length > 0){
												for (int k = 0; k < docMap.length; k++){
													docMapPk.setId(docMap[k].getId());
													mappingDao.delete(docMapPk);
												}
											}
											documentMapping.setId(documentMapping.getId() != 0 ? 0 : 0);
											mappingDao.insert(documentMapping);
										}
									}
								}
							} else//delete if user does not send any id's in file i.e dose not want any documents
							{
								DocumentMapping documentMapping2[] = mappingDao.findByDynamicWhere("EXPERIENCE_ID = ?", new Object[] { key });
								for (DocumentMapping mapping : documentMapping2){
									DocumentMappingPk documentMappingPk = new DocumentMappingPk();
									documentMappingPk.setId(mapping.getId());
									mappingDao.delete(documentMappingPk);
								}
							} // if loop to map if upload doc exist
						}// it.next
					}// row key for ends
					String[] data=experienceInfo.getBackGroundVeri();
					if(data!=null){
					
						JDBCUtiility.getInstance().update("DELETE FROM BACKGROUND_VERIFICATION WHERE USER_ID=?", new Object[] { experienceInfo.getId() });

						/*for(String text:experienceInfo.getBackGroundVeri()){
							ExperienceInfo exp=DtoToBeanConverter.backGroundVerificationDocToBeanConverter(text);
							int backGroundId=exp.getBackGroundVeriFileId();
							int filess=exp.getVerificationFileId();
							if(filess != 0 & backGroundId!=0){
									DocumentMapping docMap1[] = mappingDao.findByDynamicWhere("BACKGROUND_VERI_ID = ?", new Object[] { backGroundId });
									List<?> containArray = Arrays.asList(exp.getVerificationFileId());
									for (DocumentMapping doc : docMap1){
										contains = containArray.contains(doc.getDocumentId());
										if (contains){
											DocumentMappingPk documentMappingPk = new DocumentMappingPk();
											documentMappingPk.setId(doc.getId());
											mappingDao.delete(documentMappingPk);
										}
									}
								
								mappingDao.updateBackGroundVerivicationById(experienceInfo.getUserId(),filess,backGroundId);
							}else{
								mappingDao.updateBackGroundVerivication(experienceInfo.getUserId(),filess);
							}
							
						}*/
						
						
						if(data!=null){
							for(String text:experienceInfo.getBackGroundVeri()){
								ExperienceInfo exp=DtoToBeanConverter.backGroundVerificationDocToBeanConverter(text);
								int filess=exp.getVerificationFileId();
								mappingDao.updateBackGroundVerivication(experienceInfo.getUserId(),filess);
								
							}
						}
						
					}
					
				} catch (Exception e){
					logger.info("Failed to update Profile Info");
					result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
					e.printStackTrace();
				}
				break;
			case UPDATEID:
				ExperienceInfoPk eInfoPk = new ExperienceInfoPk();
				eInfoPk.setId(experienceInfo.getId());
				try{
					experienceInfoDao.update(eInfoPk, experienceInfo);
				} catch (ExperienceInfoDaoException e){
					e.printStackTrace();
				}
				break;
		case UPDATEDOCUMENT:
			Integer userId = experienceInfo.getId();
			Integer bgcSepId = experienceInfo.getBgc_Seperate();
			updateDocument(userId, bgcSepId, mappingDao, documentMapping, docMapPk);
			break;
		}// switch ends
		return result;
	}

	private void updateDocument(Integer userId, Integer bgcSepId, DocumentMappingDao mappingDao,
			DocumentMapping documentMapping, DocumentMappingPk docMapPk) {
		try {
			DocumentMapping documentsFromDb = mappingDao.findByUserSepId(userId);

			documentMapping.setDocumentId(bgcSepId);
			documentMapping.setUserSeprationId(userId);
			if (documentsFromDb != null) {
				documentMapping.setId(documentsFromDb.getId());
				docMapPk.setId(documentsFromDb.getId());
				mappingDao.update(docMapPk, documentMapping);
			} else {
				mappingDao.insert(documentMapping);
			}
		} catch (DocumentMappingDaoException e) {
			logger.info(e.getMessage());

			e.printStackTrace();
		}
	}

	private ExperienceInfo[] populateDtos(String[] delimitedStr) throws Exception {
		ExperienceInfo[] experienceInfo = new ExperienceInfo[delimitedStr.length];
		try{
			int j = 0;
			for (String expInfo : delimitedStr){
				ExperienceInfo info = new ExperienceInfo();
				String[] colfields = expInfo.split("~=~");
				for (int i = 0; i < colfields.length; i++){
					if (colfields[i].split("=")[0].equals("company")){
						info.setCompany(colfields[i].split("=")[1]);
					} else if (colfields[i].split("=")[0].equals("id")){
						info.setId(Integer.parseInt(colfields[i].split("=")[1]));
					} else if (colfields[i].split("=")[0].equals("currentEmp")){
						info.setCurrentEmp(Short.parseShort(colfields[i].split("=")[1]));
					} else if (colfields[i].split("=")[0].equals("dateJoining")){
						info.setDateJoining(PortalUtility.StringToDate(colfields[i].split("=")[1]));
					} else if (colfields[i].split("=")[0].equals("dateRelieving")){
						info.setDateRelieving(PortalUtility.StringToDate(colfields[i].split("=")[1]));
					} else if (colfields[i].split("=")[0].equals("joiningDesignation")){
						info.setJoiningDesignation((colfields[i].split("=")[1]));
					} else if (colfields[i].split("=")[0].equals("leavingDesignation")){
						info.setLeavingDesignation(colfields[i].split("=")[1]);
					} else if (colfields[i].split("=")[0].equals("rptMgrName")){
						info.setRptMgrName(colfields[i].split("=")[1]);
					} else if (colfields[i].split("=")[0].equals("hrName")){
						info.setHrName(colfields[i].split("=")[1]);
					} else if (colfields[i].split("=")[0].equals("employmentType")){
						info.setEmploymentType(colfields[i].split("=")[1]);
					} else if (colfields[i].split("=")[0].equals("empCode")){
						info.setEmpCode(colfields[i].split("=")[1]);
					} else if (colfields[i].split("=")[0].equals("leavingReason")){
						info.setLeavingReason(colfields[i].split("=")[1]);
					} else if (colfields[i].split("=")[0].equals("files")){
						info.setFiles(colfields[i].split("=")[1]);
					}
				}
				experienceInfo[j] = info;
				j++;
			}
		} catch (Exception e){
			throw e;
		}
		return experienceInfo;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
