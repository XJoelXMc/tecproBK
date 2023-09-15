package com.proy.tecproBK.controller;

import com.proy.tecproBK.model.personasModel;
import com.proy.tecproBK.repository.personasRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class personasController {
    @Autowired //hacemos una referencia a un repositorio
    personasRepo persona;
    @GetMapping("/api/lispersonas")
    public List<personasModel> listapersonas(){
        return persona.findAll();
    }
}
