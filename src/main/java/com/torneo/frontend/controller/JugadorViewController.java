package com.torneo.frontend.controller;

import com.torneo.frontend.client.JugadorServiceClient;
import com.torneo.frontend.client.TorneoServiceClient;
import com.torneo.frontend.dto.JugadorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/jugadores")
public class JugadorViewController {

    @Autowired
    private JugadorServiceClient servicio;

    @Autowired
    private TorneoServiceClient torneoServicio;

    @GetMapping
    public String listar(@RequestParam(required = false) String buscar, Model model) {
        List<JugadorDto> jugadores = servicio.listar();

        if (buscar != null && !buscar.trim().isEmpty()) {
            String filtro = buscar.toLowerCase();
            jugadores = jugadores.stream()
                .filter(j ->
                    (j.getNombre()     != null && j.getNombre().toLowerCase().contains(filtro)) ||
                    (j.getNickname()   != null && j.getNickname().toLowerCase().contains(filtro)) ||
                    (j.getVideojuego() != null && j.getVideojuego().toLowerCase().contains(filtro))
                )
                .collect(Collectors.toList());
        }

        model.addAttribute("jugadores", jugadores);
        model.addAttribute("buscar", buscar);
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
    public String guardar(@ModelAttribute JugadorDto jugador, RedirectAttributes attrs) {
        try {
            if (jugador.getId() != null) {
                servicio.actualizar(jugador.getId(), jugador);
                attrs.addFlashAttribute("mensaje", "✅ Jugador actualizado correctamente.");
            } else {
                servicio.crear(jugador);
                attrs.addFlashAttribute("mensaje", "✅ Jugador creado correctamente.");
            }
        } catch (Exception e) {
            attrs.addFlashAttribute("error", "❌ Ocurrió un error al guardar el jugador.");
        }
        return "redirect:/jugadores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes attrs) {
        try {
            servicio.eliminar(id);
            attrs.addFlashAttribute("mensaje", "✅ Jugador eliminado correctamente.");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", "❌ No se pudo eliminar el jugador.");
        }
        return "redirect:/jugadores";
    }
}
