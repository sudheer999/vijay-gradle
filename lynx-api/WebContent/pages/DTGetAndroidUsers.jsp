<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.google.gson.Gson"%>
<%@page import="com.dikshatech.portal.dto.*"%>
<%@page import="java.util.List"%>
<%
String userList="";

	/* String pushStatus = "";
	Object pushStatusObj = request.getAttribute("pushStatus");

	if (pushStatusObj != null) {
		pushStatus = pushStatusObj.toString();
	} */
	List<ProfileInfo> profileList=null;
	profileList=(List<ProfileInfo>)request.getAttribute("actionForm");
	if(profileList!=null){
		Gson gson = new Gson();
		userList = gson.toJson(profileList);
	}
	System.out.println(userList);
%>
<%=userList%>