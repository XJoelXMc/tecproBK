package com.proy.tecproBK.controller.Request;

import com.proy.tecproBK.model.rolesModel;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//Por medio de esta clase iremos creando
public class CrearUsuarioDTO {
    String usuario;
    String contrasenia;
    private Set<String> roles;
}
