package com.torneo.frontend.dto;

// DTO (Data Transfer Object): representa los datos de un jugador
// tal como viajan entre el frontend y la API del backend.
public class JugadorDto {

    private Integer id;
    private String nombre;
    private String nickname;
    private String email;
    private String videojuego;

    public JugadorDto() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getVideojuego() { return videojuego; }
    public void setVideojuego(String videojuego) { this.videojuego = videojuego; }
}
