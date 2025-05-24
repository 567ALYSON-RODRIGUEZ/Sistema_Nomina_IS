package com.api.nomina.datos;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EAsistencias;
import com.api.nomina.repositorio.AsistenciasRepositorio;

@Service
public class DAsistencias {

    @Autowired
    private AsistenciasRepositorio asistenciaRepositorio;

    public void crearAsistencia(
        Integer idAsistencia,
        Integer idEmpleado,
        Date fecha,
        Time horaEntrada,
        Time horaSalida,
        String tipo,
        String estado
    ) {
        asistenciaRepositorio.spGestionAsistencia(
            "CREATE", idAsistencia, idEmpleado, fecha, horaEntrada, horaSalida, tipo, estado
        );
    }

    public void actualizarAsistencia(
        Integer idAsistencia,
        Integer idEmpleado,
        Date fecha,
        Time horaEntrada,
        Time horaSalida,
        String tipo,
        String estado
    ) {
        asistenciaRepositorio.spGestionAsistencia(
            "UPDATE", idAsistencia, idEmpleado, fecha, horaEntrada, horaSalida, tipo, estado
        );
    }

    public void eliminarAsistencia(Integer idAsistencia) {
        asistenciaRepositorio.spGestionAsistencia(
            "DELETE", idAsistencia, null, null, null, null, null, null
        );
    }

    @Transactional(readOnly = true)
    public List<EAsistencias> listarAsistencias() {
        return asistenciaRepositorio.spAsistenciaListar(
            "READ", null, null, null, null, null, null, null
        );
    }

    @Transactional(readOnly = true)
    public EAsistencias buscarAsistenciaPorId(Integer idAsistencia) {
        List<EAsistencias> resultado = asistenciaRepositorio.spAsistenciaListar(
            "READ_ID", idAsistencia, null, null, null, null, null, null
        );
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    @Transactional
    public Map<String, Object> actualizarParcial(Integer idAsistencia, Map<String, Object> campos) {
        EAsistencias actual = buscarAsistenciaPorId(idAsistencia);
        if (actual == null) {
            throw new RuntimeException("Asistencia no encontrada");
        }

        Map<String, Object> cambios = new HashMap<>();

        for (Map.Entry<String, Object> entry : campos.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "idEmpleado":
                    if (value != null && !value.equals(actual.getIdEmpleado())) {
                        cambios.put("idEmpleado", value);
                        actual.setIdEmpleado((Integer) value);
                    }
                    break;
                case "fecha":
                    if (value != null && !value.equals(actual.getFecha())) {
                        cambios.put("fecha", value);
                        actual.setFecha((Date) value);
                    }
                    break;
                case "horaEntrada":
                    if (value != null && !value.equals(actual.getHoraEntrada())) {
                        cambios.put("horaEntrada", value);
                        actual.setHoraEntrada((Time) value);
                    }
                    break;
                case "horaSalida":
                    if (value != null && !value.equals(actual.getHoraSalida())) {
                        cambios.put("horaSalida", value);
                        actual.setHoraSalida((Time) value);
                    }
                    break;
                case "tipo":
                    if (value != null && !value.equals(actual.getTipo())) {
                        cambios.put("tipo", value);
                        actual.setTipo((String) value);
                    }
                    break;
                case "estado":
                    if (value != null && !value.equals(actual.getEstado())) {
                        cambios.put("estado", value);
                        actual.setEstado((String) value);
                    }
                    break;
                default:
                    break;
            }
        }

        if (!cambios.isEmpty()) {
            asistenciaRepositorio.save(actual);
        }

        return cambios;
    }
}
