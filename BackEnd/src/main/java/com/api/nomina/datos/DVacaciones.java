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
            // Manejar excepciones específicas del SP
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

    // Obtener todas las vacaciones
 // En DVacaciones.java
    @Transactional(readOnly = true)
    public List<EVacaciones> obtenerTodas() {
        return vacacionesRepositorio.listarTodos(); // Usa el nuevo método
    }

    // Obtener una vacación por ID
    @Transactional(readOnly = true)
    public EVacaciones obtenerPorId(Integer id) {
        try {
            List<EVacaciones> lista = vacacionesRepositorio.listarTodos();
            return lista.isEmpty() ? null : lista.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener vacación por ID: " + e.getMessage());
        }
    }
}