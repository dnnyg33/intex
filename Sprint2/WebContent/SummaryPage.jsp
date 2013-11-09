<jsp:directive.page import="java.util.*"/>
<jsp:directive.page import ="intex.BusinessObjects.*"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en-US" xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head>
	<title>Order Summary</title>
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
				<h1 id="logo"><a class="notext" href="index.jsp" title="myStuff">myStuff</a></h1>
				<div id="top-nav">
					<ul>
						<li class="active"><a href="index.jsp" title="Home"><span>Home</span></a></li>
						<li><a href="#" title="Promotions &amp; News"><span>Promotions &amp; News</span></a></li>
						<li><a href="#" title="Contact"><span>Contact</span></a></li>
					</ul>
				</div>
				<div class="cl">&nbsp;</div>
				<p id="cart"><span class="profile">Welcome, <title="Profile Link">Guest</a>.</span> &nbsp;<a href="register.jsp" title="register">Register</a> &nbsp;&nbsp; 
				 <a href="login.jsp" title="Login">Login</a></p>
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
						<form method="get" action="/search" id="search">
							<input name="q" type="text" size="40" placeholder="Search..." />
							</form>
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
				
				<div class="cl">&nbsp;</div>
				<div class="slider-nav">
					
				</div>
			</div>
			<!-- End Shell -->
		</div>
		<!-- End Slider -->
		
		
		<!-- Begin Main -->
		<center>
		<div id="main" class="shell">
		<h2>Order Summary</h2>				
				<%Customer c = (Customer)session.getAttribute("Customer");
				CProduct cp = (CProduct) session.getAttribute("CProduct");
				%>
					<table border="1">
						<tbody>
							<tr>
								<td><%=c.getFirstName()+" "+c.getLastName() %></td>
								<td><%=cp.getModel() %></td>
								</tr>
								<tr>
									<td><%=c.getAddress()+" \n"+c.getCity()+" "+c.getState()+", "+c.getZip() %></td>
									<td>$<%=cp.getPrice() %></td>
									</tr>
									<tr><%=(String) session.getAttribute("serial")%></tr>
						</tbody>
					</table>
					<br>
					<br>
						<form  action="actions.Checkout.action" method="post"> 
							<input class="buyButton" type="submit" name="submit" value="Complete Order" id="submit">
						</form>	
		 </center>
		 
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