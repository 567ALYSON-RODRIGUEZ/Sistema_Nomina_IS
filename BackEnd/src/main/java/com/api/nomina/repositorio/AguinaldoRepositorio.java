package com.api.nomina.repositorio;

import com.api.nomina.modelo.EPlanillaAguinaldo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AguinaldoRepositorio extends JpaRepository<EPlanillaAguinaldo, Integer> {

    List<EPlanillaAguinaldo> findByAnio(int anio);

    @Procedure(name = "PlanillaAguinaldo.generarAguinaldo")
    void generarPlanillaAguinaldo(
        @Param("anio") Integer anio,
        @Param("porcentaje_pago") Integer porcentajePago,
        @Param("accion") String accion,
        @Param("id_planilla") Integer idPlanilla
    );
}
