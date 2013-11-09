<jsp:directive.page import="java.util.*"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en-US" xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head>
	<title>Login</title>
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
				<section class="main">
				<form class="form-2" method="post" action="actions.Login.action">
					<h1><span class="log-in">Log in</span></h1>
					<p class="float">
					

						<input type="text" name="username" placeholder="Email">
						
					</p>
				
					<p class="float">

						<input type="password" name="password" placeholder="Password" class="showpassword">
					</p>
					<p>	
					<%
							if (request.getAttribute("retry") != null) {
								out.println("<p>");
								out.println(request.getAttribute("retry"));
								out.println("</p>");
								}//if


								%>
								</p>
					<p class="clearfix"> 
				 
						<input type="submit" name="mysubmitter" value="Log in">
					</p>
				</form>​​
			</section>
			
</body>
</html>