package com.api.nomina.repositorio;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.nomina.modelo.EAsistencias;

@Repository
public interface AsistenciasRepositorio extends JpaRepository<EAsistencias, Integer> {

    @Procedure(name = "Asistencia.spAsistencia")
    void spGestionAsistencia(
        @Param("accion") String accion,
        @Param("id_asistencia") Integer idAsistencia,
        @Param("id_empleado") Integer idEmpleado,
        @Param("fecha") Date fecha,
        @Param("hora_entrada") Time horaEntrada,
        @Param("hora_salida") Time horaSalida,
        @Param("tipo") String tipo,
        @Param("estado") String estado
    );

    @Procedure(name = "Asistencia.spAsistenciaListar")
    List<EAsistencias> spAsistenciaListar(
        @Param("accion") String accion,
        @Param("id_asistencia") Integer idAsistencia,
        @Param("id_empleado") Integer idEmpleado,
        @Param("fecha") Date fecha,
        @Param("hora_entrada") Time horaEntrada,
        @Param("hora_salida") Time horaSalida,
        @Param("tipo") String tipo,
        @Param("estado") String estado
    );
}
