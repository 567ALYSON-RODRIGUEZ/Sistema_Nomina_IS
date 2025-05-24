package com.api.nomina.datos;

import com.api.nomina.modelo.EFiniquito;
import com.api.nomina.modelo.EPlanillaAguinaldo;
import com.api.nomina.repositorio.FiniquitoRepositorio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DFiniquito {

    @Autowired
    private FiniquitoRepositorio repo;

    @Transactional
    public void generarFiniquito(int idEmpleado) {
        repo.generarFiniquito(idEmpleado);
    }
    
    @Transactional
    // Ejecutar el SP para generar o pagar la planilla
    public void generarPlanillaAguinaldo(Integer id_empleado) {
        repo.generarFiniquito(id_empleado);
    }

    // Consultar todas las planillas generadas por año
    @Transactional(readOnly = true)
    public List<EFiniquito> listarPorAnio(int id_empleado) {
        return repo.findAllById(id_empleado);
    }
}
