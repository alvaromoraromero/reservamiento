function filter(e) {
    let target = e.target;

    if (!target.classList.contains("draggable")) {
        return;
    }

    target.moving = true;

    if (e.clientX) {
        target.oldX = e.clientX;
        target.oldY = e.clientY;
    } else {
        target.oldX = e.touches[0].clientX;
        target.oldY = e.touches[0].clientY;
    }

    target.oldLeft = window.getComputedStyle(target).getPropertyValue('left').split('px')[0] * 1;
    target.oldTop = window.getComputedStyle(target).getPropertyValue('top').split('px')[0] * 1;

    document.onmousemove = dr;
    document.ontouchmove = dr;

    function dr(event) {
        event.preventDefault();

        if (!target.moving) {
            return;
        }
        if (event.clientX) {
            target.distX = event.clientX - target.oldX;
            target.distY = event.clientY - target.oldY;
        } else {
            target.distX = event.touches[0].clientX - target.oldX;
            target.distY = event.touches[0].clientY - target.oldY;
        }

        target.style.left = target.oldLeft + target.distX + "px";
        target.style.top = target.oldTop + target.distY + "px";
    }

    function endDrag() {
        target.moving = false;
    }
    target.onmouseup = endDrag;
    target.ontouchend = endDrag;
}
document.onmousedown = filter;
document.ontouchstart = filter;

function mostrarMapa() {
    var mapa = document.getElementById("mapa");
    mapa.style.display = "flex";
}

function cerrarMapa() {
    restaurarMapa();
    recargarMapa();
    setTimeout(function() {minimizarMapa();}, 1);
}

function maximizarMapa() {
    var mapa = document.getElementById("mapa");
    var iframe = mapa.querySelector('iframe');
    document.querySelector('.contenido').style.display = "none";
    mapa.style.width = "100%";
    mapa.style.height = "87%";
    mapa.style.marginTop = "16px";
    iframe.style.width = "100%";
    iframe.style.height = "100%";
    mapa.style.left = "";
    mapa.style.top = "";
    mapa.classList.remove("draggable");
    document.getElementById("maximizar").style.display = "none";
    document.getElementById("restaurar").style.display = "inline-block";
}

function restaurarMapa() {
    desactivarPantallaCompleta();
    var mapa = document.getElementById("mapa");
    var iframe = mapa.querySelector('iframe');
    document.querySelector('.contenido').style.display = "";
    mapa.style.width = "";
    mapa.style.height = "";
    mapa.style.marginTop = "";
    iframe.style.width = "";
    iframe.style.height = "";
    mapa.style.left = "";
    mapa.style.top = "";
    mapa.classList.add("draggable");
    document.getElementById("restaurar").style.display = "none";
    document.getElementById("maximizar").style.display = "inline-block";
}

function minimizarMapa() {
    var mapa = document.getElementById("mapa");
    mapa.style.display = "";
    document.querySelector('.contenido').style.display = "";
}

function activarPantallaCompleta() {
    maximizarMapa();
    mapa.requestFullscreen();
    document.getElementById("activar-pantalla-completa").style.display = "none";
    document.getElementById("desactivar-pantalla-completa").style.display = "inline-block";
}

function desactivarPantallaCompleta() {
    document.exitFullscreen();
    document.getElementById("activar-pantalla-completa").style.display = "inline-block";
    document.getElementById("desactivar-pantalla-completa").style.display = "none";
}

function recargarMapa() {
    var mapa = document.getElementById("mapa");
    var iframe = mapa.querySelector('iframe');
    iframe.src = iframe.src;

}