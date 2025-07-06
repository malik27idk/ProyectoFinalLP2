package com.proyecto.controllers;

import com.proyecto.models.Cupon;
import com.proyecto.services.ICuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/cupones")
public class CuponController {

    @Autowired
    private ICuponService cuponService;

    @GetMapping
    public String listarCupones(Model model) {
        model.addAttribute("cupones", cuponService.listarTodos());
        return "cupon/lista"; // 📁 templates/cupon/lista.html
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("cupon", new Cupon());
        return "cupon/formulario"; // 📁 templates/cupon/formulario.html
    }

    @PostMapping("/guardar")
    public String guardarCupon(@ModelAttribute("cupon") Cupon cupon) {
        if (cupon.getFechaInicio() == null) cupon.setFechaInicio(LocalDate.now());
        if (cupon.getEstado() == null) cupon.setEstado("1");
        cuponService.guardar(cupon);
        return "redirect:/admin/cupones";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCupon(@PathVariable Integer id) {
        cuponService.eliminar(id);
        return "redirect:/admin/cupones";
    }
}

