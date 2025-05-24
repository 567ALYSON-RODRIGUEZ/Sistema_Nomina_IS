package com.api.nomina.datos;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EParametroISR;
import com.api.nomina.repositorio.IsrRepositorio;

@Service
public class DParametroISR {

    @Autowired
    private IsrRepositorio IsrRepositorio;

    public void crearParametroIsr(EParametroISR parametro) {
        IsrRepositorio.spGestionParametroIsr("C",
            null,
            parametro.getRangoMin(),
            parametro.getRangoMax(),
            parametro.getCuotaFija(),
            parametro.getPorcentaje(),
            parametro.getVigenteDesde(),
            parametro.getVigenteHasta(),
            parametro.getTipoPeriodo()
        );
    }
    
    @Transactional(readOnly = true)
    public List<EParametroISR> mostrarTodos() {
        return IsrRepositorio.listarTodos("R", null, null, null, null, null, null, null, null);
    }

    @Transactional(readOnly = true)
    public EParametroISR mostrarPorId(Integer id) {
        List<EParametroISR> resultado = IsrRepositorio.listarTodos("R1", id, null, null, null, null, null, null, null);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public void actualizarParametroIsr(Integer id, EParametroISR parametro) {
        IsrRepositorio.spGestionParametroIsr("U",
            id,
            parametro.getRangoMin(),
            parametro.getRangoMax(),
            parametro.getCuotaFija(),
            parametro.getPorcentaje(),
            parametro.getVigenteDesde(),
            parametro.getVigenteHasta(),
            parametro.getTipoPeriodo()
        );
    }

    public void eliminarParametroIsr(Integer id) {
        IsrRepositorio.spGestionParametroIsr("D", id, null, null, null, null, null, null, null);
    }
}
