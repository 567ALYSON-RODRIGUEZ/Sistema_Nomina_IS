package com.api.nomina.modelo;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@NamedStoredProcedureQuery(
		name= "Puesto.spPuestos",
		procedureName= "sp_gestion_puestos",
		parameters= {
				@StoredProcedureParameter(mode= ParameterMode.IN, name="opc", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="cod", type= Integer.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="nom", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="base", type= BigDecimal.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="nivel", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="status", type= String.class)
		}
	)

@NamedStoredProcedureQuery(
	    name= "Puesto.listarTodos",
	    procedureName= "sp_gestion_puestos",
	    resultClasses = EPuesto.class,
	    parameters = {
	    		@StoredProcedureParameter(mode= ParameterMode.IN, name="opc", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="cod", type= Integer.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="nom", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="base", type= BigDecimal.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="nivel", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="status", type= String.class)
	    }
	)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "puestos")
public class EPuesto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_puesto;
	private String nombre;
	private BigDecimal salario_base;
	private String nivel_jerarquico;
	private String estado_puesto;
} 
