package com.api.nomina.datos;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EAnticipo;
import com.api.nomina.repositorio.AnticipoRepositorio;

@Service
public class DAnticipo {

    @Autowired
    private AnticipoRepositorio anticipoRepositorio;

    public void crearAnticipo(
        Integer idEmpleado,
        BigDecimal monto,
        Date fechaAnticipo,
        String estado,
        String descripcion
    ) {
        anticipoRepositorio.spGestionAnticipo(
            "CREATE", null, idEmpleado, monto, fechaAnticipo,
            estado, descripcion
        );
    }

    public void actualizarAnticipo(
        Integer idAnticipo,
        Integer idEmpleado,
        BigDecimal monto,
        Date fechaAnticipo,
        String estado,
        String descripcion
    ) {
        anticipoRepositorio.spGestionAnticipo(
            "UPDATE", idAnticipo, idEmpleado, monto, fechaAnticipo,
            estado, descripcion
        );
    }

    public void eliminarAnticipo(Integer idAnticipo) {
        anticipoRepositorio.spGestionAnticipo(
            "DELETE", idAnticipo, null, null, null, null, null
        );
    }

    @Transactional(readOnly = true)
    public List<EAnticipo> listarAnticipos() {
        return anticipoRepositorio.spAnticipoListar(
            "READ", null, null, null, null, null, null
        );
    }

    @Transactional(readOnly = true)
    public EAnticipo buscarAnticipoPorId(Integer idAnticipo) {
        List<EAnticipo> resultado = anticipoRepositorio.spAnticipoListar(
            "READ_ID", idAnticipo, null, null, null, null, null
        );
        return resultado.isEmpty() ? null : resultado.get(0);
    }
}
