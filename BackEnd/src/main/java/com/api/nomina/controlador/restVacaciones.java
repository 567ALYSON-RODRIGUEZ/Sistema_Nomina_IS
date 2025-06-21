package com.api.nomina.controlador;

import com.api.nomina.datos.DVacaciones;
import com.api.nomina.datos.DVacacionesdto;
import com.api.nomina.modelo.EVacaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vacaciones")
public class restVacaciones {

    @Autowired
    private DVacaciones dvacaciones;

    // Endpoint para obtener días disponibles
    @GetMapping("/diasDisponibles/{idEmpleado}/{anio}")
    public ResponseEntity<?> obtenerDiasDisponibles(
            @PathVariable Integer idEmpleado,
            @PathVariable Integer anio) {
        try {
            Map<String, Integer> result = dvacaciones.obtenerDiasDisponibles(idEmpleado, anio);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearVacacion(@RequestBody EVacaciones vacacion) {
        try {
            // Primero validar días disponibles
            Integer anio = vacacion.getFecha_inicio().getYear() + 1900; // Ajuste para Java Date
            Map<String, Integer> disponibilidad = dvacaciones.obtenerDiasDisponibles(
                    vacacion.getId_empleado(), 
                    anio);
            
            int diasDisponibles = disponibilidad.get("diasDisponibles");
            
            if (vacacion.getDias() > diasDisponibles) {
                String mensaje = diasDisponibles <= 0 ? 
                        "El empleado no tiene días disponibles para este periodo" :
                        String.format("Solo hay %d días disponibles", diasDisponibles);
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("error", mensaje));
            }
            
            // Si hay días disponibles, proceder con la creación
            dvacaciones.insertarVacacion(
                    vacacion.getId_empleado(),
                    vacacion.getFecha_inicio(),
                    vacacion.getFecha_fin(),
                    vacacion.getDias(),
                    vacacion.getEstado(),
                    anio  // Agregar el año como parámetro
            );
            
            // Devolver los días restantes
            int diasRestantes = diasDisponibles - vacacion.getDias();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("diasRestantes", diasRestantes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // Nuevo endpoint para obtener vacaciones por empleado y año
    @GetMapping("/porEmpleadoAnio/{idEmpleado}/{anio}")
    public ResponseEntity<?> obtenerPorEmpleadoAnio(
            @PathVariable Integer idEmpleado,
            @PathVariable Integer anio) {
        try {
            List<DVacacionesdto> lista = dvacaciones.listarPorEmpleadoAnio(idEmpleado, anio);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarVacacion(@PathVariable Integer id, @RequestBody EVacaciones vacacion) {
        try {
            // Validaciones iniciales
            if (vacacion == null) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("error", "Datos de vacación no proporcionados"));
            }
            
            if (!id.equals(vacacion.getId_vacacion())) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("error", "El ID en la URL no coincide con el del cuerpo."));
            }

            // Obtener la vacación actual
            EVacaciones vacacionActual = dvacaciones.obtenerPorId(id);
            if (vacacionActual == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Vacación no encontrada"));
            }

            Integer anio = vacacionActual.getFecha_inicio().getYear() + 1900;
            
            // Validar días si cambian o si se aprueba
            if (!Objects.equals(vacacion.getDias(), vacacionActual.getDias()) || 
                ("Aprobado".equals(vacacion.getEstado()) && !"Aprobado".equals(vacacionActual.getEstado()))) {
                
                Map<String, Integer> disponibilidad = dvacaciones.obtenerDiasDisponibles(
                        vacacion.getId_empleado(), 
                        anio);
                
                int diasDisponibles = disponibilidad.get("diasDisponibles");
                
                // Ajustar días si la vacación actual estaba aprobada
                if ("Aprobado".equals(vacacionActual.getEstado())) {
                    diasDisponibles += vacacionActual.getDias();
                }
                
                if (vacacion.getDias() > diasDisponibles) {
                    return ResponseEntity.badRequest()
                            .body(Collections.singletonMap("error", 
                                    "No hay suficientes días disponibles para esta modificación. Días disponibles: " + diasDisponibles));
                }
            }

            // Actualizar la vacación
            dvacaciones.actualizarVacacion(
                    id,
                    vacacion.getId_empleado(),
                    vacacion.getFecha_inicio(),
                    vacacion.getFecha_fin(),
                    vacacion.getDias(),
                    vacacion.getEstado(),
                    anio  // Usar el año calculado
            );
            
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Vacación actualizada correctamente"));
        } catch (Exception e) {
            // Log del error completo
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error al actualizar vacación: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarVacacion(@PathVariable Integer id) {
        try {
            dvacaciones.eliminarVacacion(id);
            return ResponseEntity.ok("Vacación eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/obtenerTodos")
    public ResponseEntity<?> obtenerTodas() {
        try {
            List<DVacacionesdto> lista = dvacaciones.listarConNombrePuesto();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            DVacacionesdto vacacion = dvacaciones.obtenerDetallePorId(id);
            if (vacacion != null) {
                return ResponseEntity.ok(vacacion);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Vacación no encontrada"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}