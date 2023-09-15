package com.proy.tecproBK.Service;

import com.proy.tecproBK.model.usuariosModel;
import com.proy.tecproBK.repository.usuariosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private usuariosRepo usuariosRepo;
    @Override
    //Vamos a cargar el metodo que esta en el repositorio hace la consulta
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        usuariosModel usuariosModel = usuariosRepo.buscarnombre(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " no existe"));
        Collection< ? extends GrantedAuthority> authorities=usuariosModel.getRoles().stream().map((roles)-> new SimpleGrantedAuthority("ROLE_".concat(roles.getNombre().name()))).collect(Collectors.toSet());//Convertimos roles a tipo  GrantedAuthority
        return new User(usuariosModel.getUsuario(),usuariosModel.getContrasenia(),true,true,true,true,authorities);
    }
}
