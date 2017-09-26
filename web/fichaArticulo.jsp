<%-- 
    Document   : fichaArticulo
    Created on : 02-mar-2017, 16:49:56
    Author     : raalaman
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.net.URLEncoder"%>
<%@page import="paw.model.ExcepcionDeAplicacion"%>
<%@page import="paw.model.Articulo"%>
<%@page import="paw.bd.GestorBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        Articulo a = null;
        try {
            request.setCharacterEncoding("UTF-8");
            String cart = request.getParameter("cart");
            if (cart == null || cart.trim().length() == 0) {
                response.sendRedirect("index.html");
                return;
            }
            a = new GestorBD().getArticulo(cart);
            if (a == null) {
                request.setAttribute("link", "index.html");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "El artículo " + cart + " no existe");
                return;
            }
        } catch (ExcepcionDeAplicacion ex) {
            request.setAttribute("link", "index.html");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de del servidor");
            return;
        }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title><%=a.getNombre()%></title>
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
        <meta name="description" content="La descripcion del producto aqui !!" lang="es-ES">
        <meta name="keywords" content="fabricante y tipo de electrodomésticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">
        <link href="css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="css/fichaProducto.css" rel="stylesheet" media="all" type="text/css">
        <script>
            window.onload = function () {
                var img = document.getElementById("ImgStock");
                if (img.addEventListener) {
                    img.addEventListener("click", pideStock, false);
                } else if (window.attachEvent)
                {
                    img.attachEvent(" on " + "click", pideStock);
                } else {
                    img[ " on " + "click" ] = pideStock;
                }
            };
            function pideStock() {
                $.ajax(" api/GetStockArticulo?cart=" + escape('<%= a.getCodigo()%>'),
                        {dataType: 'text',
                            cache: 'false',
                            method: 'GET',
                            async: true
                        }
                ).done(actualizaStock)
                .fail(function (xhr, status, ex) {
                    alert("Error (" + xhr.status + "):" + xhr.statusText);
                });
            }
            function actualizaStock()
            {
                var arstElto = document.getElementById("stock");
                arstElto.innerHTML = arguments[0] + ' unidades';
            }
        </script>
    </head>

    <body >
        <jsp:include page="cabecera.jsp" />
        < div class = "sombra" >
        <div class="nucleo">
            <div id="migas">
                <a href="index.html" title="Inicio" >Inicio</a>&nbsp; | &nbsp; 
                <c:choose>
                    <c:when test="${header['Referer']!=null}">
                        <a href="${header['Referer']}" title="Hojear catalogo">Hojear cat&aacute;logo</a>&nbsp; | &nbsp; 
                    </c:when>
                    <c:otherwise>
                        <a href="BuscarArticulos" title="Hojear catalogo">Hojear cat&aacute;logo</a>&nbsp; | &nbsp; 
                    </c:otherwise>
                </c:choose>
                <%=a.getCodigo()%>	<!-- &nbsp; | &nbsp; 
                    <a href="..." title="Otra cosa">Otra cosa</a>   -->	
            </div>

            <div class="contenido">

                <h1>Ficha t&eacute;cnica de <%=a.getCodigo()%></h1>
                <div class="fotoDetalle">
                    <img src="img/fotosElectr/<%=a.getFoto()%>" alt="Mie/080FO" longdesc="<%=a.getNombre()%>">
                </div>
                <div class="datosDetalle">
                    <h2><%=a.getNombre()%></h2>
                    <p><%=a.getDescripcion()%></p>
                    <div class="precio">
                        <span>Precio: <%=a.getPvp()%> &euro;</span>	
                    </div>
                    <div class="stockArt " >
                        <img id="ImgStock"
                             src="img/Almacen30x30.png" 
                             title="Ver stock disponible"
                             >&nbsp;
                        <span id="stock"></span><br/>
                    </div>

                    <div class="carroDetalle" >
                        <a href="
                           <c:url value='clientes/GestionaPedido'> 
                               <c:param  name="accion" value="Comprar"/> 
                               <c:param  name="ca" value="<%=a.getCodigo()%>"/> 
                           </c:url>
                           ">
                            <img src="img/AddCart2-50.png" title="Añadir a mi pedido en realización">
                        </a>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </div>

        <div class="separa"></div>
        <jsp:include page="pie.html" /> 
    
</body>
</html>
