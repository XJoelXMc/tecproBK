package com.proy.tecproBK.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor//creas con parametros
@NoArgsConstructor //constructor sin parametros
@Builder //se creara esta clase
@Entity
@Table(name="personas")
public class personasModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //seq_persona generado automaticamente
    @Column(name = "seq_persona")
    int seq_persona;
    @Column(name = "cedula_identidad")
    String cedula_identidad;
    @Column(name = "nombres")
    String nombres;
    @Column(name = "apellido_paterno")
    String apellido_paterno;
    @Column(name = "apellido_materno")
    String apellido_materno;
    @Column(name = "fotografia")
    String forografia;
    @Column(name = "telefono_celular")
    String telefono_celular;
    @Column(name = "cod_tipo")
    String cod_tipo;
    @Column(name = "cod_estado")
    int cod_estado;
    @OneToMany(mappedBy = "personas")
    Set<usuariosModel> usuarios;
}
