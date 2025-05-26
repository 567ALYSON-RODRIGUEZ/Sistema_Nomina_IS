package com.api.nomina.controlador;

import com.api.nomina.datos.DFiniquito;
import com.api.nomina.modelo.EFiniquito;
import com.api.nomina.repositorio.FiniquitoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5255")
@RestController
@RequestMapping("/finiquito")
public class restControllerFiniquito {

    @Autowired
    private DFiniquito dempleado;

    @Autowired
    private FiniquitoRepositorio repo;

    @PostMapping("/generar/{idEmpleado}")
    public ResponseEntity<String> generarFiniquito(@PathVariable int idEmpleado) {
        try {
            String resultado = dempleado.generarFiniquito(idEmpleado);
            return ResponseEntity.ok(resultado != null ? resultado : "Finiquito generado");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al generar el finiquito");
        }
    }
    
}
