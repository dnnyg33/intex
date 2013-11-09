<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<jsp:directive.page import="java.util.*"/>
<jsp:directive.page import ="intex.BusinessObjects.*"/>

<jsp:include page="/header.jsp">
  <jsp:param name="title" value="MyStuff Company" />
</jsp:include>

<html lang="en-US" xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head>
	<title>myStuff Online Store</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<link rel="shortcut icon" href="css/images/favicon.ico" />
	<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
	
	<script src="js/jquery-1.6.2.min.js" type="text/javascript" charset="utf-8"></script>
	<!--[if IE 6]>
		<script src="js/DD_belatedPNG-min.js" type="text/javascript" charset="utf-8"></script>
	<![endif]-->
	<script src="js/cufon-yui.js" type="text/javascript"></script>
	<script src="js/Myriad_Pro_700.font.js" type="text/javascript"></script>
	<script src="js/jquery.jcarousel.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/functions.js" type="text/javascript" charset="utf-8"></script>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	
	
	
</head>
<body>

<%
//if they just registered, show alert
try{Boolean justRegistered= (Boolean)request.getAttribute("checkEmailAlert");
if(justRegistered){
%><script>alert("Check your f-ing email!")</script><% 
}}catch(Exception e){
	//nothing
}

Customer c = (Customer)session.getAttribute("Customer");
if(c==null){
c= BusinessObjectDAO.getInstance().create("Customer");
c.setFirstName("Guest");}%>


	<!-- Begin Wrapper -->
	<div id="wrapper">
		<!-- Begin Header -->
		<div id="header">
			<!-- Begin Shell -->
			<div class="shell">
				<h1 id="logo"><a class="notext" href="index.jsp" title="myStuff">myStuff</a></h1>
				<div id="top-nav">
					<ul>
						<li class="active"><a href="index.jsp" title="Home"><span>Home</span></a></li>
						<li><a href="#" title="Promotions &amp; News"><span>Promotions &amp; News</span></a></li>
						<li><a href="mailto:jon@tengentllc.com" title="Contact"><span>Contact</span></a></li>
					</ul>
				</div>
				<div class="cl">&nbsp;</div>
				<p id="cart"><span class="profile">Welcome, <title="Profile Link"><%=c.getFirstName() %></a>.</span> &nbsp;<a href="actions.Register.action" title="register">Register</a> &nbsp;&nbsp; 
				 <a href="actions.Login.action" title="Login">Login</a></p>
			</div>
			<!-- End Shell -->
		</div>
		<!-- End Header -->
		<!-- Begin Navigation -->
		<div id="navigation">
			<!-- Begin Shell -->
			<div class="shell">
				<ul>
					
					<li><a href="#" title="SLR">Digital SLR</a>
					<div class="dd">
							<ul>
								<li><a href="#" title="SLR1">Canon</a></li>
								<li><a href="#" title="SLR2">Nikon</a></li>
								<li><a href="#" title="SLR3">Sony</a></li>
								<li><a href="#" title="SLR4">Panasonic</a></li>
							</ul>
						</div></li>
					<li><a href="#" title="Point">Digital Point and Click</a>
					<div class="dd">
							<ul>
								<li><a href="#" title="Point1">Canon</a></li>
								<li><a href="#" title="Point2">Nikon</a></li>
								<li><a href="#" title="Point3">Kodak</a></li>
								<li><a href="#" title="Point4">Polaroid</a></li>
								<li><a href="#" title="Point5">Olympus</a></li>
								<li><a href="#" title="Point6">Panasonic</a></li>
								<li><a href="#" title="Point7">Sony</a></li>
							</ul>
						</div></li>
					<li>
						<a href="#" title="Accessories">Accessories</a>
					</li>
					
					<li>
					
						<input type="text" size="10" id="search" style="width: 257px; "> 
						<input id="submitButton" type="button" value="Search">
						
					</li>
				</ul>
				<div class="cl">&nbsp;</div>
			</div>
			<!-- End Shell -->
		</div>
		<!-- End Navigation -->
		<!-- Begin Slider -->
		<div id="slider">
			<!-- Begin Shell -->
			<div class="shell">
				<ul class="slider-items">
					<li>
						<img src="css/images/slideCam1.png" alt="Slide Image" />
						<div class="slide-entry">
							<h2><span>CANON</span>7D</h2>
							<h6>Snap your photos like a pro.<br /> You'll be glad you did.</h6>
							
						</div>
					</li>
					<li>
						<img src="css/images/slideCam2.png" alt="Slide Image" />
						<div class="slide-entry">
							<h4><span>POWERFUL</span><span class="small">&amp;</span>PRECISE</h4>
							<p>The Nikon s6500 is beautifully crafted<br />for versatility &amp; performance. <br /> Don't miss it!</p>
							
						</div>
					</li>
					<li>
						<img src="css/images/slideCam3.png" alt="Slide Image" />
						<div class="slide-entry">
							<h3>ACCESSORIES</h3> 
							<h4 class="short">CASES &amp; <span> Tripods</span><br> <span> &nbsp; SD Cards &amp; </span> CABLES</h4>
							<h5>Whatever you need, WE'VE GOT IT!</h5>
							
						</div>
					</li>
				</ul>
				<div class="cl">&nbsp;</div>
				<div class="slider-nav">
					
				</div>
			</div>
			<!-- End Shell -->
		</div>
		<!-- End Slider -->
		<!-- Begin Main -->
		<div id="main" class="shell">
			<!-- Begin Content -->
			<div id="content">
				<div class="post" id="results">
					Results Here
					
					
				</div>
			</div>
			<!-- End Content -->
			<!-- Begin Sidebar -->
			<div id="sidebar">
				
			</div>
			<!-- End Sidebar -->
			<div class="cl">&nbsp;</div>
			
			</div>
			<!-- End Products Slider -->
		</div>
		<!-- End Main -->
		<!-- Begin Footer -->
		<div id="footer">
			<div class="boxes">
				<!-- Begin Shell -->
				<div class="shell">
					<div class="box post-box">
						<h2>About myStuff</h2>
						<div class="box-entry">
							<img src="css/images/smallLogo.png" alt="myStuff" />
							<p>Nulla porttitor pretium mattis. Mauris lorem massa, ultricies non mattis bibendum, semper ut erat. Morbi vulputate placerat ligula. Fusce <br />convallis, nisl a pellentesque viverra, ipsum leo sodales sapien, vitae egestas dolor nisl eu tortor. Etiam ut elit vitae nisl tempor tincidunt. Nunc sed elementum est. Phasellus sodales viverra mauris nec dictum. Fusce a leo libero. Cras accumsan enim nec massa semper eu hen-drerit nisl faucibus. Sed lectus ligula, consequat eget bibendum eu, consequat nec nisl. In sed consequat elit. Praesent nec iaculis sapien. Curabitur gravida pretium tincidunt. </p>
							<div class="cl">&nbsp;</div>
						</div>
					</div>
					<div class="box social-box">
						
						<div class="cl">&nbsp;</div>
					</div>
					<div class="box">
						<h2>Information</h2>
						<ul>

							<li><a href="#" title="Privacy Policy">Privacy Policy</a></li>
							<li><a href="#" title="Terms &amp; Conditions">Terms &amp; Conditions</a></li>
							<li><a href="#" title="Contact Us">Contact Us</a></li>
							<li><a href="login.jsp" title="Log In">Log In</a></li>
							<li><a href="#" title="Account">Account</a></li>
							
						</ul>
					</div>
										<div class="cl">&nbsp;</div>
				</div>
				<!-- End Shell -->
			</div>
			<div class="copy">
				<!-- Begin Shell -->
				<div class="shell">
					<div class="carts">
						<ul>
							<li><span>We accept</span></li>
							<li><a href="#" title="PayPal"><img src="css/images/cart-img1.jpg" alt="PayPal" /></a></li>
							<li><a href="#" title="VISA"><img src="css/images/cart-img2.jpg" alt="VISA" /></a></li>
							<li><a href="#" title="MasterCard"><img src="css/images/cart-img3.jpg" alt="MasterCard" /></a></li>
						</ul>
					</div>
					
					<div class="cl">&nbsp;</div>
				</div>
				<!-- End Shell -->
			</div>
		</div>
		<!-- End Footer -->
	</div>
	<!-- End Wrapper -->
</body>
</html>

<script type="text/javascript">
	$(function() { // this function runs as soon as the web page is "ready"
		/** 
		 * Submits the guess to the server.  This is the event code, very much
		 * like an actionPerformed in Java.
		 */
		$('#submitButton')
		
				.click(
						function() { // define the handler function for click event inline (it doesn't need a name because we're just using it one time)
							// get the value of the guess element
							var searchValue = $("#search").val();

							// send to the server (this is relative to our current page)
							$
									.ajax({
										url : "actions.Index.action",
										data : { // this is an embedded JSON object we'll send to server
											search : searchValue,
										},
										dataType : 'json', // tell JQuery to expect JSON back from the server
										success : function(ret) { // this is called as soon as the server returns
											$("#results").html(""); // clear the history <li> children (this isn't efficient, just an example)
											console.log(ret.results);
											for ( var i = 0; i < ret.results.length; i++) {
												console.log("appending");
												$("#results").append(
														"<li>" + ret.results[i]
																+ "</li>"); // reappend the entire history
											}//for
										}//success
									});//ajax

						});//click
	});//ready
</script>