<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:directive.page import="java.util.*"/>
<jsp:directive.page import ="intex.BusinessObjects.*"/>

<jsp:include page="/header.jsp">
  <jsp:param name="title" value="MyStuff Company" />
</jsp:include>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MyStuff Company</title>
</head>
<body>
<div align="right">Welcome 
<%Customer c = (Customer)session.getAttribute("Customer");%>
<%=c.getFirstName()+" "+c.getLastName()%>
</div>
<div align="center"> <font size=7>MyStuffCompany</font></div>


<div align="center">
<form  action="actions.HomePage.action" method="get"> 
<input type="text" size="40" id="search" name="search"> 

<input  type="button" value="Search" id="submitButton" > 
</form> 
</div> 
<div align="center">

<form action="">
<select name="Store">
<% List<Store> stores = BusinessObjectDAO.getInstance().searchForAll("Store");
for (Store s : stores){// populate store list dynamically
	s.getLocation();
 
	//Store all the values in the bean and now close the scriptlet 
	//Put the code for select box like 
	%>
	<option value= <%=s.getLocation() %> > <%=s.getLocation() %> </option>
	<% }%>

<option value="volvo">Volvo</option>
<option value="saab">Saab</option>
<option value="fiat">Fiat</option>
<option value="audi">Audi</option>
</select>
</form>
</div>
<!-- We'll add the history here -->
<div align="center">
<ul id="results">
</ul>
</div>
</body>
</html>

<script type="text/javascript">
  $(function() {  // this function runs as soon as the web page is "ready"
    /** 
    * Submits the guess to the server.  This is the event code, very much
    * like an actionPerformed in Java.
    */
    $('#submitButton').click(function() {  // define the handler function for click event inline (it doesn't need a name because we're just using it one time)
      // get the value of the search element
      var searchValue = $("#search").val();
      
      // send to the server (this is relative to our current page)
      $.ajax({
			url : "actions.HomePage.action",
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


<jsp:include page="/footer.jsp"/>
