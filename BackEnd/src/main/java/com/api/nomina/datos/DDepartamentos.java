package com.api.nomina.datos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EDepartamento;
import com.api.nomina.repositorio.DepartamentosRepositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class DDepartamentos {
	
	@Autowired
    private DepartamentosRepositorio departamentoRepository;

    public void crearDepartamento(Integer id, String nombre, String descripcion, String estado) {
        departamentoRepository.spGestionDeptos("C", id, nombre, descripcion, estado);
    }
    
    @Transactional(readOnly = true)
    public List<EDepartamento> mostrarTodoDepartamento() {
        return departamentoRepository.listarTodos("R", null, null, null, null);
    }

    @Transactional(readOnly = true)
    public EDepartamento mostrarDepartamento(Integer id) {
        List<EDepartamento> resultado = departamentoRepository.listarTodos("R1", id, null, null, null);
        return resultado.isEmpty() ? null : resultado.get(0);  //Devolver el primero que coincida
    }

    public void actualizarDepartamento(Integer id, String nombre, String descripcion, String estado) {
        departamentoRepository.spGestionDeptos("U", id, nombre, descripcion, estado);
    }

    public void eliminarDepartamento(Integer id) {
        departamentoRepository.spGestionDeptos("D", id, null, null, null);
    }
}
