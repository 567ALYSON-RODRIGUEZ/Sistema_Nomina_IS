package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.*;

@NamedStoredProcedureQuery(
	    name = "Finiquito.generarFiniquito",
	    procedureName = "sp_generar_finiquito",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_empleado", type = Integer.class),
	    }
	)

@NamedStoredProcedureQuery(
	    name = "Finiquito.listarTodos",
	    procedureName = "sp_generar_finiquito",
	    resultClasses = EFiniquito.class,
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
@Table(name = "finiquitos")
public class EFiniquito {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	  
	    private int id_finiquito;

	    @Column(name = "id_empleado")
	    private int idEmpleado;

	    @Column(name = "fecha_liquidacion")
	    private String fechaLiquidacion;

	    @Column(name = "sueldo_pendiente")
	    private double sueldoPendiente;

	    @Column(name = "aguinaldo_proporcional")
	    private double aguinaldoProporcional;

	    @Column(name = "bono14_proporcional")
	    private double bono14Proporcional;

	    @Column(name = "vacaciones_no_gozadas")
	    private double vacacionesNoGozadas;

	    @Column(name = "indemnizacion")
	    private double indemnizacion;

	    @Column(name = "bonificacion_incentivo")
	    private double bonificacionIncentivo;

	    @Column(name = "total_liquidacion")
	    private double totalLiquidacion;
}
