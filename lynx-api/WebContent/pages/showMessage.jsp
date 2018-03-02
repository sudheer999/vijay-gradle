<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        
        <title>Diksha : LYNX</title>
		<style type="text/css">
			/* Client-specific Styles */
			#outlook a{padding:0;} 
			body{width:100% !important;} .ReadMsgBody{width:100%;} .ExternalClass{width:100%;}
			body{-webkit-text-size-adjust:none;}
		</style>
		<%
		boolean isValid=true;
		String msg = "";
			try{
				String type = request.getParameter("TYPE");
				switch (Integer.parseInt(type)) {
					case 1:
						msg = "Leave approved successfully";
						break;
					case 2:
						msg = "Leave rejected successfully";
						break;
					case 3:
						msg = "Sorry, action has been taken already";
						break;
					case 4:
						msg = "Mail sent successfully";
						break;
				}		
			} catch (Exception e){
				e.printStackTrace();
				isValid=false;
			}
		%>
	</head>
	<% if(isValid){%>
    <body leftmargin="0" marginwidth="0" topmargin="0" marginheight="0" offset="0" style="background:url(http://www.dikshatech.com/images/newportal_images/page_bg.png) repeat #f0f0f0">
    	<center>
        	<table border="0" cellpadding="0" cellspacing="0" height="100%" width="100%" >
            	<tr>
                	<td align="center" valign="top">
                        <table border="0" cellpadding="0" cellspacing="0" width="600" id="templatePreheader">
                            <tr>
                                <td valign="top" style="color:#505050; font-family:Arial; font-size:12px; line-height:100%; text-align:left; background-color:#f0f0f0;">
                                    <table border="0" cellpadding="10" cellspacing="0" width="100%">
                                    	<tr>
                                        	<td valign="top" style="padding-left:20px; padding-bottom:10px;">
                                            	<img src="http://www.dikshatech.com/images/newportal_images/diksha_logo.png" alt="Diksha" />
                                            </td>
											<td valign="top" width="190"></td>
                                        </tr>
                                    </table>
                                    <table width="260" cellspacing="10">
										<tr>
                                        	<td><p><%=msg%></p></td>
										</tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
					</td>
                </tr>
            </table>
        </center>
    </body>
    <% }%>
</html>
