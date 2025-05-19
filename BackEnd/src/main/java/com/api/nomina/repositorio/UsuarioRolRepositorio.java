package com.api.nomina.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import com.api.nomina.modelo.EUsuario;

@Repository
public interface UsuarioRolRepositorio extends CrudRepository<EUsuario, Integer> {

    @Query(value = "SELECT r.nombre_rol FROM usuario_rol ur " +
                   "JOIN roles r ON ur.id_rol = r.id_rol " +
                   "WHERE ur.id_usuario = :idUsuario", nativeQuery = true)
    String findRolByUsuario(@Param("idUsuario") int idUsuario);
}