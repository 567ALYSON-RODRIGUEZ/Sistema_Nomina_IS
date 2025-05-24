package com.api.nomina.controlador;

import com.api.nomina.datos.DAnticipo;
import com.api.nomina.modelo.EAnticipo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/anticipo")
public class resAnticipo {

    @Autowired
    private DAnticipo dAnticipo;

    @PostMapping("/crear")
    public ResponseEntity<?> crearAnticipo(@RequestBody EAnticipo anticipo) {
        try {
            dAnticipo.crearAnticipo(
                anticipo.getIdEmpleado(),
                anticipo.getMonto(),
                anticipo.getFechaAnticipo(),
                anticipo.getEstado(),
                anticipo.getDescripcion()
            );
            return ResponseEntity.ok(anticipo);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al crear anticipo");
        }
    }

    @PutMapping("/reemplazar/{id}")
    public ResponseEntity<?> actualizarAnticipo(@PathVariable Integer id, @RequestBody EAnticipo anticipo) {
        try {
            if (!id.equals(anticipo.getIdAnticipo())) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("error", "El ID de la URL no coincide con el del cuerpo."));
            }

            dAnticipo.actualizarAnticipo(
                id,
                anticipo.getIdEmpleado(),
                anticipo.getMonto(),
                anticipo.getFechaAnticipo(),
                anticipo.getEstado(),
                anticipo.getDescripcion()
            );

            return ResponseEntity.ok(anticipo);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al actualizar anticipo");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarAnticipo(@PathVariable Integer id) {
        try {
            dAnticipo.eliminarAnticipo(id);
            return ResponseEntity.ok("Anticipo eliminado correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al eliminar anticipo.");
        }
    }

    @GetMapping("/obtenerTodos")
    public List<EAnticipo> listarAnticipos() {
        try {
            return dAnticipo.listarAnticipos();
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<?> obtenerAnticipoPorId(@PathVariable Integer id) {
        try {
            EAnticipo anticipo = dAnticipo.buscarAnticipoPorId(id);
            if (anticipo != null) {
                return ResponseEntity.ok(anticipo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Anticipo no encontrado");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al buscar el anticipo.");
        }
    }
}
