package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.*;

@NamedStoredProcedureQuery(
    name = "Empleado.spEmpleado",
    procedureName = "SP_Empleados",
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_empleado", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "nombres", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "dpi", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "nit", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha_ingreso", type = java.sql.Date.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha_egreso", type = java.sql.Date.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "tipo_pago", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "tipo_contrato", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_puesto", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_departamento", type = Integer.class)
    }
)

@NamedStoredProcedureQuery(
    name = "Empleado.spEmpleadoListar",
    procedureName = "SP_Empleados",
    resultClasses = EEmpleado.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_empleado", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "nombres", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "dpi", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "nit", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha_ingreso", type = java.sql.Date.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha_egreso", type = java.sql.Date.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "tipo_pago", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "tipo_contrato", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_puesto", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_departamento", type = Integer.class)
    }
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleados")
public class EEmpleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private int idEmpleado;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "dpi")
    private String dpi;

    @Column(name = "nit")
    private String nit;

    @Column(name = "fecha_ingreso")
    private java.sql.Date fechaIngreso;

    @Column(name = "fecha_egreso")
    private java.sql.Date fechaEgreso;

    @Column(name = "estado")
    private String estado;

    @Column(name = "tipo_pago")
    private String tipoPago;

    @Column(name = "tipo_contrato")
    private String tipoContrato;

    @Column(name = "id_puesto")
    private int idPuesto;

    @Column(name = "id_departamento")
    private int idDepartamento;
}



