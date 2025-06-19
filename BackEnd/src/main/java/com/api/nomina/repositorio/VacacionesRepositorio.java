package com.api.nomina.repositorio;

import com.api.nomina.modelo.EVacaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VacacionesRepositorio extends JpaRepository<EVacaciones, Integer> {
    
    // Versión 1: Usando name con el nombre exacto del procedimiento
    @Procedure(name = "sp_gestion_vacaciones")
    void spGestionVacaciones(
        @Param("opc") String opc,
        @Param("id") Integer id,
        @Param("id_empleado") Integer idEmpleado,
        @Param("fecha_inicio") Date fechaInicio,
        @Param("fecha_fin") Date fechaFin,
        @Param("dias") Integer dias,
        @Param("estado") String estado,
        @Param("periodo_anio") Integer periodoAnio  // Cambiado a Integer
    );

    // Versión 2: Alternativa con procedureName
    @Procedure(procedureName = "sp_gestion_vacaciones")
    void ejecutarSPVacaciones(
        @Param("opc") String opc,
        @Param("id") Integer id,
        @Param("id_empleado") Integer idEmpleado,
        @Param("fecha_inicio") Date fechaInicio,
        @Param("fecha_fin") Date fechaFin,
        @Param("dias") Integer dias,
        @Param("estado") String estado,
        @Param("periodo_anio") Integer periodoAnio
    );

    // Consulta nativa para ejecutar el SP
    @Query(value = "{call sp_gestion_vacaciones(:opc, :id, :id_empleado, :fecha_inicio, " +
                  ":fecha_fin, :dias, :estado, :periodo_anio)}", nativeQuery = true)
    List<EVacaciones> listarTodosDesdeSP(
        @Param("opc") String opc,
        @Param("id") Integer id,
        @Param("id_empleado") Integer idEmpleado,
        @Param("fecha_inicio") Date fechaInicio,
        @Param("fecha_fin") Date fechaFin,
        @Param("dias") Integer dias,
        @Param("estado") String estado,
        @Param("periodo_anio") Integer periodoAnio
    );

    @Query(value = "SELECT v.id_vacacion, v.id_empleado, " +
                  "e.nombres AS nombreCompleto, " +
                  "p.nombre AS puesto, d.nombre AS nivelJerarquico, " +
                  "v.fecha_inicio, v.fecha_fin, v.dias, v.estado " +
                  "FROM vacaciones v " +
                  "INNER JOIN empleados e ON v.id_empleado = e.id_empleado " +
                  "LEFT JOIN puestos p ON e.id_puesto = p.id_puesto " +
                  "LEFT JOIN departamentos d ON e.id_departamento = d.id_departamento", 
           nativeQuery = true)
    List<Object[]> listarVacacionesConEmpleado();
}