package com.torneo.frontend.controller;

import com.torneo.frontend.client.TorneoServiceClient;
import com.torneo.frontend.dto.TorneoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// Controlador de vistas de torneos.
@Controller
@RequestMapping("/torneos")
public class TorneoViewController {

    @Autowired
    private TorneoServiceClient servicio;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("torneos", servicio.listar());
        return "torneos";
    }

    @GetMapping("/nuevo")
    public String formNuevo(Model model) {
        model.addAttribute("torneo", new TorneoDto());
        model.addAttribute("editar", false);
        return "formulario-torneo";
    }

    @GetMapping("/editar/{id}")
    public String formEditar(@PathVariable int id, Model model) {
        model.addAttribute("torneo", servicio.obtenerPorId(id));
        model.addAttribute("editar", true);
        return "formulario-torneo";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute TorneoDto torneo) {
        if (torneo.getId() != null) {
            servicio.actualizar(torneo.getId(), torneo);
        } else {
            servicio.crear(torneo);
        }
        return "redirect:/torneos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        servicio.eliminar(id);
        return "redirect:/torneos";
    }
}
