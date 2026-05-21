package com.torneo.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// Configuración que crea el bean RestTemplate.
// RestTemplate es el cliente HTTP de Spring que usamos para consumir la API REST del backend desde el lado del servidor.

@Configuration
public class AppRestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
