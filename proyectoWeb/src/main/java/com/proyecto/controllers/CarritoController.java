package com.proyecto.controllers;

import com.proyecto.models.Carrito;
import com.proyecto.models.CarritoItem;
import com.proyecto.services.ICarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private ICarritoService carritoService;

    // ⚠️ Simulamos un usuario con ID 1 (en producción usarías el ID del usuario logueado)
    private final Integer idUsuarioSimulado = 1;

    @GetMapping
    public String verCarrito(Model model) {
        Carrito carrito = carritoService.obtenerOCrearCarritoPorUsuario(idUsuarioSimulado);
        List<CarritoItem> items = carritoService.listarItems(carrito.getId());

        model.addAttribute("carrito", carrito);
        model.addAttribute("items", items);
        return "carrito/ver"; // 📁 crea: templates/carrito/ver.html
    }

    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam("idProducto") Integer idProducto,
                                   @RequestParam("idTalla") Integer idTalla,
                                   @RequestParam("cantidad") Integer cantidad) {
        Carrito carrito = carritoService.obtenerOCrearCarritoPorUsuario(idUsuarioSimulado);
        carritoService.agregarProducto(carrito.getId(), idProducto, idTalla, cantidad);
        return "redirect:/carrito";
    }

    @GetMapping("/eliminar")
    public String eliminarItem(@RequestParam("idProducto") Integer idProducto,
                                @RequestParam("idTalla") Integer idTalla) {
        Carrito carrito = carritoService.obtenerOCrearCarritoPorUsuario(idUsuarioSimulado);
        carritoService.eliminarItem(carrito.getId(), idProducto, idTalla);
        return "redirect:/carrito";
    }

    @GetMapping("/vaciar")
    public String vaciarCarrito() {
        Carrito carrito = carritoService.obtenerOCrearCarritoPorUsuario(idUsuarioSimulado);
        carritoService.vaciarCarrito(carrito.getId());
        return "redirect:/carrito";
    }
}
// Nota: Asegúrate de tener las vistas correspondientes en templates/carrito/ver.html
