package com.api.nomina.controlador;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import com.api.nomina.datos.DNomina;
import com.api.nomina.modelo.ENomina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/nomina")
public class resNomina {

    @Autowired
    private DNomina dnomina;

    @PostMapping("/crear")
    public ResponseEntity<?> crearNomina(@RequestBody ENomina nomina) {
        try {
            dnomina.crearNomina(
                nomina.getTipo(),
                nomina.getPeriodoInicio(),
                nomina.getPeriodoFin(),
                nomina.getFechaGeneracion(),
                nomina.getEstado(),
                nomina.getIdPeriodo()
            );
            return ResponseEntity.ok(nomina);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear nómina");
        }
    }

    @PutMapping("/reemplazar/{id}")
    public ResponseEntity<?> actualizarNomina(@PathVariable Integer id, @RequestBody ENomina nomina) {
        try {
            if (!id.equals(nomina.getIdNomina())) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "El ID en la URL no coincide con el del cuerpo."));
            }

            dnomina.actualizarNomina(
                id,
                nomina.getTipo(),
                nomina.getPeriodoInicio(),
                nomina.getPeriodoFin(),
                nomina.getFechaGeneracion(),
                nomina.getEstado(),
                nomina.getIdPeriodo()
            );

            return ResponseEntity.ok(nomina);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar nómina");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarNomina(@PathVariable Integer id) {
        try {
            dnomina.eliminarNomina(id);
            return ResponseEntity.ok("Nómina eliminada correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la nómina.");
        }
    }

    @GetMapping("/obtenerTodos")
    public List<ENomina> listarNominas() {
        try {
            return dnomina.listarNominas();
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<?> obtenerNominaPorId(@PathVariable Integer id) {
        try {
            ENomina nomina = dnomina.buscarNominaPorId(id);
            if (nomina != null) {
                return ResponseEntity.ok(nomina);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nómina no encontrada");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar la nómina.");
        }
    }
}
