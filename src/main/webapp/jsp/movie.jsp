<%@ page import="third.model.Actor" %>
<%@ page import="third.model.Director" %>
<%@ page import="third.model.Movie" %>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML >
<%
 Movie movie = (Movie) request.getAttribute("movie");
 Director director = (Director) request.getAttribute("director");
 List<Actor> actors = (List<Actor>) request.getAttribute("actors");
%>
<html >
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <title><%=movie.getTitle()%>
 </title>
 <link rel="stylesheet" type="text/css" href="F:\_DEV\Projects\max\src\main\resources\templates\style.css"/>

</head>

<body
  <%--onclick="close_popup('popup_context')"  --%>
  >

<div class="header">
 <p>BLA-BLA</p>

 <div class="button_open_test" onclick="open_popup('popup_test')">
  <h1 class="push_me">Push me!</h1>
 </div>
 <div class="button_open_context" onclick="open_popup('popup_context')">
  <h1 class="raquo">&raquo</h1>
 </div>
 <div id="popup_context">
  <div class="button_close close_context" onclick="close_popup('popup_context')">
   <h1 class="times">&times</h1>
  </div>
  <table>
   <th>
    popup
   </th>
   <tr>
    <td>aaaaaa</td>
   </tr>
   <tr>
    <td>bbbbb</td>
   </tr>
   <tr>
    <td>cccccc</td>
   </tr>
  </table>
 </div>

</div>
<div class="clear">
</div>

<div id="moviesInfo">
 <div id="mainInfo">
  <div id="title">
   <h1 id="title-1">
    <%=movie.getTitle()%>
   </h1>

   <h2 id="title-2">
    или Новые приключения Шурика
   </h2>
  </div>
  <div id="info">

   <%--<img id="poster" src="<%=movie.getPoster()%>" alt="POSTER"/>--%>
   <img id="poster" src="" alt="POSTER"/>
   <table id="data">
    <tr class="tr_data">
     <td class="td_data_1">год</td>
     <td class="td_data_2"></td>
     <td class="td_data_3"><a href="">2013</a></td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">страна</td>
     <td class="td_data_2"></td>
     <td class="td_data_3"><a href="">Германия</a>, <a href="">США</a></td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">слоган</td>
     <td class="td_data_2"></td>
     <td class="td_data_3">«Classic Tale New Twist»</td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">режиссёр</td>
     <td class="td_data_2"></td>
     <td class="td_data_3">
      <% if (director != null) { %>
      <%=director.getFirstName() + " " + director.getLastName()%>
      <%} else {%>
      Режиссёр не известен
      <%}%>
     </td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">сценарий</td>
     <td class="td_data_2"></td>
     <td class="td_data_3">Scriptor)</td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">продюсер</td>
     <td class="td_data_2"></td>
     <td class="td_data_3">Producer</td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">оператор</td>
     <td class="td_data_2"></td>
     <td class="td_data_3">Cameraman</td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">композитор</td>
     <td class="td_data_2"></td>
     <td class="td_data_3">Composer</td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">художник</td>
     <td class="td_data_2"></td>
     <td class="td_data_3">Art director</td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">монтаж</td>
     <td class="td_data_2"></td>
     <td class="td_data_3">Editor</td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">жанр</td>
     <td class="td_data_2"></td>
     <td class="td_data_3">
      <%
       for (String g : movie.getGenres()) {%>
      <%= g + " / " %>
      <%
       }
      %>
     </td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">бюджет</td>
     <td class="td_data_2" id="dollar"></td>
     <td class="td_data_3">a lot</td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">премьера (мир)</td>
     <td class="td_data_2" id="calendar_1"></td>
     <td class="td_data_3"></td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">премьера (РФ)</td>
     <td class="td_data_2" id="calendar_2"></td>
     <td class="td_data_3"></td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">релиз на Blue-Ray</td>
     <td class="td_data_2 blu-ray"></td>
     <td class="td_data_3">HZ</td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">релиз на DVD</td>
     <td class="td_data_2" id="dvd"></td>
     <td class="td_data_3">HZ</td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">возраст</td>
     <td class="td_data_2"></td>
     <td class="td_data_3"></td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">рейтинг МРАА</td>
     <td class="td_data_2"></td>
     <td class="td_data_3"></td>
    </tr>
    <tr class="tr_data">
     <td class="td_data_1">время</td>
     <td class="td_data_2" id="time"></td>
     <td class="td_data_3"></td>
    </tr>
   </table>
  </div>
 </div>


 <div id="actorsInfo">
  <p id="roles"> В главных ролях: </p>

  <p class="actors">
   <%
    if (actors != null && !actors.isEmpty()) {
     for (Actor actor : actors) {%>
   <%= actor.getFirstName() + " " + actor.getLastName()%>
   <br>
   <%
    }
   } else { %>
   Список актеров пуст(
   <%
    }
   %>
  </p>
 </div>
</div>
<div id="popup_test">
 <div class="button_close" onclick="close_popup('popup_test')">
  <h1 class="times">&times</h1>
 </div>
</div>


</body>
<script type="text/javascript">
 function open_popup(id) {
  document.getElementById(id).style.display = 'block';
 }
</script>
<script type="text/javascript">
 function close_popup(id) {
//   if((document.getElementById(id).style.display == 'display')&&(document.getElementById(id).onmouseout)){
 document.getElementById(id).style.display = 'none';
//   }
 }
 function onBodyClick(event) {
    alert(event.target.childNodes[0].textContent)  ;
 }
 document.getElementsByTagName("body")[0].onclick= onBodyClick;

</script>
</html>