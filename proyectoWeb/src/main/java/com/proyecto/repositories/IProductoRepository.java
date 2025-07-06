package com.proyecto.repositories;

import com.proyecto.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoria_Id(Integer idCategoria);
    List<Producto> findByMarca_Id(Integer idMarca);

    @Query("SELECT p FROM Producto p WHERE " +
           "(:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(:nombre)) AND " +
           "(:categoriaId IS NULL OR p.categoria.id = :categoriaId) AND " +
           "(:marcaId IS NULL OR p.marca.id = :marcaId)")
    List<Producto> buscarPorFiltros(
        @Param("nombre") String nombre,
        @Param("categoriaId") Integer categoriaId,
        @Param("marcaId") Integer marcaId
    );
}
