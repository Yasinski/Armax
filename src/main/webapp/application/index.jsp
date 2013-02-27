<%@ page import="com.imhos.security.server.serializer.AuthenticationSerializer" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE HTML >
<html>
<head>

    <!--                                           -->
    <!-- Any title is fine                         -->
    <!--                                           -->
    <title>Wrapper HTML for App</title>

    <link type="text/css" rel="stylesheet" href="application.css">

    <!--                                            -->
    <!-- This script is required bootstrap stuff.   -->
    <!--                                            -->
    <script type="text/javascript" language="javascript"
            src="application.nocache.js"></script>
</head>

<!--                                           -->
<!-- The body can have arbitrary html, or      -->
<!-- you can leave the body empty if you want  -->
<!-- to create a completely dynamic ui         -->
<!--                                           -->
<body>

<h1>Sample Application</h1>

<p>
    This is an example of a host page for the App application.
    You can attach a Web Toolkit module to any HTML page you like,
    making it easy to add bits of AJAX functionality to existing pages
    without starting from scratch.
</p>

<%--<script type="text/javascript">--%>
<%--function loggedin (username) {--%>
<%--alert ("Hello from parent!");--%>
<%--}--%>
<%--function doStuff () {--%>
<%--var w = window.open("testa.html");--%>
<%--}--%>
<%--</script>--%>
<table align="center">
    <tr>
        <td>
            <div id="slot1"></div>
            <div id="slot2"></div>
        </td>
    </tr>
</table>
<% Authentication authentication = (Authentication) request.getUserPrincipal();
    if (authentication != null) {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
        AuthenticationSerializer authenticationSerializer = (AuthenticationSerializer) context.getBean("authenticationSerializer");
        String authenticationJSON = authenticationSerializer.serialize(authentication);
        request.setAttribute("authenticationJSON", authenticationJSON);
    } else {
        request.setAttribute("authenticationJSON", "");
    }
%>
<script>
    window.authentication = '${authenticationJSON}'
</script>

<br>
<a href="/connect/connect/facebook">connect facebook</a>
<br>
<a href="/connect/connect/twitter">connect twitter</a>
<%--<br>--%>
<%--<a href="/connect/signin/facebook">signin facebook</a>--%>
<%--<br>--%>
<%--<a href="/connect/signin/twitter">signin twitter</a>--%>
</body>
</html>