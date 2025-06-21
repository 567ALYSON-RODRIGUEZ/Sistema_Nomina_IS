package com.api.nomina.datos;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nomina.modelo.EVacaciones;
import com.api.nomina.repositorio.VacacionesRepositorio;

@Service
@Transactional
public class DVacaciones {

    @Autowired
    private VacacionesRepositorio vacacionesRepositorio;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Actualizar una vacación existente
    public void actualizarVacacion(Integer id, Integer idEmpleado, Date fechaInicio, Date fechaFin, Integer dias, String estado, Integer periodo_anio) {
        try {
            vacacionesRepositorio.ejecutarSPVacaciones("actualizar", id, idEmpleado, fechaInicio, fechaFin, dias, estado, periodo_anio);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar vacación: " + e.getMessage());
        }
    }

    // Eliminar vacación por ID
    public void eliminarVacacion(Integer id) {
        try {
            vacacionesRepositorio.ejecutarSPVacaciones("eliminar", id, null, null, null, null, null, null);
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
                (String) fila[4],
                (java.sql.Date) fila[5],      // fechaInicio
                (java.sql.Date) fila[6],      // fechaFin
                (Integer) fila[7],            // dias
                (String) fila[8]              // estado
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
        return vacacionesRepositorio.listarTodosDesdeSP("listar", null, null, null, null, null, null,null);
    }

    @Transactional(readOnly = true)
    public EVacaciones obtenerPorId(Integer id) {
        try {
            List<EVacaciones> lista = vacacionesRepositorio.listarTodosDesdeSP("obtener_por_id", id, null, null, null, null, null,null);
            return lista.isEmpty() ? null : lista.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener vacación por ID: " + e.getMessage());
        }
    }
    
    public Map<String, Integer> obtenerDiasDisponibles(Integer idEmpleado, Integer anio) {
        Map<String, Integer> result = new HashMap<>();
        
        // Días por derecho (15 por año)
        int diasAcumulados = 15;
        
        // Días usados (solo aprobados)
        int diasUsados = jdbcTemplate.queryForObject(
                "SELECT COALESCE(SUM(dias), 0) FROM vacaciones " +
                "WHERE id_empleado = ? AND YEAR(fecha_inicio) = ? AND estado = 'Aprobado'",
                Integer.class, idEmpleado, anio);
        
        result.put("diasAcumulados", diasAcumulados);
        result.put("diasUsados", diasUsados);
        result.put("diasDisponibles", diasAcumulados - diasUsados);
        
        return result;
    }
    
    public List<DVacacionesdto> listarPorEmpleadoAnio(Integer idEmpleado, Integer anio) {
        String sql = "SELECT v.id_vacacion, v.id_empleado, e.nombres AS nombreCompleto, " +
                     "p.nombre AS puesto, d.nombre AS nivelJerarquico, " +
                     "v.fecha_inicio, v.fecha_fin, v.dias, v.estado " +
                     "FROM vacaciones v " +
                     "INNER JOIN empleados e ON v.id_empleado = e.id_empleado " +
                     "LEFT JOIN puestos p ON e.id_puesto = p.id_puesto " +
                     "LEFT JOIN departamentos d ON e.id_departamento = d.id_departamento " +
                     "WHERE v.id_empleado = ? AND YEAR(v.fecha_inicio) = ? " +
                     "ORDER BY v.fecha_inicio DESC";

        return jdbcTemplate.query(sql, new Object[]{idEmpleado, anio}, (rs, rowNum) -> 
            new DVacacionesdto(
                rs.getInt("id_vacacion"),
                rs.getInt("id_empleado"),
                rs.getString("nombreCompleto"),
                rs.getString("puesto"),
                rs.getString("nivelJerarquico"),
                rs.getDate("fecha_inicio"),
                rs.getDate("fecha_fin"),
                rs.getInt("dias"),
                rs.getString("estado")
            )
        );
    }

    // Versión sobrecargada del método insertar con año
    public void insertarVacacion(Integer idEmpleado, Date fechaInicio, Date fechaFin, 
                               Integer dias, String estado, Integer anio) {
        try {
            String sql = "INSERT INTO vacaciones (id_empleado, fecha_inicio, fecha_fin, " +
                        "dias, estado, periodo_anio) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, idEmpleado, fechaInicio, fechaFin, dias, estado, anio);
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar vacación: " + e.getMessage());
        }
    }
}
