package com.api.nomina.datos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EDetalleNominaQ;
import com.api.nomina.repositorio.DetalleNominaQRepositorio;

@Service
public class DDetalleNominaQ {

	@Autowired
    private DetalleNominaQRepositorio detalleNominaRepositorio;

    public void generarNomina(Integer idPeriodo) {
        detalleNominaRepositorio.generarNominaQuincenal("GENERAR", idPeriodo);
    }

    public void finalizarPeriodo(Integer idPeriodo) {
        detalleNominaRepositorio.generarNominaQuincenal("FINALIZAR", idPeriodo);
    }

    @Transactional(readOnly = true)
    public List<EDetalleNominaQ> obtenerDetalle(Integer idPeriodo) {
        String tipoPeriodo = detalleNominaRepositorio.obtenerTipoPeriodo(idPeriodo);
        if (!"Quincenal".equalsIgnoreCase(tipoPeriodo)) {
            throw new IllegalArgumentException("El tipo de período no es válido. Debe ser 'Quincenal'.");
        }
        return detalleNominaRepositorio.obtenerDetalle(idPeriodo);
    }

    @Transactional(readOnly = true)
    public List<DDetalleNominadto> obtenerResumen(Integer idPeriodo) {
        String tipoPeriodo = detalleNominaRepositorio.obtenerTipoPeriodo(idPeriodo);
        if (!"Quincenal".equalsIgnoreCase(tipoPeriodo)) {
            throw new IllegalArgumentException("El tipo de período no es válido. Debe ser 'Quincenal'.");
        }

        List<Object[]> resultados = detalleNominaRepositorio.obtenerResumen(idPeriodo);
        return resultados.stream().map(r -> new DDetalleNominadto(
            (Integer) r[0],
            (String) r[1],
            (String) r[2],
            ((Number) r[3]).doubleValue(),
            ((Number) r[4]).doubleValue(),
            ((Number) r[5]).doubleValue()
        )).toList();
    }
    
    @Transactional(readOnly = true)
    public List<DDetalleNominadto> obtenerTodosLosResumenes() {
        List<Object[]> resultados = detalleNominaRepositorio.obtenerTodosLosResumenes();

        return resultados.stream().map(r -> new DDetalleNominadto(
            (Integer) r[0],
            (String) r[1],
            (String) r[2],
            ((Number) r[3]).doubleValue(),
            ((Number) r[4]).doubleValue(),
            ((Number) r[5]).doubleValue()
        )).toList();
    }
}