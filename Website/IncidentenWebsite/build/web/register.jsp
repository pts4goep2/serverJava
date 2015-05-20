<%-- 
    Document   : register
    Created on : Apr 22, 2015, 12:42:10 PM
    Author     : Indra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        <sql:setDataSource var="source" driver="com.mysql.jdbc.Driver"
                           url="jdbc:mysql://145.144.240.156:3306/mydb"
                           user="cims"  password="cims"/>
    </head>
    <body>
        <section class="loginform cf">  
            <form name="register" form action="register.jsp" method="post" accept-charset="utf-8">  
                <ul>  
                    <li><label for="firstname">Voornaam</label>  
                        <input type="text" name="firstname" required id="firstname"></li>  
                    <li><label for="lastname">Achternaam</label>  
                        <input type=text" name="lastname" required id="lastname"></li>
                    <li><label for="username">Username</label>  
                        <input type="text" name="username" required id="username"></li>  
                    <li><label for="password">Password</label>  
                        <input type="password" name="password" required id="password"></li>  
                    <li><label for="email">Email</label>  
                        <input type="text" name="email" required id="email"></li>  
                    <li><label for="birthdate">Geboortedatum</label>  
                        <input type="text" name="birthdate" required id="birthdate"></li>  
                    <li><label for="regio">Regio</label>  
                        <input type="text" name="regio" id="regio"></li>  
                    <li>  
                        <input type="submit" value="Register"></li>  
                    </form>  
                </section>

                    <c:if test="${param.firstname != null}">
                        <sql:update dataSource="${source}" var="data">
                            INSERT INTO civilian (first_name, last_name, username, password, email, birthdate, idaddress) 
                            VALUES ('${param.firstname}', '${param.lastname}', '${param.username}', '${param.password}', '${param.email}', '${param.birthdate}', 1);
                        </sql:update>
                            <c:redirect url="register_process.jsp"/>
                    </c:if>
                    </body>
                    </html>