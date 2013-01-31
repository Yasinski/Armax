<%--
  Created by IntelliJ IDEA.
  User: Panstvo
  Date: 10.09.12
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ page import="third.model.Movie, java.util.List" %>

<%
 String searchStr = (String) request.getAttribute("searchStr");
 Integer currentPage = 1;
 Integer numberOfPages = 1;
 if (request.getAttribute("currentPage") != null) {
  currentPage = (Integer) request.getAttribute("currentPage");
 }
 if (request.getAttribute("numberOfPages") != null) {
  numberOfPages = (Integer) request.getAttribute("numberOfPages");
 }
 List<Movie> movies = (List<Movie>) request.getAttribute("movies");
%>
<html>
<head>
 <title>Movies info</title>
 <meta charset="utf-8"/>
</head>
<body>
<table align="right">
 <TR>
  <TD>
   <form name="form" method="get" action="/start/">
    <input type="text" name="searchStr" value="<%=searchStr%>">
    <input type="submit" value="SEARCH">
   </form>
  </TD>
 </TR>
</table>
<table align="left">
 <TR>
  <TD>
   <a href="/start/"><input type="submit" value="HOME"></a>
  </TD>
 </TR>
</table>
<TABLE width="100%" border="1">
 <TR>
  <TH>Id</TH>
  <TH>Title</TH>
  <TH>Genre</TH>
  <TH>Release_Date</TH>
  <TH>Rating</TH>
  <TH>Director</TH>
  <TH>&nbsp;</TH>
 </TR>
 <%
  if (movies != null && !movies.isEmpty()) {
  for (Movie m : movies) {
 %>
  <TR>
   <TD><%= m.getId() %>
   </TD>
   <TD><a href="/moviePage/?Id=<%=m.getId()%>"><%=m.getTitle()%></a>
   </TD>
   <TD>
    <%
     for (String g : m.getGenres()) {%>
    <%= g + " / " %>
    <%
     }
    %>
   </TD>
   <TD><%=m.getReleaseDate()%>
   </TD>
   <TD><%=m.getRating()%>
   </TD>
   <TD>
    <%if (m.getDirector() != null) {%>
    <%=m.getDirector().getFirstName()+" "+ m.getDirector().getLastName()%>
    <%} else {%>
    "Not specified"
    <%}%>
   </TD>
   <TD>
    <a href="/updateMovieForm/?Id=<%=m.getId() %>"><input type="submit" value="Update"></a>
   </TD>
  </TR>
 <%
   }
  }
 %>
</TABLE>
<TABLE width="100%">
 <TR>
  <TD>
   <a href="/addMovieShortForm/"><input type="submit" value="Add Movie (Title only)"></a>
  </TD>
 </TR>
 <TR>
  <TD>
   <a href="/addMovieForm/"><input type="submit" value="Add Movie"></a>
  </TD>
 </TR>
 <TR>
  <TD>
   <a href="/moviesWithoutActors/"><input type="submit" value="Show Movies Without Actors"></a>
  </TD>
 </TR>
 <TR>
  <TD>
   <a href="/actorsInfo/"><input type="submit" value="Go to actors"></a>
  </TD>
 </TR>
</TABLE>
<%
 if (numberOfPages > 1) {
%>
<TABLE align="center">
 <TR>
  <TD>
   PAGES: (1-<%=numberOfPages %>)
  </TD>
 </TR>
</TABLE>
<TABLE align="center">
 <%
  for (int name = 1; name <= numberOfPages; name++) {
 %>
 <%if (name == currentPage) { %>
 <TD bgcolor="#FFFF66">
  <form>
   <form action="/start/" method="get">
    <input type="hidden" name="nextPage" value=<%=name%>>
    <input type="submit" value=<%=name%>>
   </form>
  </form>
 </TD>
 <%} else {%>
 <TD>
  <form action="/start/" method="get">
   <input type="hidden" name="nextPage" value=<%=name%>>
   <input type="submit" value=<%=name%>>
  </form>
 </TD>
 <%}%>
 <%
  }
 %>
 </TR>
</TABLE>
<%
 }
%>
</body>
</html>