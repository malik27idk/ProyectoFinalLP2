package com.proyecto.services;

import com.proyecto.models.Venta;

import java.util.List;
import java.util.Optional;

public interface IVentaService {

    // 🛒 Registrar nueva venta desde el carrito
    Venta registrarVenta(Integer idUsuario, Integer idMetodoPago, Optional<String> codigoCupon);

    // 👨‍💼 Módulo de administración
    List<Venta> listarTodas();
    Optional<Venta> buscarPorId(Integer id);

    // 🧾 Filtrado por nombre de cliente o fecha (para ADMIN)
    List<Venta> filtrarVentas(String nombreCliente, String fecha);
}
