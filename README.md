# Frontend - Sistema de Gestión de Torneos de Videojuegos

Interfaz web que consume la api reset del backend para gestionar jugadores.

## Integrantes
- Luis Samuel Machado Estrada
- Miguelangel Gaviria Hijuelos

## Tecnologías
- HTML5
- CSS3

## Descripción
Aplicación web a parte del backend. Resuelve un  CRUDL completo
(Crear, Leer, Actualizar, Eliminar y Listar) de jugadores, consumiendo la
API REST del backend (proyecto `torneos-videojuegos`).

## Operaciones implementadas
- **Listar** todos los jugadores (GET /api/jugadores)
- **Consultar** un jugador por ID (GET /api/jugadores/{id})
- **Crear** un nuevo jugador (POST /api/jugadores)
- **Actualizar** un jugador existente (PUT /api/jugadores/{id})
- **Eliminar** un jugador (DELETE /api/jugadores/{id})

## Requisitos previos
- Tener el backend `torneos-videojuegos` corriendo en `http://localhost:8080`
- Un navegador web moderno

## Cómo ejecutar
1. Asegurarse de que el backend esté corriendo en el puerto 8080.
2. Abrir el archivo `index.html` en el navegador
   (o servirlo con una extensión como Live Server de VS Code).
