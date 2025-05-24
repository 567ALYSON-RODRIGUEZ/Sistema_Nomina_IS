package com.api.nomina.controlador;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.api.nomina.datos.DAsistencias;
import com.api.nomina.modelo.EAsistencias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/asistencia")
public class resAsistencias {

    @Autowired
    private DAsistencias dasistencia;

    @PostMapping("/crear")
    public ResponseEntity<?> crearAsistencia(@RequestBody EAsistencias asistencia) {
        try {
            dasistencia.crearAsistencia(
                null,
                asistencia.getIdEmpleado(),
                asistencia.getFecha(),
                asistencia.getHoraEntrada(),
                asistencia.getHoraSalida(),
                asistencia.getTipo(),
                asistencia.getEstado()
            );
            return ResponseEntity.ok(asistencia);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear asistencia");
        }
    }

    @PutMapping("/reemplazar/{id}")
    public ResponseEntity<?> actualizarAsistencia(@PathVariable Integer id, @RequestBody EAsistencias asistencia) {
        try {
            if (!id.equals(asistencia.getIdAsistencia())) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "El ID en la URL no coincide con el del cuerpo."));
            }

            String estado = asistencia.getEstado();
            if (estado != null && !(estado.equalsIgnoreCase("Presente") || estado.equalsIgnoreCase("Ausente") || estado.equalsIgnoreCase("Justificado"))) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Estado inválido."));
            }

            dasistencia.actualizarAsistencia(
                id,
                asistencia.getIdEmpleado(),
                asistencia.getFecha(),
                asistencia.getHoraEntrada(),
                asistencia.getHoraSalida(),
                asistencia.getTipo(),
                estado
            );

            return ResponseEntity.ok(asistencia);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar asistencia");
        }
    }

    @PatchMapping("/actualizarParcial/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> campos) {
        try {
            Map<String, Object> actualizados = dasistencia.actualizarParcial(id, campos);
            return ResponseEntity.ok(actualizados);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error al actualizar parcialmente."));
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarAsistencia(@PathVariable Integer id) {
        try {
            dasistencia.eliminarAsistencia(id);
            return ResponseEntity.ok("Asistencia eliminada correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la asistencia.");
        }
    }

    @GetMapping("/obtenerTodos")
    public List<EAsistencias> listarAsistencias() {
        try {
            return dasistencia.listarAsistencias();
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<?> obtenerAsistenciaPorId(@PathVariable Integer id) {
        try {
            EAsistencias asistencia = dasistencia.buscarAsistenciaPorId(id);
            if (asistencia != null) {
                return ResponseEntity.ok(asistencia);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asistencia no encontrada");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar la asistencia.");
        }
    }
}
