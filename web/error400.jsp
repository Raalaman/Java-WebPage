<%-- 
    Document   : error400
    Created on : 04-abr-2017, 20:31:29
    Author     : Manga
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"  isErrorPage="true"%>
<%@ page session="false" %>
<!DOCTYPE html>
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
                            <c:out default="Error 400" value="${'Error '}${requestScope['javax.servlet.error.status_code']}${'. '}${'Solicitud incorrecta '}${requestScope['javax.servlet.error.message']}"/> 
                        </div>
                        <c:choose>
                            <c:when test="${link!=null}">
                                <div class="errorb"><a href="${link}">Salir de aqui</a></div>

                            </c:when>
                            <c:otherwise>
                                <div class="errorb"><a href="${pageContext.request.contextPath}/index.html">Salir de aqui</a>
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