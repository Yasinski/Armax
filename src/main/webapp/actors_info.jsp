<%@ page import="third.model.Actor" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Panstvo
  Date: 12.12.12
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
 <title>Actors' info</title>
</head>
<body>
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
   List of directors
  </th>
 </tr>
 <%
  List<Actor> actors = (List<Actor>) request.getAttribute("actors");
  if (actors != null && !actors.isEmpty()) {
   for (Actor act : actors) { %>
 <tr>
  <td><%=act.getId()%>
  </td>
  <td><%=act.getFirstName()%>
  </td>
  <td><%=act.getLastName()%>
  </td>
  <td><a href="/actorMoviesList/?Id=<%=act.getId() %>">Show movies list</a></td>
  <td><a href="/actorDirectorsList/?Id=<%=act.getId() %>">Show directors list</a></td>
 </tr>
 <%
  }
 } else {
 %>
 Список актеров пуст!
 <%
  }
 %>

</table>
<table>
 <TR>
  <TD>
   <form action="/start/" method="POST">
    <input type="submit" value="Go to movies">
   </form>
  </TD>
 </TR>
 <TR>
  <TD>
   <a href="/add_actor_form.jsp"><input type="submit" value="Add actor"></a>
  </TD>
 </TR>
</table>

</body>
</html>