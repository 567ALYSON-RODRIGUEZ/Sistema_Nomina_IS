package com.api.nomina.repositorio;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.nomina.modelo.EBonificacion;

@Repository
public interface BonificacionRepositorio extends JpaRepository<EBonificacion, Integer> {

    @Procedure(name = "ParametroBonificacion.spParametroBonificacion")
    void spGestionBonificacion(
        @Param("accion") String accion,
        @Param("id_parametro") Integer idParametro,
        @Param("monto_fijo") BigDecimal montoFijo,
        @Param("vigente_desde") Date vigenteDesde,
        @Param("vigente_hasta") Date vigenteHasta,
        @Param("estado") String estado
    );

    @Procedure(name = "ParametroBonificacion.spParametroBonificacion")
    List<EBonificacion> spListarBonificacion(
        @Param("accion") String accion,
        @Param("id_parametro") Integer idParametro,
        @Param("monto_fijo") BigDecimal montoFijo,
        @Param("vigente_desde") Date vigenteDesde,
        @Param("vigente_hasta") Date vigenteHasta,
        @Param("estado") String estado
    );
}
