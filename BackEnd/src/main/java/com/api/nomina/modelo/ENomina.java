package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;

@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "Nomina.spNomina",
        procedureName = "SP_Nominas",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_nomina", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "tipo", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "periodo_inicio", type = Date.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "periodo_fin", type = Date.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha_generacion", type = Date.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_periodo", type = Integer.class)
        }
    ),
    @NamedStoredProcedureQuery(
        name = "Nomina.spNominaListar",
        procedureName = "SP_Nominas",
        resultClasses = ENomina.class,
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_nomina", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "tipo", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "periodo_inicio", type = Date.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "periodo_fin", type = Date.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha_generacion", type = Date.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_periodo", type = Integer.class)
        }
    )
})


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nominas")
public class ENomina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nomina")
    private Integer idNomina;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "periodo_inicio")
    private Date periodoInicio;

    @Column(name = "periodo_fin")
    private Date periodoFin;

    @Column(name = "fecha_generacion")
    private Date fechaGeneracion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "id_periodo")
    private Integer idPeriodo;
}
