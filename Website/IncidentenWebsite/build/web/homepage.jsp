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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <title>JSP Page</title>


        <meta http-equiv="pragma" content="no-cache"/>
        <meta http-equiv="cache-control" content="no-cache"/>
        <meta http-equiv="expires" content="-1"/>  
        <script
        src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>

        <sql:setDataSource var="source" driver="com.mysql.jdbc.Driver"
                           url="jdbc:mysql://145.144.240.80:3306/cimsdb"
                           user="cims"  password="cims"/>

        <c:if test="${sessionScope.regioOn == null}">
        <sql:query dataSource="${source}" var="data">
            SELECT * FROM calamity;
        </sql:query>
            </c:if>


    </head>
    <body >

   <c:if test="${pageContext.session.getAttribute('User') == null}">
            
           
        <div class="wrapper">
            <section class="loginform cf">  
                <form id="login" form action="homepage.jsp" method="post" accept-charset="utf-8">  
                    <label for="username">Username</label>  
                    <input type="text" name="username" placeholder="username" required> 
                    <label for="password">Password</label>  
                    <input type="password" name="password" placeholder="password" required> 
                    <input type="submit" value="Login"> 
                    <a href="register.jsp">Register</a>
                </form>  
            </section>
        </div>


        <c:if test="${param.username != null}">
            <sql:query dataSource="${source}" var="datas">
                SELECT * FROM person WHERE personusername = '${param.username}' AND personpassword = '${param.password}'
            </sql:query>

            <c:forEach var="persoon" begin="0" items="${datas.rows}">
                <c:if test="${persoon.personusername == param.username && persoon.personpassword == param.password}">
                    ${pageContext.session.setAttribute("User", persoon.personusername)};
                    <c:redirect url="homepage.jsp"/>

                </c:if>
            </c:forEach>
        </c:if>

 </c:if>
        <c:if test="${sessionScope.User != null}">
            <form id="Logout" form action="homepage.jsp" method="post" accept-charset="utf-8">  
                <input type="submit" value="Logout" onclick="logout()"> 
                <p>Ingelogd: <c:out value="${sessionScope.User}"/></p>
            </form>
            </c:if>



            <div class="row">
                <div class="col-sm-3">
                    <div class="sidebar-nav">
                        <div class="navbar navbar-default" role="navigation">
                            <div class="navbar-header">
                                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-navbar-collapse">
                                    <span class="sr-only">Toggle navigation</span>
                                    <span class="icon-bar"></span>
                                    <span class="icon-bar"></span>
                                    <span class="icon-bar"></span>
                                </button>
                                <span class="visible-xs navbar-brand">Sidebar menu</span>
                            </div>
                            <c:if test="${pageContext.session.getAttribute('regioOn')== null}" >
                            <div class="navbar-collapse collapse sidebar-navbar-collapse">
                                <img src="resources/img/logo1.png" alt="logo">
                                <ul class="nav navbar-nav">
                                    <c:forEach var="coords" begin="0" items="${data.rows}">            
                                        <c:url value="/incident.jsp" var="completeURL">
                                            <c:param name="name" value="${coords.calamityname}"/>
                                            <c:param name="description" value="${coords.calamitydescription}"/>
                                            <c:param name="goe_lat" value="${coords.calamitylatitude}"/>
                                            <c:param name="goe_long" value="${coords.calamitylongtitude}"/>
                                            <c:param name="danger" value="${coords.calamitydanger}"/>
                                            <c:param name="id" value="${coords.calamityid}"/>
                                        </c:url>
                                        <li><a href="${completeURL}">${coords.calamityname}</a></li>
                                        </c:forEach>
                                        ${pageContext.session.setAttribute("HuidigIncidentSwitch", false)};
                                </ul>
                            </div><!--/.nav-collapse -->
                            </c:if>
                            <c:if test="${sessionScope.regioOn != null}">
                                <sql:query dataSource="${source}" var="data">
                                    SELECT * FROM calamity WHERE regionid = '${pageContext.session.getValue("regioValue")}';
                                </sql:query>
                                    
                                <div class="navbar-collapse collapse sidebar-navbar-collapse">
                                <img src="resources/img/logo1.png" alt="logo">
                                <ul class="nav navbar-nav">
                                    <c:forEach var="coords" begin="0" items="${data.rows}">            
                                        <c:url value="/incident.jsp" var="completeURL">
                                            <c:param name="name" value="${coords.calamityname}"/>
                                            <c:param name="description" value="${coords.calamitydescription}"/>
                                            <c:param name="goe_lat" value="${coords.calamitylatitude}"/>
                                            <c:param name="goe_long" value="${coords.calamitylongtitude}"/>
                                            <c:param name="danger" value="${coords.calamitydanger}"/>
                                            <c:param name="id" value="${coords.calamityid}"/>
                                        </c:url>
                                        <li><a href="${completeURL}">${coords.calamityname}</a></li>
                                        </c:forEach>
                                        ${pageContext.session.setAttribute("HuidigIncidentSwitch", false)};
                                </ul>
                            </div><!--/.nav-collapse -->
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="col-sm-9">

                    <header id="myCarousel" class="carousel slide">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                            <li data-target="#myCarousel" data-slide-to="1"></li>
                            <li data-target="#myCarousel" data-slide-to="2"></li>
                        </ol>

                        <!-- Wrapper for Slides -->
                        <div class="carousel-inner">
                            <div class="item active">
                                <!-- Set the first background image using inline CSS below. -->
                                <div class="fill" style="background-image:url('http://placehold.it/1900x1080&text=Slide One');"></div>
                                <div class="carousel-caption">
                                    <h2>Caption 1</h2>
                                </div>
                            </div>
                            <div class="item">
                                <!-- Set the second background image using inline CSS below. -->
                                <div class="fill" style="background-image:url('http://placehold.it/1900x1080&text=Slide Two');"></div>
                                <div class="carousel-caption">
                                    <h2>Caption 2</h2>
                                </div>
                            </div>
                            <div class="item">
                                <!-- Set the third background image using inline CSS below. -->
                                <div class="fill" style="background-image:url('http://placehold.it/1900x1080&text=Slide Three');"></div>
                                <div class="carousel-caption">
                                    <h2>Caption 3</h2>
                                </div>
                            </div>
                        </div>

                        <!-- Controls carousel-->
                        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                            <span class="icon-prev"></span>
                        </a>
                        <a class="right carousel-control" href="#myCarousel" data-slide="next">
                            <span class="icon-next"></span>
                        </a>
                </div>
                                
                                
                            <!-- Combobox -->                
                            Filter op regio <form id="region_action"  method="post" accept-charset="utf-8">  
                            <input type="submit" onclick="comboChange()"> 
                            <select id="region">
                                    <option value="1">Woegaarden</option>
                                    <option value="2">Tilburg</option>
                                    <option value="3">Den Haag</option>
                                    <option value="4">Woerden</option>
                                </select>
                            </form>
            
                            <p id="test"></p>

        
             
                <!-- jQuery -->
                <script src="js/jquery.js"></script>

                <!-- Bootstrap Core JavaScript -->
                <script src="js/bootstrap.min.js"></script>

                <!-- Script to Activate the Carousel -->
                <script>
                    $('.carousel').carousel({
                        interval: 5000 //changes the speed
                        });
                </script>  



                <input type="hidden" id="refreshed" value="no">
                <script type="text/javascript">
                    onload = function () {
                        var e = document.getElementById("refreshed");
                        if (e.value == "no")
                            e.value = "yes";
                        else {
                            e.value = "no";
                            location.reload();
                        }
                    }
         
              </script>
              
              <!-- Logout -->
    <script>
        function logout()
        {
        ${pageContext.session.removeAttribute("User")};
        window.location = "homepage.jsp";
        } 
   
        function comboChange()
        {
        ${pageContext.session.setAttribute("regioOn", "On")};     
        var combo = document.getElementById("region").value;
        document.getElementById("test").innerHTML = combo;
        ${pageContext.session.setAttribute("regioValue", combo)};
        }
    </script>

                </body>

                </html>
