package com.api.nomina.datos;

import com.api.nomina.modelo.EBono14;
import com.api.nomina.modelo.EPlanillaAguinaldo;
import com.api.nomina.repositorio.Bono14Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DBono14 {

	@Autowired
	private final Bono14Repositorio repo;

    DBono14(Bono14Repositorio repo) {
        this.repo = repo;
    }
    
    @Transactional
    // Ejecutar el SP para generar o pagar la planilla
    public void generarPlanillaBono14(Integer anio, String accion, Integer idPlanilla) {
        repo.generarPlanillaBono14(anio, accion, idPlanilla);
    }

    // Consultar todas las planillas generadas por año
    @Transactional(readOnly = true)
    public List<EBono14> listarPorAnio(int anio) {
        return repo.findByAnio(anio);
    }
}
