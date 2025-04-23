package com.api.nomina.modelo;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Roles")
public class ERol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IdRol;
	private String nombreRol;
}
