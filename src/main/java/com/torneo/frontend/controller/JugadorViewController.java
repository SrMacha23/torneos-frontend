package com.torneo.frontend.controller;

import com.torneo.frontend.client.JugadorServiceClient;
import com.torneo.frontend.client.TorneoServiceClient;
import com.torneo.frontend.dto.JugadorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jugadores")
public class JugadorViewController {

    @Autowired
    private JugadorServiceClient servicio;

    @Autowired
    private TorneoServiceClient torneoServicio;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("jugadores", servicio.listar());
        return "jugadores";
    }

    @GetMapping("/nuevo")
    public String formNuevo(Model model) {
        model.addAttribute("jugador", new JugadorDto());
        model.addAttribute("torneos", torneoServicio.listar());
        model.addAttribute("editar", false);
        return "formulario";
    }

    @GetMapping("/editar/{id}")
    public String formEditar(@PathVariable int id, Model model) {
        model.addAttribute("jugador", servicio.obtenerPorId(id));
        model.addAttribute("torneos", torneoServicio.listar());
        model.addAttribute("editar", true);
        return "formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute JugadorDto jugador) {
        if (jugador.getId() != null) {
            servicio.actualizar(jugador.getId(), jugador);
        } else {
            servicio.crear(jugador);
        }
        return "redirect:/jugadores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        servicio.eliminar(id);
        return "redirect:/jugadores";
    }
}
