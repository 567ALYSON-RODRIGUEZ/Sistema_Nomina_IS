package com.api.nomina.modelo;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@NamedStoredProcedureQuery(
		name= "Rol.spRoles",
		procedureName= "sp_gestion_roles",
		parameters= {
				@StoredProcedureParameter(mode= ParameterMode.IN, name="opc", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="cod", type= Integer.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="nom", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="desc", type= String.class)
		}
	)

@NamedStoredProcedureQuery(
	    name= "Rol.listarTodos",
	    procedureName= "sp_gestion_roles",
	    resultClasses = ERol.class,
	    parameters = {
	    		@StoredProcedureParameter(mode= ParameterMode.IN, name="opc", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="cod", type= Integer.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="nom", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="desc", type= String.class)
	    }
	)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class ERol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_rol;
	private String nombre_rol;
	private String descripcion;
}
