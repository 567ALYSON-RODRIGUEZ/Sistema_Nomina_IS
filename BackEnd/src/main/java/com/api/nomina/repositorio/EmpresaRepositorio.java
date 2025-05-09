package com.api.nomina.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.nomina.modelo.EEmpresa;


@Repository
public interface EmpresaRepositorio extends JpaRepository<EEmpresa, Integer> {

    @Procedure(name = "Empresa.spEmpresa")
    void spGestionEmpresa(
        @Param("accion") String accion,
        @Param("IdEmpresa") Integer IdEmpresa,
        @Param("nombreLegal") String nombreLegal,
        @Param("sigla") String sigla,
        @Param("nit") String nit,
        @Param("domicilioFiscal") String domicilioFiscal,
        @Param("correo") String correo,
        @Param("telefono") String telefono,
        @Param("estado") String estado
    );
    
 // Para los SELECT (READ o READ_ID)
    @org.springframework.data.jpa.repository.query.Procedure(name = "Empresa.spEmpresaListar")
    List<EEmpresa> spEmpresaListar(
        @Param("accion") String accion,
        @Param("IdEmpresa") Integer idEmpresa,
        @Param("nombreLegal") String nombreLegal,
        @Param("sigla") String sigla,
        @Param("nit") String nit,
        @Param("domicilioFiscal") String domicilioFiscal,
        @Param("correo") String correo,
        @Param("telefono") String telefono,
        @Param("estado") String estado
    );
}

