package com.api.nomina.modelo;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EUsuarioRolId implements Serializable {

    private Integer id_usuario;
    private Integer id_rol;
}
