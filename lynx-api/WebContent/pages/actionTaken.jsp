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
	</head>
    <body leftmargin="0" marginwidth="0" topmargin="0" marginheight="0" offset="0" style="background:url(http://www.dikshatech.com/images/newportal_images/page_bg.png) repeat #f0f0f0">
    	<center>
        	<table border="0" cellpadding="0" cellspacing="0" height="100%" width="100%" >
            	<tr>
                	<td align="center" valign="top">
                        <table border="0" cellpadding="0" cellspacing="0" width="600" id="templatePreheader">
                            <tr>
                                <td valign="top" style="color:#505050; font-family:Arial; font-size:10px; line-height:100%; text-align:left; background-color:#f0f0f0;">
                                    <table border="0" cellpadding="10" cellspacing="0" width="100%">
                                    	<tr>
                                        	<td valign="top" style="padding-left:20px; padding-bottom:10px;">
                                            	<img src="http://www.dikshatech.com/images/newportal_images/diksha_logo.png" alt="Diksha" />
                                            </td>
											<td valign="top" width="190"></td>
                                        </tr>
                                    </table>
                                    <form name="frm" method="post" action="../validate">
                                    <input type="hidden" name="cId" value="<%=request.getParameter("sId")%>"></input> 
                                    <table width="260" cellspacing="10">
										<tr>
                                        	<td><p></p>Sorry, we could not process your request. Since,
								 					You have already <stong><%=request.getParameter("statusString")%></stong> this Offer.</p>
								 					<p>Please send an email to our Associate - Talent Acquisition.</p>
								 			</td>
										</tr>
                                        <%
                                        %>                          
                                    </table>
                                    </form>
                                </td>
                            </tr>
                        </table>
                        <script type="text/javascript">
                        function change(id){
                        var change=document.getElementById(id);
                        change.value=true;
                        }
                        </script>
                     </td>
                </tr>
            </table>
        </center>
    </body>
</html>
