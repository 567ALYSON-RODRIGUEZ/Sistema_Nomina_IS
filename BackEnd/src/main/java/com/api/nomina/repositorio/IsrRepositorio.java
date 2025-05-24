package com.api.nomina.repositorio;

import com.api.nomina.modelo.EParametroISR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface IsrRepositorio extends JpaRepository<EParametroISR, Integer> {

    @Procedure(name = "ParametroIsr.spIsr")
    void spGestionParametroIsr(
        @Param("opc") String opc,
        @Param("id") Integer id,
        @Param("rango_min") BigDecimal rangoMin,
        @Param("rango_max") BigDecimal rangoMax,
        @Param("cuota_fija") BigDecimal cuotaFija,
        @Param("porcentaje") BigDecimal porcentaje,
        @Param("vigente_desde") LocalDate vigenteDesde,
        @Param("vigente_hasta") LocalDate vigenteHasta,
        @Param("tipo_periodo") String tipoPeriodo
    );

    @Procedure(name = "ParametroIsr.listarTodos")
    List<EParametroISR> listarTodos(
        @Param("opc") String opc,
        @Param("id") Integer id,
        @Param("rango_min") BigDecimal rangoMin,
        @Param("rango_max") BigDecimal rangoMax,
        @Param("cuota_fija") BigDecimal cuotaFija,
        @Param("porcentaje") BigDecimal porcentaje,
        @Param("vigente_desde") LocalDate vigenteDesde,
        @Param("vigente_hasta") LocalDate vigenteHasta,
        @Param("tipo_periodo") String tipoPeriodo
    );
}
