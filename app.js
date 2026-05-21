// ===== Configuración =====
// URL base de la API del backend (Spring Boot corriendo en el puerto 8080)
const API_URL = "http://localhost:8080/api/jugadores";

// ===== Referencias a elementos del HTML =====
const form = document.getElementById("jugadorForm");
const formTitulo = document.getElementById("formTitulo");
const inputId = document.getElementById("jugadorId");
const inputNombre = document.getElementById("nombre");
const inputNickname = document.getElementById("nickname");
const inputEmail = document.getElementById("email");
const inputVideojuego = document.getElementById("videojuego");
const btnGuardar = document.getElementById("btnGuardar");
const btnCancelar = document.getElementById("btnCancelar");
const btnRecargar = document.getElementById("btnRecargar");
const tablaBody = document.getElementById("tablaBody");
const sinDatos = document.getElementById("sinDatos");
const mensaje = document.getElementById("mensaje");

// ===== Al cargar la página, listamos los jugadores =====
document.addEventListener("DOMContentLoaded", listarJugadores);

// ===== LISTAR (R + L del CRUDL) =====
async function listarJugadores() {
    try {
        const respuesta = await fetch(API_URL);
        if (!respuesta.ok) throw new Error("No se pudo obtener la lista");

        const jugadores = await respuesta.json();
        renderizarTabla(jugadores);
    } catch (error) {
        mostrarMensaje("Error al cargar jugadores: " + error.message, "error");
    }
}

// Dibuja las filas de la tabla a partir del array de jugadores
function renderizarTabla(jugadores) {
    tablaBody.innerHTML = "";

    if (jugadores.length === 0) {
        sinDatos.classList.remove("oculto");
        return;
    }
    sinDatos.classList.add("oculto");

    jugadores.forEach(j => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${j.id}</td>
            <td>${j.nombre}</td>
            <td>${j.nickname}</td>
            <td>${j.email}</td>
            <td>${j.videojuego}</td>
            <td>
                <div class="acciones">
                    <button class="editar" onclick="cargarParaEditar(${j.id})">Editar</button>
                    <button class="peligro" onclick="eliminarJugador(${j.id})">Eliminar</button>
                </div>
            </td>
        `;
        tablaBody.appendChild(fila);
    });
}

// ===== CREAR / ACTUALIZAR (C + U del CRUDL) =====
// Un solo formulario sirve para ambos: si hay id es UPDATE, si no, es CREATE
form.addEventListener("submit", async (evento) => {
    evento.preventDefault();

    const jugador = {
        nombre: inputNombre.value,
        nickname: inputNickname.value,
        email: inputEmail.value,
        videojuego: inputVideojuego.value
    };

    const id = inputId.value;

    try {
        let respuesta;
        if (id) {
            // Hay id -> ACTUALIZAR (PUT)
            respuesta = await fetch(`${API_URL}/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(jugador)
            });
        } else {
            // No hay id -> CREAR (POST)
            respuesta = await fetch(API_URL, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(jugador)
            });
        }

        if (!respuesta.ok) throw new Error("La operación falló");

        mostrarMensaje(id ? "Jugador actualizado correctamente" : "Jugador creado correctamente", "exito");
        limpiarFormulario();
        listarJugadores();
    } catch (error) {
        mostrarMensaje("Error al guardar: " + error.message, "error");
    }
});

// ===== Cargar un jugador en el formulario para editarlo =====
async function cargarParaEditar(id) {
    try {
        const respuesta = await fetch(`${API_URL}/${id}`);
        if (!respuesta.ok) throw new Error("No se encontró el jugador");

        const j = await respuesta.json();

        inputId.value = j.id;
        inputNombre.value = j.nombre;
        inputNickname.value = j.nickname;
        inputEmail.value = j.email;
        inputVideojuego.value = j.videojuego;

        formTitulo.textContent = "Editar jugador #" + j.id;
        btnGuardar.textContent = "Actualizar";
        btnCancelar.classList.remove("oculto");

        window.scrollTo({ top: 0, behavior: "smooth" });
    } catch (error) {
        mostrarMensaje("Error: " + error.message, "error");
    }
}

// ===== ELIMINAR (D del CRUDL) =====
async function eliminarJugador(id) {
    if (!confirm("¿Seguro que querés eliminar este jugador?")) return;

    try {
        const respuesta = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
        if (!respuesta.ok) throw new Error("No se pudo eliminar");

        mostrarMensaje("Jugador eliminado correctamente", "exito");
        listarJugadores();
    } catch (error) {
        mostrarMensaje("Error al eliminar: " + error.message, "error");
    }
}

// ===== Botones auxiliares =====
btnCancelar.addEventListener("click", limpiarFormulario);
btnRecargar.addEventListener("click", listarJugadores);

// Resetea el formulario a su estado de "crear"
function limpiarFormulario() {
    form.reset();
    inputId.value = "";
    formTitulo.textContent = "Nuevo jugador";
    btnGuardar.textContent = "Guardar";
    btnCancelar.classList.add("oculto");
}

// ===== Muestra un mensaje temporal (éxito o error) =====
function mostrarMensaje(texto, tipo) {
    mensaje.textContent = texto;
    mensaje.className = "mensaje " + tipo;
    setTimeout(() => mensaje.classList.add("oculto"), 4000);
}
