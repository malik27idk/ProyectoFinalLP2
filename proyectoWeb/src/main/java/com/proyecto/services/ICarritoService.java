package com.proyecto.services;

import com.proyecto.models.Carrito;
import com.proyecto.models.CarritoItem;

import java.util.List;

public interface ICarritoService {
    Carrito obtenerOCrearCarritoPorUsuario(Integer idUsuario);
    List<CarritoItem> listarItems(Integer idCarrito);
    void agregarProducto(Integer idCarrito, Integer idProducto, Integer idTalla, int cantidad);
    void eliminarItem(Integer idCarrito, Integer idProducto, Integer idTalla);
    void vaciarCarrito(Integer idCarrito);
}

