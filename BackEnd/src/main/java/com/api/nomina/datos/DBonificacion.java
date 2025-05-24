package com.api.nomina.datos;

import com.api.nomina.modelo.EBonificacion;
import com.api.nomina.repositorio.BonificacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Service
public class DBonificacion {

    @Autowired
    private BonificacionRepositorio bonificacionRepositorio;

    public void crearParametro(BigDecimal montoFijo, Date vigenteDesde, Date vigenteHasta, String estado) {
        bonificacionRepositorio.spGestionBonificacion("CREATE", null, montoFijo, vigenteDesde, vigenteHasta, estado);
    }

    public void actualizarParametro(Integer idParametro, BigDecimal montoFijo, Date vigenteDesde, Date vigenteHasta, String estado) {
        bonificacionRepositorio.spGestionBonificacion("UPDATE", idParametro, montoFijo, vigenteDesde, vigenteHasta, estado);
    }

    public void eliminarParametro(Integer idParametro) {
        bonificacionRepositorio.spGestionBonificacion("DELETE", idParametro, null, null, null, null);
    }

    @Transactional(readOnly = true)
    public List<EBonificacion> listarParametros() {
        return bonificacionRepositorio.spListarBonificacion("READ", null, null, null, null, null);
    }

    @Transactional(readOnly = true)
    public EBonificacion buscarParametroPorId(Integer idParametro) {
        List<EBonificacion> resultado = bonificacionRepositorio.spListarBonificacion("READ_ID", idParametro, null, null, null, null);
        return resultado.isEmpty() ? null : resultado.get(0);
    }
}
