<%-- 
    Document   : index
    Created on : Mar 11, 2015, 1:35:03 PM
    Author     : rich
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
        src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>

        <sql:setDataSource var="source" driver="com.mysql.jdbc.Driver"
                           url="jdbc:mysql://localhost/coordinatentestdb"
                           user="root"  password=""/>

        <sql:query dataSource="${source}" var="data">
            SELECT * from coordinaten;
        </sql:query>

        <c:forEach var="coords" begin="0" items="${data.rows}">


            <script>
                var map;

                function initialize() {
                    var mapOptions = {
                        zoom: 8,
                        center: new google.maps.LatLng(${coords.latitude}, ${coords.longitude})
                    };
                    map = new google.maps.Map(document.getElementById('map-canvas'),
                            mapOptions);
                }

                google.maps.event.addDomListener(window, 'load', initialize);
            </script>
        </head>
        <body >
            <h1> ${coords.naam}</h1>
            <a> ${coords.beschrijving}   </a> </c:forEach>
        <div id = "map-canvas" style = "height:300px; width:500px" > </div>
    </body>
</html>
