package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Date;

@NamedStoredProcedureQuery(
    name = "ParametroIGSS.spParametroIGSS",
    procedureName = "SP_ParametrosIGSS",
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_parametro", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "porcentaje", type = BigDecimal.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "tipo", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "descripcion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "vigente_desde", type = Date.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "vigente_hasta", type = Date.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class)
    }
)

@NamedStoredProcedureQuery(
    name = "ParametroIGSS.spParametroIGSSListar",
    procedureName = "SP_ParametrosIGSS",
    resultClasses = EIgss.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_parametro", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "porcentaje", type = BigDecimal.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "tipo", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "descripcion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "vigente_desde", type = Date.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "vigente_hasta", type = Date.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class)
    }
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parametros_igss")
public class EIgss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parametro")
    private int idParametro;

    @Column(name = "porcentaje")
    private BigDecimal porcentaje;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "vigente_desde")
    private Date vigenteDesde;

    @Column(name = "vigente_hasta")
    private Date vigenteHasta;

    @Column(name = "estado")
    private String estado;
}
