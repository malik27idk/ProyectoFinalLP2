package com.proyecto.repositories;

import com.proyecto.models.VentaPago;
import com.proyecto.models.VentaPagoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVentaPagoRepository extends JpaRepository<VentaPago, VentaPagoId> { }
