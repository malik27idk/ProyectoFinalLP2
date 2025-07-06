package com.proyecto.controllers;

import com.proyecto.models.Venta;
import com.proyecto.services.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/ventas")
public class AdminVentaController {

    @Autowired
    private IVentaService ventaService;

    // 📋 Mostrar lista de ventas con filtros
    @GetMapping
    public String listarVentas(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String fecha,
            Model model) {

        List<Venta> ventas = ventaService.filtrarVentas(nombre, fecha);
        model.addAttribute("ventas", ventas);
        model.addAttribute("nombre", nombre);
        model.addAttribute("fecha", fecha);
        return "admin/ventas/lista";
    }

    // 🔍 Mostrar detalle de una venta
    @GetMapping("/{id}")
    public String detalleVenta(@PathVariable Integer id, Model model) {
        Venta venta = ventaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        model.addAttribute("venta", venta);
        return "admin/ventas/detalle";
    }
}
