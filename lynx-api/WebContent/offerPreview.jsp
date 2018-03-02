<%@page import="java.util.Date"%>
<%@page import="com.dikshatech.portal.forms.PortalForm"%>
<%@page import="com.dikshatech.portal.dto.Candidate"%>
<%@page import="com.dikshatech.portal.dto.Login"%>
<%@page import="com.dikshatech.beans.UserLogin"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>

<%	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.setDateHeader("Expires", 0); // Proxies.
%>
<html>

<head>

<%


	Login login = (Login) request.getSession(false).getAttribute("login");
	request.getSession(false);
	PortalForm form = (PortalForm) request.getAttribute("actionForm");
    Candidate candidate =(Candidate) form;
   boolean preview=false;
   String filePath = " ";
	int candidateId = 0;
	if (candidate.getOfferLetter() != null){
		filePath = "preview" + File.separator + candidate.getOfferLetter()+"?time="+new Date().getTime();
		candidateId = candidate.getId();
		preview=candidate.isHideModifyButton();
	}
%>
<title>Diksha: Offer Preview</title>
<style>
BODY{
	margin:0;
	padding:0;
	background-color:#666;
	}
#document-holder{
	width:100%;
	height:auto;
	overflow:auto;
	padding-bottom:30px;
}
#buttonsHolder{
	width:100%;
	height:30px;
	background-color:#666;

	/*margin-top:-35px;
	padding-top:5px;*/
	text-align:center;
	position:fixed;
	bottom:0px;
	clear:both;
	/*IE Crack*/
	/*position:static;*/
}
</style>
<script type="text/javascript" src="pages/js/jquery.min.js"></script>
<script language="javascript">
	function closeWin(){
		alert("Offer letter has been sent to candidate succesfully.");
		window.close();
	}
	function closeWinModify(){
		
		window.close();
	}
	
	function PrintThisPage() {
		document.getElementById('ifrm').focus(); 
		document.getElementById('ifrm').contentWindow.print();

	}
</script>
<script type="text/javascript">
var exe=false;
function submitform(action,type)
{
	if(exe)return;
	var app="&TIME="+ new Date().getTime();
	if(type=='RESENDOFFERLETTER')app="&sendforapprovalPreview=true";
	$.get(action+"?method=update&uType="+type + "&cType=CANDIDATE&id=<%=candidateId%>"+app,function(o){if(type=='RESENDOFFERLETTER')closeWinModify();else closeWin();});
	exe=true;
}

</script>

</head>
<body>


 <iframe id="ifrm" name="ifrm" src="<%=filePath%>" style="width:100%; height:95%;" frameborder="0"></iframe>
<!-- <object type="application/pdf" data="<%=filePath%>" width="100%" height="800" ></object> -->

<%
    if(candidate.isView()){
    	//System.out.println("view"+ candidate.isView());
    }else{ 
    	if (login.getLoginType() == 1){
		if (candidate.getStatus() == 5 || candidate.getStatus() == 4){
			//don't show ok, generate OfferLetter button
		} else{
			if (login.getUserId().intValue()==6 || candidate.getStatus() == 1 || (candidate.isGenerateOfferLetter() && candidate.getStatus() == 7)){
%>
   	<div id="buttonsHolder">
		<button type="button" onClick="submitform('candidate.do','SENDOFFERLETTER')">Ok, Send Offer</button>
		<button type="button" onClick="PrintThisPage()">Print</button>
	<% 	
	if(preview==false){ %>
		<button type="button" onClick="closeWinModify()" id="modify" name="modify">Modify</button>
		<%} %>
		
 	</div>
 <%
		}else	if (!candidate.isGenerateOfferLetter() && (candidate.getStatus() == 3 || candidate.getStatus() == 2 || candidate.getStatus() == 7 || candidate.getStatus() == 8)){
%>
	<div id="buttonsHolder">
		<button type="button" onClick="submitform('candidatesave.do','RESENDOFFERLETTER')">Send For Approval</button>
		<button type="button" onClick="PrintThisPage()">Print</button>
		<button type="button" onClick="closeWinModify()">Modify</button> 
	</div>
<%
			}
		}
	 }
    }
%>

</body>
</html>