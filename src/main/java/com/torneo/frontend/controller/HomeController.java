package com.torneo.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Redirige la raíz "/" a la lista de jugadores, por comodidad.
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/jugadores";
    }
}
