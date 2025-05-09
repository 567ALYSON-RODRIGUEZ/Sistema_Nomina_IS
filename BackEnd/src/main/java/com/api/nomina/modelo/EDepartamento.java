package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NamedStoredProcedureQuery(
		name= "Departamento.spDepartamentos",
		procedureName= "sp_gestion_deptos",
		parameters= {
				@StoredProcedureParameter(mode= ParameterMode.IN, name="opc", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="cod", type= Integer.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="nom", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="desc", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="status", type= String.class)
		}
	)

@NamedStoredProcedureQuery(
	    name= "Departamento.listarTodos",
	    procedureName= "sp_gestion_deptos",
	    resultClasses = EDepartamento.class,
	    parameters = {
	        @StoredProcedureParameter(mode= ParameterMode.IN, name="opc", type= String.class),
	        @StoredProcedureParameter(mode= ParameterMode.IN, name="cod", type= Integer.class),
	        @StoredProcedureParameter(mode= ParameterMode.IN, name="nom", type= String.class),
	        @StoredProcedureParameter(mode= ParameterMode.IN, name="desc", type= String.class),
	        @StoredProcedureParameter(mode= ParameterMode.IN, name="status", type= String.class)
	    }
	)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departamentos")
public class EDepartamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_departamento;
	private String nombre;
	private String descripcion;
	private String estado;
}
