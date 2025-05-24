package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NamedStoredProcedureQuery(
    name = "PlanillaBono14.generarBono14",
    procedureName = "sp_generar_planilla_bono14",
    parameters = {
    		@StoredProcedureParameter(mode = ParameterMode.IN, name = "anio", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_planilla", type = Integer.class)
    }
)

@NamedStoredProcedureQuery(
	    name = "PlanillaBono14.listarTodos",
	    procedureName = "sp_generar_planilla_bono14",
	    resultClasses = EBono14.class,
	    parameters = {
	    	@StoredProcedureParameter(mode = ParameterMode.IN, name = "anio", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_planilla", type = Integer.class)
	}
)
	 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "planilla_bono14")
public class EBono14 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_empleado")
    private int idEmpleado;

    private int anio;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Column(name = "monto_pagado")
    private BigDecimal montoPagado;

    @Column(name = "tipo_pago")
    private String tipoPago; // 'Completo' o 'Proporcional'
    
    @Column(name = "estado_pago")
    private String estadoPago;
}
