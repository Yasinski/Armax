<%--
  Created by IntelliJ IDEA.
  User: Panstvo
  Date: 10.12.12
  Time: 16:41
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
   else if (document.form.genre.value == "") {
    alert("Название жанра должно быть внесено!");
    document.form.genre.focus();
    return false;
   }
   else if (document.form.dirName.value == "") {
    alert("Имя режиссера должно быть внесено!");
    document.form.dirName.focus();
    return false;
   }
   else if (document.form.actFirstName.value == "") {
    alert("Имя актера должно быть внесено!");
    document.form.actFirstName.focus();
    return false;
   }
   else if (document.form.actLastName.value == "") {
    alert("Фамилия актера должна быть внесена!");
    document.form.actLastName.focus();
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
<form name="form" method="post" action="/movieAdd/" onSubmit="return validateForm()">
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
  <TR>
   <TH>
    Genre:
   </TH>
  </TR>
  <TR>
   <TD>
    <input name="genre" type="text">
   </TD>
  </TR>
  <TR>
   <TH>
    Director:
   </TH>
  </TR>
  <TR>
   <TD>
    First Name:
    <br>
    <input name="dirFName" type="text">
   </TD>
  </TR>
  <TR>
   <TD>
    Last Name:
    <br>
    <input name="dirLName" type="text">
   </TD>
  </TR>
  <TR>
   <TH>
    Actor:
   </TH>
  </TR>
  <TR>
   <TD>
    First Name:
    <br>
    <input name="actFirstName" type="text">
   </TD>
  </TR>
  <TR>
   <TD>
    Last Name:
    <br>
    <input name="actLastName" type="text">
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