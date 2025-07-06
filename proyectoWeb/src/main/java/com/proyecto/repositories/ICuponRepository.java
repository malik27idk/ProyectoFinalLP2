package com.proyecto.repositories;

import com.proyecto.models.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICuponRepository extends JpaRepository<Cupon, Integer> {
    Optional<Cupon> findByCodigo(String codigo);
}

