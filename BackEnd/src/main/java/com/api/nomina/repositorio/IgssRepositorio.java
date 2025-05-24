package com.api.nomina.repositorio;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.nomina.modelo.EIgss;

@Repository
public interface IgssRepositorio extends JpaRepository<EIgss, Integer> {

    @Procedure(name = "ParametroIGSS.spParametroIGSS")
    void spGestionParametroIGSS(
        @Param("accion") String accion,
        @Param("id_parametro") Integer idParametro,
        @Param("porcentaje") BigDecimal porcentaje,
        @Param("tipo") String tipo,
        @Param("descripcion") String descripcion,
        @Param("vigente_desde") Date vigenteDesde,
        @Param("vigente_hasta") Date vigenteHasta,
        @Param("estado") String estado
    );

    @Procedure(name = "ParametroIGSS.spParametroIGSSListar")
    List<EIgss> spParametroIGSSListar(
        @Param("accion") String accion,
        @Param("id_parametro") Integer idParametro,
        @Param("porcentaje") BigDecimal porcentaje,
        @Param("tipo") String tipo,
        @Param("descripcion") String descripcion,
        @Param("vigente_desde") Date vigenteDesde,
        @Param("vigente_hasta") Date vigenteHasta,
        @Param("estado") String estado
    );
}
