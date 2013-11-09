<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en-US" xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head>
	<title>Register</title>
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
</head>
<body>
	<!-- Begin Wrapper -->
	<div id="wrapper">
		<!-- Begin Header -->
		<div id="header">
			<!-- Begin Shell -->
			<div class="shell">
				<h1 id="logo"><a class="notext" href="index.jsp" title="iShop">iShop</a></h1>
				<div id="top-nav">
					<ul>
						<li class="active"><a href="index.jsp" title="Home"><span>Home</span></a></li>
					
					</ul>
				</div>
				<div class="cl">&nbsp;</div>
				
				
			</div>
			<!-- End Shell -->
		</div>
		<!-- End Header -->
		<!-- Begin Navigation -->
		<div id="navigation">
			<!-- Begin Shell -->
			
			<!-- End Shell -->
		</div>
		<!-- End Navigation -->
		<!-- Begin Slider -->
		<div id="slider">
			<!-- Begin Shell -->
			<div class="shell">
				<section class="main" >
				<form class="form-2" action="actions.Register.action" method="post">
					<h1><span class="log-in">Register</span></h1>
					
					<p class="float">
					
						<input type="text" name="email" placeholder="Email">
						
					</p>
					
					<p class="float">
					
						<input type="password" name="password" placeholder="Password" class="showpassword">
						
					</p>
					
					<p class="float">
					
						<input type="text" name="firstName" placeholder="First Name">
						
					</p>
					
					<p class="float">
					
						<input type="text" name="lastName" placeholder="Last Name">
						
					</p>
					
					<p class="float2">
					
						<input type="text" name="address" placeholder="Address">
						
					</p>
					
					<p class="float">
					
						<input type="text" name="city" placeholder="City">
						
					</p>
				
					<p class="float">

					
						<input type="text" name="state" placeholder="State (ex. UT)">
						
					</p>
					
					<p class="float">
					
						<input type="text" name="zip" placeholder="Zip">
						
					</p>
					
					<p class="float">
					
						<input type="text" name="phone" placeholder="Phone">
						
					</p>
					
					<p>
					<h1> <span class="log-in">Payment Information</span></h1>
					</p>
					
					<p class="float2">
					
						<input type="text" name="creditCardNumber" placeholder="Credit Card Number">
						
					</p>
					
					<p class="float">
					
						<input type="text" name="cardType" placeholder="Card Type">
						
					</p>
					
					<p class="float">
					
						<input type="text" name="securityNumber" placeholder="Security Code">
						
					</p>
					
					<p class="float">
					
						<input type="text" name="exMonth" placeholder="Month (ex. 02)">
						
					</p>
					
					<p class="float">
					
						<input type="text" name="exYear" placeholder="Year (ex. 13)">
						
					</p>
					


					<p class="clearfix"> 
						<%
	if (request.getAttribute("message") != null) {
%>
	<p>
		<%=request.getAttribute("message")%>
	</p>
	<%
		}
	%>
						<input type="submit" value="Register">
					</p>
					<p>
					</p>
				</form>​​
			</section>
			
</body>
</html>