package com.torneo.frontend.dto;

// DTO del torneo: representa los datos que viajan entre el frontend y el backend.
public class TorneoDto {

    private Integer id;
    private String nombre;
    private String videojuego;
    private String fecha;
    private String descripcion;

    public TorneoDto() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getVideojuego() { return videojuego; }
    public void setVideojuego(String videojuego) { this.videojuego = videojuego; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
