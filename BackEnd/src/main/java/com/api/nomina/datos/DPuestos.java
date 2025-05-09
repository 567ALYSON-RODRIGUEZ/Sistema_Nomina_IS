package com.api.nomina.datos;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EPuesto;
import com.api.nomina.repositorio.DepartamentosRepositorio;
import com.api.nomina.repositorio.PuestosRepositorio;

@Service
public class DPuestos {
	
	@Autowired
    private PuestosRepositorio puestosRepository;

    public void crearPuesto(Integer id, String nombre, BigDecimal base, String nivel, String estado) {
    	puestosRepository.spGestionPuestos("C", id, nombre, base, nivel, estado);
    }
    
    @Transactional(readOnly = true)
    public List<EPuesto> mostrarTodoPuesto() {
        return puestosRepository.listarTodos("R", null, null, null, null, null);
    }

    @Transactional(readOnly = true)
    public EPuesto mostrarPuesto(Integer id) {
        List<EPuesto> resultado = puestosRepository.listarTodos("R1", id, null, null, null, null);
        return resultado.isEmpty() ? null : resultado.get(0);  //Devolver el primero que coincida
    }

    public void actualizarPuesto(Integer id, String nombre, BigDecimal base, String nivel, String estado) {
    	puestosRepository.spGestionPuestos("U", id, nombre, base, nivel, estado);
    }

    public void eliminarPuesto(Integer id) {
    	puestosRepository.spGestionPuestos("D", id, null, null, null, null);
    }

}
