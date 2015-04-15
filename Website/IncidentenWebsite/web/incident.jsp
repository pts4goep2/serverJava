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
            SELECT * FROM calamity WHERE idcalamity = 5;
        </sql:query>

        <c:forEach var="coords" begin="0" items="${data.rows}">


           <script>
                var map;

                function initialize() {
                    var mapOptions = {
                        zoom: 8,
                        center: new google.maps.LatLng(${coords.goe_lat}, ${coords.goe_long})
                        
                    };
                    map = new google.maps.Map(document.getElementById('map-canvas'),
                            mapOptions);
                    var marker = new google.maps.Marker({position: new google.maps.LatLng(${coords.goe_lat}, ${coords.goe_long}),
                        map: map});
                }

                google.maps.event.addDomListener(window, 'load', initialize);
            </script>
            
            
        </head>
        <body >
            <h1> ${coords.name}</h1>
            <a> ${coords.description}   </a> </c:forEach>
        <div id = "map-canvas" style = "height:300px; width:500px" > </div>
    </body>
</html>
