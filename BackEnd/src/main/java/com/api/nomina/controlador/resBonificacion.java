package com.api.nomina.controlador;

import com.api.nomina.datos.DBonificacion;
import com.api.nomina.modelo.EBonificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bonificacion")
public class resBonificacion {

    @Autowired
    private DBonificacion dParametroBonificacion;

    @PostMapping("/crear")
    public ResponseEntity<?> crearParametro(@RequestBody EBonificacion bonificacion) {
        try {
            dParametroBonificacion.crearParametro(
                bonificacion.getMontoFijo(),
                bonificacion.getVigenteDesde(),
                bonificacion.getVigenteHasta(),
                bonificacion.getEstado()
            );
            return ResponseEntity.ok(bonificacion);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear parámetro de bonificación");
        }
    }

    @PutMapping("/reemplazar/{id}")
    public ResponseEntity<?> actualizarParametro(@PathVariable Integer id, @RequestBody EBonificacion bonificacion) {
        try {
            if (!id.equals(bonificacion.getIdParametro())) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "El ID en la URL no coincide con el del cuerpo."));
            }

            String estado = bonificacion.getEstado();
            if (estado != null && !estado.equalsIgnoreCase("Activo") && !estado.equalsIgnoreCase("Eliminado")) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Estado inválido."));
            }

            dParametroBonificacion.actualizarParametro(
                id,
                bonificacion.getMontoFijo(),
                bonificacion.getVigenteDesde(),
                bonificacion.getVigenteHasta(),
                estado
            );
            return ResponseEntity.ok(bonificacion);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar parámetro de bonificación");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarParametro(@PathVariable Integer id) {
        try {
            dParametroBonificacion.eliminarParametro(id);
            return ResponseEntity.ok("Parámetro de bonificación eliminado correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar parámetro de bonificación.");
        }
    }

    @GetMapping("/obtenerTodos")
    public List<EBonificacion> listarParametros() {
        try {
            return dParametroBonificacion.listarParametros();
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<?> obtenerParametroPorId(@PathVariable Integer id) {
        try {
            EBonificacion parametro = dParametroBonificacion.buscarParametroPorId(id);
            if (parametro != null) {
                return ResponseEntity.ok(parametro);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parámetro no encontrado");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el parámetro.");
        }
    }
}
