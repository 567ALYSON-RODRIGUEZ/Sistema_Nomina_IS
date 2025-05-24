package com.api.nomina.datos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EUsuarioRol;
import com.api.nomina.repositorio.PermisosRepositorio;
import com.api.nomina.repositorio.UsuarioRolRepositorio;

@Service
public class DUsuarioRol {

    @Autowired
    private PermisosRepositorio usuarioRolRepositorio;

    // Asignar relación usuario-rol
    public void asignarRol(Integer idUsuario, Integer idRol) {
        usuarioRolRepositorio.spGestionUsuarioRol("C", idUsuario, idRol);
    }

    // Eliminar relación usuario-rol
    public void eliminarRol(Integer idUsuario, Integer idRol) {
        usuarioRolRepositorio.spGestionUsuarioRol("D", idUsuario, idRol);
    }

    // Obtener todos los registros
    @Transactional(readOnly = true)
    public List<EUsuarioRol> obtenerTodos() {
        return usuarioRolRepositorio.listarTodos("R", null, null);
    }

    // Obtener uno por ID compuesto
    @Transactional(readOnly = true)
    public EUsuarioRol obtenerUno(Integer idUsuario, Integer idRol) {
        List<EUsuarioRol> resultado = usuarioRolRepositorio.listarTodos("R1", idUsuario, idRol);
        return resultado.isEmpty() ? null : resultado.get(0);
    }
}
