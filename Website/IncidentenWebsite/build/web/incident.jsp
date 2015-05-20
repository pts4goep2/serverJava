<%-- 
    Document   : index
    Created on : Mar 11, 2015, 1:35:03 PM
    Author     : richard en nadiv
--%>

<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ page session="true" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- Bootstrap Core CSS -->
 <link href="css/bootstrap.min.css" rel="stylesheet">

 <!-- Custom CSS -->
 <link href="css/half-slider.css" rel="stylesheet">
 
 <!-- CSS -->
 <link href="css/bootstrap.css" rel="stylesheet">
 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script
        src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>

        <sql:setDataSource var="source" driver="com.mysql.jdbc.Driver"
                           url="jdbc:mysql://145.144.241.47:3306/cimsdb"
                           user="cims"  password="cims"/>


        <%--<sql:query dataSource="${source}" var="data">
            SELECT * FROM calamiteitberichten WHERE idcalamity = '${param.id}';
        </sql:query>--%>


        <c:if test="${sessionScope.HuidigIncidentSwitch == false}">
            <jsp:useBean id="map" class="java.util.LinkedHashMap" />
            <c:set target="${map}" property="goe_lat" value="${param.goe_lat}" />
            <c:set target="${map}" property="goe_long" value="${param.goe_long}" />
            <c:set target="${map}" property="name" value="${param.name}" />
            <c:set target="${map}" property="description" value="${param.description}" />

            <c:set var="array" value="${map.values().toArray()}" />

            ${pageContext.session.setAttribute("HuidigeCalamiteit", array)};
            ${pageContext.session.setAttribute("HuidigIncidentSwitch", true)};
        </c:if>

        <script>
            var map;

            function initialize() {
                var mapOptions = {
                    zoom: 8,
                    center: new google.maps.LatLng(${sessionScope.HuidigeCalamiteit[0]}, ${sessionScope.HuidigeCalamiteit[1]})

                };
                map = new google.maps.Map(document.getElementById('map-canvas'),
                        mapOptions);
                var marker = new google.maps.Marker({position: new google.maps.LatLng(${sessionScope.HuidigeCalamiteit[0]}, ${sessionScope.HuidigeCalamiteit[1]}),
                    map: map});
            }

            google.maps.event.addDomListener(window, 'load', initialize);
        </script>
    <div id = "map-canvas" style = "height:300px; width:500px" > </div>
</head>

<body >
    <h1> ${sessionScope.HuidigeCalamiteit[2]}</h1>
    <a> ${sessionScope.HuidigeCalamiteit[3]}   </a>



    <%--<h1> Berichten </h1>
    <c:forEach var="berichten" begin="0" items="${data.rows}">

        <a>${berichten.firstname} ${berichten.lastname} ${berichten.timestamp} ${berichten.bericht}</a>

        <section class="loginform cf">  
        </c:forEach>--%>
    <h2> Plaats bericht </h2>

    <form name="post_bericht" form action="incident.jsp"  method="post" accept-charset="utf-8">  
        <ul>  
            <li><label for="bericht">Bericht</label>  
                <input type="text" name="bericht" required></li>  
            <li>
                <input type="submit" value="Verzend"></li>  
    </form>  
</section>

<c:if test="${param.bericht != null}">

    <%--<sql:query dataSource="${source}" var="data">
        INSERT INTO tabel (calamid, userid, bericht) VALUES ('${param.id}', '${sessionScope.user}', '${param.bericht}');
    </sql:query>--%>


    <c:redirect url="incident.jsp">
    </c:redirect>

</c:if>

        <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Script to Activate the Carousel -->
    <script>
    $('.carousel').carousel({
        interval: 5000 //changes the speed
    })
</body>
</html>
