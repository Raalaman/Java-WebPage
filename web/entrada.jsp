<%-- 
    Document   : entrada
    Created on : 30-mar-2017, 16:33:53
    Author     : raalaman
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Login</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">

        <link href="css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="css/entrada.css" rel="stylesheet" media="all" type="text/css">
        <script type="text/javascript">
            window.onload = function () {
                document.getElementById("usr").focus();
            };
        </script>
    </head>

    <body >
        <jsp:include page="cabecera.jsp" /> 
        <div class="sombra">
            <div class="nucleo">
                <div id="migas">
                    <a href="<c:url value="index.jsp"/>" title="Inicio" >Inicio</a>&nbsp; | &nbsp;
                    <a href="..." title="Otra cosa">Otra cosa</a>   -->	
                </div>

                <div class="contenido">

                    <h1>Acceso de usuario registrado</h1> 
                    <c:if test="${error!=null}">
                    
                        <div class="alerta">
                        <c:out value="${error}"/>
                    </div>   
                    </c:if>
                    
                    <form name="flogin" id="flogin" class="izda" action="Login" method="post">
                        <fieldset> 
                            <div class="field">
                                <label for="usr">Nombre de usuario :</label>
                                <input type="text" name="usr" id="usr" size="10"/>
                            </div>

                            <div class="field">
                                <label for="pwd">Contraseña:</label>
                                <input type="password" name="pwd" id="pwd" size="10" />
                            </div>

                            <div class="dcha">
                                <div class="field">
                                    <input class="submitb" type="submit"  value="Acceder" />  
                                </div>
                            </div>
                            <div style="clear:right"></div>
                            <div class="field">
                                <a href="RecuerdoContrasenia">&iquest;Olvid&oacute; su contrase&ntilde;a? </a></div>
                        </fieldset>  
                    </form>



                    <div class="nuevo izda">
                        <div>
                            <p style="font-weight:bold; margin-top:5px">Nuevo cliente.</p>
                            <p style="margin-top:5px;line-height:1.4em;">Para poder realizar pedidos on-line a través de nuestro sistema de pedidos es necesario que se registre </p>
                        </div>

                        <div  class="registrob">
                            <a href="NuevoCliente">REGISTRESE</a>
                        </div> 
                    </div>		  

                    <div class="clear"> </div>
                </div>
            </div>

            <jsp:include page="pie.html"/>

        </div>
    </body>
</html>