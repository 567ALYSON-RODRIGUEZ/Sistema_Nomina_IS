package com.api.nomina.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.nomina.datos.DDepartamentos;
import com.api.nomina.modelo.EDepartamento;

@CrossOrigin(origins = "http://localhost:5255")
@RestController
@RequestMapping("/departamento")
public class restControllerDepto {
	
	@Autowired
	private DDepartamentos ddepto;
	
	//url: http://localhost:8095/departamento/crear
	@PostMapping("/crear")
	public EDepartamento crearDepartamento(@RequestBody EDepartamento depto) {
	    System.out.println("Entra al método crearDepartamento");
	    
	    try {
	        ddepto.crearDepartamento(
	                null, //id que es automatico  
	                depto.getNombre(),
	                depto.getDescripcion(),
	                depto.getEstado()
	            );
	        System.out.print("Se ha agregado correctamente el departamento.");  
	    }catch(Exception ex) {
	        ex.printStackTrace();
	        System.out.print("No se pudo agregar el departamento.");
	    }
	    return depto;
	}
	
	//url: http://localhost:8095/departamento/obtenerTodos
	@GetMapping("/obtenerTodos")
	public List<EDepartamento> obtenerDepartamentos() {
		
		try {
			List<EDepartamento> listado = ddepto.mostrarTodoDepartamento();
			System.out.println("Datos obtenidos correctamente.");
			return listado;
			
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("No se han encontrado datos para mostrar.");
			return List.of();
		}
	}
	
	//url: http://localhost:8095/departamento/obtenerPorId/id
	@GetMapping("/obtenerPorId/{id}")
	public EDepartamento obtenerDepartamentoPorId(@PathVariable("id") Integer id) {
			
		try {
			EDepartamento edepto = ddepto.mostrarDepartamento(id);
			System.out.println("Datos obtenidos correctamente para el Id." + id);
			return edepto;
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("No se han encontrado datos para mostrar.");
		}
		return null;
	}
	
	//url: http://localhost:8095/departamento/actualizar
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<EDepartamento> actualizarDepartamento(@PathVariable Integer id, @RequestBody EDepartamento depto) {
	    System.out.println("Entra al método actualizacion Departamento");
	   
	    try {
	    	if(!id.equals(depto.getId_departamento())) {
	    		return ResponseEntity.badRequest().body(null);
	    	}
	    	
	        ddepto.actualizarDepartamento(
	        		id,
	                depto.getNombre(),
	                depto.getDescripcion(),
	                depto.getEstado()
	            );
	        System.out.print("Se ha actualizado correctamente el departamento.");  
	        return ResponseEntity.ok(depto);
	    }catch(Exception ex) {
	        ex.printStackTrace();
	        System.out.print("No se pudo actualizar el departamento.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }  
	}
	
	//url: http://localhost:8095/departamento/eliminar/id
		@PatchMapping("/eliminar/{id}")
		public void eliminar(@PathVariable("id") Integer id) {
				
			try {
				//si el id ingresado existe eliminarlo
				ddepto.eliminarDepartamento(id);
				System.out.println("Departamento eliminado correctamente");
			}catch(Exception ex) {
				ex.printStackTrace();
				System.out.println("El departamento que desea eliminar no existe..");
			}
		}
		
}
