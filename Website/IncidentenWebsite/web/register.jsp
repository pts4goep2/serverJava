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

<link href="css/bootstrap.min.css" rel="stylesheet">



<!-- CSS -->
<link href="css/bootstrap.css" rel="stylesheet">

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        <sql:setDataSource var="source" driver="com.mysql.jdbc.Driver"
                           url="jdbc:mysql://145.144.241.47:3306/cimsdb"
                           user="cims"  password="cims"/>
    </head>
    <body>
        <div class="wrapper">
        <section class="loginform cf">  
            <form id="register" form action="register.jsp" method="post" accept-charset="utf-8">  
                 <img src="resources/img/logo1.png" alt="logo">
                 <h1> Register </h1>
                    <label for="firstname">Firstname</label>  
                        <input type="text" name="firstname" placeholder="firstname" required id="firstname"> 
                    <label for="lastname">Lastname</label>  
                        <input type=text" name="lastname" placeholder="lastname" required id="lastname">
                    <label for="username">Username</label>  
                        <input type="text" name="username" placeholder="username" required id="username">  
                    <label for="password">Password</label>  
                        <input type="password" name="password" placeholder="password" required id="password">
                    <label for="email">Email</label>  
                        <input type="text" name="email" placeholder="email" required id="email"> 
                    <label for="birthdate">Birthdate</label>  
                        <input type="text" name="birthdate" placeholder="birthdate" required id="birthdate"> 
                    <label for="regio">Region</label>  
                        <input type="text" name="regio" placeholder="region" id="regio">  
                      
                        <input type="submit" value="Register">  
            </form>  
        </section>
        </div>

                    <c:if test="${param.firstname != null}">
                        <sql:update dataSource="${source}" var="data">
                            INSERT INTO person (personfirstname, personlastname, personusername, personpassword, personemail, personbirthdate) 
                            VALUES ('${param.firstname}', '${param.lastname}', '${param.username}', '${param.password}', '${param.email}', '${param.birthdate}', 1);
                        </sql:update>
                            <c:redirect url="register_process.jsp"/>
                    </c:if>
                    </body>
                    </html>
