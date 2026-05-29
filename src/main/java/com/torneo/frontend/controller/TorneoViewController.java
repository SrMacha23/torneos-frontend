package com.torneo.frontend.controller;

import com.torneo.frontend.client.JugadorServiceClient;
import com.torneo.frontend.client.TorneoServiceClient;
import com.torneo.frontend.dto.JugadorDto;
import com.torneo.frontend.dto.TorneoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/torneos")
public class TorneoViewController {

    @Autowired
    private TorneoServiceClient servicio;

    @Autowired
    private JugadorServiceClient jugadorServicio;

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
    public String guardar(@ModelAttribute TorneoDto torneo, RedirectAttributes attrs) {
        try {
            if (torneo.getId() != null) {
                servicio.actualizar(torneo.getId(), torneo);
                attrs.addFlashAttribute("mensaje", "✅ Torneo actualizado correctamente.");
            } else {
                servicio.crear(torneo);
                attrs.addFlashAttribute("mensaje", "✅ Torneo creado correctamente.");
            }
        } catch (Exception e) {
            attrs.addFlashAttribute("error", "❌ Ocurrió un error al guardar el torneo.");
        }
        return "redirect:/torneos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes attrs) {
        try {
            servicio.eliminar(id);
            attrs.addFlashAttribute("mensaje", "✅ Torneo eliminado correctamente.");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", "❌ No se pudo eliminar el torneo.");
        }
        return "redirect:/torneos";
    }

    @GetMapping("/{id}/jugadores")
    public String verJugadores(@PathVariable int id, Model model) {
        try {
            TorneoDto torneo = servicio.obtenerPorId(id);

            if (torneo == null) {
                model.addAttribute("torneo", new TorneoDto());
                model.addAttribute("jugadores", new java.util.ArrayList<>());
                return "detalle-torneo";
            }

            List<JugadorDto> jugadoresDelTorneo = jugadorServicio.listar().stream()
                .filter(j -> j.getTorneo() != null &&
                             j.getTorneo().equalsIgnoreCase(torneo.getNombre()))
                .collect(Collectors.toList());

            model.addAttribute("torneo", torneo);
            model.addAttribute("jugadores", jugadoresDelTorneo);
            return "detalle-torneo";

        } catch (Exception e) {
        model.addAttribute("torneo", new TorneoDto());
        model.addAttribute("jugadores", new java.util.ArrayList<>());
        model.addAttribute("error", "No se pudo cargar el detalle del torneo: " + e.getMessage());
        return "detalle-torneo";
        }
    }
}