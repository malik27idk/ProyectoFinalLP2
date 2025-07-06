package com.proyecto.models;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CarritoItemId implements Serializable {
    private Integer carrito;
    private Integer producto;
    private Integer talla;
    
    public CarritoItemId(Integer carritoId, Integer productoId, Integer tallaId) {
        this.carrito = carritoId;
        this.producto = productoId;
        this.talla = tallaId;
    }
    
	public Integer getCarrito() {
		return carrito;
	}
	public void setCarrito(Integer carrito) {
		this.carrito = carrito;
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
