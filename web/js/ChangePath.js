function ChangePath(evento)
{
    var evt = evento || window.event;
    var target = evt.target || evt.srcElement;
    var x = encodeURIComponent(window.location)
    var d = target.getElementsByTagName("a")[0].href+"&dir="+x;
    window.location = d;
}
