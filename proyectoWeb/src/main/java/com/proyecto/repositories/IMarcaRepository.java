package com.proyecto.repositories;

import com.proyecto.models.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMarcaRepository extends JpaRepository<Marca, Integer> { }

