package com.api.nomina.datos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EUsuario;
import com.api.nomina.repositorio.UsuarioRepositorio;

@Service
public class DUsuario {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public void crearUsuario(
        Integer idUsuario,
        String username,
        String passwordHash,
        String nombreCompleto,
        String estado
    ) {
        usuarioRepositorio.spGestionUsuario(
            "CREATE", idUsuario, username, passwordHash, nombreCompleto, estado
        );
    }

    public void actualizarUsuario(
        Integer idUsuario,
        String username,
        String passwordHash,
        String nombreCompleto,
        String estado
    ) {
        usuarioRepositorio.spGestionUsuario(
            "UPDATE", idUsuario, username, passwordHash, nombreCompleto, estado
        );
    }

    public void eliminarUsuario(Integer idUsuario) {
        usuarioRepositorio.spGestionUsuario(
            "DELETE", idUsuario, null, null, null, null
        );
    }

    @Transactional(readOnly = true)
    public List<EUsuario> listarUsuarios() {
        return usuarioRepositorio.spUsuarioListar(
            "READ", null, null, null, null, null
        );
    }

    @Transactional(readOnly = true)
    public EUsuario buscarUsuarioPorId(Integer idUsuario) {
        List<EUsuario> resultado = usuarioRepositorio.spUsuarioListar(
            "READ_ID", idUsuario, null, null, null, null
        );
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    @Transactional
    public Map<String, Object> actualizarParcial(Integer idUsuario, Map<String, Object> campos) {
        EUsuario actual = buscarUsuarioPorId(idUsuario);
        if (actual == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Map<String, Object> cambios = new HashMap<>();

        for (Map.Entry<String, Object> entry : campos.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "username":
                    if (value != null && !value.equals(actual.getUsername())) {
                        cambios.put("username", value);
                        actual.setUsername((String) value);
                    }
                    break;
                case "passwordHash":
                    if (value != null && !value.equals(actual.getPasswordHash())) {
                        cambios.put("passwordHash", value);
                        actual.setPasswordHash((String) value);
                    }
                    break;
                case "nombreCompleto":
                    if (value != null && !value.equals(actual.getNombreCompleto())) {
                        cambios.put("nombreCompleto", value);
                        actual.setNombreCompleto((String) value);
                    }
                    break;
                case "estado":
                    if (value != null && !value.equals(actual.getEstado())) {
                        cambios.put("estado", value);
                        actual.setEstado((String) value);
                    }
                    break;
                default:
                    break;
            }
        }

        if (!cambios.isEmpty()) {
            usuarioRepositorio.save(actual);
        }

        return cambios;
    }
}
