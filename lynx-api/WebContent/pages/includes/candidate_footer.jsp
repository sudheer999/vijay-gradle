<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<link rel="stylesheet" href="styles/jnewsbar.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="customizer/iris/iris.min.css" type="text/css" media="screen" />
	<style> 
		
		.jnewsbar.jnews-top{ 
			background-color: #464952;
			/* background-color: red;  */
		}
		.jnewsbar.jnews-top .jnewsbar-title h2{ 
			color: #ffffff; 
		}
		.jnewsbar.jnews-top .jnewsbar-title{ background-color: #292b2d;    
			/* background-color: red;  */      
		 }
		.jnewsbar.jnews-top li,.jnewsbar.jnews-top li a,.jnewsbar.jnews-top span ,.jnewsbar.jnews-top a .jnewsbar.jnews-top p, .jnewsbar-navigate a{ color: #ffffff; }
	</style>
	
	
<div class="newstop">   
	<h2>Things you may not know about Diksha</h2>
	<ul>
	    
		
		<li>We have Serviced clients with customer bases of 7 million to 10 million and successfully delivered 23 E2E projects. </li>
		<li>Though our origins are distinctly as an Indian company&sbquo; Diksha’s services has been deployed in 6 out of 7 continents – Africa&sbquo; Asia&sbquo; Australia&sbquo; Europe&sbquo; North America and South America.</li>  
		<li>We haven’t had the call for Antarctica yet &sbquo; but you never know?</li>	
		
		<li>We have a very strong presence among Telcos for delivering BSS related projects for more than 14 years but did you know?</li>
		<li>Diksha has the largest team of Kenan Experts in the Asia Pacific region!</li>
		
		<li>Diksha Technologies is the Winner of the Deloitte Technology fast 50 India 2010 and Deloitte Technology fast 500 Asia Pacific 2010 awards.</li>
		<li>We are also among the Red Herring Asia Top 100 list. FYI – we are Oracle &amp; TIBCO Gold Consulting Partners.</li>
		
		<li>Diksha is growing and profitable&sbquo; and has not had to raise any external capital since 2000.</li>
		
		<li>The origins of the name Diksha – what does it mean and why was it chosen? Diksha in Sanskrit means transfer of spiritual knowledge.</li>
		<li>True to its name&sbquo; the company has made and continues to make tremendous contribution in the telecom space by sharing its brilliance and expertise to deliver world class products and services.</li>
		
					    		
	
		<!-- <li><a href='' title="5 officers dead in Afghanistan as John Kerry visits" data-toggle="tooltip">5 officers dead in Afghanistan as John Kerry visits</a> <span class="comments"><a href="">100 Comments</a></span> <span class="date">| January 20, 2013</span></li>
		<li><a href='' title="Oceans: Environmental victim or savior?" data-toggle="tooltip">Oceans: Environmental victim or savior? Oceans: Environmental victim or savior? Oceans: Environmental victim or savior?</a> <span class="comments"><a href="">100 Comments</a></span> <span class="date">| January 20, 2013</span></li> -->
	</ul>
</div>  

<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script> -->
<script src="customizer/jNewsbar.jquery.min.js"></script>  

   
<script src="customizer/libs/color.js"></script>
<script src="customizer/iris/iris.js"></script>

<script>
jQuery(document).ready(function(){
	$('div.news').jNewsbar({
	position : 'top',
	effect : "slideUp",
	animSpeed: 500,
	pauseTime : 2000,
	/* height : 30,
	toggleItems: 3, */
	height : 30,
	toggleItems: 3,
	onHover : function(){
		
	}
});

$('div.newstop').jNewsbar({   
	position : 'bottom',
	effect : 'slideUp',
	animSpeed: 1000,
	pauseTime : 2000,
	pauseOnHover : true,
	height : 40,     
	toggleItems: 3,
	/* maxItems : 1, */   
	theme : "blue"
});

}); 

</script>