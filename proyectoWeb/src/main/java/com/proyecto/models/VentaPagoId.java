package com.proyecto.models;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaPagoId implements Serializable {
    private Integer venta;
    private Integer metodoPago;
	public Integer getVenta() {
		return venta;
	}
	public void setVenta(Integer venta) {
		this.venta = venta;
	}
	public Integer getMetodoPago() {
		return metodoPago;
	}
	public void setMetodoPago(Integer metodoPago) {
		this.metodoPago = metodoPago;
	}
    
    
}

