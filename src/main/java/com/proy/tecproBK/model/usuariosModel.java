package com.proy.tecproBK.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor//creas con parametros
@NoArgsConstructor //constructor sin parametros
@Builder //se creara esta clase
@Table(name="usuarios")
public class usuariosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //usuario generado automaticamente
    private int id;
    @Column(name="usuario")
    String usuario;
    @Column(name="contrasenia")
    String contrasenia;
    @Column(columnDefinition = "int DEFAULT 1")
    int cod_estado;
    //creamos la relacion, el nombre de la  nueva tabla y unimos el id de los roles y usuarios
    @ManyToMany (fetch =FetchType.EAGER,targetEntity = rolesModel.class, cascade = CascadeType.PERSIST)//fetch nos trae los roles de la tabla roles, con targetEntity = rolesModel.class hacemos referencia a la tabla roles
    //creamos una tabla intermediay relacionamos las columnas
    @JoinTable(name="usrol",joinColumns=@JoinColumn(name="usuario"),inverseJoinColumns = @JoinColumn(name="id_rol"))
    private Set<rolesModel> roles;

    @ManyToOne
    @MapsId("seq_persona")
    @JoinColumn(name = "seq_persona")
    @JsonIgnore
    personasModel personas;







}
