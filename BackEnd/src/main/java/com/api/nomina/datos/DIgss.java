package com.api.nomina.datos;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EIgss;
import com.api.nomina.repositorio.IgssRepositorio;

@Service
public class DIgss {

    @Autowired
    private IgssRepositorio igssRepositorio;

    public void crearParametroIGSS(
        BigDecimal porcentaje,
        String tipo,
        String descripcion,
        Date vigenteDesde,
        Date vigenteHasta,
        String estado
    ) {
        igssRepositorio.spGestionParametroIGSS(
            "CREATE", null, porcentaje, tipo, descripcion, vigenteDesde, vigenteHasta, estado
        );
    }

    public void actualizarParametroIGSS(
        Integer idParametro,
        BigDecimal porcentaje,
        String tipo,
        String descripcion,
        Date vigenteDesde,
        Date vigenteHasta,
        String estado
    ) {
        igssRepositorio.spGestionParametroIGSS(
            "UPDATE", idParametro, porcentaje, tipo, descripcion, vigenteDesde, vigenteHasta, estado
        );
    }

    public void eliminarParametroIGSS(Integer idParametro) {
        igssRepositorio.spGestionParametroIGSS(
            "DELETE", idParametro, null, null, null, null, null, null
        );
    }

    @Transactional(readOnly = true)
    public List<EIgss> listarParametrosIGSS() {
        return igssRepositorio.spParametroIGSSListar(
            "READ", null, null, null, null, null, null, null
        );
    }

    @Transactional(readOnly = true)
    public EIgss buscarParametroIGSSPorId(Integer idParametro) {
        List<EIgss> resultado = igssRepositorio.spParametroIGSSListar(
            "READ_ID", idParametro, null, null, null, null, null, null
        );
        return resultado.isEmpty() ? null : resultado.get(0);
    }
}
