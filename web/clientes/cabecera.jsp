<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
            <div class="pestaniaSel">Para usuarios</div>
            <div class="pestaniaNoSel">Intranet</div>
        </div>
    </div>

    <div id="menu">
        <ul>
            <li>
                <a href="<c:url value='../BuscarArticulos'/>">Comprar </a>
            </li>
            <li>
                <a href="<c:url value='PedidosCliente'/>">Mis pedidos </a>
            </li>
            <li>
                <a href="<c:url value='CambiarDatos'/>">Cambiar datos personales </a>	
            </li>
            <li>
                <a href="<c:url value='../Salir'/>">Cerrar sesión </a>	
            </li>
        </ul>
        <div style="clear: left;"></div>
    </div>
</div> 