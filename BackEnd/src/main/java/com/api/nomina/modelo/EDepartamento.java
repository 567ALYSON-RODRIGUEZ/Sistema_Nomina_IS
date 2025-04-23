package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Departamentos")
public class EDepartamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IdDepto;
	private String nombre;
	private String descripcion;	
}
