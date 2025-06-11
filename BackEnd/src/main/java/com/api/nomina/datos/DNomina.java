package com.api.nomina.datos;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.api.nomina.modelo.ENomina;
import com.api.nomina.repositorio.NominaRepositorio;

@Service
public class DNomina {

    @Autowired
    private NominaRepositorio nominaRepositorio;

    public void crearNomina(String tipo, Date periodoInicio, Date periodoFin, Date fechaGeneracion, String estado, Integer idPeriodo) {
        nominaRepositorio.spGestionNomina("CREATE", null, tipo, periodoInicio, periodoFin, fechaGeneracion, estado, idPeriodo);
    }

    public void actualizarNomina(Integer idNomina, String tipo, Date periodoInicio, Date periodoFin, Date fechaGeneracion, String estado, Integer idPeriodo) {
        nominaRepositorio.spGestionNomina("UPDATE", idNomina, tipo, periodoInicio, periodoFin, fechaGeneracion, estado, idPeriodo);
    }

    public void eliminarNomina(Integer idNomina) {
        nominaRepositorio.spGestionNomina("DELETE", idNomina, null, null, null, null, null, null);
    }

    @Transactional(readOnly = true)
    public List<ENomina> listarNominas() {
        return nominaRepositorio.spNominaListar("READ", null, null, null, null, null, null, null);
    }

    @Transactional(readOnly = true)
    public ENomina buscarNominaPorId(Integer idNomina) {
        List<ENomina> resultado = nominaRepositorio.spNominaListar("READ_ID", idNomina, null, null, null, null, null, null);
        return resultado.isEmpty() ? null : resultado.get(0);
    }
}

