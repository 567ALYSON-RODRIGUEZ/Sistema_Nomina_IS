package com.api.nomina.controlador;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.api.nomina.datos.DIgss;
import com.api.nomina.modelo.EIgss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/igss")
public class resIgss {

    @Autowired
    private DIgss digss;

    @PostMapping("/crear")
    public ResponseEntity<?> crearParametro(@RequestBody EIgss parametro) {
        try {
            digss.crearParametroIGSS(
                parametro.getPorcentaje(),
                parametro.getTipo(),
                parametro.getDescripcion(),
                parametro.getVigenteDesde(),
                parametro.getVigenteHasta(),
                parametro.getEstado()
            );
            return ResponseEntity.ok(parametro);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear parámetro IGSS");
        }
    }

    @PutMapping("/reemplazar/{id}")
    public ResponseEntity<?> actualizarParametro(@PathVariable Integer id, @RequestBody EIgss parametro) {
        try {
            if (!id.equals(parametro.getIdParametro())) {
                return ResponseEntity.badRequest().body("El ID en la URL no coincide con el del cuerpo.");
            }

            digss.actualizarParametroIGSS(
                id,
                parametro.getPorcentaje(),
                parametro.getTipo(),
                parametro.getDescripcion(),
                parametro.getVigenteDesde(),
                parametro.getVigenteHasta(),
                parametro.getEstado()
            );

            return ResponseEntity.ok(parametro);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar parámetro IGSS");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarParametro(@PathVariable Integer id) {
        try {
            digss.eliminarParametroIGSS(id);
            return ResponseEntity.ok("Parámetro IGSS eliminado correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar parámetro IGSS.");
        }
    }

    @GetMapping("/obtenerTodos")
    public List<EIgss> listarParametros() {
        try {
            return digss.listarParametrosIGSS();
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<?> obtenerParametroPorId(@PathVariable Integer id) {
        try {
            EIgss parametro = digss.buscarParametroIGSSPorId(id);
            if (parametro != null) {
                return ResponseEntity.ok(parametro);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parámetro IGSS no encontrado");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar parámetro IGSS.");
        }
    }
}
