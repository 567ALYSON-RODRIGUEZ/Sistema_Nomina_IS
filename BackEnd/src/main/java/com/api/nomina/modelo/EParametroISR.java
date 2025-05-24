package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NamedStoredProcedureQuery(
    name = "ParametroIsr.spIsr",
    procedureName = "sp_gestion_parametros_isr",
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "opc", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "rango_min", type = BigDecimal.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "rango_max", type = BigDecimal.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "cuota_fija", type = BigDecimal.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "porcentaje", type = BigDecimal.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "vigente_desde", type = LocalDate.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "vigente_hasta", type = LocalDate.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "tipo_periodo", type = String.class)
    }
)

@NamedStoredProcedureQuery(
	    name = "ParametroIsr.listarTodos",
	    procedureName = "sp_gestion_parametros_isr",
	    resultClasses = EParametroISR.class,
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "opc", type = String.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "rango_min", type = BigDecimal.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "rango_max", type = BigDecimal.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "cuota_fija", type = BigDecimal.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "porcentaje", type = BigDecimal.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "vigente_desde", type = LocalDate.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "vigente_hasta", type = LocalDate.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "tipo_periodo", type = String.class)
	    }
	)

@Entity
@Table(name = "parametros_isr")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EParametroISR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rango_min")
    private BigDecimal rangoMin;

    @Column(name = "rango_max")
    private BigDecimal rangoMax;

    @Column(name = "cuota_fija")
    private BigDecimal cuotaFija;

    @Column(name = "porcentaje")
    private BigDecimal porcentaje;

    @Column(name = "vigente_desde")
    private LocalDate vigenteDesde;

    @Column(name = "vigente_hasta")
    private LocalDate vigenteHasta;

    @Column(name = "tipo_periodo")
    private String tipoPeriodo;
}
