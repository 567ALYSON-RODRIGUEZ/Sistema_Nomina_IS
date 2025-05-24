package com.api.nomina.repositorio;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.api.nomina.modelo.EPeriodosNomina;

@Repository
public interface PeriodosRepositorio extends JpaRepository<EPeriodosNomina, Integer> {

    @Procedure(name = "Periodos.spPeriodos")
    void spGestionPeriodos(
        @Param("opc") String opc,
        @Param("id") Integer id,
        @Param("tipo") String tipo,
        @Param("desc") String desc,
        @Param("inicio") Date inicio,
        @Param("fin") Date fin,
        @Param("dias") Integer dias,
        @Param("num_pago") Integer numPago,
        @Param("cod_pago") String codPago,
        @Param("estado") String estado
    );

    @Procedure(name = "Periodos.listarTodos")
    List<EPeriodosNomina> listarPeriodos(
        @Param("opc") String opc,
        @Param("id") Integer id,
        @Param("tipo") String tipo,
        @Param("desc") String desc,
        @Param("inicio") Date inicio,
        @Param("fin") Date fin,
        @Param("dias") Integer dias,
        @Param("num_pago") Integer numPago,
        @Param("cod_pago") String codPago,
        @Param("estado") String estado
    );
}
