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
                           url="jdbc:mysql://145.144.241.132:3306/mydb"
                           user="cims"  password="cims"/>

        <%--<sql:query dataSource="${source}" var="data">
            SELECT * FROM calamiteitberichten WHERE idcalamity = ${param.id};
        </sql:query>--%>


        <script>
            var map;

            function initialize() {
                var mapOptions = {
                    zoom: 8,
                    center: new google.maps.LatLng(${param.goe_lat}, ${param.goe_long})

                };
                map = new google.maps.Map(document.getElementById('map-canvas'),
                        mapOptions);
                var marker = new google.maps.Marker({position: new google.maps.LatLng(${param.goe_lat}, ${param.goe_long}),
                    map: map});
            }

            google.maps.event.addDomListener(window, 'load', initialize);
        </script>
    <div id = "map-canvas" style = "height:300px; width:500px" > </div>
</head>

<body >
    <h1> ${param.name}</h1>
    <a> ${param.description}   </a>
    <%--<h1> Berichten </h1>
    <c:forEach var="berichten" begin="0" items="${data.rows}">

        <a>${berichten.firstname} ${berichten.lastname} ${berichten.timestamp} ${berichten.bericht}</a>

        <section class="loginform cf">  
        </c:forEach>
        <h2> Plaats bericht </h2>

        <textarea name="bericht" form="post_bericht" maxlength="50" placeholder="Plaats hier je bericht."> </textarea>
        <form name="post_bericht" form action="incident.jsp"  method="post" accept-charset="utf-8">  
            <ul>  
                <c:param name="name" value="${param.name}"/>
                <c:param name="description" value="${param.description}"/>
                <c:param name="goe_lat" value="${param.goe_lat}"/>
                <c:param name="goe_long" value="${param.goe_long}"/>
                <li>
                    <input type="submit" value="Verzend"></li>  
        </form>  
    </section>

    <c:if test="${param.bericht != null}">

        <sql:query dataSource="${source}" var="data">
            INSERT INTO tabel (calamid, userid, bericht) VALUES (${param.id, sessionScope.user, param.bericht);
        </sql:query>
                

        <c:redirect url="incident.jsp">
            <c:param name="name" value="${param.name}"/>
                <c:param name="description" value="${param.description}"/>
                <c:param name="goe_lat" value="${param.goe_lat}"/>
                <c:param name="goe_long" value="${param.goe_long}"/>
        </c:redirect>

    </c:if>--%>

</body>
</html>
