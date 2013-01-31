<%@ page import="third.model.Actor" %>
<%@ page import="third.model.Director" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Panstvo
  Date: 19.12.12
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
 Actor actor = (Actor) request.getAttribute("actor");
 List<Director> directors = (List<Director>) request.getAttribute("directors");
%>
<head>
 <title></title>
</head>
<body>
<%
 if (actor != null) {
%>
List of Directors for actor
<%=actor.getFirstName() + " " + actor.getLastName()%>
<%
 }
%>
<%
 if (directors != null && !(directors.isEmpty())) {
%>
<table width="100%" border="1">

 <tr>
  <th>
   id
  </th>
  <th>
   First Name
  </th>
  <th>
   Last Name
  </th>
  <th>
   List of movies
  </th>
  <th>
   List of actors
  </th>
 </tr>
 <%
  for (Director d : directors) {
 %>
 <tr>
  <td>
   <%=d.getId()%>
  </td>
  <td>
   <%=d.getFirstName()%>
  </td>
  <td>
   <%=d.getLastName()%>
  </td>
  <td>
   <a href="/directorMoviesList/?Id=<%=d.getId() %>"><input type="submit" value="Show movies list"></a>
  </td>
  <td>
   <a href="/directorActorsList/?Id=<%=d.getId() %>"><input type="submit" value="Actors list"></a>
  </td>
 </tr>
 <%
  }%>
</table>
<%
} else {%>
<h3> There is not any data for your request(</h3>
<%
 }
%>
</body>
</html>