package com.proyecto.models;

import lombok.*;

import java.io.Serializable;

@Data

public class ProductoTallaStockId implements Serializable {
    private Integer producto;
    private Integer talla;
    
    // Constructor vacío (obligatorio para JPA)
    public ProductoTallaStockId() {}

    // Constructor con parámetros
    public ProductoTallaStockId(Integer producto, Integer talla) {
        this.producto = producto;
        this.talla = talla;
    }
    
	public Integer getProducto() {
		return producto;
	}
	public void setProducto(Integer producto) {
		this.producto = producto;
	}
	public Integer getTalla() {
		return talla;
	}
	public void setTalla(Integer talla) {
		this.talla = talla;
	}
    
    
}
