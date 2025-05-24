package com.api.nomina.controlador;

import com.api.nomina.datos.DAguinaldo;
import com.api.nomina.modelo.EPlanillaAguinaldo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5255")
@RestController
@RequestMapping("/aguinaldo")
public class restControllerAguinaldo {

    @Autowired
    private DAguinaldo dplanilla;

    // URL: http://localhost:8095/aguinaldo/generar/2024/50
    @PostMapping("/generar/{anio}/{porcentaje}")
    public String generarPlanillaAguinaldo(
            @PathVariable("anio") int anio,
            @PathVariable("porcentaje") int porcentaje) {
        try {
            dplanilla.generarPlanillaAguinaldo(anio, porcentaje, "generar", null);
            return "Planilla de aguinaldo generada para el año " + anio + " con " + porcentaje + "%.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al generar la planilla.";
        }
    }

    @PostMapping("/pagarLote")
    public String pagarPlanillasMasivo(@RequestBody List<Integer> ids) {
        try {
            for (Integer id : ids) {
                dplanilla.generarPlanillaAguinaldo(2024, 50, "pagar", id); // anio y porcentaje válidos
            }
            return "Pago masivo realizado.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error en el pago masivo.";
        }
    }

    // URL: http://localhost:8095/aguinaldo/obtenerPorAnio/2024
    @GetMapping("/obtenerPorAnio/{anio}")
    public List<EPlanillaAguinaldo> obtenerPlanillaPorAnio(@PathVariable("anio") int anio) {
        try {
            return dplanilla.listarPorAnio(anio);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
