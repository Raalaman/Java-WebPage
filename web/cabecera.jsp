<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="logo"><a href="index.html"><img src="${pageContext.request.contextPath}/img/LogoElectrosa200.png" border="0"></a></div>
<fmt:setBundle basename="electrosaMsg"/>
        <div class="sombra">
            <div class="nucleo">
                <div id="lang">
                    <a href="${pageContext.request.contextPath}/index.html">Español</a> &nbsp; | &nbsp; <a href="${pageContext.request.contextPath}/index.html">English</a>
                </div>
            </div>
        </div>

        <div class="barra_menus">	
            <div class="pestanias">
                <div class="grupoPestanias">
                    
                    <div class="pestaniaSel"><fmt:message key="pest.usr"/></div>
                    <div class="pestaniaNoSel"><a href="${pageContext.request.contextPath}/admin/index.html"><fmt:message key="pest.intra"/></a></div>
                </div>
            </div>

            <div id="menu" >
                <ul>
                    <li>
                        <a href="<c:url value="index.jsp"/>"><fmt:message key="pest.usr.sobre"/><br/><img src="${pageContext.request.contextPath}/img/Home4.png"/></a>
                    </li>
                    <li>
                        <a href="<c:url value="Mapa"/>"><fmt:message key="pest.usr.donde"/><br/><img src="${pageContext.request.contextPath}/img/map.png"/></a>
                    </li>
                    <li>
                        <a href="<c:url value="BuscarArticulos"/>"><fmt:message key="pest.usr.catalogo"/><br/><img src="${pageContext.request.contextPath}/img/catalog.png"/></a>
                    </li>
                    <li>
                        <a href="<c:url value="clientes/AreaCliente"/>"><fmt:message key="pest.usr.reg"/><br/><img src="${pageContext.request.contextPath}/img/registrado.png"/></a>
                    </li>
                </ul>
                <div style="clear: left;"></div>
            </div>
</div>