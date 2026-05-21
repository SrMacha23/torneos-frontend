package com.torneo.frontend.client;

import com.torneo.frontend.dto.JugadorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Cliente que consume la API REST del backend (torneos-videojuegos)
// usando RestTemplate. Cada método corresponde a una operación del CRUDL.
@Service
public class JugadorServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    // URL base de la API del backend. Se configura en application.properties.
    @Value("${api.url.jugadores:http://localhost:8080/api/jugadores}")
    private String urlBase;

    // LISTAR todos los jugadores -> GET /api/jugadores
    public List<JugadorDto> listar() {
        JugadorDto[] jugadores = restTemplate.getForObject(urlBase, JugadorDto[].class);
        if (jugadores == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(jugadores);
    }

    // CONSULTAR un jugador por id -> GET /api/jugadores/{id}
    public JugadorDto obtenerPorId(int id) {
        return restTemplate.getForObject(urlBase + "/" + id, JugadorDto.class);
    }

    // CREAR un jugador -> POST /api/jugadores
    public void crear(JugadorDto jugador) {
        restTemplate.postForObject(urlBase, jugador, String.class);
    }

    // ACTUALIZAR un jugador -> PUT /api/jugadores/{id}
    public void actualizar(int id, JugadorDto jugador) {
        restTemplate.put(urlBase + "/" + id, jugador);
    }

    // ELIMINAR un jugador -> DELETE /api/jugadores/{id}
    public void eliminar(int id) {
        restTemplate.delete(urlBase + "/" + id);
    }
}
