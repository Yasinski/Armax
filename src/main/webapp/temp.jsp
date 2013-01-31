<%@ page language="java" import="java.sql.*" %>
<html>
<head>
<title></title>
</head>

<body>
<form method="post" action="/searchbook/searchbook">

<table>

<tr>

<td>ISBN Code:</td>

<td><input type="text" name="isbncode" size="10" /></td>

<td>Book Category:</td>

<td><select name="bookcategories">

<option value="-1">-Select Category-</option>

<option value="java">java</option>

<option value="c">c</option>

<option value="c++">c++</option>

<option value="php">php</option>

</select></td>

<td><input type="submit" value="Search Book" /></td>

</tr>

 <tr><input onfocus=""  value="" /><td>

 </td></tr>

</table>

</form>
</body>
</html>