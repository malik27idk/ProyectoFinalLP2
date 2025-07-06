package com.proyecto.controllers;

import com.proyecto.models.Producto;
import com.proyecto.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin/productos")
public class AdminProductoController {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private IMarcaService marcaService;

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "admin/productos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("marcas", marcaService.listarTodas());
        return "admin/productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto,
                                   @RequestParam("archivoImagen") MultipartFile archivo) throws IOException {
        // Si se subió imagen, guárdala como nombre
        if (!archivo.isEmpty()) {
            producto.setImagen(archivo.getOriginalFilename());
            // 📝 Aquí podrías guardar la imagen en una carpeta si deseas (opcional)
        }

        productoService.guardar(producto);
        return "redirect:/admin/productos";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        Producto producto = productoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("marcas", marcaService.listarTodas());

        return "admin/productos/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        productoService.eliminar(id);
        return "redirect:/admin/productos";
    }
}
