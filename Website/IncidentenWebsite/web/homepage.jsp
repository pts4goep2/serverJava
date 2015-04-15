<%-- 
    Document   : index
    Created on : Mar 11, 2015, 1:35:03 PM
    Author     : richard en nadiv
--%>

<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script
        src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>

        <sql:setDataSource var="source" driver="com.mysql.jdbc.Driver"
                           url="jdbc:mysql://145.144.241.41:3306/mydb"
                           user="cims"  password="cims"/>

        <sql:query dataSource="${source}" var="data">
            SELECT * FROM calamity;
        </sql:query>

        <c:forEach var="coords" begin="0" items="${data.rows}">            
            
        </head>
        <body >
            <h1> ${coords.name}</h1> </c:forEach>
    </body>
</html>
