<%-- 
    Document   : index
    Created on : 30-mar-2017, 16:25:50
    Author     : raalaman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Bienvenido a Electrosa</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">

        <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/clientes.css" rel="stylesheet" media="all" type="text/css">
    </head>
    <jsp:include page="cabecera.jsp" /> 
    <body>
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
                    <h1>&Aacute;rea de clientes  </h1>
                    <p>Muchas gracias por confiar en nosotros. En Electrosa haremos todo lo posible para que su trato con nostros sea lo m&aacute;s ventajoso para su empresa. </p>
                </div><div class="centro">
                    <ul>
                        <li class="columna">  
                            <ul>  
                                <li class="celda">
                                    <div class="parteCelda">
                                        <a href=../"generica.hml"><img src="../img/motif_b2c_1_194.jpg" alt="" title=""></a>
                                    </div>
                                    <div class="parteCelda2">
                                        <h3><a href="../generica.html">El mejor Servicio de Mantenimiento</a></h3>
                                        Quién mejor que Electrosa para cuidar&nbsp;sus electrodomésticos
                                        <div style="float:right; padding:10px 5px">
                                            <a style="padding:5px 8px 5px 10px;background:#5C7E6F;color:#FFF" href="../generica.html" ><span>Sepa por qu&eacute; ...</span> </a>
                                        </div>
                                    </div>
                                    <div class="clear"></div>
                                </li> 
                                <li class="celda">
                                    <div class="parteCelda">
                                        <a class="parteCelda" href="../generica.html"><img src="../img/imag_teaser_encuentre-solucion.jpg" alt="" title="" ></a>
                                    </div>
                                    <div class="parteCelda2">
                                        <h3><a href="../generica.html">Encuentre la solución más adecuada</a></h3>
                                        Descubra&nbsp;el producto que necesita&nbsp;
                                        <div style="float:right; padding:10px 5px">
                                            <a style="padding:5px 8px 5px 10px;background:#5C7E6F;color:#FFF" href="../generica.html" ><span>Comenzar ...</span> </a>
                                        </div>
                                    </div>
                                    <div class="clear"></div>
                                </li> 
                            </ul>
                        </li>

                        <li class="columna"> 
                            <ul>  
                                <li class="celda">
                                    <div class="parteCelda">
                                        <a href="../generica.html"><img src="../img/registre-garantia.jpg" alt="" title=""></a>
                                    </div>
                                    <div class="parteCelda2">
                                        <h3 ><a href="../generica.html">Garantía y Puesta en Marcha</a></h3>
                                        Solicite su Garantía y Puesta en Marcha gratuita
                                        <div style="float:right; padding:10px 5px">
                                            <a style="padding:5px 8px 5px 10px;background:#5C7E6F;color:#FFF" href="../generica.html" ><span>Registrar ...</span> </a>
                                        </div>
                                    </div>
                                    <div class="clear"></div>
                                </li> 
                                <li class="celda">
                                    <div class="parteCelda">
                                        <a href="../generica.html"><img src="../img/atencion-cliente.jpg" alt="" title=""></a>
                                    </div>
                                    <div class="parteCelda2">
                                        <h3><a href="../generica.html">Atención al Cliente</a></h3>
                                        Contacte con Electrosa<br><strong>999 99 99 99</strong>
                                        <div style="float:right; padding:10px 5px">
                                            <a style="padding:5px 8px 5px 10px;background:#5C7E6F;color:#FFF" href="../generica.html" ><span>M&aacute;s ...</span> </a>
                                        </div>
                                    </div>
                                    <div class="clear"></div>
                                </li> 			
                            </ul>
                        </li>
                    </ul>

                </div>
                <div class="clear"></div>
            </div>

            <div class="separa"></div>
        </div>
        <jsp:include page="pie.html" /> 
    </body>
</html>
