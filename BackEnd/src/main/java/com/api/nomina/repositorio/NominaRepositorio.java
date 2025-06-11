package com.api.nomina.repositorio;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.nomina.modelo.ENomina;

public interface NominaRepositorio extends JpaRepository<ENomina, Integer> {

    @Procedure(name = "Nomina.spNomina")
    void spGestionNomina(
        @Param("accion") String accion,
        @Param("id_nomina") Integer idNomina,
        @Param("tipo") String tipo,
        @Param("periodo_inicio") Date periodoInicio,
        @Param("periodo_fin") Date periodoFin,
        @Param("fecha_generacion") Date fechaGeneracion,
        @Param("estado") String estado,
        @Param("id_periodo") Integer idPeriodo
    );

    @Procedure(name = "Nomina.spNominaListar")
    List<ENomina> spNominaListar(
        @Param("accion") String accion,
        @Param("id_nomina") Integer idNomina,
        @Param("tipo") String tipo,
        @Param("periodo_inicio") Date periodoInicio,
        @Param("periodo_fin") Date periodoFin,
        @Param("fecha_generacion") Date fechaGeneracion,
        @Param("estado") String estado,
        @Param("id_periodo") Integer idPeriodo
    );
}
