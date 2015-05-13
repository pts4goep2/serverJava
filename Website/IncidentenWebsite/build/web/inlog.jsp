<%-- 
    Document   : inlog
    Created on : Apr 15, 2015, 12:03:30 PM
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
    </head>
    <body>
        <section class="loginform cf">  
            <form name="login" form action="inlog.jsp" method="post" accept-charset="utf-8">  
                <ul>  
                    <li><label for="username">Username</label>  
                        <input type="text" name="username" required></li>  
                    <li><label for="password">Password</label>  
                        <input type="password" name="password" placeholder="password" required></li>  
                    <li>  
                        <input type="submit" value="Login"></li>  
                </ul>  
            </form>  
        </section>


        <c:if test="${param.username != null}">

            <sql:setDataSource var="source" driver="com.mysql.jdbc.Driver"
                               url="jdbc:mysql://145.144.241.80:3306/mydb"
                               user="cims"  password="cims"/>

            <sql:query dataSource="${source}" var="data">
<<<<<<< HEAD
                SELECT * FROM civilian WHERE USERNAME = '${param.username}' AND PASSWORD = '${param.password}'
=======
                SELECT * FROM civilian WHERE USERNAME = ${param.username} AND PASSWORD = ${param.password}"
>>>>>>> origin/master
            </sql:query>

            <c:forEach var="persoon" begin="0" items="${data.rows}">
                <c:if test="${persoon.username == param.username && persoon.password == param.password}">
                    <c:redirect url="homepage.jsp"/>
                </c:if>
            </c:forEach>
        </c:if>
    </body>
</html>
