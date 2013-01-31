<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf8">
 <title>Login</title>
 <link rel="stylesheet" type="text/css" href="../css/login.css"/>
 <%--<script>--%>
  <%--function forceHttpsOnSubmit(objForm) {--%>
   <%--objForm.action = objForm.action.replace("http:", "https:").replace("localhost:8080", "localhost:8443");--%>
  <%--}--%>
 <%--</script>--%>
</head>
<body>

<c:if test="${not empty param.error}">
 <p class="loginerror"> Loginerror: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} </p>
</c:if>

<div class="login_form">
 <form method="POST" action="<c:url value="/j_spring_security_check" /> "
       <%--onsubmit="forceHttpsOnSubmit(this)" --%>
   >
  <table>
   <tr>
    <td>Login</td>
    <td><input type="text" name="j_username"/></td>
   </tr>
   <tr>
    <td>Password</td>
    <td><input type="password" name="j_password"/></td>
   </tr>
   <tr>
    <td>Remember me</td>
    <td><input type="checkbox" name="_spring_security_remember_me"/></td>
   </tr>
   <tr>
    <td><input type="submit" value="Login"/></td>
    <td><input type="reset" value="Reset"/></td>
   </tr>
  </table>
 </form>
</div>
</body>
</html>