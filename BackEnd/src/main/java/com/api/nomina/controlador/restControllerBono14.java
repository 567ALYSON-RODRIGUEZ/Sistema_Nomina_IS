package com.api.nomina.controlador;

import com.api.nomina.datos.DBono14;
import com.api.nomina.modelo.EBono14;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5255")
@RestController
@RequestMapping("/bono14")
public class restControllerBono14 {

	 @Autowired
	    private DBono14 dBono14;

	    @PostMapping("/generar/{anio}")
	    public String generarBono14(@PathVariable("anio") int anio) {
	        try {
	            dBono14.generarPlanillaBono14(anio, "generar", null);
	            return "Planilla de Bono 14 generada para el año " + anio;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Error al generar la planilla de Bono 14.";
	        }
	    }

	    @PostMapping("/pagarLote")
	    public String pagarLote(@RequestBody List<Integer> ids) {
	        try {
	            for (Integer id : ids) {
	                dBono14.generarPlanillaBono14(null,"pagar", id);
	            }
	            return "Pago de Bono 14 realizado correctamente.";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Error al realizar el pago del Bono 14.";
	        }
	    }

	    @GetMapping("/obtenerPorAnio/{anio}")
	    public List<EBono14> listar(@PathVariable("anio") int anio) {
	        try {
	            return dBono14.listarPorAnio(anio);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return List.of();
	        }
	    }
	}
