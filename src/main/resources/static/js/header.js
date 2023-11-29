function alternarTema() {
    let tema = sessionStorage.getItem("tema");
    if (tema==null)
        sessionStorage.setItem("tema", "oscuro");
    else if (tema==="oscuro")
        sessionStorage.removeItem("tema");
    cargarTema();
}

function cargarTema(tema=sessionStorage.getItem("tema")) {
    if (tema==null) {
        document.body.style.backgroundColor = "";
        document.getElementsByClassName("contenido")[0].style.backgroundColor = "";
        document.getElementsByClassName("contenido")[0].style.color = "";
        let tarjetas = document.getElementsByClassName("tarjeta");
        for (let i=0; i<tarjetas.length; i++)
            tarjetas[i].style.backgroundColor = "";
        document.getElementById("alternarTema").querySelector("i").classList.replace("fa-sun","fa-moon");
    }
    else if (tema==="oscuro") {
        document.body.style.backgroundColor = "black";
        document.getElementsByClassName("contenido")[0].style.backgroundColor = "#101010";
        document.getElementsByClassName("contenido")[0].style.color = "white";
        let tarjetas = document.getElementsByClassName("tarjeta");
        for (let i=0; i<tarjetas.length; i++)
            tarjetas[i].style.backgroundColor = "#303030";
        document.getElementById("alternarTema").querySelector("i").classList.replace("fa-moon", "fa-sun");
    }
}

function actualizarFechas() {
    if (document.getElementById("fechainicio") === null) return;
    var hoy = new Date();
    var minimoinicio = new Date(hoy.setDate(hoy.getDate() + 1)).toLocaleDateString('en-ca');
    var limite = new Date(hoy.setFullYear(hoy.getFullYear() + 2));
    var maximoinicio = limite.toLocaleDateString('en-ca');
    var maximofin = new Date(limite.setDate(limite.getDate() + 1)).toLocaleDateString('en-ca');
    var inicioseleccionado = document.getElementById("fechainicio").value;
    inicioseleccionado = (inicioseleccionado.length===0) ? new Date(minimoinicio) : new Date(inicioseleccionado);
    var minimofin = new Date(inicioseleccionado.setDate(inicioseleccionado.getDate() + 1)).toLocaleDateString('en-ca');
    document.getElementById("fechainicio").setAttribute('min', minimoinicio);
    document.getElementById("fechainicio").setAttribute('max', maximoinicio);
    document.getElementById("fechafin").setAttribute('min', minimofin);
    document.getElementById("fechafin").setAttribute('max', maximofin);
}

function cerrarerror() {
    var mensajes = document.getElementsByClassName('error');
    for (var i = 0; i < mensajes.length; i++) {
        if (!mensajes[i].classList.contains("notvanish"))
            mensajes[i].style.display = "none";
    }
}

onload = function() {
    cargarTema();
    actualizarFechas();

    var mensajes = document.getElementsByClassName('error');
    for (var i = 0; i < mensajes.length; i++) {
        if (!mensajes[i].classList.contains("notvanish")) {
            var newSpan = document.createElement("span");
            newSpan.innerHTML = "<i class='fas fa-times'></i>";
            //newSpan.classList.add("cerrarerror");
            newSpan.setAttribute("onclick", "cerrarerror();");
            mensajes[i].appendChild(newSpan);
        }
    }

    const alternarPass = document.querySelector(".fa-eye");
    const pass = document.getElementById("contraseña");

    if (alternarPass!=null) {
        alternarPass.addEventListener("click",
            function () {
                if (pass.getAttribute("type") === "password") {
                    this.classList.replace("fa-eye", "fa-eye-slash");
                    var tipo = "text";
                }
                else {
                    this.classList.replace("fa-eye-slash", "fa-eye");
                    var tipo = "password";
                }
                pass.setAttribute("type", tipo);
            }
        )
        alternarPass.title = "Mostrar/Ocultar contraseña";
    }
};
/*
		window.interdeal = {
			"sitekey": "3ee907a3ce23ccbdbec919da2bce9fde",
			"Position": "Left",
			"Menulang": "ES",
			"domains": {
				"js": "https://cdn.equalweb.com/",
				"acc": "https://access.equalweb.com/"
			},
			"btnStyle": {
				"vPosition": [
					"80%",
					null
				],
				"scale": [
					"0.8",
					"0.8"
				],
				"icon": {
					"type": 7,
					"shape": "semicircle",
					"outline": false
				}
			}
		};
		(function (doc, head, body) {
			var coreCall = doc.createElement('script');
			coreCall.src = 'https://cdn.equalweb.com/core/4.0.4/accessibility.js';
			coreCall.defer = true;
			coreCall.integrity = 'sha512-LDvqiv8qYdF1MIqxiGZrvcDsmN6cZy0u0l23Dj7TVXmkVSNyzjtkcll8uCb8EGdwDVHjvisVYsAWuwTf6Mpu8g==';
			coreCall.crossOrigin = 'anonymous';
			coreCall.setAttribute('data-cfasync', true);
			body ? body.appendChild(coreCall) : head.appendChild(coreCall);
		})(document, document.head, document.body);*/