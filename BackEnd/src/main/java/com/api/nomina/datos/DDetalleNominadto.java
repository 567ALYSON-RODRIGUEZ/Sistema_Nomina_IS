package com.api.nomina.datos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class DDetalleNominadto {

    private Integer idEmpleado;
    private String nombreCompleto;
    private String puesto;
    private Double totalIngresos;
    private Double totalDescuentos;
    private Double salarioNeto;

    public DDetalleNominadto(Integer idEmpleado, String nombreCompleto, String puesto, Double totalIngresos, Double totalDescuentos, Double salarioNeto) {
        this.idEmpleado = idEmpleado;
        this.nombreCompleto = nombreCompleto;
        this.puesto = puesto;
        this.totalIngresos = totalIngresos;
        this.totalDescuentos = totalDescuentos;
        this.salarioNeto = salarioNeto;
    }

  
}

