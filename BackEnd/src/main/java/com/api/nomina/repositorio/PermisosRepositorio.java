package com.api.nomina.repositorio;

import com.api.nomina.modelo.EUsuarioRol;
import com.api.nomina.modelo.EUsuarioRolId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermisosRepositorio extends JpaRepository<EUsuarioRol, EUsuarioRolId> {

    @Procedure(name = "UsuarioRol.spGestionUsuarioRol")
    void spGestionUsuarioRol(
        @Param("opc") String opc,
        @Param("id_usuario") Integer idUsuario,
        @Param("id_rol") Integer idRol
    );

    @Procedure(name = "UsuarioRol.listarTodos")
    List<EUsuarioRol> listarTodos(
        @Param("opc") String opc,
        @Param("id_usuario") Integer idUsuario,
        @Param("id_rol") Integer idRol
    );
}
