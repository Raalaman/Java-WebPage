<%@page import="java.util.Date"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
    <title>Electrosa >> cat&aacute;logo</title>
    <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
    <meta name="keywords" content="electrodomesticos" lang="es-ES">
    <meta name="language" content="es-ES">
    <meta name="robots" content="index,follow">

    <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
    <link href="../css/catalogoMosaico.css" rel="stylesheet" media="all" type="text/css"> 

    <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>

  </head>

  <body >
    <div class="logo"><a href="../index.jsp"><img src="../img/LogoElectrosa200.png" border="0"></a></div>
    <div class="sombra">
      <div class="nucleo">
        <div id="migas">
          <a href="index.html" title="Inicio" >Inicio</a>&nbsp; | &nbsp; 
          <a href="BuscarArticulos" title="Hojear catalogo">Hojear catalogo</a>	<!-- &nbsp; | &nbsp; 
          <a href="..." title="Otra cosa">Otra cosa</a>   -->
        </div>

        <div class="contenido">
          <h1>Estadísticas de venta de productos</h1>
          <p>Elige. </p>
          <div class="filtroCatalogo redondeo">
            <form name="filtroCatalogo" id="filtroCatalogo" action="HazEstadistica" >	  
              <jsp:useBean id="gbd" scope="application" class="paw.bd.GestorBD" /> 

              <label for="tipo">Tipo :</label>
              <select name="tipo" id="tipo">
                <c:forEach var="t" items="${gbd.tiposArticulos}">
                  <option value="${t}" ${(t == param.tipo?'selected':'')}>${t}</option>
                </c:forEach>
              </select>

              <label for="anio">Año :</label>
              <select name="anio" id="anio">               
                <%
                  Date hoy = new Date();
                  int anio = hoy.getYear() + 1900;
                  int[] anios = new int[5];
                  for (int i = 0;i<5;i++) anios[i] = anio-i;
                  pageContext.setAttribute("anios", anios);
                  %>                
                <c:forEach var="a" items="${anios}">
                  <option value="${a}" ${(param.anio == a?'selected':'')}>${a}</option>
                </c:forEach>
              </select>

              <label for="num">Número de artículos :</label>
              <select name="num" id="num">
                <c:forEach var="n" items="3,4,5,6,7,8">
                  <option value="${n}" ${(param.num == n?'selected':'')}>${n}</option>
                </c:forEach>
              </select>

              <input name="ver" id="buscar" type="image" title="Ver" src="../img/search25.png" alt="Ver">
            </form>
            <div class="clear"></div>
          </div>

		  <!-- Un ejemplo de gráfico de sectores. Puedes usarlo como base para la práctica -->
          <script type="text/javascript">
            google.load('visualization', '1.0');
            function drawVisualization() {
              google.visualization.drawChart({
                "containerId": "chart_div",                 
                "dataTable": [
                  ["Nombre","Ventas"],
                  ["Mie-079FO",25],
                  ["Fag-303FO",24],
                  ["Fag-332FO",21]
                ],
                "chartType": "PieChart",
                "options": {'title':'Ventas de Frigorifico en 2013',
                  'width':400,
                  'height':300
                }
              });
            }
            google.setOnLoadCallback(drawVisualization);
          </script>
          <div id="chart_div"></div>

          <div class="clear"></div>

        </div>
      </div>

      <div class="separa"></div>

    </div>
  </body>
</html>