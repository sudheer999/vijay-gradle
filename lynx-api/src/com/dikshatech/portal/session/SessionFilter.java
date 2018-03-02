package com.dikshatech.portal.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.dikshatech.common.utils.LoggerUtil;

public class SessionFilter implements Filter {

	private static Logger	logger	= LoggerUtil.getLogger(SessionFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (httpRequest.getParameter("device") != null){
			if (httpRequest.getSession(false) != null){
				if (httpRequest.getSession(false).getAttribute("login") != null || (httpRequest.getParameter("cType") != null && httpRequest.getParameter("cType").equals("LOGIN") && httpRequest.getParameter("method") != null && httpRequest.getParameter("method").equals("login"))){
					chain.doFilter(request, response);
					return;
				}
			}
			if (httpRequest.getParameter("cType") != null && httpRequest.getParameter("cType").equals("LOGIN") && httpRequest.getParameter("method") != null && httpRequest.getParameter("method").equals("login")){
				chain.doFilter(request, response);
				return;
			}
			logger.trace("Redirecting request: " + httpRequest.getContextPath() + " to noSession.html page");
			httpRequest.getSession(true);
			RequestDispatcher rd = httpRequest.getRequestDispatcher("noSession.html");
			rd.forward(request, response);
		} else{
			if (httpRequest.getSession(false) != null){
				if (httpRequest.getSession(false).getAttribute("login") != null){
					chain.doFilter(request, response);
				} else{
					chain.doFilter(request, response);
				}
			} else{
				logger.trace("Redirecting request: " + httpRequest.getContextPath() + " to login.jsp page");
				httpRequest.getSession(true);
				RequestDispatcher rd = httpRequest.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

	public static void main(String[] args) {
		String session = "<?xml version=\"1.0\" encoding=\"utf-8\"?><actionForm>C3B75F83A8460C8FF0C4B87D1B7F5F84</actionForm>";
		System.out.println(session.substring(session.indexOf("actionForm") + 11, session.length() - 13));
	}
}
