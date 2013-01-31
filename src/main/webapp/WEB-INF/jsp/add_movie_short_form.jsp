<%--
  Created by IntelliJ IDEA.
  User: Panstvo
  Date: 17.12.12
  Time: 8:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
 <title>Data Input Form</title>
 <script>
  function validateForm() {
   if (document.form.title.value == "") {
    alert("Название фильма должно быть внесено!");
    document.form.title.focus();
    return false;
   }
  }
 </script>
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
<form name="form" method="post" action="/movieAddShort/" onSubmit="return validateForm()">
 <table border="2" align="CENTER">
  <TR align="CENTER">
   <TD>INPUT DATA:</TD>
  </TR>
  <TR>
   <TH>
    Title:
   </TH>
  </TR>
  <TR>
   <TD>
    <input name="title" type="text">
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