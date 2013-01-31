<%--
  Created by IntelliJ IDEA.
  User: Panstvo
  Date: 29.01.13
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
 <title></title>
</head>
<body>

<form name="form" method="post" action="/userAdd/" onSubmit="return validateForm()">
 <table border="2" align="CENTER">
  <TR align="CENTER">
   <TD>INPUT DATA:</TD>
  </TR>
  <TR>
   <TH>
    Username:
   </TH>
  </TR>
  <TR>
   <TH>
    Password:
   </TH>
  </TR>
  <TR>
   <TD>
    <input name="username" type="text">
   </TD>
  </TR>
  <TR>
   <TD>
    <input name="password" type="text">
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