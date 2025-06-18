package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "Vacacion.spVacaciones",
        procedureName = "sp_gestion_vacaciones",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "opc", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_empleado", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha_inicio", type = Date.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha_fin", type = Date.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "dias", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class)
        }
    ),
    @NamedStoredProcedureQuery(
        name = "Vacacion.listarTodos",
        procedureName = "sp_gestion_vacaciones",
        resultClasses = EVacaciones.class,
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "opc", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_empleado", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha_inicio", type = Date.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha_fin", type = Date.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "dias", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "result", type = void.class)
        }
    )
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vacaciones")
public class EVacaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vacacion")
    private int id_vacacion;

    @Column(name = "id_empleado")
    private int id_empleado;

    @Column(name = "fecha_inicio")
    private Date fecha_inicio;

    @Column(name = "fecha_fin")
    private Date fecha_fin;

    private int dias;

    private String estado;
}