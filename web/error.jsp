<%-- 
    Document   : error
    Created on : 19-mar-2017, 18:18:26
    Author     : Manga
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"  isErrorPage="true"%>
<!DOCTYPE html>
<%@ page session="false" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Electrosa >> cat&aacute;logo</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">
        <link href="${pageContext.request.contextPath}/css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="${pageContext.request.contextPath}/css/catalogoMosaico.css" rel="stylesheet" media="all" type="text/css">
        <!--    <link href="css/catalogoLista.css" rel="stylesheet" media="all" type="text/css"> -->
    </head>

    <body >
        <jsp:include page="cabecera.jsp" /> 
        <div class="sombra">
            <div class="nucleo">
                <div id="migas">
                    <a href="index.html" title="Inicio" >Inicio</a><!-- &nbsp; | &nbsp; 
                    <a href="..." title="Otra cosa">Otra cosa</a>   -->	
                </div>


                <div class="contenido">
                    <div style="float:left"><img src="${pageContext.request.contextPath}/img/alerta.png"></div>
                    <div class="error">
                        <div>  
                            <c:out default="Error de aplicación" value="${'Error '}${requestScope['javax.servlet.error.status_code']}${'. '}${requestScope['javax.servlet.error.message']}"/> 
                        </div>
                        <c:choose>
                            <c:when test="${link!=null}">
                                <div class="errorb"><a href="${link}">Salir de aqui</a></div>
                            </c:when>
                            <c:otherwise>
                                <div class="errorb"><a href="${pageContext.request.contextPath}/index.jsp">Salir de aqui</a>
                                </div> 
                            </c:otherwise>
                        </c:choose>

                    </div>
                    <div class="clear"></div>

                </div>
            </div>

            <div class="separa"></div>
            <jsp:include page="pie.html" /> 

        </div>
    </body>
</html>