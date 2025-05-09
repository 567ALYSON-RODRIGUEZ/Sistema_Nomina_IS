package com.api.nomina.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.nomina.datos.DRoles;
import com.api.nomina.modelo.ERol;

@CrossOrigin(origins = "http://localhost:5255")
@RestController
@RequestMapping("/rol")
public class restControllerRol { 
	
	@Autowired
    private DRoles drol;

    // URL: http://localhost:8095/rol/crear
    @PostMapping("/crear")
    public ERol crearRol(@RequestBody ERol rol) {
        try {
            drol.crearRol(
                null, // ID autogenerado
                rol.getNombre_rol(),
                rol.getDescripcion()
            );
            System.out.println("Rol creado correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("No se pudo crear el rol.");
        }
        return rol;
    }

    // URL: http://localhost:8095/rol/obtenerPorId/{id}
    @GetMapping("/obtenerPorId/{id}")
    public ERol obtenerRolPorId(@PathVariable("id") Integer id) {
        try {
            ERol erol = drol.mostrarRol(id);
            System.out.println("Rol obtenido correctamente con ID: " + id);
            return erol;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("No se encontró el rol con ID: " + id);
            return null;
        }
    }
	
	 // URL: http://localhost:8095/rol/obtenerTodos
	    @GetMapping("/obtenerTodos")
	    public List<ERol> obtenerTodosLosRoles() {
	        try {
	            List<ERol> lista = drol.mostrarTodoRol();
	            System.out.println("Roles obtenidos correctamente.");
	            return lista;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            System.out.println("Error al obtener los roles.");
	            return List.of();
	        }
	    }
}
