function obtener_localizacion() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(setPosition);
    } else
    {
        alert('Su navegador no soporta la API de geolocalizacion');
    }
}
function setPosition() {
    var position = {lat: arguments[0].coords.latitude, lng: arguments[0].coords.longitude};
    var miPos = new google.maps.Marker({
        position: position,
        map: map,
        icon: "img/home.png",
        animation: google.maps.Animation.DROP,
        title: " Su situación"
    });
}

