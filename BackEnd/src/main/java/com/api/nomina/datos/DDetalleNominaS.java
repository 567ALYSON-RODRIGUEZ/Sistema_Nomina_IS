package com.api.nomina.datos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EDetalleNominaS;
import com.api.nomina.repositorio.DetalleNominaSRepositorio;

@Service
public class DDetalleNominaS {

	@Autowired
    private DetalleNominaSRepositorio detalleNominaRepositorio;

    public void generarNomina(Integer idPeriodo) {
        detalleNominaRepositorio.generarNominaSemanal("GENERAR", idPeriodo);
    }

    public void finalizarPeriodo(Integer idPeriodo) {
        detalleNominaRepositorio.generarNominaSemanal("FINALIZAR", idPeriodo);
    }

    @Transactional(readOnly = true)
    public List<EDetalleNominaS> obtenerDetalle(Integer idPeriodo) {
        String tipoPeriodo = detalleNominaRepositorio.obtenerTipoPeriodo(idPeriodo);
        if (!"Semanal".equalsIgnoreCase(tipoPeriodo)) {
            throw new IllegalArgumentException("El tipo de período no es válido. Debe ser 'Semanal'.");
        }
        return detalleNominaRepositorio.obtenerDetalle(idPeriodo);
    }

    @Transactional(readOnly = true)
    public List<DDetalleNominadto> obtenerResumen(Integer idPeriodo) {
        String tipoPeriodo = detalleNominaRepositorio.obtenerTipoPeriodo(idPeriodo);
        if (!"Semanal".equalsIgnoreCase(tipoPeriodo)) {
            throw new IllegalArgumentException("El tipo de período no es válido. Debe ser 'Semanal'.");
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