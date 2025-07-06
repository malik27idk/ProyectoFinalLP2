package com.proyecto.repositories;

import com.proyecto.models.DetalleVenta;
import com.proyecto.models.DetalleVentaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDetalleVentaRepository extends JpaRepository<DetalleVenta, DetalleVentaId> {
    List<DetalleVenta> findByVenta_Id(Integer idVenta);
}
