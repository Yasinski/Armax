<%@ page import="com.imhos.security.server.serializer.AuthenticationSerializer" %>
<%@ page import="com.imhos.security.server.service.social.SocialResponseBuilder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: Panstvo
  Date: 25.02.13
  Time: 5:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <%
        Authentication authentication = (Authentication) request.getUserPrincipal();

        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
        SocialResponseBuilder socialResponseBuilder =
                (SocialResponseBuilder) context.getBean("socialResponseBuilder");
        if (authentication != null) {
            AuthenticationSerializer authenticationSerializer =
                    (AuthenticationSerializer) context.getBean("authenticationSerializer");
            String authenticationJSON = authenticationSerializer.serialize(authentication);
            request.setAttribute("authenticationJSON", authenticationJSON);
        } else {
            request.setAttribute("authenticationJSON", "");
        }
    %>
</head>
<body>
<script>
    window.authentication = '${authenticationJSON}';
    <%
      response.getWriter().print(socialResponseBuilder.buildResponse(authentication));
      %>
</script>
</body>
</html>