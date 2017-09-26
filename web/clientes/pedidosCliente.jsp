<%-- 
    Document   : pedidosCliente
    Created on : 06-abr-2017, 17:15:11
    Author     : raalaman
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Electrosa >> Pedidos del cliente</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/clientes.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/listado.css" rel="stylesheet" media="all" type="text/css">
        <script>
            window.onload = function ()
            {
                var img = document.getElementsByTagName("a");
                for (var i = 0; i < img.length; i++)
                {
                    if (img[i].title === "Insertar detalle pedido")
                    {
                        if (img[i].addEventListener) {
                            img[i].addEventListener("click", verPedido, false);
                        } else if (window.attachEvent)
                        {
                            img[i].attachEvent(" on " + "click", verPedido);
                        } else {
                            img[i][ " on " + "click" ] = verPedido;
                        }
                    }
                }

                function verPedido(evento) {
                    if (evento)
                        evento.preventDefault();
                    else
                        window.event.returnValue = false;
                    var evt = evento || window.event;
                    target = evt.target || evt.srcElement;
                    $.ajax("VerPedido?cp=" + escape(target.id),
                            {dataType: 'text',
                                cache: 'false',
                                method: 'GET',
                                async: true
                            }
                    ).done(actualizaTablaPedido)
                            .fail(function (xhr, status, ex) {
                                alert("Error (" + xhr.status + "):" + xhr.statusText);
                            });
                }

                function actualizaTablaPedido()
                {
                    if (target.text === "[+]")
                    {
                        target.text = "[-]";
                        var objJSON = JSON.parse(arguments[0]);
                        var elementTR = document.createElement("tr");
                        var elementTD = document.createElement("td");
                        elementTD.setAttribute("colspan", "6");
                        elementTD.setAttribute("align", "center");
                        elementTR.appendChild(elementTD);
                        var filaFinal = '</table>';
                        var filaCabecera = '<table width="95%">' +
                                '<colgroup>' +
                                '<col width="9%"><col width="53%"><col width="10%">' +
                                '<col width="10%"><col width="6%"><col width="6%">' +
                                '</colgroup>' +
                                '<tr style="text-align: left"><td colspan="6">Detalle del pedido P000004-08</td></tr>' +
                                '<tr style="text-align: left">' +
                                '<td>Cantidad</td><td>Art&iacute;culo</td><td>P.V.P.</td>' +
                                '<td>Su precio</td><td>F. E. des.</td><td>F. E. prev.</td>' +
                                '</tr>';
                        var filasLineas;
                        for (var i = 0; i < objJSON.lineas.length;i++)
                        {
                            filasLineas += '<tr>' +
                                    '<td style="text-align:center">'+objJSON.lineas[i].cantidad+'</td>' +
                                    '<td style="text-align: left">'+objJSON.lineas[i].articulo.nombre+'</td>' +
                                    '<td style="text-align: right">'+objJSON.lineas[i].articulo.pvp+'&euro;</td>' +
                                    '<td style="text-align: right">'+objJSON.lineas[i].precioReal+'&euro;</td>' +
                                    '<td>'+devolverSdOfecha(objJSON.lineas[i].fechaEntregaDeseada)+'</td>' +
                                    '<td>'+devolverSdOfecha(objJSON.lineas[i].fechaEntregaPrevista)+'</td>' +
                                    '</tr>';
                        }
                        elementTD.innerHTML = filaCabecera+filasLineas+filaFinal;
                        var img = document.getElementsByTagName("a");
                        for (var i = 0; i < img.length; i++)
                        {
                            if (img[i].id === objJSON.codigo)
                            {
                                insertaDespues(img[i].parentNode.parentNode.parentNode, img[i].parentNode.parentNode, elementTR);
                                break;
                            }
                        }
                    } else
                    {
                        target.text = "[+]";
                        var objJSON = JSON.parse(arguments[0]);
                        var img = document.getElementsByTagName("a");
                        for (var i = 0; i < img.length; i++)
                        {
                            if (img[i].id === objJSON.codigo)
                            {
                                img[i].parentNode.parentNode.parentNode.removeChild(img[i].parentNode.parentNode.nextSibling);
                            }
                        }
                    }
                };
                function devolverSdOfecha(fecha){
                    if (fecha!==undefined)
                    {
                        return  fecha.dayOfMonth+' \ '+fecha.month+' \ '+fecha.year;
                    }else
                    {
                        return 'S/D*';
                    }
                };
                function insertaDespues(padre, nodo, nuevoNodo) {
                    var siguiente = nodo.nextSibling;
                    if (siguiente === null) {
                        padre.appendChild(nuevoNodo);
                    } else
                    {
                        padre.insertBefore(nuevoNodo, siguiente);
                    }
                }
                ;
            };


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
                    Bienvenido ${Cliente.getNombre()}
                </div>
                <div class="clear"></div>
                <div class="contenido">
                    <h1>Sus pedidos    </h1>
                    <a name="inicio"></a>
                    <p>Estos son sus pedidos. </p>
                    <c:if test="${pedidoRealizacion!=null}">
                        <p>Actualmente dispone de un <a href="PedidoRealizacion">pedido en realización</a>.</p>
                    </c:if>
                    <p>&nbsp;<span class="atajo"><a href="#comp">Completados</a> &nbsp; | &nbsp; <a href="#anul">Anulados</a></span></p>  
                    <table width="95%">
                        <colgroup>
                            <col width="5%">
                            <col width="5%">
                            <col width="14%">
                            <col width="14%">
                            <col width="51%">
                            <col width="11%">
                        </colgroup>
                        <tr>
                            <td colspan="6">Listado de pedidos pendientes </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>C&oacute;digo </td>
                            <td>Fecha </td>
                            <td>Direcci&oacute;n de entrega </td>
                            <td>Importe </td>
                        </tr>   
                        <c:forEach var="t" items="${PedidosPendientes}" varStatus="status">
                            <tr ${(status.count%2!=0) ? "class=\"par\"" : ""  } >
                                <td style="text-align: center">
                                    <a href="<c:url value='CreateExcell'>
                                           <c:param name="cp" value="${t.getCodigo()}"></c:param>>
                                       </c:url>">
                                        <img src="../img/excel.png" title="Descargar en Excel"/>
                                    </a>
                                </td>

                                <td  style="text-align: center">
                                    <a href="<c:url value='ConfirmaAnulacion'> <c:param name="cp" value="${t.getCodigo()}"/> </c:url>">
                                            <img src="../img/cancel.png" title="Cancelar el pedido"/>
                                        </a>
                                    </td>
                                    <td>
                                        <a title="Insertar detalle pedido" href="#" id="${t.getCodigo()}">[+]</a>
                                    <a href="<c:url value='VerPedido'>
                                           <c:param name="cp" value="${t.getCodigo()}"></c:param>>
                                       </c:url>">
                                        <c:out value="${t.getCodigo()}"/>
                                </td>
                                <td><fmt:formatDate type="date"  value="${t.getFechaCierre().getTime()}"/></td>
                                <td>${t.getDirEntrega().getCalle()}, ${t.getDirEntrega().getCiudad()}.  ${t.getDirEntrega().getCp()}- ${t.getDirEntrega().getProvincia()} </td>
                                <td style="text-align: right"><fmt:formatNumber type="currency" value="${t.getImporte()}"/> </td>
                            </tr>                    
                        </c:forEach>

                    </table>

                    <span class="atajo"><a href="#inicio">Inicio</a></span>

                    <p>&nbsp;</p>          
                    <a name="comp"></a>
                    <table width="95%">
                        <colgroup>
                            <col width="5%">
                            <col width="14%">
                            <col width="14%">
                            <col width="56%">
                            <col width="11%">
                        </colgroup>
                        <tr>
                            <td colspan="5">Listado de pedidos Completados</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>C&oacute;digo </td>
                            <td>Fecha </td>
                            <td>Direcci&oacute;n de entrega </td>
                            <td>Importe </td>
                        </tr>
                        <c:forEach var="t" items="${PedidosCompletados}" varStatus="status">
                            <tr ${(status.count%2!=0) ? "class=\"par\"" : ""  } >
                                <td  style="text-align: center">
                                    <a href="<c:url value='CreateExcell'>
                                           <c:param name="cp" value="${t.getCodigo()}"></c:param>>
                                       </c:url>">
                                        <img src="../img/excel.png" title="Descargar en Excel"/>
                                    </a>
                                </td>
                                <td>
                                    <a title="Insertar detalle pedido" href="#" id="${t.getCodigo()}">[+]</a>
                                    <a href="<c:url value='VerPedido'>
                                           <c:param name="cp" value="${t.getCodigo()}"></c:param>>
                                       </c:url>">
                                        <c:out value="${t.getCodigo()}"/>
                                    </a>
                                </td>
                                <td> <fmt:formatDate type="date" value="${t.getFechaCierre().getTime()}" /></td>
                                <td>${t.getDirEntrega().getCalle()}, ${t.getDirEntrega().getCiudad()}.  ${t.getDirEntrega().getCp()}- ${t.getDirEntrega().getProvincia()} </td>
                                <td style="text-align: right"><fmt:formatNumber type="currency" value="${t.getImporte()}"/> </td>
                            </tr>                    
                        </c:forEach>
                    </table>

                    <span class="atajo"><a href="#inicio">Inicio</a></span>

                    <p>&nbsp;</p>
                    <a name="anul"></a>
                    <table width="55%">
                        <colgroup>
                            <col width="10%">
                            <col width="26%">
                            <col width="32%">
                            <col width="32%">
                        </colgroup>
                        <tr>
                            <td colspan="4">Listado de pedidos anulados </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>C&oacute;digo</td>
                            <td>Fecha cierre</td>
                            <td>Fecha anulación</td>
                        </tr>            
                        <c:forEach var="t" items="${PedidosAnulados}" varStatus="status">
                            <tr ${(status.count%2!=0) ? "class=\"par\"" : ""  }>
                                <td style="text-align: center">
                                    <a href="<c:url value='CreateExcell'>
                                           <c:param name="cp" value="${t.getCodigo()}"></c:param>>
                                       </c:url>">
                                        <img src="../img/excel.png" title="Descargar en Excel"/>
                                    </a>
                                </td>
                                <td><a href="<c:url value='VerPedidoAnulado'>
                                           <c:param name="cp" value="${t.getCodigo()}"></c:param>>
                                       </c:url>">
                                        <c:out value="${t.getCodigo()}"/>
                                    </a>
                                </td>

                                <td><fmt:formatDate type="date"  value="${t.getFechaCierre().getTime()}"/></td>
                                <td><fmt:formatDate type="date"  value="${t.getFechaAnulacion().getTime()}"/></td>
                            </tr>
                        </c:forEach>
                    </table>

                    <span class="atajo"><a href="#inicio">Inicio</a></span>
                </div>

                <div class="clear"></div>
            </div>
            <div class="separa"></div>

            <jsp:include page="pie.html"/>

        </div>
    </body>
</html>
