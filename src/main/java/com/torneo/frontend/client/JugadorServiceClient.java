package com.torneo.frontend.client;

import com.torneo.frontend.dto.JugadorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class JugadorServiceClient {

    @Autowired
    private RestTemplate restTemplate;


    @Value("${api.url.jugadores:http://localhost:8080/api/jugadores}")
    private String urlBase;

   
    public List<JugadorDto> listar() {
        JugadorDto[] jugadores = restTemplate.getForObject(urlBase, JugadorDto[].class);
        if (jugadores == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(jugadores);
    }

  
    public JugadorDto obtenerPorId(int id) {
        return restTemplate.getForObject(urlBase + "/" + id, JugadorDto.class);
    }

  
    public void crear(JugadorDto jugador) {
        restTemplate.postForObject(urlBase, jugador, String.class);
    }

   
    public void actualizar(int id, JugadorDto jugador) {
        restTemplate.put(urlBase + "/" + id, jugador);
    }

   
    public void eliminar(int id) {
        restTemplate.delete(urlBase + "/" + id);
    }
    
}
