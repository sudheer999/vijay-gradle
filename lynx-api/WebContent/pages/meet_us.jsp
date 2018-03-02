<%@ page language="java" contentType="text/html; charset=UTF-8"   
    pageEncoding="UTF-8"%>
<%@ include file="includes/candidate_header.jsp"%>

<!-- CSS and JS files for Image hover -->   
<link rel="stylesheet" href="styles/styles.css" />
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/modernizr.js"></script>
<script>
	$(document).ready(function() {
		if (Modernizr.touch) {
			// show the close overlay button
			$(".close-overlay").removeClass("hidden");
			// handle the adding of hover class when clicked
			$(".img").click(function(e) {
				if (!$(this).hasClass("hover")) {
					$(this).addClass("hover");
				}
			});
			// handle the closing of the overlay
			$(".close-overlay").click(function(e) {
				e.preventDefault();
				e.stopPropagation();
				if ($(this).closest(".img").hasClass("hover")) {
					$(this).closest(".img").removeClass("hover");
				}
			});
		} else {
			// handle the mouseenter functionality
			$(".img").mouseenter(function() {
				/* $(this).addClass("hover"); */
				$(this).addClass("hover");
			})  
			// handle the mouseleave functionality    
			.mouseleave(function() {
				/* $(this).removeClass("hover"); */
				$(this).removeClass("hover");
			});
		}
		
		
		$("#img1").mouseenter(function(){     
			$('#div1').show();  $('#div2').hide(); $('#div3').hide(); $('#div4').hide(); $('#div5').hide(); $('#div6').hide();
		});
		$("#img1").mouseleave(function(){  
			/* alert("mouse leave event triggers...");  */
		    $('#div1').hide();  $('#div2').hide(); $('#div3').hide(); $('#div4').hide(); $('#div5').hide(); $('#div6').hide(); 
		});       
		
		$('#div1').hide();
		/* $('#div1').addClass('hidden');*/
		
		$("#img2").mouseenter(function(){  
			$('#div2').show();  $('#div1').hide(); $('#div3').hide(); $('#div4').hide(); $('#div5').hide(); $('#div6').hide(); });
		$("#img2").mouseleave(function(){  
			$('#div1').hide();  $('#div2').hide(); $('#div3').hide(); $('#div4').hide(); $('#div5').hide(); $('#div6').hide();
			});       
		$('#div2').hide();  
		
		$("#img3").mouseenter(function(){  
			$('#div3').show();  $('#div1').hide(); $('#div2').hide(); $('#div4').hide(); $('#div5').hide(); $('#div6').hide();});
		$("#img3").mouseleave(function(){  
			$('#div1').hide();  $('#div2').hide(); $('#div3').hide(); $('#div4').hide(); $('#div5').hide(); $('#div6').hide();
			});
		$('#div3').hide(); 
		
		$("#img4").mouseenter(function(){  
			$('#div4').show();  $('#div1').hide(); $('#div3').hide(); $('#div2').hide(); $('#div5').hide(); $('#div6').hide();});  
		$("#img4").mouseleave(function(){  
			$('#div1').hide();  $('#div2').hide(); $('#div3').hide(); $('#div4').hide(); $('#div5').hide(); $('#div6').hide();
			});     
		$('#div4').hide(); 
		
		$("#img5").mouseenter(function(){  
			$('#div5').show();  $('#div1').hide(); $('#div3').hide(); $('#div4').hide(); $('#div2').hide(); $('#div6').hide();});  
		$("#img5").mouseleave(function(){  
			$('#div1').hide();  $('#div2').hide(); $('#div3').hide(); $('#div4').hide(); $('#div5').hide(); $('#div6').hide();
			});         
		$('#div5').hide();   
		
		$("#img6").mouseenter(function(){  
			$('#div6').show();  $('#div1').hide(); $('#div3').hide(); $('#div4').hide(); $('#div5').hide(); $('#div2').hide();});  
		$("#img6").mouseleave(function(){  
			$('#div1').hide();  $('#div2').hide(); $('#div3').hide(); $('#div4').hide(); $('#div5').hide(); $('#div6').hide();
			});            
		$('#div6').hide();         
		
		
		$('#effect-3').children().hover(function() {
			$(this).siblings().stop().fadeTo(500,0.5);
		}, function() {
			$(this).siblings().stop().fadeTo(500,1);  
		});
		
		
	});
</script>


<style>
#div1, #div2, #div3, #div4, #div5, #div6 {
	opacity: 0.9;
	padding: 10px;
	border-radius:5px;  
	text-align: justify;
}
</style>


<!-- Page Content -->
<div class="container">

	<!-- Page wrapper -->
	<div id="page-wrapper">
			
			<div class="row">
				<div class="col-lg-12">
					<br>  
					<div class="navbar navbar-default" role="navigation" style="background-color: #fff;border: 1px solid #fff;">
				      <div style="background-color: #fff;border: 1px solid #fff;">
				        <div class="navbar-header" style="background-color: #fff;border: 1px solid #fff;">
				          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
				            <span class="sr-only">Toggle navigation</span>
				            <span class="icon-bar"></span>
				            <span class="icon-bar"></span>
				            <span class="icon-bar"></span>  
				          </button>
				        </div>
				        
				        <div class="navbar-collapse collapse" style="background-color: #fff;border: 1px solid #fff;">
				        
				          <ul class="nav navbar-nav" style="margin-left: -30px">  
				            <li><a href="aboutDiksha.jsp" style="color: #239DCE;">Home</a></li>
				            <li><a style="color: #000;margin-left: -10px;">>&nbsp;&nbsp;&nbsp;Meet Our People</a></li>
				          </ul> 
				         
				          <ul class="nav navbar-nav navbar-right">     
				            <li class="dropdown">   
				              <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: #fff;background-color: #FF7E00;border-radius:5px;height: 40px;padding-top: 10px;">Quick Links <span class="caret"></span></a>
				              <ul id="quicklinks" class="dropdown-menu" role="menu">
				                <li><a href="aboutDiksha.jsp"><i class="fa fa-home"></i>&nbsp;Home</a></li>
								<li><a href="why_diksha.jsp"><i class="fa fa-question"></i>&nbsp;Why Diksha</a></li>
								<li><a href="what_we_do.jsp"><i class="fa fa-cog"></i>&nbsp;What We Do</a></li>
								<li><a href="how_we_work.jsp"><i class="fa fa-clock-o"></i>&nbsp;How We Work</a></li>
								<li><a href="career_and_development.jsp"><i class="fa fa-user"></i>&nbsp;Career Development</a></li>
								<li><a href="rewards_and_recognition.jsp"><i class="fa fa-certificate"></i>&nbsp;Rewards &amp; Recognition</a></li>
								<li><a href="cosmos_we_build.jsp"><i class="fa fa-power-off"></i>&nbsp;The Cosmos We Build</a></li>
								<li><a href="meet_us.jsp"><i class="fa fa-search"></i>&nbsp;Meet Our People</a></li>
				              </ul>
				            </li>
				          </ul>
				        </div><!-- /.nav-collapse -->
				      </div>
				    </div>
				</div>
			</div>  
			
			  
			 <div class="row">
				<div class="col-lg-12 title1">
					<h1 class="page-header" style="border-bottom: 1px solid #fff; margin-top: -10px;">Meet Our People</h1><br></br>
				</div>
			</div>

			<div id="fadediv" style="text-align: center; background-color: #f8fafb; border-radius: 10px; padding-top: 40px; padding-bottom: 40px;">
				<div id="effect-3" class="row">
				
				
					<div class="col-md-2">
						<div class="img">
							<img src="images/our_people/abhishek.jpg" alt=""
								class="img-circle img-responsive img-center" id="img5">
							<div class="overlay img-circle img-responsive img-center"
								align="center">
								<br>
								<h4 style="color: #fff;">Abhishek</h4>
								<b style="color: #fff;">Practice Head- Consulting, India</b>
								<p style="padding: 10px;">
									<a href="" class="label label-danger" data-placement="bottom"
										rel="tooltip" title="Connect via LinkedIn"
										style="background-color: #FF7E00;"> Connect&nbsp;<img
										alt="" src="images/LinkedIn.png" style="height: 10px;"></a>
								</p>
							</div>
						</div>
					</div>
				
					<div class="col-md-2">
						<div class="img">
							<img src="images/our_people/Madhan.png" alt=""
								class="img-circle img-responsive img-center" id="img4">
							<div class="overlay img-circle img-responsive img-center"
								align="center">
								<br>
								<h4 style="color: #fff;">Madhan</h4>
								<b style="color: #fff;">Associate Vice&nbsp;President, New&nbsp;Zealand </b>
								<p style="padding: 5px;">
									<a href="http://nz.linkedin.com/pub/madhan-vasudevan/2a/121/b7a" target="http://nz.linkedin.com/pub/madhan-vasudevan/2a/121/b7a" class="label label-danger" data-placement="bottom"
										rel="tooltip" title="Connect via LinkedIn"
										style="background-color: #FF7E00;"> Connect&nbsp;<img
										alt="" src="images/LinkedIn.png" style="height: 10px;"></a>
								</p>
							</div>
						</div>
					</div>
				
					<div class="col-md-2">
						<div class="img">
							<img src="images/our_people/Sandip K Nath.png" alt=""
								class="img-circle img-responsive img-center" id="img1">
							<div class="overlay img-circle img-responsive img-center" 
								align="center">
								<br>
								<h4 style="color: #fff;">Sandip</h4>
								<b style="color: #fff;">Team Lead, USA</b>
								<p style="padding: 10px;">
									<a href="https://www.linkedin.com/pub/sandip-k-nath/3/663/6bb" target="https://www.linkedin.com/pub/sandip-k-nath/3/663/6bb" class="label label-danger" data-placement="bottom"
										rel="tooltip" title="Connect via LinkedIn"
										style="background-color: #FF7E00;"> Connect&nbsp;<img
										alt="" src="images/LinkedIn.png" style="height: 10px;"></a>
								</p>
							</div>
						</div>
					</div>

					<div class="col-md-2">
						<div class="img">  
							<img src="images/our_people/Vinuthan.png" alt=""
								class="img-circle img-responsive img-center" id="img2">
							<div class="overlay img-circle img-responsive img-center"
								align="center">
								<br>
								<h4 style="color: #fff;">Vinuthan</h4>
								<b style="color: #fff;">Team Lead, Australia</b>
								<p style="padding: 10px;">
									<a href="http://in.linkedin.com/pub/vinuthan-bn/13/a92/901" target="http://in.linkedin.com/pub/vinuthan-bn/13/a92/901" class="label label-danger" data-placement="bottom"
										rel="tooltip" title="Connect via LinkedIn"
										style="background-color: #FF7E00;"> Connect&nbsp;<img
										alt="" src="images/LinkedIn.png" style="height: 10px;"></a>
								</p>
							</div>
						</div>
					</div>

					<div class="col-md-2">
						<div class="img">
							<img src="images/our_people/Vinod Pulluru.png" alt=""
								class="img-circle img-responsive img-center" id="img3">
							<div class="overlay img-circle img-responsive img-center"
								align="center">
								<br>
								<h4 style="color: #fff;">Vinod</h4>
								<b style="color: #fff;">Principal Architect, Aruba</b>
								<p style="padding: 10px;">
									<a href="http://in.linkedin.com/in/vinodpulluru" target="http://in.linkedin.com/in/vinodpulluru" class="label label-danger" data-placement="bottom"
										rel="tooltip" title="Connect via LinkedIn"
										style="background-color: #FF7E00;"> Connect&nbsp;<img
										alt="" src="images/LinkedIn.png" style="height: 10px;"></a>
								</p>
							</div>
						</div>
					</div>
					

					<div class="col-md-2">
						<div class="img">
							<img src="images/our_people/Anvita Guruprasad.png" alt=""
								class="img-circle img-responsive img-center" id="img6">
							<div class="overlay img-circle img-responsive img-center"
								align="center">
								<br>
								<h4 style="color: #fff;">Anvita</h4>    
								<b style="color: #fff;">Team Lead, India</b>
								<p style="padding: 10px;">
									<a href="http://nl.linkedin.com/pub/anvitha-guruprasad/b/8a/23a" target="http://nl.linkedin.com/pub/anvitha-guruprasad/b/8a/23a" class="label label-danger" data-placement="bottom"
										rel="tooltip" title="Connect via LinkedIn"
										style="background-color: #FF7E00;"> Connect&nbsp;<img
										alt="" src="images/LinkedIn.png" style="height: 10px;"></a>
								</p>  
							</div>
						</div>
					</div>
				</div>
				   
				<div class="row"></br>        
				<div class="col-md-12">
					<div id="div1">
						<div class="col-md-12" style="background-color: #fcfcfc;padding: 20px; border-radius:5px;font-style: italic;border: 1px solid #ccc;color: #000;">
						<!-- <span class="more"><br> -->
						<img src="images/our_people/usa.png" alt="" class="img-responsive img-center col-md-1" style="width: 80px; height: 50px; ">
						<font style="font-style: normal;font-weight: bold;font-size: 18px;">Sandip K Nath</font><br><br>   
						Ensures a smooth development process and world-class quality of the delivered software. Agile and OOP are his usual tools of choice, but he stays open and 
						looks for new ideas all the time. At Diksha he has already impressed us with his ability to clearly explain software and technology, just like he impresses our clients.<br><br> 
						While some say he can speak to animals and others claim that his birth was foretold by a new star in the heavens, in this earth life - he's a Billing consultant &amp; an expert in 
						the Arbor/Kenan FX billing platform. Besides reading &amp; cooking – he has keen interest in Astrology, Horoscope and a little bit of Crystal Gazing.
			    		<!-- </span> -->
	         	  		 </div>
					</div>     
				</div>  
				</div>
				
				<div class="row">
					<div class="col-md-12">
						<div id="div2">
							<div class="col-md-12" style="background-color: #fcfcfc;padding: 20px; border-radius:5px;font-style: italic;border: 1px solid #ccc;color: #000;">
							<!-- <span class="more"><br> -->
							<img src="images/our_people/australia.png" alt="" class="img-responsive img-center col-md-1" style="width: 80px; height: 50px; ">
							<font style="font-style: normal;font-weight: bold;font-size: 18px;">Vinuthan</font><br><br>   
							From one of the top engineering universities in Karnataka, Vinuthan has a broad range of software projects under his belt. He's also fascinated by the 
							scientific origins of the universe (a somewhat more complex engineering project!).  He has great attention to detail and goes above and beyond to make 
							sure our finished applications are as great as our clients deserve.<br><br>
							Outside of work "VN" exercises his passion for photography and travelling. He’s amongst the few software professionals who can boast of having successfully completed the High 
							altitude trekking in the Kanchenjunga mountain range. His competitiveness is legendary, to the point of coming in to the office while on holidays to defend his foosball ranking.
				    		<!-- </span> -->
		         	  		 </div>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12">
						<div id="div3">
							<div class="col-md-12" style="background-color: #fcfcfc;padding: 20px; border-radius:5px;font-style: italic;border: 1px solid #ccc;color: #000;">
							<!-- <span class="more"><br> -->
							<img src="images/our_people/aruba.png" alt="" class="img-responsive img-center col-md-1" style="width: 80px; height: 50px; ">
							<font style="font-style: normal;font-weight: bold;font-size: 18px;">Vinod Pulluru</font><br><br>   
							Vinod brings 11+ years of BSS Architecture experience from different parts of the world to Diksha. He has a real passion for delivery, coaching and mentoring. Being 
							a University rank holder in both his Master’s and Bachelor’s degree, he is also a Certified PMP (Project Management Professional), while his expertise include 
							provisioning (service activation), billing integration, Kenan, Arbor, BSS and Data Migration.<br><br>
							If you want to catch him outside the office you'll have to be up pretty late; as a fulltime dad of two, he's often up till 4am! And btw, his design skills only marginally outshine his 
							expertise on the cricket pitch.
				    		<!-- </span> -->  
		         	  		 </div>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12">
						<div id="div4">      
							<div class="col-md-12" style="background-color: #fcfcfc;padding: 20px; border-radius:5px;font-style: italic;border: 1px solid #ccc;color: #000;">
							<!-- <span class="more"><br> -->
							<img src="images/our_people/australia.png" alt="" class="img-responsive img-center col-md-1" style="width: 80px; height: 50px; ">
							<font style="font-style: normal;font-weight: bold;font-size: 18px;">Madhan Vasudevan</font><br><br>   
							Madhan makes delicious software solutions, manages projects and trains fellow programmers at Diksha. He has been an integral part of major projects for 
							a number of companies, including Vodafone , Telstraclear, Spark New Zealand to name a few. You will for sure be amazed by the quality of talks during informal interactions with him.<br><br>
							He’s most known to enjoy pushing people out of their comfort zones and into new rewarding experiences. He can also talk for hours about how seemingly unrelated skills (such as running, reading, 
							hand balancing or juggling) can help you become a better programmer.
				    		<!-- </span> -->
		         	  		 </div>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12">  
						<div id="div5">
							<div class="col-md-12" style="background-color: #fcfcfc;padding: 20px; border-radius:5px;font-style: italic;border: 1px solid #ccc;color: #000;">
							<!-- <span class="more"><br> -->
							<img src="images/our_people/india.png" alt="" class="img-responsive img-center col-md-1" style="width: 80px; height: 50px; ">
							<font style="font-style: normal;font-weight: bold;font-size: 18px;">Abhishek Mishra</font><br><br>   
							Combining his blend of good business acumen and strong technical skills, Abhishek is the brains behind the technology, client contentment and reliability 
							of Diksha Technologies. He has been delivering cross-platform high net-worth projects across multiple geographies, but the common thread has been the same 
							genuine customer focus which he brings to his role as Practice Head.<br><br> 
							Pro tip: If you ever challenge Abhi to a duel, improve your odds and pick golf, not chess.
				    		<!-- </span> -->
		         	  		 </div>
						</div>
					</div>
				</div> 
				
				<div class="row">    
					<div class="col-md-12">    
						<div id="div6">
							<div class="col-md-12" style="background-color: #fcfcfc;padding: 20px; border-radius:5px;font-style: italic;border: 1px solid #ccc;color: #000;">
							<!-- <span class="more"><br> -->
							<img src="images/our_people/india.png" alt="" class="img-responsive img-center col-md-1" style="width: 80px; height: 50px; ">
							<font style="font-style: normal;font-weight: bold;font-size: 18px;">Anvita Guruprasad</font><br><br>   
							Anvitha’s unceasing quest for arcane knowledge has led her into the mysterious world of System testing and its reliability in real world environments. 
							Kerry is our Team Lead here, ensuring solutions made by our fabulous developers meet and exceed our clients' requirements. A 'take no prisoners' 
							approach to both life and work is what she is famous for around Diksha.<br><br>
							We're still trying to convince her to attend the ‘Smartest Engineer in the World’ challenge she got nominated for out of the office, but until then, 
							I'm sure we can make do with her super-sweet personality!     
				    		<!-- </span> -->
		         	  		 </div>
						</div>
					</div>
				</div>  
				
			</div><br></br>
			<!-- /.container -->
			
			
			
			<br></br>
			
			<script src="//platform.linkedin.com/in.js" type="text/javascript"></script>
			<script type="IN/CompanyInsider" data-id="1046835"></script>
			<br></br><br></br>   
   
			<br></br><br></br>   

			<%@ include file="includes/candidate_footer.jsp"%>


		</div>
		<!-- /#page-wrapper -->
		
		</div>
		<!-- /#container -->

	
	<!-- /#wrapper -->




</body>

</html>
