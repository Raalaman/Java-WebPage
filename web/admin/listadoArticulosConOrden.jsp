<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
    <title>Lista de pedidos del cliente</title>
    <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
    <meta name="keywords" content="electrodomesticos" lang="es-ES">
    <meta name="language" content="es-ES">
    <meta name="robots" content="index,follow">

    <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
    <link href="../css/clientes.css" rel="stylesheet" media="all" type="text/css">
    <link href="../css/listado.css" rel="stylesheet" media="all" type="text/css">
    <link href="../css/estilomenu.css" rel="stylesheet" media="all" type="text/css" />
  </head>

  <body >
    <div class="logo"><a href="../index.html"><img src="../img/LogoElectrosa200.png" border="0"></a></div>
    <div class="sombra">
      <div class="nucleo">
        <div id="lang">
          <a href="index.html">Español</a> &nbsp; | &nbsp; <a href="index.html">English</a>
        </div>
      </div>
    </div>  
    <div class="barra_menus">
      <div class="pestanias">
        <div class="grupoPestanias">
          <div class="pestaniaNoSel"><a href="../index.html">Para usuarios</a></div>
          <div class="pestaniaSel">Intranet</div>
        </div>
      </div>

      <div id="cssmenu">
        <ul>
          <li class='has-sub'><a href="index.html">Art&iacute;culos</a>
            <ul>
              <li><a href="listadoArticulos.jsp">Listar</a></li>
              <li><a href="AddArticulo">A&ntilde;adir</a> </li>
              <li><a href="HazEstadistica">Estadísticas</a> </li>
            </ul>
          </li>
          <li class='has-sub'><a href="index.html">Clientes</a>	
            <ul>
              <li><a href="index.html">Listar</a> </li>
              <li><a href="index.html">A&ntilde;adir</a> </li>
            </ul>    
          </li>
          <li><a href="index.html">Pedidos</a></li>
          <li class='last'><a href="index.html">Stocks</a></li>
        </ul>
        <div style="clear: left;"></div>
      </div>
    </div> 

    <div class="sombra">
      <div class="nucleo">
        <div id="migas">
          <a href="index.html" title="Inicio" >Inicio</a>
        </div>
        <div class="clear"></div>
        <div class="contenido">
          <h1>Listado de art&iacute;culos     </h1>
          <a name="inicio"></a>
          <table width="95%">
            <colgroup>
              <col width="4%"><col width="4%"><col width="11%"><col width="12%">
              <col width="12%"><col width="36%"><col width="10%"><col width="11%">
            </colgroup>
            <tr>
              <td colspan="8">Listado de art&iacute;culos en la BD </td>
            </tr>

            <%-- parametro "o": campo de ordenación
                 parametro "s": sentido de ordenación --%>
            <%-- Por si no se reciben parámetros inicializo un orden y un sentido de ordenación--%>
            <c:set var="o" value="${not empty param.o? param.o: 'codigo'}"></c:set>
            <c:set var="s" value="${not empty param.s? param.s: 'asc'}"></c:set>

              <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td><a href="listadoArticulosConOrden.jsp?o=codigo&s=${o eq 'codigo'?(s eq 'asc'?'desc':'asc'):'asc'}">C&oacute;digo</a></td>
              <td><a href="listadoArticulosConOrden.jsp?o=fabricante&s=${o eq 'fabricante'?(s eq 'asc'?'desc':'asc'):'asc'}">Fabricante</a></td>
              <td><a href="listadoArticulosConOrden.jsp?o=tipo&s=${o eq 'tipo'?(s eq 'asc'?'desc':'asc'):'asc'}">Tipo</a></td>
              <td><a href="listadoArticulosConOrden.jsp?o=nombre&s=${o eq 'nombre'?(s eq 'asc'?'desc':'asc'):'asc'}">Nombre</a></td>
              <td><a href="listadoArticulosConOrden.jsp?o=stock&s=${o eq 'stock'?(s eq 'asc'?'desc':'asc'):'asc'}">Stock</a></td>
              <td><a href="listadoArticulosConOrden.jsp?o=pvp&s=${o eq 'pvp'?(s eq 'asc'?'desc':'asc'):'asc'}">P.V.P.</a> </td>
            </tr>  
            <sql:query dataSource="jdbc/electrosa" var="res">
              select a.codigo, a.nombre, a.pvp, a.tipo, a.fabricante, a.foto, a.descripcion, sum(cantidad) as stock
              from articulo a left join stock on a.codigo = stock.codigoArticulo
              group by a.codigo, a.nombre, a.pvp, a.tipo, a.fabricante, a.foto, a.descripcion
              order by ${o} ${s}
            </sql:query>
            <%-- Recorrer el resultado, almacenado en "res" --%>
            <c:forEach var="f" items="${res.rows}" varStatus="st">
              <tr ${st.index % 2==0 ? 'class="par"':''}>
                <td style="text-align: center"><a href="<c:url value="EditaArticulo"><c:param name="idArt" value="${f.codigo}"/></c:url>"><img src="../img/edit.gif" title="Editar"/></a></td>
                  <td style="text-align: center"><img src="../img/cancel.png" title="Descatalogar"/></td>
                  <td><a href="<c:url value="../fichaArticulo.jsp"><c:param name="cart" value="${f.codigo}"/></c:url>">${f.codigo}</a></td>
                <td>F${f.fabricante}</td> <!-- La F está añadida, mal añadida, adrede -->
                <td>${f.tipo}</td>
                <td>${f.nombre}</td>
                <td>${f.stock!=null?f.stock:0}</td>
                <td style="text-align: right"><fmt:formatNumber type="currency" value="${f.pvp}" /></td>
              </tr>   
            </c:forEach>     
          </table>

          <span class="atajo"><a href="#inicio">Inicio</a></span>          
        </div>
        <div class="clear"></div>
      </div>
      <div class="separa"></div>
      <div class="pie">
        <span class="pie_izda">
          <a href="mailto:francisco.garcia@unirioja.es">Contacto</a> &nbsp; | &nbsp; <a href="../mapa.html">Mapa</a> </span>
        <span class="pie_dcha">
          &copy; 2012 Francisco J. García Izquierdo  </span>
        <div class="clear"></div>  
      </div>
    </div>
  </body>
</html>