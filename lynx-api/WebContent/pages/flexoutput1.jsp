
<%@ page language="java"%><%@ taglib uri="/WEB-INF/lib/fxstruts.jar"
	prefix="fx"%>
	
	callback('<fx:write name="actionForm" type="xml" 
	errors="true" />');
<%-- <%@page import="java.io.PrintWriter"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.google.gson.Gson"%>
<%
	response.setContentType("application/json");
	Gson gson = new Gson();
	Map<String, Object> output = new HashMap();
	output.put("actionForm", request.getAttribute("actionForm"));
	System.out.println(gson.toJson(output));
	/* PrintWriter out1 = response.getWriter();
	// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
	out1.print(output);
	out1.flush(); */
%>
<%="callback(" + gson.toJson(request.getAttribute("actionForm")).toString() + ")"%> --%>