package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NamedStoredProcedureQuery(
    name = "ParametroBonificacion.spParametroBonificacion",
    procedureName = "SP_ParametrosBonificacion",
    resultClasses = EBonificacion.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_parametro", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "monto_fijo", type = java.math.BigDecimal.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "vigente_desde", type = java.sql.Date.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "vigente_hasta", type = java.sql.Date.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class)
    }
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parametros_bonificacion")
public class EBonificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parametro")
    private int idParametro;

    @Column(name = "monto_fijo")
    private java.math.BigDecimal montoFijo;

    @Column(name = "vigente_desde")
    private java.sql.Date vigenteDesde;

    @Column(name = "vigente_hasta")
    private java.sql.Date vigenteHasta;

    @Column(name = "estado")
    private String estado;
}
