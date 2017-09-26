<%-- 
    Document   : dondeFancyBox
    Created on : 12-may-2017, 17:08:11
    Author     : Manga
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <title> SoFancy </title>
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.0.47/jquery.fancybox.min.css" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.0.47/jquery.fancybox.min.js"></script>
        <link href="css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <script type='text/javascript' src='js/SetLocation.js'></script>
    </head>
    <body>
         <div id="map"  style="height: 500px; width: 500px;"></div>
        <script async defer
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAsdlXTV_hfJNtwHQIZFZlU8IuCA29N3Mw&callback=initMap">
        </script>
        <script>
            function initMap() {
                var lati = findGetParameter('lati');
                var long = findGetParameter('long');
                if (lati && long)
                {
                    var center = {lat: Math.round(lati * 100) / 100, lng: Math.round(long * 100) / 100};
                    var map = new google.maps.Map(document.getElementById('map'), {
                        zoom: 8,
                        center: center
                    }
                    );
                    var marker = new google.maps.Marker({
                        position: center,
                        map: map
                    });
                }
            }
            function findGetParameter(parameterName) {
                var result = null,
                        tmp = [];
                location.search
                        .substr(1)
                        .split("&")
                        .forEach(function (item) {
                            tmp = item.split("=");
                            if (tmp[0] === parameterName)
                                result = decodeURIComponent(tmp[1]);
                        });
                return result;
            }

        </script>
    </body>
</html>
