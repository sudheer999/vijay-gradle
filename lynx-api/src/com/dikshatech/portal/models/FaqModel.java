package com.dikshatech.portal.models;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.FaqDao;
import com.dikshatech.portal.dto.Faq;
import com.dikshatech.portal.dto.FaqPk;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.exceptions.FaqDaoException;
import com.dikshatech.portal.factory.FaqDaoFactory;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.Attachements;

public class FaqModel extends ActionMethods {

	private static Logger	logger		= LoggerUtil.getLogger(FaqModel.class);
	private static FaqModel	faqMoadel	= null;

	private FaqModel() {}

	public static FaqModel getInstance() {
		if (faqMoadel == null) faqMoadel = new FaqModel();
		return faqMoadel;
	}

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		return null;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		try{
			Faq faq = (Faq) form;
			if (faq.getId() == 0){
				FaqDaoFactory.create().insert(faq);
			} else{
				FaqDaoFactory.create().update(new FaqPk(faq.getId()), faq);
			}
		} catch (Exception e){
			logger.error("", e);
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notsaved"));
		}
		return result;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		Faq faq = (Faq) form;
		FaqDao FaqDao = FaqDaoFactory.create();
		try{
			switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
				case RECEIVEALL:
					faq.setList(FaqDao.findAll());
					faq.setIsEditable(JDBCUtiility.getInstance().getRowCount("FROM PROFILE_INFO WHERE HR_SPOC=?", new Object[] { Login.getLogin(request).getUserId() }) > 0 ? "1" : "0");
					request.setAttribute("actionForm", faq);
					break;
				case RECEIVE:
					Faq Faq = FaqDao.findByPrimaryKey(faq.getId());
					request.setAttribute("actionForm", Faq);
					break;
				default:
					break;
			}
		} catch (FaqDaoException e){
			e.printStackTrace();
		}
		return new ActionResult();
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		return null;
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		Faq faq = (Faq) form;
		ActionResult result = new ActionResult();
		try{
			FaqDaoFactory.create().delete(new FaqPk(faq.getId()));
		} catch (FaqDaoException e){
			result.getActionMessages().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.delete"));
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer[] upload(List<FileItem> fileItems, String docType, int id, HttpServletRequest request, String description) {
		return null;
	}

	@Override
	public Attachements download(PortalForm form) {
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

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
}
