package com.api.nomina.datos;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.api.nomina.modelo.EPeriodosNomina;
import com.api.nomina.repositorio.PeriodosRepositorio;

@Service
public class DPeriodosNomina {
	
	@Autowired
    private PeriodosRepositorio periodosRepositorio;

    public void crearPeriodo(String tipo, String descripcion, Date inicio, Date fin, Integer dias, Integer numeroPago, String codigoPago, String estado) {
        periodosRepositorio.spGestionPeriodos("C", null, tipo, descripcion, inicio, fin, dias, numeroPago, codigoPago, estado);
    }

    @Transactional(readOnly = true)
    public List<EPeriodosNomina> mostrarTodosPeriodos() {
        return periodosRepositorio.listarPeriodos("R", null, null, null, null, null, null, null, null, null);
    }

    @Transactional(readOnly = true)
    public EPeriodosNomina mostrarPeriodoPorId(Integer id) {
        List<EPeriodosNomina> resultado = periodosRepositorio.listarPeriodos("R1", id, null, null, null, null, null, null, null, null);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public void actualizarPeriodo(Integer id, String tipo, String descripcion, Date inicio, Date fin, Integer dias, Integer numeroPago, String codigoPago, String estado) {
        periodosRepositorio.spGestionPeriodos("U", id, tipo, descripcion, inicio, fin, dias, numeroPago, codigoPago, estado);
    }

    public void cerrarPeriodo(Integer id) {
        periodosRepositorio.spGestionPeriodos("D", id, null, null, null, null, null, null, null, null);
    }
}

