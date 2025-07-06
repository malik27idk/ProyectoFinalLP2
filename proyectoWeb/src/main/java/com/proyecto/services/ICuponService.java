package com.proyecto.services;

import com.proyecto.models.Cupon;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ICuponService {
    Optional<Cupon> buscarPorCodigo(String codigo);
    boolean validarCupon(Cupon cupon, BigDecimal montoTotal);
    List<Cupon> listarTodos();
    Cupon guardar(Cupon cupon);
    void eliminar(Integer id);
}

