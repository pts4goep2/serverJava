<%-- 
    Document   : inlog
    Created on : Apr 15, 2015, 12:03:30 PM
    Author     : Indra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
            <section class="loginform cf">  
<form name="login" action="submit" method="get" accept-charset="utf-8">  
    <ul>  
        <li><label for="usermail">Email</label>  
        <input type="email" name="usermail" placeholder="yourname@email.com" required></li>  
        <li><label for="password">Password</label>  
        <input type="password" name="password" placeholder="password" required></li>  
        <li>  
        <input type="submit" value="Login"></li>  
    </ul>  
</form>  
</section>
    </body>
</html>
