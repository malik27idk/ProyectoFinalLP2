package com.proyecto.repositories;

import com.proyecto.models.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICarritoRepository extends JpaRepository<Carrito, Integer> {
    Carrito findByUsuario_Id(Integer idUsuario);
}
