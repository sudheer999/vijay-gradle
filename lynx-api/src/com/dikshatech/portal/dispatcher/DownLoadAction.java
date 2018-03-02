package com.dikshatech.portal.dispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.dikshatech.common.utils.ExportType;
import com.dikshatech.portal.actions.CDownloadAction;
import com.dikshatech.portal.dto.Login;
import com.dikshatech.portal.forms.PortalForm;
import com.dikshatech.portal.mail.Attachements;

public class DownLoadAction extends CDownloadAction {

	@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String contentType = null;
		try{
			PortalForm portalForm = (PortalForm) form;
			portalForm.setObject(Login.getLogin(request));
			Attachements attachements = portalForm.getComponentObject(portalForm.getcType()).download(portalForm);
			if (attachements != null && attachements.getFileName() != null){
				String fileName = attachements.getFileName();
				String[] temp = fileName.split("\\.");
				String fileExt = temp[temp.length - 1];
				ExportType exportType = ExportType.valueOf(fileExt.toLowerCase());
				contentType = "application/" + exportType.name(); // octet-stream
				response.setHeader("content-disposition", "attachment; filename = \"" + fileName + "\"");
				return new ResourceStreamInfo(contentType, this.getServlet().getServletConfig().getServletContext(), attachements.getFilePath());
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
