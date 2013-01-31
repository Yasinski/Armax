<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
 <title>Registration</title>
</head>
<body>
<div class="login_form">
 <form method="POST" action="/registration/" >
  <table>
   <th>Введите логин и пароль: </th>
   <tr>
    <td>Login</td>
    <td><input type="text" name="username"/></td>
   </tr>
   <tr>
    <td>Password</td>
    <td><input type="password" name="password"/></td>
   </tr>
   <tr>
    <td><input type="submit" value="Registrate"/></td>
    <td><input type="reset" value="Reset"/></td>
   </tr>
  </table>
  <input type="hidden" value="ROLE_USER" name="authority">
 </form>
</div>
</body>
</html>