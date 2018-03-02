<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="includes/candidate_header.jsp"%>     


<script>
$(document).ready( function() {
    $('#myCarousel').carousel({      
    	interval:   false
	});   
	
	var clickEvent = false;
	$('#myCarousel').on('click', '.nav a', function() {
			clickEvent = true;
			$('.nav li').removeClass('active');  
			$(this).parent().addClass('active');
			/* alert("Event triggered : "+$(this).parent());   */    
	}).on('slid.bs.carousel', function(e) {
		if(!clickEvent) {   
			var count = $('.nav').children().length -1;
			var current = $('.nav li.active');
			current.removeClass('active').next().addClass('active');
			var id = parseInt(current.data('slide-to'));
			/* alert("Id is : "+id); */    
			if(count == id) {
				$('.nav li').first().addClass('active');	
			}
		}
		clickEvent = false;  
	});
	 
  
	
});
</script> 

<style>
@-webkit-keyframes blink {
    0% {
        opacity: 1;
    }
    49% {
        opacity: 1;
    }
    50% {
        opacity: 0;
    }
    100% {
        opacity: 0;
    }
}
@-moz-keyframes blink {
    0% {
        opacity: 1;
    }
    49% {
        opacity: 1;  
    }
    50% {
        opacity: 0;
    }
    100% {
        opacity: 0;
    }
}
@-o-keyframes blink {
    0% {
        opacity: 1;
    }
    49% {
        opacity: 1;
    }
    50% {
        opacity: 0;
    }
    100% {
        opacity: 0;
    }
}
</style>
  

<script type='text/javascript' src='//code.jquery.com/jquery-1.9.1.js'></script>
<script type='text/javascript' src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type='text/javascript' src="js/oridomi.min.js"></script>
		    
<script type='text/javascript'>//<![CDATA[   
			$(window).load(function(){   
				google.maps.event.trigger(map, 'resize');        
				
						var locations = [ [ '<b>USA<br></b>', 40.0691,-74.1058 ],
				  				[ '<b>Australia<br></b>', -33.8600, 151.2094 ],
				  				[ '<b>New Zealand<br></b>', -41.2889, 174.7772 ],
				  				[ '<b>Singapore<br></b>', 1.2889, 103.7772 ],      
				  				[ '<b>Philippines<br></b>', 14.5824, 121.0617 ],
				  				[ '<b>Bangalore<br></b>', 12.9667, 77.5667 ],
				  				[ '<b>Gurgaon<br></b>', 28.2000, 77.2000 ]];          
            
				  		// Setup the different icons and shadows
				  		var iconURLPrefix = 'http://maps.google.com/mapfiles/ms/icons/';

				  		var icons = [ /* iconURLPrefix + */ 'images/diksha_icon_location.png',
				  				/* iconURLPrefix + */ 'images/diksha_icon_location.png',
				  				/* iconURLPrefix + */ 'images/diksha_icon_location.png',  
				  				/* iconURLPrefix + */ 'images/diksha_icon_location.png',
				  				/* iconURLPrefix + */ 'images/diksha_icon_location.png',
				  				/* iconURLPrefix + */ 'images/diksha_icon_location.png',
				  				/* iconURLPrefix + */ 'images/diksha_icon_location.png',
				  				'images/diksha_icon_location.png']
				  		var icons_length = icons.length;

				  		var shadow = {
				  			anchor : new google.maps.Point(15, 33),
				  			url : iconURLPrefix + 'msmarker.shadow.png'  
				  		};
			
			
			//init map
			var mapOptions = {
			    center: new google.maps.LatLng(12.9667, 77.5667),  
			    zoom: 1,      
			    mapTypeId: google.maps.MapTypeId.ROADMAP,  
			    mapTypeControl: false,
			    mapTypeControlOptions: {
			      style:google.maps.MapTypeControlStyle.DROPDOWN_MENU,
			      position:google.maps.ControlPosition.TOP_RIGHT  
			    },
			    mapTypeId: google.maps.MapTypeId.ROADMAP,
			    panControl:false,
			    zoomControl:true,
			    scaleControl:false,
			    streetViewControl:false,
			    overviewMapControl:false,
			    rotateControl:false
			} 
			
			
			  
			map = new google.maps.Map(document.getElementById('map'), mapOptions);
			//set static map url  
			 $('#static_map').css({'opacity':'1','background-image':  getStaticUrl(map)});
			
			//init oridomi  
			/* var $oridomi =  $('#static_map').oriDomi(); */
			var $oridomi =  $('#static_map').oriDomi({vPanels:7,shading:'soft',shadingIntesity:.5,touchEnabled: true,ripple:0});
			$oridomi.oriDomi('reveal', 70);       
			/* $oridomi.oriDomi('reveal', 60);     */ 
			
			//unfold map on mouseenter           
			$('#map_wrap').mouseenter(function(){ 
				$oridomi.oriDomi('accordion', 0, function(){     
					/* $oridomi.oriDomi('reveal', 0, function(){    */
			        $('#static_map').css('opacity','0');   
			        $('#map').css('opacity','1');   
			        $('#description').fadeIn(2000);         
			        
			        
			    });
			//fold map on mouseleave                                                         
			}).mouseleave(function(){
			    $('#static_map').css({'opacity':'1','background-image':  getStaticUrl(map)});
			    $('#map').css('opacity','0');     
			    $oridomi.oriDomi('reveal', 70);  
			   /* $('#description').hide();    
			    /* $oridomi.oriDomi('reveal', 60);      */
			})
			
			//get url for a static map on the basis of current state of google map
			function getStaticUrl(map){
			 return "url(http://maps.googleapis.com/maps/api/staticmap?"
			                    + "center="+map.getCenter().toUrlValue()  
			                    + "&zoom="+map.getZoom()
			                    /* + "&title=Diksha+Technologies+Fisht+Olympic+Stadium+Sochi+Russia"   */   
			                    + "&size=1075x250)";                
			}          
			  
			//popovers for markers
			var infowindow = new google.maps.InfoWindow({
				maxWidth : 200
			});
			
			//initialising marker
			var marker;
			var markers = new Array();
			var iconCounter = 0;

			// Add the markers and infowindows to the map
			for (var i = 0; i < locations.length; i++) {
				marker = new google.maps.Marker({
					position : new google.maps.LatLng(locations[i][1],
							locations[i][2]),
					map : map,
					icon : icons[iconCounter],
					shadow : shadow
				});
				
				markers.push(marker);
				
				google.maps.event.addListener(marker, 'click',
						(function(marker, i) {
							return function() {
								infowindow.setContent(locations[i][0]);
								infowindow.open(map, marker);
							}
						})(marker, i));

				iconCounter++;
				// We only have a limited number of possible icon colors, so we may have to restart the counter
				if (iconCounter >= icons_length) {
					iconCounter = 0;
				}
			}

		/*	function AutoCenter() {
				//  Create a new viewpoint bound
				var bounds = new google.maps.LatLngBounds();
				//  Go through each...
				$.each(markers, function(index, marker) {
					bounds.extend(marker.position);
				});
				//  Fit these bounds to the map
				map.fitBounds(bounds);
			}
			AutoCenter();*/
			
			});//]]>  
			
</script>


<!-- Page Content -->
<div class="container">

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
				            <li><a style="color: #000;margin-left: -10px;">>&nbsp;&nbsp;&nbsp;Why Diksha</a></li>
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
					<h1 class="page-header" style="border-bottom: 1px solid #fff; margin-top: -10px;">Why Diksha</h1><br></br>
				</div>
			</div>
			   

			<div class="row">
				<div class="col-lg-12">
					<p style="text-align: justify;">
						<b style="font-size: 20px;color: #458FDA;font-variant: small-caps;">Why you should be our next employee…</b> It’s all about hunger
						for growth, continuous learning and being ready for challenges. If
						you are all about this journey, love new ideas, great technologies  
						and working with a small but absurdly talented group of people in
						a learning-friendly, rapidly evolving environment, we want to hear
						from you.<br></br> Working at Diksha is characterized by
						home-grown innovation, cutting-edge technologies and an ever
						growing customer base. If you want to develop your career and you
						would like your occupation to be connected with modern Telecom IT
						&amp; Technologies, apply!<br></br> <b style="font-size: 20px;color: #458FDA;font-variant: small-caps;">Some things we care about :</b>
						A positive attitude. Innovation. Integrity. Respect.
						Accountability. Flexibility. Supporting one another. Exceeding
						expectations. Expressing our ideas and, equally important,
						listening to others. Oh, and Telecom!<br></br> <b style="font-size: 20px;color: #458FDA;font-variant: small-caps;">Why we come
							to work :</b> Because we are not only employees but fanatical users.
						Because we care about the impact that our brand and service makes
						on our clients and customers and we make improving upon that
						mission our daily mantra. Because technology is something we’re
						(crazy!) passionate about, just like you. And we’re constantly
						looking for new ways to be (crazy!) brilliant at it. The success
						formula behind our performance, achievements and enthusiastic
						environment is our belief in bringing together the very best,
						sharpest and most forward thinking minds working as a team.
					</p>
				</div>  
			</div> 
			
			
	<div id="myCarousel" class="carousel slide" data-interval="false">
        <!-- Wrapper for slides -->
        <div class="carousel-inner">      
            <div class="item active">
                <h3>Our People</h3>
                    <p>
                        <font class="blink_me" style="color: #16a085; font-size: 18px;font-variant: small-caps;font-weight: bold;">Great people who work together in smart, inspiring, and
									collaborative teams where respect for individual talents is the
									norm.<br></font>
									<br> As a dynamic organization, we're not afraid to think 
									outside of the box or go out of our way to meet the market
									challenges. To ensure that our team grows in the same
									direction, we hire people who push the envelope right to the
									edge! We value critical thinking and intellectual curiosity. We
									have created an environment where people with energy,
									creativity and commitment work together with high standards or
									professional excellence and integrity</p>
            </div>
            <!-- End Item -->
            <div class="item">  
                <h3>Our Culture</h3>      
                <div style="height: 210px;overflow: auto;">
                    <p>
                       Our culture? We take it very seriously. Ourselves? Not so    
										much.<br> <font class="blink_me" style="color: #e67e22; font-size: 18px;font-variant: small-caps;font-weight: bold;">Diksha’s
											culture is a blend of intense inventiveness with learning and  
											development, which is supported throughout the organization.</font><br>
										In everything we do, we are dependable, open and connected.
										These values describe how we interact with each other, our
										customers, our clients, our communities and the wider world.
										They influence how we recruit and develop our people. They
										help us thrive in differing markets. Our past success was
										built on our values, and our future will be too.<br>
										<br> At Diksha we don't just hire people to fill a
										specific job opening. We invest in talent that can have a
										long-term career with us. Yes, it is people power all the way 
										for us! Our environment and culture has people with energy,
										creativity and commitment working together to fulfill
										ambitious goals with the highest standards of professional
										excellence and integrity. Here, you have the opportunity to
										pave your own way through challenging assignments, and
										enriching developmental opportunities. Employees are
										encouraged to proactively drive their growth through internal
										courses and external seminars coordinated with our Research
										Management Group. Diksha invests in training for every
										employee at every location, from the newly hired to the
										experienced.<br>
										<br> You will have the benefit of personal identity while
										working with people at all levels and the opportunity for an
										immediate impact on the company. An atmosphere of teamwork and
										camaraderie complements this professional accessibility.
										Diksha is a company where you come to work prepared to think,
										create, and make things happen.<br> The continued growth
										of our company is directly related to the skills, interests,
										pride, and abilities of our employees, and how well we work
										together as a team. By working together as a team, we can
										strive to make every employee’s job a source of personal
										satisfaction in terms of responsibility, and opportunities for
										advancement. Our employees come to share the tremendous pride
										we have in our people, our products, solutions and our
										performance.</p>
				</div>	
            </div>
            <!-- End Item -->
       
            <!-- End Item -->
            <div class="item">
                <h3>Fun @ Diksha</h3>
                    <p>
                        Making the work place interesting and fun for employees has
									become a norm in Diksha.<br>
									<br> Fun Friday – <font class="blink_me" style="color: #8e44ad; font-size: 18px;font-variant: small-caps;font-weight: bold;">All work and no
										play makes Jack a dull boy!</font> Fun Friday as the name suggests
									promises to be a day filled with lot of fun and excitement. Fun
									Friday's involve various employee engagement activities, which
									would help in achieving fun at work and also not affect the
									work schedule of the employees. We socialize with colleagues
									over drinks and food. Fun Friday activities break the monotony
									and helps increase productivity.</p>    
            </div>
            <!-- End Item -->
        </div>
        <!-- End Carousel Inner -->
        <ul class="nav nav-pills nav-justified">  
            <li data-target="#myCarousel" data-slide-to="0" class="active" style="background-color: #16a085; "><a href="#">Our People</a></li>
            <li data-target="#myCarousel" data-slide-to="1" style="background-color: #e67e22; "><a href="#">Our Culture</a></li>
            <li data-target="#myCarousel" data-slide-to="2" style="background-color: #8e44ad; "><a href="#">Fun @ Diksha</a></li>   
        </ul>   
    </div>      
    <!-- End Carousel -->   
    
    
    
    <div class="row">
				<div class="col-lg-12"><br></br>
					<p style="text-align: justify;">
						<b style="font-size: 20px;color: #458FDA;font-variant: small-caps;">Global Presence : </b>
						Ever dreamt of working in a foreign country? This might
									just be your chance. Diksha offers a huge range of global
									opportunities for talented individuals. International exposure
									provides opportunities to develop your skillsets and broaden
									your career. It's key to our success too. Our assignments may
									vary from a few months to many years. Be sure of some
									fascinating experiences.<br></br><br></br>   
									
							<div class="container">   
					            <div id="grid" class="main">     
									<div class="view">  
										<div class="view-back">  
											<span style="color: #777;"><b style="font-size: 16px;">Diksha Technologies, Bangalore</b><br></br></span>
											<span style="color: #777;"><p style="margin-left: 20px;font-size: 14px;">5th Floor, JP Square,<br>   
											No. 190, Sankey Road,<br>
											Sadashivanagar,<br>    
											Bangalore 560 080<br>  
											Karnataka, India<br>        
											Phone: +91 80 4333 6222<br>    
											Fax: +91 80 4333 6245 </p><br></br></span>
											   
										</div>
										<div id="map_wrap">
									    <div id="map"></div>
									    <div id="static_map"></div>
									    </div>   
									</div>     
								</div>
								</div><br></br> 		
									
						
					</p>
				</div>
			</div><br></br><br></br>    
    
    
<%@ include file="includes/candidate_footer.jsp"%>

		</div>
		<!-- /#page-wrapper -->


	</div>
	<!-- /#wrapper -->  


</body>
</html>
