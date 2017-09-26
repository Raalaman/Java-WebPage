<%-- 
    Document   : donde
    Created on : 11-may-2017, 20:31:42
    Author     : Manga
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Donde estamos</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.0.47/jquery.fancybox.min.css" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.0.47/jquery.fancybox.min.js"></script>
        <link href="css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <script type='text/javascript' src='js/SetLocation.js'></script>
        <script>
            window.onload = function ()
            {
                var button = document.getElementsByClassName("buttonSet")[0];
                if (button.addEventListener) {
                    button.addEventListener("click", obtener_localizacion, false);
                } else if (window.attachEvent)
                {
                    button.attachEvent(" on " + "click", obtener_localizacion);
                } else {
                    button[ " on " + "click" ] = obtener_localizacion;
                }
                document.getElementById("fancyboxButton").onclick = function (evento) {

                    var value = document.getElementsByClassName("cpBuscar")[0].value;
                    if (value)
                    {
                        $.ajax("api/GetCoordenadas?cp=" + escape(value), {
                            dataType: 'json',
                            cache: 'false',
                            method: 'GET',
                            // Lo he puesto sincrono porque sino se hacia la peticion a dondeFancyBox.jsp sin cambiar la url
                            async: false
                        }
                        )
                                .done(CambiarDataSrc)
                                .fail(function (xhr, status, ex) {
                                    alert("Error (" + xhr.status + "):" + xhr.statusText);
                                });
                    } else
                    {
                        alert("Introduce un Codigo postal");
                    }
                };
                function CambiarDataSrc(data)
                {
                    if (data.coordX && data.coordX)
                    {
                    var fancy = document.getElementById("fancyboxButton");
                    fancy.dataset.src = window.location.origin+'${pageContext.request.contextPath}/dondeFancyBox.jsp?long=' + escape(data.coordY) + '&lati=' + escape(data.coordX);
                    }
                }

            };
        </script>
        
    </head>
    <body >
        <jsp:include page="cabecera.jsp"/>
        <div class="sombra">
            <div class="nucleo">
                <div id="migas">
                    <a href="index.jsp" title="Inicio" >Inicio</a>
                </div>
                <div class="contenido">
                    <h1>Donde estamos</h1>
                    <div class="centro">
                        <h2>Oficinas centrales </h2>
                        <p><strong>Electrosa S.L. </strong><br>
                            C/ ${almacenPrincipal.getDireccion().getCalle()}
                            ${almacenPrincipal.getDireccion().getCp()} - ${almacenPrincipal.getDireccion().getCiudad()} <br>
                            ${almacenPrincipal.getDireccion().getProvincia()} <br><br>
                            Tfno: (+34) 941 000 000 - FAX:  (+34) 941 000 001</p>
                        <h2>Almacenes de zona </h2>
                        <p>Para  facilitar la gesti&oacute;n ELECTROSA considera la superficie nacional dividida en  cinco zonas: centro, norte, sur, este y oeste. En cada zona dispone de un  almac&eacute;n (almac&eacute;n de referencia de la zona). Puede visitarlos en las siguientes direcciones:</p>

                        <c:forEach var="t" items="${almacenesCentro}">
                            <div class="dirAlmacen"><strong>Zona Centro </strong><br>
                                ${t.getDireccion().getCalle()}
                                ${t.getDireccion().getCp()} - ${t.getDireccion().getCiudad()} <br>
                                ${t.getDireccion().getProvincia()} <br>
                            </div>
                        </c:forEach>
                        <c:forEach var="t" items="${almacenesNorte}">
                            <div class="dirAlmacen"><strong>Zona Norte </strong><br>
                                ${t.getDireccion().getCalle()}
                                ${t.getDireccion().getCp()} - ${t.getDireccion().getCiudad()} <br>
                                ${t.getDireccion().getProvincia()} <br>
                            </div>
                        </c:forEach>
                        <c:forEach var="t" items="${almacenesSur}">
                            <div class="dirAlmacen"><strong>Zona Sur </strong><br>
                                ${t.getDireccion().getCalle()}
                                ${t.getDireccion().getCp()} - ${t.getDireccion().getCiudad()} <br>
                                ${t.getDireccion().getProvincia()} <br>
                            </div>
                        </c:forEach>
                        <c:forEach var="t" items="${almacenesEste}">
                            <div class="dirAlmacen"><strong>Zona Este </strong><br>
                                ${t.getDireccion().getCalle()}
                                ${t.getDireccion().getCp()} - ${t.getDireccion().getCiudad()} <br>
                                ${t.getDireccion().getProvincia()} <br>
                            </div>
                        </c:forEach>
                        <c:forEach var="t" items="${almacenesOeste}">
                            <div class="dirAlmacen"><strong>Zona Oeste </strong><br>
                                ${t.getDireccion().getCalle()}
                                ${t.getDireccion().getCp()} - ${t.getDireccion().getCiudad()} <br>
                                ${t.getDireccion().getProvincia()} <br>
                            </div>
                        </c:forEach>
                        <div class="clear"></div>
                        <div>

                            <input type="text" name="cpBuscar" class="cpBuscar" id="cpBuscar" size="55" value="">
                            <button id="fancyboxButton" data-fancybox data-src="dondeFancyBox.jsp" class="buttonEncuentra" href="javascript:;">Encuentra almacen</button>

                            <button class="buttonSet" >Mi posición</button>
                        </div>
                        <div id="map"  style="height: 500px; width: 500px;"></div>
                        <script>
                            function initMap() {
                                var center = {lat: ${almacenPrincipal.getCoordX()}, lng: ${almacenPrincipal.getCoordY()}};
                                map = new google.maps.Map(document.getElementById('map'), {
                                    zoom: 6,
                                    center: center
                                });
                                var position;
                            <c:forEach items="${listaAlmacenes}" var="almacen">
                                position = {lat: ${almacen.getCoordX()}, lng: ${almacen.getCoordY()}};
                                var marker = new google.maps.Marker({
                                    position: position,
                                    map: map
                                });
                            </c:forEach>
                            }
                        </script>
                        <script async defer
                                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAsdlXTV_hfJNtwHQIZFZlU8IuCA29N3Mw&callback=initMap">
                        </script>
                    </div>
                </div>
            </div>
            <jsp:include page="pie.html"/>
        </div>
    </body>
</html>