<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="OSS,BSS,Kenan">
<meta name="description" content="Diksha has the largest team of Kenan Experts in the Asia Pacific region">

<title>Diksha Technologies</title>

<!-- Bootstrap Core CSS -->
<link href="styles/bootstrap.css" rel="stylesheet">
<link href="styles/bootstrap.min.css" rel="stylesheet">     

<!-- Custom CSS -->
<link href="styles/sb-admin-2.css" rel="stylesheet">   
<link href="styles/style.min.css" rel="stylesheet">
<link href="styles/template.css" rel="stylesheet">
<link rel="shortcut icon" type="image/png" href="images/favicon.png" />
         
<!-- Custom Fonts -->
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<!-- CSS and JS files for Sticky header starts -->
<link rel="stylesheet" href="http://necolas.github.com/normalize.css/2.0.1/normalize.css">
<link rel="stylesheet" href="styles/sticky-style.css">    

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js" type="text/javascript"></script>
<script src="js/jquery-scrolltofixed-min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		// grab the initial top offset of the navigation 
		var stickyNavTop = $('.nav1').offset().top;

		// our function that decides weather the navigation bar should have "fixed" css position or not.
		var stickyNav = function() {
			var scrollTop = $(window).scrollTop(); // our current vertical position from the top

			// if we've scrolled more than the navigation, change its position to fixed to stick to top,
			// otherwise change it back to relative
			if (scrollTop > stickyNavTop) {
				$('.nav1').addClass('sticky');
			} else {
				$('.nav1').removeClass('sticky'); 
			}
		};
    
		stickyNav();
		// and run it again every time you scroll
		$(window).scroll(function() {
			stickyNav();
		});
		
		$('#reset').click(function(e) {  
			//location.reload();
			$.ajax({
				type:'POST',
				url: '${pageContext.request.contextPath}/candidate.do',
				data: {
					method:"update",uType:"ACCEPTREJECTOFFER",cType:"CANDIDATE",
					id:<%=request.getParameter("cId")%>,isAccepted:4
				},
				success: function(result){
					var abc=result;
					if(abc==1){
						$('#error_msg_reload').empty();
				   		$('#error_msg_reload').append("Candidate already Accepted/Rejected This Offer this link has been expired Thank you");
				   		$('#alert_modal_reload').modal('show');
					}else{
						$('#error_msg_reload').empty();
						
				   		$('#error_msg_reload').append("Need More Time");
				   		$('#alert_modal_reload').modal('show');
					}
					
				}
			});//ajax End
		});
		
		$('#reset1').click(function(e) {  
			location.reload();
		});
		
		$('#reject_offer').click(function(e) {                 
			$.ajax({
				type: 'POST',               
				url: '${pageContext.request.contextPath}/candidate.do',
				data: {
					method:"update",uType:"ACCEPTREJECTOFFER",cType:"CANDIDATE",
					id:<%=request.getParameter("cId")%>,comments:$('#reasonforreject').val(),isAccepted:3 			
 			},
			success: function (result) {    
			//	location.reload();
				var abc=result;
				if(abc==1){
					$('#error_msg_reload').empty();
			   		$('#error_msg_reload').append("Candidate already Accepted/Rejected This Offer this link has been expired Thank you");
			   		$('#alert_modal_reload').modal('show');
				}else{
					$('#error_msg_reload').empty();
			   		$('#error_msg_reload').append("Your Offer Rejection is completed successfully");
			   		$('#alert_modal_reload').modal('show');
				//  download_url1="${pageContext.request.contextPath}/pages/offerReject.jsp";
				//  window.location.href=download_url1;
				}
   			}
	      });
		});
		
		$('#accept_offer').click(function(e) {  
			$.ajax({   
				type: 'POST',
				url: '${pageContext.request.contextPath}/candidate.do',
				data: {
					method:"update",uType:"ACCEPTREJECTOFFER",cType:"CANDIDATE",    
					id:<%=request.getParameter("cId")%>,comments:'',isAccepted:2 			
 			},
			success: function (result) {
				var abc=result;
				if(abc==1){
					$('#error_msg_reload').empty();
			   		$('#error_msg_reload').append("Candidate already Accepted/Rejected This Offer this link has been expired Thank you");
			   		$('#alert_modal_reload').modal('show');	
				}else{
					$('#error_msg_reload').empty();
			   		$('#error_msg_reload').append("Thank you for accepting the offer our team will get back to you");
			   		$('#alert_modal_reload').modal('show');
			 // download_url2="${pageContext.request.contextPath}/pages/offerAccept.jsp";
			 //  window.location.href=download_url2;
			}
   			}
	      });//ajax end
		});
		
});
</script>
<!-- CSS and JS files for Sticky header starts -->

</head>
<body>

<!-- Wrapper starts -->
	<div id="wrapper">
	
	<!-- Confirmation Modal -->
		<div class="modal" id="initial">
			<div class="modal-dialog">   
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h4 class="modal-title" style="color: navy;">
							Confirm!
						</h4>
					</div> 
					<div class="modal-body" style="background-color: #f9f9f9;">
						<p>Do you confirm that you need more time to decide regarding our offer for employment? </p>
					</div>
					<div class="modal-footer" style="margin-top:-10px;">
						<!-- <button type="button" class="btn btn-default" data-dismiss="modal">Yes</button> -->
						<a data-toggle="modal" href="#myModal" class="btn btn-primary" data-dismiss="modal">Yes</a>
                        <button type="button" class="btn btn-primary" data-dismiss="modal">No</button>
					</div>
				</div>
			</div>
		</div>

		<!-- Need more time Modal -->
		<div class="modal" id="myModal">
			<div class="modal-dialog">
				<div class="modal-content"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h4 class="modal-title" style="color: navy;">
							<img alt="" src="images/timer.gif" style="width: 50px; height: 50px;"> Need More Time!
						</h4>
					</div>
					<div class="modal-body" style="background-color: #f9f9f9;">
						<p>Your response has been communicated to the Resource Management Team team who will
							get in touch with you soon. In the meanwhile we encourage you to
							learn more about what’s in store for you and what we offer at
							Diksha. Take an informed decision.</p>
					</div>
					<div class="modal-footer" style="margin-top: -10px;">
						<!-- <a href="#" class="btn btn-primary" id="reset">Ok</a> -->
						 <button type="button" class="btn btn-primary" id="reset">Ok</button>
					</div>
				</div>
			</div>
		</div> 
		
		
		
		<!-- Accept offer letter Modal -->
		<div class="modal" id="acceptModal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h4 class="modal-title" style="color: navy;">
							<img alt="" src="images/timer.gif" style="width: 50px; height: 50px;"> Accept Offer Letter!
						</h4>
					</div>
					<div class="modal-body" style="background-color: #f9f9f9;">
						<p>Thank you very much for your acceptance in employment with Diksha Technologies. A representative from 
						the Resource Management Team will get in touch with you to further the joining formalities. <br></br>
						For any further clarifications, you may write to
						<a href="mailto:rmg@dikshatech.com?Subject=OfferLetter" target="_top">
						rmg@dikshatech.com</a></p>
					</div>
					<div class="modal-footer" style="margin-top: -10px;">
						<!-- <button type="button" class="btn btn-default" data-dismiss="modal">No</button> -->
                        <button type="button" class="btn btn-primary" data-dismiss="modal" id="accept_offer">Ok</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Initial Reject offer letter Modal -->
		<div class="modal" id="initialreject">
			<div class="modal-dialog">   
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h4 class="modal-title" style="color: navy;">
							Confirm!
						</h4>
					</div>
					<div class="modal-body" style="background-color: #f9f9f9;">
						<p>Please take an informed decision. Do you want to reject the offer from Diksha Technologies? </p>
					</div>
					<div class="modal-footer" style="margin-top: -10px;">
						<!-- <button type="button" class="btn btn-default" data-dismiss="modal">Yes</button> -->
						<a data-toggle="modal" href="#rejectModal" class="btn btn-primary" data-dismiss="modal">Yes</a>
                        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
					</div>
				</div>
			</div>
		</div>
		  
		<!-- Reject offer letter Modal -->
		<div class="modal" id="rejectModal" data-backdrop="static" data-keyboard="false">
			<div class="modal-dialog">   
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h4 class="modal-title" style="color: navy;">
							<img alt="" src="images/timer.gif" style="width: 50px; height: 50px;"> Reject Offer Letter!
						</h4>
					</div>
					<form method="post" class="form-inline" onsubmit="return false">
					<div class="modal-body" style="background-color: #f9f9f9;">
						<div class="row">
						 	<label class="col-sm-5" style="font-weight: normal;">Reason for declining the offer :<font style="color:#a94442;font-weight: bold;">&nbsp;&#x2a;</font></label>
							<div class="col-md-3 form-group">
								<textarea rows="6" cols="30" class="form-control" required="required" id="reasonforreject" name="reasonforreject"></textarea>
							</div>
						</div><br></br>		
						<p>Your response has reached the Talent Acquisition Team. A representative will get in touch with you soon.</p>
					</div>
					<div class="modal-footer" style="margin-top: -10px;">
						<button type="submit" class="btn btn-primary" id="reject_offer">Ok</button>
					</div>
					</form>
				</div>
			</div>
		</div>
		

		<!-- Navigation -->
		<div class="nav1">
			<nav class="navbar navbar-default navbar-static-top" role="navigation"
				style="margin-bottom: 0; background: url(images/page_header.png);">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="aboutDiksha.jsp"><img 
						src="images/diksha_logo.png" alt="Diksha Technologies"
						style="margin-top: -15px; margin-left: -15px;"></a>
				</div>

				<div class="nav navbar-top-links navbar-right"     
					style="padding-right: 30px; padding-top: 4px;">
					<div class="tooltip-demo">
						<!-- <button type="button" class="btn btn-primary btn-circle"
							data-toggle="tooltip" data-placement="bottom"
							title="Here you can accept/ reject/ opt for more time, regarding your employment offer.">
							<i class="fa fa-question"></i>
						</button> -->
						<a data-toggle="modal" href="#acceptModal">
						<button type="button" class="btn btn-default"
							data-toggle="tooltip" data-placement="bottom"     
							title="You may accept the offer here." style="color: green;" id="accept"><b>Accept</b></button>
						</a>
						<!-- <a data-toggle="modal" href="#myModal">
							<button type="button" id="help" class="btn btn-default"
								style="color: #FF9933;"><b>Need more time to decide</b></button>   
						</a> -->
						<a data-toggle="modal" href="#initial">
							<button type="button" id="help" class="btn btn-default"
								style="color: #FF9933;"><b>Need more time to decide</b></button>   
						</a>
						<a data-toggle="modal" href="#initialreject">
						<button type="button" class="btn btn-default"  
							data-toggle="tooltip" data-placement="bottom"
							title="You may choose to reject the offer here." 
							style="color: red;"><b>Reject</b></button>
						</a>

					</div>
				</div>
			</nav>
		</div>


	<!-- 	Required Javascript files     -->
	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>
	<!-- Image hover -->
	<script type='text/javascript'
		src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type='text/javascript'
		src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
	<!-- Bootstrap dropdown button   -->
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<!-- JavaScript jQuery code from Bootply.com editor  -->
	<script type='text/javascript'>
		$(document).ready(function() {
			$("[rel='tooltip']").tooltip();

			$('.thumbnail').hover(function() {
				$(this).find('.caption').slideDown(250); //.fadeIn(250)
			}, function() {
				$(this).find('.caption').slideUp(250); //.fadeOut(205)
			});
		});
	</script>
	<!-- Javascript for tooltips   -->
	<script>
		// tooltip demo
		$('.tooltip-demo').tooltip({
			selector : "[data-toggle=tooltip]",
			container : "body"  
		})
	</script>
	<!-- JavaScript jQuery code from Bootply.com editor  -->
	<script type='text/javascript'>
		$(document).ready(function() {
			$('#openBtn').click(function() {
				$('#myModal').modal({
					show : true
				})
				
				$('#myModal1').modal({
					show : true
				})
			});      
			
			
			/* $('#reject').click(function(){
			    isClick = true;
			    document.getElementById("accept").disabled = true;
			    document.getElementById("help").disabled = true;
			}); */
			
			
		});
	</script>
	<!-- Javascript for Content slider -->
	<script src="js/flexslider.js"></script>
	<script src="js/carousel.js"></script>
	
	
	<script type="text/javascript">
		function change(id){
			var change=document.getElementById(id);
			change.value=true;
		}
		
		function pageRefresh(){
			location.reload();
		}
	</script>
	
<div class="modal fade" id="alert_modal_reload" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
  <div class="modal-dialog modal-lg" style="width: 25%;">
    <div class="modal-content" >
      <div class="modal-header" style="background-color: #045FB4">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="pageRefresh()">&times;</button>
        <h4>Message</h4>
      </div>
      
      <div class="modal-body" id="error_msg_reload"></div>
      <div class="modal-footer">
		<button type="button" class="btn btn-primary" data-dismiss="modal" id="reload_modal" onclick="pageRefresh()">OK</button>
      </div>
   
    </div>
  </div>
</div>

