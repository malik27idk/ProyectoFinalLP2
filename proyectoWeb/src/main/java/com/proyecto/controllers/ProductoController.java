package com.proyecto.controllers;

import com.proyecto.models.Producto;
import com.proyecto.repositories.ICategoriaRepository;
import com.proyecto.repositories.IMarcaRepository;
import com.proyecto.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Autowired
    private IMarcaRepository marcaRepository;

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("marcas", marcaRepository.findAll());
        return "productos/lista";
    }

    @PostMapping("/filtrar")
    public String filtrarProductos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer categoria,
            @RequestParam(required = false) Integer marca,
            Model model) {

        List<Producto> productos = productoService.filtrarProductos(nombre, categoria, marca);
        model.addAttribute("productos", productos);
        return "productos/fragmento :: listaProductos";
    }

    @GetMapping("/categoria/{id}")
    public String listarPorCategoria(@PathVariable Integer id, Model model) {
        List<Producto> productos = productoService.buscarPorCategoria(id);
        model.addAttribute("productos", productos);
        return "productos/lista";
    }

    @GetMapping("/marca/{id}")
    public String listarPorMarca(@PathVariable Integer id, Model model) {
        List<Producto> productos = productoService.buscarPorMarca(id);
        model.addAttribute("productos", productos);
        return "productos/lista";
    }

    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Integer id, Model model) {
        Producto producto = productoService.buscarPorId(id).orElse(null);
        if (producto == null) return "redirect:/productos";
        model.addAttribute("producto", producto);
        return "productos/detalle";
    }
}
