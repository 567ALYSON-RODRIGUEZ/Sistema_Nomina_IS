package com.api.nomina.modelo;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario_rol")
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "UsuarioRol.spGestionUsuarioRol",
        procedureName = "sp_gestion_usuario_rol",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "opc", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_usuario", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_rol", type = Integer.class)
        }
    ),
    @NamedStoredProcedureQuery(
        name = "UsuarioRol.listarTodos",
        procedureName = "sp_gestion_usuario_rol",
        resultClasses = EUsuarioRol.class,
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "opc", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_usuario", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_rol", type = Integer.class)
        }
    )
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EUsuarioRol {

    @EmbeddedId
    private EUsuarioRolId id;
}


