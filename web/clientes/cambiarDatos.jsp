<%-- 
    Document   : cambiarDatos
    Created on : 31-mar-2017, 21:53:37
    Author     : Manga
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Cambiar Datos</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">
        <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/formulario.css" rel="stylesheet" media="all" type="text/css">
    </head>
    <body>
        <jsp:include page="cabecera.jsp"/>
        <c:set var="ListaProvincias">A Coruña,Álava,Albacete,Alicante,Almería,Araba,Asturias,Ávila,Badajoz,Baleares,Barcelona,Bizkaia,Burgos,Cáceres,Cádiz,Cantabria,Castellón,Ceuta,Ciudad Real,Córdoba,Cuenca,Gerona,Gipuzkoa,Girona,Granada,Guadalajara,Huelva,Huesca,Illes Balears,Jaén,La Coruña,La Rioja,Las Palmas,León,Lérida,Lleida,Lugo,Madrid,Málaga,Melilla,Murcia,Navarra,Orense,Ourense,Palencia,Pontevedra,Salamanca,Santa Cruz de Tenerife,Segovia,Sevilla,Soria,Tarragona,Teruel,Toledo,Valencia,Valladolid,Vizcaya,Zamora,Zaragoza</c:set>
            <div class="sombra">
                <div class="nucleo">
                    <div id="migas">
                    <a href="<c:url value='../index.html'/>" title="Inicio" >Inicio</a> &nbsp; | &nbsp; 
                    <a href="<c:url value='AreaCliente'/>" title="Área de cliente">Área de cliente</a>&nbsp; | &nbsp;  
                </div>

                <div class="contenido">
                    <h1>Modificacion datos del cliente   </h1>
                    <p>En este apartado dejamos modificar tus datos.</p>   
                    <c:if test="${errores!=null && !errores.isEmpty()}">
                        <div class="alerta">
                            <c:forEach items="${errores}" var="error"> 
                                <c:out value="${error}"/><br>
                            </c:forEach>
                        </div>   
                    </c:if>
                    <form name="fCliente" id="fCliente" action="CambiarDatos" method="post">
                        <fieldset> 
                            <legend>Modifica tus datos </legend> 
                            <div>
                                <div class="left">
                                    <div class="field">
                                        <label for="ciudad">Población:
                                            <c:if test="${DireccionCambio.getCiudad()==null}">
                                                <span class="Requerido">Requerido</span>
                                            </c:if>
                                        </label>
                                        <input class="text" size="15" type="text" name="ciudad" id="ciudad" value="${DireccionCambio.getCiudad()!=null ? DireccionCambio.getCiudad() : ''}" />
                                    </div>
                                    <div class="field">
                                        <label for="cp">C.P.:
                                            <c:if test="${DireccionCambio.getCp()==null}">
                                                <span class="Requerido">Requerido</span>
                                            </c:if>
                                        </label>
                                        <input class="text" type="text" name="cp" id="cp" size="10" value="${DireccionCambio.getCp()!=null ? DireccionCambio.getCp() : ''}" />
                                    </div>
                                    <div class="field">
                                        <label for="nombre">Raz&oacute;n social :
                                            <c:if test="${ClienteCambio.getNombre()==null}">
                                                <span class="Requerido">Requerido</span>
                                            </c:if>
                                        </label>
                                        <input type="text" name="nombre" id="nombre" size="63" value="${ClienteCambio.getNombre()!=null ? ClienteCambio.getNombre() : ''}"/>
                                    </div>
                                    <div class="field">
                                        <label for="cif">CIF:
                                            <c:if test="${ClienteCambio.getCif()==null}">
                                                <span class="Requerido">Requerido</span>
                                            </c:if>
                                        </label>
                                        <input type="text" name="cif" id="cif" size="15" value="${ClienteCambio.getCif()!=null ? ClienteCambio.getCif() : ''}" />
                                    </div>
                                    <div class="field">
                                        <label for="calle">Dirección:
                                            <c:if test="${DireccionCambio.getCalle()==null}">
                                                <span class="Requerido">Requerido</span>
                                            </c:if>
                                        </label>
                                        <input class="text" type="text" name="calle" id="calle" size="63" value="${DireccionCambio.getCalle()!=null ? DireccionCambio.getCalle() : ''}" />
                                    </div>
                                    <div class="field">
                                        <label for="provincia">Provincia:
                                            <c:if test="${DireccionCambio.getProvincia()==null}">
                                                <span class="Requerido">Requerido</span>
                                            </c:if>
                                        </label>					
                                        <select name="provincia" id="provincia">
                                            <option value="">- Elige -</option>
                                            <c:forTokens items="${ListaProvincias}" delims="," var="t">
                                                <option value="${t}" ${(t == DireccionCambio.getProvincia() ? 'selected' : '')} >${t}</option>
                                            </c:forTokens>
                                        </select>
                                    </div>

                                    <div class="field">
                                        <label for="email">Email:
                                            <c:if test="${ClienteCambio.getEmail()==null}">
                                                <span class="Requerido">Requerido</span>
                                            </c:if>
                                        </label>
                                        <input class="text" type="text" name="email" id="email" size="40" value="${ClienteCambio.getEmail()!=null ? ClienteCambio.getEmail() : ''}" />
                                    </div>
                                    <div class="field">
                                        <label for="tfno">Teléfono:<!--<span class="Requerido">Requerido</span> --></label>
                                        <input class="text" type="text" name="tfno" id="tfno" value="${ClienteCambio.getTfno()!=null ? ClienteCambio.getTfno() : ''}" />
                                    </div>
                                </div>					
                                <div class="right">
                                    <div class="field">
                                        <label for="ciudad">Población:
                                        </label>
                                        <input class="text" disabled size="15" type="text" name="ciudad" id="ciudad" value="${Direccion.getCiudad()!=null ? Direccion.getCiudad() : ''}" />
                                    </div>
                                    <div class="field">
                                        <label for="cp">C.P.:
                                        </label>
                                        <input class="text" disabled type="text" name="cp" id="cp" size="10" value="${Direccion.getCp()!=null ? Direccion.getCp() : ''}" />
                                    </div>
                                    <div class="field">
                                        <label for="nombre">Raz&oacute;n social :
                                        </label>
                                        <input type="text" disabled name="nombre" id="nombre" size="63" value="${Cliente.getNombre()!=null ? Cliente.getNombre() : ''}"/>
                                    </div>
                                    <div class="field">
                                        <label for="cif">CIF:
                                        </label>
                                        <input type="text" disabled name="cif" id="cif" size="15" value="${Cliente.getCif()!=null ? Cliente.getCif() : ''}" />
                                    </div>
                                    <div class="field">
                                        <label for="calle">Dirección:
                                        </label>
                                        <input class="text" disabled  type="text" name="calle" id="calle" size="63" value="${Direccion.getCalle()!=null ? Direccion.getCalle() : ''}" />
                                    </div>
                                    <div class="field">
                                        <label for="provincia">Provincia:
                                        </label>
                                        <input class="text" disabled type="text" name="calle" id="calle" size="63" value="${Direccion.getProvincia()!=null ? Direccion.getProvincia() : ''}" />
                                    </div>
                                    <div class="field">
                                        <label for="email">Email:
                                        </label>
                                        <input class="text" disabled  type="text" name="email" id="email" size="40" value="${Cliente.getEmail()!=null ? Cliente.getEmail() : ''}" />
                                    </div>
                                    <div class="field">
                                        <label for="tfno">Teléfono:<!--<span class="Requerido">Requerido</span> --></label>
                                        <input class="text" disabled type="text" name="tfno" id="tfno" value="${Cliente.getTfno()!=null ? Cliente.getTfno() : ''}" />
                                    </div>
                                </div>		
                            </div>



                        </fieldset>

                        <fieldset class="submit"> 
                            <div class="right">
                                <div class="fieldchecked">
                                    <input class="submitb" type="submit"  value="Enviar los datos" />  
                                </div>
                            </div>
                        </fieldset> 
                    </form>
                </div>
            </div>
            <jsp:include page="pie.html"/>
            <div class="separa"></div>
    </body>
</html>
