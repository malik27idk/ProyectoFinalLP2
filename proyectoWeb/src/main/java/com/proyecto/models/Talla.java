package com.proyecto.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tallas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Talla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String valor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
    
    
    
}



