package com.api.nomina.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.nomina.modelo.EUsuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<EUsuario, Integer> {

    @Procedure(name = "Usuario.spUsuario")
    void spGestionUsuario(
        @Param("accion") String accion,
        @Param("id_usuario") Integer idUsuario,
        @Param("username") String username,
        @Param("password_hash") String passwordHash,
        @Param("nombre_completo") String nombreCompleto,
        @Param("estado") String estado
    );

    @Procedure(name = "Usuario.spUsuarioListar")
    List<EUsuario> spUsuarioListar(
        @Param("accion") String accion,
        @Param("id_usuario") Integer idUsuario,
        @Param("username") String username,
        @Param("password_hash") String passwordHash,
        @Param("nombre_completo") String nombreCompleto,
        @Param("estado") String estado
    );
}
