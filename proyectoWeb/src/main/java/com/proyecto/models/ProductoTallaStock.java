package com.proyecto.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "producto_talla_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProductoTallaStockId.class)
public class ProductoTallaStock {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_talla")
    private Talla talla;

    private Integer stock;

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Talla getTalla() {
		return talla;
	}

	public void setTalla(Talla talla) {
		this.talla = talla;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
    
    
}
