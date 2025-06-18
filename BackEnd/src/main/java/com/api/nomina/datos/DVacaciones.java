package com.api.nomina.datos;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EVacaciones;
import com.api.nomina.repositorio.VacacionesRepositorio;

@Service
@Transactional
public class DVacaciones {

    @Autowired
    private VacacionesRepositorio vacacionesRepositorio;

    // Insertar una nueva vacación
    public void insertarVacacion(Integer idEmpleado, Date fechaInicio, Date fechaFin, Integer dias, String estado) {
        try {
            vacacionesRepositorio.spGestionVacaciones("insertar", null, idEmpleado, fechaInicio, fechaFin, dias, estado);
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar vacación: " + e.getMessage());
        }
    }

    // Actualizar una vacación existente
    public void actualizarVacacion(Integer id, Integer idEmpleado, Date fechaInicio, Date fechaFin, Integer dias, String estado) {
        try {
            vacacionesRepositorio.spGestionVacaciones("actualizar", id, idEmpleado, fechaInicio, fechaFin, dias, estado);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar vacación: " + e.getMessage());
        }
    }

    // Eliminar vacación por ID
    public void eliminarVacacion(Integer id) {
        try {
            vacacionesRepositorio.spGestionVacaciones("eliminar", id, null, null, null, null, null);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar vacación: " + e.getMessage());
        }
    }

    // Listar con mapeo manual de List<Object[]> a List<DVacacionesdto>
    public List<DVacacionesdto> listarConNombrePuesto() {
        List<Object[]> resultados = vacacionesRepositorio.listarVacacionesConEmpleado();
        List<DVacacionesdto> listaDto = new java.util.ArrayList<>();

        for (Object[] fila : resultados) {
            DVacacionesdto dto = new DVacacionesdto(
                (Integer) fila[0],            // idVacacion
                (Integer) fila[1],            // idEmpleado
                (String) fila[2],             // nombreCompleto
                (String) fila[3],             // puesto
                (java.sql.Date) fila[4],      // fechaInicio
                (java.sql.Date) fila[5],      // fechaFin
                (Integer) fila[6],            // dias
                (String) fila[7]              // estado
            );
            listaDto.add(dto);
        }

        return listaDto;
    }

    public DVacacionesdto obtenerDetallePorId(Integer idVacacion) {
        return listarConNombrePuesto().stream()
            .filter(v -> v.getIdVacacion().equals(idVacacion))
            .findFirst()
            .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<EVacaciones> obtenerTodas() {
        return vacacionesRepositorio.listarTodosDesdeSP("listar", null, null, null, null, null, null);
    }

    @Transactional(readOnly = true)
    public EVacaciones obtenerPorId(Integer id) {
        try {
            List<EVacaciones> lista = vacacionesRepositorio.listarTodosDesdeSP("obtener_por_id", id, null, null, null, null, null);
            return lista.isEmpty() ? null : lista.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener vacación por ID: " + e.getMessage());
        }
    }
}
