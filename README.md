# Frontend - Sistema de Gestión de Torneos de Videojuegos

Aplicación web **independiente** del backend, construida con **Spring Boot + Thymeleaf**,
que consume la API REST del backend (`torneos-videojuegos`) mediante **RestTemplate**.

## Integrantes
- Luis Samuel Machado Estrada
- Miguelangel Gaviria Hijuelos

## Tecnologías
- Java 17
- Spring Boot (Web MVC)
- Thymeleaf (plantillas HTML renderizadas en el servidor)
- RestTemplate (cliente HTTP para consumir el backend)
- Maven

## Arquitectura
El navegador habla solo con el frontend; el frontend consume la API del backend
desde el servidor con RestTemplate. Así se evita el problema de CORS.

## Estructura
src/main/java/com/torneo/frontend/
├── TorneosFrontendApplication.java     Clase principal
├── config/AppRestTemplateConfig.java   Define el bean RestTemplate
├── dto/JugadorDto.java                 Datos del jugador
├── client/JugadorServiceClient.java    Consume la API (CRUDL) con RestTemplate
└── controller/
├── JugadorViewController.java       Rutas web + vistas Thymeleaf
└── HomeController.java              Redirige "/" a "/jugadores"
src/main/resources/
├── templates/jugadores.html            Listado (L) + acciones
├── templates/formulario.html           Crear (C) / Editar (U)
└── application.properties              Puerto 8081 + URL del backend

## Operaciones (CRUDL)
- **Listar** todos los jugadores
- **Consultar** un jugador por ID
- **Crear** un nuevo jugador
- **Actualizar** un jugador existente
- **Eliminar** un jugador

## Cómo ejecutar
1. Asegurarse de que el backend esté corriendo en el puerto 8080.
2. Correr el FrontEnd
3. Abrir en el navegador: `http://localhost:8081/jugadores`