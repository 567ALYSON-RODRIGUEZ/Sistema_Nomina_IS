package com.api.nomina.repositorio;

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

    // Método para listar usando consulta nativa
    @Query(nativeQuery = true, value = "SELECT * FROM vacaciones")
    List<EVacaciones> listarTodos(); // Cambiado de listarTodasVacaciones a listarTodos
}