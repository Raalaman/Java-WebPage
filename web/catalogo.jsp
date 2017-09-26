
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
        <script type='text/javascript' src='js/ChangePath.js'></script>
        <link href="css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="css/catalogoMosaico.css" rel="stylesheet" media="all" type="text/css">
        <script type="text/javascript">
            window.onload = function ()
            {
                var elementosCatalogo = document.getElementsByTagName("li");
                for (var i = 0; i < elementosCatalogo.length; i++) {
                    if (elementosCatalogo[i].className === "item redondeo")
                    {
                        if (elementosCatalogo[i].addEventListener) {
                            elementosCatalogo[i].addEventListener("dblclick", ChangePath, false);
                        } else if (window.attachEvent)
                        {
                            elementosCatalogo[i].attachEvent(" on " + "dblclick", ChangePath);
                        } else {
                            elementosCatalogo[i][ " on " + "dblclick" ] = ChangePath;
                        }
                    }
                }
            };
        </script>
    </head>

    <body >
        <jsp:include page="cabecera.jsp" /> 

        <div class="sombra">
            <div class="nucleo">

                <div id="migas">
                    <a href="<c:url value="index.jsp"/>" title="Inicio" >Inicio</a>&nbsp; | &nbsp; 
                    <a href="<c:url value="BuscarArticulos"/>" title="Hojear catalogo">Hojear catalogo</a>	<!-- &nbsp; | &nbsp; 
                    <a href="..." title="Otra cosa">Otra cosa</a>   -->	
                </div>
                <div class="contenido">
                    <h1>Nuestros productos</h1>
                    <p>Puede buscar los productos que necesite en nuestro cat&aacute;logo. Lo hemos organizado por marcas, tipo de electrodom&eacute;stico y rango de precios. Lo precios indicados en rojo corresponden a ofertas. </p>
                    <div class="filtroCatalogo">
                        <form name="filtroCatalogo" id="filtroCatalogo" action="<c:url value="BuscarArticulos"/>" method="get">

                            <label for="tipo">Tipo :</label>
                            <select name="tipo" id="tipo">
                                <option value="-1">- Cualquiera -</option>
                                <c:forEach var="t" items="${tiposArticulos}">
                                    <%--
                                     La base de datos no le importa que los datos estén en mayúsculas o no, por lo tanto se puede dar este caso.
                                     ?tipo=horno, esto hace que la base de datos saque en el catálogo solo los articulos horno
                                     Pero luego en el select aparece seleccionado cualquiera dado que el operador == es Case Sensitive.
                                    --%>
                                    <option value="${t}" ${(fn:toUpperCase(t) == fn:toUpperCase(tipo) ? 'selected' : '')} >${t}</option>
                                </c:forEach>

                            </select>

                            <label for="fabricante">Fabricante :</label>
                            <select name="fabricante" id="fabricante">
                                <option value="-1">- Cualquiera -</option>
                                <c:forEach var="t" items="${tiposFabricantes}">
                                    <%--
                                     La base de datos no le importa que los datos estén en mayúsculas o no, por lo tanto se puede dar este caso.
                                     ?tipo=horno, esto hace que la base de datos saque en el catálogo solo los articulos horno
                                     Pero luego en el select aparece seleccionado cualquiera dado que el operador == es Case Sensitive.
                                    --%>
                                    <option value="${t}" ${(fn:toUpperCase(t) == fn:toUpperCase(fabricante) ? 'selected' : '')} >${t}</option>
                                </c:forEach>
                            </select>

                            <label for="precio">Precio :</label>
                            <select name="precio" id="precio">
                                <option value="-1">- Cualquiera -</option>
                                <option ${precio=="10-50" ? 'selected' :''} value="10-50">10-50 &euro;</option>
                                <option ${precio=="50-100" ? 'selected' :''} value="50-100">50-100 &euro;</option>
                                <option ${precio=="100-200" ? 'selected' :''} value="100-200">100-200 &euro;</option>
                                <option ${precio=="200-500" ? 'selected' :''} value="200-500">200-500 &euro;</option>
                                <option ${precio=="500-1000" ? 'selected' :''} value="500-1000">500-1.000 &euro;</option>
                                <option ${precio=="1000" ? 'selected' :''} value="1000">Mas de 1.000 &euro;</option>
                            </select>
                            <br>
                            <label for="nombre">Nombre :</label>
                            <input  type="text" id="nombre" name="nombreArti" value="${nombreArti!=null ? nombreArti:''}" >

                            <label for="codigo">Codigo :</label>
                            <input  type="text" id="codigo" name="nombreCode" value="${nombreCode!=null ? nombreCode:''}">


                            <input name="buscar" id="buscar" type="image" title="Buscar" src="img/search25.png" alt="Buscar">

                        </form>

                        <div class="modovisual">
                            <a href="<c:url value="BuscarArticulos"/>">Mosaico</a> &nbsp; | &nbsp; <a href="<c:url value="BuscarArticulos"/>">Lista</a>
                        </div>
                        <div class="clear"></div>
                    </div>


                    <c:if test="${paginador != null}">
                        <div class="resumResul">
                            Encontrados ${paginador.getNumRegistros()} artículos.
                            <c:if test="${paginador.getNumRegistros() > 0}">
                                Mostrando página ${paginaActual} de ${paginador.getNumPaginas()}.
                                <span class="paginador"> 
                                    <c:if  test="${paginaActual!=1}">   

                                        <a href="
                                           <c:url value="BuscarArticulos">
                                               <c:if test="${paginaActual!=null}">
                                                   <c:param name="pag"   value="${paginador.anterior(paginaActual)}" />
                                               </c:if>
                                               <c:if test="${nombreCode!=null}">
                                                   <c:param name="nombreCode"   value="${nombreCode}" />
                                               </c:if>
                                               <c:if test="${nombreArti!=null}">
                                                   <c:param name="nombreArti"   value="${nombreArti}" />
                                               </c:if>
                                               <c:if test="${tipo!=null}">
                                                   <c:param name="tipo"   value="${tipo}" />
                                               </c:if>
                                               <c:if test="${fabricante!=null}">
                                                   <c:param name="fabricante"   value="${fabricante}" />
                                               </c:if>
                                               <c:if test="${precio!=null}">
                                                   <c:param name="precio"   value="${precio}" />
                                               </c:if>
                                               <c:if test="${precio!=null}">
                                                   <c:param name="precio"   value="${precio}" />
                                               </c:if>
                                           </c:url>
                                           ">anterior</a>


                                    </c:if>  
                                    <c:forEach var="n" items="${paginador.adyacentes(paginaActual)}"> 
                                        <c:choose>
                                            <c:when test="${n == paginaActual}">   
                                                <span>${n}</span>    
                                            </c:when>
                                            <c:otherwise>
                                                <a href="
                                                   <c:url value="BuscarArticulos">
                                                       <c:if test="${paginaActual!=null}">
                                                           <c:param name="pag"   value="${n}" />
                                                       </c:if>
                                                       <c:if test="${nombreCode!=null}">
                                                           <c:param name="nombreCode"   value="${nombreCode}" />
                                                       </c:if>
                                                       <c:if test="${nombreArti!=null}">
                                                           <c:param name="nombreArti"   value="${nombreArti}" />
                                                       </c:if>
                                                       <c:if test="${tipo!=null}">
                                                           <c:param name="tipo"   value="${tipo}" />
                                                       </c:if>
                                                       <c:if test="${fabricante!=null}">
                                                           <c:param name="fabricante"   value="${fabricante}" />
                                                       </c:if>
                                                       <c:if test="${precio!=null}">
                                                           <c:param name="precio"   value="${precio}" />
                                                       </c:if>
                                                       <c:if test="${precio!=null}">
                                                           <c:param name="precio"   value="${precio}" />
                                                       </c:if>
                                                   </c:url>


                                                   ">${n}</a>
                                            </c:otherwise>   
                                        </c:choose>
                                    </c:forEach>
                                    <c:if  test="${paginaActual!=paginador.getNumPaginas()}">   
                                        <a href="
                                           <c:url value="BuscarArticulos">
                                               <c:if test="${paginaActual!=null}">
                                                   <c:param name="pag"   value="${paginador.siguiente(paginaActual)}" />
                                               </c:if>
                                               <c:if test="${nombreCode!=null}">
                                                   <c:param name="nombreCode"   value="${nombreCode}" />
                                               </c:if>
                                               <c:if test="${nombreArti!=null}">
                                                   <c:param name="nombreArti"   value="${nombreArti}" />
                                               </c:if>
                                               <c:if test="${tipo!=null}">
                                                   <c:param name="tipo"   value="${tipo}" />
                                               </c:if>
                                               <c:if test="${fabricante!=null}">
                                                   <c:param name="fabricante"   value="${fabricante}" />
                                               </c:if>
                                               <c:if test="${precio!=null}">
                                                   <c:param name="precio"   value="${precio}" />
                                               </c:if>
                                               <c:if test="${precio!=null}">
                                                   <c:param name="precio"   value="${precio}" />
                                               </c:if>
                                           </c:url>
                                           ">Siguiente</a>   
                                    </c:if>   
                                </span>
                            </div>

                            <ul id="ResultadoBusqueda" class="resultBusqueda">
                                <c:forEach var="a" items="${articulos}"> 
                                    <li  class="item redondeo">
                                        <div class="foto">
                                            <a href="<c:url value="fichaArticulo.jsp"> <c:param  name="cart" value="${a.getCodigo()}"/> </c:url>">
                                                <img src="img/fotosElectr/${a.getFoto()}" alt="${a.getCodigo()}" longdesc="${a.getNombre()}" width="80"></a>
                                        </div>
                                        <div class="datos">
                                            <span>${a.getNombre()}</span>
                                            <div class="precio">
                                                <span class="">${a.getPvp()} &euro;</span>
                                            </div>
                                            <div class="carro">
                                                <a href="
                                                   <c:url value='clientes/GestionaPedido'> 
                                                       <c:param  name="accion" value="Comprar"/> 
                                                       <c:param  name="ca" value="${a.getCodigo()}"/> 
                                                   </c:url>
                                                   ">
                                                    <img src="img/shopcartadd_16x16.png" title="Añadir a mi carro de la compra">
                                                </a>
                                            </div>
                                        </div>			  
                                        <div class="codigo"><a href="<c:url value="fichaArticulo.jsp"> <c:param  name="cart" value="${a.getCodigo()}"/> </c:url>">${a.getCodigo()}</a></div>
                                        </li>
                                </c:forEach>
                            </ul>	    
                        </c:if>
                    </c:if>
                    <div class="clear"></div>


                </div>

                <div class="separa"></div>

                <jsp:include page="pie.html" /> 

            </div>
    </body>
</html>
