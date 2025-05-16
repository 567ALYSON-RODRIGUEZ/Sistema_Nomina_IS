package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NamedStoredProcedureQuery(
    name = "Empresa.spEmpresa",
    procedureName = "SP_Empresa",

    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "IdEmpresa", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "nombreLegal", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "sigla", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "nit", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "domicilioFiscal", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "correo", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "telefono", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class)
    })

    
    @NamedStoredProcedureQuery(
    	    name = "Empresa.spEmpresaListar",
    	    procedureName = "SP_Empresa",    
    	    resultClasses = EEmpresa.class,
    	    parameters = {
    	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
    	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "IdEmpresa", type = Integer.class),
    	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "nombreLegal", type = String.class),
    	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "sigla", type = String.class),
    	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "nit", type = String.class),
    	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "domicilioFiscal", type = String.class),
    	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "correo", type = String.class),
    	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "telefono", type = String.class),
    	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class)
    	    }
    
    
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Empresa")
public class EEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private int IdEmpresa;
    private String nombreLegal;
    private String sigla;
    private String nit;
    private String domicilioFiscal;
    private String correo;
    private String telefono;
    private String estado;
}
