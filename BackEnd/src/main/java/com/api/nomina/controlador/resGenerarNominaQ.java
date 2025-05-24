package com.api.nomina.controlador;

import com.api.nomina.datos.DDetalleNominaQ;
import com.api.nomina.modelo.EDetalleNominaQ;
import com.api.nomina.datos.DDetalleNominadto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/detallenominaQ")
public class resGenerarNominaQ {

    @Autowired
    private DDetalleNominaQ ddetalleNomina;

    @PostMapping("/generar/{idPeriodo}")
    public ResponseEntity<?> generarNomina(@PathVariable Integer idPeriodo) {
        try {
            ddetalleNomina.generarNomina(idPeriodo);
            return ResponseEntity.ok("Nómina generada correctamente.");
        } catch (Exception ex) {
            String mensajeError = "Error al generar la nómina.";
            

            Throwable cause = ex.getCause();
            while (cause != null) {
                if (cause.getMessage() != null && cause.getMessage().contains("Ya existe una nómina quincenal para este período.")) {
                    mensajeError = "Ya existe una nómina quincenal para este período.";
                    break;
                }
                cause = cause.getCause();
            }

            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        }
    }



    @PostMapping("/finalizar/{idPeriodo}")
    public ResponseEntity<?> finalizarPeriodo(@PathVariable Integer idPeriodo) {
        try {
            ddetalleNomina.finalizarPeriodo(idPeriodo);
            return ResponseEntity.ok("Periodo finalizado correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al finalizar el periodo.");
        }
    }

    @GetMapping("/detalle/{idPeriodo}")
    public ResponseEntity<?> obtenerDetalle(@PathVariable Integer idPeriodo) {
        try {
            List<EDetalleNominaQ> detalle = ddetalleNomina.obtenerDetalle(idPeriodo);
            return ResponseEntity.ok(detalle);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener el detalle de la nómina.");
        }
    }

    @GetMapping("/resumen/{idPeriodo}")
    public ResponseEntity<?> obtenerResumen(@PathVariable Integer idPeriodo) {
        try {
            List<DDetalleNominadto> resumen = ddetalleNomina.obtenerResumen(idPeriodo);
            return ResponseEntity.ok(resumen);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener el resumen de la nómina.");
        }
    }

}
