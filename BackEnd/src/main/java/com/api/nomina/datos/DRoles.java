package com.api.nomina.datos;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EPuesto;
import com.api.nomina.modelo.ERol;
import com.api.nomina.repositorio.RolesRepositorio;

@Service
public class DRoles {
	
	@Autowired
    private RolesRepositorio rolesRepository;

    public void crearRol(Integer id, String nombre, String descripcion) {
    	rolesRepository.spGestionRoles("C", id, nombre, descripcion);
    }
    
    @Transactional(readOnly = true)
    public List<ERol> mostrarTodoRol() {
        return rolesRepository.listarTodos("R", null, null, null);
    }
    
    @Transactional(readOnly = true)
    public ERol mostrarRol(Integer id) {
        List<ERol> resultado = rolesRepository.listarTodos("R1", id, null, null);
        return resultado.isEmpty() ? null : resultado.get(0);  //Devolver el primero que coincida
    }

}
