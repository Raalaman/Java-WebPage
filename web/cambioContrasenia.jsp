<%-- 
    Document   : cambioContrasenia
    Created on : 14-abr-2017, 11:57:59
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
    <jsp:include page="cabecera.jsp"/>

    <div class="sombra">
      <div class="nucleo">
        <div id="migas">
          <a href="<c:url value="index.jsp"/>" title="Inicio" >Inicio</a>&nbsp; | &nbsp; 
          <a href="..." title="Otra cosa">Otra cosa</a>   -->	
        </div>

        <div class="contenido">

          <h1>Cambiar contrase&ntilde;a </h1> 
          <p>Introduzca su nueva contrase&ntilde;a.</p>
          <form name="frecuerdo" id="frecuerdo" class="izda" action="CambioContrasenia" method="post">
              <fieldset><div class="field">
                <label for="pwd">Nueva contrase&ntilde;a:</label>
                <input type="password" name="pwd" id="pwd" size="10"/><br>
                <label for="rpwd">Repite contrase&ntilde;a:</label>
                <input type="password" name="rpwd" id="rpwd" size="10"/>
              </div>

              <div class="dcha">
                <div class="field"> 
                  <input class="submitb" type="submit"  value="Cambiar contraseña" /> 
                </div>
              </div>
              <div style="clear:right"></div>
            </fieldset>  
          </form>



          <div class="clear"> </div>
        </div>
      </div>
      <jsp:include page="pie.html"/>
  </body>
</html>