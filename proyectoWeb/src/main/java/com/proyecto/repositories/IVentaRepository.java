package com.proyecto.repositories;

import com.proyecto.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IVentaRepository extends JpaRepository<Venta, Integer> {

    List<Venta> findByUsuario_Id(Integer idUsuario);

    @Query("SELECT v FROM Venta v " +
           "WHERE (:nombre IS NULL OR LOWER(CONCAT(v.usuario.nombres, ' ', v.usuario.apellidos)) LIKE LOWER(:nombre)) " +
           "AND (:fecha IS NULL OR CAST(v.fecha AS string) = :fecha)")
    List<Venta> buscarPorFiltros(@Param("nombre") String nombre, @Param("fecha") String fecha);
}
