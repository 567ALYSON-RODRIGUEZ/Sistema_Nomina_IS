package com.api.nomina.repositorio;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.nomina.modelo.EAnticipo;

@Repository
public interface AnticipoRepositorio extends JpaRepository<EAnticipo, Integer> {

    @Procedure(name = "Anticipo.spAnticipo")
    void spGestionAnticipo(
        @Param("accion") String accion,
        @Param("id_anticipo") Integer idAnticipo,
        @Param("id_empleado") Integer idEmpleado,
        @Param("monto") BigDecimal monto,
        @Param("fecha_anticipo") Date fechaAnticipo,
        @Param("estado") String estado,
        @Param("descripcion") String descripcion
    );

    @Procedure(name = "Anticipo.spAnticipoListar")
    List<EAnticipo> spAnticipoListar(
        @Param("accion") String accion,
        @Param("id_anticipo") Integer idAnticipo,
        @Param("id_empleado") Integer idEmpleado,
        @Param("monto") BigDecimal monto,
        @Param("fecha_anticipo") Date fechaAnticipo,
        @Param("estado") String estado,
        @Param("descripcion") String descripcion
    );
}
