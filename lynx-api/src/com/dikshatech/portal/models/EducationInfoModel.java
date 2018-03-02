package com.dikshatech.portal.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.EducationBean;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DocumentMappingDao;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.EducationInfoDao;
import com.dikshatech.portal.dto.DocumentMapping;
import com.dikshatech.portal.dto.DocumentMappingPk;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.EducationInfo;
import com.dikshatech.portal.dto.EducationInfoPk;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.factory.DocumentMappingDaoFactory;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.EducationInfoDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;

public class EducationInfoModel extends ActionMethods
{
	private static Logger logger = LoggerUtil.getLogger(EducationInfoModel.class);

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		EducationInfo educationinfo = (EducationInfo) form;
		EducationInfoDao educationinfodao = EducationInfoDaoFactory.create();
		EducationInfoPk educationinfoPk = new EducationInfoPk();
		educationinfoPk.setId(educationinfo.getId());
		try
		{

			educationinfodao.delete(educationinfoPk);
		}
		catch (Exception e)
		{
			logger.info("Failed to delete Profile Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
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
		
		EducationInfo educationinfo = (EducationInfo) form;
		EducationInfoDao educationinfodao = EducationInfoDaoFactory.create();

		DocumentMapping documentMapping[];
		DocumentsDao documentsDao = DocumentsDaoFactory.create();
		DocumentMappingDao mappingDao = DocumentMappingDaoFactory.create();
		educationinfo.setUserId(educationinfo.getId()>0?educationinfo.getId():0);
		try
		{
			DropDown dropDown = new DropDown();
			if (educationinfo.getCandidateId() > 0 || educationinfo.getUserId()>0)
			{
				EducationInfo educationinfo2[] = null;
				if(educationinfo.getCandidateId() > 0){
				       educationinfo2 = educationinfodao.findWhereCandidateIdEquals(educationinfo.getCandidateId());
				}else if(educationinfo.getUserId()>0){
				       educationinfo2 = educationinfodao.findWhereUserIdEquals(educationinfo.getUserId());
				}
				EducationBean[] edu = new EducationBean[educationinfo2.length];

				for (int i = 0; i < educationinfo2.length; i++)
				{
					EducationBean eduBean = DtoToBeanConverter.DtoToBeanConverter(educationinfo2[i]);
					edu[i] = eduBean;
				}
				int k = 0;
				for (EducationBean edBean : edu)
				{
					documentMapping = mappingDao.findWhereEducationIdEquals(edBean.getId());
					if (documentMapping != null && documentMapping.length > 0)
					{
						Documents[] documentArr = new Documents[documentMapping.length];
						int i = 0;
						for (DocumentMapping tempDocMap : documentMapping)
						{
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
					}// if docs dont exist
					k++;
				}
				dropDown.setDropDown(edu);
			}
			request.setAttribute("actionForm", dropDown);

		}
		catch (Exception e)
		{
			logger.info("Failed to receive single Education Info ");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();

		}
		return result;
	}

	private EducationInfo[] populateDtos(String[] delimitedStr)
	{
		EducationInfo[] educationInfos = new EducationInfo[delimitedStr.length];

		int j = 0;
		for (String eduInfo : delimitedStr)
		{
			EducationInfo info = new EducationInfo();
			String[] colfields = eduInfo.split("~=~");

			for (int i = 0; i < colfields.length; i++)
			{
				if (colfields[i].split("=")[0].equals("id"))
				{
					info.setId(Integer.parseInt(colfields[i].split("=")[1]));
				}
				else if (colfields[i].split("=")[0].equals("degreeCourse"))
				{
					info.setDegreeCourse(colfields[i].split("=")[1]);
				}
				else if (colfields[i].split("=")[0].equals("type"))
				{
					info.setType(colfields[i].split("=")[1]);
				}
				else if (colfields[i].split("=")[0].equals("subjectMajor"))
				{
					info.setSubjectMajor(colfields[i].split("=")[1]);
				}
				else if (colfields[i].split("=")[0].equals("startDate"))
				{
					info.setStartDate(colfields[i].split("=")[1]);
				}
				else if (colfields[i].split("=")[0].equals("yearPassing"))
				{
					info.setYearPassing(Integer.parseInt(colfields[i].split("=")[1]));
				}
				else if (colfields[i].split("=")[0].equals("studIdNoEnrollNo"))
				{
					info.setStudIdNoEnrollNo(colfields[i].split("=")[1]);
				}
				else if (colfields[i].split("=")[0].equals("collegeUniversity"))
				{
					info.setCollegeUniversity(colfields[i].split("=")[1]);
				}
				else if (colfields[i].split("=")[0].equals("gradePercentage"))
				{
					info.setGradePercentage(colfields[i].split("=")[1]);
				}
				else if (colfields[i].split("=")[0].equals("gradutionDate"))
				{
					info.setGradutionDate(colfields[i].split("=")[1]);
				}
				else if (colfields[i].split("=")[0].equals("files"))
				{
					info.setFiles(colfields[i].split("=")[1]);
				}
			}
			educationInfos[j] = info;
			j++;
		}

		return educationInfos;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		EducationInfo educationinfo = (EducationInfo) form;

		EducationInfoDao educationinfodao = EducationInfoDaoFactory.create();
		EducationInfoPk[] educationinfoPk = new EducationInfoPk[educationinfo.getFields().length];
		String[] primaryKeys = new String[educationinfo.getFields().length];
		educationinfo.setUserId(educationinfo.getId()>0?educationinfo.getId():0);//set user-id passed in 'id' field

		try
		{
			int index = 0;
			for (EducationInfo edInfo : populateDtos(educationinfo.getFields()))
			{
				/**
				 * Change here for user id
				 */
				if(educationinfo.getCandidateId()>0){
					edInfo.setCandidateId(educationinfo.getCandidateId());
				}else if(educationinfo.getUserId()>0){
					edInfo.setUserId(educationinfo.getUserId()>0?educationinfo.getUserId():0);
				}
				edInfo.setModifiedBy(login.getUserId());
				educationinfoPk[index] = educationinfodao.insert(edInfo);
				DocumentMappingDao mappingDao = DocumentMappingDaoFactory.create();

				if (edInfo.getFiles() != null)
				{
					DocumentMapping documentMapping = new DocumentMapping();
					documentMapping.setDocumentId(Integer.parseInt(edInfo.getFiles()));
					documentMapping.setEducationId(educationinfoPk[index].getId());
					mappingDao.insert(documentMapping);
				}
				primaryKeys[index] = educationinfoPk[index].getId() + "," + edInfo.getSequence();
				index++;
			}
			educationinfo.setFields(primaryKeys);
			logger.info("PrimaryKeys: " + primaryKeys);
		}
		catch (Exception e)
		{
			logger.info("Failed to save Profile Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
			e.printStackTrace();

		}

		return result;
	}

	
	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request)
	{
		ActionResult result = new ActionResult();
		Login login = Login.getLogin(request);
		EducationInfo educationinfo = (EducationInfo) form;
		EducationInfoDao educationinfodao = EducationInfoDaoFactory.create();
		EducationInfo eduDBArr[] =null;
		List<Integer> newList=new ArrayList<Integer>();
		boolean contains = false;
		EducationInfoPk[] educationinfoPk = null;
		if(educationinfo.getFields() != null)
		{
			educationinfoPk = new EducationInfoPk[educationinfo.getFields().length];
		}else{
			educationinfoPk = new EducationInfoPk[0];
		}	
		

		HashMap<Integer, String> map = new HashMap<Integer, String>();

	

		try
		{

			switch (UpdateTypes.getValue(form.getuType()))
			{

			case UPDATE:
				String[] primaryKeys = new String[educationinfo.getFields().length];
				educationinfo.setUserId(educationinfo.getId()>0?educationinfo.getId():0);//set user-id passed in 'id' field

				for(EducationInfo edu: populateDtos(educationinfo.getFields())){
            		newList.add(edu.getId());
            	}

				if(educationinfo.getCandidateId()>0){
					eduDBArr = educationinfodao.findWhereCandidateIdEquals(educationinfo.getCandidateId());
				}else if(educationinfo.getUserId()>0){
					eduDBArr= educationinfodao.findWhereUserIdEquals(educationinfo.getUserId());
				}

				/**
				 * check if id exists if not then delete that row
				 * newList is new list ui sent,eduDBArr existing in db
				 */
				
				   for(int i=0;i<eduDBArr.length;i++){
				      if(!(newList.contains(eduDBArr[i].getId()))){
				    	 EducationInfoPk eduDeletePk = new EducationInfoPk();
				    	 eduDeletePk.setId(eduDBArr[i].getId());
				    	 educationinfodao.delete(eduDeletePk);  
				      }
				   }
				
				
				int index = 0;
				for(EducationInfo eInfo : populateDtos(educationinfo.getFields()))
				{
					EducationInfoPk eInfoPk = new EducationInfoPk();
					if(educationinfo.getCandidateId()>0){
						eInfo.setCandidateId(educationinfo.getCandidateId());
					}else if(educationinfo.getUserId()>0){
						eInfo.setUserId(educationinfo.getId()>0?educationinfo.getId():0);
					}
					eInfo.setModifiedBy(login.getUserId());
					if(eInfo.getId()>0)
					{
						eInfoPk.setId(eInfo.getId());
						
						educationinfodao.update(eInfoPk, eInfo);

					}else{
						eInfoPk=educationinfodao.insert(eInfo);
					}
					educationinfoPk[index] = eInfoPk;
					if(eInfo.getSequence() != 0)
					{
						primaryKeys[index] = educationinfoPk[index].getId() + "," + eInfo.getSequence();
					}else{
						primaryKeys[index] = educationinfoPk[index].getId() + "," + "0";
					}
					
					index++;
					map.put(eInfoPk.getId(), eInfo.getFiles());
				}	
				

				/**
				 * Insert in document mapping table
				 */

				String strfile = null;
				String files[] = null;
				DocumentMapping documentMapping = new DocumentMapping();
				DocumentMappingPk docMapPk = new DocumentMappingPk();
				DocumentMappingDao mappingDao = DocumentMappingDaoFactory.create();

				Set<Integer> rowKey = map.keySet();
				Integer key;
				if (!rowKey.isEmpty())
				{
					Iterator<Integer> it = rowKey.iterator();
					while (it.hasNext())
					{
						key = (Integer) it.next();
						if (map.get(key) != null)
						{
							strfile = map.get(key);
							files = strfile.split("~");

							Integer field[] = new Integer[files.length];
							for (int l = 0; l < files.length; l++)
							{
								field[l] = Integer.parseInt(files[l]);
							}
							
							/**
							 * this query and for loop is for
							 * checking if already exists and
							 * deleting and reinserting new entries
							 */
							DocumentMapping docMap1[] = mappingDao.findByDynamicWhere("EDUCATION_ID = ?", new Object[]{ key });
							
							
							List<?> containArray= Arrays.asList(field);
				  			for(DocumentMapping doc:docMap1){
				  				contains=containArray.contains(doc.getDocumentId());
				  				if(!contains){
				  					DocumentMappingPk documentMappingPk = new DocumentMappingPk();
									documentMappingPk.setId(doc.getId());
									mappingDao.delete(documentMappingPk);
				  				}
				  			}

							if (field.length > 0)
							{
								DocumentMapping docMap[] = new DocumentMapping[field.length];
								for (int i = 0; i < field.length; i++)
								{
									documentMapping.setEducationId(key);

									if (field != null && field[i] != 0)
									{
										documentMapping.setDocumentId(field[i]);
										/**
										 * this query and for loop is for
										 * checking if already exists and
										 * deleting and reinserting new entries
										 */
										docMap = mappingDao.findByDynamicWhere("DOCUMENT_ID = ? AND EDUCATION_ID = ?", new Object[]
										{ field[i], key });
										if (docMap != null && docMap.length > 0)
										{
											for (int k = 0; k < docMap.length; k++)
											{
												docMapPk.setId(docMap[k].getId());
												mappingDao.delete(docMapPk);
											}
										}

										documentMapping.setId(documentMapping.getId() != 0 ? 0 : 0);
										mappingDao.insert(documentMapping);
									}
								}
							}
						}
						else
						{
							DocumentMapping documentMapping2[] = mappingDao.findByDynamicWhere("EDUCATION_ID = ?", new Object[]
							{ key });
							for (DocumentMapping mapping : documentMapping2)
							{
								DocumentMappingPk documentMappingPk = new DocumentMappingPk();
								documentMappingPk.setId(mapping.getId());
								mappingDao.delete(documentMappingPk);
							}

						} // if loop to map if upload doc exist
					}// it.next
				}// row key for ends

				educationinfo.setFields(primaryKeys);
					logger.info("PrimaryKeys: " + primaryKeys);
				
				break; // UPDATE ENDS

			case UPDATEID:
				
				EducationInfoPk eInfoPk = new EducationInfoPk();
				eInfoPk.setId(educationinfo.getId());
				educationinfodao.update(eInfoPk, educationinfo);

				break;

			}// switch ends
		}
		catch (Exception e)
		{
			logger.info("Failed to update Profile Info");
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidlevelentry"));
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
