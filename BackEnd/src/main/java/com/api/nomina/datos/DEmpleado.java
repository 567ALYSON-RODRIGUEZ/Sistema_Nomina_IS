package com.api.nomina.datos;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EEmpleado;
import com.api.nomina.repositorio.EmpleadoRepositorio;

@Service
public class DEmpleado {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    public void crearEmpleado(
        Integer idEmpleado,
        String nombres,
        String dpi,
        String nit,
        Date fechaIngreso,
        Date fechaEgreso,
        String estado,
        String tipoPago,
        String tipoContrato,
        Integer idPuesto,
        Integer idDepartamento
    ) {
        empleadoRepositorio.spGestionEmpleado(
            "CREATE", idEmpleado, nombres, dpi, nit, fechaIngreso, fechaEgreso,
            estado, tipoPago, tipoContrato, idPuesto, idDepartamento
        );
    }

    public void actualizarEmpleado(
        Integer idEmpleado,
        String nombres,
        String dpi,
        String nit,
        Date fechaIngreso,
        Date fechaEgreso,
        String estado,
        String tipoPago,
        String tipoContrato,
        Integer idPuesto,
        Integer idDepartamento
    ) {
        empleadoRepositorio.spGestionEmpleado(
            "UPDATE", idEmpleado, nombres, dpi, nit, fechaIngreso, fechaEgreso,
            estado, tipoPago, tipoContrato, idPuesto, idDepartamento
        );
    }

    public void eliminarEmpleado(Integer idEmpleado) {
        empleadoRepositorio.spGestionEmpleado(
            "DELETE", idEmpleado, null, null, null, null, null,
            null, null, null, null, null
        );
    }

    @Transactional(readOnly = true)
    public List<EEmpleado> listarEmpleados() {
        return empleadoRepositorio.spEmpleadoListar(
            "READ", null, null, null, null, null,
            null, null, null, null, null, null
        );
    }

    @Transactional(readOnly = true)
    public EEmpleado buscarEmpleadoPorId(Integer idEmpleado) {
        List<EEmpleado> resultado = empleadoRepositorio.spEmpleadoListar(
            "READ_ID", idEmpleado, null, null, null, null,
            null, null, null, null, null, null
        );
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    @Transactional
    public Map<String, Object> actualizarParcial(Integer idEmpleado, Map<String, Object> campos) {
        EEmpleado actual = buscarEmpleadoPorId(idEmpleado);
        if (actual == null) {
            throw new RuntimeException("Empleado no encontrado");
        }

        Map<String, Object> cambios = new HashMap<>();

        for (Map.Entry<String, Object> entry : campos.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "nombres":
                    if (value != null && !value.equals(actual.getNombres())) {
                        cambios.put("nombres", value);
                        actual.setNombres((String) value);
                    }
                    break;
                case "dpi":
                    if (value != null && !value.equals(actual.getDpi())) {
                        cambios.put("dpi", value);
                        actual.setDpi((String) value);
                    }
                    break;
                case "nit":
                    if (value != null && !value.equals(actual.getNit())) {
                        cambios.put("nit", value);
                        actual.setNit((String) value);
                    }
                    break;
                case "fechaIngreso":
                    if (value != null && !value.equals(actual.getFechaIngreso())) {
                        cambios.put("fechaIngreso", value);
                        actual.setFechaIngreso((Date) value);
                    }
                    break;
                case "fechaEgreso":
                    if (value != null && !value.equals(actual.getFechaEgreso())) {
                        cambios.put("fechaEgreso", value);
                        actual.setFechaEgreso((Date) value);
                    }
                    break;
                case "estado":
                    if (value != null && !value.equals(actual.getEstado())) {
                        cambios.put("estado", value);
                        actual.setEstado((String) value);
                    }
                    break;
                case "tipoPago":
                    if (value != null && !value.equals(actual.getTipoPago())) {
                        cambios.put("tipoPago", value);
                        actual.setTipoPago((String) value);
                    }
                    break;
                case "tipoContrato":
                    if (value != null && !value.equals(actual.getTipoContrato())) {
                        cambios.put("tipoContrato", value);
                        actual.setTipoContrato((String) value);
                    }
                    break;
                case "idPuesto":
                    if (value != null && !value.equals(actual.getIdPuesto())) {
                        cambios.put("idPuesto", value);
                        actual.setIdPuesto((Integer) value);
                    }
                    break;
                case "idDepartamento":
                    if (value != null && !value.equals(actual.getIdDepartamento())) {
                        cambios.put("idDepartamento", value);
                        actual.setIdDepartamento((Integer) value);
                    }
                    break;
                default:
                    break;
            }
        }

        if (!cambios.isEmpty()) {
            empleadoRepositorio.save(actual);
        }

        return cambios;
    }
}

