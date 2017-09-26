<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
    <title>Electrosa >> Error de aplicación</title>
    <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
    <meta name="keywords" content="electrodomesticos" lang="es-ES">
    <meta name="language" content="es-ES">
    <meta name="robots" content="index,follow">

    <c:set var="ctx" value="${pageContext.request.contextPath}"/>

    <link href="${ctx}/css/electrosa.css" rel="stylesheet" media="all" type="text/css">
  </head>

  <body >
    <div class="logo"><a href="${ctx}/index.html"><img src="${ctx}/img/LogoElectrosa200.png" border="0"></a></div>

    <div class="sombra">
      <div class="nucleo">
        <div id="lang">
          <a href="${ctx}/index.html">Español</a> &nbsp; | &nbsp; <a href="${ctx}/index.html">English</a>
        </div>
      </div>
    </div>

    <div class="barra_menus">	
      <div class="pestanias">
        <div class="grupoPestanias">
          <div class="pestaniaSel">Para usuarios</div>
          <div class="pestaniaNoSel"><a href="${ctx}/admin/index.html">Intranet</a></div>
        </div>
      </div>

      <div id="menu" >
        <ul>
          <li>
            <a href="${ctx}/index.html">Sobre electrosa<br/><img src="${ctx}/img/Home4.png"/></a>
          </li>
          <li>
            <a href="${ctx}/index.html">Dónde estamos<br/><img src="${ctx}/img/map.png"/></a>
          </li>
          <li>
            <a href="${ctx}/BuscarArticulos">Hojear catálogo<br/><img src="${ctx}/img/catalog.png"/></a>
          </li>
          <li>
            <a href="${ctx}/clientes/AreaCliente">Usuario registrado<br/><img src="${ctx}/img/registrado.png"/></a>
          </li>
        </ul>
        <div style="clear: left;"></div>
      </div>
    </div> 

    <div class="sombra">
      <div class="nucleo">
        <div id="migas">
          <a href="${ctx}/index.html" title="Inicio" >Inicio</a><!-- &nbsp; | &nbsp; 
          <a href="..." title="Otra cosa">Otra cosa</a>   -->	
        </div>
        <div class="contenido">

          <div style="float:left"><img src="${ctx}/img/alerta.png"></div>
          
		  <div class="error">
		    <div>No tiene pemiso para entrar esta zona de la aplicación.</div>
		    <div class="errorb"><a href="${ctx}/index.html">Salir de aqui</a></div>
		  </div>
          <div class="clear"></div>

        </div>
      </div>

      <div class="separa"></div>

      <div class="pie">
        <span class="pie_izda">
          <a href="mailto:francisco.garcia@unirioja.es">Contacto</a> &nbsp; | &nbsp; <a href="${ctx}/mapa.html">Mapa</a> </span>
        <span class="pie_dcha">
          &copy; 2012 Francisco J. García Izquierdo  </span>
        <div class="clear"></div>  
      </div>

    </div>
  </body>
</html>
