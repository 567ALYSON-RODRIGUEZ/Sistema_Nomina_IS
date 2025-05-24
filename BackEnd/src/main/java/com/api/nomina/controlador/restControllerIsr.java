package com.api.nomina.controlador;

import com.api.nomina.datos.DParametroISR;
import com.api.nomina.modelo.EParametroISR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5255")
@RestController
@RequestMapping("/parametroIsr")
public class restControllerIsr {

    @Autowired
    private DParametroISR dparametroISR;

    // POST: http://localhost:8095/parametroIsr/crear
    @PostMapping("/crear")
    public EParametroISR crear(@RequestBody EParametroISR param) {
        try {
            dparametroISR.crearParametroIsr(param);
            System.out.println("Parámetro ISR creado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al crear parámetro ISR.");
        }
        return param;
    }

    // GET: http://localhost:8095/parametroIsr/obtenerTodos
    @GetMapping("/obtenerTodos")
    public List<EParametroISR> obtenerTodos() {
        try {
            return dparametroISR.mostrarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // GET: http://localhost:8095/parametroIsr/obtenerPorId/{id}
    @GetMapping("/obtenerPorId/{id}")
    public EParametroISR obtenerPorId(@PathVariable Integer id) {
        try {
            return dparametroISR.mostrarPorId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EParametroISR> actualizar(@PathVariable Integer id, @RequestBody EParametroISR param) {
        if (!id.equals(param.getId())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            dparametroISR.actualizarParametroIsr(id, param); 
            return ResponseEntity.ok(param);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PATCH: http://localhost:8095/parametroIsr/eliminar/{id}
    @PatchMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        try {
            dparametroISR.eliminarParametroIsr(id);
            return ResponseEntity.ok("Parámetro ISR eliminado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar.");
        }
    }
}
