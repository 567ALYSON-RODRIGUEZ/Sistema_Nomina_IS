package com.api.nomina.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.nomina.modelo.EDetalleNominaS;

@Repository
public interface DetalleNominaSRepositorio extends JpaRepository<EDetalleNominaS, Integer> {

    // Para ejecutar la acción GENERAR o FINALIZAR (sin resultado)
    @Procedure(name = "DetalleNomina.spGenerarNominaSemanal")
    void generarNominaSemanal(
        @Param("accion") String accion,
        @Param("id_periodo") Integer idPeriodo
    );

 // DETALLE: retorna lista de EDetalleNomina

    @Query(value = """
        SELECT dn.* 
        FROM detalle_nomina dn
        JOIN periodos_nomina pn ON dn.id_periodo = pn.id_periodo
        WHERE dn.id_periodo = :idPeriodo
          AND pn.tipo_periodo = 'Semanal'
    """, nativeQuery = true)
    List<EDetalleNominaS> obtenerDetalle(@Param("idPeriodo") Integer idPeriodo);

    // RESUMEN: retorna lista de campos personalizados
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
              AND pn.tipo_periodo = 'Semanal'
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
            WHERE pn.tipo_periodo = 'Semanal'
        """, nativeQuery = true)
        List<Object[]> obtenerTodosLosResumenes();

        @Query(value = "SELECT tipo_periodo FROM periodos_nomina WHERE id_periodo = :idPeriodo", nativeQuery = true)
        String obtenerTipoPeriodo(@Param("idPeriodo") Integer idPeriodo);
    }

