package com.proyecto.services;

import com.proyecto.models.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    List<Producto> listarTodos();
    Optional<Producto> buscarPorId(Integer id);
    List<Producto> buscarPorCategoria(Integer idCategoria);
    List<Producto> buscarPorMarca(Integer idMarca);
    Producto guardar(Producto producto);
    void eliminar(Integer id);

    // ✅ MÉTODO NUEVO
    List<Producto> filtrarProductos(String nombre, Integer idCategoria, Integer idMarca);
}
