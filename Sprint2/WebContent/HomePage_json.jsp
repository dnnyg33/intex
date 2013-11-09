<%@page contentType="application/json" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.*"/>
{
"results": [ <%=session.getAttribute("string")%> ]
}