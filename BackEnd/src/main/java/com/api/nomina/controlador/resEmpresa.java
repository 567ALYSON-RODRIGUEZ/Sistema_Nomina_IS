package com.api.nomina.controlador;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.nomina.datos.DEmpresa;
import com.api.nomina.modelo.EEmpresa; 

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/empresa")
public class resEmpresa {
    
    @Autowired
    private DEmpresa dempresa; 
    
    // URL: http://localhost:8080/empresa/crear
    @PostMapping("/crear")
    public EEmpresa crearEmpresa(@RequestBody EEmpresa empresa) {
        System.out.println(">>> Entró al método crearEmpresa");

        try {
            dempresa.crearEmpresa(
                null,
                empresa.getNombreLegal(),
                empresa.getSigla(),
                empresa.getNit(),
                empresa.getDomicilioFiscal(),
                empresa.getCorreo(),
                empresa.getTelefono(),
                empresa.getEstado()
            );
            System.out.println("Empresa agregada correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("No se pudo agregar la empresa.");
        }

        return empresa;
    }

 // Reemplazar Empresa por id
    @PutMapping("/reemplazar/{id}")
    public ResponseEntity<?> actualizarEmpresa(@PathVariable Integer id, @RequestBody EEmpresa empresa) {
        System.out.println(">>> Entró al método actualizarEmpresa");

        try {
            if (!id.equals(empresa.getIdEmpresa())) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("error", "El ID en la URL no coincide con el ID del cuerpo."));
            }

            String estado = empresa.getEstado();
            if (estado != null && !estado.equalsIgnoreCase("Activo") && !estado.equalsIgnoreCase("Eliminado")) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("error", "El estado solo puede ser 'Activo' o 'Eliminado'."));
            }

            dempresa.reemplazarEmpresa(
                    id,
                    empresa.getNombreLegal(),
                    empresa.getSigla(),
                    empresa.getNit(),
                    empresa.getDomicilioFiscal(),
                    empresa.getCorreo(),
                    empresa.getTelefono(),
                    estado
            );

            System.out.println("Empresa actualizada.");
            return ResponseEntity.ok(empresa);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("No se pudo actualizar la empresa.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error interno al actualizar la empresa."));
        }
    }


    
    @PatchMapping("/actualizarParcial/{id}")
    public ResponseEntity<Map<String, Object>> actualizarEmpresaParcial(@PathVariable Integer id, @RequestBody Map<String, Object> campos) {
        try {
            // Validar el campo 'estado' antes de proceder
            String estado = (String) campos.get("estado");
            if (estado != null && !estado.equals("Activo") && !estado.equals("Eliminado")) {
                // Si el estado no es válido, devolver un error con un mensaje claro
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Estado no válido. Solo se permiten 'Activo' o 'Eliminado'."));
            }

            // Llamamos al servicio que retorna los campos actualizados
            Map<String, Object> camposActualizados = dempresa.actualizarParcial(id, campos);

            // Imprimir los campos actualizados en la consola
            if (camposActualizados != null && !camposActualizados.isEmpty()) {
                System.out.println("Campos actualizados");
            } else {
                System.out.println("No se han actualizado campos.");
            }

            // Devolvemos los campos actualizados en el cuerpo de la respuesta
            return ResponseEntity.ok(camposActualizados);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error al actualizar parcialmente."));
        }
    }




    // Eliminar Empresa
    @DeleteMapping("/eliminar/{id}")
    public String eliminarEmpresa(@PathVariable Integer id) {
        System.out.println(">>> Entró al método eliminarEmpresa");

        try {
            dempresa.eliminarEmpresa(id);
            System.out.println("Empresa eliminada.");
            return "Empresa eliminada correctamente.";
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("No se pudo eliminar la empresa.");
            return "Error al eliminar la empresa.";
        }
    }
    
    
    @GetMapping("/obtenerTodos")
    public List<EEmpresa> obtenerEmpresas() {
        try {
            List<EEmpresa> listado = dempresa.listarEmpresas();
            System.out.println("Empresas obtenidas correctamente.");
            return listado;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("No se han encontrado empresas para mostrar.");
            return List.of();
        }
    }


    
 // URL: http://localhost:8095/empresa/obtenerPorId/1
    @GetMapping("/obtenerPorId/{id}")
    public EEmpresa obtenerEmpresaPorId(@PathVariable("id") Integer id) {
        try {
            EEmpresa empresa = dempresa.buscarEmpresaPorId(id); 
            if (empresa != null) {
                System.out.println("Empresa obtenida correctamente para el ID: " + id);
                return empresa;
            } else {
                System.out.println("No se encontró la empresa con ID: " + id);
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error al buscar empresa con ID: " + id);
            return null;
        }
    }


}