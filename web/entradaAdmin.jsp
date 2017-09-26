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

    <c:set var="ctx" value="${pageContext.request.contextPath}"/>

    <link href="${ctx}/css/electrosa.css" rel="stylesheet" media="all" type="text/css">
    <link href="${ctx}/css/entrada.css" rel="stylesheet" media="all" type="text/css">
    <script type="text/javascript">
      window.onload = function() {
        document.getElementById("usr").focus();
      };
    </script>
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

          <h1>Acceso de usuario administrador</h1> 

          <form name="flogin" id="flogin" class="izda" action="j_security_check" method="post">
            <fieldset> 
              <div class="field">
                <label for="usr">Nombre de usuario :</label>
                <input type="text" name="j_username" id="usr" size="10" value=""/>
              </div>

              <div class="field">
                <label for="pwd">Contraseña:</label>
                <input type="password" name="j_password" id="pwd" size="10" />
              </div>

              <div class="dcha">
                <div class="field">
                  <input class="submitb" type="submit"  value="Acceder" />  
                </div>
              </div>
              <div style="clear:right"></div>
            </fieldset>  
          </form>	  

          <div class="clear"> </div>
        </div>
      </div>

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
