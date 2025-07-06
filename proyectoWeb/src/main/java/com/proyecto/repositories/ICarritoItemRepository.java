package com.proyecto.repositories;

import com.proyecto.models.CarritoItem;
import com.proyecto.models.CarritoItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICarritoItemRepository extends JpaRepository<CarritoItem, CarritoItemId> {
    List<CarritoItem> findByCarrito_Id(Integer idCarrito);
}

