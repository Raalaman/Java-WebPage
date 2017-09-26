<%-- 
    Document   : confirmacion
    Created on : 20-abr-2017, 19:06:44
    Author     : Manga
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Electrosa >> Confirmación</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">

        <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/confirmacion.css" rel="stylesheet" media="all" type="text/css">
    </head>

    <body >

        <jsp:include page="cabecera.jsp"/>
        <div class="sombra">
            <div class="nucleo">
                <div id="migas">
                    <a href="<c:url value='../index.html'/>" title="Inicio" >Inicio</a> &nbsp; | &nbsp; 
                </div>

                <div class="contenido">
                    <div style="float:left; width:20%"><img src="../img/pregunta.png"></div>
                    <div class="confirm">
                        <div>${msg}</div>
                        <div class="NOb dcha"><a href="<c:url value="${noURL}"> <c:param name="accion" value="${noParameter}"/> </c:url>">NO</a></div>
                        <div class="SIb izda"><a href="<c:url value="${siURL}"> <c:param name="accion" value="${siParameter}"/> </c:url>">SI</a></div>
                        <div class="clear"></div>
                    </div>
                    <div class="clear"></div>

                </div>
            </div>

            <div class="separa"></div>

            <jsp:include page="pie.html"/>

        </div>
    </body>
</html>