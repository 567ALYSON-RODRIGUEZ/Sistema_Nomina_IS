package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NamedStoredProcedureQuery(
    name = "DetalleNomina.spGenerarNominaQuincenal",
    procedureName = "generar_nomina_quincenal",
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_periodo", type = Integer.class)
    },
    resultClasses = EDetalleNominaQ.class
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_nomina")
public class EDetalleNominaQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalleNomina;

    @Column(name = "id_nomina")
    private Integer idNomina;

    @Column(name = "id_periodo")
    private Integer idPeriodo;

    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "sueldo_asignado")
    private BigDecimal sueldoAsignado;

    @Column(name = "dias_laborados")
    private Integer diasLaborados;

    @Column(name = "sueldo_ordinario")
    private BigDecimal sueldoOrdinario;

    @Column(name = "sueldo_extraordinario")
    private BigDecimal sueldoExtraordinario;

    @Column(name = "bonificacion_incentivo")
    private BigDecimal bonificacionIncentivo;

    @Column(name = "vacaciones")
    private Integer vacaciones;

    @Column(name = "igss")
    private BigDecimal igss;

    @Column(name = "isr")
    private BigDecimal isr;

    @Column(name = "anticipos")
    private BigDecimal anticipos;

    @Column(name = "total_ingresos")
    private BigDecimal totalIngresos;

    @Column(name = "total_descuentos")
    private BigDecimal totalDescuentos;

    @Column(name = "salario_neto")
    private BigDecimal salarioNeto;
    
    @Column(name = "nombre_empleado")
    private String nombreEmpleado;

    @Column(name = "cargo_empleado")
    private String cargoEmpleado;

    @Column(name = "hora_extra")
    private Integer horaExtra;

}
