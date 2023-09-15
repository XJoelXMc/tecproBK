package com.proy.tecproBK.repository;
import com.proy.tecproBK.model.usuariosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface usuariosRepo extends JpaRepository<usuariosModel,Integer> {
    //aqui agarramos al primer usuario usando esta consulta es decir el primer nombre
    @Query("SELECT u FROM usuariosModel u WHERE u.usuario=?1")
    Optional<usuariosModel>//llamamos al model usuario
    buscarnombre(String nombre);//este es el metodo que buscara por el nombre del usuario
}
