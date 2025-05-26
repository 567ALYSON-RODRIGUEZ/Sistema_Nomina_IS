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
    public String generarFiniquito(int idEmpleado) {
        return repo.generarFiniquito(idEmpleado);
    }

}
