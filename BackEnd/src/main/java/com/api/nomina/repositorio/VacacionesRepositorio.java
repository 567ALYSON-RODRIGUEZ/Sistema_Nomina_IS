package com.api.nomina.repositorio;

import com.api.nomina.datos.DVacacionesdto;
import com.api.nomina.modelo.EVacaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface VacacionesRepositorio extends JpaRepository<EVacaciones, Integer> {

    // Procedimiento para insertar, actualizar o eliminar
    @Transactional
    @Procedure(name = "Vacacion.spVacaciones")
    void spGestionVacaciones(
        @Param("opc") String opc,
        @Param("id") Integer id,
        @Param("id_empleado") Integer idEmpleado,
        @Param("fecha_inicio") Date fechaInicio,
        @Param("fecha_fin") Date fechaFin,
        @Param("dias") Integer dias,
        @Param("estado") String estado
    );

    // Llamar al SP para obtener todas las vacaciones
    @Transactional
    @Procedure(name = "Vacacion.listarTodos")
    List<EVacaciones> listarTodosDesdeSP(
        @Param("opc") String opc,
        @Param("id") Integer id,
        @Param("id_empleado") Integer idEmpleado,
        @Param("fecha_inicio") Date fechaInicio,
        @Param("fecha_fin") Date fechaFin,
        @Param("dias") Integer dias,
        @Param("estado") String estado
    );

    @Query(value = """
    	    SELECT 
    	        v.id_vacacion,
    	        v.id_empleado,
    	        e.nombres AS nombre_completo,
    	        p.nombre AS puesto,
    	        v.fecha_inicio,
    	        v.fecha_fin,
    	        v.dias,
    	        v.estado
    	    FROM vacaciones v
    	    JOIN empleados e ON v.id_empleado = e.id_empleado
    	    JOIN puestos p ON e.id_puesto = p.id_puesto
    	""", nativeQuery = true)
    	List<Object[]> listarVacacionesConEmpleado();

}  