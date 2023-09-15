package com.proy.tecproBK.repository;

import com.proy.tecproBK.model.personasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface personasRepo extends JpaRepository<personasModel,Integer> {


}
