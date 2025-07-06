package com.proyecto.models;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaId implements Serializable {
    private Integer venta;
    private Integer producto;
    private Integer talla;
	public Integer getVenta() {
		return venta;
	}
	public void setVenta(Integer venta) {
		this.venta = venta;
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
