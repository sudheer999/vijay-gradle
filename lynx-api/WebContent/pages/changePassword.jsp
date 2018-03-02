<%@page import="com.dikshatech.portal.factory.LoginDaoFactory"%>
<%@page import="com.dikshatech.portal.dto.Login"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
input{
 	width: 230px;
    padding: 2px;
    }
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Diksha: Password Management</title>
<link href='styles/styles.css' rel='stylesheet' type='text/css' />
<script type='text/javascript' src='js/jquery.min.js'></script>
<%
	String uuid = request.getParameter("uid");
%>
<script type="text/javascript">
function changePassword() {
	var password=$('#password').val();
	if(password.trim()==""){
		showerror("Please enter the password");
		return;
	}
	 if(password.trim().length<6){
		 showerror("Please enter minmum 6 charactors");
		return;
	} 
	if(password!=$('#repassword').val()){
		showerror("passwords not matching!!! Please retry again.");
		return;
	}
	$.get("../login.do?method=update&cType=LOGIN&uuid=<%=uuid%>&uType=CHANGEPASSWORD&password="+password+"&TIME="+ new Date().getTime(),function(o){
		var res=null;
		try{
			res=((o.toString()+"").split("<errors>")[1].split("</errors>")[0]).trim();
		}catch (e) {
			// TODO: handle exception
		}
		if(res==null)res="Something went worng!!! Please retry again.";
		if(res.indexOf("Password Changed Successfully!!")!=0){
			showerror(res);			
		}else{
			$('#content').html("Password Changed Successfully!!");
			setTimeout(function(){
				window.close();
			},5000);
		}
	});
	
}
function showerror(res){
	$('#errormsg').html(res);
	$('#errormsg').fadeIn(500);
	$('#password').val("");
	$('#repassword').val("");
	$('#password').focus();
	setTimeout(function(){
		$('#errormsg').fadeOut(500);
	},5000);
}
</script>
</head>
<body style="background-color: white;">
	<div id="header">
        	<div id="logoContainer"><img src="images/diksha_logo.png" alt="Diksha" title="Diksha" /></div>
        </div>
		<table border="0" cellpadding="0" cellspacing="0" height="100%"
			width="100%"  >
			<tr>
				<td align="center" valign="top" style="padding: 15px">
					<div style="width:480px;box-shadow: 1px 1px 10px #888888;margin-top: 80px;color: #616161;">
					<table border="0" cellpadding="0" cellspacing="0" width="480"
						id="templatePreheader">
						<tr>
							<td valign="top"
								style="font-size: 15px; text-align: left; background-color: #f0f0f0;padding: 6px 15px;font-weight: bold;">
								Change Password
							</td>
						</tr>
					</table>
					<table border="0" cellpadding="0" cellspacing="0" width="480"
						style="border: 0px solid #DDDDDD; ">
						
						<tr>
							<td align="left" valign="top" >
								<div style="padding: 15px 0px 15px 20px; width: 458px;min-height: 60px;background-color:#f3f3f3;" id="content">
									<%
										if (uuid != null){
											try{
												Login login = LoginDaoFactory.create().findByDynamicWhere("UUID=?", new Object[] { uuid })[0];
												%>
												<table border="0" cellpadding="2" cellspacing="0" width="450px">
													<tr>
														<td>User Name</td>
														<td  ><input type="text" value="<%=login.getUserName()%>" disabled="disabled"/></td>
													</tr>
													<tr>
														<td>Enter New Password</td>
														<td  ><input type="password"   id="password" onpaste="return false;" autofocus></input></td>
													</tr>
													<tr>
														<td>Re Enter New Password</td>
														<td ><input type="password"   id="repassword"  onpaste="return false;"></input></td>
													</tr>
													<tr>
														<td  colspan="2" style="text-align:right; padding-top: 10px;">
															<div style="display:none;padding: 2px;background-color: #f0f0f0;height: 20px;text-align: center;color: red;font-weight: bold;" id="errormsg"></div>
													  	 	<input type="button" value="Change Password" onclick="changePassword();" style="width: 133px;margin-right: 44px"></input>
												  	 	</td>
													</tr>
												</table>
												<div style="display:none;margin:10px 20px 2px 0px;padding: 5px;background-color: #f0f0f0;height: 20px;text-align: center;color: red;" id="errormsg"></div>
												<%
											} catch (ArrayIndexOutOfBoundsException e){
												%><div>Password has been changed or invalid request.</div><% 
											} catch (NullPointerException e){
												%><div>Unable to process the request. Please contact Portal Admin</div><% 
											} catch (Exception e){
												%><div>Unable to process the request. Please contact Portal Admin</div><% 
											}
										}else{
											%><div>Invalid request!!!!!!</div><% 
										}
									%>
								</div>
							</td>
						</tr>
					</table>
					</div>
				</td>
			</tr>
		</table>
	</center>
</body>
</html>