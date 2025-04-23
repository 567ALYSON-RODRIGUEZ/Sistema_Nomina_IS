package com.api.nomina.modelo;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Puestos")
public class EPuesto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IdPuesto;
	private String nombre;
	private BigDecimal salarioBase;
} 
