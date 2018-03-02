<%@page import="com.dikshatech.beans.UserLogin"%>
<%@page import="com.dikshatech.portal.dto.Login"%>
<% response.setHeader ("Pragma", "no-cache"); 
   response.setHeader ("Cache-Control" , "no-cache"); 
   response.setDateHeader ("Expires", 0); 
   String ver="2.1.7";
   %> 
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--  BEGIN Browser History required section -->
<link rel="stylesheet" type="text/css" href="history/history.css" />
<!--  END Browser History required section -->
<title>LYNX : Diksha</title>
<script src="AC_OETags.js?ver=<%=ver%>" language="javascript"></script>
<!--  BEGIN Browser History required section -->
<script src="history/history.js?ver=<%=ver%>" language="javascript"></script>
<!--  END Browser History required section -->
<link rel="shortcut icon" type="image/png" href="favicon.png"/>
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico"/>
<style>
body {
	margin: 0px;
	overflow: hidden
}
</style>
<script language="JavaScript" type="text/javascript">
<!--
// -----------------------------------------------------------------------------
// Globals
// Major version of Flash required
var requiredMajorVersion = 10;
// Minor version of Flash required
var requiredMinorVersion = 0;
// Minor version of Flash required
var requiredRevision = 0;
// -----------------------------------------------------------------------------
// -->
</script>
<script language="JavaScript" type="text/javascript">		
		function _checkSession(){
			//setFocusOnFlash();
			var sessionCheck = document.getElementById("sessionExists").value;
			//alert("sessionCheck: "+sessionCheck);
			if(sessionCheck == "true"){
				//alert("sessionCheck: "+sessionCheck);
				return "true";
			}
		}
		function getCurrentContext(){
			var url = window.location.toString();
			var urlparts = url.split('/');	
			var contextPath ='';
			for (i=0;i<(urlparts.length-1);i++){
				if(i==0)
					contextPath=urlparts[i];
				else
					contextPath=contextPath+'/'+urlparts[i];
			}
			return contextPath;
		}
		
		function getCurrentDtToPass(){
			var now = new Date();
			var tStamp = now.format("isoDateTime");
			return tStamp;
		}
		
		</script>
</head>
<body onload="_checkSession();getCurrentContext();">
<%
	boolean sessionValue = false;
	System.out.println("userLogin: "+request.getSession(false).getAttribute("login"));
	if (request.getSession(false).getAttribute("login") != null) 
	{
		Login login = (Login) request.getSession(false).getAttribute("login");
		if (login.getUserLogin() != null) 
		{
			sessionValue = true;
		}
	}
%>
<input type="hidden" id="sessionExists" name="session_manage" value="<%=sessionValue%>" />
<script language="JavaScript" type="text/javascript">
<!--
// Version check for the Flash Player that has the ability to start Player Product Install (6.0r65)
var hasProductInstall = DetectFlashVer(6, 0, 65);

// Version check based upon the values defined in globals
var hasRequestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);

if ( hasProductInstall && !hasRequestedVersion ) {
	// DO NOT MODIFY THE FOLLOWING FOUR LINES
	// Location visited after installation is complete if installation is required
	var MMPlayerType = (isIE == true) ? "ActiveX" : "PlugIn";
	var MMredirectURL = window.location;
    document.title = document.title.slice(0, 47) + " - Flash Player Installation";
    var MMdoctitle = document.title;

	AC_FL_RunContent(
		"src", "playerProductInstall",
		"FlashVars", "MMredirectURL="+MMredirectURL+'&MMplayerType='+MMPlayerType+'&MMdoctitle='+MMdoctitle+"",
		"width", "100%",
		"height", "100%",
		"align", "middle",
		"id", "index",
		"quality", "high",
		"bgcolor", "#e8e8e8",
		"name", "index",
		"wmode", "opaque",
		"allowScriptAccess","sameDomain",
		"type", "application/x-shockwave-flash",
		"pluginspage", "http://www.adobe.com/go/getflashplayer"
	);
} else if (hasRequestedVersion) {
	// if we've detected an acceptable version
	// embed the Flash Content SWF when all tests are passed
	AC_FL_RunContent(
			"src", "index",
			"width", "100%",
			"height", "100%",
			"align", "middle",
			"id", "index",
			"quality", "high",
			"bgcolor", "#e8e8e8",
			"name", "index",
			"wmode", "opaque",
			"allowScriptAccess","sameDomain",
			"type", "application/x-shockwave-flash",
			"pluginspage", "http://www.adobe.com/go/getflashplayer"
	);
  } else {  // flash is too old or we can't detect the plugin
    var alternateContent = 'Alternate HTML content should be placed here. '
  	+ 'This content requires the Adobe Flash Player. '
   	+ '<a href=http://www.adobe.com/go/getflash/>Get Flash</a>';
    document.write(alternateContent);  // insert non-flash content
  }
// -->
</script>
<noscript><object
	classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" id="index"
	width="100%" height="100%"
	codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
	<param name="movie" value="index.swf?ver=<%=ver%>" />
	<param name="quality" value="high" />
	<param name="bgcolor" value="#e8e8e8" />
	<param name="allowScriptAccess" value="sameDomain" />
	<embed src="index.swf?ver=<%=ver%>" quality="high" bgcolor="#e8e8e8" width="100%"
		height="100%" name="index" align="middle" play="true" loop="false"
		quality="high" allowScriptAccess="sameDomain"
		type="application/x-shockwave-flash"
		pluginspage="http://www.adobe.com/go/getflashplayer">
	</embed> </object></noscript>
</body>
</html>