package com.api.nomina.modelo;

import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NamedStoredProcedureQuery(
		name= "Periodos.spPeriodos",
		procedureName= "sp_gestion_periodos",
		parameters= {
				@StoredProcedureParameter(mode= ParameterMode.IN, name="opc", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="id", type= Integer.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="tipo", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="desc", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="inicio", type= java.sql.Date.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="fin", type= java.sql.Date.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="dias", type= Integer.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="num_pago", type= Integer.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="cod_pago", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="estado", type= String.class)
		}
	)

@NamedStoredProcedureQuery(
	    name= "Periodos.listarTodos",
	    procedureName= "sp_gestion_periodos",
	    resultClasses = EPeriodosNomina.class,
	    parameters = {
	    		@StoredProcedureParameter(mode= ParameterMode.IN, name="opc", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="id", type= Integer.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="tipo", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="desc", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="inicio", type= java.sql.Date.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="fin", type= java.sql.Date.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="dias", type= Integer.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="num_pago", type= Integer.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="cod_pago", type= String.class),
				@StoredProcedureParameter(mode= ParameterMode.IN, name="estado", type= String.class)
	    }
	)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "periodos_nomina")
public class EPeriodosNomina {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private int id_periodo;
    private String tipo_periodo;
    private String descripcion;
    private java.sql.Date fecha_inicio;
    private java.sql.Date fecha_fin;
    private int dias_a_pagar;
    private int numero_pago;
    private String codigo_pago;
    private String estado;

}
