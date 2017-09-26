<%-- 
    Document   : nuevoArticulo
    Created on : 04-may-2017, 16:38:24
    Author     : raalaman
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Nuevo art&iacute;culo</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">
        <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/formulario.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/clientes.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/estilomenu.css" rel="stylesheet" media="all" type="text/css" />
        <script src="//cdn.ckeditor.com/4.6.2/basic/ckeditor.js"></script>
        <script type='text/javascript' src='../js/FocusForm.js'></script>
        <script type="text/javascript">
            window.onload = function ()
            {
                FocusForm("fArtic");
            };
        </script>
    </head>

    <body >
        <div class="logo"><a href="../index.html"><img src="../img/LogoElectrosa200.png" border="0"></a></div>


        <div class="sombra">
            <div class="nucleo">
                <div id="lang">
                    <a href="index.html">Español</a> &nbsp; | &nbsp; <a href="index.html">English</a>
                </div>
            </div>
        </div>  

        <div class="barra_menus">
            <div class="pestanias">
                <div class="grupoPestanias">
                    <div class="pestaniaNoSel"><a href="../index.html">Para usuarios</a></div>
                    <div class="pestaniaSel">Intranet</div>
                </div>
            </div>

            <div id="cssmenu">
                <ul>
                    <li class='has-sub'><a href="index.html">Art&iacute;culos</a>
                        <ul>
                            <li><a href="listadoArticulos.jsp">Listar</a></li>
                            <li><a href="AddArticulo">A&ntilde;adir</a> </li>
                            <li><a href="HazEstadistica">Estadísticas</a> </li>
                        </ul>
                    </li>
                    <li class='has-sub'><a href="index.html">Clientes</a>	
                        <ul>
                            <li><a href="index.html">Listar</a> </li>
                            <li><a href="index.html">A&ntilde;adir</a> </li>
                        </ul>    
                    </li>
                    <li><a href="index.html">Pedidos</a></li>
                    <li class='last'><a href="index.html">Stocks</a></li>
                </ul>
                <div style="clear: left;"></div>
            </div>
        </div> 
        <div class="sombra">
            <div class="nucleo">
                <div id="migas">
                    <a href="index.html" title="Inicio" >Inicio</a>
                </div>

                <div class="clear"></div>

                <div class="contenido">

                    <h1>A&ntilde;adir un art&iacute;culo    </h1>
                    <p>Utilice el siguiente formulario.   </p>

                    <c:if test="${errores!=null && !errores.isEmpty()}">
                        <div class="alerta">
                            <c:forEach items="${errores}" var="error"> 
                                <c:out value="${error}"/><br>
                            </c:forEach>
                        </div>   
                    </c:if>  

                    <form id="fArtic" name="fArtic" action="AddArticulo" method="post" enctype="multipart/form-data">
                        <fieldset> 
                            <legend>Datos del art&iacute;culo </legend> 
                            <div class="field">
                                <label for="nombre">Nombre:
                                    <c:if test="${nombre==null}">
                                        <span class='Requerido'>Requerido</span>
                                    </c:if>
                                </label>
                                <input type="text" name="nombre" id="nombre" size="55" value="${nombre!=null ? nombre : ''}">
                            </div>
                            <div class="field">
                                <label for="pvp">P.V.P:
                                    <c:if test="${pvp==null}">
                                        <span class='Requerido'>Requerido</span>
                                    </c:if>
                                </label>
                                <input type="text" name="pvp" id="pvp" size="15" value="${pvp!=null || pvp!=0.0 ? pvp : ''}">
                            </div>
                            <div>
                                <div class="left">
                                    <div class="field">
                                        <label for="tipo">Tipo:
                                            <c:if test="${tipo==null}">
                                                <span class='Requerido'>Requerido</span>
                                            </c:if>
                                        </label>
                                        <select name="tipo" id="tipo">
                                            <option value="">- Elige -</option> 
                                            <c:forEach var="t" items="${tiposArticulos}">
                                                <option value="${t}" ${(t == tipo ? 'selected' : '')}>${t}</option>
                                            </c:forEach>                        
                                        </select>       
                                        <input id="otroTip" type="checkbox" name="" value="" title="Introduce otro tipo" disabled/>               
                                        Otro 
                                        <div id="otrotipoCont"><!--<label>&nbsp;</label><input class="text" type="text" name="tipo" id="tipo" value="" >--></div>
                                    </div>
                                </div>
                                <div class="right">
                                    <div class="field">
                                        <label for="fabricante">Fabricante:
                                            <span class='Requerido'>Requerido</span>
                                        </label>
                                        <select name="fabricante" id="fabricante">
                                            <option value="">- Elige -</option>                       
                                            <c:forEach var="t" items="${tiposFabricantes}">
                                                <option value="${t}" ${(t == fabricante ? 'selected' : '')}>${t}</option>
                                            </c:forEach>                   
                                        </select>
                                        <input id="otroFab" type="checkbox" name="" value=""  title="Introduce otro fabricante" disabled/>
                                        Otro 
                                        <div id="otrofabricanteCont"><!--<label>&nbsp;</label><input class="text" type="text" name="fabricante" id="fabricante" value="" >--></div>
                                    </div>
                                </div>		
                            </div>
                            <label for="descripcion">
                                Descripci&oacute;n:
                            </label>
                            <div class="field">

                                <textarea name="descripcion" id="descripcion">${descripcion!=null ? descripcion : ''}</textarea>
                            </div>
                            <script>
                                CKEDITOR.editorConfig = function (config) {
                                    config.toolbarGroups = [
                                        {name: 'clipboard', groups: ['clipboard', 'undo']},
                                        {name: 'document', groups: ['mode', 'document', 'doctools']},
                                        {name: 'forms', groups: ['forms']},
                                        {name: 'editing', groups: ['find', 'selection', 'spellchecker', 'editing']},
                                        '/',
                                        {name: 'basicstyles', groups: ['basicstyles', 'cleanup']},
                                        {name: 'paragraph', groups: ['list', 'indent', 'blocks', 'align', 'bidi', 'paragraph']},
                                        {name: 'links', groups: ['links']},
                                        {name: 'insert', groups: ['insert']},
                                        {name: 'styles', groups: ['styles']},
                                        {name: 'colors', groups: ['colors']},
                                        {name: 'tools', groups: ['tools']},
                                        {name: 'others', groups: ['others']},
                                        {name: 'about', groups: ['about']}
                                    ];
                                    config.removeButtons = 'Source,Save,Templates,NewPage,Preview,Print,Copy,Cut,Undo,Redo,Paste,PasteText,PasteFromWord,Find,Replace,SelectAll,Scayt,Form,Radio,TextField,Textarea,Select,Button,ImageButton,Strike,Subscript,Superscript,CopyFormatting,RemoveFormat,Outdent,Indent,CreateDiv,Blockquote,Maximize,ShowBlocks,TextColor,BGColor,Styles,Format,Font,FontSize,Iframe,PageBreak,SpecialChar,Smiley,HorizontalRule,Table,Flash,Image,BidiRtl,BidiLtr,JustifyLeft,JustifyCenter,JustifyRight,Language,Anchor,Underline,Checkbox,HiddenField';
                                    config.uiColor = '#D5E6D9';
                                };
                                CKEDITOR.replace('descripcion');
                                
                            </script>
                            <div class="field">
                                <label for="fichFoto">
                                    Fichero fotograf&iacute;a:
                                </label>
                                <input type="file" name="fichFoto" id="fichFoto" value="${part!=null ? part : ''}">
                            </div>


                        </fieldset>


                        <fieldset class="submit"> 
                            <div class="right">
                                <div class="field">
                                    <input class="submitb" type="submit"  value="Enviar los datos" >  
                                </div>
                            </div>
                        </fieldset>  
                    </form>

                </div>
            </div>

            <div class="separa"></div>

            <jsp:include page="pie.html"/>

        </div>
    </body>
</html>
