package com.api.nomina.controlador;

import com.api.nomina.datos.DVacaciones;
import com.api.nomina.modelo.EVacaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vacaciones")
public class restVacaciones {

    @Autowired
    private DVacaciones dvacaciones;

    @PostMapping("/crear")
    public ResponseEntity<?> crearVacacion(@RequestBody EVacaciones vacacion) {
        try {
            dvacaciones.insertarVacacion(
                    vacacion.getId_empleado(),
                    vacacion.getFecha_inicio(),
                    vacacion.getFecha_fin(),
                    vacacion.getDias(),
                    vacacion.getEstado()
            );
            return ResponseEntity.ok("Vacación creada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarVacacion(@PathVariable Integer id, @RequestBody EVacaciones vacacion) {
        try {
            if (!id.equals(vacacion.getId_vacacion())) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("error", "El ID en la URL no coincide con el del cuerpo."));
            }

            dvacaciones.actualizarVacacion(
                    id,
                    vacacion.getId_empleado(),
                    vacacion.getFecha_inicio(),
                    vacacion.getFecha_fin(),
                    vacacion.getDias(),
                    vacacion.getEstado()
            );
            return ResponseEntity.ok("Vacación actualizada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PatchMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarVacacion(@PathVariable Integer id) {
        try {
            dvacaciones.eliminarVacacion(id);
            return ResponseEntity.ok("Vacación eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/obtenerTodos")
    public ResponseEntity<?> obtenerTodas() {
        try {
            List<EVacaciones> lista = dvacaciones.obtenerTodas();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            EVacaciones vacacion = dvacaciones.obtenerPorId(id);
            if (vacacion != null) {
                return ResponseEntity.ok(vacacion);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Vacación no encontrada"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}