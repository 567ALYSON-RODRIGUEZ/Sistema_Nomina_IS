package com.api.nomina.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@NamedStoredProcedureQuery(
	    name = "PlanillaAguinaldo.generarAguinaldo",
	    procedureName = "sp_generar_planilla_aguinaldo",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "anio", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "porcentaje_pago", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_planilla", type = Integer.class)
	    }
	)

@NamedStoredProcedureQuery(
	    name = "PlanillaAguinaldo.listarTodos",
	    procedureName = "sp_generar_planilla_aguinaldo",
	    resultClasses = EPlanillaAguinaldo.class,
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "anio", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "porcentaje_pago", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion ", type = String.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_planilla", type = Integer.class)
	    }
	)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "planilla_aguinaldo")
public class EPlanillaAguinaldo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_empleado")
    private int idEmpleado;

    private int anio;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Column(name = "porcentaje_pago")
    private int porcentajePago;

    @Column(name = "monto_pagado")
    private BigDecimal montoPagado;

    @Column(name = "tipo_pago")
    private String tipoPago;  // 'Completo' o 'Proporcional'
    
    @Column(name = "estado_pago")
    private String estadoPago;
   
}
