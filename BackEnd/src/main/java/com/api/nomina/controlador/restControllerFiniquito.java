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
    public String generarFiniquito(@PathVariable int idEmpleado) {
        try {
            dempleado.generarFiniquito(idEmpleado);
            return "Finiquito generado correctamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al generar finiquito.";
        }
    }
    
    @GetMapping("/resumen/{idEmpleado}")
    public List<EFiniquito> obtenerResumenFiniquito(@PathVariable int id_empleado) {
        try {
            return dempleado.listarPorAnio(id_empleado);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
