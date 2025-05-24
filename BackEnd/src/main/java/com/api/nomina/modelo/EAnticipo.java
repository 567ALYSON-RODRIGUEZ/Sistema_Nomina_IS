package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NamedStoredProcedureQuery(
    name = "Anticipo.spAnticipo",
    procedureName = "SP_Anticipo",
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_anticipo", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_empleado", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "monto", type = java.math.BigDecimal.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha_anticipo", type = java.sql.Date.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "descripcion", type = String.class)
    }
)

@NamedStoredProcedureQuery(
    name = "Anticipo.spAnticipoListar",
    procedureName = "SP_Anticipo",
    resultClasses = EAnticipo.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_anticipo", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_empleado", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "monto", type = java.math.BigDecimal.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha_anticipo", type = java.sql.Date.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "descripcion", type = String.class)
    }
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "anticipos")
public class EAnticipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anticipo")
    private int idAnticipo;

    @Column(name = "id_empleado")
    private int idEmpleado;

    @Column(name = "monto")
    private java.math.BigDecimal monto;

    @Column(name = "fecha_anticipo")
    private java.sql.Date fechaAnticipo;

    @Column(name = "estado")
    private String estado;

    @Column(name = "descripcion")
    private String descripcion;
}
