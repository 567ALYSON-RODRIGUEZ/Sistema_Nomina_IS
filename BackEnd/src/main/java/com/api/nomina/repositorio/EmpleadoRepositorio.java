package com.api.nomina.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.nomina.modelo.EEmpleado;

@Repository
public interface EmpleadoRepositorio extends JpaRepository<EEmpleado, Integer> {

    @Procedure(name = "Empleado.spEmpleado")
    void spGestionEmpleado(
        @Param("accion") String accion,
        @Param("id_empleado") Integer idEmpleado,
        @Param("nombres") String nombres,
        @Param("dpi") String dpi,
        @Param("nit") String nit,
        @Param("fecha_ingreso") java.sql.Date fechaIngreso,
        @Param("fecha_egreso") java.sql.Date fechaEgreso,
        @Param("estado") String estado,
        @Param("tipo_pago") String tipoPago,
        @Param("tipo_contrato") String tipoContrato,
        @Param("id_puesto") Integer idPuesto,
        @Param("id_departamento") Integer idDepartamento
    );

    @Procedure(name = "Empleado.spEmpleadoListar")
    List<EEmpleado> spEmpleadoListar(
        @Param("accion") String accion,
        @Param("id_empleado") Integer idEmpleado,
        @Param("nombres") String nombres,
        @Param("dpi") String dpi,
        @Param("nit") String nit,
        @Param("fecha_ingreso") java.sql.Date fechaIngreso,
        @Param("fecha_egreso") java.sql.Date fechaEgreso,
        @Param("estado") String estado,
        @Param("tipo_pago") String tipoPago,
        @Param("tipo_contrato") String tipoContrato,
        @Param("id_puesto") Integer idPuesto,
        @Param("id_departamento") Integer idDepartamento
    );
}

