package com.api.nomina.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.api.nomina.datos.DUsuarioRol;
import com.api.nomina.modelo.EUsuarioRol;

@CrossOrigin(origins = "http://localhost:5255")
@RestController
@RequestMapping("/usuarioRol")
public class restControllerUsuarioRol {

    @Autowired
    private DUsuarioRol dusuarioRol;

    // URL: http://localhost:8095/usuarioRol/asignar
    @PostMapping("/asignar")
    public String asignarRol(@RequestParam Integer idUsuario, @RequestParam Integer idRol) {
        try {
            dusuarioRol.asignarRol(idUsuario, idRol);
            return "Rol asignado correctamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al asignar el rol.";
        }
    }

    // URL: http://localhost:8095/usuarioRol/eliminar
    @PostMapping("/eliminar")
    public String eliminarRol(@RequestParam Integer idUsuario, @RequestParam Integer idRol) {
        try {
            dusuarioRol.eliminarRol(idUsuario, idRol);
            return "Rol eliminado correctamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar el rol.";
        }
    }

    // URL: http://localhost:8095/usuarioRol/obtenerTodos
    @GetMapping("/obtenerTodos")
    public List<EUsuarioRol> obtenerTodos() {
        try {
            return dusuarioRol.obtenerTodos();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // URL: http://localhost:8095/usuarioRol/uno/{idUsuario}/{idRol}
    @GetMapping("/uno/{idUsuario}/{idRol}")
    public EUsuarioRol obtenerUno(@PathVariable Integer idUsuario, @PathVariable Integer idRol) {
        try {
            return dusuarioRol.obtenerUno(idUsuario, idRol);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
