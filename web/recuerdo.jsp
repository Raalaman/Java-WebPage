<%-- 
    Document   : recuerdo
    Created on : 31-mar-2017, 17:13:20
    Author     : Manga
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Registro</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">

        <link href="css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="css/entrada.css" rel="stylesheet" media="all" type="text/css">
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

                    <h1>Recordar contrase&ntilde;a </h1> 
                    <p>Introduzca su nombre de usuario en el cuadro de texto habilitado m&aacute;s abajo. Le enviaremos un correo electr&oacute;nico con instrucciones para acceder a su cuenta.</p>
                    <p>Si no recuerda su nombre de usuario contacte con el <a href="mailto:admin@sitio.es">administrador del sitio</a>. </p>
                    
                    <c:if test="${error!=null}">
                        <div class="alerta">
                         <p>  ${error} </p>
                        </div>   
                    </c:if>
                    <br>
                    <form name="frecuerdo" id="frecuerdo" class="izda" action="RecuerdoContrasenia" method="post"><fieldset><div class="field">
                                <label for="usr">Nombre de usuario :</label>
                                <input type="text" name="usr" id="usr" size="10"/>
                            </div>

                            <div class="dcha">
                                <div class="field"> 
                                    <input class="submitb" type="submit"  value="Recuérdame la contraseña" /> 
                                </div>
                            </div>
                            <div style="clear:right"></div>
                        </fieldset>  
                    </form>



                    <div class="clear"> </div>
                </div>
            </div>

            <jsp:include page="pie.html"/>

        </div>
    </body>
</html>
