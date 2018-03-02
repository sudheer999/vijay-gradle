<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.dikshatech.portal.dto.Login"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="java.util.HashMap"%>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="pages/js/jquery.dataTables.js"></script>
<script src="pages/js/bootstrap.min.js"></script>
<script src="pages/js/bootstrapValidator.js"></script>



<head>
<title>Diksha Touch: Broadcast message</title>

<script>
var oTable;
$(document).ready(function() {
	
	$.ajax({  
        url:'login.do',
      		  data:{
      			rType:"RECEIVEALLANDROIDUSER",
      			method:"receive",
      			cType:"LOGIN",
      			forwardName:"DTGetAndroidUsers",
      		  },			          		  
         success: function(result) {
        	 $("#send_notify").attr("disabled", true);
        	 arr = jQuery.parseJSON(result);
        	 oTable = $("#user_home").dataTable({
        			"aaData" : arr,
        			"bInfo": false,
        			 "bFilter" : false,
          			"bPaginate": false,
        			"aoColumns" : [
        { "sClass": "center",
           "mDataProp" : "userId",
           "mRender": function (data, type, full) {
               return '<input type="checkbox" name="check1" id="'+data+'" value="' + data + '" onchange="disabledButvvv(this.id)" >';
        }
        	},	
        		{"mDataProp" : "firstName"},
        		],
        	});
         } 
	});

	
$('#send_all').click(function(){
	var message=$('#message').val();
 	$('#frm1').bootstrapValidator({
        message: 'This value is not valid',
         excluded: [':disabled'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	message: {    
                validators: {
                    notEmpty: {
                        message: 'The Message is required '
                    },
                   /*  regexp: {
                    	regexp: /^[a-zA-Z)-9]+$/,
                        message: 'The comments can only alphabets'
                    } */
             }
        } 
       }
    })
	
 .on('success.form.bv', function(e) { 
	
		
		$.ajax({  
	        url:'login.do',
	      		  data:{
	      			sType:"GETANDROID",
	      			method:"save",
	      			cType:"LOGIN",
	      			message:message,
	      		  },			          		  
	         success: function(result) {
	        	 location.reload(true); 
	         }
		});	
			
 });// End of Validation
});	
});
</script>
<script>
var newArray=new Array();
empId=new Number();
function disabledButvvv(id1) {
	 $('#user_home').on("click","#"+id1, function(){
	 	if(this.checked){
	 		 id_Of_Current_Row=$(this).val();						  	
	 		 empId=id_Of_Current_Row;
	 		  $("#send_notify").removeAttr("disabled");
	 		  newArray.push(empId);
	 		$('#send_user').click(function(){
	 				
	 				var message=$('#message').val();
	 				$('#frm1').bootstrapValidator({
	 			        message: 'This value is not valid',
	 			         excluded: [':disabled'],
	 			        feedbackIcons: {
	 			            valid: 'glyphicon glyphicon-ok',
	 			            invalid: 'glyphicon glyphicon-remove',
	 			            validating: 'glyphicon glyphicon-refresh'
	 			        },
	 			        fields: {
	 			        	message: {    
	 			                validators: {
	 			                    notEmpty: {
	 			                        message: 'The Message is required '
	 			                    },
	 			                   /*  regexp: {
	 			                    	regexp: /^[a-zA-Z)-9]+$/,
	 			                        message: 'The comments can only alphabets'
	 			                    } */
	 			             }
	 			        } 
	 			       }
	 			    })
	 				
	 			 .on('success.form.bv', function(e) { 
	 				
	 			$.ajax({  
	 		        url:'login.do',
	 		      		  data:{
	 		      			sType:"SENDMESSAGEFORSELECTEDANDROIDUSERS",
	 		      			method:"save",
	 		      			cType:"LOGIN",
	 		      			userIds:newArray,
	 		      			message:message,
	 		      		  },			          		  
	 		         success: function(result) {
	 		        	location.reload(true);
	 		         }
	 			});
	 			
	 			});  // End of validations
	 		});
	 	}
	 	else{
	 	
	 		var ip = newArray.indexOf(id1);
	 		if(ip != -1) {
	 			newArray.splice(ip, 1);}
	 			
	$('#send_user').click(function(){
	 				
	 				var message=$('#message').val();
	 				$('#frm1').bootstrapValidator({
	 			        message: 'This value is not valid',
	 			         excluded: [':disabled'],
	 			        feedbackIcons: {
	 			            valid: 'glyphicon glyphicon-ok',
	 			            invalid: 'glyphicon glyphicon-remove',
	 			            validating: 'glyphicon glyphicon-refresh'
	 			        },
	 			        fields: {
	 			        	message: {    
	 			                validators: {
	 			                    notEmpty: {
	 			                        message: 'The Message is required '
	 			                    },
	 			                    regexp: {
	 			                    	regexp: /^[a-zA-Z)-9]+$/,
	 			                        message: 'The comments can only alphabets'
	 			                    }
	 			             }
	 			        } 
	 			       }
	 			    })
	 				
	 			 .on('success.form.bv', function(e) { 
	 				 
	 			$.ajax({  
	 		        url:'login.do',
	 		      		  data:{
	 		      			sType:"SENDMESSAGEFORSELECTEDANDROIDUSERS",
	 		      			method:"save",
	 		      			cType:"LOGIN",
	 		      			userIds:newArray,
	 		      			message:message,
	 		      		  },			          		  
	 		         success: function(result) {
	 		        	location.reload(true);
	 		         }
	 			});
	 			
	 			});  // End of validations
	 		});
	 	}
	 	});
	 	}


function _checkSession(){
	//setFocusOnFlash();
	var sessionCheck = document.getElementById("sessionExists").value;
//	alert("sessionCheck: "+sessionCheck);
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
int userId=0;
	boolean sessionValue = false;
	System.out.println("DikshaLogin: "+request.getSession(false).getAttribute("login"));
	if (request.getSession(false).getAttribute("login") != null) 
	{
		Login login = (Login) request.getSession(false).getAttribute("login");
		userId=login.getUserId();
		if (login.getUserLogin() != null) 
		{
			sessionValue = true;
			
		}
	}else{
		response.sendRedirect("/DikshaPortal/login.jsp");
	}
	
%>
<input type="hidden" id="sessionExists" name="session_manage" value="<%=sessionValue%>" />
<% if(userId==4||userId==6||userId==9||userId==16){%>
<form id="frm1" method="post">	
<div style="margin-left: 525px;">

<div class="row" style="width:545px;">
		<div class="panel panel-default">
<div class="panel-heading">
		<center><h4 style="color:#2E9AFE;">Sending Broadcast Message</h4></center>
			
			</div></div></div>

	

		<div class="form-group" >
		<div class="col-lg-3"> 
			<textarea rows="4" name="message" cols="23"
				 id="message" style="margin-left: 10px;" class="form-control" ></textarea></div>
			<a href="#" data-toggle="modal" data-target="#offerpopup">
		<button type="submit" class="btn btn-success" id="all_send" style="margin-left: 30px;">Send All</button></a>	
		</div>
	
	
	
	
	
	<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h3 class="page-header" ></h3>
		
		</div>
	</div>
	<div class="row" style="width:545px;">
		<div class="panel panel-default">
			<div class="panel-heading">
			<!-- <a href="#" data-toggle="modal" data-target="#offerpopup1"><button
							type="button" class="btn btn-success"
							id="send_notify" style="margin-left: 200px;">
							<i class="fa fa-pencil fa-fw"></i>Send Selected
						</button></a>
			
			</div> -->

<div class="panel-body">
			<div class="row">
			<div class="col-lg-4">
				<div class="table-responsive">
					<div class="container" >
						<div class="row left span10" >
							<table class="table table-striped table-hover table-condensed "	id="user_home" style="width: 150px">
								<thead>
									<tr>
										<th><i class="icon-filter"></i></th>
										<th>Employee Name<i class="icon-filter"></i></th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<a href="#" data-toggle="modal" data-target="#offerpopup1"><button
							type="button" class="btn btn-success"
							id="send_notify" >
							<i class="fa fa-pencil fa-fw"></i>Send Selected
						</button></a>
	</div>
	
</div>
</div>
	
	<div class="modal fade" id="offerpopup" tabindex="-1" role="dialog"
	aria-labelledby="basicModal" aria-hidden="true">
	<div class="modal-dialog" style="width: 300px;">
		<div class="modal-content" style="height: 200px">
			
			<div class="modal-body" class="text-center" style="margin-top: 60px;">
				<h5>Are you sure you want to send message to all?</h5>
		<center>		
		<button type="submit"  class="btn btn-default" id="send_all">Ok</button> 
		<button type="button" class="btn btn-default" data-dismiss="modal">cancel</button></center>
			</div>
            
		</div>
	</div>
</div>
	
	<div class="modal fade" id="offerpopup1" tabindex="-1" role="dialog"
	aria-labelledby="basicModal" aria-hidden="true">
	<div class="modal-dialog" style="width: 300px;">
		<div class="modal-content" style="height: 200px">
			
			<div class="modal-body" class="text-center" style="margin-top: 60px;">
				<h5>Are you sure you want to send message to selected users?</h5>
		<center>		
		<button type="submit"  class="btn btn-default" id="send_user">Ok</button> 
		<button type="button" class="btn btn-default" data-dismiss="modal">cancel</button></center>
			</div>
            
		</div>
	</div>

	
	
	</div>

	</div>
	
	<%}else{ %>
	<center><h3>You do not have access to this page. <a href='login.do' target='_self'>Click here</a> to go back to home page.</a></h3></center>
	<%} %>
	</form>
</body>
</html>
