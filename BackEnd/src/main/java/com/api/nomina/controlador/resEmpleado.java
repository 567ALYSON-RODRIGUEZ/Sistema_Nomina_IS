package com.api.nomina.controlador;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.api.nomina.datos.DEmpleado;
import com.api.nomina.modelo.EEmpleado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/empleado")
public class resEmpleado {

    @Autowired
    private DEmpleado dempleado;

    @PostMapping("/crear")
    public ResponseEntity<?> crearEmpleado(@RequestBody EEmpleado empleado) {
        try {
            dempleado.crearEmpleado(
                null,
                empleado.getNombres(),
                empleado.getDpi(),
                empleado.getNit(),
                empleado.getFechaIngreso(),
                empleado.getFechaEgreso(),
                empleado.getEstado(),
                empleado.getTipoPago(),
                empleado.getTipoContrato(),
                empleado.getIdPuesto(),
                empleado.getIdDepartamento()
            );
            return ResponseEntity.ok(empleado);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear empleado");
        }
    }

    @PutMapping("/reemplazar/{id}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable Integer id, @RequestBody EEmpleado empleado) {
        try {
            if (!id.equals(empleado.getIdEmpleado())) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "El ID en la URL no coincide con el del cuerpo."));
            }

            String estado = empleado.getEstado();
            if (estado != null && !Arrays.asList("Activo", "Inactivo", "Suspendido", "Despedido", "Renunciado")
                                 .contains(estado.trim())) {
                return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "Estado inválido."));
            }


            dempleado.actualizarEmpleado(
                id,
                empleado.getNombres(),
                empleado.getDpi(),
                empleado.getNit(),
                empleado.getFechaIngreso(),
                empleado.getFechaEgreso(),
                estado,
                empleado.getTipoPago(),
                empleado.getTipoContrato(),
                empleado.getIdPuesto(),
                empleado.getIdDepartamento()
            );

            return ResponseEntity.ok(empleado);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar empleado");
        }
    }

    @PatchMapping("/actualizarParcial/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> campos) {
        try {
            Map<String, Object> actualizados = dempleado.actualizarParcial(id, campos);
            return ResponseEntity.ok(actualizados);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error al actualizar parcialmente."));
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable Integer id) {
        try {
            dempleado.eliminarEmpleado(id);
            return ResponseEntity.ok("Empleado eliminado correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el empleado.");
        }
    }

    @GetMapping("/obtenerTodos")
    public List<EEmpleado> listarEmpleados() {
        try {
            return dempleado.listarEmpleados();
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<?> obtenerEmpleadoPorId(@PathVariable Integer id) {
        try {
            EEmpleado empleado = dempleado.buscarEmpleadoPorId(id);
            if (empleado != null) {
                return ResponseEntity.ok(empleado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el empleado.");
        }
    }
    
    @GetMapping("/conPuestos")
    public ResponseEntity<?> obtenerEmpleadosConPuestos() {
        try {
            System.out.println("Endpoint /conPuestos fue llamado"); // Log de depuración
            List<Map<String, Object>> empleados = dempleado.obtenerEmpleadosConPuestos();
            System.out.println("Datos obtenidos: " + empleados); // Ver datos
            return ResponseEntity.ok(empleados);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", ex.getMessage()));
        }
    }
}

