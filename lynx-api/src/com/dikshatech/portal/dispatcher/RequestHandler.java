package com.dikshatech.portal.dispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.forms.PortalForm;

public class RequestHandler extends DispatchAction {

	private static Logger logger = LoggerUtil.getLogger(RequestHandler.class);
	PortalForm portalForm = new PortalForm();

	private static final String HTML_ORIGIN_VALUE = "Lynx";
	private static final String HTML_ORIGIN_KEY = "origin";

	@Override
	protected String getMethodName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String parameter) throws Exception {
		String methodName = super.getMethodName(mapping, form, request, response, parameter);
		logger.trace("Method Name: " + methodName);
		// PortalForm mainfForm = (PortalForm) form;
		return methodName;
	};

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("This is request object: " + request);
		PortalForm mainfForm = (PortalForm) form;
		ActionResult result = portalForm.getComponentObject(mainfForm.getcType()).login(mainfForm, request);
		if (result != null) {
			if (result.getActionMessages() != null) {
				// SaveErrors
				saveErrors(request, result.getActionMessages());
			}
			logger.debug("This is request is forwarded to: " + mapping.findForward(result.getForwardName()).getPath());
			logger.debug("Login DTO: " + mainfForm.toString());
			String origin = request.getParameter(HTML_ORIGIN_KEY);
			if (origin != null && origin.equals(HTML_ORIGIN_VALUE))
				return mapping.findForward(HTML_ORIGIN_VALUE);
			return mapping.findForward(result.getForwardName());
		} else
			return null;
	}

	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		session.invalidate();
		session = request.getSession(false);
		// logger.info("Session : " + session);
		// response.getWriter().println("Session : " + session);
		// logger.debug("This is request Session: "+session);
		if (session == null) {
			request.setAttribute("actionForm", "logout");
		} else {
			session.invalidate();
		}
		String origin = request.getParameter(HTML_ORIGIN_KEY);
		if (origin != null && origin.equals(HTML_ORIGIN_VALUE))
			return mapping.findForward(HTML_ORIGIN_VALUE);
		else
			return null;
	}

	/**
	 * save the data from form
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PortalForm mainfForm = (PortalForm) form;
		request.setAttribute("actionForm", form);
		ActionResult result = portalForm.getComponentObject(mainfForm.getcType()).save(mainfForm, request);
		/*mainfForm = (PortalForm) request.getSession(false).getAttribute("actionForm");
		request.setAttribute("actionForm", mainfForm);*/
		if (result != null) {
			if (result.getActionMessages() != null) {
				// SaveErrors
				saveErrors(request, result.getActionMessages());
			}
			String origin = request.getParameter(HTML_ORIGIN_KEY);
			if (origin != null && origin.equals(HTML_ORIGIN_VALUE))
				return mapping.findForward(HTML_ORIGIN_VALUE);
			return mapping.findForward(result.getForwardName());
		} else
			return null;
	}

	/**
	 * receive the data to form
	 */
	public ActionForward receive(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PortalForm mainfForm = (PortalForm) form;
		try {
			request.setAttribute("actionForm", form);
			ActionResult result = portalForm.getComponentObject(mainfForm.getcType()).receive(mainfForm, request);
			if (result != null) {
				if (result.getActionMessages() != null) {
					// SaveErrors
					saveErrors(request, result.getActionMessages());
				}
				String origin = request.getParameter(HTML_ORIGIN_KEY);
				if (origin != null && origin.equals(HTML_ORIGIN_VALUE))
					return mapping.findForward(HTML_ORIGIN_VALUE);
				if (mainfForm.getForwardName() != null)
					return mapping.findForward(mainfForm.getForwardName());
				else
					return mapping.findForward(result.getForwardName());
			} else
				return null;
		} finally {
			System.gc();
		}
	}

	/**
	 * receive the data to form
	 *//*
		public ActionForward receive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PortalForm mainfForm = (PortalForm) form;
		request.setAttribute("actionForm", form);
		ActionResult result = portalForm.getComponentObject(mainfForm.getcType()).receive(mainfForm, request);
		if (result != null){
			if (result.getActionMessages() != null){
				// SaveErrors
				saveErrors(request, result.getActionMessages());
			}
			return mapping.findForward(result.getForwardName());
		} else return null;
		}
		*/
	/**
	 * update the data from form
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PortalForm mainfForm = (PortalForm) form;
		request.setAttribute("actionForm", form);
		ActionResult result = portalForm.getComponentObject(mainfForm.getcType()).update(mainfForm, request);
		if (result != null) {
			if (result.getForwardName().equalsIgnoreCase("validJsp")) {
				request.setAttribute("actionValidJsp", "1");
				RequestDispatcher rd = request.getRequestDispatcher("pages/offerAccept.jsp");
				rd.forward(request, response);
				return null;
			}

			if (result.getActionMessages() != null) {
				// SaveErrors
				saveErrors(request, result.getActionMessages());
			}
			// ------Configuration for HTML-Lynx----
			String origin = request.getParameter(HTML_ORIGIN_KEY);
			if (origin != null && origin.equals(HTML_ORIGIN_VALUE))
				return mapping.findForward(HTML_ORIGIN_VALUE);
			// -----HTML config Ends
			return mapping.findForward(result.getForwardName());
		} else
			return null;
	}

	/**
	 * delete the data
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PortalForm mainfForm = (PortalForm) form;
		request.setAttribute("actionForm", form);
		ActionResult result = portalForm.getComponentObject(mainfForm.getcType()).delete(mainfForm, request);
		if (result != null) {
			if (result.getActionMessages() != null) {
				// SaveErrors
				saveErrors(request, result.getActionMessages());
			}
			// ------Configuration for HTML-Lynx----
			String origin = request.getParameter(HTML_ORIGIN_KEY);
			if (origin != null && origin.equals(HTML_ORIGIN_VALUE))
				return mapping.findForward(HTML_ORIGIN_VALUE);
			// -----HTML config Ends
			return mapping.findForward(result.getForwardName());
		} else
			return null;
	}

	/**
	 * process the validation requests.
	 */
	public ActionForward validate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PortalForm mainfForm = (PortalForm) form;
		request.setAttribute("actionForm", form);
		ActionResult result = portalForm.getComponentObject(mainfForm.getcType()).validate(mainfForm, request);
		if (result != null) {
			if (result.getActionMessages() != null) {
				// SaveErrors
				saveErrors(request, result.getActionMessages());
			}
			String origin = request.getParameter(HTML_ORIGIN_KEY);
			if (origin != null && origin.equals(HTML_ORIGIN_VALUE))
				return mapping.findForward(HTML_ORIGIN_VALUE);
			return mapping.findForward(result.getForwardName());
		} else
			return null;
	}

	/**
	 * process the execution requests.
	 */
	public ActionForward exec(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PortalForm mainfForm = (PortalForm) form;
		request.setAttribute("actionForm", form);
		ActionResult result = portalForm.getComponentObject(mainfForm.getcType()).exec(mainfForm, request);
		if (result != null) {
			if (result.getDispatchDestination() != null) {
				RequestDispatcher rd = request.getRequestDispatcher(result.getDispatchDestination());
				rd.forward(request, response);
				return null;
			}
			if (result.getActionMessages() != null) {
				// SaveErrors
				saveErrors(request, result.getActionMessages());
			}
			String origin = request.getParameter(HTML_ORIGIN_KEY);
			if (origin != null && origin.equals(HTML_ORIGIN_VALUE))
				return mapping.findForward(HTML_ORIGIN_VALUE);
			return mapping.findForward(result.getForwardName());
		} else
			return null;
	}

	/**
	 * default action method
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("This is request object: " + request);
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
		return null;
	}

	public ActionForward defaultMethod(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PortalForm mainfForm = (PortalForm) form;
		request.setAttribute("actionForm", form);
		ActionResult result = portalForm.getComponentObject(mainfForm.getcType()).defaultMethod(mainfForm, request,
				response);
		if (result != null) {
			if (result.getDispatchDestination() != null) {
				RequestDispatcher rd = request.getRequestDispatcher(result.getDispatchDestination());
				rd.forward(request, response);
				return null;
			}
			if (result.getActionMessages() != null) {
				// SaveErrors
				saveErrors(request, result.getActionMessages());
			}
			String origin = request.getParameter(HTML_ORIGIN_KEY);
			if (origin != null && origin.equals(HTML_ORIGIN_VALUE))
				return mapping.findForward(HTML_ORIGIN_VALUE);
			return mapping.findForward(result.getForwardName());
		} else
			return null;
	}
}
