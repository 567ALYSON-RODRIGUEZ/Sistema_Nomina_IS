package com.api.nomina.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.nomina.datos.DPuestos;
import com.api.nomina.modelo.EPuesto;

@CrossOrigin(origins = "http://localhost:5255")
@RestController
@RequestMapping("/puesto")
public class restControllerPuesto {

	@Autowired
	private DPuestos dpuesto;
	
	//url: http://localhost:8095/puesto/crear
	@PostMapping("/crear")
	public EPuesto crearPuesto(@RequestBody EPuesto puesto) {
	    System.out.println("Entra al método crear Puesto");
	    
	    try {
	    	dpuesto.crearPuesto(
	                null, //id que es automatico  
	                puesto.getNombre(),
	                puesto.getSalario_base(),
	                puesto.getNivel_jerarquico(),
	                puesto.getEstado_puesto()
	            );
	        System.out.print("Se ha agregado correctamente el puesto.");  
	    }catch(Exception ex) {
	        ex.printStackTrace();
	        System.out.print("No se pudo agregar el puesto.");
	    }
	    return puesto;
	}
	
	//url: http://localhost:8095/puesto/obtenerTodos
	@GetMapping("/obtenerTodos")
	public List<EPuesto> obtenerPuestos() {
		
		try {
			List<EPuesto> listado = dpuesto.mostrarTodoPuesto();
			System.out.println("Datos obtenidos correctamente.");
			return listado;
			
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("No se han encontrado datos para mostrar.");
			return List.of();
		}
	}
	
	//url: http://localhost:8095/puesto/obtenerPorId/id
	@GetMapping("/obtenerPorId/{id}")
	public EPuesto obtenerPuestoPorId(@PathVariable("id") Integer id) {
			
		try {
			EPuesto epuesto = dpuesto.mostrarPuesto(id);
			System.out.println("Datos obtenidos correctamente para el Id." + id);
			return epuesto;
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("No se han encontrado datos para mostrar.");
		}
		return null;
	}
	
	//url: http://localhost:8095/puesto/actualizar
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<EPuesto> actualizarPuesto(@PathVariable Integer id, @RequestBody EPuesto puesto) {
	    System.out.println("Entra al método actualizacion Puesto");
	   
	    try {
	    	if(!id.equals(puesto.getId_puesto())) {
	    		return ResponseEntity.badRequest().body(null);
	    	}
	    	
	        dpuesto.actualizarPuesto(
	        		id,
	        		puesto.getNombre(),
	                puesto.getSalario_base(),
	                puesto.getNivel_jerarquico(),
	                puesto.getEstado_puesto()
	            );
	        System.out.print("Se ha actualizado correctamente el puesto.");  
	        return ResponseEntity.ok(puesto);
	    }catch(Exception ex) {
	        ex.printStackTrace();
	        System.out.print("No se pudo actualizar el departamento.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }  
	}
	
	//url: http://localhost:8095/puesto/eliminar/id
		@PatchMapping("/eliminar/{id}")
		public void eliminar(@PathVariable("id") Integer id) {
				
			try {
				//si el id ingresado existe eliminarlo
				dpuesto.eliminarPuesto(id);
				System.out.println("Puesto eliminado correctamente");
			}catch(Exception ex) {
				ex.printStackTrace();
				System.out.println("El puesto que desea eliminar no existe..");
			}
		}
}
