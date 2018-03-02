package com.dikshatech.portal.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dikshatech.beans.NewsBean;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.DocumentsDao;
import com.dikshatech.portal.dao.NewsDao;
import com.dikshatech.portal.dto.Documents;
import com.dikshatech.portal.dto.DocumentsPk;
import com.dikshatech.portal.dto.News;
import com.dikshatech.portal.dto.NewsPk;
import com.dikshatech.portal.exceptions.DocumentsDaoException;
import com.dikshatech.portal.exceptions.NewsDaoException;
import com.dikshatech.portal.factory.DocumentsDaoFactory;
import com.dikshatech.portal.factory.NewsDaoFactory;
import com.dikshatech.portal.file.system.DocumentTypes;
import com.dikshatech.portal.file.system.PortalData;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.Attachements;
import com.dikshatech.common.utils.LoggerUtil;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

public class NewsModel extends ActionMethods {

	private static Logger	logger		= LoggerUtil.getLogger(NewsModel.class);
	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		NewsDao newsDao = NewsDaoFactory.create();
		ActionResult result = new ActionResult();
		try {
			News newsDto = (News) form;
			NewsPk pk = new NewsPk();
			pk = newsDao.insert(newsDto);
		} catch (Exception e) {
			if (e.toString().contains("Duplicate")) {
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,
						new ActionMessage("errors.failedtocreatenews"));
			} else {
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE,
						new ActionMessage("errors.newsdetailsnotsaved"));
			}

		}
		return result;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		DropDown dropDown = new DropDown();
		NewsDao newsDao = NewsDaoFactory.create();
		NewsBean[] newsBean = null;
		try {
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
			case RECEIVEALL:
				News newsAll[] = newsDao.findAll();
				newsBean = new NewsBean[newsAll.length];
				int i = 0;
				for (News newsSingle : newsAll) {
					NewsBean newsBeanSingle = DtoToBeanConverter
							.DtoToBeanConverter(newsSingle);
					newsBean[i] = newsBeanSingle;
					i++;
				}
				dropDown.setDropDown(newsBean);
				request.setAttribute("actionForm", dropDown);
				break;
			case RECEIVEIMAGENAME:
				News newsAll1[] = newsDao.findAll();
				newsBean = new NewsBean[newsAll1.length];
				int i1 = 0;
				for (News newsSingle : newsAll1) {
					NewsBean newsBeanSingle = DtoToBeanConverter
							.DtoToBeanConverterCordova(newsSingle);
					newsBean[i1] = newsBeanSingle;
					
					i1++;
				}
				dropDown.setDropDown(newsBean);
				request.setAttribute("actionForm", dropDown);
				break;

			case RECEIVE:
				dropDown = (DropDown) form;
				News news = newsDao.findByPrimaryKey(dropDown.getKey1());
				request.setAttribute("actionForm", news);
				break;

			default:
				break;

			}// switch ends
		} catch (NewsDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {

		NewsDao newsDao = NewsDaoFactory.create();
		News news = (News) form;
		NewsPk newspk = new NewsPk();
		ActionResult result = new ActionResult();
		try {
			News news1 = newsDao.findByPrimaryKey(news.getId());
			newspk.setId(news1.getId());
			newsDao.update(newspk, news);
		} catch (NewsDaoException e) {
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.failed.newsupdate"));
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		NewsDao newsDao = NewsDaoFactory.create();
		News news = (News) form;
		NewsPk newspk = new NewsPk();
		ActionResult result = new ActionResult();
		try
		{
			News news1 = newsDao.findByPrimaryKey(news.getId());
			newspk.setId(news1.getId());
			newsDao.delete(newspk);
		}
		catch (NewsDaoException e)
		{
			
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.failed.newsdelete"));
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public Integer[ ] upload(List<FileItem> fileItems, String docType,
			int id, HttpServletRequest request,String description)

	{
		boolean isUpload = true;
		Integer fieldsId[] = new Integer[fileItems.size()];
		int i = 0;
		for (FileItem fileItem2 : fileItems)
		{
			logger.info("FileName: " + fileItem2.getName());
			PortalData portalData = new PortalData();
			DocumentTypes dTypes = DocumentTypes.valueOf(docType);
			try
			{
				String fileName = portalData.saveFile(fileItem2, dTypes, id);
				String DBFilename = fileName;
				logger.info("filename" + fileName);
				if (fileName != null)
				{
					Documents documents = new Documents();
					DocumentsDao documentsDao = DocumentsDaoFactory.create();
					documents.setFilename(DBFilename);
					documents.setDoctype(docType);
					documents.setDescriptions(description);
					logger.info("The file " + fileName
							+ " successfully uploaded");
					try
					{
						DocumentsPk documentsPk = documentsDao
								.insert(documents);
						fieldsId[i] = documentsPk.getId();
					} catch (DocumentsDaoException e)
					{
						e.printStackTrace();
					}

					i++;
				}
				else
				{
					throw new FileNotFoundException();
				}

			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		if (isUpload)
		{
			logger.info("File uploaded successfully.");
		}
		else
		{
			logger.info("File uploaded failed.");
		}
		return fieldsId;
	}
	@Override
	public Attachements download(PortalForm form)
	{
		Attachements attachements = new Attachements();

		String seprator = File.separator;
		String path = "Data" + seprator;
		try
		{
			News news = (News) form;
			DocumentsDao documentsDao = DocumentsDaoFactory.create();
			Documents[ ] dacDocuments = documentsDao
					.findWhereIdEquals(news.getDocId());
			PortalData portalData = new PortalData();
			path = portalData.getDirPath();
			attachements.setFileName(dacDocuments[0].getFilename());

			path = portalData.getfolder(path);
			path = path + seprator + attachements.getFileName();
			attachements.setFilePath(path);
		} catch (DocumentsDaoException e)
		{
			// TODO Auto-generated catch block
			logger.info("failed to download check the file path:",e);
			e.printStackTrace();
		}
		return attachements;

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

	@Override
	public ActionResult defaultMethod(PortalForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}
