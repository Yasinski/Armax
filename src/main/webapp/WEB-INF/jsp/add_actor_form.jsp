<%--
  Created by IntelliJ IDEA.
  User: Panstvo
  Date: 05.01.13
  Time: 6:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
 <title></title>
</head>
<body bgcolor="#CCCC99">
<% String errorMessage = (String) request.getAttribute("error");
 if (errorMessage != null) { %>
<table align="center">
 <TR>
  <TD>
   <%= errorMessage %>
  </TD>
 </TR>
</table>
<%
 }
%>
<form name="form" method="post" action="/actorAdd/">
 <table border="2" align="CENTER">
  <TR align="CENTER">
   <TD>INPUT DATA:</TD>
  </TR>
  <TR>
   <TH>
    Actor
   </TH>
  </TR>
  <TR>
   <TH>
    First Name
   </TH>
  </TR>
  <TR>
   <TD>
    <input name="firstName" type="text">
   </TD>
  </TR>
  <TR>
   <TH>
    Last Name
   </TH>
  </TR>
  <TR>
   <TD>
    <input name="lastName" type="text">
   </TD>
  </TR>
  <TR align="CENTER">
   <TD>
    <input type="submit" value="ADD">
   </TD>
  </TR>
 </table>
</form>
</body>
</html>