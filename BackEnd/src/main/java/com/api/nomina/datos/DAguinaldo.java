package com.api.nomina.datos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EPlanillaAguinaldo;
import com.api.nomina.repositorio.AguinaldoRepositorio;

@Service
public class DAguinaldo {

    @Autowired
    private AguinaldoRepositorio planillaRepo;

    @Transactional
    // Ejecutar el SP para generar o pagar la planilla
    public void generarPlanillaAguinaldo(Integer anio, Integer porcentajePago, String accion, Integer idPlanilla) {
        planillaRepo.generarPlanillaAguinaldo(anio, porcentajePago, accion, idPlanilla);
    }

    // Consultar todas las planillas generadas por año
    @Transactional(readOnly = true)
    public List<EPlanillaAguinaldo> listarPorAnio(int anio) {
        return planillaRepo.findByAnio(anio);
    }
}
