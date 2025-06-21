package com.api.nomina.datos;



import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DVacacionesdto {
    private Integer idVacacion;
    private Integer idEmpleado;
    private String nombreCompleto;
    private String puesto;
    private String nivelJerarquico;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer dias;
    private String estado;
    private Integer diasDisponibles; // Nuevo campo

    public DVacacionesdto(Integer idVacacion, Integer idEmpleado, String nombreCompleto, 
                         String puesto, String nivelJerarquico, Date fechaInicio, 
                         Date fechaFin, Integer dias, String estado) {
        this.idVacacion = idVacacion;
        this.idEmpleado = idEmpleado;
        this.nombreCompleto = nombreCompleto;
        this.puesto = puesto;
        this.nivelJerarquico = nivelJerarquico;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.dias = dias;
        this.estado = estado;
    }
}