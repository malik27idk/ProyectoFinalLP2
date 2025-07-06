package com.proyecto.controllers;

import com.proyecto.models.Venta;
import com.proyecto.services.ICarritoService;
import com.proyecto.services.IVentaService;
import com.proyecto.services.IReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private IVentaService ventaService;

    @Autowired
    private ICarritoService carritoService;

    @Autowired
    private IReporteService reporteService;

    private final Integer idUsuarioSimulado = 1;

    @GetMapping("/checkout")
    public String mostrarCheckout(Model model) {
        var carrito = carritoService.obtenerOCrearCarritoPorUsuario(idUsuarioSimulado);
        var items = carritoService.listarItems(carrito.getId());

        model.addAttribute("items", items);
        model.addAttribute("carrito", carrito);
        return "venta/checkout";
    }

    @PostMapping("/finalizar")
    public String finalizarCompra(@RequestParam("metodoPago") Integer idMetodoPago,
                                  @RequestParam(name = "codigoCupon", required = false) String codigoCupon,
                                  Model model) {
        try {
            Venta venta = ventaService.registrarVenta(idUsuarioSimulado, idMetodoPago, Optional.ofNullable(codigoCupon));
            reporteService.generarBoletaVenta(venta.getId());
            model.addAttribute("venta", venta);
            return "venta/confirmacion";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "venta/checkout";
        }
    }

    @GetMapping("/boleta/{id}")
    public void descargarBoleta(@PathVariable Integer id, HttpServletResponse response) {
        try {
            String ruta = "boletas/boleta_" + id + ".pdf";
            Path filePath = Paths.get(ruta);

            if (!Files.exists(filePath)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "La boleta no existe");
                return;
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=boleta_" + id + ".pdf");
            Files.copy(filePath, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al descargar la boleta PDF");
        }
    }
}
