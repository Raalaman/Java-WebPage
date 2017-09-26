<%-- 
    Document   : pedidoRealizacion
    Created on : 18-abr-2017, 20:38:22
    Author     : Manga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Electrosa >> Pedido en realización</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">
        <script type='text/javascript' src='../codebase/dhtmlxcalendar.js'></script>
        <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/clientes.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/pedidoR.css" rel="stylesheet" media="all" type="text/css">
        <link href="../codebase/dhtmlxcalendar.css" rel="stylesheet" media="all" type="text/css">
        <script type="text/javascript">
            window.onload = function ()
            {
                var inputs=document.getElementsByClassName("fecha");
                var array=[];
                for(var i=0;i<inputs.length;i++)
                {
                    array.push(inputs[i].id);
                }
                initCalendars(array);
            };
            function initCalendars(array) {
                var calendarios = new dhtmlXCalendarObject(array);
                calendarios.setDateFormat("%d/%m/%Y");
                calendarios.hideTime();
                calendarios.setInsensitiveRange(null, new Date());
            }
        </script>
    </head>

    <body >
        <jsp:include page="cabecera.jsp"/>
        <div class="sombra">
            <div class="nucleo">
                <div id="migas">
                    <a href="<c:url value='../index.html'/>" title="Inicio" >Inicio</a> &nbsp; | &nbsp; 
                    <a href="<c:url value='AreaCliente'/>" title="Área de cliente">Área de cliente</a>&nbsp; | &nbsp; 
                </div>
                <div id="cliente">
                    Bienvenido, ${Cliente.getNombre()}
                </div>
                <div class="clear"></div>
                <div class="contenido">
                    <h1>Contenido de su  pedido    </h1>
                    <c:choose>
                        <c:when test="${pedidoRealizacion==null}">
                            Actualmente no se está realizando ninguna compara en Electrosa. Use el enlace comprobar en la barra del menú.
                        </c:when>
                        <c:otherwise>
                            <p>Pedido iniciado el <fmt:formatDate type="date" pattern="dd/MM/yyyy" value="${pedidoRealizacion.getInicio().getTime()}"/> a las <fmt:formatDate type="date" pattern="KK:mm" value="${pedidoRealizacion.getInicio().getTime()}"/> </p>
                            <form action="GestionaPedido" method="post">
                                <table width="95%" cellspacing="0">
                                    <tr>
                                        <td width="20%">Selecciona para borrar linea</td>
                                        <td colspan="1"><img src="../img/AddCartb.png" style="vertical-align:middle;margin-bottom:3px; margin-left:1px">&nbsp; Art&iacute;culos del pedido</td>
                                        <td width="10%">P.V.P.</td>
                                        <td width="10%">Cantidad</td>
                                        <td width="16%">Fecha de entrega (dd/mm/yyyy)</td>
                                    </tr>
                                    <c:forEach var="linea" varStatus="contador" items="${pedidoRealizacion.getLineas()}">
                                        <tr>
                                            <td width="6%" style="text-align:center">
                                                <input class="submitb" type="checkbox" name="CL_${linea.getCodigo()}" value="${linea.getCodigo()}">
                                            </td>
                                            <td width="58%">
                                                <span class="codigo">${linea.getArticulo().getCodigo()}</span> - <br/>
                                                <span class="descr">${linea.getArticulo().getNombre()}</span>
                                            </td>

                                            <td><fmt:formatNumber type="currency" value="${linea.getArticulo().getPvp()}"/></td>
                                            <td class="cantidad">
                                                <input  type="number" name="C_${linea.getCodigo()}" min="1" max="10" value="${linea.getCantidad()}">
                                            </td>
                                            <td>
                                                <input class="fecha" type="text" name="F_${linea.getCodigo()}" id="ID_Fecha${contador.count}" size="10" value="<fmt:formatDate type="date" pattern="dd/MM/yyyy"  value="${linea.getFechaEntregaDeseada().getTime()}"/>">			  
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                <input class="submitb" type="submit" name="accion" value="Seguir comprando">
                                <input class="submitb" type="submit" name="accion" value="Guardar pedido">
                                <input class="submitb" type="submit"  name="accion" value="Quitar">
                                <input class="submitb cerrar" type="submit" name="accion" value="Cerrar pedido">	
                                <input class="submitb cancelar" type="submit" name="accion" value="Cancelar">
                            </form>
                        </c:otherwise>




                    </c:choose>

                </div>
                <div class="clear"></div>
            </div>

            <div class="separa"></div>
            <jsp:include page="pie.html"/>
        </div>
    </body>
</html>
