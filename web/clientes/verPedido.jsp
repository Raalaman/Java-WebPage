
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Electrosa >> Hoja de pedido</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">

        <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/clientes.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/pedido.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/listado.css" rel="stylesheet" media="all" type="text/css">
    </head>

    <body >
        <div class="logo"><a href="../index.html"><img src="../img/LogoElectrosa200.png" border="0"></a></div>

        <jsp:include page="cabecera.jsp"/>
        <div class="sombra">
            <div class="nucleo">
                <div id="migas">
                    <a href="<c:url value='../index.html'/>" title="Inicio" >Inicio</a> &nbsp; | &nbsp; 
                    <a href="<c:url value='AreaCliente'/>" title="Área de cliente">Área de cliente</a>&nbsp; | &nbsp; 
                    <a href="<c:url value='PedidosCliente'/>" title="Mis pedidos">Mis pedidos</a>
                </div>
                <div id="cliente">
                    Bienvenido ${Cliente.getNombre()}
                </div>
                <div class="clear"></div>
                <div class="contenido">
                    <h1>Hoja de pedido    </h1>
                    <div class="cabPedido"> <span class="izda">ELECTROSA - Hoja de pedido</span> <span class="dcha">Ref. Pedido: ${Pedido.getCodigo()}</span>
                        <div class="clear"></div>
                    </div>
                    <div class="cabIdCliente">Identificación del cliente</div>
                    <div class="detIdCliente">
                        <div class="izda">Cliente: ${Cliente.getCodigo()}</div>
                        <div class="izda">${Cliente.getNombre()}</div>
                        <div class="dcha">Fecha: <fmt:formatDate type="date"  value="${Pedido.getFechaCierre().getTime()}"/></div>
                        <div class="dcha"> CIF: ${Cliente.getCif()}</div>
                        <div class="clear"></div>
                    </div>

                    <div class="izda direccionEntregaPedLabel">A entregar en:</div>
                    <div class="izda direccionEntregaPed">${Pedido.getDirEntrega().getCalle()}, ${Pedido.getDirEntrega().getCiudad()}.  ${Pedido.getDirEntrega().getCp()}- ${Pedido.getDirEntrega().getProvincia()}</div>
                    <div class="clear"></div>

                    <table width="95%">
                        <colgroup>
                            <col width="9%">
                            <col width="53%">
                            <col width="10%">
                            <col width="10%">
                            <col width="9%">
                            <col width="9%">
                        </colgroup>
                        <tr>
                            <td colspan="6">Detalle del pedido</td>
                        </tr>
                        <tr>
                            <td>Cantidad</td>
                            <td>Art&iacute;culo</td>
                            <td>P.V.P.</td>
                            <td>Su precio</td>
                            <td>F. Entrega deseada</td>
                            <td>F. Entrega prevista</td>
                        </tr>
                        <c:forEach var="linea" items="${Pedido.getLineas()}" varStatus="status">
                            <tr ${(status.count%2==0) ? "class=\"par\"" : ""  }  >
                                <td style="text-align:center">${linea.getCantidad()}</td>
                                <td>${linea.getArticulo().getNombre()}</td>
                                <td style="text-align: right"><fmt:formatNumber type="currency" value="${linea.getPrecioBase()}"/></td>
                                <td style="text-align: right"><fmt:formatNumber type="currency" value="${linea.getPrecioReal()}"/></td>
                                <td><fmt:formatDate type="date"  value="${linea.getFechaEntregaDeseada().getTime()}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${linea.getFechaEntregaPrevista()==null}">
                                            <c:out value="S.D.*"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatDate type="date"  value="${linea.getFechaEntregaPrevista().getTime()}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>

                    <div class="resPedido">
                        <span>TOTAL: <fmt:formatNumber type="currency" value="${Pedido.getImporte()}"/></span>
                        <div class="clear"></div>
                    </div>  

                    <div>
                        * S.D.: sin disponibilidad. Recibirá una notificación de entrega en el momento en que podamos atender su petición.
                    </div>   
                </div>
                <c:if test="${!Pedido.isCursado()}"> 
                    <div  class="anulab">
                        <a href="<c:url value='ConfirmaAnulacion'> <c:param name="cp" value="${Pedido.getCodigo()}"/> </c:url>">ANULAR PEDIDO</a>
                    </div> 
                </c:if>  
                <div  class="NOanulab">
                    <a href="<c:url value='PedidosCliente'/>">Volver a mis pedidos</a>
                </div> 
                <div class="clear"></div>
            </div>

            <div class="separa"></div>

            <jsp:include page="pie.html"/>

        </div>
    </body>
</html>