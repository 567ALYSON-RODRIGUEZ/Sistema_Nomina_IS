package com.api.nomina.controlador;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.nomina.datos.DPeriodosNomina;
import com.api.nomina.modelo.EPeriodosNomina;

@CrossOrigin(origins = "http://localhost:5255")
@RestController
@RequestMapping("/periodo")
public class restControllerPeriodos{

    @Autowired
    private DPeriodosNomina dperiodo;

    // POST: http://localhost:8095/periodo/crear
    @PostMapping("/crear")
    public EPeriodosNomina crearPeriodo(@RequestBody EPeriodosNomina periodo) {
        try {
            dperiodo.crearPeriodo(
                periodo.getTipo_periodo(),
                periodo.getDescripcion(),
                Date.valueOf(periodo.getFecha_inicio()),
                Date.valueOf(periodo.getFecha_fin()),
                periodo.getDias_a_pagar(),
                periodo.getNumero_pago(),
                periodo.getCodigo_pago(),
                periodo.getEstado()
            );
            System.out.println("Periodo creado correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error al crear el periodo.");
        }
        return periodo;
    }

    // GET: http://localhost:8095/periodo/obtenerTodos
    @GetMapping("/obtenerTodos")
    public List<EPeriodosNomina> obtenerTodos() {
        try {
            return dperiodo.mostrarTodosPeriodos();
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    // GET: http://localhost:8095/periodo/obtenerPorId/1
    @GetMapping("/obtenerPorId/{id}")
    public EPeriodosNomina obtenerPorId(@PathVariable("id") Integer id) {
        try {
            return dperiodo.mostrarPeriodoPorId(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // PUT: http://localhost:8095/periodo/actualizar/1
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EPeriodosNomina> actualizarPeriodo(@PathVariable Integer id, @RequestBody EPeriodosNomina periodo) {
        try {
            if (!id.equals(periodo.getId_periodo())) {
                return ResponseEntity.badRequest().body(null);
            }

            dperiodo.actualizarPeriodo(
                id,
                periodo.getTipo_periodo(),
                periodo.getDescripcion(),
                Date.valueOf(periodo.getFecha_inicio()),
                Date.valueOf(periodo.getFecha_fin()),
                periodo.getDias_a_pagar(),
                periodo.getNumero_pago(),
                periodo.getCodigo_pago(),
                periodo.getEstado()
            );

            System.out.println("Periodo actualizado correctamente.");
            return ResponseEntity.ok(periodo);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error al actualizar el periodo.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // PATCH: http://localhost:8095/periodo/cerrar/1
    @PatchMapping("/cerrar/{id}")
    public void cerrarPeriodo(@PathVariable("id") Integer id) {
        try {
            dperiodo.cerrarPeriodo(id);
            System.out.println("Periodo cerrado correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error al cerrar el periodo.");
        }
    }
}
