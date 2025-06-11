package com.api.nomina.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.nomina.modelo.EDetalleNominaQ;

@Repository
public interface DetalleNominaQRepositorio extends JpaRepository<EDetalleNominaQ, Integer> {

    @Procedure(name = "DetalleNomina.spGenerarNominaQuincenal")
    void generarNominaQuincenal(
        @Param("accion") String accion,
        @Param("id_periodo") Integer idPeriodo
    );
 

    @Query(value = """
        SELECT dn.* 
        FROM detalle_nomina dn
        JOIN periodos_nomina pn ON dn.id_periodo = pn.id_periodo
        WHERE dn.id_periodo = :idPeriodo
          AND pn.tipo_periodo = 'Quincenal'
    """, nativeQuery = true)
    List<EDetalleNominaQ> obtenerDetalle(@Param("idPeriodo") Integer idPeriodo);

    @Query(value = """
        SELECT 
            e.id_empleado AS idEmpleado, 
            e.nombres AS nombreCompleto, 
            p.nombre AS puesto, 
            dn.total_ingresos AS totalIngresos, 
            dn.total_descuentos AS totalDescuentos, 
            dn.salario_neto AS salarioNeto
        FROM detalle_nomina dn
        JOIN empleados e ON dn.id_empleado = e.id_empleado
        JOIN puestos p ON e.id_puesto = p.id_puesto
        JOIN periodos_nomina pn ON dn.id_periodo = pn.id_periodo
        WHERE dn.id_periodo = :idPeriodo
          AND pn.tipo_periodo = 'Quincenal'
    """, nativeQuery = true)
    List<Object[]> obtenerResumen(@Param("idPeriodo") Integer idPeriodo);
    
    @Query(value = """
            SELECT 
                e.id_empleado AS idEmpleado, 
                e.nombres AS nombreCompleto, 
                p.nombre AS puesto, 
                dn.total_ingresos AS totalIngresos, 
                dn.total_descuentos AS totalDescuentos, 
                dn.salario_neto AS salarioNeto
            FROM detalle_nomina dn
            JOIN empleados e ON dn.id_empleado = e.id_empleado
            JOIN puestos p ON e.id_puesto = p.id_puesto
            JOIN periodos_nomina pn ON dn.id_periodo = pn.id_periodo
            WHERE pn.tipo_periodo = 'Quincenal'
        """, nativeQuery = true)
        List<Object[]> obtenerTodosLosResumenes();

        @Query(value = "SELECT tipo_periodo FROM periodos_nomina WHERE id_periodo = :idPeriodo", nativeQuery = true)
        String obtenerTipoPeriodo(@Param("idPeriodo") Integer idPeriodo);
    }



