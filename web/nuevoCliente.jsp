<%-- 
    Document   : nuevoCliente
    Created on : 30-mar-2017, 21:01:06
    Author     : Manga
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Registro</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">
        <script src='https://www.google.com/recaptcha/api.js'></script>
        <script type='text/javascript' src='js/FocusForm.js'></script>
        <script type='text/javascript' src='js/CambiarContra.js'></script>
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
        <link href="css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="css/formulario.css" rel="stylesheet" media="all" type="text/css">
        <script type="text/javascript">
            window.onload = function ()
            {
                FocusForm("fCliente");
                document.getElementById("MostrarContra").onclick = function (evento)
                {
                    var type;
                    if (this.text === "Mostrar Contraseña")
                    {
                        this.text = "Ocultar Contraseña";
                        type = "text";

                    } else
                    {
                        this.text = "Mostrar Contraseña";
                        type = "password";
                    }
                    document.getElementById("pwd").type = type;
                    document.getElementById("rpwd").type = type;
                    if (evento)
                        evento.preventDefault();
                    else
                        window.event.returnValue = false;
                };
                var img = document.getElementById("cp");
                if (img.addEventListener) {
                    img.addEventListener("blur", pideStock, false);
                } else if (window.attachEvent)
                {
                    img.attachEvent(" on " + "blur", pideStock);
                } else {
                    img[ " on " + "blur" ] = pideStock;
                }
                function pideStock(evento) {
                    if ($('#provincia option:selected').text() === '- Elige -')
                    {
                        var evt = evento || window.event;
                        var target = evt.target || evt.srcElement;
                        $.ajax(" api/GetProvincia?cp=" + escape(target.value),
                                {dataType: 'text',
                                    cache: 'false',
                                    method: 'GET',
                                    async: true
                                }
                        ).done(actualizaProvincia)
                        .fail(function (xhr, status, ex) {
                          alert("Error (" + xhr.status + "):" + xhr.statusText);
                        });
                    }
                }
                function actualizaProvincia()
                {
                    var arstElto = document.getElementById("provincia");
                    for (var i = 0; i < arstElto.options.length; i++)
                    {
                        if (arstElto.options[i].value === arguments[0]) {
                            arstElto.selectedIndex = i;
                            break;
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
                    <a href="index.html" title="Inicio" >Inicio</a><!-- &nbsp; | &nbsp; 
                    <a href="..." title="Otra cosa">Otra cosa</a>   -->	
                </div>

                <div class="contenido">

                    <h1>Registro de cliente   </h1>
                    <p>Para poder realizar pedidos on-line a trav&eacute;s de nuestro sistema de pedidos es necesario que se registre. Desde Electrosa le agradecemos su confianza en nosotros. </p>
                    <p>Introduzca los datos solicitados mediante el  siguiente formulario.   </p>

                    <c:if test="${errores!=null && !errores.isEmpty()}">
                        <div class="alerta">
                            <c:forEach items="${errores}" var="error"> 
                                <c:out value="${error}"/><br>
                            </c:forEach>
                        </div>   
                    </c:if>


                    <form name="fCliente" id="fCliente" action="NuevoCliente" method="post">
                        <fieldset> 
                            <legend>Datos de la empresa </legend> 
                            <div class="field">
                                <label for="nombre">Raz&oacute;n social :
                                    <c:if test="${Cliente.getNombre()==null}">
                                        <span class="Requerido">Requerido</span>
                                    </c:if>
                                </label>
                                <input type="text" name="nombre" id="nombre" size="63" value="${Cliente.getNombre()!=null ? Cliente.getNombre() : ''}"/>
                            </div>
                            <div class="field">
                                <label for="cif">CIF:
                                    <c:if test="${Cliente.getCif()==null}">
                                        <span class="Requerido">Requerido</span>
                                    </c:if>
                                </label>
                                <input type="text" name="cif" id="cif" size="15" value="${Cliente.getCif()!=null ? Cliente.getCif() : ''}" />
                            </div>
                            <div class="field">
                                <label for="calle">Dirección:
                                    <c:if test="${Direccion.getCalle()==null}">
                                        <span class="Requerido">Requerido</span>
                                    </c:if>
                                </label>
                                <input class="text" type="text" name="calle" id="calle" size="63" value="${Direccion.getCalle()!=null ? Direccion.getCalle() : ''}" />
                            </div>

                            <div>
                                <div class="left">
                                    <div class="field">
                                        <label for="ciudad">Población:
                                            <c:if test="${Direccion.getCiudad()==null}">
                                                <span class="Requerido">Requerido</span>
                                            </c:if>
                                        </label>
                                        <input class="text" size="15" type="text" name="ciudad" id="ciudad" value="${Direccion.getCiudad()!=null ? Direccion.getCiudad() : ''}" />
                                    </div>
                                    <div class="field">
                                        <label for="cp">C.P.:
                                            <c:if test="${Direccion.getCp()==null}">
                                                <span class="Requerido">Requerido</span>
                                            </c:if>
                                        </label>
                                        <input class="text" type="text" name="cp" id="cp" size="10" value="${Direccion.getCp()!=null ? Direccion.getCp() : ''}" />
                                    </div>
                                </div>					
                                <!--
                                A Coruña,Álava,Albacete,Alicante,Almería,Araba,Asturias,Ávila,Badajoz,Baleares,Barcelona,Bizkaia,Burgos,Cáceres,Cádiz,Cantabria,Castellón,Ceuta,Ciudad Real,Córdoba,Cuenca,Gerona,Gipuzkoa,Girona,Granada,Guadalajara,Huelva,Huesca,Illes Balears,Jaén,La Coruña,La Rioja,Las Palmas,León,Lérida,Lleida,Lugo,Madrid,Málaga,Melilla,Murcia,Navarra,Orense,Ourense,Palencia,Pontevedra,Salamanca,Santa Cruz de Tenerife,Segovia,Sevilla,Soria,Tarragona,Teruel,Toledo,Valencia,Valladolid,Vizcaya,Zamora,Zaragoza
                                -->
                                <c:set var="ListaProvincias">A Coruña,Álava,Albacete,Alicante,Almería,Araba,Asturias,Ávila,Badajoz,Baleares,Barcelona,Bizkaia,Burgos,Cáceres,Cádiz,Cantabria,Castellón,Ceuta,Ciudad Real,Córdoba,Cuenca,Gerona,Gipuzkoa,Girona,Granada,Guadalajara,Huelva,Huesca,Illes Balears,Jaén,La Coruña,La Rioja,Las Palmas,León,Lérida,Lleida,Lugo,Madrid,Málaga,Melilla,Murcia,Navarra,Orense,Ourense,Palencia,Pontevedra,Salamanca,Santa Cruz de Tenerife,Segovia,Sevilla,Soria,Tarragona,Teruel,Toledo,Valencia,Valladolid,Vizcaya,Zamora,Zaragoza</c:set>
                                    <div class="right">
                                        <div class="field">
                                            <label for="provincia">Provincia:
                                            <c:if test="${Direccion.getProvincia()==null}">
                                                <span class="Requerido">Requerido</span>
                                            </c:if>
                                        </label>					
                                        <select name="provincia" id="provincia">
                                            <option value="">- Elige -</option>
                                            <c:forTokens items="${ListaProvincias}" delims="," var="t">
                                                <option value="${t}" ${(t == Direccion.getProvincia() ? 'selected' : '')} >${t}</option>
                                            </c:forTokens>
                                        </select>
                                    </div>
                                    <div class="field">
                                        <label for="tfno">Teléfono:<!--<span class="Requerido">Requerido</span> --></label>
                                        <input class="text" type="text" name="tfno" id="tfno" value="${Cliente.getTfno()!=null ? Cliente.getTfno() : ''}" />
                                    </div>
                                </div>		
                            </div>

                            <div class="field">
                                <label for="email">Email:
                                    <c:if test="${Cliente.getEmail()==null}">
                                        <span class="Requerido">Requerido</span>
                                    </c:if>
                                </label>
                                <input class="text" type="text" name="email" id="email" size="40" value="${Cliente.getEmail()!=null ? Cliente.getEmail() : ''}" />
                            </div>
                        </fieldset>

                        <fieldset> 
                            <legend>Datos de acceso</legend>
                            <div class="field">
                                <label for="login">Nombre de usuario:
                                    <c:if test="${login==null}">
                                        <span class="Requerido">Requerido</span>
                                    </c:if>
                                </label>
                                <input class="text" type="text" name="login" id="login" size="10" value="${login!=null ? login : ''}" />
                            </div>

                            <div>
                                <div class="left">
                                    <div class="field">
                                        <label for="pwd">Contraseña:<span class="Requerido">Requerido</span></label>
                                        <input class="text" type="password" name="pwd" id="pwd" size="10" value="" />
                                    </div>
                                </div>					
                                <div class="right">
                                    <div class="field">
                                        <label for="rpwd">Repita contraseña:<span class="Requerido">Requerido</span></label>
                                        <input class="text" type="password" name="rpwd" id="rpwd" size="10" value="" />
                                    </div>
                                </div>		
                            </div>
                        </fieldset>
                        <div class="left">
                            <a href="#" id="MostrarContra">Mostrar Contraseña</a>
                        </div>	
                        <fieldset> 
                            <legend>Captcha</legend>
                            <div class="g-recaptcha" data-sitekey="6Lf1WyAUAAAAAGch-0--zMcPXBdIvpVnFQW32DoU"></div>
                        </fieldset>

                        <fieldset class="submit"> 
                            <div class="left">
                                <div class="field">			  
                                    <input type="checkbox"  ${(privacidadOK == 1 ? 'checked' : '')} name="privacidad" value="1" id="privacidad"> <label for="privacidad" style="float:none">He leido y acepto la <a href="privacidad.html">Pol&iacute;tica de privacidad</a></label>
                                </div>
                            </div>
                            <div class="right">
                                <div class="fieldchecked">
                                    <input class="submitb" type="submit"  value="Enviar los datos" />  
                                </div>
                            </div>
                        </fieldset>  
                    </form>
                    <!--          <div style="font-size:75%;font-style: italic;line-height: 9pt">
                                <p> En cumplimiento de lo previsto en la Ley Org&aacute;nica 15/1999, de 13 de   diciembre, de Protecci&oacute;n de Datos de Car&aacute;cter Personal y su normativa de   desarrollo, le informamos de que los datos que nos facilita por medio   del presente formulario, junto a los que sean obtenidos por raz&oacute;n de su   condici&oacute;n de Usuario Registrado del Sitio Web, ser&aacute;n incorporados a un   fichero titularidad de Distribuidora de Electrodom&eacute;sticos, S.A. (en adelante, &ldquo;ELECTROSA&rdquo;), con domicilio en la calle Luis de Ulloa, s.n.                 26004 - Logro&ntilde;o, para su   tratamiento con la finalidad de gestionar su alta como Usuario   Registrado. Asimismo, le informamos que mediante el presente registro se   le asigna un nombre de usuario y contrase&ntilde;a que le permitir&aacute; iniciar   sesi&oacute;n, en cualquier momento, como Usuario Registrado del Sitio Web y,   por tanto, acceder a todas las funcionalidades del mismo. </p>
                                <p> Todos los campos que aparecen se&ntilde;alados con el texto &quot;requerido&quot; en el   presente formulario ser&aacute;n de obligada cumplimentaci&oacute;n, de tal modo que   la omisi&oacute;n de alguno de ellos podr&aacute; comportar la imposibilidad de que   podamos atender su solicitud de alta como Usuario Registrado. </p>
                                <p> Usted otorga su consentimiento expreso para que sus datos   puedan ser tratados por ELECTROSA para remitirle informaci&oacute;n acerca de   ofertas y promociones de la empresa. </p>
                                <p> A este respecto, le informamos de que la informaci&oacute;n promocional podr&aacute;   ser remitida tanto por v&iacute;a postal, como por correo electr&oacute;nico, SMS, o   cualquier otro medio de comunicaci&oacute;n electr&oacute;nica equivalente. En este   sentido, le informamos de que podr&aacute; oponerse en todo caso a que sus   datos sean tratados con esta finalidad, en cualquier momento, mediante   el env&iacute;o de un correo electr&oacute;nico a la siguiente direcci&oacute;n <strong>francisco.garcia@unirioja.es</strong> o usando los medios espec&iacute;ficos que se reconozcan en las propias comunicaciones. </p>
                                <p> Le rogamos que nos comunique inmediatamente cualquier modificaci&oacute;n de   sus datos a fin de que la informaci&oacute;n contenida en nuestros ficheros   est&eacute; en todo momento actualizada y no contenga errores. En este sentido,   usted manifiesta que la informaci&oacute;n y los datos que nos ha facilitado   son exactos, actuales y veraces. </p>
                                <p> Usted podr&aacute; ejercitar en cualquier momento su derecho de acceso,   rectificaci&oacute;n, cancelaci&oacute;n y oposici&oacute;n al tratamiento de sus datos, en   los t&eacute;rminos previstos legalmente, dirigi&eacute;ndose a la direcci&oacute;n   anteriormente se&ntilde;alada, as&iacute; como a la   siguiente direcci&oacute;n de correo electr&oacute;nico <strong>francisco.garcia@unirioja.es</strong>, y acompa&ntilde;ando copia de un documento oficial que acredite su identidad. </p>
                              </div>-->
                </div>

            </div>

            <jsp:include page="pie.html"/>

        </div>
    </body>
</html>