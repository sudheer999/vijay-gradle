

Diksha Portalv2.0 release version <version-Id>

1. drop existing database DIKSHA_PORTAL_2 and re-create it with the release db-script portalv2.0.sql and alterQueries.sql.

2. copy jar files from jars directory to ../ApacheTomcat6/lib directory

3. copy conf, jasper and email directories to /opt/dev-env/praya-java/ApacheTomcat6/lib/portal

4. Update ipaddress and port number of ApacheTomcat6 for files CANDIDATE_OFFER.html, CANDIDATE_OFFER_PLINK.html, EMPLOYEELOGIN.html, CANDIDATE_OFFER_ACCEPT.html and EMPLOYEE_WELCOME.html file at path /opt/dev-env/praya-java/ApacheTomcat6/lib/portal/email/templates  

5. Update db-user and db-password in file /opt/dev-env/praya-java/ApacheTomcat6/lib/portal/conf/Portal.properties

6. deploy DikshaPortal.war file using ApacheTomcat admin console or copy DikshaPortal.war in webapps directory
   of ApacheTomcat.
   
7. superadmin to login portal is:
	username: admin.portal@dikshatech.com
	password: praya123   	
	
8. when ever new department is added or region abbreviation is updated that region departments required to be updated/added in Portal.properties files
	with region abbreviation and IDs. Newly added departments and division will be added in DIVISON table.
	
9. before starting the application check that Portal.properties is referring to correct departmentIds from DIKSHA_PORTAL_2.DIVISON tables.
	
	e.g.: dept.<region-abbreviation>.products=<departmentId>	

10. add level ID for finance head for finance division in portal properties
   If there is no existing level as "finance head" in finance division create one and add it's level id.
   e.g : dept.<region-abbreviation>.financeHeadLevelId=<finance head level ID> 

   
11. Add any three  User Id for following departments HRD,OPERATION,FINANCE as a head handler's of these dept in portal.properties file.
    #### sample India region departments Handler Head User Id
    HANDLER.IN.HRD=<user_id>
    HANDLER.IN.OPERATION=<user_id>    
    HANDLER.IN.FINANCE=<user_id>