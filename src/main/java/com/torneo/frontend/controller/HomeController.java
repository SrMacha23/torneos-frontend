package com.torneo.frontend.controller;

import com.torneo.frontend.client.JugadorServiceClient;
import com.torneo.frontend.client.TorneoServiceClient;
import com.torneo.frontend.dto.JugadorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private JugadorServiceClient jugadorServicio;

    @Autowired
    private TorneoServiceClient torneoServicio;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        List<JugadorDto> jugadores = jugadorServicio.listar();

        long totalVideojuegos = jugadores.stream()
            .map(JugadorDto::getVideojuego)
            .filter(v -> v != null && !v.isEmpty())
            .distinct()
            .count();

        model.addAttribute("totalJugadores", jugadores.size());
        model.addAttribute("totalTorneos", torneoServicio.listar().size());
        model.addAttribute("totalVideojuegos", totalVideojuegos);
        model.addAttribute("torneos", torneoServicio.listar());
        return "dashboard";
    }
}