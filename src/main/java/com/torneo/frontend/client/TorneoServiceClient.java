package com.torneo.frontend.client;

import com.torneo.frontend.dto.TorneoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Cliente que consume la API de torneos del backend con RestTemplate.
@Service
public class TorneoServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url.torneos:http://localhost:8080/api/torneos}")
    private String urlBase;

    public List<TorneoDto> listar() {
        TorneoDto[] torneos = restTemplate.getForObject(urlBase, TorneoDto[].class);
        if (torneos == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(torneos);
    }

    public TorneoDto obtenerPorId(int id) {
        return restTemplate.getForObject(urlBase + "/" + id, TorneoDto.class);
    }

    public void crear(TorneoDto torneo) {
        restTemplate.postForObject(urlBase, torneo, String.class);
    }

    public void actualizar(int id, TorneoDto torneo) {
        restTemplate.put(urlBase + "/" + id, torneo);
    }

    public void eliminar(int id) {
        restTemplate.delete(urlBase + "/" + id);
    }
}
