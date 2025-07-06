package com.proyecto.repositories;

import com.proyecto.models.ProductoTallaStock;
import com.proyecto.models.ProductoTallaStockId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoTallaStockRepository extends JpaRepository<ProductoTallaStock, ProductoTallaStockId> { }
