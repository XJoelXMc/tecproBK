package com.proy.tecproBK.controller;

import com.proy.tecproBK.controller.Request.CrearUsuarioDTO;
import com.proy.tecproBK.model.ERoles;
import com.proy.tecproBK.model.rolesModel;
import com.proy.tecproBK.model.usuariosModel;
import com.proy.tecproBK.repository.usuariosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class usuariosController {
    @Autowired
    //ENDPOINT
    usuariosRepo usuarioRepo;

    //lista usuarios
    @GetMapping("/api/infouser")
    public List<usuariosModel> listausuarios() {
        return usuarioRepo.findAll();
    }

    //Metodo para crear usuarios
    @PostMapping("/api/addusuario")
    public ResponseEntity<usuariosModel> addusuario(@RequestBody CrearUsuarioDTO crearUsuarioDTO) {
        Set<rolesModel> roles = crearUsuarioDTO.getRoles().
                stream().map(rol -> rolesModel.builder().
                        nombre(ERoles.valueOf(rol)).
                        build()).collect(Collectors.toSet());
        usuariosModel usuarios = usuariosModel.builder().
                usuario(crearUsuarioDTO.getUsuario()).
                contrasenia(crearUsuarioDTO.getContrasenia()).
                roles(roles).
                build();
        usuarioRepo.save(usuarios);
        return ResponseEntity.ok(usuarios);
    }

    //Metodo para eliminar usuarios de la base de datos
    @DeleteMapping("/api/deluserbd/{id}")
    public void deleteUsersbd(@PathVariable int id) {
        usuarioRepo.deleteById(id);
    }

    //Metodo para eliminar usuarios de la base de datos
    @DeleteMapping("/api/deluser/{id}")
    public void deleteUsers(@PathVariable int id) {
        usuariosModel user= usuarioRepo.getById(id);
        user.setCod_estado(1);
        usuarioRepo.save(user);
    }
    @GetMapping("/api/deluser/holamundosecurity")
    public String holamundo(){
        return "hola mundo soy la seguridad";
    }

    @GetMapping("/api/deluser/holamundossecurity")
    public String holamundos(){
        return "hola mundo no soy la seguridad";
    }


}
