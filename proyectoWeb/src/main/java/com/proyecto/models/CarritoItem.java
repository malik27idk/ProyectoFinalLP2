package com.proyecto.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carrito_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CarritoItemId.class)
public class CarritoItem {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_carrito")
    private Carrito carrito;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_talla")
    private Talla talla;

    private Integer cantidad;

	public Carrito getCarrito() {
		return carrito;
	}

	public void setCarrito(Carrito carrito) {
		this.carrito = carrito;
	}

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

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
    
    
}

