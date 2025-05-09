package com.api.nomina.controlador;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.api.nomina.datos.DUsuario;
import com.api.nomina.modelo.EUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuario")
public class resUsuario {

    @Autowired
    private DUsuario dusuario;

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody EUsuario usuario) {
        try {
            dusuario.crearUsuario(
                null,
                usuario.getUsername(),
                usuario.getPasswordHash(),
                usuario.getNombreCompleto(),
                usuario.getEstado()
            );
            return ResponseEntity.ok(usuario);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear usuario");
        }
    }

    @PutMapping("/reemplazar/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Integer id, @RequestBody EUsuario usuario) {
        try {
            if (!id.equals(usuario.getIdUsuario())) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "El ID en la URL no coincide con el del cuerpo."));
            }

            String estado = usuario.getEstado();
            if (estado != null && !estado.equalsIgnoreCase("Activo") && !estado.equalsIgnoreCase("Inactivo")) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Estado inválido."));
            }

            dusuario.actualizarUsuario(
                id,
                usuario.getUsername(),
                usuario.getPasswordHash(),
                usuario.getNombreCompleto(),
                estado
            );

            return ResponseEntity.ok(usuario);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar usuario");
        }
    }

    @PatchMapping("/actualizarParcial/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> campos) {
        try {
            Map<String, Object> actualizados = dusuario.actualizarParcial(id, campos);
            return ResponseEntity.ok(actualizados);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error al actualizar parcialmente."));
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer id) {
        try {
            dusuario.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario.");
        }
    }

    @GetMapping("/obtenerTodos")
    public List<EUsuario> listarUsuarios() {
        try {
            return dusuario.listarUsuarios();
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Integer id) {
        try {
            EUsuario usuario = dusuario.buscarUsuarioPorId(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el usuario.");
        }
    }
}
