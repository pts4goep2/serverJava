<%-- 
    Document   : inlog
    Created on : Apr 15, 2015, 12:03:30 PM
    Author     : Indra
--%>
<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">



<!-- CSS -->
<link href="css/bootstrap.css" rel="stylesheet">
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
        <div class="wrapper">
        <section class="loginform cf">  
            <form id="login" form action="inlog.jsp" method="post" accept-charset="utf-8">  
                <img src="resources/img/logo1.png" alt="logo">
                <h1> Login </h1>
                   <label for="username">Username</label>  
                        <input type="text" name="username" placeholder="username" required> 
                    <label for="password">Password</label>  
                        <input type="password" name="password" placeholder="password" required> 
                      
                        <input type="submit" value="Login"> 
                 
            </form>  
        </section>
        </div>


        <c:if test="${param.username != null}">

            <sql:setDataSource var="source" driver="com.mysql.jdbc.Driver"
                           url="jdbc:mysql://145.144.241.47:3306/cimsdb"
                           user="cims"  password="cims"/>

            <sql:query dataSource="${source}" var="data">
                SELECT * FROM person WHERE personusername = '${param.username}' AND personpassword = '${param.password}'
            </sql:query>

            <c:forEach var="persoon" begin="0" items="${data.rows}">
                <c:if test="${persoon.username == param.username && persoon.password == param.password}">
                    <c:redirect url="homepage.jsp"/>
                </c:if>
            </c:forEach>
        </c:if>
    </body>
</html>
