package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@NamedStoredProcedureQuery(
    name = "Asistencia.spAsistencia",
    procedureName = "SP_Asistencias",
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_asistencia", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_empleado", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha", type = Date.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "hora_entrada", type = Time.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "hora_salida", type = Time.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "tipo", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class)
    }
)

@NamedStoredProcedureQuery(
    name = "Asistencia.spAsistenciaListar",
    procedureName = "SP_Asistencias",
    resultClasses = EAsistencias.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_asistencia", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_empleado", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha", type = Date.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "hora_entrada", type = Time.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "hora_salida", type = Time.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "tipo", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class)
    }
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "asistencias")
public class EAsistencias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    private int idAsistencia;

    @Column(name = "id_empleado")
    private int idEmpleado;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "hora_entrada")
    private Time horaEntrada;

    @Column(name = "hora_salida")
    private Time horaSalida;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "estado")
    private String estado;
}
