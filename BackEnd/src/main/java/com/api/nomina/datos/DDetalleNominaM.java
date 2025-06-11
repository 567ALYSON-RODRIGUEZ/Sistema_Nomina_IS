package com.api.nomina.datos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EDetalleNominaM;
import com.api.nomina.repositorio.DetalleNominaMRepositorio;

@Service
public class DDetalleNominaM {

	@Autowired
    private DetalleNominaMRepositorio detalleNominaRepositorio;

    public void generarNomina(Integer idPeriodo) {
        detalleNominaRepositorio.generarNominaMensual("GENERAR", idPeriodo);
    }

    public void finalizarPeriodo(Integer idPeriodo) {
        detalleNominaRepositorio.generarNominaMensual("FINALIZAR", idPeriodo);
    }

    @Transactional(readOnly = true)
    public List<EDetalleNominaM> obtenerDetalle(Integer idPeriodo) {
        String tipoPeriodo = detalleNominaRepositorio.obtenerTipoPeriodo(idPeriodo);

        if (!"Mensual".equalsIgnoreCase(tipoPeriodo)) {
            throw new IllegalArgumentException("El tipo de período no es válido. Debe ser 'Mensual'.");
        }

        return detalleNominaRepositorio.obtenerDetalle(idPeriodo);
    }

    @Transactional(readOnly = true)
    public List<DDetalleNominadto> obtenerResumen(Integer idPeriodo) {
        String tipoPeriodo = detalleNominaRepositorio.obtenerTipoPeriodo(idPeriodo);

        if (!"Mensual".equalsIgnoreCase(tipoPeriodo)) {
            throw new IllegalArgumentException("El tipo de período no es válido. Debe ser 'Mensual'.");
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