<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        
        <title>Diksha: Employment offer </title>
		<style type="text/css">
			/* Client-specific Styles */
			#outlook a{padding:0;} 
			body{width:100% !important;} .ReadMsgBody{width:100%;} .ExternalClass{width:100%;}
			body{-webkit-text-size-adjust:none;}
		</style>
		<%
		%>
	</head>
    <body leftmargin="0" marginwidth="0" topmargin="0" marginheight="0" offset="0" style="background:url(http://www.dikshatech.com/images/newportal_images/page_bg.png) repeat #f0f0f0">
    	<center>
    	 <form id="frm" name="frm" method="post" action="../validate" onsubmit="if(document.getElementById('comments').value==''){alert('Please enter comments');return false;}">
            <input type="hidden" name="Reject"  id="Reject" value="true"></input>
            <input type="hidden" name="cId" value="<%=request.getParameter("cId")%>"></input> 
			<input type="hidden" name="sId" value="<%=request.getParameter("sId")%>"></input>
			<input type="hidden" name="uuid" value="<%=request.getParameter("uuid")%>"></input>
			<input type="hidden" name="type" value="<%=request.getParameter("type")%>"></input>
			<input type="hidden" name="assignedTo" value="<%=request.getParameter("assignedTo")%>"></input>
	        <div id="trans" style="height: 100%;width: 100%;background-color: #c2c2c2;opacity:0.5;z-index: 10;position: fixed;top:0px;"></div>
			<div id="popup" style="font-size:12px;min-height: 173px;width: 400px;z-index: 11;top:35%;left: 35%;background-color: white;position: fixed;border-radius: 10px;box-shadow: 2px 2px 6px #323232;" >
			<div style="background-color: #c2c2c2;margin: 1px;text-align: center;vertical-align: center;padding: 6px;border-radius: 10px 10px 0px 0px;">Comments</div>
			<div style="padding:10px 0px 0px 15px;text-align: left;">please enter reason for reject.</div>
			<textarea style="margin: 15px 15px 7px 15px; width: 360px; height: 50px; resize: none;" id="comments"  name="comments"></textarea>
			<div style="text-align: center;margin: 15px;">
					<input type="submit" style="padding : 3px 7px; background-color: #326EAD;color:white; border-radius:10px;cursor: pointer;" value="Submit"/>
					<input type="reset" style="padding : 3px 7px; background-color: #EA6834;color:white; border-radius:10px;cursor: pointer;" onclick="window.close();return false;" value="cancel"/>
			</div>
			</div>
	</form>
        </center>
    </body>
</html>
