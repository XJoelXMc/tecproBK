package com.proy.tecproBK.repository;

import com.proy.tecproBK.model.rolesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface rolesRepo extends JpaRepository<rolesModel,Integer> {

}
