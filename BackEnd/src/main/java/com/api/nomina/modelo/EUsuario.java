package com.api.nomina.modelo;

import jakarta.persistence.*;
import lombok.*;

@NamedStoredProcedureQuery(
    name = "Usuario.spUsuario",
    procedureName = "SP_Usuarios",
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_usuario", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "username", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "password_hash", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "nombre_completo", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class)
    }
)

@NamedStoredProcedureQuery(
    name = "Usuario.spUsuarioListar",
    procedureName = "SP_Usuarios",
    resultClasses = EUsuario.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "accion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_usuario", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "username", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "password_hash", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "nombre_completo", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "estado", type = String.class)
    }
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class EUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int idUsuario;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(name = "estado")
    private String estado;
}
